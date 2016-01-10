package entities.unliving;

import java.util.ArrayList;
import java.util.List;

import entities.Entity;
import randomizers.EntityGenerator;
import randomizers.Randomizer;

public class Plague extends UnlivingEntity {
	
	private EntityGenerator eg;
	private int chanceOnPlague;
	
	public Plague(EntityGenerator eg, int chanceOnPlague) {
		this.eg = eg;
		this.chanceOnPlague = chanceOnPlague;
	}
	
	public List<Entity> spawnDead() {
		// spawn Sinners according to chanceOnPlague		
		List<Entity> spawnedEntities = new ArrayList<>();
		
		int w = (eg.getBoardDimensions()[0] + eg.getBoardDimensions()[1])/2;	
		int y = (-1/25)*chanceOnPlague + 6; // P(spawn1Sinner)=1/f(x) and P(spawn1Cradle)=1/2f(x) if x=chanceOnPlague>=50, with f(100)=2; f(50)=4; f(0)=6
		
		for (int i=0; i<w; i++) {
			if (chanceOnPlague > 0) {
					if (Randomizer.random(Math.abs(y))==0) { spawnedEntities.add(eg.spawnSinner()); }
			}
			if (chanceOnPlague >= 50) {
					if (Randomizer.random(Math.abs(y*2))==0) { spawnedEntities.add(eg.spawnCradle()); }
			}
		}
		
		return spawnedEntities;
	}
}
