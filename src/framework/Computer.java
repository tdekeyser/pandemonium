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
	
	/*
	 * TODO
	 * Bug: [H1 H2 X Y] does not return [] but [H1 Y], because the H1 targets H2 first, and then finishes because it targets an unliving entity
	 */
	
	private int heat;
	private int chanceOnPlague;
	
	private ActionSchema actionSchema; // contains method doAction() that randomizes a LivingEntity action
	
	public Computer(int[] boardDimensions, int heat, int chanceOnPlague) {
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
				newEntity = new Imp(dn.getName(), randomizeGender(), actionSchema.computeRandomPosition());
			} else {
				newEntity = spawnCradle();
			}
			livingEntities.add(newEntity);
		}
		return livingEntities;
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
		return new Sinner(randomizeGender(), actionSchema.computeRandomPosition());
	}
	
	private Entity spawnCradle() {
		return new CradleOfFilth(randomizeGender(), actionSchema.computeRandomPosition());
	}
	
	public List<Entity> spawnPlagued() {
		// spawn Sinners according to chanceOnPlague
		List<Entity> spawnedEntities = new ArrayList<>();
		int y = (-1/25)*chanceOnPlague + 6; // P(spawn1Sinner)=1/f(x) and P(spawn1Cradle)=1/2f(x) if chanceOnPlague>=50, with f(100)=2; f(50)=4; f(0)=6
		
		if (chanceOnPlague > 0) {
				if (Randomizer.random(Math.abs(y))==0) { spawnedEntities.add(spawnSinner()); }
		}
		if (chanceOnPlague >= 50) {
				if (Randomizer.random(Math.abs(y*2))==0) { spawnedEntities.add(spawnCradle()); }
		}
		return spawnedEntities;
	}
	
	public List<Entity> activateEntities(List<Entity> ePosList) {

		List<Entity> newEntities = new ArrayList<>();
		
		if (ePosList.size() == 1) { // single element on position
			Entity soleEntity = ePosList.get(0);
			newEntities.addAll(actionSchema.doAction(soleEntity));
			return newEntities;
		} else { // there is more than 1 element on this position
			
			List<Entity> targets =  new ArrayList<>(); // clone entitiesOnPositionX for all possible targets
			for (Entity entityOP : ePosList) {
				targets.add(entityOP);
			}

			entityLoop: for (int i=0; i<ePosList.size(); i++) {
				Entity e = ePosList.get(i);
				
				targets.remove(e); // remove entity itself (first occurrence of same entity in case of sinner/cradle) from target list
				
				if (!(e.isAlive())) { continue entityLoop; } // if entity is dead, continue
				
				else if (targets.size()==0) { newEntities.addAll(actionSchema.doAction(e)); break; } // if no targets left
					
				else { // there is a possible target; now randomize whether it will target or not
					
					if (!(e.getType().equals("unliving")) && ((Randomizer.random(2 + Math.abs(heat/10))) == 0)) {  // heat=10 --> P(target)=2/3; heat=100 --> P(target)=11/12=0.91
						newEntities.addAll(actionSchema.doAction(e));
						continue entityLoop;
					} else { // do the target
						
						targetLoop: for (Entity target : targets) {
							
							//if (targets.size()>1 && e.getType().equals("unliving") && target.getType().equals("unliving")) { continue; }
							
							List<Entity> actionResult = actionSchema.doAction(e, target);								
							Entity eResult = actionResult.get(0); // get the first element of the result list
							
							if (actionResult.size()==1) { newEntities.add(eResult); break targetLoop; } // this occurs when an entity has failed to target, i.e. a single result is returned
							
							Entity tarResult = actionResult.get(1);
							Entity targetInEPos = ePosList.get(ePosList.indexOf(target));
							
							if (tarResult.isAlive()) { // check if target is still alive, add it to newEntities
								newEntities.addAll(actionResult);
								targetInEPos.declareDead(); // target cannot be looped over anymore
							} else { // if not, add the killer to newEntities, but declare target dead
								if (eResult.isAlive()) { newEntities.add(eResult); }
								targetInEPos.declareDead();
							}
							break targetLoop; // if an action is successful, break out of loop and continue the entityLoop
						}
					}
				}
			}
		}
		
		List<Entity> finalEntities = new ArrayList<>();
		for (int i=0; i<newEntities.size(); i++) { // final loop to remove entities that have been declared dead + increase age of all	
			Entity newE = newEntities.get(i);
			if (((Entity) newE).isAlive()) {
				finalEntities.add(newE);
			}
			try {
				((LivingEntity) newE).increaseAge();
			} catch (ClassCastException cx) { continue; }
		}
		
		return finalEntities;
	}
	
	public List<Entity> activateUnliving(List<Entity> newEntities) {
		// activates unliving things
		List<Entity> surviving = new ArrayList<>();
		
		// Unleash DemonicFury
		if (Randomizer.random(110-heat) == 0) { // P(demonicFury)=1/110-heat
			surviving.addAll(DemonicFury.unleash(newEntities));
		} else {
			surviving.addAll(newEntities);
		}
		
		return surviving;
	}
	
}
