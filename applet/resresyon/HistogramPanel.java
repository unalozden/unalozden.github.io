package edu.csusb.danby.stat;


import java.text.NumberFormat;
import java.awt.*;  
import java.applet.*;
import javax.swing.*;

/**
 * Draws a histogram given appropriate information
 * @author Charles Stanton
 * @version  Wed Jul 24 08:23:20 PDT 2002
 */

public class HistogramPanel extends JPanel {
    private HistogramIF model;
    protected int[] count;  //array of counts for each bin
    protected int numberBins;     //number of bins 
    protected double[] theoreticalCount;  // The theoretical distribution counts
    protected boolean theoretical=false;  // Whether to plot the theoretical histogram
                 //  Which must be supplied
    protected boolean continuous=true;  // whether the theoretical is to be plotted
                // as a continuous function
    protected boolean drawHorizontalScale=true; // Whether to draw horizontal scale
    protected boolean drawVerticalScale=true; //Whether to draw vertical scale
    protected boolean doClassMarkLabels = false;
    protected double[] classMark;
    protected String[] classMarkLabel;
    protected Color scaleColor = Color.cyan;
    protected Color backgroundColor = Color.blue;
    protected Color binColor = Color.white;
    private int binStep=8;  // the number of pixels to step the histogram
    

    //private int leftOffset=28;
    //private int rightOffset=18;
    //private int topOffset=18;
    private int bottomOffset=20;
    //private int tickDivisions=5;
    
    private int height;
    private int binWidth;
    private int lowValue=0; // lowest bin index for histogram
                            //useful for discrete histograms
                            //starting at non-zero value
    private Font f = new Font("Serif", Font.PLAIN,10);
    private int mean, stdDev; //used for normal scale
    


/**
 *    Initialize the histogram to draw theoretical distribution
 * 
 */	
    public HistogramPanel(HistogramIF aModel) {
        model = aModel;
        setBackground( backgroundColor);
        setOpaque(true);
        //setMinimumSize(new Dimension(40,40));
        numberBins = model.getNumberBins();
        count = model.getBinCount();
        lowValue=model.getLowValue();
        theoreticalCount = model.getTheoreticalCount();
        doClassMarkLabels = model.useClassMarkLabels();
        classMarkLabel = model.getClassMarkLabels();
        classMark = model.getClassMarks();
        repaint();	
        }


        
    /**
    * Painting method for drawHistogram
    */
    public void paint(Graphics g){
        
        super.paint(g);
        Dimension s=this.getSize();
        binWidth=s.width/(numberBins+2);
        int y;
        height=s.height;
        // Always paint the bins. Only paint scale and theoretical
        // distribution if called for.
        paintBins(g);
        if (theoretical){ paintTheoretical(g);}
        if (drawHorizontalScale) { paintHorizontalScale(g);}
        if (drawVerticalScale) { paintVerticalScale(g);}
    }

    /*
    * paints the histogram bins
    */
    private void paintBins(Graphics g) {
        for (int j=0; j< numberBins; j++) {
        g.setColor(binColor);		
        g.fillRect((j+1)*binWidth,height-bottomOffset- count[j]*binStep, 
                         binWidth-1,count[j]*binStep);
            }
        }

    /*
    * draw the theoretical histogram from the supplied data
    * theoreticalCount[].
    */
    private void paintTheoretical(Graphics g) {
        int y0, y1,y;

        g.setColor(Color.red);
        if (continuous) {
            y1 = (int)(height-bottomOffset-theoreticalCount[0]*binStep);
            for (int j=0; j< numberBins -1; j++) {
            y0 = y1;
            y1 = (int)(height-bottomOffset-theoreticalCount[j+1]*binStep);
            g.drawLine((int)((j+1.5)*binWidth), y0,
                        (int) ((j+2.5)*binWidth), y1);
                }
            }
        else 	{  //discrete case

                for (int j=0; j< numberBins; j++) {
                y = (int)(height-bottomOffset-theoreticalCount[j]*binStep);
                g.drawLine((j+1)*binWidth,y,(j+2)*binWidth-1,y);
                g.drawLine((j+1)*binWidth,y+1,(j+2)*binWidth-1,y+1);
                }
            }
        } 
    
