package parser;
import java.util.Enumeration;
import java.util.Vector;

public class Quadruples 
{
	/* Quadruples class stores all of the TVI code generated so far,
	 * storing each individual Quadruple object represented
	 * as a vector of Quadruple
	 * */
	
	private Vector<Quadruple> Quadruple;
	private int NextQuad;
	
	public Quadruples() 
	{
		Quadruple = new Vector<Quadruple>();
		NextQuad = 0;
		String entry = "blah";
		Quadruple dummy_quad = new Quadruple(entry);
		dummy_quad = null;
		Quadruple.add(NextQuad, dummy_quad);
		NextQuad++;
	}
	
	public String GetField(int quadIndex, int field) 
	{
		return Quadruple.elementAt(quadIndex).GetQuader()[field];
	}
	
	public void SetField(int quadIndex, int index, String field) 
	{
		Quadruple.elementAt(quadIndex).GetQuader()[index] = field;
	}
	
	public int GetNextQuad() 
	{
		return NextQuad;
	}
	
	public void IncrementNextQuad() 
	{
		NextQuad++;
	}
	
	public Quadruple GetQuad(int index) 
	{
		return Quadruple.elementAt(index);
	}
	
	public void AddQuad(Quadruple quad)
	{
		Quadruple.add(NextQuad, quad);
		NextQuad++;
	}
	
	/* Prints the quadruples produced */
	
	public void PrintQuadruples() 
	{
		int quadLabel = 1;
		System.out.println("CODE: ");
		
		Enumeration<Quadruple> e = this.Quadruple.elements();
		e.nextElement();
		
		while (e.hasMoreElements()) {
			Quadruple quad = e.nextElement();
			
			System.out.print(quadLabel + ": " + quad.GetQuader()[0] + " ");
			for (int i=1; i<quad.GetQuadSize(); i++) {
				if (i == quad.GetQuadSize() - 1) {
					System.out.print(quad.GetQuader()[i] + " ");
				} else {
					System.out.print(quad.GetQuader()[i] + ", ");
				}
			}
			System.out.println();
			quadLabel++;
		}
	}
}
