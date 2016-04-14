/*
 * @(#)Agent.java	1.1 09-Mar-04
 */
package models.emptyModel;
import java.util.ArrayList;
import modulecoFramework.modeleco.EAgent;
import modulecoGUI.grapheco.descriptor.IntegerDataDescriptor;
/**
 * Describe the behaviour of an agent.
 * <p>
 * Each agent is characterised by a <tt>value</tt> which changes randomly at
 * each time step. His <tt>state</tt> depends on the sign of the <tt>value</tt>
 * (green if positive, red if negative).
 * 
 * @author Gilles Daniel (gilles@cs.man.ac.uk)
 * @version 1.0, 23-Feb-04
 * @version 1.1, 09-Mar-04
 * @see models.emptyModel.World
 */
public class Agent extends EAgent {
	/**
	 * The value of the agent (1 or -1).
	 */
	protected int value;
	/**
	 * The state of the agent.
	 * <p>
	 * <tt>value</tt>= 1 ---> <tt>state == 'green'<br>
	 * <tt>value</tt> =-1 ---> <tt>state == 'red'
	 */
	protected boolean state;
	/**
	 * Create a new <tt>agent</tt>.
	 */
	public Agent() {
		super();
		//System.out.println("[Agent()]");
	}
	/**
	 * Used by <tt>EAgent</tt>, when a new agent is created.
	 * <p>
	 * Mandatory method (inherited from its abstract superclass in <tt>EAgent</tt>)
	 * 
	 * @see modulecoFramework.modeleco.EAgent
	 * @see modulecoFramework.modeleco.CAgent
	 */
	public void getInfo() {
		//System.out.println("[Agent.getInfo()]");
	}
	/**
	 * Initialise the agent when the <tt>world</tt> is created.
	 */
	public void init() {
		//System.out.println("[Agent.init()]");
		/**
		 * Initialise the value at the creation of the agent
		 */
		value = generateRandomValue();
		/**
		 * Initialise the state of the agent
		 */
		updateState();
		//System.out.println("[models.a.Agent()] agent initiated");
	}
	/**
	 * Implement HERE the behaviour of the agent.
	 * <p>
	 * Invoked at each time step.
	 */
	public void compute() {
		//System.out.println("[Agent.compute()]");
		value = generateRandomValue();
	}
	/**
	 * Just after compute, the agent commit.
	 * <p>
	 * Invoked at each time step
	 */
	public void commit() {
		//System.out.println("[Agent.commit()]");
		updateState();
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
	 * Condiftions to change the state of the agent, and its color in the left
	 * panel (<em>Canevas</em>)
	 *  
	 */
	public void updateState() {
		if (value == 1)
			state = true;
		else
			state = false;
	}
	/**
	 * Hack used by the <em>Canevas</em>
	 * 
	 * @return a boolean
	 * @see models.emptyModel.Canevas
	 */
	public boolean getBooleanState() {
		//System.out.println("[Agent.getBooleanState()]");
		return state;
	}
	/**
	 * Return some information about the agent Accessible on right-click
	 */
	public ArrayList getDescriptors() {
		//System.out.println("[Agent.getDescriptors()]");
		descriptors.clear();
		/**
		 * <ul>
		 * <li>"Value" is the complete name to display when the agent is
		 * edited
		 * <li>"value" is the variable name. A method "setValue()" MUST EXIST
		 * <li>value is the variable itself
		 * <li>false means that you cannot edit the variable
		 * </ul>
		 */
		descriptors.add(new IntegerDataDescriptor(this, "Value", "value",
				value, false));
		return descriptors;
	}
	/**
	 * Value accessor
	 * 
	 * @return value
	 */
	public int getValue() {
		return value;
	}
	/**
	 * Set the <tt>value</tt>
	 * 
	 * @param value
	 */
	public void setValue(int value) {
		this.value = value;
	}
	/**
	 * @return a random value equal to 1 or -1
	 */
	public int generateRandomValue() {
		int exp = (int) (2 * Math.random()) + 1; // exp = 0 or 1
		return (int) Math.pow(-1, exp); // return 1 or -1
	}
}
