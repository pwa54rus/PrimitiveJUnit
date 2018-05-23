package test;

import annotation.After;
import annotation.Before;
import annotation.Test;
import exception.Check;
import exception.DivisionException;
import main.Math;

public class Second{

	@Before
	public void before(){

		System.out.println("Test started");
	}



	@Test(expectedException = DivisionException.class)
	public void equalityOf11and7devidedBy1Test(){

		Check.checkEquality(Math.divisionWithCheck(7, 1),11);
	}

	@Test
	public void equalityOf3and3IntegersTest(){

		Check.checkEquality(new Integer(3), new Integer(3));
	}



	@After
	public void after(){

		System.out.println("Test finished\n");
	}
}