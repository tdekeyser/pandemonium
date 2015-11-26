package framework;

import java.util.ArrayList;
import java.util.List;

import entities.Entity;
import entities.living.LivingEntity;
import entities.living.Sinner;
import entities.living.CradleOfFilth;
import entities.living.demons.Imp;
import entities.unliving.DemonicFury;

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
		NameGenerator dn = new NameGenerator("demonic");
		
		for (int i=0; i<amountOfLiving; i++) {
			Entity newEntity;
			
			// randomize object type acc to distribution, e.g. 40 (S), 30 (CoF), 30 (I)
			int r = Randomizer.random(100);
			if (r <= entityDistribution[0]) {
				newEntity = spawnSinner();
			} else if (r >= 100 - entityDistribution[2]) {
				newEntity = new Imp(dn.getName(), randomizeGender(), computeRandomPosition());
			} else {
				newEntity = spawnCradle();
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
	
	private String randomizeGender() {
		// randomize gender
		String gender = "Unknown";
		int g = Randomizer.random(2);
		switch (g) {
			case 0:	gender = "male"; break;
			case 1: gender = "female"; break;
		}
		return gender;
	}
	
	private Entity spawnSinner() {
		return new Sinner(randomizeGender(), computeRandomPosition());
	}
	
	private Entity spawnCradle() {
		return new CradleOfFilth(randomizeGender(), computeRandomPosition());
	}
	
	public List<Entity> spawnPlagued() {
		// spawn Sinners according to chanceOnPlague
		List<Entity> spawnedEntities = new ArrayList<>();
		int y = (-1/25)*chanceOnPlague + 6; // P(spawn1Sinner)=1/f(x) and P(spawn1Cradle)=1/2f(x) if chanceOnPlague>=50, with f(100)=2; f(50)=4; f(0)=6
		
		if (chanceOnPlague > 0) {
//			for (int i=0; i<=chanceOnPlague; i+=20) {
				if (Randomizer.random(Math.abs(y))==0) { spawnedEntities.add(spawnSinner()); }
//			}
		}
		if (chanceOnPlague >= 50) {
//			for (int i=0; i<=chanceOnPlague; i+=40) {
				if (Randomizer.random(Math.abs(y*2))==0) { spawnedEntities.add(spawnCradle()); }
//			}
		}
		return spawnedEntities;
	}
	
	public List<Entity> demonicFury(List<Entity> entityList) {
		if (Randomizer.random(102-heat) == 0) { // P(demonicFury)=1/102-heat
			return DemonicFury.unleash(entityList);
		} else {
			return entityList;
		}
	}
	
	public void activateUnliving() {
		// activates unliving things
		
	}
	
	public List<Entity> activateLiving(List<Entity> ePosList) {

		List<Entity> newEntities = new ArrayList<>();
		System.out.println("positionList: "+ePosList);
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

			entityLoop: for (int i=0; i<ePosList.size(); i++) {
				LivingEntity e = (LivingEntity) ePosList.get(i);
				
				targets.remove(e); // remove entity itself (first occurrence of same entity in case of sinner/cradle) from target list
				System.out.println("TargetList: "+targets);
				if (targets.size()==0) { newEntities.add(actionSchema.doAction(e)); break; } // if no targets left
				
				else if (!e.isAlive()) { continue; } // if entity is dead, continue
				
				else if (e.getType().equals("CradleOfFilth")) { newEntities.add(actionSchema.doAction(e)); } // cradles do not have targets
				
				else { // there is a possible target; now randomize whether it will target or not
					int r = Randomizer.random(2 + Math.abs(heat/10)); // heat=10 --> P(target)=2/3; heat=100 --> P(target)=11/12=0.91
					if (r == 0) { // don't target
						newEntities.add(actionSchema.doAction(e));
						continue entityLoop;						
					} else { // do the target
						
						targetLoop: for (Entity target : targets) {
														
							List<Entity> actionResult = actionSchema.doAction(e, target);
								
							Entity eResult = actionResult.get(0);
							if (actionResult.size()==1) { newEntities.add(eResult); break targetLoop; } // this occurs when an entity has failed to target
							
							LivingEntity tarResult = (LivingEntity) actionResult.get(1);
							LivingEntity targetInEPos = (LivingEntity) ePosList.get(ePosList.indexOf(target));
							
							if (tarResult.isAlive()) { // check if target is still alive, add it to newEntities
								newEntities.addAll(actionResult);
								targetInEPos.declareDead(); // tar cannot be targeted a second time
							} else { // if not, add the killer to newEntities, but declare target dead
								newEntities.add(eResult);
								targetInEPos.declareDead();
							}
							
							continue entityLoop; // if an action is successful, break out of loop and continue the entityLoop
						}
					}
				}
			}
		}
		
		// final loop to remove entities that have been declared dead + increase age of all	
		for (int i=0; i<newEntities.size(); i++) {
			LivingEntity newE = (LivingEntity) newEntities.get(i);
			if (!newE.isAlive()) { newEntities.remove(newE); }
			newE.increaseAge();
		}
		
		return newEntities;
	}
	
}
