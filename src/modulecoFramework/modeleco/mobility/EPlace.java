/**
 * Title:        EPlace <p>
 * Description:  Je suis un agent qui contient un agent mobile. Je redirige presque toutes mes méthodes vers cet
 * agent. Sauf la gestion des mediums qui peuvent m'être attachés.
 * Copyright:    Copyright (c)enst-bretagne
 * @author Antoine.Beugnard@enst-bretagne.fr ; Denis.Phan@enst-bretagne.fr
 * created april 2001
 * @version 1.4.2  june 2004
 */
package modulecoFramework.modeleco.mobility;

import java.util.ArrayList; //depuis JDK 1.2


import modulecoFramework.medium.Medium;
import modulecoFramework.medium.NeighbourMedium;

import modulecoFramework.modeleco.EAgent;
//import modulecoFramework.modeleco.EWorld;

import modulecoFramework.modeleco.exceptions.AlreadyUsedPlaceException;

public class EPlace extends EAgent {
	protected MobileAgent agent;
	protected int mobileAgentID;
	protected int placeID;
	protected ArrayList neighbours;
	protected ArrayList descriptors;

	/**
	 * Here are the different states for an agent (to be removed later...)
	 */

	public static int nobodyHere = 0;
	public static int somebodyHere = 1;
	public static int toBeComputed = 2;

	/**
	 * actualState represents the state of the agent (who lives on this
	 * habitation). futureState represents the state that is computed this
	 * turn.
	 */
	protected int actualState, futureState;

	public EPlace() {
		super();
		descriptors = new ArrayList();
		//System.out.println("avant Eplace.clear");
		//descriptors.clear();
		//System.out.println("apres Eplace.clear");

		mediums = new Medium[1];
		mediums[0] = new NeighbourMedium();
	}

	public void receive(MobileAgent a) throws AlreadyUsedPlaceException {
		if (agent == null) {
			agent = a;
			this.setActualState(this.getActualState());
			agent.setPlace(this);
		} else
			throw new AlreadyUsedPlaceException();
	}

	public void leave() {
		agent = null;
		this.setActualState(nobodyHere);
	}
	/*
	      public void progress() {
	      compute();
	   }
	*/
	/**
	 * Doit implanter l'agorithme de "vie" de l'EAgent
	 */
	public void compute() {
		if (agent != null)
			agent.compute(); // equiv. compute()
	}
	/**
	 * Doit implanter l'agorithme qui valide le changement d'état
	 */
	public void commit() {
		actualState = futureState;
		futureState = toBeComputed;
		if (agent != null)
			agent.commit();
	}
	/**
	 * Permet d'accéder à l'état de l'EAgent
	 */
// A REVOIR Dans "segrgation" * BlueAgent is "true" * RedAgent is "false"
	public Object getState() {
		if (agent != null)
			return agent.getState();
		else
			return null;
	}

	public int getActualState() {
		return actualState;
	}

	public void setActualState(int newState) {
		actualState = newState;
		futureState = toBeComputed;
	}

	public int getFutureState() {
		return futureState;
	}

	public void setFutureState(int newState) {
		futureState = newState;
	}
/*
	public String toString() {
		return "Devrait être redéfinie...";
	}

	public void setWorld(EWorld w) {
		world = w;
	}

	public EWorld getWorld() {
		return world;
	}
*/
    // Les services de mise à jour accessibles du package uniquement
	//public void edit(CentralControl control) {
	//if (agent != null) cf getAgent()
	//agent.edit(control);
	// 
	/**
	 * Return the name of the agent's package.
	 * used for dynamicLinks
	 * overides EAgent.pack()
	 */
	public String pack() {
		if (agent != null) {
			String n = agent.getClass().getName();
			return n.substring(0, n.indexOf('.', 0));
		} else
			return "";
	}

	public void getInfo() {
		if (agent != null)
			agent.getInfo();
	}
	// method to be implemented at the initialization afer getInfo()
	public void init() {
		neighbours = ((NeighbourMedium) this.getMediums()[0]).getNeighbours();
		futureState = toBeComputed;

		if (agent != null)
			agent.init();
	}


	public MobileAgent getAgent() {
		return agent;
	}
	public /*abstract*/
	ArrayList getDescriptors() { //en attendant mieux...
		return descriptors;
	}

	public void setPlaceID(int aID) {
		placeID = aID;
		//System.out.println("EPlace.setAgentID = "+placeID);
	}
}
