/*
 * DescriptorParameter.java
 * 
 * Created on 22 avril 2002, 14:50
 */
package modulecoGUI.Traces.grapheco;
/**
 * @author infolab
 */
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Vector;
import java.util.Iterator;
import modulecoFramework.modeleco.EWorld;
import modulecoFramework.modeleco.CAgent;
import modulecoGUI.grapheco.descriptor.DataDescriptor;
/**
 * In the data recording window, handle the variables to record.
 * <p>
 * a --> Agents <br>
 * ea --> Extra-Agents <br>
 * wc --> World Constants <br>
 * wnc --> World Non-Constants (i.e. variables) <br>
 * etc. <br>
 * 
 * @author Gilles Daniel (gilles@cs.man.ac.uk)
 * @version 1.0, 05-May-2004
 */
public class DescriptorParameter implements ActionListener {
	protected JTabbedPane descriptors;
	protected JButton acAdd, acAddAll, acRemove, acRemoveAll, ancAdd,
			ancAddAll, ancRemove, ancRemoveAll;
	protected JButton eacAdd, eacAddAll, eacRemove, eacRemoveAll, eancAdd,
			eancAddAll, eancRemove, eancRemoveAll;
	protected JButton wcAdd, wcAddAll, wcRemove, wcRemoveAll, wncAdd,
			wncAddAll, wncRemove, wncRemoveAll;
	protected JList agentVars, agentCVars, agentNCVars;
	protected JList eagentVars, eagentCVars, eagentNCVars;
	protected JList worldVars, worldCVars, worldNCVars;
	protected Vector agentDescriptors, extraAgentDescriptors, worldDescriptors;
	protected JPanel agentPanel, extraAgentPanel, worldPanel, descriptorPanel;
	protected int tabIndice;
	protected StepParameter stepParameter;
	protected EWorld eWorld;
	/**
	 * Create new DescriptorParameter.
	 * 
	 * @param eWorld
	 */
	public DescriptorParameter(EWorld eWorld) {
		this.eWorld = eWorld;
		tabIndice = 0;
		agentDescriptors = new Vector();
		extraAgentDescriptors = new Vector();
		worldDescriptors = new Vector();
		initComponents();
		/**
		 * Uncomment the following to set all the variables non-constants
		 */
		variableAllSelected(agentVars, agentNCVars);
		//variableAllSelected(eagentVars, eagentNCVars);
		variableAllSelected(worldVars, worldNCVars);
	}
	/**
	 * Initialise the different components.
	 *  
	 */
	public void initComponents() {
		descriptorPanel = new JPanel();
		descriptorPanel.setLayout(null);
		descriptorPanel.setBorder(new TitledBorder(null, "Select variables"));
		descriptors = new JTabbedPane();
		descriptorPanel.add(descriptors);
		descriptors.setBounds(10, 30, 420, 250);
		getDescriptors();
		if (agentDescriptors.size() > 0)
			buildAgentPanel();
		if (extraAgentDescriptors.size() > 0)
			buildExtraAgentPanel();
		if (worldDescriptors.size() > 0)
			buildWorldPanel();
	}
	public void refresh(EWorld eWorld) {
		this.eWorld = eWorld;
		int a_old = agentDescriptors.size();
		int ea_old = extraAgentDescriptors.size();
		int w_old = worldDescriptors.size();
		getDescriptors();
		if (agentDescriptors.size() > 0) {
			if (a_old > 0) {
				agentVars.setListData(agentDescriptors);
				agentCVars.setListData(new Vector());
				agentNCVars.setListData(new Vector());
			} else
				buildAgentPanel();
		} else if (a_old > 0) {
			descriptors.remove(agentPanel);
			tabIndice--;
		}
		if (extraAgentDescriptors.size() > 0) {
			if (ea_old > 0) {
				eagentVars.setListData(extraAgentDescriptors);
				eagentCVars.setListData(new Vector());
				eagentNCVars.setListData(new Vector());
			} else
				buildExtraAgentPanel();
		} else if (ea_old > 0) {
			descriptors.remove(extraAgentPanel);
			tabIndice--;
		}
		if (worldDescriptors.size() > 0) {
			if (w_old > 0) {
				worldVars.setListData(worldDescriptors);
				worldCVars.setListData(new Vector());
				worldNCVars.setListData(new Vector());
			} else
				buildWorldPanel();
		} else if (w_old > 0) {
			descriptors.remove(worldPanel);
			tabIndice--;
		}
	}
	/**
	 * Agent panel.
	 *  
	 */
	public void buildAgentPanel() {
		acAdd = new JButton("<");
		acAdd.addActionListener(this);
		acAdd.setBounds(100, 70, 54, 27);
		acAddAll = new JButton("<<");
		acAddAll.addActionListener(this);
		acAddAll.setBounds(100, 100, 54, 27);
		acRemove = new JButton(">");
		acRemove.addActionListener(this);
		acRemove.setBounds(100, 130, 54, 27);
		acRemoveAll = new JButton(">>");
		acRemoveAll.addActionListener(this);
		acRemoveAll.setBounds(100, 160, 54, 27);
		ancAdd = new JButton(">");
		ancAdd.addActionListener(this);
		ancAdd.setBounds(260, 70, 54, 27);
		ancAddAll = new JButton(">>");
		ancAddAll.addActionListener(this);
		ancAddAll.setBounds(260, 100, 54, 27);
		ancRemove = new JButton("<");
		ancRemove.addActionListener(this);
		ancRemove.setBounds(260, 130, 54, 27);
		ancRemoveAll = new JButton("<<");
		ancRemoveAll.addActionListener(this);
		ancRemoveAll.setBounds(260, 160, 54, 27);
		JLabel lcons = new JLabel("Constant");
		lcons.setBounds(10, 20, 80, 10);
		JLabel lvars = new JLabel("Variables");
		lvars.setBounds(160, 20, 80, 10);
		JLabel lncons = new JLabel("Not Contant");
		lncons.setBounds(320, 20, 100, 10);
		agentVars = new JList(agentDescriptors);
		agentVars.setVisibleRowCount(5);
		JScrollPane aVars = new JScrollPane(agentVars);
		aVars.setBounds(160, 50, 90, 150);
		agentCVars = new JList();
		agentCVars.setVisibleRowCount(5);
		JScrollPane aCVars = new JScrollPane(agentCVars);
		aCVars.setBounds(10, 50, 80, 150);
		agentNCVars = new JList();
		agentNCVars.setVisibleRowCount(5);
		JScrollPane aNCVars = new JScrollPane(agentNCVars);
		aNCVars.setBounds(320, 50, 80, 150);
		agentPanel = new JPanel();
		agentPanel.setLayout(null);
		agentPanel.add(lcons);
		agentPanel.add(lvars);
		agentPanel.add(lncons);
		agentPanel.add(aCVars);
		agentPanel.add(acAdd);
		agentPanel.add(acAddAll);
		agentPanel.add(acRemove);
		agentPanel.add(acRemoveAll);
		agentPanel.add(aVars);
		agentPanel.add(ancAdd);
		agentPanel.add(ancAddAll);
		agentPanel.add(ancRemove);
		agentPanel.add(ancRemoveAll);
		agentPanel.add(aNCVars);
		descriptors.insertTab("Agents variables", null, agentPanel, "",
				tabIndice);
		tabIndice++;
	}
	/**
	 * Extra Agent panel.
	 *  
	 */
	public void buildExtraAgentPanel() {
		JLabel lcons = new JLabel("Constant");
		lcons.setBounds(10, 20, 80, 10);
		JLabel lvars = new JLabel("Variables");
		lvars.setBounds(160, 20, 80, 10);
		JLabel lncons = new JLabel("Not Contant");
		lncons.setBounds(320, 20, 100, 10);
		extraAgentPanel = new JPanel();
		extraAgentPanel.setLayout(null);
		eacAdd = new JButton("<");
		eacAdd.addActionListener(this);
		eacAdd.setBounds(100, 70, 54, 27);
		eacAddAll = new JButton("<<");
		eacAddAll.addActionListener(this);
		eacAddAll.setBounds(100, 100, 54, 27);
		eacRemove = new JButton(">");
		eacRemove.addActionListener(this);
		eacRemove.setBounds(100, 130, 54, 27);
		eacRemoveAll = new JButton(">>");
		eacRemoveAll.addActionListener(this);
		eacRemoveAll.setBounds(100, 160, 54, 27);
		eancAdd = new JButton(">");
		eancAdd.addActionListener(this);
		eancAdd.setBounds(260, 70, 54, 27);
		eancAddAll = new JButton(">>");
		eancAddAll.addActionListener(this);
		eancAddAll.setBounds(260, 100, 54, 27);
		eancRemove = new JButton("<");
		eancRemove.addActionListener(this);
		eancRemove.setBounds(260, 130, 54, 27);
		eancRemoveAll = new JButton("<<");
		eancRemoveAll.addActionListener(this);
		eancRemoveAll.setBounds(260, 160, 54, 27);
		eagentVars = new JList(extraAgentDescriptors);
		eagentVars.setVisibleRowCount(5);
		JScrollPane eaVars = new JScrollPane(eagentVars);
		eaVars.setBounds(160, 50, 90, 150);
		eagentCVars = new JList();
		eagentCVars.setVisibleRowCount(5);
		JScrollPane eaCVars = new JScrollPane(eagentCVars);
		eaCVars.setBounds(10, 50, 80, 150);
		eagentNCVars = new JList();
		eagentNCVars.setVisibleRowCount(5);
		JScrollPane eaNCVars = new JScrollPane(eagentNCVars);
		eaNCVars.setBounds(320, 50, 80, 150);
		extraAgentPanel.setLayout(null);
		extraAgentPanel.add(lcons);
		extraAgentPanel.add(lvars);
		extraAgentPanel.add(lncons);
		extraAgentPanel.add(eaCVars);
		extraAgentPanel.add(eacAdd);
		extraAgentPanel.add(eacAddAll);
		extraAgentPanel.add(eacRemove);
		extraAgentPanel.add(eacRemoveAll);
		extraAgentPanel.add(eaVars);
		extraAgentPanel.add(eancAdd);
		extraAgentPanel.add(eancAddAll);
		extraAgentPanel.add(eancRemove);
		extraAgentPanel.add(eancRemoveAll);
		extraAgentPanel.add(eaNCVars);
		descriptors.insertTab("Extra-agents variables", null, extraAgentPanel,
				"", tabIndice);
		tabIndice++;
	}
	/**
	 * World panel.
	 *  
	 */
	public void buildWorldPanel() {
		JLabel lcons = new JLabel("Constant");
		lcons.setBounds(10, 20, 80, 10);
		JLabel lvars = new JLabel("Variables");
		lvars.setBounds(160, 20, 80, 10);
		JLabel lncons = new JLabel("Not Contant");
		lncons.setBounds(320, 20, 100, 10);
		worldPanel = new JPanel();
		worldPanel.setLayout(null);
		wcAdd = new JButton("<");
		wcAdd.addActionListener(this);
		wcAdd.setBounds(100, 70, 54, 27);
		wcAddAll = new JButton("<<");
		wcAddAll.addActionListener(this);
		wcAddAll.setBounds(100, 100, 54, 27);
		wcRemove = new JButton(">");
		wcRemove.addActionListener(this);
		wcRemove.setBounds(100, 130, 54, 27);
		wcRemoveAll = new JButton(">>");
		wcRemoveAll.addActionListener(this);
		wcRemoveAll.setBounds(100, 160, 54, 27);
		wncAdd = new JButton(">");
		wncAdd.addActionListener(this);
		wncAdd.setBounds(260, 70, 54, 27);
		wncAddAll = new JButton(">>");
		wncAddAll.addActionListener(this);
		wncAddAll.setBounds(260, 100, 54, 27);
		wncRemove = new JButton("<");
		wncRemove.addActionListener(this);
		wncRemove.setBounds(260, 130, 54, 27);
		wncRemoveAll = new JButton("<<");
		wncRemoveAll.addActionListener(this);
		wncRemoveAll.setBounds(260, 160, 54, 27);
		worldVars = new JList(worldDescriptors);
		worldVars.setVisibleRowCount(5);
		JScrollPane vars = new JScrollPane(worldVars);
		vars.setBounds(160, 50, 90, 150);
		worldCVars = new JList();
		worldCVars.setVisibleRowCount(5);
		JScrollPane CVars = new JScrollPane(worldCVars);
		CVars.setBounds(10, 50, 80, 150);
		worldNCVars = new JList();
		worldNCVars.setVisibleRowCount(5);
		JScrollPane NCVars = new JScrollPane(worldNCVars);
		NCVars.setBounds(320, 50, 80, 150);
		worldPanel.setLayout(null);
		worldPanel.add(lcons);
		worldPanel.add(lvars);
		worldPanel.add(lncons);
		worldPanel.add(CVars);
		worldPanel.add(wcAdd);
		worldPanel.add(wcAddAll);
		worldPanel.add(wcRemove);
		worldPanel.add(wcRemoveAll);
		worldPanel.add(vars);
		worldPanel.add(wncAdd);
		worldPanel.add(wncAddAll);
		worldPanel.add(wncRemove);
		worldPanel.add(wncRemoveAll);
		worldPanel.add(NCVars);
		descriptors.insertTab("World variables", null, worldPanel, "",
				tabIndice);
		tabIndice++;
	}
	public JPanel getPanel() {
		return descriptorPanel;
	}
	public void clearLists() {
		Vector v = new Vector();
		agentVars.setListData(v);
		agentCVars.setListData(v);
		agentNCVars.setListData(v);
		eagentVars.setListData(v);
		eagentCVars.setListData(v);
		eagentNCVars.setListData(v);
		worldVars.setListData(v);
		worldCVars.setListData(v);
		worldNCVars.setListData(v);
	}
	/**
	 * Get the name of the descriptors used by the World, each Agent and each
	 * ExtraAgent.
	 * <p>
	 * Set worldDescriptors, agentDescriptors and extraAgentDescriptors
	 */
	public void getDescriptors() {
		//System.out.println("[DescriptorParameter.getDescriptors()]");
		worldDescriptors.clear();
		agentDescriptors.clear();
		extraAgentDescriptors.clear();
		if (eWorld != null) {
			worldDescriptors.clear();
			for (Iterator i = (eWorld.getDescriptors()).iterator(); i.hasNext();)
				worldDescriptors.addElement(((DataDescriptor) i.next())
						.getDataName());
			if (eWorld.size() > 0) {
				CAgent agent = (CAgent) eWorld.get(0);
				for (Iterator j = (agent.getDescriptors()).iterator(); j
						.hasNext();)
					agentDescriptors.addElement(((DataDescriptor) j.next())
							.getDataName());
			}
			CAgent[] extraAgents = eWorld.getExtraAgents();
			if (extraAgents.length > 0) {
				for (int i = 0; i < extraAgents.length; i++)
					for (Iterator j = (extraAgents[i].getDescriptors())
							.iterator(); j.hasNext();)
						extraAgentDescriptors.addElement(((DataDescriptor) j
								.next()).getDataName());
			}
		}
	}
	public ArrayList getASelectedConstants() {
		return getSelectedVars(agentCVars);
	}
	public ArrayList getEASelectedConstants() {
		return getSelectedVars(eagentCVars);
	}
	public ArrayList getWSelectedConstants() {
		return getSelectedVars(worldCVars);
	}
	public ArrayList getASelectedVars() {
		return getSelectedVars(agentNCVars);
	}
	public ArrayList getEASelectedVars() {
		return getSelectedVars(eagentNCVars);
	}
	public ArrayList getWSelectedVars() {
		return getSelectedVars(worldNCVars);
	}
	public ArrayList getSelectedVars(JList list) {
		ArrayList varsNames = new ArrayList();
		if (list != null) {
			int index = list.getSelectedIndex();
			Vector v = getListValues(list);
			for (Iterator i = v.iterator(); i.hasNext();)
				varsNames.add(i.next());
			list.setSelectedIndex(index);
		}
		return varsNames;
	}
	public void variableSelected(JList listInit, JList listEnd) {
		Object[] initSelectedValues = listInit.getSelectedValues();
		Vector initValues = getListValues(listInit);
		Vector endValues = getListValues(listEnd);
		for (int i = 0; i < initSelectedValues.length; i++) {
			if (!endValues.contains(initSelectedValues[i])) {
				endValues.addElement(initSelectedValues[i]);
			}
			if (initValues.contains(initSelectedValues[i])) {
				initValues.remove(initSelectedValues[i]);
			}
		}
		listInit.setListData(initValues);
		listEnd.setListData(endValues);
	}
	public Vector getListValues(JList list) {
		Vector values = new Vector();
		if (list.getModel().getSize() > 0) {
			int index = list.getSelectedIndex();
			for (int i = 0; i < list.getModel().getSize(); i++) {
				list.setSelectedIndex(i);
				values.addElement(list.getSelectedValue());
			}
			list.setSelectedIndex(index);
		}
		return values;
	}
	public void variableAllSelected(JList listInit, JList listEnd) {
		Vector initValues = getListValues(listInit);
		Vector endValues = getListValues(listEnd);
		for (int i = 0; i < initValues.size(); i++) {
			if (!endValues.contains(initValues.get(i)))
				endValues.addElement(initValues.get(i));
		}
		initValues.clear();
		listInit.setListData(initValues);
		listEnd.setListData(endValues);
	}
	public void actionPerformed(ActionEvent actionEvent) {
		if (actionEvent.getSource().equals(acAdd)) {
			variableSelected(agentVars, agentCVars);
			return;
		}
		if (actionEvent.getSource().equals(acAddAll)) {
			variableAllSelected(agentVars, agentCVars);
			return;
		}
		if (actionEvent.getSource().equals(acRemove)) {
			variableSelected(agentCVars, agentVars);
			return;
		}
		if (actionEvent.getSource().equals(acRemoveAll)) {
			variableAllSelected(agentCVars, agentVars);
			return;
		}
		if (actionEvent.getSource().equals(ancAdd)) {
			variableSelected(agentVars, agentNCVars);
			return;
		}
		/**
		 * All Agent variables are set non-constants.
		 */
		if (actionEvent.getSource().equals(ancAddAll)) {
			variableAllSelected(agentVars, agentNCVars);
			return;
		}
		if (actionEvent.getSource().equals(ancRemove)) {
			variableSelected(agentNCVars, agentVars);
			return;
		}
		if (actionEvent.getSource().equals(ancRemoveAll)) {
			variableAllSelected(agentNCVars, agentVars);
			return;
		}
		if (actionEvent.getSource().equals(eacAdd)) {
			variableSelected(eagentVars, eagentCVars);
			return;
		}
		if (actionEvent.getSource().equals(eacAddAll)) {
			variableAllSelected(eagentVars, eagentCVars);
			return;
		}
		if (actionEvent.getSource().equals(eacRemove)) {
			variableSelected(eagentCVars, eagentVars);
			return;
		}
		if (actionEvent.getSource().equals(eacRemoveAll)) {
			variableAllSelected(eagentCVars, eagentVars);
			return;
		}
		if (actionEvent.getSource().equals(eancAdd)) {
			variableSelected(eagentVars, eagentNCVars);
			return;
		}
		/**
		 * All Extra-Agent variables are set non-constants.
		 */
		if (actionEvent.getSource().equals(eancAddAll)) {
			variableAllSelected(eagentVars, eagentNCVars);
			return;
		}
		if (actionEvent.getSource().equals(eancRemove)) {
			variableSelected(eagentNCVars, eagentVars);
			return;
		}
		if (actionEvent.getSource().equals(eancRemoveAll)) {
			variableAllSelected(eagentNCVars, eagentVars);
			return;
		}
		if (actionEvent.getSource().equals(wcAdd)) {
			variableSelected(worldVars, worldCVars);
			return;
		}
		if (actionEvent.getSource().equals(wcAddAll)) {
			variableAllSelected(worldVars, worldCVars);
			return;
		}
		if (actionEvent.getSource().equals(wcRemove)) {
			variableSelected(worldCVars, worldVars);
			return;
		}
		if (actionEvent.getSource().equals(wcRemoveAll)) {
			variableAllSelected(worldCVars, worldVars);
			return;
		}
		if (actionEvent.getSource().equals(wncAdd)) {
			variableSelected(worldVars, worldNCVars);
			return;
		}
		/**
		 * All World variables are set non-constants.
		 */
		if (actionEvent.getSource().equals(wncAddAll)) {
			variableAllSelected(worldVars, worldNCVars);
			return;
		}
		if (actionEvent.getSource().equals(wncRemove)) {
			variableSelected(worldNCVars, worldVars);
			return;
		}
		if (actionEvent.getSource().equals(wncRemoveAll)) {
			variableAllSelected(worldNCVars, worldVars);
			return;
		}
	}
	public boolean descriptorsValidate() {
		if (((getASelectedConstants().size() == 0) && (getASelectedVars()
				.size() == 0))
				&& ((getEASelectedConstants().size() == 0) && (getEASelectedVars()
						.size() == 0))
				&& ((getWSelectedConstants().size() == 0) && (getWSelectedVars()
						.size() == 0)))
			return false;
		return true;
	}
}