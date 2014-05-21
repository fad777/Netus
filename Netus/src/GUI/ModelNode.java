/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI;

/**
 *
 * @author fad777
 */
import GUI.Util.FileNameParser;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.io.File;
import java.util.ArrayList;

public class ModelNode extends ShapeNode //javax.swing.JPanel implements MouseListener,MouseMotionListener,ActionListener{
{
   // JPopupMenu popup;
    private static final long serialVersionUID = -7674821472601627162L;
    protected static int nodeCounter;
    protected int nodeId=0;
    public String[] agentsFileNames ;
  
   
    
    public static final Point2D anchorPoint = new Point2D.Double(90,38);
    RetrieveShapeNode _retrievedShapeNode;
    JScrollPane scrollPane = new JScrollPane();
    ShapeNode ooShapeNode;
    JPanel myPanel = new JPanel();
    
    JMenuItem itemProp = new JMenuItem("Properties");
    JMenuItem itemAssign = new JMenuItem("Assign model from file");
    JMenuItem itemExpand = new JMenuItem("Expand Model");
    JMenuItem itemCPT = new JMenuItem("CPT");
    JMenuItem itemDel = new JMenuItem("Delete");
    JMenuItem itemImportCPT = new JMenuItem("Import CPT");
    ModelLinkNode MLinkNode = new ModelLinkNode();
    
    public ModelNode(){
        super();
        ++InfluenceGUI.modelCounter;
     //  ++nodeCounter;
      // this.nodeId=nodeCounter;
    }
     @Override
    public void Clear(){
        nodeCounter=0;
    }

    public ModelNode(InfluenceGUI root,Node node){
        super(root,node);
        ++InfluenceGUI.modelCounter;
        ++nodeCounter;
        this.nodeId=nodeCounter;
        this.childId=this.nodeId;
        this.rootpane=root;
        java.util.Random rnd=new java.util.Random();
        this.setXY(100, 100);
        this.width=100;
        this.height=100;
        this.setBackground(Color.WHITE);
        this.setOpaque(false);
        this.setLayout(new FlowLayout());
        this.setBounds(x,y,width,height);
        this.setVisible(true);
        
        popup= new JPopupMenu(); //Creating Popup menu
        
        popup.add(itemProp).addActionListener(this);
        popup.add(itemAssign).addActionListener(this);
        popup.add(itemExpand).addActionListener(this);
        popup.add(itemCPT).addActionListener(this);
         popup.add(itemImportCPT).addActionListener(this);
        popup.add(itemDel).addActionListener(this);
        
        //add(popup);
        addMouseListener(this);
        addMouseMotionListener(this);
        addFocusListener(this);
        setFocusable(true);
        
    }

    public String getFullName(){
            if(!this.fullname.isEmpty()){
            return this.fullname;
        }
        String theName = this.node.name + this.nodeId;
        this.node.containerName=theName;
        return theName;
    }
    public int getNodeId(int id){
        return this.nodeId;
    }

