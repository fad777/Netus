/*
 LinkNode.java
 */

package GUI;

import java.io.*;
import javax.swing.JPanel;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.geom.*;


public class ModelLinkNode2 extends ShapeNode //javax.swing.JPanel implements MouseListener,MouseMotionListener,ActionListener{
{
    //JPopupMenu popup;
    InfluenceGUI rootpane;
    ShapeNode parent,child;
    int x1,x2,y1,y2;
    
    protected static int nodeCounter;
    protected int nodeId=0;
    private javax.swing.JPopupMenu popup;
  
    public ModelLinkNode2()
    {
        super();
       ++nodeCounter;
       this.nodeId=nodeCounter;
    }
    @Override
    public void Clear(){
        nodeCounter=0;
    }
    public ModelLinkNode2(InfluenceGUI rootp,Node node)
    {
        super(rootp,node);
       ++nodeCounter;
       this.nodeId=nodeCounter;
        this.childId=this.nodeId;
       
        rootpane=rootp;
        this.setXY(100, 100);
        this.setBackground(Color.WHITE);
        this.setLayout(new FlowLayout());
        this.setBounds(x,y,width,height);
        this.node.name="ModelLink";
        this.setVisible(true);
        popup= new JPopupMenu(); //Creating Popup menu
        popup.add("Properties").addActionListener(this);
        popup.add("Reverse Link").addActionListener(this);
        popup.add("Delete").addActionListener(this);
        add(popup);
        addMouseListener(this);
        addMouseMotionListener(this);
    }
public ModelLinkNode2(ShapeNode pnode,ShapeNode cnode)
    {
       // super(rootpane,cnode.node);
        cnode.node.addLink(pnode.node);
        parent=pnode;
        child=cnode;
        this.setXY(100, 100);
        this.setBackground(Color.WHITE);
        this.setLayout(new FlowLayout());
        this.setBounds(x,y,width,height);
        this.node.name="ModelLink";
        this.setVisible(true);
        popup= new JPopupMenu(); //Creating Popup menu
        popup.add("Properties").addActionListener(this);
        popup.add("Reverse Link").addActionListener(this);
        popup.add("Delete").addActionListener(this);
        add(popup);
        addMouseListener(this);
        addMouseMotionListener(this);
    }
  public void setNodeId(int id){
        this.nodeId=id;
    }

@Override
    public String getFullName(){
        
        return this.node.name  + "_" + this.nodeId;
    }

public void makeLink(ShapeNode pnode,ShapeNode cnode)
{
 if(pnode!=null && cnode !=null)
 {
    cnode.node.addLink(pnode.node);
    rootpane.myContentPane.repaint();

 }
    parent=pnode;
    child=cnode;
}
boolean reversed=false;
@Override
 public void mousePressed(MouseEvent evt) {


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
 public void mouseReleased(MouseEvent evt) {
                
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
                
                hortz += this.width;
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

         
        
     
         rootpane.repaint();
         odragged=false;
         
         

         }
    @Override
    public void actionPerformed(ActionEvent ae)
    {
        String command = ae.getActionCommand();
        if (command.equals("Properties")) {

            //ModelLinkNodeProperty np=new ModelLinkNodeProperty(this,rootpane, true);
           // np.setVisible(true);

         }
         else if (command.equals("Reverse Link")) {
            if(reversed==true)reversed=false;else reversed=true;
            System.out.println("reversed");
            makeLink(child, parent);
            repaint();
            rootpane.repaint();
         }
        else if (command.equals("Delete")){
             
             rootpane.myContentPane.remove((ShapeNode)this);
             rootpane.myContentPane.repaint();
         }
    }

 @Override
    protected void paintComponent(Graphics g)
    {

        
        // clear screen bitmap off
        super.paintComponent(g);
        // We need Graphics2D object so cast g to 2D object
        Graphics2D g2D = (Graphics2D)g;
        final  float dash[] = {7.0f};
        final  BasicStroke dashed= new BasicStroke(2.0f, //Width of stroke
                BasicStroke.CAP_BUTT,   // End cap style
                BasicStroke.JOIN_MITER, // Join style
                10.0f,                  // Miter limit
                dash,                   //Dash pattern
                0.0f);                  //Dash phase
        g2D.setStroke(dashed);
        
        //create the line object
       
       // this.setBounds(rootpane.getBounds());
        if(parent!=null && child!=null)
        {
   
        this.x1=(parent.x+parent.width)/2;
        this.x2=child.x+(child.width/2);
        this.y1=(parent.y+parent.height)/2;
        this.y2=child.y+(child.height/2);
        width=rootpane.myContentPane.getWidth();
        height=rootpane.myContentPane.getHeight();
        this.setBounds(Math.min(x1, x2), Math.min(y1, y2), width, height);
     


                 if(width<=0){
                     width=2;
                 }

                 if(height<=0){
                     height=2;
                 }

       // rootpane.repaint();

     }
        
         
         Point2D startPt = new Point2D.Double(Math.min(x1, x2),Math.min(y1, y2));
         Point2D endPt= new Point2D.Double(width, height);
         
      
       // g.drawLine(Math.min(x1, x2), Math.min(y1, y2), width, height);
        
      
         drawArrowLine(g2D,startPt,endPt);
            if(reversed){
                Line2D line1 = new Line2D.Double(0, 0,20,10);
                Line2D line2 = new Line2D.Double(0,0,10,20);
                g2D.draw(line1);
                g2D.draw(line2);
            }
            else
            {
              //  Line2D line1 = new Line2D.Double(width-10, height-20,width,height);
               // Line2D line2 = new Line2D.Double(width-20, height-10,width,height);
               // g2D.draw(line1);
               // g2D.draw(line2);
            
            }
    }
 
 public void drawArrowLine(Graphics2D g2, Point2D startPoint, Point2D endPoint){
       
        Point2D startPt,endPt;
        
        
            startPt= endPoint;
            endPt=startPoint;
       
        
        double dy = startPt.getY() - endPt.getY();
        double dx = startPt.getX() - endPt.getX();
        double phi = Math.toRadians(30);
        double hypotenus = 20;
        double theta = Math.atan2(dy, dx);
        double x, y, alpha;
        
        Line2D.Double line = new Line2D.Double(startPt,endPt);
        alpha = theta + phi;
        g2.draw(line);
        
        // Here is the dojo
        for(int j = 0; j < 2; j++)
        {
            x = startPt.getX() - hypotenus * Math.cos(alpha);
            y = startPt.getY() - hypotenus * Math.sin(alpha);
            g2.draw(new Line2D.Double(startPt.getX(), startPt.getY(), x, y));
            alpha = theta - phi;
        }
        
        g2.getTransform().setToIdentity();
        g2.drawString(this.getFullName(), (int)startPt.getX()-100, (int)startPt.getY()-50);
        
        g2.dispose();
        
    }

}


