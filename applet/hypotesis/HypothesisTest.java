/*
	Applet to Perform a Hypothesis Test
*/

import TestPanel;
import HypPanel;
import java.awt.*;
import java.applet.*;
import java.lang.*;

public class HypTest extends Applet
{
	java.awt.Panel cardPanel;
	HypPanel hypPanel;
	TestPanel testPanel;
	CriteriaPanel criteriaPanel;
	CardLayout CardLO;
	java.awt.Panel ControlPanel;
	java.awt.TextField dataField;
	java.awt.Button leftButton;
	java.awt.Button rightButton;
	static final int SET_POP_PARAM = 0;
	static final int SET_ALPHA = 1;
	static final int SET_N = 2;
	static final int SET_DATA_TYPE = 3;
	static final int SET_DATA = 4;
	static final int SET_SD = 5;
	static final int SET_SAM_STAT = 6;
	static final int SET_NEW = 7;
	static final int PARAM_MEAN = 10;
	static final int PARAM_PROP = 11;
	static final int TWO_TAIL = 2;
	static final int TAIL_LOW = 0;
	static final int TAIL_HI = 1;
	int tails = TWO_TAIL;
	int stage = SET_POP_PARAM;
	int param = PARAM_MEAN;
	int javaOriginY = 70;
	int n = 1;
	int numData = 0;
	int statusCycle = 0;
	double dataVal;
	double xmin = 0;
	double xmax = 1;
	double popParam = 0;
	double stDev = 1;
	double alpha = 0;
	double sampleStat;
	double xCutOffHi, xCutOffLo;
	boolean IS_PARAM = true;
	String result;
	private String[][] instruction = new String[8][6];
	
	public void init()
	{
		super.init();
		setLayout(null);
		resize(450,360);
		cardPanel = new java.awt.Panel();
		CardLO = new CardLayout(0,0);
		cardPanel.setLayout( CardLO );
		cardPanel.reshape(1,0,450,320);
		add(cardPanel);
		hypPanel = new HypPanel( this );
		hypPanel.setLayout(null);
		hypPanel.reshape(0,0,450,320);
		cardPanel.add( "hyp", hypPanel );
		testPanel = new TestPanel( this );
		testPanel.setLayout(null);
		testPanel.reshape(0,0,450,320);
		cardPanel.add( "test", testPanel );
		testPanel.hide();
		criteriaPanel= new CriteriaPanel( this );
		criteriaPanel.setLayout(null);
		criteriaPanel.reshape(0,0,450,320);
		cardPanel.add( "criteria", criteriaPanel );
		criteriaPanel.hide();
		ControlPanel = new java.awt.Panel();
		ControlPanel.setLayout(null);
		ControlPanel.reshape(1,320,448,41);
		add(ControlPanel);
		dataField = new java.awt.TextField();
		dataField.reshape(47,6,92,29);
		ControlPanel.add(dataField);
		leftButton = new java.awt.Button("Enter");
		leftButton.reshape(180,10,110,23);
		ControlPanel.add(leftButton);
		rightButton = new java.awt.Button("Redo");
		rightButton.reshape(325,10,77,23);
		ControlPanel.add(rightButton);
		CardLO.first( cardPanel );
		setInstruction();
		repaint();
	}
	
	public void setInstruction()
	{
		instruction[0][0] = "Set the population parameter by typing it into the";
		instruction[0][1] = "textfield below. This is the population mean or";
		instruction[0][2] = "proportion whose value you are testing. It is denoted";
		instruction[0][3] = "by µ sub 0 or p sub 0. We are looking for the";
		instruction[0][4] = "historical value or the staus quo for the population";
		instruction[0][5] = "parameter in the problem.";
		
		instruction[1][0] = "Set the level of significance by typing it into the";
		instruction[1][1] = "textfield below. The level of significance is denoted";
		instruction[1][2] = "by the Greek letter 'alpha' and represents the";
		instruction[1][3] = "probability of a type I error in the test.";
		instruction[1][4] = "";
		instruction[1][5] = "";
		
		instruction[2][0] = "Type the number of data values in the sample into";
		instruction[2][1] = "the textfield below. The number of data values is";
		instruction[2][2] = "denoted by the letter 'n'";
		instruction[2][3] = "";
		instruction[2][4] = "";
		instruction[2][5] = "";
		
		instruction[3][0] = "Indicate whether you want to enter the data";
		instruction[3][1] = "values individually or the mean and standard";
		instruction[3][2] = "deviation of the data. To enter the mean and";
		instruction[3][3] = "standard deviation, click the button on the left";
		instruction[3][4] = "labeled 'St Dev-Mean'. To enter the data, click";
		instruction[3][5] = "the button on the right, labeled 'enter data'.";
		
		instruction[4][0] = "You have indicated that you want to enter the";
		instruction[4][1] = "data values one at a time. Type each data value";
		instruction[4][2] = "into the textfield below and press the 'enter' key";
		instruction[4][3] = "after entering each one. When you have entered";
		instruction[4][4] = "number of data values that you indicated in step";
		instruction[4][5] = "3, the progrm will proceed to the next step.";
		
		instruction[5][0] = "You have indicated that you want to enter the";
		instruction[5][1] = "mean and standard deviation of the data. Enter";
		instruction[5][2] = "the standard deviation now by typing it into the";
		instruction[5][3] = "textfield below. Press the 'enter' key after typing";
		instruction[5][4] = "the standard deviation into the textfield.";
		instruction[5][5] = "";
		
		instruction[6][0] = "You have indicated that you want to enter the";
		instruction[6][1] = "mean and standard deviation of the data. Enter";
		instruction[6][2] = "the mean now by typing it into the textfield";
		instruction[6][3] = "below. Press the 'enter' key after typing the";
		instruction[6][4] = "mean into the textfield.";
		instruction[6][5] = "";
		
		instruction[7][0] = "The test is complete and the results are given";
		instruction[7][1] = "below. If you wish to perform a new test, press";
		instruction[7][2] = "the button on the left labeled 'New Test'.";
		instruction[7][3] = "";
		instruction[7][4] = "";
		instruction[7][5] = "";
	}
	
