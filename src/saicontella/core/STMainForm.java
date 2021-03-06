package saicontella.core;

/**
 * SaiconTella project
 *
 * @author Marios Karagiannopoulos (mariosk@gmail.com)
 *
 * February 2008
 */

import phex.query.SearchContainer;
import phex.gui.common.*;
import phex.gui.tabs.FWTab;
import phex.xml.sax.gui.DGuiSettings;
import phex.utils.DirectoryOnlyFileFilter;
import phex.host.Host;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

import java.awt.event.*;
import java.awt.*;
import java.util.Vector;
import java.util.ArrayList;
import java.io.*;
import java.net.URL;
import java.net.MalformedURLException;

import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.Spacer;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.CellConstraints;
import saicontella.phex.stsearch.STSearchTab;
import saicontella.phex.stnetwork.STNetworkTab;
import saicontella.phex.stdownload.STSWDownloadTab;
import saicontella.phex.stupload.STUploadTab;
import saicontella.phex.stlibrary.STLibraryTab;
import saicontella.phex.stwizard.STConfigurationWizardDialog;
import saicontella.phex.STFWElegantPanel;

import chrriis.dj.nativeswing.NativeInterface;
import chrriis.dj.nativeswing.components.JFlashPlayer;
import org.jdesktop.jdic.desktop.DesktopException;

public class STMainForm extends JFrame {

    public static final int NETWORK_TAB_ID = 1000;
    public static final int SEARCH_TAB_ID = 1101;
    public static final int DOWNLOAD_TAB_ID = 1003;
    public static final int UPLOAD_TAB_ID = 1004;
    public static final int LIBRARY_TAB_ID = 1007;

    public static final int NETWORK_TAB_INDEX = 0;
    public static final int SEARCH_TAB_INDEX = 1;
    public static final int DOWNLOAD_TAB_INDEX = 2;
    public static final int UPLOAD_TAB_INDEX = 3;
    public static final int LIBRARY_TAB_INDEX = 4;

    public static final int SETTINGS_TAB_INDEX = 5;
    public static final int FRIENDS_TAB_INDEX = 6;

    private STLibrary sLibrary;

    private SearchContainer searchContainer;
    private JTabbedPane mainTabbedPanel;
    private JPanel mySettingsTab;
    private JTextField userTextBox;
    private JPanel myFriendsTab;
    private JList listFriends;
    private JList listAllPeers;
    private JButton buttonAddFriend;
    private JTextField passwordTextBox;
    private JTextField listenPortTextBox;
    private JTextField addressTextBox;
    private JTextField connectionTimeoutTextBox;
    private JTextField completeDownloadFolderTextBox;
    private JTextField incompleteDownloadFolderTextBox;
    private JTextField downloadRatioTextBox;
    private JTextField uploadRatioTextBox;
    private JButton incompleteDownloadFolderBrowseButton;
    private JButton completeDownloadFolderBrowseButton;
    private JButton saveSettingsButton;

    private JTextField WSDLTextFBox;
    private JTextField maximumConnectionsTextFBox;
    private JCheckBox autoConnectCheckBox;
    private JTextField adsServerTextBox;
    private JPanel outerPanel;
    private JPanel WebServicesPanel;
    private JLabel UserLabel;
    private JLabel PasswordLabel;
    private JPanel ServentPanel;
    private JLabel ListenPortLabel;
    private JLabel AddressLabel;
    private JLabel ConnectionTimeoutLabel;
    private JLabel maximumConnectionsLabel;
    private JLabel autoConnectLabel;
    private JTextField textFieldSearchFriend;
    private JButton buttonSearchFriend;
    private JTextField maxFriendsLimitTextBox;
    private JPanel tabsPanel;
    private JLabel myAdsImageLabel;
    private JLabel myLogoImageLabel;
    private JPanel innerSettingsPanel;
    private JPanel SharedFoldersPanel;
    private JLabel CompleteDownloadFolderLabel;
    private JLabel DownloadRatioLabel;
    private JLabel UploadRatioLabel;
    private JLabel IncompleteDownloadFolderLabel;
    private JPanel innerFriendsPanel;
    private JPanel imagesPanel;
    private JLabel myFriendsAdsLabel;
    private JPanel innerAdsPanel;
    private JLabel myIShareLogoLabel;
    private JPanel rightImagePanel;
    private JPanel leftImagePanel;
    private JPanel middleImagePanel;
    private JLabel FriendsSizeLabel;
    private JLabel friendNameLabel;
    private JLabel myFriendsLabel;

    private JMenuBar mainMenuBar;
    private JMenu fileMenu;
    private JMenuItem fileMenuAdmin;
    private JMenuItem fileMenuConnect;
    private JMenuItem fileMenuConnections;
    private JMenuItem fileMenuDisconnect;
    private JMenuItem fileMenuExit;
    private JMenu friendsMenu;
    private JMenuItem friendsMenuSearch;
    private JMenuItem friendsMenuAdd;
    private JMenuItem friendsMenuDelete;
    private JMenu toolsMenu;
    private JMenuItem toolsMenuSettings;
    private JMenuItem toolsMenuDownloads;
    private JMenuItem toolsMenuUploads;
    private JMenuItem toolsMenuSharedFolders;
    private JMenu helpMenu;
    private JMenuItem helpMenuUpdates;
    private JMenuItem helpMenuManual;
    private JMenuItem helpMenuAbout;

    private STSearchTab searchTab;
    private STNetworkTab networkTab;
    private STSWDownloadTab swDownloadTab;
    private STUploadTab uploadTab;
    private STLibraryTab libraryTab;
    private DGuiSettings guiSettings;

    private ImageIcon myPeersIcon;
    private ImageIcon mySearchIcon;
    private ImageIcon myDownloadsIcon;
    private ImageIcon myUploadsIcon;
    private ImageIcon mySharesIcon;
    private ImageIcon mySettingsIcon;
    private ImageIcon myFriendsIcon;
    private ImageIcon myFriendsDeleteIcon;
    private ImageIcon myFriendsAddIcon;    
    private ImageIcon myConnectIcon;
    private ImageIcon myDisconnectIcon;
    private ImageIcon myAdminIcon;
    private ImageIcon myExitIcon;
    private ImageIcon myAboutIcon;
    private ImageIcon myManualIcon;
    private ImageIcon myUpdatesIcon;

    // allPeersData[0] => friend names from search result
    // allPeersData[1] => friends ids from search result  
    private Vector[] allPeersData;
    // friendsListData[0] => friends names
    // friendsListData[1] => friends ids
    private Vector[] friendsListData;
    private STMainForm stMainForm;
    private boolean initialized;
    private STSystemTray tray;

    final JFlashPlayer flashPlayerAds = new JFlashPlayer();
    final JFlashPlayer flashPlayerMyFriends = new JFlashPlayer();
    final JFlashPlayer flashPlayerLogo1 = new JFlashPlayer();
    final JFlashPlayer flashPlayerLogo2 = new JFlashPlayer();

    private Thread urlUpdateThreadAds;
    private Thread urlUpdateThreadMyFriends;
    private Thread urlUpdateThreadLogo1;
    private Thread urlUpdateThreadLogo2;
    
    private STUrlUpdateThread updThreadAds;
    private STUrlUpdateThread updThreadMyFriends;
    private STUrlUpdateThread updThreadLogo1;
    private STUrlUpdateThread updThreadLogo2;
    
    public void disableTabs() {
        this.mainTabbedPanel.setEnabledAt(STMainForm.NETWORK_TAB_INDEX, false);
        this.mainTabbedPanel.setEnabledAt(STMainForm.SEARCH_TAB_INDEX, false);
        this.mainTabbedPanel.setEnabledAt(STMainForm.DOWNLOAD_TAB_INDEX, false);
        this.mainTabbedPanel.setEnabledAt(STMainForm.UPLOAD_TAB_INDEX, false);
        this.mainTabbedPanel.setEnabledAt(STMainForm.LIBRARY_TAB_INDEX, false);
        this.mainTabbedPanel.setEnabledAt(STMainForm.FRIENDS_TAB_INDEX, false);
        this.mainTabbedPanel.setSelectedIndex(STMainForm.SETTINGS_TAB_INDEX);
    }

    public void disableMenus() {
        this.friendsMenu.setEnabled(false);
        this.toolsMenuSharedFolders.setEnabled(false);
    }

    public void disconnectFromHosts() {
        ArrayList<Host> hosts = this.networkTab.getAllHosts();
        if (hosts == null)
            return;
        for (int i = 0; i < hosts.size(); i++) {
            this.networkTab.disconnectFromHost((Host)hosts.get(i));
        }
     }

    public ArrayList<Host> getPeersListData() {
        if (this.networkTab == null)
            return null;
        return this.networkTab.getAllHosts();
     }

