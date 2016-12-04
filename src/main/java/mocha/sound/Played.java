package mocha.sound;

public class Played extends AbstractTimeLine {

  public Played() {
    super(1);
  }

  @Override
  protected SoundReadable formatReadable(SoundReadable readable) {
    int channel = readable.getChannel();
    switch (channel) {
      case 1:
        return readable;
      default:
        return null;
    }
  }
}
