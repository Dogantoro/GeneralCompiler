package AST;

import Visitor.CodeVisitor;

//AST for Code
public interface ASTC extends AST{
	public void accept(CodeVisitor visitor);
}
