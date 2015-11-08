package entities;
import java.util.Arrays;

public class Imp extends Demon {
	/* class for the lesser demon, 3rd in hierarchy and first mature
	 * Imp has following variables:
	 * 		String name,
	 * 		String gender,
	 * 		int age,
	 * 		boolean isAlive,
	 * 		int[] currentPosition,
	 * 		Demon partner,
	 * 		int cruelty
	 */

	public Imp(String name, String gender, int[] currentPosition) {
		super(name, gender, currentPosition);
		super.age = 4;
	}
	
	public void cannibalize(Imp impVictim) {
		// destroys Imp object at same position and increases cruelty
		impVictim.isAlive = false;
		this.cruelty++;
	}
	
	public static void main(String[] args) {
		int[] pos = {3,4};
		Imp zot = new Imp("zot", "man", pos);
		CradleOfFilth c = new CradleOfFilth("man", pos);
		System.out.println(Arrays.toString(c.currentPosition));
		
		System.out.println("result is" + zot.age + zot.name + zot.isAlive +zot.gender);
		
	}
	
}
