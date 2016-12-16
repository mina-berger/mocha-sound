package mocha.sound;

public interface Instrumental {
  public Played play(double note, double duration, double velocity);
  public String getName();
}
