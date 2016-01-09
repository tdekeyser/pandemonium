package entities.living.demons;

import java.util.Arrays;

import entities.living.LivingEntity;
import entities.living.Sinner;

public abstract class Demon extends LivingEntity {
	/* This abstract class is superclass for InfernalDemon, Imp, DemonCommander, but not for CradleOfFilth.
	 */
	
	private final String name;
	private int cruelty; // cruelty defines a demon's ability to evolve in the hierarchy

	public Demon(String name, String gender, int[] currentPosition) {
		super(gender, currentPosition);
		this.name = name;
	}
	
	@Override	
	public String toStringLong() {
		StringBuilder info = new StringBuilder();
		info.append("Name: " + getName() + System.lineSeparator());
		info.append("Type: " + getType() + System.lineSeparator());
		info.append("Gender: " + getGender() + System.lineSeparator());
		info.append("Age: " + getAge() + System.lineSeparator());
		info.append("Cruelty: " + getCruelty() + System.lineSeparator());
		info.append("Position: " + Arrays.toString(getCurrentPosition()) + System.lineSeparator());
		return info.toString();
	}
	
	public void killSinner(Sinner victim) {
		// demon kills a victim and gains cruelty
		victim.declareDead();
		this.increaseCruelty();
		this.appendToLog("Killed Sinner at position " + Arrays.toString(this.getCurrentPosition()));
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getCruelty() {
		return this.cruelty;
	}
	
	public void setCruelty(int c) {
		this.cruelty = c;
	}
	
	public void increaseCruelty() {
		// increase cruelty of demon by 1
		this.cruelty++;
	}
	
	public void decreaseCruelty() {
		// increase cruelty of demon by 1
		this.cruelty--;
	}
	
}
