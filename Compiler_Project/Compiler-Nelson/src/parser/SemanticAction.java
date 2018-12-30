package parser;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
@SuppressWarnings("unchecked")

public class SemanticAction 
{
	/* Semantic Actions produce TVI code based on the actions popped off
	 * and the subsequent call to Execute(action-num) in the Parser class. 
	 * Actions are called when there is sufficient information from the
	 * Parse stack to allow an operation to be performed. */
	
	@SuppressWarnings("unused")
	private boolean Insert;
	private boolean Global;
	private boolean Array;
	
	private Stack<Object> SemanticStack; /* Stores state info: Etypes, Tokens, Sym Tab Entries*/
	private Stack<List<SymbolTableEntry>> ParamStack;
	private Stack<Integer> ParamCount;
	private SymbolTable GlobalTable;
	private SymbolTable LocalTable;
	private SymbolTable ConstantTable;
	private SymbolTableEntry CurrentFunction;
	private Quadruples Quads;
	
	private int GlobalMemory;
	private int GlobalStore;
	private int LocalMemory;
	private int LocalStore;
	private int SemanticCounter;
	private int TempVarCounter;
	private int NextParam;
	
	public SemanticAction() throws CompilerError 
	{
		Insert = true;  /* true if in Insert mode in the SymTab */
		Global = true;  /* true if in global env, false if in local env */
		Array = false;  /* true if next variable is an array, false is simple var */
		Quads = new Quadruples();
		SemanticCounter = 0; 
		TempVarCounter = 0;
		GlobalStore = 0;
		GlobalMemory = 0;
		LocalMemory = 0;
		
		ParamCount = new Stack<Integer>();
		ParamStack = new Stack<List<SymbolTableEntry>>();
		SemanticStack = new Stack<Object>();
		
		GlobalTable = new SymbolTable();
		LocalTable = new SymbolTable();
		ConstantTable = new SymbolTable();
		InitializeGlobalTable();		
	}
	
	public void InitializeGlobalTable() throws CompilerError 
	{	
		/* Puts list of reserved words in the global table */
		GlobalTable.Insert("main", new ProcedureEntry("main", 0, null));
		GlobalTable.Insert("read", new ProcedureEntry("read", 0, null));
		GlobalTable.Insert("write", new ProcedureEntry("write", 0, null));
		GlobalTable.Insert("input", new VariableEntry("input", 0, null));
		GlobalTable.Insert("output", new VariableEntry("output", 0, null));
	}
	
	/* Determines the Action number from the Parse Stack top and calls the correct Semantic Action routine */

	public void execute(ActionNumber num, Token token) throws CompilerError
	{	
		/* Note: The variable "token" is the current token passed to Semantic Actions
		 * by the parser when the parser calls the correct routine */
		SemanticDump();
		switch (num) 
		{
			case num1: Action1(token); break;
			case num2: Action2(token); break;
			case num3: Action3(token); break;
			case num4: Action4(token); break;
			case num5: Action5(token); break;
			case num6: Action6(token); break;
			case num7: Action7(token); break;
			case num9: Action9(token); break;
			case num11: Action11(token); break;
			case num13: Action13(token); break;
			case num15: Action15(token); break;
			case num16: Action16(token); break;
			case num17: Action17(token); break;
			case num19: Action19(token); break;
			case num20: Action20(token); break;
			case num21: Action21(token); break;
			case num22: Action22(token); break;
			case num24: Action24(token); break;
			case num25: Action25(token); break;
			case num26: Action26(token); break;
			case num27: Action27(token); break;
			case num28: Action28(token); break;
			case num29: Action29(token); break;
			case num30: Action30(token); break;
			case num31: Action31(token); break;
			case num32: Action32(token); break;
			case num33: Action33(token); break;
			case num34: Action34(token); break;
			case num35: Action35(token); break;
			case num36: Action36(token); break;
			case num37: Action37(token); break;
			case num38: Action38(token); break;
			case num39: Action39(token); break;
			case num40: Action40(token); break;
			case num41: Action41(token); break;
			case num42: Action42(token); break;
			case num43: Action43(token); break;
			case num44: Action44(token); break;
			case num45: Action45(token); break;
			case num46: Action46(token); break;
			case num47: Action47(token); break;
			case num48: Action48(token); break;
			case num49: Action49(token); break;
			case num50: Action50(token); break;
			case num51: Action51(token); break;
			case num52: Action52(token); break;
			case num53: Action53(token); break;
			case num54: Action54(token); break;
			case num55: Action55(token); break;
			case num56: Action56(token); break;
			case num51READ: Action51READ(token); break;
			case num51WRITE: Action51WRITE(token); break;
			default: throw SemanticError.ReturnError(9, CompilerError.GetErrorStream().GetFileLine());
		}
	}
	
	/*====================================================================================
	=======================*===*===*===Actions===*===*===*===============================
	======================================================================================*/

	/* Insert is set to true when new variables are being declared */
	
	public void Action1(Token token) 
	{
		 Insert = true;
	}
	
	/* Insert is set to false when new variables are done being declared */
	
	public void Action2(Token token) 
	{
		Insert = false;
	}
	
	/* Pops identifiers off the Semantic Stack and allocates memory for them 
	 * in global or local tables */
	
	public void Action3(Token token) throws CompilerError 
	{
		TokenType type = ((Token) SemanticStack.pop()).GetType();
		if (Array) 
		{
			int upperBound = ((Token) SemanticStack.pop()).TokenToInt();
			int lowerBound = ((Token) SemanticStack.pop()).TokenToInt();
			int memorySize = (upperBound - lowerBound) + 1;
			
			while (!(SemanticStack.isEmpty()) && ((Token) SemanticStack.peek()).GetType().equals(TokenType.IDENTIFIER)) 
			{   
				/* If array: pop array components off stack and create new Array entry,
			       increment global or local memory, make new inserts into global or local table */
				
				Token toky = (Token) SemanticStack.pop();
				ArrayEntry id = new ArrayEntry(toky.GetValue(), 0, type, upperBound, lowerBound);
				id.SetType(type);
				id.SetUpperBound(upperBound);
				id.SetLowerBound(lowerBound);
				
				if (Global) 
				{
					id.SetAddress(GlobalMemory);
					GlobalTable.Insert(toky.GetValue(), id);
					GlobalMemory += memorySize;
				} 
				else 
				{
					id.SetAddress(LocalMemory);
					LocalTable.Insert(toky.GetValue(), id);
					LocalMemory += memorySize;
				} 
			}
		} 
		else 
		{   
			/* While stack contains identifiers: 
			 * 	pop off the semantic stack, allocate new variable entry, make new
			 *  insertion into global or local table, and increment memory */
			
			while ((!SemanticStack.isEmpty()) && SemanticStack.peek() instanceof Token && (((Token) SemanticStack.peek()).GetType().equals(TokenType.IDENTIFIER))) 
			{
				Token tok = (Token) SemanticStack.pop();
				VariableEntry id = new VariableEntry(tok.GetValue(), 0, null);
				id.SetType(type);
				
				if (Global) 
				{
					id.SetAddress(GlobalMemory);
					GlobalTable.Insert(tok.GetValue(), id);
					GlobalMemory++;
				} 
				else
				{
					id.SetAddress(LocalMemory);
					LocalTable.Insert(tok.GetValue(), id);
					LocalMemory++; 
				} 
			}	
		}
		Array = false;
	}
	
	/* Pushes the number type of identifier onto the semantic stack (REAL or INTEGER)*/
	
	public void Action4(Token token) 
	{
		SemanticStack.push(token);
	}
	
	/* Variables are done being declared, new procedure
	 * is generated in TVI code and space is allocated */
	
	public void Action5(Token token) throws CompilerError 
	{
		Insert = false;
		SymbolTableEntry id = (SymbolTableEntry) SemanticStack.pop();
		GenGenGen("PROCBEGIN", id.GetName());
		LocalStore = Quads.GetNextQuad();
		Gen("alloc", "_");
	}
	
	/* Array is set to true when new array is being declared */
	
	public void Action6(Token token) 
	{
		Array = true;
	}
	
