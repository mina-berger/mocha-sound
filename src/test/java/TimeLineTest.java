
import java.io.File;
import java.io.IOException;
import mocha.sound.DoubleMap;
import mocha.sound.OscillatorReader;
import mocha.sound.Panner;
import mocha.sound.SineOscillator;
import mocha.sound.TimeLine;
import mocha.sound.WavFileWriter;

public class TimeLineTest {
  public static void main(String[] arg) throws IOException {
TimeLine tl = new TimeLine();
DoubleMap pan1 = new DoubleMap(0);
pan1.putSecondValue(1, 0);
pan1.putSecondValue(2, 1);
DoubleMap pan2 = new DoubleMap(0.5);
DoubleMap pan3 = new DoubleMap(1);
pan3.putSecondValue(1, 0);
tl.addReadable(0,   new Panner(new OscillatorReader(new SineOscillator(), new DoubleMap(261.625), 3), pan1));
tl.addReadable(0.5, new Panner(new OscillatorReader(new SineOscillator(), new DoubleMap(329.627), 2.5), pan2));
tl.addReadable(1,   new Panner(new OscillatorReader(new SineOscillator(), new DoubleMap(391.995), 2), pan3));

WavFileWriter.create(tl, new File("wav/timeline.wav"));
  }  
  
}
