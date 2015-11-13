package entities.living.demons;

import java.util.Arrays;

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
	}
	
	@Override
	public String toString() {
		//String Representation for class InfernalDemon
		StringBuilder info = new StringBuilder();
		info.append("Name: ");
		info.append(this.getName());
		info.append("\nGender: ");
		info.append(this.getGender());
		info.append("\nAge: ");
		info.append(this.getAge());
		info.append("\nCruelty: ");
		info.append(this.getCruelty());
		info.append("\nPosition: ");
		info.append(Arrays.toString(this.getCurrentPosition()));
		return info.toString();
	}	
	
	public String toStringShort() {
		// board representation of InfernalDemon
		return "InfD";
	}
	
	public void tortureSinner(Sinner victim) {
		//torture method decreases a Sinner victim's divinity & increases infD's cruelty
		victim.decreaseDivinity();
		this.increaseCruelty();
	}
	
	public DemonCommander evolve() {
		// evolve infernaldemon into demoncommander, after cruelty = 16
		DemonCommander evolved_obj = new DemonCommander("Belzebub", this.getGender(), this.getCurrentPosition());
		evolved_obj.setAge(this.getAge());
		if (this.hasPartner()) { evolved_obj.partner = this.partner; }
		return evolved_obj;
	}

}
