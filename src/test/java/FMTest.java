
import java.io.File;
import java.io.IOException;
import mocha.sound.DoubleMap;
import mocha.sound.OscillatorReader;
import mocha.sound.Played;
import mocha.sound.SineOscillator;
import mocha.sound.SoundReadable;
import mocha.sound.TimeLine;
import mocha.sound.WavFilePlayer;
import mocha.sound.WavFileWriter;
import mocha.sound.effect.LowPassFilterModule;
import mocha.sound.effect.IIRLowPassFilter;

public class FMTest {

  public static void main(String[] args) throws IOException {
    TimeLine tl = new TimeLine();
    Played pl = new Played();
    int i = 0;

    pl.addReadable(i++ * 3, getOscillatorReader(1, 0));
    pl.addReadable(i++ * 3, getOscillatorReader(1, 1000));
    pl.addReadable(i++ * 3, getOscillatorReader(1, 5000));
    pl.addReadable(i++ * 3, getOscillatorReader(1, 10000));
    pl.addReadable(i++ * 3, getOscillatorReader(2, 1000));
    pl.addReadable(i++ * 3, getOscillatorReader(2, 5000));
    pl.addReadable(i++ * 3, getOscillatorReader(4, 1000));
    pl.addReadable(i++ * 3, getOscillatorReader(4, 5000));
    pl.addReadable(i++ * 3, getOscillatorReader(8, 1000));
    pl.addReadable(i++ * 3, getOscillatorReader(8, 5000));
    pl.addReadable(i++ * 3, getOscillatorReader(13, 1000));
    pl.addReadable(i++ * 3, getOscillatorReader(13, 5000));
    pl.addReadable(i++ * 3, getOscillatorReader(2.3678, 1000));
    pl.addReadable(i++ * 3, getOscillatorReader(2.3678, 5000));
    
    tl.addReadable(0, pl);

    WavFileWriter.create(tl, new File("wav/fm.wav"));
    WavFilePlayer.playFile(new File("wav/fm.wav"));

  }

  public static OscillatorReader getOscillatorReader(double ratio, double amp) {
    DoubleMap freqMod = new DoubleMap(500 * ratio);
    DoubleMap envMod = new DoubleMap(amp);

    OscillatorReader modulator = new OscillatorReader(new SineOscillator(), freqMod, envMod, 3);

    DoubleMap freq = new DoubleMap(500);
    DoubleMap env = new DoubleMap(1);
    env.putSecondValue(3, 0);
    return new OscillatorReader(new SineOscillator(), freq, env, new DoubleMap(0), 3, new SoundReadable[]{modulator});

  }
}
