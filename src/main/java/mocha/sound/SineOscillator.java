package mocha.sound;

public class SineOscillator extends AbstractOscillator {

  @Override
  public double getAmplitude(double delta_t) {
    return Math.sin(delta_t * 2.0 * Math.PI);
  }
}
