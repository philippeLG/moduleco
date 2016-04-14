/**
 * Title: Moduleco - voidModel.World.java <p>
 * Description: 
 * Copyright : (c)enst-bretagne
 * @author Antoine.Beugnard@enst-bretagne.fr, Denis.Phan@enst-bretagne.fr
 * @version 1.2  august,5, 2002
 * @version 1.4. february 2004    
 */
package models.voidModel;
// import java.util.Iterator;
import java.util.ArrayList;
import modulecoFramework.modeleco.ENeighbourWorld;
import modulecoGUI.grapheco.descriptor.InfoDescriptor;
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
	/**
	 * @param length
	 */
	public World(int length) {
		super(length);
	}
	public void setDefaultValues() {
	}
	public void init() {
	}
	public void compute() {
	}
	/**
	 * Invoked by ENeighbourWorld.populateAll(nsClass) to be executed first,
	 * before the end of this world's constructor during each world's creation
	 * process.
	 */
	public void getInfo() {
	}
	public ArrayList getDescriptors() {
		descriptors.clear();
		descriptors.add(new InfoDescriptor(
				"Moduleco version 1.1. du 10/07/2001"));
		return descriptors;
	}
}