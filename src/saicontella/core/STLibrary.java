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
import saicontella.core.webservices.admin.UserSettingsWrapper;

import saicontella.core.webservices.authentication.*;
import saicontella.core.webservices.authentication.LoginResponseWrapper;
import saicontella.core.webservices.authentication.ActiveSessionMiniWrapper;
import saicontella.core.webservices.authentication.ActiveSessionsResponseWrapper;
import saicontella.core.webservices.authentication.BaseResponse;
import saicontella.core.webservices.authentication.ResponseSTATUS;
import saicontella.core.webservices.authentication.FriendDetailsWrapper;
import saicontella.core.webservices.admin.UserInfoWrapper;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.rmi.RemoteException;
import java.io.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import phex.gui.common.GUIUtils;

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
        public static final String AUTH_WS_ENDPOINT = "http://85.17.217.11:8080/UserServer/UserAuthentication?wsdl";
        public static final String ADMIN_WS_ENDPOINT = "http://85.17.217.11:8080/UserServer/UserServerAdmin?wsdl";
        public static final String ADS_USER_AGENT = "SaiconTella/v1.0 (compatible; MSIE 7.0; Windows NT 6.0";
        // Advertisement links here...
        public static final String ADS_MY_FRIENDS_URL = "http://www.gamersuniverse.com/gr/images/community/com_r2_c3.jpg";
        public static final String ADS_ABOUT_URL1 = "http://85.17.217.5/www/delivery/avw.php?zoneid=40";
        public static final String ADS_ABOUT_URL2 = "http://85.17.217.5/www/delivery/avw.php?zoneid=41";
        public static final String ADS_WEB_SERVER_URL = "http://85.17.217.5/www/delivery/avw.php?zoneid=13";        
        //public static final String ADS_WEB_SERVER_URL = "http://85.17.217.5/www/images/moltoad.jpg?7082e45fb2";
        public static final int ADS_WEB_SERVER_TIMEOUT = 10; // in seconds                
        public static final String ADS_WEB_SERVER_FILE = "http://192.168.0.199/saicon_ads.txt";
        public static final int KEEP_ALIVE_THR_SECS = 5;               
        public static final int ADS_THR_SECS = 5;                               
        public static final String KEEP_ALIVE_THR_NAME = "KeepAliveToWebService_Thread";
        public static final String ADS_THR_NAME = "Advertisements_Thread";               
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
        public static final String PEER_SELECTED = "Add to friends";
        public static final String FRIEND_SELECTED = "Remove from friends";
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
    private ClientApplicationVersionResponseWrapper webServiceAuthVersion; 

    // UserServerAdmin web service
    private ServerAdminResponseWrapper adminResponse;
    private UserServerAdminImplPortBindingStub webServiceAdminProxy;

    private STMainForm stMainForm;
    public static boolean restart = false; 

    public static STLibrary getInstance() {
        if (sLibrary == null) {
            sLibrary = new STLibrary();
            gnuTellaFramework = new STGnutellaFramework(sLibrary, restart);
        }
        return sLibrary;
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
        logger.info(STResources.getStr("Application.name"));    
        logger.info(STConstants.P2PSERVENT_BAR);
        logger.info("Copyright Saicon @2008");
        logger.info(STConstants.P2PSERVENT_BAR);
        try {
            this.webServiceAuthProxy = new UserAuthenticationImplPortBindingStub();
            this.webServiceAuthProxy._setProperty(UserAuthenticationImplPortBindingStub.ENDPOINT_ADDRESS_PROPERTY, STConstants.AUTH_WS_ENDPOINT);
            this.webServiceAdminProxy = new UserServerAdminImplPortBindingStub();
            this.webServiceAdminProxy._setProperty(UserServerAdminImplPortBindingStub.ENDPOINT_ADDRESS_PROPERTY, STConstants.ADMIN_WS_ENDPOINT);
        }
        catch (Exception ex) {
            logger.error("Exception: " + ex.getMessage());
            this.fireMessageBox(ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);            
        }
    }

    public void removePhexFile(String filename) {
        File f = new File(filename);
        if (f.exists())
            f.delete();
    }
    
    public void reInitializeSTLibrary() {
        try {
            this.stMainForm.dispose();
            restart = true;
            sLibrary = null;
            sLibrary = STLibrary.getInstance();
            STMainForm mainFrame = new STMainForm(sLibrary, null);
            mainFrame.createUIComponents();
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainFrame.setVisible(true);
            restart = false;
        } catch (Exception e) {
            this.fireMessageBox(e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);  
        }
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
        this.getSTConfiguration().setMaxConnections("5");
        this.getSTConfiguration().setMaxDownload(1);
        this.getSTConfiguration().setMaxUpload(1);
        this.getSTConfiguration().setConnTimeout("30");
        this.getSTConfiguration().setMaxSearchFriendsLimit("1000");        

        // workaround for testing with virtual friends...
        /*
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
        */
    }

    public byte[] retrieveFromWebServer(String absPathObject) {
        boolean status = true;
        byte[] binaryData = null;
        FileOutputStream fos = null;
        GetMethod method = new GetMethod();
        HttpClient client = new HttpClient();

        try {
          method.setURI(new URI(absPathObject, true));
          int returnCode = client.executeMethod(method);

          if(returnCode != HttpStatus.SC_OK) {            
            this.fireMessageBox("Unable to fetch: " + absPathObject + ", status code: " + returnCode, "ERROR", JOptionPane.ERROR_MESSAGE);
            status = false;
            return binaryData;
          }

          //logger.error(method.getResponseBodyAsString());

          binaryData = method.getResponseBody();
        }
        catch (HttpException he)
        {
            this.fireMessageBox("Unable to fetch: " + absPathObject + ", status: " + he.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            status = false;
        }
        catch (IOException ie)
        {
            this.fireMessageBox("Unable to fetch: " + absPathObject + ", status: " + ie.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            status = false;
        }
        finally
        {
          method.releaseConnection();
          if(fos != null) try { fos.close(); } catch (Exception fe) {}
        }

        return binaryData;
    }

    public void updateP2PServent(boolean autoCheck) {
        if (!STLibrary.getInstance().isTheSameAppVersion()) {
            // a newer version is identified
            STAppUpdateDialog updateDlg = new STAppUpdateDialog();
            updateDlg.setTitle("Updating iShare");
            updateDlg.pack();
            updateDlg.setLocationRelativeTo(null);
            updateDlg.show();
        }
        else {
            if (!autoCheck)
                STLibrary.getInstance().fireMessageBox("There are no new updates!", "Information", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public String getApplicationLocalPath() {
        try {
            File path = new File(".");
            return path.getCanonicalPath();
        }
        catch (Exception ex) {
            this.fireMessageBox(ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public String getNewVersionFileName(String version) {
        return "../iShare_"+version+".exe";
    }
    
    public boolean downloadNewerVersion(String version, String URL) {        
        byte[] data = this.retrieveFromWebServer(URL);
        if (data != null) {
            OutputStream bos = null;
            try {
                bos = new FileOutputStream(getNewVersionFileName(version));
                bos.write(data) ;
                bos.close() ;
                bos = null;
            }
            catch (Exception e) {
                this.fireMessageBox(e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            return true;            
        }
        return false;
    }

    public String getLatestVersion() {
        return this.webServiceAuthVersion.getVersion();    
    }

    public String getLatestVersionURL() {
        return this.webServiceAuthVersion.getDownloadUrl();    
    }

    public boolean isTheSameAppVersion() {
        if (this.webServiceAuthVersion == null) {
            STLibrary.getInstance().fireMessageBox("You need to login as a valid user first!", "Information", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        return this.webServiceAuthVersion.getVersion().equals(STResources.getStr("Application.version"));
    }

    public boolean STLoginUser() {
        // Web Service logins the user here and returns a sessionid and a userid to our local object
        try {
            this.webserviceAuthResponse = this.STLoginUser(confObject.getWebServiceAccount(), confObject.getWebServicePassword(), confObject.getListenPort());
            this.webServiceAuthVersion = this.webServiceAuthProxy.checkForNewerApplication(STConstants.p2pAppId, ReleaseType.BETA);
        }
        catch (Exception ex) {
            this.fireMessageBox(ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
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
            try {
                this.keepAliveThread.stop();
                this.keepAliveThread = null;
            }
            catch (Exception ex) {
                logger.error(ex.getMessage());    
            }
        }
        this.STLogoutUser(STConstants.AUTH_WS_ENDPOINT);
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

                /*
                FriendDetailsWrapper[] friends = this.webServiceAuthProxy.searchFriend(response.getSessionId(), "");
                if (friends != null) {
                    for (int i = 0; i < friends.length; i++) {
                        logger.debug("friend[" + i + "]: " + friends[i].getFriendId() + " " + friends[i].getFriendName() + " " + friends[i].getStatus() + " " + friends[i].getUserId());
                        FriendshipStatus status = friends[i].getFriendshipStatus();
                    }
                }
                */
                
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

    public ArrayList<String[]> getUserIds(String userName, String firstName, String lastName) {
        UserListingsWrapper user = null;
        ArrayList<String[]> userIds = null;
        try {
            logger.debug("Searching userName = " + userName);
            user = this.webServiceAdminProxy.searchUsers(userName, firstName, lastName);
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

    public Vector[] getMyFriendsList() {
        Vector[] myFriendsListData = null;
        try {
            logger.debug("Retrieving all friends...");
            FriendDetailsWrapper[] myFriends= this.webServiceAuthProxy.searchFriend(this.webserviceAuthResponse.getSessionId(), "");
            if (myFriends != null) {
                myFriendsListData = new Vector[2];
                myFriendsListData[0] = new Vector();
                myFriendsListData[1] = new Vector();
                for (int i = 0; i < myFriends.length; i++) {
                    logger.debug("friend[" + i + "]: " + myFriends[i].getFriendId() + " " + myFriends[i].getFriendName() + " " + myFriends[i].getStatus() + " " + myFriends[i].getUserId());
                    if (this.getSTConfiguration().getAccountName().equals(myFriends[i].getFriendName()))
                        continue;
                    myFriendsListData[0].add(myFriends[i].getFriendName());
                    myFriendsListData[1].add(myFriends[i].getFriendId());                    
                }
                return myFriendsListData;
            }
        } catch (RemoteException ex) {
            logger.error("Exception: " + ex.getMessage());
            this.fireMessageBox(ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return myFriendsListData;
    }

    public boolean isFriendAlreadyInList(String friendName, Vector list) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).toString().equals(friendName))
                return true;
        }
        return false;
    }
    
    public void addFriendInList(String friendName, String userId) {
        try {
            logger.debug("Adding a friend " + friendName + " with userId = " + userId);
            FriendActionResponseWrapper response = this.webServiceAuthProxy.addFriend(this.webserviceAuthResponse.getSessionId(), userId);
            if (response != null) {
                if (response.getStatus() == ResponseSTATUS.ERROR) {
                    this.fireMessageBox(response.getErrorMessage(), "Error adding friend " + friendName, JOptionPane.ERROR_MESSAGE);
                }
                else {
                    this.fireMessageBox("Friend '" + friendName + "' added succesfully!", "Information", JOptionPane.INFORMATION_MESSAGE);
                }
            }
            else {
                this.fireMessageBox("Friend '" + friendName + "' added succesfully!", "Information", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (RemoteException ex) {
            logger.error("Exception: " + ex.getMessage());
            this.fireMessageBox(ex.getMessage(), "Error in friend " + friendName, JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean removeFriendFromList(String friendName, String userId) {
        boolean errorFound = false;
        try {
            logger.debug("Removing friend " + friendName + " with userId = " + userId);
            BaseResponse response = this.webServiceAuthProxy.removeFriend(this.webserviceAuthResponse.getSessionId(), userId);
            if (response != null) {
                if (response.getStatus() == ResponseSTATUS.ERROR) {
                    this.fireMessageBox(response.getErrorMessage(), "Error removing friend " + friendName, JOptionPane.ERROR_MESSAGE);
                    errorFound = true;
                }
                else {
                    this.fireMessageBox("Friend '" + friendName + "' removed succesfully!", "Information", JOptionPane.INFORMATION_MESSAGE);
                }
            }
            else {
                this.fireMessageBox("Friend '" + friendName + "' removed succesfully!", "Information", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (RemoteException ex) {
            errorFound = true;
            logger.error("Exception: " + ex.getMessage());
            this.fireMessageBox(ex.getMessage(), "Error in friend " + friendName, JOptionPane.ERROR_MESSAGE);
        }
        return (!errorFound);
    }
   
    public ArrayList<String[]> getCandidateFriends(String userName, String firstName, String lastName) {
        ArrayList<String[]> userIds = null;
        try {
            logger.debug("Searching candidate friend = " + userName);
            saicontella.core.webservices.authentication.UserInfoWrapper[] friends = null;
            friends = this.webServiceAuthProxy.searchCandidateFriend(this.webserviceAuthResponse.getSessionId(), userName, firstName, lastName, this.getSTConfiguration().getMaxSearchFriendsLimit());

            if (friends != null) {
                userIds = new ArrayList(friends.length);
                for (int i = 0; i < friends.length; i++) {
                    logger.debug("userName = " + friends[i].getUserName() + " userId = " + friends[i].getUserId());
                    String[] values = new String[2];
                    values[0] = friends[i].getUserName();
                    values[1] = friends[i].getUserId();
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

    public boolean isConnected() {
        return (this.webserviceAuthResponse != null);
    }
}
