package AST;

import Token.Token;
import Visitor.Evaluator;

public class UnaryOP implements ASTE{
	private ASTE node;
	private Token op;
	
	public UnaryOP(Token op, ASTE node) {
		this.op = op;
		this.node = node;
	}

	public ASTE getNode() {
		return node;
	}

	public Token getOp() {
		return op;
	}
	
	@Override
	public double accept(Evaluator visitor) {
		return visitor.visitUnaryOP(this);
	}
}
