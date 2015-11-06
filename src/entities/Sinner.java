package entities;

public class Sinner implements Moveable {
	int age;
	int divinity;
	int[] currentPosition = new int[2];
	boolean isAlive = true; // ironic naming, but still...
	
	public Sinner() {
		// TODO Auto-generated constructor stub
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
