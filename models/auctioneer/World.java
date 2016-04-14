/** class auctioneer.World
 * Title:        Moduleco<p>
 * Description:
 * Copyright:    Copyright (c)enst-bretagne
 * @author sebastien.chivoret@ensta.org revised denis.phan@enst-bretagne.fr
 * @version 1.4  February, 2004
 */
package models.auctioneer;
import java.util.Iterator;
import java.util.ArrayList;
import java.lang.reflect.Constructor;
import modulecoFramework.modeleco.ENeighbourWorld;
import modulecoFramework.modeleco.EAgent;
import modulecoFramework.medium.Medium;
import modulecoFramework.modeleco.randomeco.CRandomDouble;
//directly adressed in method : setRandom_s(String s)
import modulecoGUI.grapheco.descriptor.DoubleDataDescriptor;
import modulecoGUI.grapheco.descriptor.ChoiceDataDescriptor;
import modulecoGUI.grapheco.descriptor.LongDataDescriptor;
//import modulecoGUI.grapheco.descriptor.IntegerDataDescriptor;
//import modulecoGUI.grapheco.descriptor.BooleanDataDescriptor;
//import modulecoGUI.grapheco.descriptor.InfoDescriptor;
import modulecoGUI.grapheco.statManager.Var;
// import modulecoGUI.grapheco.statManager.CalculatedVar;
/**
 * This class is a set of walrassian's agent with an auctioneer-driven pure
 * exchange market
 */
