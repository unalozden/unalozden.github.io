import java.awt.*;
import java.lang.Math;
import point;
import java.util.Enumeration;
import segment;
import Constraint;
import allConstraints;
import Dictionaries;
import Intersection;
import B_Inverse;


class graphCanvas extends Canvas
{

    public point currentPoint = null;
    public int globalZoom;
    private Dictionaries currentDict = null;
    private allConstraints currentCons = null;
    private LinearProg MainThing = null;
    public point LastPoint = null;

public graphCanvas(LinearProg Main)
    {
	MainThing = Main;
	globalZoom = 1;
    }
/*****************INITIALIZATION**********************/
  public void drawAxis()
    {
	Graphics g = getGraphics();
	    Color beige = new Color(255,255,153);
    g.setColor(beige);
	g.fillRect(0,0,400,400);

	g.setColor(Color.black);
	g.drawLine(200,0,200,400);
	g.drawLine(0,200,400,200);
	g.drawString("X1",385,210);
	g.drawString("X2",185,10);
    }

/*****************Draw Objective**********************/
  public void drawObjective(allConstraints theConstraints, int zoom)
    {
    	 Constraint Objective = new Constraint(theConstraints.x1_obj,theConstraints.x2_obj,0);
      Graphics g = getGraphics();
      g.setColor(Color.orange);
	   	Objective.withZoom(zoom);
	 		g.drawLine(Objective.real1.x,Objective.real1.y,Objective.real2.x,Objective.real2.y);
    }

/*****************Draw Constraints**********************/
  public void drawConstraints(allConstraints theConstraints, int zoom)
    {
			drawConstraints(theConstraints,zoom,true);
    }
/*****************Draw Constraints**********************/
  public void drawConstraints(allConstraints theConstraints, int zoom, boolean shade)
    {
      Constraint tempCons = null;
      int i = 3;
      Graphics g = getGraphics();
      Enumeration e = theConstraints.vConstraints.elements();
      g.setColor(Color.blue);
      Polygon tempPolygon = null;
      Polygon tempPolygon2 = null;
      globalZoom = zoom;

      if(shade)
       {
      //for every constraint, draw a line and fill infeasible area!!
      //test 4 points (0,0),(400,400),(400,0) and (0,400)
      while(e.hasMoreElements())
	  {
		   tempPolygon = new Polygon();
		   tempPolygon2 = new Polygon();
		   tempCons = (Constraint)e.nextElement();
	   	   tempCons.withZoom(zoom);
		   if(!tempCons.feasible(((float)(0.0 - 200))/((float)globalZoom),(float)(200 - 0.0)/((float)globalZoom)))
		       {
			 tempPolygon.addPoint(0,0);  
			 tempPolygon2.addPoint(0,0);
			 //System.out.println("point top left is infeasible");
		       }
		   if(!tempCons.feasible(((float)(0.0 - 200))/((float)globalZoom),(float)(200 - 400.0)/((float)globalZoom)))
		       {
			 tempPolygon.addPoint(0,400);  
			  tempPolygon2.addPoint(0,400); 
			 //System.out.println("point bottom left is infeasible");
		       }
		   if(!tempCons.feasible(((float)(400.0 - 200))/((float)globalZoom),(float)(200 - 400.0)/((float)globalZoom)))
		       {
			 tempPolygon.addPoint(400,400);  
			 tempPolygon2.addPoint(400,400);
			 //System.out.println("point bottom right is infeasible");
		       }
		   if(!tempCons.feasible(((float)(400.0 - 200))/((float)globalZoom),(float)(200 - 0.0)/((float)globalZoom)))
		       {
			 tempPolygon.addPoint(400,0);
			 tempPolygon2.addPoint(400,0);
			 //System.out.println("point top right is infeasible");  
		       }
		   //in what order should we add these!!??
		   tempPolygon.addPoint(tempCons.real1.x,tempCons.real1.y);
		   tempPolygon.addPoint(tempCons.real2.x,tempCons.real2.y);
		   tempPolygon2.addPoint(tempCons.real2.x,tempCons.real2.y);
		   tempPolygon2.addPoint(tempCons.real1.x,tempCons.real1.y);
		   g.setColor(Color.white);
		   g.fillPolygon(tempPolygon);
		   g.fillPolygon(tempPolygon2);
		   tempPolygon = null;
		   tempPolygon2 = null;
	  }
       }
      g.setColor(Color.black);
      g.drawLine(200,0,200,400);
      g.drawLine(0,200,400,200);
      g.drawString("X1",385,210);
      g.drawString("X2",185,10);
      g.setColor(Color.blue);

      e = theConstraints.vConstraints.elements();
      while(e.hasMoreElements())
		{
		   		tempCons = (Constraint)e.nextElement();
	   	   	tempCons.withZoom(zoom);
		   		g.drawLine(tempCons.real1.x,tempCons.real1.y,tempCons.real2.x,tempCons.real2.y);
		   		g.setColor(Color.red);
		   		//System.out.println("X"+i+":"+tempCons.real1.x+","+tempCons.real1.y+","+tempCons.real2.x+","+tempCons.real2.y);
		   		if((tempCons.real2.x == 0) && (tempCons.real2.y >= 0) && (tempCons.real2.y <= 400))
		      			{
			 						g.drawString("X"+i,(0 + 5),(tempCons.real2.y + 10));
			 						//System.out.println("case1");
		       			}
		   		else if ((tempCons.real2.y == 0) && (tempCons.real2.x >= 0) && (tempCons.real2.x <= 400))
		      			{
			   					g.drawString("X"+i,(tempCons.real2.x),(0+10));
			   					//System.out.println("case2");
		       			}
		   		else if ((tempCons.real2.y == 400) && (tempCons.real2.x >= 0) && (tempCons.real2.x <= 400))
		      			{
			   					g.drawString("X"+i,(tempCons.real2.x),(400-10));
			   					//System.out.println("case3");
		       			}
		   		else if ((tempCons.real2.x == 400) && (tempCons.real2.y >= 0) && (tempCons.real2.y <= 400))
		       			{
			   					g.drawString("X"+i,(400 - 10),(tempCons.real2.y));
			   					//System.out.println("case4");
		       			}
		   		else if((tempCons.real1.x == 0) && (tempCons.real1.y >= 0) && (tempCons.real1.y <= 400))
		       			{
			 						g.drawString("X"+i,(0 + 5),(tempCons.real1.y));
			 						//System.out.println("case1a");
		       			}
		   		else if ((tempCons.real1.y == 0) && (tempCons.real1.x >= 0) && (tempCons.real1.x <= 400))
		      			{
			   					g.drawString("X"+i,(tempCons.real1.x),(0+5));
			   					//System.out.println("case2a");
		       			}
		   		else if ((tempCons.real1.y == 400) && (tempCons.real1.y >= 0) && (tempCons.real1.y <= 400))
		      			{
			   					g.drawString("X"+i,(tempCons.real1.x),(400-15));
			   					//System.out.println("case3a");
		       			}
		   		else if ((tempCons.real1.x == 400) && (tempCons.real1.y >= 0) && (tempCons.real1.y <= 400))
								{	
			   					g.drawString("X"+i,(400 - 25),(tempCons.real1.y));
			   					//System.out.println("case4a");
		       			}	       
		       
				   g.setColor(Color.blue);
				   tempCons.withZoom(1);
		  		 i++;
			}//end of while loop
      
    	globalZoom = zoom;
      currentCons = theConstraints;
    	currentPoint = null;
    	  LastPoint = currentPoint;
	  //Shade_ConvexHull();
	  //drawOptimal();

    }
/*****************DRAW Dictionary point**********************/
  public void drawDictionary(Dictionaries theDictionary,int zoom)
    {
	drawDictionary(theDictionary,zoom,false);
    }
/*****************DRAW Dictionary point**********************/
  public void drawDictionary(Dictionaries theDictionary,int zoom, boolean showObj)
    {
     float obj_x1,obj_x2,NormalSlope;
     Graphics g = getGraphics();
     if(currentPoint != null)
	 {
	     drawPoint(currentPoint,Color.white);
	 }
     currentPoint = new point(200+(int)(zoom*theDictionary.x1_dictvalue),200-(int)(zoom*theDictionary.x2_dictvalue));
     drawPoint(currentPoint,Color.red);
     if(LastPoint != null)
	 {
	     g.setColor(Color.red);
	     g.drawLine(currentPoint.x,currentPoint.y,LastPoint.x,LastPoint.y);
	     g.setColor(Color.blue);
	 }
     LastPoint = currentPoint;

     globalZoom = zoom;
     currentDict = theDictionary;
     if(currentCons != null)
	 {
	     drawOptimal();
	 }
     //System.out.println("Dict: "+currentDict.DictionaryString);
     //New stuff: draw optimal vector at current point...
     if(showObj)
	 {
	if(theDictionary.Obj.slope != 0.000)
	    {
		//System.out.println("Normal slope set!");
		NormalSlope = (float)((float)1.0/(float)theDictionary.Obj.slope);
	    }
	else if(theDictionary.Obj.slope == 99999)
	    {
		NormalSlope = (float)0.0;
	    }
	else //slope = 0
	    {
		NormalSlope = (float)99999.0;
	    }

	     obj_x1 = theDictionary.x1_dictvalue - (float) Math.sqrt(5.0/(1+NormalSlope*NormalSlope));
	     obj_x2 = NormalSlope*(theDictionary.x1_dictvalue-obj_x1)+theDictionary.x2_dictvalue;
	     if(theDictionary.Obj.evaluatePoint(obj_x1,obj_x2) < theDictionary.Obj.evaluatePoint(theDictionary.x1_dictvalue,theDictionary.x2_dictvalue)) //fix this!
		 {
		     obj_x1 = theDictionary.x1_dictvalue + (float) Math.sqrt(5.0/(1+NormalSlope*NormalSlope));
		     obj_x2 = NormalSlope*(theDictionary.x1_dictvalue-obj_x1)+theDictionary.x2_dictvalue;
		 }
	     
	     g.setColor(Color.black);
	     g.drawLine(currentPoint.x,currentPoint.y,(int)(200 + obj_x1*globalZoom),(int)(200-obj_x2*globalZoom));
	     g.setColor(Color.blue);
	 }
    }

/*****************DRAW A POINT**********************/
  public void drawPoint(point p, Color c)
  {
    Graphics g = getGraphics();
    g.setColor(c);
    g.fillOval(p.x - 3, p.y - 3, 6, 6);
    g.setColor(Color.black);
    g.drawOval(p.x - 3, p.y - 3, 6, 6);
  }

/******************************************************/
  public void reset()
  {
    Graphics g = getGraphics();
    Color beige = new Color(255,255,153);
    g.setColor(beige);
    g.fillRect(0,0,400,400);

    g.setColor(Color.black);
    g.drawLine(200,0,200,400);
    g.drawLine(0,200,400,200);
    g.drawString("X1",385,210);
    g.drawString("X2",185,10);
    currentPoint = null;
    LastPoint = null;
    globalZoom = 1;
    currentDict = null;
    currentCons = null;
  }
/******************************************************/
  public boolean handleEvent(Event evt)
  {
    if (evt.id == Event.MOUSE_DOWN)
    {
        point currentPoint = new point(evt.x,evt.y);
      	//System.out.println("point: "+evt.x+","+evt.y);
	currentPoint.xf = ((float)(currentPoint.x - 200))/((float)globalZoom);
	currentPoint.yf = ((float)(200 - currentPoint.y))/((float)globalZoom);
      	//System.out.println("point: "+currentPoint.x+","+currentPoint.y);
	if(currentCons == null)
	    {
		//MainThing.tf_feasible.setText("Feasibilty: no constraints entered!");
		return true;
	    }
	boolean feasible = currentCons.checkFeasibility(currentPoint.xf,currentPoint.yf);
	if(feasible)
	    {
		//System.out.println("Feasible");
		//MainThing.tf_feasible.setText("Feasibilty: your point is Feasible");
	    }
	else
	    {
		//System.out.println("INFeasible");
		//MainThing.tf_feasible.setText("Feasibilty: your point is INFeasible");			
	    }
	//now we test to see if we have hit an intersection point
	Enumeration e = currentCons.vIntersections.elements();
	Intersection tempIntersection = null;
	boolean goodPivot = false;
	if(currentDict == null)
	    {
		System.out.println("oh no!! Current Dictionary is null");
	    }
	if(!((currentDict.b_index1 == 1) && (currentDict.b_index2 == 2)))
	    {
	while(e.hasMoreElements() && !goodPivot)
	    {
		tempIntersection = (Intersection)e.nextElement();
		//System.out.println("Intersection point: "+tempIntersection.x1+","+tempIntersection.x2);
		//System.out.println("Current point: "+currentPoint.xf+","+currentPoint.yf);
		if((Math.abs(tempIntersection.x1 - currentPoint.xf) <= 0.2)
		    &&(Math.abs(tempIntersection.x2 - currentPoint.yf) <= 0.2))
		    {
			//we have hit an intersection point!!
			//System.out.println("Intersection point hit");
			//pivot to that dictionary if it is on the current line only!!
			//System.out.println("PrePivot:"+tempIntersection.basic1_index+","+tempIntersection.basic2_index
			//		 +","+currentDict.b_index1+","+currentDict.b_index2);
			if(tempIntersection.basic1_index == currentDict.b_index1 && tempIntersection.basic2_index != currentDict.b_index2)
			    {				 
				//System.out.println("Pivot1:"+tempIntersection.basic2_index+","+currentDict.b_index2);
			   MainThing.c_pEnter.select("x"+currentDict.b_index2);
				 goodPivot =currentDict.pivot("X"+currentDict.b_index2,"X"+tempIntersection.basic2_index);
					MainThing.c_pLeave.select("x"+tempIntersection.basic2_index);
			    }
			else if(tempIntersection.basic1_index == currentDict.b_index2 && tempIntersection.basic2_index != currentDict.b_index1)
			    {
			        //System.out.println("Pivot2:"+tempIntersection.basic2_index+","+currentDict.b_index1);
			    	MainThing.c_pEnter.select("x"+currentDict.b_index1);
			    	goodPivot =currentDict.pivot("X"+currentDict.b_index1,"X"+tempIntersection.basic2_index);
					  MainThing.c_pLeave.select("x"+tempIntersection.basic2_index);
			    }
			else if(tempIntersection.basic2_index == currentDict.b_index1 && tempIntersection.basic1_index != currentDict.b_index2)
			    {
				//System.out.println("Pivot3:"+tempIntersection.basic1_index+","+currentDict.b_index2); 
			 	MainThing.c_pEnter.select("x"+currentDict.b_index2);
			 	goodPivot =currentDict.pivot("X"+currentDict.b_index2,"X"+tempIntersection.basic1_index);
			 	MainThing.c_pLeave.select("x"+tempIntersection.basic1_index); 
			    }
			else if(tempIntersection.basic2_index == currentDict.b_index2 && tempIntersection.basic1_index != currentDict.b_index1)
			    {
				//System.out.println("Pivot4:"+tempIntersection.basic1_index+","+currentDict.b_index1); 
			  	MainThing.c_pEnter.select("x"+currentDict.b_index1);
			    goodPivot =currentDict.pivot("X"+currentDict.b_index1,"X"+tempIntersection.basic1_index);
			  	MainThing.c_pLeave.select("x"+tempIntersection.basic1_index);	 
			    }
			if(goodPivot)
				      {
					  //System.out.println("enter="+MainThing.c_pEnter.getSelectedItem()+" leave="+MainThing.c_pLeave.getSelectedItem());
					  MainThing.ta_dictionary.setText(currentDict.DictionaryString);
					  MainThing.lpB_Inverse = new B_Inverse(currentDict);
				   	if(MainThing.inverse)
	      		{
	 	 					MainThing.ta_constraints.setText(MainThing.lpB_Inverse.B_InverseString);
	      		}
					  String tempString = MainThing.c_pEnter.getSelectedItem();
					  MainThing.c_pEnter.remove(MainThing.c_pEnter.getSelectedIndex());
					  MainThing.c_pEnter.add(MainThing.c_pLeave.getSelectedItem());
					  MainThing.c_pLeave.remove(MainThing.c_pLeave.getSelectedIndex());
					  if((Integer.parseInt(tempString.substring(1)) != 1)
					     &&(Integer.parseInt(tempString.substring(1)) != 2))
					      {
						  MainThing.c_pLeave.add(tempString);
					      }
					  this.drawDictionary(currentDict,Integer.parseInt(MainThing.tf_zoom.getText()));
					  //MainThing.tf_info.setText("Pivot performed succesfully ");
					  if(MainThing.oneCone)
					      {
						  drawAxis();
						  drawConstraints(currentCons,globalZoom);
						  DrawCones(true,true,true);
					      }
				      }
			else
			    {
				//MainThing.tf_info.setText("Pivot cannot be performed");
			    }
		    }
	}//end while
	}//end if
      
    }
    return true;
  }
/******************************************************/
public void DrawCones(boolean drawObj,boolean drawAllCones)
{
    DrawCones(drawObj, drawAllCones, false);
}
/******************************************************/
public void DrawCones(boolean drawObj,boolean drawAllCones,boolean drawCurrentCone)
{
	Polygon tempPolygon = null;
	Enumeration e = null;
	if(drawAllCones)
	    {
		e = currentCons.vIntersections.elements();
	    }
	else
	    {
		e = currentCons.vFeasibleIntersections.elements();
	    }
	if(drawCurrentCone)
	    {
		if(currentPoint == null)
		    {
			currentPoint = new point(200+(int)(globalZoom*currentDict.x1_dictvalue),200-(int)(globalZoom*currentDict.x2_dictvalue));
			drawPoint(currentPoint,Color.red);
		    }
		currentPoint.xf = ((float)(currentPoint.x - 200))/((float)globalZoom);
		currentPoint.yf = ((float)(200 - currentPoint.y))/((float)globalZoom);
	    }
	Intersection tempIntersection = null;
	Graphics g = getGraphics();
	g.setColor(Color.yellow);
	while(e.hasMoreElements())
	{
	    if(drawCurrentCone)
		{
		    tempIntersection = (Intersection)e.nextElement();
		    if((Math.abs(tempIntersection.x1 - currentPoint.xf) <= 0.2)
		       &&(Math.abs(tempIntersection.x2 - currentPoint.yf) <= 0.2)
		       && (((tempIntersection.basic1_index == currentDict.b_index1)
			    &&(tempIntersection.basic2_index == currentDict.b_index2))
			   ||((tempIntersection.basic1_index == currentDict.b_index2)
			    &&(tempIntersection.basic2_index == currentDict.b_index1))
			   )
		       )
			{
			    tempPolygon =  new Polygon();
			    tempPolygon.addPoint((int)(globalZoom*tempIntersection.cone1_x1) +200,
						 200-(int)(globalZoom*tempIntersection.cone1_x2));
			    tempPolygon.addPoint((int)(globalZoom*tempIntersection.cone2_x1) +200,
						 200-(int)(globalZoom*tempIntersection.cone2_x2));
			    tempPolygon.addPoint((int)(globalZoom*tempIntersection.x1) +200,
						 200-(int)(globalZoom*tempIntersection.x2));
			    g.setColor(Color.yellow);
			    g.fillPolygon(tempPolygon);
			    g.setColor(Color.orange);	    
			    g.drawPolygon(tempPolygon);
			    tempPolygon = null;			    
			}
		}
	    else
		{
		    tempIntersection = (Intersection)e.nextElement();
		    tempPolygon =  new Polygon();
		    tempPolygon.addPoint((int)(globalZoom*tempIntersection.cone1_x1) +200,
					 200-(int)(globalZoom*tempIntersection.cone1_x2));
		    tempPolygon.addPoint((int)(globalZoom*tempIntersection.cone2_x1) +200,
					 200-(int)(globalZoom*tempIntersection.cone2_x2));
		    tempPolygon.addPoint((int)(globalZoom*tempIntersection.x1) +200,
					 200-(int)(globalZoom*tempIntersection.x2));
		    g.fillPolygon(tempPolygon);
		    tempPolygon = null;
		}
	}

	if(drawAllCones)
	    {
		e = currentCons.vIntersections.elements();
	    }
	else
	    {
		e = currentCons.vFeasibleIntersections.elements();
	    }
	while(e.hasMoreElements())
	{
	    tempIntersection = (Intersection)e.nextElement();
	    if(!drawCurrentCone)
		{
		    tempPolygon =  new Polygon();
		    tempPolygon.addPoint((int)(globalZoom*tempIntersection.cone1_x1) +200,
					 200-(int)(globalZoom*tempIntersection.cone1_x2));
		    tempPolygon.addPoint((int)(globalZoom*tempIntersection.cone2_x1) +200,
					 200-(int)(globalZoom*tempIntersection.cone2_x2));
		    tempPolygon.addPoint((int)(globalZoom*tempIntersection.x1) +200,
					 200-(int)(globalZoom*tempIntersection.x2));
		    
		    g.setColor(Color.orange);	    
		    g.drawPolygon(tempPolygon);
		    tempPolygon = null;
		}
	}

	drawConstraints(currentCons,globalZoom,false);
	drawDictionary(currentDict,globalZoom,drawObj);
	drawOptimal();

		
}

