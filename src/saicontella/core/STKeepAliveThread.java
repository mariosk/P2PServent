package saicontella.core;

/**
 * SaiconTella project
 *
 * @author Marios Karagiannopoulos (mariosk@gmail.com)
 *
 * February 2008
 */

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import saicontella.core.webservices.authentication.LoginResponseWrapper;
import saicontella.core.webservices.authentication.ResponseSTATUS;

public class STKeepAliveThread extends Thread {

    private LoginResponseWrapper proxyResponse;
    private static Log logger = LogFactory.getLog("saicontella.core.STKeepAliveThread");

    public STKeepAliveThread(LoginResponseWrapper proxyResponse) {
        super(STLibrary.STConstants.KEEP_ALIVE_THR_NAME);
        this.proxyResponse = proxyResponse;
    }

    public void run() {
        // infinite loop connection towards web service
        while (true) {
            try {
                proxyResponse.setStatus(ResponseSTATUS.VALID);
                logger.debug("User sessionId: " + proxyResponse.getSessionId() + " status: " + proxyResponse.getStatus().toString());
                STLibrary.getInstance().reachAllOnlinePeers(proxyResponse);
                STLibrary.getInstance().getGnutellaFramework().connectToPeers(STLibrary.getInstance().getPeersList());
                sleep((int)(STLibrary.STConstants.KEEP_ALIVE_THR_SECS * 1000));
            } catch (Exception e) {
                e.printStackTrace();
            }            
            logger.debug("[" + getName() + "]: Sleeping until the next keep alive connection towards webservice...");
        }
    }
}
