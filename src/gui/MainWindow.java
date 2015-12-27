package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class MainWindow extends JFrame {
	
	JPanel titlePanel = new JPanel();
	JPanel outerPanel = new JPanel();
	JLabel statusBar = new JLabel("** Pandemonium status **");
	JPanel inputPanel = new InputPanel();
	JPanel boardPanel = new JPanel();
	JPanel infoPanel = new JPanel();

	private final JButton initializer = new JButton("Init");
	private final JButton runner = new JButton("Run");
	private final JButton getLog = new JButton("Log");
	JButton[] wButtons = {initializer, runner, getLog};
	
	public MainWindow() {
		
		JComponent[] inputFields = ((InputPanel) inputPanel).getFields();
		WorldMaker worldMaker = new WorldMaker(
						boardPanel,
						infoPanel,
						statusBar,
						wButtons,
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
		initializer.addActionListener(worldMaker);
		runner.addActionListener(worldMaker);
		getLog.addActionListener(worldMaker);
		
		try {
			Image init = ImageIO.read(new File("src/pics/init.png"));
			Image run = ImageIO.read(new File("src/pics/run.png"));
			Image log = ImageIO.read(new File("src/pics/log.png"));
			initializer.setIcon(new ImageIcon(init));
			runner.setIcon(new ImageIcon(run));
			getLog.setIcon(new ImageIcon(log));
		} catch (IOException io) {}
		
		// boardPanel
		boardPanel.setPreferredSize(new Dimension(550, 550));
		boardPanel.setBorder(BorderFactory.createTitledBorder("World"));
		
		// infoPanel
		infoPanel.setPreferredSize(new Dimension(250, 500));
		infoPanel.setBorder(BorderFactory.createTitledBorder("Info"));
		
		// status bar
		statusBar.setOpaque(true); // must be opaque to be able to change background of JLabel
		statusBar.setBackground(Color.WHITE);
		statusBar.setHorizontalAlignment(SwingConstants.CENTER);
		statusBar.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		statusBar.setPreferredSize(new Dimension(100, 30));
		
		// titlePanel
		JLabel title;
		try {
			Image titleImage = ImageIO.read(new File("src/pics/torn_paper2.png"));
			title = new JLabel(new ImageIcon(titleImage));
		} catch (IOException io) {
			title = new JLabel("<html><font size='50' color='#c34528'>Pandemonium</font>");
		}
		titlePanel.add(title);
		titlePanel.add(Box.createRigidArea(new Dimension(200,150)));
		titlePanel.add(initializer);
		titlePanel.add(runner);
		titlePanel.add(getLog);
	
		this.setTitle("Pandemonium simulator v1.0");
		this.getContentPane().add(outerPanel);
		this.setPreferredSize(new Dimension(1200,810));
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
	
}
