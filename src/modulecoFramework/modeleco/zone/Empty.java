/**
 * Title:        Empty<p>
 * Description:  Personne.<p>
 * @author Antoine.Beugnard@enst-bretagne.fr
  * Created on may, 2000
 * @version 1.1  july,10, 2001
 */
   package modulecoFramework.modeleco.zone;

   import java.util.ArrayList;
	
   import modulecoFramework.modeleco.ZoneSelector;

   public class Empty extends ZoneSelector {
   
   /**
   * Retourne une liste vide.
   */
      public ArrayList compute() {// Calcule une zone
      // un choisi au hasard dans le monde
         ArrayList v = new ArrayList(0);
         return v;
      }
   
   /**
   * Retourne une liste vide.
   */
      public ArrayList  compute(int index) {// Calcule un voisinage
      // un choisi au hasard dans le monde
         ArrayList v = new ArrayList(0);
         return v;
      }
   }
