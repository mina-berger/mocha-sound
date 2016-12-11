package mocha.sound;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

public class WavFileWriter extends InputStream implements SoundConstants{

  boolean signed = true;
  boolean big_endian = true;
  int sample_size_byte = 2;

  ArrayList<Byte> list;

  Maximizer maximizer;
  long index_start;
  long index_end;
  long index;
  long start;

  public WavFileWriter(SoundReadable readable) throws IOException {
    start = System.currentTimeMillis();
    list = new ArrayList<>();
    double volume = Math.pow(2, sample_size_byte * 8 - 1) - 1;
    maximizer = new Maximizer(readable, volume);
    index_start = (long)SAMPLE_RATE * maximizer.getChannel();
    index_end   = index_start + maximizer.length();
    index = 0;
  }

  @Override
  public int read() throws IOException {
    if (list.isEmpty()) {
      double value;
      if(index >= index_start && index < index_end){
        value = maximizer.read();
      }else{
        value = 0;
      }
      index++;
      if(index % (SAMPLE_RATE * 2) == 0){
        System.out.println("wrote " + (index / SAMPLE_RATE / 2) + " sec");
      }
      ByteBuffer buffer = ByteBuffer.allocate(8);
      buffer.putLong((long) value);
      byte[] array = buffer.array();
      for (int i = 8 - sample_size_byte; i < 8; i++) {
        list.add(array[i]);
      }
    }
    int ret = Byte.toUnsignedInt(list.remove(0));
    return ret;
  }

  public long length() {
    return maximizer.length() / maximizer.getChannel() + (long)SAMPLE_RATE * 2;
  }

  public AudioFormat getFormat() {
    return new AudioFormat(SAMPLE_RATE, sample_size_byte * 8, maximizer.getChannel(), signed, big_endian);
  }

  public void terminate() throws IOException {
    maximizer.terminate();
    System.out.println("total time:" + ((System.currentTimeMillis() - start) / 1000));
  }
  public static void create(SoundReadable readable, File wavFile) throws IOException {
    wavFile.getParentFile().mkdirs();

    WavFileWriter ss = new WavFileWriter(readable);

    AudioSystem.write(
      new AudioInputStream(ss, ss.getFormat(), ss.length()),
      AudioFileFormat.Type.WAVE, wavFile);
    ss.terminate();

    System.out.println("wav file  :" + wavFile.getAbsolutePath());
  }



}
