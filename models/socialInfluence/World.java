/** class socialInfluence.World
 * Title:        Moduleco<p>
 * Description:  Je suis un monde spécialisé. Je construit une valeur f (méthode compute) à
 * chaque pas qui indique si la proportion de mes agents qui ont un état "vrai" est >= 0.5<p>
 * Les EAgents sont des Agent.
 * Copyright:    Copyright (c)enst-bretagne
 * @author Antoine.Beugnard@enst-bretagne.fr, Denis.Phan@enst-bretagne.fr
* @version 1.4  February, 2004
*/
package models.socialInfluence;

import java.util.Iterator;
import java.util.ArrayList;

import modulecoFramework.modeleco.ENeighbourWorld;
import modulecoFramework.modeleco.randomeco.CRandomDouble;

import modulecoGUI.grapheco.descriptor.DoubleDataDescriptor;
import modulecoGUI.grapheco.descriptor.BooleanDataDescriptor;
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
	public static String initLength = "12";
	public static String initNeighbour = "World";
	public static String initZone = "RandomIndividual";
	public static String initScheduler = "LateCommitScheduler";
	/**
	 *  
	 */
	//protected WorldEditorPanel wep;

	protected boolean alea;
	protected double theta, mu;
	protected CRandomDouble random;
	protected String random_s;

	//protected double f; // proportion de ceux qui ont choisi vrai

	/**
	 * 
	 * @param length
	 */
	public World(int length) {
		super(length);
	}

	public void compute() {
		//f = SocialChoice();
	}

	public void init() {
		getInfo();
		try {
			statManager.add(
				new CalculatedVar(
					"FalseState",
					Class.forName(this.pack() + ".Agent").getMethod(
						"getState",
						null),
					CalculatedVar.NUMBER,
					new Boolean(false)));
			statManager.add(
				new CalculatedVar(
					"Changes",
					Class.forName(this.pack() + ".Agent").getMethod(
						"hasChanged",
						null),
					CalculatedVar.NUMBER,
					new Boolean(true)));
		} catch (ClassNotFoundException e) {
			System.out.println(e.toString());
		} catch (NoSuchMethodException e) {
			System.out.println(e.toString());
		}
	}

	protected double SocialChoice() {
		int n = 0;
		double f;
		for (Iterator i = iterator(); i.hasNext();) {
			if (((Agent) i.next()).etat) {
				n++;
			}
		}
		f = ((double) n / (double) agentSetSize);
		return f;
	}

	public Object getState() {
		return new Double(SocialChoice()); // la proportion plutot que le total
	}

	public void setMu(double newMu) {
		mu = newMu;
		for (Iterator i = iterator(); i.hasNext();) {
			((Agent) i.next()).setMu(newMu);
		}
	}

	public void setTheta(double newTheta) {
		theta = newTheta;
		for (Iterator i = iterator(); i.hasNext();) {
			((Agent) i.next()).setTheta(newTheta);
		}
	}

	public double getTheta() {
		return theta;
	}

	public double getMu() {
		return mu;
	}

	public void setAlea(boolean b) {
		System.out.println("on set l'alea");
		alea = b;
	}
	public boolean getAlea() {

		return alea;
	}
	public void getInfo() {
		/*wep    = (WorldEditorPanel)cwe;
		theta  = wep.getTheta();
		alea   = wep.getAlea();
		mu     = wep.getMu();
		random = wep.getRandom();
		wep.update(this);*/
	}
	public ArrayList getDescriptors() {
		descriptors.clear();
		descriptors.add(
			new DoubleDataDescriptor(
				this,
				"Theta",
				"theta",
				theta,
				0,
				1,
				true,
				3));
		descriptors.add(
			new DoubleDataDescriptor(this, "Mu", "mu", mu, 0, 1, true, 3));
		descriptors.add(
			new BooleanDataDescriptor(this, "Alea", "alea", alea, true));
		descriptors.add(
			new ChoiceDataDescriptor(
				this,
				"The golbal random Configuration",
				"random_s",
				new String[] { "Default", "JavaGaussian", "JavaRandom" },
				random_s,
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
			random = null;
		}

	}
	public void setDefaultValues() {
		theta = 0.5;
		mu = 0.0;
		alea = true;
		//random_s = "Default";
		setRandom_s("Default");
	}
}
