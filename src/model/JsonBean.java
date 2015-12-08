package model;

public class JsonBean
{
	private String principle;
	private String interest;
	private String amort;
	
	
	private String status;
	private String payment;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPayment() {
		return payment;
	}
	public void setPayment(String payment) {
		this.payment = payment;
	}
	public String getPrinciple()
	{
		return principle;
	}
	public void setPrinciple(String principle)
	{
		this.principle = principle;
	}
	public String getInterest()
	{
		return interest;
	}
	public void setInterest(String interest)
	{
		this.interest = interest;
	}
	public String getAmort()
	{
		return amort;
	}
	public void setAmort(String amort)
	{
		this.amort = amort;
	}
	

}
