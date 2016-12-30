/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mocha.sound.midi;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.SysexMessage;
import javax.sound.midi.Transmitter;
import javax.sound.sampled.AudioFileFormat;
import mocha.sound.sequencer.RealTimeSequencer;

public class MidiUtil {

  public static final int RESOLUTION = 1000;//per second;
  public static int SYSEX_STATUS_AB = 0xf0;
  public static int SYSEX_STATUS_AD = 0xf7;
  public static final int OM_PRODUCT_ID = 0x7f;
  public static final int OM_MSG_TYPE_BREVIS = 0x00;
  public static final int OM_MSG_TYPE_SYSTEM = 0x01;

  public static AudioFileFormat.Type FILETYPE = AudioFileFormat.Type.WAVE;

  public static SysexMessage sysex(int... data) throws InvalidMidiDataException {
    byte[] bytes = new byte[data.length];
    for (int i = 0; i < data.length; i++) {
      bytes[i] = (byte) data[i];
    }
    return new SysexMessage(bytes, bytes.length);
  }


  public static void playNote(Receiver receiver, int channel, int note, int velocity, int ms) throws InvalidMidiDataException, InterruptedException {
    ShortMessage sm1 = new ShortMessage();
    sm1.setMessage(ShortMessage.NOTE_ON, channel, note, velocity);
    receiver.send(sm1, 1);
    Thread.sleep(ms);
    ShortMessage sm2 = new ShortMessage();
    sm2.setMessage(ShortMessage.NOTE_OFF, channel, note, 0);
    receiver.send(sm2, 1);

  }

  public static void playNotes(Receiver receiver, int channel, int[] notes, int shift, int ms) throws InvalidMidiDataException, InterruptedException {
    for (int i = 0; i < notes.length; i++) {
      ShortMessage note = new ShortMessage();
      note.setMessage(ShortMessage.NOTE_ON, channel, notes[i] + shift, 90);
      receiver.send(note, 1);
      Thread.sleep(5);
    }
    Thread.sleep(ms);
    for (int i = 0; i < notes.length; i++) {
      ShortMessage note = new ShortMessage();
      note.setMessage(ShortMessage.NOTE_OFF, channel, notes[i] + shift, 0);
      receiver.send(note, 1);
    }

  }

  public static void playNoteons(Receiver receiver, int channel, int[] notes, int shift, boolean on) throws InvalidMidiDataException, InterruptedException {
    for (int i = 0; i < notes.length; i++) {
      ShortMessage note = new ShortMessage();
      note.setMessage(ShortMessage.NOTE_ON, channel, notes[i] + shift, on ? 90 : 0);
      receiver.send(note, 1);
      Thread.sleep(50);
    }
  }