    @Override
    public void actionPerformed(ActionEvent ae)
    {
        String command = ae.getActionCommand();
        if (command.equals("Properties")) {

            NodeProperty np=new NodeProperty(this, true);
            
            np.setVisible(true);

         }
         else if (command.equals("CPT")) {
            CPTable cp=new CPTable(this, true);
            cp.setVisible(true);
         }
          else if (command.equals("Import CPT")) {
           this.node.importCPT(this);
         }
         else if (command.equals("Expand Model")) {
             for(ShapeNode o: this.rootpane.modelNodes){
                expandModelNodeHandler(((ModelNode)o));
             }
             
             if(this.rootpane.policyLinks.size()>0){
                 try{
                 Node node = new Node();
                 node.setKind(node_type.LINK_NODE);
                 LinkNode newLink = new LinkNode(this.rootpane,node);
                 newLink.parent = ((UpdateLinkNode)this.rootpane.policyLinks.get(0)).parent;
                 newLink.child = ((UpdateLinkNode)this.rootpane.policyLinks.get(0)).child;
                 newLink.linkNodeColor = LinkNode.linkColorDelay;
                 this.rootpane.nodes.add(newLink);
                 this.rootpane.nodes.remove(this.rootpane.policyLinks.get(0));
                 this.rootpane.myContentPane.remove(this.rootpane.policyLinks.get(0));
                 this.rootpane.myContentPane.repaint();
                 }
                 catch(Exception ex){
                     ex.printStackTrace();
                 }
             }
             this.rootpane.AddOtherAgent();
             try{
                 if(ooShapeNode!=null){
                    this.rootpane.newNode.node.states = new String[0];
                    this.rootpane.newNode.node.states = ooShapeNode.node.states.clone(); 
                 }
             }catch(Exception ex){ex.printStackTrace();}
             
             
           
         }
        else if (command.equals("Assign model from file")) {
                //String filename ="";
                if(this.expand) return;
                int i;
                JFileChooser fc = new JFileChooser();
                fc.setCurrentDirectory(new File("."));
                fc.setMultiSelectionEnabled(true);
                int retval = fc.showOpenDialog(this);
                
            if(retval==JFileChooser.APPROVE_OPTION){
                String message;
                int len ;
                File[] files = fc.getSelectedFiles();
                len = files.length;
                this.agentsFileNames = new String[len];
                 /*** Initialization for persisting node ***/
                 PersistShapeNode._modelFileNames.clear();
                for(i=0;i<files.length;i++){
                    agentsFileNames[i] =files[i].getName();
                    PersistShapeNode._modelFileNames.add( agentsFileNames[i]);
                }
                message = String.format(" %d models successfully assigned.", len);
               // ExpandAllNetworks(this.agentsFileNames);
               
                javax.swing.JOptionPane.showMessageDialog(this, message);
                AddStates(len);
            }
            else{
           
                javax.swing.JOptionPane.showMessageDialog(null, "Please, select a file for assignment");
           
            }
         }
        else if (command.equals("Delete")){
             
            try{
             rootpane.myContentPane.remove((ShapeNode)this);
             rootpane.myContentPane.repaint();
             rootpane.nodes.remove(this);
            }
            catch(Exception ex){
                
            }
            finally{
                --InfluenceGUI.modelCounter;
            }
         }
    }
    public void expandModelNodeHandler(ModelNode modelNode){
         if(modelNode.expand) return;
         modelNode.ExpandModelNode(modelNode.rootpane);
         modelNode.ExpandAllNetworks(modelNode.agentsFileNames,new String(""));
       
    }
    public void setNodeId(int id){
        this.nodeId=id;
    }
    public void setNodeName(String name){
        this.node.name=name;
    }
    
@Override
 public void focusGained(FocusEvent e) {
        
        this.borderThickness=3;
        
        //System.out.println("gained");
       // System.out.println(this.rootpane.getFocusOwner());
 }
     @Override
    public void focusLost(FocusEvent e) {
        
        this.borderThickness=1;
         // System.out.println(this.rootpane.getFocusOwner()+ "Lost focus");
         
       //  this.borderThickness=1;
         
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
                
                hortz += this.width;
                outOfBounds =true;
                
            }
            
