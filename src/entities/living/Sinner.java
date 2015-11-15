package entities.living;

import java.util.Arrays;

public class Sinner extends LivingEntity {

	private int divinity; // divinity defines a sinner's position within the hierarchy
	
	public Sinner(String gender, int[] currentPosition) {
		super(gender, currentPosition);
	}
	
	@Override
	public String toString() {
		// board representation
		return "S";
	}
	
	public String toStringLong() {
		// String representation of Sinner object
		StringBuilder info = new StringBuilder();
		info.append("Name: Sinner");
		info.append("\nGender: ");
		info.append(this.getGender());
		info.append("\nAge: ");
		info.append(this.getAge());
		info.append("\nDivinity: ");
		info.append(this.getDivinity());
		info.append("\nPosition: ");
		info.append(Arrays.toString(this.getCurrentPosition()));
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
