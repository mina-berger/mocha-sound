package mocha.sound.sequencer;

import java.util.StringTokenizer;

class Platform {

  private static final String libNameMain = "jsound";
  private static final String libNameALSA = "jsoundalsa";
  private static final String libNameDSound = "jsoundds";

  public static final int LIB_MAIN = 1;
  public static final int LIB_ALSA = 2;
  public static final int LIB_DSOUND = 4;

  private static int loadedLibs = 0;

  public static final int FEATURE_MIDIIO = 1;
  public static final int FEATURE_PORTS = 2;
  public static final int FEATURE_DIRECT_AUDIO = 3;

  private static boolean signed8;

  private static boolean bigEndian;

  private static String javahome;

  private static String classpath;

  static {
    loadLibraries();
    readProperties();
  }

  private Platform() {
  }

  static void initialize() {

  }

  static boolean isBigEndian() {
    return bigEndian;
  }

  static boolean isSigned8() {
    return signed8;
  }

  static String getJavahome() {

    return javahome;
  }

  static String getClasspath() {
    return classpath;
  }

  private static void loadLibraries() {
    try {
      JSSecurityManager.loadLibrary(libNameMain);
      // just for the heck of it...
      loadedLibs |= LIB_MAIN;
    } catch (SecurityException e) {
      throw (e);
    }
    String extraLibs = nGetExtraLibraries();
    StringTokenizer st = new StringTokenizer(extraLibs);
    while (st.hasMoreTokens()) {
      String lib = st.nextToken();
      try {
        JSSecurityManager.loadLibrary(lib);
        if (lib.equals(libNameALSA)) {
          loadedLibs |= LIB_ALSA;
        } else if (lib.equals(libNameDSound)) {
          loadedLibs |= LIB_DSOUND;
        } else {
          System.err.println("Loaded unknown lib '" + lib + "' successfully.");
        }
      } catch (Throwable t) {
        System.err.println("Couldn't load library " + lib + ": " + t.toString());
      }
    }
  }

  static boolean isMidiIOEnabled() {
    return isFeatureLibLoaded(FEATURE_MIDIIO);
  }

  static boolean isPortsEnabled() {
    return isFeatureLibLoaded(FEATURE_PORTS);
  }

  static boolean isDirectAudioEnabled() {
    return isFeatureLibLoaded(FEATURE_DIRECT_AUDIO);
  }

  private static boolean isFeatureLibLoaded(int feature) {
    int requiredLib = nGetLibraryForFeature(feature);
    boolean isLoaded = (requiredLib != 0) && ((loadedLibs & requiredLib) == requiredLib);
    return isLoaded;
  }

  private native static boolean nIsBigEndian();

  private native static boolean nIsSigned8();

  private native static String nGetExtraLibraries();

  private native static int nGetLibraryForFeature(int feature);

  private static void readProperties() {
    bigEndian = nIsBigEndian();
    signed8 = nIsSigned8();
    javahome = JSSecurityManager.getProperty("java.home");
    classpath = JSSecurityManager.getProperty("java.class.path");
  }
}
