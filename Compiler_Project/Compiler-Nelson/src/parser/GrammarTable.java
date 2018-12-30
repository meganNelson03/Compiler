package parser;

public class GrammarTable extends Exception
{
	/* Contains a 2D Array with all the productions in the grammar:
	 * the Context Free Grammar of Vascal. Populated with token types (terminals),
	 * action numbers, and non terminals which are popped off as the input file
	 * is syntactically and semantically analyzed. */
	
	private static final long serialVersionUID = 1L;
	GrammarSymbol[][] grammarTable;
	
	public GrammarTable()
	{
		grammarTable = new GrammarSymbol[][] 
			 {
	/*0: */	 {},
	/*1: */	 {TokenType.PROGRAM, TokenType.IDENTIFIER, ActionNumber.num13, TokenType.LEFTPAREN, NonTerminal.identifierList, TokenType.RIGHTPAREN, ActionNumber.num9, TokenType.SEMICOLON, NonTerminal.declarations, NonTerminal.subDeclarations, ActionNumber.num56, NonTerminal.compoundStatement, ActionNumber.num55},
	/*2: */	 {TokenType.IDENTIFIER, ActionNumber.num13, NonTerminal.identifierListTail},
	/*3: */	 {TokenType.COMMA, TokenType.IDENTIFIER, ActionNumber.num13, NonTerminal.identifierListTail},
	/*4: */	 {}, 
	/*5: */	 {TokenType.VAR, ActionNumber.num1, NonTerminal.declarationList, ActionNumber.num2},
	/*6: */	 {},
	/*7: */	 {NonTerminal.identifierList, TokenType.COLON, NonTerminal.type, ActionNumber.num3, TokenType.SEMICOLON, NonTerminal.declarationListTail},
	/*8: */  {NonTerminal.identifierList, TokenType.COLON, NonTerminal.type, ActionNumber.num3, TokenType.SEMICOLON, NonTerminal.declarationListTail},
	/*9: */  {},
	/*10: */ {NonTerminal.standardType},
	/*11: */ {NonTerminal.arrayType},
	/*12: */ {TokenType.INTEGER, ActionNumber.num4},
	/*13: */ {TokenType.REAL, ActionNumber.num4},
	/*14: */ {ActionNumber.num6, TokenType.ARRAY, TokenType.LEFTBRACKET, TokenType.INTCONSTANT, ActionNumber.num7, TokenType.DOUBLEDOT, TokenType.INTCONSTANT, ActionNumber.num7, TokenType.RIGHTBRACKET, TokenType.OF, NonTerminal.standardType},
	/*15: */ {NonTerminal.subprogramDeclaration, NonTerminal.subDeclarations},
	/*16: */ {},
	/*17: */ {ActionNumber.num1, NonTerminal.subprogramHead, NonTerminal.declarations, ActionNumber.num5, NonTerminal.compoundStatement, ActionNumber.num11},
	/*18: */ {TokenType.FUNCTION, TokenType.IDENTIFIER, ActionNumber.num15, NonTerminal.arguments, TokenType.COLON, TokenType.RESULT, NonTerminal.standardType, TokenType.SEMICOLON, ActionNumber.num16},
	/*19: */ {TokenType.PROCEDURE, TokenType.IDENTIFIER, ActionNumber.num17, NonTerminal.arguments, TokenType.SEMICOLON},
	/*20: */ {TokenType.LEFTPAREN, ActionNumber.num19, NonTerminal.parameterList, TokenType.RIGHTPAREN, ActionNumber.num20},
	/*21: */ {},
	/*22: */ {NonTerminal.identifierList, TokenType.COLON, NonTerminal.type, ActionNumber.num21, NonTerminal.parameterListTail},
	/*23: */ {TokenType.SEMICOLON, NonTerminal.identifierList, TokenType.COLON, NonTerminal.type, ActionNumber.num21, NonTerminal.parameterListTail},
	/*24: */ {},
	/*25: */ {TokenType.BEGIN, NonTerminal.statementList, TokenType.END},
	/*26: */ {NonTerminal.statement, NonTerminal.statementListTail},
	/*27: */ {TokenType.SEMICOLON, NonTerminal.statement, NonTerminal.statementListTail},
	/*28: */ {},
	/*29: */ {NonTerminal.elementaryStatement},
	/*30: */ {TokenType.IF, NonTerminal.expression, ActionNumber.num22, TokenType.THEN, NonTerminal.statement, NonTerminal.elseClause},
	/*31: */ {TokenType.WHILE, ActionNumber.num24, NonTerminal.expression, ActionNumber.num25, TokenType.DO, NonTerminal.statement, ActionNumber.num26},
	/*32: */ {TokenType.ELSE, ActionNumber.num27, NonTerminal.statement, ActionNumber.num28},
	/*33: */ {ActionNumber.num29},
	/*34: */ {TokenType.IDENTIFIER, ActionNumber.num30, NonTerminal.esTail},
	/*35: */ {NonTerminal.compoundStatement},
	/*36: */ {ActionNumber.num53, NonTerminal.subscript, TokenType.ASSIGNOP, NonTerminal.expression, ActionNumber.num31},
	/*37: */ {ActionNumber.num54, NonTerminal.parameters},
	/*38: */ {ActionNumber.num32, TokenType.LEFTBRACKET, NonTerminal.expression, TokenType.RIGHTBRACKET, ActionNumber.num33},
	/*39: */ {ActionNumber.num34},
	/*40: */ {ActionNumber.num35, TokenType.LEFTPAREN, NonTerminal.expressionList, TokenType.RIGHTPAREN, ActionNumber.num51},
	/*41: */ {ActionNumber.num36},
	/*42: */ {NonTerminal.expression, ActionNumber.num37, NonTerminal.expressionListTail},
	/*43: */ {TokenType.COMMA, NonTerminal.expression, ActionNumber.num37, NonTerminal.expressionListTail},
	/*44: */ {},
	/*45: */ {NonTerminal.simpleExpression, NonTerminal.expressionTail},
	/*46: */ {TokenType.RELOP, ActionNumber.num38, NonTerminal.simpleExpression, ActionNumber.num39},
	/*47: */ {},
	/*48: */ {NonTerminal.term, NonTerminal.simpleExpressionTail},
	/*49: */ {NonTerminal.sign, ActionNumber.num40, NonTerminal.term, ActionNumber.num41, NonTerminal.simpleExpressionTail},
	/*50: */ {TokenType.ADDOP, ActionNumber.num42, NonTerminal.term, ActionNumber.num43, NonTerminal.simpleExpressionTail},
	/*51: */ {},
	/*52: */ {NonTerminal.factor, NonTerminal.termTail},
	/*53: */ {TokenType.MULOP, ActionNumber.num44, NonTerminal.factor, ActionNumber.num45, NonTerminal.termTail},
	/*54: */ {},
	/*55: */ {TokenType.IDENTIFIER, ActionNumber.num46, NonTerminal.factorTail},
	/*56: */ {NonTerminal.constant, ActionNumber.num46},
	/*57: */ {TokenType.LEFTPAREN, NonTerminal.expression, TokenType.RIGHTPAREN},
	/*58: */ {TokenType.NOT, NonTerminal.factor, ActionNumber.num47},
	/*59: */ {NonTerminal.actualParameters},
	/*60: */ {NonTerminal.subscript, ActionNumber.num48},
	/*61: */ {ActionNumber.num49, TokenType.LEFTPAREN, NonTerminal.expressionList, TokenType.RIGHTPAREN, ActionNumber.num50},
	/*62: */ {ActionNumber.num52},
	/*63: */ {TokenType.UNARYPLUS},
	/*64: */ {TokenType.UNARYMINUS},
	/*65: */ {NonTerminal.program, TokenType.ENDMARKER},
	/*66: */ {TokenType.INTCONSTANT},
	/*67: */ {TokenType.REALCONSTANT},
			 };
	}
	
	/* Returns index of Grammar Table Production, used in conjunction with Parse Table */
	
	public GrammarSymbol GetRHSProduction(int x, int y) 
	{
		return grammarTable[x][y];
	}
	
	
	
	
}
