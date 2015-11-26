package actionschemas;

import java.util.List;

import entities.Entity;
import entities.unliving.DemonicFury;
import framework.Randomizer;

public class UActionSchema {
	
	private int[] boardDimensions;
	
	public UActionSchema(int[] boardDimensions) {
		this.boardDimensions = boardDimensions;
	}
	
	public List<Entity> demonicFury(List<Entity> entityList, int heat) {
		if (Randomizer.random(110-heat) == 0) { // P(demonicFury)=1/110-heat
			return DemonicFury.unleash(entityList);
		} else {
			return entityList;
		}
	}
}
