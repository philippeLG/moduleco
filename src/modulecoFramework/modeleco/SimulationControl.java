/**
 * modulecoFramework.modeleco.SimulationControl.java
 * Copyright (c)enst-bretagne
 * @author denis.phan@enst_bretagne.fr
 * @version 1.0 August 2000
 */
package modulecoFramework.modeleco;
/**
 * Run the simulation by managing the time scheduler.
 */
public class SimulationControl implements Runnable {
	transient protected Thread t;
	transient protected boolean runnable = false;
	/**
	 * The world.
	 */
	protected EWorld eWorld;
	/**
	 * The TimeScheduler ts is the evolution strategy adopted by agents. <br>
	 * e.g. Early or Late Commit.
	 */
	protected TimeScheduler ts;
	/**
	 * The ZoneSelector zs describes the evolving zone of agents. <br>
	 * e.g. Moore
	 */
	protected ZoneSelector zs;
	/**
	 * The time step.
	 */
	protected int iter = 0;
	/**
	 * True if the
	 */
	protected boolean withGUI = false;
	/**
	 * The name of the classes chosen as TimeScheduler and ZoneSelector. Used to
	 * create a newInstance()
	 */
	public String tsClass, zsClass;
	/**
	 * A WorldListener to interact between the World and the CentralControl,
	 * i.e. between the Framework and the GUI.
	 */
	public WorldListener worldListener;
	/**
	 * Simple constructor.
	 *  
	 */
	public SimulationControl() {
	}
	/**
	 * Alternative constructor.
	 * 
	 * @param eWorld,
	 *            the world
	 * @param tsClass,
	 *            the class name of the time scheduler chosen
	 * @param zsClass,
	 *            the class name of the zone selector
	 */
	public SimulationControl(EWorld eWorld, WorldListener worldListener, String tsClass, String zsClass) {
		this.eWorld = eWorld;
		this.worldListener = worldListener;
		this.tsClass = tsClass;
		this.zsClass = zsClass;
		//buildScheduler(); invoked from ModulecoLauncher
	}
	/**
	 * Build the TimeScheduler and ZoneSelector.
	 */
	public void buildScheduler() {
		eWorld.setSimulationControl(this);
		//System.out.println("SimulationControl.BuildScheduler");
		try {
			/**
			 * Define selector and scheduler after populateAll(nsClass) since
			 * some lists of agents need the population to be created.
			 */
			zs = (ZoneSelector) Class.forName(zsClass).newInstance();
			zs.setWorld(eWorld);
			ts = (TimeScheduler) Class.forName(tsClass).newInstance();
			ts.setZoneSelector(zs);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * Initialise the simulation.
	 *  
	 */
	public void initSimulation() {
		//System.out.println("SimulationControl.initSimulation()");
		/**
		 * Connect Agents to mediums.
		 */
		eWorld.connectAll();
		/**
		 * Initialize the agents, the mediums and finally the world.
		 */
		eWorld.initAll();
		/**
		 * Compute the initial global state.
		 */
		eWorld.compute();
	}
	/**
	 * Make the world evolve one step. <br>
	 * Invoked by centralControl.simulationMstep()
	 * 
	 * @see modulecoGUI.grapheco.CentralControl.simulationMstep()
	 */
	public synchronized void progress() {
		//System.out.println("SimulationControl.progres()");
		/**
		 * Make the agents evolve one step.
		 */
		ts.step();
		/**
		 * Commit the world changes. <br>
		 * Recalcule le monde, DP 05/08/2002
		 */
		eWorld.commitAll();
		/**
		 * Update the GUI so that the descriptors reflect the new state of the
		 * world.
		 */
		worldListener.setDataToInterface();
		worldListener.updateImage();
		/**
		 * Increment the time step.
		 */
		nextIter();
	}
	/**
	 * Increments the time step.
	 */
	public void nextIter() {
		//System.out.println("SimulationControl.nextIter() ");
		iter++;
		if (worldListener != null)
			worldListener.updateIter();
		//else
		//System.out.println("SimulationControl : worldListener = null !");
	}
	/**
	 * Manage the simulation thread.
	 */
	public synchronized void run() {
		Thread thisThread = Thread.currentThread();
		while (t == thisThread) {
			while (!runnable) {
				try {
					wait(25L);
				} catch (InterruptedException e) {
				}
			}
			/**
			 * Make the world progress one step.
			 */
			progress();
		}
	}
	/**
	 * Start the simulation.
	 */
	public synchronized void start() {
		//System.out.println("SimulationControl.start()");
		//setDataToInterface();
		if (t == null) {
			t = new Thread(this); // eWorld
			runnable = true;
			t.setPriority(Thread.MIN_PRIORITY);
			t.start();
		} else {
			runnable = true;
			notify();
		}
	}
	/**
	 * Interrupt the Thread but the simulation is not closed(). <br>
	 * Invoked by PermanentControlPanelBar.actionPerformed() by encapsulation
	 * trough CentralControl.simulationStop()
	 * 
	 * @see modulecoGUI.grapheco.CentralControl.simulationStop()
	 * @see modulecoGUI.grapheco.PermanentControlPanelBar.actionPerformed()
	 */
	public void stop() {
		if (t != null)
			t.interrupt();
		eWorld.getStatManager().saveLastTrace();
		//System.out.println("SimulationControl.stop()");
		runnable = false;
	}
	/**
	 * Close the current simulation. <br>
	 * Invoked by modulecoGUI.grapheco.CentralControl.resetWorld() by
	 * encapsulation trough CentralControl.simulationTerminate()
	 * 
	 * @see modulecoGUI.grapheco.CentralControl.simulationTerminate()
	 * @see modulecoGUI.grapheco.CentralControl.resetWorld()
	 */
	public void terminate() {
		eWorld.terminate();
		if (t != null) {
			t.interrupt();
			runnable = false;
			t = null;
		}
	}
	/**
	 * ######### <br>
	 * Accessors <br>
	 * #########
	 */
	public Thread getThread() {
		return t;
	}
	/**
	 * Get the time step.
	 * @return iter
	 */
	public int getIter() {
		return iter;
	}
}