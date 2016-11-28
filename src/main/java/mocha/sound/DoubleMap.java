package mocha.sound;

import java.util.Map.Entry;
import java.util.TreeMap;

public class DoubleMap extends TreeMap<Long, Double> implements SoundConstants{

  double sampleRate;
  long index;

  public DoubleMap(double initialValue) {
    this(SAMPLE_RATE, initialValue);
  }

  public DoubleMap(double sampleRate, double initialValue) {
    this.sampleRate = sampleRate;
    put(0l, initialValue);
    index = 0;
  }

  public void putSecondValue(double second, double value) {
    put((long) (second * sampleRate), value);
  }

  public double next() {
    return getValue(index++);
  }

  private double getValue(long index) {
    if (containsKey(index)) {
      return get(index);
    }
    Entry<Long, Double> a = floorEntry(index);
    Entry<Long, Double> b = ceilingEntry(index);
    if (a == null && b == null) {
      throw new IllegalStateException("map should contain at least one entry");
    }
    if (a == null) {
      return b.getValue();
    } else if (b == null) {
      return a.getValue();
    }
    double xa = a.getKey();
    double xb = b.getKey();
    double ya = a.getValue();
    double yb = b.getValue();

    return ya + (yb - ya) * (index - xa) / (xb - xa);

  }
}