	/******************************************************/
public void drawOptimal()
    {
	Enumeration e = currentCons.vFeasibleIntersections.elements();
	Intersection tempIntersection = null;
	Intersection MaxIntersection = null;
	while(e.hasMoreElements())
	    {
		if(MaxIntersection == null)
		    {
			MaxIntersection = (Intersection)e.nextElement();
		    }
		else
		    {
			tempIntersection = (Intersection)e.nextElement();
			if(currentDict.Obj.evaluatePoint(tempIntersection.x1,tempIntersection.x2) 
			   > currentDict.Obj.evaluatePoint(MaxIntersection.x1,MaxIntersection.x2) )
			    {
				MaxIntersection = tempIntersection;
			    }
		    }
	    }
	//now we draw the optimal point to be orange
	if(MaxIntersection != null)
	    {
		point OptimalPoint = new point((int)(200 + MaxIntersection.x1*globalZoom),(int)(200-MaxIntersection.x2*globalZoom) );
		drawPoint(OptimalPoint, Color.orange);	       
	    }
    }

	
	/*###########################################################*/
	/*#####################Don't go past here!##################*/
  /*-***********************************************************
   *
   *  SUPPORT FUNCTIONS
   *
   */
  // returns true if p1 and p2 are "near" eachother
  boolean near(point p1, point p2)
  {
    return ((Math.abs(p1.x - p2.x) <= 3) &&
	    (Math.abs(p1.y - p2.y) <= 3));
  }


