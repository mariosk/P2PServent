package saicontella.phex.stlibrary;

/**
 * SaiconTella project
 *
 * @author Marios Karagiannopoulos (mariosk@gmail.com)
 *
 * February 2008
 */

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;
import java.util.ArrayList;
import java.io.File;

import javax.swing.*;
import javax.swing.event.*;

import phex.common.QueryRoutingTable;
import phex.common.ThreadPool;
import phex.common.format.NumberFormatUtils;
import phex.event.PhexEventTopics;
import phex.gui.actions.FWAction;
import phex.gui.actions.GUIActionPerformer;
import phex.gui.common.FWPopupMenu;
import phex.gui.common.FWToolBar;
import phex.gui.common.GUIRegistry;
import phex.gui.common.GUIUtils;
import phex.gui.common.MainFrame;
import phex.gui.common.table.FWSortedTableModel;
import phex.gui.common.table.FWTable;
import phex.gui.tabs.FWTab;
import phex.gui.tabs.library.SharedFilesTableModel;
import phex.gui.tabs.library.LibraryNode;
import phex.servent.Servent;
import phex.share.ShareFile;
import phex.share.SharedFilesService;
import phex.share.SharedDirectory;
import phex.xml.sax.gui.DGuiSettings;
import phex.xml.sax.gui.DTable;
import phex.security.PhexSecurityManager;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import saicontella.core.*;
import saicontella.phex.STFWElegantPanel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bushe.swing.event.annotation.EventTopicSubscriber;

public class STLibraryTab extends FWTab
{
    private static final String SHARED_FILES_TABLE_IDENTIFIER = "SharedFilesTable";
    private static Log logger = LogFactory.getLog("saicontella.phex.STLibraryTab");
    
    private STLibraryTreePane libraryTreePane;

    private JLabel sharedFilesLabel;

    private FWTable sharedFilesTable;

    private FWPopupMenu fileTablePopup;

    private JScrollPane sharedFilesTableScrollPane;

    private Object[] friendsCheckBoxes;
    private ArrayList myFriendsList;
    private JLabel friendsStatusLabel;
    private STLibraryFriendsList friendsList;    
    private int selectedFolderIndex;
    private JScrollPane friendsScrollPane;

    private SharedFilesTableModel sharedFilesModel;

    private PanelBuilder tabBuilder;
    private CellConstraints cc;
    private STMainForm mainForm;
    private STLibraryTab thisObject;

    public STLibraryTab(STMainForm mainForm)
    {
        super(MainFrame.LIBRARY_TAB_ID, STLocalizer.getString("Library"),
            GUIRegistry.getInstance().getPlafIconPack().getIcon("Library.Tab"),
            STLocalizer.getString("TTTLibrary"), STLocalizer
                .getChar("LibraryMnemonic"), KeyStroke.getKeyStroke(STLocalizer
                .getString("LibraryAccelerator")), MainFrame.LIBRARY_TAB_INDEX);
        Servent.getInstance().getEventService().processAnnotations( this );
        this.mainForm = mainForm;
        this.selectedFolderIndex = -1;
        this.thisObject = this;

        for (int i = 0; i < STLibrary.getInstance().getSTConfiguration().getFolders().size(); i++) {
            this.applyIpSystemRulesLists(i);
        }

        //STLibrary.getInstance().addFileShares(this);
        
    }

    public void setSelectedFolderIndex(int index) {
        this.selectedFolderIndex = index;
    }

    public JList getFriendsList() {
        return this.friendsList;
    }

    public Object[] getFriendsCheckBoxes() {
        return this.friendsCheckBoxes;
    }

    private void applyIpSystemRulesLists(int folderIndex)
    {
        if (folderIndex < 0)
            return;        
        STFolder folder = (STFolder) STLibrary.getInstance().getSTConfiguration().getFolders().get(folderIndex);
        File dir = new File(folder.getFolderName());
        if ( dir != null )
        {
            SharedDirectory directory = STLibrary.getInstance().getGnutellaFramework().getServent().getSharedFilesService().getSharedDirectory(dir);
            if (directory == null)
            {
                return;
            }
            PhexSecurityManager.getInstance().removeIpSystemRuleListFromSharedResource(directory);
            for (int i = 0; i < folder.getFriends().size(); i++) {
                STFriend friend = (STFriend) folder.getFriends().get(i);
                Object[] userInfo = STLibrary.getInstance().getGnutellaFramework().getIpAddressFromFriendName(friend.getFriendName());
                if (userInfo != null) {
                    String friendIpAddress = (String)userInfo[0];
                    int port = ((Integer)userInfo[1]).intValue();
                    if (friendIpAddress == null)
                        friendIpAddress = friend.getIPAddress();
                    if (friendIpAddress != null) {
                        if (!friendIpAddress.equalsIgnoreCase("null")) {
                            PhexSecurityManager.getInstance().addIpSystemRuleListToSharedResource(directory, friendIpAddress, port);
                        }
                    }
                }
                else {
                    PhexSecurityManager.getInstance().addIpSystemRuleListToSharedResource(directory, "127.0.0.1", -1);
                }
            }
        }
    }

