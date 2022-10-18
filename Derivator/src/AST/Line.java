package AST;

import java.util.Queue;

import Visitor.CodeVisitor;
import Visitor.Evaluator;
import Visitor.Visitor;

public class Line implements ASTC{
	private AST thisLine, prevLine;
	private LineType type;
	
	public Line(AST thisLine, AST nextLine, LineType type) {
		this.thisLine = thisLine;
		this.prevLine = nextLine;
		this.type     = type;
	}

	public AST getThisLine() {
		return thisLine;
	}

	public AST getPrevLine() {
		return prevLine;
	}
	
	public LineType getType() {
		return type;
	}

	@Override
	public void accept(CodeVisitor visitor) {
		visitor.visitLine(this);
	}

}
