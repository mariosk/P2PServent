package saicontella.phex.stdownload;

/**
 * SaiconTella project
 *
 * @author Marios Karagiannopoulos (mariosk@gmail.com)
 *
 * February 2008
 */

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import phex.download.swarming.SWDownloadFile;
import phex.gui.common.GUIRegistry;
import phex.gui.common.GUIUtils;
import phex.gui.common.table.FWSortedTableModel;
import phex.gui.common.table.FWTable;
import phex.xml.sax.gui.DGuiSettings;
import phex.xml.sax.gui.DTable;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class STDownloadTransfersPanel extends JPanel
{
    private static final String TRANSFER_TABLE_IDENTIFIER = "TransferTable";
    
    private SWDownloadFile lastDownloadFile;
    
    private FWTable transferTable;
    private JScrollPane transferTableScrollPane;
    private STDownloadTransferTableModel transferModel;

    public STDownloadTransfersPanel()
    {
    }
    
    public void initializeComponent( DGuiSettings guiSettings )
    {
        CellConstraints cc = new CellConstraints();
        FormLayout layout = new FormLayout(
            "fill:d:grow", // columns
            "fill:d:grow"); //rows
        PanelBuilder tabBuilder = new PanelBuilder( layout, this );
        
        transferModel = new STDownloadTransferTableModel( );
        transferTable = new FWTable( new FWSortedTableModel( transferModel ) );
        GUIUtils.updateTableFromDGuiSettings( guiSettings, transferTable, 
            TRANSFER_TABLE_IDENTIFIER );
        
        transferTable.activateAllHeaderActions();
        transferTable.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
        transferTable.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );
        GUIRegistry.getInstance().getGuiUpdateTimer().addTable( transferTable );
        transferTableScrollPane = FWTable.createFWTableScrollPane( transferTable );
        
        tabBuilder.add( transferTableScrollPane, cc.xy( 1, 1 ) );
        
        GUIUtils.adjustTableProgresssBarHeight( transferTable );
    }
    
    public void updateDownloadFile( SWDownloadFile file )
    {
        lastDownloadFile = file;
        transferModel.updateDownloadFile( file );
    }
    
    /**
     * This is overloaded to update the table size for the progress bar on
     * every UI update. Like font size change!
     */
    @Override
    public void updateUI()
    {
        super.updateUI();
        if ( transferTable != null )
        {
            // increase table height a bit to display progress bar string better...
            GUIUtils.adjustTableProgresssBarHeight( transferTable );
        }
        if ( transferTableScrollPane != null )
        {
            FWTable.updateFWTableScrollPane( transferTableScrollPane );
        }
    }
    
    
    //////////////////////////////////////////////////////////////////////////
    /// XML serializing and deserializing
    //////////////////////////////////////////////////////////////////////////
    
    public void appendDGuiSettings( DGuiSettings dSettings )
    {
        DTable dTable = GUIUtils.createDTable( transferTable, TRANSFER_TABLE_IDENTIFIER );
        dSettings.getTableList().getTableList().add( dTable );
    }
}
