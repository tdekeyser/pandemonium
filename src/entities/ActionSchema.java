package entities;

import entities.living.CradleOfFilth;
import entities.living.LivingEntity;
import entities.living.Sinner;
import entities.living.demons.Imp;
import entities.living.demons.InfernalDemon;
import framework.Randomizer;

public class ActionSchema {
	/* provides entity random method activation
	 * Only 1 method is available outside this class: doAction(Entity e), which activates a random action of the given entity
	 */
	
	private int[] boardDimensions;
	
	public ActionSchema(int[] boardDimensions) {
		this.boardDimensions = boardDimensions;
	}
	
	public Entity doAction(Entity e) {
		/* checks the type of entity and passes to a specific randomizer method for that entity type
		 * Returns changed entity afterwards.
		 */
		
		switch (e.getType()) {
			case "Sinner": doSinnerAction((Sinner) e); break;
			case "CradleOfFilth": doCradleAction((CradleOfFilth) e); break;
			case "Imp": doImpAction((Imp) e); break;
		}
		return e;
	}
	
	private Entity doSinnerAction(Sinner e) {
		// sinner specific randomizer
		
		return moveRandomly((LivingEntity) e);
	}
	
	private Entity doCradleAction(CradleOfFilth e) {
		// cof specific action randomizer
		
		if (e.getAge() == 3) {
			Imp ec = e.evolve(); // create imp object and delete cof object
			e = null;
			return ec;
		} else {
			return moveRandomly((LivingEntity) e);
		}
	}
	
	private Entity doImpAction(Imp e) {
		// imp specific action randomizer
		
		if (e.getCruelty() == 6) {
			InfernalDemon ei = e.evolve(); // create infernaldemon object and delete imp object
			e = null;
			return ei;
		} else {
			return moveRandomly((LivingEntity) e);
		}
	}
	
	private LivingEntity moveRandomly(LivingEntity e) {
		// randomly move the moveable entity
		
		if ((e.getCurrentPosition()[0] == 0) && (e.getCurrentPosition()[1] == 0)) {
			// entity is in bottom left corner: can't move left or down
			int r = Randomizer.random(2);
			switch (r) {
				case 0: e.moveUp(); break;
				case 1: e.moveRight(); break;
			}
			return e;
		} else if ((e.getCurrentPosition()[0] == 0) && (e.getCurrentPosition()[0] == boardDimensions[0]-1)) {
			// entity is in top left corner: can't move up or left
			int r = Randomizer.random(2);
			switch (r) {
				case 0: e.moveDown(); break;
				case 1: e.moveRight(); break;
			}
			return e;
		} else if ((e.getCurrentPosition()[0] == 0) && (e.getCurrentPosition()[1] == boardDimensions[1]-1)) {
			// entity is in top right corner: can't move up or right
			System.out.println("ho");
			int r = Randomizer.random(2);
			System.out.println(r);
			switch (r) {
				case 0: e.moveDown(); break;
				case 1: e.moveRight(); break;
			}
			return e;
		} else if ((e.getCurrentPosition()[0] == boardDimensions[0]-1) && (e.getCurrentPosition()[1] == boardDimensions[1]-1)) {
			// entity is in bottom right corner: can't move down or right
			int r = Randomizer.random(2);
			switch (r) {
				case 0: e.moveLeft(); break;
				case 1: e.moveUp(); break;
			}
			return e;
		} else if (e.getCurrentPosition()[0] == 0) {
			// entity is at top: can't move up
			int r = Randomizer.random(3);
			switch (r) {
				case 0: e.moveLeft(); break;
				case 1: e.moveRight(); break;
				case 2: e.moveDown(); break;
			}
			return e;
		} else if (e.getCurrentPosition()[0] == (boardDimensions[0]-1)) {
			// entity is at bottom: can't move down
			int r = Randomizer.random(3);
			switch (r) {
				case 0: e.moveLeft(); break;
				case 1: e.moveUp(); break;
				case 2: e.moveRight(); break;
			}
			return e;
		} else if (e.getCurrentPosition()[1] == 0) {
			// entity is on left border: can't move left
			int r = Randomizer.random(3);
			switch (r) {
				case 0: e.moveRight(); break;
				case 1: e.moveUp(); break;
				case 2: e.moveDown(); break;
			}
			return e;
		} else if (e.getCurrentPosition()[1] == (boardDimensions[1]-1)) {
			// entity is on right border: can't move right
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
