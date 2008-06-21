package saicontella.phex.stwizard;

/**
 * SaiconTella project
 *
 * @author Marios Karagiannopoulos (mariosk@gmail.com)
 *
 * March 2008
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.*;

import phex.gui.common.GUIUtils;
import phex.gui.common.HTMLMultiLinePanel;
import phex.prefs.core.DownloadPrefs;
import phex.utils.DirectoryOnlyFileFilter;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import saicontella.core.STLibrary;
import saicontella.core.STLocalizer;

public class STDirectoryPanel extends JPanel
{
    private STConfigurationWizardDialog parent;
    
    private JTextField incompleteDirectoryTF;
    private JTextField downloadDirectoryTF;
    
    public STDirectoryPanel( STConfigurationWizardDialog parent )
    {
        this.parent = parent;
        prepareComponent();
    }
    
    private void prepareComponent()
    {
        FormLayout layout = new FormLayout(
            "10dlu, right:d, 2dlu, d:grow, 2dlu, d, right:d:grow", // columns
            "p, 3dlu, p, 8dlu, p, 3dlu, p, 8dlu, p" );// rows 
        
        setLayout( layout );
        
        CellConstraints cc = new CellConstraints();
        PanelBuilder builder = new PanelBuilder( layout, this );
        int columnCount = layout.getColumnCount();
        
        builder.addSeparator( STLocalizer.getString( "ConfigWizard_DirectoryHeader" ),
            cc.xywh( 1, 1, columnCount, 1 ) );
        
        HTMLMultiLinePanel welcomeLines = new HTMLMultiLinePanel(
            STLocalizer.getString( "ConfigWizard_DirectoryText" ) );
        builder.add( welcomeLines, cc.xywh( 2, 3, columnCount-2, 1 ) );
        
        builder.addLabel( STLocalizer.getString( "ConfigWizard_Incomplete" ),
            cc.xy( 2, 5 ) );
        incompleteDirectoryTF = new JTextField( DownloadPrefs.IncompleteDirectory.get(), 30 );
        incompleteDirectoryTF.setText(STLibrary.getInstance().getApplicationLocalPath() + "\\Incomplete");
        builder.add( incompleteDirectoryTF, cc.xy( 4, 5 ) );
        JButton button = new JButton( STLocalizer.getString( "ConfigWizard_SetFolder" ) );
        //button.setMargin( noInsets );
        button.addActionListener( new SetIncompleteDirectoryListener() );
        builder.add( button, cc.xy( 6, 5 ) );
        
        builder.addLabel( STLocalizer.getString( "ConfigWizard_Destination" ),
            cc.xy( 2, 7 ) );
        downloadDirectoryTF = new JTextField( DownloadPrefs.DestinationDirectory.get(), 30 );
        downloadDirectoryTF.setText(STLibrary.getInstance().getApplicationLocalPath() + "\\Complete");
        builder.add( downloadDirectoryTF, cc.xy( 4, 7 ) );
        button = new JButton( STLocalizer.getString( "ConfigWizard_SetFolder" ) );
        //button.setMargin( noInsets );
        button.addActionListener( new SetDownloadDirectoryListener() );
        builder.add( button, cc.xy( 6, 7 ) );
        
        HTMLMultiLinePanel welcomeLines2 = new HTMLMultiLinePanel(
            STLocalizer.getString( "ConfigWizard_DirectoryText2" ) );
        builder.add( welcomeLines2, cc.xywh( 2, 9, columnCount-2, 1 ) );
    }
    
    public boolean checkInput()
    {
        String downloadDirPath = downloadDirectoryTF.getText();
        File downloadDir = new File( downloadDirPath );

        String incompleteDirPath = incompleteDirectoryTF.getText();
        File incompleteDir = new File( incompleteDirPath );

        if ( !downloadDir.exists() || !downloadDir.isDirectory() )
        {
            boolean succ = downloadDir.mkdirs();
            if ( !succ )
            {
                downloadDirectoryTF.requestFocus();
                downloadDirectoryTF.selectAll();
                GUIUtils.showErrorMessage(
                    STLocalizer.getFormatedString( "CantCreateDownloadDir",
                        downloadDirectoryTF.getText() ),
                    STLocalizer.getString( "DirectoryError" ) );
                return false;
            }
        }

        if ( !incompleteDir.exists() || !incompleteDir.isDirectory())
        {
            boolean succ = incompleteDir.mkdirs();
            if ( !succ )
            {
                incompleteDirectoryTF.requestFocus();
                incompleteDirectoryTF.selectAll();
                GUIUtils.showErrorMessage(
                    STLocalizer.getFormatedString( "CantCreateIncompleteDir",
                        incompleteDirectoryTF.getText() ),
                    STLocalizer.getString( "DirectoryError" ) );
                return false;
            }
        }
        return true;
    }
    
    public void saveSettings()
    {
        String downloadDirPath = downloadDirectoryTF.getText();
        File downloadDir = new File( downloadDirPath );
        downloadDirPath = downloadDir.getAbsolutePath();
        DownloadPrefs.DestinationDirectory.set( downloadDirPath );
        STLibrary.getInstance().getSTConfiguration().setCompleteFolder(downloadDirPath);
        
        String incompleteDirPath = incompleteDirectoryTF.getText();
        File incompleteDir = new File( incompleteDirPath );
        incompleteDirPath = incompleteDir.getAbsolutePath();
        DownloadPrefs.IncompleteDirectory.set(incompleteDirPath);
        STLibrary.getInstance().getSTConfiguration().setInCompleteFolder(incompleteDirPath);
    }
    
    private class SetDownloadDirectoryListener implements ActionListener
    {
        public void actionPerformed( ActionEvent e )
        {
            JFileChooser chooser = new JFileChooser();
            chooser.setSelectedFile( new File( downloadDirectoryTF.getText() ) );
            chooser.setAcceptAllFileFilterUsed( false );
            chooser.setFileFilter( new DirectoryOnlyFileFilter() );
            chooser.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );
            chooser.setMultiSelectionEnabled( false );
            chooser.setDialogTitle(
                STLocalizer.getString( "SelectDownloadDirectory" ) );
            chooser.setApproveButtonText( STLocalizer.getString( "Select" ) );
            chooser.setApproveButtonMnemonic(
                STLocalizer.getChar( "SelectMnemonic" ) );
            int returnVal = chooser.showDialog( STDirectoryPanel.this, null );
            if( returnVal == JFileChooser.APPROVE_OPTION )
            {
                String directory = chooser.getSelectedFile().getAbsolutePath();
                downloadDirectoryTF.setText( directory );
                STLibrary.getInstance().getSTConfiguration().setCompleteFolder(directory);
            }
        }
    }

    private class SetIncompleteDirectoryListener implements ActionListener
    {
        public void actionPerformed( ActionEvent e )
        {
            JFileChooser chooser = new JFileChooser();
            chooser.setSelectedFile( new File( incompleteDirectoryTF.getText() ) );
            chooser.setAcceptAllFileFilterUsed( false );
            chooser.setFileFilter( new DirectoryOnlyFileFilter() );
            chooser.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );
            chooser.setMultiSelectionEnabled( false );
            chooser.setDialogTitle(
                STLocalizer.getString( "SelectIncompleteDirectory" ) );
            chooser.setApproveButtonText( STLocalizer.getString( "Select" ) );
            chooser.setApproveButtonMnemonic(
                STLocalizer.getChar( "SelectMnemonic" ) );
            int returnVal = chooser.showDialog( STDirectoryPanel.this, null );
            if( returnVal == JFileChooser.APPROVE_OPTION )
            {
                String directory = chooser.getSelectedFile().getAbsolutePath();
                incompleteDirectoryTF.setText( directory );
                STLibrary.getInstance().getSTConfiguration().setInCompleteFolder(directory);
            }
        }
    }
}