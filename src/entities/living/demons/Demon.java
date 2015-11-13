package entities.living.demons;

import entities.living.LivingEntity;
import entities.living.Sinner;

public abstract class Demon extends LivingEntity {
	/* abstract class is superclass for InfernalDemon, Imp, DemonCommander
	 * not a superclass for CradleOfFilth
	 */
	
	private final String name;
	Demon partner;
	private int cruelty; // cruelty defines a demon's position within the hierarchy

	public Demon(String name, String gender, int[] currentPosition) {
		super(gender, currentPosition);
		this.name = name;
	}
	
	public void killSinner(Sinner victim) {
		// demon kills a victim and gains cruelty
		victim.declareDead();
		this.increaseCruelty();
	}
	
	public boolean hasPartner() {
		// returns true if there is a partner
		return this.partner != null;
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
	
}
