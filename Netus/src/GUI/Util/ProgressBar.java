/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Util;

/**
 *
 * @author fad777
 */
import java.awt.Color;
import java.awt.Container;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

public class ProgressBar extends JPanel {

  JProgressBar pbar;

  static final int MY_MINIMUM = 0;

  static final int MY_MAXIMUM = 100;
  int value=10;

  public ProgressBar() {
    pbar = new JProgressBar();
    pbar.setMinimum(MY_MINIMUM);
    pbar.setMaximum(MY_MAXIMUM);
    pbar.setForeground(Color.CYAN);
    
    add(pbar);
  }

  public void updateBar(int newValue) {
    if(pbar.getValue() >= 98){
        System.exit(0);
    }
    pbar.setValue(newValue);
  }
  public int getIncrement(){
      
      return value;
  }
  public void setIncrement(int percent){
      
      value=percent;
  }
  public static void main(String args[]) {

    final ProgressBar it = new ProgressBar();
    

    JFrame frame = new JFrame("Process Status");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setContentPane(it);
    frame.setUndecorated(true);
    frame.pack();
    //FrameDecorator.CenterFrame(frame, it);
    frame.setVisible(true);
    

   
    for (int i = MY_MINIMUM; i <= MY_MAXIMUM; i++) {
      final int percent = i;
      try {
        SwingUtilities.invokeLater(new Runnable() {
          public void run() {
            it.updateBar(percent);
          }
        });
        java.lang.Thread.sleep(100);
      } catch (InterruptedException e) {
        ;
      }
    }
  }
}