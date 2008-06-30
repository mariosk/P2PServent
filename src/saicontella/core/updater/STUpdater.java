package saicontella.core.updater;

/**
 * SaiconTella project
 *
 * @author Marios Karagiannopoulos (mariosk@gmail.com)
 *
 * June 2008
 */

import saicontella.core.STLibrary;
import saicontella.core.STLocalizer;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;

public class STUpdater {
    final static int interval = 1000;
    private int i;
    private JLabel label;
    private JProgressBar pb;
    private Timer timer;
    private JButton button;
    private String version;
    private String URL;
    private String localPath;
    private JFrame mainPanel;
    private STLibrary sLibrary;

    public STUpdater(String version, String URL, String localPath) {
        this.sLibrary = STLibrary.getInstance();        
        this.version = version;
        this.URL = URL;
        this.localPath = localPath;
        JFrame frame = new JFrame();
        this.mainPanel = frame;
        this.mainPanel.setBackground(STLibrary.getInstance().getSTConfiguration().getBgColor());
        STLocalizer.initialize( sLibrary.getSTConfiguration().getLangLocale() );
        frame.setTitle(STLocalizer.getString("UpdateIShare"));
        frame.setSize(310, 130);
        frame.setBackground(STLibrary.getInstance().getSTConfiguration().getBgColor());
        frame.setLocationRelativeTo(null);
        frame.setIconImage(new ImageIcon("./images/gamers.png").getImage());
        frame.setResizable(false);

        button = new JButton("Start");
        button.addActionListener(new ButtonListener());
        button.setBackground( Color.GRAY );

        pb = new JProgressBar(0, 100);
        pb.setValue(0);
        pb.setStringPainted(true);

        label = new JLabel(STLocalizer.getString("DownloadVersion") + " " + version + "...");
        label.setForeground( Color.WHITE );

        JPanel panel = new JPanel();
        panel.setBackground(STLibrary.getInstance().getSTConfiguration().getBgColor());
        panel.add(button);
        panel.add(pb);

        JPanel panel1 = new JPanel();
        panel1.setBackground(STLibrary.getInstance().getSTConfiguration().getBgColor());
        panel1.setLayout(new BorderLayout());
        panel1.add(panel, BorderLayout.NORTH);
        panel1.add(label, BorderLayout.CENTER);
        panel1.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        frame.setContentPane(panel1);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create a timer.
        timer = new Timer(interval, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (i == 100){
                    mainPanel.setCursor(Cursor.getDefaultCursor());
                    Toolkit.getDefaultToolkit().beep();
                    timer.stop();
                    button.setEnabled(true);
                    pb.setValue(0);
                    String str = "<html>" + "<b>" + STLocalizer.getString("DownloadCompleted") + "</b>" + "</font>" + "</html>";
                    label.setText(str);
                }
                i = i + 1;
                pb.setValue(i);
            }
        });
    }

    public static void main(String[] args) {
        if (args.length != 3)
            return;
        STUpdater updater = new STUpdater(args[0], args[1], args[2]);        
    }

    class DownloadRunnable implements Runnable {
       
        public void run() {           
            if (STLibrary.getInstance().downloadNewerVersion(version, URL)) {
                i = 100;
                mainPanel.setCursor(Cursor.getDefaultCursor());
                try {
                    STLibrary.getInstance().fireMessageBox(STLocalizer.getString("UpdaterUninstall"), STLocalizer.getString("Information"), JOptionPane.INFORMATION_MESSAGE);
                    Process p = Runtime.getRuntime().exec(localPath + "\\uninst.exe");
                    File executable = new File(localPath + "\\i-Share.exe");
                    while (executable.exists()) {
                        Thread.sleep(2000);
                    }
                    //p.waitFor();                                                                                                                        
                    STLibrary.getInstance().fireMessageBox(STLocalizer.getString("UpdaterInstall"), STLocalizer.getString("Information"), JOptionPane.INFORMATION_MESSAGE);
                    Runtime.getRuntime().exec(STLibrary.getInstance().getNewVersionFileName(version));
                    System.exit(0);
                }
                catch (Exception ex) {
                    STLibrary.getInstance().fireMessageBox(ex.getMessage(), STLocalizer.getString("Error"), JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            i = 100;
        }
    }

    class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent ae) {
            mainPanel.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            button.setEnabled(false);
            i = 0;
            String str = "<html>" + "<b>" + STLocalizer.getString("DownloadInProgress") + "</b>" + "</font>" + "</html>";
            label.setText(str);
            timer.start();
            Thread downloadThread = new Thread(new DownloadRunnable());
            downloadThread.start();                        
        }
    }
}
