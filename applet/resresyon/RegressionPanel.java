package edu.csusb.danby.stat;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.text.NumberFormat;
import java.awt.geom.*;
import edu.csusb.danby.util.*;


/**
* This class is meant as a general purpose display for
* regressions
* @version Wed Jul 10 10:33:51 PDT 2002
*/
public class RegressionPanel extends AxesPanel {
    
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
    private NumberFormat nf;
    protected Font titleFont = new Font("Serif", Font.PLAIN, 24);		
    private double[] x,y; //vectors of data -- should be equal length
    private int dataLength;
    private RegressionCalculator rc;  //class which does the calculations
    private double a,b; //regression coefficients
    //private double xRangeLow, xRangeHigh, yRangeLow, yRangeHigh;
    boolean showResidualLines;
    boolean showConfidenceBand=false;
    boolean showPredictionBand=false;
    private final static int CONFIDENCE =0;
    private final static int PREDICTION =1;
    // whether panel should choose own horizontal scale
    protected boolean autoscaleX=true; 
    // whether panel should choose own vertical scale
    protected boolean autoscaleY = true;
    //critical values for t
    static double[] t005={Double.NaN, 63.657, 9.925, 5.841, 4.604,
                        4.032, 3.707, 3.499, 3.355, 3.250,
                        3.169, 3.106, 3.055, 3.012, 2.977,
                        2.947, 2.921, 2.898, 2.878,2.861,
                        2.845, 2.831, 2.819,2.807, 2.797,
                        2.787,2.779, 2.771, 2.763, 2.756,
                        2.756};
                        
    static double[] t025={Double.NaN, 12.706, 4.303, 3.182, 2.776,
                            2.571, 2.447, 2.365, 2.306, 2.262,
                            2.228, 2.201, 2.179, 2.160, 2.145,
                            2.131, 2.120, 2.110, 2.101, 2.093,
                            2.086, 2.080, 2.075, 2.069,2.064,
                            2.060, 2.056, 2.052, 2.048, 2.045,
                            1.960};
    private double Sxx;
    
    
    public RegressionPanel( RegressionCalculator aRc) {
        super();
        rc = aRc;
        this.setPreferredSize(new Dimension(300,400));
        update(rc);
        nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(3);
        if (autoscaleX  && dataLength >0){
            setXRange( rc.getMinX(), rc.getMaxX());
        }
        if (autoscaleY && dataLength >0){
            setYRange( rc.getMinY(), rc.getMaxY());
        }
    }
        
    public void update(RegressionCalculator aRc) {
        rc = aRc;
        x = rc.getDataX();
        y = rc.getDataY();
        b = rc.getSlope();
        a = rc.getIntercept();
        dataLength = x.length;
        if ( autoscaleX && dataLength >0 ) {
            setXRange( minValue(x), maxValue(x));
        }
        if (autoscaleY && dataLength > 0 ){
            setYRange( minValue(y),  maxValue(y));
        }
        repaint();
                
        }
        
    
    public void paint(Graphics g) {
        
        super.paint(g);
        int  ix, iy;
        g.setColor(Color.black);
        for (int i=0; i< dataLength; i++){
            
            ix = scaleX(x[i]);
            iy = scaleY(y[i]);
            g.fillOval(ix-3,iy-3,6,6);
            }

        
            if (showResidualLines){
              g.setColor(Color.blue);
              g.drawLine(scaleX(xRangeLow), scaleY(yRangeLow),
                            scaleX(xRangeHigh),scaleY(yRangeHigh));
            }
         g.setColor(Color.red);
         if (dataLength >1){
             g.drawLine(scaleX(xRangeLow), scaleY(b*xRangeLow+a), 
                        scaleX(xRangeHigh), scaleY(b*xRangeHigh+a));
         }
        drawTitle(g);
        //MSE is sqrt(SSE/(n-2)), so don't try if n <=2
        if (showConfidenceBand && (x.length>2)){
            drawBand(g, CONFIDENCE);
        }
        if (showPredictionBand && (x.length>2)){
            drawBand(g, PREDICTION);
        }
                    
    }

    

    private double minValue(double[] data){
        double min;
        if (data.length >0){
        min = data[0]; //initial value
        for(int i=1; i< data.length; i++){
            min =( data[i]<min)? data[i]: min;
            }
        return min;
        }
        else {return Double.NaN;}
    }

    private double maxValue(double[] data){
        double max;
        if (data.length >0 ){
        max = data[0]; //initial value
        for (int i=1; i< data.length; i++){
            max = (data[i]>max)? data[i]: max;
            }
        return max;
        }
        else { return Double.NaN;}
    }
    
    private void drawBand(Graphics g, int type){
        double n = x.length;
        double t;
        double S= Math.sqrt(rc.getMSE());
        Graphics2D g2d = (Graphics2D)g;
        Sxx = rc.getSxx();
        double xBar = rc.getXBar();
        GeneralPath bandLimit = new GeneralPath();
        if (x.length < 32){ t= t025[x.length-2];}
            else { t= t025[30];}
        //A is used in calculating sError
        //A is 0 for confidence intervals for means
        //A is 1 for prediction intervals
        double A;
        Color bandColor;
        if (type==CONFIDENCE){
            A=0;
            bandColor = new Color(100,0,0);
        }
        else{//type=PREDICTION
            A=1;
            bandColor=new Color(0,0, 100);
        }
                
        double xs=xRangeLow;
        double ys = a+b*xs;
        double sError = S*Math.sqrt(A+1/n+(xs-xBar)*(xs-xBar)/Sxx);
        bandLimit.moveTo(scaleX(xs), scaleY(ys+t*sError));
        double deltaX= (xRangeHigh-xRangeLow)/10.0;
        for (int i=1; i<= 10; i++){
            xs=xs+deltaX;
            ys = a+b*xs;
            sError =S*Math.sqrt(A+1/n+(xs-xBar)*(xs-xBar)/Sxx);
            bandLimit.lineTo(scaleX(xs),	scaleY(ys+t*sError));
        }
        bandLimit.lineTo(scaleX(xs), scaleY(ys-t*sError));
        for (int i=9; i >=0; i--){
            xs = xs-deltaX;
            ys = a+b*xs;
            sError =S*Math.sqrt(A+1/n+(xs-xBar)*(xs-xBar)/Sxx);
            bandLimit.lineTo(scaleX(xs),	scaleY(ys-t*sError));
        }
        bandLimit.closePath();
        g2d.setPaint(Color.gray);
        g2d.draw(bandLimit);
        g2d.setPaint(bandColor);
        Composite c= AlphaComposite.getInstance(AlphaComposite.SRC_OVER,.2f);
        g2d.setComposite(c);
        g2d.fill(bandLimit);
    }
    
    

    protected void drawTitle(Graphics g){
        g.setFont(titleFont);
        g.setColor(Color.green);
        String title=new String("y = "+nf.format(a)+"+ "+nf.format(b)+"x");
        //SHOULD USE FONT METRICS
        g.drawString(title,40,20);
    }


    public void setDrawConfidenceBand(boolean draw){
        showConfidenceBand = draw;
    }
    
    public void setDrawPredictionBand(boolean draw){
        showPredictionBand = draw;
    }
    
    public void doAutoscaleX(boolean as){
        autoscaleX = as;
        }
        
    public void doAutoscaleY(boolean as){
        autoscaleY=as;
    }
    
    

}
        
