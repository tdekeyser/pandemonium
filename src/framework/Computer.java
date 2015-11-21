package framework;

import java.util.ArrayList;
import java.util.List;
import java.lang.NoSuchMethodException;

import entities.ActionSchema;
import entities.Entity;
import entities.living.LivingEntity;
import entities.living.CradleOfFilth;
import entities.living.Sinner;
import entities.living.demons.Imp;
import entities.living.demons.Demon;

public class Computer {
	/* class that brings together methods to compute a new state based on (user) input
	 * New entityList:
	 * 		- createLivingEntities(int amountofLiving, int[] entityDistribution)
	 * 		- activateLiving(List<Entity> entitiesOnPositionX)
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
	
	private int[] computeRandomPosition() {
		// gets a random position depending on Board dimensions
		
		int[] newPosition = new int[2];
		newPosition[0] = Randomizer.random(boardDimensions[0]);
		newPosition[1] = Randomizer.random(boardDimensions[1]);
		return newPosition;
	}
	
	public void activateUnliving() {
		// activates unliving things
		
	}
	
	public List<Entity> activateLiving(List<Entity> ePosList) {
		
		List<Entity> newEntities = new ArrayList<>();
		
		if (ePosList.size() == 1) {
			// single element on position
			Entity soleEntity = ePosList.get(0);
			newEntities.add(actionSchema.doAction(soleEntity));
			
		} else {
			// if more than one entity on position, iterate over entities and over its possible targets
			
			List<Entity> targets =  new ArrayList<>(); // clone entitiesOnPositionX for all possible targets
			for (Entity entityOP : ePosList) {
				targets.add(entityOP);
			}
			
			for (int i=0; i<ePosList.size(); i++) {
				Entity e = ePosList.get(i);
				
				if (!((LivingEntity) e).isAlive()) { targets.remove(e); continue; } // if entity is dead, remove it from possible targets and continue
				
				if (e.getType().equals("CradleOfFilth") || e.getType().equals("Sinner")) { // cradle or sinners do not have targets
					newEntities.add(actionSchema.doAction(e));
					targets.remove(e);
					
				} else { // there is a possible target; now randomize whether it will target or not
					int r = Randomizer.random(2 + Math.abs(heat/10)); // heat=10 --> P(target)=2/3; heat=100 --> P(target)=11/12=0.91
					if (r == 0) { // don't target
						newEntities.add(actionSchema.doAction(e));
						targets.remove(e);
						
					} else { // do the target
						targetLoop: for (Entity target : targets) {
							if ( (e.getType().equals(target.getType()) && (((Demon) e).getName().equals(((Demon) target).getName())))) {
								if (targets.size()==1) { // if the same demon is the sole target, do its action
									newEntities.add(actionSchema.doAction(e));
								} else {
									continue targetLoop;
								}
							} else {
								try {
									List<Entity> actionResult = actionSchema.doAction(e, target);
									
									Entity eResult = actionResult.get(0);
									Entity tarResult = actionResult.get(1);
									LivingEntity targetInEPos = (LivingEntity) ePosList.get(ePosList.indexOf(target));
									
									if (((LivingEntity) tarResult).isAlive()) { // check if target is still alive, add it to newEntities
										newEntities.addAll(actionResult);
										targetInEPos.declareDead(); // tar cannot be targeted a second time
										
									} else { // if not, add the killer to newEntities, but declare target dead
										newEntities.add(eResult);
										targetInEPos.declareDead();
									}
									break targetLoop; // if an action is successful, break out of loop
								} catch (NoSuchMethodException x) { continue targetLoop; } // if an action is unsuccessful, continue
							}
						}
					}
				}
			}
		}
		
		for (int i=0; i<newEntities.size(); i++) {
			// final loop to remove entities that have been declared dead during final iteration and to increase age of all
			Entity newE = newEntities.get(i);
			if (!((LivingEntity) newE).isAlive()) { newEntities.remove(newE); }
			
			((LivingEntity) newE).increaseAge();
		}
		
		return newEntities;
	}
	
}