    private void selectCheckBoxWithName(String name) {
        for (int i = 0; i < friendsCheckBoxes.length; i++) {
            JCheckBox box = (JCheckBox)friendsCheckBoxes[i];
            if (box.getText().equalsIgnoreCase(name))
                box.setSelected(true);            
        }
    }

    private void updateFriendsStatusLabel(STFolder folder) {
        if (folder.getFriends().size() == 0) {
            this.friendsStatusLabel.setText("The folder is opened to all users");
        }
        else {
            this.friendsStatusLabel.setText("The folder is limited to your selected friends");
        }
        this.friendsStatusLabel.setToolTipText("Folder: " + folder.getFolderName());
    }
    
    public void fillFriedsList(int folderIndex) {
        clearFriendsList();        
        if (folderIndex < 0)
            return;
        STFolder folder = (STFolder) STLibrary.getInstance().getSTConfiguration().getFolders().get(folderIndex);
        for (int i = 0; i < folder.getFriends().size(); i++) {
            STFriend friend = (STFriend) folder.getFriends().get(i);
            selectCheckBoxWithName(friend.getFriendName());            
        }
        updateFriendsStatusLabel(folder);
        friendsList.setListData(friendsCheckBoxes);
        friendsList.repaint();
        applyIpSystemRulesLists(folderIndex);        
    }
    
    public void clearFriendsList() {
        for (int i = 0; i < friendsCheckBoxes.length; i++) {
            JCheckBox box = (JCheckBox)friendsCheckBoxes[i];
            box.setSelected(false);            
        }
        friendsList.setListData(friendsCheckBoxes);
        friendsList.repaint();
    }

    public boolean addFriendInFriendsList(String name) {
        try {
            STFolder folder = (STFolder) STLibrary.getInstance().getSTConfiguration().getFolders().get(this.selectedFolderIndex);
            folder.addFriend(new STFriend(name));
            this.applyIpSystemRulesLists(this.selectedFolderIndex);
            STLibrary.getInstance().getSTConfiguration().saveXMLFile();
            updateFriendsStatusLabel(folder);
            return true;
        }
        catch (Exception ex) {
            logger.error(ex.getMessage());
        }                
        return false;
    }

    public void deleteFriendInFriendsList(String name) {
        try {            
            STFolder folder = (STFolder) STLibrary.getInstance().getSTConfiguration().getFolders().get(this.selectedFolderIndex);
            for (int i = 0; i < folder.getFriends().size(); i++) {
                if (((STFriend)folder.getFriends().get(i)).getFriendName().equals(name)) {
                    folder.getFriends().remove(i);
                    break;
                }
            }
            updateFriendsStatusLabel(folder);
            this.applyIpSystemRulesLists(this.selectedFolderIndex);
            STLibrary.getInstance().getSTConfiguration().saveXMLFile();
        }
        catch (Exception ex) {
            logger.error(ex.getMessage());    
        }
    }

