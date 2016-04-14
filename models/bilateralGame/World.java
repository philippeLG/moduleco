/**
 * Title: Moduleco - bilateralGame.World.java <p>
 * Description:  Je fais jouer des Agents à des jeux symétriques bilatéraux
 * avec une matrice de payoff donnée. <p>
 * Copyright : (c)enst-bretagne
 * @author Antoine.Beugnard@enst-bretagne.fr, Denis.Phan@enst-bretagne.fr
 * @version 1.4  February, 2004
 */
package models.bilateralGame;
import java.util.Iterator;
// import java.util.ArrayList;
import modulecoFramework.abstractModels.EWorldGame;
// import modulecoGUI.grapheco.descriptor.ChoiceDataDescriptor;
// import modulecoGUI.grapheco.descriptor.InfoDescriptor;
public class World extends EWorldGame {
	/**
	 * Initial values for the 4 basic parameters of this world <br>
	 * parameter 1: world size <br>
	 * parameter 2: neighbourhood type <br>
	 * parameter 3: active zone type <br>
	 * parameter 4: scheduler type <br>
	 */
	public static String initLength = "49";
	public static String initNeighbour = "NeighbourMooreAndSelfPlan";
	public static String initZone = "World";
	public static String initScheduler = "LateCommitScheduler";
	/**
	 *  
	 */
	protected double propS1; // vrai si majorité de S1-joueurs
	/**
	 * @param length
	 */
	public World(int length) {
		super(length);
	}
	public void setDefaultValues() {
		super.s1AgainstS1 = 100;
		super.s2AgainstS1 = 176;
		super.s1AgainstS2 = 0;
		super.s2AgainstS2 = 6;
		super.setDefaultValues();
		//revisionRuleIndex="Last neighbourhood best payoff";
	}
	public void compute() {
		int n = 0;
		for (Iterator i = iterator(); i.hasNext();) {
			if (((Agent) i.next()).newStrategy) {
				n++;
			}
		}
		propS1 = ((double) n) / ((double) agentSetSize);
	}
	public Object getState() {
		return new Double(propS1);
	}
	/*
	 * public ArrayList getDescriptors() { descriptors = super.getDescriptors();
	 * 
	 * return descriptors; }
	 */
}