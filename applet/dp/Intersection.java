
import java.io.*;
import java.lang.Math;

public class Intersection
{
    public int basic1_index;
    public int basic2_index;
    public float x1;
    public float x2;
    boolean feasible;
    public float cone1_x1;
    public float cone1_x2;
    public float cone2_x1;
    public float cone2_x2;
    public float Length;

    
    public Intersection(float inter1, float inter2,
			int c1, int c2,
			Constraint Cons1, Constraint Cons2)
    {
	x1 = inter1;
	x2 = inter2;
	basic1_index = c1;
	basic2_index = c2;
	feasible = false;
	Length = 4;  //really square of length
	
	//Compute the cone
	//System.out.println("Slope of normal1 "+Cons1.NormalSlope);
	//System.out.println("Slope of normal2 "+Cons2.NormalSlope);
	cone1_x1 = x1 - (float) Math.sqrt(Length/(1+Cons1.NormalSlope*Cons1.NormalSlope));
	cone2_x1 = x1 - (float) Math.sqrt(Length/(1+Cons2.NormalSlope*Cons2.NormalSlope));
	
	cone1_x2 = Cons1.NormalSlope*(x1-cone1_x1)+x2;
	cone2_x2 = Cons2.NormalSlope*(x1-cone2_x1)+x2;
	
	if(Cons1.feasible(cone1_x1,cone1_x2))
	    {
		cone1_x1 = x1 + (float) Math.sqrt(Length/(1+Cons1.NormalSlope*Cons1.NormalSlope));
		cone1_x2 = Cons1.NormalSlope*(x1-cone1_x1)+x2;
	    }
	if(Cons2.feasible(cone2_x1,cone2_x2))
	    {
		cone2_x1 = x1 + (float) Math.sqrt(Length/(1+Cons2.NormalSlope*Cons2.NormalSlope));	
		cone2_x2 = Cons2.NormalSlope*(x1-cone2_x1)+x2;
	    }
	
    }
}
