package saicontella.phex;

/**
 * SaiconTella project
 *
 * @author Marios Karagiannopoulos (mariosk@gmail.com)
 *
 * June 2008
 */

import phex.gui.common.GradientPanel;
import phex.gui.common.PhexColors;

import java.awt.*;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class STFWElegantPanel extends JPanel
{
    private JLabel titleLabel;
    private JPanel headerPanel;

    public STFWElegantPanel( String title )
    {
        this( title, null );
    }

    public STFWElegantPanel( String title, Component container )
    {
        super( new BorderLayout() );
        headerPanel = new ElegantHeaderPanel( PhexColors.getBoxHeaderGradientFrom(), 
            PhexColors.getBoxHeaderGradientTo() );
        headerPanel.add( getTitleLabel(), BorderLayout.CENTER );
        
//        JButton button = new JButton(  );
//        button.setOpaque( false );
//        button.setBorder( new EmptyBorder( 2, 2, 2, 2 ) );
//        button.setText( "X" );
//        top.add( button, BorderLayout.EAST );
        add( headerPanel, BorderLayout.NORTH );
        if ( container != null )
        {
            add( container, BorderLayout.CENTER );
        }
        setTitle( title );
    }

    public String getTitle()
    {
        return getTitleLabel().getText();
    }

    private JLabel getTitleLabel()
    {
        if ( titleLabel != null ) return titleLabel;

        titleLabel = new JLabel();
        Font currentFont = titleLabel.getFont();
        titleLabel.setFont( currentFont.deriveFont( Font.BOLD, currentFont.getSize() + 2 ) );
        titleLabel.setBorder( new EmptyBorder( 6, 6, 6, 4 ) );
        titleLabel.setForeground(Color.WHITE);        
        return titleLabel;
    }

    public void setTitle( String title )
    {
        if ( title == null ) title = "";
        title = title.trim();
        getTitleLabel().setText( title );
    }
    
    public void addHeaderPanelComponent( Component comp, String constrain )
    {
        headerPanel.add(comp, constrain );
    }
    
    private static class ElegantHeaderPanel extends GradientPanel
    {
        public ElegantHeaderPanel( Color from, Color to )
        {
            super( from, to );
            setLayout(new BorderLayout());
        }
        
        private static Rectangle viewRect = new Rectangle();
        
        protected void paintComponent( Graphics g )
        {
            super.paintComponent( g );
            Insets in = getInsets();
            
            g.setColor( getBackground() );
            g.fillRect( 0, 0, getWidth(), getHeight() );
            
            Insets i = getInsets();
            viewRect.x = i.left;
            viewRect.y = i.top;
            viewRect.width = getWidth() - (i.right + viewRect.x) - 1;
            viewRect.height = getHeight() - (i.bottom + viewRect.y);
            
            // paint gradient
            Graphics2D g2 = (Graphics2D)g;
            Paint gradient = new GradientPaint(
                0, 0, fromColor,
                0, viewRect.height, toColor );
            g2.setPaint( gradient );
            g2.fillRect( viewRect.x, viewRect.y,
                viewRect.width, viewRect.height );

            g.setColor( PhexColors.getBoxPanelBorderColor() );
            g.drawLine( in.left, in.top, viewRect.width, in.top );
            g.drawLine( in.left, in.top, in.left, viewRect.height );
            g.drawLine( viewRect.width, in.top, viewRect.width, viewRect.height );
        }
    }
}