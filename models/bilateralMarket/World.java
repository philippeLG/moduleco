/**
 * Title: Moduleco - bilateralMarket.World.java <p>
 * Description:  Je fais jouer des Agents à des jeux symétriques bilatéraux
 * avec une matrice de gain donnée. <p>
 * Copyright : (c)enst-bretagne
 * @author Antoine.Beugnard@enst-bretagne.fr, Denis.Phan@enst-bretagne.fr
* @version 1.4  February, 2004
 */
package models.bilateralMarket;

import java.lang.reflect.Constructor;

import java.util.ArrayList;
import java.util.Iterator;

import modulecoFramework.modeleco.ENeighbourRandomPairwiseWorld;
import modulecoFramework.modeleco.randomeco.CRandomDouble;
import modulecoFramework.modeleco.ZoneSelector;
import modulecoFramework.medium.Medium;
//import modulecoFramework.modeleco.zone.RandomPairwise ;
//import modulecoFramework.modeleco.SimulationControl;

//import modulecoGUI.grapheco.statManager.CalculatedVar;
import modulecoGUI.grapheco.statManager.Var;
import modulecoGUI.grapheco.descriptor.DoubleDataDescriptor;
import modulecoGUI.grapheco.descriptor.ChoiceDataDescriptor;
import modulecoGUI.grapheco.descriptor.LongDataDescriptor;
//import modulecoGUI.grapheco.descriptor.IntegerDataDescriptor;
//import modulecoGUI.grapheco.descriptor.BooleanDataDescriptor;
/**
 * 
 *
 *
 **/
public class World extends ENeighbourRandomPairwiseWorld //ENeighbourWorld
{
	/**
	 * Initial values for the 4 basic parameters of this world <br>
	 * parameter 1: world size <br>
	 * parameter 2: neighbourhood type <br>
	 * parameter 3: active zone type <br>
	 * parameter 4: scheduler type <br>
	 */
	public static String initLength = "20";
	public static String initNeighbour = "RandomPairwise";
	public static String initZone = "World";
	public static String initScheduler = "LateCommitScheduler";
	/**
	 *  
	 */
	// WorldSize Fixed ControlPanel 469-471 = 20
	public double alpha,
		epsilon,
		mean,
		averagePrice,
		convergence,
		OldAverage,
		stdDeviation;
	protected double averageCurrentUtility, agregateIncome;
	protected CRandomDouble randomDot;
	protected long seedDot;
	protected String randomDot_s;
	private static String randomPath = "modulecoFramework.modeleco.randomeco.";
	protected String utility;

	public World(int length){
		super(length);

	}

	public void setDefaultValues() {
		setRandomDot_s("JavaRandom");
		seedDot = 10;
		utility = "Cobb Douglas";
		// Ces deux dernières variables sont spécifiques aux agents, 
		//mais commune à tous les agents : devrait être déplacées
		alpha = 0.5;
		epsilon = 1.8;

		// ces deux dernières variables devraient se trouver dans le setDefaultValues() 
		//de l'Auctioneer qui n'est (?) jamais activé
		convergence = 1E-8;
		averagePrice = 1.0;
	}

	public void populate() {
		super.populate();
		mediumsInWorld = new Medium[1];
		mediumsInWorld[0] = new Market();
		((Market) mediumsInWorld[0]).setEWorld(this);
		//((RandomPairwise)getConnectionsStrategies()).madeNewEworldClone(); // Supprimé DP 05/08/2002
	}

	public void init() {

		try {
			//statManager.add(new CalculatedVar("P", Class.forName(this.pack() + ".Agent").getMethod("getP", null), CalculatedVar.AVERAGE, null));
			//statManager.add(new CalculatedVar("Viability Minimum", Class.forName(this.pack() + ".Agent").getMethod("getGraphicViability", null), CalculatedVar.MINIMUM, null));
			//statManager.add(new CalculatedVar("Average", Class.forName(this.pack() + ".Agent").getMethod("getGraphicE1", null), CalculatedVar.AVERAGE, null));
			statManager.add(
				new Var(
					"Average",
					Class.forName(this.pack() + ".World").getMethod(
						"getAveragePrice",
						null)));
		} catch (ClassNotFoundException e) {

			e.printStackTrace(); // System.out.println(e.toString());
		} catch (NoSuchMethodException e) {

			System.out.println(e.toString());
		}

		computeAverageCurrentUtility();
	}

	public void compute() {

		//System.out.println("world.compute()");
		OldAverage = averagePrice;
		averagePrice = 0;
		stdDeviation = 0;
		double sqrAP = 0;
		for (Iterator i = iterator(); i.hasNext();) {
			double price = ((Agent) i.next()).getP1();
			averagePrice += price;
			sqrAP += price * price;
		}
		averagePrice = averagePrice / agentSetSize;
		stdDeviation =
			java.lang.Math.sqrt(sqrAP / agentSetSize - averagePrice * averagePrice);

	}

	public void commit() {

		super.commit();
		//madeNewEworldClone();
		//((RandomPairwise)getConnectionsStrategies()).madeNewEworldClone(); //DP 31 /12/2002
		//this.connectAll(); -revision des voisinages : reporte parent 05/08/2002
		//
		computeAverageCurrentUtility();
		agregateIncome = 0;
		for (Iterator i = iterator(); i.hasNext();)
			agregateIncome += ((Agent) i.next()).getTransactionalIncome();
		if (java.lang.Math.abs(OldAverage - averagePrice) < convergence) {
			simulationControl.stop();
			System.out.println("convergence");
		}
		//System.out.println("Commit.agregateIncome = "+agregateIncome);
	}

