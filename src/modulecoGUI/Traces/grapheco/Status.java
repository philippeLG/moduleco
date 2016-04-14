/*
 * Status.java
 * 
 * Created on 25 avril 2002, 18:21
 */
package modulecoGUI.Traces.grapheco;
/**
 * @author infolab
 * @version
 */
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import java.awt.Color;
public class Status {
	protected JRadioButton activate;
	protected JRadioButton deactivate;
	protected JPanel statusPanel;
	/** Creates new Status */
	public Status() {
		initComponents();
	}
	public void initComponents() {
		statusPanel = new JPanel();
		statusPanel.setLayout(null);
		statusPanel.setBorder(new TitledBorder("Collect status"));
		activate = new JRadioButton("Activate");
		deactivate = new JRadioButton("Deactivate");
		ButtonGroup groupe = new ButtonGroup();
		groupe.add(activate);
		groupe.add(deactivate);
		setDeactivate();
		activate.setBounds(10, 20, 80, 25);
		statusPanel.add(activate);
		deactivate.setBounds(100, 20, 80, 25);
		statusPanel.add(deactivate);
	}
	public void refresh() {
		setDeactivate();
	}
	public void setActivate() {
		activate.setSelected(true);
		activate.setForeground(Color.red);
		deactivate.setForeground(Color.black);
	}
	public void setDeactivate() {
		deactivate.setSelected(true);
		activate.setForeground(Color.black);
		deactivate.setForeground(Color.red);
	}
	public JPanel getPanel() {
		return statusPanel;
	}
}