	/* Pushes intconstants of array (Upper and lower bounds of array) onto Sem Stack */
	
	public void Action7(Token token) 
	{
		SemanticStack.push(token);
	}
	
	/* Pops the three first IDs of the program off the stack
	 * (eg: UltimateProgram (input, output)), places them in global table*/
	
	public void Action9(Token token) throws CompilerError 
	{
		Token id1 = (Token) SemanticStack.pop();
		Token id2 = (Token) SemanticStack.pop();
		Token id3 = (Token) SemanticStack.pop();
		
		GlobalTable.Insert(id1.GetValue(), new IODeviceEntry(id1.GetValue()));
		GlobalTable.Insert(id2.GetValue(), new IODeviceEntry(id2.GetValue()));
		GlobalTable.Insert(id3.GetValue(), new ProcedureEntry(id3.GetValue(), 0, null)); /* proc entry should be name of the program*/
		/* stub code generated */
		GenNoAddress("call", "main", "0");
		Gen("exit");
		Insert = false;
	}
	
	/* Generates end-of-procedure TVI code, returns scope to global, and
	 * reinitializes local table (ie, called after END token is popped off stack) */
	
	public void Action11(Token token) 
	{
		Global = true;
		LocalTable = new SymbolTable();
		CurrentFunction = null;
		Backpatch(LocalStore, LocalMemory);
		GenGenGen("free", Integer.toString(LocalMemory));
		Gen("PROCEND");
	}
	
	/* Pushes identifiers onto the semantic stack */
	
	public void Action13(Token token) {
		SemanticStack.push(token);
	}
	
	/* Stores the result and type of result of Function
	 * (INT or REAL), creates new function entry */
	
	public void Action15(Token token) throws CompilerError 
	{
		VariableEntry result = Create(token.GetValue() + "_RESULT", TokenType.INTEGER);
		result.SetResult();
		SymbolTableEntry id = new FunctionEntry(token.GetValue(), result);
		
		GlobalTable.Insert(id.GetName(), id);
		Global = false;
		LocalMemory = 0;
		CurrentFunction = id;
		SemanticStack.push(id);
	}
	
	/* Sets the type of function and it's result
	 * Sets the type of result variable of ID */
	
	public void Action16(Token token) 
	{
		Token type = (Token) SemanticStack.pop();
		FunctionEntry id = (FunctionEntry) SemanticStack.peek();
		id.SetType(type.GetType());
		
		id.SetResultType(type.GetType());
		CurrentFunction = id;
	}
	
	/* Creates new procedure entry with name of procedure from Parser,
	 * switches scope to local environment */
	
	public void Action17(Token token) throws CompilerError 
	{
		SymbolTableEntry id = new ProcedureEntry(token.GetValue());
		GlobalTable.Insert(id.GetName(), id);
		Global = false;
		LocalMemory = 0;
		CurrentFunction = id;
		SemanticStack.push(id);
	}
	
	/* Creates new stack (ParamCount) which keeps track of the number of parameters
	 * in a function or procedure */
	
	public void Action19(Token token) 
	{
		ParamCount = new Stack<>();
		ParamCount.push(0);
	}
	
	/* Associates function or procedure with appropriate number of parameters 
	 * (eg: gcd (a, b : integer) would be a sym tab entry with 2 parameters */
	
	public void Action20(Token token) 
	{
		SymbolTableEntry id = (SymbolTableEntry) SemanticStack.peek();
		int numParams = ParamCount.pop();
		id.SetNumberOfParameters(numParams); /* id is a func or proc entry */
	}
	
	/* Adds parameters to the associated function to a parameter stack,
	 * pops off these paramters and stores them either as a Variable or
	 * Array entry */
	
	public void Action21(Token token) throws CompilerError 
	{
		Token type = (Token) SemanticStack.pop();
		int upperBound = -1; /* if arr, pop lower and upper bounds*/
		int lowerBound = -1;
		if (Array) 
		{
			upperBound =  ((Token) SemanticStack.pop()).TokenToInt();
			lowerBound = ((Token) SemanticStack.pop()).TokenToInt();
		}
		
		/* Tokens on stack will be pushed on parameters stack from bottom-most
		 * to top-most order */
		
		Stack<Token> parameters = new Stack<>();
		while (SemanticStack.peek() instanceof Token && ((Token) SemanticStack.peek()).GetType().equals(TokenType.IDENTIFIER)) 
		{
			/* while there are IDs on the SemStack, pop them and push onto parameters */
			
			parameters.push((Token) SemanticStack.pop());
		}
		
		while (!parameters.empty()) 
		{
			/* Pop paramter off the parameter stack, make appropriate
			 * symbol table entry depending on whether the param is an array type or
			 * simple type */
			
			Token param = parameters.pop();
			SymbolTableEntry var;
			if (Array) 
			{
				var = new ArrayEntry(param.GetValue(), LocalMemory, type.GetType(), 
								upperBound, lowerBound);
			} 
			else 
			{
				var = new VariableEntry(param.GetValue(), LocalMemory, type.GetType());
			}	
			var.SetParameter();
			LocalTable.Insert(var.GetName(), var);
			CurrentFunction.AddParameter(var); /* current function is a procedure or function entry*/
			LocalMemory++;
			ParamCount.push(ParamCount.pop() + 1); /* increment Parameter count */
		}
		Array = false;
	}
	
	/* Evaluates if(...) relational expressions. When true,
	 * need to store backpatched address that is jumped to in the TVI code */
	
	public void Action22(Token token) throws SemanticError 
	{
		EType etype = (EType) SemanticStack.pop();
		if (etype != EType.RELATIONAL) 
		{
			throw SemanticError.ReturnError(0, CompilerError.GetErrorStream().GetFileLine());
		} 
		
		List<Integer> EFalse = (List<Integer>) SemanticStack.pop();
		List<Integer> ETrue = (List<Integer>) SemanticStack.pop();
		Backpatch(ETrue, Quads.GetNextQuad());
		SemanticStack.push(ETrue);
		SemanticStack.push(EFalse);
	}
	
	/* Handles while (...) expr number of loops */
	
	public void Action24(Token token) 
	{
		int beginLoop = Quads.GetNextQuad();
		SemanticStack.push(beginLoop);
	}
	
	/* Evaluates while (...) expr relations. When true,
	 * need to store backpatch address jumped to in the TVI code */
	
	public void Action25(Token token) throws SemanticError 
	{
		EType etype = (EType) SemanticStack.pop();
		if (etype != EType.RELATIONAL) 
		{
			throw SemanticError.ReturnError(3, CompilerError.GetErrorStream().GetFileLine());
		}
		List<Integer> EFalse = (List<Integer>) SemanticStack.pop();
		List<Integer> ETrue = (List<Integer>) SemanticStack.pop();
		Backpatch(ETrue, Quads.GetNextQuad());
		
		SemanticStack.push(ETrue);
		SemanticStack.push(EFalse);
	}
	
	/* When a while(...) expr is true, need to execute do (...) statement.
	 * Generates "goto int-of-start-of-loop" and backpatch the false condition
	 * address in the TVI code when the expr is no longer true */
	
	public void Action26(Token token) 
	{
		List<Integer> EFalse = (List<Integer>) SemanticStack.pop();
		@SuppressWarnings("unused")
		List<Integer> ETrue = (List<Integer>) SemanticStack.pop();
		int beginLoop = (int) SemanticStack.pop();
		GenGenGen("goto", Integer.toString(beginLoop));
		Backpatch(EFalse, Quads.GetNextQuad());
	}
	
	/* Handles the else case of if(...) expressions. 
	 * When expr is not true: generate the "goto" address
	 * to jump to in the TVI code (aka, backpatch the false
	 * condition for the if statement */
	
	public void Action27(Token token) throws CompilerError 
	{
		List<Integer> skipElse = MakeList(Quads.GetNextQuad());
		Gen("goto", "_");
		List<Integer> EFalse = (List<Integer>) SemanticStack.pop();
		List<Integer> ETrue = (List<Integer>) SemanticStack.pop();
		Backpatch(EFalse, Quads.GetNextQuad());
		SemanticStack.push(skipElse);
		SemanticStack.push(ETrue);
		SemanticStack.push(EFalse);
	}
	
