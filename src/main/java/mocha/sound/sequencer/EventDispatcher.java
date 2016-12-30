package mocha.sound.sequencer;

import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

import javax.sound.midi.MetaMessage;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.ControllerEventListener;

class EventDispatcher implements Runnable {

  private static final int AUTO_CLOSE_TIME = 5000;

  private final ArrayList<EventInfo> eventQueue = new ArrayList<>();

  private Thread thread = null;

  private final ArrayList<ClipInfo> autoClosingClips = new ArrayList<>();
  private final ArrayList<LineMonitor> lineMonitors = new ArrayList<>();

  /**
   * Approximate interval between calls to LineMonitor.checkLine
   */
  static final int LINE_MONITOR_TIME = 400;

  /**
   * This start() method starts an event thread if one is not already active.
   */
  synchronized void start() {

    if (thread == null) {
      thread = JSSecurityManager.createThread(this, "Java Sound Event Dispatcher", true, -1, true);
    }
  }

  /**
   * Invoked when there is at least one event in the queue. Implement this as a
   * callback to process one event.
   */
  protected void processEvent(EventInfo eventInfo) {
    int count = eventInfo.getListenerCount();

    // process an LineEvent
    if (eventInfo.getEvent() instanceof LineEvent) {
      LineEvent event = (LineEvent) eventInfo.getEvent();
      for (int i = 0; i < count; i++) {
        try {
          ((LineListener) eventInfo.getListener(i)).update(event);
        } catch (Throwable t) {
          t.printStackTrace();
        }
      }
      return;
    }

    // process a MetaMessage
    if (eventInfo.getEvent() instanceof MetaMessage) {
      MetaMessage event = (MetaMessage) eventInfo.getEvent();
      for (int i = 0; i < count; i++) {
        try {
          ((MetaEventListener) eventInfo.getListener(i)).meta(event);
        } catch (Throwable t) {
          t.printStackTrace();
        }
      }
      return;
    }

    // process a Controller or Mode Event
    if (eventInfo.getEvent() instanceof ShortMessage) {
      ShortMessage event = (ShortMessage) eventInfo.getEvent();
      int status = event.getStatus();

      // Controller and Mode events have status byte 0xBc, where
      // c is the channel they are sent on.
      if ((status & 0xF0) == 0xB0) {
        for (int i = 0; i < count; i++) {
          try {
            ((ControllerEventListener) eventInfo.getListener(i)).controlChange(event);
          } catch (Throwable t) {
            t.printStackTrace();
          }
        }
      }
      return;
    }

    System.err.println("Unknown event type: " + eventInfo.getEvent());
  }

  protected void dispatchEvents() {

    EventInfo eventInfo = null;

    synchronized (this) {
      try {
        if (eventQueue.isEmpty()) {
          if (autoClosingClips.size() > 0 || lineMonitors.size() > 0) {
            int waitTime = AUTO_CLOSE_TIME;
            if (lineMonitors.size() > 0) {
              waitTime = LINE_MONITOR_TIME;
            }
            wait(waitTime);
          } else {
            wait();
          }
        }
      } catch (InterruptedException e) {
      }
      if (eventQueue.size() > 0) {
        // Remove the event from the queue and dispatch it to the listeners.
        eventInfo = (EventInfo) eventQueue.remove(0);
      }

    } // end of synchronized
    if (eventInfo != null) {
      processEvent(eventInfo);
    } else {
      if (autoClosingClips.size() > 0) {
        closeAutoClosingClips();
      }
      if (lineMonitors.size() > 0) {
        monitorLines();
      }
    }
  }

  /**
   * Queue the given event in the event queue.
   */
  private synchronized void postEvent(EventInfo eventInfo) {
    eventQueue.add(eventInfo);
    notifyAll();
  }

  /**
   * A loop to dispatch events.
   */
  public void run() {

    while (true) {
      try {
        dispatchEvents();
      } catch (Throwable t) {
        t.printStackTrace();
      }
    }
  }

