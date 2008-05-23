package saicontella.phex.stupload;

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
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Collection;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import phex.common.address.DestAddress;
import phex.common.log.LogBuffer;
import phex.common.log.LogRecord;
import phex.common.log.NLogger;
import phex.gui.actions.BanHostActionUtils;
import phex.gui.actions.FWAction;
import phex.gui.actions.GUIActionPerformer;
import phex.gui.common.BrowserLauncher;
import phex.gui.common.FWElegantPanel;
import phex.gui.common.FWToolBar;
import phex.gui.common.GUIRegistry;
import phex.gui.common.GUIUtils;
import phex.gui.common.MainFrame;
import phex.gui.common.table.FWSortedTableModel;
import phex.gui.common.table.FWTable;
import phex.gui.dialogs.LogBufferDialog;
import phex.gui.tabs.FWTab;
import phex.gui.tabs.upload.UploadFilesTableModel;
import phex.prefs.core.UploadPrefs;
import phex.servent.Servent;
import phex.upload.UploadManager;
import phex.upload.UploadState;
import phex.utils.Localizer;
import phex.utils.URLUtil;
import phex.xml.sax.gui.DGuiSettings;
import phex.xml.sax.gui.DTable;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import saicontella.core.STButtonsPanel;
import saicontella.phex.STFWElegantPanel;

public class STUploadTab extends FWTab
{
    private static final String UPLOAD_TABLE_IDENTIFIER = "UploadTable";
    
    private static final UploadState[] EMPTY_UPLOADSTATE_ARRAY = new UploadState[0];

    private UploadManager uploadManager;

    private FWTable uploadTable;
    private JScrollPane uploadTableScrollPane;
    private STUploadFilesTableModel uploadModel;
    private JPopupMenu uploadPopup;

    public STUploadTab( )
    {
        super( MainFrame.UPLOAD_TAB_ID, Localizer.getString( "Upload" ),
            GUIRegistry.getInstance().getPlafIconPack().getIcon( "Upload.Tab" ),
            Localizer.getString( "TTTUpload" ),Localizer.getChar(
            "UploadMnemonic"), KeyStroke.getKeyStroke( Localizer.getString(
            "UploadAccelerator" ) ), MainFrame.UPLOAD_TAB_INDEX);
        uploadManager = Servent.getInstance().getUploadService();
    }

    public void initComponent( DGuiSettings guiSettings )
    {
        CellConstraints cc = new CellConstraints();
        FormLayout tabLayout = new FormLayout("2dlu, fill:d:grow, 2dlu", // columns
            "2dlu, fill:p:grow, 2dlu"); //rows
        PanelBuilder tabBuilder = new PanelBuilder(tabLayout, this);
        JPanel contentPanel = new JPanel();
        STFWElegantPanel banner = new STFWElegantPanel( Localizer.getString("Uploads"),
            contentPanel );
        tabBuilder.add(banner, cc.xy(2, 2));
        
        FormLayout contentLayout = new FormLayout(
            "fill:d:grow, p, p, p, p, p", // columns
            "fill:d:grow, 1dlu, p, fill:d:grow"); //rows
        PanelBuilder contentBuilder = new PanelBuilder(contentLayout, contentPanel);
        
        MouseHandler mouseHandler = new MouseHandler();
        
        uploadModel = new STUploadFilesTableModel( uploadManager );
        uploadTable = new FWTable( new FWSortedTableModel( uploadModel ) );
        GUIUtils.updateTableFromDGuiSettings( guiSettings, uploadTable, 
            UPLOAD_TABLE_IDENTIFIER );
        
        uploadTable.activateAllHeaderActions();
        uploadTable.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );
        uploadTable.getSelectionModel().addListSelectionListener(
            new SelectionHandler() );
        uploadTable.addMouseListener( mouseHandler );
        GUIRegistry.getInstance().getGuiUpdateTimer().addTable( uploadTable );
        uploadTableScrollPane = FWTable.createFWTableScrollPane( uploadTable );
        uploadTableScrollPane.addMouseListener( mouseHandler );
        contentBuilder.add( uploadTableScrollPane, cc.xywh( 1, 1, 6, 1 ) );

        // increase table height a bit to display progress bar string better...
        GUIUtils.adjustTableProgresssBarHeight( uploadTable );
        
        FWToolBar uploadToolbar = new FWToolBar( JToolBar.HORIZONTAL );
        uploadToolbar.setBorderPainted( false );
        uploadToolbar.setFloatable( false );
        contentBuilder.add( uploadToolbar, cc.xywh( 1, 3, 6, 1 ) );

        STButtonsPanel buttonsPanel = new STButtonsPanel();
        contentBuilder.add( buttonsPanel, cc.xy( 6, 4 ) );

        uploadPopup = new JPopupMenu();

        // add actions to toolbar
        FWAction action = new AbortUploadAction();
        addTabAction( action );
        uploadToolbar.addAction( action );
        uploadPopup.add( action );

