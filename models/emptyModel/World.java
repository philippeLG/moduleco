/**
 * moduleco.models.emptyModel;
 * @author Gilles Daniel (gilles@cs.man.ac.uk)
 */
package models.emptyModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Color;
import modulecoFramework.modeleco.ENeighbourWorld;

import modulecoGUI.grapheco.descriptor.IntegerDataDescriptor;
import modulecoGUI.grapheco.statManager.Var;
/**
 * Describe the world, the way agents communicate with each other, etc.
 * <p>
 * This is an example of empty model, the equivalent of the "Hello World" in
 * MODULECO. You can use this empty shell as the first building block for your
 * own model.
 * <p>
 * To build a model from scratch, you have to implement <em>at least</em> two
 * classes:
 * <ul>
 * <li><tt>World.java</tt>: describes the world
 * <li><tt>Agent.java</tt>: describes the behavior of each agent in the
 * world
 * </ul>
 * Two other classes will be useful, but not necessary:
 * <ul>
 * <li><tt>Graphique.java</tt>: manages the graphs on the right side of the
 * panel
 * <li><tt>Canevas.java</tt>: manages the colors of the agents in the left
 * panel (<em>Canevas</em>)
 * </ul>
 * <p>
 * 
 * In this very simple model, at each time step, the agents pick up a random
 * value between 0 and 5. Their state is characterised by the value: green if
 * value >= 3, red if value <3. Then those values are aggregated in the world,
 * to form a price, displayed graphically on the right panel (<em>Graphique</em>)
 * and numerically on the bottom panel (<em>World Editor</em>).
 * 
 * Here are the steps of a typical simulation:
 * <ul>
 * <li>[World.getInfo()] Get information from the <em>World Editor</em>
 * <li>[World.setDefaultValues()] Set the initial values for the variables and
 * parameters, when the world is created for the very first time
 * <li>[Agent()]---------------->Create a new Agent
 * <li>[Agent.getInfo()]-------->Get some info
 * <li>[Agent()]
 * <li>[Agent.getInfo()]
 * <li>...
 * <li>[Agent.init()]----------->Initialise the agent
 * <li>[Agent.init()]
 * <li>...
 * <li>[World.init()]----------->Initialise the world
 * <li>[World.compute()]-------->Perform a first compute
 * <li>[World()]---------------->Create the world (ONLY NOW)
 * <li>[World.getColors()]------>Get the colors of the agents
 * <li>[World.getDescriptors()]->Get the descriptors (old values)
 * <li>----- Beginning of time step -----
 * <li>[World.getDescriptors()]->Get the descriptors (old values, AGAIN)
 * <li>[Agent.compute()]-------->The agent computes
 * <li>[Agent.compute()]
 * <li>...
 * <li>[Agent.commit()]--------->The agent commits (BEFORE THE WORLD COMPUTES)
 * <li>[Agent.commit()]
 * <li>...
 * <li>[World.compute()]-------->Compute the new values (ONLY NOW)
 * <li>----- End of time step -----
 * <li>[World.getDescriptors()]
 * <li>...
 * </ul>
 * 
 * @author Gilles Daniel (gilles@cs.man.ac.uk)
 * @version 1.0, 23-Feb-04
 * @version 1.1, 09-Mar-04
 * @see modulecoFramework.modeleco.ENeighbourWorld
 * @see modulecoFramework.modeleco.EWorld
 * @see modulecoFramework.modeleco.CAgent
 */
