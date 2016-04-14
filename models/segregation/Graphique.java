
/**
 * Graphique.java
 *
 *
 * Created: Tue Sep 19 09:34:47 2000
 *
 * @author frederic.falempin@enst-bretagne.fr
 * @version 1.2  august,5, 2002  
 */

   package models.segregation;

   import java.awt.Color;

   import modulecoGUI.grapheco.graphix.Trace;


   public class Graphique extends modulecoGUI.grapheco.graphix.Graphique 
   {    
      public Graphique() 
      {
         super();
      
         this.addTrace(new Trace("People living in the good place", Color.black, "LiveInTheGoodPlace"), true);
      }   
   } // Graphique
