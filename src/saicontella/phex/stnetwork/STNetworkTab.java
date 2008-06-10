package saicontella.phex.stnetwork;

/**
 * SaiconTella project
 *
 * @author Marios Karagiannopoulos (mariosk@gmail.com)
 *
 * February 2008
 */

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.net.MalformedURLException;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;
import javax.swing.text.Keymap;
import javax.print.DocFlavor;

import org.bushe.swing.event.annotation.EventTopicSubscriber;
import org.jdesktop.jdic.desktop.DesktopException;

import phex.common.address.DefaultDestAddress;
import phex.common.address.DestAddress;
import phex.common.address.MalformedDestAddressException;
import phex.common.log.NLogger;
import phex.connection.OutgoingConnectionDispatcher;
import phex.event.ChangeEvent;
import phex.event.PhexEventTopics;
import phex.gui.actions.FWAction;
import phex.gui.actions.GUIActionPerformer;
import phex.gui.common.*;
import phex.gui.common.table.FWSortedTableModel;
import phex.gui.common.table.FWTable;
import phex.gui.prefs.NetworkTabPrefs;
import phex.gui.prefs.PhexGuiPrefs;
import phex.gui.tabs.FWTab;
import phex.gui.tabs.network.NetworkRowRenderer;
import saicontella.phex.stnetwork.STNetworkTableModel;
import saicontella.phex.STFWElegantPanel;
import saicontella.core.STResources;
import saicontella.core.STRoundJButton;
import saicontella.core.STLibrary;
import saicontella.core.STButtonsPanel;
import phex.host.*;
import phex.net.repres.PresentationManager;
import phex.servent.Servent;
import phex.utils.Localizer;
import phex.xml.sax.gui.DGuiSettings;
import phex.xml.sax.gui.DTable;

import java.net.URL;
import java.net.MalformedURLException;
import org.jdesktop.jdic.desktop.Desktop;
import org.jdesktop.jdic.desktop.DesktopException;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.debug.FormDebugPanel;

/**
 * The NetworkTab Panel.
 */
public class STNetworkTab extends FWTab
{
    private static final String NETWORK_TABLE_IDENTIFIER = "NetworkTable";

    private static final Host[] EMPTY_HOST_ARRAY = new Host[0];

    private HostManager hostMgr;
    private NetworkHostsContainer hostsContainer;

    private FWTable networkTable;
    private NetworkRowRenderer networkRowRenderer;
    private JScrollPane networkTableScrollPane;
    private STNetworkTableModel networkModel;
    private FWPopupMenu networkPopup;

    private JLabel myIPLabel;
    private DefaultComboBoxModel connectToComboModel;
    private JComboBox connectToComboBox;

    private JLabel catcherStatLabel;
    //private JLabel gWebCacheStatLabel;

    public STNetworkTab( )
    {
        super( MainFrame.NETWORK_TAB_ID, Localizer.getString( "GnutellaNet"),
            GUIRegistry.getInstance().getPlafIconPack().getIcon( "Network.Tab" ),
            Localizer.getString( "TTTGnutellaNet"), Localizer.getChar(
            "GnutellaNetMnemonic"), KeyStroke.getKeyStroke( Localizer.getString(
            "GnutellaNetAccelerator" ) ), MainFrame.NETWORK_TAB_INDEX );
        hostMgr = Servent.getInstance().getHostService();
        hostsContainer = hostMgr.getNetworkHostsContainer();

        Servent.getInstance().getEventService().processAnnotations( this );
    }

    public void disconnectFromHost(Host host)
    {
        hostMgr.removeNetworkHost(host);    
    }

