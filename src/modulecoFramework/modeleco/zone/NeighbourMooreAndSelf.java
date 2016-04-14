/**
 * Title:        NeighbourMooreAndSelf<p>
 * Description:  Chaque Agent a pour voisins NSEWNeNwSeSw et lui-même, sur un tore.<p>
 * Hypothèse : Le monde est carré, sur un tore !
 * @author Antoine.Beugnard@enst-bretagne.fr
  * Created on may, 2000
 * @version 1.1  july,10, 2001
 */
   package modulecoFramework.modeleco.zone;

   import java.util.ArrayList;
   import modulecoFramework.modeleco.NeighbourSelector;

   public class NeighbourMooreAndSelf extends NeighbourSelector {
   
   /**
   * Choisit mes 8 voisins et moi-même sur un tore.
   */
      public ArrayList  compute(int index) { // Calcule le voisinage
         ArrayList v = new ArrayList(9);
         v.add(world.get((index%length == length -1) ? (index - length + 1) :(index+1)%agentSetSize)); // E
         v.add(world.get((index >= agentSetSize-length)? index - agentSetSize + length :(index+length)%agentSetSize)); // S
         v.add(world.get((index%length == 0)? index + length -1 : (index+agentSetSize-1)%agentSetSize)); // W
         v.add(world.get((index < length)? agentSetSize - length + index : (index+agentSetSize-length)%agentSetSize)); // N
         v.add(world.get((index < length) ? ((index%length == length -1) ? agentSetSize - length :(agentSetSize - length + index +1)%agentSetSize) : ((index%length == length -1) ? (index -2*length +1)%agentSetSize : index - length +1) )); // NE
         v.add(world.get(((index < length) ? ((index%length == 0) ? agentSetSize - 1 :(agentSetSize - length + index -1)%agentSetSize) : ((index%length == 0) ? index - 1 :(index - length -1)%agentSetSize))));// NW
         v.add(world.get(((index >= agentSetSize-length) ? ((index%length == length -1) ? 0 :(index +length + 1)%agentSetSize) : ((index%length == length -1) ? index + 1 : (index +length +1)%agentSetSize ))));// SE
         v.add(world.get(((index >= agentSetSize-length) ? ((index%length == 0) ? length-1 :(index +length - 1)%agentSetSize) : ((index%length == 0) ? index + 2*length -1: (index +length -1)%agentSetSize )))); // SW
         v.add(world.get(index)); // Self
         return v;
      }
   }
