/**
 * Title: models.smallWorld.Agent<p>
 * Description:  Chaque Agent joue avec ses voisins et adopte, pour le tour suivant, la
 * stat?gie de celui qui a le meilleur gain. Le gain est r?initialis? ? chaque tour.
 * La matrice de gain est la m?me pour tous les Agents ; elle est enregistr? par le World.<p>
 * Copyright:    Copyright (c)enst-bretagne
 * @author Antoine.Beugnard@enst-bretagne.fr, denis.phan@enst-brtetagne.fr
 * @version 1.4  February, 2004  
 */
package models.smallWorld;

import java.util.Iterator;
import java.util.ArrayList;

import modulecoFramework.modeleco.EAgentLinks;
import modulecoGUI.grapheco.descriptor.IntegerDataDescriptor;
import modulecoGUI.grapheco.descriptor.DoubleDataDescriptor;
import modulecoGUI.grapheco.descriptor.BooleanDataDescriptor;
import modulecoGUI.grapheco.descriptor.InfoDescriptor;

// import modulecoFramework.medium.NeighbourMedium;

public class Agent extends EAgentLinks {
	protected int gain = 0;

	protected boolean newStrategy, oldStrategy, nextStrategy;
	private boolean hasChanged;
	protected String revisionRuleIndex;
	protected double finalAveragePayoff1;
	protected double finalAveragePayoff2;

	protected int nbNeighbour; // mod DI 25/06/2002

	public Agent() {
		super();
	}

	public void init() {
		super.init();
		//oldStrategy = ((world.indexOf(this) == (world.getCapacity()/2)) ? false : true);OBSOLETE	
		oldStrategy = true;
		if (agentID == 0
			|| agentID == world.getAgentSetSize() / 4
			|| agentID == world.getAgentSetSize() / 2
			|| agentID == 3 * world.getAgentSetSize() / 4) {
			newStrategy = false; // The agent make a mistake
			nextStrategy = oldStrategy; // false; > generate a cycle
		} else {
			newStrategy = oldStrategy;
			nextStrategy = newStrategy;
		}
		revisionRuleIndex = ((World) world).getRevisionRuleIndex();
	}

	public void compute() {

		hasChanged = false;
		gain = 0;
		oldStrategy = newStrategy;

		for (Iterator i = neighbours.iterator(); i.hasNext();) {
			gain
				+= ((World) world).matriceGain[(newStrategy ? 0 : 1)][(
					((Agent) i.next()).newStrategy ? 0 : 1)];
		}
	}

	public void commit() {
		revisionRuleIndex = ((World) world).getRevisionRuleIndex();
		if (revisionRuleIndex
			.equalsIgnoreCase("Last neighbourhood best payoff")) {
			ComputeBestPayoff();
		}
		if (revisionRuleIndex
			.equalsIgnoreCase("Last neighbourhood best average payoff")) {
			ComputeBestAveragePayoff();
		}
		hasChanged = (newStrategy != oldStrategy);
		//if (agentID ==0)
		//System.out.println("agent.commit()");
	}

	public void ComputeBestPayoff() {
		int gainMax = gain;
		Agent dpa;

		for (Iterator i = neighbours.iterator(); i.hasNext();) {
			dpa = (Agent) i.next();
			if (gainMax < dpa.gain) {
				nextStrategy = dpa.oldStrategy;
				gainMax = dpa.gain;
			}
		}
		newStrategy = nextStrategy;
	}
	public void ComputeBestAveragePayoff() {
		Agent dpa;
		int n1 = 0;
		int n2 = 0;
		int payoff1 = 0;
		int payoff2 = 0;
		double averagePayoff1;
		double averagePayoff2;

		for (Iterator i = neighbours.iterator(); i.hasNext();) {
			dpa = (Agent) i.next();
			if (dpa.oldStrategy) {
				n1++;
				payoff1 = payoff1 + dpa.gain;
			} else {
				n2++;
				payoff2 = payoff2 + dpa.gain;
			}
		}
		if (n1 == 0)
			averagePayoff1 = 0;
		else
			averagePayoff1 = payoff1 / n1;

		if (n2 == 0)
			averagePayoff2 = 0;
		else
			averagePayoff2 = payoff2 / n2;

		if (averagePayoff1 > averagePayoff2)
			nextStrategy = true;

		if (averagePayoff2 > averagePayoff1)
			nextStrategy = false;

		if (averagePayoff1 == averagePayoff2)
			nextStrategy = oldStrategy;
		newStrategy = nextStrategy;
		finalAveragePayoff1 = averagePayoff1;
		finalAveragePayoff2 = averagePayoff2;
	}
	public Boolean hasChanged() {
		return new Boolean(hasChanged);
	}

	public Object getState() {
		return new Boolean(newStrategy);
	}
	public boolean getOldStrategy() {
		return oldStrategy;
	}

	public String toString() {
		String s;
		s =
			(new Boolean(newStrategy)).toString()
				+ " g = "
				+ (new Integer(gain)).toString();
		return s;
	}

	public void getInfo() {
		revisionRuleIndex = ((World) world).getRevisionRuleIndex();
	}

	public ArrayList getDescriptors() {
		descriptors.clear();
		descriptors.add(
			new IntegerDataDescriptor(
				this,
				"Total AgentPayoff",
				"gain",
				gain,
				0,
				10,
				true));
		descriptors.add(
			new IntegerDataDescriptor(
				this,
				"Neighbour Size",
				"nbNeighbour",
				nbNeighbour,
				0,
				10,
				true));
		// mod DI 25/06/2002
		descriptors.add(
			new DoubleDataDescriptor(
				this,
				"Average Payoff S1",
				"finalAveragePayoff1",
				finalAveragePayoff1,
				true,
				6));
		descriptors.add(
			new DoubleDataDescriptor(
				this,
				"Average Payoff S2",
				"finalAveragePayoff2",
				finalAveragePayoff2,
				true,
				6));
		descriptors.add(
			new BooleanDataDescriptor(
				this,
				"New Strategy",
				"newStrategy",
				newStrategy,
				true));
		descriptors.add(
			new BooleanDataDescriptor(
				this,
				"Old Strategy",
				"oldStrategy",
				oldStrategy,
				false));
		descriptors.add(new InfoDescriptor(revisionRuleIndex));
		return descriptors;
	}
	public int getGain() {

		return gain;
	}

	public void setGain(int g) {
		gain = g;
	}
	public void setFinalAveragePayoff1(double f) {
		finalAveragePayoff1 = f;
	}
	public void setFinalAveragePayoff2(double f) {
		finalAveragePayoff2 = f;
	}
	public void setNewStrategy(boolean b) {
		newStrategy = b;
	}
	public void inverseState() {
		setNewStrategy(!newStrategy);
	}
	public void setOldStrategy(boolean b) {
		oldStrategy = b;
	}

}
