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

public class WavFileWriter extends InputStream {

  public static final float SAMPLE_RATE = 48000;

  boolean signed = true;
  boolean big_endian = true;
  int sample_size_byte = 2;

  ArrayList<Byte> list;

  Maximizer maximizer;

  public WavFileWriter(SoundReadable readable) throws IOException {
    list = new ArrayList<>();
    double volume = Math.pow(2, sample_size_byte * 8 - 1) - 1;
    maximizer = new Maximizer(readable, volume);
  }

  @Override
  public int read() throws IOException {
    if (list.isEmpty()) {
      double value = maximizer.read();
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
    return maximizer.length() / maximizer.getChannel();
  }

  public AudioFormat getFormat() {
    return new AudioFormat(SAMPLE_RATE, sample_size_byte * 8, maximizer.getChannel(), signed, big_endian);
  }

  public void terminate() throws IOException {
    maximizer.terminate();
  }
  public static void create(SoundReadable readable, File wavFile) throws IOException {
    wavFile.getParentFile().mkdirs();

    WavFileWriter ss = new WavFileWriter(readable);

    AudioSystem.write(
      new AudioInputStream(ss, ss.getFormat(), ss.length()),
      AudioFileFormat.Type.WAVE, wavFile);
    ss.terminate();

    System.out.println("wav file:" + wavFile.getAbsolutePath());
  }

  public static void main(String[] arg) throws IOException {
    DoubleMap freqMap = new DoubleMap(440.0);
    freqMap.putSecondValue(2.0, 880.0);
    DoubleMap panMap = new DoubleMap(0.0);
    panMap.putSecondValue(3.0, 1.0);

    create(new Panner(
      new OscillatorReader(new SineOscillator(), freqMap, 3), panMap), new File("wav/panned.wav"));
  }

}
