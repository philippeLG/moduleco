/** class life.world.java
 * Title:        Moduleco<p>
 * Description:  Je fais évoluer des Agents qui suivent les règles du jeu de la vie
 * de Conway.<p>
 * Copyright:    Copyright (c)enst-bretagne
 * @author Antoine.Beugnard@enst-bretagne.fr
 * Created may 2000
 * @version 1.2  august,5, 2002  
 */
package models.life;
import java.util.ArrayList;
import java.util.Vector;
import java.util.Iterator;
import modulecoFramework.modeleco.ENeighbourWorld;
import modulecoGUI.grapheco.descriptor.DoubleDataDescriptor;
import modulecoGUI.grapheco.descriptor.IntegerDataDescriptor;
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
	public static String initLength = "20";
	public static String initNeighbour = "NeighbourMoore";
	public static String initZone = "World";
	public static String initScheduler = "LateCommitScheduler";
	/**
	 *  
	 */
	protected int nbVivants; // proportions de vivants
	protected int i, s, g;
	protected double pregenerated;
	protected String conf_s;
	/**
	 * @param length
	 */
	public World(int length) {
		super(length);
	}
	public void init() {
		getInfo();
		if (!(conf_s.equals("Random"))) {
			setConfig();
		} // default configuration is Random (see Agent.init())
		try {
			statManager.add(new CalculatedVar("TrueState", Class.forName(
					this.pack() + ".Agent").getMethod("getState", null),
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
			if (((Agent) i.next()).etat) {
				n++;
			}
		}
		nbVivants = n;
	}
	public Object getState() {
		return new Double((double) nbVivants / (double) agentSetSize);
	}
	// Les services de mise à jour accéssibles du package uniquement
	// Mets la même valeur à tous les Agents
	public void setI(int n) {
		i = n;
		for (Iterator i = iterator(); i.hasNext();) {
			((Agent) i.next()).setI(n);
		}
	}
	protected int getI() {
		return i;
	}
	public void setS(int n) {
		s = n;
		for (Iterator i = iterator(); i.hasNext();) {
			((Agent) i.next()).setS(n);
		}
	}
	protected int getS() {
		return s;
	}
	public void setG(int n) {
		g = n;
		for (Iterator i = iterator(); i.hasNext();) {
			((Agent) i.next()).setG(n);
		}
	}
	protected int getG() {
		return g;
	}
	protected double getPregenerated() {
		return pregenerated;
	}
	public void setPregenerated(double d) {
		pregenerated = d;
	}
	public void getInfo() {
	}
	public ArrayList getDescriptors() {
		descriptors.clear();
		descriptors.add(new IntegerDataDescriptor(this, "Isolation", "i", i,
				true));
		descriptors.add(new IntegerDataDescriptor(this, "Suffocation", "s", s,
				true));
		descriptors.add(new IntegerDataDescriptor(this, "Generation", "g", g,
				true));
		descriptors.add(new DoubleDataDescriptor(this, "Pre-generated",
				"pregenerated", pregenerated, true, 3));
		descriptors.add(new ChoiceDataDescriptor(this, "Initial configuration",
				"conf_s", new String[]{"Random", "Stable", "Planer",
						"Planer eater", "Pentadecathlon"}, conf_s, true));
		return descriptors;
	}
	public void setConf_s(String s) {
		conf_s = s;
	}
	protected void setConfig() {
		Agent a;
		Vector points = new Vector();
		if (conf_s.equals("Stable"))
			points = stablePoints();
		if (conf_s.equals("Planer"))
			points = planerPoints();
		if (conf_s.equals("Planer eater"))
			points = planerEaterPoints();
		if (conf_s.equals("Pentadecathlon"))
			points = pentadecathlonPoints();
		for (Iterator i = iterator(); i.hasNext();) {
			a = ((Agent) i.next());
			if (points.contains(new Integer(a.agentID))) {
				a.etat = true;
			} else {
				a.etat = false;
			}
		}
	}
	protected Vector stablePoints() {
		Vector v = new Vector();
		int l = length;
		v.add(new Integer(1 + l));
		v.add(new Integer(2 + l));
		v.add(new Integer(1 + 2 * l));
		v.add(new Integer(2 + 2 * l));
		return v;
	}
	protected Vector planerPoints() {
		int l = length;
		Vector v = new Vector();
		v.add(new Integer(8 + l));
		v.add(new Integer(7 + 2 * l));
		v.add(new Integer(7 + 3 * l));
		v.add(new Integer(8 + 3 * l));
		v.add(new Integer(9 + 3 * l));
		return v;
	}
	protected Vector planerEaterPoints() {
		int l = length;
		Vector v = new Vector();
		v.add(new Integer(8 + l));
		v.add(new Integer(7 + 2 * l));
		v.add(new Integer(7 + 3 * l));
		v.add(new Integer(8 + 3 * l));
		v.add(new Integer(9 + 3 * l));
		v.add(new Integer(5 + 5 * l));
		v.add(new Integer(6 + 5 * l));
		v.add(new Integer(6 + 6 * l));
		v.add(new Integer(3 + 7 * l));
		v.add(new Integer(4 + 7 * l));
		v.add(new Integer(5 + 7 * l));
		v.add(new Integer(3 + 8 * l));
		return v;
	}
	protected Vector pentadecathlonPoints() {
		int l = length;
		Vector v = new Vector();
		v.add(new Integer(3 + 6 * l));
		v.add(new Integer(4 + 6 * l));
		v.add(new Integer(5 + 5 * l));
		v.add(new Integer(5 + 7 * l));
		v.add(new Integer(6 + 6 * l));
		v.add(new Integer(7 + 6 * l));
		v.add(new Integer(8 + 6 * l));
		v.add(new Integer(9 + 6 * l));
		v.add(new Integer(10 + 5 * l));
		v.add(new Integer(10 + 7 * l));
		v.add(new Integer(11 + 6 * l));
		v.add(new Integer(12 + 6 * l));
		return v;
	}
	public void setDefaultValues() {
		i = 1;
		s = 4;
		g = 3;
		pregenerated = 0.2;
		conf_s = "Random";
	}
}