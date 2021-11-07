import java.io.*;
import java.util.Vector;
import java.util.Enumeration;
public class Dictionaries
{
	public Vector vRows = new Vector();
	public Objective Obj;
	public int num_of_rows;
	private int slack_num;
	public int b_index1;
	public int b_index2;
        public float x1_dictvalue;
        public float x2_dictvalue;
	public String DictionaryString = null;
	
	public Dictionaries(allConstraints theConstraints)
	{
		num_of_rows = theConstraints.num_of_constraints;
		slack_num = 3;
		Constraint tempCons = null;
		DictionaryString = "";
		vRows.removeAllElements();
		Obj = new Objective(theConstraints.x1_obj,theConstraints.x2_obj);
		
		Enumeration e = theConstraints.vConstraints.elements();
		//for every constraint, create a dictionary row
		
		while(e.hasMoreElements())
		{
			tempCons = (Constraint)e.nextElement();
			Row newRow = new Row(tempCons.x1_const,tempCons.x2_const,tempCons.b,slack_num);
			vRows.addElement(newRow);
			updateString();
			slack_num++;
		}
		x1_dictvalue = 0;
		x2_dictvalue = 0;
		b_index1 = 1;
		b_index2 = 2;
	}
	
	public void updateString()
	{
		Enumeration e = vRows.elements();
		Row temp = null;
		DictionaryString = "";
		String tempD2 = "";
		String tempRest = "";
		while(e.hasMoreElements())
		{
			temp = (Row)e.nextElement();
			if(temp.slack_index == 1)
				{
					DictionaryString = DictionaryString + temp.rowString;
				}
			else if(temp.slack_index == 2)
				{
					tempD2 = temp.rowString + "- - - - - - - - - - - - - - -\n";
				}
			else
				{
					tempRest = tempRest + temp.rowString;				
				}
		}
		DictionaryString = DictionaryString + tempD2 + tempRest + Obj.objString;
		//DictionaryString = DictionaryString + Obj.objString;
	
	}

	public boolean pivot(String Enter, String Leave)
	{
	    
	    if(Enter.startsWith("Enter")||Leave.startsWith("Leave"))
		{
		    return false;  //not a valid pivot
		    }
	  
		int EnterVar = Integer.parseInt(Enter.substring(1));
		int LeaveVar = Integer.parseInt(Leave.substring(1));	
		if((LeaveVar == 1)||(LeaveVar == 2))
		{
				//we don't let x1 or x2 leave the basis
				return false;
			}
		/*if(LeaveVar == 1)
		    {
				x1_dictvalue = 0;
		    }
		else if(LeaveVar == 2)
		    {
				x2_dictvalue = 0;
		    }
		*/
		Enumeration e = vRows.elements();
		Row pivotRow = null;
		Row tempRow = null;
		boolean done = false;
		int skipRow = 0;
		int i = 0;
		//find row with leaving variable
		while(!done)
		{ 
			skipRow++;
			pivotRow = (Row)e.nextElement();
			if(pivotRow.slack_index == LeaveVar)
				{
					done = true;
				}
		}
		done = pivotRow.pivot(EnterVar);
		if(done == false)
		    {
			return false;
		    }
		e = vRows.elements();
		while(e.hasMoreElements())
		    {
			i++;
			tempRow = (Row)e.nextElement();
			if(i != skipRow)
			    {
				tempRow.substitution(pivotRow);
			    }
		    }
		Obj.substitution(pivotRow);
		b_index1 = pivotRow.b1_index; 
		b_index2 = pivotRow.b2_index;
		updateString();
		return true;
	}

	/*--------------------------------------------------------------------*/
class Row {
	public int x1_const;
	public int x2_const;
	public int slack_index;
	public int slack_constant;
	public Rational basic1, basic2, constant;
	public int b1_index, b2_index;
	public	int b;
	public String rowString = null;
	
