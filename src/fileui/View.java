/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fileui;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpATTRS;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.TransferHandler;
import javax.swing.border.TitledBorder;
import mygui.MyButton;
import mygui.MyLabel;
import mygui.MyMenuItem;
import mygui.MyPassword;
import mygui.MyPopupMenu;
import mygui.MyScrollBar;
import mygui.MyTextField;
import mygui.mylistener.PopupListener;

/**
 *
 * @author xiaoerge
 */
public class View 
{
    public class LoginView extends JPanel {

        public JPanel container;
        public JTextField username_tf;
        public JTextField password_tf;
        public MyButton login_bt;
        public JPanel login_pl, username_pl, password_pl;
        public JLabel username_lb;
        public JLabel password_lb;

        public LoginView() {
            super();

            init();
            toContentPane();
            misc();
        }

        private void init() {
            container = new JPanel();
            username_tf = new MyTextField("user@host");
            password_tf = new MyPassword("password");
            login_bt = new MyButton();
            login_pl = new JPanel();
            username_pl = new JPanel();
            password_pl = new JPanel();
            username_lb = new JLabel();
            password_lb = new JLabel();
        }

        private void toContentPane() {
            setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
            add(Box.createVerticalStrut(200));
            add(container);

            container.setLayout(new GridLayout(0, 1, 0, 3));
            container.add(username_pl);
            container.add(password_pl);
            container.add(login_pl);

            //for Mnemonic
            username_pl.setLayout(new BoxLayout(username_pl, BoxLayout.LINE_AXIS));
            username_pl.add(Box.createHorizontalStrut(5));
            username_pl.add(username_lb);
            username_pl.add(Box.createHorizontalStrut(5));
            username_pl.add(username_tf);
            
            password_pl.setLayout(new BoxLayout(password_pl, BoxLayout.LINE_AXIS));
            password_pl.add(Box.createHorizontalStrut(5));
            password_pl.add(password_lb);
            password_pl.add(Box.createHorizontalStrut(5));
            password_pl.add(password_tf);
            
            login_pl.setLayout(new BoxLayout(login_pl, BoxLayout.LINE_AXIS));
            login_pl.add(Box.createHorizontalGlue());
            login_pl.add(login_bt);
        }

        private void misc() {
            setBackground(Color.white);
            container.setBackground(Color.white);
            container.setMinimumSize(new Dimension(400, 200));
            container.setPreferredSize(new Dimension(400, 200));
            container.setMaximumSize(new Dimension(400, 200));

            login_pl.setBackground(Color.white);
            username_pl.setBackground(Model.getInstance().getDefault_color());
            password_pl.setBackground(Model.getInstance().getDefault_color());
            
            login_bt.setForeground(Color.white);
            login_bt.setFont(Model.getInstance().getFont(3));
            login_bt.setBackground(Model.getInstance().getDefault_color());
            login_bt.setMinimumSize(new Dimension(125, 50));
            login_bt.setPreferredSize(new Dimension(125, 50));
            login_bt.setMaximumSize(new Dimension(125, 50));
            login_bt.setText("Go");
            login_bt.setBorder(BorderFactory.createLineBorder(Color.white));
            login_bt.setIcon(Model.getInstance().getIc_go());
            login_bt.setHorizontalTextPosition(JButton.LEFT);
            
            username_tf.setFont(Model.getInstance().getFont(3));
            username_tf.setForeground(Model.getInstance().getDefault_color());
            username_tf.setSelectionColor(Model.getInstance().getDefault_color());
            username_tf.setSelectedTextColor(Color.white);
            username_tf.setBorder(BorderFactory.createLineBorder(Model.getInstance().getDefault_color(), 3));

            password_tf.setFont(Model.getInstance().getFont(3));
            password_tf.setForeground(Model.getInstance().getDefault_color());
            password_tf.setSelectionColor(Model.getInstance().getDefault_color());
            password_tf.setSelectedTextColor(Color.white);
            password_tf.setBorder(BorderFactory.createLineBorder(Model.getInstance().getDefault_color(), 3));
            
            username_lb.setLabelFor(username_tf);
            username_lb.setHorizontalAlignment(JLabel.CENTER);
            username_lb.setIcon(Model.getInstance().getIc_user());
            
            password_lb.setLabelFor(password_tf);
            password_lb.setHorizontalAlignment(JLabel.CENTER);
            password_lb.setIcon(Model.getInstance().getIc_lock());
        }
    }

