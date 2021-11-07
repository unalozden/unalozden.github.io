import java.awt.Color;
class point
{
  public int x;
  public int y;
	public float xf;
  public float yf;

	public int index;
	public Color coin;  //blue if no coin

  
  public boolean removed = false;

  public point(int xc, int yc) 
    {x = xc; y = yc; coin = Color.blue;}
	public point(int xc, int yc, int ind)
		{x = xc; y = yc; coin = Color.blue; index = ind;}
  public point(point p) 
    { x = p.x; y = p.y; p.coin = Color.blue;}

  public boolean equals(point p)
  {
    return((x == p.x) && (y == p.y));
  }

}