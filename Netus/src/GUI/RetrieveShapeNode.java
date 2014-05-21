/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author fad777
 */
public class RetrieveShapeNode {
    String _filename ="";
    FileInputStream _fis = null;
    ObjectInputStream _ois = null;
    List list = null;
    
    public RetrieveShapeNode(String filename){
        
        this._filename=filename;
        
        try{
        this._fis = new FileInputStream(this._filename);
        this._ois = new ObjectInputStream(this._fis);
        }
        catch(Exception ex){
            
            ex.printStackTrace();
        }
    }
    
    public ShapeNode getObject(){
        
        ShapeNode shapeNode = null;
        
        try{
            shapeNode = (ShapeNode)this._ois.readObject();
           // javax.swing.JOptionPane.showMessageDialog(null, "Object successfully retrieved. " );
        
        }
        catch(Exception ex){
            
            javax.swing.JOptionPane.showMessageDialog(null, ex.getLocalizedMessage() );
        }
        
        return shapeNode;
        
    }
    
    
    public ArrayList getObjectList(){
        
        ArrayList myList = null;
        
        try{
            myList = (ArrayList)this._ois.readObject();
          //  javax.swing.JOptionPane.showMessageDialog(null, "Objects successfully retrieved. ");
        
        }
        catch(Exception ex){
            
            ex.printStackTrace();
        }
        
        return myList;
        
    }
    
}
