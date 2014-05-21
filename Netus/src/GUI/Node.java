/**
 Node.java
 */
package GUI;

import GUI.Util.UIFileFilter;
import java.awt.Component;
import java.io.BufferedReader;
import java.io.Externalizable;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

 enum node_type {
   NATURE_NODE, DECISION_NODE, UTILITY_NODE, LINK_NODE, MODEL_NODE,
   MODEL_LINK_NODE,UPDATE_LINK_NODE;

           @Override public String toString() {
   //only capitalize the first letter
   String s = super.toString();
   return s.toUpperCase();
 }
   {

   }
 }



public class Node  implements Externalizable, Cloneable{

    private static final long serialVersionUID = -1195690980855994923L;
    String name;
    String containerName;
    node_type type;
    String[] states;
    ArrayList allparentsStates;
    ArrayList children ;
    ArrayList allparentsValues;
    ArrayList repeatStates;
    Object[] statevalues;
    Node parent;
    ArrayList allparents;
    Object[][] CPT;
    String finding;
    int numberOfParentStates; 
    boolean linkAdded;
    Object[][] OldCPT;
    int CPTzindex;
    String label;
    
    private static int nodeCounter=0;
    private  int nodeId ;
    //------------------------------------------------------------ constructors
   public Node()
   {
       name="Node";
       containerName="";
       type=null;
      // states=new String[1];
      // states[0]="S1";
       statevalues=null;
       allparentsStates =new ArrayList();
       allparentsValues =new ArrayList();
       repeatStates = new ArrayList<Integer>();
       children = new ArrayList();
       OldCPT=null;
       parent=null;
       allparents=new ArrayList();
       CPT=null;
       ++nodeCounter;
       this.nodeId=nodeCounter;
       numberOfParentStates=0;
       CPTzindex=0;
   }
   
   @Override
   public Object clone(){
       Node aClone=null;
       
       try{
           aClone = (Node)super.clone();
           
           aClone.CPT= aClone.CPT ==null? null : this.CPT.clone();
           aClone.OldCPT = aClone.OldCPT==null?null:this.OldCPT.clone();
           aClone.allparents= aClone.allparents==null? null: (ArrayList)this.allparents.clone();
           aClone.allparentsStates = aClone.allparentsStates==null? null :(ArrayList)this.allparents.clone();
           aClone.states = aClone.states==null ? null:this.states.clone();
           
       }catch(Exception ex){ex.printStackTrace();}
       return aClone;
       
   }
   
   
   
    public Node (String name, int num_states){
	this.name=name;
        states=new String[num_states];
        ++nodeCounter;
        this.nodeId= nodeCounter;
  
    }
    @Override
    public String toString(){
         
            return this.getFullName();
        
    }
    public void setNumstates(int n)
    {
        states=new String[n];
        statevalues=new Object[n];
        this.CPT=new Object[n][2];
        this.OldCPT= new Object[n][2];
        /*
        for(int i=0;i<n;i++)
        {
            this.CPT[i][1]=0.0;
            statevalues[i]=0.0;
        }*/
        //this.OldCPT= this.CPT;
    }
    
    public void setCPTstates(){
        int i;
        //set the node states
        int len = this.CPT.length;
        double value = (1.00/len);
        if(this.states !=null){
            for(i=0;i<this.CPT.length;i++){
                this.CPT[i][0]=this.states[i];
                for(int j =1 ; j < CPT[0].length; j++){
                    this.CPT[i][j]= value;
                    //this.CPT[i][len-1]=1.00-value;
                }
               
            }
            //this.OldCPT= this.CPT;
        }
        /*** Utility node has no states ***
         *   Use the CPT labels instead
         */
        else if (this.CPT!=null){ 
            
            for(i=0;i<CPT.length;i++){
                this.CPT[i][0]=allparentsStates.get(i);
            }
            
        }
    }
    
    public int getNodeId(){
        
        return this.nodeId;
    }
    
