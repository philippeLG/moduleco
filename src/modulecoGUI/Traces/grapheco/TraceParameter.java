/*
 * @(#)TraceParameter.java 1.1 10-Mar-04
 */
package modulecoGUI.Traces.grapheco;
import java.io.File;
import javax.swing.JFrame;
import javax.swing.JPanel;
import modulecoFramework.modeleco.EWorld;
import modulecoFramework.modeleco.WorldListener;
import modulecoFramework.modeleco.ModelParameters;
import modulecoGUI.grapheco.CentralControl;
import modulecoGUI.Traces.Experience;
/**
 * Record the data of the simulation into a file (CSV, TXT or DOC).
 * 
 * @author infolab
 * @author Gilles Daniel (gilles@cs.man.ac.uk)
 * @version 1.0, 22-APR-02
 * @version 1.1, 10-Mar-2004
 */
public class TraceParameter extends JFrame {
	protected WorldListener worldListener;
	protected ModelParameters modelParameters;
	protected CentralControl centralControl;
	protected ModelParameter modelParameter;
	protected DataFileParameter dataFileParameter;
	protected DescriptorParameter descriptorParameter;
	protected StepParameter stepParameter;
	protected AgentParameter agentParameter;
	protected Status status;
	protected ControlBar controlBar;
	/**
	 * Constructor called from CentralControl.
	 * 
	 * @param centralControl
	 * @param showGUI
	 *            true if we display the Record Window
	 */
	public TraceParameter(CentralControl centralControl, boolean showGUI) {
		super("Data");
		this.centralControl = centralControl;
		this.modelParameters = centralControl.getModelParameters();
		/**
		 * Build the different components.
		 */
		initComponents();
		setSize(640, 650);
		setLocation(0, 0);
		if (showGUI) {
			/**
			 * Display the Record Window
			 */
			show();
		} else
			saveSelectedParameters();
	}
	/**
	 * Constructor called from WorldListener.
	 * 
	 * @param worldListener
	 */
	public TraceParameter(WorldListener worldListener) {
		super("Data");
		this.worldListener = worldListener;
		this.modelParameters = worldListener.getModelParameters();
		/**
		 * Build the different components.
		 */
		//initComponents();
		dataFileParameter = new DataFileParameter(getDataFilePath());
		descriptorParameter = new DescriptorParameter(worldListener.getEWorld());
		agentParameter = new AgentParameter(worldListener.getEWorld());
		stepParameter = new StepParameter();
		/**
		 * Record everything
		 */
		record();
	}
	/**
	 * Build the GUI.
	 *  
	 */
	public void initComponents() {
		/**
		 * Description of the model.
		 */
		buildModelPanel();
		/**
		 * Path and name of the output file.
		 */
		buildDataFilePanel();
		/**
		 * Variables to record and constants.
		 */
		buildDescriptorsPanel();
		/**
		 * Right panel: steps
		 */
		buildStepPanel();
		/**
		 * Right panel: agents
		 */
		buildAgentPanel();
		/**
		 * Right panel: status (recording or not)
		 */
		//buildStatusPanel();
		/**
		 * Bottom buttons
		 */
		buildControlPanel();
	}
	/**
	 * Record data when there is a GUI.
	 *  
	 */
	public boolean saveSelectedParameters() {
		if (validateParameters() == true) {
			//controlBar.activate();
			/**
			 * Create a new experience.
			 */
			Experience experience = new Experience(modelParameter
					.getModelName(), modelParameter.getWorldSize(),
					modelParameter.getNeighbourName(), modelParameter
							.getZoneName(), modelParameter.getSchedulerType(),
					modelParameter.getExtraAgentCommit());
			/**
			 * Set the path to the output file and the format.
			 */
			experience.setDataFileParameters(dataFileParameter.getPathName(),
					dataFileParameter.getDataFormat());
			/**
			 * Set the constants and non-constants (i.e. variables) of the
			 * experience.
			 */
			initDescriptorsParameter(experience);
			/**
			 * Set the list of agents to record in this experience.
			 */
			initAgentParameter(experience);
			/**
			 * Set the iteration number to record.
			 */
			initStepParameter(experience);
			/**
			 * Get a reference to the current World.
			 */
			EWorld eWorld = centralControl.getEWorld();
			/**
			 * Enable the Print Stats submenu
			 */
			centralControl.controlPanel.menuItemPrintStats.setEnabled(true);
			/**
			 * Record data corresponding to this World and this Experience.
			 */
			eWorld.getStatManager().saveTraceParameter(eWorld, experience); //REVOIR
			//activateCollect();
			return true;
		} else {
			//controlBar.deactivate();
			return false;
		}
	}
	/**
	 * Record data when there is no GUI.
	 *  
	 */
	public boolean record() {
		/**
		 * Create a new experience. <br>
		 * WE SET THE LAST FLAG TO FALSE BY DEFAUT. WATCH OUT, IT MIGHT BE WRONG
		 * SOMETIMES.
		 */
		Experience experience = new Experience(modelParameters.getName(),
				modelParameters.getLength(),
				modelParameters.getNeighbourhood(), modelParameters.getZone(),
				modelParameters.getTimeScheduler(), false);
		/**
		 * Set the path to the output file and the format.
		 */
		experience.setDataFileParameters(dataFileParameter.getPathName(),
				dataFileParameter.getDataFormat());
		/**
		 * Set the constants and non-constants (i.e. variables) of the
		 * experience.
		 */
		initDescriptorsParameter(experience);
		/**
		 * Set the list of agents to record in this experience.
		 */
		initAgentParameter(experience);
		/**
		 * Set the iteration number to record.
		 */
		initStepParameter(experience);
		/**
		 * Get a reference to the current World.
		 */
		EWorld eWorld = worldListener.eWorld;
		/**
		 * Record data corresponding to this World and this Experience.
		 */
		eWorld.getStatManager().saveTraceParameter(eWorld, experience);
		return true;
	}
	/**
	 * Set the path and filename of the output file.
	 * <p>
	 * e.g. name =
	 * 
	 * @return pathname
	 */
	public String getDataFilePath() {
		/**
		 * Set the output filename.
		 * <p>
		 * e.g. name = GCMG
		 */
		String name = modelParameters.getName();
		/**
		 * Set the path to the output file.
		 * <p>
		 * e.g. directoryName = xxx/modulecoMK/outputs/GCMG
		 */
		//String directoryName = CentralControl.getModulecoPathRoot() +
		// "outputs"
		//		+ File.separator + name;
		String directoryName = System.getProperty("user.dir") + File.separator
				+ "outputs" + File.separator + name;
		/**
		 * Set the full path.
		 * <p>
		 * e.g. pathname = xxx/modulecoMK/outputs/GCMG/GCMG
		 */
		String pathname = directoryName + File.separator + name;
		return pathname;
	}
	/**
	 * Build modelParameter, i.e. the model name, neighbour, scheduler, etc.
	 * <p>
	 * modelParameter.getModelName() = "GCMG" <br>
	 * modelParameter.getWorldSize() = 5 <br>
	 * modelParameter.getNeighbourName() = "NeighbourVonNeuman" <br>
	 * modelParameter.getZoneName() = "NeighbourVonNeuman" <br>
	 * modelParameter.getSchedulerType() = "LateCommitScheduler" <br>
	 * modelParameter.getExtraAgentCommit() =
	 *  
	 */
	public void buildModelPanel() {
		getContentPane().setLayout(null);
		modelParameter = new ModelParameter(centralControl.controlPanel);
		JPanel modelPanel = modelParameter.getPanel();
		modelPanel.setBounds(0, 0, 630, 150);
		getContentPane().add(modelPanel);
	}
	/**
	 * Build the dataFileParameter, which sets the path to the output file and
	 * the format.
	 * <p>
	 * dataFileParameter.getPathName() = "xxx/modulecoMK/outputs/GCMG/GCMG.csv"
	 * <br>
	 * dataFileParameter.getDataFormat() = "CSV"
	 *  
	 */
	public void buildDataFilePanel() {
		dataFileParameter = new DataFileParameter(getDataFilePath());
		JPanel dataFilePanel = dataFileParameter.getPanel();
		dataFilePanel.setBounds(0, 150, 630, 120);
		getContentPane().add(dataFilePanel);
	}
	public void buildDescriptorsPanel() {
		descriptorParameter = new DescriptorParameter(centralControl.getEWorld());
		JPanel descriptorPanel = descriptorParameter.getPanel();
		descriptorPanel.setBounds(0, 270, 440, 300);
		getContentPane().add(descriptorPanel);
	}
	public void buildStepPanel() {
		stepParameter = new StepParameter();
		JPanel stepPanel = stepParameter.getPanel();
		stepPanel.setBounds(440, 270, 190, 150);
		getContentPane().add(stepPanel);
	}
	public void buildAgentPanel() {
		agentParameter = new AgentParameter(centralControl.getEWorld());
		JPanel agentPanel = agentParameter.getPanel();
		agentPanel.setBounds(440, 420, 190, 100);
		getContentPane().add(agentPanel);
	}
	public void buildStatusPanel() {
		status = new Status();
		JPanel statusPanel = status.getPanel();
		statusPanel.setBounds(440, 520, 190, 50);
		getContentPane().add(statusPanel);
	}
	public void buildControlPanel() {
		controlBar = new ControlBar(this);
		JPanel controlBarPanel = controlBar.getPanel();
		controlBarPanel.setBounds(160, 580, 165, 31);
		getContentPane().add(controlBarPanel);
	}
	public void refresh(CentralControl centralControl) {
		this.centralControl = centralControl;
		modelParameter.refresh(centralControl.controlPanel);
		dataFileParameter.refresh(getDataFilePath());
		descriptorParameter.refresh(centralControl.getEWorld());
		stepParameter.refresh();
		agentParameter.refresh(centralControl.getEWorld());
		//status.refresh();
		//controlBar.refresh();
	}
	/**
	 * Set the constants and non-constants (i.e. variables) of the experience
	 * for the Agents, Extra-Agents and the World.
	 * 
	 * @param experience
	 */
	public void initDescriptorsParameter(Experience experience) {
		/**
		 * Set the Agents constants and non-constants.
		 */
		experience.setANames(descriptorParameter.getASelectedConstants(),
				descriptorParameter.getASelectedVars());
		/**
		 * Set the Extra-Agents constants and non-constants.
		 */
		experience.setEANames(descriptorParameter.getEASelectedConstants(),
				descriptorParameter.getEASelectedVars());
		/**
		 * Set the World constants and non-constants.
		 */
		experience.setWNames(descriptorParameter.getWSelectedConstants(),
				descriptorParameter.getWSelectedVars());
	}
	/**
	 * Set the iteration number to record.
	 * 
	 * @param experience
	 */
	public void initStepParameter(Experience experience) {
		int n = stepParameter.getStepNumber();
		if (n != -1)
			experience.setStepNumber(n);
		if (n == 3)
			experience.setStepByX(stepParameter.getStepByX());
		if (n == 4)
			experience.setStep(stepParameter.getStepInit(), stepParameter
					.getStepEnd());
		if (n == 5) {
			experience.setStep(stepParameter.getStepInit(), stepParameter
					.getStepEnd());
			experience.setStepByX(stepParameter.getStepByX());
		}
	}
	/**
	 * Set the list of agents to record in this experience.
	 * 
	 * @param experience
	 */
	public void initAgentParameter(Experience experience) {
		if (agentParameter.getSelectedAgents().size() > 0)
			experience.setAgentIds(agentParameter.getSelectedAgents());
		else
			experience.setAllAgents(agentParameter.getAllAgent());
	}
	public boolean validateParameters() {
		if (!this.descriptorParameter.descriptorsValidate()) {
			javax.swing.JOptionPane.showMessageDialog(this,
					"Select the variables to record");
			return false;
		}
		if (!this.agentParameter.agentsValidate()) {
			javax.swing.JOptionPane.showMessageDialog(this,
					"Select the agents to record");
			return false;
		}
		if (!this.stepParameter.stepsValidate()) {
			javax.swing.JOptionPane
					.showMessageDialog(this,
							"Select the recording iteration type. \nPlease use integers for text areas.");
			return false;
		}
		if (!this.dataFileParameter.dataFilePathValidate()) {
			javax.swing.JOptionPane.showMessageDialog(this,
					"Sorry, the path you specified cannot be found");
			this.dataFileParameter.resetFilePath();
			return false;
		}
		return true;
	}
	//public void activateCollect() {
	//	status.setActivate();
	//}
	//public void deactivateCollect() {
	//	status.setDeactivate();
	//}
	public void stopCollect() {
		EWorld eWorld = worldListener.getEWorld();
		eWorld.getStatManager().stopCollectTrace();
		/**
		 * Unselect the Record submenu
		 */
		centralControl.controlPanel.checkMenuRecordQuick.setSelected(false);
		centralControl.controlPanel.checkMenuRecord.setSelected(false);
	}
}