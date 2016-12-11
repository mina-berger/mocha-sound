package mocha.sound;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Maximizer implements SoundReadable {

  SoundReadable readable;

  File tempFile;
  DataInputStream in;
  double ratio;

  public Maximizer(SoundReadable readable, double maxVolume) throws IOException {
    this.readable = readable;
    tempFile = File.createTempFile("maximizer", "");
    System.out.println(tempFile.getAbsolutePath());
    DataOutputStream out = new DataOutputStream(new FileOutputStream(tempFile));

    double max = 0;
    long start = System.currentTimeMillis();
    int channel = readable.getChannel();
    for (int i = 0; i < readable.length(); i++) {
      double value = readable.read();
      max = Math.max(max, Math.abs(value));
      out.writeDouble(value);
      if(i % (SAMPLE_RATE * channel) == 0){
        long elapsed = (System.currentTimeMillis() - start) / 1000;
        System.out.println("read " + (i / SAMPLE_RATE / channel) + " sec. (" + elapsed + " sec. elapsed)");
      }
    }
    out.flush();
    out.close();

    ratio = max == 0 ? 0 : maxVolume / max;
    in = new DataInputStream(new FileInputStream(tempFile));
  }

  @Override
  public long length() {
    return readable.length();
  }

  @Override
  public int getChannel() {
    return readable.getChannel();
  }

  @Override
  public double read() {
    try {
      return in.readDouble() * ratio;
    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }
  }

  public void terminate() throws IOException {
    in.close();
    tempFile.delete();
  }

}
