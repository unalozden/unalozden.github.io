import java.awt.*;
import java.io.*;
import java.applet.*;
import graphCanvas;
import allConstraints;
import Dictionaries;

public class LinearProg extends Applet
{
  private graphCanvas gCanvas;
  private allConstraints lpConstraints;
  private Dictionaries lpDictionary;
  public B_Inverse lpB_Inverse;
  Button b_clear;
  Button b_help;
  Button b_example;
  Button b_zoom;
  Button b_addCons;
  Button b_removeCons;
  Button b_changeObj;
  Button b_pivot;
  Button b_cones;
  Button b_conesAll;
  Button b_objNormal;
  Button b_conesOne;
  Button b_inverse;

  TextField tf_x1;
  TextField tf_x2;
  TextField tf_const;
  TextField tf_zoom;

  TextField tf_x1Obj;
  TextField tf_x2Obj;
  
  TextArea ta_dictionary;
  TextArea ta_constraints;
  Choice c_removeCons;
  Choice c_pEnter;
  Choice c_pLeave;
  Choice c_example;
  boolean allCones = false;
  boolean oneCone = false;
  boolean inverse = false;
  boolean goodPivot = true;
  	
  //*************************************************************
  public boolean action(Event evt, Object obj)
  {
      //####################################
      //####################################
      if (evt.target.equals(b_inverse))
	  {//check to see that something is drawn... and initialized
	      if(lpConstraints.num_of_constraints == 0)
		  {
		      //tf_info.setText("no constraints entered!");
		      return true;
		  }
	      else
		  {
		      if(inverse)
			  {
			      inverse = false;
			      ta_constraints.setText(lpConstraints.allConstraintsString);
			  }
		      else
			  {
			      inverse = true;
			      ta_constraints.setText(lpB_Inverse.B_InverseString);
			  }

		  }
	  }
      //####################################
      //####################################
      if (evt.target.equals(b_cones))
	  {//check to see that something is drawn... and initialized
	      if(lpConstraints.num_of_constraints == 0)
		  {
		      //tf_info.setText("no constraints entered!");
		      return true;
	      }
	      if(allCones)
		  {
		      gCanvas.reset();
		      gCanvas.drawConstraints(lpConstraints,Integer.parseInt(tf_zoom.getText()));
		      gCanvas.drawDictionary(lpDictionary,Integer.parseInt(tf_zoom.getText()));
		  }
	      gCanvas.DrawCones(false,false);
	      allCones = false;
	      oneCone = false;
	  }
      //####################################
      //####################################
      if (evt.target.equals(b_conesOne))
	  {//check to see that something is drawn... and initialized
	      if(lpConstraints.num_of_constraints == 0)
		  {
		      //tf_info.setText("no constraints entered!");
		      return true;
	      }
	      if(allCones)
		  {
		      gCanvas.reset();
		      gCanvas.drawConstraints(lpConstraints,Integer.parseInt(tf_zoom.getText()));
		      gCanvas.drawDictionary(lpDictionary,Integer.parseInt(tf_zoom.getText()));
		  }
	      if(!oneCone)
		  {
		      gCanvas.DrawCones(true,true,true);
		      allCones = false;
		      oneCone = true;
		  }
	      else
		  {
		      allCones = false;
		      oneCone = false;
		  }
	  }
      //####################################
      //####################################
      if (evt.target.equals(b_conesAll))
	  {//check to see that something is drawn... and initialized
	      if(lpConstraints.num_of_constraints == 0)
		  {
		      //tf_info.setText("no constraints entered!");
		      return true;
	      }
	      gCanvas.DrawCones(false,true);
	      allCones = true;
	      oneCone = false;
	  }
      //####################################
      //####################################
      if (evt.target.equals(b_objNormal))
	  {//check to see that something is drawn... and initialized
	      if(lpConstraints.num_of_constraints == 0)
		  {
		      //tf_info.setText("no constraints entered!");
		      return true;
	      }
	      gCanvas.DrawCones(true,allCones);
	  		gCanvas.drawObjective(lpConstraints,Integer.parseInt(tf_zoom.getText()));
	  }
      //####################################
      //####################################
    if (evt.target.equals(b_clear))
      {
        gCanvas.reset();
        oneCone = false;
	allCones = false;
	goodPivot = true;
	//clear obj
	  //clear dictionary & constraints
	lpConstraints = new allConstraints();
	lpDictionary = null;
	lpB_Inverse = null;
	ta_constraints.setText("no constraints");
	ta_dictionary.setText("no constraints");
      	tf_x2Obj.setText("");
      	tf_x1Obj.setText("");
	tf_zoom.setText("1");
      	c_pEnter.removeAll();
	c_pEnter.add("Enter");
      	c_pLeave.removeAll();
      	c_pLeave.add("Leave");
      	c_removeCons.removeAll();
      	c_removeCons.add("none");
      	//tf_info.setText("Click on Example, or input you own constraints: Enter integers in all fields!");
      	//tf_feasible.setText("Feasibility Info: (No constraints entered)");

	
        return true;
      }
      //####################################
      //####################################
     if (evt.target.equals(b_zoom))
  	    {
		if(lpConstraints.num_of_constraints == 0)
		    {
			//tf_info.setText("no constraints entered!");
			return true;
		    }
		gCanvas.reset();
  	    	gCanvas.drawConstraints(lpConstraints,Integer.parseInt(tf_zoom.getText()));
		gCanvas.drawDictionary(lpDictionary,Integer.parseInt(tf_zoom.getText()));
      		//tf_info.setText("Canvas refreshed with new zoom factor");
		oneCone = false;
  	    	return true;
  	    }
      //####################################
      //####################################
    if (evt.target.equals(b_example))
      {
     gCanvas.reset();
	 //tf_info.setText("Example initialized: you may pivot, add/remove constraints, or change objective");

	 lpConstraints = new allConstraints();
	 lpDictionary = null;
	 lpB_Inverse = null;
	 ta_constraints.setText("no constraints");
	 ta_dictionary.setText("no constraints");

	 if(c_example.getSelectedIndex() == 0)
	     {
		 //Default Example
		 tf_x2Obj.setText("2");
		 tf_x1Obj.setText("1");
		 lpConstraints.x1_obj = Integer.parseInt(tf_x1Obj.getText());
		 lpConstraints.x2_obj = Integer.parseInt(tf_x2Obj.getText());
		 tf_zoom.setText("15");
		 c_pEnter.removeAll();
		 c_pEnter.add("Enter");
		 c_pLeave.removeAll();
		 c_pLeave.add("Leave");
		 c_removeCons.removeAll();
		 c_removeCons.add("none");
		 //tf_feasible.setText("Feasibility Info: (No constraints entered)");
		 lpConstraints.addConstraint("1","1","10");
		 lpConstraints.addConstraint("-1","1","5");
		 lpConstraints.addConstraint("1","-1","5");
		 lpConstraints.addConstraint("-1","0","0");
		 lpConstraints.addConstraint("0","-1","0");
		 lpConstraints.addConstraint("1","0","6");
		 
		 for(int i=1;i<=6;i++)
		     {
			 c_removeCons.add(" ("+i+") ");
		     }
		 tf_zoom.setText("15");
		 //Default End
	     }
	 else if(c_example.getSelectedIndex() == 1)
	     {
		 //Infeasible Example
		 tf_x2Obj.setText("2");
		 tf_x1Obj.setText("-1");
		 lpConstraints.x1_obj = Integer.parseInt(tf_x1Obj.getText());
		 lpConstraints.x2_obj = Integer.parseInt(tf_x2Obj.getText());
		 tf_zoom.setText("15");
		 c_pEnter.removeAll();
		 c_pEnter.add("Enter");
		 c_pLeave.removeAll();
		 c_pLeave.add("Leave");
		 c_removeCons.removeAll();
		 c_removeCons.add("none");
		 //tf_feasible.setText("Feasibility Info: (No constraints entered)");
		 lpConstraints.addConstraint("1","1","10");
		 lpConstraints.addConstraint("-1","-1","-20");
		 lpConstraints.addConstraint("0","1","5");
		 lpConstraints.addConstraint("1","0","8");
		 lpConstraints.addConstraint("-1","0","0");
		 lpConstraints.addConstraint("0","-1","0");

		 for(int i=1;i<=6;i++)
		     {
			 c_removeCons.add(" ("+i+") ");
		     }
		 tf_zoom.setText("15");
		 //INFEASIBLE End
	     }
	 else if(c_example.getSelectedIndex() == 2)
	     {
		 //Unbounded Example
		 tf_x2Obj.setText("3");
		 tf_x1Obj.setText("4");
		 lpConstraints.x1_obj = Integer.parseInt(tf_x1Obj.getText());
		 lpConstraints.x2_obj = Integer.parseInt(tf_x2Obj.getText());
		 tf_zoom.setText("15");
		 c_pEnter.removeAll();
		 c_pEnter.add("Enter");
		 c_pLeave.removeAll();
		 c_pLeave.add("Leave");
		 c_removeCons.removeAll();
		 c_removeCons.add("none");
		 //tf_feasible.setText("Feasibility Info: (No constraints entered)");
		 lpConstraints.addConstraint("-2","1","10");
		 lpConstraints.addConstraint("-1","-1","-15");
		 lpConstraints.addConstraint("1","-2","5");
		 lpConstraints.addConstraint("-1","0","0");
		 lpConstraints.addConstraint("0","-1","0");		 

		 for(int i=1;i<=5;i++)
		     {
			 c_removeCons.add(" ("+i+") ");
		     }
		 tf_zoom.setText("10");
		 //Unbounded End
	     }
	 else if(c_example.getSelectedIndex() == 3)
	     {
		 //Chvatal p.260 Example
		 tf_x2Obj.setText("1");
		 tf_x1Obj.setText("1");
		 lpConstraints.x1_obj = Integer.parseInt(tf_x1Obj.getText());
		 lpConstraints.x2_obj = Integer.parseInt(tf_x2Obj.getText());
		 tf_zoom.setText("15");
		 c_pEnter.removeAll();
		 c_pEnter.add("Enter");
		 c_pLeave.removeAll();
		 c_pLeave.add("Leave");
		 c_removeCons.removeAll();
		 c_removeCons.add("none");
		 //tf_feasible.setText("Feasibility Info: (No constraints entered)");
		 lpConstraints.addConstraint("2","1","14");
		 lpConstraints.addConstraint("-1","2","8");
		 lpConstraints.addConstraint("2","-1","10");
		 lpConstraints.addConstraint("-1","0","0");
		 lpConstraints.addConstraint("0","-1","0");

		 for(int i=1;i<=5;i++)
		     {
			 c_removeCons.add(" ("+i+") ");
		     }
		 tf_zoom.setText("12");
		 //Chvatal p.260 End
	     }
	 else if(c_example.getSelectedIndex() == 4)
	     {
		 //Chvatal p.26 Example
		 tf_x2Obj.setText("1");
		 tf_x1Obj.setText("2");
		 lpConstraints.x1_obj = Integer.parseInt(tf_x1Obj.getText());
		 lpConstraints.x2_obj = Integer.parseInt(tf_x2Obj.getText());
		 tf_zoom.setText("15");
		 c_pEnter.removeAll();
		 c_pEnter.add("Enter");
		 c_pLeave.removeAll();
		 c_pLeave.add("Leave");
		 c_removeCons.removeAll();
		 c_removeCons.add("none");
		 //tf_feasible.setText("Feasibility Info: (No constraints entered)");
		 lpConstraints.addConstraint("2","3","3");
		 lpConstraints.addConstraint("1","5","1");
		 lpConstraints.addConstraint("2","1","4");
		 lpConstraints.addConstraint("4","1","5");
		 lpConstraints.addConstraint("-1","0","0");
		 lpConstraints.addConstraint("0","-1","0");
		 
		 for(int i=1;i<=6;i++)
		     {
			 c_removeCons.add(" ("+i+") ");
		     }
		 tf_zoom.setText("36");
		 //Chvatal p.26 End
	     }
	 else if(c_example.getSelectedIndex() == 5)
	     {
		 //Chvatal p.9 1.1a Example
		 tf_x2Obj.setText("-5");
		 tf_x1Obj.setText("3");
		 lpConstraints.x1_obj = Integer.parseInt(tf_x1Obj.getText());
		 lpConstraints.x2_obj = Integer.parseInt(tf_x2Obj.getText());
		 tf_zoom.setText("15");
		 c_pEnter.removeAll();
		 c_pEnter.add("Enter");
		 c_pLeave.removeAll();
		 c_pLeave.add("Leave");
		 c_removeCons.removeAll();
		 c_removeCons.add("none");
		 //tf_feasible.setText("Feasibility Info: (No constraints entered)");
		 lpConstraints.addConstraint("-4","-5","-3");
		 lpConstraints.addConstraint("6","-6","7");
		 lpConstraints.addConstraint("-6","6","-7");
		 lpConstraints.addConstraint("1","8","20");
		 lpConstraints.addConstraint("-1","0","0");
		 lpConstraints.addConstraint("0","-1","0");

		 
		 for(int i=1;i<=6;i++)
		     {
			 c_removeCons.add(" ("+i+") ");
		     }
		 tf_zoom.setText("8");
		 //Chvatal p.9 1.1a End
	     }
	 else if(c_example.getSelectedIndex() == 6)
	     {
		 //Chvatal p.9 1.1c Example
		 tf_x2Obj.setText("-4");
		 tf_x1Obj.setText("8");
		 lpConstraints.x1_obj = Integer.parseInt(tf_x1Obj.getText());
		 lpConstraints.x2_obj = Integer.parseInt(tf_x2Obj.getText());
		 tf_zoom.setText("15");
		 c_pEnter.removeAll();
		 c_pEnter.add("Enter");
		 c_pLeave.removeAll();
		 c_pLeave.add("Leave");
		 c_removeCons.removeAll();
		 c_removeCons.add("none");
		 //tf_feasible.setText("Feasibility Info: (No constraints entered)");
		 lpConstraints.addConstraint("3","1","7");
		 lpConstraints.addConstraint("-1","1","5");
		 lpConstraints.addConstraint("9","5","-2");
		 lpConstraints.addConstraint("-1","0","0");
		 lpConstraints.addConstraint("0","-1","0");
		 
		 for(int i=1;i<=5;i++)
		     {
			 c_removeCons.add(" ("+i+") ");
		     }
		 tf_zoom.setText("15");
		 //Chvatal p.9 1.1c End
	     }
	 else if(c_example.getSelectedIndex() == 7)
	     {
		 //Chvatal p.52 4.1a Example
		 tf_x2Obj.setText("5");
		 tf_x1Obj.setText("4");
		 lpConstraints.x1_obj = Integer.parseInt(tf_x1Obj.getText());
		 lpConstraints.x2_obj = Integer.parseInt(tf_x2Obj.getText());
		 tf_zoom.setText("15");
		 c_pEnter.removeAll();
		 c_pEnter.add("Enter");
		 c_pLeave.removeAll();
		 c_pLeave.add("Leave");
		 c_removeCons.removeAll();
		 c_removeCons.add("none");
		 //tf_feasible.setText("Feasibility Info: (No constraints entered)");
		 lpConstraints.addConstraint("2","1","9");
		 lpConstraints.addConstraint("1","0","4");
		 lpConstraints.addConstraint("0","1","3");
		 lpConstraints.addConstraint("-1","0","0");
		 lpConstraints.addConstraint("0","-1","0");
		 
		 for(int i=1;i<=5;i++)
		     {
			 c_removeCons.add(" ("+i+") ");
		     }
		 tf_zoom.setText("20");
		 //Chvatal p.52 4.1a End
	     }
	 else if(c_example.getSelectedIndex() == 8)
	     {
		 //Chvatal p.52 4.1b Example
		 tf_x2Obj.setText("1");
		 tf_x1Obj.setText("2");
		 lpConstraints.x1_obj = Integer.parseInt(tf_x1Obj.getText());
		 lpConstraints.x2_obj = Integer.parseInt(tf_x2Obj.getText());
		 tf_zoom.setText("15");
		 c_pEnter.removeAll();
		 c_pEnter.add("Enter");
		 c_pLeave.removeAll();
		 c_pLeave.add("Leave");
		 c_removeCons.removeAll();
		 c_removeCons.add("none");
		 //tf_feasible.setText("Feasibility Info: (No constraints entered)");
		 lpConstraints.addConstraint("3","1","3");
		 lpConstraints.addConstraint("-1","0","0");
		 lpConstraints.addConstraint("0","-1","0");
		 
		 for(int i=1;i<=3;i++)
		     {
			 c_removeCons.add(" ("+i+") ");
		     }
		 tf_zoom.setText("45");
		 //Chvatal p.52 4.1b End
	     }
	 else if(c_example.getSelectedIndex() == 9)
	     {
		 //Chvatal p.52 4.1c Example
		 tf_x2Obj.setText("5");
		 tf_x1Obj.setText("3");
		 lpConstraints.x1_obj = Integer.parseInt(tf_x1Obj.getText());
		 lpConstraints.x2_obj = Integer.parseInt(tf_x2Obj.getText());
		 tf_zoom.setText("15");
		 c_pEnter.removeAll();
		 c_pEnter.add("Enter");
		 c_pLeave.removeAll();
		 c_pLeave.add("Leave");
		 c_removeCons.removeAll();
		 c_removeCons.add("none");
		 //tf_feasible.setText("Feasibility Info: (No constraints entered)");
		 lpConstraints.addConstraint("1","2","5");
		 lpConstraints.addConstraint("1","0","3");
		 lpConstraints.addConstraint("0","1","2");
		 lpConstraints.addConstraint("-1","0","0");
		 lpConstraints.addConstraint("0","-1","0");
		 
		 for(int i=1;i<=5;i++)
		     {
			 c_removeCons.add(" ("+i+") ");
		     }
		 tf_zoom.setText("30");
		 //Chvatal p.52 4.1c End
	     }
	 else if(c_example.getSelectedIndex() == 10)
	     {
		 //Bohdan's creation
		 tf_x2Obj.setText("-1");
		 tf_x1Obj.setText("-3");
		 lpConstraints.x1_obj = Integer.parseInt(tf_x1Obj.getText());
		 lpConstraints.x2_obj = Integer.parseInt(tf_x2Obj.getText());
		 tf_zoom.setText("10");
		 c_pEnter.removeAll();
		 c_pEnter.add("Enter");
		 c_pLeave.removeAll();
		 c_pLeave.add("Leave");
		 c_removeCons.removeAll();
		 c_removeCons.add("none");
		 //tf_feasible.setText("Feasibility Info: (No constraints entered)");
		 lpConstraints.addConstraint("1","1","10");
		 lpConstraints.addConstraint("-1","1","10");
		 lpConstraints.addConstraint("1","-1","10");
		 lpConstraints.addConstraint("-1","-1","10");
		 lpConstraints.addConstraint("0","-1","7");
		 lpConstraints.addConstraint("0","1","7");
		 lpConstraints.addConstraint("-1","0","7");
		 lpConstraints.addConstraint("1","0","7");		 
		 for(int i=1;i<=8;i++)
		     {
			 c_removeCons.add(" ("+i+") ");
		     }
		 tf_zoom.setText("10");
		 //Chvatal p.52 4.1c End
	     }
     else if(c_example.getSelectedIndex() == 11)

	     {
		 //Degenerate/CUSTOM Example
		 //---------------------//
		 //---------------------//
		 tf_x1Obj.setText("2"); //  change value "2" into objective constant of x1
		 tf_x2Obj.setText("1"); //  change value "1" to objective constant of x2
		 //---------------------//
		 //---------------------//
		 lpConstraints.x1_obj = Integer.parseInt(tf_x1Obj.getText());
		 lpConstraints.x2_obj = Integer.parseInt(tf_x2Obj.getText());
		 tf_zoom.setText("15");
		 c_pEnter.removeAll();
		 c_pEnter.add("Enter");
		 c_pLeave.removeAll();
		 c_pLeave.add("Leave");
		 c_removeCons.removeAll();
		 c_removeCons.add("none");
		 //tf_feasible.setText("Feasibility Info: (No constraints entered)");
		 //----------------------------------------//
		 //----------------------------------------//
		 //----------------------------------------//
		 lpConstraints.addConstraint("1","3","4"); // this represents 1x1 + 3x2 <= 4
		 lpConstraints.addConstraint("5","1","5"); // add/modify/delete as you wish!!
		 lpConstraints.addConstraint("3","2","2");
		 lpConstraints.addConstraint("-1","-3","1");
		 lpConstraints.addConstraint("-2","1","2");
		 //----------------------------------------//
		 //----------------------------------------//
		 //----------------------------------------//
		 //IMPORTANT: THE FOLLOWING MUST REFLECT THE NUMBER OF CONSTRAINTS YOU HAVE ABOVE!
		 for(int i=1;i<=5 /*CHANGE 5 INTO YOUR # of constraints!*/;i++)
		     {
			 c_removeCons.add(" ("+i+") ");
		     }
                 tf_zoom.setText("65");  //set zoom factor as you wish!!
		 //CUSTOM/Degenerate #1 End
	     }
	 else if(c_example.getSelectedIndex() == 12)
	     {
		 //Class Example #1
		 tf_x2Obj.setText("1");
		 tf_x1Obj.setText("2");
		 lpConstraints.x1_obj = Integer.parseInt(tf_x1Obj.getText());
		 lpConstraints.x2_obj = Integer.parseInt(tf_x2Obj.getText());
		 tf_zoom.setText("15");
		 c_pEnter.removeAll();
		 c_pEnter.add("Enter");
		 c_pLeave.removeAll();
		 c_pLeave.add("Leave");
		 c_removeCons.removeAll();
		 c_removeCons.add("none");
		 //tf_feasible.setText("Feasibility Info: (No constraints entered)");
		 lpConstraints.addConstraint("1","1","8");
		 lpConstraints.addConstraint("1","3","18");
		 lpConstraints.addConstraint("1","-1","4");
		 lpConstraints.addConstraint("-1","0","0");
		 lpConstraints.addConstraint("0","-1","0");
		 
		 for(int i=1;i<=5;i++)
		     {
			 c_removeCons.add(" ("+i+") ");
		     }
		 tf_zoom.setText("22");
		 //Class Exmaple #1	     
	     }
	 else if(c_example.getSelectedIndex() == 13)
	     {
		 //Degenerate #2
		 tf_x2Obj.setText("1");
		 tf_x1Obj.setText("2");
		 lpConstraints.x1_obj = Integer.parseInt(tf_x1Obj.getText());
		 lpConstraints.x2_obj = Integer.parseInt(tf_x2Obj.getText());
		 tf_zoom.setText("7");
		 c_pEnter.removeAll();
		 c_pEnter.add("Enter");
		 c_pLeave.removeAll();
		 c_pLeave.add("Leave");
		 c_removeCons.removeAll();
		 c_removeCons.add("none");
		 //tf_feasible.setText("Feasibility Info: (No constraints entered)");
		 //lpConstraints.addConstraint("174","-255","1008"); //[3]
		 //lpConstraints.addConstraint("73","10","783"); //[8]
		 //lpConstraints.addConstraint("106","-17","1200"); //[7]
		 //	ta_constraints.setText(lpConstraints.allConstraintsString);
		 lpConstraints.addConstraint("6","-21","108"); //[2]
		 lpConstraints.addConstraint("6","-21","108"); //[2 again]
		 lpConstraints.addConstraint("-9","2","15"); //[4]
		 lpConstraints.addConstraint("-9","2","15"); //[4 again]
		 lpConstraints.addConstraint("-4","-1","18"); //[5]
		 lpConstraints.addConstraint("-4","-1","18"); //[5 again]
		 lpConstraints.addConstraint("-9","-7","69"); //[6]
		 lpConstraints.addConstraint("3","-1","54"); //[9]
		 lpConstraints.addConstraint("7","5","67"); //[10]
		 //	ta_constraints.setText(lpConstraints.allConstraintsString);
		 lpConstraints.addConstraint("3","1","54"); //[11]
		 lpConstraints.addConstraint("12","17","216"); //[12]
		 lpConstraints.addConstraint("2","5","36"); //[13]
		 lpConstraints.addConstraint("-1","4","47"); //[14]
		 lpConstraints.addConstraint("-7","3","79"); //[15]
		 lpConstraints.addConstraint("-9","11","123"); //[16]
		 //	ta_constraints.setText(lpConstraints.allConstraintsString);
		 lpConstraints.addConstraint("-1","0","0"); //[x1>=0]
		 lpConstraints.addConstraint("0","-1","0"); //[x2>=0]
		 
		 for(int i=1;i<=17;i++)
		     {
			 c_removeCons.add(" ("+i+") ");
		     }
		 tf_zoom.setText("7");
		 //Degenerate #2	     
	     }
	 else if(c_example.getSelectedIndex() == 14)
	     {
		 //Parabola #1
		 tf_x2Obj.setText("-1");
		 tf_x1Obj.setText("0");
		 lpConstraints.x1_obj = Integer.parseInt(tf_x1Obj.getText());
		 lpConstraints.x2_obj = Integer.parseInt(tf_x2Obj.getText());
		 tf_zoom.setText("18");
		 c_pEnter.removeAll();
		 c_pEnter.add("Enter");
		 c_pLeave.removeAll();
		 c_pLeave.add("Leave");
		 c_removeCons.removeAll();
		 c_removeCons.add("none");
		 //tf_feasible.setText("Feasibility Info: (No constraints entered)");
		 lpConstraints.addConstraint("0","-1","0");
		 lpConstraints.addConstraint("-1","-10","-10");
		 lpConstraints.addConstraint("-2","-9","-18");
		 lpConstraints.addConstraint("-3","-8","-24");		 
		 lpConstraints.addConstraint("-4","-7","-28");
		 lpConstraints.addConstraint("-5","-6","-30");
		 lpConstraints.addConstraint("-6","-5","-30");
		 lpConstraints.addConstraint("-7","-4","-28");
		 lpConstraints.addConstraint("-8","-3","-24");
		 lpConstraints.addConstraint("-9","-2","-18");
		 lpConstraints.addConstraint("-10","-1","-10");
		 lpConstraints.addConstraint("-1","0","0");
		 for(int i=1;i<=12;i++)
		     {
			 c_removeCons.add(" ("+i+") ");
		     }
		 tf_zoom.setText("18");
		 //Parabola #1	     
	     }
	 else if(c_example.getSelectedIndex() == 15)
	     {
		 //Parabola #2
		 tf_x2Obj.setText("-1");
		 tf_x1Obj.setText("0");
		 lpConstraints.x1_obj = Integer.parseInt(tf_x1Obj.getText());
		 lpConstraints.x2_obj = Integer.parseInt(tf_x2Obj.getText());
		 tf_zoom.setText("18");
		 c_pEnter.removeAll();
		 c_pEnter.add("Enter");
		 c_pLeave.removeAll();
		 c_pLeave.add("Leave");
		 c_removeCons.removeAll();
		 c_removeCons.add("none");
		 //tf_feasible.setText("Feasibility Info: (No constraints entered)");
		 lpConstraints.addConstraint("0","-1","0");
		 lpConstraints.addConstraint("-2","-9","-18");
		 lpConstraints.addConstraint("-4","-7","-28");
		 lpConstraints.addConstraint("-6","-5","-30");
		 lpConstraints.addConstraint("-8","-3","-24");
		 lpConstraints.addConstraint("-10","-1","-10");
		 lpConstraints.addConstraint("2","-9","22");
		 lpConstraints.addConstraint("4","-7","52");
		 lpConstraints.addConstraint("6","-5","90");
		 lpConstraints.addConstraint("8","-3","136");
		 lpConstraints.addConstraint("10","-1","190");
		 lpConstraints.addConstraint("1","0","20");
		 lpConstraints.addConstraint("-1","0","0");

		 for(int i=1;i<=13;i++)
		     {
			 c_removeCons.add(" ("+i+") ");
		     }
		 tf_zoom.setText("18");
		 //Parabola #2	     
	     }

	 else if(c_example.getSelectedIndex() == 16)
	     {
		 //Bland's Redundant
		 tf_x2Obj.setText("-1");
		 tf_x1Obj.setText("0");
		 lpConstraints.x1_obj = Integer.parseInt(tf_x1Obj.getText());
		 lpConstraints.x2_obj = Integer.parseInt(tf_x2Obj.getText());
		 tf_zoom.setText("18");
		 c_pEnter.removeAll();
		 c_pEnter.add("Enter");
		 c_pLeave.removeAll();
		 c_pLeave.add("Leave");
		 c_removeCons.removeAll();
		 c_removeCons.add("none");
		 //tf_feasible.setText("Feasibility Info: (No constraints entered)");
		 lpConstraints.addConstraint("0","-1","0");
		 lpConstraints.addConstraint("-1","0","0");

		 lpConstraints.addConstraint("-7","-4","-28");
		 lpConstraints.addConstraint("-6","-4","-28");
		 lpConstraints.addConstraint("-5","-4","-28");

		 lpConstraints.addConstraint("-67","-84","-420");
		 lpConstraints.addConstraint("-4","-7","-28");
		 lpConstraints.addConstraint("-29","-84","-252");

		 for(int i=1;i<=8;i++)
		     {
			 c_removeCons.add(" ("+i+") ");
		     }
		 tf_zoom.setText("18");
		 //Bland's Redundant	     
	     }
	c_removeCons.select(0);

	tf_x1.setText("");
	tf_x2.setText("");
	tf_const.setText("");
	ta_constraints.setText(lpConstraints.allConstraintsString);
	inverse = false;
	
	//send to dictionary (with slack as basic variable)
	lpDictionary = new Dictionaries(lpConstraints);
	ta_dictionary.setText(lpDictionary.DictionaryString);
	lpB_Inverse = new B_Inverse(lpDictionary);

	c_pEnter.removeAll();
	c_pEnter.addItem("Enter");
	c_pEnter.addItem("x1");
	c_pEnter.addItem("x2");
	c_pLeave.removeAll();
	c_pLeave.addItem("Leave");
	for(int i=3;i<= lpConstraints.num_of_constraints+2;i++)
	    {
		c_pLeave.addItem("x"+i);
	    }
	goodPivot = true;
	//send to graphCanvas
	gCanvas.drawAxis();
	gCanvas.drawConstraints(lpConstraints,Integer.parseInt(tf_zoom.getText()));
	gCanvas.drawDictionary(lpDictionary,Integer.parseInt(tf_zoom.getText()));
	//tf_feasible.setText("Feasibility Info: <Click on canvas to determine feasiblity of mouse point>"); 
	
        return true;	
      }
      //####################################
      //####################################
  	if (evt.target.equals(b_addCons))
      {
      	 //gCanvas.addCons();
	lpConstraints.addConstraint(tf_x1.getText(),tf_x2.getText(),tf_const.getText());
      	if(c_removeCons.getItemCount() == lpConstraints.num_of_constraints+1)
      	{
      		return true;
      	}
      	else
      	{
      		c_removeCons.add(" ("+lpConstraints.num_of_constraints+") ");
      		c_removeCons.select(0);
      		tf_x1.setText("");
      		tf_x2.setText("");
      		tf_const.setText("");
      		ta_constraints.setText(lpConstraints.allConstraintsString);
      		inverse = false;

      		//send to dictionary (with slack as basic variable)
		lpDictionary = new Dictionaries(lpConstraints);
		lpB_Inverse = new B_Inverse(lpDictionary);
      		ta_dictionary.setText(lpDictionary.DictionaryString);
 					
      		c_pEnter.removeAll();
      		c_pEnter.addItem("Enter");
      		c_pEnter.addItem("x1");
      		c_pEnter.addItem("x2");
      		c_pLeave.removeAll();
      		c_pLeave.addItem("Leave");
      		for(int i=3;i<= lpConstraints.num_of_constraints+2;i++)
      		{
      			c_pLeave.addItem("x"+i);
      		}
	   	 //send to graphCanvas
		gCanvas.drawAxis();
		gCanvas.drawConstraints(lpConstraints,Integer.parseInt(tf_zoom.getText()));
		gCanvas.drawDictionary(lpDictionary,Integer.parseInt(tf_zoom.getText()));
      //tf_info.setText("Constraint was added");
		//tf_feasible.setText("Feasibility Info: <Click on canvas to determine feasiblity of mouse point>");
      	}
	      goodPivot = true;

	 //send to graphCanvas
      	 return true;
      }
      //####################################
      //####################################
    if (evt.target.equals(b_removeCons))
      {
        //gCanvas.removeCons();
	  //re-initialize graphCanvas  
	  //romove from constraints
	  if(lpConstraints.num_of_constraints == 0)
	      {
		  //tf_info.setText("no constraints entered!");
		  return true;
	      }
	  if(c_removeCons.getSelectedIndex()==0)
	      {
		  //tf_info.setText("Please select the constraint you wish to remove");
		  return true;
	      }
	  lpConstraints.removeConstraint(c_removeCons.getSelectedIndex());
	  if(c_removeCons.getItemCount() == lpConstraints.num_of_constraints+2)
	      {
		  c_removeCons.remove(lpConstraints.num_of_constraints+1);
		  ta_constraints.setText(lpConstraints.allConstraintsString);
		  inverse = false;
		  //remove from dictionary - reinitialize dictionary
		  lpDictionary = null;
		  lpDictionary = new Dictionaries(lpConstraints);
		  lpB_Inverse = new B_Inverse(lpDictionary);
		  ta_dictionary.setText(lpDictionary.DictionaryString);
		  
		  c_pEnter.removeAll();
		  c_pEnter.addItem("Enter");
		  c_pEnter.addItem("x1");
		  c_pEnter.addItem("x2");
		  c_pLeave.removeAll();
		  c_pLeave.addItem("Leave");
		  for(int i=3;i<= lpConstraints.num_of_constraints+2;i++)
		      {
			  c_pLeave.add("x"+i);
		      }
		  
	      }
	  c_removeCons.select(0);
	  gCanvas.reset();
	  gCanvas.drawAxis();
	  gCanvas.drawConstraints(lpConstraints,Integer.parseInt(tf_zoom.getText()));	       
	  gCanvas.drawDictionary(lpDictionary,Integer.parseInt(tf_zoom.getText()));
	  //tf_info.setText("Constraint was removed");
	  if(lpConstraints.num_of_constraints == 0)
	      {
		  //tf_feasible.setText("Feasibility Info: no constraints!");
	      }
	       
	  goodPivot = true;
      }
    //####################################
    //####################################  
    if (evt.target.equals(b_changeObj))
      {
	  //gCanvas.changeObj();
	  //change dictionary
	  //change graphCanvas
	  
	  lpConstraints.x1_obj = Integer.parseInt(tf_x1Obj.getText());
	  lpConstraints.x2_obj = Integer.parseInt(tf_x2Obj.getText());
      	  lpConstraints.updateString();
	  ta_constraints.setText(lpConstraints.allConstraintsString);
      	  lpDictionary = new Dictionaries(lpConstraints);
	  lpB_Inverse = new B_Inverse(lpDictionary);
	  ta_dictionary.setText(lpDictionary.DictionaryString);
	  if(inverse)
	      {
	 	 ta_constraints.setText(lpB_Inverse.B_InverseString);
	      }
	  goodPivot = true;
	  gCanvas.reset();
	  gCanvas.drawAxis();
	  gCanvas.drawConstraints(lpConstraints,Integer.parseInt(tf_zoom.getText())); 
       
	  gCanvas.LastPoint = null;
	  gCanvas.drawDictionary(lpDictionary,Integer.parseInt(tf_zoom.getText()));	
	  c_pEnter.removeAll();
	  c_pEnter.addItem("Enter");
	  c_pEnter.addItem("x1");
	  c_pEnter.addItem("x2");
	  c_pLeave.removeAll();
	  c_pLeave.addItem("Leave");
	  
	  for(int i=3;i<= lpConstraints.num_of_constraints+2;i++)
	      {
		  c_pLeave.addItem("x"+i);
	      }
	  //tf_info.setText("Objective function has been changed");
	  return true;
      }
      //####################################
      //####################################
    if (evt.target.equals(b_pivot))
	{
        //gCanvas.pivot();
	    //change dictionary
	    //change graphCanvs
	    if(lpConstraints.num_of_constraints == 0)
		{
		    goodPivot = false;
		    //tf_info.setText("no constraints entered!");
		    return true;
		}
	    goodPivot = lpDictionary.pivot(c_pEnter.getSelectedItem(),c_pLeave.getSelectedItem());
	    if(goodPivot)
		{
		    lpB_Inverse = new B_Inverse(lpDictionary);
		    if(inverse)
			{
			    ta_constraints.setText(lpB_Inverse.B_InverseString);
			}
		    ta_dictionary.setText(lpDictionary.DictionaryString);
		    String tempString = c_pEnter.getSelectedItem();
		    c_pEnter.remove(c_pEnter.getSelectedIndex());
		    c_pEnter.addItem(c_pLeave.getSelectedItem());
		    c_pLeave.remove(c_pLeave.getSelectedIndex());
		    if((Integer.parseInt(tempString.substring(1)) != 1)
		       &&(Integer.parseInt(tempString.substring(1)) != 2))
			{
			    c_pLeave.addItem(tempString);
			}
		    gCanvas.drawDictionary(lpDictionary,Integer.parseInt(tf_zoom.getText()));
		    //tf_info.setText("Pivot performed succesfully ");

		    if(oneCone)
			{
			    gCanvas.drawAxis();
			    gCanvas.drawConstraints(lpConstraints,Integer.parseInt(tf_zoom.getText()));
			    gCanvas.DrawCones(true,true,true);
			}
		}
	    else
		{
		    //tf_info.setText("Pivot cannot be performed");
		    goodPivot = false;
		}
	    return true;
      }
    
    return false;
  }
    
		
  public void paint(Graphics g) {
  
  	Color beige_old = new Color(255,255,153);
  	Color beige = new Color(255,250,240);
        setBackground(beige);
	int width = 850;
	int height = 600;
	Color darkblue = new Color(0,0,102);
	Color lightblue = new Color(0,51,153);
	b_help.setLocation(775,15);
	b_clear.setLocation(775,40);
	b_help.setSize(60,20);
	b_clear.setSize(60,20);
	b_help.setBackground(beige);
	b_clear.setBackground(beige);	
	g.setColor(Color.gray);
	g.fill3DRect(5,10,410,450,true);
	gCanvas.setLocation(10,55);
	g.fill3DRect(5,470,410,125,true);
		
	g.setColor(beige_old);
	g.drawString("Zoom Factor:",160,50);
	tf_zoom.setLocation(235,30);
	b_zoom.setLocation(285,30);
	g.setFont(new Font("Arial",Font.BOLD,13));
	Color redish = new Color(153,51,51);
	g.setColor(redish);
	g.fill3DRect(5,5,410,20,true);
	g.setColor(beige_old);
	g.drawString("Interactive Display Area",15,20);
	g.setColor(redish);
	g.fill3DRect(5,470,410,20,true);
	g.setColor(beige_old);
	g.drawString("Duality Cones",15,485);
	b_cones.setLocation(20,495);
	b_cones.setSize(80,20);
	b_conesAll.setLocation(20,520);
	b_conesAll.setSize(80,20);
	b_objNormal.setLocation(20,545);
	b_objNormal.setSize(80,20);
	b_conesOne.setLocation(20,570);
	b_conesOne.setSize(80,20);
	Color gold = new Color(51,51,51);
	g.setColor(gold);
	b_cones.setBackground(gold);
	b_cones.setForeground(Color.white);
	b_conesAll.setBackground(gold);
	b_conesAll.setForeground(Color.white);

	b_objNormal.setBackground(gold);
	b_objNormal.setForeground(Color.white);

	b_conesOne.setBackground(gold);
	b_conesOne.setForeground(Color.white);
	g.setColor(beige_old);
	g.setFont(new Font("Arial",Font.PLAIN,12));
	g.drawString("Shows the Feasibility Cones",105,510);
	g.drawString("Show Dual Cones for all Bases",105,535);
	g.drawString("Show Objective Function and Direction",105,560);
	g.drawString("Togle: Show Current Cone or Path",105,585);
	g.setColor(redish);
	g.fill3DRect(430,5,415,60,true);
	g.setColor(Color.white);
	g.fill3DRect(435,10,330,50,true);
	g.setColor(redish);
	g.setFont(new Font("Arial",Font.BOLD,12));
	g.drawString("Information Area",440,21);
	g.setColor(Color.black);
	g.setFont(new Font("Arial",Font.PLAIN,12));
	g.drawString("Linear Programming and Pivoting in 2D",440,35);
	g.drawString(">> Enter your constraints or choose example",440,50);
	
	g.setColor(Color.gray);
	g.fill3DRect(440,80,400,255,true);
	g.setColor(beige);
	g.fillRect(442,82,396,251);
	g.setColor(redish);
	g.fill3DRect(442,82,396,20,true);
	g.setColor(beige_old);
	g.setFont(new Font("Arial",Font.BOLD,13));
	g.drawString("Dictionary",447,97);
	ta_dictionary.setLocation(457,107);
	ta_dictionary.setSize(366,190);
	ta_dictionary.setBackground(Color.white);
	c_pEnter.setLocation(457,304);
	c_pLeave.setLocation(516,304);
	b_pivot.setLocation(580,304);
	b_pivot.setBackground(Color.black);
	b_pivot.setForeground(beige);
/*
	if(!goodPivot)
		{
		  g.setColor(Color.red);
	        g.setFont(new Font("Arial",Font.PLAIN,12));
		  g.drawString("Invalid Pivot, try again",640,318);
		}
	else
		{
		  g.setColor(Color.red);
	        g.setFont(new Font("Arial",Font.PLAIN,12));
		  g.drawString("Ready to Pivot",640,318);
		}
*/
	
	g.setColor(Color.gray);
	g.fill3DRect(440,345,400,250,true);
	g.setColor(redish);
	g.fill3DRect(440,345,400,20,true);
	g.setColor(beige_old);
	g.drawString("Input Constraints",450,360);
	//g.setColor(Color.black);
	g.setColor(new Color(200,200,200));
	tf_x1.setSize(30,20);
	tf_x2.setSize(30,20);
	
	tf_x1.setLocation(445,370);
	g.drawString("X1 +",478,385);
	tf_x2.setLocation(510,370);
	g.drawString("X2   <= ", 543,385);
	tf_const.setSize(30,20);
	tf_const.setLocation(588,370);
	b_addCons.setSize(30,20);
	b_addCons.setLocation(625,372);
	b_addCons.setBackground(new Color(255,230,200));
	//b_addCons.setBackground(Color.yellow);

	tf_x1Obj.setSize(30,20);
	tf_x2Obj.setSize(30,20);
	tf_x1Obj.setLocation(445,395);
	g.drawString("X1 +",478,410);
	tf_x2Obj.setLocation(510,395);
	g.drawString("X2",543,410);
	b_changeObj.setSize(80,20);
	b_changeObj.setLocation(570,396);
	b_changeObj.setBackground(new Color(255,230,200));
	//b_changeObj.setBackground(Color.yellow);

	g.setColor(new Color(200,200,200));
	g.drawRect(442,368,220,52);
	g.drawRect(667,368,168,52);
	g.drawString("Load example:", 672,384);
	c_example.setLocation(680,390);
	b_example.setSize(30,20);
	b_example.setLocation(795,392);
	b_example.setBackground(new Color(255,220,190));
	ta_constraints.setLocation(475,430);
	ta_constraints.setSize(330,130);
	ta_constraints.setBackground(Color.white);	
	b_removeCons.setLocation(475,570);
	b_removeCons.setSize(115,20);
	c_removeCons.setLocation(590,569);
	b_inverse.setSize(140,20);
	b_inverse.setLocation(690,570);
	 
	g.setColor(Color.red);
	//88888888888888
	//gCanvas.reset();
  	//    	gCanvas.drawConstraints(lpConstraints,Integer.parseInt(tf_zoom.getText()));
	//	gCanvas.drawDictionary(lpDictionary,Integer.parseInt(tf_zoom.getText()));
	//88888888888888
	//  	gCanvas.setSize(400,400);	
	  
    } 	
	
