/** class tarifBinome.World
 * Title:        Moduleco<p>
 * Description:  Je suis un monde spécialisé. Je construit une valeur f (méthode compute) à
 * chaque pas qui indique si la proportion de mes agents qui ont un état "vrai" est >= 0.5<p>
 * Les EAgents sont des Agent.
 * Copyright:    Copyright (c)enst-bretagne
 * @author Antoine.Beugnard@enst-bretagne.fr, Denis.Phan@enst-bretagne.fr
 * Created on july 2000
 * @version 1.2  august,5, 2002
 * @version 1.4. february 2004   
 */
package models.tarifBinome;
import java.util.Iterator;
import java.util.ArrayList;
import java.lang.reflect.Constructor;
import modulecoFramework.modeleco.ENeighbourWorld;
import modulecoFramework.modeleco.randomeco.CRandomDouble;
//directly adressed in method : setRandom_s(String s)
import modulecoGUI.grapheco.descriptor.DoubleDataDescriptor;
import modulecoGUI.grapheco.descriptor.BooleanDataDescriptor;
import modulecoGUI.grapheco.descriptor.ChoiceDataDescriptor;
import modulecoGUI.grapheco.descriptor.LongDataDescriptor;
//import modulecoGUI.grapheco.descriptor.ChoiceDataDescriptor;
import modulecoGUI.grapheco.statManager.CalculatedVar;
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
	protected boolean alea;
	protected double A, P, EarlyAdopt, Alpha, Epsilon;
	protected double N; // proportion de ceux qui ont choisi d'adopter
	protected CRandomDouble random;
	protected long seed;
	protected String random_s;
	private static String randomPath = "modulecoFramework.modeleco.randomeco.";
	/**
	 * @param length
	 */
	public World(int length) {
		super(length);
	}
	public void init() {
		try {
			statManager.add(new CalculatedVar("TrueState", Class.forName(
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
	}
	public void compute() {
		int n = 0;
		for (Iterator i = iterator(); i.hasNext();) {
			if (((Agent) i.next()).state) {
				n++;
			}
		}
		N = ((double) n / (double) agentSetSize);
	}
	public Object getState() {
		return new Double(N);
	}
	public void setP(double q) {
		P = q;
		for (Iterator i = iterator(); i.hasNext();) {
			((Agent) i.next()).setP(P);
		}
	}
	public void setA(double t) {
		A = t;
		for (Iterator i = iterator(); i.hasNext();) {
			((Agent) i.next()).setA(t);
		}
	}
	public void setEarlyAdopt(double e) {
		EarlyAdopt = e;
		// a l'avenir : affecter directement l'état
		for (Iterator i = iterator(); i.hasNext();) {
			((Agent) i.next()).setEarlyAdopt(EarlyAdopt);
		}
	}
	public void setAlpha(double q) {
		Alpha = q;
		for (Iterator i = iterator(); i.hasNext();) {
			((Agent) i.next()).setAlpha(Alpha);
		}
	}
	public void setEpsilon(double q) {
		Epsilon = q;
		for (Iterator i = iterator(); i.hasNext();) {
			((Agent) i.next()).setEpsilon(Epsilon);
		}
	}
	public void setAlea(boolean b) {
		alea = b;
	}
	public void getInfo() {
		/*
		 * wep = (WorldEditorPanel)cwe; A = wep.getA(); P = wep.getP(); alea =
		 * wep.getAlea(); EarlyAdopt = wep.getEarlyAdopt(); Alpha =
		 * wep.getAlpha(); Epsilon = wep.getEpsilon(); random = wep.getRandom();
		 */
	}
	public ArrayList getDescriptors() {
		descriptors.clear();
		descriptors.add(new DoubleDataDescriptor(this, "A", "A", A, true, 3));
		descriptors.add(new DoubleDataDescriptor(this, "P", "P", P, true, 3));
		descriptors.add(new DoubleDataDescriptor(this, "Alpha", "Alpha", Alpha,
				true, 3));
		descriptors.add(new DoubleDataDescriptor(this, "Epsilon", "Epsilon",
				Epsilon, true, 3));
		descriptors.add(new DoubleDataDescriptor(this, "Early adopters",
				"EarlyAdopt", EarlyAdopt, true, 3));
		descriptors.add(new BooleanDataDescriptor(this, "Alea", "alea", alea,
				false));
		descriptors.add(new ChoiceDataDescriptor(this, "Random", "random_s",
				new String[]{"Default", "JavaRandom", "JavaGaussian"},
				random_s, true));
		descriptors.add(new LongDataDescriptor(this, "Seed", "seed", seed,
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
	public void setDefaultValues() {
		A = 0.4;
		P = 0.2;
		Alpha = 0.5;
		Epsilon = 1.5;
		EarlyAdopt = 0.01;
		alea = true;
		setRandom_s("Default");
		seed = 10;
	}
}