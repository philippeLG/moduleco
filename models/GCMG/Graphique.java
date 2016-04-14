/*
 * @(#)Graphique.java 1.0 25-Apr-04
 */
package models.GCMG;
import java.awt.Color;
import modulecoGUI.grapheco.graphix.Trace;
/**
 * Define the variables to display in the right panel (<em>Graphique</em>).
 * <p>
 * Those variables have to be recorded by the <em>statManager</em> in
 * <tt>World</tt>
 * 
 * @author Gilles Daniel (gilles@cs.man.ac.uk)
 * @version 1.0, 25-Apr-04
 */
public class Graphique extends modulecoGUI.grapheco.graphix.Graphique {
	/**
	 * Create the right panel and add some variables to display
	 */
	public Graphique() {
		super();
		this.addTrace(new Trace("Price", Color.blue, "statPrice"), true);
		//this
		//.addTrace(new Trace("Log Returns", Color.blue, "statLogReturn"),
		//		true);
		//this
		//		.addTrace(new Trace("noActive", Color.black, "statNoActive"),
		//				true);
		//this.addTrace(
		//		new Trace("ExcessDemand", Color.red, "statExcessDemand"),
		//		true);
		this.addTrace(
				new Trace("Volume", Color.green, "statVolume"),
				true);
	}
}