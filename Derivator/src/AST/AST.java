package AST;

import Visitor.Visitor;

public interface AST {
	public double accept(Visitor visitor);
}
