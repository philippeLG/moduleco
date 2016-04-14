 /* Source File Name:   Graphique.java
 * Copyright:    Copyright (c)enst-bretagne
 * @author camille.monge@enst-bretagne.fr;frederic.falempin@enst-bretagne.fr
 * * @version 1.2  august,5, 2002  
 */

   package models.lux;

   import java.util.Vector;
   import java.awt.Color;
   import java.awt.MenuItem;

   import modulecoGUI.grapheco.graphix.Trace;

   public class Graphique extends modulecoGUI.grapheco.graphix.Graphique
   {
      protected MenuItem menuItemTraceBarChartgramme;
      //protected BarChart histoRepresentation;
      protected boolean histogramme=false;protected Vector value = new Vector();
      public Graphique()
      {
         super();
         this.addTrace(new Trace("P", Color.black, "P"), true);
         this.addTrace(new Trace("Pf", Color.blue, "Pf"), true);
         this.addTrace(new Trace("x_opt", Color.green, "x_opt"), true);
         this.addTrace(new Trace("x_pess", Color.red, "x_pess"), true);
         this.addTrace(new Trace("x_fond", Color.blue, "x_fond"), true);
      	//this.addTrace(new Trace("Viability Minimum", Color.magenta, "Viability Minimum"), false);
         //this.addTrace(new Trace("Distance", Color.black, "Distance", Trace.POINT), true);
      
         /*menuItemTraceBarChartgramme = new MenuItem("Repartition des distances entre 2 mutation successives");
         menuItemTraceBarChartgramme.addActionListener(this);
         popupMenu.add(menuItemTraceBarChartgramme);
      */
      }
      /*public void actionPerformed(ActionEvent e){
      
         if (e.getSource().equals(menuItemTraceBarChartgramme)){
            histogramme=!histogramme;
            for (int i=0;i<(((World)eWorld).getStatManager()).getIteration();i++) value.addElement(new Integer((int)statManager.get("Distance",i)));
            int numBar=11;histoRepresentation=new BarChart(value,numBar, 0, (int)Math.round(((World)eWorld).getLength()*(numBar+1)/numBar), this);
            ((EPanel)getParent()).setRightRepresentation(histoRepresentation);
         }
         else super.actionPerformed(e);
      }*/
   
   }
