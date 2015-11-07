package entities;

public abstract class Demon implements Moveable {
	
	final String name;
	Demon partner;
	int cruelty; // cruelty defines a demon's position within the hierarchy
	int[] currentPosition = new int[2];
	boolean isAlive = true;
	int age;

	public Demon(String name, String gender, int age, int[] currentPosition) {
		
		this.name = name;
	}
	
	public void growOlder() { // grow 1 year older
		this.age++;
	}
	
	public void killSinner(Sinner victim) { // demon kills a victim and gains cruelty
		victim.isAlive = false;
		this.cruelty++;
	}
	
	@Override
	public void moveLeft() {
		this.currentPosition[1]--; // subtract from second value of current position = move left
	}
	
	@Override
	public void moveRight() {
		this.currentPosition[1]++; // add to second value of current position = move right
	}
	
	@Override
	public void moveUp() {
		this.currentPosition[0]--; // subtract first value of current position = move up
	}
	
	@Override
	public void moveDown() {
		this.currentPosition[0]++; // add to first value of current position = move down
	}
}
