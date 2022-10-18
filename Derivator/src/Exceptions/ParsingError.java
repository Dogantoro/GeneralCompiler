package Exceptions;

public class ParsingError extends RuntimeException{
	private static final long serialVersionUID = -3965740454560376192L;

	public ParsingError() {
		super("Parsing Error Occured");
	}
	
	public ParsingError(String msg) {
		super("Parsing Error Occured: " + msg);
	}
	

}
