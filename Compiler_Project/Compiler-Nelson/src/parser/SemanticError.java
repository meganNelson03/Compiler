package parser;

@SuppressWarnings("unused")
public class SemanticError extends CompilerError
{
	/* Semantic Error is a subclass of Compiler Error which captures 
	 * errors in the semantic phase when analyzing the meaning of the program.
	 * Types of errors include: type mismatch (real and int), undeclared variable error,
	 * wrong number of types of parameters, illegal procedures, and wrong use of
	 * identifier as a function.   */
	
	private static final long serialVersionUID = 1L;
	private static CharacterStream Cs;
	private String Message;
	
	public SemanticError(String message)
	{
		super(message, Cs);
		this.Message = message;
	}
	
	public static SemanticError ReturnError(int errorNum, int lineNumber) throws SemanticError 
	{
		switch(errorNum) 
		{
			case 0: throw new SemanticError("SEMANTIC ERROR AT LINE " + lineNumber + " : MISMATCH ERROR");
			case 1: return new SemanticError("SEMANTIC ERROR AT LINE " + lineNumber + ": UNDECLARED VAR ERROR");
			case 2: return new SemanticError("SEMANTIC ERROR AT LINE " + lineNumber + ": BAD PARAMETER");
			case 3: return new SemanticError("SEMANTIC ERROR AT LINE " + lineNumber + ": E-TYPE MISMATCH ERROR");
			case 4: return new SemanticError("SEMANTIC ERROR AT LINE " + lineNumber + ": ID MISMATCH ERROR");
			case 5: return new SemanticError("SEMANTIC ERROR AT LINE " + lineNumber + ": ILLEGAL PROCEDURE");
			case 6: return new SemanticError("SEMANTIC ERROR AT LINE " + lineNumber + ": ID TYPE IS NOT ARRAY");
			case 7: return new SemanticError("SEMANTIC ERROR AT LINE " + lineNumber + ": WRONG NUMBER OF PARAMS");
			case 8: return new SemanticError("SEMANTIC ERROR AT LINE " + lineNumber + ": ID IS NOT A FUNCTION");
			case 9: return new SemanticError("SEMANTIC ERROR AT LINE " + lineNumber + ": INVALID ACTION NUMBER");
			default: return null;
		}
	}

}
