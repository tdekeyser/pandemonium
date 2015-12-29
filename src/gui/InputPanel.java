package gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;

public class InputPanel extends JPanel implements ActionListener {
	
	private JLabel aosL = new JLabel("Amount of states:");
	private JTextField aoS = new JTextField("10");
	
	private JLabel tbsL = new JLabel("Time between state (ms):");
	private JTextField tbs = new JTextField("1000");
	
	private JLabel aoeL = new JLabel("Amount of initial entities:");
	private JTextField aoE = new JTextField("10");
	
	private JPanel entityDistrPanel = new JPanel();
	private JLabel edaL = new JLabel("Entity distribution:");
	private JTextField eDA = new JTextField("33");
	private JTextField eDB = new JTextField("33");
	private JTextField eDC = new JTextField("34");
	
	private JPanel boardDimPanel = new JPanel();
	private JLabel bdaL = new JLabel("Board dimensions:");
	private JTextField bDA = new JTextField("6");
	private JTextField bDB = new JTextField("6");
	
	private JLabel heatL = new JLabel("Heat:");
	private JSlider heatS = new JSlider(0, 100, 0);
	private JLabel copL = new JLabel("Chance on plague:");
	private JSlider chanceOnPlagueS = new JSlider(0, 100, 0);
	
	private final JButton reset = new JButton("Reset");
	
	public InputPanel() {
		
		reset.addActionListener(this);
		
		paintSliderLabels(heatS, 20, 5);
		paintSliderLabels(chanceOnPlagueS, 20, 5);
		
		entityDistrPanel.setLayout(new GridLayout(2,3));
		entityDistrPanel.add(new JLabel("<html><font color='gray'>Sinner</font>"));
		entityDistrPanel.add(new JLabel("<html><font color='gray'>Cradle</font>"));
		entityDistrPanel.add(new JLabel("<html><font color='gray'>Imp</font>"));
		entityDistrPanel.add(eDA);
		entityDistrPanel.add(eDB);
		entityDistrPanel.add(eDC);
		
		boardDimPanel.setLayout(new GridLayout(2,2));
		boardDimPanel.add(new JLabel("<html><font color='gray'>x</font>"));
		boardDimPanel.add(new JLabel("<html><font color='gray'>y</font>"));
		boardDimPanel.add(bDA);
		boardDimPanel.add(bDB);
		
		this.setLayout(new GridLayout(0,1));
		this.setPreferredSize(new Dimension(200,500));
		this.setBorder(BorderFactory.createTitledBorder("Input"));
		this.add(aosL);
		this.add(aoS);
		this.add(tbsL);
		this.add(tbs);
		this.add(aoeL);
		this.add(aoE);
		this.add(edaL);
		this.add(entityDistrPanel);
		this.add(bdaL);
		this.add(boardDimPanel);
		this.add(heatL);
		this.add(heatS);
		this.add(copL);
		this.add(chanceOnPlagueS);
		this.add(reset);
		
	}
	
	private void paintSliderLabels(JSlider s, int majorTickSpacing, int minorTickSpacing) {
		s.setMajorTickSpacing(majorTickSpacing);
		s.setMinorTickSpacing(minorTickSpacing);
		s.setPaintTicks(true);
		s.setPaintLabels(true);
	}
	
	public JComponent[] getFields() {
		JComponent[] fields = new JComponent[10];
		fields[0] = aoS;
		fields[1] = tbs;
		fields[2] = aoE;
		fields[3] = eDA;
		fields[4] = eDB;
		fields[5] = eDC;
		fields[6] = bDA;
		fields[7] = bDB;
		fields[8] = heatS;
		fields[9] = chanceOnPlagueS;
		return fields;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// reset input fields
		aoS.setText("");
		aoE.setText("");
		eDA.setText("");
		eDB.setText("");
		eDC.setText("");
		bDA.setText("");
		bDB.setText("");
	}

}
