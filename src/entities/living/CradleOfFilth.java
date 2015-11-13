package entities.living;

import java.util.Arrays;

import entities.living.demons.Imp;

public class CradleOfFilth extends LivingEntity {
	/* class for lowest hierarchy
	 * CrafleOfFilth has the following variables:
	 * 		String name,
	 * 		String gender,
	 * 		int age, --> at age 3, it turns into an Imp object
	 * 		boolean isAlive,
	 * 		int[] currentPosition,
	 * 		int cruelty
	 */
	
	public CradleOfFilth(String gender, int[] currentPosition) {
		super(gender, currentPosition);
		this.setAge(1);
	}
	
	@Override
	public String toString() {
		// String representation of CradleOfFilth object
		StringBuilder info = new StringBuilder();
		info.append("Name: Cradle of Filth\nGender: ");
		info.append(this.getGender());
		info.append("\nAge: ");
		info.append(this.getAge());
		info.append("\nPosition: ");
		info.append(Arrays.toString(this.getCurrentPosition()));
		return info.toString();
	}
	
	public String toStringShort() {
		// board representation of object
		return "CoF";
	}
	
	public Imp evolve() {
		// make this object into a Imp; then delete this object
		// generate random Demon name first, say 'Belzebub'
		Imp evolved_obj = new Imp("Belzebub", this.getGender(), this.getCurrentPosition());
		return evolved_obj;
	}
	
}