  public void init()
  {
  setBackground(new Color(255,255,153));

   	lpConstraints = new allConstraints();
 
 	b_clear = new Button("Clear All");

        add(b_clear);	
        b_help = new Button("Help!");
        add(b_help);
  	

  	b_example = new Button("Go!");    

  	add(b_example);

  	c_example = new Choice();
	c_example.add("Default");
	c_example.add("Infeasible");
	c_example.add("Unbounded");
	c_example.add("Chvatal p.260");
	c_example.add("Chv. (2.1c)");
	c_example.add("Chv. (1.1a)");
	c_example.add("Chv. (1.1c)");
	c_example.add("Chv. (4.1a)");
	c_example.add("Chv. (4.1b)");
	c_example.add("Chv. (4.1c)");
	c_example.add("General LP");
	c_example.add("Degenerate #1");
	c_example.add("Class Ex. #1");
	c_example.add("Degenerate #2");
	c_example.add("Parabola #1");	
	c_example.add("Parabola #2");
	c_example.add("Redundant");
 
  	add(c_example);
  	b_zoom = new Button("Zoom/Refresh");    
  	add(b_zoom);
  	tf_zoom = new TextField("15",2);
        tf_zoom.setEditable(true);
        tf_zoom.setBackground(Color.white);
  	add(tf_zoom);
  	b_cones = new Button("Feas. Cones");    
  	add(b_cones);
  	b_conesAll = new Button("All Cones");    
  	add(b_conesAll);
  	b_objNormal = new Button("Obj Norm");    
  	add(b_objNormal);
  	b_conesOne = new Button("Cone/Path");    
	add(b_conesOne);
//8888888888888888888
  	gCanvas = new graphCanvas(this);
	gCanvas.setSize(400,400);
	gCanvas.setBackground(Color.white);
  	add(gCanvas);
//88888888888888888
  	

	ta_dictionary = new TextArea("No constraints entered",12,45);
	ta_dictionary.setEditable(false);
	ta_dictionary.setBackground(Color.white);
  	add(ta_dictionary);

        b_pivot = new Button("Pivot");
        add(b_pivot);
  	c_pEnter = new Choice();
	c_pEnter.addItem("Enter");
  	add(c_pEnter);
  	c_pLeave = new Choice();
	c_pLeave.addItem("Leave");
  	add(c_pLeave);

  	b_inverse = new Button("Constraints / B inverse");
  	add(b_inverse);
	ta_constraints = new TextArea("No constraints entered",7,40);
	ta_constraints.setEditable(false);
	ta_constraints.setBackground(Color.white);  
  	add(ta_constraints);

        b_removeCons = new Button("Remove Constraint:");
        add(b_removeCons);
  	
	c_removeCons = new Choice();
	c_removeCons.add("none");
  	add(c_removeCons);

        b_addCons = new Button("Add");
        add(b_addCons);
  	tf_x1 = new TextField("0",2);
        tf_x1.setEditable(true);
        tf_x1.setBackground(Color.white);
  	add(tf_x1);
	tf_x2 = new TextField("0",2);
        tf_x2.setEditable(true);
        tf_x2.setBackground(Color.white);
  	add(tf_x2);
	tf_const = new TextField("0",2);
        tf_const.setEditable(true);
        tf_const.setBackground(Color.white);
  	add(tf_const);
     b_changeObj = new Button("Set Objective");
        add(b_changeObj);
  	tf_x1Obj = new TextField("0",2);
        tf_x1Obj.setEditable(true);
        tf_x1Obj.setBackground(Color.white);
  	add(tf_x1Obj);

  	tf_x2Obj = new TextField("0",2);
        tf_x2Obj.setEditable(true);
        tf_x2Obj.setBackground(Color.white);
  	add(tf_x2Obj);
	  repaint();
  }
}
