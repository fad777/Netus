

/*
 * InfluenceGUI.java
 *
 */

package GUI;

import GUI.Util.FileNameParser;
import GUI.Util.FrameDecorator;
import GUI.Util.UIFileFilter;
import com.sun.awt.AWTUtilities;
import java.util.*;
import java.util.List;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.net.*;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.swing.filechooser.*;
import javax.swing.JOptionPane.*;



public class InfluenceGUI extends javax.swing.JFrame 
                          implements ActionListener,
                          FocusListener,
                          MouseListener{
   
    ArrayList nodes;
    JPopupMenu popup;
    boolean pressed=false;
    ShapeNode draggedNode;
    PersistShapeNode _persistShapeNode = null;
    RetrieveShapeNode _retrievedShapeNode = null;
    ScrollPane myContentPane;
    
    
    public static int modelCounter;
    UIFileFilter idFilter, iidFilter, didFilter,ididFilter,allFilter;
    public int delayLinkCounter =0;
    ArrayList delayedLinks = new ArrayList();
    ArrayList<ShapeNode> modelNodes = new ArrayList<ShapeNode>();
    ArrayList<ShapeNode> policyLinks = new ArrayList<ShapeNode>();
    private Integer OtherAgentXLoc=0;
    NatureNode newNode ;
    
    static Map<String,InfluenceGUI> pendingSaveObjects = new LinkedHashMap<String,InfluenceGUI>();
    
    /** Creates new form InfluenceGUI */
    public InfluenceGUI() {
        
       
        super(); 
        modelCounter=0;
        init();
       
        

    }

    
     private void init() {
             // This is to enable Menu to show atop the contentpane
         requestFocusInWindow();
         addFocusListener(this);
         ImageIcon iconSave = new ImageIcon("ShapeIcons/save2.jpg");
         ImageIcon iconOpen = new ImageIcon("ShapeIcons/fileopen.jpg");
         ImageIcon iconNew = new ImageIcon("ShapeIcons/newfile.jpg");
         ImageIcon iconExit = new ImageIcon("ShapeIcons/exit.jpg");
            JPopupMenu.setDefaultLightWeightPopupEnabled(false);
            ToolTipManager.sharedInstance().setLightWeightPopupEnabled(false);

           JToolBar jToolBar1 = new JToolBar();
            //this.setLayout(LayoutManager.);

           nodes=new ArrayList();
           draggedNode=new ShapeNode();
          // oval icon for the oval shape
        //  URL ovalUrl = this.getClass().getResource("resources/Oval.JPG");
        //  ImageIcon ovalIcon = new ImageIcon(ovalUrl);     //Oval Insert Button
          ImageIcon ovalIcon = new ImageIcon("ShapeIcons/Oval.jpg");
          JButton ovalBtn = new JButton(ovalIcon);
          ovalBtn.setToolTipText("Nature node");
          ovalBtn.setActionCommand("Nature");
          ovalBtn.addActionListener(this);
          jToolBar1.add(ovalBtn);
          // icon for the rectangle shape
       //  final ClassLoader cl = getClass().getClassLoader();
       // final  URL rectUrl = cl.getResource("Rectangle.JPG");
          //ImageIcon rectangleIcon = new ImageIcon(rectUrl); //Rectangle Insert Button
          ImageIcon rectangleIcon = new ImageIcon("ShapeIcons/Rectangle.jpg");     //Oval Insert Button
          JButton rectangleBtn = new JButton(rectangleIcon);
          rectangleBtn.setToolTipText("Decision node");
          rectangleBtn.setActionCommand("Decision");
          rectangleBtn.addActionListener(this);
          jToolBar1.add(rectangleBtn);

         // URL diamondUrl = this.getClass().getResource("resources/Diamond.JPG");
          ImageIcon diamondIcon = new ImageIcon("ShapeIcons/Diamond.jpg"); //Rectangle Insert Button
          JButton diamondBtn = new JButton(diamondIcon);
          diamondBtn.setToolTipText("Utility node");
          diamondBtn.setActionCommand("Utility");
          diamondBtn.addActionListener(this);
          jToolBar1.add(diamondBtn);

            //   URL hexUrl = this.getClass().getResource("resources/Hexagon.JPG");
         // ImageIcon hexIcon = new ImageIcon(hexUrl); //Rectangle Insert Button
          ImageIcon hexIcon = new ImageIcon("ShapeIcons/Hexagon.jpg"); //Rectangle Insert Button
          JButton hexBtn = new JButton(hexIcon);
          hexBtn.setToolTipText("Model node");
          hexBtn.setActionCommand("Model");
          hexBtn.addActionListener(this);
          jToolBar1.add(hexBtn);

         //  URL arrowUrl = this.getClass().getResource("resources/Arrow.JPG");
          ImageIcon arrowIcon = new ImageIcon("ShapeIcons/Arrow.JPG"); //Rectangle Insert Button
         // ImageIcon arrowIcon = new ImageIcon(arrowUrl); //Rectangle Insert Button
          JButton arrowBtn = new JButton(arrowIcon);
          arrowBtn.setToolTipText("Link ");
          arrowBtn.setActionCommand("Link");
          arrowBtn.addActionListener(this);
          jToolBar1.add(arrowBtn);


           //  URL arrowUrl = this.getClass().getResource("resources/Arrow.JPG");
          ImageIcon dashedArrowIcon = new ImageIcon("ShapeIcons/DashedArrow.jpg"); //Rectangle Insert Button
         // ImageIcon arrowIcon = new ImageIcon(arrowUrl); //Rectangle Insert Button
          JButton dashedArrowBtn = new JButton(dashedArrowIcon);
          dashedArrowBtn.setToolTipText("Policy link");
          dashedArrowBtn.setActionCommand("ModelLinker");
          dashedArrowBtn.addActionListener(this);
          jToolBar1.add(dashedArrowBtn);

            //  Update link node
          ImageIcon dottedArrowIcon = new ImageIcon("ShapeIcons/DottedArrow.jpg"); //Rectangle Insert Button
         // ImageIcon arrowIcon = new ImageIcon(arrowUrl); //Rectangle Insert Button
          JButton dottedArrowBtn = new JButton(dottedArrowIcon);
          dottedArrowBtn.setToolTipText("Policy update link");
          dottedArrowBtn.setActionCommand("UpdateLinker");
          dottedArrowBtn.addActionListener(this);
          jToolBar1.add(dottedArrowBtn);
        MainFocusListener focusListener = new MainFocusListener();
        
        myContentPane = new ScrollPane();
        myContentPane.addFocusListener(focusListener);
        myContentPane.getParentContainer().addFocusListener(this);
        this.setContentPane(myContentPane.getParentContainer());
        this.myContentPane.add(jToolBar1,BorderLayout.NORTH);
       // this.pendingSaveObjects = new LinkedHashMap<String,InfluenceGUI>();
        
        
        jMenuBar1 = new javax.swing.JMenuBar();
        mnuFile = new javax.swing.JMenu();
        mnuNew = new javax.swing.JMenuItem(iconNew);
        mnuNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, Event.CTRL_MASK));
        mnuClear = new javax.swing.JMenuItem();
        mnuOpen = new javax.swing.JMenuItem(iconOpen);
        mnuOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,Event.CTRL_MASK));
        mnuSave = new javax.swing.JMenuItem(iconSave);
        mnuSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,Event.CTRL_MASK));
        mnuExit = new javax.swing.JMenuItem(iconExit);
        
        mnuModify = new javax.swing.JMenu();
        mnuAdd = new javax.swing.JMenu();
        
        mnuAddNatureNode = new javax.swing.JMenuItem();
        mnuAddDecisionNode = new javax.swing.JMenuItem();
        mnuAddUtilNode = new javax.swing.JMenuItem();
        mnuAddLinkNode = new javax.swing.JMenuItem();
        mnuAddModelNode = new javax.swing.JMenuItem();
        
        mnuNetwork = new javax.swing.JMenu();
        mnuCompile = new javax.swing.JMenuItem();
        mnuExpand = new javax.swing.JMenuItem();
        
        mnuWiz = new javax.swing.JMenu();
        mnuWiz.setText("Wizards");
        
       // mnuWiz.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W,Event.CTRL_MASK));
        //mnuCompile = new javax.swing.JMenuItem();
        //mnuExpand = new javax.swing.JMenuItem();
        
        mnuHelp = new javax.swing.JMenu();
        mnuHelp.setText("Help");
        mnuAbout = new JMenuItem();
        mnuAbout.setText("About");
        
        mnuHelp.add((mnuAbout));
        
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Netus 1.0.0");
        setBackground(new java.awt.Color(255, 255, 255));
        //setPreferredSize(new java.awt.Dimension(200, 200));

        mnuFile.setText("File");
        mnuFile.setMnemonic(KeyEvent.VK_F);
        mnuNew.setText("New                         ");
        mnuNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuNewActionPerformed(evt);
            }
        });
        mnuFile.add(mnuNew);
        mnuFile.addSeparator();
        mnuClear.setText("Clear");
        mnuClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuClearActionPerformed(evt);
            }
        });
        mnuFile.add(mnuClear);
        mnuFile.addSeparator();
        
        mnuOpen.setText("Open");
        mnuOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuOpenActionPerformed(evt);
            }
        });
        mnuFile.add(mnuOpen);
        mnuFile.addSeparator();
        
        mnuSave.setText("Save");
        mnuSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuSaveActionPerformed(evt);
            }
        });
        mnuFile.add(mnuSave);
        mnuFile.addSeparator();

        mnuExit.setText("Exit");
        mnuExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuExitActionPerformed(evt);
            }
        });
        mnuFile.add(mnuExit);
        mnuFile.addSeparator();
        
        jMenuBar1.add(mnuFile);

        mnuModify.setText("Modify");

        mnuAdd.setText("Add                             ");

        mnuAddNatureNode.setText("Nature Node");
        mnuAddNatureNode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuAddNatureNodeActionPerformed(evt);
            }
        });
        mnuAdd.add(mnuAddNatureNode);

        mnuAddDecisionNode.setText("Decision Node");
        mnuAddDecisionNode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuAddDecisionNodeActionPerformed(evt);
            }
        });
        mnuAdd.add(mnuAddDecisionNode);

        mnuAddUtilNode.setText("Utility Node");
        mnuAddUtilNode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuAddUtilNodeActionPerformed(evt);
            }
        });
        mnuAdd.add(mnuAddUtilNode);

        mnuAddLinkNode.setText("Link");
        mnuAddLinkNode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuAddLinkNodeActionPerformed(evt);
            }
        });
        mnuAdd.add(mnuAddLinkNode);

        mnuAddModelNode.setText("Model Node");
        mnuAddModelNode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuAddModelNodeActionPerformed(evt);
            }
        });
        
        mnuAdd.add(mnuAddModelNode);

        mnuModify.add(mnuAdd);

        jMenuBar1.add(mnuModify);

        mnuNetwork.setText("Network");
        mnuNetwork.addSeparator();
        mnuCompile.setText("Compile                                     ");
        mnuCompile.addActionListener(new java.awt.event.ActionListener() {

             public void actionPerformed(ActionEvent evt) {
                 mnuCompileActionPerformed(evt);
             }
         });
        mnuNetwork.add(mnuCompile);
        mnuNetwork.addSeparator();
        mnuExpand.setText("Unroll (2-time steps)");
        mnuExpand.addActionListener(new java.awt.event.ActionListener() {

             public void actionPerformed(ActionEvent evt) {
                 mnuExpandActionPerformed(evt);
             }
         });
        mnuNetwork.add(mnuExpand);
        
        jMenuBar1.add(mnuNetwork);
        jMenuBar1.add(mnuWiz);
        jMenuBar1.add(mnuHelp);
        setJMenuBar(jMenuBar1);
        
       
       
       
        
