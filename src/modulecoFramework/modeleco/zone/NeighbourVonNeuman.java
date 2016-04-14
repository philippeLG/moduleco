/**
 * Title:        VoisinageVonNeuman<p>
 * Description:  Chaque Agent a pour voisins NSEW, sur un tore.<p>
 * Hypothèse : Le monde est carré !
 * @author Antoine.Beugnard@enst-bretagne.fr
  * Created on may, 2000
 * @version 1.1  july,10, 2001
 */
   package modulecoFramework.modeleco.zone;

   import java.util.ArrayList;
   import modulecoFramework.modeleco.NeighbourSelector;

   public class NeighbourVonNeuman extends NeighbourSelector {
   
   /**
   * Choisit mes 4 voisins NSEW sur un tore.
   */
      public ArrayList  compute(int index) { // Calcule le voisinage
         ArrayList v = new ArrayList(4);
         v.add(world.get((index%length == length -1) ? (index - length + 1) :(index+1)%agentSetSize)); // E
         v.add(world.get((index >= agentSetSize-length)? index - agentSetSize + length :(index+length)%agentSetSize)); // S
         v.add(world.get((index%length == 0)? index + length -1 : (index+agentSetSize-1)%agentSetSize)); // W
         v.add(world.get((index+agentSetSize-length)%agentSetSize)); // N
         return v;
      }
   }
