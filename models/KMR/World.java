/** Modele KMR.java
 * Title:        
 * Description:
 * Copyright:    Copyright (c)enst-bretagne
 * @author Vincent.lelarge@enst-bretagne.fr 
 * created mars 2002 
 * @version 1.2  august,5, 2002  
 */
package models.KMR;
import java.util.Iterator;
import java.util.ArrayList;
import java.lang.reflect.Constructor;
import modulecoFramework.abstractModels.EWorldGame;
import modulecoFramework.modeleco.randomeco.CRandomDouble;
//directly adressed in method : setRandom_s(String s)
//import modulecoFramework.modeleco.zone.RandomPairwise ;
//import modulecoFramework.modeleco.ZoneSelector;
import modulecoGUI.grapheco.descriptor.DoubleDataDescriptor;
import modulecoGUI.grapheco.descriptor.ChoiceDataDescriptor;
import modulecoGUI.grapheco.descriptor.InfoDescriptor;
import modulecoGUI.grapheco.descriptor.LongDataDescriptor;
//import modulecoGUI.grapheco.descriptor.BooleanDataDescriptor;
/**
 * @author phan1
 *  
 */
public class World extends EWorldGame {
	/**
	 * Initial values for the 4 basic parameters of this world <br>
	 * parameter 1: world size <br>
	 * parameter 2: neighbourhood type <br>
	 * parameter 3: active zone type <br>
	 * parameter 4: scheduler type <br>
	 */
	public static String initLength = "4";
	public static String initNeighbour = "RandomIndividual";
	public static String initZone = "World";
	public static String initScheduler = "LateCommitScheduler";
	/**
	 *  
	 */
	public static int size = 14;
	protected double tremble;
	protected CRandomDouble random;
	protected long seed;
	protected String random_s;
	private static String randomPath = "modulecoFramework.modeleco.randomeco.";
	public World(int length) {
		super(length);
	}
	public void setDefaultValues() {
		setRandom_s("JavaRandom");
		seed = 4;
		tremble = 0.5;
		super.s1AgainstS1 = 1;
		super.s2AgainstS1 = 0;
		super.s1AgainstS2 = 0;
		super.s2AgainstS2 = 3;
		super.setDefaultValues();
		//revisionRuleIndex="Last neighbourhood best payoff";
		//System.out.println(" models.KMR.World.setdefaultvalue() ");
	}
	public Object getState() {
		return new Boolean(true);
	}
	public ArrayList getDescriptors() {
		descriptors = super.getDescriptors();
		descriptors.add(new LongDataDescriptor(this, "Seed", "seed", seed,
				true, 3));
		descriptors.add(new ChoiceDataDescriptor(this, "Random", "random_s",
				new String[]{"Default", "JavaRandom", "JavaGaussian"},
				random_s, true));
		descriptors.add(new InfoDescriptor(""));
		descriptors.add(new InfoDescriptor(""));
		descriptors.add(new DoubleDataDescriptor(this, "Tremble", "tremble",
				tremble, true, 3));
		//descriptors.add(new DoubleDataDescriptor(this,"Probabilité de
		// décision","probaChoice",probaChoice,true,3));
		descriptors.add(new InfoDescriptor(""));
		descriptors.add(new InfoDescriptor(""));
		descriptors.add(new InfoDescriptor(""));
		return descriptors;
	}
	public double getTremble() {
		return tremble;
	}
	public void setTremble(double m) {
		tremble = m;
	}
	public void setSeed(long d) {
		seed = d;
		//System.out.println("Seed : "+seed);
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