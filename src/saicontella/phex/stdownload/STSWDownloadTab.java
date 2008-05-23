package saicontella.phex.stdownload;

/**
 * SaiconTella project
 *
 * @author Marios Karagiannopoulos (mariosk@gmail.com)
 *
 * February 2008
 */

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import phex.common.ThreadPool;
import phex.common.URN;
import phex.common.address.DestAddress;
import phex.common.format.NumberFormatUtils;
import phex.common.log.LogBuffer;
import phex.common.log.LogRecord;
import phex.common.log.NLogger;
import phex.download.swarming.SWDownloadFile;
import phex.download.swarming.SwarmingManager;
import phex.gui.actions.BanHostActionUtils;
import phex.gui.actions.FWAction;
import phex.gui.actions.FWToggleAction;
import phex.gui.actions.GUIActionPerformer;
import phex.gui.common.*;
import phex.gui.common.table.FWSortedTableModel;
import phex.gui.common.table.FWTable;
import phex.gui.dialogs.DownloadConfigDialog;
import phex.gui.dialogs.LogBufferDialog;
import phex.gui.tabs.FWTab;
import phex.prefs.core.BandwidthPrefs;
import phex.prefs.core.DownloadPrefs;
import phex.query.ResearchSetting;
import phex.utils.Localizer;
import phex.utils.SystemShellExecute;
import phex.utils.URLUtil;
import phex.xml.sax.gui.DGuiSettings;
import phex.xml.sax.gui.DTable;
import saicontella.phex.stdownload.STSWDownloadTableModel;
import saicontella.phex.STFWElegantPanel;
import saicontella.core.STResources;
import saicontella.core.STLibrary;
import saicontella.core.STButtonsPanel;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.looks.Options;
import java.io.File;
import org.jdesktop.jdic.desktop.Desktop;
import org.jdesktop.jdic.desktop.DesktopException;

public class STSWDownloadTab extends FWTab
{
    private static final String DOWNLOAD_TABLE_IDENTIFIER = "DownloadTable";
    private static final SWDownloadFile[] EMPTY_DOWNLOADFILE_ARRAY =
        new SWDownloadFile[0];

    private SwarmingManager swarmingMgr;

    private FWTable downloadTable;
    private JScrollPane downloadTableScrollPane;
    private STSWDownloadTableModel downloadModel;

    private JTabbedPane downloadDetails;
    private JPopupMenu downloadPopup;

    public STSWDownloadTab( )
    {
        super( MainFrame.DOWNLOAD_TAB_ID, Localizer.getString( "Download" ),
            GUIRegistry.getInstance().getPlafIconPack().getIcon( "Download.Tab" ),
            Localizer.getString( "TTTDownloadTab" ),Localizer.getChar(
            "DownloadMnemonic"), KeyStroke.getKeyStroke( Localizer.getString(
            "DownloadAccelerator" ) ), MainFrame.DOWNLOAD_TAB_INDEX );
        swarmingMgr = SwarmingManager.getInstance();
    }

