 /* Source File Name:    models.bak.Graphique.java
 * Copyright:    Copyright (c)enst-bretagne
 * @author denis.phan@enst-bretagne.fr, camille.monge@enst-bretagne.fr;
 * @version 1.4  February, 2004
 */

   package models.bak;


   import java.awt.CheckboxMenuItem;
   import java.awt.Color;

   import java.awt.event.ItemEvent;
   import modulecoGUI.grapheco.graphix.Trace;

   public class Graphique2 extends modulecoGUI.grapheco.graphix.Graphique //implements ItemListener
   {
      //protected Graphique graphique;
      protected CheckboxMenuItem menuItemSingleGraph ;
      public Graphique2()
      {
         super();
      
         this.addTrace(new Trace("Viability Average", Color.green, "Viability Average"), false);
         this.addTrace(new Trace("Viability Minimum", Color.magenta, "Viability Minimum"), true);
         this.addTrace(new Trace("Distance", Color.black, "Distance", Trace.POINT), false);
      
         menuItemSingleGraph = new CheckboxMenuItem("single/X Graph",false);
         menuItemSingleGraph.addItemListener(this);
      
         popupMenu.add(menuItemSingleGraph);
      }
      public void itemStateChanged(ItemEvent e){
         if (e.getSource().equals(menuItemSingleGraph))
            if(menuItemSingleGraph.getState()){
                //graphique = new Graphique();
               centralControl.getEPanel().setRightRepresentation(this);//graphique
               centralControl.controlPanel.checkMenuDisplayXGraphics.setState(false);
               centralControl.setDisplayGraphic();
            }
      }
   
   }
