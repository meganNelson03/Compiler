package parser;


public enum NonTerminal implements GrammarSymbol 
{
	/* A non-terminal is a production rule in the grammar of Vascal which
	 * can derive other forms. This is referenced by the Grammar Symbol table
	 * in order to syntactically analyze the input file. 
	 */

	goal, program, identifierList, declarations, subDeclarations,compoundStatement, identifierListTail,
	declarationList, type, declarationListTail, standardType, arrayType, subprogramDeclaration,
	subprogramHead, arguments, parameterList, parameterListTail, statementList, statementListTail, 
	statement, elementaryStatement, expression, elseClause, esTail, subscript, parameters, expressionList, 
	expressionListTail, simpleExpression, expressionTail, term, simpleExpressionTail, factor, termTail, 
	factorTail, constant, actualParameters, sign;
		
	public boolean IsNonTerminal() 
	{
		return true;
	}
	
	public boolean IsToken() 
	{
		return false;
	}

	public boolean IsAction() 
	{
		return false;
	}
	
}
