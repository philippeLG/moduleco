
/**
 * Graphique.java
 *
 *
 * Created: Tue Sep 19 09:34:47 2000
 *
 * @author frederic.falempin@enst-bretagne.fr, Gregory.Gackel@enst-bretagne.fr
* Created on mai,27, 2001
 * @version 1.4  February, 2004
 */

   package models.epidemia;

   import java.awt.Color;

   import modulecoGUI.grapheco.graphix.Trace;


   public class Graphique extends modulecoGUI.grapheco.graphix.Graphique 
   {    
      public Graphique() 
      {
         super();
      
         this.addTrace(new Trace("Human sick", Color.red, "HumanSick"), true);
         this.addTrace(new Trace("Human immnune", Color.black, "HumanImmune"), true);
      }   
   } // Graphique
