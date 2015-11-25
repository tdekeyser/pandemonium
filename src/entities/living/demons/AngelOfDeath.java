package entities.living.demons;

import entities.living.CradleOfFilth;
import entities.living.Sinner;
import entities.living.demons.Demon;

public class AngelOfDeath extends Demon {
	// angelofdeath is very aggressive and can kill everything except for DemonCommanders, but dies when it has no cruelty left
	
	public AngelOfDeath(String name, String gender, int[] currentPosition) {
		super(name, gender, currentPosition);
		this.setType("AngelOfDeath");
		this.setCruelty(6);
	}
	
	@Override
	public String toString() {
		return "AoD";
	}
	
	public void killCradle(CradleOfFilth victim) {
		victim.declareDead();
		this.decreaseCruelty();
		this.decreaseCruelty();
	}
	
	public void killImp(Imp victim) {
		victim.declareDead();
		this.decreaseCruelty();
	}
	
	public void killInfernalDemon(InfernalDemon victim) {
		victim.declareDead();
		this.decreaseCruelty();
		this.decreaseCruelty();
	}
	
	@Override
	public void killSinner(Sinner victim) {
		// demon kills a victim and gains cruelty
		victim.declareDead();
		this.decreaseCruelty();
	}
	
}
