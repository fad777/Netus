/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Util;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author fad777
 */
public class FileNameParser {
    static int extPos=0;
    
    private FileNameParser(){
        
    }
    
    public static String getFilePathLessExt(String filename){
        
        //if(filename.split(".").length < 1) return filename;
         Path folderPath = Paths.get(".");
         StringBuilder sp = new StringBuilder();
         try{
             if(!filename.isEmpty()){
                extPos = filename.lastIndexOf(".");
                sp.append(filename.substring(0, extPos));
                folderPath = Paths.get(sp.toString());
             }
             else{
                 return new String("");
             }
         }
         catch(Exception ex){
             
             ex.printStackTrace();
             
         }
        
         return folderPath.toString();
    }
    
    public static String getFullName(String upFileName, String downFileName){
        
        String delim = System.getProperty("file.separator");
        String folderName = getFilePathLessExt(upFileName);
        
        return String.format("%s%s%s",folderName,delim,downFileName);
        
    }
    
    public static String getFolderPath(String fileName){
        
        String delim = System.getProperty("file.separator");
        try{
            int index = fileName.lastIndexOf(delim);
            return fileName.substring(0, index+1);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        
        return new String("");
        
    }
    public static String getFileNameOnly(String fileName){
        
       String delim = System.getProperty("file.separator");
        try{
            int index = fileName.lastIndexOf(delim);
            return fileName.substring(index+1);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        
        return fileName;
        
    }
    public static String getNumericFolderName(String fileName,int longestPathLen){
        
         String delim =  System.getProperty("file.separator");
         String[] fileNameArr = fileName.split("[/\\\\]");
         StringBuilder sb = new StringBuilder();
         int index =0,counter = longestPathLen;
         if(longestPathLen >=1){
             sb.append(fileNameArr[0]).append(delim);
         try{
            for(index=1;index<fileNameArr.length;index++){
               
                sb.append(fileNameArr[index]).append("Level").append(--counter).append(delim);
            }
           // sb.append(fileNameArr[fileNameArr.length-1]);
            return sb.toString();
         }
         catch(Exception ex){
             ex.printStackTrace();
         }
         }
         
         return fileName;
         
         
         
    }
    
}
