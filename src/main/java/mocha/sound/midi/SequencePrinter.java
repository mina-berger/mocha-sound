package mocha.sound.midi;

import java.io.PrintStream;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.SysexMessage;
import javax.sound.midi.Track;

public class SequencePrinter {

  public static void printSequence(Sequence sequence, PrintStream out) {
    String divisionTypeName;
    if (sequence.getDivisionType() == Sequence.PPQ) {
      divisionTypeName = "PPQ";
    } else if (sequence.getDivisionType() == Sequence.SMPTE_24) {
      divisionTypeName = "SMPTE_24";
    } else if (sequence.getDivisionType() == Sequence.SMPTE_25) {
      divisionTypeName = "SMPTE_25";
    } else if (sequence.getDivisionType() == Sequence.SMPTE_30) {
      divisionTypeName = "SMPTE_30";
    } else if (sequence.getDivisionType() == Sequence.SMPTE_30DROP) {
      divisionTypeName = "SMPTE_30DROP";
    } else {
      divisionTypeName = "unknown";
    }
    out.println("division type  : " + divisionTypeName);
    out.println("resolution     : " + sequence.getResolution());
    out.println("length in ms   : " + sequence.getMicrosecondLength());
    out.println("length in tick : " + sequence.getTickLength());
    int i = 0;
    for (Track track : sequence.getTracks()) {
      printTrack(track, "track-" + i++, out);
    }
  }

  public static void printTrack(Track track, String name, PrintStream out) {
    out.println(name + " length in tick :" + track.ticks());
    out.println(name + " size           :" + track.size());
    for (int i = 0; i < track.size(); i++) {
      printMidiEvent(track.get(i), name + ".event-" + i, out);
    }
  }

  public static void printMidiEvent(MidiEvent event, String name, PrintStream out) {
    out.println(name + " tick : " + event.getTick());
    MidiMessage message = event.getMessage();
    if (message instanceof ShortMessage) {
      printShortMessage((ShortMessage) message, name + ".short", out);
    } else if (message instanceof MetaMessage) {
      printMetaMessage((MetaMessage) message, name + ".meta", out);
    } else if (message instanceof SysexMessage) {
      out.println(name + " sysex message");
    } else {
      out.println(name + " unknown");
    }

  }

  public static void printMetaMessage(MetaMessage message, String name, PrintStream out) {
    out.println(name + " type : " + message.getType());
  }

  public static void printShortMessage(ShortMessage message, String name, PrintStream out) {
    String command;
    switch (message.getCommand()) {
      case ShortMessage.ACTIVE_SENSING:
        command = "ACTIVE_SENSING";
        break;
      case ShortMessage.CHANNEL_PRESSURE:
        command = "CHANNEL_PRESSURE";
        break;
      case ShortMessage.CONTINUE:
        command = "CONTINUE";
        break;
      case ShortMessage.CONTROL_CHANGE:
        command = "CONTROL_CHANGE";
        break;
      case ShortMessage.END_OF_EXCLUSIVE:
        command = "END_OF_EXCLUSIVE";
        break;
      case ShortMessage.MIDI_TIME_CODE:
        command = "MIDI_TIME_CODE";
        break;
      case ShortMessage.NOTE_OFF:
        command = "NOTE_OFF";
        break;
      case ShortMessage.NOTE_ON:
        command = "NOTE_ON";
        break;
      case ShortMessage.PITCH_BEND:
        command = "PITCH_BEND";
        break;
      case ShortMessage.POLY_PRESSURE:
        command = "POLY_PRESSURE";
        break;
      case ShortMessage.PROGRAM_CHANGE:
        command = "PROGRAM_CHANGE";
        break;
      case ShortMessage.SONG_POSITION_POINTER:
        command = "SONG_POSITION_POINTER";
        break;
      case ShortMessage.SONG_SELECT:
        command = "SONG_SELECT";
        break;
      case ShortMessage.START:
        command = "START";
        break;
      case ShortMessage.STOP:
        command = "STOP";
        break;
      case ShortMessage.SYSTEM_RESET:
        command = "SYSTEM_RESET";
        break;
      case ShortMessage.TIMING_CLOCK:
        command = "TIMING_CLOCK";
        break;
      case ShortMessage.TUNE_REQUEST:
        command = "TUNE_REQUEST";
        break;
      default:
        command = "unknown(" + message.getCommand() + ")";
        break;
    }
    out.println(name + " command : " + command);
    out.println(name + " channel : " + message.getChannel());
    out.println(name + " data    : " + message.getData1() + ", " + message.getData2());

  }

}
