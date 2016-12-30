package mocha.sound;

public interface Instrumental {
  public SoundReadable play(double note, double duration, double velocity);
  public String getName();
}
