package saicontella.core;

/**
 * SaiconTella project
 *
 * @author Marios Karagiannopoulos (mariosk@gmail.com)
 *
 * March 2008
 */

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.swing.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File;
import java.awt.*;
import java.net.URL;
import java.net.MalformedURLException;

public class STAdsDownloader extends Thread {
    private static Log logger = LogFactory.getLog("saicontella.core.STAdsDownloader");
    private GetMethod method;
    private HttpClient client;
    private STMainForm mainForm;

    public STAdsDownloader(STMainForm mainForm) {
        super(STLibrary.STConstants.ADS_THR_NAME);
        this.client = new HttpClient();
        client.getParams().setParameter("http.useragent", STLibrary.STConstants.ADS_USER_AGENT);
        client.getParams().setParameter("http.connection.timeout",new Integer(STLibrary.STConstants.ADS_WEB_SERVER_TIMEOUT*1000));
        this.method  = new GetMethod();
        this.mainForm = mainForm;
    }

    private void fetchImage(String absPathObject) {        
        ImageIcon imageIcon = null;
        try {
            imageIcon = new ImageIcon(new URL(absPathObject));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        this.mainForm.getAdImageLabel().setIcon(imageIcon);
        this.mainForm.getAdImageLabel().repaint();
    }

    public void run() {
        // infinite loop connection towards web server
        while (true) {
            try {                
                this.fetchImage(STLibrary.STConstants.ADS_WEB_SERVER_URL);
                sleep((int)(STLibrary.STConstants.ADS_THR_SECS * 1000));
            } catch (Exception e) {
                e.printStackTrace();
            }
            logger.debug("[" + getName() + "]: Sleeping until the next retrieval of the advertisements...");
        }
    }

    
}
