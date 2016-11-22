package mocha.sound;

public class SawOscillator implements Oscillatable {

  double t;
  double delta_t;

  public SawOscillator() {
    this(WavFileWriter.SAMPLE_RATE);
  }

  public SawOscillator(double sample_rate) {
    t = 1.0 / sample_rate;
    delta_t = 0;
  }

  @Override
  public double read(double freq, double volume) {
    double x = delta_t - Math.floor(delta_t);
    double point = (0.5 - x) * 2.0;
    delta_t += freq * t;
    return point * volume;
  }
}
