import java.io.*;
import java.util.Vector;
import java.util.Enumeration;
public class B_Inverse
{
        public String B_InverseString = null;
	
        public B_Inverse(Dictionaries theDictionary)
        {
	    String rowString = null;
	    Dictionaries.Row tempRow = null;
	    Dictionaries.Objective tempObj = null;
	    Enumeration e = theDictionary.vRows.elements();
	    //System.out.println("B Inverse computation");

	    B_InverseString = "";
	    while(e.hasMoreElements())
		{
		    tempRow = (Dictionaries.Row) e.nextElement();
		    if(tempRow.slack_index == 1 || tempRow.slack_index ==2)
			{
			     //then do nothing!
			}
		    else
			{
			    rowString = " "+ tempRow.constant.string + "   ";
		    for(int i = 3; i <= theDictionary.num_of_rows+2; i++)
			{
			    if(tempRow.slack_index == 1 || tempRow.slack_index ==2)
				{
				    //then do nothing!
				}
			    else if(tempRow.slack_index == i)
				{
				   rowString = rowString + "1   ";
				}
			    else if (tempRow.b1_index == i)
				{
				   tempRow.basic1.num = tempRow.basic1.num *-1;
				   tempRow.basic1.simplify_cs();
				   rowString = rowString + tempRow.basic1.string+ "   ";
				   tempRow.basic1.num = tempRow.basic1.num *-1;
				   tempRow.basic1.simplify();
				}
			    else if (tempRow.b2_index == i)
				{
				   tempRow.basic2.num = tempRow.basic2.num *-1;
				   tempRow.basic2.simplify_cs();
				   rowString = rowString + tempRow.basic2.string + "   ";
				   tempRow.basic2.num = tempRow.basic2.num *-1;
				   tempRow.basic2.simplify();
				}
			    else
				{
				    rowString = rowString + "0   ";
				}
			}
		         B_InverseString = B_InverseString + rowString + "\n";
			}//end of else
		}
	    //get the objective function in here as well
	    tempObj = theDictionary.Obj;
	    rowString = " "+ tempObj.constant.string+"   ";
	    for(int i = 3; i <= theDictionary.num_of_rows+2; i++)
			{
			    if (tempObj.b1_index == i)
				{
				   tempObj.basic1.num = tempObj.basic1.num *-1;
				   tempObj.basic1.simplify_cs();
				   rowString = rowString + tempObj.basic1.string+ "   ";
				   tempObj.basic1.num = tempObj.basic1.num *-1;
				   tempObj.basic1.simplify();
				}
			    else if (tempRow.b2_index == i)
				{
				   tempObj.basic2.num = tempObj.basic2.num *-1;
				   tempObj.basic2.simplify_cs();
				   rowString = rowString + tempObj.basic2.string + "   ";
				   tempObj.basic2.num = tempObj.basic2.num *-1;
				   tempObj.basic2.simplify();
				}
			    else
				{
				    rowString = rowString + "0   ";
				}
			}
		    B_InverseString = B_InverseString + rowString + "\n";
		    //System.out.println(B_InverseString);
	}
}

