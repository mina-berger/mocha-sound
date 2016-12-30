package mocha.sound.sequencer;

import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Transmitter;

public interface ReferenceCountingDevice {

    public Receiver getReceiverReferenceCounting() throws MidiUnavailableException;

    public Transmitter getTransmitterReferenceCounting() throws MidiUnavailableException;
}