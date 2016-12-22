
package mocha.sound.soundbank;

import mocha.sound.DoubleMap;
import mocha.sound.EnvelopeGenerator;
import mocha.sound.Instrumental;
import mocha.sound.OscillatorReader;
import mocha.sound.Played;
import mocha.sound.SineOscillator;
import mocha.sound.Temperament;


public class FMStrings implements Instrumental {
  EnvelopeGenerator eg1, eg2, eg3, eg4, eg5, eg6;

  public FMStrings() {
    DoubleMap env6 = new DoubleMap(0);
    env6.putSecondValue(0.1, 0.8);
    env6.putSecondValue(1.2, 0.98);
    env6.putSecondValue(2.5, 0.9);
    eg6 = new EnvelopeGenerator(env6, 1.0, 0.6, 1000);
    
    DoubleMap env5 = new DoubleMap(0);
    env5.putSecondValue(0.1, 0.8);
    env5.putSecondValue(1.2, 0.95);
    eg5 = new EnvelopeGenerator(env5, 1.0, 0.6, 1000);
    
    DoubleMap env4 = new DoubleMap(0);
    env4.putSecondValue(0.2, 0.8);
    env4.putSecondValue(1.7, 0.98);
    env4.putSecondValue(3.2, 0.99);
    eg4 = new EnvelopeGenerator(env4, 0.5, 0.6, 1000);
    
    //50	55	80	40	99	99	99	0
    DoubleMap env3 = new DoubleMap(0);
    env3.putSecondValue(0.15, 1.0);
    eg3 = new EnvelopeGenerator(env3, 0.5, 0.7, 0.5);
    
    DoubleMap env2 = new DoubleMap(0);
    env2.putSecondValue(0.1, 0.9);
    env2.putSecondValue(2.2, 0.98);
    env2.putSecondValue(4.5, 0.93);
    eg2 = new EnvelopeGenerator(env2, 5.0, 0.7, 800);

    DoubleMap env1 = new DoubleMap(0);
    env1.putSecondValue(0.15, 0.99);
    eg1 = new EnvelopeGenerator(env1, 0.5, 0.4, 1);


  }
  

  @Override
  public Played play(double note, double duration, double velocity) {
    Played played = new Played();
    double freq = Temperament.getFreq(note);
    
    DoubleMap freq6 = new DoubleMap(freq * 2.00);
    DoubleMap env6 = eg6.getEnvelope(duration, velocity);
    DoubleMap fb6 = new DoubleMap(0);
    OscillatorReader osc6 = new OscillatorReader(
      new SineOscillator(), freq6, env6, fb6, duration + eg6.getRelease());

    DoubleMap freq5 = new DoubleMap(freq * 1);
    DoubleMap env5 = eg5.getEnvelope(duration, velocity);
    DoubleMap fb5 = new DoubleMap(0);
    OscillatorReader osc5 = new OscillatorReader(
      new SineOscillator(), freq5, env5, fb5, duration + eg5.getRelease());
    
    DoubleMap freq4 = new DoubleMap(freq * 1.0);
    DoubleMap env4 = eg4.getEnvelope(duration, velocity);
    DoubleMap fb4 = new DoubleMap(0);
    OscillatorReader osc4 = new OscillatorReader(
      new SineOscillator(), freq4, env4, fb4, duration + eg4.getRelease()
      );
    
    DoubleMap freq3 = new DoubleMap(freq * 1);
    DoubleMap env3 = eg3.getEnvelope(duration, velocity);
    DoubleMap fb3 = new DoubleMap(0.2);
    OscillatorReader osc3 = new OscillatorReader(
      new SineOscillator(), freq3, env3, fb3, duration + eg3.getRelease(), 
      osc4, osc6, osc5);
    
    DoubleMap freq2 = new DoubleMap(freq * 1.001);
    DoubleMap env2 = eg2.getEnvelope(duration, velocity);
    DoubleMap fb2 = new DoubleMap(0.01);
    OscillatorReader osc2 = new OscillatorReader(new SineOscillator(), freq2, env2, fb2, duration + eg2.getRelease());
    
    DoubleMap freq1 = new DoubleMap(freq * 1.0);
    DoubleMap env1 = eg1.getEnvelope(duration, velocity);
    DoubleMap fb1 = new DoubleMap(0);
    OscillatorReader osc1 = new OscillatorReader(new SineOscillator(), freq1, env1, fb1, duration + eg1.getRelease(), osc2);
    
    played.addReadable(0, osc3);
    played.addReadable(0, osc1);
    return played;
  }

  @Override
  public String getName() {
    return "FM sound strings";
  }
  
}
