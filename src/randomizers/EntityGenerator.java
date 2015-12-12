package randomizers;

import entities.Entity;
import entities.living.CradleOfFilth;
import entities.living.LivingEntity;
import entities.living.Sinner;
import entities.living.demons.AngelOfDeath;
import entities.living.demons.Imp;
import entities.unliving.HellFire;

public class EntityGenerator {
	// brings together methods to create new entities at random
	
	int[] boardDimensions;
	NameGenerator demonGen = new NameGenerator("demonic");
	NameGenerator angelGen = new NameGenerator("angelic");
	
	public EntityGenerator(int[] boardDimensions) {
		this.boardDimensions = boardDimensions;
	}
	
	public Entity spawnSinner() {
		return new Sinner(randomizeGender(), computeRandomPosition());
	}
	
	public Entity spawnCradle() {
		return new CradleOfFilth(randomizeGender(), computeRandomPosition());
	}
	
	public Entity spawnImp() {
		return new Imp(demonGen.getName(), randomizeGender(), computeRandomPosition());
	}
	
	public Entity spawnAngelOfDeath() {
		return new AngelOfDeath(demonGen.getName(), randomizeGender(), computeRandomPosition());
	}
	
	public Entity createHellFire(String placername, String placertype) {
		HellFire h = new HellFire(placername, placertype);
		h.setCurrentPosition(computeRandomPosition());
		return h;
	}
	
	public String randomizeGender() {
		// randomize gender
		String gender = "Unknown";
		int g = Randomizer.random(2);
		switch (g) {
			case 0:	gender = "male"; break;
			case 1: gender = "female"; break;
		}
		return gender;
	}
	
	public int[] computeRandomPosition() {
		// gets a random position depending on Board dimensions
		
		int[] newPosition = new int[2];
		newPosition[0] = Randomizer.random(boardDimensions[0]);
		newPosition[1] = Randomizer.random(boardDimensions[1]);
		return newPosition;
	}
	
	public LivingEntity moveRandomly(LivingEntity e) {
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