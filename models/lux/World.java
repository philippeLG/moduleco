/** class lux.World
 * Title:        Moduleco<p>
 * Description:
 * Copyright:    Copyright (c)enst-bretagne
 * @author : sebastien.chivoret@ensta.org
 * @version 1.2  august,5, 2002  
 */
package models.lux;

import java.util.Iterator;
import java.util.ArrayList;
import java.awt.Color;

import modulecoFramework.modeleco.ENeighbourWorld;
import modulecoFramework.modeleco.randomeco.CRandomDouble;
import modulecoFramework.medium.Medium;

import modulecoGUI.grapheco.descriptor.DoubleDataDescriptor;
import modulecoGUI.grapheco.descriptor.ChoiceDataDescriptor;
import modulecoGUI.grapheco.descriptor.BooleanDataDescriptor;
import modulecoGUI.grapheco.statManager.Var;
/**
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
	public static String initLength = "5";
	public static String initNeighbour = "NeighbourVonNeuman";
	public static String initZone = "World";
	public static String initScheduler = "EarlyCommitScheduler";
	/**
	 *  
	 */
	protected CRandomDouble random;
	protected String random_s;
	protected Color[] colors;
	protected double xopt, xpess, xfond, epsilon;
	protected int OPT, PESS, FOND;
	protected double beta, mu, deltaP, p;
	//Gilles
	protected double v3;
	//Gilles
	protected boolean record;
	/**
	 * 
	 * @param length
	 */
	public World(int length) {
		super(length);
// Pas la bonne place ?
		OPT = 0;
		PESS = 1;
		FOND = 2;
	}
	public void populate() {
		//System.out.println(" world.populate() -- START");
		// setup the neighbourhood settlement
		// initialize market
		mediumsInWorld = new Medium[1];
		mediumsInWorld[0] = new Market();
		((Market) mediumsInWorld[0]).setEWorld(this);
		//System.out.println(" world.populate() -- END");
	}
	public void connect() {
		//System.out.println(" world.connect() -- START");
		// connect the Neighbourhood
		super.connect(); // ENeighbourWorld.connect()
		for (Iterator i = iterator(); i.hasNext();) {
			getMarket().attach(((Agent) i.next()), "customer");
		}
		//System.out.println(" world.connect() -- END");
	}
	public void init() {
		//System.out.println(" world.init() -- START");
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
		try {
			//statManager.add(new CalculatedVar("Viability Average",
			// Class.forName(this.pack() + ".Agent").getMethod("getEtat", null),
			// CalculatedVar.AVERAGE, null));
			//statManager.add(new CalculatedVar("Viability Minimum",
			// Class.forName(this.pack() +
			// ".Agent").getMethod("getGraphicViability", null),
			// CalculatedVar.MINIMUM, null));
			statManager.add(new Var("P", Class.forName(this.pack() + ".World")
					.getMethod("getP", null)));
			statManager.add(new Var("Pf", Class.forName(this.pack() + ".World")
					.getMethod("getPf", null)));
			statManager.add(new Var("x_opt", Class.forName(
					this.pack() + ".World").getMethod("getXoptGraphic", null)));
			statManager
					.add(new Var("x_pess", Class
							.forName(this.pack() + ".World").getMethod(
									"getXpessGraphic", null)));
			statManager
					.add(new Var("x_fond", Class
							.forName(this.pack() + ".World").getMethod(
									"getXfondGraphic", null)));
		} catch (ClassNotFoundException e) {
			System.out.println(e.toString());
		} catch (NoSuchMethodException e) {
			System.out.println(e.toString());
		}
		//System.out.println(" world.init() -- END");
	}
	/**
	 * At every time step ...
	 */
	public void commit() {
		super.commit();
		((Market) mediumsInWorld[0]).newP();
		((Market) mediumsInWorld[0]).newPf();
		//System.out.println("E/ P / Pf = " +
		// ((Market)mediumsInWorld[0]).getE() + " / " +
		// ((Market)mediumsInWorld[0]).getP() + " / " +
		// ((Market)mediumsInWorld[0]).getPf());
		((Market) mediumsInWorld[0]).reset();
		getValues();
	}
	public Color[] getColors() {
		return colors;
	}
	public Object getState() {
		return new Boolean(true);
	}
	public void getInfo() {
		//System.out.println(" world.getInfo()");
	}
	/**
	 * method call by agent.init() & competitor.int() (?double emploi ? -> M+N
	 * appels avant CAgent.init() juste après word populate ?N= nbAgents
	 * M=nbExtraAgents?)
	 */
	public int getInitialState() {
		double d = random.getDouble();
		if (d < xopt) {
			return 0;
		} else if (d < xopt + xpess)
			return 1;
		else
			return 2;
	}
	public ArrayList getDescriptors() {
		descriptors.clear();
		descriptors.add(new ChoiceDataDescriptor(this, "Random", "random_s",
				new String[]{"Default", "JavaRandom", "JavaGaussian"},
				random_s, true));
		descriptors.add(new DoubleDataDescriptor(this, "Optimistes", "xopt",
				xopt, true, 6));
		descriptors.add(new DoubleDataDescriptor(this, "Pessimistes", "xpess",
				xpess, true, 6));
		descriptors.add(new DoubleDataDescriptor(this, "Fondamentalistes",
				"xfond", xfond, false, 6));
		descriptors.add(new DoubleDataDescriptor(this, "beta", "beta", beta,
				true, 3));
		descriptors
				.add(new DoubleDataDescriptor(this, "mu", "mu", mu, true, 3));
		descriptors.add(new DoubleDataDescriptor(this, "P", "P", p, false, 3));
		descriptors.add(new DoubleDataDescriptor(this, "deltaP", "deltaP",
				deltaP, true, 3));
		descriptors.add(new BooleanDataDescriptor(this, "record", "record",
				record, true));
		return descriptors;
	}
	public void setRandom_s(String s) {
		random_s = s;
		try {
			random = (CRandomDouble) Class.forName(
					"modulecoFramework.modeleco.randomeco." + random_s)
					.newInstance();
			for (Iterator i = iterator(); i.hasNext();)
				((Agent) i.next()).setRandom(random);
		} catch (IllegalAccessException e) {
			System.out.println(e.toString());
		} catch (InstantiationException e) {
			System.out.println(e.toString());
		} catch (ClassNotFoundException e) {
			System.out.println(e.toString());
			random = null;
		}
	}
	public void setXopt(double d) {
		xopt = d;
		xfond = 1 - xopt - xpess;
	}
	public void setXpess(double d) {
		xpess = d;
		xfond = 1 - xopt - xpess;
	}
	public void setXfond(double d) {
		xfond = d;
	}
	public void setBeta(double d) {
		beta = d;
	}
	public void setMu(double d) {
		mu = d;
	}
	public void setDeltaP(double d) {
		deltaP = d;
	}
	public void setRecord(boolean b) {
		record = b;
	}
	public double getXopt() {
		return xopt;
	}
	public double getXpess() {
		return xpess;
	}
	public double getXfond() {
		return xfond;
	}
	public double getXoptGraphic() {
		return 100 * xopt;
	}
	public double getXpessGraphic() {
		return 100 * xpess;
	}
	public double getXfondGraphic() {
		return 100 * xfond;
	}
	public double getEpsilon() {
		return epsilon;
	}
	public Market getMarket() {
		return (Market) mediumsInWorld[0];
	}
	public double getBeta() {
		return beta;
	}
	public double getMu() {
		return mu;
	}
	public double getDeltaP() {
		return deltaP;
	}
	//Gilles
	public double getV3() {
		return v3;
	}
	public void cleanup() {
		((Market) mediumsInWorld[0]).closeFile();
	}
	public void getValues() {
		xopt = ((Market) mediumsInWorld[0]).getXopt();
		xpess = ((Market) mediumsInWorld[0]).getXpess();
		xfond = ((Market) mediumsInWorld[0]).getXfond();
		p = getP();
	}
	public double getP() {
		return ((Market) mediumsInWorld[0]).getP();
	}
	public double getPf() {
		return ((Market) mediumsInWorld[0]).getPf();
	}
	public double getE() {
		return ((Market) mediumsInWorld[0]).getE();
	}
	public void setDefaultValues() {
		//System.out.println(" world.setDefaultValues() -- START");
		setRandom_s("Default");
		xopt = 0.33;
		xpess = 0.33;
		xfond = 0.34;
		beta = 40; //Gilles instead of 4
		mu = 0; //Gilles instead of 0.01
		deltaP = 1; //Gilles instead of 0.01
		record = true;
		v3 = 0.005; //Gilles instead of nothing
		//System.out.println(" world.setDefaultValues() -- END");
	}
}