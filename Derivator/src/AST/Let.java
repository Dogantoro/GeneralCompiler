package AST;

import Visitor.CodeVisitor;

public class Let implements ASTC {
	private String ID;
	private ASTE value;
	
	public Let(String ID, ASTE value) {
		this.ID = ID;
		this.value = value;
	}
	
	public String getID() {
		return ID;
	}
	
	public ASTE value() {
		return value;
	}
	
	@Override
	public void accept(CodeVisitor visitor) {
		visitor.visitLet(this);
		//System.err.println("BROSKI! you need to call the visitor for " + ID);
	}

}
