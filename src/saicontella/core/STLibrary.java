package saicontella.core;

/**
 * SaiconTella project
 *
 * @author Marios Karagiannopoulos (mariosk@gmail.com)
 *
 * February 2008
 */

import saicontella.core.gnutella.*;
import saicontella.core.webservices.*;
import saicontella.phex.stlibrary.STLibraryTab;

import java.nio.*;
import javax.xml.ws.*;
import javax.swing.*;
import java.rmi.*;
import java.util.*;
import java.awt.*;
import java.io.File;

import phex.share.ShareFile;
import phex.share.SharedFilesService;
import phex.share.FileRescanRunner;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class STLibrary extends Component {

    public interface STConstants
    {
        public static final String ADS_USER_AGENT = "SaiconTella/v1.0 (compatible; MSIE 7.0; Windows NT 6.0";
        public static final int ADS_WEB_SERVER_TIMEOUT = 5; // in seconds                
        public static final String ADS_WEB_SERVER_FILE = "http://192.168.0.199/saicon_ads.txt";
        public static final int KEEP_ALIVE_THR_SECS = 5;               
        public static final int ADS_THR_SECS = 5;                               
        public static final String KEEP_ALIVE_THR_NAME = "KeepAliveToWebService_Thread";
        public static final String ADS_THR_NAME = "Advertisements_Thread";               
        public static final String P2PSERVENT_VERSION = "SaiconTella P2PServent";
        public static final String P2PSERVENT_BAR = "===========================";               
        public static final String PUBLIC_ACCESS = "public";
        public static final String PRIVATE_ACCESS = "private";
        public static enum AccessEnumerator {
            PUBLIC, 
            PRIVATE,
            UNDEFINED
        }
        public static enum StatusEnumerator {
            ONLINE, 
            OFFLINE,
            UNDEFINED
        } 
        public static final String p2pAppId = "92e4cace2cab102ba39d8cc01d4f351c";
        public static final String NO_LIST_SELECTED = "Select either a peer or a friend";
        public static final String PEER_SELECTED = "Add to friends >>>";
        public static final String FRIEND_SELECTED = "<<<< Remove from friends";
        // JMenus string here...
        public static final String FILE_MENU = "File";
        public static final String FILE_MENU_ADMINISTRATOR = "Administrator";
        public static final String FILE_MENU_CONNECT = "Connect";
        public static final String FILE_MENU_DISCONNECT = "Disconnect";
        // Separator
        public static final String FILE_MENU_EXIT = "Exit";
        // JMenus string here...
        public static final String FRIENDS_MENU = "Friends";
        public static final String FRIENDS_MENU_SEARCH = "Search";
        public static final String FRIENDS_MENU_ADD = "Add";
        public static final String FRIENDS_MENU_DELETE = "Delete";
        // JMenus string here...
        public static final String TOOLS_MENU = "Tools";
        public static final String TOOLS_MENU_SETTINGS = "Settings";
        public static final String TOOLS_MENU_SHARED_FOLDERS = "Shared folders";
        // JMenus string here...
        public static final String HELP_MENU = "Help";
        public static final String HELP_MENU_UPDATES = "Check for updates";
        public static final String HELP_MENU_ABOUT = "About";        
    }

    private static Log logger = LogFactory.getLog("saicontella/core/STLibrary");
    private STConfiguration confObject;
    private static STGnutellaFramework gnuTellaFramework;
    private static STLibrary sLibrary;
    private LoginResponseWrapper webserviceResult;
    private STKeepAliveThread keepAliveThread;
    private ActiveSessionMiniWrapper[] arrayOfPeers;

    public static STLibrary getInstance() {
        if (sLibrary == null) {
            sLibrary = new STLibrary();
            gnuTellaFramework = new STGnutellaFramework(sLibrary);
        }
        return sLibrary;
    }

    public ImageIcon resizeMyImageIcon(ImageIcon imageIcon, int width, int height) {
        Image image = imageIcon.getImage();
        final Dimension dimension = new Dimension(width, height);
        image = image.getScaledInstance((int) dimension.getWidth(), (int) dimension.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon finalIcon = new ImageIcon(image);
        return finalIcon;
    }

    private void initDefaultSTConfiguration() {
        this.getSTConfiguration().setListenAddress("*");
        this.getSTConfiguration().setListenPort("6346");
        this.getSTConfiguration().setWebServiceEndpoint("http://85.17.217.11:8080/UserServer/UserAuthentication?wsdl");
        this.getSTConfiguration().setMaxConnections("5");
        this.getSTConfiguration().setMaxDownload(100);
        this.getSTConfiguration().setMaxUpload(100);
        this.getSTConfiguration().setConnTimeout("30");
        // workaround for testing with virtual friends...
        STFriend mariosk1 = new STFriend("mariosk1");
        mariosk1.setIPAddress("192.168.178.10");
        this.getSTConfiguration().addFriend(mariosk1);
        STFriend mariosk2 = new STFriend("mariosk2");
        mariosk2.setIPAddress("192.168.178.20");
        this.getSTConfiguration().addFriend(mariosk2);
        STFriend mariosk3 = new STFriend("mariosk3");
        mariosk3.setIPAddress("192.168.178.30");
        this.getSTConfiguration().addFriend(mariosk3);
        STFriend mariosk4 = new STFriend("mariosk4");
        mariosk4.setIPAddress("192.168.178.40");
        this.getSTConfiguration().addFriend(mariosk4);
    }
    
    public STLibrary() {
        STXMLParser xmlParser = new STXMLParser();
        confObject = xmlParser.readConfigurationFile();
        if (confObject == null) {
            confObject = new STConfiguration();            
            this.initDefaultSTConfiguration();
        }
        //STMySQLClient dbClient = new STMySQLClient("mysql", Configuration.getAccountServer(), Configuration.getAccountName(), "");
    	logger.info(STConstants.P2PSERVENT_BAR);
        logger.info(STConstants.P2PSERVENT_VERSION);
        logger.info(STConstants.P2PSERVENT_BAR);
        logger.info("Copyright Saicon @2008");
        logger.info(STConstants.P2PSERVENT_BAR);
    }

    public boolean STLoginUser() {
        // Web Service logins the user here and returns a sessionid and a userid to our local object
        this.webserviceResult = this.STLoginUser(confObject.getWebServiceAccount(), confObject.getWebServicePassword(), confObject.getWebServiceEndpoint(), STConstants.p2pAppId, confObject.getListenPort());
        if (this.webserviceResult != null) {
            if (this.keepAliveThread == null) {
                this.keepAliveThread = new STKeepAliveThread(this.webserviceResult);
                this.keepAliveThread.start();
                return true;
            }
        }
        return false;
    }

    public void STLogoutUser() {
        if (this.keepAliveThread != null) {
            this.keepAliveThread.stop();
            this.keepAliveThread = null;
        }
        this.STLogoutUser(this.getSTConfiguration().getWebServiceEndpoint());
    }

    /* DEPRECATED
    public void addFileShares(STLibraryTab libraryTab) {
        SharedFilesService sharedFilesService = this.getGnutellaFramework().getServent().getSharedFilesService();
        FileRescanRunner.rescan(sharedFilesService, true, false);
        STFolder[] folders = this.getSTConfiguration().getFolders();
        for (int i=0; i<folders.length; i++) {
            STFileName[] files = folders[i].getFiles();
            libraryTab.shareDirHelper(new File(folders[i].getFolderName()));
            for (int j=0; j<files.length; j++) {
                File file = new File(folders[i].getFolderName() + "\\" + files[j].getFileName());
                ShareFile shareFile = new ShareFile(file);
                sharedFilesService.queueUrnCalculation(shareFile);
                sharedFilesService.addSharedFile(shareFile);
            }
        }
    }
    */

    public void fireMessageBox(String message, String title, int icon)
    {
        if (message != null)
            JOptionPane.showMessageDialog(this, message, title, icon);
        else
            JOptionPane.showMessageDialog(this, "NULL message", title, icon);
    }
      
    public STGnutellaFramework getGnutellaFramework() {
        return this.gnuTellaFramework;
    }
    
    public STConfiguration getSTConfiguration() {
        return this.confObject;
    }

    public Vector getPeersList() {
        Vector list = null;

        if (this.arrayOfPeers != null) {
            list = new Vector();
            for (int i = 0; i < this.arrayOfPeers.length; i++) {
                list.add(this.arrayOfPeers[i]);
                logger.debug(this.arrayOfPeers[i].getUsername() + "," + this.arrayOfPeers[i].getStatus() + "," + this.arrayOfPeers[i].getIp() + "," + this.arrayOfPeers[i].getPort() + "," + this.arrayOfPeers[i].hashCode());
            }
        }
        return list; 
    }

    // Calling the UserAuthentication web service: http://85.17.217.11:8080/UserServer/UserAuthentication?wsdl
    public LoginResponseWrapper STLoginUser(String userName, String passWord, String endPoint, String applicationId, int port) {
    	try {
            UserAuthenticationImplPortBindingStub proxy = new UserAuthenticationImplPortBindingStub();
            proxy._setProperty(UserAuthenticationImplPortBindingStub.ENDPOINT_ADDRESS_PROPERTY, endPoint);
        	// Password will be clear text.    		    		
        	/*
			MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
			byte[] input = passWord.getBytes();
			digest.update(input);
			byte[] hash = digest.digest();			
        	*/

            LoginResponseWrapper result = proxy.login(userName, passWord, applicationId, port);
            logger.debug("USERNAME: " + userName);
			logger.debug("PASSWORD: " + passWord);
			logger.debug("getStatus: " + result.getStatus().getValue());
			if (result.getStatus() == saicontella.core.webservices.ResponseSTATUS.ERROR) {
                this.fireMessageBox(result.getErrorMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                logger.error("ERROR String: " + result.getErrorMessage());
                return null;
            }
			else {
                this.fireMessageBox("Connected!", "Information", JOptionPane.INFORMATION_MESSAGE);
                logger.debug("getClientDownloadLocation: " + result.getClientDownloadLocation());
				logger.debug("getCountryId: " + result.getCountryId());
				logger.debug("getCountryName: " + result.getCountryName());
				logger.debug("getFirstName: " + result.getFirstName());
				logger.debug("getLastName: " + result.getLastName());
				logger.debug("getLatestClientVersion: " + result.getLatestClientVersion());
				logger.debug("getSessionId: " + result.getSessionId());
				logger.debug("getUserId: " + result.getUserId());
				logger.debug("getAvailableCredits: " + result.getAvailableCredits());
                ActiveSessionsResponseWrapper activeSessions = proxy.activeSessionsSerialized(result.getSessionId(), applicationId);                
				if (activeSessions.getStatus() == saicontella.core.webservices.ResponseSTATUS.ERROR) {
					logger.debug("activeSessions ERROR String: " + result.getErrorMessage());
                    this.fireMessageBox(result.getErrorMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
				else {
                    ActiveSessionMiniWrapper[] array = activeSessions.getActiveSessions();
                    if (array != null) {
                        this.arrayOfPeers = array;
                        for (int i=0; i<array.length; i++) {
                            logger.debug(array[i].getUsername() + "," + array[i].getStatus() + "," + array[i].getIp() + "," + array[i].getPort() + "," + array[i].hashCode());
                        }
                    }
                    else {
                        this.arrayOfPeers = null;                        
                    }
                }
			}
            return result;
          }
          catch(Exception ex) {
            logger.error("Exception: " + ex.getMessage());
            this.fireMessageBox(ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
          }           
          return null;
    }

    // Calling the UserAuthentication web service: http://85.17.217.11:8080/UserServer/UserAuthentication?wsdl
    public void STLogoutUser(String endPoint) {
    	try {
            UserAuthenticationImplPortBindingStub proxy = new UserAuthenticationImplPortBindingStub();
            proxy._setProperty(UserAuthenticationImplPortBindingStub.ENDPOINT_ADDRESS_PROPERTY, endPoint);

            if (this.webserviceResult == null)
                return;

            if (this.webserviceResult.getSessionId() != null && this.webserviceResult.getUserId() != null) {
                LoginResponseWrapper result = proxy.loggoff(this.webserviceResult.getSessionId(), this.webserviceResult.getUserId());
                logger.debug("FirstName: " + result.getFirstName());
                logger.debug("getStatus: " + result.getStatus().getValue());
                if (result.getStatus() == saicontella.core.webservices.ResponseSTATUS.ERROR) {
                    this.fireMessageBox(result.getErrorMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    logger.error("ERROR String: " + result.getErrorMessage());
                }
                else {
                    this.fireMessageBox("Disconnected!", "Information", JOptionPane.INFORMATION_MESSAGE);
                    this.webserviceResult = null;
                }
            }
            else {
                this.fireMessageBox("ERROR NULL sessionId and userId", "Error", JOptionPane.ERROR_MESSAGE);
                logger.error("ERROR NULL sessionId and userId");
            }
          }
          catch(Exception ex) {
            this.fireMessageBox(ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            logger.error("Exception: " + ex.getMessage());
          }
    }

    public static STFriend getSTFriend(int index, ArrayList myFriendsList) {
        return (STFriend)myFriendsList.get(index);
    }
    
    public static STFriend getSTFriend(String friendName, ArrayList myFriendsList) {
        for (int i = 0; i < myFriendsList.size(); i++) {
            STFriend f = (STFriend)myFriendsList.get(i);
            if (f.getFriendName().equalsIgnoreCase(friendName))
                return f;
        }
        return null;
    }

}
