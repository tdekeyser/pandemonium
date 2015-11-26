package entities.unliving;

import java.util.ArrayList;
import java.util.List;

import entities.Entity;
import entities.living.LivingEntity;
import entities.living.Sinner;
import entities.living.demons.Demon;

public class DemonicFury extends UnlivingEntity {
	// demonic fury increases all demons' cruelty and decreases all sinners' divinity
	
	public static List<Entity> unleash(List<Entity> entityList) {
		List<Entity> newList = new ArrayList<>();
		for (int i=0; i<entityList.size(); i++) {
			
			LivingEntity living = (LivingEntity) entityList.get(i);
			switch (living.getType()) {
				case "Sinner": ((Sinner) living).decreaseDivinity(); break;
				case "CradleOfFilth": break;
				default: ((Demon) living).increaseCruelty(); break;
			}
			newList.add(living);
		}
		System.out.println("Demonic Fury unleashed.");
		return newList;
	}
	
}
