/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Util;

import java.awt.Component;
import java.awt.Point;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;

/**
 *
 * @author fad777
 */
public  class FrameDecorator  {
    
    // This is a non-instantiable class
    // This effect achieved by making way of the private constructor
    
    private FrameDecorator(){
        throw new AssertionError();
    }
    
    public static void CenterFrame(JFrame frame, Component c){
        
        Dimension dm = Toolkit.getDefaultToolkit().getScreenSize();
        Point pt = new Point((int)((dm.getWidth()-c.getWidth())/2),
                             (int)((dm.getHeight()-c.getHeight())/2));
        
        frame.setLocation(pt);
    }
         
    
}
