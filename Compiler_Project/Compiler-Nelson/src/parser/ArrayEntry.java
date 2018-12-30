package parser;

public class ArrayEntry extends SymbolTableEntry 
{
	/* Array Entry is of type Symbol Table Entry and stores information about
	 * array assignments (including name, address, bounds, and type of entry [real or int])
	 */
	
	private String Name;
	private TokenType Type;
	private int UpperBound;
	private int LowerBound;
	private int Address;
	private boolean Parameter;
	
	public ArrayEntry(String name, int address, TokenType type, int upperBound, int lowerBound) 
	{
		this.Name = name;
		this.Address = address;
		this.Type = type;
		this.UpperBound = upperBound;
		this.LowerBound = lowerBound;	
	}
	
	public boolean IsArray() 
	{
		return true;
	}
	
	public int GetAddress() 
	{
		return Address;
	}
	
	public TokenType GetType() 
	{
		return Type;
	}
	
	public String GetName() 
	{
		return Name;
	}
	
	public int GetLowerBound() 
	{
		return LowerBound;
	}

	public int GetUpperBound() 
	{
		return UpperBound;
	}
	
	public void SetParameter() 
	{
		Parameter = !Parameter;
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

	public void SetAddress(int address) 
	{
		Address = address;	
	}

	public void SetType(TokenType type) 
	{
		Type = type;
	}

	public void SetUpperBound(int upperBound) 
	{
		UpperBound = upperBound;
	}

	public void SetLowerBound(int lowerBound) 
	{
		LowerBound = lowerBound;
		
	}
}


