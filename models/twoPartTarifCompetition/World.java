/** class market.World
 * Title:        Moduleco<p>
 * Description:
 * Copyright:    Copyright (c)enst-bretagne
 * @author denis.phan@enst-bretagne.fr, antoine.beugnard@enst-bretagne.fr,
 * @version 1.2  august,5, 2002
 * @version 1.4. february 2004   
 */
package models.twoPartTarifCompetition;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Vector;
import java.lang.reflect.Constructor;
import java.awt.Color;
import modulecoFramework.modeleco.EAgent;
import modulecoFramework.modeleco.ENeighbourWorld;
import modulecoFramework.medium.Medium;
import modulecoFramework.modeleco.randomeco.CRandomDouble;
//directly adressed in method : setRandom_s(String s)
import modulecoGUI.grapheco.descriptor.IntegerDataDescriptor;
import modulecoGUI.grapheco.descriptor.DoubleDataDescriptor;
import modulecoGUI.grapheco.descriptor.ChoiceDataDescriptor;
import modulecoGUI.grapheco.descriptor.BooleanDataDescriptor;
import modulecoGUI.grapheco.descriptor.LongDataDescriptor;
import modulecoGUI.grapheco.statManager.CalculatedVar;
/**
 * @author denis.phan@enst-bretagne.fr,
 *  
 */
