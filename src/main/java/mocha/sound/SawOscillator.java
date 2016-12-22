package mocha.sound;

public class SawOscillator extends AbstractOscillator {

  @Override
  public double getAmplitude(double delta_t) {
    double x = delta_t - Math.floor(delta_t);
    return (0.5 - x) * 2.0;
  }
}
