/**
 * Title: Moduleco - BBD.World.java <p>
 * Description:  Je fais jouer des Agents à des jeux symétriques bilatéraux
 * avec une matrice de gain donnée. <p>
 * Copyright : (c)enst-bretagne
 * @author Denis.Phan@enst-bretagne.fr
 * @version 1.4  February, 2004
 */

package models.DBBGame;

import java.util.Iterator;
import java.util.ArrayList;
import java.lang.reflect.Constructor;

import modulecoFramework.modeleco.randomeco.CRandomDouble;
import modulecoFramework.modeleco.randomeco.RandomSD;
import modulecoFramework.modeleco.ENeighbourWorld;

import modulecoGUI.grapheco.statManager.CalculatedVar;
import modulecoGUI.grapheco.descriptor.IntegerDataDescriptor;
import modulecoGUI.grapheco.descriptor.DoubleDataDescriptor;
// import modulecoGUI.grapheco.descriptor.BooleanDataDescriptor;
import modulecoGUI.grapheco.descriptor.ChoiceDataDescriptor;
import modulecoGUI.grapheco.descriptor.InfoDescriptor;
import modulecoGUI.grapheco.descriptor.LongDataDescriptor;

public class World extends ENeighbourWorld {
	/**
	 * Initial values for the 4 basic parameters of this world <br>
	 * parameter 1: world size <br>
	 * parameter 2: neighbourhood type <br>
	 * parameter 3: active zone type <br>
	 * parameter 4: scheduler type <br>
	 */
	public static String initLength = "31";
	public static String initNeighbour = "NeighbourVonNeuman";
	public static String initZone = "World";
	public static String initScheduler = "LateCommitScheduler";
	/**
	 *  
	 */
	protected double propS1; // vrai si majorité de S1-joueurs
	protected double s1AgainstS1, s2AgainstS1, s1AgainstS2, s2AgainstS2;
	protected double k, h, j;
	protected int nS2;
	protected double[][] matriceGain;
	protected String revisionRuleIndex;
	protected CRandomDouble random2;
	protected RandomSD random;
	//random_s : Rand.Config ; tremble CPR.Seed2 : random_s2 
	protected String random_s, random_s2;
	//protected Boolean _load;
	protected double rateS2, tremble, sd;
	protected long seed, seed2;
	private static String randomPath = "modulecoFramework.modeleco.randomeco.";

	/**
	 * 
	 * @param length
	 */
	public World(int length){
		super(length); 
		//matriceGain = new int [2][2];		//SC 14.06.01
	}
	public void getInfo() {
		//System.out.println("DBBGame. getInfo()");
		matriceGain = new double[2][2]; // conserver
	}
	public void setDefaultValues() {
		//System.out.println("DBBGame.setDefaultValues()");

		//revisionRuleIndex="myopicBestReply";
		k = 0; //5;
		h = 0; //-1;
		j = 2;
		rateS2 = 0.04;
		tremble = 0.4;

		sd = 1;
		seed = 6;
		random_s = "JavaLogistic";
		setRandom_s("JavaLogistic");
		seed2 = 1;
		setRandom_s2("JavaRandom");
		//revisionRuleIndex="myopicBestReply";
		revisionRuleIndex = "BehaviorialCPR";
		//setRandom_s("JavaRandom");

		// matriceGain : Matrix constructed by this.getInfo()
		// during all world's creation process

	}

	public void init() {
		Agent dpa;
		double temp;
		double minS1 = 32, maxS1 = 32;
		double maxS2 = 48, minS2 = 48, mean = 0, surplusS2 = 0, surplusS1 = 0;

		// System.out.println("models.DBBGame.world.init()");
		s1AgainstS1 = k + h + j;
		s2AgainstS1 = k - h - j;
		s1AgainstS2 = k + h - j;
		s2AgainstS2 = k - h + j;
		matriceGain[0][0] = s1AgainstS1;
		matriceGain[1][0] = s2AgainstS1;
		matriceGain[0][1] = s1AgainstS2;
		matriceGain[1][1] = s2AgainstS2;
		nS2 = 0;

		for (Iterator i = this.iterator(); i.hasNext();) {
			dpa = (Agent) i.next();
			if (!dpa.newStrategy)
				nS2++;
			mean += dpa.epsilon;
			surplusS2 += dpa.surplusS2;
			surplusS1 += dpa.surplusS1;
			if (dpa.surplusS2 > maxS2)
				maxS2 = dpa.surplusS2;
			if (dpa.surplusS2 < minS2)
				minS2 = dpa.surplusS2;
			if (dpa.surplusS1 > maxS1)
				maxS1 = dpa.surplusS1;
			if (dpa.surplusS1 < minS1)
				minS1 = dpa.surplusS1;
		}
		surplusS1 = surplusS1 / 32 / 32;
		surplusS2 = surplusS2 / 32 / 32;
		mean = mean / 32 / 32;
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

		/* rebuild neighborhood WHY AB
		  String nType = ((EAgent) (get(0))).getNeighbours().getType();
		  
		  for (int i=0;i< agentSetSize ;i++)
		      ((EAgent) (get(i))).getNeighbours().quit((EAgent) (get(i)));
		  
		  for (int i=0;i< agentSetSize ;i++)
		  {
		      if ( !((EAgent) get(i)).isConnected(nType) )
		          ((EAgent) get(i)).setNeighborhood(nType);
		  }*/
		Agent dpa;
		nS2 = 0;

		for (Iterator i = this.iterator(); i.hasNext();) {
			dpa = (Agent) i.next();
			if (!dpa.newStrategy)
				nS2++;
		}
		//System.out.println("World.compute() : nS2 = "+nS2);
	}
	/*
	   public void commit(){
	      
	   }
	*/
	public Object getState() {
		return new Double(propS1);
	}

