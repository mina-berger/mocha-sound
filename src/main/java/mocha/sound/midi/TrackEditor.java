/*
 *  mocha-java.com  * 
 */
package mocha.sound.midi;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

/**
 *
 * @author minaberger
 */
public class TrackEditor {

  MidiTimeSign timeSign;
  Track track;
  int channel;

  public TrackEditor(MidiTimeSign timeSign, Track track, int channel) {
    this.timeSign = timeSign;
    this.track = track;
    this.channel = channel;
  }

  public void setProgram(int measure, double beat, int bank_m, int bank_l, int program) {
    long tick = timeSign.getTick(measure, beat);
    try {
      track.add(new MidiEvent(new ShortMessage(ShortMessage.CONTROL_CHANGE, channel, 0x00, bank_m), tick));
      track.add(new MidiEvent(new ShortMessage(ShortMessage.CONTROL_CHANGE, channel, 0x20, bank_l), tick));
      track.add(new MidiEvent(new ShortMessage(ShortMessage.PROGRAM_CHANGE, channel, program, 0), tick));
    } catch (InvalidMidiDataException ex) {
      throw new IllegalStateException(ex);
    }
  }

  public void play(int measure, double beat, int note, int velocity, double duration) {
    long tickOn = timeSign.getTick(measure, beat);
    long tickOff = timeSign.getTick(measure, beat + duration);
    try {
      track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON, channel, note, velocity), tickOn));
      track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON, channel, note, 0), tickOff));
    } catch (InvalidMidiDataException ex) {
      throw new IllegalStateException(ex);
    }
  }

}
