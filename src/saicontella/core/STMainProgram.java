package saicontella.core;

/**
 * SaiconTella project
 *
 * @author Marios Karagiannopoulos (mariosk@gmail.com)
 *
 * February 2008
 */

import phex.gui.common.PhexColors;

import javax.swing.*;
import java.awt.*;
import java.io.File;

import saicontella.phex.stwizard.STConfigurationWizardDialog;

public class STMainProgram {

    public static void main(String[] args) {                
        STLibrary sLibrary = STLibrary.getInstance();
        sLibrary.removePhexFile(sLibrary.getApplicationLocalPath() + "/phex_SaiconNetwork.hosts");
        sLibrary.removePhexFile(sLibrary.getApplicationLocalPath() + "/phex.log");
        sLibrary.removePhexFile(sLibrary.getApplicationLocalPath() + "/phex.error.log");
        STLoginDialog loginDlg = new STLoginDialog();        
    }
}
