/*
 * @(#)World.java	1.1 09-Mar-04
 */
package models.discreteChoice2;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Iterator;

import modulecoFramework.modeleco.ENeighbourWorld;
import modulecoFramework.modeleco.randomeco.RandomSD;
import modulecoGUI.grapheco.descriptor.BooleanDataDescriptor;
import modulecoGUI.grapheco.descriptor.ChoiceDataDescriptor;
import modulecoGUI.grapheco.descriptor.DoubleDataDescriptor;
import modulecoGUI.grapheco.descriptor.InfoDescriptor;
import modulecoGUI.grapheco.descriptor.LongDataDescriptor;
import modulecoGUI.grapheco.descriptor.IntegerDataDescriptor;

import modulecoGUI.grapheco.statManager.CalculatedVar;
import modulecoGUI.grapheco.statManager.Var;

//import modulecoGUI.grapheco.statManager.CalculatedVar;
/**
 * Describe the world, the way agents communicate with each other, etc.
 * 
 * @author denis.phan@enst-bretagne.fr
 * @version 1.0, 24-Jul-04
 * @see modulecoFramework.modeleco.ENeighbourWorld
 * @see modulecoFramework.modeleco.EWorld
 * @see modulecoFramework.modeleco.CAgent
 */

public class World extends ENeighbourWorld {

	/**
	 * Initial values for the 4 basic parameters of this world <br>
	 * parameter 1: world size <br>
	 * parameter 2: neighbourhood type <br>
	 * parameter 3: active zone type <br>
	 * parameter 4: scheduler type <br>
	 */
	public static String initLength = "10";

	public static String initNeighbour = "World";

	public static String initZone = "World"; //"RandomIndividual";

	public static String initScheduler = "LateCommitScheduler";

	/**
	 * <tt>price</tt> is the observable variable in this world.
	 * <p>
	 * It will be displayed on the right panel (<em>Graphique</em>). It
	 * represents the aggregated value of all agents.
	 */
	protected double price;

	/**
	 * magnitude of social influence (interaction)
	 */
	protected double j;

	/**
	 * average idiosyncrasic willingness to pay equivalent to an external
	 * magnetic field in MS
	 */
	protected double h = 0.0;

	/**
	 * number of buyers at time t-1
	 * buyers will become equal at actualBuyers
	 * only when the market clear / world commit
	 */
	protected int previousBuyers = 0;
	/**
	 * number of buyers at time t (before commitment)
	 * reset to zero when market clear / world commit
	 */
	protected int actualBuyers = 0;


	/**
	 * % of buyers at time t = buyers/agentSetSize
	 * 
	 * Warning ! Dont use it to have eta ! this variable is only for the
	 * descriptor. Use getEta() !
	 */
	protected double eta;
	/**
	 * initialBelief about eta : typically (default) 0, 1, 0.5
	 */
	protected double initialBelief;

	/**
	 * Random generators random is always uniform
	 */
	protected long seed;

	protected RandomSD random;

	/**
	 * Random generators with standard deviation sd2
	 */
	protected long seed2;

	protected static double sd2 = 1.0;

	protected RandomSD random2;

	private static String randomPath = "modulecoFramework.modeleco.randomeco.";

	protected String random_s2;

	/**
	 * price incremental step
	 */
	protected double priceStep;

	/**
	 * true : increment the price by an incremental step
	 * false : decrement the price by an incremental step
	 */
	protected boolean incrementPrice;
	/**
	 * learning EWA parameters
	 */
	public double mu0, mu, kapa, phi, delta;

	/**
	 * Trempling hand parameters
	 */
	public double alpha, beta;

