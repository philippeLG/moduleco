 /** Source File Name:   Graphique.java
 * Copyright:    Copyright (c)enst-bretagne
 * @author camille.monge@enst-bretagne.fr;frederic.falempin@enst-bretagne.fr
 * @version 1.4  February, 2004
 */

   package models.auctioneer;

   import java.util.Vector;

   import java.awt.Color;
   import java.awt.MenuItem;

	//import java.awt.event.*;

   import modulecoGUI.grapheco.graphix.Trace;
// import modulecoGUI.grapheco.EPanel;
// import modulecoGUI.grapheco.CAgentRepresentation;

/**
* this class overide the dynamic graphics representation
* of a walrassian pure exchange market with auctioneer
* Its adress must be explicitely writing as a String in return from the method :
* World.getPreferredDynamicRepresentation()
* @see World#getPreferredDynamicRepresentation()
*/
   public class Graphique extends modulecoGUI.grapheco.graphix.Graphique
   {
      protected MenuItem menuItemTraceBarChartgramme;
      //protected BarChart histoRepresentation;
      protected boolean histogramme=false;protected Vector value = new Vector();
      public Graphique()
      {
         super();
      
         this.addTrace(new Trace("Abs(DemandExcess 1)", Color.magenta, "DemandExcess1"), true);
         this.addTrace(new Trace("Abs(DemandExcess 2)", Color.blue, "DemandExcess2"), true);
      
      
      }
   
   }
