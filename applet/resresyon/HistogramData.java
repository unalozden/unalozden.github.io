package edu.csusb.danby.stat;

import java.text.NumberFormat;

/**
 *  create a histogram of supplied array of data
 *
 * @version    Tue Jul 23 10:44:47 PDT 2002
 */
public class HistogramData implements HistogramIF {
    protected double[] dummy = {0.0};
    protected double classMark[];
    // division points for histogram bins
    protected int[] binCount;
    protected int numberBins = 5;
    protected double[] data;
    protected int size;
    protected double minData, maxData;
    int[] dummyInt = null;
    NumberFormat nf = NumberFormat.getNumberInstance();
    PrettyScaleData psd;

    final static double log10 = Math.log(10.0);


    /**
     *Constructor for  HistogramData
     *
     * @param  aData  data for the histogram
     */
    public HistogramData(double[] aData) {

        size = aData.length;
        data = new double[size];
        System.arraycopy(aData, 0, data, 0, size);
        minData = minValue(data);
        maxData = maxValue(data);
        calculateBins(data);
    }


    /**
     *  Sets the number of bins for the histogram
     *
     * @param  nbins  The new NumberBins value
     */
    public void setNumberBins(int nbins) {
        numberBins = nbins;
        calculateBins(data);
    }



    /**
     *  Gets the class mark labels for the horizontal axis
     *
     * @return    The ClassMarkLabels value
     */
    public String[] getClassMarkLabels() {
        int maxFractionDigits;
        String[] scaleLabel = new String[numberBins + 1];

        maxFractionDigits = (int) Math.max(0, 2 - 1 * psd.spreadPower);
        nf.setMaximumFractionDigits(maxFractionDigits);

        for (int i = 0; i <= numberBins; i++) {
            scaleLabel[i] = nf.format(classMark[i]);
        }
        return scaleLabel;
    }


    /**
     *  Gets the number of objects in each bin
     *
     * @return    The BinCount value
     */
    public int[] getBinCount() {
        return binCount;
    }


    /**
     *  Gets the class marks for the horizontal axis
     *
     * @return    The ClassMarks value
     */
    public double[] getClassMarks() {
        return classMark;
    }



    /**
     *  Gets the number of bins
     *
     * @return    The NumberBins value
     */
    public int getNumberBins() {
        return numberBins;
    }


    /**
     *  Gets the LowValue attribute of the HistogramData object
     *
     * @return    The LowValue value
     */
    public int getLowValue() {
        return 0;
    }
    // Label for low value


    /**
     *  Gets the TheoreticalCount attribute of the HistogramData object
     *
     * @return    The TheoreticalCount value
     */
    public double[] getTheoreticalCount() {
        return dummy;
    }


    /**
     *  Description of the Method
     *
     * @return    Description of the Returned Value
     */
    public boolean useClassMarkLabels() {
        return true;
    }


    /**
     *  Description of the Method
     *
     * @return    Description of the Returned Value
     */
    public boolean drawTheoretical() {
        return false;
    }


    private double minValue(double[] data) {
        double min;
        min = data[0];
        //initial value
        for (int i = 1; i < size; i++) {
            min = (data[i] < min) ? data[i] : min;
        }
        return min;
    }


    private double maxValue(double[] data) {
        double max;
        max = data[0];
        //initial value
        for (int i = 1; i < size; i++) {
            max = (data[i] > max) ? data[i] : max;
        }
        return max;
    }


    private void calculateBins(double[] data) {
        int[] cummulativeBinCount;

        classMark = new double[numberBins + 1];
        psd = prettyScale(minData, maxData);
        for (int i = 0; i <= numberBins; i++) {
            classMark[i] = psd.minScale + i * (psd.maxScale - psd.minScale) / numberBins;
        }
        binCount = new int[numberBins];
        cummulativeBinCount = new int[numberBins];
        for (int i = 0; i < numberBins; i++) {
            //initialize binCount
            binCount[i] = 0;
            cummulativeBinCount[i] = 0;
        }

        for (int j = 0; j < numberBins; j++) {
            for (int i = 0; i < size; i++) {
                if (data[i] < classMark[j + 1]) {
                    cummulativeBinCount[j]++;
                }
            }
        }
        binCount[0] = cummulativeBinCount[0];
        for (int j = 1; j < numberBins; j++) {
            binCount[j] = cummulativeBinCount[j] - cummulativeBinCount[j - 1];
        }
    }


    private PrettyScaleData prettyScale(double aMinData, double aMaxData) {
        double minData;
        double maxData;
        double spreadData;
        double logSpreadData;
        double minDataScaled;
        double maxDataScaled;
        double approxMinData;
        double approxMaxData;
        long spreadPower;
        PrettyScaleData returnValues;

        minData = 2 * aMinData;
        maxData = 2 * aMaxData;
        spreadData = maxData - minData;
        logSpreadData = Math.log(spreadData) / log10;
        spreadPower = Math.round(Math.floor(logSpreadData));
        minDataScaled = minData / Math.exp(log10 * spreadPower);
        maxDataScaled = maxData / Math.exp(log10 * spreadPower);
        approxMinData = Math.exp(log10 * spreadPower) * Math.round(Math.floor(minDataScaled)) / 2.0;
        approxMaxData = Math.exp(log10 * spreadPower) * Math.round(Math.ceil(maxDataScaled)) / 2.0;
        returnValues = new PrettyScaleData(approxMinData, approxMaxData, spreadPower);
        return returnValues;
    }


    /**
     *  Description of the Class
     *
     * 
     */
    class PrettyScaleData {
        /**
         *  Description of the Field
         */
        public double minScale, maxScale;
        /**
         *  Description of the Field
         */
        public long spreadPower;


        PrettyScaleData(double amd, double amxd, long sp) {
            minScale = amd;
            maxScale = amxd;
            spreadPower = sp;
        }
    }
}

