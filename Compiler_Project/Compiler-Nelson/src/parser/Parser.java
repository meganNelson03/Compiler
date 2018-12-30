package parser;
import java.io.IOException;
import java.util.Stack;

public class Parser
{
	/* The function of the parser is to ensure that the stream of
	 * tokens from the Tokenizer conforms to the rules of the language
	 * (ie, that the input is syntactically correct. */
	
	private Tokenizer Tokenize;
	private CharacterStream CharStream;
	private GrammarSymbolList GramSymList;
	private GrammarTable GramTable; 
	private ParseTable ParseTable; 
	private Stack<GrammarSymbol> ParseStack;
	private SemanticAction SemAct;
	private Token PrevToken;
	private int Counter = 0; 
	private boolean PrintOn = false;
	
	public Parser(SemanticAction semact, Tokenizer lexer) throws IOException, CompilerError 
	{
		Tokenize = lexer;
		SemAct = semact;
		
		PrevToken = Tokenize.GetT();
		Tokenize.SetT(Tokenize.GetNextToken());
		CharStream = new CharacterStream();
		ParseStack = new Stack<GrammarSymbol>();
		SemAct = new SemanticAction();
		ParseTable = new ParseTable();
		GramTable = new GrammarTable();
		GramSymList = new GrammarSymbolList();
		
		/* Populate GramSymList with Token and Nonterminals */
		GramSymList.TokenList();
		GramSymList.NonTermList();	
	}
	
	/* Parser constructor with no arguments is only used in the Parser main() 
	 * function to test the Parser class. 
	 * Note: to change the file tested, set the parseFile to new path.
	 * Current file: theUltimateTest.pas */
	
	public Parser() throws IOException, CompilerError 
	{
		String parseFile = "/Users/megannelson/331-COMPILER/Compiler331-CompleteCompiler-Nelson/src/parser/parserTest.txt";
		CharStream = new CharacterStream(parseFile);
		
		Tokenize = new Tokenizer(CharStream);
		PrevToken = Tokenize.GetT();
		Tokenize.SetT(Tokenize.GetNextToken());
		
		ParseStack = new Stack<GrammarSymbol>();
		SemAct = new SemanticAction();
		ParseTable = new ParseTable();
		GramTable = new GrammarTable();
		GramSymList = new GrammarSymbolList();
		GramSymList.TokenList();
		GramSymList.NonTermList();	
	}
	
	/* Returns index of current token (row in ParseTable) */
	
	public int MatchTerminal() 
	{
		int parseTableRow = -1;
		TokenType tk = Tokenize.GetT().GetType();
		for (int index = 0; index < GramSymList.GetTokenList().size(); index++) 
		{
			if (tk == GramSymList.GetTokenList().get(index)) 
			{
				parseTableRow = index;
			} 
		} 
		return parseTableRow;
	}
	
	/*Returns index of production in GrammarTable based on top of stack (column in ParseTable)
	 if element on top of stack is terminal, pop stack until non-terminal is on top of stack
	 if element in action, execute action and pop stack until non-terminal is on the top of stack */
	
	public int MatchStackProduction() throws IOException, CompilerError 
	{
		int parseTableCol = -2;
		GrammarSymbol gramSym = ParseStack.peek();
		for (int index = 0; index < GramSymList.GetNonTermList().size(); index++) 
		{  
			/* Search the GramSymList for the nonterminal that matches the stack top*/
			if (gramSym == GramSymList.GetNonTermList().get(index)) 
			{
				parseTableCol = index;
				break;
			} 
		}
		
		if (gramSym.IsAction()) 
		{
			ActionNumber gramSymAction = (ActionNumber) gramSym;
			SemAct.execute(gramSymAction, PrevToken);
			SemAct.SemanticDump();
			ParseStack.pop();
			gramSym = ParseStack.peek();
			if (gramSym.IsNonTerminal()) 
			{
				/* Make recursive call in order to check if top of stack is now a non-terminal*/
				parseTableCol = MatchStackProduction();
			}
		}
		
		if (gramSym.IsToken()) 
		{
			if (Tokenize.GetT().GetType() == gramSym && Tokenize.GetT().GetType() != TokenType.ENDOFFILE) 
			{   
				/* Token on top of stack matches current token in lookahead, therefore pop token off the stack
			       and make recursive call in order to get next set of non-terminal productions onto the stack */
				
				popOffTerms();
				parseTableCol = MatchStackProduction();
			} 
			else if (Tokenize.GetT().GetType() != gramSym) 
			{  
				/* Current token does not match token on top of stack --> error */
				throw SyntaxError.ReturnError(0, CharStream.GetFileLine(), Tokenize.GetLastToken().GetType());
			}
		} 
		
		if (parseTableCol == -2 && !(gramSym.equals(TokenType.ENDOFFILE))) 
		{
			parseTableCol = MatchStackProduction();
		}
		
		return parseTableCol;
	}

	/* if token type is the same as stack top, pop stack and get next token */
	
	public void popOffTerms() throws IOException, LexicalError 
	{
		ParseStack.pop();
		PrevToken = Tokenize.GetT();
		Tokenize.SetT(Tokenize.GetNextToken());
	}

	/* Initialize stack, get indices from the ParseTable and apply correct production based on index
	   if index is negative int: episilon production -> pop ParseStack
	      index is positive int: push productions onto ParseStack
	      index is 999: throw new production error (no production possible)
	   if action is on top of stack: execute action
	      otherwise, while terminals are on the stack, pop off terminals and 
	      make call to MatchStackProduction()
	   if ENDOFFILE is popped off stack: return ACCEPT   
	*/
	
