 /* Source File Name:    models.bak.Graphique3.java
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


   public class Graphique3 extends modulecoGUI.grapheco.graphix.Graphique //implements ItemListener
   {
      protected BarChart barChart;
      protected BarChart2 barChart2;
      protected MenuItem menuItemBarChart,menuItemBarChart2;
      protected CheckboxMenuItem menuItemSingleGraph ;
      private int place=2;
      public Graphique3()
      {
         super();
      
         this.addTrace(new Trace("Viability Average", Color.green, "Viability Average"), false);
         this.addTrace(new Trace("Viability Minimum", Color.magenta, "Viability Minimum"), false);
         this.addTrace(new Trace("Distance", Color.black, "Distance", Trace.POINT), true);
      
         //XGraph Listener
      
         menuItemBarChart = new MenuItem("Repartition cumulée des distances entre 2 mutations");
         menuItemBarChart.addActionListener(this);
         popupMenu.add(menuItemBarChart);
      
         menuItemBarChart2 = new MenuItem("viability distribution Chart");
         menuItemBarChart2.addActionListener(this);
         popupMenu.add(menuItemBarChart2);
      
      
         menuItemSingleGraph = new CheckboxMenuItem("single/XGraph",false);
         menuItemSingleGraph.addItemListener(this);
         popupMenu.add(menuItemSingleGraph);
      }
   /**
   	* XGraph > singleGraph
   	*/
   
      public void itemStateChanged(ItemEvent e){
      
         if (e.getSource().equals(menuItemSingleGraph))
            if(menuItemSingleGraph.getState()){
               centralControl.getEPanel().setRightRepresentation(this);//
               centralControl.controlPanel.checkMenuDisplayXGraphics.setState(false);
               centralControl.setDisplayGraphic();
            }
      }
   	/**
   	* Graph > BarChart
   	*/
   
      public void actionPerformed(ActionEvent e){
      
      
         if (e.getSource().equals(menuItemBarChart)){
            if (barChart==null){
               barChart= new BarChart();
               barChart.setCAgent((CAgent) eWorld);
               barChart.setCentralControl(centralControl);
            }
            barChart.setGraphic(this,place);
            if((getParent().getClass().toString()).equals("class modulecoGUI.grapheco.XRepresentations")){
               ((XRepresentations)getParent()).setXRepresentation((BarChart)barChart, place);
               centralControl.getEPanel().updateImage();
            }
            else if((getParent().getClass().toString()).equals("class modulecoGUI.grapheco.EPanel"))
               centralControl.getEPanel().setRightRepresentation(barChart);
         }	
         else
            if (e.getSource().equals(menuItemBarChart2)){
               if (barChart2==null){
                  barChart2= new BarChart2();
                  barChart2.setCAgent((CAgent) eWorld);
                  barChart2.setCentralControl(centralControl);
               }
               barChart2.setGraphic(this,place);
               if((getParent().getClass().toString()).equals("class modulecoGUI.grapheco.XRepresentations")){
                  ((XRepresentations)getParent()).setXRepresentation((BarChart2)barChart2, place);
                  centralControl.getEPanel().updateImage();
               }
               else if((getParent().getClass().toString()).equals("class modulecoGUI.grapheco.EPanel"))
                  centralControl.getEPanel().setRightRepresentation(barChart2);
            }	
            else
               super.actionPerformed(e);
      }
   }