	/* If the 'else' case of an if (...) expr evaluated: need to backpatch the 
	 * skipElse statement address in the TVI code */
	
	public void Action28(Token token) 
	{
		List<Integer> EFalse = (List<Integer>) SemanticStack.pop();
		List<Integer> ETrue = (List<Integer>) SemanticStack.pop();
		// skipElse pushed onto stack in 27
		List<Integer> skipElse = (List<Integer>) SemanticStack.pop();
		Backpatch(skipElse, Quads.GetNextQuad());
	}
	
	/* If there is no else clause: need to backpatch the
	 * false case of the if(...) expr with a proper address in the TVI code */
	
	public void Action29(Token token) 
	{
		List<Integer> EFalse = (List<Integer>) SemanticStack.pop();
		List<Integer> ETrue = (List<Integer>) SemanticStack.pop();
	    Backpatch(EFalse, Quads.GetNextQuad());
	    
	}
	
	/* Pushes identifier onto stack of an elementary statement expression,
	 * (eg: "x := 1" ) where x is the identifier  */
	
	public void Action30(Token token) throws SemanticError 
	{
		SymbolTableEntry id = LookupID(token); /* ID must have been declared beforehand */
		if (id == null) 
		{
			throw SemanticError.ReturnError(1, CompilerError.GetErrorStream().GetFileLine());
		}
		SemanticStack.push(id);
		SemanticStack.push(EType.ARITHMETIC);
		
	}
	
	/* Checks type of both arguments in an assignop operation
	 * (eg: gcd := a, compares type of gcd and type of a 
	 * and Generates different TVI code depending on their types */
	
	public void Action31(Token token) throws CompilerError 
	{
		EType etype = (EType) SemanticStack.pop();
		if (etype != EType.ARITHMETIC) 
		{
			throw SemanticError.ReturnError(3, CompilerError.GetErrorStream().GetFileLine());
		}
		SymbolTableEntry id2 = (SymbolTableEntry) SemanticStack.pop(); /* LHS variable */
		SymbolTableEntry offset = (SymbolTableEntry) SemanticStack.pop();
		SymbolTableEntry id = (SymbolTableEntry) SemanticStack.pop();  /* RHS variable */
		
		/* TypeCheck checks the type of RHS and LHS variables */
		int check = TypeCheck(id, id2);
		
		if (check == 3) /* int, real */
		{
			throw SemanticError.ReturnError(0, CompilerError.GetErrorStream().GetFileLine());	
		}
		if (check == 2)  /* real, int */
		{
			VariableEntry temp = Create(GenTempVarName(), TokenType.REAL);
			Gen("ltof", id2.GetName(), temp.GetName());
			if (offset == null) 
			{
				Gen("move", temp.GetName(), id.GetName());

			} 
			else 
			{
				Gen("stor", temp.GetName(), offset.GetName(), id.GetName());
			}
		} 
		else  /* int, int or real, int */
		{
			if (offset == null) 
			{
				Gen("move", id2.GetName(), id.GetName());
			} 
			else 
			{
				Gen("stor", id2.GetName(), offset.GetName(), id.GetName());
			}
		}
	}
	
	/* Handles access of index in an array */
	
	public void Action32(Token token) throws SemanticError 
	{
		EType etype = (EType) SemanticStack.pop();
		SymbolTableEntry id = (SymbolTableEntry) SemanticStack.peek();
		if (etype != EType.ARITHMETIC) 
		{
			/* array index access is always determined by a simple arithmetic expression */
			throw SemanticError.ReturnError(3, CompilerError.GetErrorStream().GetFileLine());
		} 
		if (!id.IsArray()) 
		{
			throw SemanticError.ReturnError(6, CompilerError.GetErrorStream().GetFileLine());
		}
	}
	
	/* Pops array entry from Semantic Stack after index expr is evaluated
	 * by Parser  */
	
	public void Action33(Token token) throws CompilerError 
	{
		EType etype = (EType) SemanticStack.pop();
		if (etype != EType.ARITHMETIC) 
		{
			throw SemanticError.ReturnError(3, CompilerError.GetErrorStream().GetFileLine());
		}
		SymbolTableEntry id = (SymbolTableEntry) SemanticStack.pop();
		if (id.GetType() != TokenType.INTEGER) 
		{
			throw SemanticError.ReturnError(4, CompilerError.GetErrorStream().GetFileLine());
		}
		
		ArrayEntry array = (ArrayEntry) SemanticStack.peek();
		VariableEntry temp1 = Create(GenTempVarName(), TokenType.INTEGER);
		VariableEntry temp2 = Create(GenTempVarName(), TokenType.INTEGER);
		/* allocates correct address for array access in index */
		GenGen("move", Integer.toString(array.GetLowerBound()), temp1.GetName());
		Gen("sub", id.GetName(), temp1.GetName(), temp2.GetName());
		SemanticStack.push(temp2);
	
	}
	
	/* Handles subscript expressions that are not array index access,
	 * ie, variables (either functions or real valued num).*/
	
	public void Action34(Token token) throws CompilerError {
		EType etype = (EType) SemanticStack.pop();
		SymbolTableEntry id = (SymbolTableEntry) SemanticStack.peek();
		if (id.IsFunction()) 
		{
			/* If ID is a function: execute action52, which generates
			 * 'call _' TVI code that requests ID's associated function
			 * to be evaluated */
			
			SemanticStack.push(etype); /* need to push function type onto Sem Stack */
			execute(ActionNumber.num52, token);
		}
		else 
		{   
			/* ID is not function: no function associated with ID,
		 	   therefore push 'null' on sem stack */
			
			SemanticStack.push(null);
		}
	}
	
	/* Initializes parameter count stack: identifier(parameter1, ..., parameter-n) 
	 * and checks that ID is a procedure entry */
	
	public void Action35(Token token) 
	{
		EType etype = (EType) SemanticStack.pop();
		ProcedureEntry id = (ProcedureEntry) SemanticStack.peek();
		SemanticStack.push(etype);
		ParamCount.push(0);
		ParamStack.push(id.GetParameterInfo());
	}
	
	/* Handles procedure that has zero parameters
	 * (eg: procedure hello;) */
	
	public void Action36(Token token) throws SemanticError
	{
		EType etype = (EType) SemanticStack.pop();
		ProcedureEntry id = (ProcedureEntry) SemanticStack.pop();
		if (id.GetNumberOfParameters() != 0) 
		{
			throw SemanticError.ReturnError(7, CompilerError.GetErrorStream().GetFileLine());
		}
		/* Generates procedure "call _" TVI code with no parameters */
		GenNoAddress("call", id.GetName(), Integer.toString(0));
	}
	
	/* Handles procedure and function entries with parameters. */
	