	public void instruct(Graphics g)
	{
		Font f = g.getFont();
		Font nf = new Font("sans-serif", Font.PLAIN, 14 );
		g.setFont(nf);
		FontMetrics fm = g.getFontMetrics();
		for ( int i = 0; i < instruction[stage].length; i++ )
		{
			g.drawString( instruction[stage][i], 20, 220 +i*15 );
		}
		g.setFont( f );
	}
	
	public void showHyp( Graphics g )
	{
		Font f = g.getFont();
		Font nf = new Font("sans-serif", Font.PLAIN, 14 );
		g.setFont(nf);
		FontMetrics fm = g.getFontMetrics();
		if ( ( stage > SET_ALPHA )&&( stage < SET_SAM_STAT ) )
		{
			g.drawString( "The rejection region for the hypothesis below,", 20, 115 );
			g.drawString( "is shaded in red on the graph above.", 20, 130 );
		} else if ( stage < SET_N )
		{
			g.drawString( "The hypothesis you have chosen is given below.", 20, 130 );
		}
		if ( stage < SET_SAM_STAT )
		{
			if ( param == PARAM_MEAN )
			{
				if ( tails == TAIL_LOW )
				{
					g.drawString( "H", 180, 160 );
					g.drawString( "o", 180 + fm.stringWidth("H"), 164 );
					if ( stage == SET_POP_PARAM )
					{
						g.drawString( ": µ „ µ", 180 + fm.stringWidth("Ha"), 160 );
						g.drawString( "o", 180 + fm.stringWidth("Ho: µ „ µ"), 164 );
					} else if ( stage > SET_POP_PARAM )
					{
						g.drawString( ": µ „ " + popParam, 180 + fm.stringWidth("Ha"), 160 );
					}
					g.drawString( "H", 180, 190 );
					g.drawString( "a", 180 + fm.stringWidth("H"), 194 );
					if ( stage == SET_POP_PARAM )
					{
						g.drawString( ": µ < µ", 180 + fm.stringWidth("Ha"), 190 );
						g.drawString( "o", 180 + fm.stringWidth("Ha: µ < µ"), 194 );
					} else if ( stage > SET_POP_PARAM )
					{
						g.drawString( ": µ < " + popParam, 180 + fm.stringWidth("Ha"), 190 );
					}
				} else if ( tails == TAIL_HI )
				{
					g.drawString( "H", 180, 160 );
					g.drawString( "o", 180 + fm.stringWidth("H"), 164 );
					if ( stage == SET_POP_PARAM )
					{
						g.drawString( ": µ ¾ µ", 180 + fm.stringWidth("Ha"), 160 );
						g.drawString( "o", 180 + fm.stringWidth("Ho: µ ¾ µ"), 164 );
					} else if ( stage > SET_POP_PARAM )
					{
						g.drawString( ": µ ¾ " + popParam, 180 + fm.stringWidth("Ha"), 160 );
					}
					g.drawString( "H", 180, 190 );
					g.drawString( "a", 180 + fm.stringWidth("H"), 194 );
					if ( stage == SET_POP_PARAM )
					{
						g.drawString( ": µ > µ", 180 + fm.stringWidth("Ha"), 190 );
						g.drawString( "o", 180 + fm.stringWidth("Ha: µ > µ"), 194 );
					} else if ( stage > SET_POP_PARAM )
					{
						g.drawString( ": µ > " + popParam, 180 + fm.stringWidth("Ha"), 190 );
					}
				} else if ( tails == TWO_TAIL )
				{
					g.drawString( "H", 180, 160 );
					g.drawString( "o", 180 + fm.stringWidth("H"), 164 );
					if ( stage == SET_POP_PARAM )
					{
						g.drawString( ": µ = µ", 180 + fm.stringWidth("Ha"), 160 );
						g.drawString( "o", 180 + fm.stringWidth("Ho: µ = µ"), 164 );
					} else if ( stage > SET_POP_PARAM )
					{
						g.drawString( ": µ = " + popParam, 180 + fm.stringWidth("Ha"), 160 );
					}
					g.drawString( "H", 180, 190 );
					g.drawString( "a", 180 + fm.stringWidth("H"), 194 );
					if ( stage == SET_POP_PARAM )
					{
						g.drawString( ": µ ‚ µ", 180 + fm.stringWidth("Ha"), 190 );
						g.drawString( "o", 180 + fm.stringWidth("Ha: µ ‚ µ"), 194 );
					} else if ( stage > SET_POP_PARAM )
					{
						g.drawString( ": µ ‚ " + popParam, 180 + fm.stringWidth("Ha"), 190 );
					}
				}
			} else if ( param == PARAM_PROP )
			{
				if ( tails == TAIL_LOW )
				{
					g.drawString( "H", 180, 160 );
					g.drawString( "o", 180 + fm.stringWidth("H"), 164 );
					if ( stage == SET_POP_PARAM )
					{
						g.drawString( ": p „ p", 180 + fm.stringWidth("Ha"), 160 );
						g.drawString( "o", 180 + fm.stringWidth("Ho: p „ p"), 164 );
					} else if ( stage > SET_POP_PARAM )
					{
						g.drawString( ": p „ " + popParam, 180 + fm.stringWidth("Ha"), 160 );
					}
					g.drawString( "H", 180, 190 );
					g.drawString( "a", 180 + fm.stringWidth("H"), 194 );
					if ( stage == SET_POP_PARAM )
					{
						g.drawString( ": p < p", 180 + fm.stringWidth("Ha"), 190 );
						g.drawString( "o", 180 + fm.stringWidth("Ha: p < p"), 194 );
					} else if ( stage > SET_POP_PARAM )
					{
						g.drawString( ": p < " + popParam, 180 + fm.stringWidth("Ha"), 190 );
					}
				} else if ( tails == TAIL_HI )
				{
					g.drawString( "H", 180, 160 );
					g.drawString( "o", 180 + fm.stringWidth("H"), 164 );
					if ( stage == SET_POP_PARAM )
					{
						g.drawString( ": µ ¾ p", 180 + fm.stringWidth("Ha"), 160 );
						g.drawString( "o", 180 + fm.stringWidth("Ho: p ¾ p"), 164 );
					} else if ( stage > SET_POP_PARAM )
					{
						g.drawString( ": p ¾ " + popParam, 180 + fm.stringWidth("Ha"), 160 );
					}
					g.drawString( "H", 180, 190 );
					g.drawString( "a", 180 + fm.stringWidth("H"), 194 );
					if ( stage == SET_POP_PARAM )
					{
						g.drawString( ": p > p", 180 + fm.stringWidth("Ha"), 190 );
						g.drawString( "o", 180 + fm.stringWidth("Ha: p > p"), 194 );
					} else if ( stage > SET_POP_PARAM )
					{
						g.drawString( ": p > " + popParam, 180 + fm.stringWidth("Ha"), 190 );
					}
				} else if ( tails == TWO_TAIL )
				{
					g.drawString( "H", 180, 160 );
					g.drawString( "o", 180 + fm.stringWidth("H"), 164 );
					if ( stage == SET_POP_PARAM )
					{
						g.drawString( ": p = p", 180 + fm.stringWidth("Ha"), 160 );
						g.drawString( "o", 180 + fm.stringWidth("Ho: p = p"), 164 );
					} else if ( stage > SET_POP_PARAM )
					{
						g.drawString( ": p = " + popParam, 180 + fm.stringWidth("Ha"), 160 );
					}
					g.drawString( "H", 180, 190 );
					g.drawString( "a", 180 + fm.stringWidth("H"), 194 );
					if ( stage == SET_POP_PARAM )
					{
						g.drawString( ": p ‚ p", 180 + fm.stringWidth("Ha"), 190 );
						g.drawString( "o", 180 + fm.stringWidth("Ha: p ‚ p"), 194 );
					} else if ( stage > SET_POP_PARAM )
					{
						g.drawString( ": p ‚ " + popParam, 180 + fm.stringWidth("Ha"), 190 );
					}
				}
			}
		}
	}

