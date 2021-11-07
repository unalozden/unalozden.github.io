package edu.csusb.danby.stat;


import java.awt.*;
import javax.swing.*;
import java.lang.Math;
import java.util.*;
import edu.csusb.danby.util.*;
import java.awt.geom.Point2D;

/**
* Panel to display residuals from regression
*/
public class ResidualPanel extends AxesPanel{
    /*
    Variables inherited from AxesPanel
      
    protected int panelWidth, panelHeight; // current panel width & height
    protected int graphWidth, graphHeight;
    
    protected Font f = new Font("Serif", Font.PLAIN,12); //font for labeling axes
    
    // viewport for graph, with default values
    protected double xRangeLow =-10.0;
    protected double xRangeHigh = 10.0;
    protected double yRangeLow =-10.0;
    protected double yRangeHigh = 10.0;
    
    METHODS INHERITED FROM AxesPanel
    protected int scaleX(double x)
    protected double inverseScaleX(double ix)
    protected int scaleY(double y)
    protected double inverseScaleY(double iy)
    */
    
    Dimension r;
    Point2D.Double p;
    int px, py;
    double[][] residuals;
    double maxAbsoluteResidual;	
    Font titleFont = new Font("Serif", Font.PLAIN, 24);
    RegressionCalculator rc;
    boolean autoscaleX;
    
    /**
    * constructor for ResidualPanel with a fixed range of x
    *
    * @param rc is the RegressionCalculator with data
    * @param minX the left end point
    * @param maxX the right end point
    */
    public ResidualPanel(RegressionCalculator aRc, double minX, double maxX){
        rc=aRc;
        residuals = rc.getResiduals();
        this.setPreferredSize(new Dimension(300,400));
        maxAbsoluteResidual = rc.getMaxAbsoluteResidual();
        xRangeLow= minX;
        xRangeHigh = maxX;
        autoscaleX=false;
        setDrawHorizontalScaleAtEdges(false);
    }

    /**
    * constructor for ResidualPanel, horizontal range determined from data
    *
    * @param rc is the RegressionCalculator with data
    */
    public ResidualPanel(RegressionCalculator aRc){
        rc=aRc;
        residuals = rc.getResiduals();
        this.setPreferredSize(new Dimension(300,400));
        maxAbsoluteResidual = rc.getMaxAbsoluteResidual();
        if (residuals.length >0){
            xRangeLow = rc.getMinX();
            xRangeHigh = rc.getMaxX();
        }
        autoscaleX=true;
        setDrawHorizontalScaleAtEdges(false);
    }
    
    /**
    * paint method for ResidualPanel
    */
    public void paint(Graphics g) {
        if (maxAbsoluteResidual<0.00000001){
            setYRange(-1.0, 1.0);
        }
        else{
            setYRange(-1*maxAbsoluteResidual,maxAbsoluteResidual);
        }
        if (autoscaleX && (residuals.length > 0)){
            xRangeLow = rc.getMinX();
            xRangeHigh =rc.getMaxX();
        }
        super.paint(g);
        int x,y;
        g.setColor(Color.blue);
        for (int i=0; i< residuals.length; i++ ) {
            p = new Point2D.Double(residuals[i][0],residuals[i][1]);
            px = scaleX(p.getX());
            py = scaleY(p.getY());
            g.fillOval(px-3,py-3,6,6);
            g.drawLine(px,scaleY(0.0),px, py);			
            }
            g.setColor(Color.blue);
            drawTitle(g);
        }

    /**
    * update the vector of residuals
    */
    public void update(RegressionCalculator rc){
        residuals=rc.getResiduals();
        maxAbsoluteResidual = rc.getMaxAbsoluteResidual();
        setYRange(-1*maxAbsoluteResidual,maxAbsoluteResidual);
        
        repaint();
    }

    protected void drawTitle(Graphics g){
        g.setFont(titleFont);
        g.setColor(Color.green);
        String title=new String("Residuals");
        //SHOULD USE FONT METRICS
        g.drawString(title,40,20);
    }

}