	public void Action37(Token token) throws SemanticError 
	{
		EType etype = (EType) SemanticStack.pop();
		if (etype != EType.ARITHMETIC) 
		{
			throw SemanticError.ReturnError(3, CompilerError.GetErrorStream().GetFileLine());
		}
		
		SymbolTableEntry id = (SymbolTableEntry) SemanticStack.peek();
		if (!id.IsConstant() && !id.IsVariable() && !id.IsFunctionResult() && !id.IsArray()) 
		{   /* parameter type is invalid */
			throw SemanticError.ReturnError(2, CompilerError.GetErrorStream().GetFileLine());
		}
		
		/* increment parameter count */
		ParamCount.push(ParamCount.pop() + 1);
		
		Stack parameters = new Stack(); /* find name of procedure or function on bottom of the Sem Stack */
		while (SemanticStack.peek() instanceof EType || SemanticStack.peek() instanceof SymbolTableEntry) 
		{	
			if (SemanticStack.peek() instanceof SymbolTableEntry) 
			{
				if (!(((SymbolTableEntry) SemanticStack.peek()).IsProcedure() ||
						((SymbolTableEntry) SemanticStack.peek()).IsFunction())) 
				{
					/* while stack top is not procedure or function entry,
					 * need to keep pushing parameters onto the stack: */	
					
					parameters.push(SemanticStack.pop());
				} 
				else 
				{ 
					break;
				} 
			} 
			else 
			{   
				/* extra EType is pushed onto the Semantic Stack by Action 30, 
			 	   must push onto the param stack */
				
				parameters.push(SemanticStack.pop());
			}
		}
		
		/* function ID is a procedure or function entry */
		SymbolTableEntry funcId = (SymbolTableEntry) SemanticStack.peek();
		while (!parameters.empty()) 
		{	/* function ID should be on top of the semantic stack */
			SemanticStack.push(parameters.pop());
		}
		
		if (!(funcId.GetName().equals("read") || funcId.GetName().equals("write"))) 
		{
			/* if there is a mismatch between the parameter count and the 
			 * actual number of parameters of a function : */
			
			if (ParamCount.peek() > funcId.GetNumberOfParameters()) 
			{
				throw SemanticError.ReturnError(7, CompilerError.GetErrorStream().GetFileLine());
			}
			SymbolTableEntry param = ParamStack.peek().get(NextParam);
			if (id.GetType() != param.GetType()) 
			{
				throw SemanticError.ReturnError(2, CompilerError.GetErrorStream().GetFileLine());
			}
			if (param.IsArray()) 
			{
				if ((((ArrayEntry) id).GetLowerBound()) != ((ArrayEntry) param).GetLowerBound() ||
						((ArrayEntry) id).GetUpperBound() != ((ArrayEntry) param).GetUpperBound()) 
				{
					throw SemanticError.ReturnError(2, CompilerError.GetErrorStream().GetFileLine());
				}
			}	
			/* increment parameter count */
			NextParam++;
		}	
	}
	
	/* Handles relational operators on LHS of expression */
	
	public void Action38(Token token) throws SemanticError 
	{
		EType etype = (EType) SemanticStack.pop();
		if (etype != EType.ARITHMETIC)
		{
			throw SemanticError.ReturnError(3, CompilerError.GetErrorStream().GetFileLine());
		}
		/* token pushed onto stack should be an operator */
		SemanticStack.push(token);
	}
	
	/* Handles relational expressions and generates TVI code
	 * based on type of RHS and LHS of the expression */
	
	public void Action39(Token token) throws CompilerError 
	{
		EType etype = (EType) SemanticStack.pop();
		if (etype != EType.ARITHMETIC) 
		{
			throw SemanticError.ReturnError(4, CompilerError.GetErrorStream().GetFileLine());
		}
		/* LHS of relational expression */
		SymbolTableEntry id2 = (SymbolTableEntry) SemanticStack.pop();
		Token operator = (Token) SemanticStack.pop();
		/* opcode should be relational operator (ble, etc) */
		String opcode = GetOpCode(operator);
		/* RHS of relational expression */
		SymbolTableEntry id1 = (SymbolTableEntry) SemanticStack.pop();
		
		int check = TypeCheck(id1, id2);
		
		if (check == 2) /* real, int */
		{
			VariableEntry temp = Create(GenTempVarName(), TokenType.REAL);
			Gen("ltof", id2.GetName(), temp.GetName());
			Gen(opcode, id1.GetName(), temp.GetName(), "_");
		} 
		else if (check == 3) /* int, real */
		{
			VariableEntry temp = Create(GenTempVarName(), TokenType.REAL);
			Gen("ltof", id1.GetName(), temp.GetName()); /* convert value to a floating point in TVI code */
			Gen(opcode, temp.GetName(), id2.GetName(), "_");
		} 
		else 
		{	/* no type conversion necessary (no type mismatch) */
			Gen(opcode, id1.GetName(), id2.GetName(), "_");
		}
		
		/* generate "goto _" that will backpatch later with address depending
		 * on how the relational expression evaluates */
		
		Gen("goto", "_");
		List<Integer> ETrue = MakeList(Quads.GetNextQuad() - 2);
		List<Integer> EFalse = MakeList(Quads.GetNextQuad() - 1);
		SemanticStack.push(ETrue);
		SemanticStack.push(EFalse);
		SemanticStack.push(EType.RELATIONAL);
	} 
	
	/* Pushes sign (unary plus or minus) onto semantic stack */
	
	public void Action40(Token token) 
	{
		SemanticStack.push(token);
	}
	
	/* Handles TVI code generation for unary minus and unary plus expressions */
	
	public void Action41(Token token) throws CompilerError 
	{
		EType etype = (EType) SemanticStack.pop();
		if (etype != EType.ARITHMETIC) 
		{
			throw SemanticError.ReturnError(0, CompilerError.GetErrorStream().GetFileLine());
		}
		
		SymbolTableEntry id = (SymbolTableEntry) SemanticStack.pop();
		Token sign = (Token) SemanticStack.pop();
		
		if (sign.GetType().equals(TokenType.UNARYMINUS)) 
		{
			VariableEntry temp = Create(GenTempVarName(), id.GetType());
			if (id.GetType().equals(TokenType.INTEGER)) 
			{
				Gen("uminus", id.GetName(),temp.GetName());
			} 
			else 
			{
				Gen("fuminus", id.GetName(), temp.GetName());
			}
			
			SemanticStack.push(temp);
		} 
		else 
		{
			SemanticStack.push(id);
		}
		
		SemanticStack.push(EType.ARITHMETIC);
	}
	
	/* Handles addition/subtraction operations */
	
	public void Action42(Token token) throws SemanticError 
	{
		//token should be an operator
		EType etype = (EType) SemanticStack.pop();
		
		if (token.GetType().equals(TokenType.ADDOP) && token.GetValue().equals("3")) 
		{
			if (etype != EType.RELATIONAL) 
			{
				throw SemanticError.ReturnError(3, CompilerError.GetErrorStream().GetFileLine());
			}
			
			/* top of semantic stack should be a list of integers that
			 * are in the addop expression */
			
			List<Integer> EFalse = (List<Integer>) SemanticStack.peek();
			Backpatch(EFalse, Quads.GetNextQuad());
		} 
		else 
		{
			if (etype != EType.ARITHMETIC) 
			{
				throw SemanticError.ReturnError(3, CompilerError.GetErrorStream().GetFileLine());
			}
		}
		/* token should be an operator */
		SemanticStack.push(token);
	}
	
	/* Generates code from an addition/subtraction expression based
	 * on the types of the arguments (ids) */
	
	public void Action43(Token token) throws CompilerError 
	{
		/* Operator from action42 is popped here */
		EType etype = (EType) SemanticStack.pop();
		if (etype == EType.RELATIONAL) 
		{
			List<Integer> E2False = (List<Integer>) SemanticStack.pop();
			List<Integer> E2True = (List<Integer>) SemanticStack.pop();
			Token operator = (Token) SemanticStack.pop();
			List<Integer> E1False = (List<Integer>) SemanticStack.pop();
			List<Integer> E1True = (List<Integer>) SemanticStack.pop();
			
			List<Integer> ETrue = Merge(E1True, E2True);
			List<Integer> EFalse = E2False;
			
			SemanticStack.push(ETrue);
			SemanticStack.push(EFalse);
			SemanticStack.push(EType.RELATIONAL);
		} 
		else 
		{
			SymbolTableEntry id2 = (SymbolTableEntry) SemanticStack.pop();
			Token operator = (Token) SemanticStack.pop();
			String opcode = GetOpCode(operator);
			SymbolTableEntry id1 = (SymbolTableEntry) SemanticStack.pop();
			int check = TypeCheck(id1, id2);
			
			if (check == 0) /* int, int */
			{
				VariableEntry temp = Create(GenTempVarName(), TokenType.INTEGER);
				Gen(opcode, id1.GetName(), id2.GetName(), temp.GetName());
				SemanticStack.push(temp);
			} 
			else if (check == 1) /* real, real */
			{
				VariableEntry temp = Create(GenTempVarName(), TokenType.REAL);
				Gen("f" + opcode, id1.GetName(), id2.GetName(), temp.GetName());
				SemanticStack.push(temp);
			} 
			else if (check == 2) /* real, int */
			{
				VariableEntry temp1 = Create(GenTempVarName(), TokenType.REAL);
				VariableEntry temp2 = Create(GenTempVarName(), TokenType.REAL);
				Gen("ltof", id2.GetName(), temp1.GetName()); /* convert types */
				Gen("f" + opcode, id1.GetName(), temp1.GetName(), temp2.GetName());
				SemanticStack.push(temp2);
			} 
			else if (check == 3) /* int, real */
			{
				VariableEntry temp1 = Create(GenTempVarName(), TokenType.REAL);
				VariableEntry temp2 = Create(GenTempVarName(), TokenType.REAL);
				Gen("ltof", id1.GetName(), temp1.GetName());
				Gen("f" + opcode, temp1.GetName(), id2.GetName(), temp2.GetName());
				SemanticStack.push(temp2);
			}
			SemanticStack.push(EType.ARITHMETIC);
		}
	}
	