	public boolean handleEvent(Event event)
	{
		if (event.target == leftButton && event.id == Event.ACTION_EVENT)
		{
			leftButton_Clicked(event);
			return true;
		}
		if (event.target == rightButton && event.id == Event.ACTION_EVENT)
		{
			rightButton_Clicked(event);
			return true;
		}
		return super.handleEvent(event);
	}

	void leftButton_Clicked(Event event)
	{
		if ( stage != SET_DATA_TYPE )
		{
			String dataText = dataField.getText();
			if ( ( dataText == "" ) )
			{
				dataField.setText("");
				DataAlert dataError = new DataAlert( dataText, 1 );
				dataError.show();
			}
			enterData( dataText );
		} else if ( stage == SET_DATA_TYPE )
		{
			stage = SET_SD;
			leftButton.setLabel( "Enter" );
			rightButton.setLabel( "Redo" );
			testPanel.SHOW_HYP = true;
			testPanel.repaint();
		}	
	}

	void rightButton_Clicked(Event event)
	{
		switch( stage )
		{
			case SET_POP_PARAM:
			{
				CardLO.first(cardPanel);
				break;
			}
			case SET_ALPHA:
			{
				popParam = 0;
				testPanel.SHOW_HYP = true;
				testPanel.repaint();
				stage = SET_POP_PARAM;
				break;
			}
			case SET_N:
			{
				alpha = 0;
				testPanel.SHOW_HYP = true;
				testPanel.repaint();
				stage = SET_ALPHA;
				break;
			}
			case SET_DATA:
			{
				n = 1;
				testPanel.SHOW_HYP = true;
				testPanel.repaint();
				stage = SET_N;
				break;
			}
			case SET_SD:
			{
				testPanel.SHOW_HYP = true;
				testPanel.repaint();
				stage = SET_DATA_TYPE;
				leftButton.setLabel( "St Dev-Mean" );
				rightButton.setLabel( "Enter Data" );
				break;
			}
			case SET_SAM_STAT:
			{
				stDev = 1;
				testPanel.SHOW_HYP = true;
				testPanel.repaint();
				stage = SET_SD;
				break;
			}
			case SET_NEW:
			{
				sampleStat = 0;
				testPanel.SHOW_HYP = true;
				stage = SET_SAM_STAT;
				break;
			}
		}
	}
	
