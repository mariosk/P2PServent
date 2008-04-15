package saicontella.core;

/**
 * SaiconTella project
 *
 * @author Marios Karagiannopoulos (mariosk@gmail.com)
 *
 * March 2008
 */

import saicontella.core.webservices.ResponseSTATUS;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.swing.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File;
import java.awt.*;

public class STAdsDownloader extends Thread {
    private static Log logger = LogFactory.getLog("saicontella/core/STAdsDownloader");
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

    private void retrieveFromWebServer(String absPathObject, String adImageName) {

        FileOutputStream fos = null;

        try {

          method.setURI(new URI(absPathObject, true));
          int returnCode = client.executeMethod(method);

          if(returnCode != HttpStatus.SC_OK) {
            logger.error("Unable to fetch: " + absPathObject + ", status code: " + returnCode);
            return;
          }

          //logger.error(method.getResponseBodyAsString());

          byte[] imageData = method.getResponseBody();
          fos = new FileOutputStream(new File(adImageName));
          fos.write(imageData);

        }
        catch (HttpException he)
        {
          logger.error(he);
        }
        catch (IOException ie)
        {
          logger.error(ie);
        }
        finally
        {
          method.releaseConnection();
          if(fos != null) try { fos.close(); } catch (Exception fe) {}
        }

        final ImageIcon imageIcon = new ImageIcon("adImage.gif");
		Image image = imageIcon.getImage();
		final Dimension dimension = new Dimension(100, 100);
		final double height = dimension.getHeight();
		final double width = (height / imageIcon.getIconHeight()) * imageIcon.getIconWidth();
		image = image.getScaledInstance((int)width, (int)height, Image.SCALE_SMOOTH);
		final ImageIcon finalIcon = new ImageIcon(image);
		this.mainForm.getImageLabel().setIcon(finalIcon);
    }

    public void run() {
        // infinite loop connection towards web server
        while (true) {
            try {
                String urlPath = STLibrary.getInstance().getSTConfiguration().getAdsServer();
                this.retrieveFromWebServer(urlPath, "adImage.gif");
                sleep((int)(STLibrary.STConstants.ADS_THR_SECS * 1000));
            } catch (Exception e) {
                e.printStackTrace();
            }
            logger.debug("[" + getName() + "]: Sleeping until the next retrieval of the advertisements...");
        }
    }

    
}