	/* Handles multiplication operations, pushes operation onto the stack */
	
	public void Action44(Token token) 
	{
		EType etype = (EType) SemanticStack.pop();
		if (etype == EType.RELATIONAL) 
		{
			List<Integer> EFalse = (List<Integer>) SemanticStack.pop();
			List<Integer> ETrue = (List<Integer>) SemanticStack.pop();
			if (token.GetType().equals(TokenType.MULOP) && token.GetValue().equals("5")) 
			{
				Backpatch(ETrue, Quads.GetNextQuad());
			}
			SemanticStack.push(ETrue);
			SemanticStack.push(EFalse);
		}
		SemanticStack.push(token);
	}
	
	/* Generates TVI code for multiplication operations based
	 * on types of the arguments */
	
	public void Action45(Token token) throws CompilerError
	{
		EType etype = (EType) SemanticStack.pop();
		if (etype.equals(EType.RELATIONAL)) 
		{
			/* if expression is a part of a relational expression, need
			 * to later check whether the expression evaluated to true or false,
			 * push true/false cases onto the Semantic stack: */
			
			List<Integer> E2False = (List<Integer>) SemanticStack.pop();
			List<Integer> E2True = (List<Integer>) SemanticStack.pop();
			Token operator = (Token) SemanticStack.pop();
		
			if (operator.GetType().equals(TokenType.MULOP) && operator.GetValue().equals("5"))
			{
				List<Integer> E1False = (List<Integer>) SemanticStack.pop();
				List<Integer> E1True = (List<Integer>) SemanticStack.pop();
				
				List<Integer> ETrue = E2True;
				List<Integer> EFalse = Merge(E1False, E2False);
				SemanticStack.push(ETrue);
				SemanticStack.push(EFalse);
				SemanticStack.push(EType.RELATIONAL);
			}
		
		} 
		else if (etype.equals(EType.ARITHMETIC)) 
		{
			/* pop the arguments from the multiplication expression off the stack 
			 * in order to compare types */
			
			SymbolTableEntry id2 = (SymbolTableEntry) SemanticStack.pop();
			Token operator = (Token) SemanticStack.pop();
			String opcode = GetOpCode(operator);
			SymbolTableEntry id1 = (SymbolTableEntry) SemanticStack.pop();
			int check = TypeCheck(id1, id2);
		
			if (check != 0 && 
				(operator.GetType().equals(TokenType.MULOP) &&  ((operator.GetValue().equals("4") ||
						operator.GetValue().equals("3")))))
			{
				throw SemanticError.ReturnError(2, CompilerError.GetErrorStream().GetFileLine());
			}
		
			if (check == 0) 
			{
				if (operator.GetType().equals(TokenType.MULOP) && operator.GetValue().equals("4")) 
				{
					VariableEntry temp1 = Create(GenTempVarName(), TokenType.INTEGER);
					VariableEntry temp2 = Create(GenTempVarName(), TokenType.INTEGER);
					VariableEntry temp3 = Create(GenTempVarName(), TokenType.INTEGER);
			
					Gen("div", id1.GetName(), id2.GetName(), temp1.GetName());
					Gen("mul", id2.GetName(), temp1.GetName(), temp2.GetName());
					Gen("sub", id1.GetName(), temp2.GetName(), temp3.GetName());
					SemanticStack.push(temp3);
				} else if (operator.GetType().equals(TokenType.MULOP) && ((operator.GetValue().equals("2")) ||
							operator.GetValue().equals("2"))) 
				{
					VariableEntry temp1 = Create(GenTempVarName(), TokenType.REAL);
					VariableEntry temp2 = Create(GenTempVarName(), TokenType.REAL);
					VariableEntry temp3 = Create(GenTempVarName(), TokenType.REAL);
					Gen("ltof", id1.GetName(), temp1.GetName()); /* convert types */
					Gen("ltof", id2.GetName(), temp2.GetName());
					Gen("fdiv", temp1.GetName(), temp2.GetName(), temp3.GetName());
					SemanticStack.push(temp3);
				} 
				else 
				{
					/* Both arguments are integers */
					
					VariableEntry temp = Create(GenTempVarName(), TokenType.INTEGER);
					Gen(opcode, id1.GetName(), id2.GetName(), temp.GetName());
					SemanticStack.push(temp);
				}
			
			} 
			else if (check == 1) /* real, real */
			{
				VariableEntry temp = Create(GenTempVarName(), TokenType.REAL);
				Gen("f" + opcode, id1.GetName(), id2.GetName(), temp.GetName());
				SemanticStack.push(temp);
			} 
			else if (check == 2)  /* real, int */
			{
				VariableEntry temp1 = Create(GenTempVarName(), TokenType.REAL);
				VariableEntry temp2 = Create(GenTempVarName(), TokenType.REAL);
				Gen("ltof", id2.GetName(), temp1.GetName());
				Gen("f" + opcode, id1.GetName(), temp1.GetName(), temp2.GetName());
				SemanticStack.push(temp2);
			} 
			else if (check == 3) /* int, real */
			{
				VariableEntry temp1 = Create(GenTempVarName(), TokenType.REAL);
				VariableEntry temp2 = Create(GenTempVarName(), TokenType.REAL);
				Gen("ltof", id1.GetName(), temp1.GetName()); /* type conversion */
				Gen("f" + opcode, temp1.GetName(), id2.GetName(), temp2.GetName());
				SemanticStack.push(temp2);
			}
			SemanticStack.push(EType.ARITHMETIC);
		}
	}
	
	/* Handles both identifier factors and constant factors
	 * in the code by pushing them onto the semantic stack */
	
	public void Action46(Token token) throws CompilerError {
		if (token.GetType().equals(TokenType.IDENTIFIER)) {
	           SymbolTableEntry id = LookupID(token);
	           if (id == null) {
	        	   System.out.println("!!!!!: VARIABLE " + token.GetValue().toUpperCase() + " IS UNDECLARED");
	               throw SemanticError.ReturnError(1, CompilerError.GetErrorStream().GetFileLine());
	           }
	           SemanticStack.push(id);
	       } else if (token.GetType().equals(TokenType.INTCONSTANT) ||
	                 token.GetType().equals(TokenType.REALCONSTANT)) {
	          
	           SymbolTableEntry id = LookupID(token);
	           
	           /* if id is not in the constant table, global table, or
	            * local table, need to make new constant entry: */
	           
	           if (id == null) {
	               if (token.GetType().equals(TokenType.INTCONSTANT)) {
	                   id = new ConstantEntry(token.GetValue(), TokenType.INTEGER);
	               } else if (token.GetType() == TokenType.REALCONSTANT) {
	                   id = new ConstantEntry(token.GetValue(), TokenType.REAL);
	               }
	               ConstantTable.Insert(token.GetValue(), id);
	           }
	           SemanticStack.push(id);
	       }
		SemanticStack.push(EType.ARITHMETIC);
	}
	
	/* Handles " !(...)" expressions by pushing the expression
	 * onto the stack with false/true cases */
	
	public void Action47(Token token) throws SemanticError {
		EType etype = (EType) SemanticStack.pop();
		if (etype != EType.RELATIONAL) {
			throw SemanticError.ReturnError(3, CompilerError.GetErrorStream().GetFileLine());
		}
		List<Integer> EFalse = (List<Integer>) SemanticStack.pop();
		List<Integer> ETrue = (List<Integer>) SemanticStack.pop();
		SemanticStack.push(EFalse);
		SemanticStack.push(ETrue);
		SemanticStack.push(EType.RELATIONAL);
	}
	
