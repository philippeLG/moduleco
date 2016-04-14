/**
 * Title: Moduleco - bilateralGame.World.java <p>
 * Description:  Je fais jouer des Agents à des jeux symétriques bilatéraux
 * avec une matrice de gain donnée. <p>
 * Copyright : (c)enst-bretagne
 * @author Antoine.Beugnard@enst-bretagne.fr, Denis.Phan@enst-bretagne.fr
 * @version 1.4  February, 2004
 */

package models.smallWorld2;

import java.util.Iterator;
import java.util.ArrayList;

import modulecoFramework.modeleco.ENeighbourSmallWorld;
import modulecoFramework.modeleco.ZoneSelector;
//uniquement si RandomPairwise ?

//import modulecoFramework.medium.Medium;//uniquement si RandomPairwise
//import modulecoFramework.modeleco.zone.RandomPairwise ;//uniquement si RandomPairwise
//import modulecoFramework.modeleco.SimulationControl;

import modulecoGUI.grapheco.statManager.CalculatedVar;
import modulecoGUI.grapheco.descriptor.IntegerDataDescriptor;
import modulecoGUI.grapheco.descriptor.ChoiceDataDescriptor;
import modulecoGUI.grapheco.descriptor.InfoDescriptor;
// import modulecoGUI.grapheco.descriptor.DoubleDataDescriptor;
// import modulecoGUI.grapheco.descriptor.BooleanDataDescriptor;
/**
 * 
 * @author phan1
 *
 */
public class World extends ENeighbourSmallWorld {
	/**
	 * Initial values for the 4 basic parameters of this world <br>
	 * parameter 1: world size <br>
	 * parameter 2: neighbourhood type <br>
	 * parameter 3: active zone type <br>
	 * parameter 4: scheduler type <br>
	 */
	public static String initLength = "6";
	public static String initNeighbour = "Neighbour2";
	public static String initZone = "World";
	public static String initScheduler = "LateCommitScheduler";
	/**
	 *  
	 */
	protected double propS1;
	protected int s1AgainstS1, s2AgainstS1, s1AgainstS2, s2AgainstS2;
	protected int[][] matriceGain;
	protected String revisionRuleIndex;
	boolean RandomPairwiseTest;
	//protected Autorun Autorun; // DP 20/10/2002
	//boolean autoRun ;
	//boolean nextSeed ;
	boolean converge;
	int apS1, apS2;
	//protected SimulationControl simulationControl;
	/**
	 * 
	 * @param length
	 */
	public World(int length) {
		super(length);
	}

	public ZoneSelector getConnectionsStrategies() { //uniquement si RandomPairwise

		return connectionsStrategies[0];

	}

	public void init() {

		// test Console 12/07/2002
		//for (Iterator k=iterator();k.hasNext();){
		//Agent agt = (Agent) k.next();
		//agt.temp();
		//}
		//fin test Console
		try {
			statManager.add(
				new CalculatedVar(
					"FalseState",
					Class.forName(this.pack() + ".Agent").getMethod(
						"getState",
						null),
					CalculatedVar.NUMBER,
					new Boolean(false)));
			statManager.add(
				new CalculatedVar(
					"Changes",
					Class.forName(this.pack() + ".Agent").getMethod(
						"hasChanged",
						null),
					CalculatedVar.NUMBER,
					new Boolean(true)));
		} catch (ClassNotFoundException e) {
			System.out.println(e.toString());
		} catch (NoSuchMethodException e) {
			System.out.println(e.toString());
		}

	}

	public void compute() {
		int n = 0;
		for (Iterator i = iterator(); i.hasNext();) {
			if (((Agent) i.next()).newStrategy) {
				n++;
			}
		}

		propS1 = ((double) n) / ((double) agentSetSize);
		ComputeAveragePayoff();
	}

	public void commit() {
		super.commit();
		/*
		   if(nextSeed){
		      simulationControl.stop();
		      convergenceResults();
		   }
			*/
		convergenceComputations();

	}
	public void ComputeAveragePayoff() {
		double averagePayoffS1 = 0;
		double averagePayoffS2 = 0;
		int nS1 = 0;
		int nS2 = 0;
		for (Iterator k = iterator(); k.hasNext();) {
			Agent a = (Agent) k.next();

			if (a.getOldStrategy()) {
				averagePayoffS1 =
					(averagePayoffS1 * nS1 / (nS1 + 1)
						+ a.getGain() / (nS1 + 1));
				nS1 += 1;
			} else {
				averagePayoffS2 =
					(averagePayoffS2 * nS2 / (nS2 + 1)
						+ a.getGain() / (nS2 + 1));
				nS2 += 1;
			}

		}
		apS1 = (new Double(averagePayoffS1)).intValue();

		apS2 = (new Double(averagePayoffS2)).intValue();
	}

	public void convergenceComputations() {
		boolean change = false;
		converge = true;
		for (Iterator k = iterator(); k.hasNext();) {
			change = (((Agent) k.next()).hasChanged()).booleanValue();
			if (converge == true)
				if (change == true)
					converge = false;
		}
		/*
		if (converge == true) {
					if (autoRun)
						convergenceResults();
					else
						simulationControl.stop();
					worldListener.updateStopButton(false);
			
				} else
				worldListener.updateStopButton(true);
			}
		*/
	}

