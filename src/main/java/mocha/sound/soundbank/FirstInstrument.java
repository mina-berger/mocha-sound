package mocha.sound.soundbank;

import mocha.sound.DoubleMap;
import mocha.sound.Instrumental;
import mocha.sound.OscillatorReader;
import mocha.sound.Played;
import mocha.sound.SineOscillator;
import mocha.sound.Temperament;
import mocha.sound.TriangleOscillator;

public class FirstInstrument implements Instrumental {

  @Override
  public Played play(double note, double duration, double velocity) {
    Played played = new Played();

    DoubleMap freq1 = new DoubleMap(Temperament.getFreq(note));
    DoubleMap env1 = new DoubleMap(0);
    env1.putSecondValue(0.01, velocity);
    env1.putSecondValue(0.15, 0.0);
    played.addReadable(0, new OscillatorReader(new TriangleOscillator(), freq1, env1, 0.15));

    DoubleMap freq2 = new DoubleMap(Temperament.getFreq(note));
    DoubleMap env2 = new DoubleMap(0);

    double top = 0.3 + velocity / 2.0;
    env2.putSecondValue(0.05, top);

    double real_duration;
    if (duration > 3) {
      env2.putSecondValue(3, 0);
      real_duration = 3;
    } else {
      double end_volume = (1.0 - duration / 3.0) * top;
      env2.putSecondValue(duration, end_volume);
      env2.putSecondValue(duration + 0.1, 0);
      real_duration = duration + 0.1;
    }
    played.addReadable(0, new OscillatorReader(new SineOscillator(), freq2, env2, real_duration));

    return played;
  }

}