    public void initComponent(DGuiSettings guiSettings)
    {
        cc = new CellConstraints();
        FormLayout tabLayout = new FormLayout("2dlu, fill:d:grow, 2dlu", // columns
            "2dlu, fill:p:grow, 2dlu"); //rows
        tabBuilder = new PanelBuilder(tabLayout, this);
        JPanel contentPanel = new JPanel();
        STFWElegantPanel elegantPanel = new STFWElegantPanel( STLocalizer.getString("Library"),
            contentPanel );
        tabBuilder.add(elegantPanel, cc.xy(2, 2));
        
        FormLayout contentLayout = new FormLayout(
            "fill:d:grow, p, p, p, p, p", // columns
            "fill:d:grow, fill:d:grow"); //rows
        PanelBuilder contentBuilder = new PanelBuilder(contentLayout, contentPanel);
                
        MouseHandler mouseHandler = new MouseHandler();

        libraryTreePane = new STLibraryTreePane( this );
        libraryTreePane.addTreeSelectionListener(
            new SelectionHandler() );
        JPanel tablePanel = createTablePanel( guiSettings, mouseHandler );
        JButton dummyButton = new JButton("");
        tablePanel.setBackground(dummyButton.getBackground());
        
        JSplitPane splitPane = new JSplitPane( JSplitPane.HORIZONTAL_SPLIT,
            libraryTreePane, tablePanel );
        splitPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        splitPane.setDividerSize(4);
        splitPane.setOneTouchExpandable(false);
        contentBuilder.add(splitPane, cc.xywh(1, 1, 6, 1));

        STButtonsPanel buttonsPanel = new STButtonsPanel();
        contentBuilder.add( buttonsPanel, cc.xy( 6, 2 ) );

        sharedFilesLabel = new JLabel( " " );
        sharedFilesLabel.setHorizontalAlignment( JLabel.RIGHT );
        elegantPanel.addHeaderPanelComponent(sharedFilesLabel, BorderLayout.EAST );

        fileTablePopup = new FWPopupMenu();
        
        FWAction action;
        
        action = getTabAction( RESCAN_ACTION_KEY );
        fileTablePopup.addAction( action );
        libraryTreePane.appendPopupAction( action );

        /*
        action = getTabAction( ADD_FRIEND_ACTION_KEY );
        fileTablePopup.addAction( action );
        libraryTreePane.appendPopupAction( action );

        action = getTabAction( DEL_FRIEND_ACTION_KEY );
        fileTablePopup.addAction( action );
        libraryTreePane.appendPopupAction( action );
        */
    }
/*
    class JCheckBoxChangeListener implements ChangeListener {
        public void stateChanged(ChangeEvent e)
        {       
        }
    }
*/
    public void updateMyFriendsList() {
        this.myFriendsList = STLibrary.getInstance().getSTConfiguration().getMyFriends();
        if (friendsCheckBoxes != null) {
            friendsList.removeAll();
            friendsList.revalidate();
        }

        friendsCheckBoxes = new Object[this.myFriendsList.size()];
        for (int i = 0; i < this.myFriendsList.size(); i++) {
            friendsCheckBoxes[i] = new JCheckBox(((STFriend)this.myFriendsList.get(i)).getFriendName());
            //((JCheckBox)friendsCheckBoxes[i]).addChangeListener(new JCheckBoxChangeListener());
        }        
        friendsList.setListData(friendsCheckBoxes);
        friendsList.repaint();
    }

    private JPanel createTablePanel(DGuiSettings guiSettings,
        MouseHandler mouseHandler )
    {
        JPanel panel = new JPanel();
        CellConstraints cc = new CellConstraints();
        //FormLayout layout = new FormLayout("fill:d:grow", // columns
            //"fill:d:grow, 1dlu, p"); //rows
        // 4 columns x 4 rows of 100 pixels each ;)
        //FormLayout layout = new FormLayout("100px, 100px, 100px, 100px", "100px, 100px, 100px, 100px");
         FormLayout layout = new FormLayout("fill:d:grow, fill:d:grow", // columns
         "fill:d:grow, 1dlu, p, p"); // rows

        PanelBuilder tabBuilder = new PanelBuilder(layout, panel);
        
        sharedFilesModel = new SharedFilesTableModel();
        sharedFilesTable = new FWTable(new FWSortedTableModel(
            sharedFilesModel) );
        GUIUtils.updateTableFromDGuiSettings( guiSettings, sharedFilesTable, 
            SHARED_FILES_TABLE_IDENTIFIER );
        
        sharedFilesTable.activateAllHeaderActions();
        sharedFilesTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        sharedFilesTable.addMouseListener(mouseHandler);
        sharedFilesTable.getSelectionModel().addListSelectionListener(
            new SelectionHandler());
        sharedFilesTableScrollPane = FWTable
            .createFWTableScrollPane(sharedFilesTable);

        // adding in col: 1, row: 1
        tabBuilder.add(sharedFilesTableScrollPane, cc.xy(1, 1));
        
        FWToolBar shareToolbar = new FWToolBar(FWToolBar.HORIZONTAL);
        shareToolbar.setBorderPainted(false);
        shareToolbar.setFloatable(false);
        // adding in col: 1, row: 3
        tabBuilder.add(shareToolbar, cc.xy(1, 3));

        friendsList = new STLibraryFriendsList(this);
        this.updateMyFriendsList();
        this.friendsStatusLabel = new JLabel("");                        

        friendsScrollPane = new JScrollPane(friendsList);
        // adding in col: 2, row: 1
        tabBuilder.add(friendsScrollPane, cc.xy(2, 1));
        // adding in col: 2, row: 3
        tabBuilder.add(this.friendsStatusLabel, cc.xy(2, 3));

        FWAction action;

        action = new RescanAction();
        addTabAction( RESCAN_ACTION_KEY, action );
        shareToolbar.addAction( action );

        /*
        action = new AddFriendAction();
        addTabAction( ADD_FRIEND_ACTION_KEY, action );
        shareToolbar.addAction( action );

        action = new DelFriendAction();
        addTabAction( DEL_FRIEND_ACTION_KEY, action );
        shareToolbar.addAction( action );
        */
        
        return panel;
    }

