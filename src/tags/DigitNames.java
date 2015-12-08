package tags;

import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class DigitNames extends SimpleTagSupport
{
    private int max;

    public void doTag() throws JspException, IOException
    {
            JspWriter os = this.getJspContext().getOut();
            
            StringWriter sw = new StringWriter();
            getJspBody().invoke(sw);
            //os.write("<i>Attribute = "+this.max+"</i><br/>");
            double payment = 0;
            int intPayment = 0;
            try
            {
            	payment = Double.parseDouble(sw.toString());
            	intPayment = (int) payment;
            }
            catch(Exception e)
            {
            	
            }
            if((payment < 1) && (payment >= 0))
            {
            	os.write("<i>[0]</i>");
            }
            else if(intPayment < this.max)
            {
            	os.write("<i>["+convertDigitName(intPayment)+"]</i>");
            }
            
    }
    
    public void setMax(int Max)
    {
            this.max = Max;
    }
    
    private String convertDigitName(int intPayment)
    {
    	String[] digitName = {"zero", "one", "two", "three", 
    					"four", "five", "six", "seven",
    					"eight", "nine", "ten"};
    	String result="";
    	String number = String.valueOf(intPayment);
    	char[] digitCharArray = number.toCharArray();
    	for(int i = 0; i < digitCharArray.length; i++)
    	{
    		result += digitName[Character.getNumericValue(digitCharArray[i])] + "-";
    	}
    	result = result.substring(0, result.length() -1);
    	return result;
    }
}

