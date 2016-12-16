package mocha.sound;

public class Panner implements SoundReadable {

  SoundReadable readable;
  DoubleMap panMap;
  double[] buffer;
  int bufferIndex;

  public Panner(SoundReadable readable, DoubleMap panMap) {
    if (readable.getChannel() != 1) {
      throw new IllegalArgumentException("readable should be monoral");
    }
    this.readable = readable;
    this.panMap = panMap;
    buffer = new double[2];
    bufferIndex = 2;
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
    if (bufferIndex >= buffer.length) {
      double value = readable.read();
      double pan = panMap.next();
      buffer[0] = value * pan;
      buffer[1] = value * (1.0 - pan);
      bufferIndex = 0;
    }
    return buffer[bufferIndex++];
  }

}
