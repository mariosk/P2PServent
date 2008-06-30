package saicontella.core;

import java.awt.*;

/**
 * SaiconTella project
 *
 * @author Marios Karagiannopoulos (mariosk@gmail.com)
 *
 * February 2008
 */

public class STMainProgram {

    public static void main(String[] args) {
        STLibrary sLibrary = STLibrary.getInstance();
        sLibrary.removePhexFile(sLibrary.getApplicationLocalPath() + "/phex_SaiconNetwork.hosts");
        sLibrary.removePhexFile(sLibrary.getApplicationLocalPath() + "/phex.log");
        sLibrary.removePhexFile(sLibrary.getApplicationLocalPath() + "/phex.error.log");
        STLoginDialog loginDlg = new STLoginDialog();        
    }
}