  /*
   * returns 1 if abc is a right turn
   *        -1 if abc is a left turn
   *         0 if a, b, and c are collinear
   */

  byte turn(point a, point b, point c)
  {	
    point[] T = { a , b , c };  // Triangle defined by points
    int AreaT = 0;  // Area of triangle
    
    AreaT += (T[0].x * T[1].y) - (T[0].y * T[1].x);
    AreaT += (T[1].x * T[2].y) - (T[1].y * T[2].x);
    AreaT += (T[2].x * T[0].y) - (T[2].y * T[0].x);

    if (AreaT > 0) return 1;  // right turn
    if (AreaT < 0) return -1; // left turn
    return 0;		      // collinear
  }

  // returns true if s1 and s2 intersect
  boolean intersect(segment s1, segment s2)
  {
    int t1h,t1t,t2h,t2t;

    if (!bbintersect(s1,s2)) // Quick rejection
    {
      return false;
    }
    else // Bounding boxes of edges intersect
    {
      if (!s1.equals(s2))
      {
        t1h = turn(s1.head(),s1.tail(),s2.head());
        t1t = turn(s1.head(),s1.tail(),s2.tail());
    
        if ((t1h == t1t)&&(t1h != 0)&&(t1t != 0)) { return false; }
      
        t2h = turn(s2.head(),s2.tail(),s1.head());
        t2t = turn(s2.head(),s2.tail(),s1.tail());

        if ((t2h == t2t)&&(t2h != 0)&&(t2t != 0)) { return false; }
      }
    }
    return true;
  }
 
