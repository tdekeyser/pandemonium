Pandemonium

TODO: 

1.folder to gather same classes;

2. accessor methods? -> other classes/packages don't have to increase anything
	-> e.g. in Imp

private int cruelty;
public void getCruelty() {return this.cruelty;}
public void increaseCruelty() {this.cruelty++;}
public void increaseCruelty(int c) {this.cruelty+c;}

public void cannibalize(Imp impVictim) {
		this.increaseCruelty();
		impVictim.isAlive = false;
		// and perhaps the same with 			isAlive?
	}

AND INTERESTING: THIS CAN BE DONE IN DEMON SUPERCLASS!
