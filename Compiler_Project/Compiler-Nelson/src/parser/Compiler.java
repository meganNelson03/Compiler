package parser;
import java.io.IOException;

public class Compiler 
{
	/* The compiler object sets up the necessary subsystems to generate TVI code:
	 * CharStream takes in a file, LexicalAnalyzer takes in a charstream, and the 
	 * Parser takes in the Semantic Actions and Lexical Analyzer objects. 
	 */
	
	private Tokenizer LexicalAnalyzer;
	private Parser Parse;
	private SemanticAction SemAct;
	private CharacterStream CharStream;
	private String File = "/Users/megannelson/331-COMPILER/Compiler331-CompleteCompiler-Nelson/src/parser/semanticTest.txt";
	
	/* TO TEST LEXER: Use main function in the Tokenizer class
	 * TO TEST PARSER: Use main function in the Parser class.
	 * Note: you may change the lexFile in zero-argument Parser() constructor to test
	 * 	     different file. Both the Lexer and the Parser run the ultimateTest.pas from 
	 *       their respective project descriptions sample files. Compiler class tests the
	 *       while.pas. 
	 */

	public Compiler() throws IOException, CompilerError 
	{
		CharStream = new CharacterStream(File);
		LexicalAnalyzer = new Tokenizer(CharStream);
		SemAct = new SemanticAction();
		Parse = new Parser(SemAct, LexicalAnalyzer);
	}
	
	public void Compile() throws IOException, CompilerError 
	{
		Parse.Parse();
	}
	
}