    public void setFindingState(String fstate)
    {
        finding=fstate;
    }
    public String getFindingState()
    {
       return finding;
    }
    public int getFindingStateindex()
    {
        int i=0;
       for(i=0;i<this.getNumstates();i++)
       {
           if(this.states[i].equalsIgnoreCase(finding))
               break;
       }
       return i;
    }
    public int getNumstates()
    {
        if(this.states!=null){
            return this.states.length;
        }
        return 0;
    }
public void setCPTable(Double[] values)
{
    //parent=null;
    CPT=new Object[1][values.length];
    CPT[0]=values;
}

public void setCPTable(Double[][] values,int from, int to)
{
    //parent=null;
  
   if(CPT!=null){
       
        //setCPTstates();
        int i =0;
        int j;
        for(i=0;i<CPT.length;i++){
            for(j=from;j<to;j++)
            CPT[i][j]=values[i][j-from];
     
        }
   }
     
     
}
public void importCPT(Component par){
    
                int i,j;
              try{
                JFileChooser fc = new JFileChooser();
                fc.setCurrentDirectory(new File("."));
                fc.setMultiSelectionEnabled(false);
                UIFileFilter filter = new UIFileFilter("txt") ;
                fc.addChoosableFileFilter(filter);
               
                int retval = fc.showOpenDialog(par);
                File file = null;
                
            if(retval==JFileChooser.APPROVE_OPTION){
                String message;
                int len ;
                file = fc.getSelectedFile();
           
             
                Scanner scan = new Scanner(file);
                scan.useDelimiter("(\\s|,)");
                String line;
                int numberOfParents = (this.allparents.size() > 0) ? this.allparents.size(): 1 ;
                Double[][] data=new Double[this.CPT.length][this.CPT[0].length-numberOfParents];
                if(this.CPT !=null){           
                    for(i=0;i< data.length;i++){
                        for(j=0;j<data[0].length;j++)
                        while(scan.hasNext()){
                        if(scan.hasNextDouble()){
                             line = scan.next();
                        if("".equalsIgnoreCase(line) || " ".equals(line)){
                             data[i][j]= 0.0;
                        }
                        else{
                              data[i][j]= Double.parseDouble(line);
                              break;
                        }
                        }
                        try{
                            if(!scan.hasNextDouble()) scan.next();
                        }catch(Exception ex){}
                    }
                        
                   }
                     scan.close();
                     this.setCPTable(data,numberOfParents,this.CPT[0].length);
                     //javax.swing.JOptionPane.showMessageDialog(par, "CPT Imported Successfully.");
                     
                }
            }
            }catch(Exception ex){
                 javax.swing.JOptionPane.showMessageDialog(par, "Problem loading CPT values "+ ex.getMessage() );
            }
                
                 
}
public void computeCP_states(){
       
}
public void setCPTable(String parstate,Double[] values){
    int i=0;
for(;i<parent.states.length;i++){
    if(parent.states[i].equalsIgnoreCase(parstate))
        break;
}
    CPT[i]=values;
}

public void addLink(Node par){
   try{
    par.children.add(this);
    parent=par;
    if(parent!=null){
        
        
        allparents.add(parent);
        linkAdded=true;
        /*** Updates CPT if parent has states ***/
         if(parent.states==null){
            return;
         }
        repeatStates.add(1);
        updateRepeatStates(parent.getNumstates());
        UpdateCPT(par);
    }
    
   }catch(Exception ex){ex.printStackTrace();}
    
   
        
   
}

public void UpdateCPT(Node par){
    int i;
    double pValue =0.0;
    
    numberOfParentStates =0;
    allparentsStates =new ArrayList();
    allparentsValues =new ArrayList();
   
    if(this.repeatStates.size() ==0) {
        this.repeatStates.add(1);
       updateRepeatStates(parent.getNumstates());
    }
    // Utility nodes inherit parent states
    if(this.states==null && (this.getKind().toString().equalsIgnoreCase(node_type.UTILITY_NODE.toString())==false)){
        return;
    }
    
        
    try{   
        
        //CPT=new Object[parent.states.length][this.states.length];
        
        /**Create a table of self-states as rows and parent states as column
         ** headers with an extra column for child states
         **/
        Object[][] temp = null;
      if(allparents.size()>0){
         
        for(Object o : allparents){
            
            Node obj = ((Node)o);
            
            numberOfParentStates+=obj.getNumstates();
            if(obj.OldCPT!=null) {
                temp = obj.CPT;
                obj.CPT=obj.OldCPT;
            }
            if(obj.states !=null && obj.CPT !=null)
            for(i=0;i< obj.getNumstates(); i++)
            {
                allparentsStates.add(obj.states[i]);
                //allparentsValues.add(obj.CPT[i][1]);
                
            }
             // Swap computed CPT for the parents
            if(obj.OldCPT!=null){
                obj.CPT=temp;
            } 
            
        }
      }
       
        numberOfParentStates +=1; // one added for extra column for the node name
       
        if(this.getKind().toString().equalsIgnoreCase(node_type.UTILITY_NODE.toString())){
             //this.states = new String[numberOfParentStates-1];
           Object[][] oldCPTValues;
           if(this.CPT!=null && this.CPT.length>0){
               oldCPTValues = new Object[this.CPT.length][this.CPT[0].length];
               oldCPTValues = this.CPT;
           }
           else{
               oldCPTValues=new Object[this.getRepeats(0,allparents.size())][allparents.size()+1];
           }
           this.CPT=new Object[this.getRepeats(0,allparents.size())][allparents.size()+1];
            Node myNode;
            int multiple,counter=0,index,repeats=0;
            int parentsLen = allparents.size();
            repeats=this.getRepeats(0,parentsLen);
            for(int j=0 ; j < parentsLen;j++){
                myNode = (Node)allparents.get(j);
                if(j==allparents.size()-1){
                    multiple=1;
                   
                }
                else{
                    
                    multiple = this.getRepeats(j+1,parentsLen);
                // if(j==0) CPTzindex= multiple; // first multiple is the z-index
                }
                 do{
                    for( i=0;i<myNode.states.length;i++) {
                     for(index=0;index<multiple;index++){

                         CPT[counter][j]= myNode.states[i];
                        ++counter;
                     }
                    }
                 }while(counter!=repeats);
                 
                  if( parentsLen >1 & j==(parentsLen-1)){
                        CPTzindex= this.getRepeats(j,parentsLen);
                    }
                    else{
                        CPTzindex =1;
                    }
                counter=0;
            }
           
        
          if((this.CPT!=null && oldCPTValues !=null) && (this.CPT.length==oldCPTValues.length) 
                              && (this.CPT[0].length==oldCPTValues[0].length)
                              && (oldCPTValues[0][0]!=null)){
            pValue= 1.0/(this.CPT[0].length- allparents.size());
            for( i=0;i<CPT.length;i++) {
                for(int j=this.allparents.size();j<CPT[0].length;j++){
                
                pValue =Double.parseDouble(oldCPTValues[i][j].toString());
                
                CPT[i][j] = pValue;
               // CPT[i][j] = (Double)OldCPT[i][1] * (Double)allparentsValues.get(i*((numberOfParentStates-2)+j));
            }
            }//end o
         }
         else{
            for( i=0;i<CPT.length;i++) {
            pValue= 1.0/(this.CPT[0].length- allparents.size());
            for(int j=this.allparents.size();j<CPT[0].length;j++){
                
                CPT[i][j] = pValue;
               // CPT[i][j] = (Double)OldCPT[i][1] * (Double)allparentsValues.get(i*((numberOfParentStates-2)+j));
               }
            }//end of for
         }
        
        }
        else{
            Object[][] oldCPTValues = this.CPT;
           this.CPT= new Object[this.getRepeats(0,allparents.size())][this.getNumstates()+allparents.size()];
            Node myNode;
            int multiple,counter=0,index,repeats=0;
            int parentsLen = allparents.size();
            repeats=this.getRepeats(0,allparents.size());
            for(int j=0 ; j < parentsLen;j++){
                myNode = (Node)allparents.get(j);
                if(j==allparents.size()-1){
                    multiple=1;
                   
                }
                else{
                   // if(j+1 < repeatStates.size()){
                        multiple = this.getRepeats(j+1,allparents.size());
                   // }else{
                     //   multiple=1;
                   // }
                        
                // if(j==0) CPTzindex= multiple; // first multiple is the z-index
                }
                 do{
                    if(myNode.states!=null)
                    for( i=0;i<myNode.states.length;i++) {
                     for(index=0;index<multiple;index++){
                         if(counter==CPT.length) break;
                         CPT[counter][j]= myNode.states[i];
                        ++counter;
                     }
                    }
                 }while(counter!=repeats);
                 
                  if( parentsLen >1 & j==(parentsLen-1)){
                      try{
                        CPTzindex= this.getRepeats(j,allparents.size());
                      }catch(Exception ex){}
                    }
                    else{
                        CPTzindex =1;
                    }
                counter=0;
            }
           
         if( this.OldCPT!=null && oldCPTValues !=null && (this.CPT.length==oldCPTValues.length) &&
                 (this.CPT[0].length==oldCPTValues[0].length)){
            pValue= 1.0/(this.CPT[0].length- allparents.size());
            for( i=0;i<CPT.length;i++) {
                for(int j=this.allparents.size();j<CPT[0].length;j++){
                
                pValue =Double.parseDouble(oldCPTValues[i][j].toString());
                
                CPT[i][j] = pValue;
               // CPT[i][j] = (Double)OldCPT[i][1] * (Double)allparentsValues.get(i*((numberOfParentStates-2)+j));
            }
            }//end o
         }
         else if(oldCPTValues !=null && (this.CPT.length==oldCPTValues.length) &&
                 (this.CPT[0].length==oldCPTValues[0].length)){
            pValue= 1.0/(this.CPT[0].length- allparents.size());
            for( i=0;i<CPT.length;i++) {
                for(int j=this.allparents.size();j<CPT[0].length;j++){
                
                pValue =Double.parseDouble(oldCPTValues[i][j].toString());
                
                CPT[i][j] = pValue;
               // CPT[i][j] = (Double)OldCPT[i][1] * (Double)allparentsValues.get(i*((numberOfParentStates-2)+j));
            }
            }//end o
         }
         else{
            for( i=0;i<CPT.length;i++) {
            pValue= 1.0/(this.CPT[0].length- allparents.size());
            for(int j=this.allparents.size();j<CPT[0].length;j++){
                
                CPT[i][j] = pValue;
               // CPT[i][j] = (Double)OldCPT[i][1] * (Double)allparentsValues.get(i*((numberOfParentStates-2)+j));
               }
            }//end of for
         }
            
        }
    }
    catch(Exception ex){ex.printStackTrace();}
    
   

}

