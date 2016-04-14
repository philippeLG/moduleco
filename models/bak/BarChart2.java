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
   import java.awt.Color;
   import modulecoGUI.grapheco.CAgentRepresentation; 
   import modulecoGUI.grapheco.XRepresentations ; //added DP 28/10 2001

   public class BarChart2 extends modulecoGUI.grapheco.graphix.BarChart
   {
      protected CAgentRepresentation oldRepresentation;
      protected MenuItem menuItemTraceGraph;
      protected Vector value = new Vector();
      private int place;
      public BarChart2()
      {
         super();
         //System.out.println("BarChart.Constructor");
         menuItemTraceGraph = new MenuItem("Trace Graphique");
         menuItemTraceGraph.addActionListener(this);
         popupMenu.add(menuItemTraceGraph);
         setColorBar(Color.yellow);
         setHauteur(100);
         setNumBar(10);
      
      }
   	/*
      public void updateImage()
      {
         System.out.println("BarChartUpdateImage");
      
         // if(statManager.getIteration() % refresh == 0);
         super.updateImage();
      }
   */
   /*
      public Vector getValue(){
         value.clear();
      
         for (int i=0;i<(((World)eWorld).getStatManager()).getIteration();i++) value.addElement(new Integer((int)statManager.get("Distance",i)));
         //System.out.println("BarChart.getValue()");
         return value;
      }
   */
   
      public Vector getValue(){
      
      
         value.clear();
         for(int k=0; k<10;k++){
            int hauteur =(int)((World) eWorld).viabilityHisto[k];
            for (int h=0; h<hauteur;h++){
               Integer temp = new Integer(k);
            //Integer temp = new Integer(40);
               value.addElement(temp);
            }
            //System.out.println(temp);
         }
         //System.out.println("BartChart2 ="+value);
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
