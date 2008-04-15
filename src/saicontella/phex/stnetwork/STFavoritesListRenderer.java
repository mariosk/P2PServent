package saicontella.phex.stnetwork;

/**
 * SaiconTella project
 *
 * @author Marios Karagiannopoulos (mariosk@gmail.com)
 *
 * February 2008
 */

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.JList;

import phex.common.address.DestAddress;
import phex.gui.common.GUIRegistry;
import phex.gui.common.IconPack;
import phex.host.FavoriteHost;

/**
 *
 */
public class STFavoritesListRenderer extends DefaultListCellRenderer
{
    private IconPack iconFactory;

    public STFavoritesListRenderer()
    {
        iconFactory = GUIRegistry.getInstance().getCountryIconPack();
    }

    @Override
    public Component getListCellRendererComponent( JList list, Object value,
        int index, boolean isSelected, boolean cellHasFocus)
    {
        super.getListCellRendererComponent( list, value, index, isSelected, cellHasFocus );

        if ( value instanceof FavoriteHost )
        {
            FavoriteHost host = (FavoriteHost)value;
            DestAddress hostAddress = host.getHostAddress();
            setText( hostAddress.getFullHostName() );
            String countryCode = hostAddress.getCountryCode();
            Icon icon = null;
            if ( countryCode != null && countryCode.length() > 0 )
            {
                icon = iconFactory.getIcon( countryCode );
            }
            setIcon( icon );
        }
        return this;
    }
}