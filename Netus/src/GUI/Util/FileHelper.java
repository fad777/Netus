/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Util;

import java.io.File;

/**
 *
 * @author fad777
 */
public class FileHelper {
    
    public final static String  id = "id";
    public final static String  iid = "iid";
    public final static String  did = "did";
    public final static String  idid = "idid";
    
    
    public static String getExtension(File f){
        
        String ext = null;
        String name = f.getName();
        int i = name.lastIndexOf('.');
        if(i>0 && i < name.length()-1){
            ext= name.substring(i+1).toLowerCase();
        }
    
        return ext;
    }
    
}
