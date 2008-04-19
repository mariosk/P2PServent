package saicontella.core;

/**
 * SaiconTella project
 *
 * @author Marios Karagiannopoulos (mariosk@gmail.com)
 *
 * February 2008
 */

import saicontella.core.gnutella.*;
import saicontella.core.webservices.admin.*;

import saicontella.core.webservices.authentication.LoginResponseWrapper;
import saicontella.core.webservices.authentication.ActiveSessionMiniWrapper;
import saicontella.core.webservices.authentication.UserAuthenticationImplPortBindingStub;
import saicontella.core.webservices.authentication.ActiveSessionsResponseWrapper;

import javax.swing.*;
import java.util.*;
import java.util.List;
import java.awt.*;
import java.rmi.RemoteException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;

public class STLibrary extends Component {

    public interface STConstants
    {
        // UserAuthenticationSettings (START)
        public static final String ADMINISTRATOR = "administrator";
        public static final String BANNED = "banned";
        public static final String SHARED_GB = "share-gigabytes";
        public static final String DOWNLOAD_GB = "download-gigabytes";
        public static final String TRUE = "true";
        public static final String FALSE = "false";        
        // UserAuthenticationSettings (STOP)
        public static final String ADMIN_WS_ENDPOINT = "http://85.17.217.11:8080/UserServer/UserServerAdmin?wsdl";
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

    private static Log logger = LogFactory.getLog("saicontella.core.STLibrary");
    private STConfiguration confObject;
    private static STGnutellaFramework gnuTellaFramework;
    private static STLibrary sLibrary;
    private STKeepAliveThread keepAliveThread;

    // UserAuthentication web service
    private saicontella.core.webservices.authentication.UserSettingsWrapper[] webserviceAuthSettings;
    private LoginResponseWrapper webserviceAuthResponse;
    private ActiveSessionMiniWrapper[] arrayOfPeers;
    private UserAuthenticationImplPortBindingStub webServiceAuthProxy;

    // UserServerAdmin web service
    private ServerAdminResponseWrapper adminResponse;
    private UserServerAdminImplPortBindingStub webServiceAdminProxy;

    private STMainForm stMainForm;

    public static STLibrary getInstance() {
        if (sLibrary == null) {
            sLibrary = new STLibrary();
            gnuTellaFramework = new STGnutellaFramework(sLibrary);
        }
        return sLibrary;
    }

    public saicontella.core.webservices.authentication.UserSettingsWrapper[] getCurrentUserSettings() {
        return this.webserviceAuthSettings;    
    }

    public void setSTMainForm(STMainForm stMainForm) {
        this.stMainForm = stMainForm;            
    }

    public void disconnectFromPeers() {
        this.stMainForm.disconnectFromHosts();
    }
    
    public ArrayList getPeersFromHostsTable() {
        return this.stMainForm.getPeersListData();
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
        this.getSTConfiguration().setMaxDownload(0);
        this.getSTConfiguration().setMaxUpload(0);
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
        try {
            this.webServiceAuthProxy = new UserAuthenticationImplPortBindingStub();
            this.webServiceAuthProxy._setProperty(UserAuthenticationImplPortBindingStub.ENDPOINT_ADDRESS_PROPERTY, confObject.getWebServiceEndpoint());
            this.webServiceAdminProxy = new UserServerAdminImplPortBindingStub();
            this.webServiceAdminProxy._setProperty(UserServerAdminImplPortBindingStub.ENDPOINT_ADDRESS_PROPERTY, STConstants.ADMIN_WS_ENDPOINT);
        }
        catch (Exception ex) {
            logger.error("Exception: " + ex.getMessage());
            this.fireMessageBox(ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);            
        }
    }