            if(this.y >rootpane.myContentPane.getHeight() || (this.y + this.getHeight()) > rootpane.myContentPane.getHeight()){
                
                vertz = (this.y + this.height);
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
         
         this.requestFocusInWindow(); 

         }
@Override
    protected void paintComponent(Graphics g)
    {

             super.paintComponents(g);
            
              // We need Graphics2D object so cast g to 2D object
            Graphics2D g2D = (Graphics2D)g;
            g2D.setStroke(new BasicStroke(this.borderThickness));
            RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
            rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2D.setRenderingHints(rh);
            
            
                this.setBounds(this.x,this.y,width,height);
               // Line2D line = new Line2D.Double(10,35, width-20, 35);
                g2D.setColor(Color.cyan);
                Hexagon myHex = new Hexagon(0,0,45);
                Polygon myshape = myHex.getPolygon();

                 try{
                    if(this.expand){
                         
                          Ellipse2D myshape2 = new Ellipse2D.Double(10, 10, width-15, height-25);
                          g2D.setColor(Color.yellow);
                          g2D.fill(myshape2);
                          g2D.setColor(Color.BLACK);
                          g2D.draw(myshape2);
                          g2D.setColor(Color.black);
                          //g2D.drawString(this.getFullName(), 25, 50);
                         g2D.drawString(this.getFullName(), width/3 +10, height/3 + 15);
                         
                    }
                    else{
                          g2D.fillPolygon(myshape);
                          g2D.setColor(Color.BLACK);
                          g2D.draw(myshape);
                          g2D.setColor(Color.black);
                         // g2D.drawString(this.getFullName(), 18, 40);
                          g2D.drawString(this.getFullName(), width/3 +5, height/3 + 10);
                    }
                }
                catch(Exception ex){

                   System.out.print(ex.getMessage());
                   System.out.println();
                }

              
            
            repaint(x,y,width,height);
            this.rootpane.getContentPane().repaint(x,y,width,height);
            
          
           

    }
private  void ExpandAllNetworks(String[] fileList,String ContainerFileName){
        
      
       if(fileList ==null || fileList.length<1) return;
        ArrayList myList =null;
        ShapeNode retrievedShape = null;
        ShapeNode shapeNode = null;
        ShapeNode modNode = new ShapeNode();
        String kind ="";
        int x, y =0;
      
       
       for(int index=0;index<fileList.length;index++){
             InfluenceGUI myFrame = new InfluenceGUI();
            boolean hasModelNode =false;
            this._retrievedShapeNode = new RetrieveShapeNode(fileList[index].toString());
        try{
             
             
               // retrievedShape = (ShapeNode)this._retrievedShapeNode.getObject();
                   myList = (ArrayList)this._retrievedShapeNode.getObjectList();
                
                for(Object myShape : myList){
                    
                    retrievedShape = (ShapeNode)myShape;
                    kind=retrievedShape.node.getKind().toString();
                    
                    x=retrievedShape.x;
                    y=retrievedShape.y;
                    //width=retrievedShape.width;
                    //height = retrievedShape.height;
                
                
                   if(kind.equals(node_type.DECISION_NODE.toString())){

                     shapeNode = (DecisionNode)retrievedShape;   
                     ((DecisionNode)shapeNode).setNodeId(shapeNode.childId);
                      ((DecisionNode)shapeNode).setNodeName(shapeNode.node.name);
                     ((DecisionNode)shapeNode).rootpane=myFrame;
                   }
                   else if(kind.equals(node_type.LINK_NODE.toString())){
                      // shapeNode= new LinkNode(this,retrievedShape.node);
                       
                      shapeNode = (LinkNode)retrievedShape;   
                     ((LinkNode)shapeNode).setNodeId(shapeNode.childId);
                      ((LinkNode)shapeNode).setNodeName(shapeNode.node.name);
                     ((LinkNode)shapeNode).rootpane=myFrame;
                      
                  }
                  else if(kind.equals(node_type.UPDATE_LINK_NODE.toString())){
                      // shapeNode= new LinkNode(this,retrievedShape.node);
                       
                      shapeNode = (UpdateLinkNode)retrievedShape;   
                     ((UpdateLinkNode)shapeNode).setNodeId(shapeNode.childId);
                      ((UpdateLinkNode)shapeNode).setNodeName(shapeNode.node.name);
                     ((UpdateLinkNode)shapeNode).rootpane=myFrame;
                      
                  }
                   else if(kind.equals(node_type.MODEL_NODE.toString())){
                      shapeNode = (ModelNode)retrievedShape;   
                     ((ModelNode)shapeNode).setNodeId(shapeNode.childId);
                     ((ModelNode)shapeNode).setNodeName(shapeNode.node.name);
                     ((ModelNode)shapeNode).rootpane=myFrame;
                     if(((ModelNode)shapeNode).expand) {
                         --myFrame.modelCounter;
                     
                     }
                     
                     modNode=shapeNode; 
                     hasModelNode=true;
                     
                      
                      
                   }
                   else if(kind.equals(node_type.NATURE_NODE.toString())){
                      shapeNode = (NatureNode)retrievedShape;   
                     ((NatureNode)shapeNode).setNodeId(shapeNode.childId);
                     ((NatureNode)shapeNode).setNodeName(shapeNode.node.name);
                      ((NatureNode)shapeNode).rootpane=myFrame;
                      try{
                      if(shapeNode!=null && (shapeNode.label.equalsIgnoreCase("O1")
                           || shapeNode.node.label.equalsIgnoreCase("O1"))){
                        ooShapeNode = shapeNode;
                      }
                      }catch(Exception ex){}
                     
                   }
                   else if(kind.equals(node_type.UTILITY_NODE.toString())){
                      shapeNode = (UtilityNode)retrievedShape;   
                      ((UtilityNode)shapeNode).setNodeId(shapeNode.childId);
                      ((UtilityNode)shapeNode).setNodeName(shapeNode.node.name);
                      ((UtilityNode)shapeNode).rootpane=myFrame;
                   }
                   else if(kind.equals(node_type.MODEL_LINK_NODE.toString())){
                      shapeNode = (ModelLinkNode)retrievedShape;   
                      ((ModelLinkNode)shapeNode).setNodeId(shapeNode.childId);
                      ((ModelLinkNode)shapeNode).setNodeName(shapeNode.node.name);
                      ((ModelLinkNode)shapeNode).rootpane=myFrame;
                      MLinkNode=((ModelLinkNode)shapeNode);
                   }
                   // shapeNode.moveBy(x, y);
                   // int myInset =100;
                    shapeNode.repaint();
                    int newWidth = Math.max((shapeNode.getWidth()+ shapeNode.x), this.getPreferredSize().width);
                    int newHeight = Math.max((shapeNode.getHeight()+ shapeNode.y), this.getPreferredSize().height) ;
                    
                    retrievedShape.rootpane=myFrame;
                    myFrame.myContentPane.add(shapeNode);
                    myFrame.nodes.add(shapeNode);
                    myFrame.myContentPane.setPreferredSize(new Dimension(newWidth,newHeight));
                    
                }
           
            
        }
        catch(Exception ex){
           ex.printStackTrace();
        }
        
        
        if(hasModelNode && (! ((ModelNode)modNode).expand)){
            String filename = FileNameParser.getFullName(ContainerFileName,fileList[index].toString());
            ((ModelNode)modNode).ExpandModelNode(myFrame);
             ((ModelNode)modNode).ModelLinkChildLinkNode=MLinkNode;
             rootpane.pendingSaveObjects.put(filename, myFrame);
            ((ModelNode)modNode).ExpandAllNetworks(((ModelNode)modNode).agentsFileNames,filename);
            // ((ModelNode)modNode).ModelLinkChildLinkNode=MLinkNode;
           // SaveNetwork(filename,myFrame);
           // rootpane.pendingSaveObjects.put(filename, myFrame);
        }
        else if(!hasModelNode){
             String filename = FileNameParser.getFullName(ContainerFileName,fileList[index].toString());
             rootpane.pendingSaveObjects.put(filename, myFrame);
        }
        
       }
        
     
}
 
 
 private void AddStates(int numStates){
     
        int i;
        this.node.setNumstates(numStates);
        for(i=0;i<numStates;i++)
        {
            this.node.states[i]= String.format("M%d",i);
        }
        this.node.setCPTstates();
        
         
     
 }

