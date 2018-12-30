package parser;

public enum ActionNumber implements GrammarSymbol 
{	
	/* List of action numbers that exist in the grammar. Action numbers
	 * are referenced in SemanticActions and by the Grammar Symbol table
	 * in order to analyze semantic units of information from the input
	 * file as they are popped off the Parse Stack and Semantic Stack. */
	
	num1, num2, num3, num4, num5, num6, num7, num9, num11, num13, num15, num16, num17, num19, num20,
	num21, num22, num24, num25, num26, num27, num28, num29, num30, num31, num32, num33, num34, num35, num36,
	num37, num38, num39, num40, num41, num42, num43, num44, num45, num46, num47, num48, num49, num50, 
	num51, num52, num53, num54, num55, num56, num51READ, num51WRITE, num67;
	
	public boolean IsAction() 
	{
		return true;
	}

	public boolean IsToken() 
	{
		return false;
	}

	public boolean IsNonTerminal() 
	{
		return false;
	}
}
