package entities.living;

import java.util.Arrays;

import entities.Entity;

public abstract class LivingEntity extends Entity implements Moveable {
	// superclass for every living entity
	
	private int age;
	private final String gender;

	public LivingEntity(String gender, int[] currentPosition) {
		this.gender = gender;
		setAge(1);
		setCurrentPosition(currentPosition);
	}
	
	@Override
	public void declareDead() {
		// set isAlive to false
		setAlive(false);
		appendToLog("Died: " + toStringLong());
	}
	
	public void setAge(int age) {
		// setter method for age
		this.age = age;
	}
	
	public int getAge() {
		// getter method for age
		return age;
	}
	
	public void increaseAge() {
		// grow 1 year older
		age++;
	}
	
	public String getGender() {
		return gender;
	}
	
	@Override
	public void moveLeft() {
		// subtract from second value of current position = move left
		int[] currentPosition = getCurrentPosition();
		currentPosition[1]--;
		setCurrentPosition(currentPosition);
		appendToLog("Moved to " + Arrays.toString(currentPosition));
	}
	
	@Override
	public void moveRight() {
		// add to second value of current position = move right
		int[] currentPosition = getCurrentPosition();
		currentPosition[1]++;
		setCurrentPosition(currentPosition);
		appendToLog("Moved to " + Arrays.toString(currentPosition));
	}
	
	@Override
	public void moveUp() {
		// subtract first value of current position = move up
		int[] currentPosition = getCurrentPosition();
		currentPosition[0]--;
		setCurrentPosition(currentPosition);
		appendToLog("Moved to " + Arrays.toString(currentPosition));
	}
	
	@Override
	public void moveDown() {
		// add to first value of current position = move down
		int[] currentPosition = getCurrentPosition();
		currentPosition[0]++;
		setCurrentPosition(currentPosition);
		appendToLog("Moved to " + Arrays.toString(currentPosition));
	}

}
