/* RegressionCanvas.java */


package regress;

import java.awt.*;
import java.applet.*;
import java.util.Vector;
import java.util.Enumeration;

/**
* RegressionCanvas.java
* @author Charles Stanton
*/
class RegressionCanvas extends Canvas{
	Dimension r;
	int  end1x, end1y, end2x, end2y;
	RegressionApplet applet;
	PlotPoint p;
	double xmax, ymax; // max real values for x, y
	boolean  showLineResids=true ;
	boolean showLineOnly=false;
	boolean showPointsOnly=false;
	int indx; 

		
/**
* Constructor for RegressionCanvas
* @param applet: the calling applet
*/
	public RegressionCanvas( RegressionApplet applet){
		this.applet=applet;
		setBackground(Color.white);
		xmax = 20.0;	// may want to change these later
		ymax =15.0;	// may want to change these later		
		}
		
	public Dimension preferredSize(){
		return new Dimension( 300, 250);
		}

	public Dimension minimumSize(){
		return new Dimension( 200, 200);
		}
	
	public void paint(Graphics g) {
		indx = applet.rcp.showIndex();
		switch(indx){
				case 0: 
					showLineOnly = true;
					showLineResids = false;
					showPointsOnly = false;
					break;
				case 1: 
					showLineOnly = false;
					showLineResids = true;
					showPointsOnly = false;
					break;
				case 2:
					showLineOnly = false;
					showLineResids = false;
					showPointsOnly = true;
					break;
				default:
					showLineOnly = false;
					showLineResids = true;
					showPointsOnly = false;
				}
			double a, b;
			r = size();
			g.setColor(Color.black);
			g.drawLine(15,0, 15,r.height);
			g.drawLine(0,r.height-15, r.width, r.height -15);
			PlotPoint[] ends = new PlotPoint[2];
			ends[0] = new PlotPoint(100,0);
			ends[1] = new PlotPoint(200,100);
			int x,y;
			ends = regressionLine(applet.a, applet.b);
			a = applet.a;
			b = applet.b;
		
			for (Enumeration e= applet.v.elements();
				e.hasMoreElements(); ) {
				g.setColor(Color.blue);
				p = (PlotPoint)e.nextElement();
				g.fillOval(p.x-2,r.height-p.y-2,4,4);
				if ( showLineResids) {
					g.setColor(Color.green);
					if (applet.numberPlotPoints >2){
					g.drawLine(p.x,r.height- p.y, p.x,
						r.height-(int)(a+p.x*b));
						}
					}
				}
			if (applet.numberPlotPoints >1){
				if (showLineOnly || showLineResids) {
				g.setColor(Color.red);
				g.drawLine(ends[0].x, ends[0].y, ends[1].x,
					 							ends[1].y);
				}				
				}
			}

		public Dimension mySize(){
			r=size();
			return r;
			}

		public void clear(){
			repaint();
			}


		public boolean mouseUp(Event evt, int x, int y) {
			
			 PlotPoint p = new PlotPoint(x,r.height-y);
			applet.addPlotPoint(p);
			applet.calculate();
			repaint();
			return false;
		}				


/**
* regressionLine Calculates pixel form of regression line y = a + bx
* @param a: y-intercept
* @param b: slope
*/

		
		public PlotPoint[] regressionLine(double a, double b) {
			PlotPoint[] ends = new PlotPoint[2];
			int e1x, e1y, e2x, e2y;
			//if
				 //(Math.abs(b) <= 1) {
					e1x=0;	
					e1y=(int)Math.round(r.height-a);
					e2x = r.width;
					e2y = (int)Math.round(r.height-
							(a+r.width*b));
			 		//}
			/*else {
				e1y=r.height;
				e2y =0;
				e1x=(int)Math.round(-20*(a/b));
				e2x=(int)Math.round( 20*(10-2)/b);
				}*/
			
			ends[0] = new PlotPoint( e1x, e1y);
			ends[1] = new PlotPoint( e2x, e2y);
			return ends;
			}
			

/**
* toRealCoord transforms pixel to real coordinates
* @param pixelx   x coordinate in pixels
* @param pixely   y coordinate in pixels
*/
	private double[] toRealCoord (int pixelx, int pixely) {
		double[] p = new double[2];
		p[0] = (pixelx-15)+xmax/(r.width-20);
		p[1] = (r.height- pixely -15)*ymax/(r.height-15);
		return p;
		}

/**
* toPixelCoord transforms real to pixel coordinates
* @param x x coordinate
* @param y y coordinate
*/
	private int[] toPixelCoord(double x, double y) {
		int[] p = new int[2];
		p[0] = (int)(x*(r.width-20)/xmax+15);
		p[1] = (int)((r.height-15)*(1-y/ymax));
		return p;
		}							
	




}
