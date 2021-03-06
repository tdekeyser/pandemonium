package entities.living.demons;

import java.util.Arrays;

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
		this.appendToLog(this.getName() + ", " + this.getGender() + System.lineSeparator() + "======");
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
		evolved_obj.copyAndAppendToLog(this, "Evolved to InfernalDemon from Imp.");
		return evolved_obj;
	}

	
}
