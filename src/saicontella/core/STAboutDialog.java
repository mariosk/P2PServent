package saicontella.core;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.net.URL;

public class STAboutDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JLabel ads1Label;
    private JLabel ads2Label;

    public STAboutDialog(JFrame owner) {
        super(owner);
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);        

        try {
            ImageIcon imageIcon1AboutForm = new ImageIcon(new URL(STLibrary.STConstants.ADS_ABOUT_URL1));
            imageIcon1AboutForm = STLibrary.getInstance().resizeMyImageIcon(imageIcon1AboutForm, 350, 180);
            ads1Label.setIcon(imageIcon1AboutForm);
            ImageIcon imageIcon2AboutForm = new ImageIcon(new URL(STLibrary.STConstants.ADS_ABOUT_URL2));
            imageIcon2AboutForm = STLibrary.getInstance().resizeMyImageIcon(imageIcon2AboutForm, 350, 180);
            ads2Label.setIcon(imageIcon2AboutForm);            
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });
    }

    private void onOK() {
// add your code here
        dispose();
    }
}
