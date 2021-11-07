import java.io.*;
import java.util.Vector;
import java.util.Enumeration;
public class allConstraints
{
	Vector vConstraints = new Vector();
  Vector vIntersections = new Vector();
	Vector vFeasibleIntersections = new Vector();
    public int num_of_intersections;
	public int num_of_constraints;
	public String allConstraintsString = null;
	public int x1_obj;
	public int x2_obj;
        public boolean feasibility = true;
	
	public allConstraints()
	{
		num_of_constraints = 0;
		num_of_intersections = 0;
		x1_obj = 0;
		x2_obj = 0;
	}
	
 public boolean checkFeasibility(float x1, float x2)
        {
	    if(num_of_constraints == 0)
		{
		    feasibility = true;
		    return true;
		}
	    Enumeration e = vConstraints.elements();
	    Constraint tempCons = null;
	    while(e.hasMoreElements())
		{
		    tempCons = (Constraint)e.nextElement();
		    if(!tempCons.feasible(x1,x2))
			{
			    feasibility = false;
			    return false;
			}
		}
	    feasibility = true;
	    return true;
  
	}
	 public boolean checkFeasibility(int x1, int x2)
	{
		return checkFeasibility((float)x1, (float)x2);
	}
	
	public void addConstraint(String c1,String c2, String cb)
	{
		int const1 = Integer.parseInt(c1);
	 	int const2 = Integer.parseInt(c2);
		int b = Integer.parseInt(cb);
		if((const1 == 0)&&(const2 == 0))
		{
			return;
		}
		else
		{
			Constraint newCons = new Constraint(const1,const2,b);
			//System.out.println("added constraint");
			computeIntersections(newCons);
			vConstraints.addElement(newCons);
			computeFeasibleIntersections();
			updateString();
			num_of_constraints++;
		}
	}
	
	public void removeConstraint(int remCons)
	{
		if(remCons == 0)
				{
					return;
				}
		else
		{
			//System.out.print("\n# of vector elements = "+vConstraints.size());
			vConstraints.removeElementAt(remCons-1);	
			updateString();
			num_of_constraints--;
			//re-compute the intersection points!
			Enumeration e = vConstraints.elements();
		        Constraint tempCons = null;
		        vIntersections.removeAllElements();
			num_of_constraints = 0; //i added this!
		        while(e.hasMoreElements())
		         {
			   tempCons = (Constraint)e.nextElement();
			   computeIntersections(tempCons);
			     num_of_constraints++; //i added this
			 }
			computeFeasibleIntersections();
		}
	}
	
	public void updateString()
	{
		Enumeration e = vConstraints.elements();
		Constraint temp = null;
		allConstraintsString = "Maximize   "+x1_obj+" x1 + "+x2_obj+" x2 \nsubject to:\n";
		for(int i=1; i<= vConstraints.size(); i++)
		{
			temp = (Constraint)e.nextElement();
			allConstraintsString = allConstraintsString + "("+i+")"+temp.consString;
		}
	}

