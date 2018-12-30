package parser;
import java.io.*;

public class Tokenizer
{
	/* Tokenizer class takes in a Character Stream from some input file, reads the characters
	 * in the file char-by-char, and assigns Token Types and Values depending on the stream
	 * (eg: function gcd (input : integer) would produce these tokens: <FUNCTION, null>
	 *  <IDENTIFIER, gcd> <LPAREN, null> <ID, input> <COLON, null> <INTEGER, null> <RPAREN, null>)
	 * Lexer: Isolates tokens from input containing source file */
	
	private Token LastToken;
	private Token T;
	private String Buffer;
	private CharacterStream CharStream;
	private KeywordReference Keywords;
	private int MAX_IDENTIFIER_SIZE = 32;
	private int MAX_CONSTANT_SIZE = 64;
	private  final String VALID_CHARS =
			"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890" +
					".,;:<>/*[]+-=()}{\t ";
	
	public Tokenizer(CharacterStream cs) throws IOException
	{
		CharStream = cs;
		Keywords = new KeywordReference();
	}
	
	/* calls appropriate method based on character in the stream:
	 * either read symbol, read ID, read number, or creates EOF token*/
	
	public  Token GetNextToken() throws IOException, LexicalError 
	{
		Token btoken = new Token();
		Buffer = "";
		char ch = CharStream.GetChar();
		
		
		/* if EOF is reached, return EOF token */
		if (CharStream.EndOfFile() && ch == ' ') 
		{
			btoken.SetType(TokenType.ENDOFFILE);
			btoken.SetValue(null);
			return btoken;
		}
		
		/* skips comments and blank spaces */
		while (CharStream.IsBlank(ch) || ch == '{') 
		{
			CharStream.SkipWhitespace(ch);
			ch = CharStream.GetChar();
		}
		
		if (Character.isLetter(ch)) 
		{
			btoken = ReadID(ch);
		} 
		else if (Character.isDigit(ch)) 
		{
			btoken = ReadNumber(ch);
		} 
		else if (CharStream.EndOfFile() && ch != '.')
		{
			btoken = new Token(TokenType.ENDOFFILE, null);		
		}
		else 
		{
			btoken = ReadSymbol(ch);
		} 
	
		LastToken = btoken;	
		return btoken; 
	
	}
	
	/* If char is letter: Read ID. Keep adding to identifier as long as
	 * next character is letter or number. */
	
	protected  Token ReadID(char nextChar) throws IOException, LexicalError 
	{
		Token etoken = new Token();
		while (Character.isDigit(nextChar) || Character.isLetter(nextChar)) 
		{
			Buffer = Buffer + nextChar;
			nextChar = CharStream.GetChar();	
		}

		if (!(CharStream.IsBlank(nextChar)))
		{	/* Go back to previous character in the stream (push curr character onto the stack) */
			CharStream.PushBack(nextChar);
		} 
		/* Check keyword bank to see if ID is reserved word (eg: if, else, etc) */
		etoken = ConvertToKeyword(Buffer);
		if (Buffer.length() > MAX_IDENTIFIER_SIZE) 
		{
			throw LexicalError.ReturnError(2, CharStream.GetFileLine());
		}
		return etoken;
	}
	
	/* Reads number in the char stream (comprised of digits), assigns either
	 * INTCONSTANT or REALCONSTANT types, handles E-type numbers by calling EHandler(..) */
	
	protected  Token ReadNumber(char nextChar) throws IOException, LexicalError 
	{
		Token dtoken = new Token();
		char lookAhead;
		
		while (Character.isDigit(nextChar)) 
		{
			Buffer = Buffer + nextChar;
			nextChar = CharStream.GetChar();
		}

		dtoken.SetType(TokenType.INTCONSTANT);
		dtoken.SetValue(Buffer.toString());

		switch (nextChar) 
		{
		case '.': /* case: decimal point in num*/
			dtoken.SetType(TokenType.REALCONSTANT);
			lookAhead = CharStream.GetChar();
				switch (lookAhead) 
				{
				case '.': /* input is not a num (it's a double-dot), must pushback */
					CharStream.PushBack(nextChar);
					CharStream.PushBack(lookAhead);
					dtoken.SetType(TokenType.INTCONSTANT);
					break;
				default: 
					Buffer = Buffer + nextChar;
					CharStream.PushBack(lookAhead);
					nextChar = CharStream.GetChar();
					while (Character.isDigit(nextChar)) 
					{
						Buffer = Buffer + nextChar;
						nextChar = CharStream.GetChar();		   
					}
					
					dtoken.SetValue(Buffer.toString());
					if (nextChar == 'e')
					{
						Buffer = EHandler(Buffer);
						dtoken.SetValue(Buffer.toString());
						dtoken.SetType(TokenType.REALCONSTANT);
						nextChar = CharStream.GetChar();
					} 
					break;
				} 
				break;
		/* The E-case */		
		case 'e': 
			Buffer = EHandler(Buffer);
			dtoken.SetValue(Buffer.toString());
			dtoken.SetType(TokenType.REALCONSTANT);
			nextChar = CharStream.GetChar();
			break;
		case 'E': 
			Buffer = EHandler(Buffer);
			nextChar = CharStream.GetChar();
			dtoken.SetValue(Buffer.toString());
			dtoken.SetType(TokenType.REALCONSTANT);
			break;
		default: 
			break;	
		}

		if (!(CharStream.IsBlank(nextChar))) 
		{
			CharStream.PushBack(nextChar);
		}
		
		if (Buffer.length() > MAX_CONSTANT_SIZE) 
		{
			throw LexicalError.ReturnError(3, CharStream.GetFileLine());
		}
		return dtoken;
	}
	
