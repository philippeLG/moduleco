/**
 * Title:   neighbourGame.Agent
 * * Copyright:    Copyright (c)enst-bretagne
 * @author denis.phan@enst-bretagne.fr
* @version 1.2  august,5, 2002
* @version 1.4. february 2004    
 */
package models.adoption;

import java.util.Iterator;
import java.util.ArrayList;

import modulecoFramework.modeleco.EAgent;
import modulecoGUI.grapheco.descriptor.DoubleDataDescriptor;
import modulecoGUI.grapheco.descriptor.BooleanDataDescriptor;
import modulecoFramework.modeleco.randomeco.CRandomDouble;
//import modulecoGUI.grapheco.ControlPanel;

//import modulecoFramework.medium.Medium;
import modulecoFramework.medium.NeighbourMedium;

public class Agent extends EAgent {
	/**
	*
	*/
	protected boolean newState, oldState, nextState;
	private boolean hasChanged;
	protected double theta;
	protected double thetaNeighbourood;
	/**
	*
	*/
	protected CRandomDouble random;
	/**
	*
	*/
	//protected double initProba ;

	int sumNeighbourood;
	public Agent() {
		super();
	}
	/**
	*
	*/
	public void init() {
		neighbours = ((NeighbourMedium) mediums[0]).getNeighbours();
		if (theta > ((World) world).initProba)
			oldState = true;
		else
			oldState = false;
		newState = oldState;
		nextState = newState;
		//if (agentID==1)
		//System.out.println("neighbourGame.init()");
	}
	/**
	*
	*/
	public void compute() {
		ObserveNeighbourood();
		if (sumNeighbourood > 1)
			nextState = true;
		else
			nextState = false;

	}
	/**
	*
	*/
	public void ObserveNeighbourood() {

		Agent dpa;

		if (oldState)
			sumNeighbourood = 1;
		else
			sumNeighbourood = 0;

		for (Iterator i = neighbours.iterator(); i.hasNext();) {
			dpa = (Agent) i.next();
			//if(((Boolean)dpa.getState()).booleanValue())
			if (dpa.oldState)
				sumNeighbourood = sumNeighbourood + 1;

		}

	}
	/**
	*
	*/
	public void commit() {
		oldState = newState;
		newState = nextState;
		hasChanged = (newState != oldState);
	}
	/**
	*
	*/
	public double getTheta() {
		return theta;
	}

	public Object getInverseState() {
		return new Boolean(!newState);
	}

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
		theta = Math.random();
		//if (agentID==1)
		//System.out.println("agent1.getInfo()");
	}
	/**
	*
	*/
	/*
	   public void setTheta(double t){
	      theta = t ;    
	      if (ae != null) ae.update ();     
	   }
	*/
	public void setNewState(boolean b) {
		//System.out.println("setNewState");
		if (theta > 0.5)
			theta = 1 - theta;
		else
			theta = theta + 0.5;
		oldState = newState;
		newState = b;
		System.out.println("gant : " + agentID + " inverseState()");
	}

	public void inverseState() {
		//System.out.println("inverseState()");
		setNewState(!newState);

		if (descriptors.size() != 0) {
			//System.out.println(descriptors.get(0));
			((BooleanDataDescriptor) descriptors.get(0))
				.updateLabelForegroundColor();
		} else
			System.out.println("Pas de liste !!");
	}

	public ArrayList getDescriptors() {
		//System.out.println("getDescriptors()-> clear()");
		descriptors.clear();
		//BooleanDataDescriptor(CAgent ag, String textLabel, String nameVariable, boolean value, boolean editable)
		descriptors.add(
			new BooleanDataDescriptor(
				this,
				"       State        ",
				"newState",
				newState,
				true,
				(newState ? 1 : 2)));

		//IntegerDataDescriptor(CAgent ag, String textLabel, String nameVariable, int variable, boolean editable)
		//IntegerDataDescriptor(CAgent, String textLabel, String nameVariable, int variable, int minVar, int maxVar, boolean editable)

		//DoubleDataDescriptor(CAgent ag, String textLabel, String nameVariable, double doubleVariable, boolean editable)
		//DoubleDataDescriptor(CAgent ag, String textLabel, String nameVariable, double doubleVariable, boolean editable, int precision)
		//DoubleDataDescriptor(CAgent ag, String textLabel, String nameVariable, double doubleVariable, boolean editable, int precision, int codeColor)

		descriptors.add(
			new DoubleDataDescriptor(this, "Theta", "theta", theta, false, 6));
		descriptors.add(
			new DoubleDataDescriptor(
				this,
				" thetaNeighbourood ",
				"thetaNeighbourood",
				thetaNeighbourood,
				false,
				6));
		return descriptors;
	}

	public Boolean hasChanged() {
		boolean temp;

		temp = hasChanged;
		hasChanged = false;
		return new Boolean(temp);
	}
}