	/**
	 * Agent Decision Rule "noTrembleDEWA" : (deterministicDEWA) Agent play always his
	 * Dominant EWA (experience Weighted Attraction) given the observed ex post value
	 * of his (possibly virtual, and negative) surplus in the case of buy.
	 * if kapa >= 0 and phi = 0 mu = 1 for all kapa and all all mu0
	 * Then, for delta = 1 EWA learning rule coupled with noTrembleDEWA
	 * is equivalent with the (non perturbed) Myopic Best-Response Strategy Revision. 
	 * 
	 * "LogitTrembleDEWA" Agent play his Dominant EWA according to the logistic
	 * pdf, with parameter beta = 1/(pi*sigma*squareroot(3))
	 * 
	 * "LinearTrembleDEWA" Agent play his Dominant EWA with probability alpha and
	 * play at random with probability 1-alpha. Agent play his best response
	 * with probability : alpha + (1-alpha)/cardS
	 *  
	 */
	protected String decisionRule;

	protected double thetaMin = 0;

	protected double thetaMax = 0;

	/**
	 * measure the size of avalanche
	 */
	//double ava;
	/**
	 * Class to be manage avalanche computation
	 */
	//public Avalanche avalanche; //(to remove ?)
	/**
	 * dynamic state of the system ("stable" or "running")
	 */
	//protected String sysState = "Stable"; // "Running"
	/**
	 * Constructor
	 * 
	 * @param length
	 *            square root of the "size" of the world (e.g. agentSetSize =
	 *            number of agents) agentSetSize = length x length)
	 */
	public World(int length) {
		super(length);
	}

	/**
	 * Used by <tt>EWorld</tt>, when a new world is created.
	 * <p>
	 * Mandatory method (inherited from its abstract superclass in
	 * <tt>EWorld</tt>).
	 * 
	 * @see modulecoFramework.modeleco.EWorld
	 * @see modulecoFramework.modeleco.CAgent
	 */
	public void getInfo() {
		/**
		 * set the default value of the initial price
		 * to be independent of the last value of the price ?
		 */
		price=0.7;
		//System.out.println("[World.getInfo()]");
		//sysState = "Running"; //AVERIFIER
	}

	/**
	 * Set the default values when the world is created for the very first time.
	 * <p>
	 * Initialise HERE (and not in the constructor or init()) the parameters you
	 * might want to change through the bottom panel (probe : "descriptors") (
	 * <em>World Editor</em>).
	 * <p>
	 * Mandatory method (inherited from its abstract superclass in
	 * <tt>EWorld</tt>)
	 * 
	 * @see modulecoFramework.modeleco.EWorld
	 */
	public void setDefaultValues() {
		//System.out.println("[World.setDefaultValues()]");
		/**
		 * set the default value of the initial agent's choice
		 */
		//this.setRepart("NobodyBuy");
		/**
		 * set the default value of the seed for the standard (ie uniform)
		 * random generator for initialisation of the agents (viki)
		 */
		setSeed(0); // random seed
		random = new RandomSD(seed);
		/**
		 * set the default value for theta random generator
		 */
		random_s2 = "JavaLogistic";
		setSeed2(0);
		setRandom_s2(random_s2);
    	/**
		 * set the default value of the initial agents's J
		 */
		setJ(1);
		/**
		 * set the default value of the incremental P step
		 */
		priceStep = 0.1;
		/**
		 * set the default value of the incremental J step
		 */
		//this.jStep = 0.5; //PAR BALAYAGE
		
		/**
		 * initialBelief about eta : typically (default) 0, 1, 0.5
		 */
		initialBelief = 0.0;
		/**
		 * learning EWA parameters
		 * mu = (1 - kapa) * mu / (phi + mu) + kapa * (1 - phi);
		 * if kapa = 0 => mu = mu / (phi + mu) which converge to (1 - phi)
		 * if kapa >= 0 and phi = 0 mu = 1 for all kapa and all all mu0
		 * if kapa = 1 => mu = (1 - phi) for all mu0
		 * 
		 * updatSurplus = (delta + (1 - delta) * I(w(t), 1))* Surplus(1,eta) ;
		 * A1 = (1 - mu) * A1 + mu * updatSurplus;
		 * Thus, if choice = 1 (to buy) :
		 * A1 = (1 - mu) * A1 + mu * Surplus(1,eta);
		 * if choice = 0 (do not buy :
		 * A1 = (1 - mu) * A1 + mu * delta * Surplus(1,eta);
		 * 
		 * if kapa=phi=1, then : mu = 0. if moreover, delta = 1 :
		 * then attraction equivalent at "pure" myopic best response :
		 * A1 = Surplus(1,eta);
		 */
		kapa = 1;//0
		mu0 = 0.5 ;
		phi = 0;
		delta = 1;
		/**
		 * Decision rule
		 */
		decisionRule = "noTrembleDEWA";
		
		setIncrementPrice(false);
		mu = (1 - kapa) * mu0 / (phi + mu0) + kapa * (1 - phi);
	}

