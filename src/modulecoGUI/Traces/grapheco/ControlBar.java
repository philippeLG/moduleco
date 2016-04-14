/*
 * ControlBar.java
 * 
 * Created on 22 avril 2002, 12:35
 */
package modulecoGUI.Traces.grapheco;
/**
 * @author infolab
 */
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import java.awt.event.ActionListener;
/**
 * In the data recording window, handle the buttons at the bottom.
 * 
 * @author Gilles Daniel (gilles@cs.man.ac.uk)
 * @version 1.0, 05-May-2004
 */
public class ControlBar implements ActionListener {
	private JButton save;
	private JButton cancel;
	private JButton exit;
	private JButton help;
	private JPanel controlBarPanel;
	private TraceParameter traceParameter;
	/**
	 * Creates new ControlBar.
	 * 
	 * @param traceParameter
	 */
	public ControlBar(TraceParameter traceParameter) {
		this.traceParameter = traceParameter;
		initComponents();
	}
	/**
	 * Initialise the different components.
	 *  
	 */
	public void initComponents() {
		controlBarPanel = new JPanel();
		controlBarPanel.setLayout(null);
		controlBarPanel.setBorder(new TitledBorder(null, ""));
		/**
		 * Save button.
		 */
		save = new JButton("Save");
		save.setToolTipText("Start recording");
		controlBarPanel.add(save);
		save.setBounds(2, 2, 80, 27);
		save.addActionListener(this);
		/**
		 * Cancel button.
		 */
		cancel = new JButton("Cancel");
		cancel.setToolTipText("Exit");
		controlBarPanel.add(cancel);
		cancel.setBounds(82, 2, 80, 27);
		cancel.addActionListener(this);
		/**
		 * Help button
		 */
		//help = new JButton("Help");
		//help.setEnabled(false);
		//controlBarPanel.add(help);
		//help.setBounds(180, 10, 80, 27);
		//help.addActionListener(this);
		/**
		 * Exit button.
		 */
		//exit = new JButton("Exit");
		//exit.setToolTipText("Exit application");
		//controlBarPanel.add(exit);
		//exit.setBounds(260, 10, 80, 27);
		//exit.addActionListener(this);
		/**
		 *  
		 */
		save.setEnabled(true);
		cancel.setEnabled(true);
		//deactivate();
	}
	public JPanel getPanel() {
		return controlBarPanel;
	}
	//public void activate() {
	//	save.setEnabled(false);
	//	cancel.setEnabled(true);
	//}
	//public void deactivate() {
	//	save.setEnabled(true);
	//	cancel.setEnabled(false);
	//	traceParameter.deactivateCollect();
	//}
	//public void refresh() {
	//	deactivate();
	//}
	public void actionPerformed(java.awt.event.ActionEvent actionEvent) {
		/**
		 * Save button --> save & exit
		 */
		if (actionEvent.getSource().equals(save)) {
			if (traceParameter.saveSelectedParameters())
				traceParameter.dispose();
		}
		/**
		 * Cancel button --> exit
		 */
		if (actionEvent.getSource().equals(cancel)) {
			//deactivate();
			//traceParameter.stopCollect();
			traceParameter.centralControl.controlPanel.checkMenuRecord.setSelected(false);
			traceParameter.dispose();
		}
		//if (actionEvent.getSource().equals(exit))
		//	traceParameter.dispose();
	}
}