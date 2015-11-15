package entities.living;

import entities.Entity;

public abstract class LivingEntity extends Entity implements Moveable {
	// superclass for every living entity
	
	private boolean isAlive = true;
	private int age;
	private final String gender;

	public LivingEntity(String gender, int[] currentPosition) {
		this.gender = gender;
		this.setCurrentPosition(currentPosition);
	}
	
	public boolean getIsAlive() {
		// get isAlive
		return this.isAlive;
	}
	
	public void declareDead () {
		// set isAlive to false
		this.isAlive = false;
	}
	
	public void setAge(int a) {
		// setter method for age
		this.age = a;
	}
	
	public int getAge() {
		// getter method for age
		return this.age;
	}
	
	public void increaseAge() {
		// grow 1 year older
		this.age++;
	}
	
	public String getGender() {
		return this.gender;
	}
	
	@Override
	public void moveLeft() {
		// subtract from second value of current position = move left
		int[] currentPosition = this.getCurrentPosition();
		currentPosition[1]--;
		this.setCurrentPosition(currentPosition);
	}
	
	@Override
	public void moveRight() {
		// add to second value of current position = move right
		int[] currentPosition = this.getCurrentPosition();
		currentPosition[1]++;
		this.setCurrentPosition(currentPosition);
	}
	
	@Override
	public void moveUp() {
		// subtract first value of current position = move up
		int[] currentPosition = this.getCurrentPosition();
		currentPosition[0]--;
		this.setCurrentPosition(currentPosition);
	}
	
	@Override
	public void moveDown() {
		// add to first value of current position = move down
		int[] currentPosition = this.getCurrentPosition();
		currentPosition[0]++;
		this.setCurrentPosition(currentPosition);
	}

}
