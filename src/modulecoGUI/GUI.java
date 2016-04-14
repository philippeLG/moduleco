package modulecoGUI;
import java.awt.Dimension;
import javax.swing.JFrame;
//import modulecoFramework.ModulecoAgent;
import modulecoFramework.modeleco.ModulecoLauncher;
/**
 * Launch the GUI.
 * <p>
 * The Moduleco window can appear alone or nested in the MadKit window.
 */
public class GUI extends JFrame {
//public class GUI {
	/**
	 * Bean used to host the application.
	 */
	protected ModulecoBean modulecoBean;
	/**
	 * Create and launch the world.
	 */
	protected ModulecoLauncher modulecoLauncher;
	/**
	 * Manage the display of the Window
	 */
	//public GUI(ModulecoAgent modulecoAgent) {
	public GUI() {
		/**
		 * Dimension of the Moduleco window.
		 */
		Dimension d = new Dimension(800, 600);
		/**
		 * Create the bean and get a reference to ModulecoLauncher, created in the bean.
		 */
		modulecoBean = new ModulecoBean(d);
		modulecoLauncher = modulecoBean.getModulecoLauncher();
		/**
		 * Uncomment the following lines to make Moduleco appear in its own
		 * window. <br>
		 * WARNING: the MadKit window will still appear.
		 */
		setTitle("Moduleco");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(0, 0);
		setSize(d);
		setVisible(true);
		getContentPane().add(modulecoBean);
		show();
		/**
		 * Uncomment the following lines to make Moduleco appear in the MadKit
		 * window.
		 */
		//modulecoAgent.setGUIObject(modulecoBean);
		//modulecoBean.setLocation(0, 0);
		//modulecoBean.setSize(d);
		//modulecoBean.setVisible(true);
	}
	/**
	 * @return Returns the modulecoLauncher.
	 */
	public ModulecoLauncher getModulecoLauncher() {
		return modulecoLauncher;
	}
}