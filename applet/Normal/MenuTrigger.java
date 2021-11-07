import java.awt.*;
import java.awt.event.*;

public class MenuTrigger extends Canvas {
	
	private String title;
	private Dimension size=new Dimension(70,18);
	private final int sepLocation = 55;
	
	private boolean triggerPressed=false;
	private Color triggerBgColor = Color.lightGray;
	private Color triggerFgColor = Color.black;
	
	private int x[] = new int[3];
	private int y[] = new int[3];
	
	public MenuTrigger(String title, Color bgColor) {
		super();
		this.title=title;
		setBackground(bgColor);
		this.setSize(70, 18);
	}
	
	public void pressTrigger() {
		triggerPressed = true;
System.out.println("Trigger Pressed---1  !!");
		repaint();
	}
	
	public void releaseTrigger() {
		triggerPressed = false;
		repaint();
	}
	
	public Dimension getPreferredSize() {
		return size;
	}
	
	public void paint(Graphics g) {

		if (triggerPressed) { triggerBgColor = Color.darkGray; triggerFgColor = Color.white; }
		else { triggerBgColor = Color.lightGray; triggerFgColor = Color.black; }
System.out.println("Trigger Pressed---2  !!" +triggerBgColor+ triggerFgColor );

		g.setColor(triggerBgColor); g.fillRoundRect(1, 2, size.width-2, size.height-4, 5, 5);				
		g.setColor(Color.black); g.drawRoundRect(1, 2, size.width-2, size.height-4, 5, 5);
		
		g.setColor(triggerFgColor);
		g.setFont(new Font ("Dialog", Font.PLAIN, 9));
		g.drawString(title, 10, 13);
		g.drawLine(sepLocation, 2, sepLocation, size.height-3);
		
		x[0] = sepLocation+4; x[1] = x[0]+3; x[2] = x[1]+3;
		y[0] = 9; y[1] = y[0]-3; y[2] = y[0];
		g.fillPolygon(x, y, 3); 
		y[0] = y[0]+1; y[1] = y[0]+3; y[2] = y[0];
		g.fillPolygon(x, y, 3); 
	}
		
}	