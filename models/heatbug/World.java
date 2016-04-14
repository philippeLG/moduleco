/** class heatbug.World
 * Title:        Moduleco<p>
 * Copyright:    Copyright (c)enst-bretagne
 * @author Antoine.Beugnard@enst-bretagne.fr, Denis.Phan@enst-bretagne.fr, frederic.falempin@enst-bretagne.fr, Gregory.Gackel@enst-bretagne.fr
 *@version 1.2  august,5, 2002
 */
package models.heatbug;

import java.util.ArrayList;
import java.util.Iterator;

import modulecoFramework.modeleco.mobility.EMobileWorld;
import modulecoFramework.modeleco.mobility.Move;
import modulecoFramework.modeleco.mobility.move.RandomMove; //SC 07.06.01
import modulecoFramework.modeleco.randomeco.CRandomDouble;

import modulecoGUI.grapheco.descriptor.IntegerDataDescriptor;
import modulecoGUI.grapheco.descriptor.DoubleDataDescriptor;
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
	public static String initLength = "75";
	public static String initNeighbour = "NeighbourMoore";
	public static String initZone = "World";
	public static String initScheduler = "EarlyCommitScheduler";
	/**
	 *  
	 */
	//protected WorldEditorPanel wep;

	/**
	 * The proportion of precalculated agents against random agents
	 */
	protected double proportionOfAnts;

	/**
	 * The maximum sight of an ant
	 */
	protected int sightMax;

	/**
	  * The maximum temperature an ant is searching
	  */

	protected int idealTemperatureMax;

	protected CRandomDouble random;
	protected String random_s;
	protected Move move;
	protected String move_s;
	public World(int length) {
		super(length);
	}

	public void init() {
		try {
			statManager.add(
				new CalculatedVar(
					"LiveInTheGoodPlace",
					Class.forName(this.pack() + ".Place").getMethod(
						"doILiveInTheGoodPlace",
						null),
					CalculatedVar.NUMBER,
					new Boolean(true)));
			this.setMove(new MoveHeatbug()); //wep.getMove());

		} catch (ClassNotFoundException e) {
			System.out.println(e.toString());
		} catch (NoSuchMethodException e) {
			System.out.println(e.toString());
		}
	}

	//Select all the places of the world

	public void compute() {

		for (Iterator i = iterator(); i.hasNext();) {
			((Place) i.next()).compute();
		}
	}

	public Object getState() {
		return new Double(10);
	}

	//      protected int getIndex(Place agent)
	//      {
	//         return this.indexOf(agent);
	//      }

	public void getInfo() {
		/*wep = (WorldEditorPanel)cwe;
		random = wep.getRandom();
		proportionOfAnts = wep.getproportionOfAnts();
		sightMax = wep.getsightMax();
		idealTemperatureMax = wep.getidealTemperatureMax() ;
		wep.update(this);*/
	}

	public void update() {
	}
	public ArrayList getDescriptors() {
		descriptors.clear();
		descriptors.add(
			new DoubleDataDescriptor(
				this,
				"proportionOfAnts",
				"proportionOfAnts",
				proportionOfAnts,
				true,
				3));
		descriptors.add(
			new IntegerDataDescriptor(
				this,
				"sightMax",
				"sightMax",
				sightMax,
				true));
		descriptors.add(
			new IntegerDataDescriptor(
				this,
				"idealTemperatureMax",
				"idealTemperatureMax",
				idealTemperatureMax,
				true));
		descriptors.add(
			new ChoiceDataDescriptor(
				this,
				"Random",
				"random_s",
				new String[] { "Default", "JavaRandom", "JavaGaussian" },
				random_s,
				true));
		descriptors.add(
			new ChoiceDataDescriptor(
				this,
				"move",
				"move_s",
				new String[] {
					"NearMove",
					"NorthMove",
					"SouthMove",
					"RandomMove" },
				move_s,
				true));
		return descriptors;
	}

	public void setRandom_s(String s) {

		random_s = s;
		try {
			random =
				(CRandomDouble) Class
					.forName("modulecoFramework.modeleco.randomeco." + random_s)
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
			move =
				(Move) (Class
					.forName(
						"modulecoFramework.modeleco.mobility.move." + move_s)
					.newInstance());
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
		proportionOfAnts = 0.3;
		sightMax = 6;
		idealTemperatureMax = 3;
		setRandom_s("Default");
		setMove_s("NearMove");

	}

	public void setProportionOfAnts(double proportionOfAnts) {
		this.proportionOfAnts = proportionOfAnts;
		for (Iterator i = iterator(); i.hasNext();) {
			((Place) i.next()).setproportionOfAnts(proportionOfAnts);
		}
	}

	public void setSightMax(int sightMax) {
		this.sightMax = sightMax;
		for (Iterator i = iterator(); i.hasNext();) {
			((Place) i.next()).setsightMax(sightMax);
		}
	}

	public void setIdealTemperatureMax(int idealTemperatureMax) {
		this.idealTemperatureMax = idealTemperatureMax;
		for (Iterator i = iterator(); i.hasNext();) {
			((Place) i.next()).setidealTemperatureMax(idealTemperatureMax);
		}
	}

	protected void setRandom(CRandomDouble random) {
		this.random = random;
		for (Iterator i = iterator(); i.hasNext();) {
			((Place) i.next()).setRandom(random);
		}
	}

}
