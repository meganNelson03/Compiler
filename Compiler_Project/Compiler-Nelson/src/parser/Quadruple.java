package parser;

public class Quadruple 
{
	/* The Quadruple class represents one TVI operation.
	 * (ie, "alloc 20", "move 4, _11", etc)  */
	
	private String[] Quader;
	private int QuadSize;
	
	public Quadruple(String one)
	{
		QuadSize = 1;
		Quader = new String[1];
		Quader[0] = one;
	}
	
	public Quadruple(String one, String two) 
	{
		QuadSize = 2;
		Quader = new String[2];
		Quader[0] = one;
		Quader[1] = two;
	
	}
	
	public Quadruple(String one, String two, String three) 
	{	
		QuadSize = 3;
		Quader = new String[3];
		Quader[0] = one;
		Quader[1] = two;
		Quader[2] = three;
	}
	
	public Quadruple(String one, String two, String three, String four) 
	{
		QuadSize = 4;
		Quader = new String[4];
		Quader[0] = one;
		Quader[1] = two;
		Quader[2] = three;
		Quader[3] = four;
	}
	
	public void PrintQ() 
	{
		for (int i = 0; i<Quader.length;i++) 
		{
			System.out.print(Quader[i] + " ");
		}
	}
	
	public String[] GetQuader() 
	{
		return Quader;
	}
	
	public int GetQuadSize() 
	{
		return QuadSize;
	}
}
