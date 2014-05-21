/*
    UtilityNode.java
 */

package GUI;

import java.io.*;
import javax.swing.JPanel;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
//import java.awt.geom.Rectangle2D;

public class UtilityNode extends ShapeNode //javax.swing.JPanel implements MouseListener,MouseMotionListener,ActionListener{
{
    //JPopupMenu popup;
    private static final long serialVersionUID =1981640814374465948L;
    String[][] utils;
    protected static int nodeCounter;
    protected int nodeId=0;
   // protected String fullname="";
    public static final Point2D anchorPoint = new Point2D.Double(100,50);
    
    
    
    
    // Constructor for externalization use
    public UtilityNode()
    {
         super();
       ++nodeCounter;
       this.nodeId=nodeCounter;
    }
 
    public void setNodeId(int id){
        this.nodeId=id;
    }
    public void setNodeName(String name){
        //this.node.name=name;
        this.node.name=name;
    }
     @Override
      public int getNodeId(){
        return this.nodeId;
    }
     
    public UtilityNode(InfluenceGUI root,Node node)
    {
        super(root,node);
       ++nodeCounter;
       this.nodeId=nodeCounter;
       this.childId=this.nodeId;
       this.width=100;
       this.height=100;
        this.setXY(100, 100);
        this.setBackground(Color.WHITE);
        this.setLayout(new FlowLayout());
        this.setBounds(x,y,width,height);
        this.setVisible(true);
       // popup= new JPopupMenu(); //Creating Popup menu
        popup.add("Properties").addActionListener(this);
        popup.add("Utility Table").addActionListener(this);
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
 public void focusGained(FocusEvent e) {
        
        this.borderThickness=3;
        
       // System.out.println("gained");
        //System.out.println(this.rootpane.getFocusOwner());
 }
     @Override
    public void focusLost(FocusEvent e) {
        
        this.borderThickness=1;
         // System.out.println(this.rootpane.getFocusOwner()+ "Lost focus");
         
       //  this.borderThickness=1;
         
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
    @Override
    public void actionPerformed(ActionEvent ae)
    {
        String command = ae.getActionCommand();
        if (command.equals("Properties")) {

            UtilityNodeProperty np=new UtilityNodeProperty(this, true);
            np.setVisible(true);

         }
         else if (command.equals("Utility Table")) {
            CPTable cp=new CPTable(this, true);
            cp.setVisible(true);
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

            Graphics2D g2D = (Graphics2D)g;
            g2D.setStroke(new BasicStroke(this.borderThickness));
            RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
            rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2D.setRenderingHints(rh);
            
            Line2D line = new Line2D.Double(10,35, width-5, 35);
            
            this.setBounds(this.x,this.y,width,height);
            g2D.setColor(Color.GREEN);
            
            int[] xp={0,width/2,width,width/2};
            int[] yp={width/2,0,width/2,width};
            
            Polygon myshape = new Polygon(xp,yp,4);
            g2D.fillPolygon(myshape);
            
            g2D.setColor(Color.black);
            g2D.drawPolygon(myshape);
            
            g2D.drawString(this.getFullName(), width/3 +10, height/3 + 15);
           // g2D.drawLine(15,35, width-15, 35);
           // g2D.drawString("States:", 20, 50);
           /* if(node!=null && node.states!=null && node.CPT!=null)
            for(int i=0;i<this.node.states.length;i++)
            {

               g2D.drawString(this.node.states[i]+" "+this.node.statevalues[i], 25, 65+(i*15));
            }*/
           // repaint(x,y,width,height);
            //repaint(x,y,width,height);
            this.rootpane.getContentPane().repaint(x,y,width,height);
            
           


    }
     

}
