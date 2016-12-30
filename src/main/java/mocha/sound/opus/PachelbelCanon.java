package mocha.sound.opus;


import java.io.File;
import java.io.IOException;
import mocha.sound.DoubleMap;
import mocha.sound.Instrumental;
import mocha.sound.Measures;
import mocha.sound.Panner;
import mocha.sound.Phrase;
import mocha.sound.Player;
import mocha.sound.TempoMap;
import mocha.sound.TimeLine;
import mocha.sound.TimeSign;
import mocha.sound.WavFileWriter;
import mocha.sound.effect.Volume;
import mocha.sound.soundbank.FMStrings;
import mocha.sound.soundbank.FirstInstrument;
import mocha.sound.soundbank.SecondInstrument;

public class PachelbelCanon {

  public static void main(String[] arg) throws IOException {
    TempoMap tempoMap = new TempoMap(43);
    tempoMap.put(55, 0, 43);
    tempoMap.put(55, 3.99, 10);
    tempoMap.put(56, 0, 25);
    Measures Played = new Measures(new TimeSign(4), tempoMap);
    Instrumental cembalo = new SecondInstrument();

    Player rightHand = new Player(Played, cembalo);
    Player leftHand = new Player(Played, cembalo);
    Player violin1 = new Player(Played, new FMStrings());
    Player violin2 = new Player(Played, new FirstInstrument());
    Player violin3 = new Player(Played, new FirstInstrument());

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


    for (int i = 0; i < 28; i++) {
      if(i == 11){
        leftHand.play(leftNotes[0], 1, 0.3, 0.7);
        rightHand.play(rightNotes[0], 1, 0.3, 0.6, 0);
        leftHand.nextMeasure();
        rightHand.nextMeasure();
        leftHand.setBeat(3);
        rightHand.setBeat(3);
        leftHand.play(leftNotes[7], 1, 0.9, 0.7);
        rightHand.play(rightNotes[7], 1, 0.9, 0.6, arpegio);

        leftHand.nextMeasure();
        rightHand.nextMeasure();
        continue;
      }
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
    leftHand.play(leftNotes[0], 1, 0.9, 0.7);
    rightHand.play(rightNotes[0], 1, 0.9, 0.6, arpegio);
    
    violin1.setMeasure(2);
    playViolin(violin1, 0);
    violin2.setMeasure(4);
    playViolin(violin2, 1);
    violin3.setMeasure(6);
    playViolin(violin3, 2);

    TimeLine tl = new TimeLine();
    tl.addReadable(0, new Panner(new Volume(leftHand.getPlayed(), 0.4), new DoubleMap(0.6)));
    tl.addReadable(0, new Panner(new Volume(rightHand.getPlayed(), 0.2), new DoubleMap(0.4)));
    tl.addReadable(0, new Panner(new Volume(violin1.getPlayed(), 1.0), new DoubleMap(0.5)));
    tl.addReadable(0, new Panner(new Volume(violin2.getPlayed(), 1.2), new DoubleMap(0.2)));
    tl.addReadable(0, new Panner(new Volume(violin3.getPlayed(), 1.2), new DoubleMap(0.8)));

    WavFileWriter.create(tl, new File("wav/pachelbelCanon.wav"));
  }
  public static void playViolin(Player violin, int index){
    Phrase fourQuaters = new Phrase(){
      @Override
      public void play(Player player, double... notes) {
        player.play(notes[0], 1, 0.9, 0.7);
        player.play(notes[1], 1, 0.9, 0.7);
        player.play(notes[2], 1, 0.9, 0.7);
        player.play(notes[3], 1, 0.9, 0.7);
      }
      
    };
    fourQuaters.play(violin, 78, 76, 74, 73);
    violin.nextMeasure();
    fourQuaters.play(violin, 71, 69, 71, 73);
    violin.nextMeasure();
    fourQuaters.play(violin, 74, 73, 71, 69);
    violin.nextMeasure();
    fourQuaters.play(violin, 67, 66, 67, 64);
    violin.nextMeasure();
    
    Phrase fourEighths = new Phrase(){
      @Override
      public void play(Player player, double... notes) {
        player.play(notes[0], 0.5, 0.9, 0.7);
        player.play(notes[1], 0.5, 0.7, 0.5);
        player.play(notes[2], 0.5, 0.9, 0.7);
        player.play(notes[3], 0.5, 0.7, 0.5);
      }
    };
    fourEighths.play(violin, 62, 66, 69, 67);
    fourEighths.play(violin, 66, 62, 66, 64);
    violin.nextMeasure();
    
    fourEighths.play(violin, 62, 59, 62, 69);
    fourEighths.play(violin, 67, 71, 69, 67);
    violin.nextMeasure();
    
    fourEighths.play(violin, 66, 62, 64, 73);
    fourEighths.play(violin, 74, 78, 81, 69);
    violin.nextMeasure();
    fourEighths.play(violin, 71, 67, 69, 66);
    violin.play(62, 0.5, 0.9, 0.7);
    violin.play(74, 0.5, 0.7, 0.5);
    violin.play(74, 0.75, 0.9, 0.8);
    violin.play(73, 0.25, 0.9, 0.5);
    violin.nextMeasure();
    
    Phrase fourSixteens1 = new Phrase(){
      @Override
      public void play(Player player, double... notes) {
        player.play(notes[0], 0.25, 0.9, 0.7);
        player.play(notes[1], 0.25, 0.9, 0.5);
        player.play(notes[2], 0.25, 0.9, 0.6);
        player.play(notes[3], 0.25, 0.7, 0.4);
      }
    };
    fourSixteens1.play(violin, 74, 73, 74, 62);
    fourSixteens1.play(violin, 61, 69, 64, 66);
    fourSixteens1.play(violin, 62, 74, 73, 71);
    fourSixteens1.play(violin, 73, 78, 81, 83);
    violin.nextMeasure();
    
    fourSixteens1.play(violin, 79, 78, 76, 79);
    fourSixteens1.play(violin, 78, 76, 74, 73);
    fourSixteens1.play(violin, 71, 69, 67, 66);
    fourSixteens1.play(violin, 64, 67, 66, 64);
    violin.nextMeasure();

    fourSixteens1.play(violin, 62, 64, 66, 67);
    fourSixteens1.play(violin, 69, 64, 69, 67);
    fourSixteens1.play(violin, 66, 71, 69, 67);
    fourSixteens1.play(violin, 69, 67, 66, 64);
    violin.nextMeasure();
    
    fourSixteens1.play(violin, 62, 59, 71, 73);
    fourSixteens1.play(violin, 74, 73, 71, 69);
    fourSixteens1.play(violin, 67, 66, 64, 71);
    fourSixteens1.play(violin, 69, 71, 69, 67);
    violin.nextMeasure();
    
    violin.play(66, 0.5, 0.9, 0.7);
    violin.play(78, 0.5, 0.4, 0.5);
    violin.play(76, 1.0, 0.9, 0.7);
    violin.rest(0.5);
    violin.play(74, 0.5, 0.4, 0.5);
    violin.play(78, 1.0, 0.9, 0.7);
    violin.nextMeasure();
    fourQuaters.play(violin, 83, 81, 83, 85);
    violin.nextMeasure();
    
    violin.play(86, 0.5, 0.9, 0.7);
    violin.play(74, 0.5, 0.4, 0.5);
    violin.play(73, 1.0, 0.9, 0.7);
    violin.rest(0.5);
    violin.play(71, 0.5, 0.4, 0.5);
    violin.play(74, 1.0, 0.9, 0.7);
    violin.nextMeasure();
    violin.play(74, 1.5, 0.9, 0.7);
    violin.play(74, 0.5, 0.9, 0.7);
    fourEighths.play(violin, 74, 79, 76, 81);
    violin.nextMeasure();
    
    Phrase phrase1 = new Phrase(){
      @Override
      public void play(Player player, double... notes) {
        player.play(notes[0], 0.25, 0.9, 0.9);
        player.play(notes[1], 0.125, 0.9, 0.4);
        player.play(notes[2], 0.125, 0.7, 0.6);
      }
    };
    Phrase phrase2 = new Phrase(){
      @Override
      public void play(Player player, double... notes) {
        player.play(notes[0], 0.25, 0.6, 0.9);
        player.play(notes[1], 0.125, 0.9, 0.4);
        player.play(notes[2], 0.125, 0.7, 0.6);
      }
    };
    Phrase four32_1 = new Phrase(){
      @Override
      public void play(Player player, double... notes) {
        player.play(notes[0], 0.125, 0.5, 0.9);
        player.play(notes[1], 0.125, 0.9, 0.5);
        player.play(notes[2], 0.125, 0.9, 0.7);
        player.play(notes[3], 0.125, 0.9, 0.5);
      }
    };    
    Phrase four32_2 = new Phrase(){
      @Override
      public void play(Player player, double... notes) {
        player.play(notes[0], 0.125, 0.9, 0.7);
        player.play(notes[1], 0.125, 0.9, 0.5);
        player.play(notes[2], 0.125, 0.9, 0.7);
        player.play(notes[3], 0.125, 0.9, 0.5);
      }
    };    
    phrase1.play(violin, 81, 78, 79);
    phrase1.play(violin, 81, 78, 79);
    four32_1.play(violin, 81, 69, 71, 73);
    four32_2.play(violin, 74, 76, 78, 79);
    
    phrase1.play(violin, 78, 74, 76);
    phrase2.play(violin, 78, 66, 67);
    four32_2.play(violin, 69, 71, 69, 67);
    four32_2.play(violin, 69, 66, 67, 69);
    violin.nextMeasure();
    phrase1.play(violin, 67, 71, 69);
    phrase1.play(violin, 67, 66, 64);
    four32_1.play(violin, 66, 64, 62, 64);
    four32_2.play(violin, 66, 67, 69, 71);

    phrase1.play(violin, 67, 71, 69);
    phrase2.play(violin, 71, 73, 74);
    four32_1.play(violin, 69, 71, 73, 74);
    four32_2.play(violin, 76, 78, 79, 81);
    violin.nextMeasure();
    
    phrase1.play(violin, 78, 74, 76);
    phrase1.play(violin, 78, 76, 74);
    four32_1.play(violin, 76, 73, 74, 76);
    four32_2.play(violin, 78, 76, 74, 73);
    
    phrase1.play(violin, 74, 71, 73);
    phrase2.play(violin, 74, 62, 64);
    four32_1.play(violin, 66, 67, 66, 64);
    four32_2.play(violin, 66, 74, 73, 74);
    violin.nextMeasure();
    
    phrase1.play(violin, 71, 74, 73);
    phrase1.play(violin, 71, 69, 67);
    four32_1.play(violin, 69, 67, 66, 67);
    four32_2.play(violin, 69, 71, 73, 74);

    phrase1.play(violin, 71, 74, 73);
    phrase2.play(violin, 74, 73, 71);
    four32_1.play(violin, 73, 74, 76, 74);
    four32_2.play(violin, 73, 74, 71, 73);
    violin.nextMeasure();
    
    violin.play(74, 0.5, 0.4, 0.7);
    violin.rest(0.5);
    violin.play(73, 0.5, 0.4, 0.7);
    violin.rest(0.5);
    violin.play(71, 0.5, 0.4, 0.7);
    violin.rest(0.5);
    violin.play(73, 0.5, 0.4, 0.7);
    violin.rest(0.5);
    violin.nextMeasure();
    violin.play(62, 0.5, 0.4, 0.7);
    violin.rest(0.5);
    violin.play(62, 0.5, 0.4, 0.7);
    violin.rest(0.5);
    violin.play(62, 0.5, 0.4, 0.7);
    violin.rest(0.5);
    violin.play(64, 0.5, 0.4, 0.7);
    violin.rest(0.5);
    violin.nextMeasure();
    violin.rest(0.5);
    violin.play(69, 0.5, 0.4, 0.7);
    violin.rest(0.5);
    violin.play(69, 0.5, 0.4, 0.7);
    violin.rest(0.5);
    violin.play(66, 0.5, 0.4, 0.7);
    violin.rest(0.5);
    violin.play(69, 0.5, 0.4, 0.7);

    violin.nextMeasure();
    violin.rest(0.5);
    violin.play(67, 0.5, 0.4, 0.7);
    violin.rest(0.5);
    violin.play(66, 0.5, 0.4, 0.7);
    violin.rest(0.5);
    violin.play(67, 0.5, 0.4, 0.7);
    violin.rest(0.5);
    violin.play(76, 0.5, 0.4, 0.7);
    violin.nextMeasure();
    
    Phrase fourSixteens2 = new Phrase(){
      @Override
      public void play(Player player, double... notes) {
        player.play(notes[0], 0.25, 0.5, 0.7);
        player.play(notes[1], 0.25, 0.9, 0.5);
        player.play(notes[2], 0.25, 0.8, 0.6);
        player.play(notes[3], 0.25, 0.7, 0.4);
      }
    };
    fourSixteens2.play(violin, 78, 66, 67, 66);
    fourSixteens2.play(violin, 64, 76, 78, 76);
    fourSixteens2.play(violin, 74, 66, 62, 71);
    fourSixteens2.play(violin, 69, 57, 55, 57);
    
    violin.nextMeasure();
    fourSixteens2.play(violin, 59, 71, 73, 71);
    fourSixteens2.play(violin, 69, 57, 55, 57);
    fourSixteens2.play(violin, 59, 71, 69, 71);
    fourSixteens2.play(violin, 73, 61, 59, 61);
    
    violin.nextMeasure();
    fourSixteens2.play(violin, 62, 74, 76, 74);
    fourSixteens2.play(violin, 73, 61, 62, 61);
    fourSixteens2.play(violin, 59, 71, 69, 71);
    fourSixteens2.play(violin, 73, 61, 66, 64);

    violin.nextMeasure();
    fourSixteens2.play(violin, 62, 74, 76, 79);
    fourSixteens2.play(violin, 78, 66, 69, 78);
    fourSixteens2.play(violin, 74, 79, 78, 79);
    fourSixteens2.play(violin, 76, 69, 67, 69);
    Phrase fourSixteens3 = new Phrase(){
      @Override
      public void play(Player player, double... notes) {
        player.play(notes[0], 0.25, 0.5, 0.7);
        player.play(notes[1], 0.25, 0.5, 0.5);
        player.play(notes[2], 0.25, 0.5, 0.5);
        player.play(notes[3], 0.25, 0.5, 0.5);
      }
    };
    violin.nextMeasure();
    fourSixteens3.play(violin, 66, 69, 69, 69);
    fourSixteens3.play(violin, 69, 69, 69, 69);
    fourSixteens3.play(violin, 66, 66, 66, 66);
    fourSixteens3.play(violin, 66, 66, 69, 69);

    violin.nextMeasure();
    fourSixteens3.play(violin, 67, 67, 67, 74);
    fourSixteens3.play(violin, 74, 74, 74, 74);
    fourSixteens3.play(violin, 74, 74, 71, 71);
    fourSixteens3.play(violin, 69, 69, 76, 73);

    violin.nextMeasure();
    fourSixteens3.play(violin, 69, 78, 78, 78);
    fourSixteens3.play(violin, 76, 76, 76, 76);
    fourSixteens3.play(violin, 74, 74, 74, 74);
    fourSixteens3.play(violin, 81, 81, 81, 81);
    
    violin.nextMeasure();
    fourSixteens3.play(violin, 83, 83, 83, 83);
    fourSixteens3.play(violin, 81, 81, 81, 81);
    fourSixteens3.play(violin, 83, 83, 83, 83);
    fourSixteens3.play(violin, 85, 73, 73, 73);
    
    Phrase phrase3 = new Phrase(){
      @Override
      public void play(Player player, double... notes) {
        player.play(notes[0], 0.25, 0.4, 0.9);
        player.play(notes[1], 0.125, 0.9, 0.4);
        player.play(notes[2], 0.125, 0.9, 0.5);
        player.play(notes[3], 0.25, 0.8, 0.7);
        player.play(notes[4], 0.25, 0.4, 0.6);
      }
    };
    violin.nextMeasure();
    phrase3.play(violin, 74, 62, 64, 66, 62);
    phrase3.play(violin, 61, 73, 74, 76, 73);
    phrase3.play(violin, 71, 59, 61, 62, 59);
    phrase3.play(violin, 61, 69, 67, 66, 64);

    violin.nextMeasure();
    phrase3.play(violin, 62, 67, 66, 64, 67);
    phrase3.play(violin, 66, 62, 64, 66, 69);
    phrase3.play(violin, 67, 71, 69, 67, 66);
    phrase3.play(violin, 64, 69, 67, 66, 64);
    
    violin.nextMeasure();
    phrase3.play(violin, 66, 74, 73, 74, 66);
    phrase3.play(violin, 69, 69, 71, 73, 69);
    phrase3.play(violin, 66, 74, 76, 78, 74);
    phrase3.play(violin, 78, 78, 76, 74, 73);
    
    violin.nextMeasure();
    phrase3.play(violin, 71, 71, 69, 71, 73);
    phrase3.play(violin, 74, 78, 76, 74, 78);
    phrase3.play(violin, 79, 74, 73, 71, 71);
    fourSixteens3.play(violin, 69, 64, 69, 69);
    
    violin.nextMeasure();
    violin.play(69, 1.5, 0.9, 0.6);
    violin.play(69, 0.5, 0.7, 0.4);
    violin.play(62, 1.5, 0.9, 0.6);
    violin.play(69, 0.5, 0.7, 0.4);
    violin.nextMeasure();
    violin.play(67, 1.0, 0.9, 0.6);
    violin.play(69, 1.0, 0.9, 0.6);
    violin.play(67, 0.5, 0.7, 0.6);
    violin.play(62, 0.5, 0.7, 0.6);
    violin.play(62, 0.75, 0.9, 0.6);
    violin.play(61, 0.25, 0.7, 0.4);

    violin.nextMeasure();
    violin.play(62, 0.5, 0.7, 0.6);
    violin.play(74, 0.5, 0.7, 0.6);
    violin.play(73, 1.0, 0.9, 0.6);
    violin.play(71, 1.0, 0.9, 0.6);
    violin.play(69, 1.0, 0.9, 0.6);
    violin.nextMeasure();
    violin.play(62, 0.75, 0.9, 0.6);
    violin.play(64, 0.25, 0.7, 0.4);
    violin.play(66, 1.0, 0.9, 0.6);
    violin.play(71, 1.0, 0.9, 0.6);
    violin.play(64, 0.75, 0.9, 0.6);
    violin.play(64, 0.25, 0.7, 0.4);

    violin.nextMeasure();
    
    Phrase phrase4 = new Phrase(){
      @Override
      public void play(Player player, double... notes) {
        player.play(notes[0], 0.75, 0.9, 0.5);
        player.play(notes[1], 0.25, 0.7, 0.5);
        player.play(notes[2], 0.25, 0.9, 0.7);
        player.play(notes[3], 0.25, 0.7, 0.5);
        player.play(notes[4], 0.25, 0.9, 0.7);
        player.play(notes[5], 0.25, 0.7, 0.5);
      }
    };
    Phrase phrase5 = new Phrase(){
      @Override
      public void play(Player player, double... notes) {
        player.play(notes[0], 0.25, 0.9, 0.7);
        player.play(notes[1], 0.25, 0.7, 0.5);
        player.play(notes[2], 0.25, 0.9, 0.7);
        player.play(notes[3], 0.25, 0.7, 0.5);
        player.play(notes[4], 0.75, 0.9, 0.5);
        player.play(notes[5], 0.25, 0.7, 0.5);
      }
    };
    phrase4.play(violin, 66, 78, 78, 79, 78, 76);
    phrase4.play(violin, 74, 74, 74, 76, 74, 73);
    violin.nextMeasure();

    violin.play(71, 1.0, 0.9, 0.8);
    violin.play(74, 1.0, 0.9, 0.8);
    phrase5.play(violin, 74, 72, 71, 72, 69, 69);
    violin.nextMeasure();

    phrase4.play(violin, 69, 81, 81, 83, 81, 79);
    phrase4.play(violin, 78, 78, 78, 79, 78, 76);
    violin.nextMeasure();

    phrase5.play(violin, 74, 72, 71, 72, 69, 69);
    violin.play(67, 0.5, 0.9, 0.7);
    violin.play(74, 0.5, 0.9, 0.5);
    violin.play(73, 0.75, 0.9, 0.7);
    violin.play(73, 0.25, 0.7, 0.5);
    
    violin.nextMeasure();
    violin.play(74, 0.5, 0.5, 0.5);
    violin.play(74, 1.0, 0.9, 0.7);
    violin.play(73, 1.0, 0.9, 0.7);
    violin.play(71, 1.0, 0.9, 0.7);
    violin.play(69, 1.0, 0.9, 0.7);
    violin.nextMeasure();
    violin.rest(0.5);
    violin.play(67, 1.0, 0.9, 0.7);
    violin.play(66, 1.25, 0.9, 0.7);
    violin.play(64, 0.25, 0.5, 0.5);
    violin.play(64, 1.0, 0.9, 0.7);
    
    violin.nextMeasure();
    violin.play(66, 0.5, 0.7, 0.5);
    violin.play(78, 1.0, 0.9, 0.7);
    violin.play(76, 0.5, 0.7, 0.5);
    violin.play(74, 0.5, 0.7, 0.5);
    violin.play(86, 1.0, 0.9, 0.7);
    violin.play(84, 0.5, 0.7, 0.5);
    
    violin.nextMeasure();
    violin.play(83, 1.0, 0.9, 0.5);
    violin.play(86, 0.5, 0.9, 0.7);
    violin.play(81, 0.5, 0.9, 0.5);
    violin.play(83, 1.0, 0.9, 0.7);
    violin.play(81, 1.0, 0.9, 0.7);
    
    violin.nextMeasure();
    violin.play(81, 1.0, 0.7, 0.7);
    violin.play(69, 0.75, 0.9, 0.7);
    violin.play(67, 0.25, 0.7, 0.5);
    violin.play(66, 1.0, 0.7, 0.7);
    violin.play(78, 0.75, 0.9, 0.7);
    violin.play(76, 0.25, 0.7, 0.5);

    violin.nextMeasure();
    violin.play(74, 1.5, 0.7, 0.7);
    violin.play(74, 0.5, 0.7, 0.7);
    violin.play(74, 1.0, 0.9, 0.7);
    violin.play(73, 1.0, 0.9, 0.7);
    
    Phrase phrase6 = new Phrase(){
      @Override
      public void play(Player player, double... notes) {
        player.play(notes[0], 0.5, 0.9, 0.5);
        player.play(notes[1], 0.5, 0.9, 0.5);
        player.play(notes[2], 0.5, 0.9, 0.5);
        player.play(notes[3], 0.5, 0.9, 0.5);
      }
    };    
    violin.nextMeasure();
    if(index == 2){
      violin.play(74, 1.0, 1.0, 0.7);
    }else{
      phrase6.play(violin, 74, 62, 61, 73);
      phrase6.play(violin, 71, 59, 57, 69);
      violin.nextMeasure();
      phrase6.play(violin, 67, 79, 78, 66);
      phrase6.play(violin, 64, 71, 64, 76);
      violin.nextMeasure();
      if(index == 1){
        violin.play(78, 1.0, 1.0, 0.7);
      }else{
        phrase6.play(violin, 78, 66, 64, 76);
        phrase6.play(violin, 74, 62, 61, 73);
        violin.nextMeasure();
        phrase6.play(violin, 71, 83, 81, 69);
        violin.play(67, 0.75, 0.9, 0.7);
        violin.play(76, 0.25, 0.7, 0.7);
        violin.play(69, 0.5, 0.9, 0.5);
        violin.play(69, 0.5, 0.9, 0.5);
        violin.nextMeasure();
        violin.play(69, 1.0, 1.0, 0.7);
      }
    }
    
  }

}
