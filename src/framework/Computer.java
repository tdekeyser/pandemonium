package framework;

import java.util.ArrayList;
import java.util.List;

import entities.ActionSchema;
import entities.Entity;
import entities.living.CradleOfFilth;
import entities.living.Sinner;
import entities.living.demons.Imp;

public class Computer {
	/* class that brings together methods to compute a new state based on (user) input
	 * New entityList:
	 * 		- createLivingEntities(int amountofLiving, int[] entityDistribution)
	 * Random positions:
	 * 		- computeRandomPosition()
	 */
	
	private int[] boardDimensions;
	private int heat;
	private int chanceOnPlague;
	
	private ActionSchema actionSchema; // contains method doAction() that randomizes a LivingEntity action
	
	public Computer(int[] boardDimensions, int heat, int chanceOnPlague) {
		this.boardDimensions = boardDimensions;
		this.actionSchema = new ActionSchema(boardDimensions);
		this.heat = heat;
		this.chanceOnPlague = chanceOnPlague;
	}
	
	private int[] computeRandomPosition() {
		// gets a random position depending on Board dimensions
		
		int[] newPosition = new int[2];
		newPosition[0] = Randomizer.random(boardDimensions[0]);
		newPosition[1] = Randomizer.random(boardDimensions[1]);
		return newPosition;
	}
	
	public List<Entity> activateEntities(List<Entity> previousEntities) {
		// computes a random action for each entity and 
		List<Entity> newEntities = new ArrayList<>();
		
		for (Entity e : previousEntities) {
			newEntities.add(actionSchema.doAction(e));
		}
		return newEntities;
	}
	
	public List<Entity> createLivingEntities(int amountOfLiving, int[] entityDistribution) {
		// initial state of living entities; choose between S, CoF, and I based on entityDistribution
		
		List<Entity> livingEntities = new ArrayList<>(); // output
		String gender = "";
		NameGenerator n = new NameGenerator();
		
		for (int i=0; i<amountOfLiving; i++) {
			Entity newEntity;
			
			// randomize gender
			int g = Randomizer.random(1);
			switch (g) {
				case 0: gender = "male"; break;
				case 1: gender = "female"; break;
			}
			
			// randomize object type acc to distribution, e.g. 40 (S), 30 (CoF), 30 (I)
			int r = Randomizer.random(100);
			if (r <= entityDistribution[0]) {
				newEntity = new Sinner(gender, computeRandomPosition());
			} else if (r >= 100 - entityDistribution[2]) {
				newEntity = new Imp(n.getDemonName(), gender, computeRandomPosition());
			} else {
				newEntity = new CradleOfFilth(gender, computeRandomPosition());
			}
			livingEntities.add(newEntity);
		}
		return livingEntities;
	}
	
	public static void main(String[] args) {
		// TEST Computer
		
		int[] boardDimensions = {7, 7};
		int heat = 0;
		int chanceOnPlague = 0;
		
		int[] entityDistribution = {40, 30, 30};
		int amountOfLiving = 10;
		
		Computer c = new Computer(boardDimensions, heat, chanceOnPlague);
		
		c.createLivingEntities(amountOfLiving, entityDistribution);
		
	}
}
