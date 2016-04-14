/**
 * Empirical.java
 * Copyright (c) The University of Manchester
 * @author gilles.daniel@cs.man.ac.uk
 * @version 0.1 June 2004
 */
package models.GCMG;
import modulecoFramework.modeleco.randomeco.JavaGaussian;
/**
 * Get some empirical data from an external source.
 *  
 */
public class Empirical {
	/**
	 * Number of elements in the empirical time series.
	 */
	protected int noElements;
	protected double[] price;
	protected double[] logReturn;
	protected JavaGaussian gauss;
	/**
	 * 
	 *  
	 */
	public Empirical(int noElements) {
		this.noElements = noElements;
		price = new double[noElements];
		logReturn = new double[noElements];
		gauss = new JavaGaussian();
		/**
		 * Generate the empirical data.
		 */
		generate();
	}
	/**
	 * Generate (or get from an external source) the empirical time series.
	 *  
	 */
	void generate() {
		logReturn[0] = 0;
		price[0] = 10;
		for (int tmp = 1; tmp < noElements; tmp++) {
			logReturn[tmp] = 0.05 * gauss.nextGaussian();
			price[tmp] = price[tmp-1] * Math.exp(logReturn[tmp]);
		}
	}
	/**
	 * Get a specific element of the price time series
	 */
	public double getPrice(int elementNumber) {
		return price[elementNumber];
	}
	/**
	 * Get a specific element of the excess demand time series
	 */
	public double getLogReturn(int elementNumber) {
		return logReturn[elementNumber];
	}
}