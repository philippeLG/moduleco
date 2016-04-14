/*
 * @(#)Strategy.java	1.0 5-Apr-2004
 */
package models.MG;
/**
 * Describe one strategy. A strategy maps an input signal, such as a string of
 * bits ("1101"), to a decision (1 or 0). A strategy has a score, updated at
 * each time step, according to its prediction power.
 * 
 * @author Gilles Daniel (gilles@cs.man.ac.uk)
 * @version 0.9, 8-Mar-04
 * @version 1.0, 5-Apr-2004
 */
public class Strategy {
	/**
	 * A strategy maps a specific string signal of M bits to a prediction.
	 * Here, the signal is transformed into a base-10 integer to get the index
	 * of the prediction to return. The prediction if the content of
	 * predictions[index]
	 */
	protected int[] predictions;
	/**
	 * The points of a strategy, for the last H time steps, where H = time
	 * horizon.
	 * <p>
	 * Indeed, agents forget the points above that threshold.
	 */
	protected int[] points;
	/**
	 * The virtual score of a strategy, indicating the success of this strategy
	 * if it had been played, evry time.
	 */
	protected int score;
	/**
	 * Memory size, corresponds to the size of the input signal.
	 */
	protected int memorySize;
	/**
	 * Time horizon, above which agents forget their strategy points.
	 */
	protected int timeHorizon;
	/**
	 * Number of different cases == 2^memorySize. Correspond to the number of
	 * possible input signals.
	 */
	protected int noCases;
	/**
	 * Build a strategy
	 *  
	 */
	public Strategy(int memorySize, int timeHorizon) {
		/**
		 * Initialise the memorySize
		 */
		this.memorySize = memorySize;
		/**
		 * Initialise the memorySize
		 */
		this.timeHorizon = timeHorizon;
		/**
		 * Initialise the number of cases
		 */
		noCases = (int) Math.pow(2, memorySize);
		/**
		 * Initialise randomly the strategy predictions
		 */
		predictions = new int[noCases];
		for (int i = 0; i < noCases; i++)
			predictions[i] = (int) (2 * Math.random()); // Return 0 or 1
		/**
		 * Initialise the points.
		 */
		points = new int[timeHorizon];
		for (int i = 0; i < timeHorizon; i++)
			points[i] = 0;
	}
	/**
	 * Return the prediction of this strategy, given an input signal
	 * 
	 * @param signal
	 *            is a bit string, e.g. "1101"
	 * @return prediction is an int, e.g. 1 or 0
	 */
	public int getPrediction(String signal) {
		/**
		 * Get the input number, or index
		 */
		int index = signalToIndex(signal);
		/**
		 * Return the corresponding prediction
		 */
		return this.predictions[index];
	}
	/**
	 * Maps an input signal to a strategy index. Here, the input signal is a
	 * bit string ("1101") and the output an integer. E.g. input "110" output 6
	 * 
	 * @param signal
	 * @return index
	 */
	public int signalToIndex(String signal) {
		return Integer.valueOf(signal, 2).intValue();
	}
	/**
	 * Returns the score of this strategy.
	 * 
	 * @return score
	 */
	public int getScore() {
		return score;
	}
	/**
	 * Update the score of the strategy. This is the reward function of the
	 * strategy.
	 * 
	 * @param signal
	 * @param price
	 */
	public void updateScore(String signal, int price) {
		/**
		 * Get the prediction of this specific strategy, given the input signal
		 */
		int tmpPrediction = getPrediction(signal);
		/**
		 * This is a *minority* game. You have to be in the smallest group to
		 * win.
		 * <p>
		 * If you predicted to be in group A, and the price goes up, indicating
		 * that the majority of people went into group A, then you do not
		 * score.
		 */
		int success = 0;
		if (tmpPrediction != price)
			success = 1;
		else
			success = -1;
		/**
		 * Update the array of points by forgetting the oldest one, shifting the
		 * array, and adding the new point.
		 */
		for (int i=0;i<timeHorizon-1;i++)
			points[i] = points[i+1];
		points[timeHorizon-1] = success;
		/**
		 * Update the score.
		 */
		score = 0;
		for (int i=0;i<timeHorizon;i++)
			score += points[i]; 
	}
	/**
	 * toString() method
	 */
	public String toString() {
		String s = "";
		//s += "\t\t I am a Strategy (score=" + score + ", predictions=";
		//for (int i = 0; i < noCases; i++)
		//	s += predictions[i];
		s += "\t\t I am a Strategy (score=" + score;
		s += ")\n";
		return s;
	}
}
