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


   public class BarChart3 extends modulecoGUI.grapheco.graphix.BarChart
   {
      protected CAgentRepresentation oldRepresentation;
      protected MenuItem menuItemTraceGraph;
      protected Vector value = new Vector();
      private int place, nBar;
      public BarChart3()//(int numbar, int e)
      {
         super(5 , 5);
      	//((World) eWorld).getNeighbourSize() > ne peut etre utiliseé dans le constructeur
         //System.out.println("BarChart3.Constructor");
         nBar = 5;
      	//numbar;
         menuItemTraceGraph = new MenuItem("Trace Graphique");
         menuItemTraceGraph.addActionListener(this);
         menuItemTraceGraph.setEnabled(false);
         popupMenu.add(menuItemTraceGraph);
         setColorBar(Color.green);
         setHauteur(100);
         setNumBar(nBar);
         //nBar= 5;//((World) eWorld).getNeighbourSize()+1;
      
      
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
         double h ;
         value.clear();
         double v=0;
         double vtemp=0;
         int j ;
         for(int k=0; k<nBar;k++){
         
            h=(double)(k+1)/(nBar-1);
            v = ((World) eWorld).getCumulativeEta(h);
            j = (int)(v-vtemp);
            vtemp = v;
            //System.out.println("BarChart3 / j = "+j);
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
