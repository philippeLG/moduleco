 /* Source File Name:   Graphique.java
 * Copyright:    Copyright (c)enst-bretagne
 * @author camille.monge@enst-bretagne.fr
  * @version 1.4  February, 2004
*/

   package models.adoption;

   import java.awt.Color;

   //import modulecoGUI.grapheco.CAgentRepresentation;
   import modulecoGUI.grapheco.graphix.Trace;

   public class Graphique extends modulecoGUI.grapheco.graphix.Graphique
   {
      public Graphique()
      {
         super();
      
         this.addTrace(new Trace("Number of blue", Color.blue, "FalseState"), true);
         this.addTrace(new Trace("Number of changes", Color.green, "Changes"), true);
      }
   }
