/*
 * @(#)World.java 0.1 25-Apr-04
 */
package models.GCMG;
import java.util.ArrayList;
import java.util.Iterator;
import modulecoFramework.modeleco.CAutorun;
import modulecoFramework.modeleco.ENeighbourWorld;
import modulecoGUI.grapheco.descriptor.IntegerDataDescriptor;
import modulecoGUI.grapheco.descriptor.DoubleDataDescriptor;
import modulecoGUI.grapheco.statManager.Var;
/**
 * An implementation of the Grand Canonical Minority Game, introduced by
 * Jefferies, Hart, Hui and Johnson in 2000 in "From market games to real-world
 * markets" arXiv:cond-mat/0008387.
 * <p>
 * See the paper on http://arxiv.org/abs/cond-mat/0008387
 * 
 * @author Gilles Daniel (gilles@cs.man.ac.uk)
 * @version 0.1, 25-Apr-04
 */
public class World extends ENeighbourWorld {
	/**
	 * Initial values for the 4 basic parameters of this world <br>
	 * parameter 1: world size <br>
	 * parameter 2: neighbourhood type <br>
	 * parameter 3: active zone type <br>
	 * parameter 4: scheduler type <br>
	 */
	public static String initLength = "20";
	public static String initNeighbour = "NeighbourVonNeuman";
	public static String initZone = "World";
	public static String initScheduler = "LateCommitScheduler";
	/**
	 * Number of players.
	 */
	protected int N;
	/**
	 * Number of active agents at each step, i.e. agent who take an action to
	 * buy or sell.
	 */
	protected int noActive;
	/**
	 * Excess demand, i.e. number of stocks agents want to trade.
	 * <p>
	 * >0 for buyers
	 * <p>
	 * <0 for sellers
	 * <p>
	 * excessDemand = excessDemand_buyers + excessDemand_sellers
	 */
	protected double excessDemand;
	/**
	 * Volume of transactions, defined as
	 * <p>
	 * volume = excessDemand_buyers - excessDemand_sellers
	 */
	protected double volume;
	/**
	 * Each agent i has a memory size M(i), which lets him remember the past
	 * price fluctuations. We need to know the maximum memory size to initialize
	 * the first historical fluctuations.
	 */
	protected int maximumMemorySize;
	/**
	 * The priceMovehistory indicates whether the market went up (1) or down
	 * (-1).
	 */
	protected int[] priceMoveHistory;
	/**
	 * Price
	 */
	protected double price;
	protected double previousPrice;
	protected double logReturn;
	/**
	 * Spread
	 */
	protected double spread;
	protected double previousSpread;
	/**
	 * Step in the simulation. Incremented at each time step.
	 */
	protected static int step = 0;
	/**
	 * The empirical data
	 */
	protected Empirical empirical;
	protected static int noEmpiricalElements = 30;
	/**
	 * Create the world
	 * 
	 * @param length
	 */
	public World(int length) {
		super(length);
	}
	/**
	 * Set the default value for the variables. This is executed only once, when
	 * the world is created for the first time.
	 */
	public void setDefaultValues() {
		//System.out.println("[World.setDefaultValues()]");
		/**
		 * Initialise the size of the largest memory size of all agents
		 */
		maximumMemorySize = 4;
	}
	/**
	 * Initialise the simulation.
	 */
	public void init() {
		//System.out.println("[World.init()]");
		/**
		 * Initialise the number of agents.
		 */
		N = getAgentSetSize();
		/**
		 * Set the initial price of a few variables.
		 */
		excessDemand = 0;
		price = 10;
		spread = 0;
		previousPrice = price;
		previousSpread = spread;
		noActive = 0;
		volume = 0;
		/**
		 * Create the priceMove history and initialise its first fluctuations
		 * randomly. Either 1 or -1;
		 */
		priceMoveHistory = new int[maximumMemorySize + 1];
		for (int i = 0; i < maximumMemorySize + 1; i++) {
			priceMoveHistory[i] = (Math.random() > 0.5 ? 1 : -1);
		}
		/**
		 * Generate the empirical data
		 */
		empirical = new Empirical(noEmpiricalElements);
		/**
		 * Add to the statManager the variables to observe.
		 */
		try {
			statManager.add(new Var("statPrice", Class.forName(
					this.pack() + ".World").getMethod("getPrice", null)));
			statManager
					.add(new Var("statExcessDemand", Class.forName(
							this.pack() + ".World").getMethod(
							"getExcessDemand", null)));
			statManager.add(new Var("statLogReturn", Class.forName(
					this.pack() + ".World").getMethod("getLogReturn", null)));
			statManager.add(new Var("statNoActive", Class.forName(
					this.pack() + ".World").getMethod("getNoActive", null)));
			statManager.add(new Var("statVolume", Class.forName(
					this.pack() + ".World").getMethod("getVolume", null)));
		} catch (ClassNotFoundException e) {
			System.out.println(e.toString());
		} catch (NoSuchMethodException e) {
			System.out.println(e.toString());
		}
	}
	/**
	 * There are two modes: empirical and free.
	 */
	public void compute() {
		//if (step == 0)
		//	System.out
		//			.println("[World.computeEmpirical()] Empirical mode with step = "
		//					+ step);
		//if (step == noEmpiricalElements)
		//	System.out
		//			.println("[World.computeEmpirical()] Switching mode from empirical to free with step = "
		//					+ step);
		/**
		 * Update some historical variables
		 */
		for (int i = 0; i < maximumMemorySize; i++)
			priceMoveHistory[i] = priceMoveHistory[i + 1];
		previousPrice = price;
		previousSpread = spread;
		/**
		 * Reset the number of active agents
		 */
		noActive = 0;
		/**
		 * Create temporary variables
		 */
		double agentExcessDemand = 0;
		double excessDemandBuyers = 0;
		double excessDemandSellers = 0;
		int agentAction;
		int excessDemandAction = 0;
		Agent a = new Agent();
		/**
		 * For every agent
		 */
		for (Iterator i = iterator(); i.hasNext();) {
			/**
			 * Get the agent
			 */
			a = (Agent) i.next();
			/**
			 * Get his action (1=buy, 0=nothing, -1=sell)
			 */
			agentAction = a.getAction();
			/**
			 * Get his excess demand, i.e. S[t+1]-S[t]
			 */
			agentExcessDemand = a.getExcessDemand();
			//System.out.println("(action, excessDemand) = (" + agentAction
			//		+ ", " + round(agentExcessDemand, 1) + ")");
			/**
			 * Aggreagate the agents' actions
			 */
			excessDemandAction += agentAction;
			/**
			 * Aggregate the number of active players
			 */
			noActive += (agentAction == 0 ? 0 : 1);
			/**
			 * Aggregate the excess demand
			 */
			switch (agentAction) {
				case 1 :
					if (agentExcessDemand < 0) {
						System.err
								.println("[World.compute()] Error: the agent "
										+ a.getAgentID()
										+ " is Buyer, but his excessDemand is negative.");
						System.exit(0);
					} else
						excessDemandBuyers += agentExcessDemand;
					break;
				case -1 :
					if (agentExcessDemand > 0) {
						System.err
								.println("[World.compute()] Error: the agent "
										+ a.getAgentID()
										+ " is Seller, but his excessDemand is positive.");
						System.exit(0);
					} else
						excessDemandSellers += agentExcessDemand;
					break;
				case 0 :
					break;
				default :
					System.err
							.println("[World.compute()] Error: the action of the agent is not recognised (it shoult be either 1, 0 or -1)");
					System.exit(0);
					break;
			}
		}
		/**
		 * Compute the aggregate excessDemand.
		 */
		excessDemand = excessDemandBuyers + excessDemandSellers;
		/**
		 * Compute the aggregate volume.
		 */
		volume = excessDemandBuyers - excessDemandSellers;
		if (volume < 0) {
			System.out
					.println("[World.compute()] Error: the volume is negative. (Buyers, Sellers, excessDemand, Volume) = ("
							+ round(excessDemandBuyers, 1)
							+ ", "
							+ round(excessDemandSellers, 1)
							+ ", "
							+ round(excessDemand, 1)
							+ ", "
							+ round(volume, 1)
							+ ")");
			System.exit(0);
		}
		/**
		System.out
				.println("(noActive, Buyers, Sellers, excessDemand, Volume) = ("
						+ noActive
						+ ", "
						+ round(excessDemandBuyers, 2)
						+ ", "
						+ round(excessDemandSellers, 2)
						+ ", "
						+ round(excessDemand, 2)
						+ ", "
						+ round(volume, 2)
						+ ")");
		**/
		/**
		 * Update the priceMove history
		 */
		/**
		 * Empirical mode. <br>
		 * We get the data from an external source.
		 */
		//if (step < noEmpiricalElements) {
		//	logReturn = empirical.getLogReturn(step);
		//	price = empirical.getPrice(step);
		//	spread = 0;
		//}
		/**
		 * Free mode. <br>
		 * We run the simulation freely.
		 */
		//else {
			/**
			 * TO CHANGE
			 */
			logReturn = excessDemand / (N*20);
			price = price * Math.exp(logReturn);
			//spread = 0.01 * price;
			spread = 0;
			priceMoveHistory[maximumMemorySize] = (logReturn > 0 ? 1 : -1);
		//}
		/**
		 * Increment the local step number
		 */
		step++;
		/**
		 * Print out a few variables
		 */
		System.out.println("(logReturn, price) = (" + round(logReturn, 2)
				+ ", " + round(price, 1) + ")");
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
		//System.out.println("[World.getDescriptors()] price = "
		//		+ round(price, 1));
		descriptors.clear();
		descriptors.add(new IntegerDataDescriptor(this, "Maximum memory size",
				"maximumMemorySize", maximumMemorySize, true));
		descriptors.add(new DoubleDataDescriptor(this, "Price", "price", price,
				true, 1));
		descriptors.add(new DoubleDataDescriptor(this, "Excess Demand",
				"excessDemand", excessDemand, false, 1));
		descriptors.add(new DoubleDataDescriptor(this, "Volume", "volume",
				volume, false, 1));
		descriptors.add(new DoubleDataDescriptor(this, "LogReturn",
				"logReturn", logReturn, false, 2));
		//descriptors.add(new IntegerDataDescriptor(this, "Active agents",
		//		"noActive", noActive, false));
		return descriptors;
	}
	/**
	 * Create the Autorun.
	 */
	public CAutorun createAutorun() {
		Autorun autorun = new Autorun(this);
		return autorun;
	}
	/**
	 * Ad-hoc function to round values
	 * 
	 * @param value
	 * @param decimalPlace
	 * @return
	 */
	double round(double value, int decimalPlace) {
		double power_of_ten = 1;
		while (decimalPlace-- > 0)
			power_of_ten *= 10.0;
		return Math.round(value * power_of_ten) / power_of_ten;
	}
	/**
	 * ######### <br>
	 * Accessors <br>
	 * #########
	 */
	/**
	 * @return Returns the price.
	 */
	public double getPrice() {
		return price;
	}
	/**
	 * @return Returns the noActive.
	 */
	public double getNoActive() {
		return N;
	}
	/**
	 * excessDemand accessor.
	 */
	public double getExcessDemand() {
		return excessDemand;
	}
	/**
	 * @return Returns the volume.
	 */
	public double getVolume() {
		return volume;
	}
	/**
	 * @return Returns the logReturn.
	 */
	public double getLogReturn() {
		return logReturn;
	}
	/**
	 * @return Returns the maximumMemorySize.
	 */
	public int getMaximumMemorySize() {
		return maximumMemorySize;
	}
	/**
	 * @return the priceMoveHistory.
	 */
	public int[] getPriceMoveHistory() {
		return priceMoveHistory;
	}
	/**
	 * @param price
	 *            The price to set.
	 */
	public void setPrice(double price) {
		this.price = price;
	}
	/**
	 * @param noActive
	 *            The noActive to set.
	 */
	public void setNoActive(int noActive) {
		this.noActive = noActive;
	}
	/**
	 * @param maximumMemorySize
	 *            The maximumMemorySize to set.
	 */
	public void setMaximumMemorySize(int maximumMemorySize) {
		this.maximumMemorySize = maximumMemorySize;
	}
	/**
	 * toString() method.
	 * 
	 * @return
	 */
	public String toString() {
		String s = "[GCMG.World.toString()] (initLength, initNeighbour, initZone, initScheduler) = ("
				+ initLength
				+ ", "
				+ initNeighbour
				+ ", "
				+ initZone
				+ ", "
				+ initScheduler + ")";
		return s;
	}
}
