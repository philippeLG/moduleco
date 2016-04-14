/**
 * NeighbourMedium.java
 *
 * I am a degenerated version of medium. I give acces to a list of agents connected to me.
 * I do not compute anything...
 *@author Antoine.Beugnard@enst-bretagne.fr
 * Created on april 2001, 14:15
 * @version 1.2  august,5, 2002
 */
   package modulecoFramework.medium;

   import java.util.ArrayList;

/**
 * This Class Create an Array List whith a given Neighbourhood for each agent
 * @see modulecoFramework.modeleco.ENeighbourWorld
 */
   public class NeighbourMedium extends Medium {
   
    /** Creates new MeanMedium */
      public NeighbourMedium() {
         //System.out.println("NeighbourMedium()");
      }
   
      public ArrayList getNeighbours() {
         ArrayList n;
         n = (ArrayList) refTable.get("neighbour");
         if (n == null) {
            return new ArrayList();
         }
         else 
            return n;
      }
   }
