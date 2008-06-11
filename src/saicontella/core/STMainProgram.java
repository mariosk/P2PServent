package saicontella.core;

/**
 * SaiconTella project
 *
 * @author Marios Karagiannopoulos (mariosk@gmail.com)
 *
 * February 2008
 */

import javax.swing.*;

public class STMainProgram {

    public static void main(String[] args) {
        STLibrary sLibrary = STLibrary.getInstance();
        sLibrary.removePhexFile(sLibrary.getApplicationLocalPath() + "/phex_SaiconNetwork.hosts");
        sLibrary.removePhexFile(sLibrary.getApplicationLocalPath() + "/phex.log");
        sLibrary.removePhexFile(sLibrary.getApplicationLocalPath() + "/phex.error.log");
        STMainForm mainFrame = new STMainForm(sLibrary, null);
        mainFrame.createUIComponents();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
