package mocha.sound;

import java.util.NavigableMap;
import java.util.TreeMap;

public class TimeSign extends TreeMap<Integer, Double> {

  public TimeSign(double initial) {
    put(0, initial);
  }

  public double getTotalBeat(int measure, double beat) {
    if (floorEntry(measure) == null) {
      throw new IllegalArgumentException("measure is too small:" + measure);
    }
    double floorSign = floorEntry(measure).getValue();
    if (beat > floorSign) {
      return getTotalBeat(measure + 1, beat - floorSign);
    }
    NavigableMap<Integer, Double> headMap = headMap(measure, true);

    int currentMeasure = 0;
    double currentSign = headMap.get(0);

    double totalBeat = 0;
    for (Integer nextMeasure : headMap.keySet()) {
      totalBeat += (double) (nextMeasure - currentMeasure) * currentSign;
      currentMeasure = nextMeasure;
      currentSign = headMap.get(nextMeasure);
    }
    totalBeat += (double) (measure - currentMeasure) * currentSign;
    return totalBeat + beat;
  }

}
