package entities;

import java.util.Arrays;

public abstract class Entity implements Printable {
	// abstract superclass for all entities; defines a currentPosition
	
	private String type;
	private int[] currentPosition = new int[2]; // current position on the board

	public int[] getCurrentPosition() {
		// getter method for currentPosition
		return currentPosition;
	}
	
	public void setCurrentPosition(int[] newPosition) {
		// setter method for currentPosition
		currentPosition = newPosition;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	public String getType() {
		return type;
	}
	
	public String toStringLong(){
		StringBuilder info = new StringBuilder();
		info.append("Type: " + getType() + System.lineSeparator());
		info.append("Position: " + Arrays.toString(getCurrentPosition()) + System.lineSeparator());
		return info.toString();
	};
	
}