	public Row(int c1, int c2, int cb, int row_num)
	{
		x1_const = c1;
		x2_const = c2;
		int x1_const_temp = 0 - x1_const;
		int x2_const_temp = 0 - x2_const;
		b = cb;
		slack_index = row_num;
		slack_constant = 1;
		rowString = "x"+slack_index+"  = "+b;
		if(x1_const_temp >= 0)
			{
				rowString = rowString + "  +  ";
			}
		else
			{
				rowString = rowString + "  -  ";
				x1_const_temp = 0-x1_const_temp;
			}
			
		if(x1_const_temp == 1) 
			{
				rowString = rowString + " x1";
			}
		else
			{
				rowString = rowString + x1_const_temp+ " x1";	
			}
		if(x2_const_temp >= 0)
			{
				rowString = rowString + "  +  ";
			}
		else
			{
				rowString = rowString + "  -  ";
				x2_const_temp = 0-x2_const_temp;
			}
		
		if(x2_const_temp == 1)
			{
				rowString = rowString + " x2\n";
			}
		else
			{
				rowString = rowString +x2_const_temp+" x2\n";
			}

		//rowString = "x"+slack_index+"  = "+b+"  +  "+(0-x1_const)+" x1  +  "+(0-x2_const)+" x2\n";

		b1_index = 1;
		b2_index = 2;
		basic1 = new Rational((0-x1_const),1);
		basic2 = new Rational((0-x2_const),1);
		constant = new Rational(b,1);
	}
	

    
  public void substitution(Row substRow)    
         {
	     Rational substRowb1 = new Rational(substRow.basic1.num,substRow.basic1.den);
	     Rational substRowb2 = new Rational(substRow.basic2.num,substRow.basic2.den);
	     Rational substRowc = new Rational(substRow.constant.num,substRow.constant.den);

	     if(b1_index == substRow.slack_index)
		 {
		     substRowb1.num = substRowb1.num*basic1.num;
		     substRowb1.den = substRowb1.den*basic1.den;
		     substRowb1.simplify();
		     substRowb2.num = substRowb2.num*basic1.num;
		     substRowb2.den = substRowb2.den*basic1.den;
		     substRowb2.simplify();
		     substRowc.num = substRowc.num*basic1.num;
		     substRowc.den = substRowc.den*basic1.den;
		     substRowc.simplify();
		     //fix constant
		     constant.num = ((constant.num*substRowc.den)+(constant.den*substRowc.num));
		     constant.den = (constant.den*substRowc.den);
		     constant.simplify_b();
		     //fix constants of basic variables in this row!
		     if(b2_index == substRow.b1_index)
			 {
			     basic2.num = ((basic2.num*substRowb1.den)+(basic2.den*substRowb1.num));
			     basic2.den = (basic2.den*substRowb1.den);
			     basic2.simplify();
			     basic1.num = substRowb2.num;
			     basic1.den = substRowb2.den;
			     basic1.simplify();
			     b1_index = substRow.b2_index;
			 }
		     else
			 {
			     basic2.num = ((basic2.num*substRowb2.den)+(basic2.den*substRowb2.num));
			     basic2.den = (basic2.den*substRowb2.den);
			     basic2.simplify();
			     basic1.num = substRowb1.num;
			     basic1.den = substRowb1.den;
			     basic1.simplify();
			     b1_index = substRow.b1_index;
			 }
		     
		 }
	     else
		 {
		     substRowb1.num = substRowb1.num*basic2.num;
		     substRowb1.den = substRowb1.den*basic2.den;
		     substRowb1.simplify();
		     substRowb2.num = substRowb2.num*basic2.num;
		     substRowb2.den = substRowb2.den*basic2.den;
		     substRowb2.simplify();
		     substRowc.num = substRowc.num*basic2.num;
		     substRowc.den = substRowc.den*basic2.den;
		     substRowc.simplify();
		     //fix constant
		     constant.num = ((constant.num*substRowc.den)+(constant.den*substRowc.num));
		     constant.den = (constant.den*substRowc.den);
		     constant.simplify_b();
		     //fix constants of basic variables in this row!
		     if(b1_index == substRow.b1_index)
			 {
			     basic1.num = ((basic1.num*substRowb1.den)+(basic1.den*substRowb1.num));
			     basic1.den = (basic1.den*substRowb1.den);
			     basic1.simplify();
			     basic2.num = substRowb2.num;
			     basic2.den = substRowb2.den;
			     basic2.simplify();
			     b2_index = substRow.b2_index;
			 }
		     else
			 {
			     basic1.num = ((basic1.num*substRowb2.den)+(basic1.den*substRowb2.num));
			     basic1.den = (basic1.den*substRowb2.den);
			     basic1.simplify();
			     basic2.num = substRowb1.num;
			     basic2.den = substRowb1.den;
			     basic2.simplify();
			     b2_index = substRow.b1_index;
			 }
		     
		 }
	     	if(b2_index > b1_index)
		{
			rowString = "x"+slack_index+"  = "+constant.string
				       +basic1.string+" x"+b1_index
				       +basic2.string+" x"+b2_index+"\n";		
		}
		else
		{
			rowString = "x"+slack_index+"  = "+constant.string
				       +basic2.string+" x"+b2_index
				       +basic1.string+" x"+b1_index+"\n";	
		}
        
   if(slack_index == 1)
		    {
				x1_dictvalue = ((float) constant.num)/((float)(constant.den));
		    	//System.out.println("x1 ="+x1_dictvalue);
		    }
		else if(slack_index == 2)
		    {
				x2_dictvalue = ((float)constant.num)/((float)constant.den);
		    	//System.out.println("x2 ="+x2_dictvalue);
		    }
	     

         }



