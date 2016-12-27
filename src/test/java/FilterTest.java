
import java.io.File;
import java.io.IOException;
import mocha.sound.DoubleMap;
import mocha.sound.Modulator;
import mocha.sound.Modulator.Method;
import mocha.sound.OscillatorReader;
import mocha.sound.SineOscillator;
import mocha.sound.SquareOscillator;
import mocha.sound.TimeLine;
import mocha.sound.WavFilePlayer;
import mocha.sound.WavFileWriter;
import mocha.sound.effect.IIRLowPassFilter;
import mocha.sound.effect.LowPassFilterModule;

public class FilterTest {

  public static void main(String[] args) throws IOException {
    TimeLine tl = new TimeLine();

    //tl.addReadable(0, getOscillatorReader());

    DoubleMap cutoff1 = new DoubleMap(4000);
    DoubleMap quality1 = new DoubleMap(IIRLowPassFilter.DEFAULT_QUALITY);
    LowPassFilterModule lpf1 = new LowPassFilterModule(getOscillatorReader(), cutoff1, quality1);
    Modulator mod1 = new Modulator(Method.ADD, new SineOscillator(), new DoubleMap(5), new DoubleMap(1), 2000);
    lpf1.setCutoffModulator(mod1);
    tl.addReadable(0, lpf1);

    DoubleMap cutoff2 = new DoubleMap(2000);
    DoubleMap quality2 = new DoubleMap(1.8);
    LowPassFilterModule lpf2 = new LowPassFilterModule(getOscillatorReader(), cutoff2, quality2);
    Modulator mod2 = new Modulator(Method.ADD, new SineOscillator(), new DoubleMap(5), new DoubleMap(1), 1.5);
    lpf2.setQualityModulator(mod2);
    tl.addReadable(5, lpf2);

    WavFileWriter.create(tl, new File("wav/filter2.wav"));
    WavFilePlayer.playFile(new File("wav/filter2.wav"));

  }

  public static OscillatorReader getOscillatorReader() {
    DoubleMap freq = new DoubleMap(440);
    DoubleMap env = new DoubleMap(1);
    env.putSecondValue(4.5, 1);
    env.putSecondValue(5, 0);
    return new OscillatorReader(new SquareOscillator(), freq, env, 5);
  }
}
