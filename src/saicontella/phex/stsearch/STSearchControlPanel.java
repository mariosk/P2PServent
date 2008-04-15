package saicontella.phex.stsearch;

/**
 * SaiconTella project
 *
 * @author Marios Karagiannopoulos (mariosk@gmail.com)
 *
 * February 2008
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;

import org.bushe.swing.event.annotation.EventTopicSubscriber;

import phex.common.address.DefaultDestAddress;
import phex.common.address.DestAddress;
import phex.common.address.MalformedDestAddressException;
import phex.event.PhexEventTopics;
import phex.gui.common.BoxPanel;
import phex.gui.common.table.FWTable;
import phex.gui.tabs.search.SearchResultsDataModel;
import phex.net.repres.PresentationManager;
import phex.query.KeywordSearch;
import phex.query.Search;
import phex.query.SearchContainer;
import phex.query.SearchDataEvent;
import phex.rules.SearchFilterRules;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import saicontella.core.STLibrary;
import saicontella.phex.stsearch.STSearchResultsDataModel;

public class STSearchControlPanel extends JPanel
{
    private final SearchContainer searchContainer;
    private final SearchFilterRules filterRules;
    
    private STSearchResultsDataModel displayedDataModel;
    private STSearchTab searchTab;
    private int prefWidth;

    private STSearchActivityBox activityBox;

    private JPanel searchBoxContentPanel;
    private STKeywordSearchBox keywordSearchBox;
    
    private STSearchInfoBox infoBox;
    private JScrollPane scrollPane;
    
    public STSearchControlPanel( STSearchTab tab, SearchContainer searchContainer,
        SearchFilterRules filterRules )
    {
        super( new GridBagLayout() );
        searchTab = tab;
        this.searchContainer = searchContainer;
        this.filterRules = filterRules;
        initializeComponent();
        updateUI();
        
        STLibrary.getInstance().getGnutellaFramework().getServent().getEventService().processAnnotations( this );
    }
    
    /**
     * Clears the search history in the search control panel and configuration.
     */
    public void clearSearchHistory()
    {
        keywordSearchBox.clearSearchHistory();        
    }

    public void initializeComponent()
    {
        CellConstraints cc = new CellConstraints();
        
        // TODO who knows why the ScrollPane causes such strange jumping of the panels width...
        // cant find a way to fix it...
        
        //PanelBuilder boxBuilder = new PanelBuilder( layout, this );
         
        //scrollPane = new JScrollPane( innerPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
        //    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
        //FWTable.updateFWTableScrollPane( scrollPane );
        //boxBuilder.add( scrollPane, cc.xy( 1, 1 ) );
//        
//        FormLayout layout = new FormLayout(
//            "d", // columns
//            "fill:d:grow" ); // rows
//        PanelBuilder boxBuilder = new PanelBuilder( layout, this );
//        
//        JPanel innerPanel = new JPanel();
        //boxBuilder.add( innerPanel, cc.xy( 1, 1 ) );
        
        FormLayout layout = new FormLayout(
            "fill:d:grow", // columns
            "p, p, p" ); // rows
        PanelBuilder cpPanelBuilder = new PanelBuilder( layout, this );
        
        keywordSearchBox = new STKeywordSearchBox( this );
        activityBox = new STSearchActivityBox( searchTab, this );

        infoBox = new STSearchInfoBox( this );
        
        // get prefered width of all boxes to calc width for all
        // and match height of all search boxes to reduce jumping
        prefWidth = 0;
        int prefHeight = 0;
        Dimension actPref = activityBox.getPreferredSize();
        prefWidth = Math.max( prefWidth, actPref.width );
        Dimension infoPref = infoBox.getPreferredSize();
        prefWidth = Math.max( prefWidth, infoPref.width );
        
        Dimension ksbPref = keywordSearchBox.getPreferredSize();
        prefWidth = Math.max( prefWidth, ksbPref.width );
        prefHeight = Math.max( prefHeight, ksbPref.height );

        actPref.width = prefWidth;
        keywordSearchBox.setPreferredSize( actPref );
        infoPref.width = prefWidth;
        keywordSearchBox.setPreferredSize( infoPref );
        
        ksbPref.width = prefWidth;
        ksbPref.height = prefHeight;
        keywordSearchBox.setPreferredSize( ksbPref );
        
        searchBoxContentPanel = new JPanel( new BorderLayout() );
        cpPanelBuilder.add( searchBoxContentPanel, cc.xy( 1, 1 ) );
        cpPanelBuilder.add( activityBox, cc.xy( 1, 2 ) );
        cpPanelBuilder.add( infoBox, cc.xy( 1, 3 ) );

        activityBox.postInit();
    }
    
    @Override
    public Dimension getPreferredSize()
    {
        Dimension dim = super.getPreferredSize();
        dim.width = prefWidth;
        return dim;
    }
    
    private void activateSearchBox( BoxPanel boxPanel )
    {
        searchBoxContentPanel.removeAll();
        searchBoxContentPanel.add( boxPanel, BorderLayout.CENTER );
        searchBoxContentPanel.doLayout();
        searchBoxContentPanel.revalidate();
        searchBoxContentPanel.repaint();
    }
    
    public void setDisplayedSearch( STSearchResultsDataModel searchResultsDataModel )
    {
        // otherwise no need to update...
        if ( displayedDataModel != searchResultsDataModel )
        {
            displayedDataModel = searchResultsDataModel;
            infoBox.setDisplayedSearch( displayedDataModel );
            updateControlPanel();
        }
    }
    
    public void activateKeywordSearchBox()
    {
        activateSearchBox( keywordSearchBox );
        keywordSearchBox.focusInputField();
    }

    private void updateControlPanel()
    {
        assert EventQueue.isDispatchThread() : "Not on EDT!";
        if ( displayedDataModel != null )
        {
            activityBox.displayRunningSearchPanel();

            Search search = displayedDataModel.getSearch();
            if ( search instanceof KeywordSearch )
            {
                activateSearchBox( keywordSearchBox );
                keywordSearchBox.updateControlPanel( (KeywordSearch)search );
            }
            else
            {
                throw new RuntimeException("Unknwon search type");
            }
        }
        else
        {// this is the case for a new search.
            activityBox.displayNewSearchPanel();

            keywordSearchBox.updateControlPanel( null );
        }
    }
    
    /**
     * This is overloaded to update the combo box size on
     * every UI update. Like font size change!
     */
    @Override
    public void updateUI()
    {
        super.updateUI();
        
        Color shadow = UIManager.getColor( "controlDkShadow" );
        Color window = UIManager.getColor( "window" );
        setBorder( BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder( 0, 0, 1, 1, window ),
            BorderFactory.createMatteBorder( 1, 1, 1, 1, shadow ) ) );
        setBackground( window );
        
        if ( scrollPane != null )
        {
            FWTable.updateFWTableScrollPane( scrollPane );
        }
    }
    
    /**
     * Submits a new search.
     */
    public boolean startKeywordSearch( String searchString )
    {
        if ( displayedDataModel != null && displayedDataModel.getSearch().isSearching() )
        {
            return false;
        }

        // try to find a existing and running search with the same search string
        // and select it if found.
        Search existingSearch = searchContainer.getRunningKeywordSearch( searchString );
        if ( existingSearch != null )
        {
            STSearchResultsDataModel searchResultsDataModel = 
                STSearchResultsDataModel.lookupResultDataModel( existingSearch );
            searchTab.setDisplayedSearch( searchResultsDataModel );
            return false;
        }
            
        if ( displayedDataModel == null )
        {
            Search newSearch = searchContainer.createSearch( searchString );
            STSearchResultsDataModel searchResultsDataModel =
                STSearchResultsDataModel.registerNewSearch( newSearch, filterRules );
            searchTab.setDisplayedSearch( searchResultsDataModel );
            
        }
        else
        {
            Search search = displayedDataModel.getSearch();
            if ( search instanceof KeywordSearch )
            {
                KeywordSearch keySearch = (KeywordSearch) search;
                keySearch.setSearchString( searchString, 
                    STLibrary.getInstance().getGnutellaFramework().getServent().isFirewalled() );
                keySearch.startSearching( STLibrary.getInstance().getGnutellaFramework().getServent().getQueryService() );
            }
            else
            {
                assert false : "Edited none keyword search";
                return false;
            }
        }
        return true;
    }
    
    /**
     * Submits a new search.
     */
    public boolean startBrowseHost( String hostName )
    {
        if ( displayedDataModel != null && displayedDataModel.getSearch().isSearching() )
        {
            return false;
        }
        
        PresentationManager presMgr = PresentationManager.getInstance();
        DestAddress destAddress;
        try
        {
            destAddress = presMgr.createHostAddress( hostName, 
                DefaultDestAddress.DEFAULT_PORT);
        }
        catch (MalformedDestAddressException exp)
        {
            // malformed address
            return false;
        }

        // try to find a existing and running search with the same search string
        // and select it if found.
        Search existingSearch = searchContainer.getRunningBrowseHost( destAddress, 
            null );
        if ( existingSearch != null )
        {
            STSearchResultsDataModel searchResultsDataModel =
                STSearchResultsDataModel.lookupResultDataModel( existingSearch );
            searchTab.setDisplayedSearch( searchResultsDataModel );
            return false;
        }

        Search newSearch = searchContainer.createBrowseHostSearch( destAddress, null );
        STSearchResultsDataModel searchResultsDataModel =
            STSearchResultsDataModel.registerNewSearch( newSearch, filterRules );
        searchTab.setDisplayedSearch( searchResultsDataModel );
        
        return true;
    }
    
    public void startWhatsNewSearch()
    {
        if ( displayedDataModel != null && displayedDataModel.getSearch().isSearching() )
        {
            return;
        }
        
        Search newSearch = searchContainer.createWhatsNewSearch( );
        STSearchResultsDataModel searchResultsDataModel =
            STSearchResultsDataModel.registerNewSearch( newSearch, filterRules );
        searchTab.setDisplayedSearch( searchResultsDataModel );
    }
    
    public void stopSearching()
    {
        if ( displayedDataModel != null && displayedDataModel.getSearch().isSearching() )
        {
            displayedDataModel.getSearch().stopSearching();
        }
    }
    
    /////////////////////////////// Start SearchChangeListener /////////////////////////////
    
    @EventTopicSubscriber(topic=PhexEventTopics.Search_Data)
    public void onSearchDataEvent( String topic, SearchDataEvent event )
    {
        if ( displayedDataModel == null )
        {
            return;
        }
        if ( displayedDataModel.getSearch() != event.getSource() )
        {
            return;
        }
        short type = event.getType();
        switch (type)
        {
            case SearchDataEvent.SEARCH_STARTED:
            case SearchDataEvent.SEARCH_STOPED:
            case SearchDataEvent.SEARCH_CHANGED:
                EventQueue.invokeLater( new Runnable()
                {
                    public void run()
                    {
                        updateControlPanel();
                    }
                } );
        }
    }
    
    /////////////////////////////// End SearchChangeListener /////////////////////////////
}