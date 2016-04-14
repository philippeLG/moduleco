/**
 * Title:   moduleco.models.emergenceClasses.Agent
 * Reference : Axtell, Epstein, Young (2000)
 * http://www.brookings.edu/es/dynamics/papers/classes/
 * * Copyright:    Copyright (c)enst-bretagne
 * @author denis.phan@enst-bretagne.fr
 * * @version 1.4.1 april 2004  
 */
package models.emergenceClasses;

//import java.util.Iterator;
import java.util.ArrayList;

import modulecoFramework.modeleco.EAgent;
import modulecoFramework.modeleco.randomeco.CRandomInt;
import modulecoFramework.modeleco.randomeco.CRandomDouble;
// import modulecoGUI.grapheco.descriptor.BooleanDataDescriptor;
// import modulecoFramework.medium.Medium;
import modulecoFramework.medium.NeighbourMedium;

public class Agent extends EAgent {
	protected double m;
	/**
	* Strategy code low = 0, medium = 1, high = 2
	*/
	protected int[] newStrategy, oldStrategy, nextStrategy;
	protected int lowPayoff, mediumPayoff, highPayoff;
	/**
	*
	*/
	protected ArrayList neighbours;
	/**
	*
	*/
	protected boolean tag;
	/**
	*
	*/
	protected CRandomInt randomDot, randomDotTag;
	protected CRandomDouble randomTremble;
	protected int agentMemoryLength;
	protected AgentFeature[] af;
	protected boolean testTag;
	protected int imax;

	public Agent() {
		super();
	}
	/**
	*
	*/
	public void init() {
		testTag = (((World) world).getTag()).equalsIgnoreCase("2 tags");
		if (testTag)
			imax = 2;
		else
			imax = 1;

		this.agentMemoryLength = ((World) world).memoryLength;
		this.randomDot = ((World) world).randomDot;
		this.randomTremble = ((World) world).randomTremble;
		//if (agentID == 0) System.out.println("models.emergenceClasses.Agent.init() : "+testTag);
		if (testTag) {
			this.randomDotTag = ((World) world).randomDotTag;
			tag = (randomDot.getInt(2) == 0);
			//System.out.print(" ID = "+agentID+" t = "+tag);
		}

		lowPayoff = ((World) world).PayoffMatrix[2][0];
		mediumPayoff = ((World) world).PayoffMatrix[1][1];
		highPayoff = ((World) world).PayoffMatrix[0][2];

		af = new AgentFeature[imax];
		newStrategy = new int[imax];
		oldStrategy = new int[imax];
		nextStrategy = new int[imax];

		for (int i = 0; i < imax; i++) {
			oldStrategy[i] = randomDot.getInt(3);
			af[i] = new AgentFeature(this, agentMemoryLength);
			if ((((World) world).getInitialLocation())
				.equalsIgnoreCase("Random"))
				initializeMemoryAtRandom(i);
			else
				initializeMemoryHighLow(i);
			af[i].computeStrategicLandscape();
			if (testTag)
				af[i].setName(i == 0 ? "WITHIN" : "BETWEEN");
			else
				af[i].setName("noTag");
			af[i].buildSimplexFeature();
			((World) world).simplexFeatureSet[i].add(this.agentID, af[i].asf);

		}
	}

	public void initializeMemoryAtRandom(int j) {
		for (int i = 0; i < agentMemoryLength; i++)
			af[j].strategyMemory.add((Object) new Integer(randomDot.getInt(3)));
	}

	public void initializeMemory(int strategy, int j) {
		for (int i = 0; i < agentMemoryLength; i++)
			af[j].strategyMemory.add((Object) new Integer(strategy));

	}

