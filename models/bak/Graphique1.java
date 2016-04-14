 /* Source File Name:    models.bak.Graphique.java
 * Copyright:    Copyright (c)enst-bretagne
 * @author denis.phan@enst-bretagne.fr, camille.monge@enst-bretagne.fr;
 * @version 1.4  February, 2004
 */

   package models.bak;

   import java.awt.CheckboxMenuItem;
   import java.awt.MenuItem;
   import java.awt.Color;

   import java.awt.event.ItemEvent;
   import java.awt.event.ActionEvent;

   import modulecoFramework.modeleco.CAgent;
   import modulecoGUI.grapheco.graphix.Trace;
   import modulecoGUI.grapheco.XRepresentations ; //added DP 28/10 2001


   public class Graphique1 extends modulecoGUI.grapheco.graphix.Graphique 
   {
      protected BarChart barChart;
      protected MenuItem menuItemBarChart;
      protected CheckboxMenuItem menuItemSingleGraph ;
      public Graphique1()
      {
         super();
      
         this.addTrace(new Trace("Viability Average", Color.green, "Viability Average"), true);
         this.addTrace(new Trace("Viability Minimum", Color.magenta, "Viability Minimum"), false);
         this.addTrace(new Trace("Distance", Color.black, "Distance", Trace.POINT), true);
      
         menuItemBarChart = new MenuItem("Repartition cumulée des distances entre 2 mutations");
         menuItemBarChart.addActionListener(this);
         popupMenu.add(menuItemBarChart);
      
         menuItemSingleGraph = new CheckboxMenuItem("single/X Graph",false);
         menuItemSingleGraph.addItemListener(this);
         popupMenu.add(menuItemSingleGraph);
      }
      public void itemStateChanged(ItemEvent e){
         if (e.getSource().equals(menuItemSingleGraph))
            if(menuItemSingleGraph.getState()){
               centralControl.getEPanel().setRightRepresentation(this);//
               centralControl.controlPanel.checkMenuDisplayXGraphics.setState(false);
               centralControl.setDisplayGraphic();
            }
      }
      public void actionPerformed(ActionEvent e){
      
         if (e.getSource().equals(menuItemBarChart)){
            if (barChart==null){
               barChart= new BarChart();
               barChart.setCAgent((CAgent) eWorld);
               barChart.setCentralControl(centralControl);
            }
            //barChart.setGraphic(this);
            if((getParent().getClass().toString()).equals("class modulecoGUI.grapheco.XRepresentations")){
               ((XRepresentations)getParent()).setXRepresentation((BarChart)barChart, 0);
               centralControl.getEPanel().updateImage();
            }
            else if((getParent().getClass().toString()).equals("class modulecoGUI.grapheco.EPanel"))
               centralControl.getEPanel().setRightRepresentation(barChart);
         }	
         else super.actionPerformed(e);
      }
   }
