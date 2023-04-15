
public class Node {

	public int status;
	public String key;
	public Name name;
	public Heab heab;
	public Node(int status, String key, Name name,Frequency fre)
	{
		this.status = status;
		this.key = key;
		this.name = name;
		heab=new Heab(100);
		heab.insert(fre);
	
		
	}
	@Override
	public String toString() {
		return "Node [name=" + name + ", heab=" + heab.print() + "]";
	}
	
	
	
	
	
	
}
