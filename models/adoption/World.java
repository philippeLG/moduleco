/**
 * Title: Moduleco - bilateralGame.World.java <p>
 * Description:  Je fais jouer des Agents à des jeux symétriques bilatéraux
 * avec une matrice de gain donnée. <p>
 * Copyright : (c)enst-bretagne
 * @author Antoine.Beugnard@enst-bretagne.fr, Denis.Phan@enst-bretagne.fr
 * @version 1.2  august,5, 2002
 * @version 1.4. february 2004  
 */
package models.adoption;
import java.util.ArrayList;
//   import java.util.Iterator;
import modulecoFramework.modeleco.ENeighbourWorld;
import modulecoGUI.grapheco.descriptor.DoubleDataDescriptor;
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
	public static String initNeighbour = "NeighbourVonNeuman";
	public static String initZone = "World";
	public static String initScheduler = "LateCommitScheduler";
	/**
	 * 
	 */
	public double initProba;
	/**
	 * Constructor.
	 * @param length
	 */
	public World(int length) {
		super(length);
	}
	public void getInfo() {
	}
	public void setDefaultValues() {
		initProba = 0.95;
	}
	public void init() {
		try {
			statManager.add(new CalculatedVar("FalseState", Class.forName(
					this.pack() + ".Agent").getMethod("getInverseState", null),
					CalculatedVar.NUMBER, new Boolean(false)));
			statManager.add(new CalculatedVar("Changes", Class.forName(
					this.pack() + ".Agent").getMethod("hasChanged", null),
					CalculatedVar.NUMBER, new Boolean(true)));
		} catch (ClassNotFoundException e) {
			System.out.println(e.toString());
		} catch (NoSuchMethodException e) {
			System.out.println(e.toString());
		}
		//System.out.println("world.int()");
	}
	/*
	 * public void compute() { }
	 */
	public ArrayList getDescriptors() {
		descriptors.clear();
		descriptors.add(new DoubleDataDescriptor(this, "Proba de Un",
				"initProba", initProba, true, 6));
		return descriptors;
	}
	public void setInitProba(double in) {
		initProba = in;
	}
}