package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;

public class InputPanel extends JPanel implements ActionListener {
	
	JLabel aosL = new JLabel("Amount of states:");
	JTextField aoS = new JTextField("10");
	
	JLabel tbsL = new JLabel("Time between states (ms):");
	JTextField tbs = new JTextField("1000");
	
	JLabel aoeL = new JLabel("Amount of initial entities:");
	JTextField aoE = new JTextField("5");
	
	JLabel edaL = new JLabel("Entity distribution: (a)");
	JTextField eDA = new JTextField("33");
	JLabel edbL = new JLabel("Entity distribution: (b)");
	JTextField eDB = new JTextField("33");
	JLabel edcL = new JLabel("Entity distribution: (c)");
	JTextField eDC = new JTextField("34");
	
	JLabel bdaL = new JLabel("Board dimensions: (a)");
	JTextField bDA = new JTextField("4");
	JLabel bdbL = new JLabel("Board dimensions: (b)");
	JTextField bDB = new JTextField("4");
	
	JLabel heatL = new JLabel("Heat:");
	JSlider heatS = new JSlider(0, 100, 0);
	JLabel copL = new JLabel("Chance on plague:");
	JSlider chanceOnPlagueS = new JSlider(0, 100, 0);
	
	private final JButton reset = new JButton("Reset");
	
	public InputPanel() {
		
		reset.addActionListener(this);
		
		paintSliderLabels(heatS, 20, 5);
		paintSliderLabels(chanceOnPlagueS, 20, 5);
		
		this.setBorder(BorderFactory.createTitledBorder("Simulator input"));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(aosL);
		this.add(aoS);
		this.add(tbsL);
		this.add(tbs);
		this.add(aoeL);
		this.add(aoE);
		this.add(edaL);
		this.add(eDA);
		this.add(edbL);
		this.add(eDB);
		this.add(edcL);
		this.add(eDC);
		this.add(bdaL);
		this.add(bDA);
		this.add(bdbL);
		this.add(bDB);
		this.add(heatL);
		this.add(heatS);
		this.add(copL);
		this.add(chanceOnPlagueS);
		this.add(reset);
		
	}
	
	public void paintSliderLabels(JSlider s, int majorTickSpacing, int minorTickSpacing) {
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