	/* Handles the production of array access/ simple variable access */
	public void Action48(Token token) throws CompilerError {
		SymbolTableEntry offset = (SymbolTableEntry) SemanticStack.pop();
		if (offset != null) {  /* if access is offset from zero (ie, array) : */
			if (offset.IsFunction()) {
				/* need to generate "call _" TVI code to call function */
				execute(ActionNumber.num52, token);
			} else { 
				
				/* else, need to load the address (ie, copy data from one
				 * memory location to a register) in TVI code */
				
				SymbolTableEntry id = (SymbolTableEntry) SemanticStack.pop();
				VariableEntry temp = Create(GenTempVarName(), id.GetType());
				Gen("load", id.GetName(), offset.GetName(), temp.GetName());
				SemanticStack.push(temp);
			}	
		}
		SemanticStack.push(EType.ARITHMETIC);
	}
	/* Pushes function id onto stack and initializes parameter count/ stores parameters */
	public void Action49(Token token) throws SemanticError 
	{
		EType etype = (EType) SemanticStack.pop(); /* get e-type and id, make no changes to the stack*/

		SymbolTableEntry id = (SymbolTableEntry) SemanticStack.peek(); /* should be a function */
		SemanticStack.push(etype);
		
		if (etype != EType.ARITHMETIC) 
		{
			throw SemanticError.ReturnError(3, CompilerError.GetErrorStream().GetFileLine());
		}
		if (!id.IsFunction()) 
		{
			throw SemanticError.ReturnError(8, CompilerError.GetErrorStream().GetFileLine());
		}
		ParamCount.push(0);
		ParamStack.push(id.GetParameterInfo());
	}
	
	/* Handles the generation of "param _" TVI code and reinitializes
	 * param count/ stack after appropriate code is generated 
	 * Note: this action only handles the case of factors */
	
	public void Action50(Token token) throws CompilerError 
	{
		/* all params must be generated from bottom-most to top-most */
		Stack<SymbolTableEntry> parameters = new Stack<>();

		while(SemanticStack.peek() instanceof SymbolTableEntry && (((SymbolTableEntry) SemanticStack.peek()).IsArray() ||
				(((SymbolTableEntry) SemanticStack.peek()).IsConstant() ||
				((SymbolTableEntry) SemanticStack.peek()).IsVariable()))) 
		{	
			/* push every viable symbol table entry onto the parameters stack in
		 	   reverse over from the semantic stack */
			
			parameters.push((SymbolTableEntry) SemanticStack.pop());	
		}
	
		while (!parameters.empty()) 
		{	
			/* generate TVI code for each parameter on the stack in the function/
		 	   procedure expression */
			
			Gen("param", parameters.pop().GetName());
			LocalMemory++;
		}
		
		EType etype = (EType) SemanticStack.pop();
		FunctionEntry id = (FunctionEntry) SemanticStack.pop();
		int numParams = ParamCount.pop();
		if (numParams > id.GetNumberOfParameters()) 
		{   
			/* check if the expression has the right number of params */
			throw SemanticError.ReturnError(7, CompilerError.GetErrorStream().GetFileLine());
		}
		GenNoAddress("call", id.GetName(), Integer.toString(numParams));
		ParamStack.pop();  /* reinitialize after evaluating expression */
		NextParam = 0;
		VariableEntry temp = Create(GenTempVarName(), id.GetResult().GetType());
		Gen("move", id.GetResult().GetName(), temp.GetName());
		SemanticStack.push(temp);
		SemanticStack.push(EType.ARITHMETIC);
	}
	
	/* Handles the generation of "param _" TVI code by checking
	 * the viability of procedure's/ function's parameters 
	 * (ie, correct number of parameters, storing the parameters, etc)
	 * Note: only used in the case of expressions (not factors) */
	
	public void Action51(Token token) throws CompilerError 
	{
		/* get all the parameters on the parameters stack */
		Stack<SymbolTableEntry> parameters = new Stack<>();
		while (SemanticStack.peek() instanceof SymbolTableEntry && (((SymbolTableEntry) SemanticStack.peek()).IsArray() ||
				((SymbolTableEntry) SemanticStack.peek()).IsConstant() ||
				((SymbolTableEntry) SemanticStack.peek()).IsVariable())) 
		{
			/* pushed onto parameters stack in reverse order of Semantic stack */
			parameters.push((SymbolTableEntry) SemanticStack.pop());
		}
		EType etype = (EType) SemanticStack.pop();
		ProcedureEntry id = (ProcedureEntry) SemanticStack.pop();
		
		if (id.GetName().equals("read") ||
				id.GetName().equals("write")) 
		{
			SemanticStack.push(id); /* replace everything on sem stack */
			SemanticStack.push(etype);
			while (!parameters.empty()) 
			{
				SemanticStack.push(parameters.pop()); /* store all the parameters */
			}
			if (id.GetName().equals("read")) 
			{
				execute(ActionNumber.num51READ, token);
			} 
			else 
			{   /* if id is 'write' */
				execute(ActionNumber.num51WRITE, token);
			}
		} 
		else 
		{   
			int numParams = ParamCount.pop();
			if (numParams != id.GetNumberOfParameters()) 
			{
				throw SemanticError.ReturnError(7, CompilerError.GetErrorStream().GetFileLine());
			}
			while (!parameters.isEmpty()) 
			{
				/* generate param TVI code for all parameters */
				Gen("param", parameters.pop().GetName());
				LocalMemory++;
			}
			/* make call to procedure with the number of parameters in TVI code */
			GenNoAddress("call", id.GetName(), Integer.toString(numParams));
			ParamStack.pop(); /* reinitialize */
			NextParam = 0;
		}
	}
	
	/* Handles "read(...)" procedures TVI code generation with parameters */
	
	public void Action51READ(Token token) throws CompilerError 
	{
		/* For every param on the stack in reverse order : */
		Stack<SymbolTableEntry> parameters = new Stack<>();
		while (SemanticStack.peek() instanceof SymbolTableEntry && ((SymbolTableEntry) SemanticStack.peek()).IsVariable())
		{
			/* store all parameters */
			parameters.push((SymbolTableEntry) SemanticStack.pop());
		}
		
		while (!parameters.isEmpty()) 
		{
			SymbolTableEntry id = parameters.pop();
			if (id.GetType() == TokenType.REAL) 
			{
				Gen("finp", id.GetName()); /* input TVI code */
			} 
			else 
			{
				Gen("inp", id.GetName()); /* input TVI code */
			}
		}
		EType etype = (EType) SemanticStack.pop();
		SymbolTableEntry id = (SymbolTableEntry) SemanticStack.pop();
		ParamCount.pop();
	}
	
	/* Handles the write(...) TVI code generation with multiple parameters */
	
	public void Action51WRITE(Token token) throws CompilerError 
	{
		/* for each parameter on the stack, in reverse order: */
		Stack<SymbolTableEntry> parameters = new Stack<>();
		while (SemanticStack.peek() instanceof SymbolTableEntry && 
				(((SymbolTableEntry) SemanticStack.peek()).IsConstant() ||
				((SymbolTableEntry) SemanticStack.peek()).IsVariable())) 
		{
			parameters.push((SymbolTableEntry) SemanticStack.pop());
		}
		
		while (!parameters.empty()) 
		{
			SymbolTableEntry id = parameters.pop();
			if (id.IsConstant()) 
			{
				if (id.GetType() == TokenType.REAL) 
				{
					GenGenGen("foutp", id.GetName()); /* output TVI code */
				} 
				else /* int */
				{
					GenGenGen("outp", id.GetName());  /* output TVI code */
				}
			}
			else 
			{
				/* id is a variable entry */
				GenGenGen("print", "\"" + id.GetName() + " =\"");
				if (id.GetType() == TokenType.REAL) 
				{
					Gen("foutp", id.GetName());
				} 
				else 
				{
					Gen("outp", id.GetName()); /* int */
				}
			}
			Gen("new1"); /* newline */
		}
		EType etype = (EType) SemanticStack.pop();
		SymbolTableEntry id = (SymbolTableEntry) SemanticStack.pop();
		ParamCount.pop();
	}
	
