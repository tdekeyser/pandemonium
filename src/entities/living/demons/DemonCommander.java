package entities.living.demons;

import java.util.Arrays;

import entities.living.Sinner;
import randomizers.EntityGenerator;

public class DemonCommander extends Demon {
	
	// * name, gender, age, cruelty, currentposition */

	public DemonCommander(String name, String gender, int[] currentPosition) {
		super(name, gender, currentPosition);
		this.setType("DemonCommander");
	}
	
	@Override
	public String toString() {
		// board representation of DemonCommander
		return "DC";
	}
	
	public void killAngelOfDeath(AngelOfDeath victim) {
		victim.declareDead();
		this.appendToLog("Killed AngelOfDeath at position " + Arrays.toString(this.getCurrentPosition()));
	}
	
	public Imp demonizeSinner(Sinner victim) {
		victim.declareDead();
		this.appendToLog("Demonized Sinner at position " + Arrays.toString(this.getCurrentPosition()));
		return summonImp(this.getCurrentPosition());
	}
	
	public Imp summonImp(int[] boardDimensions) {
		EntityGenerator ec = new EntityGenerator(boardDimensions);
		return (Imp) ec.spawnImp();
	}
	
}
