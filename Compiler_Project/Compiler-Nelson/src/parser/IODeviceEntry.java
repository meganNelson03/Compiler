package parser;

public class IODeviceEntry extends SymbolTableEntry 
{
	/* IODevices are of type Symbol table entry and are input/output
	 * devices, used to store variable names.
	 */
	
	private String Name;
	
	public IODeviceEntry(String name) 
	{
		this.Name = name;
	}
	
	public String GetName() 
	{
		return Name;
	}
	
	public void SetName(String name) 
	{
		this.Name = name;
	}
	
}
