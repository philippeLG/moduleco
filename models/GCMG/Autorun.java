/**
 * Autorun.java
 * Copyright (c) The University of Manchester
 * @author gilles.daniel@cs.man.ac.uk
 * @version 0.1 June 2004
 */
package models.GCMG;
import modulecoFramework.modeleco.CAutorun;
import modulecoFramework.modeleco.EWorld;
import modulecoFramework.modeleco.SimulationControl;
/**
 * Run a standalone simulation, with no help from a GUI. The Autorun is aware of
 * the existence of both the World and the SimulationControl.
 *  
 */
public class Autorun implements CAutorun {
	/**
	 * The World.
	 */
	protected World world;
	/**
	 * The SimulationControl.
	 */
	protected SimulationControl simulationControl;
	/**
	 * The Autorun is created from the World, and is given a reference to the
	 * SimulationControl later, from the Core.
	 * 
	 * @param eWorld
	 */
	public Autorun(EWorld eWorld) {
		this.world = (World) eWorld;
		//System.out.println(world.toString());
	}
	/**
	 * Run the simulation.
	 *  
	 */
	public void run() {
		/**
		 * Define the number of steps to run in the simulation. The simulation
		 * will terminate at the end.
		 */
		int noSteps = 50;
		/**
		 * Record the simulation results
		 */
		//world.worldListener.recordExperience();
		/**
		 * Run the simulation. Once this is over, the application terminates.
		 */
		for (int i = 0; i < noSteps; i++) {
			simulationControl.progress();
			//System.out.println("[Autorun.run()] time step = "
			//		+ simulationControl.getIter());
		}
	}
	/**
	 * toString() method.
	 * 
	 * @return
	 */
	public String soString() {
		return "[GCMG.Autorun()] Hello World";
	}
	/**
	 * @param simulationControl
	 *            The simulationControl to set.
	 */
	public void setSimulationControl(SimulationControl simulationControl) {
		this.simulationControl = simulationControl;
	}
}