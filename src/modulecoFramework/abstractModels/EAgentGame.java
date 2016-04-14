/**
 * Title:       bilateralGame.Agent<p>
 * Description:  Chaque Agent joue avec ses voisins et adopte, pour le tour suivant, la
 * statégie de celui qui a le meilleur gain. Le gain est réinitialisé à chaque tour.
 * La matrice de gain est la même pour tous les Agents ; elle est enregistré par le World.<p>
 * Copyright:    Copyright (c)enst-bretagne
 * @author Antoine.Beugnard@enst-bretagne.fr, Denis.Phan@enst-bretagne.fr
 * @version 1.4  February, 2004
 */
package modulecoFramework.abstractModels;

import java.util.Iterator;
import java.util.ArrayList;

import modulecoFramework.modeleco.EAgent;
//import modulecoGUI.grapheco.descriptor.IntegerDataDescriptor;
import modulecoGUI.grapheco.descriptor.BooleanDataDescriptor;

import modulecoFramework.medium.NeighbourMedium;

//import modulecoGUI.grapheco.ControlPanel;

public abstract class EAgentGame extends EAgent {
	public boolean newStrategy, oldStrategy, nextStrategy;
	protected String revisionRuleIndex;
	protected boolean hasChanged;
	protected int[][] payoffMatrix;
	protected int payoff = 0;

	public EAgentGame() {
		super();
	}

	public void getInfo() {
		revisionRuleIndex = ((EWorldGame) world).getRevisionRuleIndex();

	}

	public void setDefaultValues() {
		oldStrategy = true; //false ;
	}
	public void init() {
		payoffMatrix = new int[2][2];
		for (int i = 0; i < 2; i++)
			for (int j = 0; j < 2; j++) {
				this.payoffMatrix[i][j] =
					((EWorldGame) world).payoffMatrix[i][j];
			}
		newStrategy = oldStrategy;
		nextStrategy = newStrategy;
		neighbours = ((NeighbourMedium) mediums[0]).getNeighbours();
	}

	//=======

	public void computeBestReply() {
		EAgentGame A;
		int cumulatedPayoffS1 = 0, cumulatedPayoffS2 = 0;
		for (Iterator i = neighbours.iterator(); i.hasNext();) {
			A = (EAgentGame) i.next();
			if (A.newStrategy) {
				cumulatedPayoffS1 += payoffMatrix[0][0];
				cumulatedPayoffS2 += payoffMatrix[1][0];
			} else {
				cumulatedPayoffS1 += payoffMatrix[0][1];
				cumulatedPayoffS2 += payoffMatrix[1][1];
			}
		}
		nextStrategy = false;
		if (cumulatedPayoffS1 > cumulatedPayoffS2) {
			nextStrategy = true;
			//cumulatedPayoff = cumulatedPayoffS1
		}

		if (cumulatedPayoffS1 == cumulatedPayoffS2) {
			nextStrategy = newStrategy; // inertie
			//  if (random.getDouble() < 0.5 ) nextStrategy = true ;
		}
	}

	public void ComputeBestPayoff(int payoff) {
		int payoffMax = payoff;
		EAgentGame dpa;

		for (Iterator i = neighbours.iterator(); i.hasNext();) {
			dpa = (EAgentGame) i.next();
			if (payoffMax < dpa.payoff) {
				nextStrategy = dpa.oldStrategy;
				//on copie la strategie du meilleur voisin
				payoffMax = dpa.payoff;
			}
		}
		newStrategy = nextStrategy;
	}

	public void ComputeBestAveragePayoff(int payoff) {
		EAgentGame dpa;
		int n1 = 0;
		int n2 = 0;
		int payoff1 = 0;
		int payoff2 = 0;
		double averagePayoff1;
		double averagePayoff2;
		//System.out.println("ComputeBestAveragePayoff()");

		for (Iterator i = neighbours.iterator(); i.hasNext();) {
			dpa = (EAgentGame) i.next();
			if (dpa.oldStrategy) {
				n1++;
				payoff1 = payoff1 + dpa.payoff;
			} else {
				n2++;
				payoff2 = payoff2 + dpa.payoff;
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
		//System.out.println("averagePayoff1 = "+averagePayoff1+" averagePayoff2 = "+averagePayoff2);
		//on copie la strategie qui apporte le meilleur payoff moyen
		if (averagePayoff1 > averagePayoff2)
			nextStrategy = true;
		if (averagePayoff2 > averagePayoff1)
			nextStrategy = false;
		if (averagePayoff1 == averagePayoff2)
			nextStrategy = oldStrategy;
		newStrategy = nextStrategy;
		//finalAveragePayoff1 = averagePayoff1;
		//finalAveragePayoff2 = averagePayoff2;
	}

	//===========================================
	public Boolean hasChanged() {
		boolean temp;
		temp = hasChanged;
		hasChanged = false;
		return new Boolean(temp);
	}

	public Object getState() {
		return new Boolean(newStrategy);
	}
	/*
	   public String toString() {
	      String s;
	      s = (new Boolean(newStrategy)).toString() + " g = " + (new Integer(gain)).toString();
	      return s ;
	   }
	*/
	public ArrayList getDescriptors() {
		descriptors.clear();
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
		return descriptors;
	}

	public void setNewStrategy(boolean b) {
		newStrategy = b;
		System.out.println("gant : " + agentID + " inverseState()");
	}

	public void setOldStrategy(boolean b) {
		oldStrategy = b;
	}
}
