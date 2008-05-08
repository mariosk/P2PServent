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
        //removePhexFile("./SaiconNetwork_udphostcache.cfg");
        //removePhexFile("./phex_SaiconNetwork.hosts");
        STLibrary sLibrary = STLibrary.getInstance();
        STMainForm mainFrame = new STMainForm(sLibrary, null);
        mainFrame.createUIComponents();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
    }
}
