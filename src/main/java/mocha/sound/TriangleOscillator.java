package mocha.sound;

public class TriangleOscillator extends AbstractOscillator {

  @Override
  public double getAmplitude(double delta_t) {
    double x = delta_t - Math.floor(delta_t);
    return x < 0.25 ? x * 4.0 : x < 0.75 ? (0.5 - x) * 4.0 : (x - 1.0) * 4.0;
  }
}
