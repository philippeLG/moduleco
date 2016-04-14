/**
  * Title:       Neighbour2 <p>
  * Description:  L'Agent precedent, ainsi que le suivant sont selectionnes comme voisins.
  * @author Camille.monge@enst-bretagne.fr
   * Created on may, 2000
 * @version 1.1  july,10, 2001
  */
   package modulecoFramework.modeleco.zone;

   import java.util.ArrayList;
   import modulecoFramework.modeleco.NeighbourSelector;

   public class Neighbour2 extends NeighbourSelector {
   
   /**
   * Choisit le prédécesseur et le suivant dans la liste du monde...<p>
   */
      public ArrayList compute(int index) { // voisinage
         ArrayList v = new ArrayList(4);
         v.add(world.get((index+1)%agentSetSize)); // E
         v.add(world.get((index+agentSetSize-1)%agentSetSize)); // W
         return v;
      }
   }
