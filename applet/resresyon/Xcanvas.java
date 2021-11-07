/* file Xcanvas.java */

/* file Xcanvas.java */

package regress;

import java.awt.*;



public class Xcanvas extends Canvas{
//int X=0;
//double Xd=0.0;
//int CanvasWidth;
int CanvasHeight;
String line= new String();

/*	public  Xcanvas(int X){
		this.X = X;
		CanvasWidth = size().width;
		CanvasHeight = size().height;
		//System.out.println("CanvasHeight = " + CanvasHeight);
		}
*/	
/*	public  Xcanvas(double Xd){
		this.Xd = Xd;
		CanvasWidth = size().width;
		CanvasHeight = size().height;
		//System.out.println("CanvasHeight = " + CanvasHeight);
		}
*/
/*	public  Xcanvas(String Xstring, int X){
		this.X = X;
		this.Xstring=Xstring;
		CanvasWidth = size().width;
		CanvasHeight = size().height;
		//System.out.println("CanvasHeight = " + CanvasHeight);
		}
*/
	public  Xcanvas(String line){
		this.line=line;
		//CanvasWidth = size().width;
		CanvasHeight = size().height;
		//System.out.println("CanvasHeight = " + CanvasHeight);
		}


	public void paint(Graphics g){
		int y;  // printing height
		y = (int)(0.9*CanvasHeight);
		Font f = new Font("Helvetica",  Font.BOLD, 16);
		g.setFont(f);
		setBackground(Color.white);
		g.setColor(Color.blue);
		g.drawString(line, 5,20);
		} 
		
		
	public void clear(){
		line="";
		repaint();
		}

	public void update(String line){
		this.line = line;
		repaint();
		}

	public Dimension preferredSize(){
		Dimension d = new Dimension(250, 25);
		return d;
		}
}
