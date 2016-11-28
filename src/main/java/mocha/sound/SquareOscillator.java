package mocha.sound;

public class SquareOscillator implements Oscillatable {

  double t;
  double delta_t;

  public SquareOscillator() {
    this(SAMPLE_RATE);
  }

  public SquareOscillator(double sample_rate) {
    t = 1.0 / sample_rate;
    delta_t = 0;
  }

  @Override
  public double read(double freq, double volume) {
    double x = delta_t - Math.floor(delta_t);
    double point = x < 0.5 ? 1 : -1;
    delta_t += freq * t;
    return point * volume;
  }
}
