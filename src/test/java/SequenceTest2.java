
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Receiver;
import javax.sound.midi.Synthesizer;
import static mocha.sound.midi.MidiUtil.playMidi;
import mocha.sound.midi.PhraseEditor;
import mocha.sound.midi.SequenceEditor;
import mocha.sound.midi.TrackEditor;

public class SequenceTest2 {

  public static void main(String[] args) throws Exception {
double tempo = 65;
SequenceEditor se = new SequenceEditor(2, 4, tempo);

    TrackEditor te1 = se.getTrackEditor(0);
    te1.setProgram(0, 0, 0, 0, 42);
PhraseEditor pe1 = new PhraseEditor(se) {
  @Override
  public void playPhrase(int[] notes) {
    setTempo(0, 0, tempo * 0.78);
    setTempo(0, 0.25, tempo * 0.91);
    setTempo(0, 0.5, tempo);
    setTempo(0, 1, tempo * 0.96);
    play(0, 0.00, 0, notes[0], 100, 0.3);
    play(0, 0.25, 0, notes[1], 90, 0.3);
    play(0, 0.50, 0, notes[2], 90, 0.28);
    play(0, 0.75, 0, notes[3], 80, 0.24);
    play(0, 1.00, 0, notes[4], 90, 0.13);
    play(0, 1.25, 0, notes[5], 70, 0.18);
    play(0, 1.50, 0, notes[6], 70, 0.15);
    play(0, 1.75, 0, notes[7], 60, 0.15);
  }
};

pe1.playPhrase(1, 0, 43, 50, 59, 57, 59, 50, 59, 50);
pe1.playPhrase(1, 2, 43, 50, 59, 57, 59, 50, 59, 50);
pe1.playPhrase(2, 0, 43, 52, 60, 59, 60, 52, 60, 52);
pe1.playPhrase(2, 2, 43, 52, 60, 59, 60, 52, 60, 52);
pe1.playPhrase(3, 0, 43, 54, 60, 59, 60, 54, 60, 54);
pe1.playPhrase(3, 2, 43, 54, 60, 59, 60, 54, 60, 54);
pe1.playPhrase(4, 0, 43, 55, 59, 57, 59, 55, 59, 55);
pe1.playPhrase(4, 2, 43, 55, 59, 57, 59, 55, 59, 54);

    
    Synthesizer synthesizer = MidiSystem.getSynthesizer();
    synthesizer.open();
    Receiver receiver = synthesizer.getReceiver();
    //MidiDevice aria = MidiUtil.getMidiDevices("ARIA", true)[0];
    //aria.open();
    //Receiver receiver = aria.getReceiver();
    playMidi(se.getSequence(), receiver);
  }
}