	public boolean action( Event event , Object arg )
     {
		if ( event.target == dataField )
		{
			String dataText = dataField.getText();
			if ( ( dataText == "" ) )
			{
				dataField.setText("");
				DataAlert dataError = new DataAlert( dataText, 1 );
				dataError.show();
				return true;
			}
			enterData( dataText );
			return true;
		}
		return super.action( event, arg );
	}
	
	public void enterData( String dataText )
	{
		dataField.setText("");
		try
		{
			dataVal = ( Double.valueOf( dataText ) ).doubleValue();
		}
		catch ( NumberFormatException e )
		{
			DataAlert dataError = new DataAlert( dataText, 1 );
			dataError.show();
			return;
		}
//		dataField.setText( "" + alpha2z( dataVal ) );
		switch( stage )
		{
			case SET_POP_PARAM:
			{
				popParam = dataVal;
				testPanel.SHOW_HYP = true;
				testPanel.repaint();
				stage = SET_ALPHA;
				break;
			}
			case SET_ALPHA:
			{
				alpha = dataVal;
				testPanel.SHOW_HYP = true;
				testPanel.repaint();
				stage = SET_N;
				break;
			}
			case SET_N:
			{
				n = ( int )Math.round( dataVal );
				testPanel.SHOW_HYP = true;
				testPanel.repaint();
				stage = SET_DATA_TYPE;
				leftButton.setLabel( "St Dev-Mean" );
				rightButton.setLabel( "Enter Data" );
				break;
			}
			case SET_DATA:
			{
				testPanel.SHOW_HYP = true;
				testPanel.repaint();
				break;
			}
			case SET_SD:
			{
				stDev = dataVal/Math.sqrt( n );
				stage = SET_SAM_STAT;
				testPanel.repaint();
				break;
			}
			case SET_SAM_STAT:
			{
				sampleStat = dataVal;
				stage = SET_NEW;
				CardLO.next( cardPanel );
				break;
			}
			case SET_NEW:
			{
				CardLO.first( cardPanel );
				stage = SET_POP_PARAM;
				param = PARAM_MEAN;
				tails = TAIL_LOW;
				javaOriginY = 180;
				xmin = 0;
				xmax = 200;
				popParam = 100;
				stDev = 15;
				break;
			}
		}
	}
	
/*
*	grid2java converts the displayed coordinates of a point on the number line
*	to the panel's coordinates.
*	<p>
*	@Param myx	The coordinates of a point on the number line as dispayed on 
*					the line.
*	</p><p>
*	@Return x  The panel coordinates of a point on the number line.
*	</p>
*/
	public int grid2javaX( double myx )
	{
		int x;
		x = (int)Math.round(  ( myx - xmin )*400/( xmax - xmin ) );
		return x;
	}

