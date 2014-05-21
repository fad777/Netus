/*
DecisionNode.java
 */

package GUI;

import java.io.*;
import javax.swing.JPanel;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class DecisionNode extends ShapeNode implements MouseListener,
                                            MouseMotionListener,
                                            ActionListener{
    
    private static final long serialVersionUID = 6107171385760099181L;

    Double[] utils;
    
    private static int nodeCounter;
    private int nodeId=0;
    public static final Point2D anchorPoint = new Point2D.Double(95,40);
    private BufferedImage bi ;
    //Graphics2D graph;
    
    public DecisionNode()
    {
        super();
     //  ++nodeCounter;
       this.nodeId=nodeCounter;
    }
    
    @Override
    public void Clear(){
        nodeCounter=0;
    }

    public DecisionNode(InfluenceGUI root,Node node)
    {
        super(root,node);
        this.setXY(100,100);
        //this.setBackground(Color.WHITE);
        this.width=100;
        this.height=100;
       this.setLayout(new FlowLayout());
       this.setOpaque(false);
       this.setBounds(x,y,width,height);
       this.setVisible(true);
        bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        ++nodeCounter;
        this.nodeId=nodeCounter;
        this.childId=this.nodeId;
        
        popup= new JPopupMenu(); //Creating Popup menu
        popup.add("Properties").addActionListener(this);
        popup.add("CPTable").addActionListener(this);
        popup.add("Import CPT").addActionListener(this);
       // popup.add("Get Utils").addActionListener(this);
        popup.add("Delete").addActionListener(this);
        //add(popup);
        addMouseListener(this);
        addMouseMotionListener(this);
        addFocusListener(this);
        setFocusable(true);
    }
    @Override
    public String getFullName(){
        
           if(!this.fullname.isEmpty()){
            return this.fullname;
        }
        String theName = this.node.name + this.nodeId;
        this.node.containerName=theName;
        //lblName=theName;
        return theName;
    }
      public void setNodeId(int id){
        this.nodeId=id;
    }
     @Override
    public int getNodeId(){
        return this.nodeId;
    }
    
   public void setNodeName(String name){
        this.node.name=name;
    }
@Override
public void mousePressed(MouseEvent evt) {

this.requestFocusInWindow();
odragged=false;
/*if(!odragged)
{
    return;
}*/
        
        
         int xx = evt.getX();  // x-coordinate of point where mouse was clicked
         int yy = evt.getY();  // y-coordinate of point
         pressed=true;
         
         if (evt.isPopupTrigger()) {
               
               
                this.popup.show(this,xx,yy);
         }
         else
         {
            // System.out.println("pressing "+this.x+","+this.y);
             odragged=true;
             prevDragX = xx;
             prevDragY = yy;
          
           

         }
       
              
           
              
      }


@Override
public void mouseDragged(MouseEvent evt) {
          if(!odragged)
              return;
         odragged= true;
         int x = evt.getX();
         int y = evt.getY();
         this.moveBy(x - prevDragX, y - prevDragY);
         //this.moveBy(x, y);
         
        // System.out.println("draging "+this.x+","+this.y);
        // rootpane.update(rootpane.getGraphics());
        // this.setXY(x, y);
         prevDragX = x;
         prevDragY = y;
         rootpane.myContentPane.repaint(x,y,width,height);
         
        // System.out.println("The dragged xpoint is " + x);
         

      }

 
@Override
public void mouseReleased(MouseEvent evt) {
         // this.requestFocusInWindow();      
         //pressed=false;
            if(!odragged)
              return;
         int x = evt.getX();
         int y = evt.getY();
         int hortz=this.rootpane.myContentPane.getWidth();
         int vertz =this.rootpane.myContentPane.getHeight();
         boolean outOfBounds =false;
          if (evt.isPopupTrigger()) {
                
                popup.show(this,x-10,y-2);
         }
         else {

            this.moveBy(x - prevDragX, y - prevDragY);
       
            if((this.x >rootpane.myContentPane.getWidth()) || ((this.x + this.width) > rootpane.myContentPane.getWidth())){
                
                hortz +=  this.width;
                outOfBounds =true;
                
            }
            
            if((this.y >rootpane.myContentPane.getHeight()) || ((this.y + this.getHeight()) > rootpane.myContentPane.getHeight())){
                
                vertz +=this.height;
                outOfBounds =true;
                
            }
            
            if(outOfBounds){
                rootpane.myContentPane.setPreferredSize(new Dimension(hortz,vertz));
                rootpane.revalidate();
               
               // rootpane.repaint();
            }
                
                // rootpane.repaint();
               //rootpane.myContentPane.remove(this);
               //rootpane.nodes.remove(this);
            }

         
        
     
         rootpane.myContentPane.repaint();
         odragged=false;
         
          this.requestFocusInWindow();

         }
@Override
 public void focusGained(FocusEvent e) {
        
        this.borderThickness=3;
        
       // System.out.println("gained");
        //System.out.println(this.rootpane.getFocusOwner() + ":" + this.borderThickness);
 }
     @Override
    public void focusLost(FocusEvent e) {
        
        this.borderThickness=1;
          //System.out.println(this.rootpane.getFocusOwner()+ "Lost focus");
         //this.repaint();
       //  this.borderThickness=1;
         
 }

    @Override
    public void actionPerformed(ActionEvent ae)
    {
        String command = ae.getActionCommand();
        if (command.equals("Properties")) {

            NodeProperty np=new NodeProperty(this, true);
            np.setLocationRelativeTo(this);
            np.setVisible(true);

         }
         else if (command.equals("CPTable")) {
            CPTable cp=new CPTable(this, true);
            cp.setVisible(true);
         }
        else if (command.equals("Enter Finding")) {
            FindingProperty fp=new FindingProperty(this, true);
            fp.setVisible(true);
         }
        else if (command.equals("Import CPT")) {
            this.node.importCPT(this);
         }
        else if (command.equals("Delete")){
             
             rootpane.myContentPane.remove((ShapeNode)this);
             rootpane.myContentPane.repaint();
             rootpane.nodes.remove(this);
         }
    }



   @Override
   protected void paintComponent(Graphics g)
    {
            
             // clear screen bitmap off
           
           super.paintComponent(g);
          
           Point p = new Point(1,1);
           //SwingUtilities.convertPointFromScreen(p, this);
           bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
         //  bi = (BufferedImage)createImage(width,height);
           
           this.setBounds(this.x,this.y,width,height);
             // We need Graphics2D object so cast g to 2D object
            Graphics2D g2D = (Graphics2D)g;
            g2D.setStroke(new BasicStroke(this.borderThickness));
            RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
            rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2D.setRenderingHints(rh);
            
           // Line2D line = new Line2D.Double(15,35, width-5, 35);
            int newHeight = height-50;
            Rectangle2D myshape = new  Rectangle2D.Double(5, 25, width-10, newHeight);
            //System.out.println(p.getX() + "," + p.getY());
            g2D.setColor(Color.pink);
            g2D.fill(myshape);
            g2D.setColor(Color.BLACK);
            g2D.draw(myshape);
            g2D.setColor(Color.BLACK);
            g2D.drawString(this.getFullName(), width/2, newHeight);
           // g2D.drawImage(bi, 0, 0, this);
            
            //g2D.draw(line);
           // g2D.drawString("States:", 20, 50);
            /*
            if(node!=null && node.states!=null)
            for(int i=0;i<this.node.states.length;i++)
            {
            g2D.drawString(this.node.states[i]+" "+this.node.statevalues[i], 25, 65+(i*15));
            }*/
           // AffineTransform af = g2D.getTransform();
          //  af.translate(100, 100);
           // g2D.setTransform(af);
           // repaint();
            
            //repaint(x,y,width,height);
          //  System.out.println(this.getBounds());
            repaint(x,y,width,height);
            this.rootpane.myContentPane.repaint(x,y,width,height);
            
          
    } 


}
