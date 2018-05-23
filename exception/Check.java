package exception;

public class Check extends RuntimeException{

	private String message;

	Check(String message){

		this.message = message;
	}

	@Override
	public String getMessage(){

		return message;
	}

	public static void checkEquality(Object expected, Object current){

		if  (!expected.equals(current)){

			throw new Check("Expected object is not equal current object");
		}
	}

	public static void checkEquality(double expected, double current){

		if  (expected!=current){

			throw new Check("Expected number is not equal current number");
		}
	}

}