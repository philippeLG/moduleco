 /* Source File Name:   Graphique.java
 * Copyright:    Copyright (c)enst-bretagne
 * @author camille.monge@enst-bretagne.fr
 * @version 1.2  august,5, 2002
* @version 1.4. february 2004   
 */

   package models.tarifBinome;

   import java.awt.Color;

   import modulecoGUI.grapheco.graphix.Trace;

   public class Graphique extends modulecoGUI.grapheco.graphix.Graphique
   {
      public Graphique()
      {
         super();
      
         this.addTrace(new Trace("Number of subscribers", Color.blue, "TrueState"), true);
         this.addTrace(new Trace("Number of changes", Color.green, "Changes"), false);
      }
   }
