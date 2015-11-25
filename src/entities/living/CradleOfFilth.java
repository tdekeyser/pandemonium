package entities.living;

import java.util.Arrays;

import entities.living.demons.Imp;
import framework.NameGenerator;

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
		this.setType("CradleOfFilth");
	}
	
	@Override
	public String toString() {
		// board representation of object
		return "CoF";
	}
	
	@Override
	public String toStringLong() {
		// String representation of CradleOfFilth object
		StringBuilder info = new StringBuilder();
		info.append("Type: Cradle of Filth");
		info.append("\nGender: ");
		info.append(getGender());
		info.append("\nAge: ");
		info.append(getAge());
		info.append("\nPosition: ");
		info.append(Arrays.toString(getCurrentPosition()));
		return info.toString();
	}
	
	public Imp evolve() {
		// generate random demon name, then create new Imp object same gender and position
		NameGenerator n = new NameGenerator("demonic");
		Imp evolved_obj = new Imp(n.getName(), this.getGender(), this.getCurrentPosition());
		return evolved_obj;
	}
	
	public static void main(String[] args) {
		// TEST CradleOfFilth
		
		int[] cofPos = {3,4};
		CradleOfFilth cof = new CradleOfFilth("female", cofPos);
		System.out.println(cof.toString());
	}
	
}