/*
 *  mocha-java.com
 */
package mocha.sound.midi;

import mocha.sound.TimeSign;

/**
 *
 * @author minaberger
 */
public class MidiTimeSign extends TimeSign {

  int resolution;

  public MidiTimeSign(int resolution, double initial) {
    super(initial);
    this.resolution = resolution;
  }

  public long getTick(int measure, double beat) {
    double totalBeat = getTotalBeat(measure, beat);
    return Math.round(totalBeat * resolution);
  }

}