	// Les services de mise à jour accessibles du package uniquement
	// Mets la même valeur à tous les Agents
	// non activé l'agent lit les valeurs dans le monde

	public double getS1AgainstS1() {
		return s1AgainstS1;
	}
	public double getS1AgainstS2() {

		return s1AgainstS2;
	}
	public double getS2AgainstS1() {

		return s2AgainstS1;
	}
	public double getS2AgainstS2() {

		return s2AgainstS2;
	}
	public void setS1AgainstS1(double s1AgainstS1) {
		this.s1AgainstS1 = s1AgainstS1;
		matriceGain[0][0] = s1AgainstS1;
	}

	public void setS2AgainstS1(double s2AgainstS1) {
		this.s2AgainstS1 = s2AgainstS1;
		matriceGain[1][0] = s2AgainstS1;
	}

	public void setS1AgainstS2(double s1AgainstS2) {
		this.s1AgainstS2 = s1AgainstS2;
		matriceGain[0][1] = s1AgainstS2;
		//System.out.println("setS1AgainstS2 ; matriceGain[0][1] : "+matriceGain[0][1]);
	}

	public void setS2AgainstS2(double s2AgainstS2) {
		this.s2AgainstS2 = s2AgainstS2;
		matriceGain[1][1] = s2AgainstS2;
	}

	public String getRevisionRuleIndex() {
		return revisionRuleIndex;
	}

	public void setRevisionRuleIndex(String s) {

		revisionRuleIndex = s;
		//System.out.println("Maintenant, revisionRuleIndex vaut "+revisionRuleIndex);
	}
	/**
	 * Invoked by ENeighbourWorld.populateAll(nsClass)  
	 * to be executed first, before the end of this world's constructor
	 * during each world's creation process.
	 */

	public void setK(double k) {
		this.k = k;
	}

	public double getK() {
		return k;
	}

	public void setH(double h) {
		this.h = h;
	}

	public double getH() {
		return h;
	}
	public void setJ(double j) {
		this.j = j;
		//System.out.println("DBBGame.setJ()");
	}

	public double getJ() {
		return j;
	}

	public double getRateS2() {

		return rateS2;
	}

	public void setRateS2(double r) {

		rateS2 = r;
	}

