package edu.csusb.danby.stat;

/**
* HistogramAdapter is a trivial implementation of the Histogram interface
* @version Wed Jul 24 08:21:11 PDT 2002
*/

public class HistogramAdapter implements HistogramIF{
    public int getNumberBins(){return 0;}    //The number of bins to be drawn
    public void setNumberBins(int i){;} //allow for number of bins to be reset
    public int[] getBinCount(){return null;} //Array containing number of items in each bin
    public int getLowValue(){return 0;} //Index for lowest bin
    public boolean drawTheoretical(){return false;}	// whether to draw theoretical distribution
    public boolean useClassMarkLabels(){return false; } //whether to use supplied labels

    /*
    *	if the boolean values above are false, these methods
    *   may be given trivial null value implementations
    */
    public double[] getTheoreticalCount(){return null;} //theoretical values for distribution
    public double[] getClassMarks() {return null;}	// values for determining bins
    public String[] getClassMarkLabels(){return null; }// labels for axis
    }



