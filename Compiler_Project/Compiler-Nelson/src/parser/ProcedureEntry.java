package parser;
import java.util.ArrayList;


public class ProcedureEntry extends SymbolTableEntry 
{
	/* A procedure entry is a type of symbol table entry which stores
	 * information about procedures (such as write(...) or read(...)), such
	 * as parameter types and the number of parameters. 
	 * */
	
	private String Name;
	private int NumberOfParameters;
	private ArrayList<SymbolTableEntry> ParameterInfo;
	
	public ProcedureEntry(String name) 
	{
		this.Name = name;
		this.ParameterInfo = new ArrayList<SymbolTableEntry>();
	}
	
	public ProcedureEntry(String name, int numberOfParameters, ArrayList<SymbolTableEntry> parameterInfo)
	{
		this.Name = name;
		this.NumberOfParameters = numberOfParameters;
		this.ParameterInfo = parameterInfo;
	}
	
	public String GetName() 
	{
		return Name;
	}
	
	public boolean IsProcedure() 
	{
		return true;
	}
	
	public void AddParameter(SymbolTableEntry ste) 
	{
		ParameterInfo.add(ste);
	}

	public ArrayList<SymbolTableEntry> GetParameterInfo() 
	{
		return ParameterInfo;
	}

	public int GetNumberOfParameters() 
	{
		return NumberOfParameters;
	}
	
	public void SetNumberOfParameters(int n) 
	{
		this.NumberOfParameters = n;
	}
}
