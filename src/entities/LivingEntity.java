package entities;

public abstract class LivingEntity implements Moveable {
	// superclass for every living entity
	
	boolean isAlive = true;
	int[] currentPosition = new int[2]; // current position on the board
	int age;
	String gender;

	public LivingEntity(String gender, int[] currentPosition) {
		this.gender = gender;
		this.currentPosition = currentPosition;
	}
	
	public void growOlder() {
		// grow 1 year older
		this.age++;
	}
	
	@Override
	public void moveLeft() {
		// subtract from second value of current position = move left
		this.currentPosition[1]--;
	}
	
	@Override
	public void moveRight() {
		// add to second value of current position = move right
		this.currentPosition[1]++;
	}
	
	@Override
	public void moveUp() {
		// subtract first value of current position = move up
		this.currentPosition[0]--;
	}
	
	@Override
	public void moveDown() {
		// add to first value of current position = move down
		this.currentPosition[0]++;
	}

}
