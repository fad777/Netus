/*
 LinkNode.java
 */

package GUI;

import GUI.Util.BoundsValidator;
import GUI.Util.FrameDecorator;
import java.io.*;
import javax.swing.JPanel;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.ArrayList;


public class ModelLinkNode extends ShapeNode //javax.swing.JPanel implements MouseListener,MouseMotionListener,ActionListener{
{
    //JPopupMenu popup;
    InfluenceGUI rootpane;
    ShapeNode parent,child;
    double x1,x2,y1,y2;
    
    
    
    protected static int nodeCounter;
    protected int nodeId=0;
    // private javax.swing.JPopupMenu popup;
    
    public ModelLinkNode()
    {
        super();
        
      // ++nodeCounter;
      // this.nodeId=nodeCounter;
    }
    
   
     
    public ModelLinkNode(InfluenceGUI rootp,Node node)
    {
        super(rootp,node);
        
       this.x1=0;
       this.x2 =100;
       this.y1=0;
       this.y2 =100;
       ++nodeCounter;
       this.nodeId=nodeCounter;
       this.childId=this.nodeId;
      
        rootpane=rootp;
        this.setXY(100,100);
        //this.x2=100;
       // this.y2=100;
       // this.setBorder(BorderFactory.createEtchedBorder(new Integer(1)));
        this.setBackground(Color.WHITE);
       // this.setLayout(new FlowLayout());
       // this.setOpaque(false);
        this.setBounds(x,y,width,height);
        this.node.name="ModelLink";
        this.setVisible(true);
        popup= new JPopupMenu(); //Creating Popup menu
        popup.add("Properties").addActionListener(this);
        popup.add("Reverse Link").addActionListener(this);
        popup.add("Delete").addActionListener(this);
        //add(popup);
        addMouseListener(this);
        addMouseMotionListener(this);
        addFocusListener(this);
        setFocusable(true);
    }
public ModelLinkNode(InfluenceGUI rootp,ShapeNode pnode,ShapeNode cnode,Node node)
    {
        super(rootp,node);
        
       ++nodeCounter;
       this.nodeId=nodeCounter;
        
        rootpane = rootp;
        cnode.node.addLink(pnode.node);
        parent=pnode;
        child=cnode;
       // this.setXY(0, 0);
       // this.setBackground(Color.WHITE);
       // this.setOpaque(false);
        
      
       // this.setLayout(new FlowLayout());
        //this.setBounds(x,y,width,height);
        this.node.name="ModelLink";
       // this.setVisible(true);
        popup= new JPopupMenu(); //Creating Popup menu
        popup.add("Properties").addActionListener(this);
        popup.add("Reverse Link").addActionListener(this);
        popup.add("Delete").addActionListener(this);
        //add(popup);
        addMouseListener(this);
        addMouseMotionListener(this);
    }
  public void setNodeId(int id){
        this.nodeId=id;
    }
  public void setNodeName(String name){
        this.node.name=name;
   }
  
public void makeLink(ShapeNode pnode,ShapeNode cnode)
{
 
    if(pnode!=null && cnode !=null){
        cnode.node.addLink(pnode.node);
        //this.repaint();
        rootpane.myContentPane.repaint(x,y,width,height);

    }
     // sets the parent and the child of the linknode
        this.parent=pnode;
        this.child=cnode;
}

@Override
     public String getFullName(){
             if(!this.fullname.isEmpty()){
            return this.fullname;
        }
             
        String theName = this.node.name  + this.nodeId;
        this.node.containerName=theName;
        return theName;
    }
    @Override
    public void Clear(){
        nodeCounter=0;
    }
boolean reversed=false;
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
                
