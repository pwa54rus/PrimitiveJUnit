package test;
import annotation.After;
import annotation.Before;
import annotation.Test;
import exception.Check;
import exception.DivisionException;
import main.Math;

public class First
{
	@Before
	public void before(){

		System.out.println("Test started");
	}

	@Test
	public void equalityOf7and15Test(){

		Check.checkEquality(7, 15);
	}

	@Test(expectedException = DivisionException.class)
	public void equalityOf3and7devidedByZeroTest(){

		Check.checkEquality(Math.divisionWithCheck(7, 0),3);
	}

	@Test
	public void equalityOf3and3Test(){

		Check.checkEquality(3, 3);
	}

	@After
	public void after(){

		System.out.println("Test finished\n");
	}
}