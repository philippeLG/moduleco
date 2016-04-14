/** class models.epidemia.World.java
 * Title:        Moduleco<p>
 * Copyright:    Copyright (c)enst-bretagne
 * @author Antoine.Beugnard@enst-bretagne.fr, Denis.Phan@enst-bretagne.fr, frederic.falempin@enst-bretagne.fr, Gregory.Gackel@enst-bretagne.fr
 * @version 1.4  February, 2004
 */
package models.epidemia;
import java.util.Iterator;
import java.util.ArrayList;
import java.lang.reflect.Constructor;
import modulecoFramework.modeleco.ENeighbourWorld;
import modulecoFramework.modeleco.randomeco.CRandomDouble;
//import modulecoFramework.modeleco.randomeco.CRandomDouble;
//directly adressed in method : setRandom_s(String s)
import modulecoGUI.grapheco.descriptor.IntegerDataDescriptor;
import modulecoGUI.grapheco.descriptor.DoubleDataDescriptor;
import modulecoGUI.grapheco.descriptor.ChoiceDataDescriptor;
import modulecoGUI.grapheco.descriptor.LongDataDescriptor;
//import modulecoGUI.grapheco.descriptor.ChoiceDataDescriptor;
import modulecoGUI.grapheco.statManager.CalculatedVar;
/**
 * @author Gregory.Gackel@enst-bretagne.fr
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
	public static String initNeighbour = "NeighbourMoore";
	public static String initZone = "World";
	public static String initScheduler = "EarlyCommitScheduler";
	/**
	 *  
	 */
	//protected WorldEditorPanel wep;
	/**
	 * The proportion of opened links an angent can have;
	 */
	protected double proportionOfLinks;
	protected int timeOfDesease;
	protected int timeOfImmunity;
	protected Agent agent;
	protected int count;
	protected CRandomDouble random;
	protected long seed;
	protected String random_s;
	//protected boolean hack;
	private static String randomPath = "modulecoFramework.modeleco.randomeco.";
	/**
	 * @param length
	 */
	public World(int length) {
		super(length);
	}
	public void init() {
		try {
			statManager.add(new CalculatedVar("HumanSick", Class.forName(
					this.pack() + ".Agent").getMethod("isEveryOneSick", null),
					CalculatedVar.NUMBER, new Boolean(true)));
			statManager.add(new CalculatedVar("HumanImmune",
					Class.forName(this.pack() + ".Agent").getMethod(
							"isEveryOneImmune", null), CalculatedVar.NUMBER,
					new Boolean(true)));
		} catch (ClassNotFoundException e) {
			System.out.println(e.toString());
		} catch (NoSuchMethodException e) {
			System.out.println(e.toString());
		}
	}
	public void compute() {
		if (count == 0) {
			agent = (Agent) worldMother.get((int) ((worldMother.getAgentSetSize())
					* (random.getDouble()) % worldMother.getAgentSetSize()));
			agent.setSickHere();
		}
		count++;
		for (Iterator i = iterator(); i.hasNext();) {
			((Agent) i.next()).compute();
		}
	}
	public Object getState() {
		return new Double(10);
	}
	protected void setproportionOfLinks(double proportionOfLinks) {
		this.proportionOfLinks = proportionOfLinks;
		for (Iterator i = iterator(); i.hasNext();) {
			((Agent) i.next()).setproportionOfLinks(proportionOfLinks);
		}
	}
	protected void settimeOfDesease(int timeOfDesease) {
		this.timeOfDesease = timeOfDesease;
		for (Iterator i = iterator(); i.hasNext();) {
			((Agent) i.next()).settimeOfDesease(timeOfDesease);
		}
	}
	protected void settimeOfImmunity(int timeOfImmunity) {
		this.timeOfImmunity = timeOfImmunity;
		for (Iterator i = iterator(); i.hasNext();) {
			((Agent) i.next()).settimeOfImmunity(timeOfImmunity);
		}
	}
	protected void setRandom(CRandomDouble random) {
		this.random = random;
		for (Iterator i = iterator(); i.hasNext();) {
			((Agent) i.next()).setRandom(random);
		}
	}
	public void getInfo() {
		/*
		 * wep = (WorldEditorPanel)cwe; random = wep.getRandom();
		 * proportionOfLinks = wep.getproportionOfLinks(); timeOfDesease =
		 * wep.gettimeOfDesease(); timeOfImmunity = wep.gettimeOfImmunity();
		 * wep.update(this);
		 */
	}
	public void update() {
	}
	public ArrayList getDescriptors() {
		descriptors.clear();
		descriptors.add(new DoubleDataDescriptor(this, "proportionOfLinks",
				"proportionOfLinks", proportionOfLinks, true, 3));
		descriptors.add(new IntegerDataDescriptor(this, "timeOfDesease",
				"timeOfDesease", timeOfDesease, true));
		descriptors.add(new IntegerDataDescriptor(this, "timeOfImmunity",
				"timeOfImmunity", timeOfImmunity, true));
		descriptors.add(new ChoiceDataDescriptor(this, "Random", "random_s",
				new String[]{"Default", "JavaRandom", "JavaGaussian"},
				random_s, true));
		descriptors.add(new LongDataDescriptor(this, "Seed", "seed", seed,
				true, 3));
		return descriptors;
	}
	public void setDefaultValues() {
		proportionOfLinks = 0.60;
		timeOfDesease = 3;
		timeOfImmunity = 8;
		setRandom_s("Default");
		seed = 10;
	}
	public void setProportionOfLinks(double d) {
		proportionOfLinks = d;
	}
	public void setTimeOfDesease(int d) {
		timeOfDesease = d;
	}
	public void setTimeOfImmunity(int d) {
		timeOfImmunity = d;
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