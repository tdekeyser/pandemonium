package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import entities.Entity;
import framework.World;

public class WorldMaker implements ActionListener {
	
	JPanel boardPanel;
	
	JPanel infoPanel;
	JPanel textPanel = new JPanel(); // panel that comprises the character text of the infoPanel
	JPanel actionDisplay = new JPanel(); // panel that comprises the unliving action text of the infoPanel
	JTextArea display = new JTextArea(); // text within actionDisplay
	
	JLabel statusBar;
	JButton initializer;
	JButton runner;
	JButton getLog;
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
	int pastStates = 0;
	
	public WorldMaker(
			JPanel boardPanel,
			JPanel infoPanel,
			JLabel statusBar,
			JButton[] wButtons,
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
		this.initializer = wButtons[0];
		this.runner = wButtons[1];
		this.getLog = wButtons[2];
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
	
	public void paintGrid(String[][] boardMatrix, Map<String, List<Entity>> boardMap) {
		// display Grid on boardPanel
		
		boardPanel.removeAll(); // remove existing panel if they exist
		textPanel.removeAll();
		textPanel.revalidate();
		textPanel.repaint();
		
		JPanel grid = makeGrid(boardMatrix, boardMap); // create GridPanel instance
		boardPanel.add(grid);
		boardPanel.revalidate(); // repacks the Grid if it is initialised a second time
	}
	
	public JPanel makeGrid(String[][] boardMatrix, Map<String, List<Entity>> boardMap) {
		JPanel gridPanel = new GridPanel(textPanel, boardDimensions[0], boardDimensions[1], boardMatrix, boardMap);
		return gridPanel;
	}
	
	public void activateActionDisplays() {
		textPanel.setPreferredSize(new Dimension(230, 400));
		textPanel.setOpaque(true); // must be opaque to be able to change background of JLabel
		textPanel.setBackground(Color.WHITE);
		infoPanel.add(textPanel);
		
		display.setEditable(false);
		actionDisplay.setPreferredSize(new Dimension(230, 110));
		actionDisplay.setOpaque(true);
		actionDisplay.setBackground(Color.WHITE);
		actionDisplay.add(display);
		infoPanel.add(actionDisplay);
	}
	
	public void displayUnlivingActions() {
		display.setText("");
		List<String> unlivingActions = w.getUnlivingActions();
		if (!(unlivingActions.size() == 0)) {
			for (String action : unlivingActions) {
				display.append(action + System.lineSeparator());
			}	
			actionDisplay.revalidate();
		}
	}
	
	public World getWorldInstance() {
		return w;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		/*
		 * Processes user input, handles invalid input, and initialises the world simulator.
		 */
		
		if (arg0.getSource() == initializer) {
		
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
				initialiseWorld(); // create World instance (w)
				paintGrid(w.getBoardMatrix(), w.getBoardMap());
				activateActionDisplays();
				pastStates = 0;
				
				statusBar.setText("World initialised.");
				
			} catch (NumberFormatException ne) {
				statusBar.setText("Use numerical input only!");
			} catch (IOException io) {
				statusBar.setText(io.getMessage()); // handles IOExceptions thrown by World class
			}
			
		} else if (arg0.getSource() == runner) {
			
			new Thread(){
				// thread that repaints Grid one state at a time
				
				public void run() {
					
					if (amountOfStates<100) {
						for (int i=0; i<amountOfStates; i++) {
							w.runOnce();
							paintGrid(w.getBoardMatrix(), w.getBoardMap());
							displayUnlivingActions();							
							pastStates++;
							statusBar.setText("World simulated " + Integer.toString(pastStates) + " state(s).");
						}
					} else { // large amount of states; faster processing without creating Grid with every step
						
						try {
							w.setTimeBetweenStates(0);
						} catch (IOException io) {}
						
						statusBar.setText("Working...");
						initializer.setEnabled(false);
						runner.setEnabled(false);
						
						w.run();
						
						initializer.setEnabled(true);
						runner.setEnabled(true);
						paintGrid(w.getBoardMatrix(), w.getBoardMap());
						displayUnlivingActions();
						pastStates += amountOfStates;
						statusBar.setText("World simulated " + Integer.toString(pastStates) + " states.");
					}
				}
			}.start();
			
		} else if (arg0.getSource() == getLog) {
			
			try (BufferedWriter worldLog = Files.newBufferedWriter(Paths.get("Pandemonium_WorldLog.txt"), Charset.defaultCharset())) {
				worldLog.write(w.getWorldLog());
				statusBar.setText("World log exported.");
			} catch (IOException io) { io.printStackTrace(); }
			
		}
	
	}

}
