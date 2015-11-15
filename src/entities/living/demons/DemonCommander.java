package entities.living.demons;

import java.util.Arrays;

public class DemonCommander extends Demon {
	
	// * name, gender, age, cruelty, currentposition */

	public DemonCommander(String name, String gender, int[] currentPosition) {
		super(name, gender, currentPosition);
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
		info.append(this.getName());
		info.append("\nGender: ");
		info.append(this.getGender());
		info.append("\nAge: ");
		info.append(this.getAge());
		info.append("\nCruelty: ");
		info.append(this.getCruelty());
		info.append("\nPosition: ");
		info.append(Arrays.toString(this.getCurrentPosition()));
		return info.toString();
	}	
	
}
