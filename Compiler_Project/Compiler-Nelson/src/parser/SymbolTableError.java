package parser;

public class SymbolTableError extends CompilerError {

	private static final long serialVersionUID = 1L;

	public SymbolTableError(String message, CharacterStream cs) 
	{
		super(message, cs);
	}
	
	public SymbolTableError(String message) 
	{
		super(message);
	}
	
	public SymbolTableError ReturnError(int lineNum) throws SymbolTableError {
		throw new SymbolTableError("SYMBOL TABLE ERROR: ENTRY ALREADY EXISTS" + lineNum);
	}

}
