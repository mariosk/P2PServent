package saicontella.core.gnutella;

/** 
 * SaiconTella project
 *
 * @author Marios Karagiannopoulos (mariosk@gmail.com)
 * 
 * February 2008
 */

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import phex.common.ManagerController;
import phex.common.ThreadTracking;
import phex.common.Environment;
import phex.common.EnvironmentConstants;
import phex.common.format.TimeFormatUtils;
import phex.common.address.AddressUtils;
import phex.common.address.DestAddress;
import phex.common.address.IpAddress;
import phex.common.address.LocalServentAddress;
import phex.connection.OutgoingConnectionDispatcher;
import phex.gui.prefs.InterfacePrefs;
import phex.gui.prefs.PhexGuiPrefs;
import phex.gui.common.GUIRegistry;
import phex.net.repres.PresentationManager;
import phex.prefs.core.*;
import phex.servent.Servent;
import phex.servent.OnlineStatus;
import phex.utils.SystemProperties;
import phex.utils.Localizer;
import phex.query.Search;
import phex.query.SearchContainer;
import phex.host.Host;
import phex.host.HostStatus;

import java.io.File;
import java.util.Vector;
import java.util.ArrayList;

import saicontella.core.*;
import saicontella.core.webservices.authentication.ActiveSessionMiniWrapper;

import javax.swing.*;

public class STGnutellaFramework {
    private static Log logger = LogFactory.getLog("saicontella.core.gnutella.STGnutellaFramework");
    private static String SAICON_NETWORK = "SaiconNetwork";   
    private Servent servent;
    
    public Servent getServent() {
    	return this.servent;
    }

