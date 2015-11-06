package entities;

public abstract class Demon implements Moveable {
	final String name;
	final String gender;
	
	int age;
	boolean isAlive = true; // when demon is created, it is alive by default
	Demon partner;
	
	int cruelty; // cruelty defines a demon's position within the hierarchy
	int[] currentPosition = new int[2]; // coordinates on the board

	public Demon(String name, int age, String gender) {
		this.name = name;
		this.age = age;
		this.gender = gender;
	}
	
	public void growOlder() { // grow 1 year older
		this.age++;
	}
	
	public void killSinner(Sinner victim) { // demon kills a victim and gains cruelty
		victim.isAlive = false;
		this.cruelty++;
	}
	
	@Override
	public boolean isMoveable(int[] currentPosition) {
		// check current position against board
		return true;
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
