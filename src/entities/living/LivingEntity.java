package entities.living;

public abstract class LivingEntity implements Moveable {
	// superclass for every living entity
	
	private boolean isAlive = true;
	private int[] currentPosition = new int[2]; // current position on the board
	private int age;
	private final String gender;

	public LivingEntity(String gender, int[] currentPosition) {
		this.gender = gender;
		this.currentPosition = currentPosition;
	}
	
	public void declareDead () {
		// set isAlive to false
		this.isAlive = false;
	}
	
	public int[] getCurrentPosition() {
		// getter method for currentPosition
		return this.currentPosition;
	}
	
	public void setCurrentPosition(int[] newPosition) {
		// setter method for currentPosition
		this.currentPosition = newPosition;
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