    public void restart(STLibrary sLibrary, boolean restart) {
        try {
            // This is the server side (Gnutella context listens local IP interface and port 6347)
            logger.info("Starting server side of gnutella servent");

            // This order of the methods execution should not be changed. It took me so long to figure it out ;)
            System.setProperty( SystemProperties.PHEX_CONFIG_PATH_SYSPROP, sLibrary.getApplicationLocalPath());
            logger.debug("Phex config dir: " + System.getProperty( SystemProperties.PHEX_CONFIG_PATH_SYSPROP ));
            File oldConfigFile = Environment.getInstance().getPhexConfigFile( EnvironmentConstants.OLD_CONFIG_FILE_NAME );
            logger.debug("Phex config file: " + oldConfigFile);

            // initializations here...
            PhexCorePrefs.init();
            PhexGuiPrefs.init();
            ProxyPrefs.init();
            InterfacePrefs.init();

            InterfacePrefs.ShowBetaUpdateNotification.set(false);
            
            // According to GregorK this will work only until the FirewallCheckTimer kicks in. The workaround is:
            // http://www.gnutellaforums.com/help-support/79786-lan-dowload-waiting-ignored-candidate.html#post303417
            ConnectionPrefs.HasConnectedIncomming.set(false);
            ConnectionPrefs.AutoConnectOnStartup.set(false);

            NetworkPrefs.CurrentNetwork.set(SAICON_NETWORK);
            MessagePrefs.UseExtendedOriginIpAddress.set(true);

            ConnectionPrefs.ForceToBeUltrapeer.set(true);
            ConnectionPrefs.AllowToBecomeUP.set(true);
            ConnectionPrefs.AcceptDeflateConnection.set(true);

            NetworkPrefs.ListeningPort.set(sLibrary.getSTConfiguration().getListenPort());
            NetworkPrefs.MaxConcurrentConnectAttempts.set(sLibrary.getSTConfiguration().getMaxConnections());
            NetworkPrefs.AllowChatConnection.set(true);
            NetworkPrefs.TcpConnectTimeout.set(sLibrary.getSTConfiguration().getConnTimeout() * 1000);

            DownloadPrefs.DestinationDirectory.set(sLibrary.getSTConfiguration().getCompleteFolder());
            DownloadPrefs.IncompleteDirectory.set(sLibrary.getSTConfiguration().getInCompleteFolder());
            DownloadPrefs.MaxDownloadsPerIP.set(sLibrary.getSTConfiguration().getMaxDownload());

            UploadPrefs.MaxUploadsPerIP.set(sLibrary.getSTConfiguration().getMaxUpload());
            ProxyPrefs.ForcedIp.set("");
            // IT IS VERY IMPORTANT TO NOT Prefs.Save() ANYTHING BEFORE THE GNUTELLA SERVENT START!!!

            // servent instantiations here...
            Localizer.initialize( InterfacePrefs.LocaleName.get() );
            ThreadTracking.initialize();
            this.servent = Servent.getInstance();
            this.servent.getLocalAddress().setPort(sLibrary.getSTConfiguration().getListenPort());

            // initializations that need the servent instance here...
            if (!sLibrary.getSTConfiguration().getListenAddress().equals("*")) {
                DestAddress localAddress = AddressUtils.parseAndValidateAddress(sLibrary.getSTConfiguration().getListenAddress(), true, servent.getSecurityService());
                LocalServentAddress serventAddr = (LocalServentAddress)servent.getLocalAddress();
                serventAddr.setForcedHostIP(localAddress.getIpAddress());
                ProxyPrefs.ForcedIp.set(sLibrary.getSTConfiguration().getListenAddress());
            }

            this.servent.setOnlineStatus(OnlineStatus.ONLINE);
            ManagerController.initializeManagers();

            if (sLibrary.isConnected()) {
                if (restart)
                    this.servent.restartServer();
                else
                    this.servent.start();
                this.servent.getHostService().stop();
            }

            try
            {
                GUIRegistry.getInstance().initialize();
            }
            catch ( ExceptionInInitializerError ex )
            {
                ex.printStackTrace();
            }
            ManagerController.startupCompletedNotify();

            logger.debug("P2P network " + NetworkPrefs.CurrentNetwork.get() + " online status: " + this.servent.getOnlineStatus().isNetworkOnline());
            logger.debug("P2P network " + NetworkPrefs.CurrentNetwork.get() + " servent address: " + this.servent.getLocalAddress().getIpAddress() + ":" + servent.getLocalAddress().getPort());
            logger.debug(this.servent.getGnutellaNetwork().getNetworkGreeting());
            logger.debug(this.servent.getServentGuid());
        }
        catch (Exception ex) {
            sLibrary.fireMessageBox(STLocalizer.getString("GnutellaError") + ex.getMessage(), STLocalizer.getString("Error"), JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        	System.exit(-1);
        }               
    }
    
    public STGnutellaFramework(STLibrary sLibrary, boolean restart) {
        this.restart(sLibrary, restart);
    }

    public void setMaxUpload()
    {
        UploadPrefs.MaxUploadsPerIP.set(STLibrary.getInstance().getSTConfiguration().getMaxUpload());
    }

    public void setMaxDownload()
    {
        DownloadPrefs.MaxDownloadsPerIP.set(STLibrary.getInstance().getSTConfiguration().getMaxDownload());
    }

    private boolean isIpAddressConnected(String remoteIpAddress, int port)
    {
        try {
            Thread.sleep(1000);
        }
        catch (Exception ex) {
            logger.error(ex.getMessage());
        }
        ArrayList<Host> peersHostArray = STLibrary.getInstance().getPeersFromHostsTable();
        if (peersHostArray == null)
            return false;
        for (int i = 0; i < peersHostArray.size(); i++) {
            Host host = (Host)peersHostArray.get(i);
            if (remoteIpAddress.equals(host.getHostAddress().getIpAddress().toString()) && port == host.getHostAddress().getPort()) {
                if (host.getStatus() == HostStatus.CONNECTED
                 || host.getStatus() == HostStatus.ACCEPTING
                 || host.getStatus() == HostStatus.CONNECTING) {
                    long upSeconds = host.getConnectionUpTime( System.currentTimeMillis() ) / 1000;
                    logger.error("Peer: " + remoteIpAddress + ":" + port + " is already connected or trying to connect...UPTIME:" + TimeFormatUtils.formatSignificantElapsedTime( upSeconds ));
                    return true;
                }
            }
        }
        return false;
    }

    public void connectToPeers(Vector peersList)
    {
        try {
            logger.info("Connecting to peers taken from the web service...");
            if (peersList == null) {
                logger.error("There are no peers connected in the database...");
                return;
            }
            for (int i = 0; i < peersList.size(); i++) {
                String remoteIPAddress = ((ActiveSessionMiniWrapper)peersList.get(i)).getIp();
                int remotePort = ((ActiveSessionMiniWrapper)peersList.get(i)).getPort().intValue();
                if (isIpAddressConnected(remoteIPAddress, remotePort))
                    continue;
                this.connectToPeer(remoteIPAddress, remotePort);                
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // We use the same Gnutella framework connect to a new peer.
    public void connectToPeer(String remoteIPAddress, int remotePort)
    {
        try {
            logger.info("Starting remote connection to: " + remoteIPAddress + " port: " + remotePort);            
	        DestAddress remoteAddress = PresentationManager.getInstance().createHostAddress(remoteIPAddress+":"+remotePort, remotePort);
	        OutgoingConnectionDispatcher.dispatchConnectToHost(remoteAddress, this.servent);           
            logger.info("Connected to : " + remoteIPAddress);            
        } 
        catch (Exception ex) {
            ex.printStackTrace();            
        }           
    }

    // We use the same Gnutella framework connect to a new peer.
    public void disconnectFromPeers()
    {
        try {
            logger.info("Disconnecting from peers...");
            if (STLibrary.getInstance().isConnected())
                STLibrary.getInstance().disconnectFromPeers();
        } 
        catch (Exception ex) {
            ex.printStackTrace();            
        }           
    }
    
    public Search invokeSearchService(String query) {
        try {
            logger.debug("Sending out query for " + query + "...");
            SearchContainer searchContainer = servent.getQueryService().getSearchContainer();
            Search newSearch = searchContainer.createSearch(query);          
            logger.debug("End of Sending out query for " + query + "...");
            return newSearch;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public Object[] getIpAddressFromFriendName(String name) {
        Object[] userInfo = new Object[2];
        logger.info("Mapping ip address with friend-name " + name + " taken from the web service...");
        Vector peersList = STLibrary.getInstance().getPeersList();
        if (peersList != null) {
            for (int i = 0; i < peersList.size(); i++) {
                String ipAddress = ((ActiveSessionMiniWrapper)peersList.get(i)).getIp();
                String friendName = ((ActiveSessionMiniWrapper)peersList.get(i)).getUsername();
                if (name.equalsIgnoreCase(friendName)) {
                    Integer port = ((ActiveSessionMiniWrapper)peersList.get(i)).getPort();
                    userInfo[0] = ipAddress;
                    userInfo[1] = port;
                    return userInfo;
                }
            }
        }
        else {
            for (int i = 0; i < STLibrary.getInstance().getSTConfiguration().getMyFriends().size(); i++) {
                String ipAddress = ((STFriend)STLibrary.getInstance().getSTConfiguration().getMyFriends().get(i)).getIPAddress();
                String friendName = ((STFriend)STLibrary.getInstance().getSTConfiguration().getMyFriends().get(i)).getFriendName();
                if (friendName.equalsIgnoreCase(name)) {
                    Integer port = ((STFriend)STLibrary.getInstance().getSTConfiguration().getMyFriends().get(i)).getPortNumber();
                    userInfo[0] = ipAddress;
                    userInfo[1] = port;
                    return userInfo;
                }
            }
        }
        return null;
    }

    public String getFriendNameFromIpAddressAndPort(IpAddress ip, int port) {
        logger.info("Mapping friend name data with ip address taken from the web service...");
        Vector peersList = STLibrary.getInstance().getPeersList();
        if (peersList != null) {
            for (int i = 0; i < peersList.size(); i++) {
                String ipAddress = ((ActiveSessionMiniWrapper)peersList.get(i)).getIp();
                String friendName = ((ActiveSessionMiniWrapper)peersList.get(i)).getUsername();
                int portNumber = ((ActiveSessionMiniWrapper)peersList.get(i)).getPort().intValue(); 
                if (ipAddress.equals(ip.getFormatedString()) && port == portNumber)
                    return friendName;
            }
        }
        else {
            for (int i = 0; i < STLibrary.getInstance().getSTConfiguration().getMyFriends().size(); i++) {
                String ipAddress = ((STFriend)STLibrary.getInstance().getSTConfiguration().getMyFriends().get(i)).getIPAddress();
                String friendName = ((STFriend)STLibrary.getInstance().getSTConfiguration().getMyFriends().get(i)).getFriendName();
                int portNumber = ((STFriend)STLibrary.getInstance().getSTConfiguration().getMyFriends().get(i)).getPortNumber();
                if (ipAddress.equals(ip.getFormatedString()) && port == portNumber)
                    return friendName;
            }
        }
        return null;
    }
}