	public ArrayList getDescriptors() {
		descriptors.clear();

		descriptors.add(
			new DoubleDataDescriptor(
				this,
				"S1 against S1",
				"s1AgainstS1",
				s1AgainstS1,
				false));
		descriptors.add(
			new DoubleDataDescriptor(
				this,
				"S1 against S2",
				"s1AgainstS2",
				s1AgainstS2,
				false));
		descriptors.add(
			new IntegerDataDescriptor(
				this,
				"init.NumberOf S2",
				"nS2",
				nS2,
				false));
		descriptors.add(new InfoDescriptor(""));
		descriptors.add(
			new DoubleDataDescriptor(
				this,
				"S2 against S1",
				"s2AgainstS1",
				s2AgainstS1,
				false));
		descriptors.add(
			new DoubleDataDescriptor(
				this,
				"S2 against S2",
				"s2AgainstS2",
				s2AgainstS2,
				false));
		descriptors.add(
			new ChoiceDataDescriptor(
				this,
				"StrategyRevisionRule",
				"revisionRuleIndex",
				new String[] {
					"myopicBestReply",
					"LastNeighbour.BestPayoff",
					"LN.BestAveragePayoff",
					"BehaviorialCPR" },
				revisionRuleIndex,
				true));
		descriptors.add(new InfoDescriptor(""));

		//Seed MUST ALWAYS been calculated BEFORE RandomChoice
		descriptors.add(
			new LongDataDescriptor(
				this,
				"Rand.Conf.Seed",
				"seed",
				seed,
				true,
				3));
		descriptors.add(
			new DoubleDataDescriptor(
				this,
				"Standard deviation",
				"sd",
				sd,
				true));
		descriptors.add(
			new ChoiceDataDescriptor(
				this,
				"Ag.RandomConfig",
				"random_s",
				new String[] {
					"Default",
					"JavaRandom",
					"JavaLogistic",
					"JavaGaussian",
					"IdenticalAgents" },
				random_s,
				true));

		descriptors.add(new InfoDescriptor(""));
		descriptors.add(
			new DoubleDataDescriptor(this, "k =         ", "k", k, true));
		descriptors.add(new DoubleDataDescriptor(this, "h = ", "h", h, true));
		descriptors.add(
			new DoubleDataDescriptor(this, "j =         ", "j", j, true));
		descriptors.add(new InfoDescriptor(""));
		descriptors.add(
			new LongDataDescriptor(this, "CPR.Seed2", "seed2", seed2, true, 3));
		descriptors.add(
			new ChoiceDataDescriptor(
				this,
				"TrembleRand.Conf",
				"random_s2",
				new String[] { "Default", "JavaRandom" },
				random_s2,
				true));
		descriptors.add(
			new DoubleDataDescriptor(
				this,
				"tremble =         ",
				"tremble",
				tremble,
				true));
		return descriptors;

	}

	public void setRandom_s(String s) {
		// BUG OBSERVED 10/03/2004 : when choice selected,  setRandom_s is invoked twice
		//System.out.println("1 s = "+s+" random_s"+random_s);
		// one time with old random_s next with NEW random_s
		/*if(s!=random_s){
		   System.out.println("DBBGame : new random_s restart the model !");
		   random_s=s;
		   System.out.println("2 s = "+s+" random_s"+random_s);
		}
		else{*/
		//REVOIR LA PROCEDURE DE DEMARRAGE AVEC LES CHOIX DE MENU DEROULANT
		random_s = s;
		Object RandomSDConstructorParameters[] =
			new Object[] { new Long(seed), new Double(sd)};

		try {
			System.out.println(
				"models.DBBGame.random_s sd = " + sd + " seed = " + seed);

			Constructor constructor =
				Class.forName(randomPath + random_s).getConstructor(
					new Class[] { long.class, double.class });
			random =
				(RandomSD) constructor.newInstance(
					RandomSDConstructorParameters);
		}
		/*
		catch (IllegalAccessException e){
		   System.out.println(e.toString());}
			
		catch (InstantiationException e){
		   System.out.println(e.toString());}
		
		catch (ClassNotFoundException e){
		   random = null;}
			*/
		catch (Exception e) {
			System.out.println(
				"models.DBBGame.World.setRandom_s " + e.toString());
		}
		//}
	}

	public long getSeed() {
		return seed;
	}

	public RandomSD getRandom() {
		return random;
	}

	public void setSeed(long d) {

		seed = d;
		//System.out.println("models.DBBGame.setSeed() seed = "+seed);
		//On ne peut modifier la graine qu'au debut d'une simulation, 
		//pas en cours de simulation.
	}

	public void setNS2(int n) {
		nS2 = n;
	}
	public int GetNS2() {
		return nS2;
	}

	public void setRandom_s2(String s) {

		random_s2 = s;
		//try{random = (Random) Class.forName("modulecoFramework.modeleco.randomeco."+random_s).newInstance();}
		try {
			Constructor constructor =
				Class.forName(randomPath + random_s2).getConstructor(
					new Class[] { long.class });
			random2 =
				(CRandomDouble) constructor.newInstance(
					new Object[] { new Long(seed)});
			//System.out.println("models.DBBGame.random_s"+random+" seed : "+seed);
		} catch (Exception e) {

			System.out.println(
				"models.DBBGame.World.setRandom_s2 " + e.toString());
		}
	}

	public long getSeed2() {

		return seed2;
	}
	public CRandomDouble getRandom2() {

		return random2;
	}

	public void setSeed2(long d) {

		seed2 = d;
		//System.out.println("models.DBBGame.setSeed() seed = "+seed);
		//On ne peut modifier la graine qu'au debut d'une simulation, 
		//pas en cours de simulation.
	}

	public void setTremble(double t) {
		tremble = t;
		//System.out.println("models.DBBGame.setTremble = "+t);
	}

	public double getTremble() {
		return tremble;
	}

	public void setSd(double s) {
		sd = s;
		//System.out.println("models.DBBGame.setSd = "+sd);
	}

	public double getSd() {
		return sd;
	}

}
