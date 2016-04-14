/** class sugarscape.World
 * Title:        Moduleco<p>
 * Copyright:    Copyright (c)enst-bretagne
 * @author Antoine.Beugnard@enst-bretagne.fr, Denis.Phan@enst-bretagne.fr, frederic.falempin@enst-bretagne.fr, Gregory.Gackel@enst-bretagne.fr
 * @version 1.2  august,5, 2002
* @version 1.4. february 2004    
 */
package models.percolationSite;

import java.util.Iterator;
import java.util.ArrayList;

import modulecoFramework.modeleco.ENeighbourWorld;
import modulecoFramework.modeleco.randomeco.CRandomDouble;

import modulecoGUI.grapheco.descriptor.DoubleDataDescriptor;
import modulecoGUI.grapheco.descriptor.ChoiceDataDescriptor;
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
	public static String initNeighbour = "NeighbourMoore";
	public static String initZone = "World";
	public static String initScheduler = "EarlyCommitScheduler";
	/**
	 *  
	 */
	/**
	 * The proportion of occupated Agents against empty Agents
	 */
	protected double proportionOfTrees;

	protected CRandomDouble random;
	protected String random_s;

	protected Agent agent;
	protected int count;

	/**
	 * 
	 * @param length
	 */
	public World(int length) {
		super(length);
	}

	public void init() {
		//System.out.println("percolationSite.init()");
		try {
			statManager.add(
				new CalculatedVar(
					"DeadTrees",
					Class.forName(this.pack() + ".Agent").getMethod(
						"isEveryOneInFire",
						null),
					CalculatedVar.NUMBER,
					new Boolean(true)));

		} catch (ClassNotFoundException e) {
			System.out.println(e.toString());
		} catch (NoSuchMethodException e) {
			System.out.println(e.toString());
		}
	}

	public void compute() {
		if (count == 0) {
			agent =
				(Agent) worldMother.get(
					(int) ((worldMother.getAgentSetSize()) * (random.getDouble())));
			agent.setFireHere();
		}
		count++;

		for (Iterator i = iterator(); i.hasNext();) {
			((Agent) i.next()).compute();
		}
		//System.out.println("percolationSite.compute()");
	}

	public Object getState() {
		return new Double(10);
	}

	protected void setproportionOfTrees(double proportionOfTrees) {
		this.proportionOfTrees = proportionOfTrees;
		for (Iterator i = iterator(); i.hasNext();) {
			((Agent) i.next()).setproportionOfTrees(proportionOfTrees);
		}
		//System.out.println("percolationSite.setproportionOfTrees()");

	}

	protected void setRandom(CRandomDouble random) {
		this.random = random;
		for (Iterator i = iterator(); i.hasNext();) {
			((Agent) i.next()).setRandom(random);
		}
	}

	//      protected int getIndex(Agent agent)
	//      {
	//         return this.indexOf(agent);
	//      }

	public void getInfo() {
		/*wep = (WorldEditorPanel)cwe;
		random = wep.getRandom();
		proportionOfTrees = wep.getproportionOfTrees();
		wep.update(this);*/
	}

	public void update() {
	}

	public ArrayList getDescriptors() {

		descriptors.clear();
		descriptors.add(
			new DoubleDataDescriptor(
				this,
				"proportionOfTrees",
				"proportionOfTrees",
				proportionOfTrees,
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
		return descriptors;
	}

	public void setProportionOfTrees(double d) {
		proportionOfTrees = d;
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
	public void setDefaultValues() {
		proportionOfTrees = 0.56;
		setRandom_s("Default");

	}
}