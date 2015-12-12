package entities.unliving;

import java.util.ArrayList;
import java.util.List;

import entities.Entity;
import randomizers.EntityGenerator;
import randomizers.Randomizer;

public class DivineIntervention extends UnlivingEntity {
	// unliving entity that spawns Angels and Sinners at random
	
	public static List<Entity> intervene(EntityGenerator eg, int w) {
		// returns list of randomly spawned angels or sinners (at most w entities)
		
		List<Entity> intervention = new ArrayList<>();
		
		for (int i=0; i<w; i++) {
			int r = Randomizer.random(3);
			switch (r) {
				case 0: intervention.add(eg.spawnAngelOfDeath()); break;
				case 1: intervention.add(eg.spawnSinner()); break;
				default: break;
			}
		}
		
		if (intervention.size()>0) {
			System.out.println("Devine Intervention! " + intervention.size() + " intervened.");
		}
		
		return intervention;
		
	}
	
}
