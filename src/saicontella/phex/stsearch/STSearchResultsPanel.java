package saicontella.phex.stsearch;

/**
 * SaiconTella project
 *
 * @author Marios Karagiannopoulos (mariosk@gmail.com)
 *
 * February 2008
 */

import java.awt.Component;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.tree.TreePath;

import phex.common.ThreadPool;
import phex.common.address.DestAddress;
import phex.common.log.NLogger;
import phex.download.RemoteFile;
import phex.download.swarming.SWDownloadFile;
import phex.download.swarming.SwarmingManager;
import phex.gui.actions.BanHostActionUtils;
import phex.gui.actions.FWAction;
import phex.gui.actions.GUIActionPerformer;
import phex.gui.common.BrowserLauncher;
import phex.gui.common.FWToolBar;
import phex.gui.common.GUIRegistry;
import phex.gui.common.GUIUtils;
import phex.gui.common.table.FWTable;
import phex.gui.common.table.FWTableColumn;
import phex.gui.common.table.SortedTableHeaderRenderer;
import phex.gui.common.treetable.JTreeTable;
import phex.gui.renderer.SearchResultsRowRenderer;
import phex.gui.renderer.SearchTreeCellRenderer;
import phex.gui.tabs.search.SearchTab;
import phex.gui.tabs.search.SearchResultElement;
import phex.share.SharedFilesService;
import phex.utils.StringUtils;
import phex.utils.URLUtil;
import phex.xml.sax.gui.DGuiSettings;
import phex.xml.sax.gui.DTable;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import saicontella.core.STLibrary;
import saicontella.core.STLocalizer;

/**
 * The search result panel containing a JTreeTable to group search results together and all its
 * actions.
 */
public class STSearchResultsPanel extends JPanel
{
    private static final String SEARCH_TREE_TABLE_IDENTIFIER = "SearchTreeTable";
    private static final RemoteFile[] EMPTY_REMOTE_FILE_ARRAY = new RemoteFile[0];
    
    private STSearchTab searchTab;
    private JPopupMenu searchResultPopup;
    
    private JTreeTable searchTreeTable;
    private STSearchTreeTableModel searchTreeTableModel;
    
    private JScrollPane searchTreeTableScrollPane;
    
    public STSearchResultsPanel( STSearchTab tab )
    {
        super( );
        searchTab = tab;
    }
    
    public void initializeComponent( DGuiSettings guiSettings )
    {
        CellConstraints cc = new CellConstraints();
        FormLayout layout = new FormLayout(
            "fill:d:grow", // columns
            "fill:d:grow, 1dlu, p"); //rows
        PanelBuilder panelBuilder = new PanelBuilder( layout, this );

        MouseHandler mouseHandler = new MouseHandler();
        searchTreeTableModel = new STSearchTreeTableModel();
        searchTreeTable = new JTreeTable( searchTreeTableModel );
        GUIUtils.updateTableFromDGuiSettings( guiSettings, searchTreeTable, 
            SEARCH_TREE_TABLE_IDENTIFIER );

        searchTreeTable.activateHeaderPopupMenu();
        searchTreeTable.activateColumnResizeToFit();
        JTableHeader header = searchTreeTable.getTableHeader();
        header.setDefaultRenderer( new SortedTableHeaderRenderer(searchTreeTable) );
        header.addMouseListener( new TableHeaderMouseHandler() );
        searchTreeTable.addMouseListener( mouseHandler );
        searchTreeTable.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );
        searchTreeTable.getSelectionModel().addListSelectionListener(
            new SearchTreeTableSelectionListener() );
        searchTreeTable.setTreeCellRenderer( new SearchTreeCellRenderer() );
        
