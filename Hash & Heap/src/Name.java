

public class Name {
	String name;
	char gender;
	public Name(String name, char gender) {
	
		this.name = name;
		this.gender = gender;
	}
	@Override
	public String toString() {
		return "Name [name=" + name + ", gender=" + gender + "]";
	}
	
}
