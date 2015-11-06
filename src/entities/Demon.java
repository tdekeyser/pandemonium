package entities;

public abstract class Demon { // abstract Demon class
	String name;
	String gender;
	Demon partner;
	int age;
	int cruelty;

	public Demon() {
		// TODO Auto-generated constructor stub
	}
	
	public void growOlder() { // grow 1 year older
		this.age++;
	}
}
