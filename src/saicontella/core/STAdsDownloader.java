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

    private void fetchImages() {        
        ImageIcon imageIconMainForm = null;
        ImageIcon imageIconMyFriends = null;
        ImageIcon imageIconIShare = null;
        try {
            imageIconMainForm = new ImageIcon(new URL(STLibrary.STConstants.ADS_WEB_SERVER_URL));
            this.mainForm.setAdImageLabelIcon(imageIconMainForm);
            imageIconMyFriends = new ImageIcon(new URL(STLibrary.STConstants.ADS_MY_FRIENDS_URL));
            this.mainForm.setMyFriendsAdImageLabelIcon(imageIconMyFriends);
            imageIconIShare = new ImageIcon(new URL(STLibrary.STConstants.ISHARE_LOGO_URL));
            this.mainForm.setIShareImageLabelIcon(imageIconIShare);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        // infinite loop connection towards web server
        while (true) {
            try {
                this.fetchImages();
                sleep((int)(STLibrary.STConstants.ADS_THR_SECS * 1000));
            } catch (Exception e) {
                e.printStackTrace();
            }
            logger.debug("[" + getName() + "]: Sleeping until the next retrieval of the advertisements...");
        }
    }

    
}