    public boolean STLoginUser() {
        // Web Service logins the user here and returns a sessionid and a userid to our local object
        this.webserviceAuthResponse = this.STLoginUser(confObject.getWebServiceAccount(), confObject.getWebServicePassword(), confObject.getListenPort());
        if (this.webserviceAuthResponse != null) {
            if (this.keepAliveThread == null) {
                this.keepAliveThread = new STKeepAliveThread(this.webserviceAuthResponse);
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
        this.getGnutellaFramework().disconnectFromPeers();
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

    public void reachAllOnlinePeers(LoginResponseWrapper response) {
        try {
            ActiveSessionsResponseWrapper activeSessions = this.webServiceAuthProxy.activeSessionsSerialized(response.getSessionId(), STConstants.p2pAppId);
            if (activeSessions.getStatus() == saicontella.core.webservices.authentication.ResponseSTATUS.ERROR) {
                logger.debug("activeSessions ERROR String: " + response.getErrorMessage());
                this.fireMessageBox(response.getErrorMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
        catch (Exception ex) {
            logger.error("Exception: " + ex.getMessage());
            this.fireMessageBox(ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);            
        }
    }

    // Calling the UserAuthentication web service: http://85.17.217.11:8080/UserServer/UserAuthentication?wsdl
    public LoginResponseWrapper STLoginUser(String userName, String passWord, int port) {
    	try {
        	// Password will be clear text.    		    		
        	/*
			MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
			byte[] input = passWord.getBytes();
			digest.update(input);
			byte[] hash = digest.digest();			
        	*/
           
            LoginResponseWrapper response = this.webServiceAuthProxy.login(userName, passWord, STConstants.p2pAppId, port);

            logger.debug("USERNAME: " + userName);
			logger.debug("PASSWORD: " + passWord);
			logger.debug("getStatus: " + response.getStatus().getValue());
			if (response.getStatus() == saicontella.core.webservices.authentication.ResponseSTATUS.ERROR) {
                this.fireMessageBox(response.getErrorMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                logger.error("ERROR String: " + response.getErrorMessage());
                return null;
            }
			else {
                this.fireMessageBox("Connected!", "Information", JOptionPane.INFORMATION_MESSAGE);
                logger.debug("getClientDownloadLocation: " + response.getClientDownloadLocation());
				logger.debug("getCountryId: " + response.getCountryId());
				logger.debug("getCountryName: " + response.getCountryName());
				logger.debug("getFirstName: " + response.getFirstName());
				logger.debug("getLastName: " + response.getLastName());
				logger.debug("getLatestClientVersion: " + response.getLatestClientVersion());
				logger.debug("getSessionId: " + response.getSessionId());
				logger.debug("getUserId: " + response.getUserId());
				logger.debug("getAvailableCredits: " + response.getAvailableCredits());
                this.reachAllOnlinePeers(response);

                if (this.confObject.getAccountName().equals(STConstants.ADMINISTRATOR)) {
                    UserSettingsWrapper[] settings = new UserSettingsWrapper[4];
                    settings[0] = new UserSettingsWrapper(STConstants.ADMINISTRATOR, "true");
                    settings[1] = new UserSettingsWrapper(STConstants.BANNED, "false");
                    settings[2] = new UserSettingsWrapper(STConstants.SHARED_GB, "6");
                    settings[3] = new UserSettingsWrapper(STConstants.DOWNLOAD_GB, "6");
                    this.webServiceAdminProxy.setUserSettings(response.getUserId(), STConstants.p2pAppId, settings);
                }
                
                this.webserviceAuthSettings = this.webServiceAuthProxy.fetchUserSettings(response.getSessionId(), response.getUserId(), STConstants.p2pAppId);
                if (this.webserviceAuthSettings != null) {
                    for (int i = 0; i < this.webserviceAuthSettings.length; i++) {
                        logger.debug("getAttribute[" + i + "]: " + this.webserviceAuthSettings[i].getAttribute());
                        logger.debug("getValue[" + i + "]: " + this.webserviceAuthSettings[i].getValue());
                    }
                }

                if (this.getCurrentGBytesToShare() == 6)
                    this.confObject.setMaxUpload(1000); // unlimited
                else
                    this.confObject.setMaxUpload(this.getCurrentGBytesToShare());
                if (this.getCurrentGBytesToDownload() == 6) // unlimited
                    this.confObject.setMaxDownload(1000);
                else
                    this.confObject.setMaxDownload(this.getCurrentGBytesToDownload());
                this.getGnutellaFramework().setMaxUpload();
                this.getGnutellaFramework().setMaxDownload();

                this.stMainForm.initializeToolsTabValues();
            }
            //ADMIN SERVICE TESTS (START)
            // Searching for a username...
            /*
            UserListingsWrapper user = this.webServiceAdminProxy.searchUsers("marios", "", "");
            UserInfoWrapper[] users = user.getUserListings();
            for (int i = 0; i < users.length; i++) {
                logger.debug("userId = " + users[i].getUserId());                
            }
            */
            
            //UserInfoWrapper[] users = this.webServiceAdminProxy.listUsers();

            // ADDING A NEW USER IN USER SERVER
            /*
            CountryInfoWrapper[] infoWrapperList = this.webServiceAdminProxy.listCountries();
            CountryInfoWrapper country = null;
            RegisterResponseWrapper wrapper;

            for (CountryInfoWrapper countryInfoWrapper : infoWrapperList) {
                if ("GR".equalsIgnoreCase(countryInfoWrapper.getCode())) {
                    country = countryInfoWrapper;
                    break;
                }
            }

            XMLGregorianCalendarImpl calendar = new XMLGregorianCalendarImpl();
            calendar.setDay(24);
            calendar.setMonth(2);
            calendar.setYear(1978);

            wrapper = webServiceAdminProxy.register("mariosk78", "mariosk1978", "MariosK", "MariosK", calendar.toGregorianCalendar(), country.getId(), UUID.randomUUID().toString());
            */
                        
            //ActiveSessionWrapper[] list = this.webServiceAdminProxy.listActiveSessions();
            //ADMIN SERVICE TESTS (STOP)
            return response;
          }
          catch(Exception ex) {
            logger.error("Exception: " + ex.getMessage());
            this.fireMessageBox(ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
          }           
          return null;
    }

    public UserSettingsWrapper[] getUserSettings(String userId) {
        try {
            UserSettingsWrapper[] settings = this.webServiceAdminProxy.fetchUserSettings(userId, STConstants.p2pAppId);
            return settings;
        }
        catch (RemoteException ex) {
            logger.error("Exception: " + ex.getMessage());
            this.fireMessageBox(ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;        
    }

    public void setUserSettings(String userId, saicontella.core.webservices.admin.UserSettingsWrapper[] settings) {
        try {
            this.webServiceAdminProxy.setUserSettings(userId, STConstants.p2pAppId, settings);
        }
        catch (RemoteException ex) {
            logger.error("Exception: " + ex.getMessage());
            this.fireMessageBox(ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public ArrayList<String[]> getUserIds(String userName) {
        UserListingsWrapper user = null;
        ArrayList<String[]> userIds = null;
        try {
            logger.debug("Searching userName = " + userName);
            user = this.webServiceAdminProxy.searchUsers(userName, "", "");
            if (user != null) {
                UserInfoWrapper[] users = user.getUserListings();
                userIds = new ArrayList(users.length); 
                for (int i = 0; i < users.length; i++) {
                    logger.debug("userName = " + users[i].getUserName() + " userId = " + users[i].getUserId());
                    String[] values = new String[2];
                    values[0] = users[i].getUserName();
                    values[1] = users[i].getUserId();
                    userIds.add(values);
                }
                return userIds;
            }
        } catch (RemoteException ex) {
            logger.error("Exception: " + ex.getMessage());
            this.fireMessageBox(ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); 
        }
        return null;
    }

    // UserAuthentication web service settings (START)
    private String getSettingsAuthValue(String setting) {
        if (this.webserviceAuthSettings != null) {
            for (int i = 0; i < this.webserviceAuthSettings.length; i++) {
                if (this.webserviceAuthSettings[i].getAttribute().equals(setting))
                    return this.webserviceAuthSettings[i].getValue();
            }
        }
        return null;
    }

    private boolean getBooleanAuthSetting(String setting) {
        String value = this.getSettingsAuthValue(setting);
        if (value != null) {
            if (value.equals(STConstants.TRUE)) {
                return true;
            }
            else if (value.equals(STConstants.FALSE)) {
                return false;            
            }
        }
        return false;
    }

    private int getIntegerAuthSetting(String setting) {
        String value = this.getSettingsAuthValue(setting);
        if (value != null) {
            Integer intValue = new Integer(value);
            return intValue.intValue();
        }
        return -1;
    }

    public boolean isCurrentUserAdministrator() {
        return getBooleanAuthSetting(STConstants.ADMINISTRATOR);
    }

    public boolean isCurrentUserBanned() {
        return getBooleanAuthSetting(STConstants.BANNED);
    }

    public int getCurrentGBytesToShare() {
        return getIntegerAuthSetting(STConstants.SHARED_GB);
    }

    public int getCurrentGBytesToDownload() {
        return getIntegerAuthSetting(STConstants.DOWNLOAD_GB);
    }
    // UserAuthentication web service settings (STOP)

    // UserServerAdmin web service settings (START)
    private String getSettingsAdminValue(saicontella.core.webservices.admin.UserSettingsWrapper[] settings, String setting) {
        if (settings != null) {
            for (int i = 0; i < settings.length; i++) {
                if (settings[i].getAttribute().equals(setting))
                    return settings[i].getValue();
            }
        }
        return null;
    }

    private boolean getBooleanAdminSetting(saicontella.core.webservices.admin.UserSettingsWrapper[] settings, String setting) {
        String value = this.getSettingsAdminValue(settings, setting);
        if (value != null) {
            if (value.equals(STConstants.TRUE)) {
                return true;
            }
            else if (value.equals(STConstants.FALSE)) {
                return false;
            }
        }
        return false;
    }

    private int getIntegerAdminSetting(saicontella.core.webservices.admin.UserSettingsWrapper[] settings, String setting) {
        String value = this.getSettingsAdminValue(settings, setting);
        if (value != null) {
            Integer intValue = new Integer(value);
            return intValue.intValue();
        }
        return -1;
    }

    public boolean isUserBanned(saicontella.core.webservices.admin.UserSettingsWrapper[] settings) {
        return getBooleanAdminSetting(settings, STConstants.BANNED);
    }

    public int getGBytesToShare(saicontella.core.webservices.admin.UserSettingsWrapper[] settings) {
        return getIntegerAdminSetting(settings, STConstants.SHARED_GB);
    }

    public int getGBytesToDownload(saicontella.core.webservices.admin.UserSettingsWrapper[] settings) {
        return getIntegerAdminSetting(settings, STConstants.DOWNLOAD_GB);
    }    
    // UserAuthentication web service settings (STOP)
    
    // Calling the UserAuthentication web service: http://85.17.217.11:8080/UserServer/UserAuthentication?wsdl
    public void STLogoutUser(String endPoint) {
    	try {
            UserAuthenticationImplPortBindingStub proxy = new UserAuthenticationImplPortBindingStub();
            proxy._setProperty(UserAuthenticationImplPortBindingStub.ENDPOINT_ADDRESS_PROPERTY, endPoint);

            if (this.webserviceAuthResponse == null)
                return;

            if (this.webserviceAuthResponse.getSessionId() != null && this.webserviceAuthResponse.getUserId() != null) {
                LoginResponseWrapper result = proxy.loggoff(this.webserviceAuthResponse.getSessionId(), this.webserviceAuthResponse.getUserId());
                logger.debug("FirstName: " + result.getFirstName());
                logger.debug("getStatus: " + result.getStatus().getValue());
                if (result.getStatus() == saicontella.core.webservices.authentication.ResponseSTATUS.ERROR) {
                    this.fireMessageBox(result.getErrorMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    logger.error("ERROR String: " + result.getErrorMessage());
                }
                else {
                    this.fireMessageBox("Disconnected!", "Information", JOptionPane.INFORMATION_MESSAGE);
                    this.webserviceAuthResponse = null;
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
