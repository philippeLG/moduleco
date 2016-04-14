/**
 * Title:        VoisinageVonNeuman<p>
 * Description:  Chaque Agent a pour voisins NSEW, sur un tore.<p>
 * Hypothèse : Le monde est carré !
 * @author Vincent LELARGE
  * Created on may, 2000
 * @version 1.1  july,10, 2001
 */
   package modulecoFramework.modeleco.zone;

   import java.util.ArrayList;
   import modulecoFramework.modeleco.NeighbourSelector;

   public class NeighbourVonNeuman2level extends NeighbourSelector {
   
   /**
   * Choisit mes 4 voisins NSEW au deuxieme degre sur un tore.
   */
      public ArrayList  compute(int index) { // Calcule le voisinage
         ArrayList v = new ArrayList(4);
         v.add(world.get((index%length >= length - 2) ? (index - length + 2) :(index+2)%agentSetSize)); // E
         v.add(world.get((index >= agentSetSize-2*length)? index - agentSetSize + 2*length :(index+2*length)%agentSetSize)); // S
         v.add(world.get((index%length <= 1)? index + length -2 : (index+agentSetSize-2)%agentSetSize)); // W
         v.add(world.get((index+agentSetSize-2*length)%agentSetSize)); // N
         return v;
      }
   }
