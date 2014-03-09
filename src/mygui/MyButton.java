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
import java.awt.Graphics;
import javax.swing.JButton;
import fileui.Model;

public class MyButton extends JButton 
{   
    public MyButton()
    {
        super();
        setContentAreaFilled(false);
    }
    public MyButton(String text)
    {
        this();
        setText(text);
    }

    @Override
    public void paintComponent(Graphics g) 
    {
        if (getModel().isPressed()) 
        {
            g.setColor(getBackground().darker().darker());
        } 
        else if (getModel().isRollover()) 
        {
            g.setColor(getBackground().darker());
        } 
        else 
        {
            g.setColor(getBackground());
        }
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }
}
