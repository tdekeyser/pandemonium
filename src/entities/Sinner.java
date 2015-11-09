package entities;

public class Sinner extends LivingEntity {

	int divinity; // divinity defines a sinner's position within the hierarchy
	
	public Sinner(String gender, int[] currentPosition) {
		super(gender, currentPosition);
	}
	
	@Override
	public String toString() {
		// String representation of Sinner object
		return "S";
	}
	
}
