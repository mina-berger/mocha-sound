package mocha.sound;

public abstract class AbstractOscillator implements Oscillatable {

  double t;
  double delta_t;
  double fb_buffer;

  public AbstractOscillator() {
    this(SAMPLE_RATE);
  }

  public AbstractOscillator(double sample_rate) {
    t = 1.0 / sample_rate;
    delta_t = 0;
    fb_buffer = 0;
  }

  public abstract double getAmplitude(double delta_t);

  @Override
  public double read(double freq, double feedback, double volume) {
    double fb_value = fb_buffer * feedback;
    double amplitude;
    if (fb_value == 0) {
      amplitude = fb_buffer = getAmplitude(delta_t);
    } else {
      amplitude = getAmplitude(delta_t + fb_value);
      fb_buffer = getAmplitude(delta_t);
    }
    delta_t += freq * t;
    return amplitude * volume;

  }
}
