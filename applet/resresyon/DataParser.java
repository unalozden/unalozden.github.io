package edu.csusb.danby.stat;

import java.util.*;

/**
*   <PRE>
 *  DataParser reads an array of Strings (such
 *  as a text file). The 0th row is  the title of
 *  the data.   The first row should be
 *  a format string, with 's' for string,
 *  'i' for int, and 'd' for double. The characters
 *  should be separated by spaces. The second line
 *  should be names for the columns, again separated by
 *  spaces. After that should come the data, separated
 *  by spaces.
 *  Thus a data file should start out:
 *  ----------------------------------------------------
 *  # this is an example file
 *  #begins with a comment explaining
 *  TITLE OF DATA FILE
 *  s d d i
 *  Student_name age weight score
 *  Joe_B  34 123 78
 *  Mary_K 23 108 78
 * -------------------------------------------------------
 * </PRE>
 * @author     Charles Stanton
 * @created    September 3, 2001
 * @version    Tue Jul 23 09:51:15 PDT 2002
 */
public class DataParser extends Object {
    protected String[][] textArray;
    protected String[] titles;
    protected char[] formatString;
    protected int numberRows, numberColumns;

    
    
    protected String[] columnName;
   
    protected String title;
   
    protected int[] columnSelectionInterval = {0,0};
    boolean editable=false;
    protected String[] commentStringArray;

    /**
     *  Constructor for the DataParser
     *
     * @param  text  to be parsed
     */
    public DataParser(String[] text) {
        parseText(text);

        /*int numberStringColumns;

        int numberDoubleColumns;
        int numberIntCoumns;


        StringTokenizer currentLine;
        char formatChar;
        String token;
        String[] tempStringArray;
        numberRows = text.length;
        if (numberRows < 2) {
            //must have at least format string
            // and column names
            System.out.println("Invalid data file, number Rows =" + numberRows);
        }

        // Read format string from first line of text[]
        currentLine = new StringTokenizer(text[0]);
        numberColumns = currentLine.countTokens();
        formatString = new char[numberColumns];
        for (int j = 0; j < numberColumns; j++) {
            token = currentLine.nextToken();
            formatString[j] = token.toCharArray()[0];
        }
        // Read column names from second line of text[]
        titles = new String[numberColumns];
        currentLine = new StringTokenizer(text[1]);
        for (int j = 0; j < numberColumns; j++) {
            token = currentLine.nextToken();
            titles[j] = new String(token);
        }
        // Read data from text[] into textArray
        textArray = new String[numberRows - 2][numberColumns];
        for (int i = 2; i < numberRows; i++) {
            currentLine = new StringTokenizer(text[i]);
            if (currentLine.countTokens() == numberColumns) {
                for (int j = 0; j < numberColumns; j++) {
                    token = currentLine.nextToken();
                    textArray[i - 2][j] = new String(token);
                }
                //end for
            }
            //end if
            else {
                System.error.println("Invalid data file, currentLine.countTokens ="
                         + currentLine.countTokens() + "  numberColumns = " + numberColumns);
            }
        }
        */

    }


     private void parseText( String[] text){
        int slength = text.length;
        boolean comment = true;
        int commentLines =0;
        StringTokenizer currentLine;
        //char  firstChar;
        String token;
        String[] tempStringArray;
        //numberRows = text.length-3;
        String[]  cStringArray= new String[text.length];
        
        while(comment){
            currentLine = new StringTokenizer(text[commentLines]);
            token = currentLine.nextToken();
            if (token.toCharArray()[0] == '#'){
                cStringArray[commentLines]=text[commentLines].substring(1);
                //System.out.println(cStringArray[commentLines]);
                commentLines++;
            }
            else {comment = false;}
        }
        if (commentLines >0){
            commentStringArray = new String[commentLines];
            System.arraycopy(cStringArray,0,commentStringArray,0,commentLines);
        }   
        numberRows = text.length-(3+commentLines);
        
        
            
            
        
        if (numberRows < 0) {		//must have at least title,  format string
                                    // and column names
            System.out.println("Invalid data file +numberRows = "+
                                numberRows);// Should be some sort of exception
        }
        //Read title from first line of text
        title = new String(text[commentLines]);
        
        // Read format string from second line of text[]
        currentLine = new StringTokenizer(text[commentLines+1]);
        numberColumns = currentLine.countTokens();
        formatString = new char[numberColumns];
        for (int j=0; j< numberColumns; j++){
            token = currentLine.nextToken();
            formatString[j] = token.toCharArray()[0];
            }
        // Read column names from third line of text[]
        columnName = new String[numberColumns];
        currentLine = new StringTokenizer(text[commentLines+2]);

        for (int j=0; j< numberColumns; j++){
            token = currentLine.nextToken();
            columnName[j] = new String(token);
        }

        // Read data from text[] into textArray
        textArray = new String[numberRows][numberColumns];
        for (int i=0; i< numberRows; i++){
            currentLine = new StringTokenizer(text[i+3+commentLines]);
            if (currentLine.countTokens()==numberColumns){
                for (int j=0; j< numberColumns; j++){
                    token = currentLine.nextToken();
                    textArray[i][j] = new String(token);
                    }	//end for
            }  //end if
            else {
                System.out.println("Invalid data file, no tokens ="
                                +currentLine.countTokens()); 
                System.out.println("i ="+i+" numberColumns ="+numberColumns);// Should be some sort of exception
            }
        }			
    
  }  
    /**
     *  Gets the FormatString of the text file
     *
     * @return    The FormatString value
     */
    public char[] getFormatString() {
        return formatString;
    }


    /**
     *  Gets the Names of the data columns
     *
     * @return    The Names value
     */
    public String[] getNames() {
        return titles;
    }


    /**
     *  Gets the TextArray of the DataParser
     *
     * @return    The TextArray value
     */
    public String[][] getTextArray() {
        return textArray;
    }


    /**
     *  Gets the NumberRows in the data file
     *
     * @return    The NumberRows value
     */
    public int getNumberRows() {
        return numberRows;
        //Number of data rows+2
    }


    /**
     * Gets the number of columns in the data file
     *
     * @return    The NumberColumns value
     */
    public int getNumberColumns() {
        return numberColumns;
    }


    /**
     *  Gets the DoubleData attribute of the DataParser object
     *
     * @param  column  Description of Parameter
     * @return         The DoubleData value
     */
    public double[] getDoubleData(int column) {
        int length = textArray.length;
        double[] dData = new double[length];
        Double temp;

        if (formatString[column] == 's') {
            System.out.println("column is not numeric");
        }
        try {
            for (int i = 0; i < length; i++) {
                temp = new Double(textArray[i][column]);
                dData[i] = temp.doubleValue();
            }
            return dData;
        }
        catch (NumberFormatException nfe) {
            System.out.println("Number format exception");
            return null;
        }
    }


    /**
     *  Exception class for format errors
     *
     * @created    September 3, 2001
     */
    class FormatStringException extends Exception {
        /**
         *  Constructor for the FormatStringException
         *
         * @param  s  Description of Parameter
         */
        public FormatStringException(String s) {
            super(s);
        }
    }

}