	/**
	 * Set by default the World pevious Value in case of world reload Generally
	 * not used here, but overided because random have no descriptor
	 */
	public void setPreviousValues() {
		super.setPreviousValues();
		random = new RandomSD(seed); // no descriptor Voir usage ??
		mu = (1 - kapa) * mu0 / (phi + mu0) + kapa * (1 - phi);
	}

	/**
	 * Initialise the simulation.
	 * <p>
	 * In particular, this is where you add some variables to the
	 * <tt>statManager</tt> to be recorded or displayed in the right panel (
	 * <em>Graphique</em>).
	 */
	public void init() {
		//avalanche = new Avalanche(this);
		Agent ag;
		for (Iterator i = iterator(); i.hasNext();) {
			ag = (Agent) i.next();
			if (ag.theta < thetaMin)
				thetaMin = ag.theta;
			if (ag.theta > thetaMax)
				thetaMax = ag.theta;
		}
		System.out.println("[World.init()] - thetaMin = " + thetaMin
				+ " thetaMax = " + thetaMax);
		/**
		 * Add the variables to observe through the <em>statManager</em>
		 */
		try {
			/**
			 * <ul>
			 * <li>"statPrice" is the name used by the statManager
			 * <li>"getPrice" is the exact name of the method that returns the
			 * variable. For the <tt>Trace</tt> and the
			 * <tt>Graphique</t> to work properly, getPrice() MUST RETURN a double (@see
			 * modulecoGUI.grapheco.graphix.Trace)
			 * </ul>
			 */
			statManager.add(new Var("statNormalizedPrice", Class.forName(
					this.pack() + ".World").getMethod("getNormalizedPrice",
					null)));
			statManager.add(
					new CalculatedVar(
						"Eta",
						Class.forName(this.pack() + ".Agent").getMethod(
							"getState",
							null),
						CalculatedVar.NUMBER,
						new Boolean(true)));
		}

		catch (ClassNotFoundException e) {

			System.out.println(e.toString());
		}

		catch (NoSuchMethodException e) {

			System.out.println(e.toString());
		}
	}

	/**
	 * Before the agents have computed and committed, the world
	 * compute first
	 * <p>
	 * Invoked at each time step.
	 */
	public void compute() {
		mu = (1 - kapa) * mu / (phi + mu) + kapa * (1 - phi);
		//System.out.println("[World.computeWorldFirst()]");
	}
	/**
	 * Once the agents have computed and committed, the world commit
	 * <p>
	 * Invoked at each time step.
	 */
	public void commit() {
		boolean tempEquilibrium = (previousBuyers == actualBuyers);
		//System.out.println("previousBuyers"+previousBuyers+"actualBuyers"+actualBuyers);
		/*
		 * market clear : the actualBuyers will become the
		 * previousBuyers of the next step
		 */
		previousBuyers = actualBuyers;
		actualBuyers = 0;
		/*
		 * the ex-post surplus is computed at the current price
		 * with (actual) buyers
		 */
		for (Iterator i = iterator(); i.hasNext();)
			((Agent) i.next()).computeExpostSurplus(price);
		/*
		 * If the number of customer is in equilibrium for a given
		 * price (temporary equilibrium), one decrement the price
		 */
		if (tempEquilibrium)
			if(incrementPrice)
				price = price + priceStep;
				else
					price = price - priceStep;
	}
	
