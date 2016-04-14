/*
 * @(#)CPIterations.java 1.1 24-Feb-04
 */
package modulecoGUI.grapheco;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
/**
 * Create a couple label-field Used to display the time step in the
 * CentralControl, for instance
 * 
 * @see modulecoGUI.grapheco.CPanel
 * @author Gilles Daniel (gilles@cs.man.ac.uk)
 * @version 1.0, 23-Feb-04
 * @version 1.1, 24-Feb-04
 */
public class CPIterations extends CPanel {
	protected String title;
	protected String defaultValue;
	public JTextField field;
	/**
	 * Contructor
	 * 
	 * @param title
	 *            The text in the label
	 * @param defaultValue
	 *            The initial value in the field
	 * @param editable
	 *            Whether the field is editable or read-only
	 */
	public CPIterations(String title, String defaultValue, boolean editable) {
		super();
		this.title = title;
		this.defaultValue = defaultValue;
		BuildOptionBar(editable);
	}
	/**
	 * Build the two elements, title and field
	 */
	public void BuildOptionBar(boolean editable) {
		/**
		 * Add a label title
		 */
		constraints.fill = GridBagConstraints.NONE;
		constraints.insets = new java.awt.Insets(2, 2, 10, 2);
		JLabel labelSize = new JLabel(title, JLabel.CENTER);
		add(labelSize, 0, 0, 1, 1);
		/**
		 * Add the time step counter field
		 */
		constraints.insets = new java.awt.Insets(0, 2, 2, 2);
		field = new JTextField(defaultValue, 5);
		//field = new ModulecoTextField(defaultValue, 0, 5, 6);
		field.setFont(smallfont);
		field.setEditable(editable);
		add(field, 0, 1, 1, 1);
	}
	/**
	 * Update the content of the field
	 * 
	 * @param value
	 */
	public void setText(String value) {
		field.setText(value);
	}
}