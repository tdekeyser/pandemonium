package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class MainWindow extends JFrame {
	
	JPanel titlePanel = new JPanel();
	JPanel outerPanel = new JPanel();
	JLabel statusBar = new JLabel("** Pandemonium status **");
	JPanel inputPanel;
	JPanel boardPanel = new JPanel();
	JPanel infoPanel = new JPanel();

	private final JButton initializer = new JButton("Initialise World");
	
	public MainWindow() {
		
		// inputPanel
		inputPanel = new InputPanel();
		JComponent[] inputFields = ((InputPanel) inputPanel).getFields();
		WorldListener worldListener = new WorldListener(
						boardPanel,
						infoPanel,
						statusBar,
						(JTextField) inputFields[0], // aoS
						(JTextField) inputFields[1], // tbs
						(JTextField) inputFields[2], // aoE
						(JTextField)inputFields[3], // eDA
						(JTextField) inputFields[4], // eDB
						(JTextField) inputFields[5], // eDC
						(JTextField) inputFields[6], // bDA
						(JTextField) inputFields[7], // bDB
						(JSlider) inputFields[8], // heatS
						(JSlider) inputFields[9] // chanceOnPlagueS
							);
		initializer.addActionListener(worldListener);
		
		// boardPanel
		boardPanel.setPreferredSize(new Dimension(550, 550));
		boardPanel.setBorder(BorderFactory.createTitledBorder("World"));
		
		// infoPanel
		infoPanel.setPreferredSize(new Dimension(250, 500));
		infoPanel.setBorder(BorderFactory.createTitledBorder("Info"));
		
		// status bar
		statusBar.setOpaque(true); // must be opaque to be able to change background of JLabel
		statusBar.setBackground(Color.WHITE);
		statusBar.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		
		// titlePanel
		JLabel title = new JLabel("<html><font size='50' color='#c34528'>Pandemonium</font>");
		titlePanel.add(title);
		titlePanel.add(initializer);
		
		this.getContentPane().add(outerPanel);
		outerPanel.setLayout(new BorderLayout(10, 10)); // BorderLayout(int horizontalGap, int verticalGap)
		outerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		outerPanel.add(titlePanel, BorderLayout.NORTH);
		outerPanel.add(inputPanel, BorderLayout.WEST);
		outerPanel.add(boardPanel, BorderLayout.CENTER);
		outerPanel.add(infoPanel, BorderLayout.EAST);
		outerPanel.add(statusBar, BorderLayout.SOUTH);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			 public void run() {
			    new MainWindow();
			   }
		});
	}
}
