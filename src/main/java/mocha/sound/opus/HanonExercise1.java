package mocha.sound.opus;


import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Synthesizer;
import static mocha.sound.midi.MidiUtil.playMidi;
import mocha.sound.midi.PhraseEditor;
import mocha.sound.midi.SequenceEditor;
import mocha.sound.note.Scale;

/*
 *  mocha-java.com  * 
 */
public class HanonExercise1 {

  public static void main(String[] args) throws MidiUnavailableException {

    Scale scale = new Scale(0, 2, 4, 5, 7, 9, 11);

    double tempo = 80;
    SequenceEditor se = new SequenceEditor(1, 2, tempo);
    se.getTrackEditor(0).setProgram(0, 0, 0, 0, 73);
    PhraseEditor pe1 = new PhraseEditor(se) {
      @Override
      public void playPhrase(int[] notes) {
        play(0, 0.00, 0, notes[0], 100, 0.25);
        play(0, 0.25, 0, notes[1], 90, 0.25);
        play(0, 0.50, 0, notes[2], 80, 0.25);
        play(0, 0.75, 0, notes[3], 80, 0.25);
        play(0, 1.00, 0, notes[4], 80, 0.2);
        play(0, 1.25, 0, notes[5], 70, 0.2);
        play(0, 1.50, 0, notes[6], 70, 0.2);
        play(0, 1.75, 0, notes[7], 70, 0.2);
      }
    };
    PhraseEditor pe2 = new PhraseEditor(se) {
      @Override
      public void playPhrase(int[] notes) {
        play(0, 0.00, 0, notes[0], 100, 2);
      }
    };
    int measuer = 1;
    for (int i = 0; i < 14; i++) {
      int[] notes = new int[]{
        scale.note(60, 0, 0 + i),
        scale.note(60, 0, 2 + i),
        scale.note(60, 0, 3 + i),
        scale.note(60, 0, 4 + i),
        scale.note(60, 0, 5 + i),
        scale.note(60, 0, 4 + i),
        scale.note(60, 0, 3 + i),
        scale.note(60, 0, 2 + i),};
      pe1.playPhrase(measuer++, 0, notes);
    }
    for (int i = 0; i < 15; i++) {
      int[] notes = new int[]{
        scale.note(60, 2, 4 - i),
        scale.note(60, 2, 2 - i),
        scale.note(60, 2, 1 - i),
        scale.note(60, 2, 0 - i),
        scale.note(60, 2, -1 - i),
        scale.note(60, 2, 0 - i),
        scale.note(60, 2, 1 - i),
        scale.note(60, 2, 2 - i),};
      pe1.playPhrase(measuer++, 0, notes);
    }
    pe2.playPhrase(measuer++, 0, scale.note(60, 0, 0));

    //MidiDevice aria = MidiUtil.getMidiDevices("ARIA", true)[0];
    //aria.open();
    //Receiver receiver = aria.getReceiver();
    Synthesizer synthesizer = MidiSystem.getSynthesizer();
    synthesizer.open();
    Receiver receiver = synthesizer.getReceiver();    
    playMidi(se.getSequence(), receiver);
  }

}
