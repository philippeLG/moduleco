/*
 * Source File Name: discreteChoice.World.java Copyright: Copyright
 * (c)enst-bretagne @author : Denis.Phan@enst-bretagne.fr
 * 
 * @version 1.4 February, 2004
 */
package models.discreteChoice;
import java.util.Iterator;
import java.util.ArrayList;
import java.lang.reflect.Constructor;
import modulecoFramework.modeleco.EAgent;
import modulecoFramework.modeleco.ENeighbourSmallWorld;
import modulecoFramework.medium.Medium;
import modulecoFramework.modeleco.randomeco.CRandomDouble;
import modulecoFramework.modeleco.randomeco.RandomSD;
//import modulecoFramework.modeleco.SimulationControl;
import modulecoGUI.grapheco.statManager.CalculatedVar;
import modulecoGUI.grapheco.descriptor.ChoiceDataDescriptor;
import modulecoGUI.grapheco.descriptor.LongDataDescriptor;
import modulecoGUI.grapheco.descriptor.DoubleDataDescriptor;
/**
 * 
 *  
 */
public class World extends ENeighbourSmallWorld { //ENeighbourSmallWorld
	/**
	 * Initial values for the 4 basic parameters of this world <br>
	 * parameter 1: world size <br>
	 * parameter 2: neighbourhood type <br>
	 * parameter 3: active zone type <br>
	 * parameter 4: scheduler type <br>
	 */
	public static String initLength = "36";
	public static String initNeighbour = "World";
	public static String initZone = "World";
	public static String initScheduler = "LateCommitScheduler";
	/**
	 *  
	 */
	protected CRandomDouble random;
	protected RandomSD random2;
	protected long seed, seed2;
	protected double sd;
	protected int ns; // NeighbourSize
	protected String random_s, random_s2, thetaType;
	private static String randomPath = "modulecoFramework.modeleco.randomeco.";
	protected Autorun autorun; // DP 20/08/2002
	/**
	 * @param length
	 */
	public World(int length) {
		super(length);
		//autorun = centralControl.getAutorun(); // DP 20/08/2002
		//autorun.setWorld(this);
	}
	/**
	 * getInfo() receieve Info from the Eworld Before connection and
	 * initialisation
	 * 
	 * @see modulecoFramework.modeleco.ENeighbourWorld
	 *  
	 */
	public void getInfo() {
		//System.out.println("discreteChoice.World.getInfo");
	}
	public void setDefaultValues() {
		//System.out.println("1 - discreteChoice.World.setDefaultValues()");
		seed2 = 190; // always before setRandom
		sd = 0.1; // always before setRandom
		setRandom_s2("JavaLogistic");
		// RandomGenrationClass must always be AFTER seed
		setThetaType("Deterministic");
	}
	/**
	 * populate()
	 */
	public void populate() {
		super.populate();
		//System.out.println(" world.populate()DEBUT "+getCapacity());
		extraAgents = new Seller[1];
		extraAgents[0] = new Seller();
		((EAgent) extraAgents[0]).setWorld(this);
		extraAgents[0].getInfo();
		// setup the neighbourhood settlement
		// initialize market
		mediumsInWorld = new Medium[1];
		mediumsInWorld[0] = new Market();
		//System.out.println(" world.populate()FIN");
	}
	/**
	 * connect()
	 *  
	 */
	public void connect() {
		// connect the Neighbourhood
		super.connect(); // ENeighbourWorld.connect()
		for (Iterator i = iterator(); i.hasNext();) {
			getMarket().attach(((Agent) i.next()), "customer");
			//((Agent)i.next()).setMarket(market);
		}
		getMarket().attach(extraAgents[0], "Seller");
		// connect the seller to the market
		//System.out.println(" world.connect() ");
	}
	/**
	 * init()
	 */
	public void init() {
		super.init();
		try {
			statManager.add(new CalculatedVar("State", Class.forName(
					this.pack() + ".Agent").getMethod("getState", null),
					CalculatedVar.NUMBER, new Boolean(true)));
			statManager.add(new CalculatedVar("Changes", Class.forName(
					this.pack() + ".Agent").getMethod("hasChanged", null),
					CalculatedVar.NUMBER, new Boolean(true)));
		} catch (ClassNotFoundException e) {
			System.out.println(e.toString());
		} catch (NoSuchMethodException e) {
			System.out.println(e.toString());
		}
		//System.out.print(" world.init - "+ getNeighbourSelected());
	}
	/**
	 * commit()
	 */
	public void commit() {
		boolean testPrice = ((Seller) extraAgents[0]).getRestartTest();
		boolean testTotalAdoption = ((Seller) extraAgents[0]).getCustomers() >= agentSetSize;
		if (testPrice || testTotalAdoption) { // DP 20/08/2002
			simulationControl.stop();
			System.out.println("Seed2 " + seed2 + " ; P = ; "
					+ ((Seller) extraAgents[0]).maxPrice + " ; N = ; "
					+ ((Seller) extraAgents[0]).maxProfitCustomers
					+ " ; profit = ; " + ((Seller) extraAgents[0]).maxProfit);
			//autorun.test();
			//seed2=seed2+5;
			//autorun.restart();
		} else {
			super.commit();
			getMarket().marketClear();
			// pas forcément nécessaire ici // SP 17/01/2003
		}
		//System.out.println(" world.commit() ");
	}
	protected String Format4Double(double db) {
		// format BEGIN
		String tempString = (new Integer((new Double(db * 10000)).intValue()))
				.toString();
		String formatedString = tempString.substring(0, 1) + "."
				+ tempString.substring(1);
		// Format END
		return formatedString;
	}
	//====================================================
	public Object getState() {
		return new Boolean(true);
	}
	public Market getMarket() {
		//System.out.println(" world.getMarket() ");
		return (Market) mediumsInWorld[0];
	}
	public ArrayList getDescriptors() {
		//descriptors.clear();
		//descriptors.add(new InfoDescriptor("Test","Test"));
		descriptors = super.getDescriptors();
		//Seed MUST ALWAYS been calculated BEFORE RandomChoice
		descriptors.add(new LongDataDescriptor(this, "Seed 2", "seed2", seed2,
				true, 3));
		descriptors.add(new DoubleDataDescriptor(this, "Standard deviation",
				"sd", sd, true));
		descriptors.add(new ChoiceDataDescriptor(this, "AgentRandom",
				"random_s2", new String[]{"Default", "JavaRandom",
						"JavaGaussian", "JavaLogistic"}, random_s2, true));
		descriptors.add(new ChoiceDataDescriptor(this, "Theta Type",
				"thetaType", new String[]{"Random", "Deterministic"},
				thetaType, true));
		return descriptors;
	}
	public void setSeed2(long d) {
		seed2 = d;
		//System.out.println("Seed : "+seed);
		//On ne peut modifier la graine qu'au debut d'une simulation,
		//pas en cours de simulation.
	}
	public void setThetaType(String s) {
		thetaType = s;
	}
	/*
	 * public void setRandom_s2(String s){
	 * 
	 * random_s2=s; try{ Constructor constructor =
	 * Class.forName(randomPath+random_s2).getConstructor(new Class[]
	 * {long.class}); random2 = (Random) constructor.newInstance(new Object[]
	 * {new Long(seed2)}); //System.out.println("random_s2"+random2+" seed
	 * "+seed2); }
	 * 
	 * 
	 * catch (Exception e){
	 * 
	 * System.out.println(e.toString()); }
	 *  }
	 */
	public void setRandom_s2(String s) {
		random_s2 = s;
		Object RandomSDConstructorParameters[] = new Object[]{new Long(seed),
				new Double(sd)};
		try {
			//System.out.println("models.DBBGame.random_s sd = "+sd+ " seed =
			// "+seed);
			Constructor constructor = Class.forName(randomPath + random_s2)
					.getConstructor(new Class[]{long.class, double.class});
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
	public RandomSD getRandom2() {
		return random2;
	}
	// SmallWorld
	public void RandomGeneratorsetDefaultValues() {
		seed = 1;
		random_s = "JavaRandom";
		setRandom_s(random_s);
		nNodes = 0;
		removedLinks = 0;
		//System.out.println(" 2 -
		// discreteChoice.World.RandomGeneratorsetDefaultValues()");
	}
	/**
	 * @param theta_s
	 * @return
	 */
	public double getCumulativeDistribution(double theta_s) {
		int cumul = 0;
		Agent ag;
		for (Iterator i = iterator(); i.hasNext();) {
			ag = (Agent) i.next();
			if (ag.getTheta() > theta_s)
				cumul++;
		}
		double frequency = (double) 100 * ((double) cumul) / ((double) size());
		//System.out.println("frequency theta = "+frequency);
		return frequency;
	}
	public double getCumulativeWP(double wp) {
		int cumul = 0;
		Agent ag;
		for (Iterator i = iterator(); i.hasNext();) {
			ag = (Agent) i.next();
			if (ag.getAdjustedWP() > wp)
				cumul++;
		}
		double frequency = (double) 100 * ((double) cumul) / ((double) size());
		//System.out.println("frequency wp= "+frequency);
		return frequency;
	}
	public double getCumulativeEta(double s) {
		int cumul = 0;
		Agent ag;
		for (Iterator i = iterator(); i.hasNext();) {
			ag = (Agent) i.next();
			if (ag.getEta() < s) {
				cumul++;
			}
		}
		double frequency = (double) 100 * ((double) cumul) / ((double) size());
		//System.out.println("cumulEta = "+frequency+" s = "+s);
		return frequency;
	}
	public void setNeighbourSize(int ns) {
		this.ns = ns;
		//System.out.println("NeighbourSize = "+ns);
	}
	public int getNeighbourSize() {
		//ns = 4;
		return ns;
	}
	public long getSeed2() { // DP 20/08/2002
		return seed2;
	}
	public void setSd(double s) {
		sd = s;
		//System.out.println("models.DBBGame.setSd = "+sd);
	}
	public double getSd() {
		return sd;
	}
}