	public void incrementBuyers() {
		actualBuyers++;
	}
	/**
	 * global penetration rate in the population (% of buyers) eta = buyers /
	 * agentSetSize The value of eta depend on the schedulling
	 * 
	 * @return eta
	 * @see
	 */
	public double getEta() {
		eta = (double) previousBuyers / (double) agentSetSize;
		return eta;
	}
	//============================================================
	/**
	 * Add to the bottom panel (<em>World Editor</em>) the variables (resp
	 * parameters) to observe (resp change) during the simulation.
	 * <p>
	 * Those variables should have been initialised in setDefaultValues() when
	 * the world is created for the very first time.
	 * <p>
	 * Invoked at each time step.
	 */
	public ArrayList getDescriptors() {
		descriptors.clear();
		/**
		 * <ul>
		 * <li>"Price" is the complete name to display in the bottom panel
		 * (World Editor)
		 * <li>"price" is the variable name. A method "setPrice()" MUST EXIST
		 * <li>price is the variable itself
		 * <li>false means that you cannot change the value of the variable
		 *  
		 */
		//descriptors.add(new ChoiceDataDescriptor(this,
		//" Initial configuration", "repart", new String[] {
		//	"NobodyBuy", "EverybodyBuy", "Random" }, repart, true));
	//descriptors.add(new InfoDescriptor(""));
		//descriptors.add(new InfoDescriptor(""));
		//line 1
		descriptors.add(new LongDataDescriptor(this, "Seed2(Theta)", "seed2",
				seed2, true, 3));
		descriptors
				.add(new ChoiceDataDescriptor(this, "  AgentRandom",
						"random_s2", new String[] { "JavaRandom",
								"JavaLogistic", "IdenticalAgents",
								"JavaTriangular" }, random_s2, true));
		descriptors.add(new LongDataDescriptor(this, "Seed Init", "seed", seed,
				true, 3));
		descriptors.add(new ChoiceDataDescriptor(this, "Decision Rule",
				"decisionRule", new String[] { "noTrembleDEWA", "LogitTrembleDEWA",
						"LinearTrembleDEWA" }, decisionRule, true));
		//line 2
		descriptors.add(new BooleanDataDescriptor(this, "IncrementPrice", "incrementPrice",
				incrementPrice, true));
		descriptors.add(new DoubleDataDescriptor(this, "  Price", "price",
				price, true));
		descriptors.add(new DoubleDataDescriptor(this, "  Price Step",
				"priceStep", priceStep, true));
		descriptors.add(new InfoDescriptor(""));
		//line 3
		descriptors.add(new DoubleDataDescriptor(this, "  J", "j", j, true));
		descriptors.add(new IntegerDataDescriptor(this, "  Buyers", "previousBuyers",
				previousBuyers, false));
		descriptors.add(new DoubleDataDescriptor(this, "  % Eta", "eta", eta,
				false));
		descriptors.add(new DoubleDataDescriptor(this, " Initial Belief", "initialBelief",
				initialBelief, true));
		descriptors.add(new DoubleDataDescriptor(this, "EWA delta", "delta",
				delta, true));
		descriptors.add(new DoubleDataDescriptor(this, "EWA phi", "phi", phi,
				true));
		descriptors
		.add(new DoubleDataDescriptor(this, "EWA mu0", "mu0", mu0, true));
		descriptors
		.add(new DoubleDataDescriptor(this, "EWA mu", "mu", mu, false));
		descriptors.add(new DoubleDataDescriptor(this, "EWA kapa", "kapa",
				kapa, true));

		descriptors.add(new InfoDescriptor("delta =1 & phi = 0 ->"));
		descriptors.add(new InfoDescriptor(" myopicBestResponseSR"));

		//descriptors.add(new DoubleDataDescriptor(this, " J Step", "jStep",
		//jStep, true));
		//descriptors.add(new DoubleDataDescriptor(this, " Avalanche ", "ava",
		//	ava, false));
		//descriptors.add(new ChoiceDataDescriptor(this, " Status", "sysState",
		//new String[] { "Running", "Stable" }, sysState, false));
		return descriptors;
	}

