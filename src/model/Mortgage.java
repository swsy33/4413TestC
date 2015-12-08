package model;
import java.io.PrintWriter;
import java.util.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.stream.StreamResult;

import com.google.gson.Gson;

public class Mortgage {

	public final static String NO_BANK = "Select a bank ...";
	
	private MortgageDAO dao;
	
	public Mortgage() throws Exception
	{
		this.dao = new MortgageDAO();
	}
	
	//D1
	/*public String servePayment(String p, String a, String r)
	{
		String result = "";
		double pay = 0;
		try
		{
			pay = computePayment(p, a, r, null);
			result = "Ok\n" + String.valueOf(pay);
		}
		catch(Exception e)
		{
			result = "No\n" + e.getMessage();
		}

		return result;
		
	}
	*/
	
	
	public void serveOffer(String principle, String amort, PrintWriter pw) throws Exception
	{
		//String data = args; System.out.println("in m data = " + data);
		String validity = "";
		try
		{
			double payment = computePayment(principle, amort, "0", null);
			validity = "OK";
		}
		catch(Exception e)
		{
			validity = e.getMessage();
		}
		List<MortgageBean> mblist = new ArrayList<MortgageBean>();
		//String valid = "";
		try{
			mblist = getOffer(Double.parseDouble(principle), Integer.parseInt(amort));
			validity = "OK";
		}
		catch(Exception e)
		{
			//throw new Exception("cannot get bank list!");
			validity = "OK";
		}
		OffersPod op = new OffersPod(validity, mblist);
		Gson gson = new Gson();
		
		pw.print(gson.toJson(op));
		System.out.println("in m seroffer");
	}
	
	//D2
	public void servePayment(String args, PrintWriter pw) throws JAXBException
	{
		String data = args;
		Gson gson = new Gson();
		JsonBean jb = gson.fromJson(data, JsonBean.class);
		String principle = jb.getPrinciple();
		String interest =jb.getInterest();
		String amort = jb.getAmort();
		//String bank = jb.getBank();
		boolean status = false;
		String msg = "";
		double payment = 0;
		try
		{
			payment = computePayment(principle, amort, interest, null);
			status = true;
		}
		catch(Exception e)
		{
			msg = e.getMessage();
		}
				
		//need to change to return xml to controller
		//use JAXB to marshal a bean into xml on the print writer
		PaymentBean pb = new PaymentBean(status, payment, msg);
		JAXBContext jc = JAXBContext.newInstance(pb.getClass());
		Marshaller mashaller = jc.createMarshaller();
		mashaller.marshal(pb, new StreamResult(pw));
	}
	
	private double computePayment(String p, String a, String r, String bank) throws Exception
	{

		double result = 0; //monthly payment
		double principle = validatePrinciple(p);
		int amortization = validateAmortization(a);
		double interest;
		
		if(!r.isEmpty())
		{
			interest = validateInterest(r);
			
		}//if interest not empty
		else 
		{
			if((bank == null) || bank.isEmpty() || bank.equalsIgnoreCase(NO_BANK))
			{
				throw new Exception("You may need to choose a bank or enter an interest value!");
			}
			else
			{	//bank is specified
				MortgageBean mb = this.dao.getRate(principle, amortization, bank);
				interest = mb.getRate();
			}
		}
		result = computePayment(principle, amortization, interest);
		return result;
	}
	
	private List<MortgageBean> getOffer(double p, int a) throws Exception
	{
		List<MortgageBean> offer = new ArrayList<MortgageBean>();
		List<String> banks =  getBankList();
		for(int i = 0; i < banks.size(); i++)
		{
			MortgageBean mb = this.dao.getRate(p, a, banks.get(i));
			offer.add(mb);		
		}
		return offer;
	}
	public List<String> getBankList() throws Exception
	{
		List<MortgageBean> mblist = this.dao.getBanks();
		List<String> banklist = new LinkedList<String>();
		//banklist.add(Mortgage.NO_BANK);
		if(mblist.size() <= 0)
		{
			throw new Exception("Bank List is empty!");
		}
		else
		{
			for(Iterator<MortgageBean> i = mblist.iterator(); i.hasNext();)
			{
				MortgageBean mb = i.next();
				banklist.add(mb.getBank());
			}
		}
		return banklist;
	}
//---------------------------------------------------------------------	
	private double computePayment(double principle, double amortization, double interest)
	{
		double result;
		interest = interest/100/12;
		double expo = Math.pow((1+ interest), (-amortization * 12));
		double payment = interest * principle/(1  - expo);
		double roundP = Math.round(payment * 100);
		result = roundP / 100;
		return result;
	}

	private double validatePrinciple(String principle) throws Exception
	{
		double result;
		try
		{
			result = Double.parseDouble(principle);
		}
		catch(Exception e)
		{
			throw new Exception("Principle is not numeric!");
		}
		if(result <= 0)
		{
			throw new Exception("Principle is not positive!");
		}
		return result;
	}
	private int validateAmortization(String amortization)throws Exception
	{
		int result;
			try
			{
				result = Integer.parseInt(amortization);
			}
			catch(Exception e)
			{
				throw new Exception("Amortization is not numeric!");
			}

			if((result != 20) && (result != 25) && (result != 30))
			{
				throw new Exception("Amortization is wrong!");
			}
			return result;
	}
	
	private double validateInterest(String interest)throws Exception
	{
		double result;
		try
		{
			result = Double.parseDouble(interest);
		}
		catch(Exception e)
		{
			throw new Exception("Interest is not numeric!");
		}
		if(result < 0)
		{
			throw new Exception("Interest is not positive!");
		}
		if(result > 100)
		{
			throw new Exception("Interest is out of range!");
		}
		return result;
	}
}
