package framework;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.lang.InterruptedException;

import entities.Entity;

public class World {
	/*
	 * Main class that deals with the proceedings of the world simulator.
	 * It keeps track of the inputs, creates new states computed by Computer object, and visualises these states by Board object.
	 */
	
	private Log worldLog = new Log("WORLD LOG"); // simple log String
	
	private int amountOfEntities; 	// # living
	private int[] entityDistribution;
	private int amountOfStates;
	private int heat;
	private int chanceOnPlague;
	
	private int timeBetweenStates = 1000; // in ms
	private int stateNr = 1;
	private boolean PRINT_ON_SCREEN = false;
	
	private Computer computer;
	private Board board;
	
	private List<Entity> entityList = new ArrayList<>(); // current list of entities on board
	private List<String> unlivingActionList; // current list of unliving actions performed during last state
	
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
		if (boardDimensions[0]>50 || boardDimensions[1]>50) throw new IOException("Board dimensions cannot be larger than 25!");
		if (entityDistribution.length!=3) throw new IOException("Entity distribution must have three inputs!");
		if (entityDistribution[0]+entityDistribution[1]+entityDistribution[2]!=100) throw new IOException("Entity distribution must be totalled to 100!");
		
		this.amountOfStates = amountOfStates;
		this.amountOfEntities = amountOfEntities;
		this.entityDistribution = entityDistribution;
		this.heat = heat;
		this.chanceOnPlague = chanceOnPlague;

		this.computer = new Computer(boardDimensions, heat, chanceOnPlague);
		this.board = new Board(boardDimensions);

	}
	
	public void initialise() {
		// initialises the full board to a first state
		
		entityList.addAll(computer.createLivingEntities(amountOfEntities, entityDistribution));
		
		board.setBoardEntities(entityList);
		board.initialiseBoard();	// initialises board
		
		worldLog.appendToLog("Initial" + System.lineSeparator() + "heat: " + heat + System.lineSeparator() + "chance on plague: " + chanceOnPlague + System.lineSeparator() + board.toString());
		if (PRINT_ON_SCREEN) System.out.println(worldLog.fetchLog());
		
	}
	
	public void update(String id) {
		// separate living from unliving
		
		entityList.clear(); // clear the previous entityList
		
		List<Entity> livingList = new ArrayList<>();
		for (List<Entity> entitiesOnPosition : board.getBoardMap().values()) { // get new entities from Computer
			livingList.addAll(computer.activateEntities(entitiesOnPosition));
		}
		
		entityList.addAll(computer.activateUnliving(livingList)); // activate unliving entities and add surviving to entityList
		
		board.updateBoard(entityList); // send new entities to Board for update
		
		String updateStr = "State " + id + System.lineSeparator() + board.toString();		
		worldLog.appendToLog(updateStr); // write the update to worldLog
		if (PRINT_ON_SCREEN) System.out.println(updateStr); // immediately print the result to the screen
		
		unlivingActionList = computer.getUnlivingActions();
		for (String action : unlivingActionList) {
			worldLog.appendToLogWithoutDate(action);
		}
		
		try { //thread to sleep for the specified number of milliseconds
			Thread.sleep(timeBetweenStates);
		} catch (InterruptedException ie) {
		    System.out.println("Interrupted.");
		}	
	}
	
	public void printOnScreen() {
		PRINT_ON_SCREEN = true;
	}
	
	public void setTimeBetweenStates(int timeBetweenStates) throws IOException {
		if (timeBetweenStates<0) throw new IOException("Time between states should be greater than 0!");
		this.timeBetweenStates = timeBetweenStates;
	}
	
	public List<Entity> objectsAtPosition(int[] requestedPosition) {
		// returns list of entities at a given position
		return board.objectsAtPosition(requestedPosition);
	}
	
	public Map<String, List<Entity>> getBoardMap() {
		return board.getBoardMap();
	}
	
	public String[][] getBoardMatrix() {
		return board.getBoardMatrix();
	}
	
	public List<String> getUnlivingActions() {
		return unlivingActionList;
	}
	
	public String getWorldLog() {
		return worldLog.fetchLog();
	}
	
	public int getAmountOfStates() {
		return amountOfStates;
	}
	
	public void runOnce() {
		// proceeds by 1 state
		
		update(Integer.toString(stateNr));
		stateNr++;
	}
	
	public void run() {
		// runs the world according to the amount of states
		for (int i=0; i<amountOfStates; i++) {
			runOnce();
		}
	}	
	
	public static void main(String[] args) {
		// TEST World class, to demonstrate that the simulator can work without a GUI
		
		int amountOfStates = 10;
		int amountOfEntities = 10; // (living,unliving)
		int[] entityDistribution = {40, 20, 40}; // (S, CoF, I)
		int[] boardDimensions = {5, 5};
		int heat = 60; // random(2+heat); heat=10 --> P(target)=2/3; heat=100 --> P(target)=11/12=0.91
		int chanceOnPlague = 40; // P(spawn1Sinner)=1/f(x) and P(spawn1Cradle)=1/2f(x) if chanceOnPlague>=50, with f(100)=2; f(50)=4; f(0)=6
		
		try {
			World w = new World(amountOfStates, amountOfEntities, entityDistribution, boardDimensions, heat, chanceOnPlague);
			w.initialise();
			
			w.printOnScreen(); // allows printing of states in console
			w.setTimeBetweenStates(100);
			w.run();
			
			Entity first = w.entityList.get(0); // get an entity on the board
			System.out.println(first.toStringLong()); // print its characteristics
			System.out.println(first.fetchLog()); // print its log
	
		} catch (IOException io) { io.printStackTrace(); }
		
	}

}
