package entities;

public class Sinner implements Moveable {

	int divinity;
	boolean isAlive = true; // ironic naming, but still...
	int[] currentPosition = new int[2];
	int age;
	
	public Sinner() {
	}
	
	public void growOlder() { // grow 1 year older
		this.age++;
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
