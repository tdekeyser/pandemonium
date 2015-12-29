package entities;

import java.util.Arrays;

import framework.Log;

public abstract class Entity implements Printable {
	// abstract superclass for all entities; defines a currentPosition

	private boolean isAlive = true; // means as much as "has to appear in next state"
	private String type;
	private int[] currentPosition = new int[2]; // current position on the board
	private Log log = new Log();

	public int[] getCurrentPosition() {
		// getter method for currentPosition
		return currentPosition;
	}
	
	public boolean isAlive() {
		// get isAlive
		return isAlive;
	}
	
	public void setAlive(boolean b) {
		isAlive = b;
	}
	
	public void declareDead() {
		// set isAlive to false
		isAlive = false;
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
	
	public void appendToLog(String entry) {
		log.appendToLogWithoutDate(entry);
	}
	
	public void copyAndAppendToLog(Entity copier, String entry) {
		this.appendToLog(copier.fetchLog());
		this.appendToLog(entry);
	}
	
	public void emptyLog() {
		log.emptyLog();
	}
	
	public String fetchLog() {
		return log.fetchLog();
	}
	
	public String toStringLong(){
		StringBuilder info = new StringBuilder();
		info.append("Type: " + getType() + System.lineSeparator());
		info.append("Position: " + Arrays.toString(getCurrentPosition()) + System.lineSeparator());
		return info.toString();
	};
	
}
