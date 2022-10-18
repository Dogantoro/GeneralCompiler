package AST;

import Token.Token;
import Visitor.Evaluator;

public class Const implements ASTE {
	private Token constant;
	
	public Const(Token constant) {
		this.constant = constant;
	}
	
	public Token getToken() {
		return constant;
	}
	
	@Override
	public double accept(Evaluator visitor) {
		return visitor.visitConst(this);
	}
	

}
