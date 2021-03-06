package mocha.sound;

public class TimeLine extends AbstractTimeLine {

  public TimeLine() {
    super(2);
  }

  @Override
  protected SoundReadable formatReadable(SoundReadable readable) {
    int channel = readable.getChannel();
    switch (channel) {
      case 1:
        return new Panner(readable, new DoubleMap(0.5));
      case 2:
        return readable;
      default:
        return null;
    }
  }

}
