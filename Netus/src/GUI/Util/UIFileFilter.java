/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Util;

/**
 *
 * @author fad777
 */

import java.io.File;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.swing.*;
import javax.swing.filechooser.*;

public class UIFileFilter extends FileFilter {
    
   // private static String TYPE_UNKOWN = "Type Unknown";
  //  private static String HIDDEN_FILE = "Hidden File";
    
    private Hashtable filters = null;
    private String description = null;
    private boolean useExtInDescription = false;
    private String fullDescription = null;
    
    /**
     * Default constructor 
     *
     * Accept all filters
     * 
     */
     
    public UIFileFilter(){
        this.filters = new Hashtable();
    }
    
    /**
     * Create a filter that accepts extension only
     * @param ext
     * @return 
     */
    public UIFileFilter(String ext){
        this(ext,null);
    }
    
    public UIFileFilter(String ext, String desc){
        this();
        if(ext!=null) addExt(ext);
        if(desc!=null) setDescription(desc);
    }
    
    /**
     * Creates a filter from the given string array.
     * @param f
     * @return 
     */
    public UIFileFilter(String[] filters){
        this(filters,null);
    }
    
    /**
     * Creates a filter from the given ext array and 
     * description
     * @param f
     * @return 
     */
    public UIFileFilter(String[] filters, String desc){
        this();
        for(int i = 0 ; i < filters.length;i++){
            addExt(filters[i]);
        }
        if(desc!=null) {
            setDescription(desc);
        }
    }

    @Override
    public boolean accept(File f) {
       if(f!=null){
        if(f.isDirectory()){
            return true;
        }
        String extension = getExtension(f);
        if(extension !=null && filters.get(getExtension(f))!= null ) {
               
           return true;   
        }
      }
        return false;
    }
    
    public void addExt(String extension){
        if(filters==null){
            filters = new Hashtable(5);
        }
        filters.put(extension.toLowerCase(), this);
        fullDescription = null;
    }

    @Override
    /**
     *  Returns human readable form of this filter
     */
    public String getDescription() {
        
      if(fullDescription == null){
          if(description == null || isExtensionListInDescription()){
              fullDescription = description ==null ? "(" : description + "(";
              // get the description from the extension list
              Enumeration extensions = filters.keys();
              if(extensions !=null){
                  fullDescription += (String)extensions.nextElement();
                  while(extensions.hasMoreElements()){
                      fullDescription += "," + (String) extensions.nextElement();
                  }
                 
              }
               fullDescription += ")";
              
          }
          else{
                  fullDescription = description;
          }
      }
      return fullDescription;
    
    }
    /**
     * Sets the human readable description 
     * @return void
     */
    public void setDescription(String desc){
        this.description = desc;
        fullDescription = null;
    }
    public boolean isExtensionListInDescription(){
        return useExtInDescription;
    }
    
    public void setExtensionListInDescription(boolean b){
        useExtInDescription = b;
        fullDescription = null;
    }
     /**
     * Returns the ext of the
     * given file.
     */
    public String getExtension(File f) {
	String name = f.getName();
	if(name != null) {
	    int extensionIndex = name.lastIndexOf('.');
	    if(extensionIndex < 0) {
		return null;
	    }
	    return name.substring(extensionIndex+1).toLowerCase();
	}
	return null;
    }
    
}
