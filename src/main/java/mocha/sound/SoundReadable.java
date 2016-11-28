package mocha.sound;

public interface SoundReadable extends SoundConstants {

  public long length();

  public int getChannel();

  public double read();
}
