package framework;

import entities.living.CradleOfFilth;
import entities.living.demons.Imp;

public class EntitySchema {

	public static void main(String[] args) {
		
		int[] cofPos = {3,4};
		CradleOfFilth cof = new CradleOfFilth("female", cofPos);
		
		cof.increaseAge();
		cof.increaseAge();
		
		System.out.println(cof.getAge());
		
		Imp impie = cof.evolve();
		cof = null;
		
		System.out.println(impie.toString());
		
	}

}
