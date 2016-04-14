/** class sugarscape.World
 * Title:        Moduleco<p>
 * Copyright:    Copyright (c)enst-bretagne
 * @author Antoine.Beugnard@enst-bretagne.fr, Denis.Phan@enst-bretagne.fr, frederic.falempin@enst-bretagne.fr, Gregory.Gackel@enst-bretagne.fr
 * @version 1.2  august,5, 2002
 * @version 1.4. february 2004    
 */
package models.sugarscape;
import java.util.Iterator;
import java.util.ArrayList;
import models.sugarscape.rule.DeathRule;
import modulecoFramework.modeleco.mobility.EMobileWorld;
import modulecoFramework.modeleco.mobility.Move;
import modulecoFramework.modeleco.mobility.move.RandomMove;
import modulecoFramework.modeleco.randomeco.CRandomDouble;
import modulecoGUI.grapheco.descriptor.DoubleDataDescriptor;
import modulecoGUI.grapheco.descriptor.IntegerDataDescriptor;
import modulecoGUI.grapheco.descriptor.ChoiceDataDescriptor;
import modulecoGUI.grapheco.statManager.CalculatedVar;
public class World extends EMobileWorld {
	/**
	 * Initial values for the 4 basic parameters of this world <br>
	 * parameter 1: world size <br>
	 * parameter 2: neighbourhood type <br>
	 * parameter 3: active zone type <br>
	 * parameter 4: scheduler type <br>
	 */
	public static String initLength = "10";
	public static String initNeighbour = "NeighbourMoore";
	public static String initZone = "World";
	public static String initScheduler = "LateCommitScheduler";
	/**
	 *  
	 */
	//protected WorldEditorPanel wep;
	/**
	 * The ratio of sugar cells against empty cells
	 */
	protected double sugarQuantity;
	/**
	 * The maximum quantity of sugar a cell can produce
	 */
	//    protected int produceMax;
	/**
	 * The proportion of precalculated agents against random agents
	 */
	protected double proportionOfAnts;
	/**
	 * The maximum sight of an ant
	 */
	protected int sightMax;
	/**
	 * The maximum quantity of sugar needed for an ant
	 */
	protected int consumptionMax;
	/**
	 * compteur pour lengthchangement de saison
	 */
	protected int count;
	/**
	 * The season we are in
	 */
	protected int season;
	protected boolean initialized = false;
	protected CRandomDouble random;
	protected String random_s;
	protected Move move;
	protected String move_s;
	protected Rule rule;
	protected String rule_s;
	protected SugarPosition sugarPosition;
	public World(int length) {
		super(length);
	}
	public void init() {
		try {
			statManager.add(new CalculatedVar("LiveInTheGoodPlace", Class
					.forName(this.pack() + ".Place").getMethod(
							"doILiveInTheGoodPlace", null),
					CalculatedVar.NUMBER, new Boolean(true)));
			this.setMove(new MoveSugarscape()); //wep.getMove());
			//System.out.println(" avant init regle ");
			this.setRule(new DeathRule()); //wep.getRule());
			//            this.setSugarPosition(wep.getSugarPosition());
			//            sugarPosition= new CenterSugarPosition();
			//            sugarPosition.setWorld(this);
			//            sugarPosition.init(this);
		} catch (ClassNotFoundException e) {
			System.out.println(e.toString());
		} catch (NoSuchMethodException e) {
			System.out.println(e.toString());
		}
	}
	public void compute() {
		/*
		 * if (!initialized) { sugarPosition.setWorld(this);
		 * sugarPosition.init(this); initialized = true; }
		 */
		count++;
		if (count % 50 == 0) {
			season++;
		}
		for (Iterator i = iterator(); i.hasNext();) {
			((Place) i.next()).compute();
		}
	}
	public Object getState() {
		return new Double(10);
	}
	public int getSeason() {
		return season;
	}
	protected void setproportionOfAnts(double proportionOfAnts) {
		this.proportionOfAnts = proportionOfAnts;
		for (Iterator i = iterator(); i.hasNext();) {
			((Place) i.next()).setproportionOfAnts(proportionOfAnts);
		}
	}
	protected void setsightMax(int sightMax) {
		this.sightMax = sightMax;
		for (Iterator i = iterator(); i.hasNext();) {
			((Place) i.next()).setsightMax(sightMax);
		}
	}
	protected void setconsumptionMax(int consumptionMax) {
		this.consumptionMax = consumptionMax;
		for (Iterator i = iterator(); i.hasNext();) {
			((Place) i.next()).setconsumptionMax(consumptionMax);
		}
	}
	protected void setRandom(CRandomDouble random) {
		this.random = random;
		for (Iterator i = iterator(); i.hasNext();) {
			((Place) i.next()).setRandom(random);
		}
	}
	/*
	 * protected void setMove(Move move) { Place p; Ant a; this.move = move; for
	 * (Iterator i=iterator();i.hasNext();) { p = (Place) i.next(); a = (Ant)
	 * p.getAgent(); if (a != null) a.setMove(move); } }
	 */
	protected void setRule(Rule rule) {
		Place p;
		Ant a;
		this.rule = rule;
		for (Iterator i = iterator(); i.hasNext();) {
			p = (Place) i.next();
			a = (Ant) p.getAgent();
			if (a != null)
				a.setRule(rule);
		}
	}
	protected void setSugarPosition(SugarPosition sugarPosition) {
		this.sugarPosition = sugarPosition;
	}
	/*
	 * protected void commitMove(Agent originAgent, int destinationIndex) {
	 * Agent destinationAgent = (Agent)this.get(destinationIndex);
	 * 
	 * originAgent.setFutureState(Agent.nothingHere);
	 * destinationAgent.setFutureState(originAgent.getActualState()); }
	 */
	//      protected int getIndex(Place agent)
	//      {
	//         return this.indexOf(agent);
	//      }
	public void getInfo() {
		/*
		 * wep = (WorldEditorPanel)cwe; sugarQuantity = wep.getsugarQuantity();
		 * random = wep.getRandom(); proportionOfAnts =
		 * wep.getproportionOfAnts(); sightMax = wep.getsightMax();
		 * consumptionMax = wep.getconsumptionMax(); wep.update(this);
		 */
	}
	public void update() {
	}
	public ArrayList getDescriptors() {
		descriptors.clear();
		descriptors.add(new DoubleDataDescriptor(this, "sugarQuantity",
				"sugarQuantity", sugarQuantity, true, 3));
		descriptors.add(new DoubleDataDescriptor(this, "proportionOfAnts",
				"proportionOfAnts", proportionOfAnts, true, 3));
		descriptors.add(new IntegerDataDescriptor(this, "sightMax", "sightMax",
				sightMax, true));
		descriptors.add(new IntegerDataDescriptor(this, "consumptionMax",
				"consumptionMax", consumptionMax, true));
		descriptors.add(new ChoiceDataDescriptor(this, "Random", "random_s",
				new String[]{"Default", "JavaRandom", "JavaGaussian"},
				random_s, true));
		descriptors
				.add(new ChoiceDataDescriptor(this, "move", "move_s",
						new String[]{"NearMove", "NorthMove", "SouthMove",
								"RandomMove"}, move_s, true));
		return descriptors;
	}
	public void setRandom_s(String s) {
		random_s = s;
		try {
			random = (CRandomDouble) Class.forName(
					"modulecoFramework.modeleco.randomeco." + random_s)
					.newInstance();
		} catch (IllegalAccessException e) {
			System.out.println(e.toString());
		} catch (InstantiationException e) {
			System.out.println(e.toString());
		} catch (ClassNotFoundException e) {
			System.out.println(e.toString());
			random = null;
		}
	}
	public void setMove_s(String s) {
		move_s = s;
		//System.out.println("modulecoFramework.modeleco.mobility.move."+move_s);
		try {
			move = (Move) (Class
					.forName("modulecoFramework.modeleco.mobility.move."
							+ move_s).newInstance());
		} catch (IllegalAccessException e) {
			System.out.println(e.toString());
		} catch (InstantiationException e) {
			System.out.println(e.toString());
		} catch (ClassNotFoundException e) {
			System.out.println(e.toString());
			move = new RandomMove();
		}
	}
	public void setDefaultValues() {
		sugarQuantity = 1;
		proportionOfAnts = 0.3;
		sightMax = 6;
		consumptionMax = 9;
		setRandom_s("Default");
		setMove_s("NearMove");
	}
	public void setSugarQuantity(double d) {
		sugarQuantity = d;
	}
	public void setProportionOfAnts(double d) {
		proportionOfAnts = d;
	}
	public void setSightMax(int d) {
		sightMax = d;
	}
	public void setConsumptionMax(int d) {
		consumptionMax = d;
	}
}