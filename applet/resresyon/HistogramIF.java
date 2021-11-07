package edu.csusb.danby.stat;

/**
* HistogramIF interface defines methods which a class using HistogramPanel 
* must implement
* @version Wed Jul 24 08:19:50 PDT 2002
*/

public interface HistogramIF{
    public int getNumberBins();    //The number of bins to be drawn
    public void setNumberBins(int i); //allow for number of bins to be reset
    public int[] getBinCount(); //Array containing number of items in each bin
    public int getLowValue(); //Index for lowest bin
    public boolean drawTheoretical();	// whether to draw theoretical distribution
    public boolean useClassMarkLabels();  //whether to use supplied labels

    /*
    *	if the boolean values above are false, these methods
    *   may be given trivial null value implementations
    */
    public double[] getTheoreticalCount(); //theoretical values for distribution

    public double[] getClassMarks();	// values for determining bins
    public String[] getClassMarkLabels(); // labels for axis
   
}

