package entities;

public class DemonCommander extends Demon {
	
	// * name, gender, age, cruelty, currentposition

	public DemonCommander(String name, String gender, int[] currentPosition) {
		super(name, gender, currentPosition);
		
	}
	
	public String stringRepr() {
		//String representation for class 'DemonCommander'
		return "DC";
	}
	
	
}