    public class MenuView extends JMenuBar 
    {
        public MyButton exit;
        public MyButton change;
        //public JRadioButton show_hidden, hide_hidden;
        private ButtonGroup group;
        public JLabel status_lb;

        public MenuView() {
            super();
            init();
            toContentPane();
            misc();
        }

        private void init() {
            exit = new MyButton("Log out ");
            change = new MyButton();
            group = new ButtonGroup();
            status_lb = new JLabel();
        }

        private void toContentPane() {
            //group.add(show_hidden);
            //group.add(hide_hidden);
            
            setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
            this.add(change);
            this.add(status_lb);
            this.add(Box.createHorizontalGlue());
            this.add(exit);
        }

        private void misc() {
            setBackground(Model.getInstance().getDefault_color());

            exit.setBackground(Model.getInstance().getDefault_color());
            exit.setForeground(Color.white);
            exit.setFont(Model.getInstance().getFont(3));
            exit.setFocusable(false);
            exit.setBorder(BorderFactory.createLineBorder(Color.white, 0));
            exit.setIcon(Model.getInstance().getIc_exit());
            
            //working_dir_lb.setBackground(Model.getInstance().getDefault_color());
            status_lb.setForeground(Color.white);
            status_lb.setFont(Model.getInstance().getFont(3));

            change.setBackground(Model.getInstance().getDefault_color());
            change.setForeground(Color.white);
            change.setFont(Model.getInstance().getFont(3));
            change.setFocusable(false);
            change.setBorder(BorderFactory.createLineBorder(Model.getInstance().getDefault_color()));
            change.setIcon(Model.getInstance().getIc_change());
        }
    }
    public class MainView extends JScrollPane
    {
        public JPanel container;
        public List<ChannelSftp.LsEntry> files;
        public List<MyButton> labels;
        public MyButton mkdir;
        public MyButton upload;
        //public List<Boolean> hid_flag;
        
        public MainView()
        {
            super();
            
            init();
            toContentPane();
            misc();
        }
        private void init()
        {
            container = new JPanel();
            files = new ArrayList<>();
            labels = new ArrayList<>();
            mkdir = new MyButton();
            upload = new MyButton();
            //hid_flag = new ArrayList<>();
        }

        private void toContentPane()
        {
            setViewportView(container);        
            container.setLayout(new GridLayout(0, 6, 5, 5));
        }