    /*
    * draw horiziontal scale at bottom
    * of graph
    */
    private void paintHorizontalScale(Graphics g) {
        int iTickPos;
        int tickSpace;
        
        g.setFont(f);
        int tickPos;
        Integer xval;
        int scaleWidth = binWidth*numberBins;
        int xOffset = binWidth;
        int yOffset = height -(bottomOffset-5);
        g.setColor(scaleColor);
        g.drawLine(xOffset, yOffset, xOffset+scaleWidth, yOffset);
        if (doClassMarkLabels){
            tickSpace=scaleWidth/numberBins;
            for (int i=0; i<=numberBins; i++){
                tickPos = xOffset+i*tickSpace;
                g.drawLine(tickPos, yOffset+2, tickPos,yOffset-2);
                }
            g.drawString(classMarkLabel[0], xOffset-6, yOffset+12);
            g.drawString(classMarkLabel[numberBins], xOffset+scaleWidth-6, yOffset+12);
        }
        else { // Calculate Labels and ticks from index values
            for (int i=0; i< numberBins; i++) {
                xval =  new Integer(lowValue+i);
                tickPos = (int)((i+1.5)*binWidth);
                if (xval.intValue()%5 ==0) {
                    g.drawLine( tickPos, yOffset-5, tickPos, yOffset+5);
                    g.drawString(xval.toString(),tickPos-3,yOffset+12); 		
                    }
                g.drawLine(tickPos, yOffset+2, tickPos,yOffset-2);
            }
        }
        }

    /*
    * draws scale labeled in normal units
    */
    private void paintNormalScale(int mean, int stdDev, Graphics g){
        g.setFont(f);
        int tickPos;
        int xOffset = binWidth;
        int yOffset = height-(bottomOffset-5);
        int scaleWidth = binWidth*numberBins;
        Integer xval;
        g.setColor(scaleColor);	
        g.drawLine(xOffset, yOffset, xOffset+scaleWidth, yOffset);
        for (int i =0; i< scaleWidth/(2*stdDev); i++){
            tickPos = xOffset + mean -i*stdDev;
            g.drawLine(tickPos, yOffset-5, tickPos, yOffset+5);
            xval = new Integer(-i);
            g.drawString(xval.toString(), tickPos-3,yOffset+12);
            tickPos = xOffset + mean +i*stdDev;
            g.drawLine(tickPos, yOffset-5, tickPos, yOffset+5);
            g.drawString( new Integer(i).toString(), tickPos-3,yOffset+12);
            }
        }
        
    /**
    * draws vertical scale between left side and first bin
    */
    public void paintVerticalScale(Graphics g) {
        int xOffset = binWidth/2;
        int ypos;
        Integer yval ;
        g.setFont(f);
        g.setColor(scaleColor);
        g.drawLine(xOffset, 0, xOffset, height-bottomOffset);
        for (int i=0; i<(height-bottomOffset)/5; i++){
            yval = new Integer(i*5);
            ypos =height-bottomOffset-5*binStep*i;
            if (i%2 ==0) {
                g.drawLine(xOffset-4,ypos,xOffset+4,ypos);
                g.drawString(yval.toString(), xOffset+6,ypos-4);
                }
            else {
                g.drawLine(xOffset-2,ypos,xOffset+2,ypos);
                }
            }
        }



    /**
    * updates histogram data and repaints
    */	
    public void update(HistogramIF aModel){
        model = aModel;
        lowValue = model.getLowValue();
        numberBins = model.getNumberBins();
        count = model.getBinCount();
        theoretical = model.drawTheoretical();
        theoreticalCount = model.getTheoreticalCount();	
        repaint();
    }
    
    /**
    *  adjust the number of pixels for each increase in the number of bins.
    */
    public void setBinStep(int i){
        binStep =i;
        }
       
    /**
    * sets the draw continuous attribute
    */
    public void setDrawContinuous(boolean value){
        continuous = value;
    }
    
    public void setBackgroundColor(Color color){
        backgroundColor = color;
    }
    
    public void setBinColor(Color color){
        binColor = color;
    }
    
    public void setScaleColor(Color color){
        scaleColor = color;
    }
    
   
        

}

