package mocha.sound.sequencer;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Transmitter;

abstract class AbstractMidiDevice implements MidiDevice, ReferenceCountingDevice {

  
  private final ArrayList<Receiver> receiverList;
  private final TransmitterList transmitterList;

  private final Object lock;

  private final MidiDevice.Info info;

  protected boolean open = false;
  private int openReferenceCount;
  private final List openKeepingObjects;

  protected long id = 0;

  protected AbstractMidiDevice(MidiDevice.Info info) {
    this.info = info;
    receiverList = new ArrayList<>();
    transmitterList = new TransmitterList();
    lock = new Object();
    open = false;
    openReferenceCount = 0;
    openKeepingObjects = new ArrayList();
  }
  @Override
  public MidiDevice.Info getDeviceInfo() {
    return info;
  }

  @Override
  public void open() throws MidiUnavailableException {
    synchronized (this) {
      openReferenceCount = -1;
      doOpen();
    }
  }
  private void openInternal(Object object) throws MidiUnavailableException {
    synchronized (this) {
      if (openReferenceCount != -1) {
        openReferenceCount++;
        openKeepingObjects.add(object);
      }
      doOpen();
    }
  }

  private void doOpen() throws MidiUnavailableException {
    synchronized (this) {
      if (!open) {
        implOpen();
        open = true;
      }
    }
  }

  @Override
  public void close() {
    synchronized (this) {
      doClose();
      openReferenceCount = 0;
    }
  }

  public void closeInternal(Object object) {
    synchronized (this) {
      if (openKeepingObjects.remove(object)) {
        if (openReferenceCount > 0) {
          openReferenceCount--;
          if (openReferenceCount == 0) {
            doClose();
          }
        }
      }
    }
  }

  public void doClose() {
    synchronized (this) {
      if (open) {
        implClose();
        open = false;
      }
    }
  }

  @Override
  public boolean isOpen() {
    return open;
  }

  protected void implClose() {
    synchronized (lock) {
      for (int i = 0; i < receiverList.size(); i++) {
        receiverList.get(i).close();
      }
      receiverList.clear();
      transmitterList.close();
    }
  }

  @Override
  public long getMicrosecondPosition() {
    return -1;
  }

  @Override
  public final int getMaxReceivers() {
    if (hasReceivers()) {
      return -1;
    } else {
      return 0;
    }
  }

  @Override
  public final int getMaxTransmitters() {
    if (hasTransmitters()) {
      return -1;
    } else {
      return 0;
    }
  }

  @Override
  public final Receiver getReceiver() throws MidiUnavailableException {
    Receiver receiver;
    synchronized (lock) {
      receiver = createReceiver();
      receiverList.add(receiver);
    }
    return receiver;
  }

  @Override
  public final List<Receiver> getReceivers() {
    List<Receiver> recs;
    synchronized (lock) {
      if (receiverList == null) {
        recs = Collections.unmodifiableList(new ArrayList<>(0));
      } else {
        recs = Collections.unmodifiableList((List<Receiver>) (receiverList.clone()));
      }
    }
    return recs;
  }

  @Override
  public final Transmitter getTransmitter() throws MidiUnavailableException {
    Transmitter transmitter;
    synchronized (lock) {
      transmitter = createTransmitter();
      getTransmitterList().add(transmitter);
    }
    return transmitter;
  }

  @Override
  public final List<Transmitter> getTransmitters() {
    List<Transmitter> tras;
    synchronized (lock) {
      if (transmitterList.transmitters.isEmpty()) {
        tras = Collections.unmodifiableList(new ArrayList<>(0));
      } else {
        tras = Collections.unmodifiableList((List<Transmitter>) (transmitterList.transmitters.clone()));
      }
    }
    return tras;
  }

  long getId() {
    return id;
  }


  @Override
  public Receiver getReceiverReferenceCounting() throws MidiUnavailableException {
    Receiver receiver;
    synchronized (lock) {
      receiver = getReceiver();
      openInternal(receiver);
    }
    return receiver;
  }

  @Override
  public Transmitter getTransmitterReferenceCounting() throws MidiUnavailableException {
    Transmitter transmitter;
    synchronized (lock) {
      transmitter = getTransmitter();
      openInternal(transmitter);
    }
    return transmitter;
  }

  protected boolean hasReceivers() {
    return false;
  }

  protected Receiver createReceiver() throws MidiUnavailableException {
    throw new MidiUnavailableException("MIDI IN receiver not available");
  }

  protected TransmitterList getTransmitterList() {
    return transmitterList;
  }

  protected boolean hasTransmitters() {
    return false;
  }

  protected Transmitter createTransmitter() throws MidiUnavailableException {
    throw new MidiUnavailableException("MIDI OUT transmitter not available");
  }

  protected abstract void implOpen() throws MidiUnavailableException;

  @Override
  protected void finalize() {
    close();
  }

  protected abstract class AbstractReceiver implements Receiver {
    private boolean open = true;

