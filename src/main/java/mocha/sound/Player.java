package mocha.sound;

public class Player {

  Played played;
  Measures measures;
  Instrumental inst;
  int measure;
  double beat;

  public Player(Measures measures, Instrumental inst) {
    this.measures = measures;
    this.inst = inst;
    played = new Played();
    measure = 0;
    beat = 0;
  }

  public void play(double note, double duration, double ratio, double velocity) {
    double time = measures.getTime(measure, beat);
    double realDuration = measures.getTime(measure, beat + duration * ratio) - time;
    played.addReadable(time, inst.play(note, realDuration, velocity));
    beat += duration;
  }

  public void nextMeasure() {
    measure++;
    beat = 0;
  }

  public void setBeat(double beat) {
    this.beat = beat;
  }

  public Played getPlayed() {
    return played;
  }

}