    public void drawMenus() {
        ClickMenuActionHandler menuHandler = new ClickMenuActionHandler();

        this.stMainForm = this;
        this.mainMenuBar = new JMenuBar();        
        this.setJMenuBar(this.mainMenuBar);

        // Instantiating the File Menu
        fileMenu = new JMenu(STLibrary.STConstants.FILE_MENU);
        fileMenuConnections = new JMenuItem(STLibrary.STConstants.FILE_MENU_CONNECTIONS);
        fileMenuConnect = new JMenuItem(STLibrary.STConstants.FILE_MENU_CONNECT);
        fileMenuDisconnect = new JMenuItem(STLibrary.STConstants.FILE_MENU_DISCONNECT);
        fileMenuDisconnect.setEnabled(false);
        fileMenuAdmin = new JMenuItem(STLibrary.STConstants.FILE_MENU_ADMINISTRATOR);
        fileMenuExit = new JMenuItem(STLibrary.STConstants.FILE_MENU_EXIT);
        // Draw the order of the JMenuItems
        fileMenu.add(fileMenuConnect);
        fileMenu.add(fileMenuDisconnect);
        fileMenu.add(fileMenuAdmin);
        fileMenuAdmin.setVisible(false);        
        fileMenu.add(fileMenuConnections);
        fileMenu.add(fileMenuExit);
        // Adding the action listeners for each menu item
        fileMenuConnections.addActionListener(menuHandler);
        fileMenuConnect.addActionListener(menuHandler);
        fileMenuDisconnect.addActionListener(menuHandler);
        fileMenuAdmin.addActionListener(menuHandler);
        fileMenuExit.addActionListener(menuHandler);
        this.mainMenuBar.add(fileMenu);

        // Instantiating the Friends Menu
        friendsMenu = new JMenu(STLibrary.STConstants.FRIENDS_MENU);
        friendsMenuSearch = new JMenuItem(STLibrary.STConstants.FRIENDS_MENU_SEARCH);
        friendsMenuAdd = new JMenuItem(STLibrary.STConstants.FRIENDS_MENU_ADD);
        friendsMenuDelete = new JMenuItem(STLibrary.STConstants.FRIENDS_MENU_DELETE);
        // Draw the order of the JMenuItems
        friendsMenu.add(friendsMenuSearch);
        friendsMenu.add(friendsMenuAdd);
        friendsMenu.add(friendsMenuDelete);
        // Adding the action listeners for each menu item
        friendsMenuSearch.addActionListener(menuHandler);
        friendsMenuAdd.addActionListener(menuHandler);
        friendsMenuDelete.addActionListener(menuHandler);
        this.mainMenuBar.add(friendsMenu);

        // Instantiating the Tools Menu
        toolsMenu = new JMenu(STLibrary.STConstants.TOOLS_MENU);
        toolsMenuSettings = new JMenuItem(STLibrary.STConstants.TOOLS_MENU_SETTINGS);
        toolsMenuUploads = new JMenuItem(STLibrary.STConstants.TOOLS_MENU_UPLOADS);
        toolsMenuDownloads = new JMenuItem(STLibrary.STConstants.TOOLS_MENU_DOWNLOADS);
        toolsMenuSharedFolders = new JMenuItem(STLibrary.STConstants.TOOLS_MENU_SHARED_FOLDERS);
        // Draw the order of the JMenuItems
        toolsMenu.add(toolsMenuSettings);
        toolsMenu.add(toolsMenuUploads);
        toolsMenu.add(toolsMenuDownloads);
        toolsMenu.add(toolsMenuSharedFolders);
        // Adding the action listeners for each menu item
        toolsMenuSettings.addActionListener(menuHandler);
        toolsMenuUploads.addActionListener(menuHandler);
        toolsMenuDownloads.addActionListener(menuHandler);        
        toolsMenuSharedFolders.addActionListener(menuHandler);
        this.mainMenuBar.add(toolsMenu);

        // Instantiating the About Menu
        helpMenu = new JMenu(STLibrary.STConstants.HELP_MENU);
        helpMenuUpdates = new JMenuItem(STLibrary.STConstants.HELP_MENU_UPDATES);
        helpMenuManual = new JMenuItem(STLibrary.STConstants.HELP_MENU_MANUAL);
        helpMenuAbout = new JMenuItem(STLibrary.STConstants.HELP_MENU_ABOUT);
        // Draw the order of the JMenuItems
        helpMenu.add(helpMenuUpdates);
        helpMenu.add(helpMenuManual);
        helpMenu.add(helpMenuAbout);
        // Adding the action listeners for each menu item
        helpMenuUpdates.addActionListener(menuHandler);
        helpMenuManual.addActionListener(menuHandler);
        helpMenuAbout.addActionListener(menuHandler);
        this.mainMenuBar.add(helpMenu);
    }

    private void setUIStrings() {
        // this is for JWebBrowser
        NativeInterface.open();
        // mySettings Panel
        TitledBorder b1 = (TitledBorder)WebServicesPanel.getBorder();
        b1.setTitle(STLocalizer.getString("WebServices"));
        //b1.setTitleColor(STLibrary.getInstance().getSTConfiguration().getBgColor());
        WebServicesPanel.setBackground(STLibrary.getInstance().getSTConfiguration().getBgColor());        
        this.UserLabel.setText(STLocalizer.getString("WebServicesUser"));
        this.PasswordLabel.setText(STLocalizer.getString("WebServicesPassword"));
        this.FriendsSizeLabel.setText(STLocalizer.getString("WebServicesFriendsSize"));
        TitledBorder b2 = (TitledBorder)SharedFoldersPanel.getBorder();
        b2.setTitle(STLocalizer.getString("SharedFoldersPanel"));
        SharedFoldersPanel.setBackground(STLibrary.getInstance().getSTConfiguration().getBgColor());
        //b2.setTitleColor(STLibrary.getInstance().getSTConfiguration().getBgColor());
        this.CompleteDownloadFolderLabel.setText(STLocalizer.getString("CompleteDownloadFolder"));
        this.IncompleteDownloadFolderLabel.setText(STLocalizer.getString("IncompleteDownloadFolder"));
        this.DownloadRatioLabel.setText(STLocalizer.getString("DownloadRatio"));
        this.UploadRatioLabel.setText(STLocalizer.getString("UploadRatio"));
        this.completeDownloadFolderBrowseButton.setText(STLocalizer.getString("Browse"));
        this.incompleteDownloadFolderBrowseButton.setText(STLocalizer.getString("Browse"));
        TitledBorder b3 = (TitledBorder)ServentPanel.getBorder();
        b3.setTitle(STLocalizer.getString("Servent"));
        //b3.setTitleColor(STLibrary.getInstance().getSTConfiguration().getBgColor());
        ServentPanel.setBackground(STLibrary.getInstance().getSTConfiguration().getBgColor());
        this.ListenPortLabel.setText(STLocalizer.getString("ServentListenPort"));
        this.ConnectionTimeoutLabel.setText(STLocalizer.getString("ServentConnectionTimeout"));
        this.maximumConnectionsLabel.setText(STLocalizer.getString("ServentMaximumConnections"));
        this.AddressLabel.setText(STLocalizer.getString("ServentIPAddress"));
        this.autoConnectLabel.setText(STLocalizer.getString("ServentAutoConnect"));
        this.saveSettingsButton.setText(STLocalizer.getString("SaveSettings"));
        // myFriends Panel
        this.friendNameLabel.setText(STLocalizer.getString("friendName"));
        this.buttonSearchFriend.setText(STLocalizer.getString("buttonSearchFriend"));
        this.myFriendsLabel.setText(STLocalizer.getString("myFriendsLabel"));

        this.mySettingsTab.setBackground(STLibrary.getInstance().getSTConfiguration().getBgColor());
        this.myFriendsTab.setBackground(STLibrary.getInstance().getSTConfiguration().getBgColor());
        this.autoConnectCheckBox.setBackground(STLibrary.getInstance().getSTConfiguration().getBgColor());
        this.outerPanel.setBackground(STLibrary.getInstance().getSTConfiguration().getBgColor());
        this.imagesPanel.setBackground(STLibrary.getInstance().getSTConfiguration().getBgColor());
        this.tabsPanel.setBackground(STLibrary.getInstance().getSTConfiguration().getBgColor());        
    }

