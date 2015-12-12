package framework;

import java.util.ArrayList;
import java.util.List;

import entities.Entity;
import entities.living.CradleOfFilth;
import entities.living.LivingEntity;
import entities.living.Sinner;
import entities.living.demons.Demon;
import entities.living.demons.AngelOfDeath;
import entities.living.demons.DemonCommander;
import entities.living.demons.Imp;
import entities.living.demons.InfernalDemon;
import entities.unliving.HellFire;
import randomizers.EntityGenerator;
import randomizers.Randomizer;

public class ActionSchema {
	/* 
	 * Provides entity random method activation
	 * Only 1 (overloaded) method is available outside this class: doAction(Entity e) and doAction(Entity e, Entity target)
	 */
	
	private int[] boardDimensions;
	private EntityGenerator entityGen;
	
	public ActionSchema(int[] boardDimensions) {
		this.boardDimensions = boardDimensions;
		this.entityGen = new EntityGenerator(boardDimensions);
	}
	
	public List<Entity> doAction(Entity e) {
		/* checks the type of entity and passes to a specific randomizer method for that entity type
		 * Returns changed entity afterwards.
		 */
		List<Entity> eResult = new ArrayList<>();
		
		if ((e.getType()!="unliving") && (((LivingEntity) e).getAge()>90)) { // elderly entities die from old age
			e.declareDead();
			eResult.add(e);
			return eResult;
		}
		
		switch (e.getType()) {
			case "Sinner": eResult.add(doSinnerAction((Sinner) e)); break;
			case "AngelOfDeath": eResult.addAll(doAngelOfDeathAction((AngelOfDeath) e)); break;
			case "CradleOfFilth": eResult.add(doCradleAction((CradleOfFilth) e)); break;
			case "Imp": eResult.add(doImpAction((Imp) e)); break;
			case "InfernalDemon": eResult.add(doInfernalDemonAction((InfernalDemon) e)); break;
			case "DemonCommander": eResult.addAll(doDemonCommanderAction((DemonCommander) e)); break;
			case "unliving": eResult.add(e); break;
		}
		return eResult;
	}
	
	public List<Entity> doAction(Entity e, Entity target) {
		/* checks the type of entity and randomizes methods with a target
		 * Returns changed entity afterwards.
		 */
		List<Entity> targetresult = new ArrayList<>(); // 0: e, 1: tar
		
		switch (e.getType()) {
			case "CradleOfFilth":
				switch (target.getType()) {
					case "unliving": targetresult.addAll(doULAction(target, e)); return targetresult;
					default: targetresult.add(doCradleAction((CradleOfFilth) e)); return targetresult;
				}
			case "Imp":
				switch (target.getType()) {
					case "Sinner": ((Imp) e).killSinner((Sinner) target); break;
					case "Imp": ((Imp) e).cannibalize((Imp) target); break;
					case "unliving": targetresult.addAll(doULAction(target, e)); return targetresult;
					default: targetresult.add(doImpAction((Imp) e)); return targetresult;
				}; break;
			case "InfernalDemon":
				switch (target.getType()) {
					case "Sinner":
						int r = Randomizer.random(2);
						if (r == 0) {
							((InfernalDemon) e).killSinner((Sinner) target);
						} else {
							((InfernalDemon) e).tortureSinner((Sinner) target);
						}; break;
					case "unliving": targetresult.addAll(doULAction(target, e)); return targetresult;
					default: targetresult.add(doInfernalDemonAction((InfernalDemon) e)); return targetresult;
					} break;
			case "DemonCommander": 
				switch (target.getType()) {
					case "AngelOfDeath": ((DemonCommander) e).killAngelOfDeath((AngelOfDeath) target); break;
					case "Sinner": 
						if (Randomizer.random(2)==0) {
							((DemonCommander) e).killSinner((Sinner) target);
						} else {
							((DemonCommander) e).demonizeSinner((Sinner) target);
						}; break;
					case "unliving": targetresult.addAll(doULAction(target, e)); return targetresult;
					default: targetresult.add(entityGen.moveRandomly((LivingEntity) e)); return targetresult;
				}; break;
			case "Sinner":
				switch (target.getType()) {
				case "unliving": targetresult.addAll(doULAction(target, e)); return targetresult;
				case "Sinner":
					if ((((Sinner) e).getDivinity()>7) && (((Sinner) target).getDivinity()>7)) { // evolve into angel if both at least 7 divinity
						targetresult.add(((Sinner) e).evolve((Sinner) target));
						((LivingEntity) target).declareDead();
						targetresult.add(target);
						return targetresult;
					} else {
						targetresult.add(0, doSinnerAction((Sinner) e)); // else simply do sinneraction
						return targetresult;
					}
				default: targetresult.add(doSinnerAction((Sinner) e)); return targetresult;
				}
			case "AngelOfDeath":
				switch (target.getType()) {
					case "Sinner": ((AngelOfDeath) e).killSinner((Sinner) target); break;
					case "CradleOfFilth": ((AngelOfDeath) e).killCradle((CradleOfFilth) target); break;
					case "Imp": ((AngelOfDeath) e).killDemon((Demon) target); break;
					case "InfernalDemon": ((AngelOfDeath) e).killDemon((Demon) target); break;
					case "DemonCommander": ((AngelOfDeath) e).killDemon((Demon) target); break;
					case "unliving": targetresult.addAll(doULAction(target, e)); return targetresult;
					default: targetresult.addAll(doAngelOfDeathAction((AngelOfDeath) e)); return targetresult;
				}; break;
			case "unliving": targetresult.addAll(doULAction(e, target)); return targetresult;
			default: targetresult.addAll(doAction(e)); return targetresult;
		}
		
		targetresult.add(0, e); // add subject to front
		targetresult.add(1, target);
		return targetresult;
	}
	
