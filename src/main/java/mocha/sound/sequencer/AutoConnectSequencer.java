package mocha.sound.sequencer;

import javax.sound.midi.Receiver;

/**
 * Interface for Sequencers that are able to do the auto-connect as required by
 * MidiSystem.getSequencer()
 *
 * @author Florian Bomers
 */
public interface AutoConnectSequencer {

  /**
   * Set the receiver that this device is auto-connected. If non-null, the
   * device needs to re-connect itself to a suitable device in open().
   * @param autoConnectReceiver
   */
  public void setAutoConnect(Receiver autoConnectReceiver);

}
