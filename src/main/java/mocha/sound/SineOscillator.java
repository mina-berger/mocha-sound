package mocha.sound;

public class SineOscillator implements Oscillatable {

  double omega_t;
  double delta_t;

  public SineOscillator() {
    this(WavFileWriter.SAMPLE_RATE);
  }

  public SineOscillator(double sample_rate) {
    omega_t = 2.0 * Math.PI / sample_rate;
    delta_t = 0;
  }

  @Override
  public double read(double freq, double volume) {
    double point = Math.sin(delta_t);
    delta_t += freq * omega_t;
    return point * volume;
  }
}