 public void deepCopy2Dobject(Object[][] from ,Object[][] to ){
     if(from==null || to==null) return;
     int i =0,j=0;
    
     for(i=0;i<from.length;i++){
         for(j=0;j<from[0].length;j++){
             to[i][j]= from[i][j];
         }
     }
     
     
 }
    public void updateRepeatStates(double repeats){
        int i;
        int len =repeatStates.size();
        for(i=0;i<len;i++){
                repeatStates.set(i,(int)Math.ceil(Integer.parseInt(repeatStates.get(i).toString()) *repeats));
            
        }
    }
    public int getRepeats(int parentIdx,int parentLen){
            
        int idx =0;
        int result=1;
       for(idx=parentIdx;idx<parentLen;idx++){
            Node node = (Node)this.allparents.get(idx);
            result *=node.getNumstates();
        }
       
       return result;
    }
    public void setKind(node_type type)
    {
    if(type.toString().equalsIgnoreCase(node_type.DECISION_NODE.toString())){
         this.name="D";
       }
       else if(type.toString().equalsIgnoreCase(node_type.LINK_NODE.toString())){

           this.name="LL";
       }
       else if(type.toString().equalsIgnoreCase(node_type.UPDATE_LINK_NODE.toString())){

           this.name="UL";
       }
       else if(type.toString().equalsIgnoreCase(node_type.MODEL_NODE.toString())){
           this.name="M";
       }
       else if(type.toString().equalsIgnoreCase(node_type.NATURE_NODE.toString())){
           this.name ="S";
       }
       else if(type.toString().equalsIgnoreCase(node_type.UTILITY_NODE.toString())){
           this.name="U";
       }
       else if(type.toString().equalsIgnoreCase(node_type.MODEL_LINK_NODE.toString())){
           this.name="ML";
       }
       else{
        this.name=type.toString().substring(0, 1)+"Node";
       }
        this.type=type;
    }
     public void setType(node_type type)
    {
        
        this.type=type;
    }
    public node_type getKind()
    {
        return this.type;
    }
    public Node (String name, String stateNames){
        
	this.name=name;
        states=stateNames.split(",");
        ++nodeCounter;
        this.nodeId=nodeCounter;
    }
    
