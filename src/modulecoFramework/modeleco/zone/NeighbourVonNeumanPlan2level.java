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

   public class NeighbourVonNeumanPlan2level extends NeighbourSelector {
   
   /**
   * Choisit mes 4 voisins NSEW sur un tore.
   */
      public ArrayList  compute(int index) { // Calcule le voisinage
         ArrayList v = new ArrayList();
         //index = index + 1 ;
         int i = index;
         if (i - 2 * length >= 0 ){ v.add(world.get(i - 2*length));};
         if (i + 2 * length < agentSetSize) {v.add(world.get(i + 2*length));};
         if (((i+2)%length >= 2 )&&(i < agentSetSize - 2)) { v.add(world.get(i + 2));};
         if (((i-2)%length <= length - 3 )&&( i >=2 ))  {v.add(world.get(i - 2));};;;;;;;;;;;;;;;
         ;;;;;;;;;;;;;
      
      
      
      
      
      
      
      
         return v;
      }
   }
