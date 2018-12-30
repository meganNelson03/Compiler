package parser;

public class LexicalError extends CompilerError 
{
	/* Lexical errors are a type of compiler error that occur during
	 * the tokenizing process. There are three types of lexical error:
	 * ill-formed comments, ill formed constant, invalid characters, 
	 * max identifier size, and max constant size reached.
	 * Note: The LexicalError class references the line number that
	 * the error occurred by through access to the CharStream object
	 * passed to the Tokenizer */
	
	private static final long serialVersionUID = 1L;
	
	public LexicalError(String message) 
	{
		super(message);
	}
	
	public static LexicalError ReturnError(int errorNum, int lineNum) throws LexicalError 
	{
		switch(errorNum) 
		{
			case 0: throw new LexicalError("LEXICAL ERROR: ILL-FORMED COMMENT AT LINE " + lineNum);
			case 1: throw new LexicalError("LEXICAL ERROR: INVALID CHARACTER AT LINE " + lineNum);
			case 2: throw new LexicalError("LEXICAL ERROR: MAX IDENTIFIER LENGTH REACHED AT LINE " + lineNum);
			case 3: throw new LexicalError("LEXICAL ERROR: MAX CONSTANT SIZE REACHED AT LINE " + lineNum);
			default: return null;
		}
	}	
}
