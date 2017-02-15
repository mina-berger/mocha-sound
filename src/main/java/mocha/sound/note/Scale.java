/*
 *  mocha-java.com  * 
 */
package mocha.sound.note;

/**
 *
 * @author minaberger
 */
public class Scale {

  int[] intervals;

  public Scale(int... intervals) {
    this.intervals = intervals;
  }

  public int length() {
    return intervals.length;
  }

  public int note(int base, int octave, int index) {
    if (index >= 0) {
      octave += index / intervals.length;
      index %= intervals.length;
    } else {
      int offset = (index + 1) / intervals.length - 1;
      octave += offset;
      index += Math.abs(offset) * intervals.length;
      index %= intervals.length;
    }
    return base + octave * 12 + intervals[index];
  }
}
