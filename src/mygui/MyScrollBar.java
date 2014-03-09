/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygui;

/**
 *
 * @author xiaoerge
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.UIManager;
import javax.swing.plaf.ScrollBarUI;
import javax.swing.plaf.basic.BasicScrollBarUI;
import fileui.Model;

public class MyScrollBar extends JScrollBar {

    public MyScrollBar() {
        super();

        UIManager.put("ScrollBarUI", "mygui.MyScrollBarUI");
        
        setUI(new MyScrollBarUI());
    }
}
