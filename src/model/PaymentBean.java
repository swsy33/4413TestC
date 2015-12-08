package model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="payPod")
public class PaymentBean
{
	@XmlAttribute
	private boolean status;
	@XmlElement
	private double payment;
	@XmlElement
	private String msg;
	
	public PaymentBean()
	{
		super();
	}

	public PaymentBean(boolean status, double payment, String msg)
	{
		this.status = status;
		this.payment = payment;
		this.msg = msg;
	}
}