  /**
   * Send audio and MIDI events.
   */
  void sendAudioEvents(Object event, List listeners) {
    if (listeners == null || listeners.isEmpty()) {
      // nothing to do
      return;
    }

    start();

    EventInfo eventInfo = new EventInfo(event, listeners);
    postEvent(eventInfo);
  }

  private void closeAutoClosingClips() {
    synchronized (autoClosingClips) {
      long currTime = System.currentTimeMillis();
      for (int i = autoClosingClips.size() - 1; i >= 0; i--) {
        ClipInfo info = autoClosingClips.get(i);
        if (info.isExpired(currTime)) {
          AutoClosingClip clip = info.getClip();
          // sanity check
          if (!clip.isOpen() || !clip.isAutoClosing()) {
            autoClosingClips.remove(i);
          } else if (!clip.isRunning() && !clip.isActive() && clip.isAutoClosing()) {
            clip.close();
          } else {
          }
        } else {
        }
      }
    }
  }

  private int getAutoClosingClipIndex(AutoClosingClip clip) {
    synchronized (autoClosingClips) {
      for (int i = autoClosingClips.size() - 1; i >= 0; i--) {
        if (clip.equals(autoClosingClips.get(i).getClip())) {
          return i;
        }
      }
    }
    return -1;
  }

  /**
   * called from auto-closing clips when one of their open() method is called
   */
  void autoClosingClipOpened(AutoClosingClip clip) {
    int index = 0;
    synchronized (autoClosingClips) {
      index = getAutoClosingClipIndex(clip);
      if (index == -1) {
        autoClosingClips.add(new ClipInfo(clip));
      }
    }
    if (index == -1) {
      synchronized (this) {
        notifyAll();
      }
    }
  }

  /**
   * called from auto-closing clips when their closed() method is called
   */
  void autoClosingClipClosed(AutoClosingClip clip) {
    // nothing to do -- is removed from arraylist above
  }

  private void monitorLines() {
    synchronized (lineMonitors) {
      for (int i = 0; i < lineMonitors.size(); i++) {
        lineMonitors.get(i).checkLine();
      }
    }
  }

  void addLineMonitor(LineMonitor lm) {
    synchronized (lineMonitors) {
      if (lineMonitors.indexOf(lm) >= 0) {
        return;
      }
      lineMonitors.add(lm);
    }
    synchronized (this) {
      // need to interrupt the infinite wait()
      notifyAll();
    }
  }

  /**
   * Remove this LineMonitor instance from the list of monitors
   */
  void removeLineMonitor(LineMonitor lm) {
    synchronized (lineMonitors) {
      if (lineMonitors.indexOf(lm) < 0) {
        return;
      }
      lineMonitors.remove(lm);
    }
  }

  // /////////////////////////////////// INNER CLASSES ////////////////////////////////////////// //
  /**
   * Container for an event and a set of listeners to deliver it to.
   */
  private class EventInfo {

    private Object event;
    private Object[] listeners;

    /**
     * Create a new instance of this event Info class
     *
     * @param event the event to be dispatched
     * @param listeners listener list; will be copied
     */
    EventInfo(Object event, List listeners) {
      this.event = event;
      this.listeners = listeners.toArray();
    }

    Object getEvent() {
      return event;
    }

    int getListenerCount() {
      return listeners.length;
    }

    Object getListener(int index) {
      return listeners[index];
    }

  } // class EventInfo

  /**
   * Container for a clip with its expiration time
   */
  private class ClipInfo {

    private AutoClosingClip clip;
    private long expiration;

    /**
     * Create a new instance of this clip Info class
     */
    ClipInfo(AutoClosingClip clip) {
      this.clip = clip;
      this.expiration = System.currentTimeMillis() + AUTO_CLOSE_TIME;
    }

    AutoClosingClip getClip() {
      return clip;
    }

    boolean isExpired(long currTime) {
      return currTime > expiration;
    }
  } // class ClipInfo

  /**
   * Interface that a class that wants to get regular line monitor events
   * implements
   */
  interface LineMonitor {

    /**
     * Called by event dispatcher in regular intervals
     */
    public void checkLine();
  }

}
