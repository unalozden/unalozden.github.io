package regress;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import edu.csusb.danby.stat.*;

/**
 * Analysis sets up a tabbed pane to hold various panels
 *
 * @author     Charles S. Stanton
 * @created    Sat Jul 13 08:21:34 PDT 2002
 */
public class AnalysisPane extends JTabbedPane {

    // tabbed panes to hold the individual calucaltors
    private JPanel[] jPanel;
    protected final static int RESIDUALS = 0;
    protected final static int ESTIMATES = 1;
    private RegressionPanel regressionPanel;
    private ResidualPanel residualPanel;
    private EstimatePane estimatePane;
    private RegressionPanel predictionPanel;
    final static String[] panelLabel = {"Kalýntýlar", "Tahmin",
                    "Güven/Kestirim Aralýðý"};
    private String xName, yName;

    /**
     *  Constructor for the AnalysisPane
     * @param rc the RegressionCalculator with the data
     * @param aXName label for horizontal axis
     * @param aYName label for vertical axis
     */
    public AnalysisPane(RegressionCalculator rc, String aXName, String aYName) {
        super(JTabbedPane.TOP);
        xName=aXName;
        yName=aYName;
        setPreferredSize(new Dimension(400, 260));
        regressionPanel= new RegressionPanel(rc);
        residualPanel= new ResidualPanel(rc, 0.0, 10.0);
        add("Residuals", residualPanel);
        estimatePane = new EstimatePane(rc);
        add("Estimates", estimatePane);
        predictionPanel = new RegressionPanel(rc);
        predictionPanel.doAutoscaleX(false);
        predictionPanel.setXRange(0.0, 10.0);
        predictionPanel.setDrawConfidenceBand(true);
        predictionPanel.setDrawPredictionBand(true);
        add("Confidence & Prediction Bands", predictionPanel);
        
        
    }
    
    /**
    * update panels based on data in rc
    * @param rc RegressionCalculator with the data
    */
    public void update(RegressionCalculator rc){
        residualPanel.update(rc);
        residualPanel.repaint();
        estimatePane.update(rc);
        predictionPanel.update(rc);
    }


  

}
