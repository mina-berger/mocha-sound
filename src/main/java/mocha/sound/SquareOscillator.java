package mocha.sound;

public class SquareOscillator extends AbstractOscillator {

  @Override
  public double getAmplitude(double delta_t) {
    double x = delta_t - Math.floor(delta_t);
    return x < 0.5 ? 1 : -1;
  }
}