	public void paint(Graphics g)
	{
		resize(450,360);
		cardPanel.reshape(1,0,450,320);
		hypPanel.reshape(0,0,450,320);
		testPanel.reshape(0,0,450,320);
		ControlPanel.reshape(1,320,448,41);
		dataField.reshape(47,6,92,29);
		leftButton.reshape(180,10,110,23);
		rightButton.reshape(325,10,77,23);
		super.paint(g);
	}
	
/*
*	drawGrid draws the number line, tick marks and the coordinate numbers
*	<p>
*	@Param Graphics g  The graphics passed to the paint method
*	</p><p>
*	@Return void
*	</p>
*/
	public void drawGrid( Graphics g )
	{
		for ( int k = 0; k < 2; k++ )
		{
			if ( k == 0 )
			{
				xmin = -3;
				xmax = 3;
			} else
			{
				xmin = popParam - 3*stDev;
				xmax = popParam + 3*stDev;
			}
			if ( ( k == 0 )||( stage > SET_SD ) )
			{
				int javaOriginX = (int)Math.round( -xmin*400/( xmax - xmin ));
				double xPixPerUnit = 400/( xmax - xmin );
				double lastCoord, lastLine;
				g.drawLine( 20, javaOriginY + 30*k, 420, javaOriginY + 30*k );
				lastCoord = xmin - 1;
				lastLine = xmin - 1;
				for ( int i = (int)Math.ceil( xmin ); i <= (int)Math.floor( xmax ); i++ )
				{
					if ( (int)Math.round( ( i - lastCoord )*xPixPerUnit ) > 30 )
					{
						g.drawLine( grid2javaX( i ) + 20, javaOriginY - 3 + 30*k,
									 grid2javaX( i ) + 20, javaOriginY + 3 + 30*k );
						lastLine = i;
					}
					if ( i < 100 )
					{
						if ( (int)Math.round( ( i - lastCoord )*xPixPerUnit ) > 30 )
						{
							g.drawString( i + "", grid2javaX( i ) + 14,
												javaOriginY + 17 + 30*k );
							lastCoord = i;
						}
					} else
					{
						if ( (int)Math.round( ( i - lastCoord )*xPixPerUnit ) > 30 )
						{
							g.drawString( i + "", grid2javaX( i ) + 11,
												javaOriginY + 17 + 30*k );
							lastCoord = i;
						}
					}
				}
			}
			if ( ( k == 0 )&&( stage == SET_NEW ) )
			{
				g.fillOval( grid2javaX( ( sampleStat - popParam)/stDev ) +16,
							javaOriginY - 4, 8, 8 );
			}
			if ( ( k == 1 )&&( stage == SET_NEW ) )
			{
				g.fillOval( grid2javaX( sampleStat ) +16,
							javaOriginY + 26, 8, 8 );
				double testStat = ( sampleStat - popParam )/stDev;
				if ( ( testStat < xCutOffLo )&&( tails%2 == 0 ) )
				{
					result = "Reject the Null Hypothesis";
				} else if ( ( testStat > xCutOffHi )&&( tails > 0 ) )
				{
					result = "Reject the Null Hypothesis";
				} else
				{
					result = "Do Not Reject the Null Hypothesis";
				}
			}
		}
	}
	
/*
*	drawHorizSep draws a horizontal Mac Platinum Appearance bar at
*	height y. The bar has a five pixel width.
*	<p>
*	@Param Graphics g  The graphics passed to the paint routine.
*	@Param int y  The height (within the graphics context) at which 
*					the bar is to be drawn.
*	</p><p>
*	@Return void
*	</p>
*/
	public void drawHorizSep( Graphics g, int y )
	{
		g.drawLine( 0, y, this.bounds().width, y );
		g.setColor(Color.white);
		g.drawLine( 0, y + 1, this.bounds().width, y + 1 );
		g.setColor(Color.lightGray);
		g.drawLine( 0, y + 2, this.bounds().width, y + 2 );
		g.setColor(Color.darkGray);
		g.drawLine( 0, y + 3, this.bounds().width, y + 3 );
		g.setColor(Color.black);
		g.drawLine( 0, y + 4, this.bounds().width, y + 4 );		
	}
	
/*
*	drawNormal draws a bell curve in red that represents the distribution
*	from which the sample is taken. The popParam and standard deviation that
*	it uses are stored in globals.
*	<p>
*	@Param Graphics g The applet graphics as passed to paint via a couple of
*	method calls 
*	</p><p>
*	@Return void
*	</p>
*/
	public void drawNormal( Graphics g )
	{
		int numTails = tails/2 + 1;
		int height;
		double scaler;
		xCutOffHi = alpha2z( alpha/numTails );
		xCutOffLo = -xCutOffHi;
		int[] xPoints = new int[4];
		int[] yPoints = new int[4];
		double unitPerPix = .06;
		int oldHeight = ( int )Math.round( 60*Math.exp( -9/2 ) );
		int gridX;
		int oldGridX = grid2javaX( xmin );
		Color oldC = g.getColor();
		Color c = new Color( 0, 0, 255 );
		g.setColor( c );
		for ( double i = -3 + unitPerPix; i <= 3; i += unitPerPix )
		{
			height = ( int )Math.round( 60*Math.exp( -i*i/2 ) );
			gridX = (int)Math.round(  ( i + 3 )*400/6 );
			if ( stage > SET_ALPHA )
			{
				if ( ( i <= xCutOffLo )&&( tails%2 == 0 ) )
				{
					Color rc = new Color( 255, 0, 0 );
					g.setColor( rc );
					xPoints[0] = oldGridX + 20;
					xPoints[1] = gridX + 20;
					xPoints[2] = gridX + 20;
					xPoints[3] = oldGridX + 20;
					yPoints[0] = javaOriginY - oldHeight;
					yPoints[1] = javaOriginY - height;
					yPoints[2] = javaOriginY-1;
					yPoints[3] = javaOriginY-1;
					g.fillPolygon( xPoints, yPoints, 4 );
					g.setColor( c );
				}
				if ( ( i > xCutOffHi + unitPerPix )&&( tails > 0 ) )
				{
					Color rc = new Color( 255, 0, 0 );
					g.setColor( rc );
					xPoints[0] = oldGridX + 20;
					xPoints[1] = gridX + 20;
					xPoints[2] = gridX + 20;
					xPoints[3] = oldGridX + 20;
					yPoints[0] = javaOriginY - oldHeight;
					yPoints[1] = javaOriginY - height;
					yPoints[2] = javaOriginY-1;
					yPoints[3] = javaOriginY-1;
					g.fillPolygon( xPoints, yPoints, 4 );
					g.setColor( c );
				}
			}
			g.drawLine( oldGridX + 20, javaOriginY - oldHeight, gridX + 20,
						javaOriginY - height );
			oldHeight = height ;
			oldGridX = gridX;
		}
		g.setColor( oldC );
	}
	
