package entities.living.demons;

import entities.living.Sinner;

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
		this.setCruelty(6);
		this.setType("InfernalDemon");
	}
	
	@Override
	public String toString() {
		// board representation of InfernalDemon
		return "InfD";
	}
	
	public void tortureSinner(Sinner victim) {
		//torture method decreases a Sinner victim's divinity & increases infD's cruelty
		victim.decreaseDivinity();
		this.increaseCruelty();
		this.increaseCruelty();
	}
	
	public DemonCommander evolve() {
		// evolve infernaldemon into demoncommander, after cruelty = 16
		
		DemonCommander evolved_obj = new DemonCommander(this.getName(), this.getGender(), this.getCurrentPosition());
		evolved_obj.setAge(this.getAge());
		if (this.hasPartner()) { evolved_obj.partner = this.partner; }
		return evolved_obj;
	}

}