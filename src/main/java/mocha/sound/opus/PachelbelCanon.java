package mocha.sound.opus;


import java.io.File;
import java.io.IOException;
import mocha.sound.DoubleMap;
import mocha.sound.Instrumental;
import mocha.sound.Measures;
import mocha.sound.Panner;
import mocha.sound.Player;
import mocha.sound.TempoMap;
import mocha.sound.TimeLine;
import mocha.sound.TimeSign;
import mocha.sound.WavFileWriter;
import mocha.sound.soundbank.FMStrings;
import mocha.sound.soundbank.SecondInstrument;

public class PachelbelCanon {

  public static void main(String[] arg) throws IOException {
    TempoMap tempoMap = new TempoMap(40);
    Measures Played = new Measures(new TimeSign(4), tempoMap);
    Instrumental cembalo = new SecondInstrument();

    Player rightHand = new Player(Played, cembalo);
    Player leftHand = new Player(Played, cembalo);
    Player violin1 = new Player(Played, new FMStrings());
    Player violin2 = new Player(Played, new FMStrings());
    Player violin3 = new Player(Played, new FMStrings());

    double[][] rightNotes = new double[][]{
      new double[]{57, 62, 66},
      new double[]{61, 64, 69},
      new double[]{59, 62, 66},
      new double[]{57, 61, 66},
      new double[]{59, 62, 67},
      new double[]{57, 62, 66},
      new double[]{59, 62, 67},
      new double[]{57, 61, 64},};
    double[] leftNotes = new double[]{50, 45, 47, 42, 43, 38, 43, 45};
    double arpegio = 0.0626;


    for (int i = 0; i < 5; i++) {
      leftHand.play(leftNotes[0], 1, 0.9, 0.7);
      rightHand.play(rightNotes[0], 1, 0.9, 0.6, arpegio);

      leftHand.play(leftNotes[1], 1, 0.9, 0.7);
      rightHand.play(rightNotes[1], 1, 0.9, 0.6, arpegio);

      leftHand.play(leftNotes[2], 1, 0.9, 0.7);
      rightHand.play(rightNotes[2], 1, 0.9, 0.6, arpegio);

      leftHand.play(leftNotes[3], 1, 0.9, 0.7);
      rightHand.play(rightNotes[3], 1, 0.9, 0.6, arpegio);
      
      leftHand.nextMeasure();
      rightHand.nextMeasure();

      leftHand.play(leftNotes[4], 1, 0.9, 0.7);
      rightHand.play(rightNotes[4], 1, 0.9, 0.6, arpegio);

      leftHand.play(leftNotes[5], 1, 0.9, 0.7);
      rightHand.play(rightNotes[5], 1, 0.9, 0.6, arpegio);

      leftHand.play(leftNotes[6], 1, 0.9, 0.7);
      rightHand.play(rightNotes[6], 1, 0.9, 0.6, arpegio);

      leftHand.play(leftNotes[7], 1, 0.9, 0.7);
      rightHand.play(rightNotes[7], 1, 0.9, 0.6, arpegio);
      
      leftHand.nextMeasure();
      rightHand.nextMeasure();
    }
    violin1.setMeasure(2);
    playViolin(violin1);
    violin2.setMeasure(4);
    playViolin(violin2);
    violin3.setMeasure(6);
    playViolin(violin3);

    TimeLine tl = new TimeLine();
    //tl.addReadable(0, new Panner(leftHand.getPlayed(), new DoubleMap(0.6)));
    //tl.addReadable(0, new Panner(rightHand.getPlayed(), new DoubleMap(0.4)));
    tl.addReadable(0, new Panner(violin1.getPlayed(), new DoubleMap(0.5)));
    //tl.addReadable(0, new Panner(violin2.getPlayed(), new DoubleMap(0.2)));
    //tl.addReadable(0, new Panner(violin3.getPlayed(), new DoubleMap(0.8)));

    WavFileWriter.create(tl, new File("wav/pachelbelCanon.wav"));
  }
  public static void playViolin(Player violin){
    violin.play(78, 1, 0.9, 0.7);
    violin.play(76, 1, 0.9, 0.7);
    violin.play(74, 1, 0.9, 0.7);
    violin.play(73, 1, 0.9, 0.7);
    violin.nextMeasure();
    violin.play(71, 1, 0.9, 0.7);
    violin.play(69, 1, 0.9, 0.7);
    violin.play(71, 1, 0.9, 0.7);
    violin.play(73, 1, 0.9, 0.7);
    violin.nextMeasure();
    violin.play(74, 1, 0.9, 0.7);
    violin.play(73, 1, 0.9, 0.7);
    violin.play(71, 1, 0.9, 0.7);
    violin.play(69, 1, 0.9, 0.7);
    violin.nextMeasure();
    violin.play(67, 1, 0.9, 0.7);
    violin.play(66, 1, 0.9, 0.6);
    violin.play(67, 1, 0.9, 0.5);
    violin.play(64, 1, 0.9, 0.4);
    violin.nextMeasure();
    violin.play(62, 0.5, 0.9, 0.7);
    violin.play(66, 0.5, 0.7, 0.5);
    violin.play(69, 0.5, 0.9, 0.7);
    violin.play(67, 0.5, 0.7, 0.5);
    violin.play(66, 0.5, 0.9, 0.7);
    violin.play(62, 0.5, 0.7, 0.5);
    violin.play(66, 0.5, 0.9, 0.7);
    violin.play(64, 0.5, 0.7, 0.5);
    violin.nextMeasure();
    violin.play(62, 0.5, 0.9, 0.7);
    violin.play(59, 0.5, 0.7, 0.5);
    violin.play(62, 0.5, 0.9, 0.7);
    violin.play(69, 0.5, 0.7, 0.5);
    violin.play(67, 0.5, 0.9, 0.7);
    violin.play(71, 0.5, 0.7, 0.5);
    violin.play(69, 0.5, 0.9, 0.7);
    violin.play(73, 0.5, 0.7, 0.5);
    violin.nextMeasure();
    violin.play(66, 0.5, 0.9, 0.7);
    violin.play(62, 0.5, 0.7, 0.5);
    violin.play(64, 0.5, 0.9, 0.7);
    violin.play(73, 0.5, 0.7, 0.5);
    violin.play(74, 0.5, 0.9, 0.7);
    violin.play(78, 0.5, 0.7, 0.5);
    violin.play(81, 0.5, 0.9, 0.7);
    violin.play(69, 0.5, 0.7, 0.5);
    violin.nextMeasure();
    violin.play(71, 0.5, 0.9, 0.7);
    violin.play(67, 0.5, 0.7, 0.5);
    violin.play(69, 0.5, 0.9, 0.7);
    violin.play(66, 0.5, 0.7, 0.5);
    violin.play(62, 0.5, 0.9, 0.7);
    violin.play(74, 0.5, 0.7, 0.5);
    violin.play(74, 0.75, 0.9, 0.8);
    violin.play(73, 0.25, 0.9, 0.5);
    violin.nextMeasure();
    
    
  }

}
