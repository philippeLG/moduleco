/** * Title:      modulecoFramework.modeleco.EAgentLinks extends EAgent<p>
 * * Copyright:    Copyright (c)enst-bretagne
 * @author Denis.Phan@enst-bretagne.fr
 * @version 1.3  october,15, 2003 (creation)
 * @version 1.4.2 june 2004  
 */
package modulecoFramework.modeleco;

import java.util.Iterator;
import java.util.ArrayList;

import modulecoGUI.grapheco.descriptor.IntegerDataDescriptor;
import modulecoGUI.grapheco.descriptor.BooleanXDataDescriptor;

import modulecoFramework.medium.NeighbourMedium;

public abstract class EAgentLinks extends EAgent {

	public boolean neighbourTest[];
	public int nbNeighbour;
	public ArrayList neighboursClones;
	public int newNeighbourgID;
	public int maxNeighbourhoodSize = 10;

	public ArrayList linksDescriptors; // DP 17/10/2003  
	public int maxAgentID;

	public EAgentLinks() {
		super();
		linksDescriptors = new ArrayList();
	}

	public void init() {
		//System.out.println("EAgentLinks.init()/ medium.getNeighbours()");
		neighbours = ((NeighbourMedium) mediums[0]).getNeighbours();
		nbNeighbour = neighbours.size();
		maxAgentID = ((ENeighbourSmallWorld) world).agentSetSize - 1;
		if (neighbours.size() < maxNeighbourhoodSize) {
			neighboursClones = new ArrayList();
			neighboursClones = (ArrayList) neighbours.clone();
			neighbourTest = new boolean[maxNeighbourhoodSize + 1];
			for (int i = 1; i < nbNeighbour + 1; i++)
				neighbourTest[i] = true;
			newNeighbourgID = -10000;
		}
	}

	public void updateConnectivityIndex() { // DP 13/09/2002

		connectivity = neighbours.size();
		//System.out.println("agent : "+agentID+" new connectivity = "+connectivity);
	}
	public ArrayList getLinksDescriptors() {
		int agentTempID;
		int j = 1;
		linksDescriptors.clear();
		linksDescriptors.add(
			0,
			new IntegerDataDescriptor(
				this,
				"New Neighbourg",
				"newNeighbourgID",
				newNeighbourgID,
				0,
				maxAgentID,
				true));
		nbNeighbour = neighboursClones.size();
		if (nbNeighbour < maxNeighbourhoodSize + 1) {

			for (Iterator i = neighboursClones.iterator(); i.hasNext();) {
				EAgentLinks a = ((EAgentLinks) i.next());
				agentTempID = a.agentID;
				linksDescriptors.add(
					j,
					new BooleanXDataDescriptor(
						this,
						"Neighbour " + new Integer(agentTempID).toString(),
						"neighbourTest",
						neighbourTest[j],
						true,
						j));
				j += 1;
			}
		} else
			System.out.println("too much neighbourgs !");

		return linksDescriptors;
	}

	public void setNeighbourTest(boolean b, int j) {

		neighbourTest[j] = b;
		int agentLocation;

		if (neighbourTest[j]) {
			// add process (I) update neighbourhood of the neighbour Agent
			EAgentLinks a = (EAgentLinks) neighboursClones.get(j - 1);
			// This agent is not in the neighbourhood of Agent a 
			if (!a.neighbours.contains((Object) this)) {
				// add this agent in the neighbourhood of Agent a
				a.neighbours.add(this);
				// identification of this agent's location in the neighboursClones list of Agent a
				agentLocation = a.neighboursClones.indexOf((Object) this); //
				// turn the corresponding neighbourTestX to "true"
				a.neighbourTest[agentLocation + 1] = true;
				((ENeighbourSmallWorld) world).updateRemovedLinks(-1);
				// add process (II) add agent's a in the neighbourhood of this agent
				if (!neighbours.contains((Object) a))
					neighbours.add(a);
			}
		} else {
			// remove process (I) update neighbourhood of the neighbour Agent
			EAgentLinks a = (EAgentLinks) neighboursClones.get(j - 1);
			// This agent is in the neighbourhood of Agent a 
			if (a.neighbours.contains((Object) this)) {
				// identification of this agent's location in the neighbourhood of Agent a
				agentLocation = a.neighbours.indexOf((Object) this);
				// remove this agent's location of the neighbourhood of Agent a
				a.neighbours.remove(agentLocation);
				// identification of this agent's location in the neighboursClones list of Agent a
				agentLocation = a.neighboursClones.indexOf((Object) this); //
				// turn the corresponding neighbourTestX to "false"
				a.neighbourTest[agentLocation + 1] = false;
				((ENeighbourSmallWorld) world).updateRemovedLinks(1);
			}
			// remove process (II) update neighbourhood of this Agent
			if (neighbours.contains((Object) a))
				neighbours.remove(neighbours.indexOf((Object) a));
		}

	}
	public void setNewNeighbourgID(int nnid) {
		newNeighbourgID = nnid;
		EAgentLinks a =
			(EAgentLinks) ((ENeighbourSmallWorld) world).get(newNeighbourgID);
		if (a.neighboursClones.size() < maxNeighbourhoodSize)
			if (neighboursClones.size() < maxNeighbourhoodSize)
				addNewNeighbourg(newNeighbourgID);
			else
				System.out.println(
					"too much neighbourgs for agent : " + agentID + " !");
		else
			System.out.println(
				"too much neighbourgs for agent : " + a.agentID + " !");
	}

	public void addNewNeighbourg(int newNeighbourgID) {

		EAgentLinks a =
			(EAgentLinks) ((ENeighbourSmallWorld) world).get(newNeighbourgID);

		if (!neighbours.contains((Object) a)) {
			neighbours.add(a);
			neighboursClones.add(a);
			neighbourTest[neighboursClones.size()] = true;
			((ENeighbourSmallWorld) world).updateRemovedLinks(1);
			linksDescriptors.add(
				neighboursClones.size(),
				new BooleanXDataDescriptor(
					this,
					"Neighbour " + new Integer(newNeighbourgID).toString(),
					"neighbourTest",
					neighbourTest[neighboursClones.size()],
					true,
					neighboursClones.size()));
		}
		if (!a.neighbours.contains((Object) this)) {
			a.neighbours.add(this);
			a.neighboursClones.add(this);
			a.neighbourTest[a.neighboursClones.size()] = true;
			a.linksDescriptors.add(
				a.neighboursClones.size(),
				new BooleanXDataDescriptor(
					a,
					"Neighbour " + new Integer(agentID).toString(),
					"neighbourTest",
					neighbourTest[a.neighboursClones.size()],
					true,
					a.neighboursClones.size()));
		}

	}
}
