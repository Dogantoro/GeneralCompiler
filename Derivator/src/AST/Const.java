package AST;

import Token.Token;
import Visitor.Visitor;

public class Const implements AST {
	private Token constant;
	
	public Const(Token constant) {
		this.constant = constant;
	}
	
	public Token getToken() {
		return constant;
	}
	
	@Override
	public double accept(Visitor visitor) {
		return visitor.visitConst(this);
	}
	

}
