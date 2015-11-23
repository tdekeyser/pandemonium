package entities.living;

import java.util.Arrays;

public class Sinner extends LivingEntity {

	private int divinity; // divinity defines a sinner's position within the hierarchy
	
	public Sinner(String gender, int[] currentPosition) {
		super(gender, currentPosition);
		this.setType("Sinner");
	}
	
	@Override
	public String toString() {
		// board representation
		return "S";
	}
	
	@Override
	public String toStringLong() {
		// String representation of Sinner object
		StringBuilder info = new StringBuilder();
		info.append("Name: Sinner" + System.lineSeparator());
		info.append("Gender: " + getGender() + System.lineSeparator());
		info.append("Age: " + getAge() + System.lineSeparator());
		info.append("Divinity: " + getDivinity());
		info.append("Position: " + Arrays.toString(this.getCurrentPosition()) + System.lineSeparator());
		return info.toString();
	}
	
	public int getDivinity() {
		return this.divinity;
	}
	
	public void increaseDivinity() {
		this.divinity++;
	}
	
	public void decreaseDivinity() {
		this.divinity--;
	}
	
}