 private void ExpandModelNode(InfluenceGUI myframe){
     
  if(this.ModelLinkChildLinkNode!=null && this.ModelLinkParentLinkNode !=null){
            // this.node.setKind(node_type.NATURE_NODE);
             this.expand =true;
   
             LinkNode modelLink = new  LinkNode(this.rootpane,this.ModelLinkChildLinkNode.node);
             
            // ln.node.setType(node_type.LINK_NODE);
              modelLink.node.setType(node_type.LINK_NODE);
             
              modelLink.parent= this;
              if(this.ModelLinkChildLinkNode.child==null){
                  this.ModelLinkChildLinkNode.child = rootpane.getNodeByLabel(this.ModelLinkChildLinkNode.label);
                  this.ModelLinkParentLinkNode.parent = rootpane.getNodeByLabel(this.ModelLinkParentLinkNode.label);
              }
              modelLink.child= (this.ModelLinkChildLinkNode).child;
              
               /*** Children should turn orphans ***
              ***  
              **/
             try{
                          this.popup.remove(itemAssign);
                          this.popup.remove(itemExpand);
                          
              
             }catch(Exception ex){System.err.println(ex.getMessage());}
             try{
           
             }catch(Exception ex){System.err.println(ex.getMessage());}
      
             myframe.nodes.add(modelLink);
             myframe.myContentPane.add(modelLink);
             
            // this.rootpane.nodes.remove(this.ModelLinkParentLinkNode);
             myframe.nodes.remove(this.ModelLinkChildLinkNode);
             
             myframe.myContentPane.remove(this.ModelLinkChildLinkNode);
            // this.rootpane.myContentPane.remove(this.ModelLinkParentLinkNode);
            --myframe.modelCounter;
           
             myframe.getContentPane().getParent().revalidate();
             
         }
       
 }
 
   private void SaveNetwork(String filename,InfluenceGUI myframe) {                                        
           
          Boolean isDID = myframe.modelCounter > 0? false: true;
          PersistShapeNode persistShapeNode = new PersistShapeNode(filename,isDID);
       
        
        try{
          
            persistShapeNode.WriteListObject(myframe.nodes);
        }
        catch(Exception ex){
            System.err.println(ex.getMessage());
        }
       
     
    }    



}