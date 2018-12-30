package parser;
import java.io.IOException;

public class Main 
{
	/* Main creates a new compiler object and calls Compile() on it.*/
	
	public static void main(String[] args) throws IOException, CompilerError 
	{
		try 
		{
			Compiler compiler = new Compiler();
			compiler.Compile();
		} 
		catch (CompilerError err) 
		{
			throw CompilerError.ReturnError();
		}
	}
}
