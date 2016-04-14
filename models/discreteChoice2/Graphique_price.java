/*
 * @(#)Graphique.java	1.1 09-Mar-04
 */
   package models.discreteChoice2;
   import java.awt.Color;
   import modulecoGUI.grapheco.graphix.Trace;
/**
 * Define the variables to display in the right panel (<em>Graphique</em>).
 * 
 * Those variables have to be recorded by the <em>statManager</em> in <tt>World</tt>
 * 
 * @author Gilles Daniel (gilles@cs.man.ac.uk)
 * @version 1.0, 23-Feb-04
 * @version 1.1, 09-Mar-04
 * @see models.emptyModel.World
 */
           
   public class Graphique_price extends modulecoGUI.grapheco.graphix.Graphique {
           
   /**
	* Create the right panel and add some variables to display
	*/
              
	  public Graphique_price() {
              
		 super();
	  /**
	   * <ul>
	   * <li>"Price" is the name to display on right-click of the right
	   * panel (<em>Graphique</em>)
	   * <li>"statPrice" is the exact name used by the <em>statManager</em>
	   * </ul>
	   */
	  //this.addTrace(new Trace("Ratio", Color.green, "statRatio"), true);
		 this.addTrace(new Trace("Price", Color.red, "statNormalizedPrice"), true);
	  //this.addTrace(new Trace("Avalanche", Color.red, "statAvalanche"), true);
	  //System.out.println("discreteChoice2.Graphique_price");
	  }
   }