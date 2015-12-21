package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;

import entities.Entity;
import framework.World;

public class WorldListener implements ActionListener {
	
	JPanel boardPanel;
	JPanel infoPanel;
	JLabel statusBar;
	JTextField aoS; // amount of states
	JTextField tbs; // time between states
	JTextField aoE; // amount of initial entities
	JTextField eDA; // entity distribution (a)
	JTextField eDB; // entity distribution (b)
	JTextField eDC; // entity distribution (c)
	JTextField bDA; // board dimensions (a)
	JTextField bDB; // board dimensions (b)
	JSlider heatS; // heat
	JSlider chanceOnPlagueS; // chance on plague
	
	int amountOfStates;
	int timeBetweenStates;
	int amountOfEntities;
	int[] entityDistribution = new int[3];
	int[] boardDimensions = new int[2];
	int heat;
	int chanceOnPlague;
	
	World w; // World instance
	
	public WorldListener(
			JPanel boardPanel,
			JPanel infoPanel,
			JLabel statusBar,
			JTextField aoS,
			JTextField tbs,
			JTextField aoE,
			JTextField eDA,
			JTextField eDB,
			JTextField eDC,
			JTextField bDA,
			JTextField bDB,
			JSlider heatS,
			JSlider chanceOnPlagueS
			) {
		this.boardPanel = boardPanel;
		this.infoPanel = infoPanel;
		this.statusBar = statusBar;
		this.aoS = aoS;
		this.tbs = tbs;
		this.aoE = aoE;
		this.eDA = eDA;
		this.eDB = eDB;
		this.eDC = eDC;
		this.bDA = bDA;
		this.bDB = bDB;
		this.heatS = heatS;
		this.chanceOnPlagueS = chanceOnPlagueS;
	
	}
	
	public void initialiseWorld() throws IOException {
		// creates World instance 
		
		w = new World(amountOfStates, amountOfEntities, entityDistribution, boardDimensions, heat, chanceOnPlague);
		w.printOnScreen(); // uncomment to print Log in console
		w.setTimeBetweenStates(timeBetweenStates); // default 1000 ms
		w.initialise();
	}
	
	public JPanel makeGrid(String[][] boardMatrix, Map<String, List<Entity>> boardMap) {
		JPanel gridPanel = new GridPanel(infoPanel, boardDimensions[0], boardDimensions[1], boardMatrix, boardMap);
		return gridPanel;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		/*
		 * Processes user input, handles invalid input, and initialises the world simulator.
		 */
		
		try {
			// process input
			amountOfStates = Integer.parseInt(aoS.getText());
			timeBetweenStates = Integer.parseInt(tbs.getText());
			amountOfEntities = Integer.parseInt(aoE.getText());
			entityDistribution[0] = Integer.parseInt(eDA.getText());
			entityDistribution[1] = Integer.parseInt(eDB.getText());
			entityDistribution[2] = Integer.parseInt(eDC.getText());
			boardDimensions[0] = Integer.parseInt(bDA.getText());
			boardDimensions[1] = Integer.parseInt(bDB.getText());
			heat = (int) heatS.getValue();
			chanceOnPlague = (int) chanceOnPlagueS.getValue();
			
			// build World based on user input
			boardPanel.removeAll(); // remove existing panel if they exist
			infoPanel.removeAll();
			
			initialiseWorld(); // create World instance (w)
			JPanel grid = makeGrid(w.getBoardMatrix(), w.getBoardMap()); // create GridPanel instance
			boardPanel.add(grid);
			boardPanel.revalidate(); // repacks the Grid if it is initialised a second time
			
			statusBar.setText("World initialised.");
			
		} catch (NumberFormatException ne) {
			statusBar.setText("Use numerical input only!");
		} catch (IOException io) {
			statusBar.setText(io.getMessage()); // handles IOExceptions thrown by World class
		}
	
	}

}
