package framework;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import entities.Entity;
import entities.living.CradleOfFilth;
import entities.living.demons.Imp;

public class Board {
	
	private final int[] boardDimensions = new int[2];
	private String[][] boardMatrix;						// boardMatrix is the visual representation of the board
	private Map<String, List<Entity>> boardMap = new HashMap<String, List<Entity>>(); 	// HashMap that contains objects on every position
	
	private List<Entity> boardEntities = new ArrayList<>();

	public Board() throws IOException {
		// Default board settings is 7 rows and 7 columns
		this(7, 7);
		this.cleanMatrix();
	}
	
	public Board(int rows, int columns) throws IOException {
		// overloaded constructor to define custom rows and columns
		if (rows < 1 || columns < 1) { throw new IOException("Board: Dimensions cannot be smaller than 1."); }
		this.boardDimensions[0] = rows;
		this.boardDimensions[1] = columns;
		this.boardMatrix = new String[rows][columns];
		this.cleanMatrix();
	}
	
	public void setBoardEntities(List<Entity> newEntities) {
		// set board entities
		this.boardEntities = newEntities;
	}
	
	public int[] getBoardDimensions() {
		// get Array of board dimensions
		return this.boardDimensions;
	}
	
	public void cleanMatrix() {
		for (int i=0; i<boardMatrix.length; i++) {
			Arrays.fill(boardMatrix[i], "");
		}
	}
	
	public void initialiseBoard() {
		// make a board setting with current board entities
		
		for (Entity entity : this.boardEntities) {
			
			int[] epos = entity.getCurrentPosition();
			this.boardMatrix[epos[0]][epos[1]] += entity.toString() + " ";
			
			String eposStr = Arrays.toString(epos);
			
			if (this.boardMap.containsKey(eposStr)) {
				// add the entity to list if there exists a key with this position
				this.boardMap.get(eposStr).add(entity);
			} else {
				// else create new key-value in boardMap
				List<Entity> li = new ArrayList<>();
				li.add(entity);
				this.boardMap.put(eposStr, li);
			}
		}
	}
	
	public void updateBoard(List<Entity> newEntities) {
		// update the board to a new setting
		this.setBoardEntities(newEntities);	// set new entity list
		this.cleanMatrix();					// clear matrix
		this.boardMap.clear(); 				// clear boardmap to allow position switch
		this.initialiseBoard(); 			// re-initialise board with new list of entities
	}
	
	@Override
	public String toString() {
		// basic string representation of Board; i.e. a pile of arrays
		
		StringBuilder boardString = new StringBuilder();
		for (int i=0; i<boardMatrix.length; i++) {
			boardString.append(Arrays.toString(boardMatrix[i]));
			boardString.append("\n");
		}
		return boardString.toString();
	}
	
	public static void main(String[] args) {
		// TEST Board
		
		int[] pos = {3,4};
		int[] pos2 = {5,6};
		CradleOfFilth cof = new CradleOfFilth("male", pos);
		Imp impie = cof.evolve();
		CradleOfFilth cof2 = new CradleOfFilth("female", pos2);
		
		try {
			Board a = new Board();
			List<Entity> boardEntities = new ArrayList<>();
			boardEntities.add(cof);
			boardEntities.add(impie);
			a.setBoardEntities(boardEntities);
			a.initialiseBoard();
			
			System.out.println(a.toString());
			
			List<Entity> entitiesOnPosition34 = a.boardMap.get("[3, 4]");
			System.out.println(entitiesOnPosition34);
			System.out.println(entitiesOnPosition34.get(1).toStringLong());
			
			List<Entity> second = new ArrayList<>();
			second.add(impie);
			second.add(cof2);
			
			a.updateBoard(second);
			
			System.out.println(a.toString());
			
			
		} catch (IOException io) { io.printStackTrace(); }
		
	}

}
