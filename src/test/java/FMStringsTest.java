
import java.io.File;
import java.io.IOException;
import mocha.sound.DoubleMap;
import mocha.sound.Instrumental;
import mocha.sound.Panner;
import mocha.sound.TimeLine;
import mocha.sound.WavFilePlayer;
import mocha.sound.WavFileWriter;
import mocha.sound.soundbank.FMStrings;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author minaberger
 */
public class FMStringsTest {

  public static void main(String[] args) throws IOException {
    TimeLine tl = new TimeLine();
    Instrumental inst = new FMStrings();

    /*for (int i = 0; i < 2; i++) {
      tl.addReadable(i * 3, new Panner(inst.play(72, 2.5, ((double) i + 1.0) / 2.0), new DoubleMap(0.5)));
    }*/
    int i = 0;
    double duration = 1.5;
    tl.addReadable(i++ * duration, new Panner(inst.play(78, duration, 0.6), new DoubleMap(1)));
    tl.addReadable(i++ * duration, new Panner(inst.play(76, duration, 0.6), new DoubleMap(1)));
    tl.addReadable(i++ * duration, new Panner(inst.play(74, duration, 0.6), new DoubleMap(1)));
    tl.addReadable(i++ * duration, new Panner(inst.play(73, duration, 0.6), new DoubleMap(1)));
    tl.addReadable(i++ * duration, new Panner(inst.play(71, duration, 0.6), new DoubleMap(1)));
    tl.addReadable(i++ * duration, new Panner(inst.play(69, duration, 0.6), new DoubleMap(1)));
    tl.addReadable(i++ * duration, new Panner(inst.play(71, duration, 0.6), new DoubleMap(1)));
    tl.addReadable(i++ * duration, new Panner(inst.play(73, duration, 0.6), new DoubleMap(1)));
    
    tl.addReadable(i++ * duration, new Panner(inst.play(74, duration, 0.6), new DoubleMap(1)));
    tl.addReadable(i++ * duration, new Panner(inst.play(73, duration, 0.6), new DoubleMap(1)));
    tl.addReadable(i++ * duration, new Panner(inst.play(71, duration, 0.6), new DoubleMap(1)));
    tl.addReadable(i++ * duration, new Panner(inst.play(69, duration, 0.6), new DoubleMap(1)));
    tl.addReadable(i++ * duration, new Panner(inst.play(67, duration, 0.6), new DoubleMap(1)));
    tl.addReadable(i++ * duration, new Panner(inst.play(66, duration, 0.6), new DoubleMap(1)));
    tl.addReadable(i++ * duration, new Panner(inst.play(67, duration, 0.6), new DoubleMap(1)));
    tl.addReadable(i++ * duration, new Panner(inst.play(64, duration, 0.6), new DoubleMap(1)));

    i = 8;
    tl.addReadable(i++ * duration, new Panner(inst.play(78, duration, 0.6), new DoubleMap(0)));
    tl.addReadable(i++ * duration, new Panner(inst.play(76, duration, 0.6), new DoubleMap(0)));
    tl.addReadable(i++ * duration, new Panner(inst.play(74, duration, 0.6), new DoubleMap(0)));
    tl.addReadable(i++ * duration, new Panner(inst.play(73, duration, 0.6), new DoubleMap(0)));
    tl.addReadable(i++ * duration, new Panner(inst.play(71, duration, 0.6), new DoubleMap(0)));
    tl.addReadable(i++ * duration, new Panner(inst.play(69, duration, 0.6), new DoubleMap(0)));
    tl.addReadable(i++ * duration, new Panner(inst.play(71, duration, 0.6), new DoubleMap(0)));
    tl.addReadable(i++ * duration, new Panner(inst.play(73, duration, 0.6), new DoubleMap(0)));
    
    WavFileWriter.create(tl, new File("wav/fmstrings.wav"));
    WavFilePlayer.playFile(new File("wav/fmstrings.wav"));
  }
}
