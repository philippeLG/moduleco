/**
 * CentralControl.java
 * Copyright (c)enst-bretagne
 * @author frederic.falempin@enst-bretagne.fr, denis.phan@enst_bretagne.fr
 * @version 1.0  August 2000
 * @version 1.2  August 2002
 * @version 1.4  February 2004
 * @version 1.5  June 2004
 */
package modulecoGUI.grapheco;
import java.awt.GridBagConstraints;
import javax.swing.JFileChooser;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import modulecoFramework.modeleco.EWorld;
import modulecoFramework.modeleco.CAgent;
import modulecoFramework.modeleco.EAgent;
import modulecoFramework.modeleco.ModelParameters;
import modulecoFramework.modeleco.SimulationControl;
import modulecoFramework.modeleco.ModulecoLauncher;
import modulecoGUI.ModulecoBean;
import modulecoGUI.Traces.grapheco.TraceParameter;
/**
 * The CentralControl is the controler of the application, when the GUI is
 * enabled. In particular, the CentralControl creates the ModulecoLauncher.
 * 
 * @see modulecoGUI.grapheco.ControlPanel
 * @see modulecoFramework.modeleco.WorldListener
 */
public class CentralControl extends CAgentRepresentationContainer {
	protected EWorld eWorld;
	protected EWorld previousEworld;
	protected String model;
	protected String previousModel;
	protected EPanel ePanel;
	protected JFileChooser fileChooser = null;
	protected ArrayList previousDescriptors;
	protected Hashtable previousExtraAgentDescriptors;
	//protected models.discreteChoice.Autorun autorun;
	//protected models.smallWorld.Autorun autorun2;
	public EditorManager editorManager;
	public boolean statManagerEnabled, worldEnabled;
	protected GraphicsSelector graphicsSelector;
	/**
	 * The model parameters
	 */
	public ModelParameters modelParameters;
	/**
	 * The TraceParameter manages the trace, to log the simulation results.
	 */
	protected TraceParameter traceParameter;
	/**
	 * The ModulecoLauncher launches the world.
	 */
	public ModulecoLauncher modulecoLauncher;
	/**
	 * The SimulationControl runs the simulation by managing the time scheduler.
	 */
	protected SimulationControl simulationControl;
	/**
	 * The ModulecoBean launches the GUI.
	 */
	protected ModulecoBean modulecoBean;
	/**
	 * The ControlPanel manages the GUI.
	 */
	public ControlPanel controlPanel;
	/**
	 * The local path to the root directory. <br>
	 * e.g. modulecoPathRoot = xxx/modulecoMK/
	 */
	protected static String modulecoPathRoot = System.getProperty("user.dir")
			+ File.separator;
	/**
	 * The URL to find resources on the Web. <br>
	 * e.g. webResources = http://www.cs.man.ac.uk/~danielg/moduleco/
	 */
	protected static String webResources = "http://www.cs.man.ac.uk/ai/public/moduleco/";
	/**
	 * Constructor.
	 * 
	 * @param modulecoBean
	 */
	public CentralControl(ModulecoBean modulecoBean) {
		/**
		 * Get a reference to ModulecoBean.
		 */
		this.modulecoBean = modulecoBean;
		/**
		 * Create the ModulecoLauncher with a link to the CentralControl.
		 */
		modulecoLauncher = new ModulecoLauncher(this);
		/**
		 * Create the Control Panel.
		 */
		controlPanel = new ControlPanel(this);
		/**
		 * Create the EditorManager.
		 */
		editorManager = new EditorManager(this);
		/**
		 * Set the GUI constraints
		 */
		constraints.fill = GridBagConstraints.NONE;
		constraints.anchor = GridBagConstraints.NORTH;
		constraints.ipadx = 0;
		constraints.ipady = 0;
		constraints.insets = new java.awt.Insets(0, 0, 0, 0);
		/**
		 * Create the EPanel.
		 */
		ePanel = new EPanel(800, 500, this);
		ePanel.setControlPanel(controlPanel);
		ePanel.CheckMenuDisplayWorld();
		this.addCAgentRepresentation(ePanel);
		/**
		 * Create the previous descriptors and initialise them to nothing.
		 */
		previousDescriptors = new ArrayList();
		previousExtraAgentDescriptors = new Hashtable();
		/**
		 * Hack for the two models "discreteChoice" and "smallWorld".
		 */
		//buildAutorun();
	}
	/**
	 * Ask for the creation of the world, update the GUI, update the trace.
	 * <p>
	 * This method is called by the ControlPanel only. The parameters model,
	 * neighbour, scheduler and zone represent the values selected in the GUI.
	 * 
	 * @param previousModel
	 * @param model
	 * @param neighbour
	 * @param scheduler
	 * @param zone
	 */
	public void create(String previousModel, String model, String neighbour,
			String scheduler, String zone) {
		//System.out.println("[CentralControl.create()]");
		/**
		 * Check that the internal panel is open.
		 */
		if (modulecoBean.getModulecoInternalPanel().isClosed()) {
			System.out
					.println("[CentralControl.create()] The panel is closed.");
			//modulecoBean.getModulecoInternalPanel().show();
			//FillDesktop();
		}
		/**
		 * Get the size of the world defined in the GUI
		 */
		// DP 24/07/2004
		int size = controlPanel.getChosenSize();
		Integer Size = new Integer(size);
		if (model.equals("smallWorld"))
			Size = smallWorldHadHocInit(model, size);
		/*
		 * int size = Size.intValue(); if (size > 8) {
		 * controlPanel.setCircleWorldDesabled(); } else {
		 * controlPanel.setCircleWorldEnabled(); } }
		 */
		/**
		 * If the world we are creating is the same as the previous one, reuse
		 * the parameters and descriptors set in the GUI.
		 */
		if (previousModel == model) {
			/**
			 * Save the value of the descriptors of this model in order to reuse
			 * them later on.
			 */
			previousEworld = eWorld;
			previousDescriptors = previousEworld.returnDescriptors();
			setExtraAgentDescriptors_temp(eWorld);
			simulationTerminate();
			/**
			 * We build the same world, but this time with the parameters from
			 * the GUI.
			 */
			modelParameters.setName(model);
			modelParameters.setStringLength(Size.toString());
			modelParameters.setNeighbourhood(neighbour);
			modelParameters.setZone(zone);
			modelParameters.setTimeScheduler(scheduler);
			modulecoLauncher.create(model, modelParameters);
		} else {
			/**
			 * Create a completely new world.
			 */
			modulecoLauncher.create(model, null);
			modelParameters = modulecoLauncher.getModelParameters();
		}
		/**
		 * Enable some buttons and sub-menus this invocation need to be here;
		 * because method enableButtons(boolean) need to acces at
		 * modelParameters.
		 *  
		 */
		controlPanel.enableButtons(true);
		/**
		 * Represent the World
		 */
		setCAgent(modulecoLauncher.getEWorld());
		/**
		 * Get the simulation control
		 */
		setSimulationControl(modulecoLauncher.getSimulationControl());
		/**
		 * Set the flag worldEnabled.
		 */
		worldEnabled = true;
		/**
		 * Send the parameters values from the world to the GUI.
		 */
		editorManager.setDataToInterface();
		/**
		 * Display the World Editor at the bottom.
		 */
		displayWorldEditor(true);
		//eWorld.getInfo();
		updateMenuDisplayXGraphics();
		/**
		 * Update the GUI.
		 */
		validate();
		/**
		 * Update the trace.
		 */
		if ((traceParameter != null) && (traceParameter.isShowing())) {
			traceParameter.refresh(this);
		}
		/**
		 * Set the panel title.
		 */
		modulecoBean.getModulecoInternalPanel().setTitle(model);
	}
	/**
	 * @param className
	 * @return
	 */
	public CAgentRepresentation loadCAgentRepresentationClass(String className) {
		CAgentRepresentation cAgentRepresentation = null;
		try {
			cAgentRepresentation = (CAgentRepresentation) Class.forName(
					className).newInstance();
		} catch (IllegalAccessException e) {
			System.out.println(e.toString());
		} catch (InstantiationException e) {
			System.out.println(e.toString());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		cAgentRepresentation.setCentralControl(this);
		return cAgentRepresentation;
	}
	/**
	 * Surcharge la méthode updateImage() de la classe parente
	 * CAgentRepresentationContainer afin de permettre le controle de
	 * l'affichage.
	 */
	public void updateImage() {
		super.updateImage();
	}
	protected void setStatManagerState(boolean statManagerEnabled) {
		this.statManagerEnabled = statManagerEnabled;
	}
	/**
	 * Load a world i.e. all its parameters, previously saved in a file.
	 */
	public void loadWorld() {
		/**
		 * Set the initial directory
		 */
		String filename = modulecoPathRoot + "outputs" + File.separator;
		/**
		 * Create the file chooser if it does not exist yet
		 */
		if (fileChooser == null) {
			/**
			 * Create the file chooser
			 */
			fileChooser = new JFileChooser(new File(filename));
		}
		/**
		 * Display the file chooser
		 */
		int retval = fileChooser.showDialog(this, "Load World");
		/**
		 * If the burron OK is pressed
		 */
		if (retval == JFileChooser.APPROVE_OPTION) {
			/**
			 * Get the file chosen
			 */
			File file = fileChooser.getSelectedFile();
			/**
			 * Load the eWorld from the file.
			 */
			try {
				FileInputStream fileInputStream = new FileInputStream(file);
				ObjectInputStream objectInputStream = new ObjectInputStream(
						fileInputStream);
				eWorld = (EWorld) objectInputStream.readObject();
				objectInputStream.close();
				fileInputStream.close();
			} catch (IOException ex) {
				System.out.println(ex.toString());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			//eWorld.setCentralControl(this);
			this.setCAgent(eWorld);
		}
	}
	/**
	 * Save the parameters of a World into a file, by serialisation.
	 */
	public void saveWorld() {
		//System.out.println("[CentralControl.saveWorld()] ");
		/**
		 * Set the initial directory
		 */
		String filename = modulecoPathRoot + "outputs" + File.separator;
		/**
		 * Create the file chooser if it does not exist yet
		 */
		if (fileChooser == null) {
			/**
			 * Create the file chooser
			 */
			fileChooser = new JFileChooser(new File(filename));
		}
		/**
		 * Display the file chooser
		 */
		int retval = fileChooser.showDialog(this, "Save World");
		/**
		 * If the burron OK is pressed
		 */
		if (retval == JFileChooser.APPROVE_OPTION) {
			/**
			 * Get the file chosen
			 */
			File file = fileChooser.getSelectedFile();
			/**
			 * Store the object eWorld in the file. Only the non-transient and
			 * non-static variables are serialised and stored in the file.
			 */
			try {
				FileOutputStream fileOutputStream = new FileOutputStream(file);
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(
						fileOutputStream);
				objectOutputStream.writeObject(eWorld);
				objectOutputStream.flush();
				fileOutputStream.flush();
				objectOutputStream.close();
				fileOutputStream.close();
			} catch (IOException ex) {
				System.out.println("[CentralControl.saveWorld()] exception: "
						+ ex);
			}
			System.out
					.println("[CentralControl.saveWorld()] World successfully saved in "
							+ filename + file.getName());
		}
	}
	/**
	 * Print the content of the simulation in outputs/output.txt Gilles
	 */
	public void printoutStats() {
		//System.out.println("[CentralControl.printoutStats()]");
		// Create the file handler outputs/output.txt
		//File file = new File(modulecoPathRoot + "outputs" + File.separator,
		//		"output.txt");
		// Build the content to store in the file
		/*
		 * int iteration = (eWorld.getStatManager()).getIteration();
		 * System.out.println("Iteration = " + iteration); double price =
		 * (eWorld.getStatManager()).get("price", iteration);
		 * System.out.println("price = " + price);
		 */
		//(eWorld.getStatManager()).saveLastTrace();
		//System.out.println("[CentralControl.printoutStats()] END");
	}
	/**
	 * **************** <br>
	 * Editors <br>
	 * **************** <br>
	 */
	/**
	 * Open a pop-up editor on agent(y,x), attached to ControlPanel.
	 * 
	 * Methode invoquée depuis grapheco.Canevas. This method invoke
	 * EAgent.get(index) which invoke by return EAgentEditor
	 */
	public void editAgent(int y, int x) {
		/**
		 * Compute the agentID
		 */
		int index = ((int) (y * Math.sqrt(eWorld.getAgentSetSize()))) + x;
		/**
		 * Build the pop-up menu.
		 */
		editAgent(index);
	}
	/**
	 * Alternate Agent Editor
	 * 
	 * @param index
	 */
	public void editAgent(int index) {
		editorManager.buildPopUpEditor((EAgent) (eWorld.get(index)),
				"Agent ID : " + index);
	}
	public void editMobileAgent(int index, CAgent mca) {
		editorManager.buildPopUpEditor(mca, "Agent ID : " + index);
	}
	/**
	 * Open a fixed editor on agent(index), attached to ControlPanel.
	 */
	public void editAgentLinks(int index) {
		editorManager.editAgentLinks((EAgent) (eWorld.get(index)),
				"Agent ID : " + index);
	}
	/**
	 * Choose an existing file on the file system.
	 * <p>
	 * DOES NOT WORK SO FAR
	 */
	public JFileChooser getJFileChooser() {
		/**
		 * Start by creating the fileChooser, if it does not already exist.
		 */
		if (fileChooser == null) {
			fileChooser = new JFileChooser(modulecoPathRoot + "outputs"
					+ File.separator);
			System.out.println("[CentralControl.getJFileChooser()] Created: "
					+ fileChooser.toString());
		}
		return fileChooser;
	}
	public void resetRepresentations() {
		resetImage();
	}
	public ArrayList getDescriptors_temp() {
		return previousDescriptors;
	}
	/**
	 * Create a new Trace and record the experience.
	 * 
	 * @param showGUI
	 *            true of we display the Record Window.
	 */
	public void recordExperience(boolean showGUI) {
		traceParameter = new TraceParameter(this, showGUI);
	}
	//modifier 18/04/02 CK
	public void stopCollectTrace() {
		(eWorld.getStatManager()).stopCollectTrace();
	}
	public EWorld getEWorld() {
		return this.eWorld;
	}
	// CK 23/07/2002
	public EWorld getEWorldOld() {
		return this.previousEworld;
	}
	public Hashtable getExtraAgentDescriptors_temp() {
		return this.previousExtraAgentDescriptors;
	}
	public void setExtraAgentDescriptors_temp(EWorld eWorld) {
		if (eWorld.getExtraAgents().length > 0) {
			CAgent[] extraAgents = eWorld.getExtraAgents();
			for (int i = 0; i < extraAgents.length; i++) {
				CAgent agent = extraAgents[i];
				this.previousExtraAgentDescriptors.put(new Integer(i), agent
						.getDescriptors());
			}
		}
	}
	/**
	 * Update the display of the step number in the GUI.
	 *  
	 */
	public void updateIter() {
		controlPanel.updateIter();
	}
	public void updateSize(Integer Size) {
		controlPanel.permanentControlPanelBar.textFieldSize
				.setType(ModulecoTextField.EVEN);
		controlPanel.permanentControlPanelBar.textFieldSize.setText(Size
				.toString());
	}
	//========Display Graphic Representation in
	// ePanel===================================
	public EPanel getEPanel() {
		return ePanel;
	}
	/**
	 * informe EPanel qu'une mise a jour doit être faite dans les options
	 * d'affichage DisplayWorldOption have changed méthode invoquée à
	 * l'initialisation par le constructeur et par ControlPanel lorsque
	 * checkMenuDisplayGraphic est activé
	 */
	// Modifie DP 12/07/2002
	public void displayWorldEditor(boolean display) {
		// Pour mémoire mais insiffisant pour déconnecter les calculs : REVOIR
		if (display) {
			this.editorManager.buildAdditionalPanels(eWorld, eWorld
					.getExtraAgents());
			editorManager
					.buildAdditionalPanels(eWorld, eWorld.getExtraAgents());
			//System.out.println("ePanel.addAdditionalPanel(eWorld.fixed_ae);");
			ePanel.validate();
		} else {
			//ePanel.removeAdditionalPanel(eWorld.fixed_ae);
			//System.out.println("ePanel.removeAdditionalPanel(eWorld.fixed_ae);");
			this.editorManager.worldFixed_ae = null;
			ePanel.validate();
		}
	}
	/*
	 * informe EPanel qu'une mise a jour doit être faite dans les options
	 * d'affichage Graphic = right representation DisplayGraphicOption have
	 * changed method invoqued à l'initialisation par le constructeur and by
	 * ControlPanel.itemStateChanged() as soon as checkMenuDisplayGraphic is
	 * activated :
	 */
	public void setDisplayGraphic() {
		//ePanel.setDisplayGraphic();
	}
	/*
	 * Central.Control.setDisplayXGraphics(boolean t) invoqued by
	 * ControlPanel.itemStateChanged() as soon as checkMenuDisplayGraphic is
	 * activated :
	 */
	protected void setDisplayXGraphics(boolean t) {
		//ePanel.setDisplayXGraphics(t);
	}
	protected boolean getDisplayXGraphicsEnabled() { // utile ?
		boolean test = false; //ePanel.getDisplayXGraphicsEnabled();
		return test;
	}
	/*
	 * CentralControl.updateMenuDisplayXGraphics() invoqued by :
	 * CentralControl.Create()
	 *  
	 */
	public void updateMenuDisplayXGraphics() { // rajouté DP 27/07/2002
		//System.out.println("CentralControl.updateMenuDisplayXGraphics
		// DEBUT");
		boolean displayGraphic = false; //Panel.getDisplayXGraphicsEnabled();
		controlPanel.setMenuDisplayXGraphicsEnabled(displayGraphic);
		//inform EPanel by the way of setDisplayXGraphics()
		//setDisplayXGraphics(controlPanel.checkMenuDisplayXGraphics.getState());//
		//System.out.println("CentralControl.updateMenuDisplayXGraphics
		// FIN");//+test
	}
	//=======================================//DP 20/08/2002
	public void updatePermanentControlPanelBar() { //DP 20/08/2002
		if (controlPanel.getPermanentControlPanelBar().buttonStop.isEnabled()) {
			controlPanel.getPermanentControlPanelBar().buttonCreate
					.setEnabled(true);
			controlPanel.getPermanentControlPanelBar().buttonStop
					.setEnabled(false);
			controlPanel.getPermanentControlPanelBar().buttonStart
					.setEnabled(true);
			controlPanel.getPermanentControlPanelBar().buttonMStep
					.setEnabled(true);
			System.out
					.println("CentralControl.updatePermanentControlPanelBar()");
		} else {
			controlPanel.getPermanentControlPanelBar().buttonCreate
					.setEnabled(false);
			controlPanel.getPermanentControlPanelBar().buttonStop
					.setEnabled(true);
			controlPanel.getPermanentControlPanelBar().buttonStart
					.setEnabled(false);
			controlPanel.getPermanentControlPanelBar().buttonMStep
					.setEnabled(false);
		}
	}
	//=============== Torus Versus Circle World ========================
	/*
	 * // invoked by method : EWorld.getPreferredStaticRepresentation()
	 */
	public boolean getDisplayWorldType() { //ajoute DP 12/07/2002
		//System.out.println("centralControl.getDisplayWorldType() -> Invoke
		// controlPanel");
		return controlPanel.checkMenuDisplayCircleWorld.getState();
	}
	public void inverseLeftRepresentation() {
		// Add DP 06/2002
		// invoked in CBufferedCanvas - by listener actionPerformed(ActionEvent
		// e)
		//if (e.getSource().equals(menuItemRepresentation)){
	}
	public Integer smallWorldHadHocInit(String model, int s) // ajouté DP le
	// 20/10/2002
	{
		//System.out.println("centralControl.smallWorldHadHocInit(-) -DEBUT");
		int size = s;
		if (size % 2 != 0) {
			size++;
			controlPanel.permanentControlPanelBar.textFieldSize
					.setType(ModulecoTextField.EVEN);
			controlPanel.permanentControlPanelBar.textFieldSize
					.setText((new Integer(size)).toString());
		}
		controlPanel.checkMenuDisplayCircleWorld.setState(true);
		controlPanel.checkMenuDisplayTorusWorld.setState(false);
		//controlPanel.setDisplayCircleWorld();
		ePanel.setDisplayCircleWorld();
		//System.out.println("centralControl.smallWorldHadHocInit(-) - FIN");
		return new Integer(size);
	}
	//===========
	/**
	public void buildAutorun() { //DP 20/08/2002
		autorun = new models.discreteChoice.Autorun(this);
		autorun2 = new models.smallWorld.Autorun(this);
		//autorun3 = new models.AReisiFT.Autorun(this);
		//System.out.println("CentralControl.buildAutorun(): new Autorun !");
	}
	
	// A ABSTRAIRE
	public models.discreteChoice.Autorun getAutorun() { //DP 20/08/2002
		return autorun;
	}
	public models.smallWorld.Autorun getAutorun2() { //DP 20/10/2002
		return autorun2;
	}
	**/
	/*
	 * public void setMenuBar() {
	 * (((JComponent)this.getParent()).getRootPane()).setJMenuBar(controlPanel.getMenuBar()); }
	 */
	public void setEPanel(EPanel ep) {
		this.ePanel = ep;
		ePanel.setControlPanel(controlPanel);
	}
	public void FillDesktop() {
		//System.out.println("CentralControl.FillDesktop()");
		if (modulecoBean.modulecoInternalPanel == null) {
			modulecoBean.FillDesktop();
			modulecoBean.getModulecoInternalPanel().BuildAgentRepresentations();
		}
	}
	/**
	 * **************** <br>
	 * SimulationControl manager <br>
	 * **************** <br>
	 */
	/**
	 * Start the simulation.
	 */
	public void simulationStart() {
		simulationControl.start();
	}
	/**
	 * Make the world evolve one step.
	 */
	public void simulationMstep() {
		simulationControl.progress();
	}
	/**
	 * Stop the simulation.
	 * <p>
	 * invoked by PermanentControlPanelBar.actionPerformed() Encapsulate
	 * simulationControl.stop(); stop the current simulation i.e; interrupt the
	 * Thread but the simulation is not closed.
	 */
	public void simulationStop() {
		if (eWorld != null)
			simulationControl.stop();
	}
	/**
	 * Terminate the current simulation. <br>
	 */
	public void simulationTerminate() {
		if (eWorld != null)
			simulationControl.terminate();
	}
	/**
	 * Reset the world. <br>
	 * invoked by controlPanel.DisableProcess()
	 */
	public void resetWorld() {
		worldEnabled = false;
		simulationTerminate();
		controlPanel.updateIter();
		resetRepresentations();
		displayWorldEditor(false);
	}
	/**
	 * Get the current step number.
	 */
	public int getIter() {
		if (simulationControl != null)
			return simulationControl.getIter();
		else
			return 0;
	}
	/**
	 * **************** <br>
	 * Accessors <br>
	 * **************** <br>
	 */
	public void setSimulationControl(SimulationControl sc) {
		this.simulationControl = sc;
	}
	public void setCAgent(EWorld ew) {
		super.setCAgent(ew);
		//System.out.println("[CentralControl.setCAgent()] ew is a "
		//		+ ew.getClass().getName());
		this.eWorld = ew; //utile ?
	}
	/**
	 * @return modulecoPathRoot, the local path to the root directory.
	 *         <p>
	 *         e.g. modulecoPathRoot = xxx/modulecoMK/
	 */
	public static String getModulecoPathRoot() {
		return modulecoPathRoot;
	}
	/**
	 * @return webRessources, the URL to find resources on the Web.
	 *         <p>
	 *         e.g. webResources = http://www.cs.man.ac.uk/ai/public/
	 */
	public static String getWebResources() {
		return webResources;
	}
	public void setGraphicsSelector(GraphicsSelector graphicsSelector) {
		this.graphicsSelector = graphicsSelector;
	}
	public GraphicsSelector getGraphicsSelector() {
		return graphicsSelector;
	}
	public ModulecoLauncher getModulecoLauncher() {
		return modulecoLauncher;
	}
	public ModelParameters getModelParameters() {
		return modelParameters;
	}
	public ControlPanel getControlPanel() {
		return controlPanel;
	}
	public void setCentralControl(CentralControl centralControl) {
	}
}
