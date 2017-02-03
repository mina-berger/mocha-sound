
import java.io.File;
import javax.sound.midi.MidiSystem;
import mocha.sound.midi.SequencePrinter;

/*
 *  mocha-java.com  * 
 */

/**
 *
 * @author minaberger
 */
public class PrintSequenceTest {
  public static void main(String[] args) throws Exception {
    SequencePrinter.printSequence(MidiSystem.getSequence(new File("midi/test.mid")), System.out);
  }
  
}
