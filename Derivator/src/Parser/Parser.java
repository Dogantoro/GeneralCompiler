package Parser;

import AST.*;
import Exceptions.ParsingError;
import Lexer.Lexer;
import Token.Token;
import Token.TokenType;

public class Parser {
	Lexer lexer;
	
	public Parser(Lexer lexer) {
		this.lexer = lexer;
	}
	
	private void next(TokenType type) {
		if (lexer.getToken().getType().equals(type)) {
			lexer.nextToken();
		} else {
			//I don't really know why this is needed but I used it in an earlier project
			//so it must have a purpose.
			//throw new ParsingError("Uh... this error isn't supposed to happen, idk how you got here");
			
			//IT HAS A PURPOSE NOW!
			String corrected = lexer.getCode().substring(Math.max(0, lexer.getIndex() - 8), lexer.getIndex());
			corrected += ")";
			corrected += lexer.getCode().substring(lexer.getIndex(), Math.min(lexer.getIndex() + 5, lexer.getCode().length()));
			throw new ParsingError("Parenthesis are supposed to close like this: " + corrected);
		}
	}
	
	public AST factor() {
		TokenType type = lexer.getTokenType();
		if (type == TokenType.DECIMAL) {
			Real out = new Real(lexer.getToken());
			next(TokenType.DECIMAL);
			return out;
		} else if (type == TokenType.ADD) {
			next(TokenType.ADD);
			return new UnaryOP(lexer.getPrev(), factor());
		} else if (type == TokenType.SUB) {
			next(TokenType.SUB);
			return new UnaryOP(lexer.getPrev(), factor());
		} else if (type == TokenType.LPAR) {
			next(TokenType.LPAR);
			AST out = add_sub();
			next(TokenType.RPAR);
			return out;
		} else if (type == TokenType.FUNC) {
			Token func = lexer.getToken();
			next(TokenType.FUNC);
			AST param = factor();
			return new Function(func, param);
		} else if (type == TokenType.CONST) {
			Token constant = lexer.getToken();
			next(TokenType.CONST);
			return new Const(constant);
		}
		
		throw new ParsingError("Unsupported factor");
	}
	
	public AST exp() {
		AST out = factor();
		while (lexer.getTokenType() == TokenType.EXP) {
			Token token = lexer.getToken();
			next(TokenType.EXP);
			out = new BiOP(out, token, factor());
		}
		return out;
	}
	
	public AST mul_div() {
		AST out = exp();
		while (lexer.getTokenType() == TokenType.MUL || lexer.getTokenType() == TokenType.DIV) {
			Token token = lexer.getToken();
			if (lexer.getTokenType() == TokenType.MUL)
				next(TokenType.MUL);
			else
				next(TokenType.DIV);
			out = new BiOP(out, token, exp());
		}
		return out;
	}
	
	public AST add_sub() {
		AST out = mul_div();
		while (lexer.getTokenType() == TokenType.ADD || lexer.getTokenType() == TokenType.SUB) {
			Token token = lexer.getToken();
			if (lexer.getTokenType() == TokenType.ADD)
				next(TokenType.ADD);
			else
				next(TokenType.SUB);
			out = new BiOP(out, token, mul_div());
		}
		return out;
	}
	
	public AST parse() {
		lexer.nextToken();
		return add_sub();
	}
}
