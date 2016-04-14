/*
 * @(#)Graphique.java	1.0 26-Feb-04
 */
package models.MG;
import java.awt.Color;
import modulecoGUI.grapheco.graphix.Trace;
/**
 * Define the variables to display in the right panel (<em>Graphique</em>).
 * <p>
 * Those variables have to be recorded by the <em>statManager</em> in <tt>World</tt>
 * 
 * @author Gilles Daniel (gilles@cs.man.ac.uk)
 * @version 1.0, 26-Feb-04
 */
public class Graphique extends modulecoGUI.grapheco.graphix.Graphique {
	/**
	 * Create the right panel and add some variables to display
	 */
	public Graphique() {
		super();
		//this.addTrace(new Trace("Price", Color.blue, "myStatPrice"), true);
		this.addTrace(
				new Trace("ExcessDemand", Color.red, "myStatExcessDemand"),
				true);
	}
}
