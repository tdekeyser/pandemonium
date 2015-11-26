package entities.living.demons;

import java.util.Arrays;

import entities.living.CradleOfFilth;

public class Imp extends Demon {
	/* class for the lesser demon, 3rd in hierarchy and first mature
	 * Imp has following variables:
	 * 		String name,
	 * 		String gender,
	 * 		int age,
	 * 		boolean isAlive,
	 * 		int[] currentPosition,
	 * 		Demon partner,
	 * 		int cruelty
	 */

	public Imp(String name, String gender, int[] currentPosition) {
		super(name, gender, currentPosition);
		this.setAge(4);
		this.setType("Imp");
		this.appendToLog(this.getName()+", Imp, " + this.getGender() + System.lineSeparator() + "======");
	}
	
	@Override
	public String toString() {
		// board representation of Imp
		return "I";
	}
	
	public void cannibalize(Imp impVictim) {
		// destroys Imp object at same position and increases cruelty
		impVictim.declareDead();
		this.increaseCruelty();
		this.appendToLog("Cannibalized Imp at position " + Arrays.toString(this.getCurrentPosition()));
	}
	
	public InfernalDemon evolve() {
		// evolve Imp to InfernalDemon, after cruelty = 6
		
		InfernalDemon evolved_obj = new InfernalDemon(this.getName(), this.getGender(), this.getCurrentPosition());
		evolved_obj.setAge(this.getAge());
		evolved_obj.setCruelty(this.getCruelty());
		if (this.hasPartner()) { evolved_obj.partner = this.partner; }
		evolved_obj.copyAndAppendToLog(this, evolved_obj.toStringLong() + "Evolved from Imp.");
		return evolved_obj;
	}
	
	public static void main(String[] args) {
		// TEST Imp
		
		int[] cofPos = {3,4};
		CradleOfFilth cof = new CradleOfFilth("female", cofPos);
		cof.increaseAge();
		cof.increaseAge();
		
		Imp impie = cof.evolve();
		cof = null;
		
		System.out.println(impie.toStringLong());	

	}
	
}
