 /* Source File Name:   Graphique.java
 * Copyright:    Copyright (c)enst-bretagne
 * @author camille.monge@enst-bretagne.fr
 * Created may-september 2000
 * @version 1.2  august,5, 2002  
 */

   package models.life;

   import java.awt.Color;

   import modulecoGUI.grapheco.graphix.Trace;

   public class Graphique extends modulecoGUI.grapheco.graphix.Graphique
   {
    /**
     * This constructor add the number of linux adopters trace to the graphic representation
     */
      public Graphique()
      {
         super();
      
         this.addTrace(new Trace("Number of living cells", Color.blue, "TrueState"), true);
      }
   }