    public void initComponent( DGuiSettings guiSettings )
    {
        MouseHandler mouseHandler = new MouseHandler();
        CellConstraints cc = new CellConstraints();
        FormLayout layout = new FormLayout(
            "2dlu, fill:d:grow, 2dlu, p, p, p", // columns
            "2dlu, fill:d:grow, fill:d:grow, 2dlu"); //rows
        PanelBuilder tabBuilder = new PanelBuilder( layout, this );

        JPanel downloadTablePanel = initDownloadTablePanel( guiSettings, mouseHandler );

        downloadDetails = new JTabbedPane( JTabbedPane.BOTTOM );
        downloadDetails.putClientProperty(Options.EMBEDDED_TABS_KEY, Boolean.TRUE);
        downloadDetails.setBorder( BorderFactory.createEmptyBorder( 2, 0, 0, 0) );

        // Workaround for very strange j2se 1.4 split pane layout behavior
        Dimension dim = new Dimension( 400, 400 );
        downloadTablePanel.setPreferredSize( dim );
        downloadDetails.setPreferredSize( dim );
        dim = new Dimension( 0, 0 );        
        downloadTablePanel.setMinimumSize( dim );
        downloadDetails.setMinimumSize( dim );


        JSplitPane splitPane = new JSplitPane( JSplitPane.VERTICAL_SPLIT, downloadTablePanel,
            null );
        splitPane.setBorder( BorderFactory.createEmptyBorder( 0, 0, 0, 0) );
        splitPane.setDividerSize( 4 );
        splitPane.setOneTouchExpandable( false );
        splitPane.setDividerLocation( 0.5 );
        splitPane.setResizeWeight( 0.5 );

        tabBuilder.add( splitPane, cc.xywh( 2, 2, 5, 1 ) );
        STButtonsPanel buttonsPanel = new STButtonsPanel();
        tabBuilder.add( buttonsPanel, cc.xy( 6, 3 ) );

        // increase table height a bit to display progress bar string better...
        GUIUtils.adjustTableProgresssBarHeight( downloadTable );


        ActionListener updateDownloadFileInfoAction = new ActionListener()
        {
            public void actionPerformed( ActionEvent e )
            {
                updateDownloadFileInfo();
            }
        };
        GUIRegistry.getInstance().getGuiUpdateTimer().addActionListener(
            updateDownloadFileInfoAction );
    }


    private JPanel initDownloadTablePanel( DGuiSettings guiSettings, MouseHandler mouseHandler )
    {
        JPanel downloadTablePanel = new JPanel( );

        CellConstraints cc = new CellConstraints();
        FormLayout layout = new FormLayout(
            "fill:d:grow", // columns
            "fill:d:grow, 1dlu, p"); //rows
        PanelBuilder tabBuilder = new PanelBuilder( layout, downloadTablePanel );

        downloadModel = new STSWDownloadTableModel();
        downloadTable = new FWTable( new FWSortedTableModel( downloadModel ) );
        GUIUtils.updateTableFromDGuiSettings( guiSettings, downloadTable,
            DOWNLOAD_TABLE_IDENTIFIER );

        downloadTable.activateAllHeaderActions();
        downloadTable.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );
        downloadTable.getSelectionModel().addListSelectionListener(
            new DownloadSelectionHandler() );
        downloadTable.addMouseListener( mouseHandler );
        GUIRegistry.getInstance().getGuiUpdateTimer().addTable( downloadTable );
        downloadTableScrollPane = FWTable.createFWTableScrollPane( downloadTable );
        downloadTableScrollPane.addMouseListener( mouseHandler );
        tabBuilder.add( downloadTableScrollPane, cc.xy( 1, 1 ) );

        FWToolBar fileToolbar = new FWToolBar( JToolBar.HORIZONTAL );
        fileToolbar.setBorderPainted( false );
        fileToolbar.setFloatable( false );
        tabBuilder.add( fileToolbar, cc.xy( 1, 3 ) );

        downloadPopup = new JPopupMenu();

        FWAction startDownloadAction = new StartDownloadAction();
        addTabAction( startDownloadAction );
        downloadPopup.add( startDownloadAction );

        FWAction stopDownloadAction = new StopDownloadAction();
        addTabAction( stopDownloadAction );
        downloadPopup.add( stopDownloadAction );

        FWAction removeDownloadAction = new RemoveDownloadAction();
        addTabAction( removeDownloadAction );
        downloadPopup.add( removeDownloadAction );
        downloadTable.getActionMap().put( removeDownloadAction, removeDownloadAction );
        downloadTable.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
            .put( (KeyStroke)removeDownloadAction.getValue(
            FWAction.ACCELERATOR_KEY), removeDownloadAction );

        FWAction action = new GeneratePreviewAction();
        addTabAction( action );
        fileToolbar.addAction( action );
        downloadPopup.add( action );

