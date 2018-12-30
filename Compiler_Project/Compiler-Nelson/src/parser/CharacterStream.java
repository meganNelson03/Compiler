package parser;
import java.io.*;
import java.util.Stack;

public class CharacterStream extends FileNotFoundException 
{
	/* Character stream is responsible for handling the file to be compiled.
	 * Has methods for getting characters from the stream, 
	 * pushes characters back onto a character stack, skips blank spaces and comments, 
	 * and keeps track of current file line  */
	
	private static final long serialVersionUID = 1L;
	private BufferedReader Stream;
	private char Ch;
	private int FileLine = 0;
	private Stack<Character> CharStack;
	
	public CharacterStream() 
	{
		CharStack = new Stack<Character>();
	}
	
	public CharacterStream(String filename) throws FileNotFoundException 
	{
		Stream = new BufferedReader(new FileReader(filename));
		CharStack = new Stack<Character>();
	}
	
	/* Returns current character in the stream. If character is a new line, 
	 * increment file line number and return the character on the next line */
	
	public char GetChar() throws IOException 
	{
		char ch ;	
		if (CharStack.isEmpty()) 
		{
			ch = (char) Stream.read();
		} 
		else 
		{ 
			ch = CharStack.pop();
		}
		if (ch == '\n') 
		{
			FileLine++;
		}
		
		return ch;
	}
	
	/* Skips whitespaces in the input stream. Used by the Tokenizer
	 * in order to find the next meaningful unit to create a new token 
	 */
	
	public void SkipWhitespace(char ch) throws IOException 
	{
		switch (ch) 
		{
		case ' ':  break;
		case '\n': break;
		case '\t': break;
		case '{':  while (ch != '}') 
				   {
					   ch = GetChar();
				   }
				   break;
		}
	}
	
	public boolean IsBlank(char ch) 
	{
		if (ch == ' ' || ch == '\n' || ch == '\t') 
		{
			return true;
		} 
		else 
		{
			return false;	
		}
	}
	
	/* Skips comment of the form : { .... } 
	 * Used by the Tokenizer  */
	
	public void SkipComment() throws IOException 
	{
		while (Ch != '}') 
		{
			Ch = GetChar();
		} 
	}
	
	/* Pushes a character back onto the stream 
	 * so that the character in the input file can be re-read by the Tokenizer */
	
	public void PushBack(char ch) 
	{
		CharStack.push(ch);
	}
	
	/* Pops a character off of the stream */
	
	public char PopStack() 
	{
		char ch = CharStack.pop();
		return ch;
	}
	
	/* Checks when the end of file is reached. 
	 * Referenced by the Tokenizer in order to determine when to stop producing tokens,
	 * and referenced by the Parser in order to check if a full program is syntactically
	 * correct */
	
	public boolean EndOfFile() throws IOException 
	{
		return (!Stream.ready());	
	}
	
	public boolean StackEmpty() 
	{
		return CharStack.empty();
	}
	
	/* Returns the current file line of the input file. Used by error handling 
	 * to return line that an error occurred on 
	 * */
	
	public int GetFileLine() 
	{
		return FileLine;
	}
	
	public BufferedReader GetStream() 
	{
		return Stream;
	}
}
