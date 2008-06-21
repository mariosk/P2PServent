package saicontella.core;

/**
 * SaiconTella project
 *
 * @author Marios Karagiannopoulos (mariosk@gmail.com)
 *
 * June 2008
 */

import org.jdesktop.jdic.desktop.Desktop;
import org.jdesktop.jdic.desktop.DesktopException;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.net.URL;
import java.net.MalformedURLException;
import java.util.*;
import java.util.List;

public class STLoginDialog extends JDialog implements ItemListener {
    private JPanel contentPane;
    private JButton buttonConnect;
    private JPasswordField passwordField;
    private JTextField textFieldUserName;
    private JLabel registerLabel;
    private JLabel forgotPassLabel;
    private JTextField textFieldPort;
    private JCheckBox checkBoxAutoConnect;
    private JLabel imageLabel;
    //private ImageIcon[] images;
    private JPanel buttonsPanel;
    private JPanel fieldsPanel;
    private JComboBox langsComboBox;
    private List<Locale> availableLocales;

    public STLoginDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonConnect);

        this.setIconImage(STLibrary.getInstance().getAppIcon().getImage());

        availableLocales = STLocalizer.getAvailableLocales();
        int configLangFound = -1;
        for (int i = 0; i < availableLocales.size(); i++) {
            if (configLangFound < 0) {
                if (STLibrary.getInstance().getSTConfiguration().getLangLocale() != null) {
                    if (STLibrary.getInstance().getSTConfiguration().getLangLocale().equals(availableLocales.get(i).toString())) {
                        configLangFound = i;
                        break;
                    }
                }
            }
        }
        langsComboBox.addItemListener(this);
        if (configLangFound >= 0) {
            STLocalizer.initialize( STLibrary.getInstance().getSTConfiguration().getLangLocale() );
        }
        else {
            STLocalizer.initialize( "en_US" );        
        }

        for (int i = 0; i < availableLocales.size(); i++) {
            //ImageIcon image = new ImageIcon(STResources.getAppStr("lang_" + availableLocales.get(i).toString() + ".ico"));
            //langsComboBox.addItem(image);
            langsComboBox.addItem(STLocalizer.getString(availableLocales.get(i).toString()));
        }

        if (configLangFound >= 0)
            langsComboBox.setSelectedIndex(configLangFound);
        else
            langsComboBox.setSelectedIndex(0);


        this.imageLabel.setIcon(STLibrary.getInstance().getLoginIcon());
        this.checkBoxAutoConnect.setSelected(STLibrary.getInstance().getSTConfiguration().getAutoConnect());
        if (STLibrary.getInstance().getSTConfiguration().getWebServiceAccount() != null
         && STLibrary.getInstance().getSTConfiguration().getWebServicePassword() != null
         && STLibrary.getInstance().getSTConfiguration().getListenPort() != 0) {
            this.textFieldUserName.setText(STLibrary.getInstance().getSTConfiguration().getWebServiceAccount());
            this.passwordField.setText(STLibrary.getInstance().getSTConfiguration().getWebServicePassword());
            this.textFieldPort.setText(new Integer(STLibrary.getInstance().getSTConfiguration().getListenPort()).toString());
        }
        else {
            this.textFieldPort.setText(new Integer(STLibrary.getInstance().getSTConfiguration().getListenPort()).toString());
        }
        
        LabelListening app = new LabelListening();        
        this.registerLabel.setFocusable(true);
        this.registerLabel.putClientProperty(LabelListening.TEXT_KEY, this.registerLabel.getText());
        this.registerLabel.setFocusable(true);
        this.registerLabel.addFocusListener(app);
        this.registerLabel.addMouseListener(app);

        this.forgotPassLabel.setFocusable(true);
        this.forgotPassLabel.putClientProperty(LabelListening.TEXT_KEY, this.forgotPassLabel.getText());
        this.forgotPassLabel.setFocusable(true);
        this.forgotPassLabel.addFocusListener(app);
        this.forgotPassLabel.addMouseListener(app);

        buttonConnect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
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

        if (STLibrary.getInstance().getSTConfiguration().getAutoConnect()) {
            this.onOK();
            setVisible(false);
        }
        else {
            setTitle(STLocalizer.getString("LoginIShare") + " v" + STLibrary.getInstance().getVersion());            
            pack();
            setLocationRelativeTo(null);
            setResizable(false);                                               
            setVisible(true);
        }
    }

    public void itemStateChanged(ItemEvent e) {
        STLibrary.getInstance().getSTConfiguration().setLangLocale(availableLocales.get(langsComboBox.getSelectedIndex()).toString());
        STLocalizer.initialize( STLibrary.getInstance().getSTConfiguration().getLangLocale() );
    }
    
    private void onOK() {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        STLibrary.getInstance().getSTConfiguration().setAutoConnect(String.valueOf(this.checkBoxAutoConnect.isSelected()));
        STLibrary.getInstance().getSTConfiguration().setWebServiceAccount(this.textFieldUserName.getText());
        STLibrary.getInstance().getSTConfiguration().setWebServicePassword(false, this.passwordField.getText());
        STLibrary.getInstance().getSTConfiguration().setListenPort(this.textFieldPort.getText());
        STLibrary.getInstance().getSTConfiguration().setLangLocale(availableLocales.get(langsComboBox.getSelectedIndex()).toString());        
        STLibrary.getInstance().getSTConfiguration().saveXMLFile();

        STLibrary.getInstance().getGnutellaFramework().restart(STLibrary.getInstance(), true);
        STMainForm mainFrame = new STMainForm(STLibrary.getInstance(), null);
        mainFrame.setVisible(false);
        this.setCursor(Cursor.getDefaultCursor());

        if (mainFrame.connectAction(this.textFieldUserName.getText(), this.passwordField.getText(), Integer.parseInt(this.textFieldPort.getText()))) {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            dispose();
            mainFrame.pack();
            mainFrame.initFrameSize();            
            mainFrame.initializeToolsTabValues();
            mainFrame.createUIComponents();
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainFrame.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
        else {
            this.setCursor(Cursor.getDefaultCursor());
            mainFrame.dispose();
            // do not dispose here the loginDlg to let the user to retry once more. 
        }
    }

    private void onCancel() {
        dispose();
        System.exit(0);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    class LabelListening extends MouseAdapter implements FocusListener {
        private static final String TEXT_KEY = "jlabel_text_key";

        public void focusGained(FocusEvent e) {
            JLabel label = (JLabel) e.getComponent();
            String text = (String) label.getClientProperty(TEXT_KEY);
            label.setText("<html><u>" + text + "</u></html>");
        }

        public void focusLost(FocusEvent e) {
            JLabel label = (JLabel) e.getComponent();
            String text = (String) label.getClientProperty(TEXT_KEY);
            label.setText(text);
        }

        public void mousePressed(MouseEvent e) {
            e.getComponent().requestFocusInWindow();
            try {
                if (e.getComponent() == registerLabel)
                    Desktop.browse(new URL(STLibrary.STConstants.REGISTER_URL));
                else if (e.getComponent() == forgotPassLabel)
                    Desktop.browse(new URL(STLibrary.STConstants.LOST_PASSWORD_URL));                
            } catch(MalformedURLException ex) {
                STLibrary.getInstance().fireMessageBox(ex.getMessage(), STLocalizer.getString("Error"), JOptionPane.ERROR_MESSAGE);
            } catch (DesktopException ex) {
                STLibrary.getInstance().fireMessageBox(ex.getMessage(), STLocalizer.getString("Error"), JOptionPane.ERROR_MESSAGE);
            }            
        }
    }
}
