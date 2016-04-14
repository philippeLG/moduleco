 /**
 * Title:       Neighbour4 <p>
 * Description:  La zone autour des 2 prédécesseurs et suivants est sélectionnée.<p>
 * @author Antoine.Beugnard@enst-bretagne.fr
  * Created on may, 2000
 * @version 1.1  july,10, 2001
 */
   package modulecoFramework.modeleco.zone;

   import java.util.ArrayList;
   
	import modulecoFramework.modeleco.NeighbourSelector;

   public class Neighbour4 extends NeighbourSelector {
   
   /**
   * Choisit les deux prédécesseurs et les deux suivants dans la liste du monde...<p>
   */
      public ArrayList compute(int index) { // voisinage
         ArrayList v = new ArrayList(4);
         v.add(world.get((index+1)%agentSetSize)); // E
         v.add(world.get((index+2)%agentSetSize)); // EE
         v.add(world.get((index+agentSetSize-1)%agentSetSize)); // W
         v.add(world.get((index+agentSetSize-2)%agentSetSize)); // WW
         return v;
      }
   }
