package saicontella.core;

/**
 * SaiconTella project
 *
 * @author Marios Karagiannopoulos (mariosk@gmail.com)
 *
 * June 2008
 */

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class STAppUpdateDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel versionsJlabel;

    public STAppUpdateDialog(JFrame owner) {
        super(owner);
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

// call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

// call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        this.versionsJlabel.setText("Latest Version:" + STLibrary.getInstance().getLatestVersion());
    }

    private void onOK() {
        try {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));            
            STLibrary.getInstance().STLogoutUser();
            String command = STLibrary.getInstance().getApplicationLocalPath() + "\\i-ShareUpdater.exe " + "\"" + STLibrary.getInstance().getLatestVersion() + "\"" + " " + "\"" + STLibrary.getInstance().getLatestVersionURL() + "\"" + " " + "\"" + STLibrary.getInstance().getApplicationLocalPath() + "\"";
            Runtime.getRuntime().exec(command);
            System.exit(0);
        }
        catch (Exception ex) {
            STLibrary.getInstance().fireMessageBox(ex.getMessage(), STLocalizer.getString("Error"), JOptionPane.ERROR_MESSAGE);
        }
        this.setCursor(Cursor.getDefaultCursor());
        dispose();
    }

    private void onCancel() {
// add your code here if necessary
        dispose();
    }
}