	public boolean pivot(int leaving)
	{
		int temp;
		if(leaving == b1_index)
		{
			temp = basic1.num;
			if(temp == 0)
			    {//bad pivot
				return false;
			    }
			basic1.num = basic1.den;
			basic1.den = temp;
			basic1.simplify();
			basic2.num = -1*basic2.num*basic1.num;
			basic2.den = basic2.den*basic1.den;
			basic2.simplify();
			constant.num = -1*constant.num*basic1.num;
			constant.den = constant.den*basic1.den;
			constant.simplify_b();
			temp = b1_index;
			b1_index = slack_index;
			slack_index = temp;
		}
		else if(leaving == b2_index)
		{
			temp = basic2.num;
			if(temp == 0)
			    {//bad pivot
				return false;
			    }
			basic2.num = basic2.den;
			basic2.den = temp;
			basic2.simplify();
			basic1.num = -1*basic1.num*basic2.num;
			basic1.den = basic1.den*basic2.den;
			basic1.simplify();
			constant.num = -1*constant.num*basic2.num;
			constant.den = constant.den*basic2.den;
			constant.simplify_b();
			temp = b2_index;
			b2_index = slack_index;
			slack_index = temp;
		}
		
		if(b2_index > b1_index)
		{
			rowString = "x"+slack_index+"  = "+constant.string
				       +basic1.string+" x"+b1_index
				       +basic2.string+" x"+b2_index+"\n";		
		}
		else
		{
			rowString = "x"+slack_index+"  = "+constant.string
				       +basic2.string+" x"+b2_index
				       +basic1.string+" x"+b1_index+"\n";	
		}
		if(slack_index == 1)
		    {
				x1_dictvalue = ((float) constant.num)/((float)(constant.den));
		    	//System.out.println("x1 ="+x1_dictvalue);
		    }
		else if(slack_index == 2)
		    {
				x2_dictvalue = ((float)constant.num)/((float)constant.den);
		    	//System.out.println("x2 ="+x2_dictvalue);
		    }
		return true;
	}

}
	
class Objective   {
	public int x1_const;
	public int x2_const;
	public int b_index1;
	public int b_index2;
	public float slope;
	
	public Rational basic1, basic2, constant;
	public int b1_index, b2_index;
	
	public String objString = null;
	
	public Objective(int c1, int c2)
	{
	    
	    x1_const = c1;
	    x2_const = c2;
	    b_index1 = 1;
	    b_index2 = 2;
	    objString = "--------------------------\n z   = 0  +  "+x1_const+" x1  +  "+x2_const+" x2";
	    basic1 = new Rational(x1_const,1);
	    basic2 = new Rational(x2_const,1);
	    constant = new Rational(0,1);
	    if(x1_const == 0)
		{
		    slope = 0;
		}
	    else if(x2_const == 0)
		{
		    slope = 99999;
		}
	    else
		{
		    slope = (float)(-1.0*((float)x1_const)/((float)x2_const));
		}
	}
	public float evaluatePoint(float x1, float x2)
	{
	    return(x1_const*x1 + x2_const*x2);
	}
	
