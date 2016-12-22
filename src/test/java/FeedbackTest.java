
import java.io.File;
import java.io.IOException;
import mocha.sound.DoubleMap;
import mocha.sound.Oscillatable;
import mocha.sound.OscillatorReader;
import mocha.sound.SawOscillator;
import mocha.sound.SineOscillator;
import mocha.sound.SquareOscillator;
import mocha.sound.TimeLine;
import mocha.sound.TriangleOscillator;
import mocha.sound.WavFileWriter;

public class FeedbackTest {

  public static void main(String[] args) throws IOException {
TimeLine tl = new TimeLine();
int i = 0;

tl.addReadable(i++ * 3, getOscillatorReader(new SineOscillator(), 0));
tl.addReadable(i++ * 3, getOscillatorReader(new SineOscillator(), 1.1));
tl.addReadable(i++ * 3, getOscillatorReader(new SineOscillator(), 2));
tl.addReadable(i++ * 3, getOscillatorReader(new SineOscillator(), 2.2));
tl.addReadable(i++ * 3, getOscillatorReader(new SineOscillator(), 5));

tl.addReadable(i++ * 3, getOscillatorReader(new TriangleOscillator(), 0));
tl.addReadable(i++ * 3, getOscillatorReader(new TriangleOscillator(), 1.1));
tl.addReadable(i++ * 3, getOscillatorReader(new TriangleOscillator(), 2));
tl.addReadable(i++ * 3, getOscillatorReader(new TriangleOscillator(), 2.2));
tl.addReadable(i++ * 3, getOscillatorReader(new TriangleOscillator(), 5));

tl.addReadable(i++ * 3, getOscillatorReader(new SawOscillator(), 0));
tl.addReadable(i++ * 3, getOscillatorReader(new SawOscillator(), 1.1));
tl.addReadable(i++ * 3, getOscillatorReader(new SawOscillator(), 2));
tl.addReadable(i++ * 3, getOscillatorReader(new SawOscillator(), 2.2));
tl.addReadable(i++ * 3, getOscillatorReader(new SawOscillator(), 5));

tl.addReadable(i++ * 3, getOscillatorReader(new SquareOscillator(), 0));
tl.addReadable(i++ * 3, getOscillatorReader(new SquareOscillator(), 1.1));
tl.addReadable(i++ * 3, getOscillatorReader(new SquareOscillator(), 2));
tl.addReadable(i++ * 3, getOscillatorReader(new SquareOscillator(), 2.2));
tl.addReadable(i++ * 3, getOscillatorReader(new SquareOscillator(), 5));

WavFileWriter.create(tl, new File("wav/fb.wav"));
    //WavFilePlayer.playFile(new File("wav/fm.wav"));

  }

public static OscillatorReader getOscillatorReader(Oscillatable oscillatable, double fbAmp) {
  DoubleMap freq = new DoubleMap(500);
  DoubleMap env = new DoubleMap(1);
  DoubleMap fb = new DoubleMap(fbAmp);

  env.putSecondValue(3, 0);
  return new OscillatorReader(oscillatable, freq, env, fb, 3.0);
}
}
