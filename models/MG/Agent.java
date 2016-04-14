/*
 * @(#)Agent.java	1.0	5-Apr-04
 */
package models.MG;
import java.util.ArrayList;
import modulecoFramework.modeleco.EAgent;
import modulecoGUI.grapheco.descriptor.IntegerDataDescriptor;
/**
 * Describe the behaviour of an agent.
 * 
 * Each agent is characterised by a <tt>value</tt> which changes randomly at
 * each time step His <tt>state</tt> depends on the sign of the <tt>value</tt>
 * (green if positive, red if negative)
 * 
 * @author Gilles Daniel (gilles@cs.man.ac.uk)
 * @version 0.9, 8-Mar-04
 * @version 1.0, 5-Apr-04
 */
public class Agent extends EAgent {
	/**
	 * Memory size of the agent
	 */
	protected int memorySize;
	/**
	 * Time horizon, above which the agents forgets his strategy scores
	 */
	protected int timeHorizon;
	/**
	 * Number of strategies
	 */
	protected int noStrategies;
	/**
	 * Set of strategies A strategy maps an input predictions (historical data)
	 * to a prediction (1 or 0)
	 */
	protected SetOfStrategies setOfStrategies;
	/**
	 * The prediction of the agent for next bit
	 */
	protected int prediction;
	/**
	 * His state
	 */
	protected boolean state;
	/**
	 * Wealth of the agent, i.e. his number of points
	 */
	protected int wealth;
	/**
	 * Create a new <tt>agent</tt>
	 */
	public Agent() {
		super();
		//System.out.println("[Agent()]");
	}
	/**
	 * Used by ...
	 */
	public void getInfo() {
		//System.out.println("[Agent.getInfo()]");
	}
	/**
	 * Initialise the agent
	 */
	public void init() {
		System.out.println("[Agent.init()]");
		/**
		 * Initialise the memory size.
		 * <p>
		 * 1 <= M <= maximumMemorySize
		 */
		memorySize = (int) (((World) world).maximumMemorySize * Math.random()) + 1;
		//memorySize = agentID / 10 + 1;
		//System.out.println("[Agent.init()] agentID=" + agentID + ", M=" + memorySize);
		/**
		 * Initialise the time horizon.
		 * <p>
		 * memorySize <= timeHorizon <= maximumTimeHorizon
		 */
		timeHorizon = memorySize * (1 + (int) (5 * Math.random()));
		//System.out.println("[Agent.init()] M= " + memorySize + ", H=" + timeHorizon);
		/**
		 * Initialise the strategies
		 */
		noStrategies = 5;
		setOfStrategies = new SetOfStrategies(noStrategies, memorySize,
				timeHorizon);
		/**
		 * Initialise the prediction
		 */
		prediction = (int) (2 * Math.random());
		/**
		 * Initialise and get the first signal
		 */
		//String signal = "";
		//signal = ((World) world).getSignal(memorySize);
		/**
		 * Initialise the state accordingly
		 */
		updateState();
		/**
		 * Initialise the wealth
		 */
		wealth = 0;
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
		 * End the previous time step
		 */
		/**
		 * Get the previous signal (at time t-1)
		 */
		String previousSignal = ((World) world).getSignal(memorySize);
		/**
		 * Get the final price (at time t-1)
		 */
		int previousPrice = ((World) world).price;
		/**
		 * Update the wealth of the agent
		 */
		updateWealth(previousPrice);
		/**
		 * Update the virtual scores of his strategies
		 */
		setOfStrategies.updateScores(previousSignal, previousPrice);
		//System.out.println("[Agent.commit()]");
		/**
		 * New time step
		 */
		String newSignal = previousSignal.substring(1) + previousPrice;
		/**
		 * Compute the new prediction. Return 1 or 0.
		 */
		prediction = setOfStrategies.getBestPrediction(newSignal);
		//System.out.println("[Agent.compute()] prediction = " + prediction);
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
	 * The agent updates his wealth and the (virtual) scores of his strategies
	 */
	public void commit() {
	}
	/**
	 * Get the state of the agent Could be a boolean, an Integer, a Double,
	 * etc.
	 */
	public Object getState() {
		//System.out.println("[Agent.getState()]");
		return new Boolean(state);
	}
	/**
	 * Hack used by the <em>Canevas</em>
	 * 
	 * @return a boolean
	 */
	public boolean getBooleanState() {
		//System.out.println("[Agent.getBooleanState()]");
		return state;
	}
	/**
	 * Return some information about the agent Accessible on right-click
	 */
	public ArrayList getDescriptors() {
		System.out.println("[Agent.getDescriptors()]");
		descriptors.clear();
		descriptors.add(new IntegerDataDescriptor(this, "Memory size",
				"memorySize", memorySize, false));
		descriptors.add(new IntegerDataDescriptor(this, "Wealth", "wealth",
				wealth, false));
		descriptors.add(new IntegerDataDescriptor(this, "Prediction",
				"prediction", prediction, false));
		return descriptors;
	}
	/**
	 * Prediction accessor.
	 * 
	 * @return prediction, an integer equals to 0 or 1
	 */
	public int getPrediction() {
		//System.out.println("[Agent.getPrediction()]");
		return prediction;
	}
	/**
	 * Set the <tt>Prediction</tt>
	 * 
	 * @param prediction
	 */
	public void setPrediction(int prediction) {
		//System.out.println("[Agent.setPrediction()]");
		this.prediction = prediction;
	}
	/**
	 * wealth accessor
	 * 
	 * @return wealth
	 */
	public double getWealth() {
		//System.out.println("[Agent.getWealth()]");
		return wealth;
	}
	/**
	 * Update the agent's wealth
	 */
	public void updateWealth(int price) {
		//System.out.println("[Agent.updateWealth()]");
		/**
		 * This is a *minority* game. You must be in the smallest group to win
		 */
		if (prediction != price) {
			wealth += 1;
			//if (agentID == 0)
			//	System.out.println("[Agent.updateWealth()] wealth up to " +
			// wealth);
		}
		else
			wealth += -1;
		//System.out.println("welath"+wealth);
	}
	/**
	 * Update the state of the agent
	 */
	public void updateState() {
		if (prediction == 1)
			state = true;
		else
			state = false;
	}
	/**
	 * @return Returns the memorySize.
	 */
	public int getMemorySize() {
		return memorySize;
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
}
