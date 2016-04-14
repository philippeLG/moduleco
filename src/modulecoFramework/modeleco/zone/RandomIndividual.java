/**
 * Title:        Moduleco<p>
 * Description:  Un individu au hasard est sélectionné.<p>
 * @author Antoine.Beugnard@enst-bretagne.fr
  * Created on may, 2000
 * @version 1.1  july,10, 2001
 */
   package modulecoFramework.modeleco.zone;

   import java.util.ArrayList;
   import modulecoFramework.modeleco.ZoneSelector;

   public class RandomIndividual extends ZoneSelector {
   
      public ArrayList compute() {// Calcule une zone
      // un choisi au hasard dans le monde
         ArrayList v = new ArrayList(1);
         v.add(0, world.get((int) (Math.random() * agentSetSize)));
         return v;
      }
      public ArrayList  compute(int index) {// Calcule un voisinage
      // un choisi au hasard dans le monde
         ArrayList v = new ArrayList(1);
         v.add(0, world.get((int) (Math.random() * agentSetSize)));
         return v;
      }
   }
