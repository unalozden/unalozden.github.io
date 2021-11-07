package regress;


import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.applet.*;
import javax.swing.event.*;
import java.awt.event.*;
import edu.csusb.danby.applet.*;
import edu.csusb.danby.stat.*;
import java.util.*;

/**
* Illustrates finding the least squares regression line and related statistics
* @author Charles S. Stanton
* @version Tue Jul 30 14:32:41 PDT 2002
*/
public class RegressionApplet extends DApplet 
        implements  InteractiveRegressionPanel.InteractiveControl  {
    private InteractiveRegressionPanel regressionPanel;
    private ResidualPanel residualPanel;
    private AnalysisPane analysisPane;
    private RegressionCalculator regressionCalculator;
    JMenuBar menubar;
    JMenu file, show;
    DControlPanel cp;
    String[] fileMenuItem = {"Çýkýþ"};
    JToggleButton confidenceButton;
    JToggleButton predictionButton;
    
    /**
    * init method for applet
    */
    public void init () {
        double [] xv = new double[0];
        double [] yv = new double[0];
        regressionCalculator = new RegressionCalculator(xv,yv);
        regressionPanel = new InteractiveRegressionPanel(regressionCalculator, this);
        analysisPane = new AnalysisPane(regressionCalculator, "x", "y");	
        dFrame  = new DFrame("Regresyon", regressionPanel,analysisPane);
        dFrame.setSize(600,400);
        String[]  buttonLabels ={"Sil"};
        cp = new DControlPanel(dFrame, this, buttonLabels);
        menubar = new JMenuBar();
        dFrame.setJMenuBar(menubar);
    
        // construct "Dosya" menu with "Çýkýþ"
        file = new DMenu(this, "Dosya", fileMenuItem);
        menubar.add(file);
    
        dFrame.pack();
        dFrame.validate();
        setVisible(true);
    } 

    /**
    * override DApplet.controlPanelButtonAction() to provide
    * correct behavior for buttons
    */
    public void doControlPanelButtonAction( ActionEvent e){
        String label= e.getActionCommand();
        if (label.equals("Sil")){
            regressionCalculator.reset(); 
            regressionPanel.update(regressionCalculator);
            analysisPane.update(regressionCalculator);
            }
    }

    /**
    * override DApplet.menuAction() to provide menu actions
    */
    public void menuAction( String menuLabel, String itemLabel){
        if (menuLabel.equals("Dosya")){
            if (itemLabel.equals("Çýkýþ")){
                dFrame.dispose();
                }
            }
    }
        
    /**
    * update display using values in regressionCalculator
    */
    public void update(RegressionCalculator regressionCalculator){
        analysisPane.update(regressionCalculator);
        regressionPanel.update(regressionCalculator);
    }
    
      /**
    * provides applet info
    */
     public String getAppletInfo() {
        return "An interactive regression applet.\nAuthor: Charles S. Stanton; Updated Ünal Özden, 2007";
    }
}



