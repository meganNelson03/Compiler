package parser;
import java.util.ArrayList;

public class GrammarSymbolList 
{	
	/* Grammar Symbol List contains two lists, one with all viable token types 
	 * and the other with all non-terminals in the grammar. Grammar symbol list
	 * is referenced by the Parser while productions are being matched
	 * on the Parse Stack */
	
	private ArrayList<TokenType> TokenList;
	private ArrayList<NonTerminal> NonTermList;

	public GrammarSymbolList() 
	{
		TokenList = new ArrayList<TokenType>();
		NonTermList = new ArrayList<NonTerminal>();	
	}
	
	/* List of tokens (key matches row index of parseTable)	*/
	
	public void TokenList() 
	{	
		TokenList.add(0, TokenType.PROGRAM);
		TokenList.add(1, TokenType.BEGIN);
		TokenList.add(2, TokenType.END);
		TokenList.add(3, TokenType.VAR);
		TokenList.add(4, TokenType.FUNCTION);
		TokenList.add(5, TokenType.PROCEDURE);
		TokenList.add(6, TokenType.RESULT);
		TokenList.add(7, TokenType.INTEGER);
		TokenList.add(8, TokenType.REAL);
		TokenList.add(9, TokenType.ARRAY);
		TokenList.add(10, TokenType.OF);
		TokenList.add(11, TokenType.IF);
		TokenList.add(12, TokenType.THEN);
		TokenList.add(13, TokenType.ELSE);
		TokenList.add(14, TokenType.WHILE);
		TokenList.add(15, TokenType.DO);
		TokenList.add(16, TokenType.NOT);
		TokenList.add(17, TokenType.IDENTIFIER);
		TokenList.add(18, TokenType.INTCONSTANT);
		TokenList.add(19, TokenType.REALCONSTANT);
		TokenList.add(20, TokenType.RELOP);
		TokenList.add(21, TokenType.MULOP);
		TokenList.add(22, TokenType.ADDOP);
		TokenList.add(23, TokenType.ASSIGNOP);
		TokenList.add(24, TokenType.COMMA);
		TokenList.add(25, TokenType.SEMICOLON);
		TokenList.add(26, TokenType.COLON);
		TokenList.add(27, TokenType.LEFTPAREN);
		TokenList.add(28, TokenType.RIGHTPAREN);
		TokenList.add(29, TokenType.LEFTBRACKET);
		TokenList.add(30, TokenType.RIGHTBRACKET);
		TokenList.add(31, TokenType.UNARYMINUS);
		TokenList.add(32, TokenType.UNARYPLUS);
		TokenList.add(33, TokenType.DOUBLEDOT);
		TokenList.add(34, TokenType.ENDMARKER);
	}
	
	/* List of nonTerminal production (key matches column index of parseTable) */
	public void NonTermList() 
	{
		NonTermList.add(0, NonTerminal.program);
		NonTermList.add(1, NonTerminal.identifierList);
		NonTermList.add(2, NonTerminal.declarations);
		NonTermList.add(3, NonTerminal.subDeclarations);
		NonTermList.add(4, NonTerminal.compoundStatement);
		NonTermList.add(5, NonTerminal.identifierListTail);
		NonTermList.add(6, NonTerminal.declarationList);
		NonTermList.add(7, NonTerminal.type);
		NonTermList.add(8, NonTerminal.declarationListTail);
		NonTermList.add(9, NonTerminal.standardType);
		NonTermList.add(10, NonTerminal.arrayType);
		NonTermList.add(11, NonTerminal.subprogramDeclaration);
		NonTermList.add(12, NonTerminal.subprogramHead);
		NonTermList.add(13, NonTerminal.arguments);
		NonTermList.add(14, NonTerminal.parameterList);
		NonTermList.add(15, NonTerminal.parameterListTail);
		NonTermList.add(16, NonTerminal.statementList);
		NonTermList.add(17, NonTerminal.statement);
		NonTermList.add(18, NonTerminal.statementListTail);
		NonTermList.add(19, NonTerminal.elementaryStatement);
		NonTermList.add(20, NonTerminal.expression);
		NonTermList.add(21, NonTerminal.elseClause);
		NonTermList.add(22, NonTerminal.esTail);
		NonTermList.add(23, NonTerminal.subscript);
		NonTermList.add(24, NonTerminal.parameters);
		NonTermList.add(25, NonTerminal.expressionList);
		NonTermList.add(26, NonTerminal.expressionListTail);
		NonTermList.add(27, NonTerminal.simpleExpression);
		NonTermList.add(28, NonTerminal.expressionTail);
		NonTermList.add(29, NonTerminal.term);
		NonTermList.add(30, NonTerminal.simpleExpressionTail);
		NonTermList.add(31, NonTerminal.sign);
		NonTermList.add(32, NonTerminal.factor);
		NonTermList.add(33, NonTerminal.termTail);
		NonTermList.add(34, NonTerminal.factorTail);
		NonTermList.add(35, NonTerminal.actualParameters);
		NonTermList.add(36, NonTerminal.goal);
		NonTermList.add(37, NonTerminal.constant);
	}
	
	public ArrayList<TokenType> GetTokenList()
	{
		return TokenList;
	}
	
	public ArrayList<NonTerminal> GetNonTermList()
	{
		return NonTermList;
	}
}