  public static void noteoff(Receiver receiver) {
    for (int i = 0; i < 16; i++) {
      try {
        ShortMessage note;
        note = new ShortMessage();
        note.setMessage(ShortMessage.CONTROL_CHANGE, i, 120, 0);
        receiver.send(note, 1);
        note = new ShortMessage();
        note.setMessage(ShortMessage.CONTROL_CHANGE, i, 123, 0);
      } catch (InvalidMidiDataException ex) {
        Logger.getLogger(MidiUtil.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  }

  public static void main(String[] args) throws Exception {
    printEnv(System.out);

    /*MidiDevice out_dev = getMidiDevice("ARIA Player", true);
    out_dev.open();
    Receiver out = out_dev.getReceiver();
    printMidiDeviceInfo(out_dev.getDeviceInfo(), System.out, 0);
    out.send(new ShortMessage(ShortMessage.NOTE_ON, 0, 60, 120), -1);
    Thread.sleep(1000);
    out.send(new ShortMessage(ShortMessage.NOTE_ON, 0, 60, 0), -1);
    Thread.sleep(1000);
    */
    //playMidi(new File("/Users/minaberger/mywork/sonat_2c.mid"), new String[]{"ARIA Player"});
  }

  public static void playMidi(File midiFile, String[] ports) throws Exception {
    Sequence sequence = null;
    try {
      sequence = MidiSystem.getSequence(midiFile);
    } catch (InvalidMidiDataException e) {
      throw e;
    } catch (IOException e) {
      throw e;
    }
    playMidi(sequence, ports);
  }

  public static Sequencer getSequencer() throws MidiUnavailableException {
    RealTimeSequencer sequencer = null;
    try {
      //sequencer = MidiSystem.getSequencer();
      sequencer = new RealTimeSequencer();
    } catch (MidiUnavailableException e) {
      throw e;
    }
    if (sequencer == null) {
      throw new MidiUnavailableException("can't get a Sequencer");
    }
    try {
      sequencer.open();
    } catch (MidiUnavailableException e) {
      throw e;
    }
    return sequencer;
  }

  public static void playMidi(Sequence sequence, String[] ports) throws Exception {
    Sequencer sequencer = getSequencer();
    try {
      sequencer.setSequence(sequence);
    } catch (InvalidMidiDataException e) {
      throw e;
    }
    System.out.println("[Sequencer] " + sequencer.getClass().getName());
    //printMidiDeviceInfo(sequencer.getDeviceInfo(), System.out, 1);
    MidiDevice[] devices = new MidiDevice[ports.length];
    try {
      for (int i = 0; i < ports.length; i++) {
        devices[i] = getMidiDevice(ports[i], true);
        Transmitter seqTransmitter = sequencer.getTransmitter();
        Receiver receiver = devices[i].getReceiver();
        noteoff(receiver);
        seqTransmitter.setReceiver(receiver);
      }
    } catch (MidiUnavailableException e) {
      e.printStackTrace();
      throw e;
    }
    //System.out.println(sequencer.getReceivers().size());
    //System.out.println(sequencer.getTransmitters().size());

    //sequencer.getTransmitter();
    //System.out.println(sequencer.getTransmitters().size());
    System.out.println("start");
    //System.out.println("[Synthesizer] " + device1.getClass().getName());
    //printMidiDeviceInfo(device1.getDeviceInfo(), System.out, 1);
    //sequencer.addMetaEventListener(new EndOfTrack(sequencer, synthesizer));
    sequencer.start();
  }

  public static MidiDevice getMidiDevice(String strDeviceName, boolean output) {
    MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
    for (int i = 0; i < infos.length; i++) {
      if (!infos[i].getName().equals(strDeviceName)) {
        continue;
      }
      try {
        MidiDevice device = MidiSystem.getMidiDevice(infos[i]);
        System.out.println(device.getDeviceInfo().getName() + ":"
          + "out:" + device.getMaxReceivers() + "in:" + device.getMaxTransmitters());
        if (!output && device.getDeviceInfo().getName().contains("PC-50 MIDI OUT")) {
          device.open();
          return device;
        }
        //if ((!output && device.getMaxTransmitters() != 0)
        //        || (output && device.getMaxReceivers() != 0)) {
        device.open();
        return device;
        //}
      } catch (MidiUnavailableException mue) {
      }
    }
    return null;
  }

  public static void printEnv(PrintStream out) {
    MidiDevice.Info[] mdis = MidiSystem.getMidiDeviceInfo();
    out.println("MidiDevice.Info");
    for (int i = 0; i < mdis.length; i++) {
      out.println(" MidiDevice.Info[" + i + "]");
      printMidiDeviceInfo(mdis[i], out, 2);
    }
    int[] midi_file_type = MidiSystem.getMidiFileTypes();
    out.print("Midi File Type:");
    for (int i = 0; i < midi_file_type.length; i++) {
      out.print(midi_file_type[i] + " ");
    }
    out.println("");

  }

  public static void printMidiDeviceInfo(MidiDevice.Info mdi, PrintStream out, int indent) {
    String head = "";
    while (head.length() < indent) {
      head += " ";
    }
    out.println(head + "       Name : " + mdi.getName());
    out.println(head + "    Version : " + mdi.getVersion());
    out.println(head + "     Vendor : " + mdi.getVendor());
    out.println(head + "Description : " + mdi.getDescription());
    //MidiDevice md = MidiSystem.getMidiDevice(mdi);
  }

  public static void __main(String[] args) throws MidiUnavailableException, InvalidMidiDataException, InterruptedException {
    printEnv(System.out);
    Receiver receiver1 = null;
    try {
      MidiDevice device1 = getMidiDevice("ARIA Player", true);
      //MidiDevice device1 = MidiSystem.getMidiDevice("Game Controller 1");
      printMidiDeviceInfo(device1.getDeviceInfo(), System.out, 1);
      receiver1 = device1.getReceiver();
      int channel = 0;
      int[] notes = new int[]{24, 60, 64, 67, 70};
      playNoteons(receiver1, channel, notes, 0, true);
      Thread.sleep(1000);
      playNoteons(receiver1, channel, notes, 0, false);

    } finally {
      if (receiver1 != null) {
        receiver1.close();
      }
    }
  }

}
