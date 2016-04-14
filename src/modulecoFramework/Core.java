/**
 * ModulecoLauncher.java
 * Copyright (c) The University of Manchester
 * @author gilles.daniel@cs.man.ac.uk
 * @version 0.1 June 2004
 */
package modulecoFramework;
import modulecoFramework.utils.ExistingModels;
import modulecoFramework.modeleco.EWorld;
import modulecoFramework.modeleco.CAutorun;
import modulecoFramework.modeleco.ModulecoLauncher;
import modulecoFramework.modeleco.SimulationControl;
import modulecoGUI.GUI;
/**
 * The core of Moduleco. This is where we decide if there will be a GUI or not.
 */
public class Core {
	/**
	 * Launch the world.
	 */
	protected ModulecoLauncher modulecoLauncher;
	/**
	 * Manage the simulation.
	 */
	protected SimulationControl simulationControl;
	/**
	 * The GUI.
	 */
	protected GUI gui;
	/**
	 * List of existing models.
	 */
	protected static ExistingModels modelsList;
	protected static boolean withGUI = true;
	protected static String model = null;
	protected static int noSimulations = 1;
	/**
	 * Core of the application.
	 * 
	 * @param withGUI
	 *            true if there is a GUI
	 * @param model
	 *            the name of model to launch initially
	 */
	//public Core(boolean withGUI, String model, ModulecoAgent modulecoAgent) {
	public Core(boolean withGUI, String model) {
		/**
		 * Print out the copyright message.
		 */
		/*
		 * System.out.println("Moduleco\nBeta version 1.5 - 19/04/2004");
		 * System.out .println("Created by Antoine Beugnard & Denis Phan -
		 * ENST-Bretagne"); System.out.println("Maintained by Gilles Daniel &
		 * Denis Phan"); System.out.println("Adapted to MadKit by Jacques
		 * Ferber"); System.out .println("Please update this version by download
		 * the latest version at:");
		 * System.out.println("http://www.cs.man.ac.uk/ai/public/moduleco");
		 * System.out.println("http://www-eco.enst-bretagne.fr/~phan/moduleco");
		 */
		//System.out.println("[Core()] (model, withGUI) = (" + model + ", "
		//		+ withGUI + ")");
		/**
		 * If a GUI is requested, create and launch it. <br>
		 * Else launch the model.
		 */
		if (withGUI) {
			/**
			 * Create the GUI. The GUI will create the modulecoLauncher, with a
			 * link to the CentralControl.
			 */
			gui = new GUI();
			/**
			 * Get the reference to the ModulecoLauncher
			 */
			//modulecoLauncher = gui.getModulecoLauncher();
			/**
			 * Create the model.
			 */
			//modulecoLauncher.create("GCMG", null);
		} else {
			/**
			 * Create the ModulecoLauncher with no GUI
			 */
			modulecoLauncher = new ModulecoLauncher();
			/**
			 * Number of simulations to run. <br>
			 * One simulation has to terminate before the next one starts.
			 */
			for (int i = 0; i < noSimulations; i++) {
				/**
				 * Create the model with no initial parameters. They will be
				 * taken from the World directly.
				 */
				modulecoLauncher.create(model, null);
				/**
				 * Get the World.
				 */
				EWorld eWorld = modulecoLauncher.getEWorld();
				/**
				 * Get the Simulation Control.
				 */
				simulationControl = modulecoLauncher.getSimulationControl();
				/**
				 * Create the Autorun, provide the reference to the
				 * SimulationControl and start.
				 */
				CAutorun autorun = eWorld.createAutorun();
				/**
				 * If there is an Autorun class in this model, run it.
				 */
				if (autorun != null) {
					autorun.setSimulationControl(simulationControl);
					autorun.run();
				}
				/**
				 * Else simply launch the simulation. <br>
				 * BE CAREFUL: out of any manegement, the simulation might never
				 * terminate.
				 */
				else {
					System.out
							.println("BE CAREFUL: there is no Autorun in the model you have launched. You will need to add a class Autorun in your model if you want to launch simulations without a GUI. Please see the model example /models/GCMG.");
					//		System.out
					//		.println("BE CAREFUL: there is no Autorun in the model
					// you have launched.\nThe simulation has now started, but
					// might never terminate.");
					//simulationControl.start();
				}
			}
		}
	}
	/**
	 * Print out the usage of the command-line arguments.
	 */
	private static void doUsage() {
		int noModels = modelsList.getNoModels();
		int noModelsPerLine = 4;
		String modelsString = "\t\t\t";
		int tmp = 0;
		for (int i = 0; i < noModels; i++) {
			modelsString += modelsList.getName(i) + ", ";
			tmp = tmp + 1;
			if (tmp >= noModelsPerLine) {
				modelsString += "\n\t\t\t";
				tmp = 0;
			}
		}
		/**
		 * Print out the usage.
		 */
		System.out.println("USAGE\n\tmoduleco [--noGUI model] [noSimulations]");
		System.out
				.println("WHERE\n\t--noGUI model\tlets you run Moduleco in command-line, without Graphical Interface.");
		System.out
				.println("\tnoSimulations\tis the number of simulations to launch consecutively.");
		System.out.println("\nMODELS AVAILABLE:");
		System.out.println(modelsString + "\n");
	}
	/**
	 * Main method.
	 * <p>
	 * 
	 * USAGE <br>
	 * Core [OPTIONS] [MODEL] <br>
	 * OPTIONS <br>
	 * -n, --noGUI Run Moduleco in comand-line. The GUI is enabled by default.
	 * MODEL <br>
	 * model Name of the model to launch. <br>
	 */
	public static void main(String args[]) {
		/**
		 * Create the list of existing models.
		 */
		modelsList = new ExistingModels();
		/**
		 * Parse the arguments.
		 */
		if (args.length > 0) {
			/**
			 * There are some arguments
			 */
			withGUI = false;
			/**
			 * The first argument must be -n or --noGUI
			 */
			if (args[0].startsWith("-n") || args[0].startsWith("--noGUI")) {
				/**
				 * A second argument is provided. It should be a model name.
				 */
				if (args.length > 1) {
					/**
					 * Check if the model name provided is valid.
					 */
					if (modelsList.exists(args[1])) {
						model = args[1];
						/**
						 * Set the number of consecutive simulations to run, if
						 * it's provided.
						 */
						if (args.length > 2)
							noSimulations = Integer.parseInt(args[2]);
					}
					/**
					 * The model name provided is not valid
					 */
					else {
						System.out.println("\n" + args[1]
								+ " is not a valid model name.\n");
						doUsage();
						System.exit(0);
					}
					/**
					 * Only one argument was provided
					 */
				} else {
					System.out.println("\nPlease provide a model name.\n");
					doUsage();
					System.exit(0);
				}
				/**
				 * The first argument is neither --noGUI nor -n
				 */
			} else {
				System.out
						.println("\n" + args[0] + " is not a valid option.\n");
				doUsage();
				System.exit(0);
			}
		}
		/**
		 * Create the Core application.
		 */
		Core core = new Core(withGUI, model);
	}
}