package framework;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import entities.Entity;

public class World {
	
	private Log worldLog = new Log("WORLD LOG"); // simple log String
	
	private int[] amountOfEntities = new int[2]; 	// with [0] living and [1] unliving
	private int[] entityDistribution;
	private int amountOfStates;
	
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
		
	}
	
	public List<Entity> objectsAtPosition(int[] requestedPosition) {
		// returns list of entities at a given position
		return board.objectsAtPosition(requestedPosition);
	}
	
	public String getWorldLog() {
		return worldLog.fetchLog();
	}
	
	public void run() {
		for (int i=0; i<amountOfStates; i++) {
			// proceed by one state
		}
	}
	
	public static void main(String[] args) {
		// TEST World class
		
		int amountOfStates = 0;
		int[] boardDimensions = {7, 7};
		int[] amountOfEntities = {10, 0};
		int[] entityDistribution = {40, 30, 30};
		int heat = 0;
		int chanceOnPlague = 0;
		
		try {
			World w = new World(amountOfStates, amountOfEntities, entityDistribution, boardDimensions, heat, chanceOnPlague);
			w.initialise();
			System.out.println(w.getWorldLog());
		} catch (IOException io) { io.printStackTrace(); }
		
	}

}
