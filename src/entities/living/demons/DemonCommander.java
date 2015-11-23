package entities.living.demons;

public class DemonCommander extends Demon {
	
	// * name, gender, age, cruelty, currentposition */

	public DemonCommander(String name, String gender, int[] currentPosition) {
		super(name, gender, currentPosition);
		this.setType("DemonCommander");
	}
	
	@Override
	public String toString() {
		// board representation of DemonCommander
		return "DC";
	}
	
}