	/* Reads symbol in the stream and assigns appropriate Token Type */
	
	protected  Token ReadSymbol(char nextChar) throws IOException, LexicalError 
	{
		Token ctoken = new Token();
		char lookAhead;
		
		switch(nextChar) 
		{
		case ',': 
			ctoken.SetType(TokenType.COMMA);
			ctoken.SetValue(null);
			break;
		case '.': 
			ctoken.SetType(TokenType.ENDMARKER);
			ctoken.SetValue(null);
			lookAhead = CharStream.GetChar();
			if (lookAhead == '.') 
			{
				ctoken.SetType(TokenType.DOUBLEDOT);
				ctoken.SetValue(null);
				CharStream.GetChar();
			} 
			else 
			{ 
				CharStream.PushBack(lookAhead);
			}
			break;
		case ':': 
			ctoken.SetType(TokenType.COLON);
			ctoken.SetValue(null);
			lookAhead = CharStream.GetChar();
			
			/* Need lookAhead character to check if the assignment is correct, since
			 * some symbol Tokens are more than one char (ie, :=) */
			
			if (lookAhead == '=') 
			{
				ctoken.SetType(TokenType.ASSIGNOP);
				ctoken.SetValue(null);
			} 
			else 
			{  
				/* Push back the lookahead token (return to prev char) if (lookAhead != '=') */
				CharStream.PushBack(lookAhead);
			}
			break;
		case ';': 
			ctoken.SetType(TokenType.SEMICOLON);
			ctoken.SetValue(null);
			CharStream.GetChar();
			break;
		case '(':
			ctoken.SetType(TokenType.LEFTPAREN);
			ctoken.SetValue(null);
			break;
		case ')': 
			ctoken.SetType(TokenType.RIGHTPAREN);
			ctoken.SetValue(null);
			lookAhead = CharStream.GetChar();
			if (lookAhead == ')' || lookAhead != ' ') 
			{
				CharStream.PushBack(lookAhead);
			}
			break;
		case '[': 
			ctoken.SetType(TokenType.LEFTBRACKET);
			ctoken.SetValue(null);
			break;
		case ']': 
			ctoken.SetType(TokenType.RIGHTBRACKET);
			ctoken.SetValue(null);
			lookAhead = CharStream.GetChar();
			if (lookAhead != ' ' && lookAhead != ']') 
			{
				CharStream.PushBack(lookAhead);
			}
			break;
		case '=': 
			ctoken.SetType(TokenType.RELOP);
			ctoken.SetValue("1");
			break;
		case '<': 
			ctoken.SetType(TokenType.RELOP);
			ctoken.SetValue("3");
			lookAhead = CharStream.GetChar();
			switch (lookAhead) 
			{
				case '>': ctoken.SetValue("2"); break;
				case '=': ctoken.SetValue("5"); break;
				default: CharStream.PushBack(lookAhead);
			}
			break;
		case '>': 
			ctoken.SetType(TokenType.RELOP);
			ctoken.SetValue("4");
			lookAhead = CharStream.GetChar();
			switch (lookAhead) 
			{
				case '=': 
					ctoken.SetValue("6"); break;
				default: 
					CharStream.PushBack(lookAhead); break;
			}
			break;
		case '*': 
			ctoken.SetType(TokenType.MULOP);
			ctoken.SetValue("1");
			break;
		case '/': 
			ctoken.SetType(TokenType.MULOP);
			ctoken.SetValue("2");
			break;
		case '+': 
			if (LastToken.GetType() == TokenType.RIGHTPAREN || LastToken.GetType() == TokenType.RIGHTBRACKET 
				|| LastToken.GetType() == TokenType.IDENTIFIER
				|| LastToken.GetType() == TokenType.INTCONSTANT || LastToken.GetType() == TokenType.REALCONSTANT) 
			{
					ctoken.SetType(TokenType.ADDOP);	
					ctoken.SetValue("1");
			} 
			else 
			{
				ctoken.SetType(TokenType.UNARYPLUS);
				ctoken.SetValue(null);
			}
			break;
		
		case '-': 
			if (LastToken.GetType() == TokenType.RIGHTPAREN || LastToken.GetType() == TokenType.RIGHTBRACKET 
				|| LastToken.GetType() == TokenType.IDENTIFIER
				|| LastToken.GetType() == TokenType.INTCONSTANT || LastToken.GetType() == TokenType.REALCONSTANT) 
			{		
				ctoken.SetType(TokenType.ADDOP);
				ctoken.SetValue("2");
			} 
			else 
			{
				ctoken.SetType(TokenType.UNARYMINUS);
				ctoken.SetValue(null);
			}
			break;
		case '}': 
			throw LexicalError.ReturnError(0, CharStream.GetFileLine());
				
		default: 
			/* Symbol not recognized in the language */ 
			if (VALID_CHARS.indexOf(nextChar) < 0) 
			{
				throw LexicalError.ReturnError(1, CharStream.GetFileLine());
			}	
		}
		return ctoken;
	}
	
