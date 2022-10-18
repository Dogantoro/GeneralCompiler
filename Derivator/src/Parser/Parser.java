package Parser;

import java.util.Queue;

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
	
	private boolean next(TokenType type) {
		if (lexer.getToken().getType().equals(type)) {
			lexer.nextToken();
			return true;
		}
		return false;
	}
	
	public ASTE factor() {
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
			ASTE out = add_sub();
			if (!next(TokenType.RPAR)) {
				String corrected = (lexer.getIndex() - 1> 8 ? "..." : "");
				corrected += lexer.getCode().substring(Math.max(0, lexer.getIndex() - 1 - 8), lexer.getIndex() - 1);
				corrected += ")";
				corrected += lexer.getCode().substring(lexer.getIndex() - 1, Math.min(lexer.getIndex() - 1 + 5, lexer.getCode().length()));
				corrected += (lexer.getIndex() - 1 + 5 < lexer.getCode().length()? "..." : "");
				throw new ParsingError("Parenthesis are supposed to close like this: " + corrected);
			}
			return out;
		} else if (type == TokenType.FUNC) {
			Token func = lexer.getToken();
			next(TokenType.FUNC);
			ASTE param = factor();
			return new Function(func, param);
		} else if (type == TokenType.CONST) {
			Token constant = lexer.getToken();
			next(TokenType.CONST);
			return new Const(constant);
		}
		
		throw new ParsingError("Unsupported factor \"" + type + "\"");
	}
	
	public ASTE exp() {
		ASTE out = factor();
		while (lexer.getTokenType() == TokenType.EXP) {
			Token token = lexer.getToken();
			next(TokenType.EXP);
			out = new BiOP(out, token, factor());
		}
		return out;
	}
	
	public ASTE mul_div() {
		ASTE out = exp();
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
	
	public ASTE add_sub() {
		ASTE out = mul_div();
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
	
	public AST line() {
		ASTC code = null;//new Line(add_sub(), null);
		while (!lexer.EOC()) {
			code = new Line(add_sub(), code, LineType.DISP);
			if (!next(TokenType.ENDL)) {
				String corrected = (lexer.getIndex() > 8 ? "..." : "");
				corrected += lexer.getCode().substring(Math.max(0, lexer.getIndex() - 8), lexer.getIndex()) + ";";
				throw new ParsingError("End lines with semicolons, like this: " + corrected);
			}
		}
		return code;
	}
	
	public AST parse() {
		lexer.nextToken();
		return line();
	}
}
