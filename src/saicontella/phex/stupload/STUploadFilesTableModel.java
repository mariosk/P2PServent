package saicontella.phex.stupload;

/**
 * SaiconTella project
 *
 * @author Marios Karagiannopoulos (mariosk@gmail.com)
 *
 * May 2008
 */

import java.awt.EventQueue;
import java.util.Comparator;

import javax.swing.event.TableModelEvent;

import org.apache.commons.collections.comparators.ComparableComparator;
import org.bushe.swing.event.annotation.EventTopicSubscriber;

import phex.common.format.NumberFormatUtils;
import phex.event.ContainerEvent;
import phex.event.PhexEventTopics;
import phex.gui.common.table.FWSortableTableModel;
import phex.gui.comparator.DestAddressComparator;
import phex.gui.comparator.TransferSizeComparator;
import phex.gui.renderer.ETACellRenderer;
import phex.gui.renderer.HostAddressCellRenderer;
import phex.gui.renderer.ProgressCellRenderer;
import phex.gui.renderer.TransferSizeCellRenderer;
import phex.servent.Servent;
import phex.upload.UploadManager;
import phex.upload.UploadState;
import phex.utils.Localizer;
import saicontella.core.STLibrary;

public class STUploadFilesTableModel extends FWSortableTableModel
{
    public static final int HOST_MODEL_INDEX = 0;
    //public static final int VENDOR_MODEL_INDEX = 1;
    public static final int FRIEND_MODEL_INDEX = 1;
    public static final int FILE_MODEL_INDEX = 2;
    public static final int PROGRESS_MODEL_INDEX = 3;
    public static final int SIZE_MODEL_INDEX = 4;
    public static final int RATE_MODEL_INDEX = 5;
    public static final int ETA_MODEL_INDEX = 6;
    public static final int STATUS_MODEL_INDEX = 7;
    
    /**
     * The unique column id is not allowed to ever change over Phex releases. It
     * is used when serializing column information. The column id is containd in
     * the identifier field of the TableColumn.
     */
    public static final int HOST_COLUMN_ID = 1001;
    //public static final int VENDOR_COLUMN_ID = 1002;
    public static final int FRIEND_COLUMN_ID = 1002;    
    public static final int FILE_COLUMN_ID = 1003;
    public static final int PROGRESS_COLUMN_ID = 1004;
    public static final int SIZE_COLUMN_ID = 1005;
    public static final int RATE_COLUMN_ID = 1006;
    public static final int STATUS_COLUMN_ID = 1007;
    public static final int ETA_COLUMN_ID = 1008;

    /**
     * Column ids orderd according to its corresponding model index
     */
    private static final Integer[] COLUMN_IDS = new Integer[]
    {
        HOST_COLUMN_ID,
        //VENDOR_COLUMN_ID,
        FRIEND_COLUMN_ID,            
        FILE_COLUMN_ID,
        PROGRESS_COLUMN_ID,
        SIZE_COLUMN_ID,
        RATE_COLUMN_ID,
        ETA_COLUMN_ID,
        STATUS_COLUMN_ID
    };
    

    private static String[] tableColumns;
    private static Class[] tableClasses;

    static
    {
        tableColumns = new String[]
        {
            Localizer.getString( "Host" ),
            //Localizer.getString( "Vendor" ),
            "User",
            Localizer.getString( "File" ),
            Localizer.getString( "PercentSign" ),
            Localizer.getString( "Size" ),
            Localizer.getString( "Rate" ),
            Localizer.getString( "UploadTable_ETA" ),
            Localizer.getString( "Status" )
        };

        tableClasses = new Class[]
        {
            HostAddressCellRenderer.class,
            //String.class,
            String.class,
            String.class,
            ProgressCellRenderer.class,
            TransferSizeCellRenderer.class,
            String.class,
            ETACellRenderer.class,
            String.class
        };
    }

    private UploadManager uploadMgr;

    public STUploadFilesTableModel( UploadManager uploadMgr )
    {
        super( COLUMN_IDS, tableColumns, tableClasses );
        this.uploadMgr = uploadMgr;
        Servent.getInstance().getEventService().processAnnotations( this );
    }

