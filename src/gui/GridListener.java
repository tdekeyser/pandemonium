package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import entities.Entity;

public class GridListener implements ActionListener {
	
	JPanel textPanel = new JPanel(); // panel that comprises the text of the infoPanel
	JTextPane charInfo = new JTextPane(); // actual content of the infoPanel
	JScrollPane scrollPane = new JScrollPane(charInfo);
	
	int rows; // of grid
	int columns; // of grid
	private Map<String, List<Entity>> boardMap; // boardMap from World; contains entity info per position
	private Map<JButton, String> buttonGrid; // buttonGrid from GridPanel; contains position per button

	public GridListener(JPanel textPanel, int rows, int columns) {
		this.textPanel = textPanel;
		this.rows = rows;
		this.columns = columns;
		
		charInfo.setEditable(false);
		scrollPane.setPreferredSize(new Dimension(220,390));
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		textPanel.add(scrollPane);
		
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
					
					switch (e.getType()) { // match gifs
					case "CradleOfFilth": charInfo.insertIcon(new ImageIcon("src/pics/cradle2.gif")); break;
					case "Imp": charInfo.insertIcon(new ImageIcon("src/pics/imp5.gif")); break;
					case "InfernalDemon": charInfo.insertIcon(new ImageIcon("src/pics/infernal2.gif")); break;
					case "DemonCommander": charInfo.insertIcon(new ImageIcon("src/pics/commander1.gif")); break;
					case "Sinner": charInfo.insertIcon(new ImageIcon("src/pics/sinner4.gif")); break;
					case "AngelOfDeath": charInfo.insertIcon(new ImageIcon("src/pics/angelofdeath1.gif")); break;
					case "unliving": charInfo.insertIcon(new ImageIcon("src/pics/hellfire.gif")); break;
					}
					
					StyledDocument doc = charInfo.getStyledDocument();
					StyleContext sc = StyleContext.getDefaultStyleContext(); 
					AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, Color.BLACK);
					try{
						doc.insertString(doc.getEndPosition().getOffset(), e.toStringLong()+System.lineSeparator(), aset);
					}catch(BadLocationException bl) {}				
					
					textPanel.revalidate();
					textPanel.repaint();
				}	
			}
		}
	}
	
}
