
package mocha.sound;


public class EnvelopeGenerator {
  DoubleMap templateEnvelope;
  double release;
  double finalValue;
  double sensitivity;
  double amp;
  
  public EnvelopeGenerator(DoubleMap templateEnvelope, double release, double finalValue, double sensitivity, double amp){
    this.templateEnvelope = templateEnvelope;
    this.release = release;
    this.finalValue = finalValue;
    this.sensitivity = sensitivity;
    this.amp = amp;
  }
public DoubleMap getEnvelope(double duration, double velocity){
  DoubleMap envelope = templateEnvelope.createSubMap(duration);
  envelope.putSecondValue(duration + release, finalValue);
  envelope.multiply((1 - (1 - velocity) * sensitivity) * amp);
  return envelope;
}
public double getRelease(){
  return release;
}
  
  
}
