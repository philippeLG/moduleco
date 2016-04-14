/**
 * ModelParameters.java
 * Copyright (c) The University of Manchester
 * @author gilles.daniel@cs.man.ac.uk
 * @version 0.1 June 2004
 */
package modulecoFramework.modeleco;
/**
 * Represent the 4 initial parameters of a model: <br>
 * name; <br>
 * length; <br>
 * neighbourhood; <br>
 * zone; <br>
 * time scheduler; <br>
 */
public class ModelParameters {
	protected String name = "";
	protected String stringLength = "";
	protected String neighbourhood = "";
	protected String zone = "";
	protected String timeScheduler = "";
	protected int length = 0;
	/**
	 * Simple constructor.
	 *  
	 */
	public ModelParameters() {
	}
	/**
	 * Constructor used when the world corresponding to this model is created
	 * for the very first time.
	 *  
	 */
	public ModelParameters(String name, String stringLength, String neighbourhood,
			String zone, String timeScheduler) {
		this.name = name;
		this.stringLength = stringLength;
		this.neighbourhood = neighbourhood;
		this.zone = zone;
		this.timeScheduler = timeScheduler;
		this.length = Integer.parseInt(stringLength);
	}
	/**
	 * @return Returns the length.
	 */
	public String getStringLength() {
		return stringLength;
	}
	/**
	 * Set both stringLength and length.
	 * 
	 * @param strinLength
	 *  
	 */
	public void setStringLength(String stringLength) {
		this.stringLength = stringLength;
		this.length = Integer.parseInt(stringLength);
	}
	/**
	 * @return Returns the neighbourhood.
	 */
	public String getNeighbourhood() {
		return neighbourhood;
	}
	/**
	 * @param neighbourhood
	 *            The neighbourhood to set.
	 */
	public void setNeighbourhood(String neighbourhood) {
		this.neighbourhood = neighbourhood;
	}
	/**
	 * @return Returns the timeScheduler.
	 */
	public String getTimeScheduler() {
		return timeScheduler;
	}
	/**
	 * @param timeScheduler
	 *            The timeScheduler to set.
	 */
	public void setTimeScheduler(String timeScheduler) {
		this.timeScheduler = timeScheduler;
	}
	/**
	 * @return Returns the zone.
	 */
	public String getZone() {
		return zone;
	}
	/**
	 * @param zone
	 *            The zone to set.
	 */
	public void setZone(String zone) {
		this.zone = zone;
	}
	/**
	 * @return Returns the length.
	 */
	public int getLength() {
		return length;
	}
	/**
	 * @param length
	 *            The length to set.
	 */
	public void setLength(int length) {
		this.length = length;
	}
	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}
}