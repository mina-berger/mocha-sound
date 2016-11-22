package mocha.sound;

public class OscillatorReader implements SoundReadable {

  public static final int CHANNELS = 1;

  Oscillatable oscillatable;
  DoubleMap freqMap;
  double seconds;

  public OscillatorReader(Oscillatable oscillatable, DoubleMap freqMap, double seconds) {
    this.oscillatable = oscillatable;
    this.freqMap = freqMap;
    this.seconds = seconds;
  }

  @Override
  public long length() {
    return (long) (WavFileWriter.SAMPLE_RATE * CHANNELS * seconds);
  }

  @Override
  public int getChannel() {
    return CHANNELS;
  }

  @Override
  public double read() {
    return oscillatable.read(freqMap.next(), 1);
  }

}