    public int getRowCount()
    {
        return uploadMgr.getUploadListSize();
    }

    public Object getValueAt( int row, int col )
    {
        UploadState uploadState = uploadMgr.getUploadStateAt( row );
        if ( uploadState == null )
        {
            fireTableRowsDeleted( row, row );
            return "";
        }

        switch ( col )
        {
            case HOST_MODEL_INDEX:
                return uploadState.getHostAddress();
/*
            case VENDOR_MODEL_INDEX:
                String vendor = uploadState.getVendor();
                if ( vendor == null )
                {
                    return "";
                }
                else
                {
                    return vendor;
                }
*/
            case FRIEND_MODEL_INDEX:
                return STLibrary.getInstance().getGnutellaFramework().getFriendNameFromIpAddressAndPort(uploadState.getHostAddress().getIpAddress(), uploadState.getHostAddress().getPort());
            
            case FILE_MODEL_INDEX:
                return uploadState.getFileName();

            case PROGRESS_MODEL_INDEX:
                return uploadState.getProgress();

            case SIZE_MODEL_INDEX:
                return uploadState;
            case RATE_MODEL_INDEX:
            {
                return NumberFormatUtils.formatSignificantByteSize( 
                    uploadState.getTransferSpeed() ) + Localizer.getString( "PerSec" );
            }
            case ETA_MODEL_INDEX:
                return uploadState;
            case STATUS_MODEL_INDEX:
                return STUploadStatusInfo.getUploadStatusString( uploadState.getStatus() );
        }
        return "";
    }
    
    /**
     * Returns an attribute value that is used for comparing on sorting
     * for the cell at row and column. If not overwritten the call is forwarded
     * to getValueAt().
     * The returned Object is compared via the Comparator returned from
     * getColumnComparator(). If no comparator is specified the returned Object
     * must implement the Comparable interface.
     */
    @Override
    public Object getComparableValueAt( int row, int column )
    {
        UploadState uploadState = uploadMgr.getUploadStateAt( row );
        if ( uploadState == null )
        {
            return "";
        }
        switch ( column )
        {
            case RATE_MODEL_INDEX:
                return new Long( uploadState.getTransferSpeed() );
        }
        return getValueAt( row, column );
    }

    /**
     * Returns the most comparator that is used for sorting of the cell values
     * in the column. This is used by the FWSortedTableModel to perform the
     * sorting. If not overwritten the method returns null causing the
     * FWSortedTableModel to use a NaturalComparator. It expects all Objects that
     * are returned from getComparableValueAt() to implement the Comparable interface.
     */
    @Override
    public Comparator getColumnComparator( int column )
    {
        switch( column )
        {
            case HOST_MODEL_INDEX:
                return new DestAddressComparator();
            case PROGRESS_MODEL_INDEX:
                return ComparableComparator.getInstance();
            case SIZE_MODEL_INDEX:
                return new TransferSizeComparator();
            // for all other columns use default comparator
            default:
                return null;
        }
    }
    
    /**
     * Indicates if a column is hideable.
     */
    @Override
    public boolean isColumnHideable( int columnIndex )
    {
        if ( columnIndex == FILE_MODEL_INDEX )
        {
            return false;
        }
        return true;
    }
    
    @EventTopicSubscriber(topic=PhexEventTopics.Upload_State)
    public void onUploadStateEvent( String topic, final ContainerEvent event )
    {
        EventQueue.invokeLater( new Runnable() {
            public void run()
            {
                int position = event.getPosition();
                if ( event.getType() == ContainerEvent.Type.ADDED )
                {
                    fireTableChanged(
                        new TableModelEvent( STUploadFilesTableModel.this, position, position,
                        TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT ) );
                }
                else if ( event.getType() == ContainerEvent.Type.REMOVED )
                {
                    fireTableChanged(
                        new TableModelEvent( STUploadFilesTableModel.this, position, position,
                        TableModelEvent.ALL_COLUMNS, TableModelEvent.DELETE ) );
                }
            }
        } );
    }
        
}