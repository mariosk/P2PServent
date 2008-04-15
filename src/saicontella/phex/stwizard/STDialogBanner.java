package saicontella.phex.stwizard;

/**
 * SaiconTella project
 *
 * @author Marios Karagiannopoulos (mariosk@gmail.com)
 *
 * March 2008
 */

import java.awt.*;

import javax.swing.*;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import phex.gui.common.GUIRegistry;
import phex.gui.common.PhexColors;
import saicontella.core.STResources;
import saicontella.core.STLibrary;

/**
 *
 */
public class STDialogBanner extends JPanel
{
    private String headerText;
    private String subHeaderText;
    private Icon image;
    
    private JLabel titleLabel;
    
    public STDialogBanner( String aHeaderText, String aSubHeaderText )
    {
        super();
        
        headerText = aHeaderText;
        subHeaderText = aSubHeaderText;
        
        image = STLibrary.getInstance().resizeMyImageIcon(new ImageIcon(STResources.getStr( "myApplicationIcon.ico" )), 40, 40);

        CellConstraints cc = new CellConstraints();
        FormLayout layout = new FormLayout(
            "4dlu, 2dlu, d:grow", // columns
            "3dlu, d, 3dlu, d, 3dlu"); //rows);
        PanelBuilder builder = new PanelBuilder( layout, this );
        setBackground( Color.WHITE );
        
        Font font = UIManager.getFont("TitledBorder.font");
        font = font.deriveFont( Font.BOLD, font.getSize() + 2);
        titleLabel = new JLabel( headerText );
        titleLabel.setFont( font );
        builder.add( titleLabel, cc.xywh( 2, 2, 2, 1 ) );
        
        font = UIManager.getFont( "Label.font" );
        font = font.deriveFont( Font.PLAIN, font.getSize() - 1 );
        JLabel subLabel = new JLabel( subHeaderText );
        subLabel.setFont( font );
        builder.add( subLabel, cc.xy( 3, 4 ) );
    }
    
    public void setHeaderText( String headerText )
    {
        titleLabel.setText( headerText );
    }
      
    /* These rectangles/insets are allocated once for all 
     * ButtonUI.paint() calls.  Re-using rectangles rather than 
     * allocating them in each paint call substantially reduced the time
     * it took paint to run.  Obviously, this method can't be re-entered.
     */
    private static Rectangle viewRect = new Rectangle();
    
    protected void paintComponent(Graphics g)
    {
        // paint background.
        g.setColor( getBackground() );
        g.fillRect( 0, 0, getWidth(), getHeight() );
        
        Insets i = getInsets();
        viewRect.x = i.left;
        viewRect.y = i.top;
        viewRect.width = getWidth() - (i.right + viewRect.x);
        viewRect.height = getHeight() - (i.bottom + viewRect.y);
        
        // paint gradient
        Graphics2D g2 = (Graphics2D)g;
        Paint gradient = new GradientPaint(
            0, 0, PhexColors.getBoxHeaderGradientTo(),
            (int)(getWidth()/2), 0,
            getBackground() );
        g2.setPaint( gradient );
        g2.fillRect( viewRect.x, viewRect.y,
            viewRect.width, viewRect.height );
        
        if ( image != null )
        {
            int x = Math.max( 0, getWidth() - 7 - image.getIconWidth() );
            int y = 7;
            image.paintIcon( this, g2, x, y );
        }
    }
    
    @Override
    public Dimension getPreferredSize()
    {
        Dimension dim = super.getPreferredSize();
        
        int height = 0;
        int width = 0;
        if ( image != null )
        {// calc img height...
            // border of 7 above and below..
            height = image.getIconHeight() + 14;
            // border of 7 left and right
            width = image.getIconWidth() + 14;
        }
        dim.height = Math.max( dim.height, height );
        dim.width = dim.width + width;
        return dim;
    }
    
    @Override
    public Dimension getMaximumSize()
    {
        Dimension maxDim = super.getMaximumSize();
        Dimension prefDim = getPreferredSize();
        maxDim.height = prefDim.height;
        return maxDim;
    }
    
    public static void main(String args[])
    {
        PhexColors.updateColors();
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setSize( 400, 300 );
        frame.getContentPane().setLayout( new BorderLayout() );
        
        STDialogBanner banner = new STDialogBanner("New Download", "Add a new download from Magnet URI or URL" );
        frame.getContentPane().add( banner, BorderLayout.NORTH );
        
        frame.setVisible(true);
    }
}
