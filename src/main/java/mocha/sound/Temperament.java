package mocha.sound;

public class Temperament {

public static double NOTES_IN_OCTAVE = 12;
public static double FREQUENCY_A = 440;
public static double NOTE_NUMBER_A = 69;

public static double getFreq(double noteNumber) {
  return Math.pow(2d, (noteNumber - NOTE_NUMBER_A) / NOTES_IN_OCTAVE) * FREQUENCY_A;
}
  public static void main(String[] args){
System.out.println(getFreq(60));
System.out.println(getFreq(64));
System.out.println(getFreq(67));
  }

}
