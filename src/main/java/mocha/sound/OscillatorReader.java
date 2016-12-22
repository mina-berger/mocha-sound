package mocha.sound;

public class OscillatorReader implements SoundReadable {

  public static final int CHANNELS = 1;

  Oscillatable oscillatable;
  DoubleMap freqMap;
  DoubleMap envMap;
  DoubleMap fbMap;
  double seconds;
  SoundReadable[] freqModulators;

  public OscillatorReader(Oscillatable oscillatable, DoubleMap freqMap, double seconds) {
    this(oscillatable, freqMap, new DoubleMap(1), new DoubleMap(0), seconds);
  }

  public OscillatorReader(Oscillatable oscillatable, DoubleMap freqMap, DoubleMap envMap, double seconds) {
    this(oscillatable, freqMap, envMap, new DoubleMap(0), seconds);
  }

public OscillatorReader(
  Oscillatable oscillatable, DoubleMap freqMap, DoubleMap envMap, DoubleMap fbMap, double seconds,
  SoundReadable... freqModulators) {
  this.oscillatable = oscillatable;
  this.freqMap = freqMap;
  this.envMap = envMap;
  this.fbMap = fbMap;
  this.seconds = seconds;
  this.freqModulators = freqModulators;
}

  @Override
  public long length() {
    return (long) (SAMPLE_RATE * CHANNELS * seconds);
  }

  @Override
  public int getChannel() {
    return CHANNELS;
  }

@Override
public double read() {
  double omega_t = freqMap.next();
  for (SoundReadable freqModulator : freqModulators) {
    omega_t += freqModulator.read();
  }
  return oscillatable.read(omega_t, fbMap.next(), 1) * envMap.next();
}

}
