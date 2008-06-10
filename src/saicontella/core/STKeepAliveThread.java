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

import java.util.Random;

public class STKeepAliveThread extends Thread {

    private LoginResponseWrapper proxyResponse;
    private static Log logger = LogFactory.getLog("saicontella.core.STKeepAliveThread");    
    private Random rnd;
    private int pollingPeriod;

    public STKeepAliveThread(LoginResponseWrapper proxyResponse) {
        super(STLibrary.STConstants.KEEP_ALIVE_THR_NAME);
        this.proxyResponse = proxyResponse;
        this.rnd = new Random(System.currentTimeMillis() + 123456789);
        this.pollingPeriod = Math.abs(this.rnd.nextInt(STLibrary.STConstants.KEEP_ALIVE_THR_SECS)) + 5;
        //this.pollingPeriod = STLibrary.STConstants.KEEP_ALIVE_THR_SECS;
    }

    public void run() {
        // infinite loop connection towards web service
        try {
            while (true) {
                proxyResponse.setStatus(ResponseSTATUS.VALID);
                logger.debug("User sessionId: " + proxyResponse.getSessionId() + " status: " + proxyResponse.getStatus().toString());
                STLibrary.getInstance().reachAllOnlinePeers(proxyResponse);
                STLibrary.getInstance().getGnutellaFramework().connectToPeers(STLibrary.getInstance().getPeersList());
                sleep(this.pollingPeriod * 1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.debug("[" + getName() + "]: Sleeping until the next keep alive connection towards webservice...");
    }
}