	public double alpha2z( double alpha )
	{
		double height, oldHeight, scaler, gridX, oldGridX;
		double xCutOff = popParam, cumHeight = 0.00001;
		scaler = java.lang.Math.sqrt( 2*java.lang.Math.PI );
		oldHeight = Math.exp( -( 5.001 )*( 5.001 )/2 )/scaler;
		for ( double i = 5; i > -5; i -= .001 )
		{
			height = Math.exp( -( i )*( i )/2 )/scaler;
			cumHeight += .0005*( oldHeight + height );
			if ( cumHeight > alpha )
			{
				return i;
			}
			oldHeight = height;
		}
		return Double.NaN;
	}
	
}

import java.awt.Panel;
import java.awt.*;

public class HypPanel extends java.awt.Panel
{
	HypTest hypTest;
	java.awt.Checkbox Mean_Lo;
	CheckboxGroup Group1;
	java.awt.Checkbox Mean_Hi;
	java.awt.Checkbox Two_Mean;
	java.awt.Checkbox Prop_Lo;
	java.awt.Checkbox Prop_Hi;
	java.awt.Checkbox Two_Prop;
	
	public HypPanel( HypTest hypTest )
	{
		this.hypTest = hypTest;
		setLayout(null);
		resize(450,320);
		Group1 = new CheckboxGroup();
		Mean_Lo = new java.awt.Checkbox("", Group1, false);
		Mean_Lo.reshape(20,50,20,40);
		add(Mean_Lo);
		Mean_Hi = new java.awt.Checkbox("", Group1, false);
		Mean_Hi.reshape(20,140,20,40);
		add(Mean_Hi);
		Two_Mean = new java.awt.Checkbox("", Group1, false);
		Two_Mean.reshape(20,240,20,40);
		add(Two_Mean);
		Prop_Lo = new java.awt.Checkbox("", Group1, false);
		Prop_Lo.reshape(240,50,20,40);
		add(Prop_Lo);
		Prop_Hi = new java.awt.Checkbox("", Group1, false);
		Prop_Hi.reshape(240,140,20,40);
		add(Prop_Hi);
		Two_Prop = new java.awt.Checkbox("", Group1, false);
		Two_Prop.reshape(240,240,20,40);
		add(Two_Prop);
	}

	public boolean action(Event event, Object arg )
	{
		if ( event.target.equals( Mean_Lo ) )
		{
			hypTest.param = hypTest.PARAM_MEAN;
			hypTest.tails = hypTest.TAIL_LOW;
			hypTest.CardLO.next( hypTest.cardPanel );
			hypTest.testPanel.SHOW_HYP = true;
		}
		else if ( event.target.equals( Mean_Hi ) )
		{
			hypTest.param = hypTest.PARAM_MEAN;
			hypTest.tails = hypTest.TAIL_HI;
			hypTest.CardLO.next( hypTest.cardPanel );
			hypTest.testPanel.SHOW_HYP = true;
		}
		else if ( event.target.equals( Two_Mean ) )
		{
			hypTest.param = hypTest.PARAM_MEAN;
			hypTest.tails = hypTest.TWO_TAIL;
			hypTest.CardLO.next( hypTest.cardPanel );
			hypTest.testPanel.SHOW_HYP = true;
		}
		else if ( event.target.equals( Prop_Lo ) )
		{
			hypTest.param = hypTest.PARAM_PROP;
			hypTest.tails = hypTest.TAIL_LOW;
			hypTest.CardLO.next( hypTest.cardPanel );
			hypTest.testPanel.SHOW_HYP = true;
		}
		else if ( event.target.equals( Prop_Hi ) )
		{
			hypTest.param = hypTest.PARAM_PROP;
			hypTest.tails = hypTest.TAIL_HI;
			hypTest.CardLO.next( hypTest.cardPanel );
			hypTest.testPanel.SHOW_HYP = true;
		}
		else if ( event.target.equals( Two_Prop ) )
		{
			hypTest.param = hypTest.PARAM_PROP;
			hypTest.tails = hypTest.TWO_TAIL;
			hypTest.CardLO.next( hypTest.cardPanel );
			hypTest.testPanel.SHOW_HYP = true;
		}
		else return super.action(event, arg );
		return true;
	}

