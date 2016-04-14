 /* Source File Name:    models.bak.BarChart.java
 * Copyright:    Copyright (c)enst-bretagne
 * @author camille.monge@enst-bretagne.fr;Denis.Phan@enst-bretagne.fr
 * @version 1.4  February, 2004
 */

   package models.bak;

   import java.util.Vector;
// import modulecoGUI.grapheco.graphix.Graphique;
// import java.awt.event.ActionListener;
   import java.awt.event.ActionEvent;
   import java.awt.MenuItem;
   import modulecoGUI.grapheco.CAgentRepresentation; 
   import modulecoGUI.grapheco.XRepresentations ; //added DP 28/10 2001

   public class BarChart extends modulecoGUI.grapheco.graphix.BarChart
   {
      protected CAgentRepresentation oldRepresentation;
      protected MenuItem menuItemTraceGraph;
      protected Vector value = new Vector();
      private int place;
      public BarChart()
      {
         super();
         //System.out.println("BarChart.Constructor");
         menuItemTraceGraph = new MenuItem("Trace Graphique");
         menuItemTraceGraph.addActionListener(this);
         popupMenu.add(menuItemTraceGraph);
      
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
         value.clear();
      
         for (int i=0;i<(((World)eWorld).getStatManager()).getIteration();i++){
            Integer temp = new Integer((int)statManager.get("Distance",i));
            value.addElement(temp);
            //System.out.println("temp ="+temp);
         }
         //System.out.println("BarChart.getValue()");
         //System.out.println("BartChart ="+value);
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
