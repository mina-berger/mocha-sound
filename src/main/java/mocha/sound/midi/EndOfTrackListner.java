package mocha.sound.midi;

import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequencer;

public class EndOfTrackListner extends Thread implements MetaEventListener {

  private final Sequencer sequencer;

  public EndOfTrackListner(Sequencer sequencer) {
    this.sequencer = sequencer;
  }

  @Override
  public void meta(MetaMessage message) {
    if (message.getType() != 0x2f) {
      return;
    }
    try {
      sleep(1000);
    } catch (InterruptedException e) {
      System.out.println("failed to sleep");
    }
    sequencer.close();
    for (Receiver receiver : sequencer.getReceivers()) {
      receiver.close();
    }
  }
}
