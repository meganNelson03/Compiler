package parser;

public class ConstantEntry extends SymbolTableEntry 
{
	/* A constant entry is a type of Symbol Table entry that handles the storage
	 * of constant values (real or int) 
	 */
	
	private String Name;
	private TokenType Type;
	
	public ConstantEntry() 
	{
	}
	
	public ConstantEntry(String name, TokenType type) 
	{
		this.Name = name;
		this.Type = type;
	}
	
	public boolean IsConstant() 
	{
		return true;
	}
	
	public TokenType GetType() 
	{
		return Type;
	}
	
	public String GetName() 
	{
		return Name;
	}
}
