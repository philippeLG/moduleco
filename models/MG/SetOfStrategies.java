/*
 * @(#)SetOfStrategies.java	1.0 5-Apr-2004
 */
package models.MG;
/**
 * Deals with the strategies and their (virtual) score
 * 
 * @author Gilles Daniel (gilles@cs.man.ac.uk)
 * @version 0.9, 8-Mar-04
 * @version 1.0, 5-Apr-2004
 */
public class SetOfStrategies {
	/**
	 * Number of strategies
	 */
	protected int noStrategies;
	/**
	 * The strategies themselves are stored in an array of Strategies
	 */
	protected Strategy[] strategies;
	/**
	 * Build the strategies
	 * 
	 * @param noStrategies
	 * @param memorySize
	 * @param timeHorizon
	 */
	public SetOfStrategies(int noStrategies, int memorySize, int timeHorizon) {
		/**
		 * Initialise the strategies
		 */
		this.noStrategies = noStrategies;
		strategies = new Strategy[noStrategies];
		for (int i = 0; i < noStrategies; i++)
			strategies[i] = new Strategy(memorySize, timeHorizon);
	}
	/**
	 * Return the prediction of the best strategies
	 * 
	 * @param signal
	 *            is a bit string, e.g. "1101"
	 * @return 1 or 0 is an integer representing the best prediction for this
	 *         signal
	 */
	public int getBestPrediction(String signal) {
		/**
		 * Find the index of the best strategy
		 */
		int bestScore = Integer.MIN_VALUE;
		int bestStrategy = 0;
		for (int j = 0; j < noStrategies; j++) {
			int tmpBestScore = strategies[j].getScore();
			if (tmpBestScore > bestScore) {
				bestScore = tmpBestScore;
				bestStrategy = j;
			}
		}
		//System.out.println("Best strategy ("+ bestStrategy +"), best score
		// ("+ strategies[bestStrategy].getScore() +"): " + signal + "--->"+
		// strategies[bestStrategy].getPrediction(signal));
		/**
		 * Return the prediction of that particular strategy
		 */
		return strategies[bestStrategy].getPrediction(signal);
	}
	/**
	 * Update the virtual scores of the strategies
	 */
	public void updateScores(String signal, int price) {
		/**
		 * Update the score of all strategies
		 */
		for (int i = 0; i < noStrategies; i++)
			strategies[i].updateScore(signal, price);
	}
	/**
	 * toString() method
	 */
	public String toString() {
		String s = "";
		//s += "\t I am a SetOfStrategies (noStrategies=" + noStrategies
		// +")\n";
		for (int i = 0; i < noStrategies; i++)
			s += strategies[i].toString();
		return s;
	}
}
