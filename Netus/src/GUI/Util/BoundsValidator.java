/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Util;

import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author fad777
 */
public final  class BoundsValidator {
     Point2D start,end;
     Rectangle myrect;
    public BoundsValidator(){
        
        start = new Point2D.Double();
        end = new Point2D.Double();
        myrect = new Rectangle();
        
    }
    
    public  void checkLinkBounds(Point2D startPt, Point2D endPt,Rectangle rect){
        
        
        double Ux=0.0,Uy=0.0,Lx=0.0,Ly=0.0;
        double x1,y1,x2,y2;
        double tol = 100.0;
        boolean keepX=false,keepY=false,keepBounds =false;
        
        x1=startPt.getX();
        y1=startPt.getY();
        x2= endPt.getX();
        y2= endPt.getY();
        
        
        if((Math.abs(y1-y2)==0) && (Math.abs(x1-x2)==0) ){
            
            Ux=x1 + 50;
            Uy= y1+10 ;
            Lx=x1+100;
            Ly= Uy ;
            keepBounds = true;
           
            
           
        }
        
        else if((Math.abs(y1-y2)<=tol) && (Math.abs(x1-x2)<=tol) && (y1<y2)){
            
            Ux=x1+ 50.0;
            Uy= y1 + 100.0;
            Lx=x2+50.0;
            Ly= y2 ;
            keepBounds = true;
            keepX=true;
            keepY=true;
            
           
        }
        else if((Math.abs(y1-y2)<=tol) && (Math.abs(x1-x2)<=tol) && (y1>y2)){
            
            Ux=x1+ 50.0;
            Uy= y1 + 100.0;
            Lx=x2+50.0;
            Ly= y2 ;
            keepBounds = true;
            keepX=true;
            keepY=true;
            
           
        }
         else if((Math.abs(y1-y2)<=tol) && (x1 < x2)){
            
            Ux=x1+ 100.0;
            Uy= y1 + 50.0;
            Lx=x2;
            Ly= y2 + 50.0;
            keepBounds = true;
            keepY=true;
            
           
        }
        else if((Math.abs(y1-y2)<=tol) && (x1 > x2)){
            
            Ux=x1;
            Uy= y1 + 50.0;
            Lx=x2+100;
            Ly= y2 + 50.0;
            keepBounds = true;
            keepY=true;
           
        }
        else if((Math.abs(x1-x2)<=tol) && (y1 < y2)){
            
            Ux=x1+ 50.0;
            Uy= y1 + 100.0;
            Lx=x2+50.0;
            Ly= y2;
            keepBounds = true;
            keepX=true;
            
           
        }
        else if((Math.abs(x1-x2)<=tol) && (y1 > y2)){
            
            Ux= x1+ 50.0;
            Uy= y1;
            Lx=x2+50.0;
            Ly= y2 + 100.0;
            keepBounds = true;
            keepX=true;
            
           
        }
     
        else if((x1 < x2) && (y1<y2) ){
            
            Ux= x1 + 100.0;
            Uy= y1 + 50.0;
            Lx= x2  ;
            Ly=y2+30;
        } 
        else  if((x1 < x2) && (y1>y2) ){
            
            Ux= x1 + 100.0;
            Uy= y1 + 50.0;
            Lx= x2  ;
            Ly=y2+60;
        } 
        
        else  if((x1 > x2) && (y1<y2) ){
            
            Ux= x1;
            Uy= y1 + 50.0;
            Lx= x2+100  ;
            Ly=y2+50;
        } 
         else  if((x1 > x2) && (y1>y2) ){
            
            Ux= x1;
            Uy= y1 + 50.0;
            Lx= x2+100  ;
            Ly=y2+50;
        } 
   
        // Set to false to allow more rooms for arrow movement 
        // when in vertical or horizontal alignment .
       // keepBounds =false;
        
        start = new Point2D.Double(Ux,Uy);
        end = new Point2D.Double(Lx,Ly);
        
        Polygon p = new Polygon();
        p.addPoint((int)Ux, (int)Uy);
        p.addPoint((int)Lx, (int)Ly);
        p.addPoint((int)Ux, (int)Ly);
        p.addPoint((int)Lx, (int)Uy);
        
        if(!keepBounds){
            myrect = p.getBounds();
        }
        else{
            
            int ypoint = (int)rect.getY();
            int xpoint = (int) rect.getX();
            int width = (int)rect.getWidth();
            int height = (int)rect.getHeight();
            
            if(keepX==true){
                ypoint +=70.0;
                height -=150;
            }
            if(keepY==true){
                xpoint+=70.0;
                width-=150;
            }
            if(keepX && keepY){
                xpoint = (int)rect.getX();
                width = (int)rect.getWidth();
                height = (int)rect.getHeight();
                
            }
            
            myrect = new Rectangle(xpoint,ypoint,width,height);
        }
        
       
        
        
    }
    
     public Point2D getStartPoint(){
        return start;
    }
    public Point2D getEndPoint(){
        return end;
    }
    public Rectangle getRect(){
         
        return myrect;
    }
    
    
    
}
