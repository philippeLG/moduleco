/**
 * Title:   moduleco.models.emergenceClasses. World
 * Reference : Axtell, Epstein, Young (2000)
 * http://www.brookings.edu/es/dynamics/papers/classes/
 * * Copyright:    Copyright (c)enst-bretagne
 * @author denis.phan@enst-bretagne.fr
 * * @version 1.4.1 april 2004  
 */
package models.emergenceClasses;
// import java.util.Iterator;
import java.util.ArrayList;
import java.lang.reflect.Constructor;
//import modulecoFramework.modeleco.zone.RandomPairwise ;
import modulecoFramework.modeleco.ENeighbourRandomPairwiseWorld;
import modulecoFramework.modeleco.randomeco.CRandomInt;
import modulecoFramework.modeleco.randomeco.CRandomDouble;
// import modulecoGUI.grapheco.descriptor.InfoDescriptor;
import modulecoGUI.grapheco.descriptor.LongDataDescriptor;
import modulecoGUI.grapheco.descriptor.DoubleDataDescriptor;
import modulecoGUI.grapheco.descriptor.IntegerDataDescriptor;
import modulecoGUI.grapheco.descriptor.ChoiceDataDescriptor;
public class World extends ENeighbourRandomPairwiseWorld //ENeighbourWorld
{
	/**
	 * Initial values for the 4 basic parameters of this world <br>
	 * parameter 1: world size <br>
	 * parameter 2: neighbourhood type <br>
	 * parameter 3: active zone type <br>
	 * parameter 4: scheduler type <br>
	 */
	public static String initLength = "10";
	public static String initNeighbour = "RandomPairwise";
	public static String initZone = "World";
	public static String initScheduler = "LateCommitScheduler";
	/**
	 *  
	 */
	protected CRandomInt randomDot, randomDotTag;
	protected CRandomDouble randomTremble;
	protected long seedDot, seedDotTag, seedT;
	protected String random_T; // randomDot_s,
	private static String randomPath = "modulecoFramework.modeleco.randomeco.";
	protected double tremble;
	protected int memoryLength;
	protected ArrayList[] simplexFeatureSet;
	protected SimplexFeature ar1, ar2, ar3; //mixedNashEquilibrium;
	protected WorldFeature wr;
	protected int[][] PayoffMatrix;
	protected String initialLocation, tag;
	/**
	 * @param length
	 */
	public World(int length) {
		super(length);
	}
	/**
	 * Invoked by ENeighbourWorld.populateAll(nsClass) to be executed first,
	 * before the end of this world's constructor during each world's creation
	 * process. *
	 */
	public void getInfo() {
		PayoffMatrix = new int[3][3];
		PayoffMatrix[0][0] = 0;
		PayoffMatrix[0][1] = 0;
		PayoffMatrix[0][2] = 70;
		PayoffMatrix[1][0] = 0;
		PayoffMatrix[1][1] = 50;
		PayoffMatrix[1][2] = 50;
		PayoffMatrix[2][0] = 30;
		PayoffMatrix[2][1] = 30;
		PayoffMatrix[2][2] = 30;
	}
	public void populate() {
		super.populate();
		//double sf01 = (double) (1.0 / 4.0);
		//double sf02 = (double) (1.0 / 2.0);
		//double sf03 = (double) (1.0 / 4.0);
		/**
		 * randomDot is the 3-position pseudo-random generator used for to
		 * initialize the agent's strategies. Sequence of random values depends
		 * on the seed : seedDot.
		 */
		Object RandomConstructorParameter[] = new Object[]{new Long(seedDot)};
		try {
			Constructor constructor = Class.forName(randomPath + "JavaRandom")
					.getConstructor(new Class[]{long.class});
			randomDot = (CRandomInt) constructor
					.newInstance(RandomConstructorParameter);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		/**
		 * randomDotTag is the 2-position pseudo-random generator used for to
		 * initialize the agent's Tags. Sequence of random values depends on the
		 * seed : seedDotTag.
		 */
		if (tag.equalsIgnoreCase("2 tags")) {
			Object RandomConstructorParameter2[] = new Object[]{new Long(
					seedDotTag)};
			try {
				Constructor constructor = Class.forName(
						randomPath + "JavaRandom").getConstructor(
						new Class[]{long.class});
				randomDotTag = (CRandomInt) constructor
						.newInstance(RandomConstructorParameter2);
			} catch (Exception e) {
				System.out.println(e.toString());
			}
			simplexFeatureSet = new ArrayList[2];
			simplexFeatureSet[0] = new ArrayList(); //WHITHIN
			simplexFeatureSet[1] = new ArrayList(); // BETWEEN
		} else {
			simplexFeatureSet = new ArrayList[1];
			simplexFeatureSet[0] = new ArrayList();
		}
		wr = new WorldFeature(this);
	}
	public void init() {
	}
	public void compute() {
	}
	public void commit() {
		super.commit();
	}
	public void setDefaultValues() {
		seedDot = 1;
		seedDotTag = 1;
		initialLocation = "Random";
		seedT = 1;
		setRandom_T("JavaRandom");
		tremble = 0.1;
		memoryLength = 20;
		tag = "no tag";
	}
	public ArrayList getDescriptors() {
		descriptors.clear();
		descriptors = super.getDescriptors(); // Pourrait être évité ?
		descriptors.add(new LongDataDescriptor(this, "Seed RandomInt",
				"seedDot", seedDot, true, 3));
		descriptors.add(new ChoiceDataDescriptor(this, "InitialLocation",
				"initialLocation", new String[]{"Random", "HighLow"},
				initialLocation, true));
		descriptors.add(new LongDataDescriptor(this, "Seed Tremble", "seedT",
				seedT, true, 3));
		descriptors.add(new ChoiceDataDescriptor(this, "Tremble", "random_T",
				new String[]{"Default", "JavaRandom"}, random_T, true));
		descriptors.add(new DoubleDataDescriptor(this, "Tremble", "tremble",
				tremble, true, 3));
		descriptors.add(new IntegerDataDescriptor(this, "Memory Length",
				"memoryLength", memoryLength, true));
		descriptors.add(new ChoiceDataDescriptor(this, "Tag", "tag",
				new String[]{"no tag", "2 tags"}, tag, true));
		descriptors.add(new LongDataDescriptor(this, "Seed RandomTag",
				"seedDotTag", seedDotTag, true, 3));
		return descriptors;
	}
	public void setSeedDot(long d) {
		seedDot = d;
		//System.out.println("Seed : "+seed);
		//setRandom_s(randomDot_s); //sinon on cree un randomDot a chaque
		// fois...
		//On ne peut modifier la graine qu'au debut d'une simulation,
		//pas en cours de simulation.
	}
	public void setSeedDotTag(long dt) {
		seedDotTag = dt;
	}
	public void setInitialLocation(String s) {
		initialLocation = s;
	}
	public String getInitialLocation() {
		return initialLocation;
	}
	public void setSeedT(long st) {
		seedT = st;
	}
	public void setRandom_T(String s) {
		random_T = s;
		try {
			Constructor constructor = Class.forName(randomPath + random_T)
					.getConstructor(new Class[]{long.class});
			randomTremble = (CRandomDouble) constructor
					.newInstance(new Object[]{new Long(seedT)});
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		//for (Iterator i=iterator();i.hasNext();)
		//((Agent) i.next()).setRandom(randomDot);
	}
	public double getTremble() {
		return tremble;
	}
	public void setTremble(double tr) {
		tremble = tr;
	}
	public int getMemoryLength() {
		return memoryLength;
	}
	public void setMemoryLength(int ml) {
		memoryLength = ml;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tg) {
		tag = tg;
	}
}