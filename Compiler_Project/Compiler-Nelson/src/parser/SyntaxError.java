package parser;

public class SyntaxError extends CompilerError
{	
	private static final long serialVersionUID = 1L;
	
	public SyntaxError(String message) 
	{
			super(message);
	}
	
	public static SyntaxError ReturnError(int errorNum, int lineNum, TokenType type) throws SyntaxError 
	{
		switch (errorNum) 
		{
		case 0: throw new SyntaxError("PARSE ERROR AT LINE: BAD TOKEN AT " + lineNum + " WITH TERMINAL " + type);
		case 1: throw new SyntaxError("PARSE ERROR AT LINE: BAD PRODUCTION AT " + lineNum + " WITH TERMINAL " + type);
		default: return null;
		}
	}
		
}