	private List<Entity> doULAction(Entity e, Entity target) {
		// method that brings together some unliving entity actions
		
		List<Entity> result = new ArrayList<>();
		try {
			switch (e.toString()) {
				case "H":
					((HellFire) e).explode((LivingEntity) target);
					result.add(0, e);
					result.add(1, target);
					return result;
			}
		} catch (ClassCastException cx) {
			// target is unliving, do nothing
			result.add(0, e);
		}
		
		return result;
	}
	
	private List<Entity> doAngelOfDeathAction(AngelOfDeath e) {
		// angel specific randomizer
		List<Entity> result = new ArrayList<>();
		if (e.getCruelty()<1) {
			e.declareDead();
		} else {
			if (Randomizer.random(5)==0) {
				HellFire h = ((AngelOfDeath) e).dropHellFire(boardDimensions);
				result.add(h);
			} else {
				entityGen.moveRandomly(e);
			}
		}
		result.add(e);
		return result;
	}
		
	private Entity doSinnerAction(Sinner e) {
		// sinner specific randomizer
		int r = Randomizer.random(2);
		if (r==0) {
			e.pray();
			return e;
		} else {
			return entityGen.moveRandomly((LivingEntity) e);
		} 
	}
	
	private Entity doCradleAction(CradleOfFilth e) {
		// cof specific action randomizer
		if (e.getAge() == 3) {
			Imp ec = e.evolve(); // create imp object and delete cof object
			return ec;
		} else {
			return entityGen.moveRandomly((LivingEntity) e);
		}
	}
	
	private Entity doImpAction(Imp e) {
		// imp specific action randomizer
		if ((e.getCruelty() == 6) || (e.getAge() == 20)) {
			InfernalDemon ei = e.evolve(); // create infernaldemon object and delete imp object
			return ei;
		} else {
			return entityGen.moveRandomly((LivingEntity) e);
		}
	}
	
	private Entity doInfernalDemonAction(InfernalDemon e) {
		// infernaldemon specific action randomizer
		
		if ((e.getCruelty() == 18) || (e.getAge() == 50)) {
			DemonCommander einf = e.evolve();
			return einf;
		} else {
			return entityGen.moveRandomly((LivingEntity) e);
		}
	}
	
	private List<Entity> doDemonCommanderAction(DemonCommander e) {
		// demoncommander specific action randomizer
		
		List<Entity> result = new ArrayList<>();
		
		if (Randomizer.random(3)==0) {
			Imp summonedImp = e.summonImp(boardDimensions);
			result.add(summonedImp);
		} else {
			entityGen.moveRandomly(e);
		}
		result.add(e);
		return result;
	}
	
}
