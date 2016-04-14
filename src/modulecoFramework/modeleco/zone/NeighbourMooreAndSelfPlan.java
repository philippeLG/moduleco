/**
 * Title:        VoisinageVonNeuman<p>
 * Description:  Chaque Agent a pour voisins 8+lui, sur un plan.<p>
 * Hypothèse : Le monde est carré !
 * @author Antoine.Beugnard@enst-bretagne.fr
  * Created on may, 2000
 * @version 1.1  july,10, 2001
 */
   package modulecoFramework.modeleco.zone;

   import java.util.ArrayList;
   import modulecoFramework.modeleco.NeighbourSelector;

   public class NeighbourMooreAndSelfPlan extends NeighbourSelector {
   
   /**
   * Choisit mes 9 voisins NSEW sur un plan
   */
      public ArrayList  compute(int index) { // Calcule le voisinage
         ArrayList v = new ArrayList();
         //index = index + 1 ;
         int i = index;
      //	int ws = world.ws;
        // System.out.println(index);
         if (index == 0) {v.add(world.get(1));
            v.add(world.get(length)); v.add(world.get(length+1));}
         if (index == length-1) {v.add(world.get(length-2));
            v.add(world.get(2*length-1)); v.add(world.get(2*length-2));}
         if (index == agentSetSize - length ) {v.add(world.get(agentSetSize-2*length+1));
            v.add(world.get(agentSetSize-length+1)); v.add(world.get(agentSetSize-2*length));}
         if (index == agentSetSize-1) {v.add(world.get(agentSetSize-2));
            v.add(world.get(agentSetSize-length-1)); v.add(world.get(agentSetSize-length-2));}
         if ((index != 0) && ( index != length-1 ) && (index != agentSetSize-1) && (index != agentSetSize - length)) {
            if (index%length == 0) { 
               v.add(world.get(index-length));
               v.add(world.get(i-length+1));
               v.add(world.get(i+1));
               v.add(world.get(i+length+1));
               v.add(world.get(i+length));
            }
            if (index%length == length-1) { 
               v.add(world.get(index-length));
               v.add(world.get(i-length-1));
               v.add(world.get(i-1));
               v.add(world.get(i+length-1));
               v.add(world.get(i+length));
            }
            if (index < length ) { 
               v.add(world.get(i-1));
               v.add(world.get(i+length+1));
               v.add(world.get(i+1));
               v.add(world.get(i+length-1));
               v.add(world.get(i+length));
            }
            if (index > agentSetSize - length) { 
               v.add(world.get(i-length));
               v.add(world.get(i-length-1));
               v.add(world.get(i+1));
               v.add(world.get(i-length+1));
               v.add(world.get(i-1));
            }
         }
         if ((index < agentSetSize -length) && (index > length ) && (index%length != length-1) && (index%length != 0))
         { 
            v.add(world.get(i-length-1));
            v.add(world.get(i-length));
            v.add(world.get(i-length+1));
            v.add(world.get(i-1));
            v.add(world.get(i+1));
            v.add(world.get(i+length-1));
            v.add(world.get(i+length));
            v.add(world.get(i+length+1));
         }
         v.add(world.get(index));
         return v;
      }
   }
