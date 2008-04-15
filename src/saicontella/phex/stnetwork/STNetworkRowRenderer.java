package saicontella.phex.stnetwork;

/**
 * SaiconTella project
 *
 * @author Marios Karagiannopoulos (mariosk@gmail.com)
 *
 * February 2008
 */

import java.awt.*;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import phex.gui.common.GUIUtils;
import phex.gui.common.PhexColors;
import phex.gui.common.table.FWTable;
import phex.host.*;

/**
 *
 */
public class STNetworkRowRenderer implements TableCellRenderer
{
    private static final Color FAILED_COLOR = Color.gray;
    //private static final Color CONNECTING_COLOR = new Color( 0x7F, 0x00, 0x00 );
    //private static final Color CONNECTING_COLOR_SELECTED = new Color( 0xFF, 0x7f, 0x7f );
    //private static final Color CONNECTED_COLOR = new Color( 0x00, 0x7F, 0x00 );
    //private static final Color CONNECTED_COLOR_SELECTED = new Color( 0x7f, 0xFF, 0x7f );

    private NetworkHostsContainer hostsContainer;

    private Color connectingColor;
    private Color selectionConnectingColor;
    private Color connectedColor;
    private Color selectionConnectedColor;

    public STNetworkRowRenderer( NetworkHostsContainer hostsContainer )
    {
        this.hostsContainer = hostsContainer;
    }

    /**
     * @Override
     */
    public void updateUI()
    {
        connectingColor = null;
        selectionConnectingColor = null;
        connectedColor = null;
        selectionConnectedColor = null;
    }

    private Color getForegroundColorForStatus( HostStatus status, JTable table, boolean isSelected )
    {
        switch ( status )
        {
            case NOT_CONNECTED:
                return null;

            case ERROR:
            case DISCONNECTED:
                return FAILED_COLOR;

            case CONNECTING:
            case ACCEPTING:
                if ( isSelected )
                {
                    selectionConnectingColor = determineColor( selectionConnectingColor,
                        PhexColors.NETWORK_HOST_CONNECTING_COLORS, table, isSelected );
                    return selectionConnectingColor;
                }
                else
                {
                    connectingColor = determineColor( connectingColor,
                        PhexColors.NETWORK_HOST_CONNECTING_COLORS, table, isSelected );
                    return connectingColor;
                }

            case CONNECTED:
                if ( isSelected )
                {
                    selectionConnectedColor = determineColor( selectionConnectedColor,
                        PhexColors.NETWORK_HOST_CONNECTED_COLORS, table, isSelected );
                    return selectionConnectedColor;
                }
                else
                {
                    connectedColor = determineColor( connectedColor,
                        PhexColors.NETWORK_HOST_CONNECTED_COLORS, table, isSelected );
                    return connectedColor;
                }
        }
        return null;
    }

    private Color determineColor( Color cache, Color[] candidates, JTable table, boolean isSelected )
    {
        if ( cache != null )
        {
            return cache;
        }
        Color base = isSelected ? table.getSelectionBackground() : table.getBackground();
        return GUIUtils.getBestColorMatch( base, candidates );
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
        boolean isSelected, boolean hasFocus, int row, int column)
    {
        TableCellRenderer renderer = table.getDefaultRenderer(
            table.getColumnClass( column ) );
        Component comp = renderer.getTableCellRendererComponent( table, value,
            isSelected, hasFocus, row, column );
        FWTable fwTable = (FWTable) table;
        if ( isSelected )
        {
            comp.setForeground( table.getSelectionForeground() );
        }
        else
        {
            comp.setForeground( table.getForeground() );
        }


        if (row < hostsContainer.getNetworkHostCount() )
        {
            int modelRow = fwTable.translateRowIndexToModel( row );
            Host host = hostsContainer.getNetworkHostAt( modelRow );
            if ( host == null )
            {
                return comp;
            }
            Color col = getForegroundColorForStatus( host.getStatus(), fwTable, isSelected );
            if ( col != null )
            {
                comp.setForeground( col );
            }
        }
        return comp;
    }
}