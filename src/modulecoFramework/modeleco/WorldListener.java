/**
 * WorldListener.java
 * Copyright (c)enst-bretagne
 * @author denis.phan@enst_bretagne.fr
 * @version 1.0  May 2004
 */
package modulecoFramework.modeleco;
import java.util.ArrayList;
import java.util.Hashtable;
import modulecoGUI.grapheco.CentralControl;
import modulecoGUI.Traces.grapheco.TraceParameter;
/**
 * Manage the World communications with external control. The GUI, when there is
 * one, is managed by the CentralControl
 */
public class WorldListener {
	/**
	 * The CentralControl manages the GUI.
	 */
	public CentralControl centralControl;
	/**
	 * Parameters of the model.
	 */
	protected ModelParameters modelParameters;
	/**
	 * True if there is a GUI, i.e. a CentralControl.
	 */
	protected boolean withGUI = false;
	/**
	 * The World.
	 */
	public EWorld eWorld;
	/**
	 * True if we record the experience.
	 */
	protected boolean recording = false;
	/**
	 * Constructor with no GUI.
	 * 
	 * @param eWorld
	 */
	public WorldListener(EWorld eWorld, ModelParameters modelParameters) {
		this.eWorld = eWorld;
		this.modelParameters = modelParameters;
	}
	/**
	 * Constructor with GUI.
	 * 
	 * @param eWorld
	 * @param centralControl
	 */
	public WorldListener(EWorld eWorld, ModelParameters modelParameters,
			CentralControl centralControl) {
		this.eWorld = eWorld;
		this.modelParameters = modelParameters;
		this.centralControl = centralControl;
		withGUI = true;
	}
	/**
	 * Once the step number has been updated by the SimulationControl, update
	 * its display in the GUI.
	 */
	public void updateIter() {
		if (withGUI)
			centralControl.updateIter();
		//else
		//System.out
		//		.println("[WorldListener.updateIter()] CentralControl = null");
	}
	/**
	 * Update the representation of the world in the GUI.
	 */
	public void updateImage() {
		if (withGUI)
			centralControl.updateImage();
		//else
		//System.out
		//		.println("[WorldListener.updateImage()] CentralControl = null");
	}
	/**
	 * Create a new Trace and record the experience.
	 * 
	 * @param showGUI,
	 *            true if we show the Record Window, false if we record all the
	 *            experience.
	 */
	public void recordExperience() {
		/**
		 * Set the recording flag to true.
		 */
		recording = true;
		/**
		 * Create the Trace.
		 */
		TraceParameter traceParameter = new TraceParameter(this);
	}
	public boolean statManagerEnabled() {
		if (withGUI) {
			if (centralControl.statManagerEnabled)
				return true;
			else
				return false;
		} else
			return false;
	}
	public void restart() {
		centralControl.getEPanel().getControlPanel().CreateWorld();
		centralControl.getEPanel().getControlPanel()
				.getPermanentControlPanelBar().buttonCreate.setEnabled(true);
		centralControl.getEPanel().getControlPanel()
				.getPermanentControlPanelBar().buttonStop.setEnabled(false);
		centralControl.getEPanel().getControlPanel()
				.getPermanentControlPanelBar().buttonStart.setEnabled(true);
		centralControl.getEPanel().getControlPanel()
				.getPermanentControlPanelBar().buttonMStep.setEnabled(true);
	}
	public void updateStopButton(boolean state) {
		if (state) {
			//			verifier si utile !
			centralControl.getEPanel().getControlPanel()
					.getPermanentControlPanelBar().buttonStop.setEnabled(state);
		} else {
			centralControl.getEPanel().getControlPanel()
					.getPermanentControlPanelBar().buttonCreate
					.setEnabled(true);
			centralControl.getEPanel().getControlPanel()
					.getPermanentControlPanelBar().buttonStop.setEnabled(state);
			centralControl.getEPanel().getControlPanel()
					.getPermanentControlPanelBar().buttonStart
					.setEnabled(false);
			centralControl.getEPanel().getControlPanel()
					.getPermanentControlPanelBar().buttonMStep.setEnabled(true);
		}
	}
	/**
	 * fields descriptors (probe) update values the ArrayList of descriptors for
	 * all CAgents
	 * 
	 * @see modulecoGUI.editorManager.setDataToInterface()
	 */
	public void setDataToInterface() {
		//		Activated at each step from SimulationControl
		if (withGUI) {
			centralControl.editorManager.setDataToInterface();
		}
	}
	public void terminate() {
		if (withGUI) {
			centralControl.editorManager.ClosePopUp();
			centralControl.editorManager.CloseFAEList();
		}
	}
	/**
	 * ######### <br>
	 * Accessors <br>
	 * #########
	 */
	public boolean getDisplayWorldType() {
		if (withGUI) {
			if (centralControl.getDisplayWorldType())
				return true;
			else
				return false;
		} else
			return false;
	}
	public String getNeighbourhood() {
		if (withGUI) {
			return centralControl.controlPanel.getNeighbourSelected();
		} else
			return modelParameters.getNeighbourhood();
	}
	public ArrayList getDescriptors_temp() {
		//if (centralControl != null) {
		return centralControl.getDescriptors_temp();
	}
	/*
	 * public void closedAE() { if (centralControl != null) if
	 * (centralControl.editorManager.worldAEexist())
	 * centralControl.editorManager.ClosePopUp(); }
	 */
	public CAgent[] returnOldExtraAgents() {
		return this.centralControl.getEWorldOld().getExtraAgents();
	}
	public Hashtable getExtraAgentDescriptors_temp() {
		return centralControl.getExtraAgentDescriptors_temp();
	}
	/**
	 * @return Returns the withGUI.
	 */
	public boolean isWithGUI() {
		return withGUI;
	}
	/**
	 * @return Returns the centralControl.
	 */
	public CentralControl getCentralControl() {
		return centralControl;
	}
	/**
	 * @return Returns the eWorld.
	 */
	public EWorld getEWorld() {
		return eWorld;
	}
	/**
	 * @return Returns the modelParameters.
	 */
	public ModelParameters getModelParameters() {
		return modelParameters;
	}
	/**
	 * @return Returns the recording.
	 */
	public boolean isRecording() {
		if (withGUI)
			return centralControl.statManagerEnabled;
		else
			return recording;
	}
}