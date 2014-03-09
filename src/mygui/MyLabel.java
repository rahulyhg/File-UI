/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.border.TitledBorder;
import fileui.Model;

/**
 *
 * @author xiaoerge
 */
public class MyLabel extends JComponent {

    private JLabel label;
    private int x0, y0, myx, myy;

    public MyLabel(String text) {
        super();
        label = new JLabel(text);
        
        setLayout(new GridLayout());
        add(label);
        
        this.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                x0 = e.getXOnScreen();
                y0 = e.getYOnScreen();

                myx = getX();
                myy = getY();
            }
            
        });
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);

                //Component com = (Component) e.getSource();

                int dx = e.getXOnScreen() - x0;
                int dy = e.getYOnScreen() - y0;
                
                
                //if (getX() < getParent().getWidth())
                    setLocation(myx + dx, myy + dy);
            }
        });
    }
    
    public void setIcon(Icon icon)
    {
        label.setIcon(icon);
    }
    public void setText(String text)
    {
        label.setText(text);
    }
    public String getText()
    {
        return label.getText();
    }
}