        action = new RemoveUploadAction();
        addTabAction( action );
        uploadToolbar.addAction( action );
        uploadPopup.add( action );

        uploadToolbar.addSeparator();
        uploadPopup.addSeparator();
        
//        action = new AddToFavoritesAction();
//        addTabAction( action );
//        uploadPopup.add( action );

        action = new ChatToHostAction();
        addTabAction( action );
        uploadToolbar.addAction( action );
        uploadPopup.add( action );
               
        uploadToolbar.addSeparator();
        uploadPopup.addSeparator();

        action = new ClearUploadsAction();
        addTabAction( action );
        uploadToolbar.addAction( action );
        uploadPopup.add( action );
        
        if ( UploadPrefs.UploadStateLogBufferSize.get().intValue() > 0 )
        {
            action = new UploadStateLogAction();
            addTabAction( action );
            uploadToolbar.addAction( action );
            uploadPopup.add( action );
        }
        
        GUIRegistry.getInstance().getGuiUpdateTimer().addActionListener( 
            new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    refreshTabActions();
                }
            } );
    }
    
    /**
     * Indicates if this tab is visible by default, when there is no known 
     * visible setting from the user.
     * @return true if visible by default false otherwise.
     */
    @Override
    public boolean isVisibleByDefault()
    {
        return false;
    }

    /**
     * This is overloaded to update the table size for the progress bar on
     * every UI update. Like font size change!
     */
    @Override
    public void updateUI()
    {
        super.updateUI();
        if ( uploadTable != null )
        {
            // increase table height a bit to display progress bar string better...
            GUIUtils.adjustTableProgresssBarHeight( uploadTable );
        }
        if ( uploadTableScrollPane != null )
        {
            FWTable.updateFWTableScrollPane( uploadTableScrollPane );
        }
    }

    private UploadState[] getSelectedUploadStates()
    {
        int[] viewRows = uploadTable.getSelectedRows();
        if ( viewRows.length == 0 )
        {
            return EMPTY_UPLOADSTATE_ARRAY;
        }
        int[] modelRows = uploadTable.convertRowIndicesToModel( viewRows );

        UploadState[] states = uploadManager.getUploadStatesAt( modelRows );
        return states;
    }

    private UploadState getSelectedUploadState()
    {
        int viewRow = uploadTable.getSelectedRow();
        int modelRow = uploadTable.translateRowIndexToModel( viewRow );
        UploadState state = uploadManager.getUploadStateAt( modelRow );
        return state;
    }

    //////////////////////////////////////////////////////////////////////////
    /// XML serializing and deserializing
    //////////////////////////////////////////////////////////////////////////

    @Override
    public void appendDGuiSettings( DGuiSettings dSettings )
    {
        super.appendDGuiSettings( dSettings );
        DTable dTable = GUIUtils.createDTable( uploadTable, UPLOAD_TABLE_IDENTIFIER );
        dSettings.getTableList().getTableList().add( dTable );
    }

    //////////////////////////////////////////////////////////////////////////
    /// Actions
    //////////////////////////////////////////////////////////////////////////

    private class AbortUploadAction extends FWAction
    {
        AbortUploadAction()
        {
            super( Localizer.getString( "AbortUpload" ),
                GUIRegistry.getInstance().getPlafIconPack().getIcon("Upload.Stop"),
                Localizer.getString( "TTTAbortUpload" ) );
            refreshActionState();
        }

        public void actionPerformed( ActionEvent e )
        {
            UploadState[] states = getSelectedUploadStates();
            for ( int i = 0; i < states.length; i++ )
            {
                if ( states[i] != null && states[i].isUploadRunning() )
                {
                    states[i].stopUpload();
                }

            }
            refreshActionState();
        }

        @Override
        public void refreshActionState()
        {
            UploadState[] states = getSelectedUploadStates();
            boolean state = false;
            for ( int i = 0; i < states.length; i++ )
            {
                if ( states[i] != null && states[i].isUploadRunning() )
                {
                    state = true;
                    break;
                }
            }
            setEnabled( state );
        }
    }

    private class RemoveUploadAction extends FWAction
    {
        RemoveUploadAction()
        {
            super( Localizer.getString( "RemoveUpload" ),
                GUIRegistry.getInstance().getPlafIconPack().getIcon("Upload.Remove"),
                Localizer.getString( "TTTRemoveUpload" ) );
            refreshActionState();
        }

        public void actionPerformed( ActionEvent e )
        {
            UploadState[] states = getSelectedUploadStates();
            for ( int i = 0; i < states.length; i++ )
            {
                if ( states[i] != null )
                {
                    uploadManager.removeUploadState( states[i] );
                }
            }
            refreshActionState();
        }

        @Override
        public void refreshActionState()
        {
            if ( uploadTable.getRowCount() > 0 )
            {
                setEnabled( true );
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
            super( Localizer.getString( "ChatToHost" ),
                GUIRegistry.getInstance().getPlafIconPack().getIcon("Upload.Chat"),
                Localizer.getString( "TTTChatToHost" ) );
            refreshActionState();
        }

        public void actionPerformed( ActionEvent e )
        {
            UploadState state = getSelectedUploadState();
            if ( state == null )
            {
                return;
            }
            GUIActionPerformer.chatToHost( state.getHostAddress() );
        }

        @Override
        public void refreshActionState()
        {
            if ( uploadTable.getSelectedRowCount() == 1 )
            {
                setEnabled( true );
            }
            else
            {
                setEnabled( false );
            }
        }
    }
    
    private final class BanHostActionProvider implements BanHostActionUtils.BanHostActionProvider
    {
        public DestAddress[] getBanHostAddresses()
        {
            UploadState[] states = getSelectedUploadStates();
            final DestAddress[] addresses = new DestAddress[states.length];
            for (int i = 0; i < states.length; i++)
            {
                addresses[ i ] = states[i].getHostAddress();
            }
            return addresses;
        }

        public boolean isBanHostActionEnabled( boolean allowMultipleAddresses )
        {
            if ( uploadTable.getSelectedRow() < 0 || 
                 ( !allowMultipleAddresses && uploadTable.getSelectedRowCount() > 1 ) )
            {
                return false;
            }
            else
            {
                return true;
            }
        }
    }

/*
    private class AddToFavoritesAction extends FWAction
    {
        public AddToFavoritesAction()
        {
            super( Localizer.getString( "AddToFavorites" ),
                GUIRegistry.getInstance().getPlafIconPack().getIcon( "Upload.FavoriteHost" ),
                Localizer.getString( "TTTAddToFavorites" ) );
            refreshActionState();
        }

        public void actionPerformed( ActionEvent e )
        {
            UploadState[] states = getSelectedUploadStates();
            DestAddress[] addresses = new DestAddress[states.length];
            for (int i = 0; i < states.length; i++)
            {
                addresses[ i ] = states[i].getHostAddress();
            }
            GUIActionPerformer.addHostsToFavorites( addresses );
        }

        @Override
        public void refreshActionState()
        {
            if ( uploadTable.getSelectedRowCount() == 1 )
            {
                setEnabled( true );
            }
            else
            {
                setEnabled( false );
            }
        }
    }
*/
    class ClearUploadsAction extends FWAction
    {
        ClearUploadsAction()
        {
            super( Localizer.getString( "ClearCompleted" ),
                GUIRegistry.getInstance().getPlafIconPack().getIcon("Upload.Trash"),
                Localizer.getString( "TTTClearCompleted" ) );
            refreshActionState();
        }

        public void actionPerformed( ActionEvent e )
        {
            uploadManager.cleanUploadStateList();
        }

        @Override
        public void refreshActionState()
        {
            if ( uploadTable.getRowCount() > 0 )
            {
                setEnabled( true );
            }
            else
            {
                setEnabled( false );
            }
        }
    }
    
    private class UploadStateLogAction extends FWAction
    {
        public UploadStateLogAction()
        {
            super( Localizer.getString( "UploadTab_ViewLog" ),
                null,
                Localizer.getString( "UploadTab_TTTViewLog" ) );
            refreshActionState();
        }

        public void actionPerformed( ActionEvent e )
        {
            try
            {
                UploadState uploadState = getSelectedUploadState();
                LogBuffer buffer = uploadManager.getUploadStateLogBuffer();
                
                Collection<LogRecord> logRecords = buffer.getLogRecords( uploadState );
                if ( logRecords != null )
                {
                    LogBufferDialog dialog = new LogBufferDialog( logRecords );
                    dialog.setVisible( true );
                }
            }
            catch ( Throwable th )
            {
                NLogger.error( UploadStateLogAction.class, th, th );
            }
        }

        @Override
        public void refreshActionState()
        {
            if ( uploadTable.getSelectedRowCount() == 1 )
            {
                UploadState uploadState = getSelectedUploadState();
                if ( uploadState != null )
                {
                    setEnabled( true );
                    return;
                }
            }
            setEnabled( false );
        }
    }

    //////////////////////////////////////////////////////////////////////////
    /// Listeners
    //////////////////////////////////////////////////////////////////////////

    private class SelectionHandler implements ListSelectionListener
    {
        public void valueChanged(ListSelectionEvent e)
        {
            if ( !e.getValueIsAdjusting() )
            {
                refreshTabActions();
            }
        }
    }

    private class MouseHandler extends MouseAdapter implements MouseListener
    {
        @Override
        public void mouseReleased(MouseEvent e)
        {
            if (e.isPopupTrigger())
            {
                popupMenu((Component)e.getSource(), e.getX(), e.getY());
            }
        }

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
            if ( source == uploadTable || source == uploadTableScrollPane )
            {
                uploadPopup.show(source, x, y);
            }
        }
    }
}