/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mocha.sound;

import java.io.File;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

/**
 *
 * @author minaberger
 */
public class WavFilePlayer {
  public static void playFile(File audio_file){
    try{
      AudioInputStream audio_input_stream = AudioSystem.getAudioInputStream(audio_file);
      AudioFormat audio_format = audio_input_stream.getFormat();
      DataLine.Info info = new DataLine.Info(SourceDataLine.class, audio_format, AudioSystem.NOT_SPECIFIED);
      SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
      line.open(audio_format, AudioSystem.NOT_SPECIFIED);
      line.start();
      int buffer_size = 128000;
      int bytes_read = 0;
      byte[] ab_data = new byte[buffer_size];
      while (bytes_read != -1) {
        bytes_read = audio_input_stream.read(ab_data, 0, ab_data.length);
        if (bytes_read >= 0) {
          line.write(ab_data, 0, bytes_read);
        }
      }
      line.drain();
      line.close();
    }catch(Exception e){
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }  
}
