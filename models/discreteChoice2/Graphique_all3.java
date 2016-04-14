 /*
 * @(#)Graphique.java	1.1 09-Mar-04
 */
package models.discreteChoice2;
import java.awt.CheckboxMenuItem;
import java.awt.MenuItem;
import java.awt.event.ItemEvent;
import java.awt.event.ActionEvent;
import java.awt.Color;

//import models.discreteChoice.BarChart1;
//import modulecoFramework.modeleco.CAgent;
//import modulecoGUI.grapheco.XRepresentations;
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
public class Graphique_all3 extends modulecoGUI.grapheco.graphix.Graphique {
	protected MenuItem menuItemBarChart;

	protected CheckboxMenuItem menuItemSingleGraph;

	private boolean singleGraph;

	private int place = 0;
	/**
	 * Create the right panel and add some variables to display
	 */
	public Graphique_all3() {
		super();
		/**
		 * <ul>
		 * <li>"Price" is the name to display on right-click of the right
		 * panel (<em>Graphique</em>)
		 * <li>"statPrice" is the exact name used by the <em>statManager</em>
		 * </ul>
		 */
		this.addTrace(new Trace("Ratio", Color.getHSBColor((float)0.35, (float)1, (float)0.45), "statRatio"), true);
		this.addTrace(new Trace("Price", Color.black, "statPrice"), true);
		this.addTrace(new Trace("Avalanche", Color.red, "statAvalanche"), true);
        menuItemSingleGraph = new CheckboxMenuItem("single/XGraph",false);
        menuItemSingleGraph.addItemListener(this);
        popupMenu.add(menuItemSingleGraph);
        //System.out.println("discreteChoice2.Graphique_all3");
	}
	/**
	 * XGraph > singleGraph
	 */

	public void itemStateChanged(ItemEvent e) {
		if (e.getSource().equals(menuItemSingleGraph))
			if (menuItemSingleGraph.getState()) {
				centralControl.getEPanel().setRightRepresentation(this);//
				centralControl.controlPanel.checkMenuDisplayXGraphics
						.setState(false);
				centralControl.setDisplayGraphic();
			}
	}

	/**
	 * Graph > BarChart
	 */
	public void actionPerformed(ActionEvent e) {
/*
		if (e.getSource().equals(menuItemBarChart)) {
			if (barChart1 == null) {
				barChart1 = new BarChart1();
				barChart1.setCAgent((CAgent) eWorld);
				barChart1.setCentralControl(centralControl);
			}
			barChart1.setGraphic(this, place);
			if ((getParent().getClass().toString())
					.equals("class modulecoGUI.grapheco.XRepresentations")) {
				((XRepresentations) getParent()).setXRepresentation(
						(BarChart1) barChart1, place);
				centralControl.getEPanel().updateImage();
			} else if ((getParent().getClass().toString())
					.equals("class modulecoGUI.grapheco.EPanel"))
				centralControl.getEPanel().setRightRepresentation(barChart1);
		} else
			super.actionPerformed(e);
			*/
	}
	
}