        private void misc()
        {
            setBackground(Color.white);
            container.setBackground(Color.white);
            getViewport().setBackground(Color.white);
            
            getVerticalScrollBar().setUnitIncrement(16);
            setVerticalScrollBar(new MyScrollBar());
            
            mkdir.setIcon(Model.getInstance().getIc_add());
            mkdir.setBorder(BorderFactory.createTitledBorder(
                    BorderFactory.createLineBorder(Model.getInstance().getColor(8), 0),
                    "Make dir", TitledBorder.CENTER, TitledBorder.BELOW_BOTTOM, 
                    Model.getInstance().getFont(3), Color.white));
            mkdir.setBackground(Model.getInstance().getColor(3));
            mkdir.setFocusable(false);
            
            upload.setIcon(Model.getInstance().getIc_upload());
            upload.setBorder(BorderFactory.createTitledBorder(
                    BorderFactory.createLineBorder(Model.getInstance().getColor(8), 0),
                    "Upload", TitledBorder.CENTER, TitledBorder.BELOW_BOTTOM, 
                    Model.getInstance().getFont(3), Color.white));
            upload.setBackground(Model.getInstance().getColor(3));
            upload.setFocusable(false);
        }
        public void addEntry(ChannelSftp.LsEntry entry)
        {
            MyButton temp = new MyButton();
            
            files.add(entry);
            
            /*
            if (entry.getFilename().startsWith("."))
                hid_flag.add(true);
            else
                hid_flag.add(false);
            */
            
            labels.add(temp);
            container.add(temp);
            
            temp.setFocusable(false);
            
            if (entry.getAttrs().isDir())
            {
                temp.setIcon(Model.getInstance().getIc_directory());
                temp.setBorder(BorderFactory.createTitledBorder(
                    BorderFactory.createLineBorder(Model.getInstance().getColor(8), 0),
                    entry.getFilename(), TitledBorder.CENTER, TitledBorder.BELOW_BOTTOM, 
                    Model.getInstance().getFont(3), Color.white));
                temp.setBackground(Model.getInstance().getColor(1));
            }
            else
            {
                temp.setIcon(Model.getInstance().getIc_file());
                temp.setBorder(BorderFactory.createTitledBorder(
                    BorderFactory.createLineBorder(Model.getInstance().getColor(2), 0),
                    entry.getFilename(), TitledBorder.CENTER, TitledBorder.BELOW_BOTTOM, 
                    Model.getInstance().getFont(3), Color.white));
                temp.setBackground(Model.getInstance().getDefault_color());
            }
            
            if (entry.getFilename().equals(".."))
            {
                temp.setBackground(Model.getInstance().getColor(3));
                temp.setIcon(Model.getInstance().getIc_back());
                temp.setBorder(BorderFactory.createTitledBorder(
                    BorderFactory.createLineBorder(Model.getInstance().getColor(3), 0),
                    "Back", TitledBorder.CENTER, TitledBorder.BELOW_BOTTOM, 
                    Model.getInstance().getFont(3), Color.white));
            }
            
            container.repaint();
        }
        
        /**
         * Add blanks to container for better grid alignment
         */
        public void placeHolder()
        {
            container.add(mkdir);
            container.add(upload);
            
            while (container.getComponentCount() < 36)
            {
                container.add(new JLabel());
            }
        }

        
    }
    public class EntryPopupMenuView extends MyPopupMenu
    {
        private MyMenuItem remove, download;
        
        public EntryPopupMenuView()
        {
            init();
            toContentPane();
            misc();
        }
        private void init() 
        {
            remove = new MyMenuItem("Remove");
            download = new MyMenuItem("Download");
        }

        private void toContentPane() 
        {   
            
            addMenuItem(download);
            addSeparator();
            addMenuItem(remove);
        }
        private void misc() 
        {
            remove.setIcon(Model.getInstance().getIc_trash());
            remove.setBackground(Model.getInstance().getDefault_color());
            remove.setForeground(Color.white);
            remove.setFont(Model.getInstance().getFont(3));
            
            download.setIcon(Model.getInstance().getIc_download());
            download.setBackground(Model.getInstance().getDefault_color());
            download.setForeground(Color.white);
            download.setFont(Model.getInstance().getFont(3));
        }
        
    }
  
    public class MainPopupMenuView extends MyPopupMenu
    {
        private MyMenuItem upload, mkdir;
        
        public MainPopupMenuView()
        {
            init();
            toContentPane();
            misc();
        }
        private void init() 
        {
            upload = new MyMenuItem("Upload");
            mkdir = new MyMenuItem("Make directory");
        }

        private void toContentPane() 
        {
            addMenuItem(upload);
            addSeparator();
            addMenuItem(mkdir);
        }
        private void misc()
        {
            upload.setIcon(Model.getInstance().getIc_upload());
            upload.setBackground(Model.getInstance().getDefault_color());
            upload.setForeground(Color.white);
            upload.setFont(Model.getInstance().getFont(3));
            
            mkdir.setIcon(Model.getInstance().getIc_add());
            mkdir.setBackground(Model.getInstance().getDefault_color());
            mkdir.setForeground(Color.white);
            mkdir.setFont(Model.getInstance().getFont(3));
        }
        
    }
}
