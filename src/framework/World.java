package framework;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.lang.InterruptedException;

import entities.Entity;

public class World {
	
	private Log worldLog = new Log("WORLD LOG"); // simple log String
	
	private int amountOfEntities; 	// # living
	private int[] entityDistribution;
	private int amountOfStates;
	private int timeBetweenStates = 1000; // in ms
	
	private Computer computer;
	private Board board;
	
	public List<Entity> entityList = new ArrayList<>();
	
	public World(
			int amountOfStates,
			int amountOfEntities,
			int[] entityDistribution,
			int[] boardDimensions,
			int heat,
			int chanceOnPlague
		) throws IOException {
		// attempts to create a World; throws IOException if there is an invalid input
		
		if (amountOfStates<0 || amountOfEntities<0 || entityDistribution[0]<0 || entityDistribution[1]<0 || entityDistribution[2]<0) throw new IOException("None of the input can be negative!");
		if (boardDimensions[0]<2 || boardDimensions[1]<2) throw new IOException("Board dimensions cannot be smaller than 2!");
		
		this.amountOfStates = amountOfStates;
		this.amountOfEntities = amountOfEntities;
		this.entityDistribution = entityDistribution;

		this.computer = new Computer(boardDimensions, heat, chanceOnPlague);
		this.board = new Board(boardDimensions);

	}
	
	public void initialise() {
		// initialises the full board to a first state
		
		entityList.addAll(computer.createLivingEntities(amountOfEntities, entityDistribution));
		
		board.setBoardEntities(entityList);
		board.initialiseBoard();	// initialises board
		
		worldLog.appendToLog("Initial" + System.lineSeparator() + board.toString());
		System.out.println(worldLog.fetchLog());
	}
	
	public void update(String id) {
		// separate living from unliving
		
		entityList.clear(); // clear the previous entityList
		
		List<Entity> livingList = new ArrayList<>();
		for (List<Entity> entitiesOnPosition : board.getBoardMap().values()) { // get new entities from Computer
			livingList.addAll(computer.activateEntities(entitiesOnPosition));
		}
		
		entityList.addAll(computer.activateUnliving(livingList)); // activate unliving entities and add surviving to entityList
		entityList.addAll(computer.spawnPlagued()); // spawn sinners/cradles acc to chanceOnPlague

		board.updateBoard(entityList); // send new entities to Board for update
		
		String updateStr = "State " + id + System.lineSeparator() + board.toString();		
		worldLog.appendToLog(updateStr); // write the update to worldLog
		System.out.println(updateStr); // immediately print the result to the screen
		
		try { //thread to sleep for the specified number of milliseconds
			Thread.sleep(timeBetweenStates);
		} catch (InterruptedException ie) {
		    System.out.println("Interrupted.");
		}	
	}
	
	public void setTimeBetweenStates(int timeBetweenStates) throws IOException {
		if (timeBetweenStates<0) throw new IOException("Time between states should be greater than 0!");
		this.timeBetweenStates = timeBetweenStates;
	}
	
	public List<Entity> objectsAtPosition(int[] requestedPosition) {
		// returns list of entities at a given position
		return board.objectsAtPosition(requestedPosition);
	}
	
	public String getWorldLog() {
		return worldLog.fetchLog();
	}
	
	public void run() {
		// runs the world according to the amount of states
		for (int i=1; i<=amountOfStates; i++) {
			update(Integer.toString(i));
		}
		System.out.println("World finished.");
	}
	
	public static void main(String[] args) {
		// TEST World class
		
		int amountOfStates = 120;
		int[] boardDimensions = {3, 3};
		int amountOfEntities = 4; // (living,unliving)
		int[] entityDistribution = {0, 0, 100}; // (S, CoF, I)
		int heat = 100; // random(2+heat); heat=10 --> P(target)=2/3; heat=100 --> P(target)=11/12=0.91
		int chanceOnPlague = 0; // P(spawn1Sinner)=1/f(x) and P(spawn1Cradle)=1/2f(x) if chanceOnPlague>=50, with f(100)=2; f(50)=4; f(0)=6
		
		try {
			World w = new World(amountOfStates, amountOfEntities, entityDistribution, boardDimensions, heat, chanceOnPlague);
			w.initialise();
			
			w.setTimeBetweenStates(0);
			w.run();
			
			Entity first = w.entityList.get(0);
			System.out.println(first.fetchLog());
			System.out.println(first.toStringLong());
	
		} catch (IOException io) { io.printStackTrace(); }
		
	}

}
