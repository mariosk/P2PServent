package saicontella.core;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.builder.PanelBuilder;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.net.MalformedURLException;

import org.jdesktop.jdic.desktop.Desktop;
import org.jdesktop.jdic.desktop.DesktopException;

public class STButtonsPanel extends JPanel {

    public STButtonsPanel() {
        super();

        CellConstraints cc = new CellConstraints();
        FormLayout layout = new FormLayout(
            //"8dlu, right:d, 2dlu, right:d, 2dlu, d, 2dlu:grow, 8dlu", // columns
            "right:d, right:d, 2dlu, right:d, 2dlu, d, 2dlu:grow, 8dlu", // columns
            //"0dlu, d, 2dlu, d, 10dlu:grow, d, 2dlu, d, 2dlu, d, 0dlu", // columns
            "fill:d:grow, 3dlu, p"); // 3 rows

        PanelBuilder cacheStatusBuilder = new PanelBuilder( layout, this );

        JButton igamerButton = new JButton("");
        igamerButton.setBorder(BorderFactory.createEmptyBorder());
        igamerButton.setIcon(new ImageIcon(STResources.getStr("myIGamersImage")));
        ConnectToIGamersHandler igamersHandler = new ConnectToIGamersHandler();
        igamerButton.addActionListener( igamersHandler );
        cacheStatusBuilder.add( igamerButton, cc.xy( 1, 3 ) );

        JButton exitButton = new JButton("");
        exitButton.setBorder(BorderFactory.createEmptyBorder());
        exitButton.setIcon(new ImageIcon(STResources.getStr("myExitImage")));
        ExitHandler exitHandler = new ExitHandler();
        exitButton.addActionListener( exitHandler );
        cacheStatusBuilder.add( exitButton, cc.xy( 6, 3) );

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
            STLibrary.getInstance().getGnutellaFramework().disconnectFromPeers();
            STLibrary.getInstance().STLogoutUser();
            System.exit(0);
        }
    }
    
}