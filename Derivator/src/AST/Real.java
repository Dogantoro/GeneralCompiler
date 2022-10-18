package AST;

import Token.Token;
import Visitor.Visitor;

public class Real implements AST{
	private Token num;
	
	public Real(Token num) {
		this.num = num;
	}

	public Token getNum() {
		return num;
	}
	
	@Override
	public double accept(Visitor visitor) {
		return visitor.visitReal(this);
	}

}
