/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;
import GUI.Util.FileNameParser;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author fad777
 */
public class PersistShapeNode{
    
    private ShapeNode _node =null;
    private String _filename = "";
    private Boolean _isDID = true;
    private FileOutputStream _fos = null;
    private ObjectOutputStream _oos = null;
    private ArrayList<Object> objList = new ArrayList<Object>();
    public static ArrayList<String> _modelFileNames = new ArrayList<String>();
    final static Charset ENCODING = StandardCharsets.UTF_8;
    final static String baseNetFile = "core.net";
    final static String OSNAME = System.getProperty("os.name") ;
    int _longestPathLen ;
    static String FILE_DELIMITER = null ;
    static CPTable CPTupdate = null;
    public static String netFileName="";
    public PersistShapeNode(String filename,Boolean isDID){
        
        this._filename=filename;
        this._isDID= isDID;
        _longestPathLen=0;
        /***  I do not support using this method again
         ***  ___DEPRECATED____
        if(!OSNAME.isEmpty() && OSNAME.startsWith("Windows")){
            FILE_DELIMITER= new String("\\");
          
        }
        else{
            FILE_DELIMITER = new String("/");
        }
        *****/
        FILE_DELIMITER= "/";
        
        
        try{
             
             this._fos = new FileOutputStream(this._filename);
             this._oos = new ObjectOutputStream(this._fos);
            
            
            
        }
        catch(IOException ex){
            ex.printStackTrace();
            
        }
       
    }
    
      public PersistShapeNode(String filename,Boolean isDID,int longestPathLen){
        
        this._filename=filename;
        this._isDID= isDID;
        this._longestPathLen = longestPathLen;
        /***  I do not support using this method again
         ***  ___DEPRECATED____
        if(!OSNAME.isEmpty() && OSNAME.startsWith("Windows")){
            FILE_DELIMITER= new String("\\");
          
        }
        else{
            FILE_DELIMITER = new String("/");
        }
        *****/
        FILE_DELIMITER= "/";
        
        
        try{
             
             this._fos = new FileOutputStream(this._filename);
             this._oos = new ObjectOutputStream(this._fos);
            
            
            
        }
        catch(IOException ex){
            ex.printStackTrace();
            
        }
       
    }
     
    public void setFileName(String filename){
        
        this._filename=filename;
    }
    
