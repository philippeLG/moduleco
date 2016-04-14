/**
 * Title:        Medeleco<p>
 * Description:  Je définis un monde (carré) constituée de EPlace. Chaque est place est connectée aux autres
 * via un Voisinage. Des agents mobiles (MobileAgent) passent de Eplace en EPlace :<p>
 * Copyright:    Copyright (c)enst-bretagne
 * @author Antoine.Beugnard@enst-bretagne.fr, denis.phan@enst-bretagne.fr
 * created april 2001 by Antoine.Beugnard@enst-bretagne.fr
 * revised 2004 by denis.phan@enst-bretagne.fr 
 * @version 1.1 april 2001
 * @version 1.4.2  june 2004
 */
package modulecoFramework.modeleco.mobility;

import java.util.ArrayList;
//import java.util.Iterator;

import modulecoFramework.modeleco.ENeighbourWorld;
import modulecoFramework.modeleco.ZoneSelector;

public abstract class EMobileWorld extends ENeighbourWorld {

	protected ArrayList mobileAgentSet;

	public EMobileWorld(int length) {
		super(length);
	}
	/**
	 * Populate the world with Places, Mediums, ZoneSelectors This is a default
	 * implementation to be ascendent compatible with the previous neighbourhood
	 * management
	 */
	public void populateAll(String nsClass) {
		EPlace a;
		// create Agents
		try {
			for (int i = 0; i < agentSetSize; i++) { // populate the world ;
				// after size() == capacity
				a =
					(EPlace) Class
						.forName(this.pack() + ".Place")
						.newInstance();
				a.setWorld(this);
				add(i, a);
				a.setAgentID(i); //ajouté dp
				a.getInfo(); //ajouté dp

				//System.out.println("EAgent: "+a+", "+a.getClass());
				//				MadKit
				//launchAgent(a, "agent-" + i, false);
				/**
				 * ascendent compatibility
				 */
				connectionsStrategies = new ZoneSelector[1];
				connectionsStrategies[0] =
					(ZoneSelector) Class.forName(nsClass).newInstance();
				connectionsStrategies[0].setWorld(this);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// créé mais non utilisé à ce moment juin 2004
		mobileAgentSet = new ArrayList();
		for (int i = 0; i < agentSetSize; i++) {
			MobileAgent ma = ((EPlace) this.get(i)).getAgent();
			if (ma != null)
				mobileAgentSet.add(ma);
		}
		this.populate();
	}
	/**
	 * public access to protected ArrayList mobileAgentSet
	 * @return mobileAgentSet
	 */
	public ArrayList getMobileAgentSet() {
		// créé mais non utilisé à ce moment juin 2004
		return mobileAgentSet;
	}

	protected void setMove(Move move) {
		System.out.println("EMobileWorld.setMove()");
		// CETTE CLASSE N'EST ACTIVE NULLE PART !
		for (int i = 0; i < agentSetSize; i++) {
			MobileAgent ma = ((EPlace) this.get(i)).getAgent();
			if (ma != null) {
				ma.setMove(move);
			}
		}
	}
	/*
	 * protected void commitMove(MobileAgent originPlace, int destinationIndex) {
	 * EPlace destinationPlace = (EPlace)this.get(destinationIndex);
	 * 
	 * destinationPlace.receive(originPlace);
	 * originPlace.setFutureState(Agent.noBodyHere); }
	 */

}