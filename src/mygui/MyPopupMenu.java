/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mygui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPopupMenu;

/**
 *
 * @author xiaoerge
 */
public class MyPopupMenu extends JPopupMenu
{   
    private List<MyMenuItem> list;
    
    public MyPopupMenu()
    {
        super();
        
        list = new ArrayList<>();
        
        setBackground(Color.white);
    }
    public void addMenuItem(MyMenuItem item)
    {
        list.add(item);
        this.add(item);
    }
    public MyMenuItem getMenuItem(String text_on_menu)
    {
        for (MyMenuItem m : list)
        {
            if (m.getText().equals(text_on_menu))
                return m;
        }
        return null;
    }
}
