package saicontella.core;

/**
 * SaiconTella project
 *
 * @author Marios Karagiannopoulos (mariosk@gmail.com)
 *
 * March 2008
 */

import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.event.*;
import java.awt.*;
import java.util.Vector;

import phex.gui.actions.FWAction;
import saicontella.phex.stlibrary.STLibraryTab;
import saicontella.phex.stlibrary.STLibraryTab.AddFriendAction;

public class STFriendDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JList friendsList;
    private JRadioButton radioButtonReject;
    private JRadioButton radioButtonDeny;
    private JRadioButton radioButtonAccept;
    private Vector friendsListData;
    private Vector friendsListIds;
    private Component parent;
    private int[] friendsListAction;

    public STFriendDialog(Component parent, Vector[] data) {

        super((JFrame)parent);
        friendsListAction = new int[data[0].size()];
        for (int i = 0; i < friendsListAction.length; i++) {
            friendsListAction[i] = -1;
        }
        this.parent = parent;
        this.friendsListData = data[0];
        this.friendsListIds = data[1];        
        this.friendsList.setListData(this.friendsListData);
        this.friendsList.repaint();
        this.friendsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.radioButtonAccept.setForeground(Color.GRAY);
        this.radioButtonReject.setForeground(Color.GRAY);
        this.radioButtonDeny.setForeground(Color.GRAY);
        this.radioButtonAccept.setBackground(Color.BLACK);
        this.radioButtonReject.setBackground(Color.BLACK);
        this.radioButtonDeny.setBackground(Color.BLACK);
        this.radioButtonAccept.setEnabled(false);
        this.radioButtonReject.setEnabled(false);
        this.radioButtonDeny.setEnabled(false);

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
        friendsList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                radioButtonAccept.setEnabled(true);
                radioButtonReject.setEnabled(true);
                radioButtonDeny.setEnabled(true);

                switch (friendsListAction[friendsList.getSelectedIndex()]) {
                    case STLibrary.STConstants.ACCEPTED:
                        radioButtonAccept.setSelected(true);
                        radioButtonReject.setSelected(false);
                        radioButtonDeny.setSelected(false);
                        break;
                    case STLibrary.STConstants.REJECTED:
                        radioButtonAccept.setSelected(false);
                        radioButtonReject.setSelected(true);
                        radioButtonDeny.setSelected(false);
                        break;
                    case STLibrary.STConstants.DENIED:
                        radioButtonAccept.setSelected(false);
                        radioButtonReject.setSelected(false);
                        radioButtonDeny.setSelected(true);
                        break;
                    default:
                        radioButtonAccept.setSelected(false);
                        radioButtonReject.setSelected(false);
                        radioButtonDeny.setSelected(false);                        
                }                
            }
        });
        radioButtonAccept.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (radioButtonAccept.isSelected()) {
                    if (friendsList.getSelectedIndex() >= 0)
                        friendsListAction[friendsList.getSelectedIndex()] = STLibrary.STConstants.ACCEPTED;
                    radioButtonReject.setSelected(false);
                    radioButtonDeny.setSelected(false);
                }
            }
        });
        radioButtonReject.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (radioButtonReject.isSelected()) {
                    if (friendsList.getSelectedIndex() >= 0)
                        friendsListAction[friendsList.getSelectedIndex()] = STLibrary.STConstants.REJECTED;
                    radioButtonAccept.setSelected(false);
                    radioButtonDeny.setSelected(false);
                }
            }
        });
        radioButtonDeny.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (radioButtonDeny.isSelected()) {                    
                    if (friendsList.getSelectedIndex() >= 0)
                        friendsListAction[friendsList.getSelectedIndex()] = STLibrary.STConstants.DENIED;
                    radioButtonReject.setSelected(false);
                    radioButtonAccept.setSelected(false);
                }
            }
        });        
    }

    private void onOK() {
        //STMainForm parentObj = (STMainForm) this.parent;
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        for (int i = 0; i < friendsListAction.length; i++) {
            STLibrary.getInstance().setFriendStatus(friendsListAction[i], (Integer)friendsListIds.get(i), friendsListData.get(i).toString());
        }
        this.setCursor(Cursor.getDefaultCursor());
        dispose();
    }

    private void onCancel() {
// add your code here if necessary
        dispose();
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        contentPane = new JPanel();
        contentPane.setLayout(new GridLayoutManager(2, 1, new Insets(10, 10, 10, 10), -1, -1));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel1.add(spacer1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1, true, false));
        panel1.add(panel2, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        buttonOK = new JButton();
        buttonOK.setText("OK");
        panel2.add(buttonOK, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonCancel = new JButton();
        buttonCancel.setText("Cancel");
        panel2.add(buttonCancel, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        panel3.add(scrollPane1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        friendsList = new JList();
        scrollPane1.setViewportView(friendsList);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }
}
