package mocha.sound;

public class Modulator {
  public enum Method {ADD, MULTIPLY};
  
  Method method;
  Oscillatable oscillatable;
  DoubleMap freqMap;
  DoubleMap envMap;
  double volume;

  public Modulator(Method method, Oscillatable oscillatable, DoubleMap freqMap, DoubleMap envMap, double volume) {
    this.method = method;
    this.oscillatable = oscillatable;
    this.freqMap = freqMap;
    this.envMap = envMap;
    this.volume = volume;
  }

  public double modulate(double value) {
    double mod = oscillatable.read(freqMap.next(), 0, envMap.next()) * volume;
    switch(method){
      case ADD      : return value + mod;
      case MULTIPLY : return value * mod;
      default       : return value;
    }
  }

}
