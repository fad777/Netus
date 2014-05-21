/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI;
import java.awt.Container;
import java.awt.Polygon;
import java.awt.Graphics;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author fad777
 */
public class Hexagon  extends JPanel{

        protected float a=0;
        protected float b=0;
	protected float c=30;
	private int startX=10;
	private int startY=10;
        private Polygon hex;


    public Hexagon(int startX, int startY, int len ){

        setStartX(startX);
	setStartY(startY);
	c = len;
	a = c/2;
	b = (float) Math.sin(Math.toRadians(60))*c;

    }
    @Override
    public void paintComponent(Graphics g){


        super.paintComponent(g);
       
       

	//g.drawPolygon(p);


    }
    public Polygon getPolygon(){

         Polygon p = new Polygon();



		//draw hex shape

        p.addPoint(0,(int)b);
        p.addPoint((int)a,0);
        p.addPoint((int)(a+c),0);
        p.addPoint((int)(2*c),(int)b);
        p.addPoint((int)(a+c),(int)(2*b));
        p.addPoint((int)(a+c),(int)(2*b));
        p.addPoint((int)a,(int)(2*b));
        p.addPoint(0,(int)b);

        return p;
    }
    public void setStartX(int startX) {
		this.startX = startX;
	}

	public int getStartX() {
		return startX;
	}

	public void setStartY(int startY) {
		this.startY = startY;
	}

	public int getStartY() {
		return startY;
	}


}

