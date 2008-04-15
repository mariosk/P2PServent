===================================================
Author: Marios Karagiannopoulos {mariosk@gmail.com}
Date:	4 April 2008
===================================================

Steps to make an installable P2PServent for Windows.

1. Copy all the libs from dir $SRC/lib to $SRC/release/lib

2. Build the project using IntelliJ IDEA

3. Copy all the class files from $SRC/out/production/P2PServent to $SRC/release/P2PServent.jar

4. Use JSmooth (http://jsmooth.sourceforge.net/) to open the $SRC/release/p2pservent.jsmooth project

5. Compile the jsmooth project (You should have a new P2PServent.exe in the $SRC/release directory)

6. Use MAKENSISW tool of nullsoft installer to create the Setup.exe inside the $SRC/release directory

7. Open and compile the p2pservent.nsi template;

You may change the following variables:

  !define VERSION "0.6"
  !define RELEASE_DIR "C:\DISK_F\p2p_saicon\P2PServent\release"
  !define SRC_DIR "C:\DISK_F\p2p_saicon\P2PServent"

8. If you need to make a debug version of P2PServent:

Just uncomment out the following line from the log4j.properties (inside P2PServent.jar):

log4j.rootCategory=DEBUG, LOGFILE

========================================================================================

References:

[1] Java Installer tools: http://www.java2s.com/Product/Java/Development/Installer.htm

[2] NSIS (Nullsoft Scriptable Install System) 
    http://nsis.sourceforge.net/Main_Page

[3] JSmooth is a Java Executable Wrapper. It creates native Windows launchers (standard .exe) for your java applications.
    http://jsmooth.sourceforge.net/