	/* Called by ReadNumber(...) in the event that a number
	 * has an 'e' componenent (eg, 2.31e-7) */
	
	protected  String EHandler(String buffer) throws IOException 
	{
		// lookAhead1: gets character after 'e'
		char lookAhead1 = CharStream.GetChar();
		char lookAhead2;
		
		/* if char after 'e' is digit, 
		* append 'e' to num and capture the rest of the digits in the sequence */
		
		if (Character.isDigit(lookAhead1)) 
		{
			buffer = buffer + 'e';
			while (Character.isDigit(lookAhead1)) 
			{
				buffer = buffer + lookAhead1;
				lookAhead1 = CharStream.GetChar();
			}
			CharStream.PushBack(lookAhead1);
		}
		else if (lookAhead1 == '+' || lookAhead1 == '-') 
		{
			lookAhead2 = CharStream.GetChar();
			if (Character.isDigit(lookAhead2)) 
			{
				buffer = buffer + 'e' + lookAhead1;
				while (Character.isDigit(lookAhead2)) 
				{
					buffer = buffer + lookAhead2;
					lookAhead2 = CharStream.GetChar();
				} 
			} 
			else 
			{	
				/* Rest of char in the lookAhead stream are not apart of 
				 of 'e' num sequence, need to pushback characters
				 (eg, e could be a variable, identifier, error, etc) */
				
				CharStream.PushBack(lookAhead2);
				buffer = buffer + 'e' + lookAhead1;
			}
		} 
		else 
		{  
			/* e not apart of expression, pushback lookAhead and e */
			CharStream.PushBack(lookAhead1);
			CharStream.PushBack('e');
		}
		Buffer = buffer;
		return buffer;
	}

	/* Converts identifier String to reserved keyword if identifier is in the 
	 * Keywords list */
	
	protected  Token ConvertToKeyword(String buffer) 
	{
		Token toky = new Token();
		if (Keywords.GetHashtable().containsValue(buffer.toUpperCase())) 
		{
			toky.SetType(TokenType.valueOf(buffer.toUpperCase()));
			toky.SetValue(null);
		} 
		else if (Keywords.GetHashtable2().containsKey(buffer.toUpperCase())) 
		{
			toky.SetType(Keywords.GetHashtable2().get(buffer.toUpperCase()).GetType());
			toky.SetValue(Keywords.GetHashtable2().get(buffer.toUpperCase()).GetValue());
		}
		else 
		{
			toky.SetType(TokenType.IDENTIFIER);
			toky.SetValue(buffer.toString());
		}
		return toky;	
	}

	/* Used to return Token T in Tokenizer*/
	
	public Token GetT()
	{
		return T;
	}
	/* Used to set Token T in tokenizer */
	
	public void SetT(Token t) 
	{
		T = t;
	}
	
	/* Get's the LastToken field in Tokenizer,
	 * referenced by Parser */
	
	public Token GetLastToken() {
		return LastToken;
	}
	
	/* Tests the Tokenizer on input file independently */
	
	public static void main(String[] args) throws IOException, LexicalError 
	{
		/* Note: change the path of lexFile to test different file
		 * Current file: ultimateTest.pas from the Lexical Analyzer phase */
		
		String lexFile = "/Users/megannelson/331-COMPILER/Compiler331-CompleteCompiler-Nelson/src/parser/lexerTest.txt";
		CharacterStream cs = new CharacterStream(lexFile);
		
		Tokenizer tk = new Tokenizer(cs);
		
		tk.SetT(tk.GetNextToken());
		System.out.println(tk.GetT().ToString());
		while (!(tk.GetLastToken().GetType() == TokenType.ENDOFFILE)) 
		{
			tk.SetT(tk.GetNextToken());	
			System.out.println(tk.GetT().ToString());
		} 
	}

}
