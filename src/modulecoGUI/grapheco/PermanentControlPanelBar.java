/**
 * Title : PermanentControlPanelBar.java 
 * Permanent Control of Contol Panel Bar 
 * Copyright:    Copyright (c)enst-bretagne
 * @author denis.phan@enst-bretagne.fr
 * 
 *  
 *(first version 1.0 of september 19, 2000
 * @version 1.2  august,5, 2002
 */
package modulecoGUI.grapheco;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
/**
 * Manage the four buttons "Create, Start, Stop and MStep", plus the World Size.
 */
public class PermanentControlPanelBar extends CPanel implements ActionListener {
	protected CentralControl centralControl;
	protected ControlPanel controlPanel;
	public JButton buttonCreate, buttonStart, buttonStop, buttonMStep;
	protected ModulecoTextField textFieldSize;
	protected JTextField textFieldStep;
	public static int maxSizeWorld = 200;
	/**
	 * Constructor.
	 * 
	 * @param centralControl
	 * @param controlPanel
	 */
	public PermanentControlPanelBar(CentralControl centralControl,
			ControlPanel controlPanel) {
		this.centralControl = centralControl;
		this.controlPanel = controlPanel;
		constraints.weightx = 1.0;
		constraints.weighty = 1.0;
		constraints.anchor = GridBagConstraints.WEST;
		BuildOptionBar();
	}
	/**
	 * Build the permanent command: the 4 buttons the world size
	 */
	protected void BuildOptionBar() {
		/**
		 * Set the constraints
		 */
		constraints.anchor = GridBagConstraints.SOUTH;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.insets = new java.awt.Insets(2, 2, 2, 2);
		/**
		 * Add the buttons
		 */
		buttonCreate = new JButton("Create");
		add(buttonCreate, 0, 0, 1, 1);
		buttonCreate.addActionListener(this);
		buttonStart = new JButton("Start");
		buttonStart.setEnabled(false);
		add(buttonStart, 0, 1, 1, 1);
		buttonStart.addActionListener(this);
		buttonMStep = new JButton("MStep");
		buttonMStep.setEnabled(false);
		add(buttonMStep, 1, 0, 1, 1);
		buttonMStep.addActionListener(this);
		buttonStop = new JButton("Stop");
		buttonStop.setEnabled(false);
		add(buttonStop, 1, 1, 1, 1);
		buttonStop.addActionListener(this);
		/**
		 * Label "World size"
		 */
		constraints.insets = new java.awt.Insets(2, 2, 0, 2);
		JLabel labelSize = new JLabel("World Size", JLabel.CENTER);
		add(labelSize, 2, 0, 1, 1);
		/**
		 * Text field representing the world size
		 */
		constraints.insets = new java.awt.Insets(0, 2, 2, 2);
		textFieldSize = new ModulecoTextField("49", 0, 2, maxSizeWorld);
		textFieldSize.setFont(smallfont);
		add(textFieldSize, 2, 1, 1, 1);
		textFieldSize.setEnabled(false);
		textFieldSize.addActionListener(this);
		/**
		 * Label "Step"
		 */
		constraints.insets = new java.awt.Insets(2, 2, 0, 2);
		JLabel labelStep = new JLabel("Step", JLabel.CENTER);
		add(labelStep, 3, 0, 1, 1);
		/**
		 * Text field representing the iteration step
		 */
		constraints.insets = new java.awt.Insets(0, 2, 2, 2);
		textFieldStep = new JTextField("0", 5);
		textFieldStep.setFont(smallfont);
		textFieldStep.setEditable(false);
		add(textFieldStep, 3, 1, 1, 1);
		textFieldStep.addActionListener(this);
	}
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == buttonCreate) {
			controlPanel.CreateWorld();
			buttonCreate.setEnabled(true);
			buttonStop.setEnabled(false);
			buttonStart.setEnabled(true);
			buttonMStep.setEnabled(true);
		}
		if (e.getSource() == buttonStop) {
			centralControl.simulationStop();
			buttonCreate.setEnabled(true);
			buttonStop.setEnabled(false);
			buttonStart.setEnabled(true);
			buttonMStep.setEnabled(true);
		}
		if (e.getSource() == buttonStart) {
			centralControl.simulationStart();
			buttonCreate.setEnabled(false);
			buttonStop.setEnabled(true);
			buttonStart.setEnabled(false);
			buttonMStep.setEnabled(false);
		}
		if (e.getSource() == buttonMStep) {
			centralControl.simulationMstep();
			buttonCreate.setEnabled(true);
			buttonStop.setEnabled(false);
			buttonStart.setEnabled(true);
			buttonMStep.setEnabled(true);
		}
		if (e.getSource() == textFieldSize) {
			/**
			 * Create a new world with the new world size
			 */
			controlPanel.CreateWorld();
		}
	}
	public void paint(java.awt.Graphics g) {
		super.paint(g);
		g.setColor(java.awt.Color.lightGray);
	}
}