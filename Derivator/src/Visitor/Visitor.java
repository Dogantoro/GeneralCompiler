package Visitor;

import java.util.HashMap;

import AST.AST;
import AST.BiOP;
import AST.Const;
import AST.Function;
import AST.Real;
import AST.UnaryOP;
import Exceptions.ParsingError;
import Parser.Parser;
import Token.TokenType;

public class Visitor {
	private Parser parser;
	private HashMap<TokenType, biop> biopMap;
	private HashMap<String, function> funcMap;
	private HashMap<String, Double> constMap;

	public Visitor(Parser parser) {
		this.parser = parser;
		
		biopMap = new HashMap<>();
		biopMap.put(TokenType.ADD, (a,b) -> a + b);
		biopMap.put(TokenType.SUB, (a,b) -> a - b);
		biopMap.put(TokenType.MUL, (a,b) -> a * b);
		biopMap.put(TokenType.DIV, (a,b) -> a / b);
		biopMap.put(TokenType.EXP, (a,b) -> Math.pow(a, b));
		
		funcMap = new HashMap<>();
		funcMap.put("abs", x -> Math.abs(x));
		funcMap.put("sgn", x -> Math.signum(x));
		funcMap.put("sqrt", x -> Math.sqrt(x));
		funcMap.put("cbrt", x -> Math.cbrt(x));
		funcMap.put("factorial", x -> Math.exp(logGamma(x)));
		funcMap.put("exp", x -> Math.exp(x));
		funcMap.put("log", x -> Math.log10(x));
		funcMap.put("ln", x -> Math.log(x));
		
		//trig
		funcMap.put("sin", x -> Math.sin(x));
		funcMap.put("cos", x -> Math.cos(x));
		funcMap.put("tan", x -> Math.tan(x));
		funcMap.put("arcsin", x -> Math.asin(x));
		funcMap.put("arccos", x -> Math.acos(x));
		funcMap.put("arctan", x -> Math.atan(x));
		funcMap.put("sinh", x -> Math.sinh(x));
		funcMap.put("cosh", x -> Math.cosh(x));
		funcMap.put("tanh", x -> Math.tanh(x));
		
		constMap = new HashMap<>();
		constMap.put("pi" , Math.PI);
		constMap.put("e"  , Math.E);
		constMap.put("phi", 1.618033988749894);
		
	}

	public double visit(AST node) {
		return node.accept(this);
	}

	public double visit() {
		return visit(parser.parse());
	}

	public double visitBiOP(BiOP node) {
		return biopMap.get(node.getOp().getType()).calculate(visit(node.getLeft()), visit(node.getRight()));
	}

	public double visitUnaryOP(UnaryOP node) {
		if (node.getOp().getType() == TokenType.ADD)
			return visit(node.getNode());
		return -visit(node.getNode());
	}

	public double visitReal(Real node) {
		return Double.parseDouble(node.getNum().getValue());
	}
	
	public double visitFunc(Function node) {
		if (funcMap.containsKey(node.getFunction().getValue()))
			return funcMap.get(node.getFunction().getValue()).run(visit(node.getParam()));
		throw new ParsingError("Unknown Function \"" + node.getFunction().getValue() + "\"");
	}
	
	public double visitConst(Const node) {
		if (constMap.containsKey(node.getToken().getValue()))
			return constMap.get(node.getToken().getValue());
		throw new ParsingError("Unknown Constant \"" + node.getToken().getValue() + "\"");
	}

	@FunctionalInterface
	interface biop {
		double calculate(double a, double b);
	}

	@FunctionalInterface
	interface function {
		double run(double x);
	}

	static double logGamma(double x) {
		double tmp = (x - 0.5) * Math.log(x + 4.5) - (x + 4.5);
		double ser = 1.0 + 76.18009173 / (x + 0) - 86.50532033 / (x + 1) + 24.01409822 / (x + 2) - 1.231739516 / (x + 3)
				+ 0.00120858003 / (x + 4) - 0.00000536382 / (x + 5);
		return tmp + Math.log(ser * Math.sqrt(2 * Math.PI));
	}
}
