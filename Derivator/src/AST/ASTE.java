package AST;

import Visitor.Evaluator;
import Visitor.Visitor;

//AST for Expressions
public interface ASTE extends AST{
	public double accept(Evaluator visitor);
}
