package gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JPanel;

import entities.Entity;

public class GridPanel extends JPanel {
	
	private JPanel textPanel;
	private int r; // # rows
	private int c; // # columns
	private String[][] boardMatrix; // boardMatrix of World instance; matrix of Strings
	private Map<String, List<Entity>> boardMap; // boardMap from World instance; contains list of Entities per position
	private Map<JButton, String> buttonGrid; // HashMap contains position per button in grid
	
	public GridPanel(JPanel textPanel, int r, int c, String[][] boardMatrix, Map<String, List<Entity>> boardMap) {
		this.textPanel = textPanel;
		this.r = r;
		this.c = c;
		this.boardMatrix = boardMatrix;
		this.boardMap = boardMap;
		this.buttonGrid = new HashMap<JButton, String>(); // Maps buttons to their position within the grid
		
		this.setLayout(new GridLayout(r,c)); // == boardDimensions
		this.setPreferredSize(new Dimension(500,500));
		
		makeButtonGrid();
		
	}
	
	private void makeButtonGrid() {
		 /*
		  * For each row and for each column, create new button with corresponding boardMatrix text.
		  * 
		  * If there is the boardMatrix position is empty (i.e. there's no Entity on this position),
		  * the button cannot be clicked.
		  * If there is at least one Entity on the position, the button is connected to a GridListener object,
		  * and the button and its position are added to a Map<> buttonGrid, that is at the end passed on to
		  * the GridListener object for Entity info matching (see further info in GridListener.java).
		  * 
		  */
		
		GridListener gridListener = new GridListener(textPanel);
		
		for (int i=0; i<r; i++) {
			for (int j=0; j<c; j++) {
				JButton button = new JButton(boardMatrix[i][j]);
				
				if (boardMatrix[i][j].equals("")) {
					button.setEnabled(false);  // no entities on this position, so button can't be clicked
				} else {
					button.addActionListener(gridListener);
					int[] position = {i, j};
					buttonGrid.put(button, Arrays.toString(position)); // available button added to Map object
				}
				this.add(button); // GridLayout default adds left to right, top to bottom
			}
		}
		
		gridListener.setMaps(buttonGrid, boardMap); // pass Map<> buttonGrid and boardMap to GridListener object
		
	}

}
