package entities;

public abstract class Entity {
	// abstract superclass for all entities; defines a currentPosition
	
	private int[] currentPosition = new int[2]; // current position on the board

	public int[] getCurrentPosition() {
		// getter method for currentPosition
		return this.currentPosition;
	}
	
	public void setCurrentPosition(int[] newPosition) {
		// setter method for currentPosition
		this.currentPosition = newPosition;
	}
	
	public abstract String toStringLong(); // obligatory board representation of entity object

}