    @Override
    public synchronized void send(MidiMessage message, long timeStamp) {
      if (open) {
        implSend(message, timeStamp);
      } else {
        throw new IllegalStateException("Receiver is not open");
      }
    }

    protected abstract void implSend(MidiMessage message, long timeStamp);


    @Override
    public void close() {
      open = false;
      synchronized (lock) {
        receiverList.remove(this);
      }
      closeInternal(this);
    }

    protected boolean isOpen() {
      return open;
    }

  }

  protected class BasicTransmitter implements Transmitter {

    Receiver receiver;
    TransmitterList transmitterlist;

    protected BasicTransmitter() {
      receiver = null;
      transmitterlist = null;
    }

    private void setTransmitterList(TransmitterList transmitterlist) {
      this.transmitterlist = transmitterlist;
    }

    @Override
    public void setReceiver(Receiver receiver) {
      if (transmitterlist != null && this.receiver != receiver) {
        transmitterlist.receiverChanged(this, this.receiver, receiver);
        this.receiver = receiver;
      }
    }

    @Override
    public Receiver getReceiver() {
      return receiver;
    }

    @Override
    public void close() {
      closeInternal(this);
      if (transmitterlist != null) {
        transmitterlist.receiverChanged(this, this.receiver, null);
        transmitterlist.remove(this);
        transmitterlist = null;
      }
    }

  }

  class TransmitterList {

    private final ArrayList<Transmitter> transmitters;
    private MidiOutDevice.MidiOutReceiver midiOutReceiver;

    private int optimizedReceiverCount = 0;

    TransmitterList() {
      this.transmitters = new ArrayList<>();
    }

    private void add(Transmitter t) {
      synchronized (transmitters) {
        transmitters.add(t);
      }
      if (t instanceof BasicTransmitter) {
        ((BasicTransmitter) t).setTransmitterList(this);
      }
    }

    private void remove(Transmitter t) {
      synchronized (transmitters) {
        int index = transmitters.indexOf(t);
        if (index >= 0) {
          transmitters.remove(index);
        }
      }
    }

    private void receiverChanged(BasicTransmitter t, Receiver oldR, Receiver newR) {
      synchronized (transmitters) {
        if (midiOutReceiver == oldR) {
          midiOutReceiver = null;
        }
        if (newR != null) {
          if ((newR instanceof MidiOutDevice.MidiOutReceiver) && midiOutReceiver == null) {
            midiOutReceiver = (MidiOutDevice.MidiOutReceiver) newR;
          }
        }
        optimizedReceiverCount = midiOutReceiver != null ? 1 : 0;
      }
    }

    void close() {
      synchronized (transmitters) {
        for (int i = 0; i < transmitters.size(); i++) {
          transmitters.get(i).close();
        }
        transmitters.clear();
      }
    }

    void sendMessage(int packedMessage, long timeStamp) {
      try {
        synchronized (transmitters) {
          int size = transmitters.size();
          if (optimizedReceiverCount == size) {
            if (midiOutReceiver != null) {
              midiOutReceiver.sendPackedMidiMessage(packedMessage, timeStamp);
            }
          } else {
            for (int i = 0; i < size; i++) {
              Receiver receiver = ((Transmitter) transmitters.get(i)).getReceiver();
              if (receiver != null) {
                if (optimizedReceiverCount > 0) {
                  if (receiver instanceof MidiOutDevice.MidiOutReceiver) {
                    ((MidiOutDevice.MidiOutReceiver) receiver).sendPackedMidiMessage(packedMessage, timeStamp);
                  } else {
                    receiver.send(new FastShortMessage(packedMessage), timeStamp);
                  }
                } else {
                  receiver.send(new FastShortMessage(packedMessage), timeStamp);
                }
              }
            }
          }
        }
      } catch (InvalidMidiDataException e) {
        e.printStackTrace();
      }
    }

    void sendMessage(byte[] data, long timeStamp) {
      try {
        synchronized (transmitters) {
          int size = transmitters.size();
          for (int i = 0; i < size; i++) {
            Receiver receiver = ((Transmitter) transmitters.get(i)).getReceiver();
            if (receiver != null) {
              receiver.send(new FastSysexMessage(data), timeStamp);
            }
          }
        }
      } catch (InvalidMidiDataException e) {
        e.printStackTrace();
      }
    }

    void sendMessage(MidiMessage message, long timeStamp) {
      if (message instanceof FastShortMessage) {
        sendMessage(((FastShortMessage) message).getPackedMsg(), timeStamp);
        return;
      }
      synchronized (transmitters) {
        int size = transmitters.size();
        if (optimizedReceiverCount == size) {
          if (midiOutReceiver != null) {
            midiOutReceiver.send(message, timeStamp);
          }
        } else {
          for (int i = 0; i < size; i++) {
            Receiver receiver = ((Transmitter) transmitters.get(i)).getReceiver();
            if (receiver != null) {
              receiver.send(message, timeStamp);
            }
          }
        }
      }
    }
  }
}
