/**
 * EAgent.java
 * Copyright (c)enst-bretagne
 * @author Antoine.Beugnard@enst-bretagne.fr ; Denis.Phan@enst-bretagne.fr ;
 *         sebastien.chivoret@ensta.org
 * @version 1.0  May 2000
 * @version 1.2  August 2002
 * @version 1.4.2 June 2004
 */
package modulecoFramework.modeleco;
import java.util.ArrayList;
import java.util.Iterator;
import modulecoFramework.medium.Medium;
import modulecoFramework.medium.NeighbourMedium;
import modulecoGUI.grapheco.descriptor.InfoDescriptor;
import modulecoGUI.grapheco.descriptor.DataDescriptor;
import modulecoGUI.Traces.Etat;
// MadKit
//import madkit.kernel.AbstractAgent;
// MadKit
/**
 * Je suis une classe abstraite qui représente des agents. Un agent connait des
 * voisins. L'évolution est décrite dans la méthode compute. La validation de
 * l'évolution dans la méthode commit.
 * <p>
 * Bien que runnable, je m'exécute dans le Thread courrant-celui du monde.
 * <p>
 * Les sous classes concrètes doivent redéfinir compute, commit, getState et
 * toString.
 * <p>
 * Sous classes concrètes connues :
 * 
 * @see elem.Agent,
 * @see dp.Agent,
 * @see life.Agent La methode edit() est spécialisée dans les sous-classes.
 *      Copyright: (c)enst-bretagne
 *  
 */
public abstract class EAgent implements CAgent {
	/**
	 * agent ID is a number specific tothe Agent from 0 to (size-1)
	 */
	public int agentID;
	/**
	 * The Agent knows their world
	 */
	protected EWorld world;
	/**
	 * EAgent Medium for interaction between agents
	 */
	protected Medium[] mediums;
	protected int numbersOfMediums = 0; // The number of Mediums
	/**
	 * connectivity in the Agent neighbours'medium
	 */
	public int connectivity; // DP 13/09/2002
	/**
	 * Communications with probes by the way of Array List
	 */
	public ArrayList descriptors, neighbours;
	public EAgent() {
		descriptors = new ArrayList();
		//fixed_ae=null; //SC 19.06.01
		mediums = new Medium[1];
		mediums[0] = new NeighbourMedium();
	}
	//	MadKit
	//public void activate() {
	//	createGroup(false, "moduleco", "simulation", null, null);
	//	requestRole("moduleco", "simulation", "agent", null);
	//}
	//	MadKit
	/**
	 * Required to be a CAgent
	 * Invoked by subclass <tt>ENeighbourWorld</tt>, when a new agent is created.
	 * <p>
	 * method get info is invoked after the agent constructor but before
	 * probe(descriptors) updates and initialisation.
	 * Mandatory method (inherited from its abstract superclass in <tt>EAgent</tt>)
	 * 
	 * @see modulecoFramework.modeleco.ENeighbourWorld
	 * @see modulecoFramework.modeleco.CAgent
	 */
	public abstract void getInfo();
	/**
	 * Invoked by SimulationControl.initSimulation() Initialise this EAgent
	 * Required to be a CAgent
	 * 
	 * @see modulecoFramework.modeleco.SimulationControl.initSimulation()
	 */
	public abstract void init();
	/**
	 * method to be invoked by the scheduler contain the internal deliberation
	 * of this EAgent Required to be a CAgent
	 * 
	 * @see modulecoFramework.modeleco.scheduler
	 */
	public abstract void compute();
	/**
	 * method to be invoked by the scheduler to implement the validation of
	 * cstate change after compute
	 * 
	 * @see modulecoFramework.modeleco.scheduler
	 */
	public abstract void commit();
	/**
	 * method terminate() close the current simulation for this agent invoked by
	 * EWorld.terminate()
	 * 
	 * @see modulecoFramework.modeleco.EWorld.terminate()
	 * @see modulecoFramework.modeleco.SimulationControl.terminate()
	 */
	public void terminate() {
		System.out.println("EAgent.terminate()");
		//closed();
	}
	//======================================================================
	public String pack() {
		String n = this.getClass().getName();
		return n.substring(0, n.lastIndexOf('.'));
	}
	/**
	 * setAgentID from ENeighbourWorld
	 */
	public void setAgentID(int agentID) {
		this.agentID = agentID;
		//System.out.println("EAgent.setAgentID = "+agentID);
	}
	public int getAgentID() {
		return agentID;
	}
	public void inverseState() {
		// BUG : PREVOIR DE REPËRCUTER AUX INTERFACES ?
	}
	public Medium[] getMediums() {
		return mediums;
	}
	/**
	 * Permet d'accéder à l'état de l'EAgent
	 */
	public abstract Object getState();
	public ArrayList getNeighbours() { // ajouté DP 11/07/2002
		return neighbours;
	}
	public void setNeighbours(ArrayList nghbrs) { // ajouté DP 18/09/2001
		neighbours = nghbrs;
		//System.out.println("EAgent.setNeighbours()");
	}
	public String toString() {
		return "AgentID : " + (new Integer(agentID)).toString();
		//EAgent.toString() ; Devrait être redéfinie...";
	}
	public void setWorld(EWorld w) {
		world = w;
	}
	public EWorld getWorld() {
		return world;
	}
	public ArrayList getDescriptors() {
		descriptors.clear();
		descriptors.add(new InfoDescriptor("No data"));
		return descriptors;
	}
	public Object getPState(ArrayList vars) {
		Etat etat = new Etat(agentID, "Agent");
		ArrayList des = this.getDescriptors();
		for (Iterator j = vars.iterator(); j.hasNext();) {
			String varName = (String) j.next();
			for (Iterator i = des.iterator(); i.hasNext();) {
				DataDescriptor d = (DataDescriptor) i.next();
				if (d.getDataName().equals(varName))
					etat.add(d);
			}
		}
		return etat;
	}
	/**
	 * To close the agent deprecated ?
	 */
	/*
	 * public void closed() { }
	 */
}