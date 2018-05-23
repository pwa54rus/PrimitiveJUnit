package main;

import exception.DivisionException;

public class Math{

	public static double divisionWithCheck(double x, double y){

		if (y == 0.0) {
			throw new DivisionException();
		}

		return x/y;
	}
}