    public PersistShapeNode(ShapeNode node,String filename){
        
        this._node= node;
        
        
        Path folderPath = Paths.get(FileNameParser.getFilePathLessExt(filename));
        
        this._filename=FileNameParser.getFullName(filename, filename);
        try{
            if(Files.notExists(folderPath)){
                
                Files.createDirectory(folderPath);
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        
        try{
             this._fos = new FileOutputStream(this._filename);
             this._oos = new ObjectOutputStream(this._fos);
            
            
            
        }
        catch(IOException ex){
            ex.printStackTrace();
            
        }
       
        
    }
    // This method serializes the node passed to the constructor and to the file also passed
    // to the constructor
    public void WriteObject (){
        try{
            
         this._oos.writeObject(this._node);
         this._oos.flush();
         this._oos.close();
        
        }
        catch(Exception ex){
            
            ex.printStackTrace();
            
        }
        finally{
         
        }
    }
    
    public void WriteObject (Object obj){
        try{
            
         this._oos.writeObject(obj);
         this._oos.flush();
         this._oos.close();
         WriteNet();
         
        }
        catch(Exception ex){
            
            javax.swing.JOptionPane.showMessageDialog(null, ex.getMessage());
            
        }
        finally{
         
        }
    }
    
    // This method serializes list of nodes to the file passed to the one-argument (filename) constructor  that
    // 
    
     public void WriteListObject (ArrayList obj){
        this.objList = obj;
        try{
            
         this._oos.writeObject(obj);
         this._oos.flush();
         this._oos.close();
          WriteNet();
        }
        catch(Exception ex){
            
            ex.printStackTrace();
        }
        finally{
            
        }
            
    }
     
     
     public List<Object> ReadCoreNet() {
        
         List<Object> list = new ArrayList<Object>();
        
        Path path = Paths.get(baseNetFile);
        
        try{
            Scanner scanner = new Scanner(path,ENCODING.name());
            while(scanner.hasNextLine()){
            list.add(scanner.nextLine().toString());
            }
            return list;
            
        }
        catch(Exception ex){
           ex.printStackTrace();
        }
        
        return list;
     }
     
     public void WriteNet() throws IOException {
         int index =0;
       if(this._isDID){
         List<Object> alines = ReadCoreNet();
         StringBuilder sp = new StringBuilder();
         String subFileName = new String();
         int extPos,extPos2,backSlashPos ;
         String delim =System.getProperty("file.separator");
       
         extPos = _filename.lastIndexOf(".");
         backSlashPos = _filename.lastIndexOf(delim);
         subFileName = (backSlashPos >=0) ? _filename.substring(backSlashPos+1,_filename.length()): _filename;
         extPos2 = subFileName.lastIndexOf(".");
         sp.append(_filename.substring(0, extPos));
         String numericPath = FileNameParser.getNumericFolderName(sp.toString(),this._longestPathLen);
         //Path folderPath = Paths.get(sp.toString());
         Path folderPath = Paths.get(numericPath);
         sp.setLength(0);
         sp.append(subFileName.substring(0, extPos2));
         sp.append(".net");
         String filename = String.format("%s%s%s",folderPath.toString(),FILE_DELIMITER,sp.toString());
         // Path path = Paths.get(sp.toString());
         Path path = Paths.get(filename);
         
         try{
         /*** Create the directory ***/
            if(Files.notExists(folderPath)){
            Files.createDirectory(folderPath);
            }
            else{
                /*** Overwrite folder ***/
               javax.swing.JOptionPane.showMessageDialog(null, "Failed to save to Hugin format, folder already exists, please use a different name");
                return;
            }
         }
         catch(Exception ex){ex.printStackTrace();} 
         
        /* 
         for(index=0;index < _modelFileNames.size();index++){
             String newPath = String.format("%s%s%s",folderPath.toString(),FILE_DELIMITER,_modelFileNames.get(index).toString());
             Path newModelPath = Paths.get(newPath);
             Path oldModelPath = Paths.get(_modelFileNames.get(index).toString());
             Files.copy(oldModelPath, newModelPath);
         } */
       
         try {
             BufferedWriter writer = Files.newBufferedWriter(path, ENCODING);
             for(Object line :alines){
                 
                 writer.write((String)line);
                 writer.newLine();
                 writer.flush();
                 //System.out.println(line);
             }
             
             
         }
         catch(Exception ex){
           ex.printStackTrace();
         }
         // Write the nodes and the potential data
         String elem;
       
        
         
          try {
             BufferedWriter writer = Files.newBufferedWriter(path, ENCODING,StandardOpenOption.APPEND);
             
             for(Object obj :objList){
                ShapeNode sn = (ShapeNode)obj;
                CPTupdate = new CPTable(sn,false);
                StringBuilder sb = new StringBuilder();
               int statesLen = sn.node.states==null? 0: sn.node.states.length;
               String kind = sn.node.getKind().toString();
               Boolean isUtility = sn.node.getKind().toString().equalsIgnoreCase(node_type.UTILITY_NODE.toString());
               if(kind.equalsIgnoreCase(node_type.LINK_NODE.toString())||
                  kind.equalsIgnoreCase(node_type.MODEL_LINK_NODE.toString()) ||
                  kind.equalsIgnoreCase(node_type.UPDATE_LINK_NODE.toString())){
                  continue;
                }
                for(index =0; index < statesLen;index++){
                    if(index == sn.node.states.length-1){
                        String str = String.format("\"%s\"",sn.node.states[index]);
                        sb.append(str); 
                    }
                    else{
                         String str = String.format("\"%s\" ",sn.node.states[index]);
                         sb.append(str); 
                    }
                }
                
                /**** Write out the  format Data ***/
                 String nodeName = returnType(sn.node.getKind().toString());
                 //elem = String.format("%s %s",nodeName,(sn.getFullName()));
                 elem = String.format("%s %s",nodeName,sn.label);
                 writer.write(elem);
                 writer.newLine();
                 
                 writer.write("{");
                 writer.newLine();
                 
                 //elem =String.format("label = \"%s\" ;",sn.fullname );
                 elem =String.format("label = \"%s\" ;",sn.label );
                 writer.write(elem);
                 writer.newLine();
                 
                 elem =String.format("position = (%d %d) ;",sn.x,sn.y );
                 writer.write(elem);
                 writer.newLine();
                 if(sb.toString().isEmpty() || isUtility){
                     
                 }else{
                 elem =String.format("states = (%s);",sb.toString());
                 writer.write(elem);
                 writer.newLine();
                 }
                 
                 elem =String.format("HR_LinkMode = \"%s\";",getChildrenInHuginFormat(sn.node.children));
                 writer.write(elem);
                 writer.newLine();
                 
                 writer.write("}");
                 writer.newLine();
                 writer.flush();
             }
                /**** End of the  format Data ***/
                 
                 try{
                 
                   for(Object obj :objList){
                        ShapeNode sn = (ShapeNode)obj;
                        CPTupdate = new CPTable(sn,false);
                        StringBuilder sb = new StringBuilder();
                        int statesLen = sn.node.states==null? 0: sn.node.states.length;
                        String kind = sn.node.getKind().toString();
                    if(kind.equalsIgnoreCase(node_type.LINK_NODE.toString())||
                        kind.equalsIgnoreCase(node_type.MODEL_LINK_NODE.toString()) ||
                        kind.equalsIgnoreCase(node_type.UPDATE_LINK_NODE.toString())){
                        continue;
                    }
                    
                    /**** Write out the  potential Data  && Parameter File***/
                 StringBuilder nameBuilder = new StringBuilder();
                 if(sn.node.allparents.isEmpty()){
                     //nameBuilder.append(sn.getFullName());
                     nameBuilder.append(sn.label);
                     writeParamFile(sn);
                 }
                 else{
                     
                     nameBuilder.append(sn.label).append(" | ");
                     int len = sn.node.allparents.size();
                     for(index=0; index <len;index++ ){
                          if(index ==(len-1)){
                            String str = String.format("%s",(((Node)sn.node.allparents.get(index))).label);
                            nameBuilder.append(str); 
                        }
                        else{
                            //add space after string
                            String str = String.format("%s ",(((Node)sn.node.allparents.get(index)).label));
                            nameBuilder.append(str); 
                        }
                     }
                 }
                 elem = String.format("potential (%s)",(nameBuilder.toString()));
                 writer.write(elem);
                 writer.newLine();
                 
                 writer.write("{");
                 writer.newLine();
                 
                 elem =String.format("data");
                 writer.write(elem);
                 writer.newLine();
                 
                 StringBuilder sb2 = new StringBuilder();
                 StringBuilder openParenthesis = new StringBuilder();
                  StringBuilder closeParenthesis = new StringBuilder();
                  if(sn.node.getKind().toString().equalsIgnoreCase(node_type.UTILITY_NODE.toString())){
                      if(sn.node.states!=null){
                         sn.node.states = new String[sn.node.CPT.length];
                         for(index =0;index<sn.node.CPT.length ;index++){
                             sn.node.states[index]=sn.node.CPT[index][0].toString();
                         }  
                      }
                        // sn.node.allparents.clear();
                   }
                 
                 if(sn.node.allparents.isEmpty()  && (sn.node.states !=null)){
                     if(sn.node.getKind().toString().equalsIgnoreCase(node_type.NATURE_NODE.toString()) &&
                             ((sn.node.getFullName().equalsIgnoreCase("Sx"))||sn.node.getFullName().equalsIgnoreCase("S"))){
                         
                         
                     }
                     sb2.append("(");
                   try{
                     for(index=0;index < sn.node.states.length; index++){
                         
                        if(index == sn.node.states.length-1){
                            String str = String.format("%s",sn.node.CPT[index][1].toString());
                            sb2.append(str); 
                        }
                        else{
                             String str = String.format("%s ",sn.node.CPT[index][1].toString());
                             sb2.append(str); 
                        }
                     }
                     sb2.append(")");
                   }catch(Exception ex){
                       ex.printStackTrace();
                   }
                 }
                 else if(!sn.node.allparents.isEmpty()){
                     String newLine = System.getProperty("line.separator");
                     Boolean isUtility = sn.node.getKind().toString().equalsIgnoreCase(node_type.UTILITY_NODE.toString());
                     
                     int theIndex = sn.node.allparents.size() > 1? 1 : 0;
                    // int len = Integer.parseInt(sn.node.repeatStates.get(theIndex).toString());
                     int length= sn.node.allparents.size();
                     int repeat = sn.node.CPTzindex;
                     int startIndex, numberStates =0;
                     startIndex = isUtility==true? 0:0;
                     /*if(repeat>1){
                         sb2.append("(");
                     }*/
                     for(int idx=startIndex;idx<length;idx++){
                         openParenthesis.append("(");
                     }
                      for(int idx=startIndex;idx<length;idx++){
                                    closeParenthesis.append(")");
                      }
                      
                     if(isUtility){
                             numberStates = 1;
                             //length=0;
                         }
                         else{
                             if(sn.node.states!=null && sn.node.states.length>0){
                                numberStates =sn.node.states.length;
                             }else{
                                 numberStates=0;
                             }
                             
                         }
                   if( sn.node.CPT!=null && sn.node.CPT.length>0){
                     for(index=0;index < sn.node.CPT.length ; index++){
                         StringBuilder rowData = new StringBuilder();
                         if(!isUtility){
                         rowData.append("( ");
                         }
                         
                         for(int j=0 ; j<numberStates ; j++){
                            rowData.append((sn.node.CPT[index][length+j])).append(" "); 
                         }
                         if(!isUtility){
                         rowData.append(") ");
                         }
                         sb2.append(rowData);
                        
                        if((index+1)!=sn.node.CPT.length){
                         for(int k =sn.node.repeatStates.size()-1; k>0;k--){
                             if((index+1) % sn.node.getRepeats(k, sn.node.allparents.size())==0){
                          
                                sb2.append(")");
                             }
                           
                         }
                            sb2.append(newLine);
                          for(int k =sn.node.repeatStates.size()-1; k>0;k--){
                             if((index+1) % sn.node.getRepeats(k, sn.node.allparents.size())==0){
                               sb2.append("(");
                            }
                           
                         }
                        }
                        else{
                            sb2.append(newLine);
                           }
                         
                           /*if(repeat>1 && (index+1) % repeat==0 && (index+1)==sn.node.CPT.length){
                               sb2.append(")");
                           }*/
                           
                     }// end of for
                   }
                     
                   
                     
                 }
                 
                 elem =String.format(" = %s%s%s;",openParenthesis.toString(),sb2.toString(),closeParenthesis.toString());
                 writer.write(elem);
                 writer.newLine();
                 
                 writer.write("}");
                 writer.newLine();
                 writer.flush();
                
                }
                 
                 
                 }
                 catch(Exception ex){ex.printStackTrace();}
                  
              
                 
                // writer.write("}");
                 //writer.newLine();
                 writer.flush();
                 
                 /**** End of the  potential Data ***/
                
                 
             
             
             
         }
         catch(Exception ex){
            ex.printStackTrace();
         }
          
   }
             
}

     private String getChildrenInHuginFormat(ArrayList list){
         StringBuilder childrenNames = new StringBuilder();
         for(Object obj : list){
             
              String childName = "";
              childName =  String.format("[%s:0]", (((Node)obj).label));
              childrenNames.append(childName);
             
         }
         
         return childrenNames.toString();
     }
     
     
     private String returnType(String kind ){
         String _kind = new String() ;
        try{
          if(kind.equalsIgnoreCase(node_type.DECISION_NODE.toString())){
              
              _kind= new String("decision");
           }
          else if(kind.equalsIgnoreCase(node_type.NATURE_NODE.toString())){
             _kind= new String("node");      
          }
          else if(kind.equalsIgnoreCase(node_type.UTILITY_NODE.toString())){
              _kind= new String("utility");    
          }
          else {
              _kind= new String("node");
          }
                  
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        
        return _kind; 
         
     }
     
     private void writeParamFile(ShapeNode sn){
         if(netFileName.isEmpty()) return;
         int index;
         StringBuilder sb = new StringBuilder();
         String filename = String.format("%s/%s",netFileName, "param.txt");
         Path path = Paths.get(filename);
         
         try{
              for(index=0;index < sn.node.states.length; index++){
                         
                        if(index == sn.node.states.length-1){
                            String str = String.format("%s",sn.node.CPT[index][1].toString());
                            sb.append(str); 
                        }
                        else{
                             String str = String.format("%s,",sn.node.CPT[index][1].toString());
                             sb.append(str); 
                        }
           }
            BufferedWriter writer;
            try{
             writer = Files.newBufferedWriter(path, ENCODING,StandardOpenOption.CREATE,StandardOpenOption.APPEND);
             writer.write(sb.toString());
             writer.newLine();
             writer.flush();
            }catch(Exception ex ){}
           
         }
         catch(Exception ex){
             ex.printStackTrace();
         }
         
     }
     
     private String getLabel(Node obj){
          ShapeNode par = new ShapeNode(null,obj);
          Node node = par.node;
            String label ="";
            char c ;
           
            
            if(node.type==node_type.NATURE_NODE){
               label="S"; 
               int asciiCount = ('X'+par.getNodeId()-1) > 90 ? (65+par.getNodeId()-4):'X'+ par.getNodeId()-1;
               c= (char) (asciiCount);
                label = (label+ c);
            }
            else if(node.type==node_type.DECISION_NODE){
               
                label="D";
                 label = (label+ par.getNodeId());
            }
            else if(node.type==node_type.UTILITY_NODE){
                
                label="U";
                label =(label+ par.getNodeId());
            }
            else if(node.type==node_type.LINK_NODE){
               
                 label="L";
                  label =(label+ par.getNodeId());
            }
            else if(node.type==node_type.MODEL_NODE){
               
                 label="Mod";
                  label =(label+ par.getNodeId());
            }
            
             return label;
             
     }
}
