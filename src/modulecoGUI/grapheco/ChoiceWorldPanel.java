/**
 * Title : PermanentControlPanelBar.java
 * Permanent Control of Control Panel Bar
 * Copyright:    Copyright (c)enst-bretagne
 * @author denis.phan@enst-bretagne.fr
 * created september 19, 2000
 * @version 1.2  august,5, 2002
 */
package modulecoGUI.grapheco;
import java.awt.GridBagConstraints;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
public class ChoiceWorldPanel extends CPanel implements ItemListener {
	protected JComboBox choiceWorld;
	protected int worldSelectIndex, maxWorld;
	protected JLabel labelWorld;
	protected String[] worldName;
	protected ControlPanel controlPanel;
	protected PermanentControlPanelBar permanentControlPanelBar;
	/**
	 * Constructor
	 * 
	 * @param controlPanel
	 * @param permanentControlPanelBar
	 */
	public ChoiceWorldPanel(ControlPanel controlPanel,
			PermanentControlPanelBar permanentControlPanelBar) {
		this.controlPanel = controlPanel;
		this.permanentControlPanelBar = permanentControlPanelBar;
		this.worldName = controlPanel.worldName;
		this.maxWorld = controlPanel.noModels;
		//constraints.anchor = GridBagConstraints.REMAINDER;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.fill = GridBagConstraints.NONE;
		BuildChoiceWorldPanel();
	}
	protected void BuildChoiceWorldPanel() {
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.SOUTHWEST;
		constraints.insets = new java.awt.Insets(2, 2, 5, 2);
		labelWorld = new JLabel("Model", JLabel.CENTER);
		add(labelWorld, 0, 0, 1, 1);
		constraints.insets = new java.awt.Insets(0, 2, 2, 2);
		/**
		 * Dropdown list representing the available worlds
		 */
		choiceWorld = new JComboBox();
		buildChoiceWorld(choiceWorld);
		choiceWorld.setFont(smallfont);
		add(choiceWorld, 0, 1, 1, 1);
		choiceWorld.addItemListener(this);
		/**
		 * Set the initial world by default
		 */
		choiceWorld.setSelectedIndex(0);
	}
	protected void buildChoiceWorld(JComboBox c) {
		for (int i = 0; i < maxWorld + 1; i++)
			c.addItem((Object) worldName[i]);
	}
	public int getSelectedIndex() {
		worldSelectIndex = choiceWorld.getSelectedIndex();
		return worldSelectIndex;
	}
	public void setSelectedIndex(int worldSelectIndex) {
		//System.out.println("[ChoiceWorldPanel.setSelectedIndex()]
		// worldSelectIndex = " + worldSelectIndex);
		this.worldSelectIndex = worldSelectIndex;
		choiceWorld.setSelectedIndex(worldSelectIndex);
	}
	public void itemStateChanged(ItemEvent e) {
		if (e.getSource().equals(choiceWorld)) {
			worldSelectIndex = choiceWorld.getSelectedIndex();
			/**
			 * Uncomment the following line if you don't want to respond to the
			 * event "Select the 'no World seleected'" in the dropdown list
			 */
			//if (worldSelectIndex > 0)
			controlPanel.choiceWorldPanelSelected();
		}
	}
	public void paint(java.awt.Graphics g) {
		super.paint(g);
		g.setColor(java.awt.Color.lightGray);
	}
	public String getChoiceWorldName() {
		String worldName = choiceWorld.getItemAt(getSelectedIndex()).toString();
		return worldName;
	}
}