	public void initializeMemoryHighLow(int j) {
		for (int i = 0; i < agentMemoryLength; i++)
			if (randomDot.getInt(2) == 0)
				af[j].strategyMemory.add((Object) new Integer(0));
		//Strategy.LOW_STRATEGY;
		else
			af[j].strategyMemory.add((Object) new Integer(2));
		// Strategy.HIGH_STRATEGY;
	}
	/**
	*
	*/
	public void compute() {
		//if (agentID == 0) System.out.println();
		int typeNeighbourhood;
		neighbours = ((NeighbourMedium) mediums[0]).getNeighbours();
		Agent neighbour = (Agent) neighbours.get(0);
		if (testTag) {
			if (neighbour.tag == this.tag)
				typeNeighbourhood = 0;
			else
				typeNeighbourhood = 1;
		} else
			typeNeighbourhood = 0;
		af[typeNeighbourhood].strategyMemory.remove(0);
		af[typeNeighbourhood].strategyMemory.add(
			(Object) new Integer(neighbour.newStrategy[typeNeighbourhood]));

		if (randomTremble.getDouble() > ((World) world).getTremble())
			nextStrategy[typeNeighbourhood] =
				computeNextStrategy(typeNeighbourhood);
		else
			nextStrategy[typeNeighbourhood] = randomDot.getInt(3);
	}

	/**
	* strategyMemory
	*/

	public int computeNextStrategy(int i) {
		// Strategy 
		af[i].computeStrategicLandscape();
		double lowExpectedPayoff = lowPayoff;
		double mediumExpectedPayoff =
			mediumPayoff * (af[i].lowFrequency + af[i].mediumFrequency);
		double highExpectedPayoff = highPayoff * af[i].lowFrequency;
		return computeBestReply(
			lowExpectedPayoff,
			mediumExpectedPayoff,
			highExpectedPayoff);
	}

	protected int computeBestReply(
		double lowExpectedPayoff,
		double mediumExpectedPayoff,
		double highExpectedPayoff) { //			case 1
		if (lowExpectedPayoff > mediumExpectedPayoff) {
			if (lowExpectedPayoff > highExpectedPayoff)
				return 0;
			if (lowExpectedPayoff < highExpectedPayoff)
				return 2;
			if (lowExpectedPayoff == highExpectedPayoff) {
				if (randomDot.getInt(2) == 0)
					return 0;
				else
					return 2;
			}
		} //		case 2
		if (lowExpectedPayoff < mediumExpectedPayoff) {
			if (mediumExpectedPayoff > highExpectedPayoff)
				return 1;
			if (mediumExpectedPayoff < highExpectedPayoff)
				return 2;
			if (randomDot.getInt(2) == 0)
				return 1;
			else
				return 2;
		} //		case 3
		if (highExpectedPayoff > lowExpectedPayoff)
			return 2;
		if (highExpectedPayoff < lowExpectedPayoff)
			if (randomDot.getInt(2) == 0)
				return 0;
			else
				return 1;
		//			case 4
		return randomDot.getInt(3);
	} /**
						* strategyMemory
						*/
	public void commit() {
		for (int i = 0; i < imax; i++) {
			oldStrategy[i] = newStrategy[i];
			newStrategy[i] = nextStrategy[i];
			af[i].computeStrategicLandscape();
			af[i].updateStrategicLandscape();
		} 
	}
/**
 *
 */
	public void setSimplexFeature(SimplexFeature sp) {
	}
	
	public Object getState() { // inutile
		return new Boolean(true);
	} /**
	*
	*/
	public String toString() {
		String s ="";
		//s = "Strategy : " + (new Integer(newStrategy)).toString();
		return s;
	} /**
		*
		*/
	public void getInfo() {

	} /**
	*
	*/ /*
	public void setNewState(boolean b){
	 oldState=newState;
	 newState = b ;    
	 if (ae != null) ae.update ();      }
	
	public void inverseState(){
	 setNewState(!newState);
	}
	*/
	public ArrayList getDescriptors() {
		descriptors.clear();
		//BooleanDataDescriptor(CAgent ag, String textLabel, String nameVariable, boolean value, boolean editable)
		//descriptors.add(new BooleanDataDescriptor(this,"       State        ","newState",newState,true ));
		//IntegerDataDescriptor(CAgent ag, String textLabel, String nameVariable, int variable, boolean editable)
		//IntegerDataDescriptor(CAgent, String textLabel, String nameVariable, int variable, int minVar, int maxVar, boolean editable)
		//DoubleDataDescriptor(CAgent ag, String textLabel, String nameVariable, double doubleVariable, boolean editable)
		//DoubleDataDescriptor(CAgent ag, String textLabel, String nameVariable, double doubleVariable, boolean editable, int precision)
		//DoubleDataDescriptor(CAgent ag, String textLabel, String nameVariable, double doubleVariable, boolean editable, int precision, int codeColor)
		return descriptors;
	}
}