	/* Handles generation of TVI code function of simple identifier function
	 * without longer expression (ie, factor with no actual parameters) */
	
	public void Action52(Token token) throws CompilerError 
	{
		EType etype = (EType) SemanticStack.pop();
		SymbolTableEntry id = (SymbolTableEntry) SemanticStack.pop();
		if (!id.IsFunction()) 
		{
			throw SemanticError.ReturnError(8, CompilerError.GetErrorStream().GetFileLine());
		} 
		if (id.GetNumberOfParameters() > 0) 
		{   /* can't have any parameters */
			throw SemanticError.ReturnError(7, CompilerError.GetErrorStream().GetFileLine());
		}
		GenNoAddress("call", id.GetName(), Integer.toString(0));
		VariableEntry temp = Create(GenTempVarName(), id.GetType());
		Gen("move", id.GetResult().GetName(), temp.GetName());
		SemanticStack.push(temp);
		SemanticStack.push(null);
	}
	
	/* Handles the storage on semantic  stack of RHS assignment operations 
	 * for function in the current local scope 
	 * (eg: in function gcd(...):  gcd := something */
	
	public void Action53(Token token) throws SemanticError 
	{
		EType etype = (EType) SemanticStack.pop();
		SymbolTableEntry id = (SymbolTableEntry) SemanticStack.pop();
		if (id.IsFunction()) 
		{	
			if (id != CurrentFunction) 
			{
				throw SemanticError.ReturnError(5, CompilerError.GetErrorStream().GetFileLine());
	        }	    
	        SemanticStack.push(id.GetResult());
	        SemanticStack.push(EType.ARITHMETIC);   
		} 
		else 
		{
			SemanticStack.push(id);
			SemanticStack.push(etype);
		}
	}	
	
	/* Checks the validity of procedure declaration with
	 * parameters  */
	
	public void Action54(Token token) throws SemanticError 
	{
		EType etype = (EType) SemanticStack.pop();
		SymbolTableEntry id = (SymbolTableEntry) SemanticStack.peek();
		SemanticStack.push(etype);
		
		if (!id.IsProcedure()) 
		{
			throw SemanticError.ReturnError(5, CompilerError.GetErrorStream().GetFileLine());
		}
	}
	
	/* Last action to be executed from the Parse Stack: backpatches
	 * global store and global memory with correct addresses and frees memory
	 * used in program */
	
	public void Action55(Token token) 
	{
		Backpatch(GlobalStore, GlobalMemory);
		GenGenGen("free", Integer.toString(GlobalMemory));
		Gen("PROCEND");
	}
	
	/* Generates procedure begins and initial memory allocation 
	 * (to be backpatched later) for procedure */
	
	public void Action56(Token token) throws CompilerError 
	{
		Gen("PROCBEGIN", "main");
		GlobalStore = Quads.GetNextQuad();
		Gen("alloc", "_");
	}
	
	/*===================================================================================================
	==========================*==*==*======Intermediate Code Generation======*==*==*=====================
	==========================================Getters and Setters======================================*/
	
	/*Gen(...) function generates intermediate TVI code */
	
	public Quadruple Gen(String x) 
	{
		Quadruple quad = new Quadruple(x);
		quad = new Quadruple(x);
		Quads.AddQuad(quad);
		return quad;	
	}
	
	/* Gen(...) function that handles "param _" TVI opcode */
	
	public Quadruple Gen(String x, String y) throws CompilerError
	{
		Quadruple quad;
		String address = "";
		String prefixy;
	
		if (IsSpecialString(y)) /* if true, do not produce address int, just supply string argument */
		{
			address = y;
		} 
		else if (LookupID(y) != null) 
		{
			if (x.equals("param")) 
			{
				prefixy = GetParamPrefix(LookupID(y));
				address = prefixy + Integer.toString(GetSTEAddress(LookupID(y)));
			}
			else
			{
				prefixy = GetSTEPrefix(LookupID(y));
				address = prefixy + Integer.toString(GetSTEAddress(LookupID(y)));
			}
		}
		quad = new Quadruple(x, address);
		Quads.AddQuad(quad);  /* store intermediate code as Quad */
		return quad;
	}
	
	/* Gen(...) function with two addresses in TVI opcode */ 
	
	public Quadruple Gen(String x, String y, String z) throws CompilerError 
	{
		Quadruple quad;
		String address = "";
		String address2 = "";
		String prefixy;
		
		if (IsSpecialString(y)) 
		{
			address = y;
		} 
		else if (LookupID(y) != null) 
		{
			prefixy = GetSTEPrefix(LookupID(y));
			address = prefixy + Integer.toString(GetSTEAddress(LookupID(y)));
		}
		
		if (IsSpecialString(z)) 
		{
			address2 = z;
		}
		else if (LookupID(z) != null) 
		{
			prefixy = GetSTEPrefix(LookupID(z));
			address2 = prefixy + Integer.toString(GetSTEAddress(LookupID(z)));
		}		
		quad = new Quadruple(x, address, address2);
		Quads.AddQuad(quad);
		return quad;
	}
	
	/* Gen(..) function with three address format */
	
	public Quadruple Gen(String x, String y, String z, String a) throws CompilerError 
	{
		Quadruple quad = new Quadruple(x, y, z, a);
		String address = "";
		String address2 = "";
		String address3 = "";
		String prefixy;
		
		if (IsSpecialString(y)) 
		{
			address = y;
		}
		else if (LookupID(y) != null) 
		{
			prefixy = GetSTEPrefix(LookupID(y));
			address = prefixy + Integer.toString(GetSTEAddress(LookupID(y)));
		}
		
		if (IsSpecialString(z)) {
			address2 = z;
			
		} else if (LookupID(z) != null) {
			prefixy = GetSTEPrefix(LookupID(z));
			address2 = prefixy + Integer.toString(GetSTEAddress(LookupID(z)));
		} 
		
		
		if (IsSpecialString(a)) {
			address3 = a;
		} else if (LookupID(a) != null) {
			prefixy = GetSTEPrefix(LookupID(a));
			address3 = prefixy + Integer.toString(GetSTEAddress(LookupID(a)));
		}
		
		quad = new Quadruple(x, address, address2, address3);
		Quads.AddQuad(quad);
		return quad;
	}
	
	/* Gen(..) function that creates two String TVI code */
	
	public Quadruple GenGenGen(String x, String y) {
		Quadruple quad;
		quad = new Quadruple(x,  y);
		Quads.AddQuad(quad);
		return quad;
	}
	
	/* Gen(...) function used by GetSTEAddress
	 * Last field is the only address field   */
	
	public Quadruple GenGen(String x, String y, String z) throws CompilerError 
	{
		// used by GetSTEAddress
		Quadruple quad;
		String address = null;
		String prefix;

		if (LookupID(z) != null) 
		{
			prefix = GetSTEPrefix(LookupID(z));
			address = prefix + Integer.toString(GetSTEAddress(LookupID(z)));
		}
		// y is a constant
		quad = new Quadruple(x, y, address);
		Quads.AddQuad(quad);
		return quad;
	}
	
	/* Gen(...) function that returns no addresses and creates
	 * all String argument TVI code */
	
	public Quadruple GenNoAddress(String x, String y, String z) 
	{
		Quadruple quad;
		quad = new Quadruple(x, y, z);
		Quads.AddQuad(quad);
		return quad;
	}
	
	/* Helper function for Gen(...)
	   if true: don't return address for String argument */
	
	public boolean IsSpecialString(String x) 
	{
		if (x.equals("main") || x.equals("_")) 
		{
			return true;
		} 
		else if (LookupID(x) != null && LookupID(x).IsConstant()) 
		{
			return false;
		} 
		else 
		{ 
			return false;
		}
	}
	
	/* Checks the type (real or int) of two number-valued IDs, different
	 * TVI code is generated depending on ID types in multiple actions */
	
