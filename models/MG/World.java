/*
 * @(#)World.java	1.0 5-Apr-04
 */
package models.MG;

import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Color;

import modulecoFramework.modeleco.ENeighbourWorld;

import modulecoGUI.grapheco.descriptor.IntegerDataDescriptor;
import modulecoGUI.grapheco.statManager.Var;
/**
 * An implementation of the Minority Game, introduced by Challet and Zhang in
 * 1997 in "Emergence of Cooperation and Organization in an Evolutionary Game"
 * arXiv:adapt-org/9708006 v2 see the paper on
 * http://arxiv.org/abs/adap-org/9708006
 * 
 * @author Gilles Daniel (gilles@cs.man.ac.uk)
 * @version 0.9, 8-Mar-04
 * @version 1.0, 8-Apr-04
 */
public class World extends ENeighbourWorld {
	/**
	 * Initial values for the 4 basic parameters of this world <br>
	 * parameter 1: world size <br>
	 * parameter 2: neighbourhood type <br>
	 * parameter 3: active zone type <br>
	 * parameter 4: scheduler type <br>
	 */
	public static String initLength = "10";
	public static String initNeighbour = "NeighbourVonNeuman";
	public static String initZone = "World";
	public static String initScheduler = "LateCommitScheduler";
	/**
	 * Number of players Must be an odd number The constructor will initially
	 * get it from the parameters.txt file
	 */
	protected int N;
	/**
	 * Each agent i has a memory size M(i), which lets him remember the past
	 * price fluctuations. We need to know the maximum memory size to
	 * initialize the first historical fluctuations.
	 */
	protected int maximumMemorySize;
	/**
	 * Each agent i has a time horizon H(i), above which he forgets his
	 * strategy scores. We need to know the maximum time horizon to initialize
	 * the first historical fluctuations.
	 */
	protected int maximumTimeHorizon;
	/**
	 * The price represents here whether the market is up (1) or down (0)
	 */
	protected int price;
	/**
	 * <tt>history</tt> records the last values of the price It represents
	 * the historical fluctuations of the market (1 for up, 0 for down)
	 * Basically, it's a succession of bits 1-1-0-1 for up-up-down-up It's size
	 * depends on the maximumMemorySize
	 */
	protected int[] history;
	/**
	 * Number of players in group A, i.e. who play 1
	 */
	protected int excessDemand;
	/**
	 * the agents' state is symbolised by a color
	 */
	protected Color colors[];
	/**
	 * Create the world
	 * 
	 * @param length
	 */
	public World(int length) {
		super(length);
	}
	/**
	 * Set the default value for the variables. This is executed only once,
	 * when the world is created for the first time.
	 */
	public void setDefaultValues() {
		//System.out.println("[World.setDefaultValues()]");
		/**
		 * Initialise the largest memory size of all agents
		 */
		maximumMemorySize = 10;
	}
	/**
	 * Initialise the simulation In particular, this is where you add some
	 * variables to the <tt>statManager</tt> to be recorded or displayed in
	 * the right panel
	 */
	public void init() {
		//System.out.println("[World.init()]");
		/**
		 * Initialise the number of agents
		 */
		N = getAgentSetSize();
		/**
		 * Initialise the excess demand so that the first value is in
		 * acceptable boundaries
		 */
		excessDemand = N / 2;
		/**
		 * Create the history array and initialise its first fluctuations
		 */
		history = new int[maximumMemorySize + 1];
		for (int i = 0; i < maximumMemorySize + 1; i++) {
			history[i] = (int) (2 * Math.random()); // Return 0 or 1
		}
		/**
		 * Define the colors of the agents, indicating their state
		 */
		colors = new Color[2];
		colors[0] = Color.green; // value > 0
		colors[1] = Color.red; // value <=0
		/**
		 * Add the observable variable price to the statManager
		 */
		try {
			/**
			 * "myStatPrice" is the name to display. "getPrice" is the accessor
			 * to the variable
			 */
			//statManager.add(new Var("myStatPrice", Class.forName(
			//		this.pack() + ".World").getMethod("getPrice", null)));
			statManager.add(
				new Var(
					"myStatExcessDemand",
					Class.forName(this.pack() + ".World").getMethod(
						"getExcessDemand",
						null)));
		} catch (ClassNotFoundException e) {
			System.out.println(e.toString());
		} catch (NoSuchMethodException e) {
			System.out.println(e.toString());
		}
	}
	/**
	 * Invoked at every time step, once the agents have 'played'
	 */
	public void compute() {
		//System.out.println("[World.compute()]");
		/**
		 * Compute the excess demand as the aggregation of the predictions of
		 * each agent
		 */
		excessDemand = 0;
		for (Iterator i = iterator(); i.hasNext();) {
			/**
			 * You access each agent, one by one, thanks to the Iterator. Given
			 * that agent return 1 or 0, the some of all represents directly
			 * the number of people in group A
			 */
			//Agent a = new Agent();
			//a = (Agent) i.next();
			//int tmp = a.getPrediction();
			//excessDemand += tmp;
			excessDemand += ((Agent) i.next()).getPrediction();
		}
		/**
		 * Setup the price.
		 * <p>
		 * The price obeys the law of demand and supply. If the excess demand
		 * is positive (above average), the price goes up, and vice-versa.
		 */
		price = (excessDemand > N / 2 ? 1 : 0);
		//System.out.println("[World.compute()] excessDemand / price = "
		//		+ excessDemand + " / " + price);
		/**
		 * Update the history
		 */
		int[] tmpHistory = new int[maximumMemorySize + 1];
		tmpHistory = history;
		for (int i = 0; i < maximumMemorySize; i++)
			history[i] = tmpHistory[i + 1];
		history[maximumMemorySize] = price;
		//String s="history: ";
		//for (int i=0; i<maximumMemorySize+1; i++)
		//	s += history[i];
		//System.out.println(s);
	}
	/**
	 * Used by <tt>EWorld</tt>, when a new world is created
	 * 
	 * @see modulecoFramework.modeleco.EWorld
	 */
	public void getInfo() {
		//System.out.println("[World.getInfo()]");
	}
	public void update() {
		//System.out.println("[World.update()]");
	}
	/**
	 * Add to the bottom panel the variables (resp parameters) to observe (resp
	 * change) during the simulation Invoked at every step
	 */
	public ArrayList getDescriptors() {
		//System.out.println("[World.getDescriptors()] excessDemand / price =
		// "
		//		+ excessDemand + " / " + price);
		descriptors.clear();
		descriptors.add(
			new IntegerDataDescriptor(
				this,
				"ExcessDemand",
				"excessDemand",
				excessDemand,
				false));
		descriptors.add(
			new IntegerDataDescriptor(this, "Price", "price", price, false));
		descriptors.add(
			new IntegerDataDescriptor(
				this,
				"Maximum memory size",
				"maximumMemorySize",
				maximumMemorySize,
				true));
		return descriptors;
	}
	/**
	 * Price accessor. It *has to* return a double for the Trace to work
	 * properly.
	 * 
	 * @return price
	 */
	public double getPrice() {
		return price;
	}
	/**
	 * excessDemand accessor Get the number of agents in group A
	 */
	public double getExcessDemand() {
		return excessDemand;
	}
	/**
	 * Set the price
	 * 
	 * @param price
	 */
	public void setPrice(int price) {
		this.price = price;
	}
	public void setMaximumMemorySize(int maximumMemorySize) {
		this.maximumMemorySize = maximumMemorySize;
	}
	/**
	 * Return the last M bits of the history, where M is the memorySize of the
	 * agent The predictions sent to each agent might be smaller than the
	 * history size
	 * 
	 * @param memorySize
	 * @return predictions
	 */
	public String getSignal(int memorySize) {
		/**
		 * Create the signal to send
		 */
		String signal = "";
		/**
		 * Extract the last M bits of the history, where M = memorySize
		 */
		for (int i = 0; i < memorySize; i++) {
			signal
				+= Integer.toString(history[maximumMemorySize - memorySize + i]);
		}
		//System.out.println("[World.getSignal()] signal = " + signal);
		/**
		 * Return the signal
		 */
		return signal;
	}
	/**
	 * To handle the colors Necessary only if the default colors are modified
	 * by the creation of a canevas
	 * 
	 * @return the colors used to represent the states of the agents
	 */
	public Color[] getColors() {
		return colors;
	}
	/**
	 * @param excessDemand
	 *            The excessDemand to set.
	 */
	public void setExcessDemand(int excessDemand) {
		this.excessDemand = excessDemand;
	}
}
