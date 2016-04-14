 /** models.NTvsLinux.World
	*@author frederic.falempin@enst-bretagne.fr, camille.monge@enst-bretagne.fr
	* @version 1.2  august,5, 2002
	* @version 1.4. february 2004 modulecoGUI.graphe  
	*/

   package models.NTvsLinux;

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
      
         this.addTrace(new Trace("Number of Linux adopters", Color.blue, "TrueState"), true);
      }
   }
