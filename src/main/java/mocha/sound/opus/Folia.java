/*
 *  mocha-java.com  * 
 */
package mocha.sound.opus;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import mocha.sound.midi.MidiUtil;
import static mocha.sound.midi.MidiUtil.playMidi;
import mocha.sound.midi.PhraseEditor;
import mocha.sound.midi.SequenceEditor;
import mocha.sound.note.Scale;

/**
 *
 * @author minaberger
 */
public class Folia {

  public static void main(String[] args) throws MidiUnavailableException {
    Scale minor = new Scale(0, 2, 3, 5, 7, 8, 11);
    Scale major = new Scale(0, 2, 4, 5, 7, 9, 11);

    double tempo = 80;
    SequenceEditor se = new SequenceEditor(4, 3, tempo);
    //se.getTrackEditor(0).setProgram(0, 0, 0, 0, 73);
    PhraseEditor pe1 = new PhraseEditor(se) {
      @Override
      public void playPhrase(int[] notes) {
        // violin-1
        play(0, 0.00, 0, notes[3], 100, 0.5);
        play(0, 1.00, 0, notes[3], 90, 1.5);
        play(0, 2.50, 0, notes[4], 90, 0.4);

        // violin-2
        play(0, 0.00, 1, notes[2], 100, 0.5);
        play(0, 1.00, 1, notes[2], 90, 1.75);

        // viola
        play(0, 1.00, 2, notes[1], 90, 1.75);
        play(0, 0.00, 2, notes[1], 100, 0.5);

        // cello
        play(0, 0.00, 3, notes[0], 100, 1.75);
        play(0, 2.00, 3, notes[0], 90, 1.0);
      }
    };
    PhraseEditor pe2 = new PhraseEditor(se) {
      @Override
      public void playPhrase(int[] notes) {
        // violin-1
        play(0, 0.00, 0, notes[3], 100, 0.5);
        play(0, 1.00, 0, notes[3], 90, 1.00);
        play(0, 2.00, 0, notes[3], 80, 0.75);

        // violin-2
        play(0, 0.00, 1, notes[2], 100, 0.5);
        play(0, 1.00, 1, notes[2], 90, 1.75);

        // viola
        play(0, 0.00, 2, notes[1], 100, 0.5);
        play(0, 1.00, 2, notes[1], 90, 1.75);

        // cello
        play(0, 0.00, 3, notes[0], 100, 1.75);
        play(0, 2.00, 3, notes[0], 90, 1.0);
      }
    };
    PhraseEditor pe3 = new PhraseEditor(se) {
      @Override
      public void playPhrase(int[] notes) {
        play(0, 0.00, 0, notes[3], 100, 0.5);
        play(0, 0.00, 1, notes[2], 100, 0.5);
        play(0, 0.00, 2, notes[1], 100, 0.5);
        play(0, 0.00, 3, notes[0], 100, 0.5);

        play(0, 1.00, 0, notes[7], 90, 1.5);
        play(0, 2.50, 0, notes[8], 90, 0.5);
        play(0, 1.00, 1, notes[6], 90, 1.75);
        play(0, 1.00, 2, notes[5], 90, 1.75);
        play(0, 1.00, 3, notes[4], 90, 2.0);
      }
    };
    PhraseEditor pe4 = new PhraseEditor(se) {
      @Override
      public void playPhrase(int[] notes) {
        play(0, 0.00, 0, notes[3], 100, 3);
        play(0, 0.00, 1, notes[2], 100, 3);
        play(0, 0.00, 2, notes[1], 100, 3);
        play(0, 0.00, 3, notes[0], 100, 3);
      }
    };
    int measure = 1;
    for (int i = 0; i < 2; i++) {
      pe1.playPhrase(measure++, 0,
        minor.note(62, -1, 0),
        minor.note(62, 0, 2),
        minor.note(62, 0, 4),
        minor.note(62, 1, 0),
        minor.note(62, 1, 1));
      pe2.playPhrase(measure++, 0,
        minor.note(62, -2, 4),
        minor.note(62, 0, 1),
        minor.note(62, 0, 3),
        minor.note(62, 1, -1));
      pe1.playPhrase(measure++, 0,
        minor.note(62, -1, 0),
        minor.note(62, 0, 2),
        minor.note(62, 0, 4),
        minor.note(62, 1, 0),
        minor.note(62, 1, 0));
      pe2.playPhrase(measure++, 0,
        major.note(65, -2, 4),
        major.note(65, 0, 1),
        major.note(65, 0, 3),
        major.note(65, 1, -1));
      pe1.playPhrase(measure++, 0,
        major.note(65, -1, 0),
        major.note(65, 0, 2),
        major.note(65, 0, 4),
        major.note(65, 1, 0),
        major.note(65, 1, 1));
      pe2.playPhrase(measure++, 0,
        major.note(65, -2, 4),
        major.note(65, 0, 1),
        major.note(65, 0, 3),
        major.note(65, 1, -1));
      if (i == 0) {
        pe1.playPhrase(measure++, 0,
          minor.note(62, -1, 0),
          minor.note(62, 0, 2),
          minor.note(62, 0, 4),
          minor.note(62, 1, 0),
          minor.note(62, 1, 1));
        pe2.playPhrase(measure++, 0,
          minor.note(62, -2, 4),
          minor.note(62, 0, 1),
          minor.note(62, 0, 3),
          minor.note(62, 1, -1));
      } else {
        pe3.playPhrase(measure++, 0,
          minor.note(62, -1, 0),
          minor.note(62, 0, 2),
          minor.note(62, 0, 4),
          minor.note(62, 1, 0),
          minor.note(62, -2, 4),
          minor.note(62, 0, 1),
          minor.note(62, 0, 3),
          minor.note(62, 1, 0),
          minor.note(62, 1, -1));
        pe4.playPhrase(measure++, 0,
          minor.note(62, -2, 0),
          minor.note(62, 0, 2),
          minor.note(62, 0, 4),
          minor.note(62, 1, 0));
      }
    }

    MidiDevice aria = MidiUtil.getMidiDevices("ARIA", true)[0];
    aria.open();
    Receiver receiver = aria.getReceiver();
    //Synthesizer synthesizer = MidiSystem.getSynthesizer();
    //synthesizer.open();
    //Receiver receiver = synthesizer.getReceiver();    
    playMidi(se.getSequence(), receiver);
  }

}
