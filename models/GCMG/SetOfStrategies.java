/*
 * @(#)SetOfStrategies.java 1.0 5-Apr-2004
 */
package models.GCMG;
import java.util.ArrayList;
import java.lang.Integer;
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
	protected int[] tmpScores;
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
		tmpScores = new int[noStrategies];
	}
	/**
	 * Return the index of the strategy with the highest score. If more than one
	 * strategy have the best score, we toss a coin.
	 */
	public int getBestStrategy() {
		/**
		 * Get the score of the strategies.
		 */
		for (int i = 0; i < noStrategies; i++)
			tmpScores[i] = strategies[i].getScore();
		/**
		 * Get the best score.
		 */
		int bestScore = Integer.MIN_VALUE;
		for (int i = 0; i < noStrategies; i++)
			if ( tmpScores[i] > bestScore)
				bestScore = tmpScores[i];
		//System.out.println("\nbestScore = " + bestScore);		
		/**
		 * Get the list of strategies that have this best score.
		 */
		ArrayList bestStrategies = new ArrayList();
		int noBestStrategies = 0;
		for (int i = 0; i < noStrategies; i++)
			if (tmpScores[i] == bestScore){
				noBestStrategies ++;
				bestStrategies.add(new Integer(i));
				//System.out.println("Strategy " + i + " is a best strategy (score = " + tmpScores[i] + ")");	
			}
		/**
		 * Randomly select one of the best strategies
		 */	
		int bestStrategy = ((Integer)bestStrategies.get((int)(noBestStrategies * Math.random()))).intValue();	
		/**
		 * Uncomment the following to return a random strategy
		 */
		//bestStrategy = (int) (noStrategies * Math.random());
		//System.out.println("bestStrategy = " + bestStrategy + " out of " + noBestStrategies);
		return bestStrategy;
	}
	/**
	 * Get the score of a given strategy
	 * 
	 * @param strategyNumber
	 * @return
	 */
	public int getScore(int strategyNumber) {
		return strategies[strategyNumber].getScore();
	}
	/**
	 * Return the prediction of a given strategy. Either 1 or -1.
	 * 
	 * @param strategyNumber
	 * @param signal
	 * @return
	 */
	public int getPrediction(int strategyNumber, int[] signal) {
		//return (Math.random() > 0.5 ? 1 : -1);
		return strategies[strategyNumber].getPrediction(signal);
	}
	/**
	 * Update the virtual scores of the strategies
	 */
	public void updateScores(int[] signal, int priceMove) {
		/**
		 * Update the score of all strategies
		 */
		for (int i = 0; i < noStrategies; i++)
			strategies[i].updateScore(signal, priceMove);
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