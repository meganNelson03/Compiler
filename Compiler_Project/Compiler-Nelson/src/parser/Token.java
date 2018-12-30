package parser;

public class Token 
{	
	/* A Token is a type-value pair representing 
 	   atomized units of information aquired from
 	   the Character Stream.
 	 */
	
	private TokenType type;
	private String value;
	
	public Token() 
	{	
	}
	
	public Token(TokenType type, String value) 
	{
		this.type = type;
		this.value = value;
	}
	
	public void SetType(TokenType type) 
	{
		this.type = type;
	}

	public TokenType GetType() 
	{
		return type;
	}
	
	public void SetValue(String value) 
	{
		this.value = value;
	}
	
	public String GetValue() 
	{
		return value;
	}
	
	public String ToString() 
	{
		String tokenPrint;
		tokenPrint = "< " + type + " , " + value + " >";
		return tokenPrint;
	}
	
	public int TokenToInt() 
	{
		return Integer.parseInt(value);
	}

	public boolean IsToken() 
	{
		return true;
	}
		
}
