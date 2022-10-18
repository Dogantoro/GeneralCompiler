package AST;

import Token.Token;
import Visitor.Visitor;

public class UnaryOP implements AST{
	private AST node;
	private Token op;
	
	public UnaryOP(Token op, AST node) {
		this.op = op;
		this.node = node;
	}

	public AST getNode() {
		return node;
	}

	public Token getOp() {
		return op;
	}
	
	@Override
	public double accept(Visitor visitor) {
		return visitor.visitUnaryOP(this);
	}
}