//        pack();
    }// </editor-fold>

    public ShapeNode getNodeByLabel(String theLabel){
        ShapeNode ret = null;
        if(theLabel==null || theLabel.isEmpty()){
            return null;
        }
        for(Object n:nodes){
            ret = (ShapeNode)n;
            String label = ret.label;
            
            if((label.equalsIgnoreCase(theLabel)) ){
                return ret;
            }
        }
        return null;         
    }
    
    protected void paintComponent(Graphics g){
        //this.getContentPane().update(g);
       // this.update(g);
    }

    // Toolbar actions performed 
    public void actionPerformed(ActionEvent ae) {
        String command=ae.getActionCommand();
         ShapeNode s = new ShapeNode();
         Node newNode = new Node();
         NodeProperty np ;
        if(command.equals("Nature"))
        {
        Node node=new Node();
        node.setKind(node_type.NATURE_NODE);

        s=new NatureNode(this,node);
       //newNode.setKind(node_type.NATURE_NODE);
        //np =new NodeProperty();
        //np.genLabel(s, "S", "1");
       // np.dispose();
        //nodes.add(s);
       // s.repaint();
        
        
        }
        else if(command.equals("Decision"))
        {
            Node node=new Node();
            node.setKind(node_type.DECISION_NODE);
            s=new DecisionNode(this,node);
       
        }
        else if(command.equals("Utility"))
        {
        Node node=new Node();
        node.setKind(node_type.UTILITY_NODE);

        s=new UtilityNode(this,node);
        //nodes.add(s);

        //s.repaint();
        }
        else if(command.equals("Link"))
        {
        Node node=new Node();
        node.setKind(node_type.LINK_NODE);

        s=new LinkNode(this,node);
        if(s.label.equalsIgnoreCase(LinkNode.DelayLinkLabel)){
            if(!this.delayedLinks.contains(s)){
            this.delayedLinks.add(s);
            }
        }
        //nodes.add(s);
        

       // s.repaint();
        }
        else if(command.equals("ModelLinker"))
        {
        Node node=new Node();
        node.setKind(node_type.MODEL_LINK_NODE);

        s=new ModelLinkNode(this,node);
        //nodes.add(s);

       // s.repaint();
        }
         else if(command.equals("UpdateLinker"))
        {
        Node node=new Node();
        node.setKind(node_type.UPDATE_LINK_NODE);

        s=new UpdateLinkNode(this,node);
        //nodes.add(s);

        //s.repaint();
        }
        else if(command.equals("Model"))
        {
        Node node=new Node();
        node.setKind(node_type.MODEL_NODE);
       
        s=new ModelNode(this,node);
        modelNodes.add(s);
        //nodes.add(s);

        
        }
       // s.repaint(s.x,s.y,s.width,s.height);
        s.requestFocusInWindow();
      //  s.repaint();
        nodes.add(s);
       
       
        
       // this.repaint();
     
       this.myContentPane.add(s);
     //  this.revalidate();
         // this.myContentPane.repaint();
       // this.getContentPane().setComponentZOrder(s.getParent(), 0);
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        jMenuBar1 = new javax.swing.JMenuBar();
        mnuFile = new javax.swing.JMenu();
        mnuNew = new javax.swing.JMenuItem();
        mnuClear = new javax.swing.JMenuItem();
        mnuOpen = new javax.swing.JMenuItem();
        mnuSave = new javax.swing.JMenuItem();
        mnuExit = new javax.swing.JMenuItem();
        mnuModify = new javax.swing.JMenu();
        mnuAdd = new javax.swing.JMenu();
        mnuAddNatureNode = new javax.swing.JMenuItem();
        mnuAddDecisionNode = new javax.swing.JMenuItem();
        mnuAddUtilNode = new javax.swing.JMenuItem();
        mnuAddLinkNode = new javax.swing.JMenuItem();
        mnuAddModelNode = new javax.swing.JMenuItem();
        mnuNetwork = new javax.swing.JMenu();
        mnuCompile = new javax.swing.JMenuItem();
        mnuExpand = new javax.swing.JMenuItem();
        mnuWiz = new javax.swing.JMenu();
        mnuLearning = new javax.swing.JMenuItem();
        mnuHelp = new javax.swing.JMenu();
        mnuAbout = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Nutus");
        setBackground(new java.awt.Color(255, 255, 255));
        setFont(new java.awt.Font("DialogInput", 0, 10)); // NOI18N

        jToolBar1.setRollover(true);

        jMenuBar1.setFont(new java.awt.Font("Liberation Sans", 0, 13)); // NOI18N

        mnuFile.setText("File");

        mnuNew.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        mnuNew.setText("New");
        mnuNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuNewActionPerformed(evt);
            }
        });
        mnuFile.add(mnuNew);

        mnuClear.setText("Clear");
        mnuClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuClearActionPerformed(evt);
            }
        });
        mnuFile.add(mnuClear);

        mnuOpen.setText("Open");
        mnuOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuOpenActionPerformed(evt);
            }
        });
        mnuFile.add(mnuOpen);

        mnuSave.setText("Save");
        mnuSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuSaveActionPerformed(evt);
            }
        });
        mnuFile.add(mnuSave);

        mnuExit.setText("Exit");
        mnuExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuExitActionPerformed(evt);
            }
        });
        mnuFile.add(mnuExit);

        jMenuBar1.add(mnuFile);

        mnuModify.setText("View");

        mnuAdd.setText("Add");

        mnuAddNatureNode.setText("Nature Node");
        mnuAddNatureNode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuAddNatureNodeActionPerformed(evt);
            }
        });
        mnuAdd.add(mnuAddNatureNode);

        mnuAddDecisionNode.setText("Decision Node");
        mnuAddDecisionNode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuAddDecisionNodeActionPerformed(evt);
            }
        });
        mnuAdd.add(mnuAddDecisionNode);

        mnuAddUtilNode.setText("Utility Node");
        mnuAddUtilNode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuAddUtilNodeActionPerformed(evt);
            }
        });
        mnuAdd.add(mnuAddUtilNode);

        mnuAddLinkNode.setText("Link Node");
        mnuAddLinkNode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuAddLinkNodeActionPerformed(evt);
            }
        });
        mnuAdd.add(mnuAddLinkNode);

        mnuAddModelNode.setText("Model Node");
        mnuAddModelNode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuAddModelNodeActionPerformed(evt);
            }
        });
        mnuAdd.add(mnuAddModelNode);

        mnuModify.add(mnuAdd);

        jMenuBar1.add(mnuModify);

        mnuNetwork.setText("Network");

        mnuCompile.setText("Compile");
        mnuCompile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuCompileActionPerformed(evt);
            }
        });
        mnuNetwork.add(mnuCompile);

        mnuExpand.setText("Expand Time");
        mnuExpand.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuExpandActionPerformed(evt);
            }
        });
        mnuNetwork.add(mnuExpand);

        jMenuBar1.add(mnuNetwork);

        mnuWiz.setText("Wizards");

        mnuLearning.setText("Learning Wizard");
        mnuWiz.add(mnuLearning);

        jMenuBar1.add(mnuWiz);

        mnuHelp.setText("Help");

        mnuAbout.setText("About");
        mnuHelp.add(mnuAbout);

        jMenuBar1.add(mnuHelp);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(253, 253, 253)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(253, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(221, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mnuExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuExitActionPerformed
        // TODO add your handling code here:
        System.exit(1);
    }//GEN-LAST:event_mnuExitActionPerformed

    private void mnuAddNatureNodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuAddNatureNodeActionPerformed
        // TODO add your handling code here:

        Node node=new Node();
        node.setKind(node_type.NATURE_NODE);
        
        ShapeNode s=new NatureNode(this,node);
        nodes.add(s);
        
        s.repaint();
        this.myContentPane.add(s);

    }//GEN-LAST:event_mnuAddNatureNodeActionPerformed
 ShapeNode clickedNode=null;
