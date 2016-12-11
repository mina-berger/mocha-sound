package mocha.sound;

import java.util.ArrayList;
import java.util.TreeMap;
import mocha.sound.TempoMap.Tempo;

public class TempoMap extends TreeMap<Double, Tempo> {

  ArrayList<Tempo> settings;

  public TempoMap(double initial) {
    settings = new ArrayList<>();
    settings.add(new TempoMap.Tempo(0, 0.0, initial));
  }

  public void put(int measure, double beat, double tempo) {
    settings.add(new TempoMap.Tempo(measure, beat, tempo));
  }

  void updateTime(TimeSign timeSign) {
    clear();
    for (Tempo tempo : settings) {
      put(timeSign.getTotalBeat(tempo.measure, tempo.beat), tempo);
    }

    Tempo currentTempo = get(0.0);
    double currentBeat = 0;
    double currentTime = 0;
    for (Double nextBeat : keySet()) {
      Tempo nextTempo = get(nextBeat);
      currentTime += (nextBeat - currentBeat) * 60.0 / ((nextTempo.tempo + currentTempo.tempo) / 2d);
      nextTempo.time = currentTime;
      currentTempo = nextTempo;
      currentBeat = nextBeat;
    }
  }

  public double getTime(double totalBeat) {
    if (totalBeat < 0) {
      throw new IllegalArgumentException("total beat is too small:totalBeat=" + totalBeat);
    }
    double floorKey = floorKey(totalBeat);
    Tempo floorTempo = get(floorKey);
    Double ceilingKey = ceilingKey(totalBeat);
    Tempo ceilingTempo;
    if (ceilingKey == null) {
      ceilingKey = totalBeat;
      ceilingTempo = floorTempo;
    } else {
      ceilingTempo = get(ceilingKey);
    }
    double tempo;
    if (ceilingKey == floorKey) {
      tempo = floorTempo.tempo;
    } else {
      tempo = floorTempo.tempo + (ceilingTempo.tempo - floorTempo.tempo) * (totalBeat - floorKey) / (ceilingKey - floorKey);
    }
    return floorTempo.time + (totalBeat - floorKey) * 60d / ((floorTempo.tempo + tempo) / 2d);

  }

  public static class Tempo {

    int measure;
    double beat;
    double tempo;
    Double time;

    public Tempo(int measure, double beat, double tempo) {
      this.measure = measure;
      this.beat = beat;
      this.tempo = tempo;
      time = null;
    }
  }

  public static void main(String[] arg) {
    TimeSign ts = new TimeSign(4);
    ts.put(4, 3.0);
    TempoMap map = new TempoMap(120);

    map.put(2, -0.00000000001, 120);
    map.put(2, 0, 60);
    map.updateTime(ts);
    for (int i = 0; i <= 5; i++) {
      System.out.println(i + ":" + map.getTime(ts.getTotalBeat(i, 0)));
    }
  }
}
