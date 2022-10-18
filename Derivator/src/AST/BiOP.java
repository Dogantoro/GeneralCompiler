package AST;

import Token.Token;
import Visitor.Evaluator;

public class BiOP implements ASTE{
	private ASTE left, right;
	private Token op;
	public BiOP(ASTE left, Token op, ASTE right) {
		super();
		this.left = left;
		this.op = op;
		this.right = right;
	}
	public ASTE getLeft() {
		return left;
	}
	public ASTE getRight() {
		return right;
	}
	public Token getOp() {
		return op;
	}
	
	@Override
	public double accept(Evaluator visitor) {
		return visitor.visitBiOP(this);
	}
	
	
}
