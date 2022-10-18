package AST;

import Token.Token;
import Visitor.Evaluator;

public class Real implements ASTE{
	private Token num;
	
	public Real(Token num) {
		this.num = num;
	}

	public Token getNum() {
		return num;
	}
	
	@Override
	public double accept(Evaluator visitor) {
		return visitor.visitReal(this);
	}

}
