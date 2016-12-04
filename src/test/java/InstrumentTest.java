
import java.io.File;
import java.io.IOException;
import mocha.sound.DoubleMap;
import mocha.sound.Instrumental;
import mocha.sound.Panner;
import mocha.sound.TimeLine;
import mocha.sound.WavFileWriter;
import mocha.sound.soundbank.FirstInstrument;

public class InstrumentTest {

  public static void main(String[] arg) throws IOException {
    double[][] notes = new double[][]{
      new double[]{60, 64, 67, 72, 76},
      new double[]{60, 62, 69, 74, 77},
      new double[]{59, 62, 67, 74, 77},
      new double[]{60, 64, 67, 72, 76},
    };
    TimeLine tl = new TimeLine();
    Instrumental inst = new FirstInstrument();
    double time = 0;
    double duration = 0.25;
    for (int i = 0; i < notes.length * 2; i++) {
      int index = i / 2;
      tl.addReadable(time, new Panner(inst.play(notes[index][0], duration * 7.6, 0.7), new DoubleMap(0.75)));
      time += duration;
      tl.addReadable(time, new Panner(inst.play(notes[index][1], duration * 6.6, 0.5), new DoubleMap(0.75)));
      time += duration;
      tl.addReadable(time, new Panner(inst.play(notes[index][2], duration * 0.6, 0.6), new DoubleMap(0.25)));
      time += duration;
      tl.addReadable(time, new Panner(inst.play(notes[index][3], duration * 0.6, 0.8), new DoubleMap(0.25)));
      time += duration;
      tl.addReadable(time, new Panner(inst.play(notes[index][4], duration * 0.6, 1.0), new DoubleMap(0.25)));
      time += duration;
      tl.addReadable(time, new Panner(inst.play(notes[index][2], duration * 0.6, 0.6), new DoubleMap(0.25)));
      time += duration;
      tl.addReadable(time, new Panner(inst.play(notes[index][3], duration * 0.6, 0.8), new DoubleMap(0.25)));
      time += duration;
      tl.addReadable(time, new Panner(inst.play(notes[index][4], duration * 0.6, 1.0), new DoubleMap(0.25)));
      time += duration;
      System.out.print(".");
    }
    System.out.println(".");

    WavFileWriter.create(tl, new File("wav/clavier_sample.wav"));
  }

}
