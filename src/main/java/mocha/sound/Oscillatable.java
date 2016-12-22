package mocha.sound;

public interface Oscillatable extends SoundConstants {
  public double read(double freq, double feedback, double volume);
}
