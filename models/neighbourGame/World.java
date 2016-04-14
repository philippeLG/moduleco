/**
 * Title: Moduleco - bilateralGame.World.java <p>
 * Description:  Je fais jouer des Agents à des jeux symétriques bilatéraux
 * avec une matrice de gain donnée. <p>
 * Copyright : (c)enst-bretagne
 * @author Antoine.Beugnard@enst-bretagne.fr, Denis.Phan@enst-bretagne.fr
 * @version 1.2  august,5, 2002
 * @version 1.4. february 2004  
 */
package models.neighbourGame;
import java.util.ArrayList;
import java.util.Iterator;
import modulecoFramework.modeleco.ENeighbourWorld;
import modulecoGUI.grapheco.descriptor.DoubleDataDescriptor;
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
	public double socialChoice;
	/**
	 * @param length
	 */
	public World(int length) {
		super(length);
	}
	public void setDefaultValues() {
	}
	public void init() {
		socialChoice = computeSocialChoice();
		//System.out.println("world.int()");
	}
	public void compute() {
		socialChoice = computeSocialChoice();
	}
	public void getInfo() {
	}
	public ArrayList getDescriptors() {
		descriptors.clear();
		descriptors.add(new DoubleDataDescriptor(this, "SocialChoice",
				"socialChoice", socialChoice, false, 6));
		return descriptors;
	}
	private double computeSocialChoice() {
		double n = 0;
		double thetaTemp;
		double f;
		for (Iterator i = iterator(); i.hasNext();) {
			thetaTemp = ((Agent) i.next()).getTheta();
			n += thetaTemp;
		}
		f = ((double) n / (double) this.size());
		return f;
	}
	public double getSocialChoice() {
		return socialChoice;
	}
}