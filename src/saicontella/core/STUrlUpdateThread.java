package saicontella.core;

import chrriis.dj.nativeswing.components.*;

import javax.swing.SwingUtilities;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class STUrlUpdateThread implements Runnable {

    private static Log logger = LogFactory.getLog("saicontella.core.STUrlUpdateThread");
    private JFlashPlayer player;
    private boolean run;
    private String URL;

    public STUrlUpdateThread(JFlashPlayer p, String URL) {
        player = p;       
        this.URL = URL;
        run = true;
    }

    //@Override
    public void commandReceived(WebBrowserEvent e, String command, String[] args) {
        System.out.println("command="+command);
    }

    public void run() {
        while (run) {
            try {
                //STUrlUpdater updater = new STUrlUpdater(browser, "http://www.google.gr/search?hl=el&q=" + counter + "&btnG=%C1%ED%E1%E6%DE%F4%E7%F3%E7+Google&meta=");
                STUrlUpdater updater = new STUrlUpdater(player, this.URL);
                SwingUtilities.invokeLater(updater);
                logger.debug("Sleeping before the next retrieval of advertisements! [" + this.URL + "]");
                Thread.sleep((int)(STLibrary.STConstants.ADS_THR_SECS * 1000));
            } catch (InterruptedException ex) {

            }
        }
    }

    void stop() {
        run = false;
    }
}