	public void convergenceResults() {
		boolean state; //=false;
		int cooperator = 0;
		int defector = 0;
		for (Iterator k = iterator(); k.hasNext();) {
			state = ((Boolean) ((Agent) k.next()).getState()).booleanValue();
			if (state == true)
				cooperator++;
			else
				defector++;
		}
		if (converge == true)
			System.out.println(
				" seed = "
					+ seed
					+ " ; cooperators = ; "
					+ cooperator
					+ " ; defectors = ; "
					+ defector
					+ " ; fixed point");
		else
			System.out.println(
				" seed = "
					+ seed
					+ " ;  cooperators = ; "
					+ cooperator
					+ " ; defectors = ; "
					+ defector
					+ " ;  cycle");
		seed++;
		//Autorun.restart();
	}

	public Object getState() {
		return new Double(propS1);
	}

	public int getS1AgainstS1() {
		return s1AgainstS1;
	}
	public int getS1AgainstS2() {

		return s1AgainstS2;
	}
	public int getS2AgainstS1() {

		return s2AgainstS1;
	}
	public int getS2AgainstS2() {

		return s2AgainstS2;
	}
	public void setS1AgainstS1(int s1AgainstS1) {
		this.s1AgainstS1 = s1AgainstS1;
		matriceGain[0][0] = s1AgainstS1;
	}

	public void setS2AgainstS1(int s2AgainstS1) {
		this.s2AgainstS1 = s2AgainstS1;
		matriceGain[1][0] = s2AgainstS1;
	}

	public void setS1AgainstS2(int s1AgainstS2) {
		this.s1AgainstS2 = s1AgainstS2;
		matriceGain[0][1] = s1AgainstS2;
	}

	public void setS2AgainstS2(int s2AgainstS2) {
		this.s2AgainstS2 = s2AgainstS2;
		matriceGain[1][1] = s2AgainstS2;
	}

	public void setApS1(int ap1) {
		apS1 = ap1;
	}

	public void setApS2(int ap2) {
		apS2 = ap2;
	}

	public String getRevisionRuleIndex() {
		return revisionRuleIndex;
	}

	public void setRevisionRuleIndex(String s) {

		revisionRuleIndex = s;
	}
	/**
	* Invoked by ENeighbourWorld.populateAll(nsClass)  
	* to be executed first, before the end of this world's constructor
	* during each world's creation process.
	*/
	public void getInfo() {
		matriceGain = new int[2][2]; // conserver  
	}

	/*
	   public void setNextSeed(boolean ns){
	   
	   
	      nextSeed=ns;
	   }
	   public void setAutoRun(boolean ar){
	   
	   
	      autoRun=ar;
	   }
	*/
	public ArrayList getDescriptors() {
		descriptors = super.getDescriptors();
		//descriptors.clear();
		descriptors.add(
			new IntegerDataDescriptor(
				this,
				"S1 against S1",
				"s1AgainstS1",
				s1AgainstS1,
				true));
		descriptors.add(
			new IntegerDataDescriptor(
				this,
				"S1 against S2",
				"s1AgainstS2",
				s1AgainstS2,
				true));
		descriptors.add(
			new IntegerDataDescriptor(
				this,
				"averagePayoff S1",
				"apS1",
				apS1,
				false));
		descriptors.add(new InfoDescriptor(""));
		descriptors.add(
			new IntegerDataDescriptor(
				this,
				"S2 against S1",
				"s2AgainstS1",
				s2AgainstS1,
				true));
		descriptors.add(
			new IntegerDataDescriptor(
				this,
				"S2 against S2",
				"s2AgainstS2",
				s2AgainstS2,
				true));
		descriptors.add(
			new IntegerDataDescriptor(
				this,
				"averagePayoff S2",
				"apS2",
				apS2,
				false));
		descriptors.add(new InfoDescriptor(""));
		descriptors.add(new InfoDescriptor(""));
		descriptors.add(new InfoDescriptor(""));
		//descriptors.add(new BooleanDataDescriptor(this,"NextSeed","nextSeed",nextSeed,true));
		//descriptors.add(new BooleanDataDescriptor(this,"AutoRun","autoRun",autoRun,true));

		descriptors.add(
			new ChoiceDataDescriptor(
				this,
				"Strategy revis. rule",
				"revisionRuleIndex",
				new String[] {
					"Last neighbour best payoff",
					"Last neighbour best average payoff" },
				revisionRuleIndex,
				true));
		return descriptors;
	}
	public void RandomGeneratorsetDefaultValues() {
		seed = 1;
		random_s = "JavaRandom";
		setRandom_s(random_s);
		nNodes = 0;
		removedLinks = 0;
	}
	public void setDefaultValues() {
		//autoRun =  false ;//true
		//nextSeed = false;// true
		s1AgainstS1 = 170;
		s2AgainstS1 = 176;
		s1AgainstS2 = 0;
		s2AgainstS2 = 6;
		revisionRuleIndex = "Last neighbourhood best payoff";

		matriceGain[0][0] = s1AgainstS1;
		matriceGain[1][0] = s2AgainstS1;
		matriceGain[0][1] = s1AgainstS2;
		matriceGain[1][1] = s2AgainstS2;
	}

}
