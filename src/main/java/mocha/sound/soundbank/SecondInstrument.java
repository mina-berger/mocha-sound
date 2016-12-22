package mocha.sound.soundbank;

import mocha.sound.DoubleMap;
import mocha.sound.EnvelopeGenerator;
import mocha.sound.Instrumental;
import mocha.sound.OscillatorReader;
import mocha.sound.Played;
import mocha.sound.SineOscillator;
import mocha.sound.SoundReadable;
import mocha.sound.Temperament;

public class SecondInstrument implements Instrumental {
  EnvelopeGenerator eg1, eg2, eg3, eg4;
  public SecondInstrument(){
    DoubleMap env1 = new DoubleMap(0);
    env1.putSecondValue(0.01, 1);
    env1.putSecondValue(1.0, 0.75);
    env1.putSecondValue(2.0, 0.0);
    eg1 = new EnvelopeGenerator(env1, 0.1, 0.2, 1);
    DoubleMap env2 = new DoubleMap(0);
    env2.putSecondValue(0.01, 1);
    env2.putSecondValue(0.6, 0.75);
    env2.putSecondValue(1.5, 0.0);
    eg2 = new EnvelopeGenerator(env2, 0.1, 0.7, 800);
    DoubleMap env3 = new DoubleMap(0);
    env3.putSecondValue(0.01, 1);
    env3.putSecondValue(1.5, 0.95);
    env3.putSecondValue(3.0, 0.0);
    eg3 = new EnvelopeGenerator(env3, 0.1, 0.2, 1);
    DoubleMap env4 = new DoubleMap(0);
    env4.putSecondValue(0.01, 1);
    env4.putSecondValue(0.8, 0.95);
    env4.putSecondValue(1.8, 0.0);
    eg4 = new EnvelopeGenerator(env4, 0.1, 0.6, 1000);
    
  }

  @Override
  public Played play(double note, double duration, double velocity) {
    Played played = new Played();
    double freq = Temperament.getFreq(note);

    DoubleMap freq2 = new DoubleMap(freq * 14.14);
    DoubleMap env2 = eg2.getEnvelope(duration, velocity);
    OscillatorReader osc2 = new OscillatorReader(new SineOscillator(), freq2, env2, duration + eg2.getRelease());
    
    DoubleMap freq1 = new DoubleMap(freq);
    DoubleMap env1 = eg1.getEnvelope(duration, velocity);
    DoubleMap fb1 = new DoubleMap(0);
    OscillatorReader osc1 = new OscillatorReader(new SineOscillator(), freq1, env1, fb1, duration + eg1.getRelease(), new SoundReadable[]{osc2});
    played.addReadable(0, osc1);

    DoubleMap freq4 = new DoubleMap(freq);
    DoubleMap env4 = eg4.getEnvelope(duration, velocity);
    OscillatorReader osc4 = new OscillatorReader(new SineOscillator(), freq4, env4, duration + eg4.getRelease());
    
    DoubleMap freq3 = new DoubleMap(freq);
    DoubleMap env3 = eg3.getEnvelope(duration, velocity);
    DoubleMap fb3 = new DoubleMap(0);
    OscillatorReader osc3 = new OscillatorReader(new SineOscillator(), freq3, env3, fb3, duration + eg3.getRelease(), new SoundReadable[]{osc4});
    played.addReadable(0, osc3);

    DoubleMap freq6 = new DoubleMap(freq * 1.007);
    DoubleMap env6 = eg4.getEnvelope(duration, velocity);
    OscillatorReader osc6 = new OscillatorReader(new SineOscillator(), freq6, env6, duration + eg4.getRelease());
    
    DoubleMap freq5 = new DoubleMap(freq * 0.993);
    DoubleMap env5 = eg3.getEnvelope(duration, velocity);
    DoubleMap fb5 = new DoubleMap(0);
    OscillatorReader osc5 = new OscillatorReader(new SineOscillator(), freq5, env5, fb5, duration + eg3.getRelease(), new SoundReadable[]{osc6});
    played.addReadable(0, osc5);

    return played;
  }

  @Override
  public String getName() {
    return "FM Electric Piano";
  }
  

}