    public void initComponent( DGuiSettings guiSettings )
    {
        CellConstraints cc = new CellConstraints();

        FormLayout layout = new FormLayout(
            "2dlu, fill:d:grow, 2dlu", // columns
            "2dlu, fill:d:grow, 4dlu, d, 2dlu"); //rows

        PanelBuilder contentBuilder = new PanelBuilder( layout, this );

        //JPanel upperPanel = new FormDebugPanel();

        JPanel upperPanel = new JPanel( );
        STFWElegantPanel upperElegantPanel = new STFWElegantPanel( Localizer.getString("Connections"),
            upperPanel );
        upperElegantPanel.setBackground(Color.BLACK);
        upperElegantPanel.setForeground(Color.GRAY);

        layout = new FormLayout(
            "0dlu, d, 2dlu, d, 10dlu:grow, d, 2dlu, d, 2dlu, d, 0dlu", // columns
            "fill:d:grow, 3dlu, p"); // 3 rows
        
        PanelBuilder upperBuilder = new PanelBuilder( layout, upperPanel );

        networkModel = new STNetworkTableModel( hostMgr.getNetworkHostsContainer() );
        networkTable = new FWTable( new FWSortedTableModel( networkModel ) );
        GUIUtils.updateTableFromDGuiSettings( guiSettings, networkTable,
            NETWORK_TABLE_IDENTIFIER );

        // TODO3 try for a improced table sorting strategy.
        //((FWSortedTableModel)networkTable.getModel()).setTable( networkTable );

        networkTable.activateAllHeaderActions();
        networkTable.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );
        networkTable.getSelectionModel().addListSelectionListener(
            new SelectionHandler() );
        MouseHandler mouseHandler = new MouseHandler();
        networkTable.addMouseListener( mouseHandler );
        GUIRegistry.getInstance().getGuiUpdateTimer().addTable( networkTable );
        networkTableScrollPane = FWTable.createFWTableScrollPane( networkTable );
        networkTableScrollPane.addMouseListener( mouseHandler );

        upperBuilder.add( networkTableScrollPane, cc.xywh( 2, 1, 9, 1 ) );

        JLabel label = new JLabel( Localizer.getString( "NetworkTab_MyAddress" ) );
        upperBuilder.add( label, cc.xy( 2, 3 ) );
        myIPLabel = new JLabel( "" );
        myIPLabel.addMouseListener( new MouseAdapter()
            {
            @Override
            public void mouseReleased(MouseEvent e)
            {
                if (e.isPopupTrigger())
                {
                    popupMenu((Component)e.getSource(), e.getX(), e.getY());
                }
            }

            @Override
            public void mousePressed(MouseEvent e)
            {
                if (e.isPopupTrigger())
                {
                    popupMenu((Component)e.getSource(), e.getX(), e.getY());
                }
            }

            private void popupMenu(Component source, int x, int y)
            {
                JPopupMenu menu = new JPopupMenu();
                menu.add( new CopyMyIpAction() );
                menu.show( source, x, y );
            }
            });
        upperBuilder.add( myIPLabel, cc.xy( 4, 3 ) );
        
        label = new JLabel( Localizer.getString( "ConnectTo" )
            + Localizer.getChar( "ColonSign" ) );
        upperBuilder.add( label, cc.xy( 6, 3 ) );

// TODO2 add connection and disconnect network buttons to ConnectTo status line
//       because it is not available from toolbar anymore...

        ConnectToHostHandler connectToHostHandler = new ConnectToHostHandler();

