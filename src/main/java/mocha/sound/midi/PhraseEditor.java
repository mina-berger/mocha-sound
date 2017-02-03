/*
 *  mocha-java.com  * 
 */
package mocha.sound.midi;

/**
 *
 * @author minaberger
 */
public abstract class PhraseEditor {

  private final SequenceEditor se;
  private int baseMeasure;
  private double baseBeat;

  public PhraseEditor(SequenceEditor se) {
    this.se = se;
    baseMeasure = 0;
    baseBeat = 0;
  }

  public void playPhrase(int measure, double beat, int... notes) {
    baseMeasure = measure;
    baseBeat = beat;
    playPhrase(notes);
  }

  protected abstract void playPhrase(int[] notes);

  protected void setTempo(int measure, double beat, double tempo) {
    se.setTempo(baseMeasure + measure, baseBeat + beat, tempo);
  }

  protected void play(int measure, double beat, int track, int note, int velocity, double duration) {
    se.getTrackEditor(track).play(baseMeasure + measure, baseBeat + beat, note, velocity, duration);
  }

}
