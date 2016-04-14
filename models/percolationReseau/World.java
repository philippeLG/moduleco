/** class sugarscape.World
 * Title:        Moduleco<p>
 * Copyright:    Copyright (c)enst-bretagne
 * @author Antoine.Beugnard@enst-bretagne.fr, Denis.Phan@enst-bretagne.fr, frederic.falempin@enst-bretagne.fr, Gregory.Gackel@enst-bretagne.fr
 * @version 1.2  august,5, 2002
* @version 1.4. february 2004    
 */
package models.percolationReseau;

import java.util.Iterator;
import java.util.ArrayList;

import modulecoFramework.modeleco.ENeighbourWorld;
import modulecoFramework.modeleco.randomeco.CRandomDouble;

import modulecoGUI.grapheco.statManager.CalculatedVar;
import modulecoGUI.grapheco.descriptor.DoubleDataDescriptor;
import modulecoGUI.grapheco.descriptor.ChoiceDataDescriptor;

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
	 * The proportion of occupated Agents against empty Agents;
	 */
	protected double proportionOfLinks;
	protected CRandomDouble random;
	protected String random_s;
	protected Agent agent;
	protected int count = 0;

	/**
	 * 
	 * @param length
	 */
	public World(int length) {
		super(length);
	}

	public void init() {

		try {
			statManager.add(
				new CalculatedVar(
					"DeadTrees",
					Class.forName(this.pack() + ".Agent").getMethod(
						"isEveryOneDead",
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
		if (count == 1) {
			agent =
				(Agent) worldMother.get(
					(int) ((worldMother.getAgentSetSize()) * (random.getDouble())));
			agent.setFireHere();
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
		//   sugarQuantity = wep.getsugarQuantity();
		random = wep.getRandom();
		proportionOfLinks = wep.getproportionOfLinks();
		wep.update(this);*/
	}

	public void update() {
	}

	public ArrayList getDescriptors() {
		descriptors.clear();
		descriptors.add(
			new ChoiceDataDescriptor(
				this,
				"Random",
				"random_s",
				new String[] { "Default", "JavaRandom", "JavaGaussian" },
				random_s,
				true));
		descriptors.add(
			new DoubleDataDescriptor(
				this,
				"proportionOfLinks",
				"proportionOfLinks",
				proportionOfLinks,
				true,
				3));
		return descriptors;
	}

	public void setProportionOfLinks(double d) {
		proportionOfLinks = d;
	}

	public void setRandom_s(String s) {

		random_s = s;
		try {
			random =
				(CRandomDouble) Class
					.forName("modulecoFramework.modeleco.randomeco." + random_s)
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
	public void setDefaultValues() {
		proportionOfLinks = 0.27;
		setRandom_s("Default");
	}
}