	public void computeAverageCurrentUtility() {

		double agregateCurrentUtility = 0;
		//System.out.println(" world.commit() ");
		for (Iterator i = iterator(); i.hasNext();) {
			agregateCurrentUtility += ((Agent) i.next()).getCurrentUtility();
		}
		averageCurrentUtility = agregateCurrentUtility / (double) agentSetSize;
		// A vérifier DP 10/05/2002
	}
/**
 * Invoked by ENeighbourWorld.populateAll(nsClass)  
 * to be executed first, before the end of this world's constructor
 * during each world's creation process.
 */
	public void getInfo() {
	}

	public ArrayList getDescriptors() {
		//descriptors.clear();
		descriptors = super.getDescriptors(); // Pourrait être évité ?
		descriptors.add(
			new LongDataDescriptor(this, "Seed", "seedDot", seedDot, true, 3));
		descriptors.add(
			new ChoiceDataDescriptor(
				this,
				"Random",
				"randomDot_s",
				new String[] { "Default", "JavaRandom", "JavaGaussian" },
				randomDot_s,
				true));
		descriptors.add(
			new DoubleDataDescriptor(
				this,
				"alpha",
				"alpha",
				alpha,
				0.01,
				0.99,
				true,
				3));
		descriptors.add(
			new DoubleDataDescriptor(
				this,
				"epsilon",
				"epsilon",
				epsilon,
				1,
				750,
				true,
				8));
		//utility.equals("CES")
		descriptors.add(
			new ChoiceDataDescriptor(
				this,
				"Utility",
				"utility",
				new String[] { "Cobb Douglas", "CES" },
				utility,
				true));
		descriptors.add(
			new DoubleDataDescriptor(
				this,
				"averageCurrentUtility",
				"averageCurrentUtility",
				averageCurrentUtility,
				false,
				6));
		descriptors.add(
			new DoubleDataDescriptor(
				this,
				"AgregateIncome",
				"agregateIncome",
				agregateIncome,
				false,
				6));
		descriptors.add(
			new DoubleDataDescriptor(
				this,
				"convergence",
				"convergence",
				convergence,
				1E-9,
				1E-6,
				true,
				8));
		descriptors.add(
			new DoubleDataDescriptor(
				this,
				"AveragePrice",
				"averagePrice",
				averagePrice,
				false,
				6));
		descriptors.add(
			new DoubleDataDescriptor(
				this,
				"StdDeviation",
				"stdDeviation",
				stdDeviation,
				false,
				6));

		return descriptors;
	}

	/*
	   public Random getRandom(){
	      return randomDot;
	   }	
		*/

	public double getStdDeviation() {
		return stdDeviation;
	}
	public void setStdDeviation(double s) {
		stdDeviation = s;
	}
	public double getAlpha() {

		return alpha;
	}
	public void setAlpha(double d) {
		alpha = d;
	}
	public double getAveragePrice() {

		return averagePrice;
	}

	public void setAveragePrice(double a) {

		averagePrice = a;
	}
	public Market getMarket() {

		return (Market) mediumsInWorld[0];
	}
	public double getMean() {
		return 100 * mean;
	}

	public void setSeedDot(long d) {
		seedDot = d;
		//System.out.println("Seed : "+seed);
		//setRandom_s(randomDot_s); //sinon on cree un randomDot a chaque fois...

		//On ne peut modifier la graine qu'au debut d'une simulation, 
		//pas en cours de simulation.
	}

	public void setRandomDot_s(String s) {

		randomDot_s = s;
		try {
			Constructor constructor =
				Class.forName(randomPath + randomDot_s).getConstructor(
					new Class[] { long.class });
			randomDot =
				(CRandomDouble) constructor.newInstance(
					new Object[] { new Long(seedDot)});
		} catch (Exception e) {

			System.out.println(e.toString());
		}

		for (Iterator i = iterator(); i.hasNext();)
			 ((Agent) i.next()).setRandom(randomDot);
	}

	public void setEpsilon(double d) {
		epsilon = d;
	}

	public double getEpsilon() {
		return epsilon;
	}
	public ZoneSelector getConnectionsStrategies() {
		return connectionsStrategies[0];
	}

	public void setUtility(String s) {

		utility = s;
		if (utility.equals("Cobb Douglas")) {
			//setAlpha(0.8);
			//((Market)mediumsInWorld[0]).setP1(1);//efficacité à vérifier
		}
		if (utility.equals("CES")) {
			//setAlpha(1.8);
			//((Market)mediumsInWorld[0]).setP1(2);//Efficacité à vérifier
		}
	}

	public String getUtility() {

		return utility;
	}

	public double getAverageCurrentUtility() {

		return averageCurrentUtility;
	}

	public void setConvergence(double d) {

		convergence = d;
	}

	public double getConvergence() {

		return convergence;
	}

	public double getAgregateIncome() {
		//System.out.println("agregateIncome = "+agregateIncome);
		return agregateIncome;

	}

}