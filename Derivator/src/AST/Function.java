package AST;

import Token.Token;
import Visitor.Evaluator;

public class Function implements ASTE{

	private Token function;
	private ASTE param;
	
	public Token getFunction() {
		return function;
	}

	public ASTE getParam() {
		return param;
	}

	public Function(Token function, ASTE param) {
		this.function = function;
		this.param = param;
	}
	
	@Override
	public double accept(Evaluator visitor) {
		return visitor.visitFunc(this);
	}

}
