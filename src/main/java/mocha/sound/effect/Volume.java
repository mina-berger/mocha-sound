/*
 *  mocha-java.com  * 
 */
package mocha.sound.effect;

import mocha.sound.SoundReadable;

/**
 *
 * @author minaberger
 */
public class Volume implements SoundReadable {
  SoundReadable source;
  double volume;
  
  public Volume(SoundReadable source, double volume){
    this.source = source;
    this.volume = volume;
  }

  @Override
  public long length() {
    return source.length();
  }

  @Override
  public int getChannel() {
    return source.getChannel();
  }

  @Override
  public double read() {
    return source.read() * volume;
  }
  
}
