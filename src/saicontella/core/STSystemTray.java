package saicontella.core;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class STSystemTray {

    private TrayIcon trayIcon = null;
    private SystemTray tray = null; 
    
    public STSystemTray() {

        if (SystemTray.isSupported()) {

            tray = SystemTray.getSystemTray();
            Image image = STLibrary.getInstance().getAppIcon().getImage();

            MouseListener mouseListener = new MouseListener() {
                public void mouseClicked(MouseEvent e) {
                    //System.out.println("Tray Icon - Mouse clicked!");
                }
                public void mouseEntered(MouseEvent e) {
                    //System.out.println("Tray Icon - Mouse entered!");
                }
                public void mouseExited(MouseEvent e) {
                    //System.out.println("Tray Icon - Mouse exited!");
                }
                public void mousePressed(MouseEvent e) {
                    //System.out.println("Tray Icon - Mouse pressed!");
                }
                public void mouseReleased(MouseEvent e) {
                    //System.out.println("Tray Icon - Mouse released!");
                }
            };

            ActionListener exitListener = new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    //System.out.println("Exiting...");
                    STLibrary.getInstance().exitApplication();                    
                }
            };

            PopupMenu popup = new PopupMenu();
            MenuItem defaultItem = new MenuItem("Exit");
            defaultItem.addActionListener(exitListener);
            popup.add(defaultItem);

            trayIcon = new TrayIcon(image, "i-Share v" + STLibrary.getInstance().getVersion(), popup);


            ActionListener actionListener = new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    //trayIcon.displayMessage("i-Share v" + STLibrary.getInstance().getVersion(), "", TrayIcon.MessageType.INFO);                    
                    STLibrary.getInstance().DeIconifyMainForm();
                }
            };
            trayIcon.addActionListener(actionListener);
            

            trayIcon.setImageAutoSize(true);
            trayIcon.addMouseListener(mouseListener);
        }
    }

    public void addToSystemTray() {
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.err.println("TrayIcon could not be added.");
        }
    }

    public void removeFromSystemTray() {
        try {
            tray.remove(trayIcon);
        } catch (Exception e) {
            System.err.println("TrayIcon could not be removed.");
        }
    }
}
