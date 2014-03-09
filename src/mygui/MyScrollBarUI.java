/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mygui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicScrollBarUI;
import fileui.Model;

/**
 *
 * @author xiaoerge
 */
public class MyScrollBarUI extends BasicScrollBarUI {

        @Override
        protected JButton createDecreaseButton(int orientation) {
            MyButton up = new MyButton();
            
            up.setBackground(Model.getInstance().getDefault_color());
            up.setIcon(Model.getInstance().getIc_up());
            up.setBorder(BorderFactory.createLineBorder(Color.white));
            
            return up;
        }

        @Override
        protected JButton createIncreaseButton(int orientation) {
            MyButton down = new MyButton();
            
            down.setBackground(Model.getInstance().getDefault_color());
            down.setIcon(Model.getInstance().getIc_down());
            down.setBorder(BorderFactory.createLineBorder(Color.white));
            
            return down;
        }

        @Override
        protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
            g.setColor(Color.white);
            g.fillRect(0, 0, trackBounds.width, trackBounds.height*10);
            //super.paintTrack(g, c, trackBounds);
        }

        @Override
        protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
            g.setColor(Model.getInstance().getDefault_color());
            g.fillRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height);
            
            //super.paintThumb(g, c, thumbBounds);
        }

        @Override
        protected void setThumbBounds(int x, int y, int width, int height) {
            super.setThumbBounds(x, y, 30, 100);
        }

        @Override
        protected Rectangle getThumbBounds() {
            return new Rectangle(super.getThumbBounds().x, super.getThumbBounds().y, 30, 100);
        }

        @Override
        protected Dimension getMinimumThumbSize() {
            return new Dimension(30, 20);
        }

        @Override
        protected Dimension getMaximumThumbSize() {
            return new Dimension(30, 100);
        }
    }
