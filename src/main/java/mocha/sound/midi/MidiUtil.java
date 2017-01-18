/*
 *  mocha-java.com
 */
package mocha.sound.midi;

import java.util.ArrayList;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Transmitter;

/**
 *
 * @author minaberger
 */
public class MidiUtil {

  public static void main(String[] args) {
    printMidiDevices();
  }

  public static void printMidiDevices() {
    MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
    System.out.println("MidiDevice.Info : " + infos.length + " item(s)");
    for (int i = 0; i < infos.length; i++) {
      System.out.println(" MidiDevice.Info[" + i + "]");
      System.out.println("  Name         : " + infos[i].getName());
      System.out.println("  Version      : " + infos[i].getVersion());
      System.out.println("  Vendor       : " + infos[i].getVendor());
      System.out.println("  Description  : " + infos[i].getDescription());
      MidiDevice device;
      try {
        device = MidiSystem.getMidiDevice(infos[i]);
      } catch (MidiUnavailableException ex) {
        throw new IllegalStateException(ex);
      }
      System.out.println("  Receivers    : " + device.getMaxReceivers());
      System.out.println("  Transmitters : " + device.getMaxTransmitters());
    }
  }

  public static MidiDevice[] getMidiDevices(String strDeviceName, boolean output) {
    MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
    ArrayList<MidiDevice> list = new ArrayList<>();
    for (MidiDevice.Info info : infos) {
      if (!info.getName().contains(strDeviceName)) {
        continue;
      }
      try {
        MidiDevice device = MidiSystem.getMidiDevice(info);
        if (output && device.getMaxReceivers() == 0) {
          continue;
        } else if (!output && device.getMaxTransmitters() == 0) {
          continue;
        }
        list.add(device);
      } catch (MidiUnavailableException e) {
      }
    }
    return list.toArray(new MidiDevice[]{});
  }

  public static void playNote(Receiver receiver, int channel, int note, int velocity, int ms) throws InvalidMidiDataException, InterruptedException {
    ShortMessage sm1 = new ShortMessage(ShortMessage.NOTE_ON, channel, note, velocity);
    receiver.send(sm1, -1);
    Thread.sleep(ms);
    ShortMessage sm2 = new ShortMessage(ShortMessage.NOTE_OFF, channel, note, 0);
    receiver.send(sm2, -1);
  }

  public static void reset(Receiver receiver) {
    try {
      for (int i = 0; i < 16; i++) {
        receiver.send(new ShortMessage(ShortMessage.CONTROL_CHANGE, i, 0x78, 0), -1);
        receiver.send(new ShortMessage(ShortMessage.CONTROL_CHANGE, i, 0x7B, 0), -1);
        receiver.send(new ShortMessage(ShortMessage.CONTROL_CHANGE, i, 0x79, 0), -1);
      }
    } catch (InvalidMidiDataException ex) {
      throw new IllegalStateException(ex);
    }
  }

  public static void playMidi(Sequence sequence, Receiver receiver) {
    Sequencer sequencer;
    try {
      sequencer = MidiSystem.getSequencer(false);
    } catch (MidiUnavailableException e) {
      throw new IllegalStateException(e);
    }
    if (sequencer == null) {
      throw new IllegalStateException("can't get a Sequencer");
    }
    try {
      sequencer.open();
    } catch (MidiUnavailableException e) {
      throw new IllegalStateException(e);
    }
    try {
      sequencer.setSequence(sequence);
    } catch (InvalidMidiDataException e) {
      throw new IllegalStateException(e);
    }
    Transmitter seqTransmitter;
    try {
      seqTransmitter = sequencer.getTransmitter();
    } catch (MidiUnavailableException e) {
      throw new IllegalStateException(e);
    }
    seqTransmitter.setReceiver(receiver);
    try {
      Thread.sleep(3000);
    } catch (InterruptedException ex) {
    }
sequencer.addMetaEventListener(new EndOfTrackListner(sequencer));
System.out.println("start");
sequencer.start();
  }

  public static MetaMessage getTempoMessage(double bpm) {
    long mpq = Math.round(60000000d / bpm);
    byte[] data = new byte[3];
    data[0] = new Long(mpq / 0x10000).byteValue();
    data[1] = new Long((mpq / 0x100) % 0x100).byteValue();
    data[2] = new Long(mpq % 0x100).byteValue();
    try {
      return new MetaMessage(0x51, data, data.length);
    } catch (InvalidMidiDataException ex) {
      throw new IllegalStateException(ex);
    }
  }
}
