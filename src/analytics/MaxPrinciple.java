package analytics;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

/**
 * Application Lifecycle Listener implementation class maxPrinciple
 *
 */
@WebListener
public class MaxPrinciple implements  HttpSessionAttributeListener {

	private double maxValue = 0;
	/**
	 * Default constructor. 
	 */
	public MaxPrinciple() {

	}

	/**
	 * @see HttpSessionAttributeListener#attributeAdded(HttpSessionBindingEvent)
	 */
	public void attributeAdded(HttpSessionBindingEvent arg0)  { 
		String name = arg0.getName();
		if(name.equalsIgnoreCase("sessionPrinciple"))
		{
			//System.out.println("attribute Added");
			setSessionMaxPrinciple(arg0);
			setContextMaxPrinciple(arg0);
			//System.out.println("session # " + arg0.getSession().getId());
		}

	}

	/**
	 * @see HttpSessionAttributeListener#attributeRemoved(HttpSessionBindingEvent)
	 */
	public void attributeRemoved(HttpSessionBindingEvent arg0)  { 
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpSessionAttributeListener#attributeReplaced(HttpSessionBindingEvent)
	 */
	public void attributeReplaced(HttpSessionBindingEvent arg0)  { 

		String name = arg0.getName();
		//		this.get
		if(name.equalsIgnoreCase("sessionPrinciple"))
		{
			setSessionMaxPrinciple(arg0);
			setContextMaxPrinciple(arg0);
			//System.out.println("session # " + arg0.getSession().getId());
		}
	}

	private void setContextMaxPrinciple(HttpSessionBindingEvent arg0)
	{
		HttpSession hs = arg0.getSession();
		String sessionMP = getSessionMaxPrinciple();
		String contextMP = getContextMaxPrinciple(arg0);
		String currMax;
		try
		{
			currMax = getMaxValue(sessionMP, contextMP);
			hs.getServletContext().setAttribute("maxPrinciple", currMax);
			//System.out.println("currentMax " + currMax);

		}
		catch(Exception e)
		{

		}
	}

	private String getContextMaxPrinciple(HttpSessionBindingEvent arg0)
	{

		HttpSession hs = arg0.getSession();
		String result = (String)hs.getServletContext().getAttribute("maxPrinciple");
		if(result == null)
			result = "0";
		return result;

	}

	private String getSessionMaxPrinciple()
	{
		String result;
		if(this.maxValue == 0)
			result = "0";
		else
			result = String.valueOf(this.maxValue);
		return result;
	}

	private void setSessionMaxPrinciple(HttpSessionBindingEvent arg0)
	{
		HttpSession hs = arg0.getSession();
		String currentMax = (String) hs.getAttribute("sessionPrinciple");
		setMaxValue(currentMax);
	}


	private void setMaxValue(String newValue)
	{

		double nv = validatePrinciple(newValue);
		if(this.maxValue < nv)
			this.maxValue = nv;

	}

	//get max value between Value1 and Value2
	private String getMaxValue(String v1, String v2)
	{
		String result = v1;
		try
		{
			double dv1 = Double.parseDouble(v1);
			double dv2 = Double.parseDouble(v2);
			if(dv1 < dv2) result = String.valueOf(dv2);
		}
		catch(Exception e)
		{		
		}
		return result;		
	}

	//validate session principle
	private double validatePrinciple(String principle) 
	{
		double result = 0;
		try
		{
			result = Double.parseDouble(principle);
		}
		catch(Exception e)
		{
			result = 0;
		}
		if(result <= 0)
		{
			result = 0;
		}
		return result;
	}

}