package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import framework.World;

public class WorldMaker implements ActionListener {
	
	private JPanel boardPanel; // contains Grid
	
	private JPanel infoPanel; // contains information about Grid
	private JPanel textPanel = new JPanel(); // panel that comprises the character text of the infoPanel
	private JPanel actionDisplay = new JPanel(); // panel that comprises the unliving action text of the infoPanel
	private JTextArea display = new JTextArea(); // text within actionDisplay
	
	private JLabel statusBar;
	private JButton initializer;
	private JButton runner;
	private JButton getLog;
	private JTextField aoS; // amount of states
	private JTextField tbs; // time between states
	private JTextField aoE; // amount of initial entities
	private JTextField eDA; // entity distribution (a)
	private JTextField eDB; // entity distribution (b)
	private JTextField eDC; // entity distribution (c)
	private JTextField bDA; // board dimensions (a)
	private JTextField bDB; // board dimensions (b)
	private JSlider heatS; // heat
	private JSlider chanceOnPlagueS; // chance on plague
	
	private int amountOfStates;
	private int timeBetweenStates;
	private int amountOfEntities;
	private int[] entityDistribution = new int[3];
	private int[] boardDimensions = new int[2];
	private int heat;
	private int chanceOnPlague;
	
	private World w; // World instance
	private int pastStates = 0; // important to keep how many states have passed (for status bar)
	
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
		
		runner.setEnabled(false);
		getLog.setEnabled(false);
			
	}
	
	private void initialiseWorld() throws IOException {
		// creates World instance 
		
		w = new World(amountOfStates, amountOfEntities, entityDistribution, boardDimensions, heat, chanceOnPlague);
		w.printOnScreen(); // uncomment to print Log in console
		w.setTimeBetweenStates(timeBetweenStates); // default 1000 ms
		w.initialise();
	}
	
	private void paintGrid() {
		// display Grid on boardPanel
		
		boardPanel.removeAll(); // remove existing grid
		textPanel.removeAll(); // remove existing info
		
		JPanel grid = new GridPanel(textPanel, boardDimensions[0], boardDimensions[1], w.getBoardMatrix(), w.getBoardMap()); // create GridPanel object
		boardPanel.add(grid);
		boardPanel.revalidate(); // repacks the Grid if it is initialised a second time
	}
	
	private void activateActionDisplays() {
		// initialise child panels of infoPanel
		
		textPanel.setPreferredSize(new Dimension(230, 400));
		textPanel.setBackground(Color.WHITE);
		infoPanel.add(textPanel); // will contain Character info
		
		display.setEditable(false);
		display.setPreferredSize(new Dimension(220, 100));
		actionDisplay.setBackground(Color.WHITE);
		actionDisplay.add(display); // will contain unliving action info
		infoPanel.add(actionDisplay);
	}
	
	private void displayUnlivingActions() {
		// If last state had effected unliving actions (plague, demonic fury, divine intervention), put them in display (in infoPanel)

		display.setText("");
		List<String> unlivingActions = w.getUnlivingActions();
		if (!(unlivingActions.size() == 0)) {
			for (String action : unlivingActions) {
				display.append(action + System.lineSeparator());
			}	
			actionDisplay.revalidate(); // revalidate panel after adding content
		}
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
				paintGrid();
				activateActionDisplays();
				pastStates = 0;
				
				statusBar.setText("World initialised.");
				runner.setEnabled(true); // enable the next buttons
				getLog.setEnabled(true);
				
			} catch (NumberFormatException ne) {
				statusBar.setText("Use numerical input only!");
			} catch (IOException io) {
				statusBar.setText(io.getMessage()); // handles IOExceptions thrown by World class
			}
			
		} else if (arg0.getSource() == runner) {
			
			new Thread(){ // thread that allows to dynamically repaint Grid without freezing the GUI
				
				public void run() {
					
					if (amountOfStates<100) {
						for (int i=0; i<amountOfStates; i++) {
							
							w.runOnce(); // run world once
							
							paintGrid(); // then paint the grid
							displayUnlivingActions();	
							
							pastStates++;
							statusBar.setText("World simulated " + Integer.toString(pastStates) + " state(s)."); // update the status bar
						}
					} else { // large amount of states; faster processing without creating Grid with every step
						
						try {
							w.setTimeBetweenStates(0);
						} catch (IOException io) { /* impossible exception; leaving it empty is OK */ }
						
						statusBar.setText("Working..."); // set working text in status bar
						initializer.setEnabled(false); // while working, you cannot click on buttons
						runner.setEnabled(false);
						getLog.setEnabled(false);
						
						w.run(); // run all states
									
						paintGrid();
						displayUnlivingActions();
						
						initializer.setEnabled(true); // re-enable buttons
						runner.setEnabled(true);
						getLog.setEnabled(true);
						
						pastStates += amountOfStates;
						statusBar.setText("World simulated " + Integer.toString(pastStates) + " states."); // update status bar
					}
				}
			}.start();
			
		} else if (arg0.getSource() == getLog) {
			// Write WorldLog into a .txt file using NIO classes (Files and Paths), appears in pandemonium/ folder.
			
			try (BufferedWriter worldLog = Files.newBufferedWriter(Paths.get("Pandemonium_WorldLog.txt"), Charset.defaultCharset())) {
				worldLog.write(w.getWorldLog());
				statusBar.setText("World log exported.");
			} catch (IOException io) { io.printStackTrace(); }
			
		}
	
	}

}
