/*
 * @(#)Strategy.java 1.0 5-Apr-2004
 */
package models.GCMG;
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
	 * Memory size, corresponds to the size of the input signal.
	 */
	protected int memorySize;
	/**
	 * A strategy maps a specific string signal of M bits to a prediction. Here,
	 * the signal is transformed into a base-10 integer to get the index of the
	 * prediction to return. The prediction if the content of
	 * predictions[index].
	 */
	protected int[] predictions;
	/**
	 * Time horizon, above which agents forget their strategy points.
	 */
	protected int timeHorizon;
	/**
	 * The prediction success of a strategy, over the last H time steps, where H =
	 * time horizon. +1 for a correct prediction, -1 for an incorrect prediction
	 * <p>
	 * Above their time horizon H, strategies forget their prediction success.
	 */
	protected int[] predictionSuccess;
	/**
	 * The virtual score of a strategy, indicating its success over the last H
	 * time steps. <br>
	 * -H <= score <= H
	 */
	protected int score;
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
		 * Initialise the number of cases.
		 */
		int noCases = (int) Math.pow(2, memorySize);
		/**
		 * Initialise randomly the strategy predictions. Either 1 or -1;
		 */
		predictions = new int[noCases];
		for (int i = 0; i < noCases; i++)
			predictions[i] = (Math.random() > 0.5 ? 1 : -1);
		/**
		 * Initialise the prediction success. Either 1 or -1;
		 */
		predictionSuccess = new int[timeHorizon];
		for (int i = 0; i < timeHorizon; i++) {
			predictionSuccess[i] = (Math.random() > 0.5 ? 1 : -1);
			//predictionSuccess[i] = 0;
		}
	}
	/**
	 * Return the prediction of this strategy, given an input signal
	 * 
	 * @param signal
	 *            is a bit string, e.g. "1101"
	 * @return prediction is an int, e.g. 1 or 0
	 */
	public int getPrediction(int[] signal) {
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
	 * Maps an input signal to a strategy index. Here, the input signal is a bit
	 * string ("1 1 -1 1") and the output an integer. E.g. input "1 1 -1" output
	 * 6
	 * 
	 * @param signal
	 * @return index
	 */
	public int signalToIndex(int[] signal) {
		/**
		 * Transform the signal into a string
		 */
		String binarySignal = "";
		for (int i = 0; i < memorySize; i++)
			binarySignal += signal[i];
		/**
		 * Transform -1 into 0. E.g. "1 1 -1" -> "1 1 0"
		 */
		binarySignal = binarySignal.replaceAll("-1", "0");
		/**
		 * Perform a binary translation. E.g. "1 1 0" -> 6
		 */
		int index = Integer.valueOf(binarySignal, 2).intValue();
		//System.out.println(memorySize + ": signal[0]: " + signal[0] + " -> "
		//		+ binarySignal + " -> " + index);
		return index;
	}
	/**
	 * Update the score of the strategy. This is the reward function of the
	 * strategy.
	 * 
	 * @param signal
	 * @param price
	 */
	public void updateScore(int[] signal, int priceMove) {
		/**
		 * Get the prediction of this specific strategy, given the input signal
		 */
		int tmpPrediction = getPrediction(signal);
		/**
		 * This is a *minority* game. You have to be in the smallest group to
		 * win.
		 * <p>
		 * If you predicted to be in group A, and the price goes up, indicating
		 * that the majority of people went into group A, then you do not score.
		 */
		int success = 0;
		if (tmpPrediction == priceMove)
			success = -1;
		else
			success = 1;
		/**
		 * Update the array of points by forgetting the oldest one, shifting the
		 * array, and adding the new point.
		 */
		for (int i = 0; i < timeHorizon - 1; i++)
			predictionSuccess[i] = predictionSuccess[i + 1];
		predictionSuccess[timeHorizon - 1] = success;
		/**
		 * Update the strategy virtual score. This score is simply an
		 * aggregation of the last H prediction success.
		 * <p>
		 * e.g. [1 1 1 -1] --> score = 2
		 */
		score = 0;
		for (int i = 0; i < timeHorizon; i++)
			score += predictionSuccess[i];
	}
	/**
	 * toString() method
	 */
	public String toString() {
		String s = "";
		//s += "\t\t I am a Strategy (score=" + score + ", predictions=";
		//for (int i = 0; i < (int) Math.pow(2, memorySize); i++)
		//	s += predictions[i];
		s += "\t\t I am a Strategy (score, predictionSuccess) = (" + score
				+ ", ";
		for (int i = 0; i < timeHorizon; i++)
			s += predictionSuccess[i];
		s += ")\n";
		return s;
	}
	/**
	 * Returns the score of this strategy.
	 * 
	 * @return score
	 */
	public int getScore() {
		return score;
	}
}