package model;

public class MortgageBean
{
	private String bank;
	private double rate;
	
	public MortgageBean(String bank, double rate)
	{
		this.bank = bank;
		this.rate = rate;
	}
		
	public MortgageBean(String bank)
	{
		super();
		this.bank = bank;
		this.rate = 0;
	}

	public void setBank(String bank)
	{
		this.bank = bank;
	}
	
	public void setRate(double rate)
	{
		this.rate = rate;
	}
	
	public String getBank()
	{
		return this.bank;
	}
	
	public double getRate()
	{
		return this.rate;
	}

	@Override
	public String toString()
	{
		return "MortgageBean [bank=" + bank + ", rate=" + rate + "]";
	}
	

}
