
import java.io.*;
import java.lang.Math;
public class Constraint
{
    public int x1_const;
    public int x2_const;
    public float slope;
    public float NormalSlope;
    public float intercept;
    private float tempx;
    private float tempy;
    public point real1 = null;
    public point real2 = null;
    public	int b;
    public String consString = null;
    float margin = (float)0.0005; //margin for letting something be feasible
     
	public Constraint(int c1, int c2, int cb)
    {
	int temp = 1;
	x1_const = c1;
	x2_const = c2;
	b = cb;
	// consString = "    "+x1_const+" x1  +  "+x2_const+" x2"+"  <=  "+b+"\n";
	consString = "    ";
	if(x1_const != 0)
	    {
		if (x1_const == -1)
		    consString = consString+"- x1";
		else if(x1_const != 1)
		    consString = consString+x1_const+" x1";
		else
		    consString = consString+" x1";
	    }
	if(x1_const != 0 && x2_const != 0)
	    {
		if(x2_const > 0)
		    consString = consString+"  +  ";
		else
		    {
			consString = consString+"  -  ";
			temp = -1;
		    }
	    }
	if(x2_const != 0)
	    {
		if (x2_const*temp == -1)
		    consString = consString+"- x2";
		else if(x2_const*temp != 1)
		    consString = consString+(x2_const*temp)+" x2";
		else
		    consString = consString+" x2";
	    }
	consString = consString + "  <=  "+b+"\n";
	if(x2_const == 0)
	    {//vertical line, note x1_const can't be zero in this case
	    real1 = new point(200 + (int)(((float)b)/((float)x1_const)),0);
		real2 = new point(200 + (int)(((float)b)/((float)x1_const)),400);
		slope = 99999;
	    }
	else if(x1_const == 0)
	    {//horizontal line, note x2_const can't be zero in this case
	    real1 = new point(0,200 - (int)(((float)b)/((float)x2_const)));
		real2 = new point(400,200 - (int)(((float)b)/((float)x2_const)));
		slope = 0;
	    }
	else
	    {
		//System.out.println("(x1,x2) = ("+x1_const+","+x2_const+")");
		slope = (float)(-1.0*((float)x1_const)/((float)x2_const));
		intercept = (float)(((float)b)/((float)x2_const));
		//System.out.println("Slope = "+slope);
		//System.out.println("Intercept = "+intercept);
		//what is x when y = 200?
		tempx = (float)((float)200.0/slope + ((float)(x2_const*intercept)/(float)x1_const));
		//what is y when x = 200
		tempy = (float)((float)200.0*slope + (float)intercept);
		if( tempx <= 200 && tempx >= -200)
		    {
			real1 = new point(200+(int)tempx,0);
				//System.out.println(":"+real1.x+","+real1.y);
				//System.out.println("case1");	
		    }
		else if(tempy <= 200 && tempy >= -200)
		    {
			real1 = new point(400,200-(int)tempy);
				//System.out.println("case2");
		    }
		else
		    {
			real1 = new point(400,200 - ((int)(intercept + (200.0*slope))));
				//System.out.println("case3");
		    }
		//what is x when y = -200
		tempx = (float)((float)-200.0/slope + ((float)(x2_const*intercept)/(float)x1_const));
		//what is y when x = -200
		tempy = (float)((float)-200.0*slope + (float)intercept);
		if( tempx <= 200 && tempx >= -200)
		    {
			real2 = new point(200+(int)tempx,400);
				//System.out.println("case4");
				//System.out.println(":"+real2.x+","+real2.y);
		    }
		else if(tempy <= 200 && tempy >= -200)
		    {
			real2 = new point(0,200-(int)tempy);	
				//System.out.println("case5");	
		    }
		else
		    {
			real2 = new point(0,200 - ((int)(intercept - (200.0*slope))));	
				//System.out.println("case6");
		    }		
	    }
	//else
	//    {//vertical line, note x1_const can't be zero in this case
	//	real1 = new point(200 + (int)(((float)b)/((float)x1_const)),0);
	//	real2 = new point(200 + (int)(((float)b)/((float)x1_const)),400);
	//	}
	if(slope != 0.000)
	    {
		NormalSlope = (float)((float)1.0/(float)slope);
	    }
	else if(slope == 99999)
	    {
		NormalSlope = (float)0.0;
	    }
	else //slope = 0
	    {
		NormalSlope = (float)99999.0;
	    }
	//System.out.println("Slope = "+slope);	
    }
    public void withZoom(int zoom)
    {
	
	if(x2_const != 0)
	    {
		//System.out.println("(x1,x2) = ("+x1_const+","+x2_const+")");
		slope = (float)(-1.0*((float)x1_const)/((float)x2_const));
		intercept = (float)(zoom*((float)b)/((float)x2_const));
		//System.out.println("Slope = "+slope);
		//System.out.println("Intercept = "+intercept);
		real1 = new point(400,200 - ((int)(intercept + (200.0*slope))));
		real2 = new point(0,200 - ((int)(intercept - (200.0*slope))));			
	    }
	else
	    {//vertical line, note x1_const can't be zero in this case
		real1 = new point(200 + (int)(zoom*(((float)b)/((float)x1_const))),0);
		real2 = new point(200 + (int)(zoom*(((float)b)/((float)x1_const))),400);
	    }

	/*
	  if(x2_const == 0)
	  {//vertical line, note x1_const can't be zero in this case
	  real1 = new point(200 + (int)(zoom*(((float)b)/((float)x1_const))),0);
	  real2 = new point(200 + (int)(zoom*(((float)b)/((float)x1_const))), 400);
	  }
	  else if(x1_const == 0)
	  {//horizontal line, note x2_const can't be zero in this case
	  real1 = new point(0,200 - (int)(zoom*(((float)b)/((float)x2_const))));
	  real2 = new point(400,200 - (int)(zoom*(((float)b)/((float)x2_const))));
	  }
	  else
	  {
	  //System.out.println("(x1,x2) = ("+x1_const+","+x2_const+")");
	  slope = (float)(-1.0*((float)x1_const)/((float)x2_const));
	  intercept = (float)(zoom*(((float)b)/((float)x2_const)));
	  //System.out.println("Slope = "+slope);
	  //System.out.println("Intercept = "+intercept);
	  //what is x when y = 200?
	  tempx = (float)((float)200.0/slope + ((float)(x2_const*intercept)/(float)x1_const));
	  //what is y when x = 200
	  tempy = (float)((float)200.0*slope + (float)intercept);
	  if( tempx <= 200 && tempx >= -200)
	  {
	  real1 = new point(200+(int)tempx,0);
	  System.out.println(":"+real1.x+","+real1.y);
	  
	  System.out.println("case1");	
	  }
	  else if(tempy <= 200 && tempy >= -200)
	  {
	  real1 = new point(400,200-(int)tempy);
	  System.out.println("case2");
	  }
	  else
	  {
	  real1 = new point(400,200 - ((int)(intercept + (200.0*slope))));
	  System.out.println("case3");
	  }
	  //what is x when y = -200
	  tempx = (float)((float)-200.0/slope + ((float)(x2_const*intercept)/(float)x1_const));
	  //what is y when x = -200
	  tempy = (float)((float)-200.0*slope + (float)intercept);
	  if( tempx <= 200 && tempx >= -200)
	  {
	  real2 = new point(200+(int)tempx,400);
	  System.out.println("case4");
	  System.out.println(":"+real2.x+","+real2.y);
	  }
	  else if(tempy <= 200 && tempy >= -200)
	  {
	  real2 = new point(0,200-(int)tempy);	
	  System.out.println("case5");	
	  }
	  else
	  {
	  real2 = new point(0,200 - ((int)(intercept - (200.0*slope))));	
	  System.out.println("case6");
	  }		
	  }
	*/
    }
    public boolean feasible(float x1, float x2)
    {
	return(((float)(x1_const*x1) + (float)(x2_const*x2)) <= ((float)b + margin));
    }
	
}