    public String getFullName(){
       
        if(this.containerName.isEmpty()){
            return this.name;
        }
       
        return  this.containerName;
    }

    

    public void writeExternal(ObjectOutput out) throws IOException {
       
        out.writeInt(nodeId);
        out.writeObject(name);
        out.writeObject(type);
        out.writeObject(states);
        out.writeObject(statevalues);
        out.writeObject(CPT);
        out.writeObject(finding);
        out.writeObject(allparents);
        out.writeObject(parent);
        out.writeInt(numberOfParentStates);
        out.writeObject(allparentsStates);
        out.writeObject(allparentsValues);
        out.writeObject(containerName);
        out.writeInt(CPTzindex);
        out.writeObject(repeatStates);
        out.flush();
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
         
         this.nodeId=in.readInt();
         this.name= (String)in.readObject();
         this.type=(node_type)in.readObject();
         this.states=(String[])in.readObject();
         this.statevalues=(Object[])in.readObject();
         this.CPT= (Object[][])in.readObject();
         this.finding=(String)in.readObject();
         this.allparents=(ArrayList)in.readObject();
         this.parent=(Node)in.readObject();
         this.numberOfParentStates=in.readInt();
         this.allparentsStates=(ArrayList)in.readObject();
         allparentsValues=(ArrayList)in.readObject();
         this.containerName=(String)in.readObject();
         this.CPTzindex=in.readInt();
         this.repeatStates=(ArrayList)in.readObject();
         this.setKind(type);
        
        
    }
    
    
}
