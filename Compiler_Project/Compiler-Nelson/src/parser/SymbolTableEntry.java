package parser;
import java.util.ArrayList;

public class SymbolTableEntry 
{	
	/* Parent class of a suite of child classes which can be
	 * symbol table of entries of type: Array, Constant,
	 * Function, Procedure, Variable and IODevice     */
	
	private boolean Parameter;
	
	public boolean IsVariable() 
	{
		return false;
	}
	
	public boolean IsProcedure() 
	{
		return false;
	}
	
	public boolean IsFunction() 
	{
		return false;
	}
	
	public boolean IsFunctionResult() 
	{
		return false;
	}
	
	public boolean IsParameter() 
	{
		return false;
	}
	
	public boolean IsArray() 
	{
		return false;
	}
	
	public boolean IsReserved() 
	{
		return false;
	}
	
	public boolean IsConstant() 
	{
		return false;
	}
	
	public int GetAddress() 
	{
		return -1;
	}
	
	public TokenType GetType() 
	{
		return null;
	}
	
	public String GetName() 
	{
		return null;
	}
	
	public VariableEntry GetResult() 
	{
		return null;
	}

	public void SetParameter() 
	{
		Parameter = !Parameter;
	}

	public int GetNumberOfParameters() 
	{
		return -1;
	}

	public ArrayList<SymbolTableEntry> GetParameterInfo() 
	{
		return null;
	}
	public void AddParameter(SymbolTableEntry var) 
	{	
	}

	public void AddParameter(ArrayEntry var) 
	{		
	}
	
	public boolean IsToken() 
	{
		return false;
	}

	public void SetNumberOfParameters(int numParams2) 
	{
	}

	public TokenType GetResultType() 
	{
		return null;
	}
	
	
}
