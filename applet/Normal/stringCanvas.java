import java.awt.*;
import java.awt.event.*;

public class stringCanvas extends Canvas {
	
	private String stringShow1 = new String();
	private String stringShow2 = new String();
	private Color bgColor = Color.white;
	private Color fgColor = Color.black;
	private Dimension size;
	
	public stringCanvas(int width, int height, Color bgColor, Color fgColor) {
		
		this("", "", width, height, bgColor, fgColor);
	}
	
	public stringCanvas(String s1, String s2, int width, int height, Color bgColor, Color fgColor) {
		
		this.stringShow1 = s1;
		this.stringShow2 = s2;
		this.bgColor = bgColor;
		this.fgColor = fgColor;
		this.setSize(width, height);
		size = new Dimension(width, height);
	}
	
	public void setText(String s1, String s2) {
		
		this.stringShow1 = s1;
		this.stringShow2 = s2;
		Graphics g = this.getGraphics();
		g.setColor(bgColor); g.fillRect(0, 0, size.width, size.height);
		g.setFont(new Font ("Dialog", Font.PLAIN, 9));
		FontMetrics fm = g.getFontMetrics();
		
		g.setColor(bgColor); g.fillRect(0, 0, size.width, size.height);
		g.setColor(fgColor); 
		g.drawString(stringShow1, 1, size.height-6);
		g.setColor(Color.red); 
		g.drawString(stringShow2, 1 + fm.stringWidth(stringShow1), size.height-6);
	}
	
	public void clearText() {
		
		setText("", "");
	}
	
	public void paint(Graphics g) {

		g.setFont(new Font ("Dialog", Font.PLAIN, 9));
		FontMetrics fm = g.getFontMetrics();
		
		g.setColor(bgColor); g.fillRect(0, 0, size.width, size.height);
		g.setColor(fgColor); 
		g.drawString(stringShow1, 1, size.height-6);
		g.setColor(Color.red); 
		g.drawString(stringShow2, 1 + fm.stringWidth(stringShow1), size.height-6);
	}
		
}	