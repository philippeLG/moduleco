/**
 * CAutorun.java
 * Copyright (c) The University of Manchester
 * @author gilles.daniel@cs.man.ac.uk
 * @version 0.1 June 2004
 */
package modulecoFramework.modeleco;
/**
 * Interface for the Autorun classes, used in every model to run simulations in
 * standalone.
 *  
 */
public interface CAutorun {
	/**
	 * Run the simulation. <br>
	 * This should be self-sufficient, i.e. should start, manage and stop the
	 * simulation.
	 */
	public void run();
	/**
	 * Set the reference to the SimulationControl.
	 * 
	 * @param simulationControl
	 */
	public void setSimulationControl(SimulationControl simulationControl);
}