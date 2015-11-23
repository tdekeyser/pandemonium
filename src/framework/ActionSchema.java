package framework;

import java.util.ArrayList;
import java.util.List;

import entities.Entity;
import entities.living.CradleOfFilth;
import entities.living.LivingEntity;
import entities.living.Sinner;
import entities.living.demons.DemonCommander;
import entities.living.demons.Imp;
import entities.living.demons.InfernalDemon;

public class ActionSchema {
	/* provides entity random method activation
	 * Only 1 (overloaded) method is available outside this class: doAction(Entity e) and doAction(Entity e, Entity target)
	 */
	
	private int[] boardDimensions;
	
	public ActionSchema(int[] boardDimensions) {
		this.boardDimensions = boardDimensions;
	}
	
	public Entity doAction(Entity e) {
		/* checks the type of entity and passes to a specific randomizer method for that entity type
		 * Returns changed entity afterwards.
		 */
		Entity eResult = e;
		
		switch (e.getType()) {
			case "Sinner": eResult = doSinnerAction((Sinner) e); break;
			case "CradleOfFilth": eResult = doCradleAction((CradleOfFilth) e); break;
			case "Imp": eResult = doImpAction((Imp) e); break;
			case "InfernalDemon": eResult = doInfernalDemonAction((InfernalDemon) e); break;
			case "DemonCommander": eResult = moveRandomly((LivingEntity) e); break;
		}
		return eResult;
	}
	
	public List<Entity> doAction(Entity e, Entity target) throws NoSuchMethodException {
		/* checks the type of entity and randomizes methods with a target
		 * Returns changed entity afterwards.
		 */
		List<Entity> targetresult = new ArrayList<>(); // 0: e, 1: tar
		
		switch (e.getType()) {
			case "Imp":
				switch (target.getType()) {
					case "Sinner": ((Imp) e).killSinner((Sinner) target); break;
					case "Imp": ((Imp) e).cannibalize((Imp) target); break;
					default: throw new NoSuchMethodException();
				}; break;
			case "InfernalDemon":
				if (target.getType().equals("Sinner")) {
					int r = Randomizer.random(2);
					if (r == 0) {
						((InfernalDemon) e).killSinner((Sinner) target);
					} else {
						((InfernalDemon) e).tortureSinner((Sinner) target);
					}
				} else {
					throw new NoSuchMethodException();
				}; break;
			case "DemonCommander": 
				switch (target.getType()) {
					case "Sinner": ((DemonCommander) e).killSinner((Sinner) target); break;
					default: throw new NoSuchMethodException();
				}; break;
		}
		
		targetresult.add(0, e); // add subject to front
		targetresult.add(1, target);		
		return targetresult;
	}
		
	private Entity doSinnerAction(Sinner e) {
		// sinner specific randomizer
		
		return moveRandomly((LivingEntity) e);
	}
	
	private Entity doCradleAction(CradleOfFilth e) {
		// cof specific action randomizer
		
		if (e.getAge() == 3) {
			Imp ec = e.evolve(); // create imp object and delete cof object
			return ec;
		} else {
			return moveRandomly((LivingEntity) e);
		}
	}
	
	private Entity doImpAction(Imp e) {
		// imp specific action randomizer
		
		if ((e.getCruelty() == 6) || (e.getAge() == 20)) {
			InfernalDemon ei = e.evolve(); // create infernaldemon object and delete imp object
			return ei;
		} else {
			return moveRandomly((LivingEntity) e);
		}
	}
	
	private Entity doInfernalDemonAction(InfernalDemon e) {
		// infernaldemon specific action randomizer
		
		if ((e.getCruelty() == 16) || (e.getAge() == 50)) {
			DemonCommander einf = e.evolve();
			return einf;
		} else {
			return moveRandomly((LivingEntity) e);
		}
	}
	
	private LivingEntity moveRandomly(LivingEntity e) {
		// randomly move the moveable entity
		int onePos = e.getCurrentPosition()[0];
		int twoPos = e.getCurrentPosition()[1];
		int oneDimLimit = boardDimensions[0]-1;
		int twoDimLimit = boardDimensions[1]-1;
		
		if ((onePos == 0) && (twoPos == 0)) {
			// entity is in top left corner (0,0)
			int r = Randomizer.random(2);
			switch (r) {
				case 0: e.moveDown(); break;
				case 1: e.moveRight(); break;
			}
			return e;
		} else if ((onePos == 0) && (twoPos == twoDimLimit)) {
			// entity is in top right corner (0, B)
			int r = Randomizer.random(2);
			switch (r) {
				case 0: e.moveDown(); break;
				case 1: e.moveLeft(); break;
			}
			return e;
		} else if ((onePos == oneDimLimit) && (twoPos == 0)) {
			// entity is in bottom left corner (B, 0)
			int r = Randomizer.random(2);
			switch (r) {
				case 0: e.moveUp(); break;
				case 1: e.moveRight(); break;
			}
			return e;
		} else if ((onePos == oneDimLimit) && (twoPos == twoDimLimit)) {
			// entity is in bottom right corner (B, B)
			int r = Randomizer.random(2);
			switch (r) {
				case 0: e.moveLeft(); break;
				case 1: e.moveUp(); break;
			}
			return e;
		} else if (onePos == 0) {
			// entity is at (0, 0<x<B)
			int r = Randomizer.random(3);
			switch (r) {
				case 0: e.moveLeft(); break;
				case 1: e.moveRight(); break;
				case 2: e.moveDown(); break;
			}
			return e;
		} else if (onePos == oneDimLimit) {
			// entity is at (B, 0<x<B)
			int r = Randomizer.random(3);
			switch (r) {
				case 0: e.moveLeft(); break;
				case 1: e.moveUp(); break;
				case 2: e.moveRight(); break;
			}
			return e;
		} else if (twoPos == 0) {
			// entity is on (0<x<B, 0)
			int r = Randomizer.random(3);
			switch (r) {
				case 0: e.moveRight(); break;
				case 1: e.moveUp(); break;
				case 2: e.moveDown(); break;
			}
			return e;
		} else if (twoPos == twoDimLimit) {
			// entity is on (0<x<B, B)
			int r = Randomizer.random(3);
			switch (r) {
				case 0: e.moveLeft(); break;
				case 1: e.moveUp(); break;
				case 2: e.moveDown(); break;
			}
			return e;
		} else {
			int r = Randomizer.random(4);
			switch (r) {
				case 0: e.moveLeft(); break;
				case 1: e.moveUp(); break;
				case 2: e.moveDown(); break;
				case 3: e.moveRight(); break;
			}
			return e;
		}
	}
}
