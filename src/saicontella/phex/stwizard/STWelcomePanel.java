package saicontella.phex.stwizard;

/**
 * SaiconTella project
 *
 * @author Marios Karagiannopoulos (mariosk@gmail.com)
 *
 * March 2008
 */

import javax.swing.JPanel;

import phex.gui.common.HTMLMultiLinePanel;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import saicontella.core.STResources;
import saicontella.core.STLocalizer;

public class STWelcomePanel extends JPanel
{
    private STConfigurationWizardDialog parent;
    
    public STWelcomePanel( STConfigurationWizardDialog parent )
    {
        this.parent = parent;
        prepareComponent();
    }
    
    private void prepareComponent()
    {
        FormLayout layout = new FormLayout(
            "10dlu, 200dlu:grow, 2dlu", // columns
            "p, 3dlu, top:p" );// rows 
        
        setLayout( layout );
        
        PanelBuilder builder = new PanelBuilder( layout, this );
        CellConstraints cc = new CellConstraints();
        
        builder.addSeparator( STLocalizer.getString( "ConfigWizard_WelcomeToPhexHeader" ), cc.xywh( 1, 1, 3, 1 ) );
        
        HTMLMultiLinePanel welcomeLines = new HTMLMultiLinePanel(
            STLocalizer.getString( "ConfigWizard_WelcomeToPhexText" ) );
        builder.add( welcomeLines, cc.xy( 2, 3 ) );
    }
}
