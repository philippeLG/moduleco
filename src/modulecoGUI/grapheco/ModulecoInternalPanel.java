/**
 * ModulecoInternalPanel.java
 * Copyright (c)enst-bretagne
 * @author denis.phan@enst_bretagne.fr
 * @version 1.0  August 2003
 */
package modulecoGUI.grapheco;
import javax.swing.JInternalFrame;
import java.awt.Dimension;
import java.awt.BorderLayout;
import modulecoGUI.ModulecoBean;
import modulecoGUI.grapheco.EPanel;
/**
 * Represent the internal panel, in the centre of the GUI. The Internal Panel
 * hosts the EPanel.
 *  
 */
public class ModulecoInternalPanel extends JInternalFrame {
	protected Dimension dimension;
	protected CentralControl centralControl;
	protected ModulecoBean modulecoBean;
	protected EPanel ePanel;
	protected int width, height;
	//protected JScrollPane jScrollPane1 ;
	/**
	 * Constructor.
	 */
	public ModulecoInternalPanel(ModulecoBean modulecoBean) {
		super("No Model Selected", true, false, true, true);
		this.modulecoBean = modulecoBean;
		Dimension dimension = modulecoBean.getDimension();
		width = dimension.width;
		height = dimension.height;
		this.centralControl = modulecoBean.getCentralControl();
		/**
		 * Maximise the internal panel in the GUI.
		 */
		try {
			this.setMaximum(true);
		} catch (Exception e) {
			//System.err
				//	.println("[modulecoGUI.grapheco.ModulecoInternalPanel()] Error while maximising the panel size: setMaximum impossible");
		}
		
		//getContentPane().add(jScrollPane1, BorderLayout.CENTER);
		//BuildAgentRepresentations();
		//System.out.println("ModulecoInternalPanel.constructor-FIN");
	}
	public void BuildAgentRepresentations() {
		//Strategie 1 : EPanel invoked from this
		//Supprimer 83-86 central control
		//ePanel = new EPanel(w, h, centralControl);
		//centralControl.setEPanel(ePanel);
		//ePanel.CheckMenuDisplayWorld();
		//centralControl.addCAgentRepresentation(ePanel);
		//getContentPane().add( (Component) ePanel, BorderLayout.CENTER);
		//Strategie 2 : EPanel invoked from centralControl
		/**
		 * Get the EPanel from the CentralControl.
		 */
		//System.out
		//		.println("[ModulecoInternalPanel.BuildAgentRepresentations()] Get the EPanel from the CentralControl");
		EPanel ePanel = centralControl.getEPanel();
		/**
		 * Add the EPanel to the Internal Panel.
		 */
		getContentPane().add(ePanel, BorderLayout.CENTER);
	}
}