	public void paint(Graphics g)
	{
		Mean_Lo.reshape(20,50,20,40);
		Mean_Hi.reshape(20,140,20,40);
		Two_Mean.reshape(20,240,20,40);
		Prop_Lo.reshape(240,50,20,40);
		Prop_Hi.reshape(240,140,20,40);
		Two_Prop.reshape(240,240,20,40);
		Font f = g.getFont();
		Font nf = new Font("sans-serif", Font.PLAIN, 14 );
		g.setFont(nf);
		FontMetrics fm = g.getFontMetrics();
		
		g.drawString( "H", 60, 60 );
		g.drawString( "o", 60 + fm.stringWidth("H"), 64 );
		g.drawString( ": µ „ µ", 60 + fm.stringWidth("Ho"), 60 );
		g.drawString( "o", 60 + fm.stringWidth("Ho: µ „ µ"), 64 );
		g.drawString( "H", 60, 90 );
		g.drawString( "a", 60 + fm.stringWidth("H"), 94 );
		g.drawString( ": µ < µ", 60 + fm.stringWidth("Ha"), 90 );
		g.drawString( "o", 60 + fm.stringWidth("Ha: µ < µ"), 94 );
		
		g.drawString( "H", 60, 150 );
		g.drawString( "o", 60 + fm.stringWidth("H"), 154 );
		g.drawString( ": µ ¾ µ", 60 + fm.stringWidth("Ho"), 150 );
		g.drawString( "o", 60 + fm.stringWidth("Ho: µ ¾ µ"), 154 );
		g.drawString( "H", 60, 180 );
		g.drawString( "a", 60 + fm.stringWidth("H"), 184 );
		g.drawString( ": µ > µ", 60 + fm.stringWidth("Ha"), 180 );
		g.drawString( "o", 60 + fm.stringWidth("Ha: µ > µ"), 184 );
		
		g.drawString( "H", 60, 250 );
		g.drawString( "o", 60 + fm.stringWidth("H"), 254 );
		g.drawString( ": µ = µ", 60 + fm.stringWidth("Ho"), 250 );
		g.drawString( "o", 60 + fm.stringWidth("Ho: µ = µ"), 254 );
		g.drawString( "H", 60, 280 );
		g.drawString( "a", 60 + fm.stringWidth("H"), 284 );
		g.drawString( ": µ ‚ µ", 60 + fm.stringWidth("Ha"), 280 );
		g.drawString( "o", 60 + fm.stringWidth("Ha: µ ‚ µ"), 284 );
		
		g.drawString( "H", 280, 60 );
		g.drawString( "o", 280 + fm.stringWidth("H"), 64 );
		g.drawString( ": p „ p", 280 + fm.stringWidth("Ho"), 60 );
		g.drawString( "o", 280 + fm.stringWidth("Ho: p „ p"), 64 );
		g.drawString( "H", 280, 90 );
		g.drawString( "a", 280 + fm.stringWidth("H"), 94 );
		g.drawString( ": p < p", 280 + fm.stringWidth("Ha"), 90 );
		g.drawString( "o", 280 + fm.stringWidth("Ha: p < p"), 94 );
		
		g.drawString( "H", 280, 150 );
		g.drawString( "o", 280 + fm.stringWidth("H"), 154 );
		g.drawString( ": p ¾ p", 280 + fm.stringWidth("Ho"), 150 );
		g.drawString( "o", 280 + fm.stringWidth("Ho: p ¾ p"), 154 );
		g.drawString( "H", 280, 180 );
		g.drawString( "a", 280 + fm.stringWidth("H"), 184 );
		g.drawString( ": p > p", 280 + fm.stringWidth("Ha"), 180 );
		g.drawString( "o", 280 + fm.stringWidth("Ha: p > p"), 184 );
		
		g.drawString( "H", 280, 250 );
		g.drawString( "o", 280 + fm.stringWidth("H"), 254 );
		g.drawString( ": p = p", 280 + fm.stringWidth("Ho"), 250 );
		g.drawString( "o", 280 + fm.stringWidth("Ho: p = p"), 254 );
		g.drawString( "H", 280, 280 );
		g.drawString( "a", 280 + fm.stringWidth("H"), 284 );
		g.drawString( ": p ‚ p", 280 + fm.stringWidth("Ha"), 280 );
		g.drawString( "o", 280 + fm.stringWidth("Ha: p ‚ p"), 284 );
		
		g.setFont(f);
		super.paint(g);
	}
}

import java.awt.Panel;
import java.awt.Graphics;

public class TestPanel extends java.awt.Panel
{
	HypTest hypTest;
	boolean SHOW_HYP = false;
	public TestPanel( HypTest hypTest )
	{
		this.hypTest = hypTest;
	}
	
	public void paint( Graphics g )
	{
		hypTest.drawHorizSep( g, 316 );
		hypTest.drawNormal( g );
		hypTest.drawGrid( g );
		hypTest.instruct( g );
		if ( SHOW_HYP )
		{
			hypTest.showHyp( g );
			SHOW_HYP = false;
		}
	}
}

import java.awt.Event;
import java.awt.Panel;
import java.awt.*;

public class CriteriaPanel extends java.awt.Panel
{
	HypTest hypTest;
	CheckboxGroup Group1;
	java.awt.Checkbox accept;
	java.awt.Checkbox reject;
	int stage = 0;
	private String[][] instruction = new String[5][6];
	
	public CriteriaPanel( HypTest hypTest )
	{
		this.hypTest = hypTest;
		setLayout(null);
		resize(450,320);
		Group1 = new CheckboxGroup();
		accept = new java.awt.Checkbox("", Group1, false);
		accept.reshape(20,130,20,40);
		add(accept);
		reject = new java.awt.Checkbox("", Group1, false);
		reject.reshape(20,160,20,40);
		add(reject);
		setInstruction();
	}
	
