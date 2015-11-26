package framework;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.lang.InterruptedException;

import entities.Entity;

public class World {
	
	private Log worldLog = new Log("WORLD LOG"); // simple log String
	
	private int[] amountOfEntities = new int[2]; 	// with [0] living and [1] unliving
	private int[] entityDistribution;
	private int amountOfStates;
	private int timeBetweenStates = 1000; // in ms
	
	private Computer computer;
	private Board board;
	
	private List<Entity> entityList = new ArrayList<>();
	
	public World(
			int amountOfStates,
			int[] amountOfEntities,
			int[] entityDistribution,
			int[] boardDimensions,
			int heat,
			int chanceOnPlague
		) throws IOException {
		// attempts to create a World; throws IOException if there is an invalid input
		
		this.amountOfStates = amountOfStates;
		this.amountOfEntities = amountOfEntities;
		this.entityDistribution = entityDistribution;
		
		this.computer = new Computer(boardDimensions, heat, chanceOnPlague);
		this.board = new Board(boardDimensions);

	}
	
	public void initialise() {
		// initialises the full board to a first state
		
		entityList.addAll(computer.createLivingEntities(amountOfEntities[0], entityDistribution));
		
		board.setBoardEntities(entityList);
		board.initialiseBoard();	// initialises board
		
		worldLog.appendToLog("Initial" + System.lineSeparator() + board.toString());
		System.out.println(worldLog.fetchLog());
	}
	
	public void update(String id) {
		// separate living from unliving
		
		// first do unliving entities, then living
		// unlivingl = activateUnliving(unlivinglist);
		
		entityList.clear();
		// get new entities from Computer
		
		for (List<Entity> entitiesOnPosition : board.getBoardMap().values()) {
			entityList.addAll(computer.activateLiving(entitiesOnPosition));
		}
		
		// spawn sinners acc to chanceOnPlague
		entityList = computer.spawnPlagues(entityList);

		// send new entities to Board for update
		board.updateBoard(entityList);
		
		String updateStr = "State " + id + System.lineSeparator() + board.toString();		
		worldLog.appendToLog(updateStr);
		System.out.println(updateStr);
		try {
		    //thread to sleep for the specified number of milliseconds
			Thread.sleep(timeBetweenStates);
		} catch (InterruptedException ie) {
		    System.out.println(ie);
		}	
	}
	
	public void setTimeBetweenStates(int timeBetweenStates) {
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
		for (int i=1; i<amountOfStates; i++) {
			update(Integer.toString(i));
		}
	}
	
	public static void main(String[] args) {
		// TEST World class
		
		int amountOfStates = 50;
		int[] boardDimensions = {3, 3};
		int[] amountOfEntities = {4, 0}; // (living,unliving)
		int[] entityDistribution = {80, 10, 10}; // (S, CoF, I)
		int heat = 0; // P(kill)=2/3
		int chanceOnPlague = 0;
		
		try {
			World w = new World(amountOfStates, amountOfEntities, entityDistribution, boardDimensions, heat, chanceOnPlague);
			w.initialise();
			
			w.setTimeBetweenStates(10);
			w.run();
			
			//int[] rp = {3,4};
//			for (Entity o : w.objectsAtPosition(rp)) {
//				System.out.println(o.toStringLong());
//			}
		} catch (IOException io) { io.printStackTrace(); }
		
	}

}
