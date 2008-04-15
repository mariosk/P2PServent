package saicontella.phex.stsearch;

/**
 * SaiconTella project
 *
 * @author Marios Karagiannopoulos (mariosk@gmail.com)
 *
 * February 2008
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ButtonUI;
import javax.swing.plaf.basic.BasicButtonUI;

import phex.gui.common.*;
import phex.gui.tabs.search.SearchTab;
import phex.utils.Localizer;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class STSearchActivityBox extends BoxPanel
{
    private static Border ROLLOVER_BUTTON_BORDER = new CompoundBorder( 
        GUIUtils.ROLLOVER_BUTTON_BORDER, new EmptyBorder( 2, 2, 2, 2) );
    private static ButtonUI ACTIVITY_BUTTON_UI = new ActivityButtonUI();
    
    private JPanel newSearchActivityP;
    private JToggleButton keywordSearchBtn;
    private JToggleButton whatsNewBtn;
    private JToggleButton browseHostBtn;
    
    private JPanel runningSearchActivityP;
    private JButton newSearchBtn;
    private JButton closeSearchBtn;
    
    public STSearchActivityBox( STSearchTab searchTab, final STSearchControlPanel cp )
    {
        super( Localizer.getString( "SearchTab_SearchActivity" ) );
        
        CellConstraints cc = new CellConstraints();
        getContentPanel().setLayout( new BorderLayout() );
        
        newSearchActivityP = new JPanel();
        newSearchActivityP.setOpaque(false);
        
        getContentPanel().add( newSearchActivityP, BorderLayout.CENTER );
        FormLayout newSearchLayout = new FormLayout(
            "6dlu, fill:p:grow, 6dlu", // columns
            "2dlu, p, 1dlu, p, 1dlu, p, 2dlu" ); // rows
        PanelBuilder newSearchBuilder = new PanelBuilder( newSearchLayout, 
            newSearchActivityP );
        
        keywordSearchBtn = new JToggleButton( 
            Localizer.getString( "SearchTab_KeywordSearch" ), 
            GUIRegistry.getInstance().getPlafIconPack().getIcon( "Search.Search" ) );
        keywordSearchBtn.setToolTipText( Localizer.getString( "SearchTab_TTTKeywordSearch") );
        updateActivityBtnProps( keywordSearchBtn );
        keywordSearchBtn.addActionListener(new ActionListener()
            {
                public void actionPerformed( ActionEvent e )
                {
                    cp.activateKeywordSearchBox( );
                }
            });
        
        ButtonGroup group = new ButtonGroup();
        group.add(keywordSearchBtn);

        newSearchBuilder.add( keywordSearchBtn, cc.xy(2, 2) );
        
        runningSearchActivityP = new JPanel();
        runningSearchActivityP.setOpaque(false);
        FormLayout runningLayout = new FormLayout(
            "6dlu, fill:p:grow, 6dlu", // columns
            "2dlu, p, 1dlu, p, 2dlu" ); // rows
        PanelBuilder runningBuilder = new PanelBuilder( runningLayout, 
            runningSearchActivityP );
        
        newSearchBtn = new JButton( Localizer.getString( "SearchTab_NewSearch" ),
                    GUIRegistry.getInstance().getPlafIconPack().getIcon( "Search.Search") );
        newSearchBtn.setToolTipText( Localizer.getString( "SearchTab_TTTNewSearch") );
        newSearchBtn.addActionListener( 
            searchTab.getTabAction( SearchTab.CREATE_NEW_SEARCH_ACTION ) );
        updateActivityBtnProps( newSearchBtn );
        
        closeSearchBtn = new JButton( Localizer.getString( "SearchTab_CloseSearch" ), 
                    GUIRegistry.getInstance().getPlafIconPack().getIcon( "Search.Close") );
        closeSearchBtn.setToolTipText( Localizer.getString( "SearchTab_TTTCloseSearch") );
        closeSearchBtn.addActionListener( 
            searchTab.getTabAction( SearchTab.CLOSE_SEARCH_ACTION ) );
        updateActivityBtnProps( closeSearchBtn );
                
        runningBuilder.add( newSearchBtn, cc.xy(2, 2) );
        runningBuilder.add( closeSearchBtn, cc.xy(2, 4) );
    }
    
    public void postInit()
    {
        keywordSearchBtn.doClick();
    }

    public void displayRunningSearchPanel()
    {
        JPanel activityContentP = getContentPanel();
        activityContentP.removeAll();
        activityContentP.add( runningSearchActivityP, BorderLayout.CENTER );
        activityContentP.doLayout();
        activityContentP.revalidate();
        activityContentP.repaint();
        
        newSearchBtn.getModel().setRollover(false);
        closeSearchBtn.getModel().setRollover(false);
    }

    public void displayNewSearchPanel()
    {
        JPanel activityContentP = getContentPanel();
        activityContentP.removeAll();
        activityContentP.add( newSearchActivityP, BorderLayout.CENTER );
        activityContentP.doLayout();
        activityContentP.revalidate();
        activityContentP.repaint();
        keywordSearchBtn.doClick();
    }
    
    private void updateActivityBtnProps( AbstractButton b )
    {
        b.setUI( ACTIVITY_BUTTON_UI );
        b.setIconTextGap( 8 );
        b.setHorizontalAlignment( SwingConstants.LEFT );
        b.setCursor( Cursor.getPredefinedCursor( Cursor.HAND_CURSOR ) );
        b.setBorder( ROLLOVER_BUTTON_BORDER );
        b.setRolloverEnabled( true );
    }
    
    public void updateUI()
    {
        super.updateUI();
        if ( keywordSearchBtn != null )
        {
            keywordSearchBtn.setUI( ACTIVITY_BUTTON_UI );
        }
        if ( whatsNewBtn != null )
        {
            whatsNewBtn.setUI( ACTIVITY_BUTTON_UI );
        }
        if ( browseHostBtn != null )
        {
            browseHostBtn.setUI( ACTIVITY_BUTTON_UI );
        }
    }
    
    private static class ActivityButtonUI extends BasicButtonUI
    {
        private Color fromColor = PhexColors.getBoxPanelBackground().darker();
        private Color toColor = PhexColors.getBoxPanelBackground().brighter();

        public void update( Graphics g, JComponent c )
        {
            if ( c.isOpaque() )
            {
                g.setColor( PhexColors.getBoxPanelBackground() );                    
                int width = c.getWidth();
                int height = c.getHeight();
                g.fillRect( 0, 0, width, height );
                
                AbstractButton b = (AbstractButton) c;
                if ( b.isSelected() )
                {                      
                    Paint gradient = new GradientPaint( 0, 0, fromColor,
                        width/2, height/2, toColor, true );                    
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setPaint( gradient );
                    g2.fillRect( 0, 0, width, height );
                }
            }
            paint( g, c );
        }
    }
}
