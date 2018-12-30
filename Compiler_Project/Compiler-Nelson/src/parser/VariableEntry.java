package parser;

public class VariableEntry extends SymbolTableEntry 
{
	/* Variable entries are a type of Symbol table entry
	 * whose value can be changed by assignment.
	 */
	
	private String Name;
	private TokenType Type;
	private boolean Result;
	private boolean Parameter;
	private int Address;
	
	public VariableEntry() 
	{
	}
	
	public VariableEntry(String name, int address, TokenType type) 
	{
		this.Name = name;
		this.Address = address;
		this.Type = type;
	}
	
	public boolean IsVariable() 
	{
		return true;
	}

	public boolean IsParameter() 
	{
		if (Parameter) 
		{
			return true;
		} 
		else 
		{
			return false;
		}
	}
	
	public int GetAddress() 
	{
		return Address;
	}
	
	public TokenType GetType() 
	{
		return Type;
	}
	
	public void SetType(TokenType type) 
	{
		this.Type = type;
	}
	
	public String GetName() 
	{
		return Name;
	}
	
	public void SetResult() 
	{
		Result = !Result;
	}

	public void SetParameter() 
	{
		Parameter = !Parameter;	
	}

	public void SetAddress(int address) 
	{
		this.Address = address;	
	}

	public void SetName(String name) 
	{
		Name = name;
	}
}
	
