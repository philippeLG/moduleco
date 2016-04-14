 /* Source File Name:    models.discreteChoice.BarChart1.java
 * Copyright:    Copyright (c)enst-bretagne
 * @author camille.monge@enst-bretagne.fr;Denis.Phan@enst-bretagne.fr
 * @version 1.4  February, 2004
* @version 1.4. february 2004  
 */

   package models.discreteChoice;

   import java.util.Vector;
// import modulecoGUI.grapheco.graphix.Graphique;
// import java.awt.event.ActionListener;
   import java.awt.event.ActionEvent;
   import java.awt.MenuItem;
   import java.awt.Color;
   import modulecoGUI.grapheco.CAgentRepresentation; 
   import modulecoGUI.grapheco.XRepresentations ; //added DP 28/10 2001


   public class BarChart1 extends modulecoGUI.grapheco.graphix.BarChart
   {
      protected CAgentRepresentation oldRepresentation;
      protected MenuItem menuItemTraceGraph;
      protected Vector value = new Vector();
      private int place, nBar;
      public BarChart1()//(int numbar, int e)
      {
         //super(10, 10);        //System.out.println("BarChart.Constructor");
         nBar = 11;
      	//numbar;
         menuItemTraceGraph = new MenuItem("Trace Graphique");
         menuItemTraceGraph.addActionListener(this);
         menuItemTraceGraph.setEnabled(false);
         popupMenu.add(menuItemTraceGraph);
         setColorBar(Color.red);
         setHauteur(100);
         setNumBar(nBar);
      
      
      }
   	/*
      public void updateImage()
      {
         System.out.println("BarChartUpdateImage");
      
         // if(statManager.getIteration() % refresh == 0);
         super.updateImage();
      }
   */
      public Vector getValue(){
         setColorBar(Color.red);
         double h ;
         value.clear();
         for(int k=0; k<nBar;k++){
            //if((((World) eWorld).random_s2).equalsIgnoreCase("JavaLogistic"))
            h=(double)k/nBar-0.5;
            double v = ((World) eWorld).getCumulativeDistribution(h);
            int j = (int) v;
            for (int p=0;p<j;p++){
               value.addElement(new Integer(k));
            }
         }
         return value;
      }
   
   
      public void setGraphic(CAgentRepresentation cAgentRepresentation, int place){
      
         oldRepresentation = cAgentRepresentation;
         this.place = place;
      }
   
      public void actionPerformed(ActionEvent e){
      
      
         if (e.getSource().equals(menuItemTraceGraph)){
         
            if((getParent().getClass().toString()).equals("class modulecoGUI.grapheco.XRepresentations")){
               {
                  ((XRepresentations)getParent()).setXRepresentation(oldRepresentation, place);
                  centralControl.getEPanel().updateImage();
               }
            }
            else if((getParent().getClass().toString()).equals("class modulecoGUI.grapheco.EPanel"))
               centralControl.getEPanel().setRightRepresentation(oldRepresentation);
         }
         else super.actionPerformed(e);
      }
   }
