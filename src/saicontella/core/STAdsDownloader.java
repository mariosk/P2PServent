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
import javax.swing.text.html.HTMLEditorKit;
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
        ImageIcon imageIconIShare1 = null;
        ImageIcon imageIconIShare2 = null;
        try {
            STHTMLParser parser = new STHTMLParser();

            parser.runCallback(STLibrary.STConstants.ADS_WEB_SERVER_URL);
            imageIconMainForm = parser.getImageIcon();
            if (imageIconMainForm != null)
                this.mainForm.setAdImageLabelIcon(imageIconMainForm, parser.getLinkURL());

            parser.runCallback(STLibrary.STConstants.ADS_MY_FRIENDS_URL);
            imageIconMyFriends = parser.getImageIcon();
            if (imageIconMyFriends != null)
                this.mainForm.setMyFriendsAdImageLabelIcon(imageIconMyFriends, parser.getLinkURL());

            parser.runCallback(STLibrary.STConstants.ISHARE_LOGO_URL1);
            imageIconIShare1 = parser.getImageIcon();
            if (imageIconIShare1 != null)
                this.mainForm.setIShareImageLabelIcon1(imageIconIShare1, parser.getLinkURL());

            parser.runCallback(STLibrary.STConstants.ISHARE_LOGO_URL2);
            imageIconIShare2 = parser.getImageIcon();
            if (imageIconIShare2 != null)
                this.mainForm.setIShareImageLabelIcon2(imageIconIShare2, parser.getLinkURL());             

        } catch (Exception e) {
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