int prevDragX;  // During dragging, these record the x and y coordinates
      int prevDragY;
    private void mnuAddDecisionNodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuAddDecisionNodeActionPerformed
        // TODO add your handling code here:
        Node node=new Node();
        node.setKind(node_type.DECISION_NODE);

        ShapeNode s=new DecisionNode(this,node);
        nodes.add(s);
        
        s.repaint();
        this.myContentPane.add(s);
    }//GEN-LAST:event_mnuAddDecisionNodeActionPerformed

    private void mnuAddUtilNodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuAddUtilNodeActionPerformed
        // TODO add your handling code here:
        Node node=new Node();
        node.setKind(node_type.UTILITY_NODE);

        ShapeNode s=new UtilityNode(this,node);
        nodes.add(s);
        
        s.repaint();
         this.myContentPane.add(s);
    }//GEN-LAST:event_mnuAddUtilNodeActionPerformed

    private void mnuAddLinkNodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuAddLinkNodeActionPerformed
        // TODO add your handling code here:
        Node node=new Node();
        node.setKind(node_type.LINK_NODE);

        ShapeNode s=new UpdateLinkNode(this,node);
        nodes.add(s);
       
        s.repaint();
        this.myContentPane.add(s);
    }//GEN-LAST:event_mnuAddLinkNodeActionPerformed

    public void clearWorkspace(){
        try{
        for(int i=0;i<nodes.size();i++)
        {
            if((ShapeNode)nodes.get(i)!=null){
            this.myContentPane.remove((ShapeNode)nodes.get(i));
            ((ShapeNode)nodes.get(i)).Clear();
            }
        }
        nodes.clear();
        this.delayedLinks.clear();
        this.policyLinks.clear();
        modelNodes.clear();
        
        
        //repaint();
         this.myContentPane.getParentContainer().repaint();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        finally{
            modelCounter =0;
             
        }
        
    }
    private void mnuClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuClearActionPerformed
        // TODO add your handling code here:
        clearWorkspace();
       // this.myContentPane.update(this.getGraphics());
    }//GEN-LAST:event_mnuClearActionPerformed

    private void mnuNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuNewActionPerformed
        // TODO add your handling code here:
        clearWorkspace(); 
        //this.myContentPane.update(this.getGraphics());
    }//GEN-LAST:event_mnuNewActionPerformed

    private void mnuSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuSaveActionPerformed
       Boolean isIID = modelCounter > 0? true: false; 
       idFilter = new UIFileFilter("id","ID Files");
       iidFilter = new UIFileFilter("iid","IID Files");
       didFilter = new UIFileFilter("did","DID Files");
       ididFilter = new UIFileFilter("idid","IDID Files");
       allFilter = new UIFileFilter(new String[] {"id","iid","did","idid"} ,"All Files");
       
      
       JFileChooser fc = new JFileChooser();
       
        if(!isIID &&  this.delayedLinks.size() <1){
            fc.addChoosableFileFilter(idFilter);
        }
        
        if(isIID){
            fc.addChoosableFileFilter(iidFilter);
            fc.addChoosableFileFilter(ididFilter);
            fc.setFileFilter(iidFilter);
        }
        else if(this.delayedLinks.size()>0){
            fc.addChoosableFileFilter(didFilter);
        }
        
        
        
       
        
       
       fc.setAcceptAllFileFilterUsed(false);
      
       fc.setCurrentDirectory(new File("."));
       
       int retval = fc.showSaveDialog(this);
       
       String filename ="",filename2="";
       if(retval==JFileChooser.APPROVE_OPTION){
           
           FileFilter myfileFilter = fc.getFileFilter();
           
           
           File file = fc.getSelectedFile();
           //filename=FileNameParser.GetFullName(file.getName(), file.getName());
           if(!allFilter.accept(file) && !isIID){
               filename2=file.getName() + ".iid";
           }
           else if(!allFilter.accept(file) && this.delayedLinks.size()>0){
               filename2=file.getName() + ".did";
           }
           else if(!allFilter.accept(file) && this.delayedLinks.size()>0 && isIID){
               filename2=file.getName() + ".idid";
           }
           
           else if(!allFilter.accept(file)){
               String ext = "";
               if(myfileFilter.getDescription().split(" ").length > 0){ 
                     String[] descArr =  myfileFilter.getDescription().split(" ");
                     ext = descArr[0];
                 }
               else{
                     ext =  myfileFilter.getDescription().toString();
               }
               filename2=file.getName() + "." + ext.toLowerCase();
           }
           else{
                filename2=file.getName();
           }
           Boolean isDID =false;
           isDID = !isIID ;
           this._persistShapeNode = new PersistShapeNode(filename2,isDID);
          // if(this._persistShapeNode.netFileName.isEmpty()){
              
          // }
        
        try{
           // this._persistShapeNode.WriteObject((ShapeNode)nodes.get(0));
            this._persistShapeNode.WriteListObject(nodes);
            this._persistShapeNode.netFileName=FileNameParser.getFilePathLessExt(filename2);
            savePendingObjects(filename2);
            JOptionPane.showMessageDialog(null, "Network successfully saved." );
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
       }
       else{
           
           javax.swing.JOptionPane.showMessageDialog(null, "Saving network aborted by user...");
       }
        this.setTitle("Nutus: " + filename2);
    }//GEN-LAST:event_mnuSaveActionPerformed

    
    private void mnuOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuOpenActionPerformed
        
        
      idFilter = new UIFileFilter("id","ID Files");
       iidFilter = new UIFileFilter("iid","IID Files");
       didFilter = new UIFileFilter("did","DID Files");
       ididFilter = new UIFileFilter("idid","IDID Net Files");
       allFilter = new UIFileFilter(new String[] {"id","iid","did","idid"} ,"All Files");
       
      
       JFileChooser fc = new JFileChooser();
       
        fc.addChoosableFileFilter(idFilter);
        fc.addChoosableFileFilter(iidFilter);
        fc.addChoosableFileFilter(didFilter);
        fc.addChoosableFileFilter(ididFilter);
        fc.addChoosableFileFilter(allFilter);
        fc.setFileFilter(allFilter);
       
       fc.setAcceptAllFileFilterUsed(false);
      
       fc.setCurrentDirectory(new File("."));
       
       int retval = fc.showOpenDialog(this);
       
       String filename ="";
       if(retval==JFileChooser.APPROVE_OPTION){
           
           File file = fc.getSelectedFile();
           filename=file.getName();
       }
       else{
           
           javax.swing.JOptionPane.showMessageDialog(null, "Please, select a file to view network");
           
       }
       
       if(!filename.isEmpty()){
            clearWorkspace();
            getSavedNetwork(filename);
            this.myContentPane.repaint();
       }
       this.setTitle("Nutus: " + filename);
    }//GEN-LAST:event_mnuOpenActionPerformed

    private void mnuAddModelNodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuAddModelNodeActionPerformed
        
        Node node=new Node();
        node.setKind(node_type.MODEL_NODE);

        ShapeNode s=new ModelNode(this,node);
        nodes.add(s);
        
        s.repaint();
        this.myContentPane.add(s);
    }//GEN-LAST:event_mnuAddModelNodeActionPerformed

    private void mnuCompileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuCompileActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mnuCompileActionPerformed

    private void mnuExpandActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuExpandActionPerformed
        // TODO add your handling code here:
        expandTime2();
    }//GEN-LAST:event_mnuExpandActionPerformed

    private  void getSavedNetwork(String filename){
        
         //  List myList = null;
       // nodes = null;
        ArrayList myList =null;
        ShapeNode retrievedShape = null;
        ShapeNode shapeNode = null;
        String kind ="";
        int x, y =0;
        this._retrievedShapeNode = new RetrieveShapeNode(filename);
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
                     ((DecisionNode)shapeNode).rootpane=this;
                   }
                   else if(kind.equals(node_type.LINK_NODE.toString())){
                      // shapeNode= new LinkNode(this,retrievedShape.node);
                       
                      shapeNode = (LinkNode)retrievedShape;   
                     ((LinkNode)shapeNode).setNodeId(shapeNode.childId);
                      ((LinkNode)shapeNode).setNodeName(shapeNode.node.name);
                     ((LinkNode)shapeNode).rootpane=this;
                     
                     if(shapeNode.label.equalsIgnoreCase("Delay")){
                       //((LinkNode)shapeNode).parent.dalayLinks.add(shapeNode);
                        this.delayedLinks.add(shapeNode);
                     }
                      
                  }
                  else if(kind.equals(node_type.UPDATE_LINK_NODE.toString())){
                      // shapeNode= new LinkNode(this,retrievedShape.node);
                       
                      shapeNode = (UpdateLinkNode)retrievedShape;   
                     ((UpdateLinkNode)shapeNode).setNodeId(shapeNode.childId);
                      ((UpdateLinkNode)shapeNode).setNodeName(shapeNode.node.name);
                     ((UpdateLinkNode)shapeNode).rootpane=this;
                      
                  }
                   else if(kind.equals(node_type.MODEL_NODE.toString())){
                        shapeNode = (ModelNode)retrievedShape;   
                     ((ModelNode)shapeNode).setNodeId(shapeNode.childId);
                     ((ModelNode)shapeNode).setNodeName(shapeNode.node.name);
                     ((ModelNode)retrievedShape).rootpane=this;
                     if(shapeNode.expand) {
                         --InfluenceGUI.modelCounter;
                     }
                     modelNodes.add(shapeNode);
                   }
                   else if(kind.equals(node_type.NATURE_NODE.toString())){
                        shapeNode = (NatureNode)retrievedShape;   
                     ((NatureNode)shapeNode).setNodeId(shapeNode.childId);
                     ((NatureNode)shapeNode).setNodeName(shapeNode.node.name);
                      ((NatureNode)retrievedShape).rootpane=this;
                   }
                   else if(kind.equals(node_type.UTILITY_NODE.toString())){
                    shapeNode = (UtilityNode)retrievedShape;   
                     ((UtilityNode)shapeNode).setNodeId(shapeNode.childId);
                      ((UtilityNode)shapeNode).setNodeName(shapeNode.node.name);
                      ((UtilityNode)retrievedShape).rootpane=this;
                   }
                   else if(kind.equals(node_type.MODEL_LINK_NODE.toString())){
                      shapeNode = (ModelLinkNode)retrievedShape;   
                     ((ModelLinkNode)shapeNode).setNodeId(shapeNode.childId);
                      ((ModelLinkNode)shapeNode).setNodeName(shapeNode.node.name);
                      ((ModelLinkNode)retrievedShape).rootpane=this;
                   }
                   // shapeNode.moveBy(x, y);
                   // int myInset =100;
                    shapeNode.node.label=shapeNode.label;
                    shapeNode.repaint();
                    int newWidth = Math.max((shapeNode.getWidth()+ shapeNode.x), this.getPreferredSize().width);
                    int newHeight = Math.max((shapeNode.getHeight()+ shapeNode.y), this.getPreferredSize().height) ;
                    
                    retrievedShape.rootpane=this;
                    this.myContentPane.add(shapeNode);
                    nodes.add(shapeNode);
                    this.myContentPane.setPreferredSize(new Dimension(newWidth,newHeight));
                }
           // javax.swing.JOptionPane.showMessageDialog(null, shapeNode.node.getKind().toString()); 
           // repaint();
               // this.setComponentZOrder(shapeNode, 0);
            
        }
        catch(Exception ex){
           ex.printStackTrace();
        }
        
    }
  
    private void savePendingObjects(String ContainerFileName){
          
          String folderName = FileNameParser.getFilePathLessExt(ContainerFileName);
          // Get the set interface to get the key and value pair
          final Set<Map.Entry<String,InfluenceGUI>> set = this.pendingSaveObjects.entrySet();
          final List<String> keyList = new ArrayList<String>(this.pendingSaveObjects.keySet());
          final int size = keyList.size();
          int longestPathLen ;
          
          if(size >0){
          //Get the iterator for the interface
          Iterator it = set.iterator();
          final String longestPath = keyList.get(size-1).toString();
          String netFileNameTemp = "";
          String delim = "/";
          String[] longestPathArr = longestPath.split("[/\\\\]");
          longestPathLen = longestPathArr.length-1;
          //while(it.hasNext()){
          for(int i =0;i<size;i++){
              
          //Map.Entry ele = (Map.Entry)it.next();
          Map.Entry ele = (Map.Entry)it.next();
          InfluenceGUI myframe = (InfluenceGUI)this.pendingSaveObjects.get(keyList.get(i));
          String fileName = String.format("%s%s",folderName,keyList.get(i).toString());
          String fileName2 =fileName;
          Boolean isDID = myframe.modelCounter > 0? false: true;
          Boolean isNewPath =false;
         
          Path myPath = Paths.get(FileNameParser.getFolderPath(fileName));
          if(Files.notExists(myPath)){
              isNewPath=true;
              fileName = fileName.format("%s%s",FileNameParser.getNumericFolderName(myPath.toString(), 
                         longestPathLen),FileNameParser.getFileNameOnly(fileName));
          }
          PersistShapeNode persistShapeNode = new PersistShapeNode(fileName,isDID,longestPathLen);
        
        try{
            persistShapeNode.setFileName(fileName2);
            Path oldPath = Paths.get(FileNameParser.getNumericFolderName(myPath.toString(),longestPathLen));
            
            if(!Paths.get(persistShapeNode.netFileName).equals(oldPath) && i>0){
                persistShapeNode.netFileName = FileNameParser.getFolderPath(fileName);
                netFileNameTemp = persistShapeNode.netFileName;
            }
            else if(Paths.get(persistShapeNode.netFileName).equals(oldPath)){
                // do nothing
               netFileNameTemp= persistShapeNode.netFileName;
            }
            else{
                 if(i==0){
                 persistShapeNode.netFileName=fileName2.format("%s%s",FileNameParser.getNumericFolderName(FileNameParser.getFilePathLessExt(fileName).toString(), 
                                                            longestPathLen),"");
                 }
            }
           persistShapeNode.WriteListObject(myframe.nodes); 
        }
        catch(Exception ex){
           ex.printStackTrace();
        }
       }
          this.pendingSaveObjects.clear();
      }
          
    }
    
    
     private  void expandTime(){
        
         //  List myList = null;
       // nodes = null;
        ArrayList myList = new ArrayList();
        myList.addAll(this.nodes);
        ShapeNode retrievedShape = null;
        
        DecisionNode decNode =null;
        NatureNode   obsNode = null;
        NatureNode   stateNode = null;
        UtilityNode utilNode = null;
        
        DecisionNode decNode0 =null;
        NatureNode   obsNode0 = null;
        NatureNode   stateNode0 = null;
        UtilityNode utilNode0 = null;
        
        ShapeNode shapeNode = null;
        LinkNode lnk=null;
        String kind ="";
        int x, xMax=0, y =0;
        
        try{
             
             
               // Get maximum horizontal location
            
            for(Object myShape : myList){
                    
                    retrievedShape = (ShapeNode)myShape;
                    x=retrievedShape.x;
                    xMax =( x > xMax) ? x : xMax;
            }
               
                
                for(Object myShape : myList){
                    
                    retrievedShape = (ShapeNode)myShape;
                    kind=retrievedShape.node.getKind().toString(); 
                    
                    x=retrievedShape.x;
                    y=retrievedShape.y;
                
                   if(kind.equals(node_type.LINK_NODE.toString())) continue;
                   if(kind.equals(node_type.DECISION_NODE.toString())){
                    
                    try{
                     Node node= new Node();
                     node.states= retrievedShape.node.states == null? null: retrievedShape.node.states.clone();
                     node.CPT= retrievedShape.node.CPT ==null? null: retrievedShape.node.CPT.clone();
                     
                     
                     Node node2 = new Node();
                     node.setKind(node_type.DECISION_NODE);
                     node2.setKind(node_type.LINK_NODE);
                     shapeNode =new DecisionNode(this,node); 
                   
                             
                      
                     lnk = new LinkNode(this,node2);
                     lnk.linkNodeColor = new Color(142,42,42);
                     shapeNode.x = xMax + x + 100;
                     shapeNode.y = y ;// ((DecisionNode)shapeNode).setNodeId(shapeNode.childId);
                    //  ((DecisionNode)shapeNode).setNodeName(shapeNode.node.name);
                     ((DecisionNode)shapeNode).rootpane=this;
                     
                      ShapeNode prnt= (ShapeNode)retrievedShape;
                      ShapeNode chld=shapeNode;
                      decNode = (DecisionNode)shapeNode;
                      decNode0= (DecisionNode)retrievedShape;
                      decNode.label= retrievedShape.label.replaceAll("\\d+", "2");
                      decNode.fullname= retrievedShape.node.containerName.replaceAll("\\d+", "2");
                      decNode.node.name=decNode.fullname;
                      decNode.node.label=decNode.label;
                      
                      lnk.makeLink(prnt,chld);
                    
                    }catch(Exception ex){ex.printStackTrace();}
                    
                    
                   }
                   else if(kind.equals(node_type.NATURE_NODE.toString())){
                     Node node= new Node();
                     node.states= retrievedShape.node.states == null? null: retrievedShape.node.states.clone();
                     node.CPT= retrievedShape.node.CPT ==null? null: retrievedShape.node.CPT.clone();
                     Node node2 = new Node();
                     node.setKind(node_type.NATURE_NODE);
                     node2.setKind(node_type.LINK_NODE);
                     
                     shapeNode =new NatureNode(this,node); 
                     
                     shapeNode.x = xMax + x + 100;
                     shapeNode.y = y ;// ((DecisionNode)shapeNode).setNodeId(shapeNode.childId);
                    //  ((DecisionNode)shapeNode).setNodeName(shapeNode.node.name);
                     ((NatureNode)shapeNode).rootpane=this;
                     
                      ShapeNode prnt= (ShapeNode)retrievedShape;
                      ShapeNode chld=shapeNode;
                     
                      if(retrievedShape.label.toUpperCase().startsWith("S")){
                        lnk = new LinkNode(this,node2);
                        lnk.linkNodeColor = lnk.linkColorBrown;
                        stateNode = (NatureNode)shapeNode;
                        stateNode0= (NatureNode)retrievedShape;
                        stateNode.label= retrievedShape.label.replaceAll("\\d+", "2");
                        stateNode.fullname= retrievedShape.node.containerName.replaceAll("\\d+", "2");
                        stateNode.node.name=stateNode.fullname;
                        stateNode.node.label=stateNode.label;
                        
                        try{
                        lnk.makeLink(prnt,chld);
                        }catch(Exception ex ){ex.printStackTrace();}
                      }
                      else{
                          obsNode = (NatureNode)shapeNode;
                          obsNode0= (NatureNode)retrievedShape;
                          obsNode.label= retrievedShape.label.replaceAll("\\d+", "2");
                          obsNode.fullname= retrievedShape.node.containerName.replaceAll("\\d+", "2");
                          obsNode.node.name=obsNode.fullname;
                           obsNode.node.label=obsNode.label;
                      }
                     
                   }
                   else if(kind.equals(node_type.UTILITY_NODE.toString())){
                     Node node= new Node();
                     //node.states= retrievedShape.node.states == null? null: retrievedShape.node.states.clone();
                     node.CPT= retrievedShape.node.CPT ==null? null: retrievedShape.node.CPT.clone();
                     node.setKind(node_type.UTILITY_NODE);
                     shapeNode =new UtilityNode(this,node); 
                     
                      
                     shapeNode.x = xMax + x + 100;
                     shapeNode.y = y ;// ((DecisionNode)shapeNode).setNodeId(shapeNode.childId);
                    //  ((DecisionNode)shapeNode).setNodeName(shapeNode.node.name);
                     ((UtilityNode)shapeNode).rootpane=this;
                     
                      //ShapeNode prnt= (ShapeNode)retrievedShape;
                     // ShapeNode chld=shapeNode;
                      utilNode = (UtilityNode)shapeNode;
                      utilNode0 = (UtilityNode)retrievedShape;
                      utilNode.label= retrievedShape.label.replaceAll("\\d+", "2");
                      utilNode.fullname= retrievedShape.node.containerName.replaceAll("\\d+", "2");
                      utilNode.node.name=utilNode.fullname;
                      utilNode.node.label=utilNode.label;
                   }
                   else if(kind.equals(node_type.MODEL_LINK_NODE.toString())){
                      shapeNode = (ModelLinkNode)retrievedShape;   
                     ((ModelLinkNode)shapeNode).setNodeId(shapeNode.childId);
                      ((ModelLinkNode)shapeNode).setNodeName(shapeNode.node.name);
                      ((ModelLinkNode)retrievedShape).rootpane=this;
                   }
                   try{
                    shapeNode.repaint();
                    int newWidth = Math.max((shapeNode.getWidth()+ shapeNode.x), this.getPreferredSize().width);
                    int newHeight = Math.max((shapeNode.getHeight()+ shapeNode.y), this.getPreferredSize().height) ;
                    
                    retrievedShape.rootpane=this;
                    this.myContentPane.add(shapeNode);
                    if(lnk!=null){
                     
                     this.myContentPane.add(lnk);
                     nodes.add(lnk);
                    }
                    nodes.add(shapeNode);
                    
                    this.myContentPane.setPreferredSize(new Dimension(newWidth,newHeight));
                   }catch(Exception ex){ex.printStackTrace();}
                }
                        
                        LinkNode tempLnk1 = new LinkNode(this,new Node());
                        tempLnk1.node.setKind(node_type.LINK_NODE);
                        LinkNode tempLnk2 = new LinkNode(this,new Node());
                        tempLnk2.node.setKind(node_type.LINK_NODE);
                        LinkNode decRewardLnk = new LinkNode(this,new Node());
                        decRewardLnk.node.setKind(node_type.LINK_NODE);
                        LinkNode obsStateLnk = new LinkNode(this,new Node());
                        obsStateLnk.node.setKind(node_type.LINK_NODE);
                        LinkNode obsDecLnk = new LinkNode(this,new Node());
                        obsDecLnk.node.setKind(node_type.LINK_NODE);
                        LinkNode stateRewardLnk = new LinkNode(this, new Node());
                        stateRewardLnk.node.setKind(node_type.LINK_NODE);
                        tempLnk1.linkNodeColor = tempLnk1.linkColorBrown;
                        tempLnk2.linkNodeColor=tempLnk2.linkColorBrown;
                       
                        try{
                            tempLnk1.makeLink(decNode0,obsNode);
                            tempLnk2.makeLink(decNode0,stateNode);
                            obsDecLnk.makeLink(obsNode, decNode);
                            obsStateLnk.makeLink(stateNode, obsNode);
                            stateRewardLnk.makeLink(stateNode, utilNode);
                            decRewardLnk.makeLink(decNode, utilNode);
                            
                            this.nodes.add(tempLnk1);
                            this.nodes.add(tempLnk2);
                            this.nodes.add(obsDecLnk);
                            this.nodes.add(obsStateLnk);
                            this.nodes.add(stateRewardLnk);
                            this.nodes.add(decRewardLnk);
                            
                            
                        }catch(Exception ex ){ex.printStackTrace();}
            
        }
        catch(Exception ex){
           ex.printStackTrace();
        }
        
    }
    private  void expandTime2(){
        
         //  List myList = null;
       // nodes = null;
        if(delayedLinks.size()<1) return;
        ArrayList myList = new ArrayList();
        myList.addAll(this.nodes);
        ShapeNode retrievedShape = null;
        
        ShapeNode shapeNode = null;
        LinkNode lnk=null;
        String kind ="";
        int x, xMax=0, y =0;
        
        try{
             
             
               // Get maximum horizontal location
            
            for(Object myShape : myList){
                    
                    retrievedShape = (ShapeNode)myShape;
                    x=retrievedShape.x;
                    xMax =( x > xMax) ? x : xMax;
            }
               
                
                for(Object myShape : myList){
                    
                    retrievedShape = (ShapeNode)myShape;
                    kind=retrievedShape.node.getKind().toString();
                    
                    x=retrievedShape.x;
                    y=retrievedShape.y;
                
                   if(kind.equalsIgnoreCase(node_type.LINK_NODE.toString()) || kind.equalsIgnoreCase(node_type.MODEL_LINK_NODE.toString())){
                       continue;
                   }
                   
                   else {
                    try{
                    
                     Node node= new Node();
                     node.states= retrievedShape.node.states == null? null: retrievedShape.node.states.clone();
                     node.CPT= retrievedShape.node.CPT ==null? null: retrievedShape.node.CPT.clone();
                     node.CPT= retrievedShape.node.OldCPT ==null? null: retrievedShape.node.OldCPT.clone();
                     node.setKind(retrievedShape.node.getKind());
                     
                     if(kind.equalsIgnoreCase(node_type.NATURE_NODE.toString())) shapeNode = new NatureNode(this,node);
                     else if(kind.equalsIgnoreCase(node_type.DECISION_NODE.toString())) shapeNode = new DecisionNode(this,node);
                     else if(kind.equalsIgnoreCase(node_type.UTILITY_NODE.toString())) shapeNode = new UtilityNode(this,node);
                     else if(kind.equalsIgnoreCase(node_type.MODEL_NODE.toString())) shapeNode = new ModelNode(this,node);
                   
                                       
                     shapeNode.x = xMax + x + 100;
                     shapeNode.y = y ;// ((DecisionNode)shapeNode).setNodeId(shapeNode.childId);
                    //  ((DecisionNode)shapeNode).setNodeName(shapeNode.node.name);
                     (shapeNode).rootpane=this;
                      shapeNode.label= retrievedShape.label.replaceAll("\\d+", "1");
                      retrievedShape.label= retrievedShape.label.replaceAll("\\d+", "2"); // change the label to old time
                      shapeNode.fullname= retrievedShape.node.containerName.replaceAll("\\d+", "1");
                      retrievedShape.fullname= retrievedShape.node.containerName.replaceAll("\\d+", "2");
                      retrievedShape.node.label= retrievedShape.label;
                      retrievedShape.node.name=retrievedShape.fullname;
                      retrievedShape.node.containerName= retrievedShape.node.containerName.replaceAll("\\d+", "2");
                      shapeNode.node.name=shapeNode.fullname;
                      shapeNode.node.label=shapeNode.label;
                     // shapeNode.node.states= retrievedShape.node.states.clone();
                      shapeNode.node.CPT = new Object[retrievedShape.node.CPT.length][retrievedShape.node.CPT[0].length];
                      shapeNode.node.deepCopy2Dobject(retrievedShape.node.CPT, shapeNode.node.CPT);
                      shapeNode.node.OldCPT=  new Object[retrievedShape.node.CPT.length][retrievedShape.node.CPT[0].length];
                      shapeNode.node.deepCopy2Dobject(retrievedShape.node.OldCPT, shapeNode.node.OldCPT);
                      
                      if(kind.equalsIgnoreCase(node_type.MODEL_NODE.toString())){
                          //retrievedShape.ModelLinkChildLinkNode.child.label = retrievedShape.ModelLinkChildLinkNode.child.label.replaceAll("\\d+", "1");
                          //retrievedShape.ModelLinkParentLinkNode.parent.label = retrievedShape.ModelLinkParentLinkNode.parent.label.replaceAll("\\d+", "1");
                          ShapeNode child = retrievedShape.ModelLinkChildLinkNode.child;
                          ShapeNode parent =retrievedShape.ModelLinkParentLinkNode.parent;// 
                          
                        //  Node newNode = new Node();
                         // Node newNode2 = new Node();
                        //  newNode.setKind(node_type.MODEL_LINK_NODE);
                         // newNode2.setKind(node_type.LINK_NODE);
                        //  ModelLinkNode modelLink = new ModelLinkNode(this,newNode);
                          
                        //  modelLink.child = getNodeByLabel(child.label.replaceAll("\\d+", "1"));
                        //  LinkNode  newLink = new LinkNode(this,newNode2);
                         // newLink.parent = getNodeByLabel(parent.label.replaceAll("\\d+", "1"));
                          
                          shapeNode.ModelLinkChildLinkNode= new ModelLinkNode(); //new ModelLinkNode();//
                          shapeNode.ModelLinkChildLinkNode.node = new Node();
                          shapeNode.ModelLinkChildLinkNode.node.setKind(node_type.MODEL_LINK_NODE);
                          shapeNode.ModelLinkChildLinkNode.label=child.label.replaceAll("\\d+", "1");
                          shapeNode.ModelLinkParentLinkNode=new LinkNode();// new LinkNode();
                          shapeNode.ModelLinkParentLinkNode.node= new Node();// new LinkNode();
                          shapeNode.ModelLinkParentLinkNode.node.setKind(node_type.LINK_NODE);
                          ((ModelNode)shapeNode).agentsFileNames = ((ModelNode)retrievedShape).agentsFileNames.clone();
                          shapeNode.ModelLinkParentLinkNode.label = parent.label.replaceAll("\\d+", "1");
                          modelNodes.add(shapeNode);
                      }
                      
                    }catch(Exception ex){ex.printStackTrace();}
                    
                   }
                  
                   try{
                    shapeNode.repaint();
                    int newWidth = Math.max((shapeNode.getWidth()+ shapeNode.x), this.getPreferredSize().width);
                    int newHeight = Math.max((shapeNode.getHeight()+ shapeNode.y), this.getPreferredSize().height) ;
                    
                    retrievedShape.rootpane=this;
                    this.myContentPane.add(shapeNode);
                   
                    nodes.add(shapeNode);
                    
                    this.myContentPane.setPreferredSize(new Dimension(newWidth,newHeight));
                   }catch(Exception ex){ex.printStackTrace();}
                }
                this.OtherAgentXLoc = 2*xMax+100;  
                makeNewLinks();
        }
        catch(Exception ex){
           ex.printStackTrace();
        }
        
    }  
   
  public void AddOtherAgent(){
      Node node = new Node();
      node.setKind(node_type.NATURE_NODE);
      newNode = new NatureNode(this,node);
      newNode.label="OO1";
      newNode.node.label="OO1";;
      newNode.node.containerName="OO1";
      newNode.fullname="OO1";
      newNode.setFullName("OO1");
      
      newNode.x = this.OtherAgentXLoc;
      newNode.y =newNode.y + 600;
      //newNode.node.states= new String[1];
     // newNode.node.states[0]="S1";
      
       ShapeNode tempNode=getNodeByLabel("Mod2");
       if(tempNode!=null) createDependency(getNodeByLabel("Mod2"), newNode, true);
       tempNode=getNodeByLabel("Mod1");
       if(tempNode!=null) createDependency(newNode,getNodeByLabel("Mod1"),false);
       tempNode=getNodeByLabel("ODX2");
       if(tempNode!=null) createDependency(getNodeByLabel("ODX2"),newNode,true);
       tempNode=getNodeByLabel("SX1");
       if(tempNode!=null) createDependency(getNodeByLabel("SX1"),newNode,false);
       tempNode=getNodeByLabel("OSX1");
       if(tempNode!=null) createDependency(getNodeByLabel("OSX1"),newNode,false);
       this.nodes.add(newNode);
       this.myContentPane.add(newNode);
     
  }
  private void createDependency(ShapeNode parent, ShapeNode child , Boolean isTemporalLink){
        // Add Links
      Node node = new Node();
      node.setKind(node_type.LINK_NODE);
      LinkNode lnk = new LinkNode(this,node);
      if(isTemporalLink) lnk.linkNodeColor = LinkNode.linkColorDelay;
      lnk.parent = parent;
      lnk.child = child;
      lnk.makeLink(lnk.parent, lnk.child);
      this.nodes.add(lnk);
  }
  private void makeNewLinks(){
      
      ShapeNode retrievedShape = null;
       ArrayList myList = new ArrayList();
       String kind = null;
       myList.addAll(this.nodes);
      
       ShapeNode parent=new ShapeNode();
       ShapeNode child = new ShapeNode();
    
      // parent.setKind(node_type.LINK_NODE);
      // chld.setKind(node_type.LINK_NODE);
     
       try{
        
      for(Object myShape : myList){
            
            
            retrievedShape = (ShapeNode)myShape;
            kind=retrievedShape.node.getKind().toString();
                    
                  
       if(kind.equals(node_type.LINK_NODE.toString()) && !retrievedShape.label.equalsIgnoreCase("Delay")){
            Node node = new Node();
            node.setKind(node_type.LINK_NODE);
            LinkNode newLink = new LinkNode(this,node);
           String parentLabel = ((LinkNode)retrievedShape).parent.node.label.replaceAll("\\d+", "1");
           String chldLabel = ((LinkNode)retrievedShape).child.node.label.replaceAll("\\d+", "1");
           newLink.parent = getNodeByLabel(parentLabel);
           newLink.child =getNodeByLabel(chldLabel);
           newLink.makeLink(newLink.parent, newLink.child);  
           newLink.label = "LinkLabel";
           newLink.node.label="LinkLabel";
           nodes.add(newLink);
           this.myContentPane.add(newLink);
          
      }            
      else if(kind.equals(node_type.MODEL_LINK_NODE.toString()) && !retrievedShape.label.equalsIgnoreCase("Delay")){
            Node node = new Node();
            node.setKind(node_type.MODEL_LINK_NODE);
            ModelLinkNode newLink = new ModelLinkNode(this,node);
            String parentLabel = ((ModelLinkNode)retrievedShape).parent.node.label.replaceAll("\\d+", "1");
            String chldLabel = ((ModelLinkNode)retrievedShape).child.node.label.replaceAll("\\d+", "1");
            newLink.parent = getNodeByLabel(parentLabel);
            newLink.child =getNodeByLabel(chldLabel);
            newLink.makeLink(newLink.parent, newLink.child);  
            newLink.label = "ModelLinkLabel";
            newLink.node.label="ModelLinkLabel";
            newLink.parent.ModelLinkChildLinkNode = newLink;
            nodes.add(newLink);
            this.myContentPane.add(newLink);
          
      }
       else if(kind.equals(node_type.LINK_NODE.toString()) && retrievedShape.label.equalsIgnoreCase("Delay")){
            this.nodes.remove(retrievedShape);
            this.myContentPane.remove(retrievedShape);
       }
                   
                   
    }
      makeTemporalLinks();
       }catch(Exception ex){ex.printStackTrace();}
      
  }
  
   private void makeTemporalLinks(){
      
      ShapeNode retrievedShape = null;
       ArrayList myList = new ArrayList();
       String kind = null;
       myList.addAll(this.nodes);
      
       ShapeNode parent=new ShapeNode();
       ShapeNode child = new ShapeNode();
    
      // parent.setKind(node_type.LINK_NODE);
      // chld.setKind(node_type.LINK_NODE);
     
       try{
        
      for(Object myShape : myList){
            
            
            retrievedShape = (ShapeNode)myShape;
            kind=retrievedShape.node.getKind().toString();
                    
                  
      for(int i=0 ; i < retrievedShape.dalayLinks.size(); i++){
          
           parent = getNodeByLabel(retrievedShape.label.toString().replaceAll("\\d+", "2"));
           child =getNodeByLabel(retrievedShape.dalayLinks.get(i).toString().replaceAll("\\d+", "1"));
           
           
           if((parent.node.getKind().toString().equalsIgnoreCase(node_type.MODEL_NODE.toString())) &&
               child.node.getKind().toString().equalsIgnoreCase(node_type.MODEL_NODE.toString())){
                          Node node = new Node();

               UpdateLinkNode newLink = new UpdateLinkNode(this,node);
               node.setKind(node_type.LINK_NODE);
               newLink.parent = parent;
               newLink.child =child;
               newLink.makeLink(newLink.parent, newLink.child);   
               newLink.label = LinkNode.DelayLinkLabel;
               newLink.node.label = LinkNode.DelayLinkLabel;
               nodes.add(newLink);
               policyLinks.add(newLink);
                this.myContentPane.add(newLink);
           }
           else{
                          Node node = new Node();

               LinkNode newLink = new LinkNode(this,node);
               newLink.linkNodeColor = LinkNode.linkColorDelay;
               node.setKind(node_type.LINK_NODE);
               newLink.parent = parent;
               newLink.child =child;
               newLink.makeLink(newLink.parent, newLink.child);   
               newLink.label = LinkNode.DelayLinkLabel;
               newLink.node.label = LinkNode.DelayLinkLabel;
               nodes.add(newLink);
                this.myContentPane.add(newLink);
           }
          
           
          
           
          
      }
                   
                   
    }
       }catch(Exception ex){ex.printStackTrace();}
      
  }
    /**
    * @param args the command line arguments
    */
   public class MainFocusListener extends FocusAdapter{
       @Override
       public void focusGained(FocusEvent e){
          // System.out.println("Gained");
       }
        @Override
       public void focusLost(FocusEvent e){
           
       }
   }
    public static void main(String args[]) {
       
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                InfluenceGUI myFrame = new InfluenceGUI();
              
                myFrame.setTitle("Nutus");
               // myFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
               
                
                myFrame.setSize(800, 600);
                myFrame.setLocationRelativeTo(null);
               // FrameDecorator.CenterFrame(myFrame, myFrame);
                myFrame.pack();
                
                myFrame.setVisible(true);
                //new InfluenceGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JMenuItem mnuAbout;
    private javax.swing.JMenu mnuAdd;
    private javax.swing.JMenuItem mnuAddDecisionNode;
    private javax.swing.JMenuItem mnuAddLinkNode;
    private javax.swing.JMenuItem mnuAddModelNode;
    private javax.swing.JMenuItem mnuAddNatureNode;
    private javax.swing.JMenuItem mnuAddUtilNode;
    private javax.swing.JMenuItem mnuClear;
    private javax.swing.JMenuItem mnuCompile;
    private javax.swing.JMenuItem mnuExit;
    private javax.swing.JMenuItem mnuExpand;
    private javax.swing.JMenu mnuFile;
    private javax.swing.JMenu mnuHelp;
    private javax.swing.JMenuItem mnuLearning;
    private javax.swing.JMenu mnuModify;
    private javax.swing.JMenu mnuNetwork;
    private javax.swing.JMenuItem mnuNew;
    private javax.swing.JMenuItem mnuOpen;
    private javax.swing.JMenuItem mnuSave;
    private javax.swing.JMenu mnuWiz;
    // End of variables declaration//GEN-END:variables

    public void mouseClicked(MouseEvent e) {
        
        
        //javax.swing.JOptionPane.showMessageDialog(null,e.getComponent().getName());
       
        
    }

    public void mousePressed(MouseEvent e) {
       requestFocusInWindow();
    }

    public void mouseReleased(MouseEvent e) {
        
    }

    public void mouseEntered(MouseEvent e) {
        
    }

    public void mouseExited(MouseEvent e) {
        
    }

    public void focusGained(FocusEvent e) {
       // System.out.println("Parent gain focus");
    }

    public void focusLost(FocusEvent e) {
        //System.out.println("Parent lost focus");
    }

}