  public void computeIntersections(Constraint newConstraint)
    {
	   float x1,x2,const1,constx,const2,const3;
	   Enumeration e = vConstraints.elements();
	   Constraint tempCons = null;
	   for(int i=0; i< vConstraints.size();i++)
	       {
		   if(i >= num_of_constraints) //i added this too
		       {
			   return;
		       }
		   tempCons = (Constraint)e.nextElement();
		   if(tempCons == newConstraint)
		       {
			   //System.out.println("Cool - can check this!");
			   //we don't count this as an intersection point!
		       }
		   //attempt to solve for x2
		   else if(newConstraint.x1_const == 0)
		       {
			 if(tempCons.x1_const != 0)
			     {    
				 x2 = ((float)newConstraint.b)/((float)newConstraint.x2_const);
				 x1 = (((float)tempCons.b)-(((float)tempCons.x2_const)*(x2)))/((float)tempCons.x1_const);
				 //System.out.println("got here1");
				 Intersection newIntersection = new Intersection(x1,x2,i+3,num_of_constraints+3,tempCons, newConstraint);
				 vIntersections.addElement(newIntersection);
				 num_of_intersections++;
				 //System.out.println("1Intersection point from ("+(i+3)+")and("+(num_of_constraints+3)+"): ("+x1+","+x2+")");

			     }
		       }
		   else if(newConstraint.x2_const == 0)
		       {
			 if(tempCons.x2_const != 0)
			     {
				 x1 = ((float)newConstraint.b)/((float)newConstraint.x1_const);
				 x2 = (((float)tempCons.b)-(((float)tempCons.x1_const)*(x1)))/((float)tempCons.x2_const);
				 //System.out.println("got here2");
				 Intersection newIntersection = new Intersection(x1,x2,i+3,num_of_constraints+3,tempCons, newConstraint);
				 vIntersections.addElement(newIntersection);
				 num_of_intersections++;
				 //System.out.println("2Intersection point from ("+(i+3)+")and("+(num_of_constraints+3)+"): ("+x1+","+x2+")");
			     }
		       }
		   else if(tempCons.x1_const == 0)
		       {
			 x2 = ((float)tempCons.b)/((float)tempCons.x2_const);
			 x1 = (((float)newConstraint.b)-(((float)newConstraint.x2_const)*(x2)))/((float)newConstraint.x1_const);			 
			     
			 //System.out.println("got here3");
			 Intersection newIntersection = new Intersection(x1,x2,i+3,num_of_constraints+3,tempCons, newConstraint);
			 vIntersections.addElement(newIntersection);
			 num_of_intersections++;
			 //System.out.println("3Intersection point from ("+(i+3)+")and("+(num_of_constraints+3)+"): ("+x1+","+x2+")");
		       }
		   else if(tempCons.x2_const == 0)
		       {
			 x1 = ((float)tempCons.b)/((float)tempCons.x1_const);
			 x2 = (((float)newConstraint.b)-(((float)newConstraint.x1_const)*(x1)))/((float)newConstraint.x2_const);			 
			 //System.out.println("got here4");	
			 Intersection newIntersection = new Intersection(x1,x2,i+3,num_of_constraints+3,tempCons, newConstraint);
			 vIntersections.addElement(newIntersection);
			 num_of_intersections++;
			 //System.out.println("4Intersection point from ("+(i+3)+")and("+(num_of_constraints+3)+"): ("+x1+","+x2+")");
	
		       }
		  else
		      {
			  //damn... not a trivial case...
			  //solve for x2 = const1 + constx*x1
			  const1 = ((float)newConstraint.b)/((float)newConstraint.x2_const);
			  constx = -1*((float)newConstraint.x1_const)/((float)newConstraint.x2_const);
			  //substitute and solve for x1
			  // (x1_const + x2_const*constx)*x1 = b - x2_const*const1
			  const2 = ((float)tempCons.x1_const)+(((float)tempCons.x2_const)*constx);
			  if(const2 != 0)
			      {
				  //lines aren't parallel....
				  const3 = (((float)tempCons.b)-((float)tempCons.x2_const)*const1);
				  //System.out.println("ohoh: "+const3+","+const2+","+const1+","+constx);
				  x1 = const3/const2;
				  x2 = const1 + constx*x1;			 
				  //System.out.println("got here5");
				  Intersection newIntersection = new Intersection(x1,x2,i+3,num_of_constraints+3,tempCons, newConstraint);
				  vIntersections.addElement(newIntersection);
				  num_of_intersections++;
				  //System.out.println("5Intersection point from ("+(i+3)+")and("+(num_of_constraints+3)+"): ("+x1+","+x2+")");
			      }
		      }
	       }
       }
    
	public void computeFeasibleIntersections()
	{
		Enumeration e = vIntersections.elements();
		Intersection tempIntersection = null;
		vFeasibleIntersections.removeAllElements();
		//System.out.println("convex hull:");
		while(e.hasMoreElements())
		{
			tempIntersection = (Intersection)e.nextElement();
			//System.out.println("Check: ("+tempIntersection.x1+","+tempIntersection.x2+")");

			if(checkFeasibility(tempIntersection.x1, tempIntersection.x2))
				{
					vFeasibleIntersections.addElement(tempIntersection);
					//System.out.println("("+tempIntersection.x1+","+tempIntersection.x2+")");
				}
		}
	}
	

}
