 /**
 * Title:       Neighbour8 <p>
 * Description:  La zone autour des 4 pr�d�cesseurs et suivants est s�lectionn�e.<p>
 * @author Damien.Iggiotti@enst-bretagne.fr
 * Created on june, 2002
 * @version 1.1  june,11, 2002
 */
   package modulecoFramework.modeleco.zone;

   import java.util.ArrayList;
   
	import modulecoFramework.modeleco.NeighbourSelector;

   public class Neighbour8 extends NeighbourSelector {
   
   /**
   * Choisit les deux pr�d�cesseurs et les deux suivants dans la liste du monde...<p>
   */
      public ArrayList compute(int index) { // voisinage
         ArrayList v = new ArrayList(4);
         v.add(world.get((index+1)%agentSetSize)); // E
         v.add(world.get((index+2)%agentSetSize)); // EE
         v.add(world.get((index+3)%agentSetSize)); // EEE
         v.add(world.get((index+4)%agentSetSize)); // EEEE
         v.add(world.get((index+agentSetSize-1)%agentSetSize)); // W
         v.add(world.get((index+agentSetSize-2)%agentSetSize)); // WW
         v.add(world.get((index+agentSetSize-3)%agentSetSize)); // WWW
         v.add(world.get((index+agentSetSize-4)%agentSetSize)); // WWWW
         return v;
      }
   }
