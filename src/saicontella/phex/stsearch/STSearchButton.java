package saicontella.phex.stsearch;

/**
 * SaiconTella project
 *
 * @author Marios Karagiannopoulos (mariosk@gmail.com)
 *
 * February 2008
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicButtonListener;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.plaf.basic.BasicHTML;
import javax.swing.text.View;

import org.bushe.swing.event.annotation.EventTopicSubscriber;

import phex.common.address.DestAddress;
import phex.event.PhexEventTopics;
import phex.gui.common.GUIRegistry;
import phex.gui.common.GUIUtils;
import phex.gui.common.IconPack;
import phex.gui.common.PhexColors;
import phex.gui.tabs.search.SearchResultsDataModel;
import phex.query.KeywordSearch;
import phex.query.Search;
import phex.query.SearchDataEvent;
import saicontella.core.STLibrary;

public class STSearchButton extends JToggleButton
{
    private Search search;
    private STSearchTab searchTab;
    
    public STSearchButton( Search search, STSearchTab searchTab )
    {
        super( );
        this.search = search;
        this.searchTab = searchTab;
        
        updateButtonDisplay();
        
        setUI( new SearchButtonUI() );
        setBorder( GUIUtils.ROLLOVER_BUTTON_BORDER );
        setRolloverEnabled( true );
        setHorizontalAlignment( SwingConstants.LEFT );
        setMargin( GUIUtils.EMPTY_INSETS );
        
        STLibrary.getInstance().getGnutellaFramework().getServent().getEventService().processAnnotations( this );
    }
    
    public Search getSearch()
    {
        return search;
    }
    
    public void updateButtonDisplay()
    {
        StringBuffer textBuf = new StringBuffer();
        if ( search instanceof KeywordSearch )
        {
            textBuf.append( ((KeywordSearch)search).getSearchString() );
            setIcon( GUIRegistry.getInstance().getPlafIconPack().getIcon("Search.Search") );
        }
        else
        {
            textBuf.append( search.toString() );
            setIcon( IconPack.EMPTY_IMAGE_16 );
        }
        
        SearchResultsDataModel dataModel = SearchResultsDataModel.lookupResultDataModel(search);
        int dispElem = 0;
        int filteredElem = 0;
        if ( dataModel != null )
        {
            dispElem = dataModel.getSearchElementCount();
            filteredElem = dataModel.getFilteredElementCount();
        }
        textBuf.append( " (" );
        textBuf.append( dispElem );
        if ( filteredElem > 0 )
        {
            textBuf.append( "/" )
                .append( filteredElem );
        }
        textBuf.append( ")" );
        setText( textBuf.toString() );
    }
    
    private static class SearchButtonUI extends BasicButtonUI
    {
        private static final Icon closeIcon =
            GUIRegistry.getInstance().getPlafIconPack().getIcon("Search.Close");
//        private static final Icon closeIcon = 
//            ImageFilterUtils.createGrayIcon( 
//                GUIRegistry.getInstance().getIconFactory().getIcon("Close"),
//                true, 99 );
        private Color fromColor = PhexColors.getBoxPanelBackground().darker();
        private Color toColor = PhexColors.getBoxPanelBackground().brighter();
        
        @Override
        public Dimension getMinimumSize( JComponent c )
        {
            Dimension dim = super.getMinimumSize(c);
            dim.width = 0;
            return dim;
        }

        @Override
        public Dimension getPreferredSize( JComponent c )
        {
            Dimension dim = super.getPreferredSize(c);
            dim.width = Math.max(150, dim.width + closeIcon.getIconWidth() );
            return dim;
        }

        @Override
        public void update( Graphics g, JComponent c )
        {
            if ( c.isOpaque() )
            {
                g.setColor( c.getBackground() );                    
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
        
        /* These rectangles/insets are allocated once for all 
         * ButtonUI.paint() calls.  Re-using rectangles rather than 
         * allocating them in each paint call substantially reduced the time
         * it took paint to run.  Obviously, this method can't be re-entered.
         */
        private static Rectangle viewRect = new Rectangle();
        private static Rectangle displayRect = new Rectangle();
        private static Rectangle textRect = new Rectangle();
        private static Rectangle iconRect = new Rectangle();
        
        @Override
        public void paint( Graphics g, JComponent c )
        {
            AbstractButton b = (AbstractButton) c;
            ButtonModel model = b.getModel();

            FontMetrics fm = b.getFontMetrics( g.getFont() );

            Insets i = c.getInsets();

            displayRect.x = viewRect.x = i.left;
            displayRect.y = viewRect.y = i.top;
            viewRect.width = b.getWidth() - (i.right + viewRect.x);
            if ( b.isSelected() )
            {
                displayRect.width = viewRect.width - closeIcon.getIconWidth();
            }
            else
            {
                displayRect.width = viewRect.width;
            }
            displayRect.height = viewRect.height = b.getHeight()
                - (i.bottom + viewRect.y);

            textRect.x = textRect.y = textRect.width = textRect.height = 0;
            iconRect.x = iconRect.y = iconRect.width = iconRect.height = 0;

            Font f = c.getFont();
            g.setFont( f );

            // layout the text and icon
            String text = SwingUtilities.layoutCompoundLabel( c, fm, b
                .getText(), b.getIcon(), b.getVerticalAlignment(), b
                .getHorizontalAlignment(), b.getVerticalTextPosition(), b
                .getHorizontalTextPosition(), displayRect, iconRect, textRect,
                b.getText() == null ? 0 : b.getIconTextGap() );

            clearTextShiftOffset();

            // perform UI specific press action, e.g. Windows L&F shifts text
            if ( model.isArmed() && model.isPressed() )
            {
                paintButtonPressed( g, b );
            }

            // paint the close button
            if ( b.isSelected() )
            {
                paintCloseIcon( g, b );
            }

            // Paint the Icon
            if ( b.getIcon() != null )
            {
                paintIcon( g, c, iconRect );
            }

            if ( text != null && !text.equals( "" ) )
            {
                View v = (View) c.getClientProperty( BasicHTML.propertyKey );
                if ( v != null )
                {
                    v.paint( g, textRect );
                }
                else
                {
                    paintText( g, b, textRect, text );
                }
            }

            if ( b.isFocusPainted() && b.hasFocus() )
            {
                // paint UI specific focus
                paintFocus( g, b, viewRect, textRect, iconRect );
            }
        }
        
        private void paintCloseIcon( Graphics g, AbstractButton b )
        {
            ButtonModel model = b.getModel();
            int x = b.getWidth() - b.getInsets().right - closeIcon.getIconWidth();
            int y = b.getHeight()/2 - closeIcon.getIconHeight()/2;
            if(model.isPressed() && model.isArmed()) 
            {
                closeIcon.paintIcon( b, g, x + getTextShiftOffset(),
                    y + getTextShiftOffset());
            } 
            else
            {
                closeIcon.paintIcon( b, g, x, y);
            }
        }
        
        @Override
        protected BasicButtonListener createButtonListener(AbstractButton b)
        {
            return new CloseButtonListener( b );
        }
        
        public static class CloseButtonListener extends BasicButtonListener
        {
            private STSearchButton btn;
            
            public CloseButtonListener( AbstractButton b )
            {
                super( b );
                btn = (STSearchButton)b;
            }
            
            @Override
            public void mouseReleased(MouseEvent e) 
            {
                if (SwingUtilities.isLeftMouseButton(e) ) 
                {
                    AbstractButton b = (AbstractButton) e.getSource();
                    if ( b.isSelected() )
                    {
                        int w = closeIcon.getIconWidth();
                        int h = closeIcon.getIconHeight();
                        int x = b.getWidth() - b.getInsets().right - w;
                        int y = b.getHeight()/2 - h/2;
                        Rectangle rec = new Rectangle( x, y, w, h );
                        if ( rec.contains(e.getX(), e.getY() ) )
                        {
                            btn.searchTab.closeSearch( btn.search );
                        }
                        else
                        {
                            super.mouseReleased(e);
                        }
                    }
                    else
                    {
                        super.mouseReleased(e);
                    }
                }
            }
        }
    }
    
    @EventTopicSubscriber(topic=PhexEventTopics.Search_Data)
    public void onSearchDataEvent( String topic, SearchDataEvent event )
    {
        if ( search != event.getSource() )
        {
            return;
        }
        updateButtonDisplay();
    }
}