        SharedFilesService sharedFilesService = STLibrary.getInstance().getGnutellaFramework().getServent().getSharedFilesService();
        SwarmingManager swarmingMgr = SwarmingManager.getInstance();
        SearchResultsRowRenderer resultRowRenderer = new SearchResultsRowRenderer(
            sharedFilesService, swarmingMgr );
        List<TableColumn> colList = searchTreeTable.getColumns( true );
        for ( TableColumn column : colList )
        {
            column.setCellRenderer( resultRowRenderer );
        }
        searchTreeTableScrollPane = FWTable.createFWTableScrollPane( searchTreeTable );
        searchTreeTableScrollPane.addMouseListener( mouseHandler );
        panelBuilder.add( searchTreeTableScrollPane, cc.xy( 1, 1 ) );
        
        FWToolBar resultToolbar = new FWToolBar( JToolBar.HORIZONTAL );
        resultToolbar.setBorderPainted( false );
        resultToolbar.setFloatable( false );
        panelBuilder.add( resultToolbar, cc.xy( 1, 3 ) );

        // init popup menu
        searchResultPopup = new JPopupMenu();

        // add actions to toolbar
        FWAction action = new SWQuickDownloadAction();
        searchTab.addTabAction( QUICK_DOWNLOAD_ACTION_KEY, action );
        resultToolbar.addAction( action );
        searchResultPopup.add( action );

        action = new SWConfigDownloadAction();
        searchTab.addTabAction( CONFIG_DOWNLOAD_ACTION_KEY, action );
        resultToolbar.addAction( action );
        searchResultPopup.add( action );

        //action = new AddAsCandidateAction();
        //addTabAction( ADD_AS_CANDIDATE_ACTION_KEY, action );
        //resultToolbar.addAction( action );
        //searchResultPopup.add( action );

        action = new BrowseHostAction();
        searchTab.addTabAction( BROWSE_HOST_ACTION_KEY, action );
        resultToolbar.addAction( action );
        searchResultPopup.add( action );
        
        action = new ChatToHostAction();
        searchTab.addTabAction( CHAT_TO_HOST_ACTION_KEY, action );
        searchResultPopup.add( action );

        searchResultPopup.addSeparator();
        