	public int TypeCheck(SymbolTableEntry x, SymbolTableEntry y) 
	{
		SymbolTableEntry entryX = LookupID(x);
		SymbolTableEntry entryY = LookupID(y);
		
		if (entryX.GetType().equals(TokenType.INTEGER)) 
		{
			if (entryY.GetType().equals(TokenType.INTEGER)) 
			{
				return 0;
			}
			if (entryY.GetType().equals(TokenType.REAL)) 
			{
				return 3;
			}
		} 
		else if (entryX.GetType().equals(TokenType.REAL)) 
		{
			if (entryY.GetType().equals(TokenType.INTEGER)) 
			{
				return 2;
			}
			if (entryY.GetType().equals(TokenType.REAL)) 
			{
				return 1;
			}
		}
		
		System.out.println("Type Check returned null");
		return -1; /* type check is invalid */
	}
	
	/* inserts a new variable entry into correct symbol table and
	 * associates entry with a valid memory address */
	
	public VariableEntry Create(String name, TokenType type) throws CompilerError 
	{
		VariableEntry varEntry = new VariableEntry();
		varEntry.SetType(type);
		varEntry.SetName(name);
		
		if (Global) 
		{
			varEntry.SetAddress(GlobalMemory); 
			GlobalMemory++;
			GlobalTable.Insert(varEntry.GetName(), varEntry);
		} 
		else 
		{
			varEntry.SetAddress(LocalMemory);
			LocalMemory++;
			LocalTable.Insert(varEntry.GetName(), varEntry);
		}
		TempVarCounter++;
		return varEntry;
	}
	
	/* Fills in created "temp" variables with distinct name in TVI code */
	
	public String GenTempVarName() 
	{
		String temp = "temp";
		return temp.concat(Integer.toString(TempVarCounter));
	}
	
	/* Fills in LocalMemory and GlobalMemory store after other TVI code is generated*/
	
	public void Backpatch(int i, int x) 
	{
		Quads.SetField(i, 1, Integer.toString(x));
	}
	
	/* Fills in "goto _" with address or "alloc _" with memory allocation
	   after other TVI code is generated  */
	
	public void Backpatch(List<Integer> num, int x) 
	{
		for (Integer i : num) 
		{
			if (Quads.GetField(i, 0).equals("goto")) 
			{
				Quads.SetField(i, 1, Integer.toString(x));
			}
			else 
			{
				Quads.SetField(i, 3, Integer.toString(x));
			}
		}
	}
	
	/* Used to make list of false and true cases in boolean expression 
	 * loop cases */
	
	public List<Integer> MakeList(int i)
	{
		List<Integer> list = new ArrayList<Integer>();
		list.add(i);
		return (List<Integer>) list;
	}
	
	public List<Integer> Merge(List<Integer> list1, List<Integer> list2)
	{
		list1.addAll(list2);
		return list1;
	}
	
	/* Looks up ID of token entry in local, global, and constant tables */
	
	public SymbolTableEntry LookupID(Token id) 
	{
		SymbolTableEntry ste = LocalTable.LookUp(id.GetValue().toLowerCase());
		if (ste == null) 
		{
			ste = GlobalTable.LookUp(id.GetValue());
			if (ste == null) 
			{
				ste = GlobalTable.LookUp(id.GetValue().toLowerCase());
			}
		}	
		if (ste == null) 
		{
			ste = ConstantTable.LookUp(id.GetValue());
			if (ste == null) 
			{
				ste = ConstantTable.LookUp(id.GetValue().toLowerCase());
			}
		} 
		return ste;
	}
	
	/* Looks up ID of symbol table entry in local, global, and constant tables*/
	
	public SymbolTableEntry LookupID(SymbolTableEntry id) 
	{
		SymbolTableEntry ste = LocalTable.LookUp(id.GetName().toLowerCase());
		if (ste == null) 
		{
			ste = GlobalTable.LookUp(id.GetName());
			if (ste == null) 
			{
				ste = GlobalTable.LookUp(id.GetName().toLowerCase());
			}
		} 
		if (ste == null) 
		{
			ste = ConstantTable.LookUp(id.GetName());
			if (ste == null) 
			{
				ste = ConstantTable.LookUp(id.GetName().toLowerCase());
			}
		}
		return ste;
	}
	
	/* Looks up ID of token string (id) in global, local, and constant tables */
	
	public SymbolTableEntry LookupID(String id) 
	{
		SymbolTableEntry ste = LocalTable.LookUp(id.toLowerCase());
		
		if (ste == null) 
		{
			ste = GlobalTable.LookUp(id);
			if (ste == null) 
			{
				ste = GlobalTable.LookUp(id.toLowerCase());
			}
		} 
		if (ste == null) 
		{
			ste = ConstantTable.LookUp(id);
			if (ste == null) 
			{
				ste = ConstantTable.LookUp(id.toLowerCase());
			}
		}
		return ste;
	}
	
	/* gets TVI format of operator code based on token type */
	
	public String GetOpCode(Token token) 
	{
		String opCode = "";
		String value = token.GetValue();
		
		switch (token.GetType()) 
		{
		case ADDOP: 
			if (value.equals("1")) 
			{
				opCode = "add";
			} else if (value.equals("2")) 
			{
				opCode = "sub";
			} break;
		case MULOP: 
			if (value.equals("1")) 
			{
				opCode = "mul";
			} else if (value.equals("3")) 
			{
				opCode = "div";
			} else if (value.equals("2")) 
			{
				// for '/' op
				opCode = "div";
			}
			break;
		case RELOP:
			if (value.equals("3")) 
			{
				opCode = "blt";
			} else if (value.equals("4")) 
			{
				opCode = "bgt";	
			} else if(value.equals("2")) 
			{
				opCode = "bne";
			} else if (value.equals("5")) 
			{
				opCode = "ble";
			} else if (value.equals("6")) 
			{
				opCode = "bge";
			} else if (value.equals("1")) 
			{
				opCode = "beq";
			} 
			break;
		default: opCode = null;
		}
		
		return opCode;
	}
	
	/* Returns address of symbol table entry */
	
	public int GetSTEAddress(SymbolTableEntry ste) throws CompilerError 
	{
		if (ste.IsArray() || ste.IsVariable()) 
		{
			return ste.GetAddress();
		}
		
		if (ste.IsConstant()) 
		{
			VariableEntry temp = Create(GenTempVarName(), ste.GetType());
			GenGen("move", ste.GetName(), temp.GetName());
			return temp.GetAddress();
		}
		return -1;
	}
	
	/* returns proper TVI address prefix depending on
	   whether ste is in local, constant, or global symbol table */
	
	public String GetSTEPrefix(SymbolTableEntry ste) 
	{
		if (Global) 
		{
			return "_";
		} 
		else 
		{
			SymbolTableEntry entry = LocalTable.LookUp(ste.GetName());
			SymbolTableEntry entry1 = ConstantTable.LookUp(ste.GetName());
			if (entry == null) 
			{
				if (entry1 != null) 
				{
					// if ste does exist in Constant table:
					return "%";
				}
				// entry is a global var:
				return "_";
			} 
			else 
			{  
				if (ste.IsParameter()) 
				{
					return "^%";  /* dereference address location */
				} 
				else 
				{
					return "%";  /* address is in local scope */
				}
			}
		}
	}
	
	/* returns the proper prefix for a parameter 
	   used when generating code with the opcode "param". */
	
	public String GetParamPrefix(SymbolTableEntry param) 
	{
		if (Global) 
		{
			return "@_";
		} 
		else 
		{
			/* local */
			if (param.IsParameter()) 
			{
				return "%";
			} 
			else 
			{
				return "@%";
			}
		}
	}
	
	public Quadruples GetQuad() 
	{
		return Quads;
	}
	
	public Stack<Object> GetSemanticStack()
	{
		return SemanticStack;
	}	
	
	/* prints contents of the SemanticStack */
	
	public void SemanticDump() 
	{
		boolean t = false;
		if (t) 
		{
			System.out.println(" >>--- " + SemanticCounter + "---<<");
			SemanticCounter++;
			System.out.println("SEM STACK: " + SemanticStack.toString());
		}
	}
}
