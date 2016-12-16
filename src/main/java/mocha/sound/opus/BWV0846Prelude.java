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
import mocha.sound.soundbank.SecondInstrument;

public class BWV0846Prelude {

  public static void main(String[] arg) throws IOException {
    TempoMap tempoMap = new TempoMap(60);
    tempoMap.put(33, 1, 60);
    tempoMap.put(33, 3.5, 30);
    tempoMap.put(33, 3.75, 15);
    tempoMap.put(34, 0, 20);
    Measures Played = new Measures(new TimeSign(4), tempoMap);
    Instrumental inst = new SecondInstrument();

    Player rightHand = new Player(Played, inst);
    Player leftHand = new Player(Played, inst);

    double[][] notes1 = new double[][]{
      new double[]{60, 64, 67, 72, 76},
      new double[]{60, 62, 69, 74, 77},
      new double[]{59, 62, 67, 74, 77},
      new double[]{60, 64, 67, 72, 76},
      new double[]{60, 64, 69, 76, 81},
      new double[]{60, 62, 66, 69, 74},
      new double[]{59, 62, 67, 74, 79},
      new double[]{59, 60, 64, 67, 72},
      new double[]{57, 60, 64, 67, 72},
      new double[]{50, 57, 62, 66, 72},
      new double[]{55, 59, 62, 67, 71},
      new double[]{55, 58, 64, 67, 73},
      new double[]{53, 57, 62, 69, 74},
      new double[]{53, 56, 62, 65, 71},
      new double[]{52, 55, 60, 67, 72},
      new double[]{52, 53, 57, 60, 65},
      new double[]{50, 53, 57, 60, 65},
      new double[]{43, 50, 55, 59, 65},
      new double[]{48, 52, 55, 60, 64},
      new double[]{48, 55, 58, 60, 64},
      new double[]{41, 53, 57, 60, 64},
      new double[]{42, 48, 57, 60, 63},
      new double[]{44, 53, 59, 60, 62},
      new double[]{43, 53, 55, 59, 62},
      new double[]{43, 52, 55, 60, 64},
      new double[]{43, 50, 55, 60, 65},
      new double[]{43, 50, 55, 59, 65},
      new double[]{43, 51, 57, 60, 66},
      new double[]{43, 52, 55, 60, 67},
      new double[]{43, 50, 55, 60, 65},
      new double[]{43, 50, 55, 59, 65},
      new double[]{36, 48, 55, 58, 64},};
    double[][] notes2 = new double[][]{
      new double[]{36, 48, 53, 57, 60, 65, 53, 50, 53, 50},
      new double[]{36, 47, 67, 71, 74, 77, 62, 65, 64, 62},};

    double[] notes3 = new double[]{36, 48, 60, 67, 72};

    for (int i = 0; i < notes1.length; i++) {
      leftHand.play(notes1[i][0], 0.25, 7.6, 0.7);
      leftHand.play(notes1[i][1], 0.25, 6.6, 0.5);
      rightHand.setBeat(0.5);
      rightHand.play(notes1[i][2], 0.25, 0.6, 0.6);
      rightHand.play(notes1[i][3], 0.25, 0.6, 0.8);
      rightHand.play(notes1[i][4], 0.25, 0.6, 1.0);
      rightHand.play(notes1[i][2], 0.25, 0.6, 0.6);
      rightHand.play(notes1[i][3], 0.25, 0.6, 0.8);
      rightHand.play(notes1[i][4], 0.25, 0.6, 1.0);

      leftHand.setBeat(2);
      leftHand.play(notes1[i][0], 0.25, 7.6, 0.7);
      leftHand.play(notes1[i][1], 0.25, 6.6, 0.5);
      rightHand.setBeat(2.5);
      rightHand.play(notes1[i][2], 0.25, 0.6, 0.6);
      rightHand.play(notes1[i][3], 0.25, 0.6, 0.8);
      rightHand.play(notes1[i][4], 0.25, 0.6, 1.0);
      rightHand.play(notes1[i][2], 0.25, 0.6, 0.6);
      rightHand.play(notes1[i][3], 0.25, 0.6, 0.8);
      rightHand.play(notes1[i][4], 0.25, 0.6, 1.0);

      leftHand.nextMeasure();
      rightHand.nextMeasure();
    }
    for (int i = 0; i < notes2.length; i++) {
      leftHand.play(notes2[i][0], 0.25, 15.6, 0.7);
      leftHand.play(notes2[i][1], 0.25, 14.6, 0.5);
      rightHand.setBeat(0.5);
      rightHand.play(notes2[i][2], 0.25, 0.6, 0.5);
      rightHand.play(notes2[i][3], 0.25, 0.6, 0.6);
      rightHand.play(notes2[i][4], 0.25, 0.6, 0.8);
      rightHand.play(notes2[i][5], 0.25, 0.6, 1.0);
      rightHand.play(notes2[i][4], 0.25, 0.6, 0.8);
      rightHand.play(notes2[i][3], 0.25, 0.6, 0.6);
      rightHand.play(notes2[i][4], 0.25, 0.6, 1.0);
      rightHand.play(notes2[i][3], 0.25, 0.6, 0.8);
      rightHand.play(notes2[i][2], 0.25, 0.6, 0.6);
      rightHand.play(notes2[i][3], 0.25, 0.6, 1.0);
      rightHand.play(notes2[i][6], 0.25, 0.6, 0.8);
      rightHand.play(notes2[i][7], 0.25, 0.6, 0.6);
      rightHand.play(notes2[i][8], 0.25, 0.6, 0.8);
      rightHand.play(notes2[i][9], 0.25, 0.6, 0.6);

      leftHand.nextMeasure();
      rightHand.nextMeasure();
    }
    leftHand.play(notes3[0], 4, 1, 0.7);
    leftHand.setBeat(0.0625);
    leftHand.play(notes3[1], 4, 1, 0.5);
    rightHand.setBeat(0.125);
    rightHand.play(notes3[2], 4, 1, 0.5);
    rightHand.setBeat(0.1875);
    rightHand.play(notes3[3], 4, 1, 0.5);
    rightHand.setBeat(0.25);
    rightHand.play(notes3[4], 4, 1, 0.5);

    TimeLine tl = new TimeLine();
    tl.addReadable(0, new Panner(leftHand.getPlayed(), new DoubleMap(0.75)));
    tl.addReadable(0, new Panner(rightHand.getPlayed(), new DoubleMap(0.25)));

    WavFileWriter.create(tl, new File("wav/bwv0846Prelude.wav"));
  }

}