        STFWElegantPanel elegantPanel = new STFWElegantPanel( Localizer.getString("DownloadFiles"),
            downloadTablePanel );
        return elegantPanel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateUI()
    {
        super.updateUI();
        if ( downloadTable != null )
        {
            // increase table height a bit to display progress bar string better...
            GUIUtils.adjustTableProgresssBarHeight( downloadTable );
        }
        if ( downloadTableScrollPane != null )
        {
            FWTable.updateFWTableScrollPane( downloadTableScrollPane );
        }
    }

    /**
     * Updates dynamic information when changes occur. Called every 2 seconds
     * to update possible download changes or when the selected download
     * changes.
     */
    private void updateDownloadFileInfo( )
    {
        // map view to model
        int selectedRow = downloadTable.getSelectedRow();
        int modelRow = downloadTable.translateRowIndexToModel(
            selectedRow );
        refreshTabActions();

        SWDownloadFile file = swarmingMgr.getDownloadFile(
            modelRow );

        if ( file != null )
        {
            ResearchSetting researchSetting = file.getResearchSetting();
            if ( researchSetting.isSearchRunning() )
            {
                Object[] args =
                {
                    new Integer( researchSetting.getSearchHitCount() ),
                    new Integer( researchSetting.getSearchProgress() )
                };

            }
        }
    }

    private SWDownloadFile[] getSelectedDownloadFiles()
    {
        if ( downloadTable.getSelectedRowCount() == 0 )
        {
            return EMPTY_DOWNLOADFILE_ARRAY;
        }
        int[] viewIndices = downloadTable.getSelectedRows();
        int[] modelIndices = downloadTable.convertRowIndicesToModel( viewIndices );
        SWDownloadFile[] files = swarmingMgr.getDownloadFilesAt( modelIndices );
        return files;
    }

    private SWDownloadFile getSelectedDownloadFile()
    {
        int viewIndex = downloadTable.getSelectedRow();
        if ( viewIndex < 0 )
        {
            return null;
        }
        int modelIndex = downloadTable.translateRowIndexToModel( viewIndex );
        SWDownloadFile file = swarmingMgr.getDownloadFile( modelIndex );
        return file;
    }

    //////////////////////////////////////////////////////////////////////////
    /// XML serializing and deserializing
    //////////////////////////////////////////////////////////////////////////

    /**
     * {@inheritDoc}
     */
    @Override
    public void appendDGuiSettings( DGuiSettings dSettings )
    {
        super.appendDGuiSettings( dSettings );
        DTable dTable = GUIUtils.createDTable( downloadTable, DOWNLOAD_TABLE_IDENTIFIER );
        dSettings.getTableList().getTableList().add( dTable );
    }


    //////////////////////////////////////////////////////////////////////////
    /// Table Listeners
    //////////////////////////////////////////////////////////////////////////

    /**
     * Selection listener for download file table.
     */
    class DownloadSelectionHandler implements ListSelectionListener
    {
        public void valueChanged( ListSelectionEvent e )
        {
            try
            {
	            updateDownloadFileInfo( );
            }
            catch ( Exception exp )
            {
                NLogger.error( DownloadSelectionHandler.class, exp, exp );
            }
        }
    }

    private class MouseHandler extends MouseAdapter implements MouseListener
    {
        /**
         * {@inheritDoc}
         */
        @Override
        public void mouseClicked(MouseEvent e)
        {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void mouseReleased(MouseEvent e)
        {
            if (e.isPopupTrigger())
            {
                popupMenu((Component)e.getSource(), e.getX(), e.getY());
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
                popupMenu((Component)e.getSource(), e.getX(), e.getY());
            }
        }

        private void popupMenu(Component source, int x, int y)
        {
            if ( source == downloadTable || source == downloadTableScrollPane )
            {
                refreshTabActions();
                downloadPopup.show( source, x, y );
            }
        }
    }

    //////////////////////////////////////////////////////////////////////////
    /// Actions
    //////////////////////////////////////////////////////////////////////////

    /**
     * Starts a download.
     */
    class StartDownloadAction extends FWAction
    {
        StartDownloadAction()
        {
            super( Localizer.getString( "StartDownload" ),
                GUIRegistry.getInstance().getPlafIconPack().getIcon("Download.StartDownload"),
                Localizer.getString( "TTTStartDownload" ) );
            refreshActionState();
        }

