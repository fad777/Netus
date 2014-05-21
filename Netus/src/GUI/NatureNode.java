/*
 NatureNode.java
 */

package GUI;

import java.io.*;
import javax.swing.JPanel;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class NatureNode extends ShapeNode { 
    //JPopupMenu popup;
    private static final long serialVersionUID =-8175308503074311829L;
    protected static int nodeCounter;
    protected int nodeId=0;
    public static final Point2D anchorPoint = new Point2D.Double(100,45);
    //private javax.swing.JPopupMenu popup;
    public NatureNode()
    {
        super();
       ++nodeCounter;
       this.nodeId=nodeCounter;
       //popup= new JPopupMenu();
    }

    public NatureNode(InfluenceGUI root,Node node)
    {
        super(root,node);
       ++nodeCounter;
       this.nodeId=nodeCounter;
       this.childId=this.nodeId;
       this.width=100;
       this.height=100;
        //java.util.Random rnd=new java.util.Random();
         this.setXY(100, 100);
       // this.setBackground(Color.WHITE);
         this.setOpaque(false);
        this.setLayout(new FlowLayout());
        this.setBounds(x,y,width,height);
        this.setVisible(true);
        popup= new JPopupMenu(); //Creating Popup menu
        //popup.setLightWeightPopupEnabled(false);
      
        popup.add("Properties").addActionListener(this);
        popup.add("CPTable").addActionListener(this);
        popup.add("Import CPT").addActionListener(this);
        popup.add("Delete").addActionListener(this);
       // add(popup);
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
        return theName;
    }
      @Override
    public void Clear(){
        nodeCounter=0;
    }
       @Override
      public int getNodeId(){
        return this.nodeId;
    }
    @Override
    public void actionPerformed(ActionEvent ae){
        String command = ae.getActionCommand();
        if (command.equals("Properties")) {
            
            NodeProperty np=new NodeProperty(this, true);
            np.setModal(true);
            np.setVisible(true);
            
         }
         else if (command.equals("CPTable")) {
            CPTable cp=new CPTable(this, true);
            cp.setModal(true);
            cp.setVisible(true);
         }
        else if (command.equals("Enter Finding")) {
            FindingProperty fp=new FindingProperty(this, true);
            fp.setVisible(true);
         }
        else if (command.equals("Import CPT")) {
            this.node.importCPT(this);
             CPTable cp=new CPTable(this, true);
             cp.setModal(true);
             cp.setVisible(true);
             
         }
        else if (command.equals("Delete")){
             
             rootpane.myContentPane.remove((ShapeNode)this);
             rootpane.myContentPane.getParentContainer().repaint();
             rootpane.nodes.remove(this);
         }
    }
    
      public void setNodeId(int id){
        this.nodeId=id;
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
               
                requestFocusInWindow();
                this.popup.show(this,xx,yy);
                
              //  System.out.print("menu");
         }
         else
         {
            // System.out.println("pressing "+this.x+","+this.y);
             odragged=true;
             prevDragX = xx;
             prevDragY = yy;
          
           

         }
       
              
           this.requestFocusInWindow();
              
     }
@Override
public void mouseReleased(MouseEvent evt) {
     this.requestFocusInWindow();           
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
       
            if(this.x >rootpane.myContentPane.getWidth() || (this.x + this.width) > rootpane.myContentPane.getWidth()){
                
                hortz +=this.width;
                outOfBounds =true;
                
            }
            
            if(this.y >rootpane.myContentPane.getHeight() || (this.y + this.getHeight()) > rootpane.myContentPane.getHeight()){
                
                vertz += this.height;
                outOfBounds =true;
                
            }
            
            if(outOfBounds){
                rootpane.myContentPane.setPreferredSize(new Dimension(hortz,vertz));
                rootpane.revalidate();
               // rootpane.repaint();
            }
           
               //rootpane.myContentPane.remove(this);
               //rootpane.nodes.remove(this);
            }

         
        
     
         rootpane.myContentPane.repaint();
         odragged=false;
         
         this.requestFocusInWindow();

         }

public void mouseDragged(MouseEvent evt) {
          if(!odragged)
              return;
         odragged= true;
         int x = evt.getX();
         int y = evt.getY();
         this.moveBy(x - prevDragX, y - prevDragY);
         
         
        
         
        // System.out.println("draging "+this.x+","+this.y);
        // rootpane.update(rootpane.getGraphics());
        // this.setXY(x, y);
         prevDragX = x;
         prevDragY = y;
         this.rootpane.myContentPane.repaint(x,y,width,height);
         
        // System.out.println("The dragged xpoint is " + x);
         

      }

@Override
 public void focusGained(FocusEvent e) {
        
        this.borderThickness=3;
        
       // System.out.println("gained");
       // System.out.println(this.rootpane.getFocusOwner());
 }
     @Override
    public void focusLost(FocusEvent e) {
        
        this.borderThickness=1;
         // System.out.println(this.rootpane.getFocusOwner()+ "Lost focus");
        // this.repaint();
       //  this.borderThickness=1;
         
 }


@Override
    protected void paintComponent(Graphics g) {
      
    
            super.paintComponents(g);
            
            // We need Graphics2D object so cast g to 2D object
            Graphics2D g2D = (Graphics2D)g;
            g2D.setStroke(new BasicStroke(this.borderThickness));
            RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
            rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2D.setRenderingHints(rh);
            
            this.setBounds((int)this.x,this.y,width,height);
           // Line2D line = new Line2D.Double(15,35, width-5, 35);
            Ellipse2D myshape = new Ellipse2D.Double(10, 10, width-15, height-25);
            
            g2D.setColor(Color.yellow);
            g2D.fill(myshape);
            g2D.setColor(Color.BLACK);
            
           
            g2D.draw(myshape);
            
            //Double d=0.0;
            g2D.setColor(Color.black);
            //g2D.drawString(this.getFullName(), 25, 45);
            g2D.drawString(this.getFullName(), width/3 +10, height/3 + 15);
            //this.setAnchorPoint(getAnchorPoint(myshape, 0));
            repaint(x,y,width,height);
            this.rootpane.myContentPane.repaint(x,y,width,height);
            
    }

 
    

    
    
}
