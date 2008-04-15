package saicontella.core;

/**
 * SaiconTella project
 *
 * @author Marios Karagiannopoulos (mariosk@gmail.com)
 *
 * February 2008
 */

import phex.gui.common.MainFrame;
import phex.gui.common.GUIRegistry;
import phex.gui.prefs.InterfacePrefs;
import phex.utils.Localizer;

import javax.swing.*;
import java.io.File;

import saicontella.phex.stwizard.STConfigurationWizardDialog;

public class STMainProgram {

    private static void removePhexFile(String filename) {
        File f = new File(filename);
        if (f.exists())
            f.delete();
    }

    public static void main(String[] args) {
        // temporary placeholder fix?...
        removePhexFile("./SaiconNetwork_udphostcache.cfg");
        removePhexFile("./phex_SaiconNetwork.hosts");
        //removePhexFile("./phexCorePrefs.properties");
        //removePhexFile("./phexCorePrefs.properties.bak");

        STLibrary sLibrary = STLibrary.getInstance();
        STMainForm mainFrame = new STMainForm(sLibrary, null);
        mainFrame.createUIComponents();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
    }

}
