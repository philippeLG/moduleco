/**
 * Title : PermanentControlPanelBar.java
 * Permanent Control of Contol Panel Bar
 * Copyright:    Copyright (c)enst-bretagne
 * @author denis.phan@enst-bretagne.fr
 * created on september 19, 2000
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
public class ChoiceNeighbourPanel extends CPanel implements ItemListener {
	protected JComboBox choiceNeighbour;
	protected int neighbourSelectIndex, maxNeighbour;
	protected JLabel labelNeighbour;
	protected String[] neighbourName;
	protected ControlPanel controlPanel;
	public ChoiceNeighbourPanel(ControlPanel controlPanel) {
		this.controlPanel = controlPanel;
		this.neighbourName = controlPanel.neighbourName;
		this.maxNeighbour = controlPanel.noNeighbourhoods;
		BuildChoiceNeighbourPanel();
	}
	protected void BuildChoiceNeighbourPanel() {
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new java.awt.Insets(2, 2, 5, 2);
		labelNeighbour = new JLabel("Neighbour", JLabel.CENTER);
		constraints.anchor = GridBagConstraints.SOUTH;
		add(labelNeighbour, 0, 0, 1, 1);
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.insets = new java.awt.Insets(0, 2, 2, 2);
		constraints.anchor = GridBagConstraints.CENTER;
		choiceNeighbour = new JComboBox();
		buildChoiceNeighbour(choiceNeighbour);
		add(choiceNeighbour, 0, 1, 1, 1);
		choiceNeighbour.setFont(smallfont);
		choiceNeighbour.addItemListener(this);
		choiceNeighbour.setSelectedIndex(0);
	}
	/**
	 * Build the list of the neighbour names.
	 * @param c
	 */
	protected void buildChoiceNeighbour(JComboBox c) {
		for (int i = 0; i < maxNeighbour; i++) {
			String name = neighbourName[i];
			c.addItem((Object) name);
		}
	}
	public int getSelectedIndex() {
		neighbourSelectIndex = choiceNeighbour.getSelectedIndex();
		return neighbourSelectIndex;
	}
	public void setSelectedIndex(int neighbourSelectIndex) {
		this.neighbourSelectIndex = neighbourSelectIndex;
		choiceNeighbour.setSelectedIndex(neighbourSelectIndex);
	}
	public void itemStateChanged(ItemEvent e) {
		if (e.getSource().equals(choiceNeighbour)) {
			neighbourSelectIndex = choiceNeighbour.getSelectedIndex();
			// two events : checkOut, checkIn
			if (neighbourSelectIndex > 0)
				controlPanel.ChoiceNeighbourPanelSelected();
		}
	}
	public void paint(java.awt.Graphics g) {
		super.paint(g);
		g.setColor(java.awt.Color.lightGray);
	}
	public String getChoiceNeighbourName() {
		String neighbourName = choiceNeighbour.getItemAt(getSelectedIndex())
				.toString();
		return neighbourName;
	}
}