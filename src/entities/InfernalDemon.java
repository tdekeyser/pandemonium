package entities;

public class InfernalDemon extends Demon {
	/* class for the Greater demon, 1st in hierarchy 
	 * InfernalDemon has following variables:
	 * 		String name,
	 * 		String gender,
	 * 		int age,
	 * 		boolean isAlive,
	 * 		int[] currentPosition,
	 * 		Demon partner,
	 * 		int cruelty
	 */

	public InfernalDemon(String name, String gender, int[] currentPosition) {
		//Constructor for class InfernalDemon
		super(name, gender, currentPosition);
	}
	
	@Override
	public String toString() {
		//String Representation for class InfernalDemon
		return "InfD";
	}
	
	public void tortureSinner(Sinner victim) {
		//torture method decreases a Sinner victim's divinity & increases infD's cruelty
		victim.divinity--;
		this.cruelty++;
	}

}
