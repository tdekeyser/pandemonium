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

	JPanel infoPanel;
	private int r; // # rows
	private int c; // # columns
	private Map<String, JButton> buttonGrid = new HashMap<String, JButton>();
	private String[][] boardMatrix;
	private Map<String, List<Entity>> boardMap;
	
	public GridPanel(JPanel infoPanel, int r, int c, String[][] boardMatrix, Map<String, List<Entity>> boardMap) {
		this.infoPanel = infoPanel;
		this.r = r;
		this.c = c;
		this.boardMatrix = boardMatrix;
		this.boardMap = boardMap;
		
		this.setLayout(new GridLayout(r,c)); // == boardDimensions
		this.setPreferredSize(new Dimension(500,500));
		
		makeButtonGrid();
		
	}
	
	public void makeButtonGrid() {

		GridListener gridListener = new GridListener(infoPanel, r, c);
		
		for (int i=0; i<r; i++) {
			for (int j=0; j<c; j++) {
				JButton button = new JButton(boardMatrix[i][j]); // create new button on each position
				
				if (boardMatrix[i][j].equals("")) {
					button.setEnabled(false);  // no entities on this position, so button can't be clicked
				} else {
					int[] position = {i, j};
					buttonGrid.put(Arrays.toString(position), button); // available button added to Map object
					button.addActionListener(gridListener);
				}
				this.add(button);
			}
		}
		
		gridListener.setMaps(buttonGrid, boardMap);
		
		this.revalidate();
	}

}