    public STMainForm(STLibrary sLibrary, DGuiSettings guiSettings) {
        super();

        this.initialized = false;
        this.sLibrary = sLibrary;
        this.sLibrary.setSTMainForm(this);
        
        $$$setupUI$$$();
        setUIStrings();                

        this.tray = new STSystemTray();
        this.drawMenus();
       
        imagesPanel.setSize(this.getWidth(), 500);        

        STButtonsPanel buttonsPanel1 = new STButtonsPanel();
        STButtonsPanel buttonsPanel2 = new STButtonsPanel();
        JPanel emptyPanel1 = new JPanel();
        JPanel emptyPanel2 = new JPanel();

        STFWElegantPanel settingsElegantPanel = new STFWElegantPanel( STLocalizer.getString("Settings"), emptyPanel1);
        CellConstraints ccSettings = new CellConstraints();
        FormLayout layoutSettings = new FormLayout(
            "d, fill:d:grow, d, d, d, d", // columns
            "fill:d:grow, fill:d:grow, fill:d:grow, d, d"); //rows
        this.mySettingsTab.setLayout(layoutSettings);
        this.mySettingsTab.add(settingsElegantPanel, ccSettings.xy(1, 1));
        this.mySettingsTab.add(innerSettingsPanel, ccSettings.xy(1, 2));
        this.mySettingsTab.add(buttonsPanel1, ccSettings.xy(6, 3));
        this.mySettingsTab.add(emptyPanel1, ccSettings.xy(1, 4));
        this.mySettingsTab.add(emptyPanel1, ccSettings.xy(1, 5));

        STFWElegantPanel friendsElegantPanel = new STFWElegantPanel( STLocalizer.getString("Friends"), emptyPanel2);
        CellConstraints ccFriends = new CellConstraints();
        FormLayout layoutFriends = new FormLayout(
            "fill:d:grow, fill:d:grow, fill:d:grow, fill:d:grow, fill:d:grow, fill:d:grow, d, d, d, d", // columns
            "d, fill:d:grow, fill:d:grow, d, d"); //rows
        this.myFriendsTab.setLayout(layoutFriends);
        this.myFriendsTab.add(friendsElegantPanel, ccFriends.xywh(1, 1, 9, 1));
        this.myFriendsTab.add(innerFriendsPanel, ccFriends.xywh(1, 2, 8, 2));
        this.myFriendsTab.add(innerAdsPanel, ccFriends.xywh(9, 2, 1, 1));
        this.myFriendsTab.add(buttonsPanel2, ccFriends.xy(9, 3));
        this.myFriendsTab.add(emptyPanel2, ccFriends.xy(1, 4));
        this.myFriendsTab.add(emptyPanel2, ccFriends.xy(1, 5));
        
        /*
        ArrayList<Object> gradients = new ArrayList<Object>(5);
        gradients.add(0.28f);
        gradients.add(0.00f);
        gradients.add(new Color(0x33FF00));
        gradients.add(new Color(0x33CC00));
        gradients.add(new Color(0x339900));
        UIManager.put("Button.gradient", gradients);
        UIManager.put("CheckBox.gradient", gradients);
        UIManager.put("CheckBoxMenuItem.gradient", gradients);
        UIManager.put("MenuBar.gradient", gradients);
        UIManager.put("ScrollBar.gradient", gradients);
        UIManager.put("RadioButton.gradient", gradients);
        */
        
        this.setTitle(STResources.getAppStr("Application.name") + " v" + STResources.getAppStr("Application.version") + " (" + sLibrary.getSTConfiguration().getListenAddress() + ":" + sLibrary.getSTConfiguration().getListenPort() + ")");
        this.guiSettings = guiSettings;

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowHandler());

        if (sLibrary.getSTConfiguration() != null)
            sLibrary.getGnutellaFramework().getServent().getEventService().processAnnotations(this);

        mainTabbedPanel.addChangeListener(new ChangeActionHandler());
        ClickButtonActionHandler clickActionHandler = new ClickButtonActionHandler();
        this.buttonAddFriend.addActionListener(clickActionHandler);
        this.saveSettingsButton.addActionListener(clickActionHandler);
        this.completeDownloadFolderBrowseButton.addActionListener(new SetCompleteDownloadDirectoryListener());
        this.incompleteDownloadFolderBrowseButton.addActionListener(new SetIncompleteDownloadDirectoryListener());

        STAdsDownloader adsDownloader = new STAdsDownloader(this);
        adsDownloader.start();
                