	public void MisterPopNPush() throws IOException, CompilerError 
	{
		InitStack();
		int indexLookahead = MatchTerminal();
		int indexStackTop = MatchStackProduction();
		int entry = ParseTable.getParseIndex(indexLookahead, indexStackTop);
		DumpStack(entry, ParseStack);
		
		while (!(ParseStack.isEmpty())) 
		{
			ParseStack.pop();
			DumpStack(entry, ParseStack);
			if (entry == 999) 
			{   
				/* entry doesn't have valid production (token and non-terminal mismatch) */
				throw SyntaxError.ReturnError(1, CharStream.GetFileLine(), Tokenize.GetLastToken().GetType());
			} 
			else 
			{	
				/* Push grammar table productions onto the stack */
				for (int y = GramTable.grammarTable[entry].length-1; y >= 0; y--) 
				{
					ParseStack.push(GramTable.grammarTable[entry][y]);
				}
				
				DumpStack(entry, ParseStack);
				while (ParseStack.peek().IsToken()) 
				{
					if (ParseStack.peek() == Tokenize.GetT().GetType()) 
					{  
						/* If what's on top of the stack matches the current token: */
						ParseStack.pop();
						DumpStack(entry, ParseStack);
						/* Get next token : */
						PrevToken = Tokenize.GetT();
						Tokenize.SetT(Tokenize.GetNextToken());
						
						if (ParseStack.peek().IsAction()) 
						{
							ActionNumber gsAction = (ActionNumber) ParseStack.peek();
							SemAct.execute(gsAction, PrevToken);
							SemAct.SemanticDump();
							ParseStack.pop();	
							DumpStack(entry, ParseStack);
						}
					}  
					else 
					{   
						/* If what's on top of the stack doesn't match the current token: */
						throw SyntaxError.ReturnError(0, CharStream.GetFileLine(), Tokenize.GetT().GetType());
					}
				}
				
				if (ParseStack.peek().IsAction()) 
				{   
					/* If top of stack is an action, pop the stack and execute action : */
					ActionNumber gsAction = (ActionNumber) ParseStack.peek();
					SemAct.execute(gsAction, PrevToken);
					SemAct.SemanticDump();
					DumpStack(entry, ParseStack);
					ParseStack.pop();	
				}

				/* After tokens and actions have been popped off stack, 
				 * match production list (column in parseTable) and current token
				 * (row in parseTable)  */
				
				indexStackTop = MatchStackProduction();
				indexLookahead = MatchTerminal();
				if (indexLookahead != -998) 
				{   
					/* Find corresponding entry in the parse table */
					
					entry = ParseTable.getParseIndex(indexLookahead, indexStackTop);
					while (entry < 0) 
					{	
						/* While productions are epsilon and disappear from stack (via matches)
					       pop the stack and find the next set of productions to push on the
					       stack */
						
						ParseStack.pop();
						indexStackTop = MatchStackProduction();
						indexLookahead = MatchTerminal();
						DumpStack(entry, ParseStack);
						if (Tokenize.GetT().GetType() != TokenType.ENDOFFILE) 
						{
							entry = ParseTable.getParseIndex(indexLookahead, indexStackTop);
						}
						else 
						{
							break;
						}
					}
				}
				if (Tokenize.GetT().GetType() == TokenType.ENDOFFILE) 
				{
					break;	
				}
			}
		}
		
		/* All stack productions have been executed successfully, program is
		 * syntactically correct (EOF token has been popped with ENDMARKER) */
		
		if (PrintOn) 
		{
			System.out.println(ParseStack + ": ACCEPTED");
		}
	}
	
	public void InitStack() 
	{
		ParseStack.push(TokenType.ENDOFFILE);
		ParseStack.push(NonTerminal.goal);
	}

	/* Print ParseStack and SemanticStack contents
	   set 'on' to 'false' to turn off printing   */
	
	public void DumpStack(int entry, Stack<GrammarSymbol> stack) 
	{
		if (PrintOn) 
		{
			Counter += 1;
			System.out.println(">>-" + Counter + "-<<");
			System.out.println("STACK ::==" + stack.toString());
			if (stack.peek().IsNonTerminal()) 
			{
				System.out.println("POPPED: " + "<" + stack.peek() + "> with TOKEN: <" + Tokenize.GetT().GetType() + "," +Tokenize.GetT().GetValue() + "> ---> *PUSH* , RHS Production: [" + entry + "]");
			} 
			else if (stack.peek().IsToken()) 
			{
				System.out.println("POPPED: " + "<" + stack.peek() + "> with TOKEN: <" + Tokenize.GetT().GetType() + "," +Tokenize.GetT().GetValue() + "> ---> *MATCH* CONSUME TOKENS , RHS Production: [" + entry + "]");
			} 
			else if (stack.peek().IsAction()) 
			{
				System.out.println("POPPED: " + stack.peek());
			}
			System.out.println();
		}
	}
	
	public void PrintQuad() 
	{
		SemAct.GetQuad().PrintQuadruples();	
	}
	
	/* Used to turn on and off the Parse DumpStack() function when testing the Parser*/
	
	public boolean TurnOnPrinting() {
		PrintOn = !PrintOn;
		return PrintOn;
	}
	
	public void Parse() throws IOException, CompilerError {
		  MisterPopNPush();
		  PrintQuad();	
	}
	
	public static void main(String[] args) throws IOException, CompilerError 
	{	
		Parser parser = new Parser();
		parser.TurnOnPrinting();
		parser.MisterPopNPush();
	}
	
}


