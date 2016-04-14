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

   public class NeighbourVonNeumanPlan extends NeighbourSelector {
   
   /**
   * Choisit mes 4 voisins NSEW sur un tore.
   */
      public ArrayList  compute(int index) { // Calcule le voisinage
         ArrayList v = new ArrayList();
         //index = index + 1 ;
         int i = index;
         if (i - length >= 0 ){ v.add(world.get(i - length));};
         if (i + length < agentSetSize) {v.add(world.get(i + length));};
         if (((i+1)%length != 0 )&&(i!=agentSetSize)) { v.add(world.get(i + 1));};
         if (((i-1)%length != length - 1 )&&(i!=0))  {v.add(world.get(i - 1));};;;;;;;;;;;;;;;
         ;;;;;;;;;;;;;
      
      
      
      
      
      
      
      
         return v;
      }
   }
