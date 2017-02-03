
import mocha.sound.midi.MidiTimeSign;

public class MidiTimeSignTest {

  public static void main(String[] args) {
    MidiTimeSign ts = new MidiTimeSign(480, 4);
    ts.put(4, 3.0);
    for (int i = 0; i <= 5; i++) {
      System.out.println("measure=" + i
        + ", total beat=" + ts.getTotalBeat(i, 0)
        + ", tick=" + ts.getTick(i, 0));
    }
  }

}
