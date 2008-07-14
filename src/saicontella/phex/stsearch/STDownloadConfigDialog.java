package saicontella.phex.stsearch;

/**
 * SaiconTella project
 *
 * @author Marios Karagiannopoulos (mariosk@gmail.com)
 *
 * March 2008
 */

import saicontella.core.STLibrary;
import saicontella.core.STLocalizer;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.HeadlessException;
import java.awt.event.*;
import java.io.File;

import javax.swing.*;

import phex.common.log.NLogger;
import phex.download.RemoteFile;
import phex.download.strategy.*;
import phex.download.swarming.SWDownloadFile;
import phex.download.swarming.SwarmingManager;
import phex.gui.common.*;
import phex.prefs.core.DownloadPrefs;
import phex.query.DynamicQueryConstants;
import phex.utils.StringUtils;
import phex.utils.FileUtils;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class STDownloadConfigDialog extends JDialog
{
    private JTextField fileNameTF;
    private JTextField destDirectoryTF;
    private JTextField searchTermTF;
    private JComboBox downloadStrategyCbx;
    private JRadioButton topPriority;
    private JRadioButton bottomPriority;
    
    
    /**
     * The remote file for a new download to be configured.
     * Either the remoteFile or the downloadFile is given for configuration
     * never both.
     */
    private RemoteFile remoteFile;

    /**
     * The download file for a already queued download to be configured.
     * Either the remoteFile or the downloadFile is given for configuration
     * never both.
     */
    private SWDownloadFile downloadFile;
    private JCheckBox switchToDownloadTab;

    /**
     * @throws java.awt.HeadlessException
     */
    public STDownloadConfigDialog( RemoteFile remoteFile ) throws HeadlessException
    {
        super(GUIRegistry.getInstance().getMainFrame(), STLocalizer
            .getString("ConfigDownload_DialogTitleNew"), true );
        this.remoteFile = remoteFile;
        prepareComponent();
        fillFields();
    }
    
    /**
     * @throws java.awt.HeadlessException
     */
    public STDownloadConfigDialog( SWDownloadFile downloadFile ) throws HeadlessException
    {
        super(GUIRegistry.getInstance().getMainFrame(), STLocalizer
            .getString("ConfigDownload_DialogTitleConfig"), false);
        this.downloadFile = downloadFile;
        prepareComponent();
        fillFields();
    }
    
    /**
     * 
     */
    private void prepareComponent()
    {
        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent evt)
            {
                closeDialog();
            }
        });
        CellConstraints cc = new CellConstraints();
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        JPanel contentPanel = new JPanel();
        //JPanel contentPanel = new FormDebugPanel();
        contentPane.add(contentPanel, BorderLayout.CENTER);

        FormLayout layout = new FormLayout(
            "4dlu, d, 2dlu, d, 2dlu, fill:d:grow, 2dlu, fill:d:grow, 2dlu, d, 4dlu", // columns
            "p, p, 10dlu, p, 3dlu, p, 3dlu, p, 3dlu, p, " + //10 rows
            "10dlu, p, 3dlu, p, 3dlu, p," + // 6 rows
            "10dlu, p, 3dlu, p, 6dlu" ); //row
        PanelBuilder builder = new PanelBuilder(layout, contentPanel);
        
        int row = 1;
        STDialogBanner banner = new STDialogBanner( STLocalizer.getString("ConfigDownload_BannerHeader"),
            STLocalizer.getString("ConfigDownload_BannerSubHeader") );
        builder.add( banner, cc.xywh( 1, row, 11, 1 ));
        row ++; //2
        builder.add(new JSeparator(), cc.xywh(1, row, 11, 1));
        row += 2; //4
        builder.addSeparator( STLocalizer.getString( "ConfigDownload_File" ),
            cc.xywh( 2, row, 9, 1 ) );
        row += 2; //6
        builder.addLabel( STLocalizer.getString( "ConfigDownload_FileName" ), cc.xy( 4, row, "left, center") );
        fileNameTF = new JTextField( 40 );
        builder.add( fileNameTF, cc.xywh( 6, row, 3, 1 ));
        row += 2; //8
        builder.addLabel( STLocalizer.getString( "ConfigDownload_DestinationDirectory" ), cc.xy( 4, row, "left, center") );
        destDirectoryTF = new JTextField( 40 );
        builder.add( destDirectoryTF, cc.xywh( 6, row, 3, 1 ));
        JButton browseDestDirBtn = new JButton( STLocalizer.getString( "ConfigDownload_Browse" ) );
        browseDestDirBtn.addActionListener( new BrowseDestDirBtnListener());
        builder.add( browseDestDirBtn, cc.xy( 10, row ) );
        row += 2; //10
        builder.addLabel( STLocalizer.getString( "ConfigDownload_ReSearchTerm" ), cc.xy( 4, row, "left, center") );
        searchTermTF = new JTextField( 40 );
        builder.add( searchTermTF, cc.xywh( 6, row, 3, 1 ));
        row += 2; //12
        builder.addSeparator( STLocalizer.getString( "ConfigDownload_Options" ),
            cc.xywh( 2, row, 9, 1 ) );
        row += 2; //14
        builder.addLabel( STLocalizer.getString( "ConfigDownload_DownloadStrategy" ), cc.xy( 4, row, "left, center") );
        String[] strategyArr = new String[]
        {            
            STLocalizer.getString( "DownloadTab_StrategyAvailability" ),
            STLocalizer.getString( "DownloadTab_StrategyBeginning" ),
            STLocalizer.getString( "DownloadTab_StrategyBeginningEnd" ),
            STLocalizer.getString( "DownloadTab_StrategyRandom" )
        };
        downloadStrategyCbx = new JComboBox( strategyArr );
        builder.add( downloadStrategyCbx, cc.xywh( 6, row, 3, 1 ));
        row += 2; //16
        if ( remoteFile != null )
        {
            builder.addLabel( STLocalizer.getString( "ConfigDownload_Priority" ), cc.xy( 4, row, "left, center") );
            ButtonGroup priorityGroup = new ButtonGroup();
            topPriority = new JRadioButton( STLocalizer.getString( "ConfigDownload_AddToTop" ) );
            bottomPriority = new JRadioButton( STLocalizer.getString( "ConfigDownload_AddToBottom" ) );
            priorityGroup.add( topPriority );
            priorityGroup.add( bottomPriority );
            builder.add( topPriority, cc.xywh( 6, row, 1, 1 ));
            builder.add( bottomPriority, cc.xywh( 8, row, 1, 1 ));
        }
        row += 2; //18
        builder.add(new JSeparator(), cc.xywh(1, row, 11, 1));
        row += 2; //20

        if ( remoteFile != null )
        {
            switchToDownloadTab = new JCheckBox( STLocalizer.getString( "ConfigDownload_SwitchToDownloadTab" ) );
            builder.add(switchToDownloadTab, cc.xywh(2, row, 5, 1));
        }
        
        JButton cancelBtn = new JButton(STLocalizer.getString("Cancel"));
        cancelBtn.addActionListener(new CancelBtnListener());
        JButton okBtn = new JButton(STLocalizer.getString("OK"));
        okBtn.addActionListener(new OkBtnListener());
        JPanel btnPanel = ButtonBarFactory.buildOKCancelBar(okBtn, cancelBtn);
        builder.add(btnPanel, cc.xywh(8, row, 3, 1));
        this.setIconImage(STLibrary.getInstance().getAppIcon().getImage());
        pack();
        setLocationRelativeTo(getParent());
    }
    
    private void fillFields()
    {
        if ( remoteFile != null )
        {
            fileNameTF.setText( remoteFile.getFilename() );
            destDirectoryTF.setText( DownloadPrefs.DestinationDirectory.get() );
            searchTermTF.setText( StringUtils.createNaturalSearchTerm(
                remoteFile.getDisplayName() ) );
            downloadStrategyCbx.setSelectedIndex( 0 );
            topPriority.setSelected( true );
        }
        else if ( downloadFile != null )
        {
            fileNameTF.setText( downloadFile.getFileName() );
            
            String destDirStr; 
            File destDir = downloadFile.getDestinationDirectory();
            if ( destDir != null )
            {
                destDirStr = destDir.getAbsolutePath();
            }
            else
            {
                destDirStr = DownloadPrefs.DestinationDirectory.get();
            }
            destDirectoryTF.setText( destDirStr );
            searchTermTF.setText( downloadFile.getResearchSetting().getSearchTerm() );
            
            ScopeSelectionStrategy strategy = downloadFile.getMemoryFile().getScopeSelectionStrategy();
            if ( strategy instanceof AvailBeginRandSelectionStrategy )
            {
                downloadStrategyCbx.setSelectedIndex( 0 );
            }
            else if ( strategy instanceof BeginAvailRandSelectionStrategy )
            {
                downloadStrategyCbx.setSelectedIndex( 1 );
            }
            else if ( strategy instanceof BeginEndAvailRandSelectionStrategy )
            {
                downloadStrategyCbx.setSelectedIndex( 2 );
            }
            else if ( strategy instanceof RandomScopeSelectionStrategy )
            {
                downloadStrategyCbx.setSelectedIndex( 3 );
            }
            else
            {
                NLogger.error( STDownloadConfigDialog.class,
                    "Unknown scope selection strategy: " + strategy.getClass().getName() );
            }
        }
            
    }
    
    private void closeDialog()
    {
        setVisible(false);
        dispose();
    }
    
    /**
     * Returns true if the dialog input is valid otherwise false. 
     * This method causes error dialogs to be displayed in case of an input error.
     * @return
     */
    private boolean isInputValid()
    {
        String filename = fileNameTF.getText().trim();
        if ( filename.length() == 0 )
        {
            GUIUtils.showErrorMessage( this,
                STLocalizer.getString( "ConfigDownload_ErrorNoFileName" ) );
            fileNameTF.requestFocus();
            return false;
        }
        
        String directoryStr = destDirectoryTF.getText().trim();
        if ( filename.length() == 0 )
        {
            GUIUtils.showErrorMessage( this,
                STLocalizer.getString( "ConfigDownload_ErrorNoDestDir" ) );
            destDirectoryTF.requestFocus();
            return false;
        }
        File directory = new File( directoryStr );
        if ( !directory.isAbsolute() || (directory.exists() && !directory.isDirectory() ) )
        {
            GUIUtils.showErrorMessage( this,
                STLocalizer.getString( "ConfigDownload_ErrorInvalidDestDir" ) );
            destDirectoryTF.requestFocus();
            return false;
        }
        if ( !directory.exists() && !directory.mkdirs() )
        {
            GUIUtils.showErrorMessage( this,
                STLocalizer.getString( "ConfigDownload_ErrorCreateDestDir" ) );
            destDirectoryTF.requestFocus();
            return false;
        }

        String researchTerm = searchTermTF.getText().trim();
        if ( researchTerm.length() < DynamicQueryConstants.MIN_SEARCH_TERM_LENGTH )
        {
            Object[] objArr = new Object[ 1 ];
            objArr[ 0 ] = new Integer( DynamicQueryConstants.MIN_SEARCH_TERM_LENGTH );
            GUIUtils.showErrorMessage( this,
                STLocalizer.getFormatedString( "ConfigDownload_ErrorMinSearchTerm", objArr ) );
            searchTermTF.requestFocus();
            return false;
        }
        
        return true;
    }
    
    private void performChanges()
    {
        SwarmingManager swarmingMgr = STLibrary.getInstance().getGnutellaFramework().getServent().getInstance().getDownloadService();
        String filename = fileNameTF.getText().trim();
        String directoryStr = destDirectoryTF.getText().trim();
        File destDir = new File( directoryStr );
        String researchTerm = searchTermTF.getText().trim();
        
        SWDownloadFile downloadFileToChange = null;
        if ( remoteFile != null )
        {
            downloadFileToChange = swarmingMgr.addFileToDownload( remoteFile,
                FileUtils.convertToLocalSystemFilename( filename ),
                researchTerm );            
            remoteFile.setInDownloadQueue( true );                    
        }
        else if ( downloadFile != null )
        {
            downloadFile.setFileName( filename );
            downloadFile.getResearchSetting().setSearchTerm( researchTerm );
            downloadFileToChange = downloadFile;
        }
        else
        {
            throw new RuntimeException( "RemoteFile and DownloadFile is null" );
        }
        downloadFileToChange.setDestinationDirectory( destDir );
        ScopeSelectionStrategy strategy;
        switch (downloadStrategyCbx.getSelectedIndex())
        {
        case 0:
            strategy = ScopeSelectionStrategyProvider.getAvailBeginRandSelectionStrategy();
            break;
        case 1:
            strategy = ScopeSelectionStrategyProvider.getBeginAvailRandSelectionStrategy();
            break;
        case 2:
            strategy = ScopeSelectionStrategyProvider.getBeginEndAvailRandSelectionStrategy();
            break;
        case 3:
            strategy = ScopeSelectionStrategyProvider.getRandomSelectionStrategy();
            break;
        default:
            NLogger.error( STDownloadConfigDialog.class,
                "Unknown scope selection strategy index: " + downloadStrategyCbx.getSelectedIndex() );
            strategy = ScopeSelectionStrategyProvider.getAvailBeginRandSelectionStrategy();
            break;
        }
        downloadFileToChange.getMemoryFile().setScopeSelectionStrategy( strategy );
        
        if ( remoteFile != null )
        {
            if ( topPriority.isSelected() )
            {
                swarmingMgr.moveDownloadFilePriority( downloadFileToChange, SwarmingManager.PRIORITY_MOVE_TO_TOP );
            }
            else if ( bottomPriority.isSelected() )
            {
                swarmingMgr.moveDownloadFilePriority( downloadFileToChange, SwarmingManager.PRIORITY_MOVE_TO_BOTTOM );
            }
        }
    }
    
    private final class OkBtnListener implements ActionListener
    {
        public void actionPerformed( ActionEvent e )
        {
            try
            {
                if ( isInputValid() )
                {
                    performChanges();
                    if ( switchToDownloadTab != null && switchToDownloadTab.isSelected() )
                    {
                        GUIRegistry.getInstance().getMainFrame().setSelectedTab( MainFrame.DOWNLOAD_TAB_ID );
                    }
                    closeDialog();
                }
            }
            catch ( Throwable th )
            {
                NLogger.error( OkBtnListener.class, th, th );
            }
        }
    }

    private final class CancelBtnListener implements ActionListener
    {
        public void actionPerformed( ActionEvent e )
        {
            try
            {
                closeDialog();
            }
            catch ( Throwable th )
            {
                NLogger.error( CancelBtnListener.class, th, th );
            }
        }
    }
    
    private final class BrowseDestDirBtnListener implements ActionListener
    {
        public void actionPerformed( ActionEvent e )
        {
            try
            {
                File file = FileDialogHandler.openSingleDirectoryChooser( 
                    STDownloadConfigDialog.this,
                    STLocalizer.getString( "ConfigDownload_SelectDestinationDirectory" ),
                    STLocalizer.getString( "ConfigDownload_Select" ),
                    STLocalizer.getChar( "ConfigDownload_SelectMnemonic" ),
                    new File( destDirectoryTF.getText() ) );
                if ( file != null )
                {                    
                    destDirectoryTF.setText( file.getAbsolutePath() );
                }
            }
            catch ( Throwable th )
            {
                NLogger.error( BrowseDestDirBtnListener.class, th, th );
            }
        }
    }
}