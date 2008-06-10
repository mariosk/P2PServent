package saicontella.phex.stwizard;

/**
 * SaiconTella project
 *
 * @author Marios Karagiannopoulos (mariosk@gmail.com)
 *
 * March 2008
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

import phex.common.log.NLogger;
import phex.gui.common.DialogBanner;
import phex.gui.common.GUIRegistry;
import phex.gui.dialogs.options.OptionsDialog;
import phex.gui.prefs.UpdatePrefs;
import phex.utils.Localizer;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import saicontella.core.STResources;
import saicontella.core.STMainForm;
import saicontella.core.STLibrary;

public class STConfigurationWizardDialog extends JDialog
{
    private static final int WELCOME_PAGE = 1;
    //private static final int BANDWIDTH_PAGE = 2;
    private static final int DIRECTORY_PAGE = 3;
    //private static final int CONTENTCOMMUNITY_PAGE = 4; 
    private static final int SHARING_PAGE = 5;
    
    private static final int GOODBYE_PAGE = 6;
    
    private JPanel wizardContentPanel;
    
    private STWelcomePanel welcomePanel;
    private STDirectoryPanel directoryPanel;
    private STSharingPanel sharingPanel;
    private STContentCommunityPanel contentCommunityPanel;
    private STGoodbyePanel goodbyePanel;
    private Component parent;
    
    private int currentPage;
    private JButton finishBtn;
    private JButton backBtn;
    private JButton nextBtn;

    public STConfigurationWizardDialog(Component parent)
    {
        //super( GUIRegistry.getInstance().getMainFrame(),
        //STResources.getStr( "ConfigWizard_DialogTitle" ), false );
        this.parent = parent;
        this.setIconImage(STLibrary.getInstance().getAppIcon().getImage());
        currentPage = WELCOME_PAGE;
        prepareComponent();
    }

    private void prepareComponent()
    {
        CloseEventHandler closeEventHandler = new CloseEventHandler();
        addWindowListener( closeEventHandler );
        
        Container contentPane = getContentPane();
        contentPane.setLayout( new BorderLayout() );
        
        JPanel contentPanel = new JPanel();
        //JPanel contentPanel = new FormDebugPanel();
        contentPane.add(contentPanel, BorderLayout.CENTER);
        
        CellConstraints cc = new CellConstraints();
        FormLayout layout = new FormLayout("4dlu, fill:d:grow, 4dlu", // columns
            "p, p, 4dlu, fill:p:grow, 8dlu," +  // rows
            "p, 2dlu, p 4dlu" ); //btn rows
        PanelBuilder contentPB = new PanelBuilder(layout, contentPanel);
        int columnCount = layout.getColumnCount();
        int rowCount = layout.getRowCount();
        
        STDialogBanner banner = new STDialogBanner(
            STResources.getStr( "ConfigWizard_BannerHeader" ),
            STResources.getStr( "ConfigWizard_BannerSubHeader" ) );
        contentPB.add(banner, cc.xywh(1, 1, columnCount, 1));
        
        contentPB.add(new JSeparator(), cc.xywh(1, 2, columnCount, 1));
        
        wizardContentPanel = new JPanel( new GridLayout(1,1) );
        //wizardContentPanel.setLayout(new BorderLayout());
        contentPB.add( wizardContentPanel, cc.xywh( 2, 4, 1, 1 ) );
                
        // button bar
        contentPB.add( new JSeparator(), cc.xywh( 1, rowCount - 3, columnCount, 1 ) );
        
        backBtn = new JButton( STResources.getStr( "WizardDialog_Back" ) );
        backBtn.addActionListener( new BackBtnListener());
        
        nextBtn = new JButton( STResources.getStr( "WizardDialog_Next" ) );
        nextBtn.setDefaultCapable( true );
        nextBtn.setRequestFocusEnabled( true );
        nextBtn.addActionListener( new NextBtnListener());
        
        finishBtn = new JButton( STResources.getStr( "WizardDialog_Finish" ) );
        finishBtn.addActionListener( new FinishBtnListener());
        finishBtn.setEnabled(false);
        
        JButton cancelBtn = new JButton( STResources.getStr( "WizardDialog_Cancel" ) );
        cancelBtn.addActionListener( closeEventHandler );
        
        JPanel btnPanel = ButtonBarFactory.buildWizardBar(backBtn, nextBtn,
            finishBtn, cancelBtn);        
        contentPB.add( btnPanel, cc.xywh( 2, rowCount - 1, columnCount - 2, 1 ) );
        
        setDefaultCloseOperation( JDialog.DISPOSE_ON_CLOSE );
        getRootPane().setDefaultButton( nextBtn );
        
        // set first panel to show...
        updatePage();
        pack();
        int height = Math.max( 400, getHeight() );
        setSize( height*5/4, height );
        
        setLocationRelativeTo( getParent() );
    }
    
    public void setFinishBtnEnabled( boolean state )
    {
        finishBtn.setEnabled(state);
    }
    
    private void closeDialog()
    {
        UpdatePrefs.ShowConfigWizard.set( Boolean.FALSE );
        setVisible(false);
        dispose();
    }
        
    private void updatePage()
    {
        wizardContentPanel.removeAll();
        JPanel newPage = null;
        switch ( currentPage )
        {
        case WELCOME_PAGE:
            if ( welcomePanel == null )
            {
                welcomePanel = new STWelcomePanel(this);
            }
            newPage = welcomePanel;
            backBtn.setEnabled(false);
            nextBtn.setEnabled(true);
            break;
        /*
        case BANDWIDTH_PAGE:
            if ( bandwidthPanel == null )
            {
                bandwidthPanel = new STBandwidthPanel(this);
            }
            newPage = bandwidthPanel;
            backBtn.setEnabled(true);
            nextBtn.setEnabled(true);
            break;
         */
        case DIRECTORY_PAGE:
            if ( directoryPanel == null )
            {
                directoryPanel = new STDirectoryPanel(this);
            }
            newPage = directoryPanel;
            backBtn.setEnabled(true);
            nextBtn.setEnabled(true);
            break;
        case SHARING_PAGE:
            if ( sharingPanel == null )
            {
                sharingPanel = new STSharingPanel(this);
            }
            newPage = sharingPanel;
            nextBtn.setEnabled(true);
            backBtn.setEnabled(true);
            break;
        /*
        case CONTENTCOMMUNITY_PAGE:
            if ( contentCommunityPanel == null )
            {
                contentCommunityPanel = new STContentCommunityPanel(this);
            }
            newPage = contentCommunityPanel;
            nextBtn.setEnabled(true);
            backBtn.setEnabled(true);
            break;
        */            
        case GOODBYE_PAGE:
            if ( goodbyePanel == null )
            {
                goodbyePanel = new STGoodbyePanel(this);
            }
            newPage = goodbyePanel;
            nextBtn.setEnabled(false);
            backBtn.setEnabled(true);
            finishBtn.setEnabled(true);
            break;
        }
        wizardContentPanel.add(newPage, BorderLayout.CENTER);
        
        wizardContentPanel.doLayout();
        wizardContentPanel.revalidate();
        wizardContentPanel.repaint();

        // here we adjust the size of the dialog if necessary
        Dimension prefSize = getPreferredSize();
        Dimension currSize = getSize();
        if ( prefSize.height > currSize.height )
        {
            int height = Math.max( prefSize.height, currSize.height );
            setSize( height*5/4, height );
            doLayout();
        }        
    }
    
    private final class NextBtnListener implements ActionListener
    {
        public void actionPerformed( ActionEvent e )
        {
            try
            {
                switch ( currentPage )
                {
                case WELCOME_PAGE:
                    //currentPage = BANDWIDTH_PAGE;
                    currentPage = DIRECTORY_PAGE;
                    break;
                /*
                case BANDWIDTH_PAGE:
                    currentPage = DIRECTORY_PAGE;
                    break;
                */
                case DIRECTORY_PAGE:
                    if ( !directoryPanel.checkInput() )
                    {
                        currentPage = DIRECTORY_PAGE;
                    }
                    else
                    {
                        //currentPage = CONTENTCOMMUNITY_PAGE;
                        currentPage = SHARING_PAGE;
                    }
                    break;
/*
                case CONTENTCOMMUNITY_PAGE:
                    currentPage = SHARING_PAGE;
                    break;
*/                    
                case SHARING_PAGE:
                    currentPage = GOODBYE_PAGE;
                    break;
                }
                updatePage();
            }
            catch ( Throwable th )
            {
                NLogger.error( NextBtnListener.class, th, th );
            }
        }
    }
    
    private final class BackBtnListener implements ActionListener
    {
        public void actionPerformed( ActionEvent e )
        {
            try
            {
                switch ( currentPage )
                {
                    /*
                case BANDWIDTH_PAGE:
                    currentPage = WELCOME_PAGE;
                    break;
                    */
                case DIRECTORY_PAGE:
                    //currentPage = BANDWIDTH_PAGE;
                    currentPage = WELCOME_PAGE;                    
                    break;
/*
                case CONTENTCOMMUNITY_PAGE:
                    currentPage = DIRECTORY_PAGE;
                    break;
*/
                case SHARING_PAGE:
                    //currentPage = CONTENTCOMMUNITY_PAGE;
                    currentPage = DIRECTORY_PAGE;
                    break;
                case GOODBYE_PAGE:
                    currentPage = SHARING_PAGE;
                    break;
                }
                updatePage();
            }
            catch ( Throwable th )
            {
                NLogger.error( BackBtnListener.class, th, th );
            }
        }
    }

    private final class FinishBtnListener implements ActionListener
    {
        public void actionPerformed( ActionEvent e )
        {
            try
            {
                if ( directoryPanel != null )
                {
                    if ( !directoryPanel.checkInput() )
                    {
                        currentPage = DIRECTORY_PAGE;
                        updatePage();
                        return;
                    }
                    directoryPanel.saveSettings();
                }
                if ( contentCommunityPanel != null )
                {
                    contentCommunityPanel.saveSettings();
                }
                boolean openOptions = false;
                if ( goodbyePanel != null )
                {
                    openOptions = goodbyePanel.isOpenOptionsSelected();
                }
                closeDialog();

                STLibrary.getInstance().getSTConfiguration().setWebServiceAccount("");
                STLibrary.getInstance().getSTConfiguration().setWebServicePassword(false, "");
                STLibrary.getInstance().getSTConfiguration().saveXMLFile();
                STLibrary.getInstance().reInitializeSTLibrary(openOptions);
                /*
                if ( openOptions )
                {
                    //OptionsDialog dialog = new OptionsDialog( );
                    //dialog.setVisible( true );
                    STMainForm mainForm = (STMainForm) parent;
                    mainForm.initializeToolsTabValues();
                    mainForm.getMainTabbedPanel().setSelectedIndex(STMainForm.SETTINGS_TAB_INDEX);
                }
                */
            }
            catch ( Throwable th )
            {
                NLogger.error( FinishBtnListener.class, th, th );
            }
        }
    }
    
    private final class CloseEventHandler extends WindowAdapter implements ActionListener
    {
        @Override
        public void windowClosing(WindowEvent evt)
        {
            closeDialog();
            System.exit(-1);
        }

        /**
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed(ActionEvent e)
        {
            closeDialog();
            System.exit(-1);
        }
    }
}
