package parser;

public enum TokenType implements GrammarSymbol
{
	/* Token Type is a type of grammar symbol in Vascal's grammar and
	 * represents the terminal productions in the context free grammar */
	
	PROGRAM, BEGIN, END, VAR, FUNCTION, PROCEDURE,
	RESULT, INTEGER, REAL, ARRAY, OF, IDENTIFIER, IF,
	THEN, ELSE, WHILE, DO, NOT, INTCONSTANT,
	REALCONSTANT, RELOP, MULOP, ADDOP, ASSIGNOP,
	COMMA, SEMICOLON, COLON, LEFTPAREN, RIGHTPAREN,
	DOUBLEDOT, LEFTBRACKET, RIGHTBRACKET, UNARYMINUS,
	UNARYPLUS, ENDMARKER, ENDOFFILE;
	
	public boolean IsToken() 
	{
		return true;
	}
	
	public boolean IsNonTerminal() 
	{
		return false;
	}

	public boolean IsAction() 
	{
		return false;
	}
}
