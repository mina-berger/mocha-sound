package mocha.sound.opus;


import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Receiver;
import javax.sound.midi.Synthesizer;
import mocha.sound.midi.MidiUtil;
import static mocha.sound.midi.MidiUtil.playMidi;
import mocha.sound.midi.PhraseEditor;
import mocha.sound.midi.SequenceEditor;
import mocha.sound.midi.TrackEditor;

public class BWV1007Prelude {

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
    PhraseEditor pe2 = new PhraseEditor(se) {
      @Override
      public void playPhrase(int... notes) {
        setTempo(0, 0, tempo);
        setTempo(0, 1.5, tempo * 0.9);
        play(0, 0.00, 0, notes[0], 90, 0.20);
        play(0, 0.25, 0, notes[1], 70, 0.15);
        play(0, 0.50, 0, notes[2], 80, 0.15);
        play(0, 0.75, 0, notes[3], 70, 0.15);
        play(0, 1.00, 0, notes[4], 90, 0.28);
        play(0, 1.25, 0, notes[5], 70, 0.18);
        play(0, 1.50, 0, notes[6], 80, 0.28);
        play(0, 1.75, 0, notes[7], 70, 0.18);
      }
    };
    PhraseEditor pe3 = new PhraseEditor(se) {
      @Override
      public void playPhrase(int... notes) {
        setTempo(0, 0, tempo * 0.78);
        setTempo(0, 0.25, tempo * 0.91);
        setTempo(0, 0.5, tempo);
        setTempo(0, 1, tempo * 0.8);
        setTempo(0, 1.25, tempo * 0.6);
        setTempo(0, 1.5, tempo / 4);
        setTempo(0, 2.25, tempo * 0.96);
        play(0, 0.00, 0, notes[0], 100, 0.3);
        play(0, 0.25, 0, notes[1], 90, 0.3);
        play(0, 0.50, 0, notes[2], 90, 0.28);
        play(0, 0.75, 0, notes[3], 80, 0.28);
        play(0, 1.00, 0, notes[4], 90, 0.13);
        play(0, 1.25, 0, notes[5], 70, 0.18);
        play(0, 1.50, 0, notes[6], 100, 0.5);
        play(0, 2.25, 0, notes[7], 90, 0.28);
        play(0, 2.50, 0, notes[8], 90, 0.28);
        play(0, 2.75, 0, notes[9], 80, 0.28);
        play(0, 3.00, 0, notes[10], 90, 0.28);
        play(0, 3.25, 0, notes[11], 70, 0.28);
        play(0, 3.50, 0, notes[12], 70, 0.28);
        play(0, 3.75, 0, notes[13], 60, 0.25);
      }
    };
    PhraseEditor pe4 = new PhraseEditor(se) {
      @Override
      public void playPhrase(int... notes) {
        setTempo(0, 0, tempo * 0.78);
        setTempo(0, 0.25, tempo * 0.91);
        setTempo(0, 1, tempo);
        play(0, 0.00, 0, notes[0], 85, 0.28);
        play(0, 0.25, 0, notes[1], 80, 0.28);
        play(0, 0.50, 0, notes[2], 75, 0.28);
        play(0, 0.75, 0, notes[3], 80, 0.15);
      }
    };
    PhraseEditor pe5 = new PhraseEditor(se) {
      @Override
      public void playPhrase(int... notes) {
        setTempo(0, 0, tempo * 0.7);
        setTempo(0, 0.5, tempo);
        setTempo(0, 1.5, tempo * 0.9);
        play(0, 0.00, 0, notes[0], 90, 0.4);
        play(0, 0.50, 0, notes[1], 80, 0.25);
        play(0, 0.75, 0, notes[2], 70, 0.25);
        play(0, 1.00, 0, notes[3], 70, 0.25);
        play(0, 1.25, 0, notes[4], 70, 0.25);
        play(0, 1.50, 0, notes[5], 70, 0.25);
        play(0, 1.75, 0, notes[6], 70, 0.2);
      }
    };
    PhraseEditor pe6 = new PhraseEditor(se) {
      @Override
      public void playPhrase(int... notes) {
        setTempo(0, 0, tempo * 0.8);
        setTempo(0, 0.25, tempo);
        setTempo(0, 1.5, tempo * 0.9);
        play(0, 0.00, 0, notes[0], 90, 0.2);
        play(0, 0.25, 0, notes[1], 90, 0.25);
        play(0, 0.50, 0, notes[2], 80, 0.25);
        play(0, 0.75, 0, notes[3], 70, 0.25);
        play(0, 1.00, 0, notes[4], 70, 0.25);
        play(0, 1.25, 0, notes[5], 70, 0.25);
        play(0, 1.50, 0, notes[6], 70, 0.25);
        play(0, 1.75, 0, notes[7], 70, 0.2);
      }
    };
    PhraseEditor pe7 = new PhraseEditor(se) {
      @Override
      public void playPhrase(int... notes) {
        setTempo(0, 0, tempo * 0.78);
        setTempo(0, 0.25, tempo * 0.91);
        setTempo(0, 1, tempo);
        play(0, 0.00, 0, notes[0], 85, 0.20);
        play(0, 0.25, 0, notes[1], 80, 0.28);
        play(0, 0.50, 0, notes[2], 75, 0.28);
        play(0, 0.75, 0, notes[3], 75, 0.15);
      }
    };
    PhraseEditor pe8 = new PhraseEditor(se) {
      @Override
      public void playPhrase(int... notes) {
        setTempo(0, 0, tempo * 0.9);
        setTempo(0, 0.25, tempo);
        play(0, 0.00, 0, notes[0], 85, 0.20);
        play(0, 0.25, 1, notes[1], 70, 0.35);
        play(0, 0.50, 0, notes[2], 85, 0.20);
        play(0, 0.75, 1, notes[3], 70, 0.35);
      }
    };
    PhraseEditor pe9 = new PhraseEditor(se) {
      @Override
      public void playPhrase(int[] notes) {
        setTempo(0, 0, tempo * 0.8);
        setTempo(0, 0.25, tempo);
        setTempo(0, 0.5, tempo * 1.1);
        setTempo(0, 1, tempo * 0.96);
        play(0, 0.00, 0, notes[0], 100, 0.3);
        play(0, 0.25, 0, notes[1], 90, 0.3);
        play(0, 0.50, 0, notes[2], 100, 0.35);
        play(0, 0.75, 0, notes[3], 80, 0.24);
        play(0, 1.00, 0, notes[4], 100, 0.3);
        play(0, 1.25, 0, notes[5], 80, 0.28);
        play(0, 1.50, 0, notes[6], 90, 0.3);
        play(0, 1.75, 0, notes[7], 80, 0.28);
      }
    };
    PhraseEditor pe10 = new PhraseEditor(se) {
      @Override
      public void playPhrase(int... notes) {
        setTempo(0, 0, tempo * 0.5);
        play(0, 0.10, 0, notes[0], 85, 2);
        play(0, 0.25, 0, notes[1], 85, 2.25);
        play(0, 0.40, 0, notes[2], 85, 2.40);
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

    pe1.playPhrase(5, 0, 43, 52, 59, 57, 59, 55, 54, 55);
    pe2.playPhrase(5, 2, 52, 55, 54, 55, 47, 50, 49, 47);
    pe1.playPhrase(6, 0, 49, 55, 57, 55, 57, 55, 57, 55);
    pe1.playPhrase(6, 2, 49, 55, 57, 55, 57, 55, 57, 55);
    pe1.playPhrase(7, 0, 54, 57, 62, 61, 62, 57, 55, 57);
    pe2.playPhrase(7, 2, 54, 57, 55, 57, 50, 54, 52, 50);

    pe1.playPhrase(8, 0, 40, 47, 55, 54, 55, 47, 55, 47);
    pe1.playPhrase(8, 2, 40, 47, 55, 54, 55, 47, 55, 47);
    pe1.playPhrase(9, 0, 40, 49, 50, 52, 50, 49, 47, 45);
    pe2.playPhrase(9, 2, 55, 54, 52, 62, 61, 59, 57, 55);
    pe2.playPhrase(10, 0, 54, 52, 50, 62, 57, 62, 54, 57);
    pe2.playPhrase(10, 2, 50, 52, 54, 57, 55, 54, 52, 50);
    
    pe1.playPhrase(11, 0, 56, 50, 53, 52, 53, 50, 56, 50);
    pe1.playPhrase(11, 2, 59, 50, 53, 52, 53, 50, 56, 50);
    pe1.playPhrase(12, 0, 48, 52, 57, 59, 60, 57, 52, 50);
    pe1.playPhrase(12, 2, 48, 52, 57, 59, 60, 57, 54, 52);
    pe1.playPhrase(13, 0, 51, 54, 51, 54, 57, 54, 57, 54);
    pe1.playPhrase(13, 2, 51, 54, 51, 54, 57, 54, 57, 54);
    pe2.playPhrase(14, 0, 55, 54, 52, 55, 54, 55, 57, 54);
    pe2.playPhrase(14, 2, 55, 54, 52, 50, 48, 47, 45, 43);
    
    pe1.playPhrase(15, 0, 42, 48, 50, 48, 50, 48, 50, 48);
    pe1.playPhrase(15, 2, 42, 48, 50, 48, 50, 48, 50, 48);
    pe1.playPhrase(16, 0, 43, 47, 53, 52, 53, 47, 53, 47);
    pe1.playPhrase(16, 2, 43, 47, 53, 52, 53, 47, 53, 47);
    pe1.playPhrase(17, 0, 43, 48, 52, 50, 52, 48, 52, 48);
    pe1.playPhrase(17, 2, 43, 48, 52, 50, 52, 48, 52, 48);
    pe1.playPhrase(18, 0, 43, 54, 60, 59, 60, 54, 60, 54);
    pe1.playPhrase(18, 2, 43, 54, 60, 59, 60, 54, 60, 54);
    pe1.playPhrase(19, 0, 43, 50, 59, 57, 59, 55, 54, 52);
    pe2.playPhrase(19, 2, 50, 48, 47, 45, 43, 42, 40, 38);
    
    pe1.playPhrase(20, 0, 37, 45, 52, 54, 55, 52, 54, 55);
    pe1.playPhrase(20, 2, 37, 45, 52, 54, 55, 52, 54, 55);
    pe1.playPhrase(21, 0, 36, 45, 50, 52, 54, 50, 52, 54);
    pe1.playPhrase(21, 2, 36, 45, 50, 52, 54, 50, 52, 54);
    pe3.playPhrase(22, 0, 36, 45, 50, 54, 57, 61, 62, 45, 47, 48, 50, 52, 54, 55);
    
    pe2.playPhrase(23, 0, 57, 54, 50, 52, 54, 55, 57, 59);
    pe2.playPhrase(23, 2, 60, 57, 54, 55, 57, 59, 60, 62);
    pe4.playPhrase(24, 0, 63, 62, 61, 62);
    pe4.playPhrase(24, 1, 62, 60, 59, 60);
    pe2.playPhrase(24, 2, 60, 57, 54, 52, 50, 45, 47, 48);
    
    pe1.playPhrase(25, 0, 38, 45, 50, 54, 57, 59, 60, 57);
    pe1.playPhrase(25, 2, 59, 55, 50, 48, 47, 43, 45, 47);
    pe1.playPhrase(26, 0, 38, 43, 47, 50, 55, 57, 59, 55);
    pe4.playPhrase(26, 2, 61, 58, 57, 58);
    pe4.playPhrase(26, 3, 58, 57, 56, 57);
    
    pe1.playPhrase(27, 0, 57, 55, 54, 55, 55, 52, 49, 47);
    pe1.playPhrase(27, 2, 45, 49, 52, 55, 57, 61, 62, 61);
    pe1.playPhrase(28, 0, 62, 57, 54, 52, 54, 57, 50, 54);
    pe2.playPhrase(28, 2, 45, 50, 49, 47, 45, 43, 42, 40);

    pe5.playPhrase(29, 0, 38, 60, 59, 57, 55, 54, 52);
    pe6.playPhrase(29, 2, 50, 60, 59, 57, 55, 54, 52, 50);
    pe6.playPhrase(30, 0, 48, 59, 57, 55, 54, 52, 50, 48);
    pe6.playPhrase(30, 2, 47, 57, 55, 54, 52, 50, 48, 47);
    
    pe7.playPhrase(31, 0, 45, 55, 54, 52);
    pe8.playPhrase(31, 1, 54, 57, 50, 57);
    pe8.playPhrase(31, 2, 52, 57, 54, 57);
    pe8.playPhrase(31, 3, 55, 57, 52, 57);
    pe8.playPhrase(32, 0, 54, 57, 50, 57);
    pe8.playPhrase(32, 1, 55, 57, 52, 57);
    pe8.playPhrase(32, 2, 54, 57, 50, 57);
    pe8.playPhrase(32, 3, 55, 57, 52, 57);
    
    pe8.playPhrase(33, 0, 54, 57, 50, 57);
    pe8.playPhrase(33, 1, 52, 57, 54, 57);
    pe8.playPhrase(33, 2, 55, 57, 57, 57);
    pe8.playPhrase(33, 3, 59, 57, 50, 57);
    pe8.playPhrase(34, 0, 57, 57, 59, 57);
    pe8.playPhrase(34, 1, 60, 57, 50, 57);
    pe8.playPhrase(34, 2, 59, 57, 60, 57);
    pe8.playPhrase(34, 3, 62, 57, 59, 57);
    
    pe8.playPhrase(35, 0, 60, 57, 59, 57);
    pe8.playPhrase(35, 1, 60, 57, 57, 57);
    pe8.playPhrase(35, 2, 59, 57, 57, 57);
    pe8.playPhrase(35, 3, 59, 57, 55, 57);
    pe8.playPhrase(36, 0, 57, 57, 55, 57);
    pe8.playPhrase(36, 1, 57, 57, 54, 57);
    pe8.playPhrase(36, 2, 55, 57, 54, 57);
    pe8.playPhrase(36, 3, 55, 57, 52, 57);

    pe7.playPhrase(37, 0, 54, 57, 50, 52);
    pe8.playPhrase(37, 1, 53, 50, 54, 50);
    pe8.playPhrase(37, 2, 55, 50, 56, 50);
    pe8.playPhrase(37, 3, 57, 50, 58, 50);
    pe8.playPhrase(38, 0, 59, 50, 60, 50);
    pe8.playPhrase(38, 1, 61, 50, 62, 50);
    pe8.playPhrase(38, 2, 63, 50, 64, 50);
    pe8.playPhrase(38, 3, 65, 50, 66, 50);
    pe9.playPhrase(39, 0, 67, 59, 50, 59, 67, 59, 67, 59);
    pe9.playPhrase(39, 2, 67, 59, 50, 59, 67, 59, 67, 59);
    pe9.playPhrase(40, 0, 67, 57, 50, 57, 67, 57, 67, 57);
    pe9.playPhrase(40, 2, 67, 57, 50, 57, 67, 57, 67, 57);
    pe9.playPhrase(41, 0, 66, 57, 50, 57, 66, 57, 66, 57);
    pe9.playPhrase(41, 2, 66, 57, 50, 57, 66, 57, 66, 57);
    pe10.playPhrase(42, 0, 43, 59, 67);
    

    
    Synthesizer synthesizer = MidiSystem.getSynthesizer();
    synthesizer.open();
    //Receiver receiver = synthesizer.getReceiver();
    MidiDevice aria = MidiUtil.getMidiDevices("ARIA", true)[0];
    aria.open();
    Receiver receiver = aria.getReceiver();
    playMidi(se.getSequence(), receiver);
  }
}
