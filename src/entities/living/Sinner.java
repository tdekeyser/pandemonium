package entities.living;

import java.util.Arrays;

import entities.living.demons.AngelOfDeath;
import randomizers.NameGenerator;

public class Sinner extends LivingEntity {

	private int divinity; // divinity defines a sinner's position within the hierarchy
	
	public Sinner(String gender, int[] currentPosition) {
		super(gender, currentPosition);
		this.setType("Sinner");
		this.appendToLog("Sinner, "+ this.getGender() + System.lineSeparator() + "======");
	}
	
	@Override
	public String toString() {
		// board representation
		return "S";
	}
	
	@Override
	public String toStringLong() {
		// String representation of Sinner object
		StringBuilder info = new StringBuilder();
		info.append("Type: " + getType() + System.lineSeparator());
		info.append("Gender: " + getGender() + System.lineSeparator());
		info.append("Age: " + getAge() + System.lineSeparator());
		info.append("Divinity: " + getDivinity() + System.lineSeparator());
		info.append("Position: " + Arrays.toString(this.getCurrentPosition()) + System.lineSeparator());
		return info.toString();
	}
	
	public int getDivinity() {
		return this.divinity;
	}
	
	public void pray() {
		increaseDivinity();
		appendToLog("Prayed.");
	}
	
	public void setDivinity(int d) {
		this.divinity = d;
	}
	
	public void increaseDivinity() {
		this.divinity++;
	}
	
	public void decreaseDivinity() {
		this.divinity--;
	}
	
	public AngelOfDeath evolve(Sinner partner) {
		 // two sinners can merge into an AngelOfDeath
		NameGenerator an = new NameGenerator("angelic");
		AngelOfDeath aod = new AngelOfDeath(an.getName(), this.getGender(), this.getCurrentPosition());
		aod.setAge(this.getAge());
		aod.copyAndAppendToLog(this, "Evolved to AngelOfDeath from Sinners");
		partner.declareDead();
		this.declareDead();		
		return aod;	
	}
	
}
