/*
 * @(#)Agent.java 0.1 25-Apr-04
 */
package models.GCMG;
import java.util.ArrayList;
import modulecoFramework.modeleco.EAgent;
import modulecoGUI.grapheco.descriptor.IntegerDataDescriptor;
import modulecoGUI.grapheco.descriptor.DoubleDataDescriptor;
/**
 * In the GCMG, agents make a prediction about tomorrow's price variation
 * according to past price variations. This prediction can be either 1 (the
 * market should go up) or -1 (the market shoulg go down).
 * <p>
 * Agents's predictions come from a set of initial strategies that map possible
 * past price variations with a prediction. Agents constantly update the virtual
 * scores of those strategies, and pick up the best one at every time step.
 * <p>
 * The agents will then take an action: action=1 (buy) if prediction=1,
 * action=-1 (sell) if prediction=-1, or action=0 (do not trade if they are not
 * confident enough in their strategies.
 * 
 * @author Gilles Daniel (gilles@cs.man.ac.uk)
 * @version 0.1 25-Apr-04
 */
public class Agent extends EAgent {
	/**
	 * Memory size M of the agent. <br>
	 * 1 <= M <= maximumMemorySize
	 */
	protected int memorySize;
	/**
	 * Time horizon H above which the agents forgets his strategy scores. <br>
	 * H ~= 5 * memorySize
	 */
	protected int timeHorizon;
	/**
	 * Number of strategies S. <br>
	 * S ~= 4
	 */
	protected static int noStrategies = 4;
	/**
	 * Set of strategies A strategy maps an input predictions (historical data,
	 * -1 1 -1 -1) to a prediction (1 or -1)
	 */
	protected SetOfStrategies setOfStrategies;
	/**
	 * The action of this agent. Either 1, -1 or 0;
	 */
	protected int action;
	/**
	 * The excess demand corresponding to this action. It represents here the
	 * number of stocks the agent wants to buy or sell. <br>
	 * excessDEmand ~= +-
	 */
	protected double excessDemand;
	/**
	 * The agent's assets. They are divided into a risky-free asset, Bonds, and
	 * a risky one, Stocks.
	 * <p>
	 * bonds + p * stocks = wealth;
	 */
	protected double bonds;
	protected double stocks;
	protected double wealth;
	/**
	 * His state.
	 */
	protected int state;
	/**
	 * Variables used during the simulation to handle the historic priceMove
	 * variations.
	 */
	protected int[] previousSignal;
	protected int[] newSignal;
	/**
	 * Minimum confidence to play. <br>
	 * 0 <= minimumConfidence <= 1
	 */
	protected int minimumConfidence;
	/**
	 * Confidence in strategies. <br>
	 * -1 <= confidence <= 1
	 */
	protected double confidence;
	/**
	 * Create a new <tt>agent</tt>
	 */
	public Agent() {
		super();
		//System.out.println("[Agent()]");
	}
	public void getInfo() {
		//System.out.println("[Agent.getInfo()]");
	}
	/**
	 * Initialise the agent
	 */
	public void init() {
		//System.out.println("[Agent.init()]");
		/**
		 * Initialise the memory size.
		 * <p>
		 * 1 <= M <= maximumMemorySize
		 */
		//memorySize = (int) (((World) world).maximumMemorySize *
		// Math.random()) + 1;
		memorySize = ((World) world).getMaximumMemorySize();
		/**
		 * Create the signals.
		 */
		previousSignal = new int[memorySize];
		newSignal = new int[memorySize];
		/**
		 * Initialise the time horizon.
		 * <p>
		 * memorySize <= timeHorizon <= 4*memorySize
		 */
		//timeHorizon = memorySize * (1 + (int) (5 * Math.random()));
		//timeHorizon = memorySize * 5;
		timeHorizon = 100;
		/**
		 * Minimum confidence to play.
		 */
		minimumConfidence = Math.min(1, timeHorizon / 6);
		//System.out.println("minimumConfidence = " + minimumConfidence);
		/**
		 * Initialise strategies
		 */
		setOfStrategies = new SetOfStrategies(noStrategies, memorySize,
				timeHorizon);
		/**
		 * Initialise the action. Either 1, -1 or 0.
		 */
		action = (int) (3 * Math.random()) - 1;
		/**
		 * Initialise the state accordingly
		 */
		updateState();
		/**
		 * Initialise the wealth
		 */
		bonds = 1000;
		stocks = 100;
		wealth = bonds + 10 * stocks;
		/**
		 * Display some characteristics for one specific agent
		 */
		//if (agentID == 0)
		//	System.out.println(toString());
	}
	/**
	 * Compute the next prediction
	 */
	public void compute() {
		//System.out.println("[Agent.compute()]");
		/**
		 * ##########################
		 * <p>
		 * Finish the previous time step: set Bi[t+1] and Si[t+1]
		 * <p>
		 * bonds = Bi[t] <br>
		 * stocks = Si[t] <br>
		 * ##########################
		 */
		/**
		 * Get the previous signal.
		 * <p>
		 */
		int[] priceMoveHistory = ((World) world).getPriceMoveHistory();
		for (int i = 0; i < memorySize; i++) {
			previousSignal[i] = priceMoveHistory[priceMoveHistory.length
					- memorySize - 1 + i];
			newSignal[i] = priceMoveHistory[priceMoveHistory.length
					- memorySize + i];
		}
		/**
		 * Get the previous price.
		 * <p>
		 * price = p[t+1] <br>
		 * previousPrice = p[t] <br>
		 * priceMove = sign (p[t+1]-p[t]) <br>
		 */
		double price = ((World) world).price;
		double previousPrice = ((World) world).previousPrice;
		double spread = ((World) world).spread;
		double previousSpread = ((World) world).previousSpread;
		int priceMove = newSignal[memorySize - 1];
		/**
		 * Update the agent's position.
		 * <p>
		 * Bi[t+1] = f (Bi[t], price, etc.) <br>
		 * Si[t+1] = f (Si[t], price, etc.) <br>
		 * Wi[t+1] = f (Bi[t+1], Si[t+1], price) <br>
		 */
		updatePosition(price, spread, previousPrice, previousSpread);
		/**
		 * Update the virtual scores of his strategies
		 */
		setOfStrategies.updateScores(previousSignal, priceMove);
		String s = "";
		//if (agentID == 0) {
		//	s = "price = " + round(price, 1) + ", (bonds, stocks, wealth) = ("
		//			+ round(bonds, 1) + ", " + round(stocks, 1) + ", "
		//			+ round(wealth, 1) + ")\n";
		//	System.out.println(s);
		//}
		/**
		 * ##########################
		 * <p>
		 * New time step
		 * <p>
		 * ##########################
		 */
		/**
		 * Get the best strategy.
		 */
		int bestStrategy = setOfStrategies.getBestStrategy();
		/**
		 * Compute the confidence in this strategy
		 */
		confidence = (double) (setOfStrategies.getScore(bestStrategy) - minimumConfidence)
				/ timeHorizon;
		/**
		 * If the confidence is big enough, get the prediction and excess demand
		 * for this strategy
		 */
		if (confidence > 0) {
			//System.out.println("confidence = " + confidence);
			int prediction = setOfStrategies.getPrediction(bestStrategy,
					newSignal);
			action = prediction;
			/**
			 * Get the new stocks and excess demand
			 */
			switch (action) {
				case 1 :
					excessDemand = confidence * bonds / (price + spread);
					break;
				case -1 :
					excessDemand = -confidence * stocks;
					break;
			}
			//stocks = stocks + excessDemand;
		} else {
			action = 0;
			excessDemand = 0;
		}
		//if (wealth < 100) {
		//	action = 0;
		//	excessDemand = 0;
		//}
		/**
		 * Update his state, buyer or seller
		 */
		updateState();
		/**
		 * Print out some information about me
		 */
		//if (agentID == 0)
		//	System.out.println(toString());
	}
	/**
	 * Commit(), after the compute()
	 */
	public void commit() {
		//System.out.println("[Agent.commit()]");
	}
	/**
	 * Update the agent's bonds, stocks and wealth.
	 * <p>
	 * price and spread are at time t+1, previousPrice and previousSpread at
	 * time t.
	 */
	public void updatePosition(double price, double spread,
			double previousPrice, double previousSpread) {
		//System.out.println("[Agent.updatePosition()]");
		/**
		 * Update the bonds: Bi[t+1] = f (Bi[t])
		 * <p>
		 * price = p[t+1] <br>
		 * previousPrice = p[t] <br>
		 * bonds = Bi[t] <br>
		 * stocks = Si[t] <br>
		 * confidence = ci[t] <br>
		 */
		double newBonds = 0;
		switch (action) {
			case 1 :
				newBonds = bonds
						* (1 - confidence * (price + spread)
								/ (previousPrice + previousSpread));
				break;
			case -1 :
				newBonds = bonds + confidence * stocks * (price - spread);
				break;
			case 0 :
				newBonds = bonds;
				break;
		}
		/**
		 * AD-HOC: pretend the bonds serve an interest rate.
		 */
		bonds = bonds * 1.0001;
		//System.out.println("[Agent.updatePosition()] (bonds, stocks) = (" +
		// bonds + ", " + stocks + ")");
		/**
		 * Update the stocks: Si[t+1] = f (Si[t])
		 * <p>
		 * stocks = Si[t] <br>
		 * excessDemand = Si[t+1] - Si[t] <br>
		 */
		double newStocks = stocks + excessDemand;
		/**
		 * Update the wealth: Wi[t+1] = f (Wi[t])
		 * <p>
		 * bonds = Bi[t+1] <br>
		 * stocks = Si[t+1] <br>
		 * price = p[t+1] <br>
		 */
		double newWealth = newBonds + price * newStocks;
		/**
		 * Display a message error if the bonds, stocks or wealth become
		 * negative.
		 */
		if (newBonds < 0 || newStocks < 0 || newWealth < 0) {
			System.out.println("[Agent.updatePosition()] Error in agent "
					+ agentID
					+ ". Something seems negative where it shouldn't be:");
			System.out
					.println("(price, spread, previousPrice, previousSpread) = ("
							+ round(price, 2)
							+ ", "
							+ round(spread, 2)
							+ ", "
							+ round(previousPrice, 2)
							+ ", "
							+ round(previousSpread, 2) + ")");
			System.out.println("(action, confidence) = (" + action + ", "
					+ round(confidence, 2) + ")");
			System.out
					.println("(bonds, stocks, wealth, newBonds, newStocks, newWealth) = ("
							+ round(bonds, 2)
							+ ", "
							+ round(stocks, 2)
							+ ", "
							+ round(wealth, 2)
							+ ", "
							+ round(newBonds, 2)
							+ ", "
							+ round(newStocks, 2)
							+ ", "
							+ round(newWealth, 2) + ")");
			System.exit(0);
		} else {
			bonds = newBonds;
			stocks = newStocks;
			wealth = newWealth;
		}
	}
	/**
	 * Update the state of the agent on the GUI.
	 */
	public void updateState() {
		state = action;
	}
	/**
	 * toString() method
	 */
	public String toString() {
		String s = "";
		s += "I am an Agent (S=" + noStrategies + ", M=" + memorySize + ", H= "
				+ timeHorizon + ")\n";
		s += setOfStrategies.toString();
		return s;
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
	 * Return some information about the agent accessible on right-click.
	 */
	public ArrayList getDescriptors() {
		//System.out.println("[Agent.getDescriptors()]");
		descriptors.clear();
		descriptors.add(new IntegerDataDescriptor(this, "Memory size",
				"memorySize", memorySize, false));
		descriptors.add(new DoubleDataDescriptor(this, "Confidence",
				"confidence", confidence, false, 1));
		descriptors.add(new IntegerDataDescriptor(this, "Action", "action",
				action, false, 1));
		descriptors.add(new DoubleDataDescriptor(this, "Bonds", "bonds", bonds,
				false, 1));
		descriptors.add(new DoubleDataDescriptor(this, "Stocks", "stocks",
				stocks, false, 1));
		descriptors.add(new DoubleDataDescriptor(this, "Wealth", "wealth",
				wealth, false, 1));
		return descriptors;
	}
	//
	// Getters and Setters
	//
	/**
	 * Get the state of the agent Could be a boolean, an Integer, a Double, etc.
	 */
	public Object getState() {
		//System.out.println("[Agent.getState()]");
		return new Integer(state);
	}
	/**
	 * @return Returns the action.
	 */
	public int getAction() {
		return action;
	}
	/**
	 * @return Returns the stocks.
	 */
	public double getStocks() {
		return stocks;
	}
	/**
	 * @return Returns the excessDemand.
	 */
	public double getExcessDemand() {
		return excessDemand;
	}
}