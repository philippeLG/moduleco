/**
 * EWarningDialog.java
 * Copyright (c)enst-bretagne
 * @author Philippe.LeGoff@enst-bretagne.fr, Denis.@enst-bretagne.fr
 * @version 1.0 August 2000
 * @version 1.1  july 2001
 * @version 1.2 July 2003
 */
package modulecoGUI.grapheco;
import javax.swing.JLabel;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JButton;
/**
 * Pop-up menu displaying a warning message.
 * <p>
 * Usage: <br>
 * EWarningDialog warningPopup = new EWarningDialog("Message to display"); <br>
 * warningPopup.setVisible(true);
 *  
 */
public class EWarningDialog extends JDialog {
	// Variables declaration - do not modify
	private JLabel labelMessage;
	private JButton buttonOK;
	private String message;
	// End of variables declaration
	//Utilisé par modulecoFramework.modeleco.zone.RandomPairwise
	public EWarningDialog(String message) {
		super(new JFrame(), "Warning", true);
		this.message = message;
		initComponents();
		pack();
	}
	private void initComponents() {
		labelMessage = new JLabel();
		buttonOK = new JButton();
		setLayout(new java.awt.BorderLayout());
		setResizable(false);
		setModal(true);
		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent evt) {
				closeDialog();
			}
		});
		labelMessage.setBackground(new java.awt.Color(204, 204, 204));
		labelMessage.setText(message);
		labelMessage.setForeground(java.awt.Color.black);
		labelMessage.setHorizontalAlignment(JLabel.CENTER);
		labelMessage.setFont(new java.awt.Font("Dialog", 0, 14));
		add(labelMessage, java.awt.BorderLayout.NORTH);
		buttonOK.setBackground(new java.awt.Color(204, 204, 204));
		buttonOK.setForeground(java.awt.Color.black);
		buttonOK.setText("OK");
		buttonOK.setFont(new java.awt.Font("Dialog", 0, 11));
		buttonOK.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				okActionPerformed(evt);
			}
		});
		add(buttonOK, java.awt.BorderLayout.SOUTH);
	}
	protected void okActionPerformed(java.awt.event.ActionEvent evt) {
		closeDialog();
	}
	protected void closeDialog() {
		setVisible(false);
		dispose();
	}
}