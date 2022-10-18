package AST;

import Token.Token;
import Visitor.Visitor;

public class Function implements AST{

	private Token function;
	private AST param;
	
	public Token getFunction() {
		return function;
	}

	public AST getParam() {
		return param;
	}

	public Function(Token function, AST param) {
		this.function = function;
		this.param = param;
	}
	
	@Override
	public double accept(Visitor visitor) {
		return visitor.visitFunc(this);
	}

}
