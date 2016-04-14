 /* Source File Name:   Graphique.java
 * Copyright:    Copyright (c)enst-bretagne
 * @author camille.monge@enst-bretagne.fr, denis.phan@enst-bretagne.fr
 * @version 1.4  February, 2004
 */

   package models.discreteChoice;
   import java.awt.CheckboxMenuItem;
   import java.awt.MenuItem;
   import java.awt.event.ItemEvent;
   import java.awt.event.ActionEvent;
   import java.awt.Color;

   import modulecoFramework.modeleco.CAgent;
// import modulecoGUI.grapheco.CAgentRepresentation;
   import modulecoGUI.grapheco.graphix.Trace;
   import modulecoGUI.grapheco.XRepresentations ; //added DP 28/07 2002
   //import modulecoGUI.grapheco.graphix.BarChart;
// import modulecoGUI.grapheco.CentralControl;

   public class Graphique extends modulecoGUI.grapheco.graphix.Graphique {
      protected BarChart1 barChart1;
      protected MenuItem menuItemBarChart;
      protected CheckboxMenuItem menuItemSingleGraph ;
      private boolean singleGraph;
      private int place=0;
      public Graphique()
      {
         super();
      
         this.addTrace(new Trace("Number of subscribers", Color.blue, "State"), true);
         this.addTrace(new Trace("Number of changes", Color.green, "Changes"), true);
         menuItemBarChart = new MenuItem("XX");
         menuItemBarChart.addActionListener(this);
         popupMenu.add(menuItemBarChart);
      
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
            if (barChart1==null){
               barChart1= new BarChart1();
               barChart1.setCAgent((CAgent) eWorld);
               barChart1.setCentralControl(centralControl);
            }
            barChart1.setGraphic(this,place);
            if((getParent().getClass().toString()).equals("class modulecoGUI.grapheco.XRepresentations")){
               ((XRepresentations)getParent()).setXRepresentation((BarChart1)barChart1, place);
               centralControl.getEPanel().updateImage();
            }
            else if((getParent().getClass().toString()).equals("class modulecoGUI.grapheco.EPanel"))
               centralControl.getEPanel().setRightRepresentation(barChart1);
         }	
         else super.actionPerformed(e);
      }
   }
