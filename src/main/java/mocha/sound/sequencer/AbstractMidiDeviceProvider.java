package mocha.sound.sequencer;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.spi.MidiDeviceProvider;

public abstract class AbstractMidiDeviceProvider extends MidiDeviceProvider {

  private static final boolean enabled;

  static {
    Platform.initialize();
    enabled = Platform.isMidiIOEnabled();
  }

  synchronized void readDeviceInfos() {
    Info[] infos = getInfoCache();
    MidiDevice[] devices = getDeviceCache();
    if (!enabled) {
      if (infos == null || infos.length != 0) {
        setInfoCache(new Info[0]);
      }
      if (devices == null || devices.length != 0) {
        setDeviceCache(new MidiDevice[0]);
      }
      return;
    }

    int oldNumDevices = (infos == null) ? -1 : infos.length;
    int newNumDevices = getNumDevices();
    if (oldNumDevices != newNumDevices) {
      Info[] newInfos = new Info[newNumDevices];
      MidiDevice[] newDevices = new MidiDevice[newNumDevices];

      for (int i = 0; i < newNumDevices; i++) {
        Info newInfo = createInfo(i);
        if (infos != null) {
          for (int ii = 0; ii < infos.length; ii++) {
            Info info = infos[ii];
            if (info != null && info.equalStrings(newInfo)) {
              // new info matches the still existing info. Use old one
              newInfos[i] = info;
              info.setIndex(i);
              infos[ii] = null; // prevent re-use
              newDevices[i] = devices[ii];
              devices[ii] = null;
              break;
            }
          }
        }
        if (newInfos[i] == null) {
          newInfos[i] = newInfo;
        }
      }
      if (infos != null) {
        for (int i = 0; i < infos.length; i++) {
          if (infos[i] != null) {
            infos[i].setIndex(-1);
          }
        }
      }
      setInfoCache(newInfos);
      setDeviceCache(newDevices);
    }
  }

  @Override
  public MidiDevice.Info[] getDeviceInfo() {
    readDeviceInfos();
    Info[] infos = getInfoCache();
    MidiDevice.Info[] localArray = new MidiDevice.Info[infos.length];
    System.arraycopy(infos, 0, localArray, 0, infos.length);
    return localArray;
  }

  @Override
  public MidiDevice getDevice(MidiDevice.Info info) {
    if (info instanceof Info) {
      readDeviceInfos();
      MidiDevice[] devices = getDeviceCache();
      Info[] infos = getInfoCache();
      Info thisInfo = (Info) info;
      int index = thisInfo.getIndex();
      if (index >= 0 && index < devices.length && infos[index] == info) {
        if (devices[index] == null) {
          devices[index] = createDevice(thisInfo);
        }
        if (devices[index] != null) {
          return devices[index];
        }
      }
    }

    throw new IllegalArgumentException("not supported : " + info.toString());
  }
  static class Info extends MidiDevice.Info {

    private int index;

    Info(String name, String vendor, String description, String version, int index) {
      super(name, vendor, description, version);
      this.index = index;
    }

    boolean equalStrings(Info info) {
      return (info != null
        && getName().equals(info.getName())
        && getVendor().equals(info.getVendor())
        && getDescription().equals(info.getDescription())
        && getVersion().equals(info.getVersion()));
    }

    int getIndex() {
      return index;
    }

    void setIndex(int index) {
      this.index = index;
    }

  }

  abstract int getNumDevices();

  abstract MidiDevice[] getDeviceCache();

  abstract void setDeviceCache(MidiDevice[] devices);

  abstract Info[] getInfoCache();

  abstract void setInfoCache(Info[] infos);

  abstract Info createInfo(int index);

  abstract MidiDevice createDevice(Info info);
}
