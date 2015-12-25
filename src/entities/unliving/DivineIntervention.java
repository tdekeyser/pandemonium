package entities.unliving;

import java.util.ArrayList;
import java.util.List;

import entities.Entity;
import randomizers.EntityGenerator;
import randomizers.Randomizer;

public class DivineIntervention extends UnlivingEntity {
	// unliving entity that spawns Angels and Sinners at random
	
	private EntityGenerator eg;
	private int w; // weight of the intervention
	
	public DivineIntervention(EntityGenerator eg, int w) {
		this.eg = eg;
		this.w = w;
	}
	
	public List<Entity> intervene() {
		// returns list of randomly spawned angels or sinners (at most w entities)
		
		List<Entity> intervention = new ArrayList<>();
		
		int[] boardDimensions = eg.getBoardDimensions();
		if (boardDimensions[0]>7 || boardDimensions[1]>7) {
			w += ((boardDimensions[0]+boardDimensions[1])/4);
		}
		
		for (int i=0; i<w; i++) {
			int r = Randomizer.random(3);
			switch (r) {
				case 0: intervention.add(eg.spawnAngelOfDeath()); break;
				case 1: intervention.add(eg.spawnSinner()); break;
				default: break;
			}
		}
		
		return intervention;
		
	}
	
}
