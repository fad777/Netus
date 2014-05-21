/*
ShapeNode.java
 */

package GUI;
import java.io.*;
import javax.swing.JPanel;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.util.Random;
import java.io.Serializable;
import java.util.ArrayList;

public class ShapeNode extends javax.swing.JPanel implements MouseListener,
                                                             MouseMotionListener,
                                                             FocusListener,
                                                             ActionListener,
                                                             Externalizable,
                                                            Comparable<ShapeNode>{
    private static final long serialVersionUID = 8766365189058326553L;
    int x=100;
    int y=100;
    int width=100;
    int height=100;
    Node node;
    protected javax.swing.JPopupMenu popup;
    
    InfluenceGUI rootpane;
    int childId = 0;
    
    protected static int nodeCounter;
    protected int nodeId=0;
    protected Point2D anchorPoint = new Point2D.Double();
    protected String fullname ="";
    
    protected LinkNode ModelLinkParentLinkNode;
    protected ModelLinkNode ModelLinkChildLinkNode;
    protected boolean expand =false;
    public String label="";
    public ArrayList dalayLinks =new ArrayList();
    public int borderThickness = 1;
    
    
    public ShapeNode(){
         super(true);
        
        //rootpane=null;
        //this.node=null;
        //this.setBackground(Color.WHITE);
        //this.setLayout(new FlowLayout());
        this.setBounds(x,y,width,height);
        this.setVisible(true);
        //popup.setDefaultLightWeightPopupEnabled(false);
        popup= new JPopupMenu();
        add(popup);
        addMouseListener(this);
        addMouseMotionListener(this);
        addFocusListener(this);
    }
    
      public String getFullName(){
        return fullname;
    }
     
      public int getNodeId(){
        return this.nodeId;
    }
      
   public void setFullName(String name){
        
       
        fullname = name;
        
        //this.node.name=name;
        
    }
      
    public void Clear(){
        
        nodeCounter=0;
    }

    /**
     *
     * @param root
     * @param node
     */
    public ShapeNode(InfluenceGUI root,Node node)
    {
        //java.util.Random rnd=new Random();
        super(true);
        
        this.rootpane=root;
        //this.setXY((int)rnd.nextDouble()*root.getWidth()-100, (int)rnd.nextDouble()*root.getHeight()-100);
        this.node=node;
       
       // this.setBackground(Color.BLUE);
       // this.setLayout(new FlowLayout());
        this.fullname="";
        try{
            this.label = node.label==null? "": node.label;
        this.setBounds(x,y,width,height);
        this.setXY(x, y);
        //popup.setDefaultLightWeightPopupEnabled(false);
        this.setVisible(true);//
        this.nodeId=node.getNodeId();
        popup= new JPopupMenu();
        //add(popup);
      //  addMouseListener(this);
        addMouseMotionListener(this);
        addFocusListener(this);
        setFocusable(true);
        requestFocusInWindow();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public void setAnchorPoint(Point2D pt){
        
        this.anchorPoint=pt;
    }
    public Point2D getAnchorPoint(){
        
        return this.anchorPoint;
    }
    

    public void actionPerformed(ActionEvent ae)
    {
        String command = ae.getActionCommand();
        if (command.equals("Properties")) {
            if(node.type==node_type.NATURE_NODE)
            {
            NodeProperty np=new NodeProperty(this, true);
            np.setVisible(true);
            }
            else if(node.type==node_type.DECISION_NODE)
            {
                NodeProperty np=new NodeProperty(this, true);
                np.setVisible(true);
            }
            else if(node.type==node_type.UTILITY_NODE)
            {
                NodeProperty np=new NodeProperty(this, true);
                np.setVisible(true);
            }
            else if(node.type==node_type.LINK_NODE)
            {
                NodeProperty np=new NodeProperty(this, true);
                np.setVisible(true);
            }
         }
         else if (command.equals("CPTable")) {
            CPTable cp=new CPTable(this, true);
            cp.setVisible(true);
         }
        
    }
public boolean  containsPoint(int x,int y)
{
    if(x>this.x && x<this.x+this.width && y>this.y && y<this.y+this.height)
        return true;
    else return false;
}

public void setXY(int x,int y)
{

    this.x=x;
    this.y=y;
    //rootpane.myContentPane.remove(this);
    rootpane.myContentPane.getParentContainer().repaint(x,y,width,height);
    this.setBounds(this.x,this.y,this.width,this.height);

    //rootpane.myContentPane.add(this);
  // rootpane.myContentPane.update(rootpane.getGraphics());
  // rootpane.update(rootpane.getGraphics());


}
public void moveBy(int x,int y)
{

      //  this.repaint(this.x,this.y,width,height);
        this.x+=x;
        this.y+=y;
      // rootpane.myContentPane.remove(this);
        
        this.setBounds(this.x,this.y,width,height);
     //  rootpane.myContentPane.add(this);

      
}
      int prevDragX=0;  // During dragging, these record the x and y coordinates
      int prevDragY=0;
      boolean dragged=false;
      boolean odragged=false;
      boolean pressed=true;
      
 public void mousePressed(MouseEvent evt) {
 // this.requestFocusInWindow();
/*
odragged=false;

        
        
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
       
         */     
           
              
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


public void mouseReleased(MouseEvent evt) {
    //this.requestFocusInWindow();
    /*
         //pressed=false;
            if(!odragged)
              return;
         int x = evt.getX();
         int y = evt.getY();
          if (evt.isPopupTrigger()) {
                popup.show(this,x-10,y-2);
         }
         else {

            this.moveBy(x - prevDragX, y - prevDragY);
           // this.moveBy(x , y );
          //  System.out.println("releasing "+this.x+","+this.y);
            //this.setXY(x, y);
            if ( this.x >= rootpane.myContentPane.getSize().width
              || this.y >= rootpane.myContentPane.getSize().height 
              || this.x + this.width < 0 ||
                    this.y + this.height < 0 ) {  // shape is off-screen
                // remove shape from list of shapes
              //  System.out.println("The released pts are "+ this.x + "," + this.y);
               rootpane.myContentPane.remove(this);
               rootpane.nodes.remove(this);
            }

         }
         rootpane.myContentPane.repaint();
         odragged=false;
         */
          
         }



      public void mouseEntered(MouseEvent evt) { }   // Other methods required for MouseListener and
      public void mouseExited(MouseEvent evt) { }    //              MouseMotionListener interfaces.
      public void mouseMoved(MouseEvent evt) {
      }
      public void mouseClicked(MouseEvent evt) {   
          
      }
   
  
    public void draw(javax.swing.JFrame parent)
    {

       // parent.add(this);

    }

    public void writeExternal(ObjectOutput out) throws IOException {
       try{
            out.writeObject(node);
            out.writeInt(childId);
            out.writeInt(x);
            out.writeInt(y);
            out.writeInt(width);
            out.writeInt(height);
            out.writeObject(popup);
            out.writeUTF(fullname); 
        try{
        out.writeUTF(this.label);
        }catch(Exception ex){} 
            out.writeObject(ModelLinkChildLinkNode);
            out.writeObject(ModelLinkParentLinkNode);
            out.writeObject(this.expand);
        
         if(this.node.getKind().toString().equalsIgnoreCase(node_type.MODEL_NODE.toString())){
           out.writeObject(((ModelNode)this).agentsFileNames);
          }
         
          try{
              
                out.writeObject(this.dalayLinks);
            }catch(Exception ex){ex.printStackTrace();}
          
       }catch(Exception ex){}
       
      
        
       // out.writeObject(rootpane);
    }
    
    protected static Point2D getAnchorPoint(Shape shape,int anchorPtIndex){
     
     PathIterator iterator = shape.getPathIterator(null);
    
     ArrayList<Point2D> pointList = new ArrayList<Point2D>();
     
     while(iterator.isDone()==false){
         
         Point2D pt = retrievePt(iterator);
         pointList.add(pt);
         iterator.next();
     }
     
     return pointList.get(anchorPtIndex);
 }
    
 public static Point2D retrievePt(PathIterator pi){
      double[] coords = new double[6];
      int type = pi.currentSegment(coords);
      Point2D pt= new Point2D.Double();
      
      switch(type){
          
          case PathIterator.SEG_MOVETO:
          case PathIterator.SEG_LINETO:
          case PathIterator.SEG_CUBICTO:
          case PathIterator.SEG_QUADTO:
              pt = new Point2D.Double(coords[0],coords[1]);
              break;
          case PathIterator.SEG_CLOSE:
              pt = new Point2D.Double();
      }
              
        return pt;     
          
          
   }
     
 

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        try{
        this.node=(Node)in.readObject();
        this.childId=in.readInt();
        this.x = in.readInt();
        this.y = in.readInt();
        this.width=in.readInt();
        this.height = in.readInt();
        this.popup = (JPopupMenu)in.readObject();
        this.fullname =in.readUTF();
        try{
        this.label=in.readUTF();
        }catch(Exception ex){}
        this.ModelLinkChildLinkNode= (ModelLinkNode)in.readObject();
        this.ModelLinkParentLinkNode=(LinkNode)in.readObject();
        this.expand = (Boolean)in.readObject();
       
        
        try{
        if(this.node.getKind().toString().equalsIgnoreCase(node_type.MODEL_NODE.toString())){
            ((ModelNode)this).agentsFileNames=(String[])in.readObject();
        }
        }
        catch(Exception ex){
           // ex.printStackTrace();
        }
          try{
              
                    this.dalayLinks = (ArrayList)in.readObject();
            }
            catch(Exception ex){
               // ex.printStackTrace();
            }
        }catch(Exception ex){}
        
      
       // this.setFullName(this.node.name);
        
       // this.setBounds(this.x, this.y, this.width, this.height);
        
       // this.rootpane= (InfluenceGUI)in.readObject();
    }
    
    @Override
    public String toString(){
        
        return this.node.getKind().toString();
    }

    public int compareTo(ShapeNode o) {
        
        return this.getFullName().compareToIgnoreCase(o.getFullName());
    }

    
 public void focusGained(FocusEvent e) {
      
    }
     
    public void focusLost(FocusEvent e) {
          
         
    }


}
