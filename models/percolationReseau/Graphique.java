
/**
 * Graphique.java
 *
 *
 * Created: Tue Sep 19 09:34:47 2000
 *
 * @author frederic.falempin@enst-bretagne.fr, Gregory.Gackel@enst-bretagne.fr
 * @version 1.2  august,5, 2002
* @version 1.4  February, 2004 
 */

   package models.percolationReseau;

   import java.awt.Color;

   import modulecoGUI.grapheco.graphix.Trace;


   public class Graphique extends modulecoGUI.grapheco.graphix.Graphique 
   {    
      public Graphique() 
      {
         super();
      
         this.addTrace(new Trace(" Dead Trees", Color.black, "DeadTrees"), true);
      }   
   } // Graphique
