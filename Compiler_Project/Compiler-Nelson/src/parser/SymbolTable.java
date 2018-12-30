package parser;
import java.util.Hashtable;
@SuppressWarnings("unused")

public class SymbolTable {
	
	/* Symbol Table management routines are accessed
	 * by the lexical analyzer and by the semantic routines.
	 * Symbol tables are made (the constant, global, and 
	 * local tables) to store information which can be 
	 * recalled to look up names in the body of the program.
	 * */
	
	private int Size;
	private Hashtable<String, SymbolTableEntry> Hash;
	
	public SymbolTable() 
	{
		Hash = new Hashtable<String, SymbolTableEntry>();
	}
	
	public SymbolTable(int size) 
	{
		this.Size = size;
		Hash = new Hashtable<String, SymbolTableEntry>();
	}
	
	public boolean HasEntry(String index) 
	{
		if (Hash.containsKey(index)) 
		{
			return true;
		}
		else 
		{
			return false;
		}
	}
	
	public SymbolTableEntry LookUp(String index) 
	{
		if (HasEntry(index)) 
		{
			return Hash.get(index);
		}
		else 
		{
			return null;
		}
		
	}
	
	public void Insert(String index, SymbolTableEntry entry) throws CompilerError 
	{
		if (HasEntry(index) == false) 
		{
			Hash.put(index, entry);
		} else {
			//throw SymbolTableError.ReturnError();
		}
	}
	
	public int SizeSymbolTable() 
	{
		return Hash.size();
	}
	
	/* Prints the contents of the table */
	
	public void DumpTable() 
	{
		for (String entry : Hash.keySet()) 
		{
			System.out.println("Name: " + entry + ", Symbol: " + Hash.get(entry));
			System.out.println("-----------------------------------------------");
		}
	}
}
