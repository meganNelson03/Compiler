package parser;
import java.util.Hashtable;

public class KeywordReference {
	
	/* Contains two lists of key words (reserved words) in the Vascal language.
	 * When a word is recognized as a keyword in the Tokenizer (if the
	 * identifier exists in Ht or Ht2): 
	 * token <IDENTIFIER, keyword> -- converted to --> <KEYWORD, null> */

	private Hashtable<Integer, String> Ht;
	private Hashtable<String, Token> Ht2;
	
	protected KeywordReference() 
	{
		Ht = new Hashtable<Integer, String>();
		Ht2 = new Hashtable<String, Token>();
		Ht.put(1, "PROGRAM");		
		Ht.put(2, "BEGIN");				
		Ht.put(3, "END");				
		Ht.put(4, "VAR");			    
		Ht.put(5, "FUNCTION");			
		Ht.put(6, "PROCEDURE");			
		Ht.put(7, "RESULT");
		Ht.put(8, "INTEGER");
		Ht.put(9, "REAL");
		Ht.put(10, "ARRAY");
		Ht.put(11, "OF");
		Ht.put(12, "IF");
		Ht.put(13, "THEN");
		Ht.put(14, "ELSE");
		Ht.put(15, "WHILE");
		Ht.put(16, "DO");
		Ht.put(17, "NOT");
		
		Ht2.put("MOD", new Token(TokenType.MULOP, "4"));  
		Ht2.put("DIV",  new Token(TokenType.MULOP, "3")); 
		Ht2.put("AND",  new Token(TokenType.MULOP, "5")); 
		Ht2.put("OR", new Token(TokenType.ADDOP, "3"));   
	}
	
	public Hashtable<String, Token> GetHashtable2()
	{
		return Ht2;
	}
	
	public Hashtable<Integer,String> GetHashtable() 
	{
		return Ht;
	}	
}
