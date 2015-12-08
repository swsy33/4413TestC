package model;

import java.util.List;

public class OffersPod
{
	private String validity;
	public String getValidity() {
		return validity;
	}

	public void setValidity(String validity) {
		this.validity = validity;
	}

	public List<MortgageBean> getOffers() {
		return offers;
	}

	public void setOffers(List<MortgageBean> offers) {
		this.offers = offers;
	}

	private List<MortgageBean> offers;

	public OffersPod(String validity, List<MortgageBean> offers)
	{
		this.validity = validity;
		this.offers = offers;
	}
	
}
