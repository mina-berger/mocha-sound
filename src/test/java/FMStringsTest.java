
import java.io.File;
import java.io.IOException;
import mocha.sound.DoubleMap;
import mocha.sound.Instrumental;
import mocha.sound.Panner;
import mocha.sound.Played;
import mocha.sound.TimeLine;
import mocha.sound.WavFilePlayer;
import mocha.sound.WavFileWriter;
import mocha.sound.effect.IIRLowPassFilter;
import mocha.sound.effect.LowPassFilterModule;
import mocha.sound.soundbank.FMStrings;

public class FMStringsTest {

  public static void main(String[] args) throws IOException {
    TimeLine tl = new TimeLine();
    Instrumental inst = new FMStrings();

    Played pl1 = new Played();
    Played pl2 = new Played();
    int i = 0;
    double duration = 1.5;
    pl1.addReadable(i++ * duration, inst.play(78, duration, 0.6));
    pl1.addReadable(i++ * duration, inst.play(76, duration, 0.6));
    pl1.addReadable(i++ * duration, inst.play(74, duration, 0.6));
    pl1.addReadable(i++ * duration, inst.play(73, duration, 0.6));
    pl1.addReadable(i++ * duration, inst.play(71, duration, 0.6));
    pl1.addReadable(i++ * duration, inst.play(69, duration, 0.6));
    pl1.addReadable(i++ * duration, inst.play(71, duration, 0.6));
    pl1.addReadable(i++ * duration, inst.play(73, duration, 0.6));
    
    pl1.addReadable(i++ * duration, inst.play(74, duration, 0.6));
    pl1.addReadable(i++ * duration, inst.play(73, duration, 0.6));
    pl1.addReadable(i++ * duration, inst.play(71, duration, 0.6));
    pl1.addReadable(i++ * duration, inst.play(69, duration, 0.6));
    pl1.addReadable(i++ * duration, inst.play(67, duration, 0.6));
    pl1.addReadable(i++ * duration, inst.play(66, duration, 0.6));
    pl1.addReadable(i++ * duration, inst.play(67, duration, 0.6));
    pl1.addReadable(i++ * duration, inst.play(64, duration, 0.6));

    i = 8;
    pl2.addReadable(i++ * duration, inst.play(78, duration, 0.6));
    pl2.addReadable(i++ * duration, inst.play(76, duration, 0.6));
    pl2.addReadable(i++ * duration, inst.play(74, duration, 0.6));
    pl2.addReadable(i++ * duration, inst.play(73, duration, 0.6));
    pl2.addReadable(i++ * duration, inst.play(71, duration, 0.6));
    pl2.addReadable(i++ * duration, inst.play(69, duration, 0.6));
    pl2.addReadable(i++ * duration, inst.play(71, duration, 0.6));
    pl2.addReadable(i++ * duration, inst.play(73, duration, 0.6));
    
    
    //tl.addReadable(0, new Panner(pl1, new DoubleMap(1)));
    //tl.addReadable(0, new Panner(pl2, new DoubleMap(0)));
    
    DoubleMap cutoff1 = new DoubleMap(2000);
    DoubleMap cutoff2 = new DoubleMap(2000);
    tl.addReadable(0, new Panner(new LowPassFilterModule(pl1, cutoff1, new DoubleMap(IIRLowPassFilter.DEFAULT_QUALITY)), new DoubleMap(1)));
    tl.addReadable(0, new Panner(new LowPassFilterModule(pl2, cutoff2, new DoubleMap(IIRLowPassFilter.DEFAULT_QUALITY)), new DoubleMap(0)));
  
    WavFileWriter.create(tl, new File("wav/fmstrings3.wav"));
    WavFilePlayer.playFile(new File("wav/fmstrings3.wav"));
  }
}
