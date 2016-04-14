/*
 * AgentParameter.java
 *
 * Created on 27 avril 2002, 17:40
 */

package modulecoGUI.Traces.grapheco;

/**
 *
 * @author  infolab
 * @version 
 */
import javax.swing.JPanel;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.border.TitledBorder;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import modulecoFramework.modeleco.EWorld;
import java.util.ArrayList;
import java.util.Iterator;

public class AgentParameter implements ItemListener {
	/** Creates new AgentParameter */
	protected JPanel agentParameterPanel;
	protected JRadioButton allAgents, selectAgents;
	protected JComboBox selectedAgents;
	protected EWorld eWorld;
	protected AgentFrame agentFrame;
	// Static Access to static Field
	static int ieSelected = ItemEvent.SELECTED;

	public AgentParameter(EWorld eWorld) {
		this.eWorld = eWorld;
		initComponents();
	}

	public void initComponents() {
		agentParameterPanel = new JPanel();
		agentParameterPanel.setLayout(null);
		agentParameterPanel.setBorder(new TitledBorder("Agents"));
		allAgents = new JRadioButton("All");
		allAgents.addItemListener(this);
		selectAgents = new JRadioButton("Select");
		selectAgents.addItemListener(this);
		selectedAgents = new JComboBox();
		selectedAgents.addItemListener(this);
		ButtonGroup groupe = new ButtonGroup();
		groupe.add(allAgents);
		groupe.add(selectAgents);
		allAgents.setBounds(10, 20, 50, 25);
		selectAgents.setBounds(10, 50, 70, 25);
		selectedAgents.setBounds(80, 50, 100, 25);
		agentParameterPanel.add(allAgents);
		agentParameterPanel.add(selectAgents);
		agentParameterPanel.add(selectedAgents);
		refresh(eWorld);
	}
	public void refresh(EWorld eWorld) {
		this.eWorld = eWorld;
		if ((this.agentFrame != null) && (this.agentFrame.isShowing())) {
			selectedAgents.removeAllItems();
			this.agentFrame.refresh(this.eWorld, this);
		} else
			allAgents.setSelected(true);
	}

	public JPanel getPanel() {
		return agentParameterPanel;
	}

	public void setAgentState() {
		selectedAgents.removeAllItems();
	}

	public void setAgents(ArrayList agents) {
		selectedAgents.removeAllItems();
		if (agents.size() > 0) {
			for (Iterator i = agents.iterator(); i.hasNext();)
				selectedAgents.addItem(i.next());
		} else
			allAgents.setSelected(true);
	}

	public ArrayList getSelectedAgents() {
		ArrayList agents = new ArrayList();
		for (int i = 0; i < selectedAgents.getModel().getSize(); i++) {
			selectedAgents.setSelectedIndex(i);
			agents.add(selectedAgents.getSelectedItem());
		}
		return agents;
	}

	public void getAgents() {
		agentFrame = new AgentFrame(eWorld, this);
	}

	public void itemStateChanged(ItemEvent itemEvent)
	 {
		if (itemEvent.getSource().equals(allAgents)) {
			if (itemEvent.getStateChange() == ieSelected) {
				setAgentState();
				return;
			}
		}

		if (itemEvent.getSource().equals(selectAgents)) {
			if (itemEvent.getStateChange() == ieSelected) {
				getAgents();
				return;
			}
		}
	}

	public boolean agentsValidate() {
		if ((selectAgents.isSelected())
			&& (selectedAgents.getModel().getSize() == 0))
			return false;
		return true;
	}
	public boolean getAllAgent() {
		return allAgents.isSelected();
	}
}
