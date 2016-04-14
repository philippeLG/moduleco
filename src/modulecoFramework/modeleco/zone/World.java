/**
 * Title:        World<p>
 * Description:  Tout le monde est sélectionné.<p>
 * @author Antoine.Beugnard@enst-bretagne.fr
 * Created on may, 2000
 * @version 1.1  july,10, 2001
 */
   package modulecoFramework.modeleco.zone;

   import java.util.ArrayList;
   import modulecoFramework.modeleco.ZoneSelector;

   public class World extends ZoneSelector {
   
   /**
   * Tout le monde.
   */
      public ArrayList  compute() {// Calcule une zone
         return world.getAgents() ;
      }
   
   /**
   * Tout le monde.
   */
      public ArrayList  compute(int index) {// Calcule un voisinage
         return world.getAgents() ;
      }
   }
