/** class models.bak.World
 * Title:        Moduleco<p>
 * Description: je suis un monde dans lequel cohabitent des agents dont les viabilites varient entre
 * 0 et 1. l'agent qui a la plus faible viabilite est remplace par un autre agent a chaque tour,
 * ainsi que ces voisins.
 * la moyenne de la viabilite de tous les agents est egalement calcule a chaque tour.
 * Copyright:    Copyright (c)enst-bretagne
 * @author camille.monge@enst-bretagne.fr, denis.phan@@enst-bretagne.fr
 * @version 1.4  February, 2004
 */
package models.bak;
import java.util.Iterator;
import java.util.ArrayList;
import java.lang.reflect.Constructor;
import modulecoFramework.modeleco.ENeighbourWorld;
import modulecoFramework.modeleco.randomeco.CRandomDouble;
//directly adressed in method : setRandom_s(String s)
import modulecoGUI.grapheco.descriptor.DoubleDataDescriptor;
import modulecoGUI.grapheco.descriptor.IntegerDataDescriptor; //DP 29/06/2001
import modulecoGUI.grapheco.descriptor.ChoiceDataDescriptor;
import modulecoGUI.grapheco.descriptor.LongDataDescriptor;
import modulecoGUI.grapheco.statManager.CalculatedVar;
import modulecoGUI.grapheco.statManager.Var;
public class World extends ENeighbourWorld {
	/**
	 * Initial values for the 4 basic parameters of this world <br>
	 * parameter 1: world size <br>
	 * parameter 2: neighbourhood type <br>
	 * parameter 3: active zone type <br>
	 * parameter 4: scheduler type <br>
	 */
	public static String initLength = "20";
	public static String initNeighbour = "NeighbourVonNeuman";
	public static String initZone = "World";
	public static String initScheduler = "LateCommitScheduler";
	/**
	 *  
	 */
	protected double Min; // Minimum Viability in entire World
	protected int XMin, YMin;
	// Localisation of the Agent who have the minimum Fitmess
	protected int Distance = 0;
	// Distance between the 2 Agents who changed their Fitness on the 2 last
	// turns
	protected double Average; // Average Viability in the world
	protected int[] distanceHisto;
	protected double[] viabilityHisto;
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
	/*
	 * World.getInfo()invoqued by : EWorld before setDefaultValue() condition
	 * displayXgraphics = true is needed for to exhibit 4graphics on the
	 * rightRepresentation
	 */
	public void getInfo() {
		//DP 29/06/2001
		int statClasses;
		if (length % 5 == 0) { // % = modulo
			statClasses = length / 5 - 1;
		} else {
			statClasses = length / 5;
		}
		//System.out.println("length/5 ="+statClasses);
		//distanceHistohisto = new int[statClasses]
	}
	public void init() {
		viabilityHisto = new double[10];
		try {
			statManager.add(new CalculatedVar("Viability Average", Class
					.forName(this.pack() + ".Agent").getMethod(
							"getGraphicViability", null),
					CalculatedVar.AVERAGE, null));
			statManager.add(new CalculatedVar("Viability Minimum", Class
					.forName(this.pack() + ".Agent").getMethod(
							"getGraphicViability", null),
					CalculatedVar.MINIMUM, null));
			statManager.add(new Var("Distance", Class.forName(
					this.pack() + ".World").getMethod("getDistance", null)));
		} catch (ClassNotFoundException e) {
			/* System.out.println( */
			e.printStackTrace(); // toString());
		} catch (NoSuchMethodException e) {
			System.out.println(e.toString());
		}
	}
	public void compute() {
		// compute the minimum Viability and the average viability in the world.
		int i, j;
		double Somme = 0;
		double tmp;
		int XMinOld = XMin;
		int YMinOld = YMin;
		int DistanceX, DistanceY;
		// Revoir calcul de la distance DP 29/06/2001
		Min = 1;
		for (i = 0; i < length; i++)
			for (j = 0; j < length; j++) {
				tmp = ((Agent) get(j * length + i)).Viability;
				Somme += tmp;
				if (tmp < Min) {
					Min = tmp;
					XMin = i;
					YMin = j;
					DistanceX = Math.min(Math.abs(XMinOld - i), length
							- Math.abs(XMinOld - i));
					DistanceY = Math.min(Math.abs(YMinOld - j), length
							- Math.abs(YMinOld - j));
					Distance = DistanceX + DistanceY;
				}
			}
		for (i = 0; i < 10; i++)
			viabilityHisto[i] = 0;
		for (Iterator h = iterator(); h.hasNext();) {
			BuidHisto(((Agent) h.next()).getViability());
		}
		for (i = 0; i < 10; i++)
			//System.out.println(viabilityHisto[i]);
			Average = ((double) Somme / (double) agentSetSize);
		//System.out.println(" Min = "+Min + " X:"+XMin + " Y:"+YMin + "
		// Distance:"+Distance);
		//System.out.println("Min ="+(new Double(Min).toString().trim()));
		//System.out.println("Average ="+(new
		// Double(Average).toString().trim()));
	}
	public void BuidHisto(double localViability) {
		if (localViability < 0.1)
			viabilityHisto[0]++;
		else if (localViability < 0.2)
			viabilityHisto[1]++;
		else if (localViability < 0.3)
			viabilityHisto[2]++;
		else if (localViability < 0.4)
			viabilityHisto[3]++;
		else if (localViability < 0.5)
			viabilityHisto[4]++;
		else if (localViability < 0.6)
			viabilityHisto[5]++;
		else if (localViability < 0.7)
			viabilityHisto[6]++;
		else if (localViability < 0.8)
			viabilityHisto[7]++;
		else if (localViability < 0.9)
			viabilityHisto[8]++;
		else
			viabilityHisto[9]++;
	}
	public void setAverage(double Average) {
		// A completer pour DoubleDataDescriptor.java
	}
	public void setDistance(int Distance) {
		// A completer pour IntegerDataDescriptor.java
	}
	public double getDistance() {
		return Distance;
	}
	public Object getState() { //useless
		return new Boolean(true);
	}
	public ArrayList getDescriptors() {
		descriptors.clear();
		descriptors.add(new DoubleDataDescriptor(this, "World Average Fitness",
				"Average", Average, false, 3));
		descriptors.add(new IntegerDataDescriptor(this,
				"Distance between mutations", "Distance", Distance, false));
		//DP 29/06/2001
		descriptors.add(new LongDataDescriptor(this, "Seed", "seed", seed,
				true, 3));
		descriptors.add(new ChoiceDataDescriptor(this, "Random", "random_s",
				new String[]{"Default", "JavaRandom", "JavaGaussian"},
				random_s, true));
		//descriptors.add(new
		// ListDataDescriptor(this,"Complete","Name",4,true,new
		// DoubleDataDescriptor(this,"Average Viability in the
		// world","",Average,false,3) ));
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
		setRandom_s("Default");
		seed = 10;
	}
}