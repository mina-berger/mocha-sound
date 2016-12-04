
import java.io.File;
import java.io.IOException;
import mocha.sound.DoubleMap;
import mocha.sound.OscillatorReader;
import mocha.sound.Panner;
import mocha.sound.SineOscillator;
import mocha.sound.TimeLine;
import mocha.sound.WavFileWriter;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author minaberger
 */
public class BeatTest {
  public static void main(String[] args) throws IOException{
TimeLine tl = new TimeLine();
DoubleMap pan1 = new DoubleMap(0);
DoubleMap pan2 = new DoubleMap(1);

double freqC = 500;
DoubleMap freq1 = new DoubleMap(freqC);
DoubleMap freq2 = new DoubleMap(freqC);
freq2.putSecondValue(1.999, freqC);
freq2.putSecondValue(2, freqC + 1);
freq2.putSecondValue(3.999, freqC + 1);
freq2.putSecondValue(4, freqC + 5);
tl.addReadable(0, new Panner(new OscillatorReader(new SineOscillator(), freq1, 6), pan1));
tl.addReadable(0, new Panner(new OscillatorReader(new SineOscillator(), freq2, 6), pan2));

WavFileWriter.create(tl, new File("wav/beat.wav"));
    
  }
  
}
