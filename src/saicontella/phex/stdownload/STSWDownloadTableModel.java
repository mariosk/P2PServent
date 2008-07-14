package saicontella.phex.stdownload;

/**
 * SaiconTella project
 *
 * @author Marios Karagiannopoulos (mariosk@gmail.com)
 *
 * February 2008
 */

import java.awt.EventQueue;
import java.util.Comparator;

import javax.swing.event.TableModelEvent;

import org.apache.commons.collections.comparators.ComparableComparator;
import org.apache.commons.collections.comparators.ReverseComparator;
import org.bushe.swing.event.annotation.EventTopicSubscriber;

import phex.common.format.NumberFormatUtils;
import phex.download.swarming.SWDownloadFile;
import phex.download.swarming.SWDownloadInfo;
import phex.download.swarming.SwarmingManager;
import phex.event.ContainerEvent;
import phex.event.PhexEventTopics;
import phex.gui.common.table.FWSortableTableModel;
import phex.gui.common.table.FWSortedTableModel;
import phex.gui.comparator.ETAComparator;
import phex.gui.comparator.TransferSizeComparator;
import phex.gui.renderer.DateCellRenderer;
import phex.gui.renderer.ETACellRenderer;
import phex.gui.renderer.ProgressCellRenderer;
import phex.gui.renderer.TransferSizeCellRenderer;
import phex.servent.Servent;
import saicontella.core.STLocalizer;

public class STSWDownloadTableModel extends FWSortableTableModel
{   
    private static final int FILE_MODEL_INDEX = 0;
    private static final int PROGRESS_MODEL_INDEX = 1;
    private static final int SIZE_MODEL_INDEX = 2;
    private static final int RATE_MODEL_INDEX = 3;
    private static final int ETA_MODEL_INDEX = 4;
    private static final int CANDIDATE_COUNT_MODEL_INDEX = 5;
    private static final int STATUS_MODEL_INDEX = 6;
    private static final int PRIORITY_MODEL_INDEX = 7;
    private static final int SEARCH_TERM_MODEL_INDEX = 8;
    private static final int CREATED_TIME_MODEL_INDEX = 9;
    private static final int DOWNLOADED_TIME_MODEL_INDEX = 10;
    private static final int SHA1_MODEL_INDEX = 11;
    
    /**
     * The unique column id is not allowed to ever change over Phex releases. It
     * is used when serializing column information. The column id is containd in
     * the identifier field of the TableColumn.
     */
    private static final Integer FILE_COLUMN_ID = Integer.valueOf( 1001 );
    private static final Integer PROGRESS_COLUMN_ID = Integer.valueOf( 1002 );
    private static final Integer SIZE_COLUMN_ID = Integer.valueOf( 1003 );
    private static final Integer RATE_COLUMN_ID = Integer.valueOf( 1004 );
    private static final Integer STATUS_COLUMN_ID = Integer.valueOf( 1005 );
    private static final Integer CANDIDATE_COUNT_COLUMN_ID = Integer.valueOf( 1006 );
    private static final Integer SEARCH_TERM_COLUMN_ID = Integer.valueOf( 1007 );
    private static final Integer SHA1_COLUMN_ID = Integer.valueOf( 1008 );
    private static final Integer PRIORITY_COLUMN_ID = Integer.valueOf( 1009 );
    private static final Integer CREATED_TIME_COLUMN_ID = Integer.valueOf( 1010 );
    private static final Integer DOWNLOADED_TIME_COLUMN_ID = Integer.valueOf( 1011 );
    private static final Integer ETA_COLUMN_ID = Integer.valueOf( 1012 );

    /**
     * Column ids orderd according to its corresponding model index
     */
    private static final Integer[] COLUMN_IDS = new Integer[]
    {
        FILE_COLUMN_ID,
        PROGRESS_COLUMN_ID,
        SIZE_COLUMN_ID,
        RATE_COLUMN_ID,
        ETA_COLUMN_ID,
        CANDIDATE_COUNT_COLUMN_ID,
        STATUS_COLUMN_ID,
        PRIORITY_COLUMN_ID,
        SEARCH_TERM_COLUMN_ID,
        CREATED_TIME_COLUMN_ID,
        DOWNLOADED_TIME_COLUMN_ID,
        SHA1_COLUMN_ID
    };

    private static String[] tableColumns;
    private static Class<?>[] tableClasses;

    /**
     * Initialize super tableColumns field
     */
    static
    {
        tableColumns = new String[]
        {
            STLocalizer.getString( "File" ),
            STLocalizer.getString( "PercentSign" ),
            STLocalizer.getString( "Size" ),
            STLocalizer.getString( "Rate" ),
            STLocalizer.getString( "DownloadTable_ETA" ),
            STLocalizer.getString( "NumberOfCandidates" ),
            STLocalizer.getString( "Status" ),
            STLocalizer.getString( "Priority" ),
            STLocalizer.getString( "SearchTerm" ),
            STLocalizer.getString( "Created" ),
            STLocalizer.getString( "Downloaded" ),
            STLocalizer.getString( "SHA1" )
        };

        tableClasses = new Class[]
        {
            String.class,
            ProgressCellRenderer.class,
            TransferSizeCellRenderer.class,
            String.class,
            ETACellRenderer.class,
            String.class,
            String.class,
            Integer.class,
            String.class,
            DateCellRenderer.class,
            DateCellRenderer.class,
            String.class
        };
    }

    private SwarmingManager downloadService;

    public STSWDownloadTableModel( SwarmingManager downloadService )
    {
        super( COLUMN_IDS, tableColumns, tableClasses );
        this.downloadService = downloadService;
        Servent.getInstance().getEventService().processAnnotations( this );
    }

