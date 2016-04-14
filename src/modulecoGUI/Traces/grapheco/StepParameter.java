/*
 * StepParameter.java
 * 
 * Created on 22 avril 2002, 14:35
 */
package modulecoGUI.Traces.grapheco;
/**
 * @author infolab
 * @version
 */
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.Color;
public class StepParameter implements ItemListener {
	protected JTextField stepInit, stepEnd, stepByX;
	protected JRadioButton stepAll, stepFirstEnd, stepOneBy, stepInitEnd;
	protected JPanel stepParameterPanel;
	/**
	 * Static Access to static Field
	 */
	static int ieSelected = ItemEvent.SELECTED;
	static int ieDeselected = ItemEvent.DESELECTED;
	/** 
	 * Creates new StepParameter
	 *
	 */
	public StepParameter() {
		initComponents();
	}
	public void initComponents() {
		stepParameterPanel = new JPanel();
		stepParameterPanel.setLayout(null);
		stepParameterPanel.setBorder(new TitledBorder("Iterations"));
		JLabel lbend = new JLabel("End : ");
		lbend.setForeground(Color.black);
		JLabel lbiter = new JLabel("Iterations");
		lbiter.setForeground(Color.black);
		stepInit = new JTextField();
		stepEnd = new JTextField();
		stepByX = new JTextField();
		stepAll = new JRadioButton("All");
		stepAll.addItemListener(this);
		stepInitEnd = new JRadioButton("Init : ");
		stepInitEnd
				.setToolTipText("Please specify two integers for the first and last iteration.");
		stepInitEnd.addItemListener(this);
		stepFirstEnd = new JRadioButton("First and last");
		stepFirstEnd.addItemListener(this);
		stepOneBy = new JRadioButton("One by");
		stepOneBy
				.setToolTipText("Please specify an integer for the iterations interval.");
		stepOneBy.addItemListener(this);
		/*
		 * ButtonGroup groupe = new ButtonGroup(); groupe.add(stepAll);
		 * groupe.add(stepFirstEnd); groupe.add(stepOneBy);
		 */
		//groupe.add(stepInitEnd);
		stepAll.setBounds(10, 20, 100, 20);
		stepFirstEnd.setBounds(10, 50, 100, 20);
		stepOneBy.setBounds(10, 80, 60, 20);
		stepInitEnd.setBounds(10, 110, 50, 20);
		stepByX.setBounds(70, 80, 40, 20);
		stepInit.setBounds(60, 110, 40, 20);
		stepEnd.setBounds(140, 110, 40, 20);
		lbiter.setBounds(120, 80, 60, 20);
		lbend.setBounds(110, 110, 40, 20);
		stepParameterPanel.add(stepAll);
		stepParameterPanel.add(stepFirstEnd);
		stepParameterPanel.add(stepOneBy);
		stepParameterPanel.add(stepInitEnd);
		stepParameterPanel.add(stepByX);
		stepParameterPanel.add(stepInit);
		stepParameterPanel.add(stepEnd);
		stepParameterPanel.add(lbend);
		stepParameterPanel.add(lbiter);
		refresh();
	}
	public void refresh() {
		stepAll.setSelected(true);
		stepFirstEnd.setSelected(false);
		stepOneBy.setSelected(false);
		stepInitEnd.setSelected(false);
		setStepState(false, false);
	}
	public JPanel getPanel() {
		return stepParameterPanel;
	}
	public int getStepNumber() {
		if (stepAll.isSelected())
			return 1;
		if (stepFirstEnd.isSelected())
			return 2;
		if ((stepOneBy.isSelected()) && (!stepInitEnd.isSelected()))
			return 3;
		if ((stepInitEnd.isSelected()) && (!stepOneBy.isSelected()))
			return 4;
		if ((stepInitEnd.isSelected()) && (stepOneBy.isSelected()))
			return 5;
		return -1;
	}
	public int getStepInit() {
		try {
			return Integer.parseInt(stepInit.getText());
		} catch (NumberFormatException ne) {
			return -1;
		}
	}
	public int getStepEnd() {
		try {
			return Integer.parseInt(stepEnd.getText());
		} catch (NumberFormatException ne) {
			return -1;
		}
	}
	public int getStepByX() {
		try {
			return Integer.parseInt(stepByX.getText());
		} catch (NumberFormatException ne) {
			return -1;
		}
	}
	public void setStepState(boolean bx, boolean ie) {
		//stepInit.setText("");
		stepInit.setEditable(ie);
		//stepEnd.setText("");
		stepEnd.setEditable(ie);
		//stepByX.setText("");
		stepByX.setEditable(bx);
	}
	public void deactivateStepInit() {
		stepInit.setText("");
		stepEnd.setText("");
		stepInit.setEditable(false);
		stepEnd.setEditable(false);
	}
	public void itemStateChanged(ItemEvent itemEvent) {
		if (itemEvent.getSource().equals(stepAll)) {
			if (itemEvent.getStateChange() == ieSelected) {
				setStepState(false, false);
				stepInit.setText("");
				stepEnd.setText("");
				stepByX.setText("");
				stepInitEnd.setSelected(false);
				stepFirstEnd.setSelected(false);
				stepOneBy.setSelected(false);
				return;
			}
		}
		if (itemEvent.getSource().equals(stepFirstEnd)) {
			if (itemEvent.getStateChange() == ieSelected) {
				setStepState(false, false);
				stepInit.setText("");
				stepEnd.setText("");
				stepByX.setText("");
				stepInitEnd.setSelected(false);
				stepAll.setSelected(false);
				stepOneBy.setSelected(false);
				return;
			}
		}
		if (itemEvent.getSource().equals(stepOneBy)) {
			if (itemEvent.getStateChange() == ieSelected) {
				stepAll.setSelected(false);
				stepFirstEnd.setSelected(false);
				if (stepInitEnd.isSelected())
					setStepState(true, true);
				else
					setStepState(true, false);
				return;
			} else {
				if (itemEvent.getStateChange() == ieDeselected) {
					stepByX.setText("");
					stepByX.setEditable(false);
				}
			}
		}
		if (itemEvent.getSource().equals(stepInitEnd)) {
			if (itemEvent.getStateChange() == ieSelected) {
				stepAll.setSelected(false);
				stepFirstEnd.setSelected(false);
				if (stepOneBy.isSelected())
					setStepState(true, true);
				else
					setStepState(false, true);
				return;
			} else if (itemEvent.getStateChange() == ieDeselected)
				deactivateStepInit();
		}
	}
	public boolean stepsValidate() {
		if ((!stepInitEnd.isSelected()) && (!stepFirstEnd.isSelected())
				&& (!stepAll.isSelected()) && (!stepOneBy.isSelected()))
			return false;
		if ((this.stepInitEnd.isSelected())
				&& (stepInit.getText().length() == 0 || stepEnd.getText()
						.length() == 0))
			return false;
		if ((this.stepOneBy.isSelected()) && (stepByX.getText().length() == 0))
			return false;
		if (((stepInitEnd.isSelected()) && (stepOneBy.isSelected()))
				&& (stepInit.getText().length() == 0
						|| stepEnd.getText().length() == 0 || stepByX.getText()
						.length() == 0))
			return false;
		if ((stepInitEnd.isSelected()) && (!stepOneBy.isSelected())) {
			try {
				int n = Integer.parseInt(stepInit.getText());
				int m = Integer.parseInt(stepEnd.getText());
				return true;
			} catch (NumberFormatException ne) {
				return false;
			}
		}
		if ((stepInitEnd.isSelected()) && (stepOneBy.isSelected())) {
			try {
				int n = Integer.parseInt(stepInit.getText());
				int m = Integer.parseInt(stepEnd.getText());
				int k = Integer.parseInt(stepByX.getText());
				return true;
			} catch (NumberFormatException ne) {
				return false;
			}
		}
		if ((stepOneBy.isSelected()) && (!stepInitEnd.isSelected())) {
			try {
				int n = Integer.parseInt(stepByX.getText());
				return true;
			} catch (NumberFormatException ne) {
				return false;
			}
		}
		return true;
	}
}