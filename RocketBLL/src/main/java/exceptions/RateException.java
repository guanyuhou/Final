package exceptions;

import rocketDomain.RateDomainModel;

public class RateException extends Exception {

	//	TODO - RocketBLL RateException - RateDomainModel should be an attribute of RateException
	//	* Add RateRomainModel as an attribute
	//	* Create a constructor, passing in RateDomainModel
	//	* Create a getter (no setter, set value only in Constructor)

	private RateDomainModel rdm;
	
	public RateException(RateDomainModel rdm)
	{
		this.rdm = rdm;
	}

	public RateException() {
	}

	public RateDomainModel getrdm()
	{
		return this.rdm;
	}
}
