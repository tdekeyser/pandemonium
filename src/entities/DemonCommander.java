package entities;

public class DemonCommander extends Demon {
	
	// * name, gender, age, cruelty, currentposition

	public DemonCommander(String name, String gender, int[] currentPosition) {
		super(name, gender, currentPosition);
		
	}
	
	@Override
	public String toString() {
		//String representation for class 'DemonCommander'
		return "DC";
	}
	
	// TODO folders that gather all demons + think about getters/setters for variables??
	
}
