package gui;

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
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

import entities.Entity;

public class GridListener implements ActionListener {
	
	private JPanel textPanel = new JPanel(); // panel that comprises the text of the infoPanel
	private JTextPane charInfo = new JTextPane(); // actual content of the infoPanel
	private JScrollPane scrollPane = new JScrollPane(charInfo); // embed content in a scrollPanel
	
	private Map<String, List<Entity>> boardMap; // boardMap from World; contains entity info per position
	private Map<JButton, String> buttonGrid; // buttonGrid from GridPanel; contains position per button

	public GridListener(JPanel textPanel) {
		this.textPanel = textPanel;
		
		charInfo.setEditable(false);
		scrollPane.setPreferredSize(new Dimension(220,390));
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		textPanel.add(scrollPane);
	}
	
	public void setMaps(Map<JButton, String> buttonGrid, Map<String, List<Entity>> boardMap) {
		// update the Maps
		
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
					
					switch (e.getType()) { // insert images according to the entity types on board
					case "CradleOfFilth": charInfo.insertIcon(new ImageIcon("src/pics/cradle2.gif")); break;
					case "Imp": charInfo.insertIcon(new ImageIcon("src/pics/imp5.gif")); break;
					case "InfernalDemon": charInfo.insertIcon(new ImageIcon("src/pics/infernal2.gif")); break;
					case "DemonCommander": charInfo.insertIcon(new ImageIcon("src/pics/commander1.gif")); break;
					case "Sinner": charInfo.insertIcon(new ImageIcon("src/pics/sinner4.gif")); break;
					case "AngelOfDeath": charInfo.insertIcon(new ImageIcon("src/pics/angelofdeath1.gif")); break;
					case "unliving": charInfo.insertIcon(new ImageIcon("src/pics/hellfire.gif")); break;
					}
					
					StyledDocument doc = charInfo.getStyledDocument(); // add text to the JTextPane
					try{
						doc.insertString(doc.getEndPosition().getOffset(), e.toStringLong()+System.lineSeparator(), null); // insert normal string without attributeSet (null)
					} catch(BadLocationException bl) { // in case the cursor cannot be set on specific location
						bl.printStackTrace();
					}				
				}
					
				textPanel.revalidate(); // revalidate the panel after adding content
					
					
			}
		}
	}
	
}
