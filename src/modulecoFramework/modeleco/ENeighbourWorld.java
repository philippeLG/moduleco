/**
 * ENeighbourWorld.java
 * Copyright (c)enst-bretagne
 * @author Antoine.Beugnard@enst-bretagne.fr, Denis.Phan@enst-bretagne.fr, philippe.legoff@enst-bretagne.fr
 * @version 1.0  May 2000
 * @version 1.2  August 2002
 * @version 1.4  February 2004
 */
package modulecoFramework.modeleco;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Hashtable;
import modulecoFramework.medium.Medium;
import modulecoGUI.grapheco.descriptor.DataDescriptor;
/**
 * Represent the World as a collection of neighbour agents. <br>
 * Used to populate the Agents.
 *  
 */
public abstract class ENeighbourWorld extends EWorld {
	public ENeighbourWorld(int length) {
		super(length);
	}
	/**
	 * Populate the world with Agents, Mediums and ZoneSelectors. <br>
	 * This is a default implementation to be ascendent compatible with the
	 * previous neighbourhood management.
	 */
	public void populateAll(String nsClass) {
		EAgent eAgent;
		/**
		 * Create the Agents
		 */
		try {
			for (int i = 0; i < agentSetSize; i++) {
				/**
				 * Populate the World.
				 */
				eAgent = (EAgent) Class.forName(this.pack() + ".Agent")
						.newInstance();
				eAgent.setWorld(this);
				add(i, eAgent);
				//a.setAgentID(this.indexOf(a));
				eAgent.setAgentID(i);
				eAgent.getInfo();
				// MadKit
				//if (worldListener.isWithGUI()) {
				//	launchAgent(eAgent, "agent-" + i, false);
				//}
				// MadKit
				/**
				 * ascendent compatibility
				 */
				connectionsStrategies = new ZoneSelector[1];
				connectionsStrategies[0] = (ZoneSelector) Class
						.forName(nsClass).newInstance();
				connectionsStrategies[0].setWorld(this);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.populate();
		if (getExtraAgents().length > 0) {
			if (!newWorld.booleanValue()) {
				setExtraAgentPreviousValues();
			}
		}
	}
	/////// Methods that subclasses CAN redefine ///////////
	/**
	 * Connect Agents with Mediums... This is a default implementation to be
	 * ascendent compatible with the previous neighbourhood management It
	 * assumes that Agent have already created a NeighbourMedium.
	 */
	public void connectAll() {
		//System.out.println("DEBUT ENeighbourWorld.connectAll");
		Iterator it;
		CAgent agent;
		Medium[] agentMediums;
		for (int i = 0; i < agentSetSize; i++) { // for all Visible CAgent
			agent = (CAgent) this.get(i);
			agentMediums = agent.getMediums();
			agentMediums[0].clear();
			// Ajoute AB-DP 19/09/2001 -revision des voisinages
			agentMediums[0].attach(agent, "source");
			// I attach my i th CAgent to mediums j
			for (it = (connectionsStrategies[0].compute(i)).iterator(); it
					.hasNext();) { // for all the computed agents to connect...
				agentMediums[0].attach(((CAgent) it.next()), "neighbour");
				// I attach computed CAgent to mediums j
			}
		}
		this.connect();
		//System.out.println("FIN : ENeighbourWorld.connectAll()");
	}
	public void setExtraAgentDefaultValues() {
		CAgent[] extraAgents = this.getExtraAgents();
		for (int i = 0; i < extraAgents.length; i++)
			extraAgents[i].getInfo();
	}
	public void setExtraAgentPreviousValues()
	//invoked by this.populateAll(nsClass)
	{
		Hashtable desc_temp = worldListener.getExtraAgentDescriptors_temp();
		CAgent[] extraAgent = this.getExtraAgents();
		//extraAgent.length : from the current simulation
		for (int i = 0; i < extraAgent.length; i++) {
			// to be upgraded : the real upper limit is the PREVIOUS
			// extraAgent.length
			// if extraAgent.length(current) > extraAgent.length(previous)
			// the following instruction buid a null value for a
			ArrayList a = (ArrayList) desc_temp.get(new Integer(i));
			if (a != null) {
				// if (a!=null) : for to aviod nullPointerException : to be
				// upgraded
				if (a.size() != 0) {
					for (int j = 0; j < a.size(); j++) {
						((DataDescriptor) a.get(j)).setAgent(extraAgent[i]);
						((DataDescriptor) a.get(j)).set();
					}
				}
			}
		}
	}
}