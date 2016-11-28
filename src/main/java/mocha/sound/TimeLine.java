package mocha.sound;

import java.util.ArrayList;
import java.util.TreeMap;

public class TimeLine extends TreeMap<Long, ArrayList<SoundReadable>> implements SoundReadable {

  long index;
  ArrayList<ReadableCounter> reading;
  ArrayList<Double> buffer;

  public TimeLine() {
    index = 0;
    reading = new ArrayList<>();
    buffer = new ArrayList<>();
  }

  public void addReadable(double second, SoundReadable readable) {
    long key = (long) (second * SAMPLE_RATE);
    if (!containsKey(key)) {
      put(key, new ArrayList<>());
    }
    int channel = readable.getChannel();
    switch (channel) {
      case 1:
        get(key).add(new Panner(readable, new DoubleMap(0.5)));
        break;
      case 2:
        get(key).add(readable);
        break;
      default:
        throw new IllegalArgumentException("unexpected channel=" + readable.getChannel());
    }
  }

  @Override
  public long length() {
    long length = 0;
    for (long key : keySet()) {
      for (SoundReadable readable : get(key)) {
        length = Math.max(length, key * 2 + readable.length());
      }
    }
    return length;
  }

  @Override
  public int getChannel() {
    return 2;
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
      double valueL = 0;
      double valueR = 0;
      for (ReadableCounter counter : reading) {
        valueL += counter.readable.read();
        valueR += counter.readable.read();
        counter.rest -= 2;
      }
      buffer.add(valueL);
      buffer.add(valueR);
    }
    return buffer.remove(0);
  }

  class ReadableCounter {

    long rest;
    SoundReadable readable;

    ReadableCounter(SoundReadable readable) {
      this.readable = readable;
      rest = readable.length();
      System.out.println(rest);
    }
  }

}
