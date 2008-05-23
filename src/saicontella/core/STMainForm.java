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
import phex.utils.Localizer;
import phex.host.Host;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

import java.awt.event.*;
import java.awt.*;
import java.util.Vector;
import java.util.ArrayList;
import java.io.File;

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
    private ImageIcon myApplicationIcon;
    private ImageIcon myConnectIcon;
    private ImageIcon myDisconnectIcon;
    private ImageIcon myAdminIcon;
    private ImageIcon myExitIcon;

    // allPeersData[0] => friend names from search result
    // allPeersData[1] => friends ids from search result  
    private Vector[] allPeersData;
    // friendsListData[0] => friends names
    // friendsListData[1] => friends ids
    private Vector[] friendsListData;
    private STMainForm stMainForm;

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
        helpMenuAbout = new JMenuItem(STLibrary.STConstants.HELP_MENU_ABOUT);
        // Draw the order of the JMenuItems
        helpMenu.add(helpMenuUpdates);
        helpMenu.add(helpMenuAbout);
        // Adding the action listeners for each menu item
        helpMenuUpdates.addActionListener(menuHandler);
        helpMenuAbout.addActionListener(menuHandler);
        this.mainMenuBar.add(helpMenu);
    }

    public STMainForm(STLibrary sLibrary, DGuiSettings guiSettings) {
        super();

        this.sLibrary = sLibrary;
        this.sLibrary.setSTMainForm(this);
        
        UIDefaults uiDefaults = UIManager.getDefaults();                        
        uiDefaults.put("Menu.background", Color.BLACK);
        uiDefaults.put("Menu.foreground", Color.WHITE);
        uiDefaults.put("Menu.selectionBackground", Color.GRAY);

        uiDefaults.put("MenuBar.background", Color.BLACK);
        uiDefaults.put("MenuBar.foreground", Color.WHITE);
        uiDefaults.put("MenuBar.selectionBackground", Color.GRAY);

        uiDefaults.put("MenuItem.background", Color.BLACK);
        uiDefaults.put("MenuItem.foreground", Color.WHITE);
        uiDefaults.put("MenuItem.selectionBackground", Color.GRAY);

        uiDefaults.put("TextField.selectionBackground", Color.BLACK);
        uiDefaults.put("TextField.highlight", Color.BLACK);        
        uiDefaults.put("Tree.selectionBackground", Color.GRAY);
        uiDefaults.put("List.selectionBackground", Color.GRAY);
        uiDefaults.put("ComboBox.selectionBackground", Color.BLACK);

        uiDefaults.put("TabbedPane.background", Color.BLACK);
        uiDefaults.put("TabbedPane.foreground", Color.WHITE);
        uiDefaults.put("TabbedPane.selectHighlight", Color.GRAY);
        uiDefaults.put("TabbedPane.selected", Color.BLACK);
        uiDefaults.put("TabbedPane.shadow", Color.RED);
        uiDefaults.put("TabbedPane.borderHighlightColor", Color.RED);

        uiDefaults.put("Label.background", Color.BLACK);
        uiDefaults.put("Label.foreground", Color.GRAY);
        uiDefaults.put("Panel.background", Color.BLACK);
        uiDefaults.put("Panel.foreground", Color.GRAY);

        uiDefaults.put("OptionPane.background", Color.BLACK);
        uiDefaults.put("OptionPane.foreground", Color.GRAY);

        uiDefaults.put("OptionPane.errorDialog.titlePane.foreground", Color.GRAY);
        uiDefaults.put("OptionPane.messageForeground", Color.GRAY);
        uiDefaults.put("OptionPane.questionDialog.titlePane.foreground", Color.GRAY);
        uiDefaults.put("OptionPane.warningDialog.titlePane.foreground", Color.GRAY);
        uiDefaults.put("ComboBox.selectionBackground", Color.GRAY);

        // Phex Colors
        uiDefaults.put("activeCaptionBorder", Color.BLACK);
        uiDefaults.put("info", Color.WHITE);
        PhexColors.updateColors();

        //PhexColors.activatePhexColors4P2PServent();
        
        $$$setupUI$$$();

        this.drawMenus();

        STButtonsPanel buttonsPanel1 = new STButtonsPanel();
        STButtonsPanel buttonsPanel2 = new STButtonsPanel();
        JPanel emptyPanel1 = new JPanel();
        JPanel emptyPanel2 = new JPanel();

        STFWElegantPanel settingsElegantPanel = new STFWElegantPanel( "Settings", emptyPanel1);
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

        STFWElegantPanel friendsElegantPanel = new STFWElegantPanel( "Friends", emptyPanel2);
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
        
        this.myLogoImageLabel.setIcon(new ImageIcon(STResources.getStr("myLogoImage")));
        
        if (this.sLibrary.getSTConfiguration() != null) {
            if (this.sLibrary.getSTConfiguration().getAutoConnect()) {
                boolean connected = STLibrary.getInstance().STLoginUser();
                if (connected) {
                    this.sLibrary.getInstance().getGnutellaFramework().connectToPeers(STLibrary.getInstance().getPeersList());
                    this.fileMenuDisconnect.setEnabled(true);
                    this.fileMenuConnect.setEnabled(false);
                    if (STLibrary.getInstance().isCurrentUserAdministrator())
                        stMainForm.fileMenuAdmin.setVisible(true);
                    if (STLibrary.getInstance().isCurrentUserBanned()) {
                        sLibrary.fireMessageBox("Unfortunately you are not authorized to use the peer to peer service. Contact the administrator!", "Information", JOptionPane.INFORMATION_MESSAGE);
                        STLibrary.getInstance().STLogoutUser();
                        System.exit(-1);
                        stMainForm.disableMenus();
                        stMainForm.disableTabs();
                    }                    
                    STLibrary.getInstance().updateP2PServent(true);
                }
            }
            this.setTitle(STResources.getStr("Application.name") + " v" + STResources.getStr("Application.version") + " (" + sLibrary.getSTConfiguration().getListenAddress() + ":" + sLibrary.getSTConfiguration().getListenPort() + ")");
        }
        this.guiSettings = guiSettings;

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowHandler());

        pack();
        initFrameSize();

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
                    STLibrary.getInstance().fireMessageBox("You need to connect first!", "ERROR", JOptionPane.ERROR_MESSAGE);
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
    }

    public void setMyFriendsAdImageLabelIcon(ImageIcon icon) {
        icon = STLibrary.getInstance().resizeMyImageIcon(icon, 200, 300);
        this.myFriendsAdsLabel.setIcon(icon);
        this.myFriendsAdsLabel.repaint();
    }

    public void setAdImageLabelIcon(ImageIcon icon) {
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        double h = size.getHeight() - (size.getHeight() / 2) + 0.2*size.getHeight();
        if (h < 600) {            
            icon = STLibrary.getInstance().resizeMyImageIcon(icon, icon.getIconWidth(), 70);
        }        
        this.myAdsImageLabel.setIcon(icon);
        this.myAdsImageLabel.repaint();        
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
                libraryTab.updateMyFriendsList();
            }
            else if (currentPanel.equals(myFriendsTab)) {
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
                if (friendsListData == null)
                    friendsListData = new Vector[2];                                
                if (friendsListData[0] != null)
                    listFriends.setListData(friendsListData[0]);
                if (retrievedFromWS) saveFriendsListInXML();

                // Viewing pending friends...
                if (sLibrary.isConnected()) {
                    Vector[] pendingFriends = sLibrary.fetchPendingFriends();
                    STFriendDialog friendDlg = new STFriendDialog(stMainForm, pendingFriends);
                    friendDlg.setIconImage(myApplicationIcon.getImage());
                    friendDlg.setTitle("Pending friends...");
                    friendDlg.setLocationRelativeTo(null);                    
                    friendDlg.pack();
                    friendDlg.setVisible(true);
                    stMainForm.mainTabbedPanel.setSelectedIndex(STMainForm.NETWORK_TAB_INDEX);
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

    private void initFrameSize() {
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        int w = (int)(size.getWidth() - 0.25*size.getWidth());
        if (w < 700)
            w = 700;
        double h = size.getHeight() - (size.getHeight() / 2) + 0.2*size.getHeight();
        if (h < 600) {
            h = 600;
            this.tabsPanel.setPreferredSize(new Dimension(w, 470));
            this.myLogoImageLabel.setIcon(STLibrary.getInstance().resizeMyImageIcon(new ImageIcon(STResources.getStr("myLogoImage")), 70, 70));            
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
            myPeersIcon = STLibrary.getInstance().resizeMyImageIcon(new ImageIcon(STResources.getStr("myPeersIcon.ico")), 15, 15);
            mySearchIcon = STLibrary.getInstance().resizeMyImageIcon(new ImageIcon(STResources.getStr("mySearchIcon.ico")), 15, 15);
            myDownloadsIcon = STLibrary.getInstance().resizeMyImageIcon(new ImageIcon(STResources.getStr("myDownloadsIcon.ico")), 15, 15);
            myUploadsIcon = STLibrary.getInstance().resizeMyImageIcon(new ImageIcon(STResources.getStr("myUploadsIcon.ico")), 15, 15);
            mySharesIcon = STLibrary.getInstance().resizeMyImageIcon(new ImageIcon(STResources.getStr("mySharesIcon.ico")), 15, 15);
            mySettingsIcon = STLibrary.getInstance().resizeMyImageIcon(new ImageIcon(STResources.getStr("mySettingsIcon.ico")), 15, 15);
            myFriendsIcon = STLibrary.getInstance().resizeMyImageIcon(new ImageIcon(STResources.getStr("myFriendsIcon.ico")), 15, 15);
            myFriendsAddIcon = STLibrary.getInstance().resizeMyImageIcon(new ImageIcon(STResources.getStr("myFriendsAddIcon.ico")), 15, 15);
            myFriendsDeleteIcon = STLibrary.getInstance().resizeMyImageIcon(new ImageIcon(STResources.getStr("myFriendsDeleteIcon.ico")), 15, 15);
            myApplicationIcon = STLibrary.getInstance().resizeMyImageIcon(new ImageIcon(STResources.getStr("myApplicationIcon.ico")), 15, 15);
            myConnectIcon = STLibrary.getInstance().resizeMyImageIcon(new ImageIcon(STResources.getStr("myConnectIcon.ico")), 15, 15);
            myDisconnectIcon = STLibrary.getInstance().resizeMyImageIcon(new ImageIcon(STResources.getStr("myDisconnectIcon.ico")), 15, 15);
            myAdminIcon = STLibrary.getInstance().resizeMyImageIcon(new ImageIcon(STResources.getStr("myAdminIcon.ico")), 15, 15);            
            myExitIcon = STLibrary.getInstance().resizeMyImageIcon(new ImageIcon(STResources.getStr("myExitIcon.ico")), 15, 15);
                    
            this.setIconImage(myApplicationIcon.getImage());
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
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        // STNetwork Tab
        networkTab = new STNetworkTab();
        networkTab.initComponent(guiSettings);
        this.mainTabbedPanel.add(networkTab, NETWORK_TAB_INDEX);
        this.mainTabbedPanel.setTitleAt(NETWORK_TAB_INDEX, "myPeers");
        this.mainTabbedPanel.setIconAt(NETWORK_TAB_INDEX, myPeersIcon);

        // STSearch Tab
        searchContainer = sLibrary.getGnutellaFramework().getServent().getQueryService().getSearchContainer();
        searchTab = new STSearchTab(searchContainer, sLibrary.getGnutellaFramework().getServent().getQueryService().getSearchFilterRules());
        searchTab.initComponent(guiSettings);
        this.mainTabbedPanel.add(searchTab, SEARCH_TAB_INDEX);
        this.mainTabbedPanel.setTitleAt(SEARCH_TAB_INDEX, "mySearch");
        this.mainTabbedPanel.setIconAt(SEARCH_TAB_INDEX, mySearchIcon);

        //  SWDownload Tab
        swDownloadTab = new STSWDownloadTab();
        swDownloadTab.initComponent(guiSettings);
        this.mainTabbedPanel.add(swDownloadTab, DOWNLOAD_TAB_INDEX);
        this.mainTabbedPanel.setTitleAt(DOWNLOAD_TAB_INDEX, "myDownloads");
        this.mainTabbedPanel.setIconAt(DOWNLOAD_TAB_INDEX, myDownloadsIcon);

        //  STUpload Tab
        uploadTab = new STUploadTab();
        uploadTab.initComponent(guiSettings);
        this.mainTabbedPanel.add(uploadTab, UPLOAD_TAB_INDEX);
        this.mainTabbedPanel.setTitleAt(UPLOAD_TAB_INDEX, "myUploads");
        this.mainTabbedPanel.setIconAt(UPLOAD_TAB_INDEX, myUploadsIcon);

        //  STLibrary Tab
        libraryTab = new STLibraryTab(this);
        libraryTab.initComponent(guiSettings);
        this.mainTabbedPanel.add(libraryTab, LIBRARY_TAB_INDEX);
        this.mainTabbedPanel.setTitleAt(LIBRARY_TAB_INDEX, "myShares");
        this.mainTabbedPanel.setIconAt(LIBRARY_TAB_INDEX, mySharesIcon);

        //  Settings Tab
        this.mainTabbedPanel.add(mySettingsTab, SETTINGS_TAB_INDEX);
        this.mainTabbedPanel.setTitleAt(SETTINGS_TAB_INDEX, "mySettings");
        this.mainTabbedPanel.setIconAt(SETTINGS_TAB_INDEX, mySettingsIcon);

        //  myFriends Tab
        this.mainTabbedPanel.add(myFriendsTab, FRIENDS_TAB_INDEX);
        this.mainTabbedPanel.setTitleAt(FRIENDS_TAB_INDEX, "myFriends");
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

        // Adding all the local file shares...
        //this.sLibrary.addFileShares(this.libraryTab);

        this.initializeToolsTabValues();
        this.getContentPane().add(this.outerPanel);
    }

    private class ClickMenuActionHandler implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            JMenuItem sourceObject = (JMenuItem) e.getSource();
            if (sourceObject.getText().equals(STLibrary.STConstants.FILE_MENU_CONNECT)) {
                stMainForm.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                boolean connected = STLibrary.getInstance().STLoginUser();
                stMainForm.setCursor(Cursor.getDefaultCursor());
                if (connected) {
                    STLibrary.getInstance().getGnutellaFramework().connectToPeers(STLibrary.getInstance().getPeersList());
                    stMainForm.fileMenuDisconnect.setEnabled(true);
                    stMainForm.fileMenuConnect.setEnabled(false);
                    if (STLibrary.getInstance().isCurrentUserAdministrator())
                        stMainForm.fileMenuAdmin.setVisible(true);
                    if (STLibrary.getInstance().isCurrentUserBanned()) {
                        sLibrary.fireMessageBox("Unfortunately you are not authorized to use the peer to peer service. Contact the administrator!", "Information", JOptionPane.INFORMATION_MESSAGE);
                        STLibrary.getInstance().STLogoutUser();
                        System.exit(-1);
                        stMainForm.disableMenus();
                        stMainForm.disableTabs();
                    }
                    STLibrary.getInstance().updateP2PServent(true);
                }
            } else if (sourceObject.getText().equals(STLibrary.STConstants.HELP_MENU_ABOUT)) {
                stMainForm.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                STAboutDialog aboutDlg = new STAboutDialog();
                aboutDlg.setTitle("About");
                aboutDlg.setSize(400, 400);
                aboutDlg.setLocationRelativeTo(null);
                aboutDlg.pack();
                aboutDlg.setVisible(true);
                aboutDlg.setIconImage(myApplicationIcon.getImage());
                stMainForm.setCursor(Cursor.getDefaultCursor());
            } else if (sourceObject.getText().equals(STLibrary.STConstants.FILE_MENU_DISCONNECT)) {
                stMainForm.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                STLibrary.getInstance().STLogoutUser();
                stMainForm.setCursor(Cursor.getDefaultCursor());
                stMainForm.fileMenuDisconnect.setEnabled(false);
                stMainForm.fileMenuConnect.setEnabled(true);
                stMainForm.fileMenuAdmin.setVisible(false);
            } else if (sourceObject.getText().equals(STLibrary.STConstants.FILE_MENU_ADMINISTRATOR)) {
                STAdminDialog adminDlg = new STAdminDialog();
                adminDlg.setTitle("Administration");
                adminDlg.setLocationRelativeTo(null);
                adminDlg.pack();
                adminDlg.setVisible(true);
                adminDlg.setIconImage(myApplicationIcon.getImage());
            } else if (sourceObject.getText().equals(STLibrary.STConstants.FILE_MENU_EXIT)) {
                STLibrary.getInstance().STLogoutUser();
                System.exit(0);
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
                String friendIpAddress = STLibrary.getInstance().getGnutellaFramework().getIpAddressFromFriendName(friend.getFriendName());
                if (friendIpAddress != null)
                    friend.setIPAddress(friendIpAddress);
                if (friendId != null)
                    friend.setFriendId(friendId.toString());
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
                    STLibrary.getInstance().fireMessageBox("You need to connect first!", "ERROR", JOptionPane.ERROR_MESSAGE);
                    return;
                }                
                stMainForm.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                // get the selected index from the RIGHT JList (listFriends)
                Object[] friendNames = listFriends.getSelectedValues();
                if (friendNames == null) {
                    stMainForm.setCursor(Cursor.getDefaultCursor());
                    STLibrary.getInstance().fireMessageBox("Please select at leaset one friend to remove!", "Error", JOptionPane.ERROR);
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
                    STLibrary.getInstance().fireMessageBox("You need to connect first!", "ERROR", JOptionPane.ERROR_MESSAGE);
                    return;
                }                
                stMainForm.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                // get the selected index from the LEFT JList (listAllPeers)
                Object[] friendNames = listAllPeers.getSelectedValues();
                if (friendNames == null) {
                    stMainForm.setCursor(Cursor.getDefaultCursor());
                    STLibrary.getInstance().fireMessageBox("Please select at least one friend to add!", "Error", JOptionPane.ERROR);
                    return;
                }
                int[] indices = listAllPeers.getSelectedIndices();
                // adding the selected friends from the left JList to the right JList
                for (int i = 0; i < indices.length; i++) {
                    if (STLibrary.getInstance().isFriendAlreadyInList(friendNames[i].toString(), friendsListData[0])) {
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
                sLibrary.fireMessageBox("Saved.", "Information", JOptionPane.INFORMATION_MESSAGE);
            } else if (sourceObject.getText().equals("Save Settings")) {
                sLibrary.getSTConfiguration().setMaxSearchFriendsLimit(maxFriendsLimitTextBox.getText());                
                sLibrary.getSTConfiguration().setWebServiceAccount(userTextBox.getText());
                sLibrary.getSTConfiguration().setWebServicePassword(passwordTextBox.getText());
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
                sLibrary.fireMessageBox("Saved.", "Information", JOptionPane.INFORMATION_MESSAGE);
                sLibrary.STLogoutUser();
                sLibrary.reInitializeSTLibrary(false);                
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
            //ExitPhexAction.performCloseGUIAction();
            STLibrary.getInstance().STLogoutUser();
            System.exit(0);
        }

        @Override
        public void windowOpened(WindowEvent e) {
            File f = new File(STResources.getStr("Application.configurationFile"));
            if (!f.exists()) {
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
                    Localizer.getString("SelectDownloadDirectory"));
            chooser.setApproveButtonText(Localizer.getString("Select"));
            chooser.setApproveButtonMnemonic(
                    Localizer.getChar("SelectMnemonic"));
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
                    Localizer.getString("SelectDownloadDirectory"));
            chooser.setApproveButtonText(Localizer.getString("Select"));
            chooser.setApproveButtonMnemonic(
                    Localizer.getChar("SelectMnemonic"));
            int returnVal = chooser.showDialog(STMainForm.this, null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                String directory = chooser.getSelectedFile().getAbsolutePath();
                incompleteDownloadFolderTextBox.setText(directory);
            }
        }
    }

}
