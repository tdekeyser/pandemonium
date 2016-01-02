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
		this.setType("InfernalDemon");
	}
	
	@Override
	public String toString() {
		// board representation of InfernalDemon
		return "InfD";
	}
	
	public void plot() {
		this.increaseCruelty();
		this.appendToLog("Plotted horrific schemes.");
	}
	
	public void tortureSinner(Sinner victim) {
		//torture method decreases a Sinner victim's divinity & increases infD's cruelty
		victim.decreaseDivinity();
		this.increaseCruelty();
		this.increaseCruelty();
		this.appendToLog("Tortured Sinner at position " + Arrays.toString(this.getCurrentPosition()));
	}
	
	public DemonCommander evolve() {
		// evolve infernaldemon into demoncommander, after cruelty = 16
		
		DemonCommander evolved_obj = new DemonCommander(this.getName(), this.getGender(), this.getCurrentPosition());
		evolved_obj.setAge(this.getAge());
		evolved_obj.setCruelty(this.getCruelty());
		evolved_obj.copyAndAppendToLog(this, "Evolved to DemonCommander from InfernalDemon.");
		return evolved_obj;
	}

}
