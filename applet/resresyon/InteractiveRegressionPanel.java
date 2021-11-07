package edu.csusb.danby.stat;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;



/**
* This class adds the ability for the panel to add points via
* mouse clicks on the panel.
* @version Wed Jul 10 08:36:44 PDT 2002
*/
public class InteractiveRegressionPanel extends RegressionPanel {
            
    RegressionCalculator rc;
    InteractiveControl ic;	
    
    /**
    * constructor for InteractiveRegressionPanel
    * @param aRc  the RegressionCalculator keeping the data
    * @param iRc the calling class with the update method
    */
    public InteractiveRegressionPanel( RegressionCalculator aRc, InteractiveControl aIc) {
        super(aRc);
        rc = aRc;
        ic = aIc;
        autoscaleX=false;
        autoscaleY=false;
        setViewport(0.0,10.0,0.0,10.0);
    
        this.addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent e){
            Point p = e.getPoint();
            double x,y;
            x = inverseScaleX(p.getX());
            y = inverseScaleY(p.getY());
            rc.addPoint(x, y);  // 
            ic.update(rc);
            }
        });
    }
    
    /**
    * The InteractiveControl must provide an update method
    * @param rc is the RegressionCalculator with the data
    */
    public interface  InteractiveControl{
        public void update(RegressionCalculator rc);
    }

}
        
