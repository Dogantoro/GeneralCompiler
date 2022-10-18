import java.util.Scanner;

import Lexer.Lexer;
import Parser.Parser;
import Visitor.CodeVisitor;
import Visitor.Evaluator;

public class Runner {

	public static void main(String[] args) {
		Scanner userInput = new Scanner(System.in);
		
		//2 * 5 + 4 * F(8+2)
		//"2 * 5 + 4 * 1 + (8+2)"
		Lexer lexer = new Lexer(userInput.nextLine());
		
//		while (lexer.nextToken()) {
//			System.out.println(lexer.getToken());
//		}
		
		Parser parser = new Parser(lexer);
		
		CodeVisitor visitor = new CodeVisitor(parser);
		visitor.visit();
		
//		if (Math.abs(value - Math.round(value)) < 0.00001)
//			System.out.println((int) Math.round(value));
//		else
//			System.out.println(value);
		

	}

}
