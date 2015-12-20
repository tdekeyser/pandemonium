package gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import entities.Entity;

public class GridListener implements ActionListener {
	
	JPanel infoPanel;
	JPanel textPanel = new JPanel();
	JTextArea charInfo = new JTextArea();
	
	int rows;
	int columns;
	private Map<String, List<Entity>> boardMap;
	private Map<String, JButton> buttonGrid;

	public GridListener(JPanel infoPanel, int rows, int columns) {
		this.infoPanel = infoPanel;
		this.rows = rows;
		this.columns = columns;
		
		textPanel.add(charInfo);
		
		textPanel.setOpaque(true); // must be opaque to be able to change background of JLabel
		textPanel.setBackground(Color.WHITE);
		infoPanel.add(textPanel);
		
	}
	
	public void setMaps(Map<String, JButton> buttonGrid, Map<String, List<Entity>> boardMap) {
		this.buttonGrid = buttonGrid;
		this.boardMap = boardMap;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		for (int i=0; i<rows; i++) {
			for (int j=0; j<columns; j++) {
				int[] position = {i,j};
				String posStr = Arrays.toString(position);
				JButton b = buttonGrid.get(posStr);
				
				if (b == arg0.getSource()) {
					
					for (Entity e : boardMap.get(posStr)) {
						charInfo.append(e.toStringLong() + System.lineSeparator()	);
						textPanel.revalidate();
					}
				}
			}
		}
	}
	
}
