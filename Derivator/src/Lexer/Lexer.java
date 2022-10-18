package Lexer;

import java.util.HashMap;
import java.util.Set;

import Exceptions.LexerError;
import Token.*;

public class Lexer {
	private String code;
	private int index;
	private Token currentToken, lastToken;
	private int codeLength;
	
	private Set<Character> numerical, whitespace;
	private HashMap<Character, TokenType> symbols;
	
	public Lexer(String code) {
		this.code = code;
		index = 0;
		codeLength = code.length();
		
		//Character sets
		numerical = Set.of('0','1','2','3','4','5','6','7','8','9','.');
		whitespace = Set.of(' ', '\n', '\t', '\r');
		
		//Symbol mapping
		symbols = new HashMap<>();
		symbols.put('+', TokenType.ADD);
		symbols.put('-', TokenType.SUB);
		symbols.put('*', TokenType.MUL);
		symbols.put('/', TokenType.DIV);
		symbols.put('^', TokenType.EXP);
		symbols.put('(', TokenType.LPAR);
		symbols.put(')', TokenType.RPAR);
		symbols.put(';', TokenType.ENDL);
	}
	
	public boolean nextToken() {
		
		if (EOC())
			return false;
		
		char Char = code.charAt(index);
		lastToken = currentToken;
		
		//Whitespace
		if (whitespace.contains(Char)) {
			ws();
			return nextToken();
		}
		
		//Decimal
		if (numerical.contains(Char)) {
			currentToken = new Token(TokenType.DECIMAL, readNum());
			return true;
		}
		
		//Symbols
		if (symbols.containsKey(Char)) {
			currentToken = new Token(symbols.get(Char));
			index++;
			return true;
		}
		
		//Functions
		if (Character.isAlphabetic(Char)) {
			String ID = readString();
			if (!EOC() && code.charAt(index)=='(') {
				currentToken = new Token(TokenType.FUNC, ID);
			} else {
				currentToken = new Token(TokenType.CONST, ID);
			}
			
			return true;
		}
		
		return false;
	}
	
	public Token getPrev() {
		return lastToken;
	}
	
	public Token getToken() {
		return currentToken;
	}
	
	public TokenType getTokenType() {
		return currentToken.getType();
	}
	
	//End of Code
	public boolean EOC() {
		return index == codeLength;
	}
	
	private void ws() {
		while (!EOC() && whitespace.contains(code.charAt(index)))
			index++;
	}
	
	private String readString() {
		String out = "";
		while (!EOC() && Character.isAlphabetic(code.charAt(index))) {
			out += code.charAt(index);
			index++;
		}
		//throw new LexerError("Functions must be in form: functionName(Parameter)");
		return out;
	}
	
	private String readNum() {
		String num = "";
		while (!EOC() && numerical.contains(code.charAt(index))) {
			num += code.charAt(index);
			index++;
		}
		
		//Check if the String is a correctly formatted number
		try {
			Double.parseDouble(num);
		} catch (NumberFormatException E) {
			throw new LexerError("\"" + num + "\" is not a valid number\nMake sure that there is only one decimal point and that your number is in the range 1.7e-308 to 1.7e+308");
		}
		
		return num;
	}
	
	public int getIndex() {
		return index;
	}
	
	public String getCode() {
		return code;
	}
	

}
