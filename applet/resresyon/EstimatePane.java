package edu.csusb.danby.stat;

import java.awt.*;
import javax.swing.*;
import javax.swing.text.*;
import java.text.NumberFormat;

/**
* Display statistics of regression
* @author Charles S. Stanton
* @version Sat Jul 13 08:53:12 PDT 2002
*/
public class EstimatePane extends JScrollPane {
            
    RegressionCalculator rc;
    JTextPane estimates;
    JViewport vp;
    SimpleAttributeSet attrs;
    NumberFormat nf;
    double a, b;//Regression line coefs
    double aCILower, aCIUpper, bCILower, bCIUpper;//confidence interval
    double t, bSE, aSE;
    double SSE, SSR, Syy, MSE, F;
    static double[] t025={Double.NaN, 12.706, 4.303, 3.182, 2.776,
                            2.571, 2.447, 2.365, 2.306, 2.262,
                            2.228, 2.201, 2.179, 2.160, 2.145,
                            2.131, 2.120, 2.110, 2.101, 2.093,
                            2.086, 2.080, 2.075, 2.069,2.064,
                            2.060, 2.056, 2.052, 2.048, 2.045,
                            1.960};
    /**
    * constructor for EstimatePanel
    * @param aRc  the RegressionCalculator keeping the data
    */
    public EstimatePane( RegressionCalculator aRc) {
        super( JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                    JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                    
        rc = aRc;
        
        nf = NumberFormat.getNumberInstance();
        setPreferredSize(new Dimension(200, 200));
        estimates = new JTextPane();
        estimates.setEditable(false);
        attrs = new SimpleAttributeSet();
        StyleConstants.setBold(attrs, true);
        showMsg("Regresyon Statistics");
        showInfoLine("");
        StyleConstants.setBold(attrs, false);
        //maxFractionDigits = getMaxFractionDigits(minData, maxData);
        nf.setMaximumFractionDigits(4);
        update(rc);
              
        vp = getViewport();
        vp.add(estimates);
        revalidate();
        repaint();
    }
    
    
    /**
    * update pane with information in the regression calculator
    * @param aRc the regression calculator
    */
    public void update(RegressionCalculator aRc){
        rc = aRc;
        Document doc=estimates.getDocument();
        int docLength = doc.getLength();
        try{
            doc.remove(0,docLength-1);
        }
        catch (BadLocationException ex) {
            ex.printStackTrace();
        }
        showHead("Örnek Ýstatistikleri");
        int n = rc.getDataLength();
        showInfoLine("Gözlem sayýsý     n = "+n);
        showInfoLine("Y Ortalama  Y bar = "+format(rc.getYBar()));
        estimates.repaint();
        showInfo("s");
        subscript("Y");
        showInfoLine("= "+format(Math.sqrt(rc.getSyy()/((float)(n-1)) )));
        showInfoLine("Pearson Korelasyon Katsayýsý r = "+format(rc.getPearsonR()));
        showInfoLine("");
        showHead("Parametre Tahminleri");
        a = rc.getIntercept();
        b = rc.getSlope();
        showInfoLine("a = "+format(a));
        showInfoLine("b = "+format(b));
        showInfoLine("");
        
        showHead("95%  Güven Aralýðý");
        
        if (n>32){ t = t025[30];}
        else if (n > 2){ t= t025[n-2];}
        else {t = Double.NaN;}
        MSE = rc.getMSE();
        if (n>2){
            bSE = Math.sqrt(MSE/rc.getSxx());
        }
        else{
            bSE = Double.NaN;
        }
        aSE =bSE*Math.sqrt(rc.getSumXSquared()/n);
        aCILower = a-t*aSE;
        aCIUpper = a+t*aSE;
        showInfoLine("a   :    ("+format(aCILower)+", "+format(aCIUpper)+")"  );
        
        
        
        bCILower = b-t*bSE;
        bCIUpper = b+t*bSE;
        showInfoLine("b    :    ("+format(bCILower)+", "+format(bCIUpper)+")");
        showInfoLine("");
        showHead("Varyans Aralýðý");
        showInfoLine("Kaynak      Serbestlik Derecesi   Kareler Toplamý");
        showInfoLine("");
        SSR = rc.getSSR();
        showInfoLine("model              1                                 "+format(SSR));
        showInfoLine("Hata              "+(n-1)+"                         "+format(rc.getSSE()));
        showInfoLine("Toplam               "+n+"                            "+format(rc.getSyy()));
        showInfoLine("");
        showInfoLine("MSE ="+format(MSE));
        F = SSR/MSE;
        showInfo("F = "+format(F)+"    ");
        /* pValue not available for applet version
        showInfoLine("p = "+format(rc.getP(F)));
        */
        estimates.repaint();
    }
        
     private void showInfo(String msg) {
        StyleConstants.setBold(attrs, false);
        showMsg(msg);
    }
    
    private void showInfoLine(String msg) {
        StyleConstants.setBold(attrs, false);
        showMsgLine(msg);
    }
    
    private void showHead(String msg) {
        StyleConstants.setBold(attrs, true);
        showMsgLine(msg);
    }
    
    private void subscript(String subscript){
        StyleConstants.setSubscript(attrs, true);
        showMsg(subscript);
        StyleConstants.setSubscript(attrs, false);
    }
        

    protected void showMsg(String msg) {
        Document doc = estimates.getDocument();
       try {
            doc.insertString(doc.getLength(), msg, attrs);
        }
        catch (BadLocationException ex) {
            ex.printStackTrace();
        }
    }
        
    private void showMsgLine(String msg){
        msg += "\n";
        showMsg( msg);
    }
        
     //Find a reasonable number of digits to display for a, b
    private int findMaxFractionDigits( double value){
        double logValue;
        double log10 = Math.log(10.0);
        int maxFractionDigits;
        long power;
        if (value == 0.0) { return 2;}
        else {
            value = Math.abs(value);
            logValue = Math.log(value)/log10;
            power = (long)Math.floor(logValue);
            maxFractionDigits =(int) ( 4-1*power);
            return maxFractionDigits;
        }
    }
    
    private String format(double value){
        double logValue,absValue;
        double log10 = Math.log(10.0);
        int maxFractionDigits;
        long power;
        if (value == 0.0) { nf.setMaximumFractionDigits(2);}
        else {
            absValue = Math.abs(value);
            logValue = Math.log(absValue)/log10;
            power = (long)Math.floor(logValue);
            maxFractionDigits =(int) ( 4-1*power);
            nf.setMaximumFractionDigits(maxFractionDigits);
        }
        return nf.format(value);
    }
    
    
    
    
}
        
