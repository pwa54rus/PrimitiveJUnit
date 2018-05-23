package exception;

public class DivisionException extends RuntimeException{

	@Override
	public String getMessage(){

		return "Division by zero happened";
	}
}