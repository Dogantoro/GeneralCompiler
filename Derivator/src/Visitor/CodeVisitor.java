package Visitor;

import AST.ASTC;
import AST.ASTE;
import AST.Line;
import Parser.Parser;

public class CodeVisitor implements Visitor{

	private Parser parser;
	private Evaluator eval;
	
	public CodeVisitor (Parser parser) {
		this.parser = parser;
		eval = new Evaluator();
	}
	
	public void visit(ASTC node) {
		node.accept(this);
	}

	public void visit() {
		visit((ASTC) parser.parse());
	}
	
	public void visitLine(Line line) {
		
		if (line.getPrevLine() != null) {
			visit((ASTC) line.getPrevLine());
		}
		
		switch (line.getType()) {
			case DISP:
				{
					double value = eval.visit((ASTE) line.getThisLine());
					if (Math.abs(value - Math.round(value)) < 0.00001)
						System.out.println((int) Math.round(value));
					else
						System.out.println(value);
				} break;
		}
	}
}