public class World extends ENeighbourWorld {
	/**
	 * Initial values for the 4 basic parameters of this world <br>
	 * parameter 1: world size <br>
	 * parameter 2: neighbourhood type <br>
	 * parameter 3: active zone type <br>
	 * parameter 4: scheduler type <br>
	 */
	public static String initLength = "20";
	public static String initNeighbour = "Empty";
	public static String initZone = "World";
	public static String initScheduler = "EarlyCommitScheduler";
	/**
	 *  
	 */
	protected double alpha, epsilon;
	protected double k, convergence;
	protected double averageCurrentUtility, agregateIncome;
	protected CRandomDouble random;
	protected long seed;
	protected String random_s;
	protected String utility;
	//protected boolean hack;
	private static String randomPath = "modulecoFramework.modeleco.randomeco.";
	//protected EAgent extraAgents;
	/**
	 * @param length
	 */
	public World(int length) {
		super(length);
	}
	public void getInfo() {
		//System.out.println(" world.getInfo()");
	}
	public void populate() {
		//		System.out.println(" world.populate()DEBUT");
		extraAgents = new Auctioneer[1];
		extraAgents[0] = new Auctioneer(); //
		((EAgent) extraAgents[0]).setWorld(this);
		extraAgents[0].getInfo();
		if (utility.equals("Cobb Douglas")) {
			((Auctioneer) extraAgents[0]).setP1(1); //efficacité à vérifier
		}
		if (utility.equals("CES")) {
			((Auctioneer) extraAgents[0]).setP1(2); //Efficacité à vérifier
		}
		// setup the neighbourhood settlement
		// initialize market
		mediumsInWorld = new Medium[1];
		mediumsInWorld[0] = new Market();
		((Market) mediumsInWorld[0]).setEWorld(this);
		((Market) mediumsInWorld[0]).setAuctioneer((Auctioneer) extraAgents[0]);
		//System.out.println("world.populate()FIN");
	}
	public void connect() {
		// connect the Neighbourhood
		super.connect(); // ENeighbourWorld.connect()
		for (Iterator i = iterator(); i.hasNext();) {
			getMarket().attach(((Agent) i.next()), "customer");
		}
		getMarket().attach(extraAgents[0], "auctioneer");
		// connect competitor to market
		//System.out.println(" world.connect() ");
	}
	public void init() {
		//System.out.println(" world.init()");
		try {
			//statManager.add(new CalculatedVar("DemandExcess2",
			// Class.forName(this.pack() + ".Agent").getMethod("getE2", null),
			// CalculatedVar.SUM, null));
			statManager.add(new Var("DemandExcess1", Class.forName(
					this.pack() + ".World").getMethod("getAbsE1", null)));
			statManager.add(new Var("DemandExcess2", Class.forName(
					this.pack() + ".World").getMethod("getAbsE2", null)));
		} catch (ClassNotFoundException e) {
			System.out.println("models.auctioneer.World" + e.toString());
			//e.printStackTrace();
		} catch (NoSuchMethodException e) {
			System.out.println("models.auctioneer.World" + e.toString());
			//e.printStackTrace();
		}
		computeAverageCurrentUtility();
	}
	public void commit() {
		super.commit();
		computeAverageCurrentUtility();
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
	public Object getState() {
		return new Boolean(true);
	}
	/**
	 * method call by agent.init() & competitor.int() (?double emploi ? -> M+N
	 * appels avant CAgent.init() juste après word populate ?N= nbAgents
	 * M=nbExtraAgents?)
	 */
	public Market getMarket() {
		return (Market) mediumsInWorld[0];
	}
	public void setAlpha(double d) {
		alpha = d;
		for (Iterator i = iterator(); i.hasNext();)
			((Agent) i.next()).setAlpha(alpha);
	}
	public void setEpsilon(double d) {
		epsilon = d;
	}
	//variables communes aux agents : ne devraient être dans le monde, mais
	// dans un objet exterieur
	public double getAlpha() {
		return alpha;
	}
	public double getEpsilon() {
		return epsilon;
	}
	public ArrayList getDescriptors() {
		descriptors.clear();
		/**
		 * //System.out.println("World.getDescriptors()"); descriptors.add(new
		 * LongDataDescriptor(this,"Seed","seed",seed,true,3));
		 * descriptors.add(new ChoiceDataDescriptor(this,"Random","random_s",new
		 * String[]{"Default", "JavaRandom"},random_s, true));
		 * descriptors.add(new
		 * DoubleDataDescriptor(this,"alpha","alpha",alpha,0.01,0.99,true,3));
		 * descriptors.add(new
		 * DoubleDataDescriptor(this,"epsilon","epsilon",epsilon,1,750,true,8));//utility.equals("CES")
		 * descriptors.add(new ChoiceDataDescriptor(this,"Utility","utility",new
		 * String[]{"Cobb Douglas", "CES"},utility, true)); descriptors.add(new
		 * DoubleDataDescriptor(this,"AverageCurrentUtility","averageCurrentUtility",averageCurrentUtility,false,6));
		 * descriptors.add(new
		 * DoubleDataDescriptor(this,"AgregateIncome","agregateIncome",agregateIncome,false,6));
		 * descriptors.add(new
		 * DoubleDataDescriptor(this,"convergence","convergence",convergence,1E-9,1E-6,true,8));
		 * descriptors.add(new
		 * DoubleDataDescriptor(this,"k","k",k,0.01,0.9,true,3));
		 */
		/**
		 * descriptors.add(new LongDataDescriptor(this,"seed","S",seed,true,3));
		 * descriptors.add(new ChoiceDataDescriptor(this,"random_s","R",new
		 * String[]{"Default", "JavaRandom"},random_s, true));
		 * descriptors.add(new
		 * DoubleDataDescriptor(this,"alpha","a",alpha,0.01,0.99,true,3));
		 * descriptors.add(new
		 * DoubleDataDescriptor(this,"epsilon","e",epsilon,1,750,true,8));//utility.equals("CES")
		 * descriptors.add(new ChoiceDataDescriptor(this,"utility","U",new
		 * String[]{"Cobb Douglas", "CES"},utility, true)); descriptors.add(new
		 * DoubleDataDescriptor(this,"averageCurrentUtility","AU",averageCurrentUtility,false,6));
		 * descriptors.add(new
		 * DoubleDataDescriptor(this,"agregateIncome","AI",agregateIncome,false,6));
		 * descriptors.add(new
		 * DoubleDataDescriptor(this,"convergence","c",convergence,1E-9,1E-6,true,8));
		 * descriptors.add(new
		 * DoubleDataDescriptor(this,"k","k",k,0.01,0.9,true,3));
		 */
		descriptors
				.add(new LongDataDescriptor(this, "S", "seed", seed, true, 3));
		descriptors.add(new ChoiceDataDescriptor(this, "R", "random_s",
				new String[]{"Default", "JavaRandom"}, random_s, true));
		descriptors.add(new DoubleDataDescriptor(this, "a", "alpha", alpha,
				0.01, 0.99, true, 3));
		descriptors.add(new DoubleDataDescriptor(this, "e", "epsilon", epsilon,
				1, 750, true, 8));
		//utility.equals("CES")
		descriptors.add(new ChoiceDataDescriptor(this, "U", "utility",
				new String[]{"Cobb Douglas", "CES"}, utility, true));
		descriptors.add(new DoubleDataDescriptor(this, "AU",
				"averageCurrentUtility", averageCurrentUtility, false, 6));
		descriptors.add(new DoubleDataDescriptor(this, "AI", "agregateIncome",
				agregateIncome, false, 6));
		descriptors.add(new DoubleDataDescriptor(this, "c", "convergence",
				convergence, 1E-9, 1E-6, true, 8));
		descriptors.add(new DoubleDataDescriptor(this, "k", "k", k, 0.01, 0.9,
				true, 3));
		return descriptors;
	}
	public void setSeed(long d) {
		seed = d;
		//System.out.println("Seed : "+seed);
		//setRandom_s(random_s); //sinon on cree un random a chaque fois...
		//On ne peut modifier la graine qu'au debut d'une simulation,
		//pas en cours de simulation.
	}
	public void setRandom_s(String s) {
		//System.out.println("setRandom_s");
		random_s = s;
		try {
			Constructor constructor = Class.forName(randomPath + random_s)
					.getConstructor(new Class[]{long.class});
			random = (CRandomDouble) constructor
					.newInstance(new Object[]{new Long(seed)});
		} catch (Exception e) {
			System.out
					.println((this.getClass()).getName() + " " + e.toString());
		}
		for (Iterator i = iterator(); i.hasNext();)
			((Agent) i.next()).setRandom(random);
	}
	// Inutile pour l'instant
	public String getRandom_s() {
		return random_s;
	}
	// Inutile pour l'instant mais a activer
	public CRandomDouble getRandom() {
		return random;
	}
	public void setDefaultValues() {
		//System.out.println("setDefaultValues()");
		seed = 10;
		random_s = "JavaRandom";
		setRandom_s("JavaRandom");
		setUtility("Cobb Douglas");
		// Ces deux dernières variables sont spécifiques aux agents,
		//mais commune à tous les agents : devrait être déplacées
		alpha = 0.8;
		epsilon = 1.8;
		// ces deux dernières variables devraient se trouver dans le
		// setDefaultValues()
		//de l'Auctioneer qui n'est (?) jamais activé
		convergence = 1E-6;
		k = 0.5;
	}
	public void setUtility(String s) {
		utility = s;
		if (utility.equals("Cobb Douglas")) {
			alpha = 0.8;
		}
		if (utility.equals("CES")) {
			alpha = 0.5;
		}
		//System.out.println("Utility = " + utility);
	}
	public String getUtility() {
		return utility;
	}
	// devrait être dans un observateur extérieur
	public double getAverageCurrentUtility() {
		return averageCurrentUtility;
	}
	// devra être dans un observateur extérieur après réforme StatManager
	public double getAbsE1() { // devra être mis ailleurs après réforme
							   // StatManager
		double AbsE1 = java.lang.Math.abs(((Market) mediumsInWorld[0]).getE1());
		//System.out.println("AbsE1 = "+AbsE1);
		return AbsE1;
	}
	public double getAbsE2() { // devra être mis ailleurs après réforme
							   // StatManager
		double AbsE2 = java.lang.Math.abs(((Market) mediumsInWorld[0]).getE2());
		//System.out.println("AbsE1 = "+AbsE1);
		return AbsE2;
	}
	// ces methodes devraient être dans l'auctioneer
	public void setK(double d) {
		k = d;
	}
	public double getK() {
		return k;
	}
	public void setConvergence(double d) {
		convergence = d;
	}
	public double getConvergence() {
		return convergence;
	}
	public void setAgregateIncome(double Y) {
		agregateIncome = Y;
		System.out.println("agregateIncome = " + agregateIncome);
	}
}