/**
 * EAgentEditor.java
 * Copyright (c)enst-bretagne
 * @author Denis.phan@enst-bretagne.fr, sebastien.chivoret@ensta.org
 * @version 1.0 May 2000
 * @version 1.4.2  May 2004
 */
package modulecoGUI.grapheco;
import javax.swing.JPanel;
import java.awt.Component;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import modulecoFramework.modeleco.CAgent;
import modulecoFramework.modeleco.EWorld;
import modulecoGUI.grapheco.descriptor.DataDescriptor;
/**
 * Editor of EAgents, either World or Agents.
 *  
 */
public class EAgentEditor extends EAdditionalPanel //CPanel
{
	protected Font font = new Font("Dialog", 0, 12);
	protected Font smallfont = new Font("Dialog", 0, 11);
	protected Color colorBackground = Color.lightGray;
	protected Color colorForeground = Color.black;
	public CAgent agent;
	public EWorld eWorld;
	public CentralControl control;
	public int agentID;
	protected ArrayList descriptors;
	protected ArrayList containerList;
	protected static int columns;
	protected int line = 0, number = 0;
	protected AEFrame aeFrame;
	/**
	 * Invoqued by a CAgent (e.g. Eworld, EAgent, etc.)
	 * 
	 * @param ea
	 */
	public EAgentEditor(CAgent ea) {
		agent = ea;
		setBackground(colorBackground);
		setForeground(colorForeground);
		containerList = new ArrayList();
	}
	/**
	 * @param col
	 */
	public void build(int col) {
		//System.out.println("[EAgentEditor.build()] col = " + col);
		columns = col;
		this.removeAll();
		descriptors = agent.getDescriptors();
		/**
		 * The editor gets the data from the agent
		 */
		if (descriptors.size() != 0) {
			for (int i = 0; i < descriptors.size(); i++) {
				JPanel container = ((DataDescriptor) descriptors.get(i))
						.buildContainer(containerList);
				add((Component) container, i % columns, line, 1, 1);
				//container.setVisible(true);
				number++;
				if (i % columns == columns - 1)
					line++;
			}
			if (descriptors.size() % columns != 0) {
				for (int k = 0; k < columns - descriptors.size() % columns; k++) {
					add((Component) new JPanel(), descriptors.size() % columns
							+ k, line, 1, 1);
					number++;
				}
				line++;
			}
			validate();
		}
	}
	/**
	 * Update the editor. <br>
	 * Method called by the edited agent.
	 */
	public void update() {
		// (setDataToInterface)
		//System.out.println("***updating "+ag.getClass().getName());
		descriptors = agent.getDescriptors(); //the data are updated
		for (int i = 0; i < descriptors.size(); i++) {
			((DataDescriptor) descriptors.get(i))
					.updateContainer((JPanel) containerList.get(i));
		}
	}
	/**
	 * @return
	 */
	public int getLine() {
		return line;
	}
	/**
	 * @return
	 */
	public int getColumns() {
		return columns;
	}
	/**
	 * @param evt
	 */
	public void exitForm(java.awt.event.WindowEvent evt) {
		agent.terminate();
	}
	/**
	 * return the CAgent associated with this EAgentEditor
	 * 
	 * @return CAgent
	 */
	public CAgent getCAgent() {
		return agent;
	}
	/**
	 * if this EAgentEditor is within a PopUp, set from EditorManager the
	 * AEFrame associated with this EAgentEditor.
	 * 
	 * @param aeFrame
	 */
	public void setPopUpFrame(AEFrame aeFrame) {
		this.aeFrame = aeFrame;
	}
	/**
	 * if this EAgentEditor is within a PopUp, return the AEFrame associated
	 * with this EAgentEditor.
	 * 
	 * @return AEFrame
	 */
	public AEFrame getPopUpFrame() {
		return aeFrame;
	}
}