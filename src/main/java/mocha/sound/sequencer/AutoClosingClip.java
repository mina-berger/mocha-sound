package mocha.sound.sequencer;

import javax.sound.sampled.Clip;

interface AutoClosingClip extends Clip {

  boolean isAutoClosing();

  void setAutoClosing(boolean value);
}
