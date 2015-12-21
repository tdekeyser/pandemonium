package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;

import entities.Entity;

public class GridListener implements ActionListener {
	
	JPanel infoPanel; // the EAST panel of MainWindow
	JPanel textPanel = new JPanel(); // panel that comprises the text of the infoPanel
	JTextArea charInfo = new JTextArea(); // actual content of the infoPanel
	
	int rows; // of grid
	int columns; // of grid
	private Map<String, List<Entity>> boardMap; // boardMap from World; contains entity info per position
	private Map<JButton, String> buttonGrid; // buttonGrid from GridPanel; contains position per button

	public GridListener(JPanel infoPanel, int rows, int columns) {
		this.infoPanel = infoPanel;
		this.rows = rows;
		this.columns = columns;
		
		charInfo.setEditable(false);
		textPanel.add(charInfo);
		textPanel.setPreferredSize(new Dimension(200, 500));
		
		textPanel.setOpaque(true); // must be opaque to be able to change background of JLabel
		textPanel.setBackground(Color.WHITE);
		infoPanel.add(textPanel);
		
	}
	
	public void setMaps(Map<JButton, String> buttonGrid, Map<String, List<Entity>> boardMap) {
		this.buttonGrid = buttonGrid;
		this.boardMap = boardMap;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		/* When a gridbutton is clicked, it should show Entity info of all Entities on this position.
		 * When a button is clicked, the event identifies which button has been clicked in the grid,
		 * then gets its position within the grid from Map<>buttonGrid.
		 * The position is then a key in boardMap that gains access to every Entity on that specific position.
		 */
		
		charInfo.setText(""); // empty charInfo
		
		for (JButton b : buttonGrid.keySet()) {
			if (b == arg0.getSource()) {
				String position = buttonGrid.get(b);
				
				for (Entity e : boardMap.get(position)) {
					charInfo.append(e.toStringLong() + System.lineSeparator());
					textPanel.revalidate();
				}	
			}
		}
	}
	
}
