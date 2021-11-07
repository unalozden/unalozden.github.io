/* ResidualCanvas.java */

package regress;

import java.awt.*;
import java.applet.*;
import java.util.Vector;
import java.util.Enumeration;

/**
* ResidualCanvas.java
*/
	class ResidualCanvas extends Canvas{
		Dimension r;
		RegressionApplet applet;
		PlotPoint p;
		Vector residuals;

		public ResidualCanvas( RegressionApplet applet, Vector
								 residuals){
		this.applet=applet;
		this.residuals=residuals;
		setBackground(new Color(210,210,210));
		}
		
		public Dimension preferredSize(){
			return new Dimension( 300, 250);
			}

		public Dimension minimumSize(){
			return new Dimension( 200, 200);
			}

		public void paint(Graphics g) {
			int x,y;
		r = size();
		g.setColor(Color.black);
		g.drawLine(0,r.height/2,r.width,r.height/2);
		g.setColor(Color.blue);
		for (Enumeration e= residuals.elements();
			e.hasMoreElements(); ) {
			p = (PlotPoint)e.nextElement();
			//System.out.println("new point="+p.x+p.y);
			g.fillOval(p.x-2,r.height/2-p.y-2,4,4);
			g.drawLine(p.x,r.height/2,p.x, r.height/2-p.y);			
			}
		}

		public void update( Vector residuals){
			this.residuals=residuals;
			repaint();
			}

		public Dimension mySize(){
			r=size();
			return r;
			}

		public void clear(){
			residuals=new Vector();
			repaint();
			}


/*		public boolean mouseUp(Event evt, int x, int y) {
			 PlotPoint p = new PlotPoint(x,y);
			applet.addPlotPoint(p);
			repaint();
			return false;
		}				
*/		
		//public void drawResidualionLine(double a, double b) {

								
	




}
