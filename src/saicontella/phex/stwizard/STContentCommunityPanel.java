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
import phex.utils.Localizer;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import phex.prefs.core.DownloadPrefs;
import phex.prefs.core.SubscriptionPrefs;

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
        
        builder.addSeparator( Localizer.getString( "ConfigWizard_ContentCommunityHeader" ),
            cc.xywh( 1, 1, 3, 1 ) );
        
        HTMLMultiLinePanel welcomeLines = new HTMLMultiLinePanel(
            Localizer.getString( "ConfigWizard_ContentCommunityText" ) );
        builder.add( welcomeLines, cc.xy( 2, 3 ) );
        
        joinPolarSkulk = new JCheckBox( Localizer.getString( "ConfigWizard_JoinPolarSkulk" ) );
        builder.add( joinPolarSkulk, cc.xy( 2, 5 ) );
    }
    
    public boolean isJoinPolarSkulkSelected()
    {
        return joinPolarSkulk.isSelected();
    }
    
    public void saveSettings()
    {
        boolean readoutMagmas = joinPolarSkulk.isSelected();
        DownloadPrefs.AutoReadoutMagmaFiles.set( new Boolean( readoutMagmas ) );
        
        boolean silentSubscriptions = joinPolarSkulk.isSelected();
        SubscriptionPrefs.DownloadSilently.set( new Boolean( silentSubscriptions ) );
        
        if (joinPolarSkulk.isSelected())
        {
            String polarSkulkMagnet = new String("magnet:?xs=http://draketo.de/magma/polar-skulk/polar-skulk.magma&dn=polar-skulk.magma");
            
            
            SubscriptionPrefs.SubscriptionMagnets.get().add(polarSkulkMagnet); 
        }
    }
}