	public void setInstruction()
	{
		instruction[0][0] = "The test criteria for the test that you have chosen";
		instruction[0][1] = "is given above. The z value referred to there is the";
		instruction[0][2] = "z-score computed from the sample mean via the";
		instruction[0][3] = "z-score formula. These values are also graphed above.";
		instruction[0][4] = "Decide which of the statements is true and finish the";
		instruction[0][5] = "test by clicking on the appropriate checkbox.";
		
		instruction[1][0] = "You are correct. Because the sample statistic above";
		instruction[1][1] = "is drawn in the red rejection region you should reject";
		instruction[1][2] = "the null hypothesis. Congratulations. You may try a new";
		instruction[1][3] = "test by clicking on the 'New' button below.";
		instruction[1][4] = "";
		instruction[1][5] = "";
		
		instruction[2][0] = "You are incorrect. Because the sample statistic above";
		instruction[2][1] = "is drawn in the red rejection region you should have";
		instruction[2][2] = "rejected the null hypothesis. You may try a new test'";
		instruction[2][3] = "by clicking on the 'New' button below.";
		instruction[2][4] = "";
		instruction[2][5] = "";
		
		instruction[3][0] = "You are correct. Because the sample statistic above";
		instruction[3][1] = "is not drawn in the red rejection region you should";
		instruction[3][2] = "not reject the null hypothesis. Congratulations! You";
		instruction[3][3] = "may try a 'New' test by clicking on the new button";
		instruction[3][4] = "below.";
		instruction[3][5] = "";
		
		instruction[4][0] = "You are incorrect. Because the sample statistic above";
		instruction[4][1] = "is not drawn in the red rejection region you should";
		instruction[4][2] = "not have rejected the null hypothesis. You may try";
		instruction[4][3] = "another test by clicking on the 'New' button below.";
		instruction[4][4] = "";
		instruction[4][5] = "";
	}
	
	public void instruct(Graphics g)
	{
		Font f = g.getFont();
		Font nf = new Font("sans-serif", Font.PLAIN, 14 );
		g.setFont(nf);
		FontMetrics fm = g.getFontMetrics();
		if ( hypTest.tails == hypTest.TAIL_LOW )
		{
			g.drawString( "Do not reject H", 60, 155 );
			g.drawString( "o", 60 + fm.stringWidth("Do not reject H"), 159 );
			g.drawString( "    if z „ " + hypTest.xCutOffLo, 60 + fm.stringWidth("Do not reject Ho"), 155 );
			g.drawString( "Reject H", 60, 185 );
			g.drawString( "a", 60 + fm.stringWidth("Do not reject H"), 189 );
			g.drawString( "    if z < " + hypTest.xCutOffLo, 60 + fm.stringWidth("Reject Ha"), 185 );
		} else if ( hypTest.tails == hypTest.TAIL_HI )
		{
			g.drawString( "Do not reject H", 60, 155 );
			g.drawString( "o", 60 + fm.stringWidth("Do not reject H"), 159 );
			g.drawString( "    if z ¾ " + hypTest.xCutOffHi, 60 + fm.stringWidth("Do not reject Ho"), 155 );
			g.drawString( "Reject H", 60, 185 );
			g.drawString( "a", 60 + fm.stringWidth("Reject H"), 189 );
			g.drawString( "    if z > " + hypTest.xCutOffHi, 60 + fm.stringWidth("Do not reject Ha"), 185 );
		} else if ( hypTest.tails == hypTest.TWO_TAIL )
		{
			g.drawString( "Do not reject H", 60, 155 );
			g.drawString( "o", 60 + fm.stringWidth("Do not reject H"), 159 );
			g.drawString( "    if z > " + hypTest.xCutOffLo + " and z < " + hypTest.xCutOffHi, 
								60 + fm.stringWidth("Do not reject Ho"), 155 );
			g.drawString( "Reject H", 60, 185 );
			g.drawString( "a", 60 + fm.stringWidth("Reject H"), 189 );
			g.drawString( "    if z > " + hypTest.xCutOffLo + " and z < " + hypTest.xCutOffHi, 
							60 + fm.stringWidth("Reject Ha"), 185 );
		}
		for ( int i = 0; i < instruction[stage].length; i++ )
		{
			g.drawString( instruction[stage][i], 20, 220 +i*15 );
		}
		g.setFont( f );
	}

	public boolean action(Event event, Object what)
	{
		if ( event.target.equals( accept ) )
		{
			if ( hypTest.result.equals( "Do Not Reject the Null Hypothesis" ) )
			{
				stage = 3;
			} else
			{
				stage = 2;
			}
			repaint();
		}
		else if ( event.target.equals( reject ) )
		{
			if ( hypTest.result.equals( "Reject the Null Hypothesis" ) )
			{
				stage = 1;
			} else
			{
				stage = 4;
			}
			repaint();
		}
		super.action(event,what);
		return true;
	}
	
	public void paint( Graphics g )
	{
		accept.reshape(20,130,20,40);
		reject.reshape(20,160,20,40);
		hypTest.drawHorizSep( g, 316 );
		hypTest.drawNormal( g );
		hypTest.drawGrid( g );
		instruct( g );
//		g.drawString( hypTest.result, 20, 130 );
	}
}