        searchResultPopup.add( searchTab.getTabAction(
            SearchTab.CREATE_NEW_SEARCH_ACTION ) );
        searchResultPopup.add( searchTab.getTabAction(
            SearchTab.CLEAR_SEARCH_RESULTS_ACTION ) );
        searchResultPopup.add( searchTab.getTabAction(
            SearchTab.CLOSE_SEARCH_ACTION ) );
    }
    
    public void setDisplayedSearch( STISearchDataModel searchResultsDataModel )
    {
        searchTreeTableModel.setDisplayedSearch(searchResultsDataModel);
    }
    
    /**
     * Returns all selected remote files. If the represent of a group of
     * remote files is selected all remote files of the represent are returned.
     * If singleForAll is true, then all remote files of the represent are 
     * returned if a single remote file of the represent set is selected.
     * @param allOfRepresent
     * @return list of selected remote files
     */
    public RemoteFile[] getSelectedRemoteFiles( boolean singleForAll )
    {
        STISearchDataModel searchDataModel = searchTreeTableModel.getDisplayedResultsData();
        if (searchDataModel == null)
        {
            return EMPTY_REMOTE_FILE_ARRAY;
        }
        
        TreePath[] selectionPaths = searchTreeTable.getTreeSelectionModel().getSelectionPaths();
        if ( selectionPaths == null || selectionPaths.length == 0 )
        {
            return EMPTY_REMOTE_FILE_ARRAY;
        }
        HashSet<RemoteFile> remoteFileSet = new HashSet<RemoteFile>();
        for ( int i = 0; i < selectionPaths.length; i++ )
        {
            if ( selectionPaths[i].getPathCount() == 3 )
            {
                if ( singleForAll )
                {
                    SearchResultElement element = (SearchResultElement)selectionPaths[i].getPathComponent( 1 );
                    RemoteFile[] files = element.getRemoteFiles();
                    remoteFileSet.addAll( Arrays.asList( files ) );
                }
                else
                {
                    RemoteFile remoteFile = (RemoteFile)selectionPaths[i].getPathComponent( 2 );
                    remoteFileSet.add( remoteFile );
                }
            }
            else
            {
                SearchResultElement element = (SearchResultElement)selectionPaths[i].getPathComponent( 1 );
                RemoteFile[] files = element.getRemoteFiles();
                remoteFileSet.addAll( Arrays.asList( files ) );
            }            
        }
        RemoteFile[] result = new RemoteFile[ remoteFileSet.size() ];
        remoteFileSet.toArray( result );
        return result;
    }

    /**
     * Returns the selected RemoteFile. In case useRepresent is true and a
     * SearchResultElement containing multiple RemoteFiles is selected a
     * representiv RemoteFile for this collection is returned, if useRepresent is
     * false null is returned in this case.
     * Null is also returned when there are no displayed results datas,
     * or the tree selection path is null.
     * @return the selected RemoteFile or null.
     */
    private RemoteFile getSelectedRemoteFile( boolean useRepresent )
    {
        STISearchDataModel searchDataModel = searchTreeTableModel.getDisplayedResultsData();
        if (searchDataModel == null)
        {
            return null;
        }
        
        TreePath selectionPath = searchTreeTable.getTreeSelectionModel().getSelectionPath();
        if ( selectionPath == null )
        {
            return null;
        }
        RemoteFile remoteFile;
        if ( selectionPath.getPathCount() == 3 )
        {
            remoteFile = (RemoteFile)selectionPath.getPathComponent( 2 );
        }
        else
        {
            SearchResultElement element = (SearchResultElement)selectionPath.getPathComponent( 1 );
            if ( !useRepresent && element.getRemoteFileListCount() != 0 )
            {
                return null;
            }
            remoteFile = element.getSingleRemoteFile();
        }
        
        return remoteFile;
    }
    
    
    /**
     * This is overloaded to update the table scroll pane on
     * every UI update.
     */
    @Override
    public void updateUI()
    {
        super.updateUI();
        if ( searchTreeTableScrollPane != null )
        {
            searchTreeTable.updateUI();
            int rows = searchTreeTable.getRowCount(); 
            FWTable.updateFWTableScrollPane( searchTreeTableScrollPane );
        }
    }
    
    public void appendDGuiSettings( DGuiSettings dSettings )
    {
        DTable dTable = GUIUtils.createDTable( searchTreeTable, SEARCH_TREE_TABLE_IDENTIFIER );
        dSettings.getTableList().getTableList().add( dTable );
    }
    
    /////////////////////// Start Table Actions///////////////////////////////////

    private static final String QUICK_DOWNLOAD_ACTION_KEY = "SWQuickDownloadAction";
    private static final String CONFIG_DOWNLOAD_ACTION_KEY = "SWConfigDownloadAction";
    //private static final String ADD_AS_CANDIDATE_ACTION_KEY = "AddAsCandidateAction";
    private static final String VIEW_BITZI_TICKET_ACTION_KEY = "ViewBitziTicketAction";
    private static final String CHAT_TO_HOST_ACTION_KEY = "ChatToHostAction";
    private static final String BROWSE_HOST_ACTION_KEY = "BrowseHostAction";
    private static final String ADD_TO_FAVORITES_ACTION_KEY = "AddToFavoritesAction";

    private class SWQuickDownloadAction extends FWAction
    {
        public SWQuickDownloadAction()
        {
            super( STLocalizer.getString( "QuickDownload" ),
                GUIRegistry.getInstance().getPlafIconPack().getIcon("Search.Download"),
                STLocalizer.getString( "TTTQuickDownload" ) );
            refreshActionState();
        }

        public void actionPerformed( ActionEvent e )
        {
            final RemoteFile[] rfiles = getSelectedRemoteFiles( true );
            Runnable runner = new Runnable()
            {
                public void run()
                {
                    try
                    {
                        SwarmingManager swarmingMgr = SwarmingManager.getInstance();
                        for ( int i = 0; i < rfiles.length; i++ )
                        {
                            rfiles[i].setInDownloadQueue( true );
            
                            SWDownloadFile downloadFile = swarmingMgr.getDownloadFile(
                                rfiles[i].getFileSize(), rfiles[i].getURN() );
            
                            if ( downloadFile != null )
                            {
                                downloadFile.addDownloadCandidate( rfiles[i] );
                            }
                            else
                            {
                                RemoteFile dfile = new RemoteFile( rfiles[i] );
                                String searchTerm = StringUtils.createNaturalSearchTerm( dfile.getFilename() );
                                swarmingMgr.addFileToDownload( dfile,
                                    dfile.getFilename(), searchTerm );
                            }
                        }
                    }
                    catch ( Throwable th )
                    {
                        NLogger.error( SWQuickDownloadAction.class, th, th);
                    }
                }
            };
            ThreadPool.getInstance().addJob(runner, "QuickDownloadAction" );
        }

        @Override
        public void refreshActionState()
        {
            if ( searchTreeTable.getSelectedRow() < 0 )
            {
                setEnabled( false );
            }
            else
            {
                setEnabled( true );
            }
        }
    }

    private class SWConfigDownloadAction extends FWAction
    {
        public SWConfigDownloadAction()
        {
            super( STLocalizer.getString( "Download" ),
                GUIRegistry.getInstance().getPlafIconPack().getIcon("Search.ConfigDownload"),
                STLocalizer.getString( "TTTDownload" ) );
            refreshActionState();
        }

        public void actionPerformed( ActionEvent e )
        {
            SwarmingManager swarmingMgr = SwarmingManager.getInstance();
            RemoteFile[] rfiles = getSelectedRemoteFiles( true );
            
            for ( int i = 0; i < rfiles.length; i++ )
            {
                SWDownloadFile downloadFile = swarmingMgr.getDownloadFile(
                    rfiles[i].getFileSize(), rfiles[i].getURN() );

                if ( downloadFile != null )
                {
                    downloadFile.addDownloadCandidate( rfiles[i] );
                    rfiles[i].setInDownloadQueue( true );
                }
                else
                {
                    STDownloadConfigDialog dialog = new STDownloadConfigDialog( rfiles[i] );
                    dialog.setVisible( true );
                }
            }
        }

        @Override
        public void refreshActionState()
        {
            if ( searchTreeTable.getSelectedRow() < 0 )
            {
                setEnabled( false );
            }
            else
            {
                setEnabled( true );
            }
        }
    }

    private class ViewBitziTicketAction extends FWAction
    {
        public ViewBitziTicketAction()
        {
            super( STLocalizer.getString( "ViewBitziTicket" ),
                GUIRegistry.getInstance().getPlafIconPack().getIcon("Search.ViewBitzi"),
                STLocalizer.getString( "TTTViewBitziTicket" ) );
            refreshActionState();
        }

        public void actionPerformed( ActionEvent e )
        {
            // since getSelectedRemoteFile() returns null on the top level
            // SearchResultElement when it has multiple childs, we use
            // getSelectedRemoteFiles() as a 
            RemoteFile rfile = getSelectedRemoteFile( true );
            if ( rfile == null )
            {
                return;
            }
            String url = URLUtil.buildBitziLookupURL( rfile.getURN() );
            try
            {
                BrowserLauncher.openURL( url );
            }
            catch ( IOException exp )
            {
                NLogger.warn( ViewBitziTicketAction.class, exp, exp);
                Object[] dialogOptions = new Object[]
                {
                    STLocalizer.getString( "Yes" ),
                    STLocalizer.getString( "No" )
                };

                int choice = JOptionPane.showOptionDialog( searchTab,
                    STLocalizer.getString( "FailedToLaunchBrowserURLInClipboard" ),
                    STLocalizer.getString( "FailedToLaunchBrowser" ),
                    JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null,
                    dialogOptions, STLocalizer.getString( "Yes" ) );
                if ( choice == 0 )
                {
                    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(
                        new StringSelection( url ), null);
                }
            }
        }

        @Override
        public void refreshActionState()
        {
            if ( searchTreeTable.getSelectedRowCount() == 1 )
            {
                RemoteFile rfile = getSelectedRemoteFile( true );
                if ( rfile != null && rfile.getURN() != null )
                {
                    setEnabled( true );
                    return;
                }
            }
            else
            {
                setEnabled( false );
            }
        }
    }

    private class ChatToHostAction extends FWAction
    {
        public ChatToHostAction()
        {
            super( STLocalizer.getString( "ChatToHost" ),
                GUIRegistry.getInstance().getPlafIconPack().getIcon("Search.Chat"),
                STLocalizer.getString( "TTTChatToHost" ) );
            refreshActionState();
        }

        public void actionPerformed( ActionEvent e )
        {
            RemoteFile rfile = getSelectedRemoteFile( false );
            if ( rfile == null )
            {
                return;
            }

            if ( !rfile.getQueryHitHost().isChatSupported() )
            {
                return;
            }
            GUIActionPerformer.chatToHost( rfile.getHostAddress() );
        }

        @Override
        public void refreshActionState()
        {
            if ( searchTreeTable.getSelectedRowCount() == 1 )
            {
                RemoteFile rfile = getSelectedRemoteFile( false );
                if ( rfile != null
                    && rfile.getQueryHitHost().isChatSupported() )
                {
                    setEnabled( true );
                    return;
                }
            }
            setEnabled( false );
        }
    }

    private class BrowseHostAction extends FWAction
    {
        public BrowseHostAction()
        {
            super( STLocalizer.getString( "BrowseHost" ),
                GUIRegistry.getInstance().getPlafIconPack().getIcon("Search.BrowseHost"),
                STLocalizer.getString( "TTTBrowseHost" ) );
            refreshActionState();
        }

        public void actionPerformed( ActionEvent e )
        {
            RemoteFile rfile = getSelectedRemoteFile( false );
            if ( rfile == null )
            {
                return;
            }
            if ( !rfile.getQueryHitHost().isBrowseHostSupported() )
            {
                return;
            }
            GUIActionPerformer.browseHost( rfile.getHostAddress() );
        }

        @Override
        public void refreshActionState()
        {
            if ( searchTreeTable.getSelectedRowCount() == 1 )
            {
                RemoteFile rfile = getSelectedRemoteFile( false );
                if ( rfile != null
                    && rfile.getQueryHitHost().isBrowseHostSupported() )
                {
                    setEnabled( true );
                    return;
                }
            }
            setEnabled( false );
        }
    }
    
    private final class BanHostActionProvider implements BanHostActionUtils.BanHostActionProvider
    {
        public DestAddress[] getBanHostAddresses()
        {
            RemoteFile[] files = getSelectedRemoteFiles( false );
            final DestAddress[] addresses = new DestAddress[files.length];
            for (int i = 0; i < files.length; i++)
            {
                addresses[ i ] = files[i].getHostAddress();
            }
            return addresses;
        }

        public boolean isBanHostActionEnabled( boolean allowMultipleAddresses )
        {
            if ( searchTreeTable.getSelectedRow() < 0 || 
                 ( !allowMultipleAddresses && searchTreeTable.getSelectedRowCount() > 1 ) )
            {
                return false;
            }
            else
            {
                return true;
            }
        }
    }
    
    private class AddToFavoritesAction extends FWAction
    {
        public AddToFavoritesAction()
        {
            super( STLocalizer.getString( "AddToFavorites" ),
                GUIRegistry.getInstance().getPlafIconPack().getIcon("Search.FavoriteHost"),
                STLocalizer.getString( "TTTAddToFavorites" ) );
            refreshActionState();
        }

        public void actionPerformed( ActionEvent e )
        {
            RemoteFile[] files = getSelectedRemoteFiles( false );
            
            DestAddress[] addresses = new DestAddress[files.length];
            for (int i = 0; i < files.length; i++)
            {
                addresses[ i ] = files[i].getHostAddress();
            }
            GUIActionPerformer.addHostsToFavorites( addresses );
        }

        @Override
        public void refreshActionState()
        {
            if ( searchTreeTable.getSelectedRow() < 0 )
            {
                setEnabled( false );
            }
            else
            {
                setEnabled( true );
            }
        }
    }

    /////////////////////// End Table Actions///////////////////////////////////
        
    private class MouseHandler extends MouseAdapter implements MouseListener
    {
        @Override
        public void mouseClicked(MouseEvent e)
        {
            try
            {
                if (e.getClickCount() == 2)
                {
                    if (e.getSource() == searchTreeTable)
                    {
                        searchTab.getTabAction(QUICK_DOWNLOAD_ACTION_KEY)
                            .actionPerformed(null);
                    }
                }
            }
            catch (Throwable th)
            {
                NLogger.error( MouseHandler.class, th, th);
            }
        }

        @Override
        public void mouseReleased(MouseEvent e)
        {
            try
            {
                if (e.isPopupTrigger())
                {
                    popupMenu((Component) e.getSource(), e.getX(), e.getY());
                }
            }
            catch (Throwable th)
            {
                NLogger.error( MouseHandler.class, th, th);
            }
        }

        @Override
        public void mousePressed(MouseEvent e)
        {
            try
            {
                if (e.isPopupTrigger())
                {
                    popupMenu((Component) e.getSource(), e.getX(), e.getY());
                }
            }
            catch (Throwable th)
            {
                NLogger.error( MouseHandler.class, th, th);
            }
        }

        private void popupMenu(Component source, int x, int y)
        {
            if (source == searchTreeTable
                || source == searchTreeTableScrollPane)
            {
                searchResultPopup.show(source, x, y);
            }
        }
    }

    private class TableHeaderMouseHandler extends MouseAdapter implements
        MouseListener
    {
        @Override
        public void mouseClicked(MouseEvent e)
        {
            try
            {
                FWTableColumn column = searchTreeTable.getResizingColumn(e
                    .getPoint());
                int clickCount = e.getClickCount();

                // only handle sorting on one click count and when not clicked
                // between two columns.
                if (column == null && clickCount == 1)
                {
                    handleColumnSorting(e);
                }
            }
            catch (Throwable th)
            {
                NLogger.error( TableHeaderMouseHandler.class, th, th);
            }
        }

        /**
         * Handles sorting on a column on single click.
         */
        private void handleColumnSorting(MouseEvent e)
        {
            int viewIdx = searchTreeTable.getColumnModel().getColumnIndexAtX(
                e.getX());
            int modelIdx = searchTreeTable.convertColumnIndexToModel(viewIdx);
            if (modelIdx == -1)
            {
                return;
            }

            // reverse order on each click
            FWTableColumn column = (FWTableColumn) searchTreeTable
                .getColumnModel().getColumn(viewIdx);
            boolean isAscending = column.reverseSortingOrder();

            searchTreeTableModel.sortByColumn(modelIdx, isAscending);
        }
    }

    /**
     * We like to select all expanded rows of the parent row in the table. 
     */
    private class SearchTreeTableSelectionListener implements
        ListSelectionListener
    {
        public void valueChanged(ListSelectionEvent e)
        {
            try
            {
                if (!e.getValueIsAdjusting())
                {
                    searchTab.refreshTabActions();
                }
                /*
                 // causes all child rows to be selected...
                 ListSelectionModel model = (ListSelectionModel)e.getSource();
                 int startIdx = e.getFirstIndex();
                 int endIdx = e.getLastIndex();
                 for (int i = startIdx; i <= endIdx; i++)
                 {
                 if ( model.isSelectedIndex(i) )
                 {
                 Object node = searchTreeTable.getNodeOfRow(i);
                 if ( node == null )
                 {
                 continue;
                 }
                 if ( !searchTreeTableModel.isLeaf( node ) &&
                 searchTreeTable.getTree().isExpanded(i) )
                 {
                 int start = i + 1;
                 int stop = i + searchTreeTableModel.getChildCount( node );
                 model.addSelectionInterval(start, stop);
                 }
                 }
                 }
                 */}
            catch (Throwable th)
            {
                NLogger.error( SearchTreeTableSelectionListener.class, th, th);
            }
        }
    }
}