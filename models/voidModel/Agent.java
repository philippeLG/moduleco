/**
 * Title:   voidModel.Agent
 * * Copyright:    Copyright (c)enst-bretagne
 * @author denis.phan@enst-bretagne.fr
 * @version 1.2  august,5, 2002
  * @version 1.4. february 2004  
 */
package models.voidModel;

// import java.util.Iterator;
import java.util.ArrayList;

import modulecoFramework.modeleco.EAgent;
import modulecoGUI.grapheco.descriptor.BooleanDataDescriptor;
import modulecoFramework.medium.NeighbourMedium;

public class Agent extends EAgent {
	protected double m;
	/**
	*
	*/
	protected boolean newState, oldState, nextState;
	/**
	*
	*/
	protected ArrayList neighbours;
	/**
	*
	*/
	public Agent() {
		super();
	}
	/**
	*
	*/
	public void init() {
		neighbours = ((NeighbourMedium) mediums[0]).getNeighbours();

		//oldStrategy = ((world.indexOf(this) == (world.getCapacity()/2)) ? false : true);

	}
	/**
	*
	*/
	public void compute() {

	}

	/**
	*
	*/
	public void commit() {
		oldState = newState;
		newState = nextState;
	}
	/**
	*
	*/
	public Object getState() {
		return new Boolean(newState);
	}
	/**
	*
	*/
	public String toString() {
		String s;
		s = "State : " + (new Boolean(newState)).toString();
		return s;
	}
	/**
	*
	*/
	public void getInfo() {

	}
	/**
	*
	*/

	public void setNewState(boolean b) {
		oldState = newState;
		newState = b;
		System.out.println("gant : " + agentID + " inverseState()");
	}
	public void inverseState() {
		setNewState(!newState);
	}

	public ArrayList getDescriptors() {
		descriptors.clear();
		//BooleanDataDescriptor(CAgent ag, String textLabel, String nameVariable, boolean value, boolean editable)
		descriptors.add(
			new BooleanDataDescriptor(
				this,
				"       State        ",
				"newState",
				newState,
				true));

		//IntegerDataDescriptor(CAgent ag, String textLabel, String nameVariable, int variable, boolean editable)
		//IntegerDataDescriptor(CAgent, String textLabel, String nameVariable, int variable, int minVar, int maxVar, boolean editable)

		//DoubleDataDescriptor(CAgent ag, String textLabel, String nameVariable, double doubleVariable, boolean editable)
		//DoubleDataDescriptor(CAgent ag, String textLabel, String nameVariable, double doubleVariable, boolean editable, int precision)
		//DoubleDataDescriptor(CAgent ag, String textLabel, String nameVariable, double doubleVariable, boolean editable, int precision, int codeColor)

		return descriptors;
	}
}
