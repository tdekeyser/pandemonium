package entities.unliving;

import java.util.ArrayList;
import java.util.List;

import entities.Entity;
import entities.living.Sinner;
import entities.living.demons.Demon;
import framework.Log;

public class DemonicFury extends UnlivingEntity {
	// demonic fury increases all demons' cruelty and decreases all sinners' divinity
	
	public static List<Entity> unleash(List<Entity> entityList) {
		List<Entity> newList = new ArrayList<>();
		for (int i=0; i<entityList.size(); i++) {
			Entity e = entityList.get(i);
			try {
				switch (e.getType()) {
					case "Sinner": ((Sinner) e).decreaseDivinity(); break;
					case "CradleOfFilth": break;
					case "unliving": throw new ClassCastException();
					default: ((Demon) e).increaseCruelty(); break;
				}
				newList.add(e);
			} catch (ClassCastException cx) { newList.add(e); continue; }
		}
		
		return newList;
	}
	
}
