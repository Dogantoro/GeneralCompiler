package AST;

import Token.Token;
import Visitor.Visitor;

public class BiOP implements AST{
	private AST left, right;
	private Token op;
	public BiOP(AST left, Token op, AST right) {
		super();
		this.left = left;
		this.op = op;
		this.right = right;
	}
	public AST getLeft() {
		return left;
	}
	public AST getRight() {
		return right;
	}
	public Token getOp() {
		return op;
	}
	
	@Override
	public double accept(Visitor visitor) {
		return visitor.visitBiOP(this);
	}
	
	
}