    /**
     * This is overloaded to update the table scroll pane on every UI update.
     */
    @Override
    public void updateUI()
    {
        super.updateUI();
        if (sharedFilesTableScrollPane != null)
        {
            FWTable.updateFWTableScrollPane(sharedFilesTableScrollPane);
        }
    }
    
    @EventTopicSubscriber(topic=PhexEventTopics.Share_Update)
    public void onShareUpdateEvent( String topic, Object event )
    {
        EventQueue.invokeLater( new Runnable()
        {
            public void run()
            {
                SharedFilesService filesService = Servent.getInstance().getSharedFilesService();
                QueryRoutingTable qrt = filesService.getLocalRoutingTable();
                
                String label = STLocalizer.getFormatedString( "LibraryTab_StatsHeader",
                    NumberFormatUtils.formatNumber( filesService.getFileCount() ),
                    NumberFormatUtils.formatSignificantByteSize( filesService.getTotalFileSizeInKb() * 1024L ),
                    NumberFormatUtils.formatDecimal( qrt.getFillRatio(), 2 ),
                    NumberFormatUtils.formatDecimal( qrt.getTableSize()/1024.0, 0 ) );            
                sharedFilesLabel.setText( label );
            }
        });
    }

    //////////////////////////////////////////////////////////////////////////
    /// XML serializing and deserializing
    //////////////////////////////////////////////////////////////////////////

    /**
     * {@inheritDoc}
     */
    @Override
    public void appendDGuiSettings(DGuiSettings dSettings)
    {
        super.appendDGuiSettings(dSettings);
        DTable dTable = GUIUtils.createDTable( sharedFilesTable, SHARED_FILES_TABLE_IDENTIFIER );        
        dSettings.getTableList().getTableList().add(dTable);
    }

    //////////////////////////////////////////////////////////////////////////
    /// Actions
    //////////////////////////////////////////////////////////////////////////
    private static final String DEL_FRIEND_ACTION_KEY = "DelFriendAction";

    public class DelFriendAction extends FWAction
    {
        DelFriendAction()
        {
            super(STLocalizer.getString("DelFriend"), mainForm.getFriendsDeleteIcon(), STLocalizer.getString("DelFriend"));
        }

        public void actionPerformed(ActionEvent e)
        {
            if (selectedFolderIndex < 0) {
                STLibrary.getInstance().fireMessageBox(STLocalizer.getString("DelShareFolder"), STLocalizer.getString("Error"), JOptionPane.ERROR_MESSAGE);
                return;
            }
            // Deleting a friend from the list of the added already friends inside the friendsList
            /*
            Vector data = friendsVector;
            if (data == null || data.size() == 0) {            
                STLibrary.getInstance().fireMessageBox("There are no friends to delete!", "ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }
            */
            /*
            STFriendDialog friendDlg = new STFriendDialog(thisObject, this, friendsVector);
            friendDlg.setTitle("Deleting a friend...");
            GUIUtils.centerAndSizeWindow(friendDlg, 7, getHeight());
            friendDlg.pack();
            friendDlg.setVisible(true);
            */
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void refreshActionState()
        {
        }
    }

    private static final String ADD_FRIEND_ACTION_KEY = "AddFriendAction";

    public class AddFriendAction extends FWAction
    {
        AddFriendAction()
        {
            super(STLocalizer.getString("AddFriend"), mainForm.getFriendsAddIcon(), STLocalizer.getString("AddFriend"));
        }

