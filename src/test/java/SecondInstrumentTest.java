
import java.io.File;
import java.io.IOException;
import mocha.sound.DoubleMap;
import mocha.sound.Instrumental;
import mocha.sound.Panner;
import mocha.sound.TimeLine;
import mocha.sound.WavFilePlayer;
import mocha.sound.WavFileWriter;
import mocha.sound.soundbank.SecondInstrument;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author minaberger
 */
public class SecondInstrumentTest {
  public static void main(String[] args) throws IOException{
TimeLine tl = new TimeLine();
Instrumental inst = new SecondInstrument();

for(int i = 0;i < 10;i++){
  tl.addReadable(i, new Panner(inst.play(60, 0.5, ((double)i + 1.0) / 10.0), new DoubleMap(0.5)));
}
WavFileWriter.create(tl, new File("wav/secondinstrument.wav"));
WavFilePlayer.playFile(new File("wav/secondinstrument.wav"));
  }
}
