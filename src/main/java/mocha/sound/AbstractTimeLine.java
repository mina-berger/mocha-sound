package mocha.sound;

import java.util.ArrayList;
import java.util.TreeMap;

public abstract class AbstractTimeLine extends TreeMap<Long, ArrayList<SoundReadable>> implements SoundReadable {

  protected long index;
  protected ArrayList<ReadableCounter> reading;
  protected int channel;
  ArrayList<Double> buffer;

  public AbstractTimeLine(int channel) {
    this.channel = channel;
    index = 0;
    reading = new ArrayList<>();
    buffer = new ArrayList<>();
  }

  protected abstract SoundReadable formatReadable(SoundReadable readable);

  public void addReadable(double second, SoundReadable readable) {
    long key = (long) (second * SAMPLE_RATE);
    if (!containsKey(key)) {
      put(key, new ArrayList<>());
    }
    SoundReadable formatted = formatReadable(readable);
    if (formatted == null || formatted.getChannel() != channel) {
      throw new IllegalArgumentException("unexpected channel=" + readable.getChannel());
    }
    get(key).add(formatted);
  }

  @Override
  public long length() {
    long length = 0;
    for (long key : keySet()) {
      for (SoundReadable readable : get(key)) {
        length = Math.max(length, key * channel + readable.length());
      }
    }
    return length;
  }

  @Override
  public int getChannel() {
    return channel;
  }

  @Override
  public double read() {
    if (buffer.isEmpty()) {
      if (containsKey(index)) {
        for (SoundReadable readable : get(index)) {
          reading.add(new ReadableCounter(readable));
        }
      }
      index++;
      for (int i = reading.size() - 1; i >= 0; i--) {
        if (reading.get(i).rest <= 0) {
          reading.remove(i);
        }
      }
      double[] values = new double[channel];
      for (ReadableCounter counter : reading) {
        for (int i = 0; i < channel; i++) {
          values[i] += counter.readable.read();
        }
        counter.rest -= channel;
      }
      for (int i = 0; i < channel; i++) {
        buffer.add(values[i]);
      }
    }
    return buffer.remove(0);
  }

  class ReadableCounter {

    long rest;
    SoundReadable readable;

    ReadableCounter(SoundReadable readable) {
      this.readable = readable;
      rest = readable.length();
    }
  }

}