                vertz +=this.height;
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

         
        
     
         //rootpane.repaint();
         odragged=false;
         this.requestFocusInWindow();
         
         

         }
    @Override
    public void actionPerformed(ActionEvent ae)
    {
                String command = ae.getActionCommand();
                if (command.equals("Properties")) {

                ModelLinkNodeProperty np=new ModelLinkNodeProperty(this,rootpane, true);
                //FrameDecorator.CenterFrame(rootpane, np);
                np.setVisible(true);
                rootpane.myContentPane.repaint();

         }
        else if (command.equals("Reverse Link")) {
             try{
                if(reversed==true)reversed=false;else reversed=true;
                System.out.println("reversed");
                 if(this.child != null && this.child.node.allparents !=null){
                    this.child.node.allparents.remove(this.parent.node);
                    this.child.node.updateRepeatStates(1.0/(double)(this.parent.node.getNumstates()));
                    this.child.node.repeatStates.remove(this.child.node.repeatStates.size()-1);
                    this.child.node.repeatStates.trimToSize();
                    this.parent.node.children.remove(this.child.node);
                }
                this.node.linkAdded=false;
                makeLink(this.child, this.parent);
                rootpane.myContentPane.repaint();
             }catch(Exception ex){
                 ex.printStackTrace();
             }
         }
        else if (command.equals("Delete")){
               
                try{
                rootpane.myContentPane.remove((ShapeNode)this);
                rootpane.myContentPane.repaint();
                if(this.child != null && this.child.node.allparents !=null){
                    this.child.node.allparents.remove(this.parent.node);
                this.child.node.updateRepeatStates(1.0/(double)(this.parent.node.getNumstates()));
                this.child.node.repeatStates.remove(this.child.node.repeatStates.size()-1);
                this.child.node.repeatStates.trimToSize();
                this.parent.node.children.remove(this.child.node);
                }
               // this.child.node.repeatStates.add(1);
                rootpane.nodes.remove(this);
                }
                catch(Exception ex){}
         }
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
          //System.out.println(this.rootpane.getFocusOwner()+ "Lost focus");
         
       //  this.borderThickness=1;
         
 }

 @Override
    protected void paintComponent(Graphics g){

        
      
        
            super.paintComponents(g);
             Point2D startPt;
             Point2D endPt;
             double offset =45;
             final double curX1;
             final double curY1;
             final double curX2;
             final double curY2;
             final double curW;
             final double curH;
             BoundsValidator bv = new BoundsValidator();
              // We need Graphics2D object so cast g to 2D object
            Graphics2D g2D = (Graphics2D)g;
            final  float dash[] = {7.0f};
            final  BasicStroke dashed= new BasicStroke(this.borderThickness+1, //Width of stroke
                BasicStroke.CAP_BUTT,   // End cap style
                BasicStroke.JOIN_MITER, // Join style
                10.0f,                  // Miter limit
                dash,                   //Dash pattern
                0.0f);                  //Dash phase
                g2D.setStroke(dashed);
            
       //this.setBounds(rootpane.getBounds());
        if(this.parent!=null && this.child!=null){
              
            
            
              Rectangle rect1= new Rectangle(this.parent.x,this.parent.y,this.parent.width,this.parent.height);
              Rectangle rect2=new Rectangle(this.child.x,this.child.y,this.child.width,this.child.height);
              Rectangle rectUnion = rect1.union(rect2);
              
              curX1 = (int)rectUnion.getX(); //+(this.parent.width/2);
              curY1 = (int)rectUnion.getY();//Math.min(rect2.y, rect2.y)-50;//+(this.parent.height/2);// + (this.parent.getHeight()/2);
             // System.out.println(Math.min(rect1.x,rect2.x)-50);
             // System.out.println(Math.min(rect2.y, rect2.y)-50);
              curX2 = this.child.getX()/2 +50; //+ (this.child.getWidth()/2);
              curY2 = (this.child.getY()/2) +100;//+(this.child.getHeight()/2);
               
              //curW = Math.abs(curX1-curX2);
             // curH = Math.abs(curY1-curY2);
           
            
             this.width= Math.max(this.parent.x, this.child.x)+ this.parent.width;//Math.abs(curX1-curX2)+ (this.child.width); //Math.abs(this.x2-this.x1);
             this.height=(int)Math.abs(curY1-curY2)+ (2*this.child.height);;//Math.max(curY1,curY2);//Math.max(this.y1,this.y2); //Math.abs(this.y2-this.y1);
         
             bv.checkLinkBounds(new Point2D.Double(parent.x,parent.y), new Point2D.Double(child.x,child.y), rectUnion);
             Rectangle r = bv.getRect();
             this.setBounds(r);
             startPt=bv.getStartPoint();
             endPt = bv.getEndPoint();
             drawArrowLine(g2D,startPt,endPt);

        }
        
        else{ // not linked to any object nodes
             offset =45;
             curX1= this.x  ;
             curY1= this.y + offset;//this.y1;
             curX2= this.x + this.getWidth();//this.x2;
             curY2=this.y + this.getHeight() + offset;//this.y2;
             
              
             
             startPt = new Point2D.Double(x1,y1);
             endPt = new Point2D.Double(x2,y2);
             
             
             
             
             this.setBounds(this.x, this.y, width, height);
             //Line2D.Double line = new Line2D.Double(startPt,endPt);
             //g2D.draw(line);
           
         if(reversed){
                
               
                drawArrowLine(g2D,endPt,startPt);
               
                
            }
            else{
                
                drawArrowLine(g2D,startPt,endPt);
            }
           
           
        }
            
            //Updates line start and end points;
            this.x1= startPt.getX();
            this.y1= startPt.getY();
            
            this.x2= endPt.getX();
            this.y2 = endPt.getY();
            
            repaint(x,y,width,height);
            this.rootpane.getContentPane().repaint(x,y,width,height);
           
    }
 
 
 
 public void drawArrowLine(Graphics2D g2, Point2D startPoint, Point2D endPoint){
       
        
        Point startPt,endPt;
        
        startPt = new Point((int)startPoint.getX(),(int)startPoint.getY());
        endPt = new Point((int)endPoint.getX(),(int)endPoint.getY());//new Point2D.Double(this.getX(),this.getY());//startPoint;  
        
        
         if( this.child != null){
          // SwingUtilities.convertPointFromScreen((Point)startPt, this);
          // SwingUtilities.convertPointFromScreen((Point)endPt, this);
           
           startPt= SwingUtilities.convertPoint(rootpane.myContentPane, startPt, this);
           endPt = SwingUtilities.convertPoint(rootpane.myContentPane, endPt, this);
         }
        
    
        double dy = endPt.getY() - startPt.getY();
        double dx = endPt.getX() - startPt.getX();
        double phi = Math.toRadians(30);
        double hypotenus = 20;
        double theta = Math.atan2(dy, dx);
        double x, y, alpha;
        Line2D line = new Line2D.Double(startPt,endPt);
        Point midPt= new Point((int)(line.getX1()+line.getX2())/2,(int)(line.getY1()+line.getY2())/2);
       // g2.drawString(this.getFullName(), (int)midPt.getX(), (int)midPt.getY());
        alpha = theta + phi;
        g2.draw(line);
        
        // Here is the dojo
       // ArrayList<Line2D> lineList = new ArrayList<Line2D>();
        for(int j = 0; j < 2; j++)
        {
            x = line.getX2() - hypotenus * Math.cos(alpha);
            y = line.getY2() - hypotenus * Math.sin(alpha);
           // lineList.add(new Line2D.Double(line.getX1(), line.getY1(), x, y));
            g2.draw(new Line2D.Double(line.getX2(), line.getY2(), x, y));
            alpha = theta - phi;
        }
      
        
    }

 

 
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
       
        super.writeExternal(out);
        
        out.writeObject((ShapeNode)this.parent);
        out.writeObject((ShapeNode)this.child);
        out.writeDouble(this.x1);
        out.writeDouble(this.x2);
        out.writeDouble(this.y1);
        out.writeDouble(this.y2);
        
        
       
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        
        super.readExternal(in);
        
        this.parent = (ShapeNode)in.readObject();
        this.child=(ShapeNode)in.readObject();
        this.x1=in.readDouble();
        this.x2=in.readDouble();
        this.y1=in.readDouble();
        this.y2= in.readDouble();
        this.repaint();
        
        
    }

}






