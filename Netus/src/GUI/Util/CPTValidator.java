/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Util;

import java.util.ArrayList;

/**
 *
 * @author fad777
 */
public class CPTValidator {
    
    public CPTValidator(){
        
    }
    
    public static boolean isValidCPT(Double[] values){
        int i;
        double total=0.0d;
        double tolerance =0.00000001;
        for(i=0;i<values.length;i++){
            
            total += values[i];
        }
        if(Math.abs(total-1.0)<=tolerance){
            return true;
        }
        
        return false;
    }
}
