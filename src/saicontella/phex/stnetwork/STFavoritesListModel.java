package saicontella.phex.stnetwork;

/**
 * SaiconTella project
 *
 * @author Marios Karagiannopoulos (mariosk@gmail.com)
 *
 * February 2008
 */

import java.awt.EventQueue;

import javax.swing.AbstractListModel;

import org.bushe.swing.event.annotation.EventTopicSubscriber;

import phex.event.ContainerEvent;
import phex.event.PhexEventTopics;
import phex.event.ContainerEvent.Type;
import phex.host.FavoriteHost;
import phex.host.FavoritesContainer;
import phex.servent.Servent;

public class STFavoritesListModel extends AbstractListModel
{
    private final FavoritesContainer favoritesContainer;

    public STFavoritesListModel( FavoritesContainer favoritesContainer )
    {
        Servent.getInstance().getEventService().processAnnotations( this );
        this.favoritesContainer = favoritesContainer;
    }

    public int getSize()
    {
        return favoritesContainer.getBookmarkedHostsCount();
    }

    public Object getElementAt( int row )
    {
        FavoriteHost host = favoritesContainer.getBookmarkedHostAt( row );
        if ( host == null )
        {
            fireIntervalRemoved( this, row, row );
            return "";
        }
        return host;
    }
    
    @EventTopicSubscriber(topic=PhexEventTopics.Net_Favorites)
    public void onBookmarkedHostEventAdded( String topic, final ContainerEvent event )
    {
        EventQueue.invokeLater( new Runnable() {
            public void run()
            {
                if ( event.getType() == Type.ADDED )
                {
                    fireIntervalAdded( this, event.getPosition(), event.getPosition() );
                }
                else if ( event.getType() == Type.REMOVED )
                {
                    fireIntervalRemoved( this, event.getPosition(), event.getPosition() );
                }
            }
        });
    }
}