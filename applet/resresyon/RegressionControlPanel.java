/*File RegressionControlPanel.java */

/*File RegressionControlPanel.java  */

package regress;

import java.awt.*;
import java.applet.*;
/**  
* This is the control panel for the RegressionApplet Class   		*
* @author Charles Stanton					*
* @version April 10 1997					*
*/
public class RegressionControlPanel extends Panel{
	Font f = new Font("TimesRoman", Font.PLAIN,10);
	RegressionApplet applet;
	FlowLayout fl = new FlowLayout();
	String line =new String();
	Xcanvas xc = new Xcanvas("");
	int indx=1;	 //index of choice of line display

/**
* constructor of control panel
* @param applet the calling applet
*/
	public RegressionControlPanel(RegressionApplet applet){
		this.applet=applet;
		setBackground(new Color(230,230,230));
		setLayout(fl);
		Choice whatShow = new Choice();
		whatShow.addItem("Show Regression Line");
		whatShow.addItem("Show Line and Residuals");
		whatShow.addItem("Show points only");
		add(whatShow);
		whatShow.select(indx);
		Button clear = new Button("Clear");
		add(clear);
		add(xc); 
		}

/**
* action handler for control panel
*/
	public boolean action(Event evt, Object arg) {
		if (evt.target instanceof Button){
			String buttonLabel;
			Button b;
			b = (Button) evt.target;
			buttonLabel=b.getLabel();
			if (buttonLabel.equals("Clear")) {
				applet.clear();
				applet.resc.clear();
				}
			}  					//END BUTTON CODE		
		else if (evt.target instanceof Choice){
			indx = ((Choice)evt.target).getSelectedIndex();
			}
		applet.calculate();							
		return true;
	}							//END ACTION CODE

/**
* updates displayed values of regression calculation
* @param a intercept of regression line
* @param b slope of regression line
*/	
	public void update(double a, double b) {
		double pa, pb; // round to 5 places
		pa = (Math.round(1.0E5*a))*1.0E-5;
		pb = (Math.round(1.0E5*b))*1.0E-5;
		line = "y = "+String.valueOf(pa)+" + "+String.valueOf(pb)+"x";
		xc.update(line);
		repaint();
	}

/**
*  clears panel 
*/
	public void clear() {
		line="";
		xc.clear();
		}

/**
* showIndex communicates choice of line display
* to regressionCanvas
*/
	public int showIndex(){
		return indx;
		}

}