	public void setInitialBelief(double ib){
		initialBelief = ib;
	}
	
	public double getInitialBelief(){
		return initialBelief ;
	}
	
	public void setIncrementPrice(boolean test) {
		incrementPrice = test;
	}
	
	public void setDecisionRule(String s) {
		this.decisionRule = s;
	}

	public String getDecisionRule() {
		return decisionRule;
	}

	/*
	 * public void setRepart(String r) {
	 * 
	 * this.repart = r; if (r.equalsIgnoreCase("NobodyBuy")) this._stateofspin =
	 * 0; else if (r.equalsIgnoreCase("EverybodyBuy")) this._stateofspin = 1;
	 * else this._stateofspin = 2; //"Random" }
	 */
	public void setSeed(long d) { //   <- erreur ?

		this.seed = (long) d;
	}

	public RandomSD getRandom() {

		return random;
	}

	public void setRandom_s2(String s) {

		if (s.equals("JavaRandom"))
			random_s2 = "RandomSD";
		else
			random_s2 = s;
		Object RandomSDConstructorParameters[] = new Object[] { new Long(seed),
				new Double(sd2) };
		//System.out.println("models.discreteChoice2.setRandom_s2 s = "+random_s2);
		//sd = " + sd2+ " seed =" + seed);
		try {
			Constructor constructor = Class.forName(randomPath + random_s2)
					.getConstructor(new Class[] { long.class, double.class });
			random2 = (RandomSD) constructor
					.newInstance(RandomSDConstructorParameters);
		}
		/*
		 * catch (IllegalAccessException e){ System.out.println(e.toString());}
		 * 
		 * catch (InstantiationException e){ System.out.println(e.toString());}
		 * 
		 * catch (ClassNotFoundException e){ random = null;}
		 */

		catch (Exception e) {

			System.out.println(e.toString());
		}
	}

	public void setSeed2(long d) {

		this.seed2 = (long) d;
	}

	public RandomSD getRandom2() {

		return random2;
	}

	public void setPrice(double price) {

		this.price = price;
	}

	/**
	 * normalisation des prix pour le graphique : n'est pas utilisé par les
	 * agents (revoir !!!)
	 * 
	 * @return
	 */

	public double getPrice() {

		return price; //10.0 * (price + 5.0);
	}

	public double getNormalizedPrice() { // VERFIER UTILITE

		return 10.0 * (price + 5.0);
	}

	public void setJ(double j) {

		this.j = j;
	}

	public double getJ() {

		return j;
	}

	public void setPreviousBuyers(int pb) {

		previousBuyers = pb;
	}
	public int getPreviousBuyers() {
		System.out.println("getPreviousBuyers() = "+previousBuyers);
		return previousBuyers;
	}

	public void setPriceStep(double d) {

		this.priceStep = d;
	}

	public double getPriceStep() {

		return this.priceStep;
	}

	/**
	 * learning EWA parameters
	 */
	public void setMu0(double mu0) {
		this.mu0 = mu0;
	}

	public double getMu() {
		return mu;
	}
	
	public double getMu0() {
		return mu0;
	}

	public void setKapa(double kapa) {
		this.kapa = kapa;
	}

	public double getKapa() {
		return kapa;
	}

	public void setPhi(double phi) {
		this.phi = phi;
	}

	public double getPhi() {
		return phi;
	}

	public void setDelta(double delta) {
		this.delta = delta;
	}

	public double getDelta() {
		return delta;
	}

	/**
	 * Compute CumulativeDistribution of the theta
	 * 
	 * @param d
	 * @return frequency
	 */
	public double getCumulativeDistribution(double d) {

		int cumul = 0;
		Agent ag;
		for (Iterator i = iterator(); i.hasNext();) {
			ag = (Agent) i.next();
			if (ag.theta <= d)
				cumul++;
		}
		double frequency = (double) 100 * ((double) cumul) / ((double) size());
		//System.out.println("frequency theta = "+frequency);
		return frequency;
	}
} //END_CLASS
