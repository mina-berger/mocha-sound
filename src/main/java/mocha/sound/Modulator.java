package mocha.sound;

public class Modulator {

  Oscillatable oscillatable;
  DoubleMap freqMap;
  DoubleMap envMap;
  double volume;

  public Modulator(Oscillatable oscillatable, DoubleMap freqMap, DoubleMap envMap, double volume) {
    this.oscillatable = oscillatable;
    this.freqMap = freqMap;
    this.envMap = envMap;
    this.volume = volume;
  }

  public double modulate(double value) {
    double mod = oscillatable.read(freqMap.next(), 0, envMap.next()) * volume;
    return value + mod;
  }

}
