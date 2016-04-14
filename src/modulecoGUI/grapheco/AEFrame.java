/**
 * AEFrame.java
 * Copyright (c)enst-bretagne
 * @author denis.phan@enst-bretagne.fr
 * @version 1.0  May 2004
 */
package modulecoGUI.grapheco;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Font;
/**
 * Agent Editor Frame. <br>
 * This is the pop-up menu representing an agent.
 *  
 */
public class AEFrame extends Frame {
	EAgentEditor ae;
	public AEFrame(EAgentEditor ae, String title) {
		this.ae = ae;
		add(ae);
		setFont(new Font(null, Font.ITALIC, 5));
		setTitle(title);
		setVisible(true);
		pack();
		/**
		 * Add an action listener to be able to close the window.
		 */
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				closedAE();
			}
		});
	}
	/**
	 * Close the Afent Editor Frame.
	 *  
	 */
	public void closedAE() {
		//System.out.println("AEFRame.closedAE()");
		//ae.getCAgent().setAE(null); RETABLIR LEFFET pour EAgent ?
		if (ae != null) {
			ae.setVisible(false);
			ae = null;
		} else
			System.out
					.println("[modulecoGUI.grapheco.AEFrame.closedAE()] No active AgentEditor");
		dispose();
	}
}