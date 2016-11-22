package mocha.sound;

public class TriangleOscillator implements Oscillatable {

  double t;
  double delta_t;

  public TriangleOscillator() {
    this(WavFileWriter.SAMPLE_RATE);
  }

  public TriangleOscillator(double sample_rate) {
    t = 1.0 / sample_rate;
    delta_t = 0;
  }

  @Override
  public double read(double freq, double volume) {
    double x = delta_t - Math.floor(delta_t);
    double point = x < 0.25 ? x * 4.0 : x < 0.75 ? (0.5 - x) * 4.0 : (x - 1.0) * 4.0;
    delta_t += freq * t;
    return point * volume;
  }
}
