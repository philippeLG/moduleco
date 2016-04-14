/**
 * ModulecoBean.java
 * Copyright (c)enst-bretagne
 * @author denis.phan@enst_bretagne.fr
 * @version 1.0  August 2002
 */
package modulecoGUI;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JRootPane;
import javax.swing.JDesktopPane;
import modulecoFramework.modeleco.ModulecoLauncher;
import modulecoGUI.grapheco.CentralControl;
import modulecoGUI.grapheco.ControlPanel;
import modulecoGUI.grapheco.ModulecoInternalPanel;
/**
 * Create the CentralControl and host the GUI.
 */
public class ModulecoBean extends JRootPane {
	/**
	 * Manage the GUI.
	 */
	protected CentralControl centralControl;
	/**
	 * Launch the world.
	 */
	protected ModulecoLauncher modulecoLauncher;
	/**
	 * Manage the GUI.
	 */
	protected ControlPanel controlPanel;
	public ModulecoInternalPanel modulecoInternalPanel;
	protected JDesktopPane desktop;
	protected Dimension dimension;
	/**
	 * Constructor used when no GUI.
	 * 
	 * @param dimension
	 */
	public ModulecoBean(Dimension dimension) {
		/**
		 * Create the CentralControl.
		 */
		centralControl = new CentralControl(this);
		/**
		 * Get the reference to the ModulecoLauncher and the ControlPanel.
		 */
		modulecoLauncher = centralControl.getModulecoLauncher();
		controlPanel = centralControl.getControlPanel();
		/**
		 * Set the dimension.
		 */
		this.dimension = dimension;
		setPreferredSize(dimension);
		/**
		 * Create the deskop, in the centre of the GUI. The desktop hosts the
		 * Internal Panel.
		 */
		desktop = new JDesktopPane();
		/**
		 * Add the menu bar at the top. <br>
		 * File, Active Zone, etc.
		 */
		setJMenuBar(controlPanel.getMenuBar());
		getContentPane().setLayout(new BorderLayout());
		/**
		 * Add the Control Panel. <br>
		 * Create, Start, etc.
		 */
		getContentPane().add(controlPanel, BorderLayout.NORTH);
		/**
		 * Add the desktop.
		 */
		getContentPane().add(desktop, BorderLayout.CENTER);
		/**
		 * Add the Internal Panel to the Desktop.
		 */
		FillDesktop();
	}
	/**
	 * Get the desktop.
	 * 
	 * @return
	 */
	public JDesktopPane getDesktop() {
		return desktop;
	}
	/**
	 * Create the Internal Panel.
	 */
	public void FillDesktop() {
		/**
		 * Create the Internal Panel.
		 */
		modulecoInternalPanel = new ModulecoInternalPanel(this);
		/**
		 * Add the Internal Panel to the desktop.
		 */
		desktop.add(modulecoInternalPanel,
				javax.swing.JLayeredPane.DEFAULT_LAYER);
		/**
		 * Set it visible.
		 */
		modulecoInternalPanel.setVisible(true);
		modulecoInternalPanel.setSize(792, 492);
		/**
		 * Add the EPanel to the Internal Panel.
		 */
		modulecoInternalPanel.BuildAgentRepresentations();
	}
	/**
	 * Get the internal panel.
	 * 
	 * @return the Internal Panel
	 */
	public ModulecoInternalPanel getModulecoInternalPanel() {
		return modulecoInternalPanel;
	}
	/**
	 * @return Returns the dimension.
	 */
	public Dimension getDimension() {
		return dimension;
	}
	/**
	 * @return Returns the centralControl.
	 */
	public CentralControl getCentralControl() {
		return centralControl;
	}
	/**
	 * @return Returns the modulecoLauncher.
	 */
	public ModulecoLauncher getModulecoLauncher() {
		return modulecoLauncher;
	}
}