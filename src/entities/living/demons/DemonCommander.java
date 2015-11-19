package entities.living.demons;

import java.util.Arrays;

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
	
	public String toStringLong() {
		//String representation for class 'DemonCommander'
		StringBuilder info = new StringBuilder();
		info.append("Name: ");
		info.append(getName());
		info.append("\nGender: ");
		info.append(getGender());
		info.append("\nAge: ");
		info.append(getAge());
		info.append("\nCruelty: ");
		info.append(getCruelty());
		info.append("\nPosition: ");
		info.append(Arrays.toString(getCurrentPosition()));
		return info.toString();
	}	
	
}