        public void actionPerformed( ActionEvent e )
        {
            SWDownloadFile[] files = getSelectedDownloadFiles();
            for ( int i = 0; i < files.length; i++ )
            {
                if ( files[i] != null && files[i].isDownloadStopped() )
                {
                    files[i].startDownload();
                }

            }
            refreshActionState();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void refreshActionState()
        {
            SWDownloadFile[] files = getSelectedDownloadFiles();
            boolean state = false;
            for ( int i = 0; i < files.length; i++ )
            {
                if ( files[i] != null && files[i].isDownloadStopped() )
                {
                    state = true;
                    break;
                }
            }
            setEnabled( state );
        }
    }

    /**
     * Stops a download.
     */
    class StopDownloadAction extends FWAction
    {
        StopDownloadAction()
        {
            super( "Pause",
                GUIRegistry.getInstance().getPlafIconPack().getIcon("Download.StopDownload"),
                Localizer.getString( "TTTStopDownload" ) );
            refreshActionState();
        }

        public void actionPerformed( ActionEvent e )
        {
            final SWDownloadFile[] files = getSelectedDownloadFiles();
            Runnable runner = new Runnable()
            {
                public void run()
                {
                    try
                    {
                        for ( int i = 0; i < files.length; i++ )
                        {
                            if ( files[i] != null && !files[i].isFileCompletedOrMoved()
                             && !files[i].isDownloadStopped() )
                            {
                                files[i].stopDownload();
                            }
                        }
                        refreshActionState();
                    }
                    catch ( Throwable th )
                    {
                        NLogger.error( StopDownloadAction.class, th, th);
                    }
                }
            };
            ThreadPool.getInstance().addJob(runner, "StopDownloadFiles" );
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void refreshActionState()
        {
            SWDownloadFile[] files = getSelectedDownloadFiles();
            boolean state = false;
            for ( int i = 0; i < files.length; i++ )
            {
                if ( files[i] != null && !files[i].isFileCompletedOrMoved()
                  && !files[i].isDownloadStopped() )
                {
                    state = true;
                    break;
                }
            }
            setEnabled( state );
        }
    }

    class GeneratePreviewAction extends FWAction
    {
        GeneratePreviewAction ( )
        {
            super( Localizer.getString( "DownloadTab_PreviewDownload" ),
                GUIRegistry.getInstance().getPlafIconPack().getIcon("Download.Preview"),
                Localizer.getString( "DownloadTab_TTTPreviewDownload" ) );
            refreshActionState();
        }

