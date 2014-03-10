/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fileui;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;
import com.jcraft.jsch.SftpProgressMonitor;
import com.jcraft.jsch.SftpStatVFS;
import com.jcraft.jsch.UIKeyboardInteractive;
import com.jcraft.jsch.UserInfo;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import mygui.MyButton;
import mygui.MyPopupMenu;
import mygui.mylistener.PopupListener;
import mygui.mylistener.WaitLayerUI;

/**
 *
 * @author xiaoerge
 */
public class GUI extends JFrame {

    private JSch jsch;
    private Session session;
    private Channel channel;
    private ChannelSftp channelsftp;
    private String username;
    private String password;
    private String host;
    private int port;
    private UserInfo accountinfo;

    //
    private JPanel container;
    private View.MenuView menu_view;
    private View.LoginView login_view;
    private View.MainView main_view;

    private String working_directory_backup = ".";
    private Timer stopper;
    private WaitLayerUI layer;

    public GUI() {
        jsch = new JSch();
        session = null;
        accountinfo = null;
        channel = null;
        container = (JPanel) getContentPane();

        menu_view = new View().new MenuView();
        login_view = new View().new LoginView();
        main_view = new View().new MainView();

        layer = new WaitLayerUI();
        stopper = new Timer(500, new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                layer.stop();
            }
        });

        setJMenuBar(menu_view);
        getContentPane().setBackground(Color.white);

        container.setLayout(new CardLayout());
        container.add(login_view, "login");
        container.add(new JLayer(main_view, layer), "main");

        stopper.setRepeats(false);

        container.setPreferredSize(new Dimension(1000, 600));

        login_view.login_bt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final String host = login_view.username_tf.getText();
                final String passwd = login_view.password_tf.getText();
                final int port = 22;

                if (host.length() > 0 && passwd.length() > 0) {
                    new Thread(new Runnable() {

                        @Override
                        public void run() {
                            try {

                                layer.start();

                                GUI.this.username = host.substring(0, host.indexOf('@'));
                                GUI.this.host = host.substring(host.indexOf('@') + 1);
                                GUI.this.password = passwd;
                                GUI.this.port = port;

                                accountinfo = new AccountInfo(username, password, login_view.password_tf);
                                session = jsch.getSession(GUI.this.username, GUI.this.host, GUI.this.port);

                                session.setUserInfo(new AccountInfo(username, password, login_view.password_tf));
                                session.connect();

                                channel = session.openChannel("sftp");
                                channel.connect();

                                channelsftp = (ChannelSftp) channel;

                                CardLayout card = (CardLayout) container.getLayout();
                                card.show(container, "main");

                                //$ ls 
                                ls(".");
                            } catch (JSchException ex) {
                                menu_view.status_lb.setText(ex.getMessage());
                            } catch (SftpException ex) {
                                menu_view.status_lb.setText(ex.getMessage());

                            }
                            if (!stopper.isRunning()) {
                                stopper.start();
                            }
                        }
                    }).start();
                }
            }
        });

        menu_view.exit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if (channelsftp.isConnected() && channel.isConnected()) {
                    int choice = JOptionPane.showConfirmDialog(null, "Log out", "", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

                    //didnt really log out yet
                    if (choice == JOptionPane.YES_OPTION) {
                        CardLayout card = (CardLayout) container.getLayout();
                        card.show(container, "login");

                        //login_view.username_tf.setText("");
                        login_view.password_tf.setText("");
                        menu_view.status_lb.setText("");

                        if (session != null) {
                            session.disconnect();
                        }
                        if (channel != null) {
                            channel.disconnect();
                        }
                        if (channelsftp != null) {
                            channelsftp.quit();
                        }
                    }
                }
            }
        });

        getRootPane().setDefaultButton(login_view.login_bt);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);

                if (session != null) {
                    session.disconnect();
                }
                if (channel != null) {
                    channel.disconnect();
                }
                if (channelsftp != null) {
                    channelsftp.quit();
                }

                System.exit(0);
            }
        });

        TransferHandler handler = new TransferHandler() {

            @Override
            public boolean canImport(TransferHandler.TransferSupport info) {
                // we only import FileList
                if (!info.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                    return false;
                }
                return true;
            }

            @Override
            public boolean importData(TransferHandler.TransferSupport info) {
                if (!info.isDrop()) {
                    return false;
                }

                // Check for FileList flavor
                if (!info.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                    displayDropLocation("Doesn't accept a drop of this type.");
                    return false;
                }

                // Get the fileList that is being dropped.
                Transferable t = info.getTransferable();
                List<File> data;
                try {
                    data = (List<File>) t.getTransferData(DataFlavor.javaFileListFlavor);
                } catch (Exception e) {
                    return false;
                }

                //DefaultListModel model = (DefaultListModel) fileDropper.getModel();
                for (final File file : data) {
                    new Thread(new Runnable() {

                        @Override
                        public void run() {
                            try {
                                layer.start();
                                put(channelsftp.pwd(), file);
                                ls(channelsftp.pwd());
                            } catch (SftpException ex) {
                                menu_view.status_lb.setText(ex.getMessage());
                            }
                            if (!stopper.isRunning()) {
                                stopper.start();
                            }
                        }
                    }).start();
                }

                return true;
            }

            private void displayDropLocation(String string) {
                System.out.println(string);
            }
        };
        main_view.setTransferHandler(handler);

        main_view.mkdir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog(main_view, "Make new directory with name");

                if (name != null && name.length() > 0) {
                    try {
                        layer.start();
                        channelsftp.mkdir(name);
                        ls(channelsftp.pwd());

                    } catch (SftpException ex) {
                        menu_view.status_lb.setText(ex.getMessage());
                    }
                    if (!stopper.isRunning()) {
                        stopper.start();
                    }
                }
            }
        });

        main_view.upload.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int result;

                final JFileChooser chooser = new JFileChooser();
                chooser.setDialogTitle("Upload");
                chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                chooser.setAcceptAllFileFilterUsed(false);

                if (chooser.showOpenDialog(main_view) == JFileChooser.APPROVE_OPTION) {
                    new Thread(new Runnable() {

                        @Override
                        public void run() {
                            try {
                                layer.start();
                                put(channelsftp.pwd(), chooser.getSelectedFile());
                                ls(channelsftp.pwd());
                            } catch (SftpException ex) {
                                menu_view.status_lb.setText(ex.getMessage());
                            }
                            if (!stopper.isRunning()) {
                                stopper.start();
                            }
                        }
                    }).start();
                }
            }
        }
        );

        menu_view.change.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (channelsftp != null) {
                    String path = JOptionPane.showInputDialog("Change directory to path\n"
                            + "Does not accept ~ sign\n"
                            + "Takes absolute path");

                    if (path != null && path.length() > 0) {
                        try {
                            layer.start();
                            channelsftp.cd(path);
                            ls(channelsftp.pwd());
                        } catch (SftpException ex) {
                            menu_view.status_lb.setText(ex.getMessage());
                            
                        }
                        if (!stopper.isRunning()) {
                                stopper.start();
                            }
                    }
                }
            }
        });
        
        View.MainPopupMenuView popup = new View().new MainPopupMenuView();
        main_view.addMouseListener(new PopupListener(popup));
        
        //case sensitive
        popup.getMenuItem("Make directory").addActionListener(main_view.mkdir.getActionListeners()[0]);
        popup.getMenuItem("Upload").addActionListener(main_view.upload.getActionListeners()[0]);
    }

    private void get(String path, ChannelSftp.LsEntry entry) throws SftpException {
        if (!entry.getAttrs().isDir()) {
            channelsftp.get(entry.getFilename(), channelsftp.lpwd(), new MyProgressMonitor());

        } else if (!entry.getFilename().equals(".") && !entry.getFilename().equals("..")) {
            //cd into the dir
            channelsftp.cd(path + "/" + entry.getFilename());

            File file = new File(channelsftp.lpwd() + "/" + entry.getFilename());
            file.mkdir();

            channelsftp.lcd(file.getAbsolutePath());

            Vector vec = channelsftp.ls(channelsftp.pwd());

            for (Object o : vec) {
                if (o instanceof ChannelSftp.LsEntry) {
                    ChannelSftp.LsEntry local_entry = (ChannelSftp.LsEntry) o;

                    if (!local_entry.getFilename().equals(".") && !local_entry.getFilename().equals("..")) {
                        get(channelsftp.pwd(), local_entry);
                    }
                }
            }

            channelsftp.cd(path);
            channelsftp.lcd(file.getParent());

        } else {
            System.err.println("Error get");
        }
    }

    private void put(String path, File file) throws SftpException {
        if (!file.isDirectory()) {
            channelsftp.put(file.getAbsolutePath(), channelsftp.pwd(), new MyProgressMonitor());

        } else {
            //cd into the dir
            channelsftp.lcd(file.getAbsolutePath());

            channelsftp.mkdir(file.getName());

            channelsftp.cd(file.getName());

            File[] farray = file.listFiles();

            for (File f : farray) {
                put(channelsftp.pwd(), f);
            }

            channelsftp.lcd(file.getParent());
            channelsftp.cd(path);
        }
    }

    private void rm(String path, ChannelSftp.LsEntry entry) throws SftpException {

        if (!entry.getAttrs().isDir()) {
            channelsftp.rm(path + "/" + entry.getFilename());
        } else {
            //cd into the dir
            channelsftp.cd(path + "/" + entry.getFilename());

            Vector vec = channelsftp.ls(channelsftp.pwd());

            for (Object o : vec) {
                if (o instanceof ChannelSftp.LsEntry) {
                    ChannelSftp.LsEntry local_entry = (ChannelSftp.LsEntry) o;

                    if (!local_entry.getFilename().equals(".") && !local_entry.getFilename().equals("..")) {
                        rm(channelsftp.pwd(), local_entry);
                    }
                }
            }
            channelsftp.rmdir(path + "/" + entry.getFilename());
            channelsftp.cd(path);
        }
    }

    private void ls(String path) throws SftpException {

        //$ ls                    
        //if attempting to go into a dir with no permission, this line will fail
        //fix this later
        Vector vec = channelsftp.ls(path);

        if (vec.size() > 0) {
            main_view.container.removeAll();
            main_view.files.clear();
            main_view.labels.clear();

            
            Collections.sort(vec, new Comparator<ChannelSftp.LsEntry>() {
                @Override
                public int compare(ChannelSftp.LsEntry o1, ChannelSftp.LsEntry o2) {
                    return o1.getFilename().compareTo(o2.getFilename());
                }
            });
             
            for (int i = 0; i < vec.size(); i++) {
                Object obj = vec.elementAt(i);
                if (obj instanceof ChannelSftp.LsEntry) {
                    ChannelSftp.LsEntry entry = (ChannelSftp.LsEntry) obj;

                    doSelectAdd(entry);
                }
            }

            menu_view.status_lb.setText(channelsftp.pwd());

            main_view.placeHolder();
            
            repaint();

            addButtonListeners();
            addMenuListeners();
        }
    }

    private void doSelectAdd(ChannelSftp.LsEntry entry) {
        try {
            if (entry.getFilename().equals(".")) {
                //ignore current directory
            } else if (entry.getFilename().equals("..")) {
                main_view.addEntry(entry);
            } else if (entry.getFilename().startsWith(".")) {
                //ignore hidden files
            } //optimize this block
            else {
                main_view.addEntry(entry);
            }
        } catch (Exception ex) {
            menu_view.status_lb.setText(ex.getMessage());
        }
    }

    private void addMenuListeners() {
        final Iterator<MyButton> it_mb = main_view.labels.iterator();
        final Iterator<ChannelSftp.LsEntry> it_file = main_view.files.iterator();

        while (it_mb.hasNext()) {
            final ChannelSftp.LsEntry entry = it_file.next();
            final MyButton button = it_mb.next();

            View.EntryPopupMenuView popup = new View().new EntryPopupMenuView();
            button.addMouseListener(new PopupListener(popup));

            popup.getMenuItem("Download").addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    new Thread(new Runnable() {

                        @Override
                        public void run() {
                            try {
                                layer.start();
                                get(channelsftp.pwd(), entry);
                                
                            } catch (SftpException ex) {
                                menu_view.status_lb.setText(ex.getMessage());
                            }
                            if (!stopper.isRunning()) {
                                    stopper.start();
                                }
                        }
                    }).start();
                }
            });

            popup.getMenuItem("Remove").addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int choice = JOptionPane.showConfirmDialog(null, "Delete " + entry.getFilename(), "", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

                    if (choice == JOptionPane.YES_OPTION) {

                        new Thread(new Runnable() {

                            @Override
                            public void run() {
                                try {
                                    layer.start();
                                    rm(channelsftp.pwd(), entry);
                                    ls(channelsftp.pwd());

                                    
                                } catch (SftpException ex) {
                                    menu_view.status_lb.setText(ex.getMessage());
                                }
                                if (!stopper.isRunning()) {
                                        stopper.start();
                                    }
                            }
                        }).start();
                    }
                }
            });
        }
    }

    private void addButtonListeners() {
        try {
            //save current dirctory in case ls fails
            //we can revert back to this pwd
            final String pwd = channelsftp.pwd();
        } catch (SftpException ex) {
        }

        final Iterator<MyButton> it_mb = main_view.labels.iterator();
        final Iterator<ChannelSftp.LsEntry> it_file = main_view.files.iterator();

        while (it_mb.hasNext()) {
            final ChannelSftp.LsEntry entry = it_file.next();

            it_mb.next().addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    if (entry.getAttrs().isDir()) {
                        try {
                            layer.start();

                            working_directory_backup = channelsftp.pwd();
                            //  '/' unix file seperator
                            channelsftp.cd(channelsftp.pwd() + "/" + entry.getFilename());

                            //cd works fine when going into a file with no permission
                            //but ls does not. 
                            //so we make a copy of our working_directory_backup
                            //and use it to come back
                            ls(channelsftp.pwd());

                            

                        } catch (SftpException ex) {
                            try {
                                //purpose here is to go back to a working directory 
                                //when permission denied error occurs
                                channelsftp.cd(working_directory_backup);
                            } catch (SftpException ex1) {
                                menu_view.status_lb.setText(ex.getMessage());
                            }

                            menu_view.status_lb.setText(ex.getMessage());
                        }
                        if (!stopper.isRunning()) {
                                stopper.start();
                            }
                    }
                }
            });
        }
    }

    public static void main(String[] args) {
        GUI gui = new GUI();
        gui.setTitle(Model.getInstance().getAppname());
        gui.pack();
        gui.setLocationRelativeTo(null);
        gui.setIconImage(Model.getInstance().getApp_icon().getImage());
        gui.setVisible(true);
        gui.setDefaultCloseOperation(GUI.EXIT_ON_CLOSE);
    }
}
