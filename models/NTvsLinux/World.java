/** models.NTvsLinux.World
	*@author frederic.falempin@enst-bretagne.fr
 * @version 1.2  august,5, 2002
* @version 1.4. february 2004   
	*/
package models.NTvsLinux;

import java.util.Iterator;
import java.util.ArrayList;

import modulecoFramework.modeleco.ENeighbourWorld;
import modulecoFramework.modeleco.randomeco.CRandomDouble;

import modulecoGUI.grapheco.descriptor.DoubleDataDescriptor;
import modulecoGUI.grapheco.descriptor.ChoiceDataDescriptor;
import modulecoGUI.grapheco.statManager.CalculatedVar;

/**
 * This class define a moduleco world that simulate the Nicolas
 * Julien model NT versus Linux. At the begining, almost all agent are NT
 * adopters. The earlyAdopt variable contains the proportion of agent that
 * adopts Linux at the begining.<BR>
 * The further evolution is ruled by the following equation:
 *
 *  <BR>
 * <B>
 * <PRE>
 *                                  th(a((xl - alpha*xw) + b(XL - beta * XW)))+1
 *  Probality(Agent adopts Linux) = --------------------------------------------
 *                                                        2
 * </PRE>
 * </B>
 * <BR>
 * - xl is the proportion of agent's neighbours adopters of Linux <BR>
 * - xw is the proportion of agent's neighbours adopters of NT <BR>
 * - XL is the proportion of agents adopters of Linux <BR>
 * - XW is the proportion of agents adopters of NT <BR>
 * - a estimate the preference for standadisation <BR>
 * - b estimate the preference for global standardisation
 * over local one <BR>
 * - alpha estimates the local influence of previous NT adoptions
 * as compared to Linux adoptions on adoption behaviours <BR>
 * - beta estimates the global influence of previous NT adoptions
 * as compared to Linux adoptions on adoption behaviours <BR>
 *
 * @author frederic.falempin@enst-bretagne.fr
 * @version armagedon
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
	public static String initNeighbour = "NeighbourVonNeuman";
	public static String initZone = "World";
	public static String initScheduler = "EarlyCommitScheduler";
	/**
	 *  
	 */
	/**
	* The random used during the World creation
	*/
	private CRandomDouble randomInitialisation;
	protected String randomInitialisation_s;
	/**
	 *  The random used to calculate the evolution
	 *  of the agent
	 */
	private CRandomDouble randomProbability;
	protected String randomProbability_s;

	/**
	 * The proportion of agent who adopt linux at the begining
	 */
	protected double earlyAdopt;
	/**
	 * a estimate the preference for standadisation (a in the above equation)
	 */
	protected double a;
	/**
	 * b estimate the preference for global standadisation (b in the above equation)
	 */
	protected double b;
	/**
	 * alpha estimate the local influence of previous NT adoptions
	 * as compared to Linux adoptions on adoption behaviours (alpha in the above equation)
	 */
	protected double alpha;
	/**
	 * beta estimate the global influence of previous NT adoptions
	 * as compared to Linux adoptions on adoption behaviours (beta in the above equation)
	 */
	protected double beta;
	/**
	 * LinuxAdoptersProportion is the proportion of agents adopters of Linux (XL  in the above equation)
	 */
	protected double LinuxAdoptersProportion;
	/**
	 * NTAdoptersProportion is the proportion of agents adopters of NT (XW  in the above equation)
	 */
	protected double NTAdoptersProportion;

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
					"TrueState",
					Class.forName(this.pack() + ".Agent").getMethod(
						"getState",
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
		int LinuxAdopters = 0;
		for (Iterator i = iterator(); i.hasNext();) {
			if (((Boolean) (((Agent) i.next()).getState())).booleanValue()) {
				LinuxAdopters++;
			}
		}

		LinuxAdoptersProportion = ((double) LinuxAdopters / (double) agentSetSize);
		NTAdoptersProportion = 1 - LinuxAdoptersProportion;
	}

	public Object getState() {
		return new Boolean(true);
	}

	public CRandomDouble getRandomInitialisation() {
		return randomInitialisation;
	}

	public CRandomDouble getRandomProbability() {
		return randomProbability;
	}

	/**
	 * @return The proportion of linux adopter agents
	 */
	public double getLinuxAdoptersProportion() {
		return LinuxAdoptersProportion;
	}

	/**
	 * @return The proportion of NT adopter agents
	 */
	public double getNTAdoptersProportion() {
		return NTAdoptersProportion;
	}

	/**
	 * @return alpha value
	 */
	protected double getAlpha() {
		return alpha;
	}

	/**
	 * Change the value of alpha
	 * @param alpha The new value of alpha
	 */
	public void setAlpha(double alpha) {
		this.alpha = alpha;
		for (Iterator i = iterator(); i.hasNext();) {
			((Agent) i.next()).setAlpha(alpha);
		}
	}

	/**
	 * @return beta value
	 */
	protected double getBeta() {
		return beta;
	}

	/**
	 * Change the value of beta
	 * @param beta The new value of beta
	 */
	public void setBeta(double beta) {
		this.beta = beta;
		for (Iterator i = iterator(); i.hasNext();) {
			((Agent) i.next()).setBeta(beta);
		}
	}

	/**
	 * @return a value
	 */
	protected double getA() {
		return a;
	}

	/**
	 * Change the value of a
	 * @param a The new value of a
	 */
	public void setA(double a) {
		this.a = a;
		for (Iterator i = iterator(); i.hasNext();) {
			((Agent) i.next()).setA(a);
		}
	}

	/**
	 * @return b value
	 */
	protected double getB() {
		return b;
	}

	/**
	 * Change the value of b
	 * @param b The new value of b
	 */
	public void setB(double b) {
		this.b = b;
		for (Iterator i = iterator(); i.hasNext();) {
			((Agent) i.next()).setB(b);
		}
	}

	/**
	 * @return The proportion of early adopters of linux
	 */
	protected double getEarlyAdopt() {
		return earlyAdopt;
	}

	public void getInfo() {
		/*wep = (WorldEditorPanel)cwe;
		a = wep.getA();
		b = wep.getB();
		alpha = wep.getAlpha();
		beta = wep.getBeta();
		earlyAdopt = wep.getEarlyAdopt();
		randomInitialisation = wep.getRandomInitialisation();
		randomProbability = wep.getRandomProbability();
		wep.update(this);*/
	}
	public ArrayList getDescriptors() {
		descriptors.clear();
		descriptors.add(
			new DoubleDataDescriptor(
				this,
				"Local preference for standard",
				"a",
				a,
				true,
				3));
		descriptors.add(
			new DoubleDataDescriptor(
				this,
				"Global preference for standard",
				"b",
				b,
				true,
				3));
		descriptors.add(
			new DoubleDataDescriptor(
				this,
				"WinNT local influence",
				"alpha",
				alpha,
				true,
				3));
		descriptors.add(
			new DoubleDataDescriptor(
				this,
				"WinNT global influence",
				"beta",
				beta,
				true,
				3));
		descriptors.add(
			new DoubleDataDescriptor(
				this,
				"Linux early adopters",
				"LinuxAdoptersProportion",
				LinuxAdoptersProportion,
				false,
				5));
		descriptors.add(
			new ChoiceDataDescriptor(
				this,
				"random (initialisation)",
				"randomInitialisation_s",
				new String[] { "Default", "JavaRandom", "JavaGaussian" },
				randomInitialisation_s,
				true));
		descriptors.add(
			new ChoiceDataDescriptor(
				this,
				"random (probability)",
				"randomProbability_s",
				new String[] { "Default", "JavaRandom", "JavaGaussian" },
				randomProbability_s,
				true));
		return descriptors;
	}
	public void setRandomInitialisation_s(String s) {
		randomInitialisation_s = s;
		try {
			randomInitialisation =
				(CRandomDouble) Class
					.forName(
						"modulecoFramework.modeleco.randomeco."
							+ randomInitialisation_s)
					.newInstance();
		} catch (IllegalAccessException e) {
			System.out.println(e.toString());
		} catch (InstantiationException e) {
			System.out.println(e.toString());
		} catch (ClassNotFoundException e) {
			randomInitialisation = null;
		}

	}
	public void setRandomProbability_s(String s) {

		randomProbability_s = s;
		try {
			randomProbability =
				(CRandomDouble) Class
					.forName(
						"modulecoFramework.modeleco.randomeco."
							+ randomProbability_s)
					.newInstance();
		} catch (IllegalAccessException e) {

			System.out.println(e.toString());
		} catch (InstantiationException e) {

			System.out.println(e.toString());
		} catch (ClassNotFoundException e) {

			randomProbability = null;
		}

	}
	public void setDefaultValues() {
		a = 0.2;
		b = 1;
		alpha = 2;
		beta = 3;
		earlyAdopt = 0.01;
		setRandomInitialisation_s("Default");
		setRandomProbability_s("Default");
	}
}