        buttonSearchFriend.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!STLibrary.getInstance().isConnected()) {
                    STLibrary.getInstance().fireMessageBox(STLocalizer.getString("ConnectionNeeded"), STLocalizer.getString("Error"), JOptionPane.ERROR_MESSAGE);
                    return;
                }
                stMainForm.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                ArrayList<String[]> listOfUserIds = null;
                if (!textFieldSearchFriend.getText().trim().equals(""))
                    listOfUserIds = STLibrary.getInstance().getCandidateFriends(textFieldSearchFriend.getText(), "", "");
                else
                    listOfUserIds = STLibrary.getInstance().getCandidateFriends("", "", "");
                if (listOfUserIds != null) {
                    listAllPeers.removeAll();
                    allPeersData[0] = new Vector(listOfUserIds.size());
                    allPeersData[1] = new Vector(listOfUserIds.size());
                    for (int i = 0; i < listOfUserIds.size(); i++) {
                        allPeersData[0].add(listOfUserIds.get(i)[0]);
                        allPeersData[1].add(listOfUserIds.get(i)[1]);
                    }
                    listAllPeers.setListData(allPeersData[0]);
                }
                stMainForm.setCursor(Cursor.getDefaultCursor());
            }
        });
        
        this.initialized = true;               
    }

    public boolean initializedProperly() {
        return this.initialized;
    }
   
    public void setMyFriendsAdImageLabelIcon(ImageIcon icon, String link) {
        this.flashPlayerMyFriends.setVisible(false);
        this.myFriendsAdsLabel.setVisible(true);        
        this.myFriendsAdsLabel.setIcon(icon);
        this.myFriendsAdsLabel.repaint();
        if (link.length() > 0)
            this.myFriendsAdsLabel.addMouseListener(new STMouseListener(link, this));
    }

    public void setAdImageLabelIcon(ImageIcon icon, String link) {
        setRetrievedImageIconGeneric(icon, link, this.myAdsImageLabel);
        this.flashPlayerAds.setVisible(false);
    }

    public void setIShareImageLabelIcon1(ImageIcon icon, String link) {
        setRetrievedImageIconGeneric(icon, link, this.myLogoImageLabel);
        this.flashPlayerLogo1.setVisible(false);
    }

    public void setIShareImageLabelIcon2(ImageIcon icon, String link) {
        setRetrievedImageIconGeneric(icon, link, this.myIShareLogoLabel);
        this.flashPlayerLogo2.setVisible(false);
    }
    
    private void setRetrievedImageIconGeneric(ImageIcon icon, String link, JLabel labelComponent) {
        labelComponent.setVisible(true);
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        double h = size.getHeight() - (size.getHeight() / 2) + 0.2*size.getHeight();
        if (h < 700) {
            icon = STLibrary.getInstance().resizeMyImageIcon(icon, (int)(0.7*icon.getIconWidth()), (int)(0.7*icon.getIconHeight()));
        }
        labelComponent.setIcon(icon);
        labelComponent.repaint();
        if (link.length() > 0)
            labelComponent.addMouseListener(new STMouseListener(link, this));        
    }

    public void setMyFriendsAdImageLabelIcon() {        
        if (updThreadMyFriends == null) {
            this.innerAdsPanel.removeAll();
            this.innerAdsPanel.add(flashPlayerMyFriends);
            updThreadMyFriends = new STUrlUpdateThread(flashPlayerMyFriends, STLibrary.STConstants.ADS_MY_FRIENDS_URL);
            urlUpdateThreadMyFriends = new Thread(updThreadMyFriends);
            urlUpdateThreadMyFriends.start();
        }        
    }
    
    public void setAdImageLabelIcon() {
        if (updThreadAds == null) {
            this.middleImagePanel.removeAll();
            this.middleImagePanel.add(flashPlayerAds);
            updThreadAds = new STUrlUpdateThread(flashPlayerAds, STLibrary.STConstants.ADS_WEB_SERVER_URL);
            urlUpdateThreadAds = new Thread(updThreadAds);
            urlUpdateThreadAds.start();
        }
    }

    public void setIShareImageLabelIcon1() {
        if (updThreadLogo1 == null) {
            this.leftImagePanel.removeAll();
            this.leftImagePanel.add(flashPlayerLogo1);            
            updThreadLogo1 = new STUrlUpdateThread(flashPlayerLogo1, STLibrary.STConstants.ISHARE_LOGO_URL1);
            urlUpdateThreadLogo1 = new Thread(updThreadLogo1);
            urlUpdateThreadLogo1.start();
        }
    }

    public void setIShareImageLabelIcon2() {
        if (updThreadLogo2 == null) {
            this.rightImagePanel.removeAll();
            this.rightImagePanel.add(flashPlayerLogo2);
            updThreadLogo2 = new STUrlUpdateThread(flashPlayerLogo2, STLibrary.STConstants.ISHARE_LOGO_URL2);
            urlUpdateThreadLogo2 = new Thread(updThreadLogo2);
            urlUpdateThreadLogo2.start();
        }        
    }
    
    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        outerPanel = new JPanel();
        outerPanel.setLayout(new BorderLayout(0, 0));
        mainTabbedPanel = new JTabbedPane();
        mainTabbedPanel.setTabLayoutPolicy(1);
        outerPanel.add(mainTabbedPanel, BorderLayout.CENTER);
        mySettingsTab = new JPanel();
        mySettingsTab.setLayout(new GridLayoutManager(14, 12, new Insets(0, 0, 0, 0), -1, -1));
        mainTabbedPanel.addTab("Settings", mySettingsTab);
        final Spacer spacer1 = new Spacer();
        mySettingsTab.add(spacer1, new GridConstraints(0, 11, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        mySettingsTab.add(spacer2, new GridConstraints(13, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(100, 14), null, 0, false));
        final Spacer spacer3 = new Spacer();
        mySettingsTab.add(spacer3, new GridConstraints(1, 11, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer4 = new Spacer();
        mySettingsTab.add(spacer4, new GridConstraints(2, 11, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer5 = new Spacer();
        mySettingsTab.add(spacer5, new GridConstraints(3, 11, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer6 = new Spacer();
        mySettingsTab.add(spacer6, new GridConstraints(4, 11, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer7 = new Spacer();
        mySettingsTab.add(spacer7, new GridConstraints(0, 10, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer8 = new Spacer();
        mySettingsTab.add(spacer8, new GridConstraints(0, 9, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer9 = new Spacer();
        mySettingsTab.add(spacer9, new GridConstraints(2, 8, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer10 = new Spacer();
        mySettingsTab.add(spacer10, new GridConstraints(3, 7, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer11 = new Spacer();
        mySettingsTab.add(spacer11, new GridConstraints(4, 6, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer12 = new Spacer();
        mySettingsTab.add(spacer12, new GridConstraints(7, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer13 = new Spacer();
        mySettingsTab.add(spacer13, new GridConstraints(8, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer14 = new Spacer();
        mySettingsTab.add(spacer14, new GridConstraints(9, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer15 = new Spacer();
        mySettingsTab.add(spacer15, new GridConstraints(10, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        WebServicesPanel = new JPanel();
        WebServicesPanel.setLayout(new GridLayoutManager(3, 5, new Insets(0, 0, 0, 0), -1, -1));
        mySettingsTab.add(WebServicesPanel, new GridConstraints(0, 0, 2, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        WebServicesPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Web Services"));
        UserLabel = new JLabel();
        UserLabel.setText("User:");
        UserLabel.setDisplayedMnemonic('U');
        UserLabel.setDisplayedMnemonicIndex(0);
        WebServicesPanel.add(UserLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(146, 14), null, 0, false));
        userTextBox = new JTextField();
        userTextBox.setText("");
        WebServicesPanel.add(userTextBox, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(100, 20), new Dimension(200, 20), 1, false));
        PasswordLabel = new JLabel();
        PasswordLabel.setText("Password:");
        PasswordLabel.setDisplayedMnemonic('P');
        PasswordLabel.setDisplayedMnemonicIndex(0);
        WebServicesPanel.add(PasswordLabel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(146, 14), null, 0, false));
        passwordTextBox = new JTextField();
        WebServicesPanel.add(passwordTextBox, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(100, 20), new Dimension(200, -1), 1, false));
        WSDLTextFBox = new JTextField();
        WSDLTextFBox.setEditable(false);
        WebServicesPanel.add(WSDLTextFBox, new GridConstraints(2, 1, 1, 4, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(100, 20), null, 1, false));
        final Spacer spacer16 = new Spacer();
        WebServicesPanel.add(spacer16, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer17 = new Spacer();
        WebServicesPanel.add(spacer17, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        ServentPanel = new JPanel();
        ServentPanel.setLayout(new GridLayoutManager(5, 4, new Insets(0, 0, 0, 0), -1, -1));
        mySettingsTab.add(ServentPanel, new GridConstraints(2, 0, 5, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        ServentPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Servent"));
        ListenPortLabel = new JLabel();
        ListenPortLabel.setText("Listen port:");
        ListenPortLabel.setDisplayedMnemonic('L');
        ListenPortLabel.setDisplayedMnemonicIndex(0);
        ServentPanel.add(ListenPortLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(146, 14), null, 0, false));
        listenPortTextBox = new JTextField();
        ServentPanel.add(listenPortTextBox, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(100, 20), new Dimension(100, -1), 1, false));
        AddressLabel = new JLabel();
        AddressLabel.setText("Address:");
        AddressLabel.setDisplayedMnemonic('A');
        AddressLabel.setDisplayedMnemonicIndex(0);
        ServentPanel.add(AddressLabel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(146, 14), null, 0, false));
        addressTextBox = new JTextField();
        ServentPanel.add(addressTextBox, new GridConstraints(1, 1, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(180, 20), new Dimension(190, -1), 1, false));
        ConnectionTimeoutLabel = new JLabel();
        ConnectionTimeoutLabel.setText("Connection timeout:");
        ConnectionTimeoutLabel.setDisplayedMnemonic('C');
        ConnectionTimeoutLabel.setDisplayedMnemonicIndex(0);
        ServentPanel.add(ConnectionTimeoutLabel, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(146, 14), null, 0, false));
        maximumConnectionsLabel = new JLabel();
        maximumConnectionsLabel.setText("Maximum Connections:");
        maximumConnectionsLabel.setDisplayedMnemonic('M');
        maximumConnectionsLabel.setDisplayedMnemonicIndex(0);
        ServentPanel.add(maximumConnectionsLabel, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(146, 14), null, 0, false));
        autoConnectLabel = new JLabel();
        autoConnectLabel.setText("Auto Connect:");
        autoConnectLabel.setDisplayedMnemonic('T');
        autoConnectLabel.setDisplayedMnemonicIndex(2);
        ServentPanel.add(autoConnectLabel, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(146, 14), null, 0, false));
        connectionTimeoutTextBox = new JTextField();
        ServentPanel.add(connectionTimeoutTextBox, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(100, 20), new Dimension(100, -1), 1, false));
        maximumConnectionsTextFBox = new JTextField();
        ServentPanel.add(maximumConnectionsTextFBox, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(100, 20), new Dimension(100, -1), 1, false));
        final Spacer spacer18 = new Spacer();
        ServentPanel.add(spacer18, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer19 = new Spacer();
        ServentPanel.add(spacer19, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        autoConnectCheckBox = new JCheckBox();
        autoConnectCheckBox.setSelected(true);
        autoConnectCheckBox.setText("Enable");
        ServentPanel.add(autoConnectCheckBox, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 1, false));
        SharedFoldersPanel = new JPanel();
        SharedFoldersPanel.setLayout(new GridLayoutManager(5, 3, new Insets(0, 0, 0, 0), -1, -1));
        mySettingsTab.add(SharedFoldersPanel, new GridConstraints(7, 0, 5, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        SharedFoldersPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Shared Folders"));
        completeDownloadFolderTextBox = new JTextField();
        completeDownloadFolderTextBox.setEditable(false);
        SharedFoldersPanel.add(completeDownloadFolderTextBox, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(274, 20), null, 1, false));
        uploadRatioTextBox = new JTextField();
        SharedFoldersPanel.add(uploadRatioTextBox, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(274, 20), new Dimension(100, -1), 1, false));
        adsServerTextBox = new JTextField();
        SharedFoldersPanel.add(adsServerTextBox, new GridConstraints(4, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(274, 20), new Dimension(190, -1), 1, false));
        CompleteDownloadFolderLabel = new JLabel();
        CompleteDownloadFolderLabel.setText("Complete download folder:");
        CompleteDownloadFolderLabel.setDisplayedMnemonic('D');
        CompleteDownloadFolderLabel.setDisplayedMnemonicIndex(9);
        SharedFoldersPanel.add(CompleteDownloadFolderLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(148, 14), null, 0, false));
        DownloadRatioLabel = new JLabel();
        DownloadRatioLabel.setText("Download ratio:");
        DownloadRatioLabel.setDisplayedMnemonic('R');
        DownloadRatioLabel.setDisplayedMnemonicIndex(9);
        SharedFoldersPanel.add(DownloadRatioLabel, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(148, 14), null, 0, false));
        UploadRatioLabel = new JLabel();
        UploadRatioLabel.setText("Upload ratio:");
        UploadRatioLabel.setDisplayedMnemonic('O');
        UploadRatioLabel.setDisplayedMnemonicIndex(3);
        SharedFoldersPanel.add(UploadRatioLabel, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(148, 14), null, 0, false));
        incompleteDownloadFolderTextBox = new JTextField();
        incompleteDownloadFolderTextBox.setEditable(false);
        SharedFoldersPanel.add(incompleteDownloadFolderTextBox, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(274, 20), null, 1, false));
        IncompleteDownloadFolderLabel = new JLabel();
        IncompleteDownloadFolderLabel.setText("Incomplete download folder:");
        IncompleteDownloadFolderLabel.setDisplayedMnemonic('I');
        IncompleteDownloadFolderLabel.setDisplayedMnemonicIndex(0);
        SharedFoldersPanel.add(IncompleteDownloadFolderLabel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(148, 14), null, 0, false));
        downloadRatioTextBox = new JTextField();
        downloadRatioTextBox.setText("");
        SharedFoldersPanel.add(downloadRatioTextBox, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(274, 20), new Dimension(100, -1), 1, false));
        incompleteDownloadFolderBrowseButton = new JButton();
        incompleteDownloadFolderBrowseButton.setEnabled(true);
        incompleteDownloadFolderBrowseButton.setText("Browse");
        incompleteDownloadFolderBrowseButton.setMnemonic('B');
        incompleteDownloadFolderBrowseButton.setDisplayedMnemonicIndex(0);
        SharedFoldersPanel.add(incompleteDownloadFolderBrowseButton, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(100, 25), new Dimension(100, -1), 0, false));
        completeDownloadFolderBrowseButton = new JButton();
        completeDownloadFolderBrowseButton.setEnabled(true);
        completeDownloadFolderBrowseButton.setText("Browse");
        completeDownloadFolderBrowseButton.setMnemonic('W');
        completeDownloadFolderBrowseButton.setDisplayedMnemonicIndex(3);
        SharedFoldersPanel.add(completeDownloadFolderBrowseButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(100, 25), new Dimension(100, -1), 0, false));
        saveSettingsButton = new JButton();
        saveSettingsButton.setEnabled(true);
        saveSettingsButton.setText("Save Settings");
        saveSettingsButton.setMnemonic('S');
        saveSettingsButton.setDisplayedMnemonicIndex(0);
        mySettingsTab.add(saveSettingsButton, new GridConstraints(12, 1, 1, 1, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(100, 25), new Dimension(100, -1), 0, false));
        myFriendsTab = new JPanel();
        myFriendsTab.setLayout(new GridLayoutManager(2, 3, new Insets(0, 0, 0, 0), -1, -1));
        mainTabbedPanel.addTab("myFriends", myFriendsTab);
        buttonAddFriend = new JButton();
        buttonAddFriend.setEnabled(false);
        myFriendsTab.add(buttonAddFriend, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        myFriendsTab.add(scrollPane1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        listAllPeers = new JList();
        scrollPane1.setViewportView(listAllPeers);
        final JScrollPane scrollPane2 = new JScrollPane();
        myFriendsTab.add(scrollPane2, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        listFriends = new JList();
        scrollPane2.setViewportView(listFriends);        
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return outerPanel;
    }

    private class ChangeActionHandler implements ChangeListener {

        public void stateChanged(ChangeEvent e) {
            boolean retrievedFromWS = false;
            JTabbedPane tabbedPane = (JTabbedPane) e.getSource();
            JPanel currentPanel = (JPanel) tabbedPane.getSelectedComponent();
            if (currentPanel == null)
                return;
            if (currentPanel.equals(libraryTab)) {
                if (!STLibrary.getInstance().isConnected()) {
                    STLibrary.getInstance().fireMessageBox(STLocalizer.getString("ConnectionNeeded"), STLocalizer.getString("Error"), JOptionPane.ERROR_MESSAGE);
                    tabbedPane.setSelectedIndex(0);
                    return;
                }
                //libraryTab.updateMyFriendsList();
            }
            else if (currentPanel.equals(searchTab)) {
                if (!STLibrary.getInstance().isConnected()) {
                    STLibrary.getInstance().fireMessageBox(STLocalizer.getString("ConnectionNeeded"), STLocalizer.getString("Error"), JOptionPane.ERROR_MESSAGE);
                    tabbedPane.setSelectedIndex(0);
                    return;
                }
            }
            else if (currentPanel.equals(myFriendsTab)) {
                if (searchTab != null && !STLibrary.getInstance().isConnected()) {
                    STLibrary.getInstance().fireMessageBox(STLocalizer.getString("ConnectionNeeded"), STLocalizer.getString("Error"), JOptionPane.ERROR_MESSAGE);
                    tabbedPane.setSelectedIndex(0);
                    return;
                }                                
                ClickListActionHandler clickListHandler = new ClickListActionHandler();
                listAllPeers.addListSelectionListener(clickListHandler);
                listFriends.addListSelectionListener(clickListHandler);
                if (networkTab == null)
                    return;
                //Retrieving here the list of the added friends from the configuration
                // file saicontella.xml if the user is not connected yet
                Vector[] myFriendsList = null;
                if (STLibrary.getInstance().isConnected()) {
                    myFriendsList = STLibrary.getInstance().getMyFriendsList();
                    retrievedFromWS = true;
                }
                else {
                    myFriendsList = STLibrary.getInstance().getSTConfiguration().getMyFriendsAndIdsVectorData();
                }                 
                if (myFriendsList != null) {
                    friendsListData = new Vector[2];
                    friendsListData[0] = myFriendsList[0];
                    friendsListData[1] = myFriendsList[1];
                }
                if (allPeersData == null)
                    allPeersData = new Vector[2];                
                if (allPeersData[0] != null)
                    listAllPeers.setListData(allPeersData[0]);
                if (friendsListData == null) {
                    friendsListData = new Vector[2];
                    friendsListData[0] = new Vector();
                    friendsListData[1] = new Vector();                    
                }
                listFriends.setListData(friendsListData[0]);
                if (retrievedFromWS && myFriendsList != null) saveFriendsListInXML();

                // Viewing pending friends...
                if (sLibrary.isConnected()) {
                    Vector[] pendingFriends = sLibrary.fetchPendingFriends();
                    if (pendingFriends != null) {
                        setIconImage(sLibrary.getAppIcon().getImage());
                        STFriendDialog friendDlg = new STFriendDialog(stMainForm, pendingFriends);                        
                        friendDlg.setTitle(STLocalizer.getString("PendingFriends"));
                        friendDlg.setLocationRelativeTo(null);
                        friendDlg.pack();
                        friendDlg.setVisible(true);
                        stMainForm.mainTabbedPanel.setSelectedIndex(STMainForm.NETWORK_TAB_INDEX);                        
                    }
                }
            }
        }
    }

    private class ClickListActionHandler implements ListSelectionListener {

        public void valueChanged(ListSelectionEvent e) {
            if (e.getSource() instanceof JList) {
                JList currentList = (JList) e.getSource();
                if (currentList == listAllPeers) {
                    buttonAddFriend.setEnabled(true);
                    buttonAddFriend.setText(STLibrary.STConstants.PEER_SELECTED);
                    listFriends.clearSelection();
                } else if (currentList == listFriends) {
                    buttonAddFriend.setEnabled(true);
                    buttonAddFriend.setText(STLibrary.STConstants.FRIEND_SELECTED);
                    listAllPeers.clearSelection();
                } else {
                    buttonAddFriend.setEnabled(false);
                    buttonAddFriend.setText(STLibrary.STConstants.NO_LIST_SELECTED);
                    listFriends.clearSelection();
                    listAllPeers.clearSelection();
                }
            }
        }
    }

    public void initFrameSize() {
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        int w = (int)(size.getWidth() - 0.25*size.getWidth());
        if (w < 700)
            w = 700;
        double h = size.getHeight() - (size.getHeight() / 2) + 0.2*size.getHeight();
        if (h < 700) {
            h = 700;
            this.tabsPanel.setPreferredSize(new Dimension(w, 470));                        
        }
        this.mySettingsTab.setAutoscrolls(true);
        this.innerSettingsPanel.setAutoscrolls(true);
        this.tabsPanel.setAutoscrolls(true);
        this.setSize(w, (int)h);        
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setResizable(false);
    }

    public ImageIcon getFriendsAddIcon() {
        return this.myFriendsAddIcon;
    }

    public ImageIcon getFriendsDeleteIcon() {
        return this.myFriendsDeleteIcon;
    }

    public void initializeToolsTabValues() {
        this.maxFriendsLimitTextBox.setText(new Integer(sLibrary.getSTConfiguration().getMaxSearchFriendsLimit()).toString());
        this.userTextBox.setText(sLibrary.getSTConfiguration().getWebServiceAccount());
        this.passwordTextBox.setText(sLibrary.getSTConfiguration().getWebServicePassword());
        this.listenPortTextBox.setText(String.valueOf(sLibrary.getSTConfiguration().getListenPort()));
        this.addressTextBox.setText(sLibrary.getSTConfiguration().getListenAddress());
        this.connectionTimeoutTextBox.setText(String.valueOf(sLibrary.getSTConfiguration().getConnTimeout()));
        this.maximumConnectionsTextFBox.setText(String.valueOf(sLibrary.getSTConfiguration().getMaxConnections()));
        this.autoConnectCheckBox.setSelected(sLibrary.getSTConfiguration().getAutoConnect());
        this.completeDownloadFolderTextBox.setText(sLibrary.getSTConfiguration().getCompleteFolder());
        this.incompleteDownloadFolderTextBox.setText(sLibrary.getSTConfiguration().getInCompleteFolder());
        
        if (sLibrary.getSTConfiguration().getMaxDownload() == 1000)
            this.downloadRatioTextBox.setText("Unlimited");
        else
            this.downloadRatioTextBox.setText(String.valueOf(sLibrary.getSTConfiguration().getMaxDownload()));
        this.downloadRatioTextBox.setEnabled(false);

        if (sLibrary.getSTConfiguration().getMaxUpload() == 1000)
            this.uploadRatioTextBox.setText("Unlimited");
        else
            this.uploadRatioTextBox.setText(String.valueOf(sLibrary.getSTConfiguration().getMaxUpload()));
        this.uploadRatioTextBox.setEnabled(false);

        this.completeDownloadFolderBrowseButton.setName("completeDownloadFolderBrowseButton");
        this.incompleteDownloadFolderBrowseButton.setName("incompleteDownloadFolderBrowseButton");        
    }
    
    public void createUIComponents() {
        // Removing Tabs to add them later
        this.mainTabbedPanel.remove(0);
        this.mainTabbedPanel.remove(0);

        // Load images
        try {
            myPeersIcon = STLibrary.getInstance().resizeMyImageIcon(new ImageIcon(STResources.getAppStr("myPeersIcon.ico")), 15, 15);
            mySearchIcon = STLibrary.getInstance().resizeMyImageIcon(new ImageIcon(STResources.getAppStr("mySearchIcon.ico")), 15, 15);
            myDownloadsIcon = STLibrary.getInstance().resizeMyImageIcon(new ImageIcon(STResources.getAppStr("myDownloadsIcon.ico")), 15, 15);
            myUploadsIcon = STLibrary.getInstance().resizeMyImageIcon(new ImageIcon(STResources.getAppStr("myUploadsIcon.ico")), 15, 15);
            mySharesIcon = STLibrary.getInstance().resizeMyImageIcon(new ImageIcon(STResources.getAppStr("mySharesIcon.ico")), 15, 15);
            mySettingsIcon = STLibrary.getInstance().resizeMyImageIcon(new ImageIcon(STResources.getAppStr("mySettingsIcon.ico")), 15, 15);
            myFriendsIcon = STLibrary.getInstance().resizeMyImageIcon(new ImageIcon(STResources.getAppStr("myFriendsIcon.ico")), 15, 15);
            myFriendsAddIcon = STLibrary.getInstance().resizeMyImageIcon(new ImageIcon(STResources.getAppStr("myFriendsAddIcon.ico")), 15, 15);
            myFriendsDeleteIcon = STLibrary.getInstance().resizeMyImageIcon(new ImageIcon(STResources.getAppStr("myFriendsDeleteIcon.ico")), 15, 15);
            myConnectIcon = STLibrary.getInstance().resizeMyImageIcon(new ImageIcon(STResources.getAppStr("myConnectIcon.ico")), 15, 15);
            myDisconnectIcon = STLibrary.getInstance().resizeMyImageIcon(new ImageIcon(STResources.getAppStr("myDisconnectIcon.ico")), 15, 15);
            myAdminIcon = STLibrary.getInstance().resizeMyImageIcon(new ImageIcon(STResources.getAppStr("myAdminIcon.ico")), 15, 15);
            myExitIcon = STLibrary.getInstance().resizeMyImageIcon(new ImageIcon(STResources.getAppStr("myExitIcon.ico")), 15, 15);
            myAboutIcon = STLibrary.getInstance().resizeMyImageIcon(new ImageIcon(STResources.getAppStr("myAboutIcon.ico")), 15, 15);
            myManualIcon = STLibrary.getInstance().resizeMyImageIcon(new ImageIcon(STResources.getAppStr("myManualIcon.ico")), 15, 15);
            myUpdatesIcon = STLibrary.getInstance().resizeMyImageIcon(new ImageIcon(STResources.getAppStr("myUpdatesIcon.ico")), 15, 15);
                    
            this.setIconImage(sLibrary.getAppIcon().getImage());
            this.fileMenuConnections.setIcon(myPeersIcon);
            this.fileMenuConnect.setIcon(myConnectIcon);
            this.fileMenuDisconnect.setIcon(myDisconnectIcon);
            this.fileMenuAdmin.setIcon(myAdminIcon);
            this.fileMenuExit.setIcon(myExitIcon);
            this.friendsMenuAdd.setIcon(myFriendsAddIcon);
            this.friendsMenuDelete.setIcon(myFriendsDeleteIcon);
            this.friendsMenuSearch.setIcon(myFriendsIcon);
            this.toolsMenuSettings.setIcon(mySettingsIcon);
            this.toolsMenuDownloads.setIcon(myDownloadsIcon);
            this.toolsMenuUploads.setIcon(myUploadsIcon);
            this.toolsMenuSharedFolders.setIcon(mySharesIcon);
            this.helpMenuAbout.setIcon(myAboutIcon);
            this.helpMenuManual.setIcon(myManualIcon);            
            this.helpMenuUpdates.setIcon(myUpdatesIcon);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        // STNetwork Tab
        networkTab = new STNetworkTab();
        networkTab.initComponent(guiSettings);
        this.mainTabbedPanel.add(networkTab, NETWORK_TAB_INDEX);
        this.mainTabbedPanel.setTitleAt(NETWORK_TAB_INDEX, STLocalizer.getString("myPeers"));
        this.mainTabbedPanel.setIconAt(NETWORK_TAB_INDEX, myPeersIcon);

        // STSearch Tab
        searchContainer = sLibrary.getGnutellaFramework().getServent().getQueryService().getSearchContainer();
        searchTab = new STSearchTab(searchContainer, sLibrary.getGnutellaFramework().getServent().getQueryService().getSearchFilterRules(), sLibrary.getGnutellaFramework().getServent().getDownloadService());
        searchTab.initComponent(guiSettings);
        this.mainTabbedPanel.add(searchTab, SEARCH_TAB_INDEX);
        this.mainTabbedPanel.setTitleAt(SEARCH_TAB_INDEX, STLocalizer.getString("mySearch"));
        this.mainTabbedPanel.setIconAt(SEARCH_TAB_INDEX, mySearchIcon);

        //  SWDownload Tab
        swDownloadTab = new STSWDownloadTab(sLibrary.getGnutellaFramework().getServent().getDownloadService());
        swDownloadTab.initComponent(guiSettings);
        this.mainTabbedPanel.add(swDownloadTab, DOWNLOAD_TAB_INDEX);
        this.mainTabbedPanel.setTitleAt(DOWNLOAD_TAB_INDEX, STLocalizer.getString("myDownloads"));
        this.mainTabbedPanel.setIconAt(DOWNLOAD_TAB_INDEX, myDownloadsIcon);

        //  STUpload Tab
        uploadTab = new STUploadTab();
        uploadTab.initComponent(guiSettings);
        this.mainTabbedPanel.add(uploadTab, UPLOAD_TAB_INDEX);
        this.mainTabbedPanel.setTitleAt(UPLOAD_TAB_INDEX, STLocalizer.getString("myUploads"));
        this.mainTabbedPanel.setIconAt(UPLOAD_TAB_INDEX, myUploadsIcon);

        //  STLibrary Tab
        libraryTab = new STLibraryTab(this);                
        libraryTab.initComponent(guiSettings);
        this.mainTabbedPanel.add(libraryTab, LIBRARY_TAB_INDEX);
        this.mainTabbedPanel.setTitleAt(LIBRARY_TAB_INDEX, STLocalizer.getString("myShares"));
        this.mainTabbedPanel.setIconAt(LIBRARY_TAB_INDEX, mySharesIcon);

        //  Settings Tab
        this.mainTabbedPanel.add(mySettingsTab, SETTINGS_TAB_INDEX);
        this.mainTabbedPanel.setTitleAt(SETTINGS_TAB_INDEX, STLocalizer.getString("mySettings"));
        this.mainTabbedPanel.setIconAt(SETTINGS_TAB_INDEX, mySettingsIcon);

        //  myFriends Tab
        this.mainTabbedPanel.add(myFriendsTab, FRIENDS_TAB_INDEX);
        this.mainTabbedPanel.setTitleAt(FRIENDS_TAB_INDEX, STLocalizer.getString("myFriends"));
        this.mainTabbedPanel.setIconAt(FRIENDS_TAB_INDEX, myFriendsIcon);

        this.mainTabbedPanel.invalidate();
        this.mainTabbedPanel.repaint();

        sLibrary.getGnutellaFramework().getServent().getEventService().processAnnotations(this);

        this.mainTabbedPanel.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                Component comp = mainTabbedPanel.getSelectedComponent();
                if (comp instanceof FWTab) {
                    ((FWTab) comp).tabSelectedNotify();
                }
            }
        });

        this.initializeToolsTabValues();
        this.getContentPane().add(this.outerPanel);
    }
        
    public boolean connectAction(String userName, String passWord, int port) {
        stMainForm.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        boolean connected = STLibrary.getInstance().STLoginUser(userName, passWord, port);
        stMainForm.setCursor(Cursor.getDefaultCursor());
        if (connected) {
            STLibrary.getInstance().getGnutellaFramework().connectToPeers(STLibrary.getInstance().getPeersList());
            stMainForm.fileMenuDisconnect.setEnabled(true);
            stMainForm.fileMenuConnect.setEnabled(false);
            if (STLibrary.getInstance().isCurrentUserAdministrator())
                stMainForm.fileMenuAdmin.setVisible(true);
            if (STLibrary.getInstance().isCurrentUserBanned()) {
                sLibrary.fireMessageBox(STLocalizer.getString("BannedUser"), STLocalizer.getString("Information"), JOptionPane.INFORMATION_MESSAGE);
                STLibrary.getInstance().exitApplication();
                stMainForm.disableMenus();
                stMainForm.disableTabs();
            }
            STLibrary.getInstance().updateP2PServent(true);            
        }
        return connected;
    }
    
    private class ClickMenuActionHandler implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            JMenuItem sourceObject = (JMenuItem) e.getSource();
            if (sourceObject.getText().equals(STLibrary.STConstants.FILE_MENU_CONNECT)) {
                stMainForm.connectAction(STLibrary.getInstance().getSTConfiguration().getWebServiceAccount(), STLibrary.getInstance().getSTConfiguration().getWebServicePassword(), STLibrary.getInstance().getSTConfiguration().getListenPort());
            } else if (sourceObject.getText().equals(STLibrary.STConstants.HELP_MENU_ABOUT)) {
                stMainForm.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                stMainForm.setIconImage(sLibrary.getAppIcon().getImage());
                STAboutDialog aboutDlg = new STAboutDialog(stMainForm);
                aboutDlg.setTitle(STLocalizer.getString("About"));
                aboutDlg.setSize(400, 400);
                aboutDlg.setLocationRelativeTo(null);
                aboutDlg.pack();
                aboutDlg.setVisible(true);
                stMainForm.setCursor(Cursor.getDefaultCursor());
            } else if (sourceObject.getText().equals(STLibrary.STConstants.FILE_MENU_DISCONNECT)) {
                stMainForm.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                STLibrary.getInstance().STLogoutUser();
                stMainForm.setCursor(Cursor.getDefaultCursor());
                stMainForm.fileMenuDisconnect.setEnabled(false);
                stMainForm.fileMenuConnect.setEnabled(true);
                stMainForm.fileMenuAdmin.setVisible(false);
            } else if (sourceObject.getText().equals(STLibrary.STConstants.FILE_MENU_ADMINISTRATOR)) {
                stMainForm.setIconImage(sLibrary.getAppIcon().getImage());
                STAdminDialog adminDlg = new STAdminDialog(stMainForm);
                adminDlg.setTitle(STLocalizer.getString("Administration"));
                adminDlg.setLocationRelativeTo(null);
                adminDlg.pack();
                adminDlg.setVisible(true);                
            } else if (sourceObject.getText().equals(STLibrary.STConstants.FILE_MENU_EXIT)) {
                STLibrary.getInstance().exitApplication();
            } else if (sourceObject.getText().equals(STLibrary.STConstants.HELP_MENU_MANUAL)) {
                try {
                    org.jdesktop.jdic.desktop.Desktop.browse(new URL(STLocalizer.getString("ManualUrl")));
                } catch(MalformedURLException ex) {
                    STLibrary.getInstance().fireMessageBox(ex.getMessage(), STLocalizer.getString("Error"), JOptionPane.ERROR_MESSAGE);
                } catch (DesktopException ex) {
                    STLibrary.getInstance().fireMessageBox(ex.getMessage(), STLocalizer.getString("Error"), JOptionPane.ERROR_MESSAGE);
                }                
            } else if (sourceObject.getText().equals(STLibrary.STConstants.FILE_MENU_CONNECTIONS)) {
                stMainForm.mainTabbedPanel.setSelectedIndex(STMainForm.NETWORK_TAB_INDEX);
            } else if (sourceObject.getText().equals(STLibrary.STConstants.FRIENDS_MENU_ADD)) {
                stMainForm.mainTabbedPanel.setSelectedIndex(STMainForm.FRIENDS_TAB_INDEX);
            } else if (sourceObject.getText().equals(STLibrary.STConstants.FRIENDS_MENU_SEARCH)) {
                stMainForm.mainTabbedPanel.setSelectedIndex(STMainForm.FRIENDS_TAB_INDEX);
            } else if (sourceObject.getText().equals(STLibrary.STConstants.FRIENDS_MENU_DELETE)) {
                stMainForm.mainTabbedPanel.setSelectedIndex(STMainForm.FRIENDS_TAB_INDEX);
            } else if (sourceObject.getText().equals(STLibrary.STConstants.TOOLS_MENU_DOWNLOADS)) {
                stMainForm.mainTabbedPanel.setSelectedIndex(STMainForm.DOWNLOAD_TAB_INDEX);
            } else if (sourceObject.getText().equals(STLibrary.STConstants.TOOLS_MENU_UPLOADS)) {
                stMainForm.mainTabbedPanel.setSelectedIndex(STMainForm.UPLOAD_TAB_INDEX);                
            } else if (sourceObject.getText().equals(STLibrary.STConstants.TOOLS_MENU_SETTINGS)) {
                stMainForm.mainTabbedPanel.setSelectedIndex(STMainForm.SETTINGS_TAB_INDEX);
            } else if (sourceObject.getText().equals(STLibrary.STConstants.TOOLS_MENU_SHARED_FOLDERS)) {
                stMainForm.mainTabbedPanel.setSelectedIndex(STMainForm.LIBRARY_TAB_INDEX);
            } else if (sourceObject.getText().equals(STLibrary.STConstants.HELP_MENU_UPDATES)) {
                STLibrary.getInstance().updateP2PServent(false);                
            }
        }
    }

    public JTabbedPane getMainTabbedPanel() {
        return this.mainTabbedPanel;
    }

    private void saveFriendsListInXML() {
        sLibrary.getSTConfiguration().getMyFriends().clear();
        if (friendsListData[0] != null) {
            for (int i = 0; i < friendsListData[0].size(); i++) {
                Object friendName = friendsListData[0].get(i);
                Object friendId = friendsListData[1].get(i);
                STFriend friend = new STFriend(friendName.toString());
                if (friendId != null)
                    friend.setFriendId(friendId.toString());                                
                Object[] userInfo = STLibrary.getInstance().getGnutellaFramework().getIpAddressFromFriendName(friend.getFriendName());
                if (userInfo != null) {
                    String friendIpAddress = (String)userInfo[0];
                    int port = ((Integer)userInfo[1]).intValue();
                    if (friendIpAddress != null) {
                        friend.setIPAddress(friendIpAddress);
                        friend.setPortNumber(port);
                    }
                }
                sLibrary.getSTConfiguration().addFriend(friend);
            }
        }
        sLibrary.getSTConfiguration().saveXMLFile();
    }

    // LEFT JList: listAllPeers, DATA: allPeersData
    // RIGHT JList: listFriends, DATA: friendsListData 
    private class ClickButtonActionHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JButton sourceObject = (JButton) e.getSource();
            // Removing a friend...
            if (sourceObject.getText().equals(STLibrary.STConstants.FRIEND_SELECTED)) {
                if (!STLibrary.getInstance().isConnected()) {
                    STLibrary.getInstance().fireMessageBox(STLocalizer.getString("ConnectionNeeded"), STLocalizer.getString("Error"), JOptionPane.ERROR_MESSAGE);
                    return;
                }                
                stMainForm.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                // get the selected index from the RIGHT JList (listFriends)
                Object[] friendNames = listFriends.getSelectedValues();
                if (friendNames == null) {
                    stMainForm.setCursor(Cursor.getDefaultCursor());
                    STLibrary.getInstance().fireMessageBox(STLocalizer.getString("SelectFriendToRemove"), STLocalizer.getString("Error"), JOptionPane.ERROR);
                    stMainForm.setCursor(Cursor.getDefaultCursor());
                    return;
                }
                int[] indices = listFriends.getSelectedIndices();
                // removing the selected friend from that JList
                Vector[] tmpFriendsList = new Vector[2];
                tmpFriendsList[0] = new Vector();
                tmpFriendsList[1] = new Vector();
                for (int i = 0; i < friendsListData[0].size(); i++) {
                    tmpFriendsList[0].add(i, friendsListData[0].get(i));
                    tmpFriendsList[1].add(i, friendsListData[1].get(i));
                }
                for (int i = 0; i < indices.length; i++) {
                    // removing the friend through the web service
                    if (STLibrary.getInstance().removeFriendFromList(tmpFriendsList[0].get(indices[i]).toString(), tmpFriendsList[1].get(indices[i]).toString())) {
                        // each time we remove an entry from the vector the capacity changes...
                        // so removing always position 0 is a solution...
                        friendsListData[0].remove(indices[i] - i);
                        friendsListData[1].remove(indices[i] - i);
                        // setting the new list data
                        listFriends.setListData(friendsListData[0]);
                    }
                }
                saveFriendsListInXML();
                stMainForm.setCursor(Cursor.getDefaultCursor());
            // Adding a friend...
            } else if (sourceObject.getText().equals(STLibrary.STConstants.PEER_SELECTED)) {
                if (!STLibrary.getInstance().isConnected()) {
                    STLibrary.getInstance().fireMessageBox(STLocalizer.getString("ConnectionNeeded"), STLocalizer.getString("Error"), JOptionPane.ERROR_MESSAGE);
                    return;
                }                
                stMainForm.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                // get the selected index from the LEFT JList (listAllPeers)
                Object[] friendNames = listAllPeers.getSelectedValues();
                if (friendNames == null) {
                    stMainForm.setCursor(Cursor.getDefaultCursor());
                    STLibrary.getInstance().fireMessageBox(STLocalizer.getString("SelectFriendToAdd"), STLocalizer.getString("Error"), JOptionPane.ERROR);
                    stMainForm.setCursor(Cursor.getDefaultCursor());
                    return;
                }
                int[] indices = listAllPeers.getSelectedIndices();
                // adding the selected friends from the left JList to the right JList
                for (int i = 0; i < indices.length; i++) {
                    if (STLibrary.getInstance().isFriendAlreadyInList(friendNames[i].toString(), friendsListData[0])) {
                    STLibrary.getInstance().fireMessageBox(STLocalizer.getString("FriendAlreadyInList"), STLocalizer.getString("Warning"), JOptionPane.WARNING_MESSAGE);                        
                        continue;
                    }
                    friendsListData[0].add(allPeersData[0].get(indices[i]));
                    friendsListData[1].add(allPeersData[1].get(indices[i]));
                    // adding the friend through the web service
                    STLibrary.getInstance().addFriendInList(allPeersData[0].get(indices[i]).toString(), allPeersData[1].get(indices[i]).toString());                                    
                }
                listFriends.setListData(friendsListData[0]);
                // saveFriendsListInXML should be called before addFriendsInList
                saveFriendsListInXML();
                stMainForm.setCursor(Cursor.getDefaultCursor());
            } else if (sourceObject.getText().equals("Save")) {
                // saveFriendsListInXML();
                sLibrary.fireMessageBox(STLocalizer.getString("Saved"), STLocalizer.getString("Information"), JOptionPane.INFORMATION_MESSAGE);
            } else if (sourceObject.getText().equals("Save Settings")) {
                sLibrary.getSTConfiguration().setMaxSearchFriendsLimit(maxFriendsLimitTextBox.getText());                
                sLibrary.getSTConfiguration().setWebServiceAccount(userTextBox.getText());
                // password is not encrypted in settings box
                sLibrary.getSTConfiguration().setWebServicePassword(false, passwordTextBox.getText());
                sLibrary.getSTConfiguration().setListenPort(listenPortTextBox.getText());
                sLibrary.getSTConfiguration().setListenAddress(addressTextBox.getText());
                sLibrary.getSTConfiguration().setConnTimeout(connectionTimeoutTextBox.getText());
                sLibrary.getSTConfiguration().setMaxConnections(maximumConnectionsTextFBox.getText());
                sLibrary.getSTConfiguration().setAutoConnect(String.valueOf(autoConnectCheckBox.isSelected()));
                sLibrary.getSTConfiguration().setCompleteFolder(completeDownloadFolderTextBox.getText());
                sLibrary.getSTConfiguration().setInCompleteFolder(incompleteDownloadFolderTextBox.getText());
                //sLibrary.getSTConfiguration().setMaxDownload(Integer.parseInt(this.mainForm.downloadRatioTextBox.getText()));
                //sLibrary.getSTConfiguration().setMaxUpload(Integer.parseInt(this.mainForm.uploadRatioTextBox.getText()));                
                sLibrary.getSTConfiguration().saveXMLFile();
                sLibrary.fireMessageBox(STLocalizer.getString("Saved"), STLocalizer.getString("Information"), JOptionPane.INFORMATION_MESSAGE);
                if (sLibrary.getFirstTimeOpened()) {
                    sLibrary.fireMessageBox(STLocalizer.getString("ReLaunch"), STLocalizer.getString("Information"), JOptionPane.INFORMATION_MESSAGE);
                    sLibrary.exitApplication();
                }
                sLibrary.STLogoutUser();
                sLibrary.reInitializeSTLibrary(false, false);
            }
        }
    }

    /**
     * Class to handle the WindowClosing event on the main frame.
     */
    private class WindowHandler extends WindowAdapter {
        /**
         * Just delegate to the ExitPhexAction acion.
         */
        @Override
        public void windowClosing(WindowEvent e) {
            STLibrary.getInstance().exitApplication();            
        }

        @Override
        public void windowIconified(WindowEvent e) {
            tray.addToSystemTray();
        }

        @Override
        public void windowDeiconified(WindowEvent e) {
            tray.removeFromSystemTray();
        }

        @Override
        public void windowOpened(WindowEvent e) {            
            File f = new File(sLibrary.getConfigurationFile());
            if (!f.exists()
              || sLibrary.getSTConfiguration().getCompleteFolder() == null
              || sLibrary.getSTConfiguration().getInCompleteFolder() == null
              || sLibrary.getSTConfiguration().getCompleteFolder().equals("null")
              || sLibrary.getSTConfiguration().getInCompleteFolder().equals("null")) {
                STLibrary.getInstance().setFirstTimeOpened();
                //if (UpdatePrefs.ShowConfigWizard.get().booleanValue())
                STConfigurationWizardDialog dialog = new STConfigurationWizardDialog(stMainForm);
                dialog.setVisible(true);
            }
        }
    }

    private class DesktopIndicatorHandler implements DesktopIndicatorListener {
        public void onDesktopIndicatorClicked(DesktopIndicator source) {
            setVisible(true);
            source.hideIndicator();
            if (STMainForm.this.getState() != JFrame.NORMAL) {
                STMainForm.this.setState(Frame.NORMAL);
            }
            STMainForm.this.requestFocus();
        }
    }

    private class SetCompleteDownloadDirectoryListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JFileChooser chooser = new JFileChooser();
            chooser.setSelectedFile(new File(completeDownloadFolderTextBox.getText()));
            chooser.setAcceptAllFileFilterUsed(false);
            chooser.setFileFilter(new DirectoryOnlyFileFilter());
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setMultiSelectionEnabled(false);
            chooser.setDialogTitle(
                    STLocalizer.getString("SelectDownloadDirectory"));
            chooser.setApproveButtonText(STLocalizer.getString("Select"));
            chooser.setApproveButtonMnemonic(
                    STLocalizer.getChar("SelectMnemonic"));
            int returnVal = chooser.showDialog(STMainForm.this, null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                String directory = chooser.getSelectedFile().getAbsolutePath();
                completeDownloadFolderTextBox.setText(directory);
            }
        }
    }

    private class SetIncompleteDownloadDirectoryListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JFileChooser chooser = new JFileChooser();
            chooser.setSelectedFile(new File(incompleteDownloadFolderTextBox.getText()));
            chooser.setAcceptAllFileFilterUsed(false);
            chooser.setFileFilter(new DirectoryOnlyFileFilter());
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setMultiSelectionEnabled(false);
            chooser.setDialogTitle(
                    STLocalizer.getString("SelectDownloadDirectory"));
            chooser.setApproveButtonText(STLocalizer.getString("Select"));
            chooser.setApproveButtonMnemonic(
                    STLocalizer.getChar("SelectMnemonic"));
            int returnVal = chooser.showDialog(STMainForm.this, null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                String directory = chooser.getSelectedFile().getAbsolutePath();
                incompleteDownloadFolderTextBox.setText(directory);
            }
        }
    }

}
