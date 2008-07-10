package saicontella.core;

/**
 * SaiconTella project
 *
 * @author Marios Karagiannopoulos (mariosk@gmail.com)
 *
 * March 2008
 */

import saicontella.core.webservices.admin.UserSettingsWrapper;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class STAdminDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JCheckBox bannedCheckBox;
    private JComboBox comboBoxDload;
    private JComboBox comboBoxUpload;
    private JTextField textFieldSearch;
    private JButton buttonSearch;
    private JList userNamesFoundList;
    private JLabel labelSelectedUser;
    private String[] listUserIds;

    public STAdminDialog(JFrame owner) {
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
        buttonSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ArrayList<String[]> listOfUserIds = STLibrary.getInstance().getUserIds(textFieldSearch.getText(), "", "");
                if (listOfUserIds != null) {
                    userNamesFoundList.removeAll();
                    String[] listData = new String[listOfUserIds.size()];
                    listUserIds = new String[listOfUserIds.size()];
                    for (int i = 0; i < listOfUserIds.size(); i++) {
                        listData[i] = listOfUserIds.get(i)[0];
                        listUserIds[i] = listOfUserIds.get(i)[1]; 
                    }
                    userNamesFoundList.setListData(listData);                   
                }
            }
        });
        userNamesFoundList.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                labelSelectedUser.setText(userNamesFoundList.getSelectedValue().toString());
                // retrieving the relevant settings for the selected user...
                int index = userNamesFoundList.getSelectedIndex();
                String userId = listUserIds[index];
                saicontella.core.webservices.admin.UserSettingsWrapper[] settings = STLibrary.getInstance().getUserSettings(userId);
                bannedCheckBox.setSelected(STLibrary.getInstance().isUserBanned(settings));
                int gbToShare = STLibrary.getInstance().getGBytesToShare(settings);
                int gbToDownload = STLibrary.getInstance().getGBytesToDownload(settings);
                index = gbToDownload;
                if (index > 6) index = 6;
                if (index < 0) index = 0;
                comboBoxDload.setSelectedIndex(index);
                index = gbToShare;
                if (index > 6) index = 6;
                if (index < 0) index = 0;
                comboBoxUpload.setSelectedIndex(index);                
            }
        });
        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                UserSettingsWrapper[] settings = new UserSettingsWrapper[4];
                settings[0] = new UserSettingsWrapper(STLibrary.STConstants.ADMINISTRATOR, "false");
                settings[1] = new UserSettingsWrapper(STLibrary.STConstants.BANNED, bannedCheckBox.isSelected() ? "true" : "false");
                Integer index = new Integer(comboBoxUpload.getSelectedIndex());
                settings[2] = new UserSettingsWrapper(STLibrary.STConstants.SHARED_GB, index.toString());
                index = new Integer(comboBoxDload.getSelectedIndex());
                settings[3] = new UserSettingsWrapper(STLibrary.STConstants.DOWNLOAD_GB, index.toString());
                int i = userNamesFoundList.getSelectedIndex();
                String userId = listUserIds[i];
                STLibrary.getInstance().setUserSettings(userId, settings);
                STLibrary.getInstance().fireMessageBox(STLocalizer.getString("SuccessSubmit") + userNamesFoundList.getSelectedValue().toString(), STLocalizer.getString("Information"), JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    private void onOK() {
// add your code here
        dispose();
    }

    private void onCancel() {
// add your code here if necessary
        dispose();
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here 
    }
}
