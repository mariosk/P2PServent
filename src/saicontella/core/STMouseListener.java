package saicontella.core;

import org.jdesktop.jdic.desktop.DesktopException;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.*;
import java.net.URL;
import java.net.MalformedURLException;

public class STMouseListener extends MouseAdapter {

    private String linkURL;
    private JFrame parent;
    
    public STMouseListener(String linkURL, JFrame parent) {
        this.linkURL = linkURL;
        this.parent = parent;
    }
    
    public void mouseEntered(MouseEvent mouseEvent) {
        parent.setCursor(Cursor.HAND_CURSOR);
    }

    public void mouseExited(MouseEvent e) {
        parent.setCursor(Cursor.getDefaultCursor());
    }

    public void mouseClicked(MouseEvent e) {
        e.getComponent().requestFocusInWindow();
        try {
            org.jdesktop.jdic.desktop.Desktop.browse(new URL(linkURL));
        } catch(MalformedURLException ex) {
            STLibrary.getInstance().fireMessageBox(ex.getMessage(), STLocalizer.getString("Error"), JOptionPane.ERROR_MESSAGE);
        } catch (DesktopException ex) {
            STLibrary.getInstance().fireMessageBox(ex.getMessage(), STLocalizer.getString("Error"), JOptionPane.ERROR_MESSAGE);
        }
    }
}