        public void substitution(Row substRow)    
	{
	    Rational substRowb1 = new Rational(substRow.basic1.num,substRow.basic1.den);
	    Rational substRowb2 = new Rational(substRow.basic2.num,substRow.basic2.den);
	    Rational substRowc = new Rational(substRow.constant.num,substRow.constant.den);
	    
	    if(b_index1 == substRow.slack_index)
		{
		    substRowb1.num = substRowb1.num*basic1.num;
		    substRowb1.den = substRowb1.den*basic1.den;
		    substRowb1.simplify();
		    substRowb2.num = substRowb2.num*basic1.num;
		    substRowb2.den = substRowb2.den*basic1.den;
		    substRowb2.simplify();
		    substRowc.num = substRowc.num*basic1.num;
		    substRowc.den = substRowc.den*basic1.den;
		    substRowc.simplify();
		    //fix constant
		    constant.num = ((constant.num*substRowc.den)+(constant.den*substRowc.num));
		    constant.den = (constant.den*substRowc.den);
		    constant.simplify_b();
		    //fix constants of basic variables in this row!
		    if(b_index2 == substRow.b1_index)
			{
			    basic2.num = ((basic2.num*substRowb1.den)+(basic2.den*substRowb1.num));
			    basic2.den = (basic2.den*substRowb1.den);
			    basic2.simplify();
			    basic1.num = substRowb2.num;
			    basic1.den = substRowb2.den;
			    basic1.simplify();
			    b_index1 = substRow.b2_index;
			}
		    else
			{
			    basic2.num = ((basic2.num*substRowb2.den)+(basic2.den*substRowb2.num));
			    basic2.den = (basic2.den*substRowb2.den);
			    basic2.simplify();
			    basic1.num = substRowb1.num;
			    basic1.den = substRowb1.den;
			    basic1.simplify();
			    b_index1 = substRow.b1_index;
			}
		    
		}
	    else
		{
		    substRowb1.num = substRowb1.num*basic2.num;
		    substRowb1.den = substRowb1.den*basic2.den;
		    substRowb1.simplify();
		    substRowb2.num = substRowb2.num*basic2.num;
		    substRowb2.den = substRowb2.den*basic2.den;
		    substRowb2.simplify();
		    substRowc.num = substRowc.num*basic2.num;
		    substRowc.den = substRowc.den*basic2.den;
		    substRowc.simplify();
		    //fix constant
		    constant.num = ((constant.num*substRowc.den)+(constant.den*substRowc.num));
		    constant.den = (constant.den*substRowc.den);
		    constant.simplify_b();
		    //fix constants of basic variables in this row!
		    if(b_index1 == substRow.b1_index)
			 {
			     basic1.num = ((basic1.num*substRowb1.den)+(basic1.den*substRowb1.num));
			     basic1.den = (basic1.den*substRowb1.den);
			     basic1.simplify();
			     basic2.num = substRowb2.num;
			     basic2.den = substRowb2.den;
			     basic2.simplify();
			     b_index2 = substRow.b2_index;
			 }
		    else
			{
			    basic1.num = ((basic1.num*substRowb2.den)+(basic1.den*substRowb2.num));
			    basic1.den = (basic1.den*substRowb2.den);
			    basic1.simplify();
			    basic2.num = substRowb1.num;
			    basic2.den = substRowb1.den;
			    basic2.simplify();
			    b_index2 = substRow.b1_index;
			}
		    
		}
	    if(b_index2 > b_index1)
		{
		    objString = "--------------------------\n z   = "+constant.string
			+basic1.string+" x"+b_index1
			+basic2.string+" x"+b_index2+"\n";		
		}
	    else
		{
		    objString = "--------------------------\n z   = "+constant.string
			+basic2.string+" x"+b_index2
			+basic1.string+" x"+b_index1+"\n";	
		}
	    
	    
	}
	
	}
    

}