package saicontella.phex.stsearch;

/**
 * SaiconTella project
 *
 * @author Marios Karagiannopoulos (mariosk@gmail.com)
 *
 * February 2008
 */

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListCellRenderer;
import javax.swing.text.Keymap;

import phex.common.log.NLogger;
import phex.gui.common.BoxPanel;
import phex.gui.common.GUIRegistry;
import phex.gui.common.GUIUtils;
import phex.gui.prefs.PhexGuiPrefs;
import phex.gui.prefs.SearchTabPrefs;
import phex.query.DynamicQueryConstants;
import phex.query.KeywordSearch;
import phex.utils.Localizer;

import com.jgoodies.forms.builder.ButtonBarBuilder;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class STKeywordSearchBox extends BoxPanel
{
    private STSearchControlPanel controlPanel;
    
    private DefaultComboBoxModel searchComboModel;
    private JComboBox searchTermComboBox;
    private JButton searchButton;
    private JButton stopButton;
    
    public STKeywordSearchBox( STSearchControlPanel cp )
    {
        super( Localizer.getString( "SearchTab_KeywordSearch" ) );
        controlPanel = cp;
        
        CellConstraints cc = new CellConstraints();
        FormLayout searchBoxLayout = new FormLayout(
            "6dlu, p, 6dlu", // columns
            "4dlu, p, 2dlu, p, 6dlu, p, 4dlu" ); // rows
        PanelBuilder searchBoxBuilder = new PanelBuilder( searchBoxLayout, 
            getContentPanel() );
        
        searchBoxBuilder.addLabel( Localizer.getString( "SearchTab_TypeYourSearch" ),
            cc.xy(2, 2) );
        
        SubmitSearchHandler submitSearchHandler = new SubmitSearchHandler();
        
        searchComboModel = new DefaultComboBoxModel(
            SearchTabPrefs.SearchTermHistory.get().toArray() );
        searchTermComboBox = new JComboBox( searchComboModel );
        searchTermComboBox.setEditable( true );
        JTextField editor = ((JTextField)searchTermComboBox.getEditor().getEditorComponent());
        Keymap keymap = JTextField.addKeymap( "SearchTermEditor", editor.getKeymap() );
        editor.setKeymap( keymap );
        keymap.addActionForKeyStroke( KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),
            submitSearchHandler );
        GUIUtils.assignKeymapToComboBoxEditor( keymap, searchTermComboBox );
        searchTermComboBox.setSelectedItem( "" );
        searchBoxBuilder.add( searchTermComboBox, cc.xy(2, 4) );
        
        searchButton = new JButton( Localizer.getString( "SearchTab_StartSearch" ),
            GUIRegistry.getInstance().getPlafIconPack().getIcon( "Search.Search" ) );
        searchButton.setCursor( Cursor.getPredefinedCursor( Cursor.HAND_CURSOR ) );        
        searchButton.setToolTipText( Localizer.getString( "SearchTab_TTTStartSearch") );
        searchButton.setMargin( GUIUtils.NARROW_BUTTON_INSETS );
        searchButton.addActionListener( submitSearchHandler );
        
        StopSearchHandler stopSearchHandler = new StopSearchHandler();
        stopButton = new JButton( Localizer.getString( "SearchTab_StopSearch" ),
            GUIRegistry.getInstance().getPlafIconPack().getIcon( "Search.Stop" ) );
        stopButton.setCursor( Cursor.getPredefinedCursor( Cursor.HAND_CURSOR ) );
        stopButton.setToolTipText( Localizer.getString( "SearchTab_TTTStopSearch") );
        stopButton.setMargin( GUIUtils.NARROW_BUTTON_INSETS );        
        stopButton.addActionListener( stopSearchHandler );
        
        ButtonBarBuilder builder = new ButtonBarBuilder();
        builder.setLeftToRightButtonOrder(true);
        builder.addFixedNarrow( searchButton );
        builder.addRelatedGap();
        builder.addFixedNarrow( stopButton );
        builder.addGlue();
        JPanel btnBar = builder.getPanel();
        btnBar.setOpaque(false);
        searchBoxBuilder.add( btnBar, cc.xy(2, 6) );
        
        adjustComponents();
    }
    
    private void adjustComponents()
    {
        if ( searchTermComboBox != null )
        {
            GUIUtils.adjustComboBoxHeight( searchTermComboBox );
            //adjust combobox width
            ListCellRenderer renderer = searchTermComboBox.getRenderer();
            if ( renderer != null )
            {
                FontMetrics fm = searchTermComboBox.getFontMetrics( searchTermComboBox.getFont() );
                int maxFmWidth = fm.getMaxAdvance() * 10;
                int minFmWidth = fm.getMaxAdvance() * 8;
                
                Dimension maxDim = searchTermComboBox.getMaximumSize();
                maxDim.width = Math.max( minFmWidth, Math.min( maxFmWidth, maxDim.width ) );
                searchTermComboBox.setMaximumSize( maxDim );
                
                Dimension prefDim = searchTermComboBox.getPreferredSize();
                prefDim.width = Math.max( minFmWidth, Math.min( maxFmWidth, prefDim.width ) );
                searchTermComboBox.setPreferredSize( prefDim );
            }
        }
        
        if ( searchButton != null )
        {
            String orgText = searchButton.getText();
            searchButton.setText( Localizer.getString( "SearchTab_StartSearch" ) );
            Dimension dim = searchButton.getPreferredSize();
            searchButton.setText( Localizer.getString( "SearchTab_Searching" ) );
            Dimension dim2 = searchButton.getPreferredSize();
            dim.width = Math.max(dim.width, dim2.width);
            searchButton.setPreferredSize(dim);
            searchButton.setText( orgText );
        }
    }
    
    /**
     * Clears the search history in the search control panel and configuration.
     */
    public void clearSearchHistory()
    {
        searchComboModel.removeAllElements();
        SearchTabPrefs.SearchTermHistory.get().clear();
        PhexGuiPrefs.save( false );
    }
    
    public void focusInputField()
    {
        searchTermComboBox.requestFocus();
    }

    
    /**
     * This is overloaded to update the combo box size on
     * every UI update. Like font size change!
     */
    @Override
    public void updateUI()
    {
        super.updateUI();
        adjustComponents();
    }
    
    public void updateControlPanel( KeywordSearch search)
    {
        if ( search != null )
        {
            String searchString = search.getSearchString();
            searchTermComboBox.setSelectedItem( searchString );
            ((JTextField)searchTermComboBox.getEditor().getEditorComponent()).setText(
                searchString );
            
            if ( search.isSearching() )
            {
                searchButton.setText( Localizer.getString( "SearchTab_Searching" ) );
                searchButton.setToolTipText( Localizer.getString( "SearchTab_TTTSearching" ) );
                searchButton.setEnabled(false);
                searchTermComboBox.setEnabled( false );
            }
            else
            {
                searchButton.setText( Localizer.getString( "SearchTab_StartSearch" ) );
                searchButton.setToolTipText( Localizer.getString( "SearchTab_TTTStartSearch") );
                searchButton.setEnabled(true);
                searchTermComboBox.setEnabled( true );
            }
        }
        else
        {// this is the case for a new search.
            searchTermComboBox.setSelectedItem( null );
            ((JTextField)searchTermComboBox.getEditor().getEditorComponent()).setText( "" );
            searchButton.setText( Localizer.getString( "SearchTab_StartSearch" ) );
            searchButton.setToolTipText( Localizer.getString( "SearchTab_TTTStartSearch") );
            searchButton.setEnabled(true);
            searchTermComboBox.setEnabled( true );
        }
    }
    
    /**
     * Submits a new search.
     */
    private class SubmitSearchHandler extends AbstractAction implements ActionListener
    {
        public void actionPerformed( ActionEvent e )
        {
            String searchStr = (String)searchTermComboBox.getEditor().getItem();
            searchComboModel.setSelectedItem( searchStr );
            searchStr = searchStr.trim();
            if ( searchStr.length() == 0 )
            {
                return;
            }

            if ( searchStr.length() < DynamicQueryConstants.MIN_SEARCH_TERM_LENGTH )
            {
                Object[] objArr = new Object[ 1 ];
                objArr[ 0 ] = new Integer( DynamicQueryConstants.MIN_SEARCH_TERM_LENGTH );
                GUIUtils.showErrorMessage( Localizer.getFormatedString(
                        "MinSearchTerm", objArr ) );
                searchTermComboBox.getEditor().selectAll();
                try
                {
                    ((JComponent)searchTermComboBox.getEditor().getEditorComponent()).requestFocus();
                }
                catch ( Exception exp )
                {
                    NLogger.error( SubmitSearchHandler.class, 
                        searchTermComboBox.getEditor().toString(), exp );
                }
                
                return;
            }
            
            boolean succ = controlPanel.startKeywordSearch( searchStr );
            if ( !succ )
            {
                return;
            }
            
            int idx = searchComboModel.getIndexOf( searchStr );
            if ( idx < 0 )
            {
                searchComboModel.insertElementAt( searchStr, 0 );
                if ( searchComboModel.getSize() >
                    SearchTabPrefs.MaxSearchHistorySize.get().intValue() )
                {
                    searchComboModel.removeElementAt(
                        searchComboModel.getSize() - 1 );
                }
                saveSearchList();
            }
            else if ( idx > 0 )
            {
                searchComboModel.removeElementAt( idx );
                searchComboModel.insertElementAt( searchStr, 0 );
                saveSearchList();
            }
        }

        private void saveSearchList()
        {
            int length = searchComboModel.getSize();
            ArrayList searchList = new ArrayList( length );
            for ( int i = 0; i < length; i++ )
            {
                searchList.add( searchComboModel.getElementAt( i ) );
            }
            SearchTabPrefs.SearchTermHistory.get().clear();
            SearchTabPrefs.SearchTermHistory.get().addAll( searchList );
            SearchTabPrefs.SearchTermHistory.changed();
            PhexGuiPrefs.save( false );
        }
    }
    
    /**
     * Submits a new search.
     */
    private class StopSearchHandler extends AbstractAction implements ActionListener
    {
        public void actionPerformed( ActionEvent e )
        {
            controlPanel.stopSearching();
        }
    }
}