        public void actionPerformed( ActionEvent e )
        {
            try
            {
                final SWDownloadFile file = getSelectedDownloadFile();
                if ( file == null ) return;
                Runnable runner = new Runnable()
                {
                    public void run()
                    {
                        try {
                            File previewFile = file.getPreviewFile();
                            Desktop.open(previewFile);
                        } catch (DesktopException e) {
                            STLibrary.getInstance().fireMessageBox(e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);                            
                        }

                        /*
                        try
                        {
                            File previewFile = file.getPreviewFile();
                            SystemShellExecute.launchFile( previewFile );
                        }
                        catch ( Throwable th )
                        {
                            NLogger.error( GeneratePreviewAction.class, th, th);
                        }
                        */
                    }
                };
                ThreadPool.getInstance().addJob(runner, "GenerateDownloadPreview" );
            }
            catch ( Throwable th )
            {
                NLogger.error( GeneratePreviewAction.class, th, th);
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void refreshActionState()
        {
            try
            {
                SWDownloadFile file = getSelectedDownloadFile();
                if ( file == null )
                {
                    // no file, no do
                    setEnabled ( false );
                }
                else
                {
                    setEnabled( file.isPreviewPossible() );
                }
            }
            catch ( Throwable th )
            {
                NLogger.error( GeneratePreviewAction.class, th, th);
            }
        }
    }    

    /**
     * Removes a download from the list.
     */
    class RemoveDownloadAction extends FWAction
    {
        RemoveDownloadAction()
        {
            super( Localizer.getString( "RemoveDownload" ),
                GUIRegistry.getInstance().getPlafIconPack().getIcon("Download.RemoveDownload"),
                Localizer.getString( "TTTRemoveDownload" ), null,
                KeyStroke.getKeyStroke( KeyEvent.VK_DELETE, 0 ) );
            refreshActionState();
        }

        public void actionPerformed( ActionEvent e )
        {
            SWDownloadFile[] files = getSelectedDownloadFiles();

            List<SWDownloadFile> warningFiles = new ArrayList<SWDownloadFile>( files.length );
            List<SWDownloadFile> removeFiles = new ArrayList<SWDownloadFile>( files.length );
            for( int i = 0; i < files.length; i++ )
            {
                if ( files[i] == null )
                {
                    continue;
                }
                if ( files[i].isFileCompletedMoved() )
                {
                    removeFiles.add( files[i] );
                }
                else if ( files[i].isFileCompleted() )
                {
                    // ignore the intermediate state of a file between beeing
                    // completed and beeing completed moved
                }
                // if download not started.
                else if ( files[i].getTransferredDataSize() == 0 )
                {
                    removeFiles.add( files[i] );
                }
                else
                {// if not completed... schedule for warning
                    warningFiles.add( files[i] );
                }
            }

            Integer warningSize = new Integer( warningFiles.size() );
            for ( int i = 0; i < warningSize.intValue(); i++ )
            {
                SWDownloadFile file = warningFiles.get( i );
                Object[] warningParams = new Object[]
                {
                    file.getFileName(),
                    NumberFormatUtils.formatSignificantByteSize( file.getTransferredDataSize() ),
                    NumberFormatUtils.formatSignificantByteSize( file.getTransferDataSize() )
                };
                Object[] titleParams = new Object[]
                {
                    new Integer( i + 1 ),
                    warningSize
                };

                Object[] dialogOptions;
                if ( warningSize.intValue() - i > 1 )
                {
                    dialogOptions = new Object[]
                    {
                        Localizer.getString( "Yes" ),
                        Localizer.getString( "No" ),
                        Localizer.getString( "YesToAll" ),
                        Localizer.getString( "NoToAll" ),
                    };
                }
                else
                {
                    dialogOptions = new Object[]
                    {
                        Localizer.getString( "Yes" ),
                        Localizer.getString( "No" )
                    };
                }

                int choice = JOptionPane.showOptionDialog(
                    GUIRegistry.getInstance().getMainFrame(),
                    Localizer.getFormatedString( "RemoveDownloadWarning", warningParams),
                    Localizer.getFormatedString( "RemoveDownloadTitle", titleParams ),
                    JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null,
                    dialogOptions, Localizer.getString( "Yes" ) );
                if ( choice == 0 )
                {
                    removeFiles.add( file );
                }
                else if ( choice == 2 )
                {
                    removeFiles.addAll( warningFiles.subList( i, warningFiles.size() ) );
                    break;
                }
                else if ( choice == 3 )
                {
                    break;
                }
            }

            // do the remove...
            if ( removeFiles.size() > 0 )
            {
                final SWDownloadFile[] filesToRemove = new SWDownloadFile[ removeFiles.size() ];
                removeFiles.toArray( filesToRemove );
                Runnable runner = new Runnable()
                {
                    public void run()
                    {
                        try
                        {
                            swarmingMgr.removeDownloadFiles( filesToRemove );
                        }
                        catch ( Throwable th )
                        {
                            NLogger.error( RemoveDownloadAction.class, th, th);
                        }
                    }
                };
                ThreadPool.getInstance().addJob(runner, "RemoveDownloadFiles" );
                downloadTable.getSelectionModel().clearSelection();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void refreshActionState()
        {
            int row = downloadTable.getSelectedRow();
            if ( row < 0 )
            {
                setEnabled( false );
            }
            else
            {
                setEnabled( true );
            }
        }
    }
}