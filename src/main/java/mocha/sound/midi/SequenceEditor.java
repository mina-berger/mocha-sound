/*
 *  mocha-java.com  * 
 */
package mocha.sound.midi;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.Sequence;
import javax.sound.midi.Track;

/**
 *
 * @author minaberger
 */
public class SequenceEditor {
  public static final int RESOLUTION = 480;
  MidiTimeSign timeSign;
  Sequence sequence;
  Track conductor;
  TrackEditor[] trackEditors;
public SequenceEditor(int trackLength, double initialBeat, double tempo){
  if(trackLength > 16){
    throw new IllegalArgumentException("max of trackLength is 16");
  }
  this.timeSign = new MidiTimeSign(RESOLUTION, initialBeat);
  try {
    sequence = new Sequence(Sequence.PPQ, RESOLUTION);
  } catch (InvalidMidiDataException ex) {
    throw new IllegalStateException(ex);
  }
  conductor = sequence.createTrack();
  trackEditors = new TrackEditor[trackLength];
  for(int i = 0;i < trackLength;i++){
    trackEditors[i] = new TrackEditor(timeSign, sequence.createTrack(), i);
  }
  setTempo(0, 0, tempo);
}
public TrackEditor getTrackEditor(int index){
  return trackEditors[index];
}
public Sequence getSequence(){
  return sequence;
}
public MidiTimeSign getTimeSign(){
  return timeSign;
}
public final void setTempo(int measure, double beat, double bpm) {
  long tick = timeSign.getTick(measure, beat);
  long mpq = Math.round(60000000d / bpm);
  byte[] data = new byte[3];
  data[0] = new Long(mpq / 0x10000).byteValue();
  data[1] = new Long((mpq / 0x100) % 0x100).byteValue();
  data[2] = new Long(mpq % 0x100).byteValue();
  try {
    conductor.add(new MidiEvent(new MetaMessage(0x51, data, data.length), tick));
  } catch (InvalidMidiDataException ex) {
    throw new IllegalStateException(ex);
  }
}
}