public class World extends ENeighbourWorld {
	/**
	 * Initial values for the 4 basic parameters of this world <br>
	 * parameter 1: world size <br>
	 * parameter 2: neighbourhood type <br>
	 * parameter 3: active zone type <br>
	 * parameter 4: scheduler type <br>
	 */
	public static String initLength = "50";
	public static String initNeighbour = "NeighbourVonNeuman";
	public static String initZone = "World";
	public static String initScheduler = "EarlyCommitScheduler";
	/**
	 *  
	 */
	protected int nbCompetitors;
	protected boolean proportion;
	protected double alpha;
	protected double epsilon;
	protected Vector earlyAdopters;
	protected CRandomDouble random;
	protected long seed;
	protected String random_s;
	private static String randomPath = "modulecoFramework.modeleco.randomeco.";
	protected Color colors[];
	protected boolean hack = true;
	//trick to avoid an infinite recurvive call to setNbCompetitors
	public World(int length) {
		super(length);
	}
	public void populate() {
		// super.populate();
		//System.out.println(" world.populate()DEBUT");
		extraAgents = new Competitor[nbCompetitors];
		for (int i = 0; i < nbCompetitors; i++) {
			extraAgents[i] = new Competitor(i);
			((EAgent) extraAgents[i]).setWorld(this);
			extraAgents[i].getInfo();
		}
		// setup the neighbourhood settlement
		// initialize market
		mediumsInWorld = new Medium[1];
		mediumsInWorld[0] = new Market();
		//System.out.println(" world.populate()FIN");
	}
	public void connect() {
		// connect the Neighbourhood
		super.connect(); // ENeighbourWorld.connect()
		for (Iterator i = iterator(); i.hasNext();) {
			getMarket().attach(((Agent) i.next()), "customer");
			//((Agent)i.next()).setMarket(market);
		}
		for (int i = 0; i < nbCompetitors; i++) { // create competitors = new
			// agents (out of the array)
			getMarket().attach(extraAgents[i], "competitor");
			// connect competitor to market
		}
		//System.out.println(" world.connect() ");
		earlyAdopters = new Vector();
		for (int i = 0; i < nbCompetitors; i++) {
			earlyAdopters.add(new Double(((Competitor) extraAgents[i])
					.getEarlyAdopters()));
			//System.out.println("taille de earlyAdopters :
			// "+earlyAdopters.size());
		}
	}
	public void commit() {
		super.commit();
		/*
		 * for (int i =0; i < nbCompetitors; i++) ((Competitor)
		 * extraAgents[i]).setDemand(getMarket().getDemand(i),i);
		 */
		getMarket().marketClear();
		//System.out.println(" world.commit() ");
	}
	public void init() {
		//System.out.println(" world.init()");
		colors = new Color[11];
		colors[0] = Color.white; // don't buy
		colors[1] = Color.blue; // Competitor 1
		colors[2] = Color.red; // Competitor 2
		colors[3] = Color.green; // Competitor 3
		colors[4] = Color.magenta; // Competitor 4
		colors[5] = Color.darkGray; // Competitor 5
		colors[6] = Color.cyan; // Competitor 6
		colors[7] = Color.yellow; // Competitor 7
		colors[8] = Color.gray; // Competitor 8
		colors[9] = Color.orange; // Competitor 9
		colors[10] = Color.pink; // Competitor 10
		for (int i = 0; i < nbCompetitors; i++) {
			try {
				statManager
						.add(new CalculatedVar("Competitor" + (i + 1), Class
								.forName(this.pack() + ".Agent").getMethod(
										"getState", null),
								CalculatedVar.NUMBER, new Integer(i + 1)));
			} catch (ClassNotFoundException e) {
				System.out.println(e.toString());
			} catch (NoSuchMethodException e) {
				System.out.println(e.toString());
			}
		}
		try {
			statManager.add(new CalculatedVar("Competitor0", Class.forName(
					this.pack() + ".Agent").getMethod("getBooleanState", null),
					CalculatedVar.NUMBER, new Boolean(true)));
		} catch (ClassNotFoundException e) {
			System.out.println(e.toString());
		} catch (NoSuchMethodException e) {
			System.out.println(e.toString());
		}
	}
	public Color[] getColors() {
		return colors;
	}
	public Object getState() {
		return new Boolean(true);
	}
	public int getNbCompetitors() {
		//System.out.println(" world.getNbCompetitors() ");
		return nbCompetitors;
	}
	public Vector getEarlyAdopt() {
		//System.out.println(" world.getEarlyAdopt() ");
		return earlyAdopters;
	}
	protected double getAlpha() {
		//System.out.println(" world.getAlpha() ");
		return alpha;
	}
	protected double getEpsilon() {
		return epsilon;
	}
	public void setEpsilon(double newEpsilon) {
		//System.out.println(" world.setEpsilon() ");
		epsilon = newEpsilon;
		for (Iterator i = iterator(); i.hasNext();)
			((Agent) i.next()).setEpsilon(newEpsilon);
	}
	public void setAlpha(double newAlpha) {
		alpha = newAlpha;
		for (Iterator i = iterator(); i.hasNext();)
			((Agent) i.next()).setAlpha(alpha);
	}
	public void setProportion(boolean b) {
		proportion = b;
		for (Iterator i = iterator(); i.hasNext();)
			((Agent) i.next()).setProportion(b);
	}
	public void getInfo() {
	}
	/**
	 * method call by agent.init() & competitor.int() (?double emploi ? -> M+N
	 * appels avant CAgent.init() juste après word populate ?N= nbAgents
	 * M=nbExtraAgents?)
	 */
	public Market getMarket() {
		//System.out.println(" world.getMarket() ");
		return (Market) mediumsInWorld[0];
	}
	public ArrayList getDescriptors() {
		descriptors.clear();
		descriptors.add(new IntegerDataDescriptor(this, "nbCompetitors",
				"nbCompetitors", nbCompetitors, true));
		descriptors.add(new DoubleDataDescriptor(this, "alpha", "alpha", alpha,
				true, 3));
		descriptors.add(new DoubleDataDescriptor(this, "epsilon", "epsilon",
				epsilon, true, 3));
		descriptors.add(new BooleanDataDescriptor(this, "proportion",
				"proportion", proportion, true));
		descriptors.add(new ChoiceDataDescriptor(this, "Random", "random_s",
				new String[]{"Default", "JavaRandom", "JavaGaussian"},
				random_s, true));
		descriptors.add(new LongDataDescriptor(this, "Seed", "seed", seed,
				true, 3));
		return descriptors;
	}
	public void setDefaultValues() {
		nbCompetitors = 2;
		alpha = 0.5;
		epsilon = 1.5;
		proportion = false;
		setRandom_s("Default");
		seed = 10;
	}
	public void setNbCompetitors(int j) {
		nbCompetitors = j;
		if (hack) { // first call. It **is** a NbCompetitors change
			restart();
		}
		hack = !hack;
	}
	public void setSeed(long d) {
		seed = d;
		//System.out.println("Seed : "+seed);
		//setRandom_s(random_s); //sinon on cree un random a chaque fois...
		//On ne peut modifier la graine qu'au debut d'une simulation,
		//pas en cours de simulation.
	}
	public void setRandom_s(String s) {
		random_s = s;
		try {
			Constructor constructor = Class.forName(randomPath + random_s)
					.getConstructor(new Class[]{long.class});
			random = (CRandomDouble) constructor
					.newInstance(new Object[]{new Long(seed)});
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		for (Iterator i = iterator(); i.hasNext();)
			((Agent) i.next()).setRandom(random);
	}
}