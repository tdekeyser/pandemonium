package entities.unliving;

import java.util.Arrays;

import entities.Entity;
import entities.Printable;
import entities.living.LivingEntity;
import entities.living.demons.Demon;

public class HellFire extends UnlivingEntity implements Printable {
	// class for Bomb-like entity (entities that step on HellFire will die)
	private String placedBy;
	
	public HellFire(String placername, String placertype) {
		placedBy = placername + ", " + placertype + " at " + Arrays.toString(getCurrentPosition());
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
		str.append("Placed by " + placedBy + System.lineSeparator());
		return str.toString();
	}
	
}