        connectToComboModel = new DefaultComboBoxModel(
            NetworkTabPrefs.ConnectToHistory.get().toArray() );
        connectToComboBox = new JComboBox( connectToComboModel );
        connectToComboBox.setEditable( true );
        JTextField editor = ((JTextField)connectToComboBox.getEditor().getEditorComponent());
        Keymap keymap = JTextField.addKeymap( "ConnectToEditor", editor.getKeymap() );
        editor.setKeymap( keymap );
        keymap.addActionForKeyStroke( KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),
            connectToHostHandler );
        GUIUtils.assignKeymapToComboBoxEditor( keymap, connectToComboBox );
        connectToComboBox.setSelectedItem( "" );        
        connectToComboBox.setPrototypeDisplayValue("123.123.123.123:12345");
        upperBuilder.add( connectToComboBox, cc.xy( 8, 3 ) );

        JButton connectHostButton = new JButton( Localizer.getString( "Connect" ) );        
        connectHostButton.addActionListener( connectToHostHandler );
        upperBuilder.add( connectHostButton, cc.xy( 10, 3 ) );
        
        /////////////////////////// Lower Panel ////////////////////////////////

        JPanel lowerPanel = new JPanel();
        layout = new FormLayout(
            "d, fill:10dlu:grow, d", // columns
            "top:p"); //rows
        layout.setColumnGroups( new int[][]{{1, 3}} );
        PanelBuilder lowerBuilder = new PanelBuilder( layout, lowerPanel );

        STButtonsPanel buttonsPanel = new STButtonsPanel();
        lowerBuilder.add( buttonsPanel, cc.xy( 3, 1 ) );
       
        contentBuilder.add( upperElegantPanel, cc.xy( 2, 2 ) );
        contentBuilder.add( lowerPanel, cc.xy( 2, 4 ) );

        // Set up cell renderer to provide the correct color based on connection.
        networkRowRenderer = new NetworkRowRenderer(
            hostMgr.getNetworkHostsContainer() );
        List<TableColumn> colList = networkTable.getColumns( true );
        for ( TableColumn column : colList )
        {
            column.setCellRenderer( networkRowRenderer );
        }

        // Setup popup menu...
        networkPopup = new FWPopupMenu();

        FWAction action;
        action = new DisconnectHostAction();
        addTabAction( DISCONNECT_HOST_ACTION_KEY, action );

        networkTable.getActionMap().put( DISCONNECT_HOST_ACTION_KEY, action);
        networkTable.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(
            (KeyStroke)action.getValue(FWAction.ACCELERATOR_KEY), DISCONNECT_HOST_ACTION_KEY );
        networkPopup.addAction( action );

        networkPopup.addSeparator();

        action = new ChatToHostAction();
        addTabAction( CHAT_TO_HOST_ACTION_KEY, action );
        networkPopup.addAction( action );

        updateIpLabel( Servent.getInstance().getLocalAddress() );
    }

    /**
     * This is overloaded to update the combo box size on
     * every UI update. Like font size change!
     */
    @Override
    public void updateUI()
    {
        super.updateUI();

        if ( connectToComboBox != null )
        {
            GUIUtils.adjustComboBoxHeight( connectToComboBox );
            ListCellRenderer renderer = connectToComboBox.getRenderer();
            if ( renderer != null )
            {
                FontMetrics fm = connectToComboBox.getFontMetrics( connectToComboBox.getFont() );
                int width = fm.getMaxAdvance() * 15;
                Dimension dim = connectToComboBox.getMaximumSize();
                dim.width = Math.min( width, dim.width );

                dim = connectToComboBox.getPreferredSize();
                dim.width = Math.min( width, dim.width );
            }
        }

        if ( networkTableScrollPane != null )
        {
            FWTable.updateFWTableScrollPane( networkTableScrollPane );
        }

        if ( networkRowRenderer != null )
        {// since this is no component we need to call updateUI manually
            networkRowRenderer.updateUI();
        }
    }

    /**
     * Stores NetworkTab settings in XJB object model.
     */
    @Override
    public void appendDGuiSettings( DGuiSettings dSettings )
    {
        super.appendDGuiSettings( dSettings );
        DTable dTable = GUIUtils.createDTable( networkTable, NETWORK_TABLE_IDENTIFIER );
        dSettings.getTableList().getTableList().add( dTable );
    }

    private void compactHostsEntries(Host[] hosts)
    {
        if (hosts == null)
            return;

        for (int i = 0; i < hosts.length; i++) {
            for (int j = i+1; j < hosts.length; j++) {
                try {
                    if ((hosts[i].getHostAddress().getPort() == hosts[j].getHostAddress().getPort()) &&
                        (hosts[i].getHostAddress().getIpAddress().getFormatedString().equals(hosts[j].getHostAddress().getIpAddress().getFormatedString())) &&
                        (hosts[j].isConnected() && hosts[i].isConnected() && hosts[i].isIncomming()))                    
                    {
                        hosts[j].disconnect();
                    }
                }
                catch (Exception ex) {                    
                }
            }
        }

        return;
    }
    
    public ArrayList<Host> getAllHosts()
    {
        ArrayList<Host> allHosts = new ArrayList();
        int rows = networkTable.getRowCount();
        int[] viewRows = new int[rows];
        for (int i = 0; i < rows; i++) {
            viewRows[i] = i;                
        }
        if ( viewRows.length == 0 )
        {
            return null;
        }
        int[] modelRows = networkTable.convertRowIndicesToModel( viewRows );
        Host[] hosts = hostsContainer.getNetworkHostsAt( modelRows );
        //compactHostsEntries(hosts);
        for (int i = 0; i < hosts.length; i++) {
            allHosts.add(hosts[i]);
        }
        return allHosts;
    }

    private Host[] getSelectedHosts()
    {
        int[] viewRows = networkTable.getSelectedRows();
        if ( viewRows.length == 0 )
        {
            return EMPTY_HOST_ARRAY;
        }
        int[] modelRows = networkTable.convertRowIndicesToModel( viewRows );

        Host[] hosts = hostsContainer.getNetworkHostsAt( modelRows );
        return hosts;
    }

    private Host getSelectedHost()
    {
        int viewRow = networkTable.getSelectedRow();
        int modelRow = networkTable.translateRowIndexToModel( viewRow );
        if ( modelRow < 0 )
        {
            return null;
        }
        Host hosts = hostsContainer.getNetworkHostAt( modelRow );
        return hosts;
    }

    private void updateIpLabel( DestAddress localAddress )
    {
        myIPLabel.setText( localAddress.getFullHostName() );
        String countryCode = localAddress.getCountryCode();
        Icon icon = null;
        if ( countryCode != null && countryCode.length() > 0 )
        {
            icon = GUIRegistry.getInstance().getCountryIconPack().getIcon(
                countryCode );
        }
        myIPLabel.setIcon( icon );
    }

    /**
     * Reacts on ip changes.
     */
    @EventTopicSubscriber(topic=PhexEventTopics.Servent_LocalAddress)
    public void onLocaleAddressEvent( String topic, ChangeEvent event )
    {
        final DestAddress localAddress = (DestAddress) event.getNewValue();
        EventQueue.invokeLater( new Runnable()
        {
            public void run()
            {
                updateIpLabel( localAddress );
            }
        } );
    }

    //////////////////////// Actions ///////////////////////////////////////////

    private static final String DISCONNECT_HOST_ACTION_KEY = "DisconnectHostAction";
    private static final String CHAT_TO_HOST_ACTION_KEY = "ChatToHostAction";

    private class DisconnectHostAction extends FWAction
    {
        public DisconnectHostAction()
        {
            super( Localizer.getString( "DisconnectHost" ),
                GUIRegistry.getInstance().getPlafIconPack().getIcon("Network.DisconnectHost"),
                Localizer.getString( "TTTDisconnectHost" ), null,
                KeyStroke.getKeyStroke( KeyEvent.VK_DELETE, 0 ) );
            refreshActionState();
        }

        public void actionPerformed( ActionEvent e )
        {
            Host[] hosts = getSelectedHosts();
            hostMgr.removeNetworkHosts( hosts );
        }

        @Override
        public void refreshActionState()
        {
            if ( networkTable.getSelectedRowCount() > 0 )
            {
                setEnabled( true );
            }
            else
            {
                setEnabled( false );
            }
        }
    }

    private class ChatToHostAction extends FWAction
    {
        public ChatToHostAction()
        {
            super( Localizer.getString( "ChatToHost" ),
                GUIRegistry.getInstance().getPlafIconPack().getIcon("Network.Chat"),
                Localizer.getString( "TTTChatToHost" ) );
            refreshActionState();
        }

        public void actionPerformed( ActionEvent e )
        {
            Host host = getSelectedHost();
            if ( host == null )
            {
                return;
            }
            GUIActionPerformer.chatToHost( host.getHostAddress() );
        }

        @Override
        public void refreshActionState()
        {
            if ( networkTable.getSelectedRowCount() == 1 )
            {
                Host host = getSelectedHost();
                if ( host != null )
                {
                    setEnabled( true );
                    return;
                }
            }
            setEnabled( false );
        }
    }


    /////////////////////// inner classes //////////////////////////////

    private class SelectionHandler implements ListSelectionListener
    {
        public void valueChanged(ListSelectionEvent e)
        {
            if ( !e.getValueIsAdjusting() )
            {
                refreshTabActions();
            }
        }
    }

    private class ConnectToIGamersHandler extends AbstractAction implements ActionListener
    {
        public void actionPerformed( ActionEvent e )
        {
            try {
                String urlString = "http://www.gamersuniverse.com/igamer/users/p2p_login.php?username="
                        + STLibrary.getInstance().getSTConfiguration().getWebServiceAccount()
                        + "&password=" + STLibrary.getInstance().getSTConfiguration().getWebServicePassword();
                Desktop.browse(new URL(urlString));
            } catch(MalformedURLException ex) {
                STLibrary.getInstance().fireMessageBox(ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            } catch (DesktopException ex) {
                STLibrary.getInstance().fireMessageBox(ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class ExitHandler extends AbstractAction implements ActionListener
    {
        public void actionPerformed( ActionEvent e )
        {
            STLibrary.getInstance().STLogoutUser();
            System.exit(0);            
        }
    }
    
    private class ConnectToHostHandler extends AbstractAction implements ActionListener
    {
        public void actionPerformed( ActionEvent e )
        {
            String str = (String)connectToComboBox.getEditor().getItem();
            connectToComboModel.setSelectedItem( str );
            str = str.trim();
            if ( str.length() == 0 )
            {
                return;
            }

            int idx = connectToComboModel.getIndexOf( str );
            if ( idx < 0 )
            {
                connectToComboModel.insertElementAt( str, 0 );
                if ( connectToComboModel.getSize() >
                    NetworkTabPrefs.MaxConnectToHistorySize.get().intValue() )
                {
                    connectToComboModel.removeElementAt(
                        connectToComboModel.getSize() - 1 );
                }
                saveConnectToHostList();
            }
            else if ( idx > 0 )
            {
                connectToComboModel.removeElementAt( idx );
                connectToComboModel.insertElementAt( str, 0 );
                saveConnectToHostList();
            }
            connectToHost( str );
            connectToComboBox.setSelectedItem( "" );
        }

        private void connectToHost( String hostAddr )
        {
            if (hostAddr.length() == 0)
            {
                return;
            }
            StringTokenizer	tokens = new StringTokenizer(hostAddr, ";");
            String firstHost = tokens.nextToken();

            // Add new host and connect.
            try
            {
                DestAddress address = PresentationManager.getInstance().createHostAddress(
                    firstHost, DefaultDestAddress.DEFAULT_PORT );
                OutgoingConnectionDispatcher.dispatchConnectToHost( address, Servent.getInstance() );
            }
            catch ( MalformedDestAddressException exp )
            {
            }
            networkModel.fireTableDataChanged();

            while (tokens.hasMoreTokens())
            {
                String hostString = tokens.nextToken();
                try
                {
                    DestAddress address = PresentationManager.getInstance().createHostAddress(
                        hostString, DefaultDestAddress.DEFAULT_PORT );
                    hostMgr.getCaughtHostsContainer().addCaughtHost( address,
                        CaughtHostsContainer.HIGH_PRIORITY );
                }
                catch (MalformedDestAddressException exp)
                {
                }
            }
        }

        private void saveConnectToHostList()
        {
            int length = connectToComboModel.getSize();
            List<String> ipList = new ArrayList<String>( length );
            for ( int i = 0; i < length; i++ )
            {
                ipList.add( (String)connectToComboModel.getElementAt( i ) );
            }
            NetworkTabPrefs.ConnectToHistory.get().clear();
            NetworkTabPrefs.ConnectToHistory.get().addAll( ipList );
            NetworkTabPrefs.ConnectToHistory.changed();
            PhexGuiPrefs.save( false );
        }
    }

    private class MouseHandler extends MouseAdapter implements MouseListener
    {
        @Override
        public void mouseReleased(MouseEvent e)
        {
            if (e.isPopupTrigger())
            {
                popupMenu((Component)e.getSource(), e.getX(), e.getY());
            }
        }

        @Override
        public void mousePressed(MouseEvent e)
        {
            if (e.isPopupTrigger())
            {
                popupMenu((Component)e.getSource(), e.getX(), e.getY());
            }
        }

        private void popupMenu(Component source, int x, int y)
        {
            if (source == networkTable || source == networkTableScrollPane )
            {
                networkPopup.show(source, x, y);
            }
        }
    }

    //////////////////////////////////////////////////////////////////////////
    /// Actions
    //////////////////////////////////////////////////////////////////////////

    /**
     * Starts a download.
     */
    class CopyMyIpAction extends FWAction
    {
        CopyMyIpAction()
        {
            super( Localizer.getString( "Copy" ),
                IconPack.EMPTY_IMAGE_16,
                Localizer.getString( "TTTCopyMyIP" ) );
        }

        public void actionPerformed( ActionEvent e )
        {
            DestAddress address = Servent.getInstance().getLocalAddress();
            StringSelection data = new StringSelection( address.getFullHostName() );
            Clipboard clipboard =
              Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(data, data);
        }

        /**
         * @see phex.gui.actions.FWAction#refreshActionState()
         */
        @Override
        public void refreshActionState()
        {
        }
    }
}
