package entities;

// Interface for any entity that can move
interface Moveable {	
	
	public boolean isMoveable(int[] currentPosition); // check whether entity can go somewhere
	
	public void moveLeft();
	public void moveRight();
	public void moveUp();
	public void moveDown();
	
}