    public int getRowCount()
    {
        return downloadService.getDownloadFileCount();
    }

    public Object getValueAt( int row, int column )
    {
        SWDownloadFile download = downloadService.getDownloadFile( row );
        if ( download == null )
        {
            fireTableRowsDeleted( row, row );
            return null;
        }

        switch (column)
        {
        case FILE_MODEL_INDEX:
            return download.getFileName();
        case PROGRESS_MODEL_INDEX:
            return download.getProgress();
        case SIZE_MODEL_INDEX:
            // 2: handled by TransferSizeCellRenderer
            return download;
        case RATE_MODEL_INDEX:
        {
            long maxRate = download.getDownloadThrottlingRate();
            String maxRateStr;
            if ( maxRate >= Integer.MAX_VALUE )
            {
                maxRateStr = STLocalizer.getDecimalFormatSymbols().getInfinity();
            }
            else
            {
                maxRateStr = NumberFormatUtils.formatSignificantByteSize( maxRate) 
                    + STLocalizer.getString( "PerSec" );
            }
            return NumberFormatUtils.formatSignificantByteSize( 
                download.getTransferSpeed() ) + STLocalizer.getString( "PerSec" )
                + " (" + maxRateStr + ")";
        }
        case ETA_MODEL_INDEX:
            return download;
        case CANDIDATE_COUNT_MODEL_INDEX:
            return String.valueOf(download.getDownloadingCandidatesCount())
                + " / "
                + String.valueOf(download.getQueuedCandidatesCount())
                + " / " + String.valueOf(download.getCandidatesCount());
        case STATUS_MODEL_INDEX:
            return SWDownloadInfo.getDownloadFileStatusString(download
                .getStatus());
        case PRIORITY_MODEL_INDEX:
            return downloadService.getDownloadPriority(download);
        case SEARCH_TERM_MODEL_INDEX:
            return download.getResearchSetting().getSearchTerm();
        case CREATED_TIME_MODEL_INDEX:
            return download.getCreatedDate();
        case DOWNLOADED_TIME_MODEL_INDEX:
            return download.getDownloadedDate();
        case SHA1_MODEL_INDEX:
            return download.getResearchSetting().getSHA1();
        default:
            return "";
        }
    }

    /**
     * Returns an attribute value that is used for comparing on sorting
     * for the cell at row and column. If not overwritten the call is forwarded
     * to getValueAt().
     * The returned Object is compared via the Comparator returned from
     * getColumnComparator(). If no comparator is specified the returned Object
     * must implement the Comparable interface.
     */
    public Object getComparableValueAt( int row, int column )
    {
        SWDownloadFile download = downloadService.getDownloadFile( row );
        if ( download == null )
        {
            return "";
        }
        switch ( column )
        {
            case CANDIDATE_COUNT_MODEL_INDEX:
                return Integer.valueOf( download.getCandidatesCount() );
            case RATE_MODEL_INDEX:
                return Long.valueOf( download.getTransferSpeed() );
            case ETA_MODEL_INDEX:
                return download;
        }
        return getValueAt( row, column );
    }

    /**
     * Returns the most comparator that is used for sorting of the cell values
     * in the column. This is used by the FWSortedTableModel to perform the
     * sorting. If not overwritten the method returns null causing the
     * FWSortedTableModel to use a NaturalComparator. It expects all Objects that
     * are returned from getComparableValueAt() to implement the Comparable interface.
     *
     */
    public Comparator getColumnComparator( int column )
    {
        switch( column )
        {
            case CANDIDATE_COUNT_MODEL_INDEX:
            	return FWSortedTableModel.REVERSE_COMPARABLE_COMPARATOR;
            case PROGRESS_MODEL_INDEX:
                return FWSortedTableModel.REVERSE_COMPARABLE_COMPARATOR;
            case RATE_MODEL_INDEX:
            	return FWSortedTableModel.REVERSE_COMPARABLE_COMPARATOR;
            case ETA_MODEL_INDEX:
                return new ETAComparator();
            case SIZE_MODEL_INDEX:
                return new ReverseComparator( new TransferSizeComparator() );
            case CREATED_TIME_MODEL_INDEX:
                return ComparableComparator.getInstance();
            case DOWNLOADED_TIME_MODEL_INDEX:
                return ComparableComparator.getInstance();
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
    
    /**
     * Indicates if a column is visible by default.
     */
    @Override
    public boolean isColumnDefaultVisible( int columnIndex )
    {
        if ( columnIndex == SHA1_MODEL_INDEX
          || columnIndex == DOWNLOADED_TIME_MODEL_INDEX
          || columnIndex == CREATED_TIME_MODEL_INDEX )
        {
            return false;
        }
        return true;
    }
    
    @EventTopicSubscriber(topic=PhexEventTopics.Download_File)
    public void onDownloadFileEvent( String topic, final ContainerEvent event )
    {
        EventQueue.invokeLater( new Runnable() {
            public void run()
            {
                int position = event.getPosition();
                if ( event.getType() == ContainerEvent.Type.ADDED )
                {
                    fireTableChanged( new TableModelEvent(STSWDownloadTableModel.this,
                        position, position, TableModelEvent.ALL_COLUMNS,
                        TableModelEvent.INSERT ) );
                }
                else if ( event.getType() == ContainerEvent.Type.REMOVED )
                {
                    fireTableChanged( new TableModelEvent(STSWDownloadTableModel.this,
                        position, position, TableModelEvent.ALL_COLUMNS,
                        TableModelEvent.DELETE ) );            
                }
            }
        });
    }
}