package entities.living.demons;

import java.util.Arrays;

import entities.living.CradleOfFilth;
import entities.living.Sinner;
import entities.living.demons.Demon;
import entities.unliving.HellFire;
import randomizers.EntityGenerator;

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
		this.appendToLog("Killed Cradle at position " + Arrays.toString(victim.getCurrentPosition()));
	}
	
	public void killDemon(Demon victim) {
		victim.declareDead();
		this.decreaseCruelty();
		this.appendToLog("Killed " + victim.getType() + " at position " + Arrays.toString(victim.getCurrentPosition()));
	}
	
	public HellFire dropHellFire(int[] boardDimensions) {
		// drop a Bomb-like unliving entity
		EntityGenerator eg = new EntityGenerator(boardDimensions);
		HellFire h = (HellFire) eg.createHellFire(getName(), getType());
		this.decreaseCruelty();
		appendToLog("Dropped HellFire.");
		return h;	
	}
	
	@Override
	public void killSinner(Sinner victim) {
		// demon kills a victim and gains cruelty
		victim.declareDead();
		this.decreaseCruelty();
		this.appendToLog("Killed Sinner at position " + Arrays.toString(victim.getCurrentPosition()));
	}
	
}
