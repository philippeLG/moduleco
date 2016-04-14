/** class segregation.World
 * Title:        Moduleco<p>
 * Copyright:    Copyright (c)enst-bretagne
 * @author Antoine.Beugnard@enst-bretagne.fr, Denis.Phan@enst-bretagne.fr, frederic.falempin@enst-bretagne.fr
 * @version 1.2  august,5, 2002  
 */
package models.segregation;

import java.util.Iterator;
import java.util.ArrayList;

import modulecoFramework.modeleco.mobility.EMobileWorld;
import modulecoFramework.modeleco.mobility.Move;
import modulecoFramework.modeleco.mobility.move.RandomMove; //SC 07.06.01
import modulecoFramework.modeleco.randomeco.CRandomDouble;

import modulecoGUI.grapheco.statManager.CalculatedVar;
// import modulecoGUI.grapheco.descriptor.IntegerDataDescriptor;
import modulecoGUI.grapheco.descriptor.DoubleDataDescriptor;
import modulecoGUI.grapheco.descriptor.BooleanDataDescriptor;
import modulecoGUI.grapheco.descriptor.ChoiceDataDescriptor;

public class World extends EMobileWorld {
	/**
	 * Initial values for the 4 basic parameters of this world <br>
	 * parameter 1: world size <br>
	 * parameter 2: neighbourhood type <br>
	 * parameter 3: active zone type <br>
	 * parameter 4: scheduler type <br>
	 */
	public static String initLength = "21";
	public static String initNeighbour = "NeighbourMoore";
	public static String initZone = "World";
	public static String initScheduler = "EarlyCommitScheduler";
	/**
	 *  
	 */

	/**
	 * The proportion of different (different color) neighbours the habitant
	 * find acceptable to continue to live in a given place
	 */
	protected double acceptation; // = 0;

	/**
	 * The proportion of precalculated agents against random agents
	 */
	protected double randomize; // = 0;

	/**
	 * The proportion of free agent (with no habitant) against occupated agent
	 */
	protected double freeAgentsProportion; // = 0;

	/**
	 * The proportion of Red against Blue occupated agent
	 */
	protected double redBlueProportion; // = 0;

	protected CRandomDouble random;

	protected Move move;

	protected String random_s; //SC 01.06.01
	protected String move_s;
	protected boolean schelling;

	public World(int length) {
		super(length);

	}

	public void populate() {
		//System.out.println("segregation.world.populate()");
	}

	public void makeOldDistribution() {
		int j = 0;
		for (Iterator i = iterator(); i.hasNext();)
			 ((Place) i.next()).setOldDistribution(j);

	}
	public void init() {
		if (schelling)
			setInitialColor();
		else
			makeOldDistribution();
		//System.out.println("segregation.world.init()");

		try {
			statManager.add(
				new CalculatedVar(
					"LiveInTheGoodPlace",
					Class.forName(this.pack() + ".Place").getMethod(
						"doILiveInTheGoodPlace",
						null),
					CalculatedVar.NUMBER,
					new Boolean(true)));
			this.setMove(/*wep.getMove()*/
			move);
		} catch (ClassNotFoundException e) {
			System.out.println(e.toString());
		} catch (NoSuchMethodException e) {
			System.out.println(e.toString());
		}
	}

	public Object getState() {
		return new Double(10);
	}

	public void setAcceptation(double acceptation) {
		this.acceptation = acceptation;
		for (Iterator i = iterator(); i.hasNext();) {
			((Place) i.next()).setAcceptation(acceptation);
		}
	}

	protected double getAcceptation() {
		return acceptation;
	}

	public void setRandomize(double randomize) {
		this.randomize = randomize;
		for (Iterator i = iterator(); i.hasNext();) {
			((Place) i.next()).setRandomize(randomize);
		}
	}

	public void setFreeAgentsProportion(double freeAgentsProportion) {
		this.freeAgentsProportion = freeAgentsProportion;
		for (Iterator i = iterator(); i.hasNext();) {
			((Place) i.next()).setFreeAgentsProportion(freeAgentsProportion);
		}
	}

	public void setRedBlueProportion(double redBlueProportion) {
		this.redBlueProportion = redBlueProportion;
		for (Iterator i = iterator(); i.hasNext();) {
			((Place) i.next()).setRedBlueProportion(redBlueProportion);
		}
	}

	protected String inversePeopleColor(String peopleColor) {

		if (peopleColor == "red")
			peopleColor = "blue";
		else
			peopleColor = "red";
		return peopleColor;
	}

	public void setInitialColor() { // DP 04/09/2001
		String peopleColor = "red";
		int j = 0;
		//int k = 0;
		for (Iterator i = iterator(); i.hasNext();) {
			if ((j % length == 0) && (length % 2 == 0) && (j != 0))
				peopleColor = inversePeopleColor(peopleColor);

			peopleColor = inversePeopleColor(peopleColor);

			if ((j == 0)
				|| (j == length - 1)
				|| (j == length * (length - 1))
				|| j == length * length - 1)
				 ((Place) i.next()).setIntitialPeopleColor("void", j);
			else {
				// ICI EST LA SOLUTION
				if (random.getDouble() < 0.1)
					 ((Place) i.next()).setIntitialPeopleColor("void", j);
				else if (
					random.getDouble() > 0.1 && random.getDouble() < 0.4) {
					peopleColor = inversePeopleColor(peopleColor);
					((Place) i.next()).setIntitialPeopleColor(peopleColor, j);
					peopleColor = inversePeopleColor(peopleColor);
				} else
					 ((Place) i.next()).setIntitialPeopleColor(peopleColor, j);
			}
			j++;
		}
	}

	protected void setRandom(CRandomDouble random) {
		this.random = random;
		for (Iterator i = iterator(); i.hasNext();) {
			((Place) i.next()).setRandom(random);
		}
	}

	public void setMove(Move move) {
		this.move = move;
		Place p;
		SegregationAgent a;
		for (Iterator i = iterator(); i.hasNext();) {
			p = (Place) i.next();
			a = (SegregationAgent) p.getAgent();
			if (a != null)
				a.setMove(move);
		}
	}

	public void getInfo() {
	}

	public void update() {
	}

	public void setDefaultValues() {
		acceptation = 0.37;
		randomize = 0.7;
		freeAgentsProportion = 0.5;
		redBlueProportion = 0.5;
		setRandom_s("Default");
		setMove_s("RandomMove");
		schelling = true;
	}

	public ArrayList getDescriptors() {
		descriptors.clear();
		descriptors.add(
			new DoubleDataDescriptor(
				this,
				"acceptation",
				"acceptation",
				acceptation,
				true,
				3));
		descriptors.add(
			new DoubleDataDescriptor(
				this,
				"freeAgentsProportion",
				"freeAgentsProportion",
				freeAgentsProportion,
				0,
				1,
				true,
				3));
		descriptors.add(
			new DoubleDataDescriptor(
				this,
				"redBlueProportion",
				"redBlueProportion",
				redBlueProportion,
				0,
				1,
				true,
				3));
		descriptors.add(
			new DoubleDataDescriptor(
				this,
				"randomize",
				"randomize",
				randomize,
				true,
				3));
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
					"RandomMove",
					"NearMove",
					"NorthMove",
					"SouthMove" },
				move_s,
				true));
		descriptors.add(
			new BooleanDataDescriptor(
				this,
				"Schelling",
				"schelling",
				schelling,
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
	public void setSchelling(boolean s) {
		schelling = s;
	}
}
