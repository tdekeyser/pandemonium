package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

public class CoverWindow extends JFrame implements ActionListener {
	/*
	 * CoverWindow creates an initial screen; it gives the possibility to get some information about the proceedings of the application before starting.
	 */
	
	private JButton startB = new JButton("Start"); // start and info buttons
	private JButton infoB = new JButton("Info");
	
	private JPanel outer = new JPanel(); // panels for the frame
	private JPanel header = new JPanel();
	private JPanel body = new JPanel();
	private JPanel buttonPanel = new JPanel();
	
	private String root = "src/gui/pics/"; // locations of entity images
	private String cradleLoc = root + "cradle2.gif";
	private String impLoc = root + "imp5.gif";
	private String infLoc = root + "infernal2.gif";
	private String dcLoc = root + "commander1.gif";
	private String sLoc = root + "sinner4.gif";
	private String aodLoc = root + "angelofdeath1.gif";
	private String hLoc = root + "hellfire.gif";
	
	// text for info panel
	private String infoText = "<font size='4'><p>This application simulates <i><b>Pandemonium</b></i>, the Capital of Satan and his Peers.</p>"
			+ "<p>It is a world dominated by a hierarchy of demons and corpses, a hive of pain and suffering, and sieged by demonic divinities. The world depends on its temperature (<b>Heat</b>) and the possibility of spawned creatures (<b>Plague</b>). These parameters change the way the world develops.</p><br/></font>";
	private String cradleText = "<b>Cradle of Filth</b><p>Demons are born as a Cradle of Filth, a passive demon that cannot but wait to move up in the demon hierarchy.</p><br/>";
	private String impText = "<b>Imp</b><p>Imps are aggressive creatures that have either evolved from their cradles, spawned from the ground, or answered the call of their Commander. They are the lowest rank of demons in <i>Pandemonium</i>. This demon gets rid of the dead but also cannibalizes its kin when it is able to.</p><br/>";
	private String infernalText = "<b>Infernal Demon</b><p>Next in the hierarchy is the Infernal Demon. Evolved from Imps, these are cruel demons that torture the unfortunate or kill them.</p><br/>";
	private String commanderText = "<b>Demon Commander</b><p>A leader among demons and the greatest in rank, the Demon Commander can summon a demon army anew. It singlehandedly withstands and brings down fallen angels.</p><br/>";
	private String sinnerText = "<b>Sinner</b><p>The unfortunate among us eventually arrive at <i>Pandemonium</i>. These sinners cannot but pray and wander, waiting to be killed by a demon or saved by angels.</p><br/>";
	private String angelText = "<b>Angel of Death</b><p>Sinners that have prayed are able to unite in a final call for above. Unfortunately, they are heard by the Angel of Death, a fallen angel that brings even more chaos to <i>Pandemonium</i>. In its frenzy, it is able kill greater demons and randomly drops devastating Hellfire bombs.</p><br/>";
	private String hellfireText = "<b>Hellfire</b><p>Hellfire kills any approaching creature. It is dropped by an Angel of Death and lingers in place.</p><br/>";
	
	public CoverWindow() {
		
		makeButton(startB);
		makeButton(infoB);
		startB.addActionListener(this); // button action listeners are defined below
		infoB.addActionListener(this);
		
		JLabel title; // title of the application
		try {
			Image titleImage = ImageIO.read(new File("src/gui/pics/title3.png"));
			title = new JLabel(new ImageIcon(titleImage));
		} catch (IOException io) {
			title = new JLabel("<html><font size='50' color='#c34528'>Pandemonium</font>");
		}
		
		header.setLayout(new BorderLayout());
		header.setBackground(new Color(27,27,27));
		header.add(title, BorderLayout.CENTER);
		
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));
		buttonPanel.setBackground(new Color(27,27,27));
		buttonPanel.add(startB);
		buttonPanel.add(infoB);
		
		body.setLayout(new BorderLayout());
		body.setBorder(BorderFactory.createEmptyBorder(20,20,0,0));
		body.setBackground(new Color(27,27,27));
		body.add(buttonPanel, BorderLayout.WEST);
		
		outer.setLayout(new BoxLayout(outer, BoxLayout.PAGE_AXIS));
		outer.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		outer.setBackground(new Color(27,27,27));
		outer.add(header);
		outer.add(body);
		
		this.setTitle("Pandemonium simulator v1.0");
		this.setPreferredSize(new Dimension(800,700));
		this.getContentPane().add(outer);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
		
	}
	
	private void makeButton(JButton button) {
		// creates fancy black background buttons
		
		button.setBackground(new Color(27,27,27));
		button.setFocusable(false);
		button.setForeground(Color.LIGHT_GRAY);
		button.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}
	
	private void insertInfo() {
		/*
		 * InsertInfo creates a JTextPane with additional information about the world and its entities.
		 */
		
		JTextPane info = new JTextPane();
		JScrollPane scroll = new JScrollPane(info); // put the textpane in a scrollable window
		info.setEditable(false); // the pane should not be editable
		info.setPreferredSize(new Dimension(320, 50));
		info.setContentType("text/html"); // set type to HTML to easily add some basic style
		
		HTMLEditorKit kit = new HTMLEditorKit(); // create HTMLEditorKit and HTMLDocument to embed images in the HTML, and link the JTextPane to the HTMLEditor.
	    HTMLDocument doc = new HTMLDocument();
	    info.setEditorKit(kit);
	    info.setDocument(doc); // consider the JTextPane as an HTMLDocument
	    
	    try {
		    kit.insertHTML(doc, doc.getLength(), infoText, 0, 0, null); // insert HTML via the editorKit at current endposition (doc.getLength()), without extra tags (null)
		    
			info.insertIcon(new ImageIcon(cradleLoc));
			kit.insertHTML(doc, doc.getLength(), cradleText, 0, 0, null);
			
			info.insertIcon(new ImageIcon(impLoc));
			kit.insertHTML(doc, doc.getLength(), impText, 0, 0, null);
			
			info.insertIcon(new ImageIcon(infLoc));
			kit.insertHTML(doc, doc.getLength(), infernalText, 0, 0, null);
			
			info.insertIcon(new ImageIcon(dcLoc));
			kit.insertHTML(doc, doc.getLength(), commanderText, 0, 0, null);
			
			info.insertIcon(new ImageIcon(sLoc));
			kit.insertHTML(doc, doc.getLength(), sinnerText, 0, 0, null);
			
			info.insertIcon(new ImageIcon(aodLoc));
			kit.insertHTML(doc, doc.getLength(), angelText, 0, 0, null);
			
			info.insertIcon(new ImageIcon(hLoc));
			kit.insertHTML(doc, doc.getLength(), hellfireText, 0, 0, null);
		} catch (BadLocationException | IOException b) {}
		
		body.add(scroll, BorderLayout.CENTER);
		body.revalidate();
	}
	
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == startB) {
			// start simulator in MainWindow
		    new MainWindow();
			
			this.setVisible(false); // get rid of this frame
			this.dispose();
			
		} else if (arg0.getSource() == infoB) {
			// show info panel
			insertInfo();
			
		}
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			 public void run() {
			    new CoverWindow();
			   }
		});
	}
	
}
