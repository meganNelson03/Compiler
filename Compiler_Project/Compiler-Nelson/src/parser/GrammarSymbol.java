package parser;

public interface GrammarSymbol 
{
	/* There are three types of symbols in the Vascal grammar: token, non-terminals,
	 * and executable actions. Grammar Symbols are extended by these enum types. */
	
	boolean IsToken();
	boolean IsNonTerminal();
	boolean IsAction();	
}
