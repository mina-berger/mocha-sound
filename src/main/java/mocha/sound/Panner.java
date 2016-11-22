package mocha.sound;

import java.util.ArrayList;

public class Panner implements SoundReadable {

  SoundReadable readable;
  DoubleMap panMap;
  ArrayList<Double> buffer;

  public Panner(SoundReadable readable, DoubleMap panMap) {
    if (readable.getChannel() != 1) {
      throw new IllegalArgumentException("readable should be monoral");
    }
    this.readable = readable;
    this.panMap = panMap;
    buffer = new ArrayList<>();
  }

  @Override
  public long length() {
    return readable.length() * 2;
  }

  @Override
  public int getChannel() {
    return 2;
  }

  @Override
  public double read() {
    if (buffer.isEmpty()) {
      double value = readable.read();
      double pan = panMap.next();
      buffer.add(value * pan);
      buffer.add(value * (1.0 - pan));
    }
    return buffer.remove(0);
  }

}
