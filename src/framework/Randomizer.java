package framework;

import java.io.IOException;

public class Randomizer {
	/* Randomizer returns random integers using the module Math.random().
	 * 
	 * Randomizer.random(int border) returns a random integer within the interval [1, border]
	 */
	
	private static double compute_random(int border) {
		return Math.random()*border + 1;
	}
	
	public static int random(int border) throws IOException {
		if (border < 1) throw new IOException("Randomizer: Right border cannot be smaller 1.");
		return (int) compute_random(border);
	}
	
	public static void main(String[] args) throws IOException {
		// TEST Randomizer.random()
		
		System.out.println(Randomizer.random(5));
		System.out.println(Randomizer.random(120));
		try {
			Randomizer.random(0); 		// should return IOException
		} catch (IOException io) {
			System.out.println("Hey, found a border smaller than 0.");
		}
	}

}
