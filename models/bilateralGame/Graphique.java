 /* Source File Name:   models.bilateralGame.World.java <p>
 * Copyright:    Copyright (c)enst-bretagne
 * @author denis.phan@enst-bretagne.fr
  * @version 1.4  February, 2004
*/

   package models.bilateralGame;

   import java.awt.Color;

// import modulecoGUI.grapheco.CAgentRepresentation;
   import modulecoGUI.grapheco.graphix.Trace;

   public class Graphique extends modulecoGUI.grapheco.graphix.Graphique
   {
      public Graphique(){
         super();
      
         this.addTrace(new Trace("Number of red", Color.red, "FalseState"), true);
         this.addTrace(new Trace("Number of changes", Color.green, "Changes"), true);
      }
   }
