package entities.living.demons;

import java.util.Arrays;

import entities.living.CradleOfFilth;
import entities.living.Sinner;
import entities.living.demons.Demon;
import entities.unliving.HellFire;

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
		this.appendToLog("Killed Cradle at position " + Arrays.toString(victim.getCurrentPosition()));
	}
	
	public void killImp(Imp victim) {
		victim.declareDead();
		this.decreaseCruelty();
		this.appendToLog("Killed Imp at position " + Arrays.toString(victim.getCurrentPosition()));
	}
	
	public void killInfernalDemon(InfernalDemon victim) {
		victim.declareDead();
		this.decreaseCruelty();
		this.decreaseCruelty();
		this.appendToLog("Killed InfernalDemon at position " + Arrays.toString(victim.getCurrentPosition()));
	}
	
	public HellFire dropHellFire() {
		// drop a Bomb-like unliving entity
		HellFire h = new HellFire(getName(), getType());
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