public class World extends ENeighbourWorld {
	/**
	 * Initial values for the 4 basic parameters of this world <br>
	 * parameter 1: world size <br>
	 * parameter 2: neighbourhood type <br>
	 * parameter 3: active zone type <br>
	 * parameter 4: scheduler type <br>
	 */
	public static String initLength = "5";
	public static String initNeighbour = "NeighbourVonNeuman";
	public static String initZone = "World";
	public static String initScheduler = "LateCommitScheduler";
	/**
	 *  
	 */
	/**
	 * <tt>price</tt> is the observable variable in this world.
	 * <p>
	 * It will be displayed on the right panel (<em>Graphique</em>). It
	 * represents the aggregated value of all agents.
	 */
	protected int price;
	/**
	 * the agents' state is symbolised by a color
	 */
	protected Color colors[];
	/**
	 * Constructor
	 * 
	 * @param length
	 *            size of the world (length x length), i.e. squre root of the
	 *            number of agents, e.g. 5
	 */
	public World(int length) {
		super(length);
	}
	/**
	 * Used by <tt>EWorld</tt>, when a new world is created.
	 * <p>
	 * Mandatory method (inherited from its abstract superclass in <tt>EWorld</tt>).
	 * 
	 * @see modulecoFramework.modeleco.EWorld
	 * @see modulecoFramework.modeleco.CAgent
	 */
	public void getInfo() {
		System.out.println("[World.getInfo()]");
	}
	/**
	 * Set the default values when the world is created for the very first
	 * time.
	 * <p>
	 * Initialise HERE (and not in the constructor or init()) the parameters
	 * you might want to change through the bottom panel (<em>World Editor</em>).
	 * <p>
	 * Mandatory method (inherited from its abstract superclass in <tt>EWorld</tt>)
	 * 
	 * @see modulecoFramework.modeleco.EWorld
	 */
	public void setDefaultValues() {
		System.out.println("[World.setDefaultValues()]");
		/**
		 * Initialise the price
		 */
		setPrice(50);
	}
	/**
	 * Initialise the simulation.
	 * <p>
	 * In particular, this is where you add some variables to the <tt>statManager</tt>
	 * to be recorded or displayed in the right panel (<em>Graphique</em>).
	 */
	public void init() {
		System.out.println("[World.init()]");
		// Define the colors of the agents, indicating their state
		colors = new Color[2];
		colors[0] = Color.green; // value > 0
		colors[1] = Color.red; // value <=0
		/**
		 * Add the variables to observe through the <em>statManager</em>
		 */
		try {
			/**
			 * <ul>
			 * <li>"statPrice" is the name used by the statManager
			 * <li>"getPrice" is the exact name of the method that returns the
			 * variable. For the <tt>Trace</tt> and the <tt>Graphique</t> to work properly, getPrice() MUST RETURN a double (@see
			 * modulecoGUI.grapheco.graphix.Trace)
			 * </ul>
			 */
			statManager.add(
				new Var(
					"statPrice",
					Class.forName(this.pack() + ".World").getMethod(
						"getPrice",
						null)));
		} catch (ClassNotFoundException e) {
			System.out.println(e.toString());
		} catch (NoSuchMethodException e) {
			System.out.println(e.toString());
		}
	}
	/**
	 * Once the agents have computed and committed, the world computes.
	 * <p>
	 * Invoked at each time step.
	 */
	public void compute() {
		//System.out.println("[World.compute()]");
		/**
		 * Compute the new price as the aggregation of the values of each
		 * agent.
		 * <p>
		 * You access each agent, one by one, thanks to the Iterator
		 */
		int excessDemand = 0;
		for (Iterator i = iterator(); i.hasNext();)
			excessDemand += ((Agent) i.next()).getValue();
		price += excessDemand;
		System.out.println("[World.compute()] price = " + price);
	}
	/**
	 * Add to the bottom panel (<em>World Editor</em>) the variables (resp
	 * parameters) to observe (resp change) during the simulation.
	 * <p>
	 * Those variables should have been initialised in setDefaultValues() when
	 * the world is created for the very first time.
	 * <p>
	 * Invoked at each time step.
	 */
	public ArrayList getDescriptors() {
		System.out.println("[World.getDescriptors()] price = " + price);
		descriptors.clear();
		/**
		 * <ul>
		 * <li>"Price" is the complete name to display in the bottom panel (
		 * <em>World Editor</em>)
		 * <li>"price" is the variable name. A method "setPrice()" MUST EXIST
		 * <li>price is the variable itself
		 * <li>false means that you cannot change the value of the variable
		 * </ul>
		 */
		descriptors.add(
			new IntegerDataDescriptor(this, "Price", "price", price, false));
		return descriptors;
	}
	/**
	 * Price accessor.
	 * <p>
	 * It *MUST* return a double, whatever the type of the variable (here price
	 * is already a double), for the Trace (and then the Graphique) to work
	 * properly.
	 * <p>
	 * See init()statManager.add().
	 * 
	 * @return price
	 */
	public double getPrice() {
		return price;
	}
	/**
	 * Set the price.
	 * 
	 * @param price
	 */
	public void setPrice(int price) {
		this.price = price;
	}
	/**
	 * Handle the colors of the agents.
	 * <p>
	 * Necessary only if the default colors are modified by the creation of a
	 * canevas.
	 * 
	 * @return the colors used to represent the states of the agents
	 * @see models.emptyModel.Canevas
	 */
	public Color[] getColors() {
		System.out.println("[World.getColors()]");
		return colors;
	}
}
