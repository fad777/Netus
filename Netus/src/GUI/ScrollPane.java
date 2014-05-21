/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.Adjustable;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

/**
 *
 * @author fad777
 */
public class ScrollPane extends JPanel implements MouseListener{
    
    Container parent ;
    JScrollPane sp ;
    JLabel label;
    public ScrollPane(){
         super(true);
         label = new JLabel("go");
         setLayout(new BorderLayout());
         
         JScrollBar hbar = new JScrollBar(
                 JScrollBar.HORIZONTAL);
         JScrollBar vbar = new JScrollBar(
                 JScrollBar.VERTICAL);
         addMouseListener(this);
         
         hbar.setUnitIncrement(2);
         hbar.setBlockIncrement(1);
         hbar.addAdjustmentListener(new MyAdjustmentListener());
        MyAdjustmentListener listener = new MyAdjustmentListener(); 
        sp = new JScrollPane();
        sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        sp.getHorizontalScrollBar().addAdjustmentListener(listener);
        sp.getVerticalScrollBar().addAdjustmentListener(listener);
        sp.getViewport().setView(this);  
        sp.addComponentListener(new MyComponentListener());
        sp.setRequestFocusEnabled(true);
        sp.setFocusable(true);
        Dimension d =new Dimension(800,600);
        this.setPreferredSize(d);
         //this.add(sp);
         //sp.getViewport().setBackground(Color.white);
        // vbar.setUnitIncrement(2);
        // vbar.setBlockIncrement(1);
        // vbar.addAdjustmentListener(new MyAdjustmentListener());
         
         
        // add(hbar,BorderLayout.SOUTH);
        // add(vbar,BorderLayout.EAST);
        // parent = this.getParent();
        this.setBackground(Color.white);
         
    }

    public void mouseClicked(MouseEvent e) {
       return;
    }

    public void mousePressed(MouseEvent e) {
        sp.requestFocusInWindow();
    }

    public void mouseReleased(MouseEvent e) {
        return;
    }

    public void mouseEntered(MouseEvent e) {
       return;
    }

    public void mouseExited(MouseEvent e) {
       return;
    }
    
     class MyComponentListener implements ComponentListener{
        
         @Override
        public void componentResized(ComponentEvent e) {
           ScrollPane.this.setSize(new Dimension(sp.getWidth()-20, sp.getHeight()-20));
        }

        public void componentMoved(ComponentEvent e) {
            
        }

        public void componentShown(ComponentEvent e) {
           
        }

        public void componentHidden(ComponentEvent e) {
            
        }
         
     }
     
     
     class MyAdjustmentListener implements AdjustmentListener {
    // This method is called whenever the value of a scrollbar is changed,
    // either by the user or programmatically.
    public void adjustmentValueChanged(AdjustmentEvent evt) {
       //  repaint();
        Adjustable source = evt.getAdjustable();

        // getValueIsAdjusting() returns true if the user is currently
        // dragging the scrollbar's knob and has not picked a final value
        if (evt.getValueIsAdjusting()) {
            // The user is dragging the knob
            return;
        }

        // Determine which scrollbar fired the event
        int orient = source.getOrientation();
        if (orient == Adjustable.HORIZONTAL) {
            // Event from horizontal scrollbar
        } else {
            // Event from vertical scrollbar
        }

        // Determine the type of event
        int type = evt.getAdjustmentType();
        switch (type) {
          case AdjustmentEvent.UNIT_INCREMENT:
              // Scrollbar was increased by one unit
              break;
          case AdjustmentEvent.UNIT_DECREMENT:
              // Scrollbar was decreased by one unit
              break;
          case AdjustmentEvent.BLOCK_INCREMENT:
              // Scrollbar was increased by one block
              break;
          case AdjustmentEvent.BLOCK_DECREMENT:
              // Scrollbar was decreased by one block
              break;
          case AdjustmentEvent.TRACK:
              // The knob on the scrollbar was dragged
              break;
        }

        // Get current value
        int value = evt.getValue();
       // System.err.println("Moved by " + value);
    }
}
     
     
   public JScrollPane getParentContainer(){
        
        return sp;
    }
}
