package mocha.sound.sequencer;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.util.Properties;

import java.security.AccessController;
import java.security.PrivilegedAction;

import javax.sound.sampled.AudioPermission;

class JSSecurityManager {

  private JSSecurityManager() {
  }

  private static boolean hasSecurityManager() {
    return (System.getSecurityManager() != null);
  }

  static void checkRecordPermission() throws SecurityException {
    SecurityManager sm = System.getSecurityManager();
    if (sm != null) {
      sm.checkPermission(new AudioPermission("record"));
    }
  }

  static void loadLibrary(final String libName) {
    try {
      if (hasSecurityManager()) {
        PrivilegedAction action = new PrivilegedAction() {
          @Override
          public Object run() {
            System.loadLibrary(libName);
            return null;
          }
        };
        AccessController.doPrivileged(action);
      } else {
        System.loadLibrary(libName);
      }
    } catch (UnsatisfiedLinkError e) {
      e.printStackTrace();
      throw (e);
    }
  }

  static String getProperty(final String propertyName) {
    String propertyValue;
    if (hasSecurityManager()) {
      try {
        PrivilegedAction action = new PrivilegedAction() {
          @Override
          public Object run() {
            try {
              return System.getProperty(propertyName);
            } catch (Throwable t) {
              return null;
            }
          }
        };
        propertyValue = (String) AccessController.doPrivileged(action);
      } catch (Exception e) {
        e.printStackTrace();
        propertyValue = System.getProperty(propertyName);
      }
    } else {
      propertyValue = System.getProperty(propertyName);
    }
    return propertyValue;
  }

  static void loadProperties(final Properties properties, final String filename) {
    if (hasSecurityManager()) {
      try {
        // invoke the privileged action using 1.2 security
        PrivilegedAction action = new PrivilegedAction() {
          @Override
          public Object run() {
            loadPropertiesImpl(properties, filename);
            return null;
          }
        };
        AccessController.doPrivileged(action);
      } catch (Exception e) {
        e.printStackTrace();
        loadPropertiesImpl(properties, filename);
      }
    } else {
      // not JDK 1.2 security, assume we already have permission
      loadPropertiesImpl(properties, filename);
    }
  }

  private static void loadPropertiesImpl(Properties properties, String filename) {
    String fname = System.getProperty("java.home");
    try {
      if (fname == null) {
        throw new Error("no java.home");
      }
      File f = new File(fname, "lib");
      f = new File(f, filename);
      fname = f.getCanonicalPath();
      InputStream in = new FileInputStream(fname);
      BufferedInputStream bin = new BufferedInputStream(in);
      try {
        properties.load(bin);
      } finally {
        if (in != null) {
          in.close();
        }
      }
    } catch (IOException | Error t) {
      System.err.println("Could not load properties file \"" + fname + "\"");
      t.printStackTrace();
    }
  }

  /*private static ThreadGroup getTopmostThreadGroup() {
    ThreadGroup topmostThreadGroup;
    if (hasSecurityManager()) {
      try {
        // invoke the privileged action using 1.2 security
        PrivilegedAction action = new PrivilegedAction() {
          public Object run() {
            try {
              return getTopmostThreadGroupImpl();
            } catch (Throwable t) {
              return null;
            }
          }
        };
        topmostThreadGroup = (ThreadGroup) AccessController.doPrivileged(action);
        if (Printer.debug) {
          Printer.debug("Got topmost thread group with JDK 1.2 security");
        }
      } catch (Exception e) {
        if (Printer.debug) {
          Printer.debug("Exception getting topmost thread group with JDK 1.2 security");
        }
        // try without using JDK 1.2 security
        topmostThreadGroup = getTopmostThreadGroupImpl();
      }
    } else {
      // not JDK 1.2 security, assume we already have permission
      topmostThreadGroup = getTopmostThreadGroupImpl();
    }
    return topmostThreadGroup;
  }*/

  private static ThreadGroup getTopmostThreadGroupImpl() {
    ThreadGroup g = Thread.currentThread().getThreadGroup();
    while ((g.getParent() != null) && (g.getParent().getParent() != null)) {
      g = g.getParent();
    }
    return g;
  }

  /**
   * Create a Thread in the topmost ThreadGroup.
   */
  static Thread createThread(final Runnable runnable,
    final String threadName,
    final boolean isDaemon, final int priority,
    final boolean doStart) {
    Thread thread = null;
    if (hasSecurityManager()) {
      PrivilegedAction action = new PrivilegedAction() {
        @Override
        public Object run() {
          try {
            return createThreadImpl(runnable, threadName,
              isDaemon, priority,
              doStart);
          } catch (Throwable t) {
            t.printStackTrace();
            return null;
          }
        }
      };
      thread = (Thread) AccessController.doPrivileged(action);
    } else {
      thread = createThreadImpl(runnable, threadName, isDaemon, priority, doStart);
    }
    return thread;
  }

  private static Thread createThreadImpl(Runnable runnable,
    String threadName,
    boolean isDaemon, int priority,
    boolean doStart) {
    ThreadGroup threadGroup = getTopmostThreadGroupImpl();
    Thread thread = new Thread(threadGroup, runnable);
    if (threadName != null) {
      thread.setName(threadName);
    }
    thread.setDaemon(isDaemon);
    if (priority >= 0) {
      thread.setPriority(priority);
    }
    if (doStart) {
      thread.start();
    }
    return thread;
  }


  /*static List getProviders(final Class providerClass) {
        PrivilegedAction action = new PrivilegedAction() {
                public Object run() {
                    List p = new ArrayList();
                    Iterator ps = Service.providers(providerClass);
                    while (ps.hasNext()) {
                        try {
                            Object provider = ps.next();
                            if (providerClass.isInstance(provider)) {
                                // $$mp 2003-08-22
                                // Always adding at the beginning reverses the
                                // order of the providers. So we no longer have
                                // to do this in AudioSystem and MidiSystem.
                                p.add(0, provider);
                            }
                        } catch (Throwable t) {
                            //$$fb 2002-11-07: do not fail on SPI not found
                            if (Printer.err) t.printStackTrace();
                        }                                                                  }
                    return p;
                }
            };
        List providers = (List) AccessController.doPrivileged(action);
        return providers;
    }*/
}
