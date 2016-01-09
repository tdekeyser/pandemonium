package entities.unliving;

import java.util.ArrayList;
import java.util.List;

import entities.Entity;
import entities.living.Sinner;
import entities.living.demons.Demon;

public class DemonicFury extends UnlivingEntity {
	// demonic fury increases all demons' cruelty and decreases all sinners' divinity
	
	public static List<Entity> unleash(List<Entity> entityList) {
		List<Entity> newList = new ArrayList<>();
		for (int i=0; i<entityList.size(); i++) {
			Entity e = entityList.get(i);
			switch (e.getType()) {
				case "Sinner": ((Sinner) e).decreaseDivinity(); break;
				case "CradleOfFilth": break;
				case "unliving": break;
				default: ((Demon) e).increaseCruelty(); break;
			}
			newList.add(e);
		}
		
		return newList;
	}
	
}
