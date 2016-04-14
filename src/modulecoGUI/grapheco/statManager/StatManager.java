/**
 * StatManager.java
 * Copyright (c)enst-bretagne
 * @author frederic.falempin@enst-bretagne.fr
 * @version 1.8  September 2000
 * @version 1.2  August  2002
 */
package modulecoGUI.grapheco.statManager;
import java.util.Hashtable;
import java.util.Vector;
import java.util.Enumeration;
import modulecoFramework.modeleco.EWorld;
import modulecoGUI.Traces.VarCollector;
import modulecoGUI.Traces.Experience;
/**
 * This class is instanciated by each world in order to calculate some
 * variables, and keep their evolution. It is usefull to trace the curves on
 * the graphic. The variables can be computed using methods declared in the
 * world (grapheco.statManager.Var) , or declared in the agent contained by the
 * world (grapheco.statManager.CalculatedVar).
 * 
 * @see grapheco.statManager.Var
 * @see grapheco.statManager.CalculatedVar
 */
public class StatManager {
	/**
	 * The eWorld that has instancied the StatManager. The variables will be
	 * calculated using this world methods
	 */
	private EWorld eWorld;
	/**
	 * The VarCalculator that will calculate the variables linked to agent
	 * methods (grapheco.statManager.CalculatedVars).
	 */
	private VarCalculator varCalculator;
	/**
	 * This hashtable links Calculated Var names to their vectors. A vector
	 * contains all the evolution of a variable during the simulation.
	 */
	private Hashtable hashCalculatedVars;
	/**
	 * This hashtable links Vars to their vectors. A vector contains all the
	 * evolution of a variable during the simulation.
	 */
	private Hashtable hashVars;
	/**
	 * This hashtable links Var names to Vars.
	 */
	private Hashtable hashVarNameToNonCalculatedVars;
	/**
	 * This is the number of iteration (step) since the begining of simulation
	 */
	private int iteration = 0;
	protected VarCollector varCollector;
	protected boolean collectTrace;
	/**
	 * Constructs a StatManager (Used by modeleco.EWorld).
	 * 
	 * @param eWorld
	 *            The world on which calculations have to be made (usually the
	 *            world who instanciate the StatManager
	 * @see modeleco.EWorld
	 */
	public StatManager(EWorld eWorld) {
		this.eWorld = eWorld;
		varCalculator = new VarCalculator(eWorld);
		hashCalculatedVars = new Hashtable();
		hashVarNameToNonCalculatedVars = new Hashtable();
		hashVars = new Hashtable();
	}
	/**
	 * Make the calculation (called by modeleco.EWorld). It increase the
	 * iteration value, and compute all the stored variables (whith add
	 * methods).
	 * 
	 * @see modeleco.EWorld
	 */
	public void step() {
		//First increment the iteration
		iteration++;
		//Make the var calculator calculte the Calculated Vars
		varCalculator.calculate();
		//Put in the vectors the values of Calculated Vars
		for (Enumeration e = hashCalculatedVars.keys(); e.hasMoreElements();) {
			//Just ask the Var calculator for the value of
			// the Calculated var
			String varName = (String) e.nextElement();
			((Vector) hashCalculatedVars.get(varName)).add(iteration,
					new Double(varCalculator.get(varName)));
		}
		//Put in the vectors the values of Vars
		for (Enumeration e = hashVars.keys(); e.hasMoreElements();) {
			Var var = (Var) e.nextElement();
			try {
				//Invoke on the world the method (a field of Var) to
				//get the variable value
				((Vector) hashVars.get(var)).add(iteration, var.getMethod()
						.invoke(eWorld, null));
			} catch (IllegalAccessException ex) {
				System.out.println(ex.toString());
			} catch (java.lang.reflect.InvocationTargetException ex) {
				System.out.println(((this.getClass()).getPackage()).getName()
						+ " " + ex.toString());
			}
		}
		/**
		 * Record data (collect the trace) for this iteration.
		 */
		if ((varCollector != null) && (collectTrace)) {
			varCollector.collectTrace(iteration);
		}
	}
	/**
	 * Add a Calculated Var (accessed in the agent) to the StatManager, to make
	 * this Calculated Var computed.
	 * 
	 * @see grapheco.statManager.CalculatedVar
	 */
	public void add(CalculatedVar calculatedVar) {
		// Create the vector to store the evolution of the Claculated Var
		Vector vector = new Vector(); //Maybe something can be done here for
		// optimization of the vector use
		// Link it with the Calculated var name
		hashCalculatedVars.put(calculatedVar.getName(), vector);
		// Add it to VarCalculator in order to make it calculated after
		varCalculator.add(calculatedVar);
		//fill the vector with initial value of the Calculated Var
		vector.add(0, new Double(varCalculator.get(calculatedVar.getName())));
	}
	/**
	 * Add a Var (accessed in the world) to the StatManager, to make thi Var
	 * computed.
	 * 
	 * @see grapheco.statManager.Var
	 */
	public void add(Var var) {
		Vector vector = new Vector(); //Maybe something can be done here for
		// optimization of the vector use
		//Link the Var with its vector
		hashVars.put(var, vector);
		//Link the Var with its name
		hashVarNameToNonCalculatedVars.put(var.getName(), var);
		try {
			//fill the vector with initial value of the Var
			vector.add(0, var.getMethod().invoke(eWorld, null));
		} catch (IllegalAccessException ex) {
			System.out.println(ex.toString());
		} catch (java.lang.reflect.InvocationTargetException ex) {
			System.out.println(ex.toString());
		}
	}
	/**
	 * Returns the value of a variable calculated by StatManager, at the
	 * current iteration (i.e. the last calculated value).
	 * 
	 * @return The value of the variable.
	 * @param varName
	 *            The name of the variable.
	 */
	public double get(String varName) {
		try //is it a Calculated var ?
		{
			// Find in the Calculated Var vector its value (current iteration)
			return ((Double) ((Vector) hashCalculatedVars.get(varName))
					.elementAt(this.iteration)).doubleValue();
		} catch (ArrayIndexOutOfBoundsException e) {
		} catch (NullPointerException e) {// No it isn't, it's a Var.
			try {
				// Find in the Var vector its value (current iteration)
				Var var = (Var) hashVarNameToNonCalculatedVars.get(varName);
				return ((Double) ((Vector) hashVars.get(var))
						.elementAt(this.iteration)).doubleValue();
			} catch (ArrayIndexOutOfBoundsException f) {
			} catch (NullPointerException f) {
				return 0;
			}
		}
		return 0;
	}
	/**
	 * Returns the value of a variable calculated by StatManager, at the
	 * specified iteration of simulation.
	 * 
	 * @return The value of the variable.
	 * @param varName
	 *            The name of the variable.
	 * @param iteration
	 *            The iteration of the simulation that interest us.
	 */
	public double get(String varName, int iteration) {
		try //is it a Calculated var ?
		{
			// Find in the Calculated Var vector its value (current iteration)
			return ((Double) ((Vector) hashCalculatedVars.get(varName))
					.elementAt(iteration)).doubleValue();
		} catch (ArrayIndexOutOfBoundsException e) {
		} catch (NullPointerException e) {// No it isn't, it's a Var.
			try {
				// Find in the Var vector its value (current iteration)
				Var var = (Var) hashVarNameToNonCalculatedVars.get(varName);
				return ((Double) ((Vector) hashVars.get(var))
						.elementAt(iteration)).doubleValue();
			} catch (ArrayIndexOutOfBoundsException f) {
			} catch (NullPointerException f) {
				return 0;
			}
		}
		return 0;
	}
	/**
	 * @return The current iteration of the simulation.
	 */
	public int getIteration() {
		return iteration;
	}
	public void saveTraceParameter(EWorld eWorld, Experience experience) {
		System.out
				.println("Start recording data in " + experience.getPathname() + " ...");
		varCollector = new VarCollector(eWorld, experience);
		collectTrace = true;
	}
	public void stopCollectTrace() {
		System.out
				.println("Stop recording data.");
		if (collectTrace) {
			collectTrace = false;
			varCollector.removeTrace();
		}
	}
	public void saveLastTrace() {
		if ((varCollector != null) && (collectTrace)) {
			varCollector.saveLastTrace(iteration);
		}
	}
}
