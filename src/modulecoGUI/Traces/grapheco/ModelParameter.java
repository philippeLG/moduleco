/*
 * ModelParameter.java
 * 
 * Created on 22 avril 2002, 10:30
 */
package modulecoGUI.Traces.grapheco;
/**
 * @author infolab
 */
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import modulecoGUI.grapheco.ControlPanel;
/**
 * In the data recording window, handle the display of the simulation
 * parameters.
 * 
 * @author Gilles Daniel (gilles@cs.man.ac.uk)
 * @version 1.0, 05-May-2004
 */
public class ModelParameter {
	/**
	 * Creates new ModelParameter.
	 */
	protected JTextField modelName, neighbourName, schedulerType, zoneName,
			worldSize;
	protected JRadioButton extraAgentCommit;
	protected JPanel modelPanel;
	protected ControlPanel controlPanel;
	public ModelParameter(ControlPanel controlPanel) {
		this.controlPanel = controlPanel;
		initComponents();
	}
	/**
	 * Initialise the different components.
	 *  
	 */
	public void initComponents() {
		modelPanel = new JPanel();
		modelPanel.setLayout(null);
		modelPanel.setBorder(new TitledBorder(null, "Model parameters"));
		JLabel lbname = new JLabel("Model name");
		JLabel lbneig = new JLabel("Neighbour name");
		JLabel lbsch = new JLabel("Scheduler type");
		JLabel lbsize = new JLabel("World size");
		JLabel lbzone = new JLabel("Zone name");
		modelName = new JTextField(controlPanel.getWorldSelected());
		neighbourName = new JTextField(controlPanel.getNeighbourSelected());
		schedulerType = new JTextField(controlPanel.getSchedulerType());
		zoneName = new JTextField(controlPanel.getZoneSelected());
		worldSize = new JTextField(controlPanel.getWorldSize());
		extraAgentCommit = new JRadioButton("Extra agent commit first",
				controlPanel.getExtraAgentCommit());
		modelName.setEditable(false);
		worldSize.setEditable(false);
		neighbourName.setEditable(false);
		zoneName.setEditable(false);
		schedulerType.setEditable(false);
		extraAgentCommit.setEnabled(false);
		lbname.setBounds(20, 30, 120, 17);
		lbneig.setBounds(20, 70, 120, 17);
		lbsch.setBounds(20, 110, 120, 17);
		lbsize.setBounds(350, 30, 120, 17);
		lbzone.setBounds(350, 70, 120, 17);
		extraAgentCommit.setBounds(350, 110, 169, 25);
		modelName.setBounds(150, 30, 170, 21);
		neighbourName.setBounds(150, 70, 170, 21);
		schedulerType.setBounds(150, 110, 170, 21);
		worldSize.setBounds(450, 30, 170, 21);
		zoneName.setBounds(450, 70, 170, 21);
		;
		modelPanel.add(lbname);
		modelPanel.add(lbneig);
		modelPanel.add(lbsch);
		modelPanel.add(lbsize);
		modelPanel.add(lbzone);
		modelPanel.add(extraAgentCommit);
		modelPanel.add(modelName);
		modelPanel.add(neighbourName);
		modelPanel.add(schedulerType);
		modelPanel.add(worldSize);
		modelPanel.add(zoneName);
	}
	public void refresh(ControlPanel controlPanel) {
		modelName.setText(controlPanel.getWorldSelected());
		neighbourName.setText(controlPanel.getNeighbourSelected());
		schedulerType.setText(controlPanel.getSchedulerType());
		zoneName.setText(controlPanel.getZoneSelected());
		worldSize.setText(controlPanel.getWorldSize());
		extraAgentCommit.setEnabled(controlPanel.getExtraAgentCommit());
	}
	public JPanel getPanel() {
		return modelPanel;
	}
	public String getModelName() {
		return modelName.getText();
	}
	public String getNeighbourName() {
		return neighbourName.getText();
	}
	public String getZoneName() {
		return zoneName.getText();
	}
	public String getSchedulerType() {
		return schedulerType.getText();
	}
	public int getWorldSize() {
		return Integer.parseInt(worldSize.getText());
	}
	public boolean getExtraAgentCommit() {
		return extraAgentCommit.isSelected();
	}
}