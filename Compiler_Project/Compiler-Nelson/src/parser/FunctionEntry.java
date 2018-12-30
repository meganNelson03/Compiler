package parser;
import java.util.ArrayList;
import java.util.List;

public class FunctionEntry extends SymbolTableEntry 
{
	/* A Function Entry is a type of Symbol Table Entry which handles 
	 * the storage of functions which varying numbers of parameters, parameter
	 * types, and stores the result of the function */
	
	private String Name;
	private int NumberOfParameters;
	private ArrayList<SymbolTableEntry> ParameterInfo;
	private VariableEntry Result;
	private TokenType Type;
	@SuppressWarnings("unused")
	private TokenType ResultType;
	
	public FunctionEntry(String name, VariableEntry result) 
	{
		this.Name = name;
		this.Result = result;
		this.ParameterInfo = new ArrayList<SymbolTableEntry>();
	}
	
	public FunctionEntry(String name, int numberOfParameters, List<SymbolTableEntry> parameterInfo, VariableEntry result)
	{
		this.Name = name;
		this.NumberOfParameters = numberOfParameters;
		this.ParameterInfo = new ArrayList<SymbolTableEntry>();
		this.Result = result;
	}
	
	public boolean IsFunction() 
	{
		return true;
	}
	
	public String GetName() 
	{
		return Name;
	}
	
	public VariableEntry GetResult() 
	{
		return Result;
	}
	
	public void SetResultType(TokenType resultType)
	{
		this.Result.SetType(resultType);
	}
	
	public TokenType GetType() 
	{
		return Type;
	}
	
	public void SetType(TokenType type) 
	{
		this.Type = type;
	}

	public int GetNumberOfParameters() 
	{
		return NumberOfParameters;
	}
	
	public void SetNumberOfParameters(int p)
	{
		NumberOfParameters = p;
	}
	
	public void AddParameter(SymbolTableEntry ste) 
	{
		ParameterInfo.add(ste);
	}
	
	public ArrayList<SymbolTableEntry> GetParameterInfo()
	{
		return this.ParameterInfo;
	}
	
}
