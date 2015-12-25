package entities.unliving;

import java.util.Arrays;

import entities.Printable;
import entities.living.LivingEntity;


public class HellFire extends UnlivingEntity implements Printable {
	// class for Bomb-like entity (entities that step on HellFire will die)
	private String placedBy;
	
	public HellFire(String placername, String placertype) {
		placedBy = placername + ", " + placertype;
	}
	
	public void explode(LivingEntity victim) {
		victim.declareDead();
		victim.appendToLog("Killed by HellFire.");
		this.declareDead();
	}
	
	@Override
	public String toString() {
		return "H";
	}
	
	public String toStringLong() {
		StringBuilder str = new StringBuilder("HellFire"+System.lineSeparator());
		str.append("Placed by: " + System.lineSeparator() + placedBy + System.lineSeparator() + "At position: " + Arrays.toString(getCurrentPosition()) + System.lineSeparator());
		return str.toString();
	}
	
}
