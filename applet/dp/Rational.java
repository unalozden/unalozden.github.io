import java.lang.Math;

public class Rational
{
	int num;
	int den;
	int gcd;
	String string;
	
	public Rational(int n, int d)
	{
		num = n;
		den = d;
			
		simplify();
	}
	
	public int greatestCD(int n, int d)
	{
		int rem;
		int quot;
		
		rem=(Math.abs(n)%Math.abs(d));
		quot=(Math.abs(n)/Math.abs(d));
		if(rem == 0)
		   {
		   		return(d);
		   }
		else
			{
			 	 return(greatestCD(d,rem));	
			}		
	}
	
	public void simplify()
	{
		int temp=1;
		gcd = greatestCD(num,den);
		if(gcd != 1)
		{
			num = num/gcd;
			den = den/gcd;
			gcd = 1;
		}
		if((num < 0)&&(den < 0))
		    {
			num = -1*num;
			den = -1*den;
		    }
		if((den < 0)&&(num > 0))
		    {
			num = -1*num;
			den = -1*den;
		    }
		//fix the string now
		if(num < 0)
			{
				string = "  -  ";
				temp=-1;
			}
		else
			{
				string = "  +  ";
				temp=1;
			}
		
		if(num == 0)
			{
				string = string+"0";	
			}
		else if(den == 1)
		{
			if((num*temp) == 1)
				string = string+"";  /*Don't write 1 x1 */
			else
				string = string+(num*temp);
		}
		else
		{
			string = string+"("+(num*temp)+"/"+den+")";
		}
	}

	public void simplify_b()
	{
		int temp=1;
		gcd = greatestCD(num,den);
		if(gcd != 1)
		{
			num = num/gcd;
			den = den/gcd;
			gcd = 1;
		}
		if((num < 0)&&(den < 0))
		    {
			num = -1*num;
			den = -1*den;
		    }
		if((den < 0)&&(num > 0))
		    {
			num = -1*num;
			den = -1*den;
		    }
		//fix the string now
		if(num < 0)
			{
				string = "  -  ";
				temp=-1;
			}
		else
			{
				string = "  +  ";
				temp=1;
			}
		
		if(num == 0)
			{
				string = string+"0";	
			}
		else if(den == 1)
		{
		       	string = string+(num*temp);
		}
		else
		{
			string = string+"("+(num*temp)+"/"+den+")";
		}
	}
	public void simplify_cs()
	{
		int temp=1;
		gcd = greatestCD(num,den);
		if(gcd != 1)
		{
			num = num/gcd;
			den = den/gcd;
			gcd = 1;
		}
		if((num < 0)&&(den < 0))
		    {
			num = -1*num;
			den = -1*den;
		    }
		if((den < 0)&&(num > 0))
		    {
			num = -1*num;
			den = -1*den;
		    }
		//fix the string now
		if(num < 0)
			{
				string = "-";
				temp=-1;
			}
		else
			{
				string = "";
				temp=1;
			}
		
		if(num == 0)
			{
				string = string+"0";	
			}
		else if(den == 1)
		{
		       	string = string+(num*temp);
		}
		else
		{
			string = string+"("+(num*temp)+"/"+den+")";
		}
	}

	
}
