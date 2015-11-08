package entities;

public class Sinner extends LivingEntity {

	int divinity; // divinity defines a sinner's position within the hierarchy
	
	public Sinner(String gender, int[] currentPosition) {
		super(gender, currentPosition);
	}
	
}
