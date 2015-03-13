# Troubleshooting #

The following are some common issues that people have run into and their solutions follow.

## Can not run under Mac OSX ##
If you try to run YADFC(tm) under Mac OSX and you get something similar to:
```
5/12/09 11:02:50 PM [0x0-0x43043].com.apple.JarLauncher[404] Exception in thread "main"  
5/12/09 11:02:50 PM [0x0-0x43043].com.apple.JarLauncher[404] 
java.lang.UnsupportedClassVersionError: Bad version number in .class file 
5/12/09 11:02:50 PM [0x0-0x43043].com.apple.JarLauncher[404]  at 
java.lang.ClassLoader.defineClass1(Native Method) 
5/12/09 11:02:50 PM [0x0-0x43043].com.apple.JarLauncher[404]  at 
java.lang.ClassLoader.defineClass(ClassLoader.java:675) 
5/12/09 11:02:50 PM [0x0-0x43043].com.apple.JarLauncher[404]  at 
java.security.SecureClassLoader.defineClass(SecureClassLoader.java:124) 
5/12/09 11:02:50 PM [0x0-0x43043].com.apple.JarLauncher[404]  at 
java.net.URLClassLoader.defineClass(URLClassLoader.java:260) 
5/12/09 11:02:50 PM [0x0-0x43043].com.apple.JarLauncher[404]  at 
java.net.URLClassLoader.access$100(URLClassLoader.java:56) 
5/12/09 11:02:50 PM [0x0-0x43043].com.apple.JarLauncher[404]  at 
java.net.URLClassLoader$1.run(URLClassLoader.java:195) 
5/12/09 11:02:50 PM [0x0-0x43043].com.apple.JarLauncher[404]  at 
java.security.AccessController.doPrivileged(Native Method) 
5/12/09 11:02:50 PM [0x0-0x43043].com.apple.JarLauncher[404]  at 
java.net.URLClassLoader.findClass(URLClassLoader.java:188) 
5/12/09 11:02:50 PM [0x0-0x43043].com.apple.JarLauncher[404]  at 
java.lang.ClassLoader.loadClass(ClassLoader.java:316) 
5/12/09 11:02:50 PM [0x0-0x43043].com.apple.JarLauncher[404]  at 
sun.misc.Launcher$AppClassLoader.loadClass(Launcher.java:280) 
5/12/09 11:02:50 PM [0x0-0x43043].com.apple.JarLauncher[404]  at 
java.lang.ClassLoader.loadClass(ClassLoader.java:251) 
5/12/09 11:02:50 PM [0x0-0x43043].com.apple.JarLauncher[404]  at 
java.lang.ClassLoader.loadClassInternal(ClassLoader.java:374) 
```

Under OS X 10.5 Java 6 SE 1.0.6\_07, wouldn't launch via standard double-click (which activates the JAR Launcher app). To fix:

  1. Open /Applications/Utilities/Java/Java Preferences.
  1. From the Java Preferences menu, select Preferences.
  1. In each list, drag Java 6 SE to the top to make it the preferred version.
  1. Return to the Finder and run the DF calculator .jar file normally.

A special thanks to Joram from the Legends of Zork forums for helping out here!