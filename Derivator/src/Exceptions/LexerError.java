package Exceptions;

public class LexerError extends RuntimeException{
	private static final long serialVersionUID = 423244994058912827L;	
	
	public LexerError() {
		super("Lexer Error Occured");
	}
	
	public LexerError(String msg) {
		super("Lexer Error Occured: " + msg);
	}
	

}
