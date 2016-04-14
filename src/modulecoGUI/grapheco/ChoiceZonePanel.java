/**
 * Title : PermanentControlPanelBar.java
 * Permanent Control of Contol Panel Bar
 * Copyright:    Copyright (c)enst-bretagne
 * @author denis.phan@enst-bretagne.fr
 *created on september 19, 2000
 * @version 1.2  august,5, 2002
 */
package modulecoGUI.grapheco;
import javax.swing.JLabel;
// import java.awt.Choice;
import javax.swing.JComboBox;
import java.awt.GridBagConstraints;
// import java.awt.Font;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
public class ChoiceZonePanel extends CPanel implements ItemListener {
	protected JComboBox choiceZone;
	protected int zoneSelectIndex, maxZone;
	protected JLabel labelZone;
	protected String[] zoneName;
	protected ControlPanel controlPanel;
	public ChoiceZonePanel(ControlPanel controlPanel) {
		this.controlPanel = controlPanel;
		this.zoneName = controlPanel.neighbourName;
		this.maxZone = controlPanel.noNeighbourhoods;
		BuildChoiceZonePanel();
	}
	protected void BuildChoiceZonePanel() {
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new java.awt.Insets(2, 2, 5, 2);
		labelZone = new JLabel("Zone", JLabel.CENTER);
		constraints.anchor = GridBagConstraints.SOUTH;
		add(labelZone, 0, 0, 1, 1);
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.insets = new java.awt.Insets(0, 2, 2, 2);
		constraints.anchor = GridBagConstraints.CENTER;
		choiceZone = new JComboBox();
		buildChoiceZone(choiceZone);
		add(choiceZone, 0, 1, 1, 1);
		choiceZone.setFont(smallfont);
		choiceZone.addItemListener(this);
		choiceZone.setSelectedIndex(0);
	}
	/**
	 * Build the list of the zone names
	 * 
	 * @param c
	 */
	protected void buildChoiceZone(JComboBox c) {
		for (int i = 0; i < maxZone; i++) {
			String name = zoneName[i];
			c.addItem((Object) name);
		}
	}
	public int getSelectedIndex() {
		zoneSelectIndex = choiceZone.getSelectedIndex();
		return zoneSelectIndex;
	}
	public void setSelectedIndex(int zoneSelectIndex) {
		this.zoneSelectIndex = zoneSelectIndex;
		choiceZone.setSelectedIndex(zoneSelectIndex);
	}
	public void itemStateChanged(ItemEvent e) {
		if (e.getSource().equals(choiceZone)) {
			zoneSelectIndex = choiceZone.getSelectedIndex();
			// two events : checkOut, checkIn
			if (zoneSelectIndex > 0)
				controlPanel.ChoiceZonePanelSelected();
		}
	}
	public void paint(java.awt.Graphics g) {
		super.paint(g);
		g.setColor(java.awt.Color.lightGray);
	}
	public String getChoiceZoneName() {
		String zoneName = choiceZone.getItemAt(getSelectedIndex()).toString();
		return zoneName;
	}
}