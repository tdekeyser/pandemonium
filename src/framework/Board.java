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
	
	private int[] boardDimensions = new int[2];		// boardDimensions comprises [height, width]
	private String[][] boardMatrix;			// boardMatrix is the visual representation of the board
	private Map<String, List<Entity>> boardMap = new HashMap<String, List<Entity>>(); 	// HashMap that contains objects on every position
	
	private List<Entity> boardEntities = new ArrayList<>();

	public Board(int[] boardDimensions) throws IOException {
		// overloaded constructor to define custom rows and columns
		
		int rows = boardDimensions[0];
		int columns = boardDimensions[1];
		if (rows < 1 || columns < 1) { throw new IOException("Board: Dimensions cannot be smaller than 1."); }
		
		this.boardDimensions = boardDimensions;
		boardMatrix = new String[rows][columns];
		clearMatrix();
		
	}
	
	public void setBoardEntities(List<Entity> newEntities) {
		// set board entities
		this.boardEntities = newEntities;
	}
	
	public void initialiseBoard() {
		// make a board setting with current board entities
		
		for (Entity entity : boardEntities) {
			
			int[] epos = entity.getCurrentPosition();
			boardMatrix[epos[0]][epos[1]] += entity.toString() + " ";

			String eposStr = Arrays.toString(epos);
			
			if (boardMap.containsKey(eposStr)) {
				// add the entity to list if there exists a key with this position
				boardMap.get(eposStr).add(entity);
			} else {
				// else create new key-value in boardMap
				List<Entity> li = new ArrayList<>();
				li.add(entity);
				boardMap.put(eposStr, li);
			}
		}
	}
	
	public void updateBoard(List<Entity> newEntities) {
		// update the board to a new setting
		
		setBoardEntities(newEntities);	// set new entity list
		clearMatrix();					// clear matrix
		boardMap.clear(); 				// clear boardmap to allow position switch
		initialiseBoard(); 			// re-initialise board with new list of entities
	}
	
	public Map<String, List<Entity>> getBoardMap() {
		// returns complete boardMap
		return boardMap;
	}
	
	public String[][] getBoardMatrix() {
		return boardMatrix;
	}
	
	public List<Entity> objectsAtPosition(int[] requestedPosition) {
		// returns list of entities at a given position
		return boardMap.get(Arrays.toString(requestedPosition));
	}
	
	public int[] getBoardDimensions() {
		// get Array of board dimensions
		return boardDimensions;
	}
	
	private void clearMatrix() {
		// empties boardMatrix fields (i.e. fills it will empty strings)
		
		for (int i=0; i<boardMatrix.length; i++) {
			Arrays.fill(boardMatrix[i], "");
		}
	}
	
	@Override
	public String toString() {
		// basic string representation of Board; i.e. a pile of arrays
		
		StringBuilder boardString = new StringBuilder();
		for (int i=0; i<boardMatrix.length; i++) {
			boardString.append(Arrays.toString(boardMatrix[i]) + System.lineSeparator());
		}
		return boardString.toString();
	}
	
}
