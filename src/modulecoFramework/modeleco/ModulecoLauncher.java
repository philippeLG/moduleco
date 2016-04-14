/**
 * ModulecoLauncher.java
 * Copyright (c)enst-bretagne
 * @author denis.phan@enst_bretagne.fr
 * @version 1.0 February 2004
 */
package modulecoFramework.modeleco;
import java.lang.reflect.Constructor;
// MadKit
//import modulecoFramework.ModulecoAgent;
//MadKit
import modulecoGUI.grapheco.CentralControl;
/**
 * Launch the world. <br>
 * The option with no GUI is usefull in case we want to use the command-line.
 */
public class ModulecoLauncher {
	/**
	 * The CentralControl is used with the ControlPanel to manage the GUI.
	 */
	protected CentralControl centralControl;
	/**
	 * The SimulationControl runs the simulation by managing the time scheduler.
	 */
	protected SimulationControl simulationControl;
	/**
	 * The World.
	 */
	protected EWorld eWorld;
	/**
	 * The model. <br>
	 * e.g. model = GCMG
	 */
	protected String model;
	/**
	 * Parameters of the model.
	 */
	protected ModelParameters modelParameters;
	/**
	 * Listen to the world during a simultion.
	 */
	protected WorldListener worldListener;
	/**
	 * Constructor with no GUI, called from modulecoFramework.Core
	 */
	public ModulecoLauncher() {
		//System.out.println("[ModulecoLauncher()] with no GUI");
	}
	/**
	 * Constructor with GUI, called from modulecoGUI.grapheco.CentralControl
	 * <br>
	 * We now have a reference to the CentralControl.
	 */
	public ModulecoLauncher(CentralControl centralControl) {
		this.centralControl = centralControl;
		//System.out.println("[ModulecoLauncher()] with GUI");
	}
	/**
	 * Create a world from the model name and eventually the parameters provided
	 * (usually by the GUI). <br>
	 * We provide parameters only when we re-create the same world. If we don't,
	 * parameters will be read in the World class by using reflection.
	 * 
	 * @param modelName
	 *            name of the model
	 * @param parameters
	 *            of the model (null if we create this model for the first time)
	 */
	public void create(String modelName, ModelParameters parameters) {
		/**
		 * This constructor will be used to create an instance of the World.
		 */
		Constructor worldConstructor;
		/**
		 * The fact that parameters are provided means that we are re-creating
		 * the same world. As a consequence, we should keep the values of the
		 * previous descriptors.
		 */
		Boolean forgetDescriptors = new Boolean((parameters == null
				? true
				: false));
		/**
		 * We use reflection to create the World.
		 */
		try {
			/**
			 * Return the world class
			 */
			Class worldClass = Class.forName("models." + modelName + ".World");
			/**
			 * Return the world class constructor
			 */
			Class worldConstructorParameterTypes[] = new Class[]{int.class};
			worldConstructor = worldClass
					.getConstructor(worldConstructorParameterTypes);
			/**
			 * If no parameters are provided, i.e. if this world is new, use
			 * reflection to get the initial parameters of the model.
			 */
			if (parameters == null) {
				/**
				 * Create the ModelParameters containing the initilisation
				 * values of this World. <br>
				 * Use reflection to get the values in the World Class.
				 * 
				 * length; <br>
				 * neighbourHood; <br>
				 * zone; <br>
				 * timeScheduler; <br>
				 */
				modelParameters = new ModelParameters(modelName, (String) worldClass
						.getDeclaredField("initLength").get(null),
						(String) worldClass.getDeclaredField("initNeighbour")
								.get(null), (String) worldClass
								.getDeclaredField("initZone").get(null),
						(String) worldClass.getDeclaredField("initScheduler")
								.get(null));
			}
			/**
			 * Get the initial parameters of the model: length, neighbour, zone
			 * and scheduler.
			 */
			int length = modelParameters.getLength();
			String neighbour = (String) modelParameters.getNeighbourhood();
			String zone = (String) modelParameters.getZone();
			String scheduler = (String) modelParameters.getTimeScheduler();
			//System.out
			//		.println("[ModulecoLauncher.create()] (length, neighbour, zone,
			// scheduler) = ("
			//				+ length
			//				+ ", "
			//				+ neighbour
			//				+ ", "
			//				+ zone
			//				+ ", "
			//				+ scheduler + ")");
			/**
			 * Path to the appropriate neigbour, zone and scheduler classes.
			 * <br>
			 * e.g. zsClass = VonNeuman <br>
			 * tsClass = LateCommitScheduler
			 */
			String nsClass = "modulecoFramework.modeleco.zone." + neighbour;
			String zsClass = "modulecoFramework.modeleco.zone." + zone;
			String tsClass = "modulecoFramework.modeleco.scheduler."
					+ scheduler;
			/**
			 * Create the new world
			 */
			Object worldConstructorParameters[] = new Object[]{new Integer(
					length)};
			eWorld = (EWorld) worldConstructor
					.newInstance(worldConstructorParameters);
			/**
			 * Create the world listener.
			 */
			createWorldListener(modelParameters, forgetDescriptors, neighbour,
					length);
			/**
			 * Launch the world.
			 */
			// MadKit
			//if (worldListener.isWithGUI()) {
			//	ModulecoAgent.launchWorld(eWorld);
			//}
			// MadKit
			/**
			 * Populate all the agents.
			 */
			agentsLauncher(eWorld, nsClass);
			/**
			 * Create the SimulationControl.
			 */
			simulationControl = new SimulationControl(eWorld, worldListener,
					tsClass, zsClass);
			simulationControl.buildScheduler();
			simulationControl.initSimulation();
		} catch (Exception e) {
			System.out.println("[ModulecoLauncher.create()] Exception: "
					+ e.toString());
			e.printStackTrace();
		}
	}
	/**
	 * Create the WorldListener and eventually link it to the CentralControl, if
	 * there is a GUI.
	 * 
	 * @param forgetDescriptors
	 * @param neighbour
	 * @param length
	 */
	public void createWorldListener(ModelParameters modelParameters,
			Boolean forgetDescriptors, String neighbour, int length) {
		/**
		 * Create the world listener.
		 */
		if (centralControl != null) {
			//System.out
			//		.println("[ModulecoLauncher.create()] Creating WordListener with GUI");
			/**
			 * If there is a GUI, link the WorldListener with the
			 * CentralControl.
			 */
			worldListener = new WorldListener(eWorld, modelParameters, centralControl);
		} else {
			//System.out
			//		.println("[ModulecoLauncher.create()] Creating WorldListener with no GUI.");
			/**
			 * If there is no GUI, just create the WorldListener.
			 */
			worldListener = new WorldListener(eWorld, modelParameters);
		}
		eWorld.setWorldListener(worldListener);
		/**
		 * Build the descriptors with or without the previous values.
		 */
		eWorld.buildDescriptors(forgetDescriptors);
		/**
		 * Hack for the specific neighbourhood "RandomPairwise"
		 */
		if ((neighbour == "RandomPairwise") && (length % 2 != 0)) {
			length = length + 1;
			if (centralControl != null)
				centralControl.updateSize(new Integer(length));
		}
	}
	/**
	 * Create Agents, Mediums and Extra-Agents
	 * 
	 * @param world
	 *            the world
	 * @param nsClass
	 *            the neighbourhood type
	 */
	public void agentsLauncher(EWorld eWorld, String nsClass) {
		if (eWorld != null) {
			eWorld.populateAll(nsClass);
		}
	}
	/**
	 * @return Returns the eWorld.
	 */
	public EWorld getEWorld() {
		return eWorld;
	}
	/**
	 * @return Returns the simulationControl.
	 */
	public SimulationControl getSimulationControl() {
		return simulationControl;
	}
	/**
	 * @return Returns the modelParameters.
	 */
	public ModelParameters getModelParameters() {
		return modelParameters;
	}
}