        public void actionPerformed(ActionEvent e)
        {
            if (selectedFolderIndex < 0) {
                STLibrary.getInstance().fireMessageBox(STLocalizer.getString("ShareFolder"), STLocalizer.getString("Error"), JOptionPane.ERROR_MESSAGE);
                return;
            }            
            // Adding a friend from the list of the configured friends inside saicontella.xml
            Vector data = STLibrary.getInstance().getSTConfiguration().getMyFriendsVectorData();
            if (data == null || data.size() == 0) {
                STLibrary.getInstance().fireMessageBox(STLocalizer.getString("NoFriendsToAdd"), STLocalizer.getString("Error"), JOptionPane.ERROR_MESSAGE);
                return;
            }
            /*
            STFriendDialog friendDlg = new STFriendDialog(thisObject, this, STLibrary.getInstance().getSTConfiguration().getMyFriendsVectorData());
            friendDlg.setTitle("Adding a friend...");
            GUIUtils.centerAndSizeWindow(friendDlg, 7, getHeight());
            friendDlg.pack();
            friendDlg.setVisible(true);
            */                                                           
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void refreshActionState()
        {
        }
    }

    private static final String RESCAN_ACTION_KEY = "RescanAction";
    
    class RescanAction extends FWAction
    {
        RescanAction()
        {
            super(STLocalizer.getString("LibraryTab_Rescan"),
                GUIRegistry.getInstance().getPlafIconPack().getIcon(
                "Library.Refresh"), STLocalizer.getString("LibraryTab_TTTRescan"));
        }
        
        public void actionPerformed(ActionEvent e)
        {
            libraryTreePane.updateFileSystem();
            GUIActionPerformer.rescanSharedFiles();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void refreshActionState()
        {
        }
    }


    //////////////////////////////////////////////////////////////////////////
    /// Listeners
    //////////////////////////////////////////////////////////////////////////

    private class SelectionHandler implements ListSelectionListener,
        TreeSelectionListener
    {
        public void valueChanged(ListSelectionEvent e)
        {
            if (!e.getValueIsAdjusting())
            {
                refreshTabActions();
            }
        }

        public void valueChanged( TreeSelectionEvent e )
        {
            final Object lastPathComponent = e.getPath().getLastPathComponent();

            // run in separate thread.. not event thread to make sure tree selection
            // changes immediately while table needs a little more to update.
            ThreadPool.getInstance().addJob( new Runnable()
            {
                public void run()
                {
                    clearFriendsList();
                    sharedFilesTable.clearSelection();
                    if ( lastPathComponent instanceof LibraryNode)
                    {
                        LibraryNode node = (LibraryNode) lastPathComponent;
                        if (node.getSystemFile() == null)
                                selectedFolderIndex = -1;
                        // then fill data..
                        sharedFilesModel.setDisplayDirectory( 
                            ((LibraryNode)lastPathComponent).getSystemFile() );
                    }
                    else
                    {
                        sharedFilesModel.setDisplayDirectory( null );
                    }
                }
            }, "LibraryTableUpdate" );
            refreshTabActions();
        }
    }

    private class MouseHandler extends MouseAdapter implements MouseListener
    {
        private Log logger = LogFactory.getLog("saicontella.phex.stlibrary.STLibraryTab.MouseHandler");
        
        /**
         * {@inheritDoc}
         */
        @Override
        public void mouseClicked(MouseEvent e)
        {
            Component source = (Component)e.getSource();
            if (source == sharedFilesTable ) {
                Object selectedObj = sharedFilesModel.getValueAt(sharedFilesTable.getSelectedRow(), 0);
                String folderStr = (String)sharedFilesModel.getValueAt(sharedFilesTable.getSelectedRow(), 1);
                if (selectedObj instanceof ShareFile) {
                    ShareFile fileSelected = (ShareFile)selectedObj;
                    logger.debug("SELECTED FILE: " + fileSelected.getSystemFile().toString());
                    int folderIndex = STLibrary.getInstance().getSTConfiguration().getSTFolderIndex(folderStr);
                    if (folderIndex >= 0) {
                        int fileIndex = STLibrary.getInstance().getSTConfiguration().getSTFileIndex(fileSelected.getSystemFile().toString(), folderIndex);
                        logger.debug("SELECTED FOLDER INDEX: " + folderStr + " => " + fileIndex);
                        setSelectedFolderIndex(folderIndex);
                        fillFriedsList(folderIndex);
                    }
                }
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void mouseReleased(MouseEvent e)
        {
            if (e.isPopupTrigger())
            {
                popupMenu((Component) e.getSource(), e.getX(), e.getY());
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void mousePressed(MouseEvent e)
        {
            if (e.isPopupTrigger())
            {
                popupMenu((Component) e.getSource(), e.getX(), e.getY());
            }
        }

        private void popupMenu(Component source, int x, int y)
        {
            if (source == sharedFilesTable )
            {
                refreshTabActions();
                fileTablePopup.show(source, x, y);
            }
        }
    }
}