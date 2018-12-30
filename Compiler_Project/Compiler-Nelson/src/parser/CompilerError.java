package parser;
@SuppressWarnings("unused")

public class CompilerError extends Exception 
{
	/* Compiler Error is the super class extended by SyntaxError, SemanticError 
	 * and LexicalError which captures errors in the syntax analysis and 
	 * semantic analysis of an input file  */
	
	private static final long serialVersionUID = 1L;
	private static String ErrorMessage;
	private static CharacterStream CharStream = new CharacterStream(); 
	
	public CompilerError(String message) 
	{
		super(message);
		ErrorMessage = message;
	}
	
	public CompilerError(String message, CharacterStream cs) 
	{
		super(message);
		ErrorMessage = message;
	}
	
	/*  ReturnError() is overriden in SyntaxError, SemanticError and LexicalError. 
	 *  Returns errors with specific information about the type of error,
	 *  including the line number in the input file that the error
	 *  occurred on */
	
	public static CompilerError ReturnError() throws CompilerError 
	{
		throw new CompilerError(ErrorMessage);
	}
	
	/* Used to retrieve the CharStream object which keeps track of the
	 * line number an error occurs on */
	
	public static CharacterStream GetErrorStream() 
	{
		return CharStream;
	}

}