  // Intersection of bounding boxes of two line segments
  boolean bbintersect(segment s1, segment s2)
  {
    int xmin1,xmax1,ymin1,ymax1;
    int xmin2,xmax2,ymin2,ymax2;

    // min and max x-coords of s1
    if (s1.head().x >= s1.tail().x)
      { xmin1 = s1.tail().x; xmax1 = s1.head().x;}
    else
      { xmin1 = s1.head().x; xmax1 = s1.tail().x;}

    // min and max y-coords of s1
    if (s1.head().y >= s1.tail().y)
      { ymin1 = s1.tail().y; ymax1 = s1.head().y;}
    else
      { ymin1 = s1.head().y; ymax1 = s1.tail().y;}

    // min and max x-coords of s2
    if (s2.head().x >= s2.tail().x)
      { xmin2 = s2.tail().x; xmax2 = s2.head().x;}
    else
      { xmin2 = s2.head().x; xmax2 = s2.tail().x;}

    // min and max y-coords of s2
    if (s2.head().y >= s2.tail().y)
      { ymin2 = s2.tail().y; ymax2 = s2.head().y;}
    else
      { ymin2 = s2.head().y; ymax2 = s2.tail().y;}

    return ((xmax1 >= xmin2) && (xmax2 >= xmin1) &&
	    (ymax1 >= ymin2) && (ymax2 >= ymin2));
  }
}
