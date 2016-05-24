package rocketBase;

import static org.junit.Assert.*;

import org.junit.Test;

import exceptions.RateException;

public class rate_test {

	//TODO - RocketBLL rate_test
	//		Check to see if a known credit score returns a known interest rate
	
	//TODO - RocketBLL rate_test
	//		Check to see if a RateException is thrown if there are no rates for a given
	//		credit score
	@Test
	public void test() throws RateException{

		RateBLL.getRate(0);
		RateBLL.getRate(999);
		
	}

	public void test2() throws RateException
	{
		assertTrue(RateBLL.getRate(600) == 5);
		assertTrue(RateBLL.getRate(700) == 4);
		assertTrue(RateBLL.getRate(800) == 3.5);
	}
}
