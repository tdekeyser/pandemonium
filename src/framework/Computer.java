package framework;

import java.util.ArrayList;
import java.util.List;

import entities.Entity;
import entities.living.LivingEntity;
import entities.unliving.DemonicFury;
import entities.unliving.DivineIntervention;
import entities.unliving.Plague;
import randomizers.EntityGenerator;
import randomizers.Randomizer;

public class Computer {
	/* class that brings together methods to compute a new state based on (user) input
	 * 
	 * 		- createLivingEntities(int amountofLiving, int[] entityDistribution),
	 * 		- activateLiving(List<Entity> entitiesOnPositionX),
	 * 		- activateUnliving(List<Entity>), and
	 * 		- getUnlivingActions()
	 *
	 */
	
	private int heat;
	private int chanceOnPlague;
	
	private ActionSchema actionSchema; // contains method doAction() that randomizes a LivingEntity action
	private EntityGenerator entityGen; // contains methods to create random entities
	
	private DivineIntervention divineIntervention;
	private Plague plague;
	private List<String> unlivingActions = new ArrayList<>();
	
	public Computer(int[] boardDimensions, int heat, int chanceOnPlague) {
		this.actionSchema = new ActionSchema(boardDimensions);
		this.entityGen = new EntityGenerator(boardDimensions);
		
		this.divineIntervention = new DivineIntervention(entityGen, ((int) Math.abs(heat/20)+3));
		this.plague = new Plague(entityGen, chanceOnPlague);
		
		this.chanceOnPlague = chanceOnPlague;
		this.heat = heat;
		
	}
	
	public List<Entity> createLivingEntities(int amountOfLiving, int[] entityDistribution) {
		// initial state of living entities; choose between S, CoF, and I based on entityDistribution
		
		List<Entity> livingEntities = new ArrayList<>(); // output
		
		for (int i=0; i<amountOfLiving; i++) {
			Entity newEntity;
			
			// randomize object type acc to distribution, e.g. 40 (S), 30 (CoF), 30 (I)
			int r = Randomizer.random(100);
			if (r <= entityDistribution[0]) {
				newEntity = entityGen.spawnSinner();
			} else if (r >= 100 - entityDistribution[2]) {
				newEntity = entityGen.spawnImp();
			} else {
				newEntity = entityGen.spawnCradle();
			}
			livingEntities.add(newEntity);
		}
		return livingEntities;
	}
	
	public List<Entity> activateEntities(List<Entity> ePosList) {
		/*
		 * Method that gets as input entities at a specific position.
		 * It first evaluates whether there is more than one entity in the input. If there is, there is a possibility
		 * to target, and thus the need to loop over all possible entities in the list (entityLoop).
		 * Then, it creates a list of possible targets, loops over the targets (targetLoop) and adds the result to a result list.
		 * Finally, in lifeLoop it loops over the result list and removes any entities that have been declared dead.
		 */
		
		List<Entity> newEntities = new ArrayList<>();
		
		if (ePosList.size() == 1) { // single element on position
			Entity soleEntity = ePosList.get(0);
			newEntities.addAll(actionSchema.doAction(soleEntity));
			
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
					
					int P_TARGET = 2 + Math.abs(heat/10); // P(don't target) = 1/(P_TO_TARGET)
					
					if (!(e.getType().equals("unliving")) && ((Randomizer.random(P_TARGET)) == 0)) {
						newEntities.addAll(actionSchema.doAction(e));
						continue entityLoop;
					} else { // do the target
						
						boolean targetSuccessful = false;
						targetLoop: for (Entity target : targets) {
							
							List<Entity> actionResult = actionSchema.doAction(e, target);		
							
							if (actionResult.size()==1) { continue targetLoop; } // this occurs when an entity has failed to target, i.e. a single result is returned
							
							Entity eResult = actionResult.get(0);
							Entity tarResult = actionResult.get(1);
							Entity targetInEPos = ePosList.get(ePosList.indexOf(target)); // get target in entityList (= identical entity)
							
							if (tarResult.isAlive()) { // check if target is still alive, add it to newEntities
								newEntities.addAll(actionResult);
								targetInEPos.declareDead(); // target cannot be looped over anymore
							} else { // if not, add the killer to newEntities, but declare target dead
								if (eResult.isAlive()) { newEntities.add(eResult); }
								targetInEPos.declareDead();
							}
							targetSuccessful = true;
							break targetLoop; // if an action is successful, break out of loop and continue the entityLoop
						}
						
						if (!targetSuccessful) { // none of the targets have been successful
							newEntities.addAll(actionSchema.doAction(e));
						}
						
					}
				}
			}
		}
		
		List<Entity> finalEntities = new ArrayList<>();
		lifeLoop: for (int i=0; i<newEntities.size(); i++) { // final loop to remove entities that have been declared dead + increase age of all	
			Entity newE = newEntities.get(i);
			if (((Entity) newE).isAlive()) {
				finalEntities.add(newE);
			}
			try {
				((LivingEntity) newE).increaseAge();
			} catch (ClassCastException cx) { continue lifeLoop; }
		}
		
		return finalEntities;
	}
	
	public List<String> getUnlivingActions() {
		return unlivingActions;
	}
	
	public List<Entity> activateUnliving(List<Entity> newEntities) {
		/* 
		 * Activates unliving entities at random and changes its results in the List<Entity> newEntities
		 */
		
		unlivingActions.clear(); // clear previous list
		String unlivingAction;
		List<Entity> surviving = new ArrayList<>();
		
		// Unleash DemonicFury: P = 1/(110-heat)
		if (Randomizer.random(110-heat) == 0) { // P(demonicFury)=1/110-heat
			surviving.addAll(DemonicFury.unleash(newEntities));
			unlivingAction = "Demonic Fury unleashed!" + System.lineSeparator();
			unlivingActions.add(unlivingAction);
		} else {
			surviving.addAll(newEntities);
		}
		
		// Divine Intervention: P = 1/(110-heat)
		if (Randomizer.random(110-heat) == 0) {
			List<Entity> intervention = divineIntervention.intervene();
			surviving.addAll(intervention);
			if (intervention.size()>0) {
				unlivingAction = "Devine Intervention!" + System.lineSeparator() + "(" + intervention.size() + " intervened)" + System.lineSeparator();
				unlivingActions.add(unlivingAction);
			}
		}
		
		// Plague: P = chanceOnPlague/100
		if (Randomizer.random(100)<chanceOnPlague) {
			List<Entity> spawnedEntities = plague.spawnDead();
			surviving.addAll(spawnedEntities);
			if (spawnedEntities.size()>0) {
				unlivingAction = "Plague! (" + spawnedEntities.size() + " spawned)" + System.lineSeparator();
				unlivingActions.add(unlivingAction);
			}
		}
		
		return surviving;
	}
	
}
