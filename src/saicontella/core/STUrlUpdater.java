package saicontella.core;

import chrriis.dj.nativeswing.components.JWebBrowser;
import chrriis.dj.nativeswing.components.JFlashPlayer;

public class STUrlUpdater implements Runnable {

    private JFlashPlayer browser;
    private String url;

    public STUrlUpdater(JFlashPlayer b, String location) {
        browser = b;
        url = location;
    }

    public void run() {
        browser.load(url);
    }
}
