package saicontella.phex.stwizard;

/**
 * SaiconTella project
 *
 * @author Marios Karagiannopoulos (mariosk@gmail.com)
 *
 * March 2008
 */

import javax.swing.JCheckBox;
import javax.swing.JPanel;

import phex.gui.common.HTMLMultiLinePanel;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import phex.prefs.core.DownloadPrefs;
import phex.prefs.core.SubscriptionPrefs;
import saicontella.core.STLocalizer;

public class STContentCommunityPanel extends JPanel
{
    private STConfigurationWizardDialog parent;
    private JCheckBox joinPolarSkulk;
    
    public STContentCommunityPanel( STConfigurationWizardDialog parent )
    {
        this.parent = parent;
        prepareComponent();
    }
    
    private void prepareComponent()
    {
        FormLayout layout = new FormLayout(
            "10dlu, 200dlu:grow, 2dlu", // columns
            "p, 3dlu, top:p, 16dlu, p" );// rows 
        
        setLayout( layout );
        
        PanelBuilder builder = new PanelBuilder( layout, this );
        CellConstraints cc = new CellConstraints();
        
        builder.addSeparator( STLocalizer.getString( "ConfigWizard_ContentCommunityHeader" ),
            cc.xywh( 1, 1, 3, 1 ) );
        
        HTMLMultiLinePanel welcomeLines = new HTMLMultiLinePanel(
            STLocalizer.getString( "ConfigWizard_ContentCommunityText" ) );
        builder.add( welcomeLines, cc.xy( 2, 3 ) );
        
        joinPolarSkulk = new JCheckBox( STLocalizer.getString( "ConfigWizard_JoinPolarSkulk" ) );
        builder.add( joinPolarSkulk, cc.xy( 2, 5 ) );
    }
    
    public boolean isJoinPolarSkulkSelected()
    {
        return joinPolarSkulk.isSelected();
    }
    
    public void saveSettings()
    {
        boolean readoutMagmas = joinPolarSkulk.isSelected();
        DownloadPrefs.AutoReadoutMagmaFiles.set( Boolean.valueOf( readoutMagmas ) );
        
        boolean silentSubscriptions = joinPolarSkulk.isSelected();
        SubscriptionPrefs.DownloadSilently.set( Boolean.valueOf( silentSubscriptions ) );
        
        if (joinPolarSkulk.isSelected())
        {
            String polarSkulkMagnet = "magnet:?xs=http://polar-skulk.draketo.de/polar-skulk.magma&dn=polar-skulk.magma";
            
            
            SubscriptionPrefs.SubscriptionMagnets.get().add(polarSkulkMagnet); 
        }
    }
}
