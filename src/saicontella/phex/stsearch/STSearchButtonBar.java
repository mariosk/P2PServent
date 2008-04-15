package saicontella.phex.stsearch;

/**
 * SaiconTella project
 *
 * @author Marios Karagiannopoulos (mariosk@gmail.com)
 *
 * February 2008
 */

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JToggleButton;

import org.bushe.swing.event.annotation.EventTopicSubscriber;

import phex.common.log.NLogger;
import phex.event.ContainerEvent;
import phex.event.PhexEventTopics;
import phex.gui.common.FWButtonBar;
import phex.gui.common.GUIRegistry;
import phex.gui.tabs.search.cp.SearchInfoBox;
import phex.gui.tabs.search.SearchResultsDataModel;
import phex.query.Search;
import phex.query.SearchContainer;
import saicontella.core.STLibrary;
import saicontella.phex.stsearch.STSearchResultsDataModel;

public class STSearchButtonBar extends FWButtonBar
{
    private final UpdateButtonsTimerAction updateButtonsAction;
    private STSearchTab searchTab;
    private STSearchResultsDataModel displayedDataModel;
    private SearchContainer searchContainer;
    private ButtonGroup searchButtonGroup;
    private Object accessLock = new Object();
    /**
     * This button is used in the button group to indicate nothing is selected.
     */
    private AbstractButton notSelectedButton;
    private HashMap<Search, STSearchButton> searchButtonMap;
    private ButtonHandler buttonHandler;
    
    public STSearchButtonBar( STSearchTab searchTab, SearchContainer searchContainer )
    {
        super();
        this.searchTab = searchTab;
        updateButtonsAction = new UpdateButtonsTimerAction();
        searchButtonGroup = new ButtonGroup();
        notSelectedButton = new JToggleButton();
        searchButtonGroup.add(notSelectedButton);
        
        searchButtonMap = new HashMap<Search, STSearchButton>();
        buttonHandler = new ButtonHandler();
        this.searchContainer = searchContainer;
        int count = searchContainer.getSearchCount();
        for ( int i = 0; i < count; i++ )
        {
            Search search = searchContainer.getSearchAt(i);
            if ( search != null )
            {
                addSearch( search );
            }
        }
        addMouseListener( new MouseListener());
        STLibrary.getInstance().getGnutellaFramework().getServent().getEventService().processAnnotations( this );
    }
    
    @Override
    public void addNotify()
    {
        super.addNotify();
        GUIRegistry.getInstance().getGuiUpdateTimer().addActionListener( 
            updateButtonsAction );
    }
    
    @Override
    public void removeNotify()
    {
        super.removeNotify();
        GUIRegistry.getInstance().getGuiUpdateTimer().removeActionListener( 
            updateButtonsAction );
    }
    
    public void setDisplayedSearch( STSearchResultsDataModel searchResultsDataModel )
    {
        if ( displayedDataModel != searchResultsDataModel )
        {
            displayedDataModel = searchResultsDataModel;
            if ( searchResultsDataModel != null )
            {
                Search search = searchResultsDataModel.getSearch();
                STSearchButton btn = searchButtonMap.get(search);
                if ( btn != null )
                {// button might not be available yet since its created delayed on 
                 // event thread.
                    btn.setSelected(true);
                }
            }
            else
            {
                notSelectedButton.setSelected(true);
            }
        }
    }
    
    private void addSearch(Search search)
    {
        STSearchButton btn = new STSearchButton( search, searchTab );
        btn.addActionListener( buttonHandler );
        
        synchronized ( accessLock )
        {
            searchButtonMap.put( search, btn );
            searchButtonGroup.add(btn);
            addButton( btn );
        }
        
        if ( displayedDataModel != null && search == displayedDataModel.getSearch() )
        {// select the button of the displayed model
            btn.setSelected(true);
        }
    }
    
    @EventTopicSubscriber(topic=PhexEventTopics.Search_Update)
    public void onSearchUpdateEvent( String topic, final ContainerEvent event )
    {
        if ( searchContainer != event.getContainer() )
        {
            return;
        }
        EventQueue.invokeLater( new Runnable() {
            public void run()
            {
                Search search = (Search) event.getSource();
                int position = event.getPosition();
                if ( event.getType() == ContainerEvent.Type.ADDED )
                {
                    addSearch(search);
                }
                else if ( event.getType() == ContainerEvent.Type.REMOVED )
                {
                    STSearchButton btn = searchButtonMap.remove(search);
                    synchronized ( accessLock )
                    {
                        searchButtonGroup.remove( btn );
                        removeButton( btn );
                    }
                }
            }
        });
    }
    
    private final class MouseListener extends MouseAdapter
    {
        @Override
        public void mouseClicked( MouseEvent e )
        {
            try
            {
                if (e.getClickCount() == 2)
                {
                    // handle double click as new search request
                    searchTab.setDisplayedSearch( null );
                }
            }
            catch (Throwable th)
            {
                NLogger.error( MouseListener.class, th, th);
            }
        }
    }

    private final class UpdateButtonsTimerAction implements ActionListener
    {
        public void actionPerformed( ActionEvent e )
        {
            try
            {
                synchronized ( accessLock )
                {
                    Iterator iterator = buttons.iterator();
                    while( iterator.hasNext() )
                    {
                        STSearchButton searchButton = (STSearchButton)iterator.next();
                        searchButton.updateButtonDisplay();
                    }
                }
            }
            catch ( Throwable th )
            {
                NLogger.error(SearchInfoBox.class, th, th);
            }
        }
    }

    private class ButtonHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            try
            {
                STSearchButton searchButton = ((STSearchButton)e.getSource());
                
                searchButton.updateButtonDisplay();
                searchTab.refreshTabActions();
                Search search = searchButton.getSearch();
                if ( search == null )
                {
                    return;
                }
                STSearchResultsDataModel dataModel = STSearchResultsDataModel.lookupResultDataModel( search );
                searchTab.setDisplayedSearch( dataModel );
            }
            catch ( Exception exp)
            {// catch all handler
                NLogger.error( ButtonHandler.class, exp, exp );
            }
        }
    }
}