/**
 * Title:        VoisinageVonNeumanSight<p>
 * Description:  Chaque Agent a pour voisins NSEW, sur un tore.<p>
 * Hypothèse : Le monde est carré !
 * @author Antoine.Beugnard@enst-bretagne.fr
 * Created on may, 2000
 * @version 1.1  july,10, 2001
 */
   package modulecoFramework.modeleco.zone;

   import java.util.ArrayList;

   import modulecoFramework.modeleco.NeighbourSelector;

   public class NeighbourVonNeumanSight extends NeighbourSelector {
   
   /**
    * Choisit mes voisins sur un tore de longueur SIGHT.
    */
   
      protected int sight;
      int north;
      int west;
      int south;
      int east;
      int random;
      boolean versDroite;
   
      public NeighbourVonNeumanSight() {
         this(1);
      }
   
      public NeighbourVonNeumanSight(int sight) {
         this.sight = sight;
        //        System.out.println("************" + sight + "*********");
      }
   
      public ArrayList  compute(int index) { // Calcule le voisinage
         north = index;
         west = index;
         south = index;
         east = index;
        //        int nbTour=0;
      
         ArrayList v = new ArrayList(4*sight);
        //        for (int i=1;i<=4*sight;i++) {
        //            v.add(null);
        //        }
      
         for (int i=1;i<=sight;i++) {
            //            random = (Math.round((float) (4*sight*Math.random())))%(4*sight);
            //            if (v.get(random)!=null) {
            //                nbTour = 0;
            //               versDroite = (Math.round((float) (10*Math.random()))%2==1);
            //            }
            //            while (v.get(random)!=null && (nbTour<=4*sight)) {
            //                random = (random - 1) % (4*sight);
            //                nbTour++;
            //            }
         
            //            if (nbTour > 4*sight)
            //                System.out.println("PLUS DE PLACE DANS LA LISTE DES VOISINS !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            //            else {
            east = findEast(east);
            v.add(/*random,*/world.get(east)); // E
            //            }
         
         /*            random = (Math.round((float) (4*sight*Math.random())))%(4*sight);
            if (v.get(random)!=null) {
                nbTour = 0;
         //                versDroite = (Math.round((float) (10*Math.random()))%2==1);
            }
            while (v.get(random)!=null && (nbTour<=4*sight)) {
                random = (random - 1) % (4*sight);
                nbTour++;
            }
         
            if (nbTour > 4*sight)
                System.out.println("PLUS DE PLACE DANS LA LISTE DES VOISINS !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            else {
         */            south = findSouth(south);
            v.add(/*random,*/world.get(south)); // S
         /*            }
            random = (Math.round((float) (4*sight*Math.random())))%(4*sight);
            if (v.get(random)!=null) {
                nbTour = 0;
         //                versDroite = (Math.round((float) (10*Math.random()))%2==1);
            }
            while (v.get(random)!=null && (nbTour<=4*sight)) {
                random = (random - 1) % (4*sight);
                nbTour++;
            }
         
            if (nbTour > 4*sight)
                System.out.println("PLUS DE PLACE DANS LA LISTE DES VOISINS !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            else {
         */    west = findWest(west);
            v.add(/*random,*/world.get(west)); // W
         /*            }
            random = (Math.round((float) (4*sight*Math.random())))%(4*sight);
            if (v.get(random)!=null) {
                nbTour = 0;
         //                versDroite = (Math.round((float) (10*Math.random()))%2==1);
            }
            while (v.get(random)!=null && (nbTour<=4*sight)) {
                random = (random - 1) % (4*sight);
                nbTour++;
            }
         
            if (nbTour > 4*sight)
                System.out.println("PLUS DE PLACE DANS LA LISTE DES VOISINS !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            else {
         */                north = findNorth(north);
            v.add(/*random,*/world.get(north)); // N
         //            }
         }
      
         return v;
      }
   
      public void setSight(int sight) {
         this.sight = sight;
      }
   
      protected int findEast(int index) {
         return ((index%length == length -1) ? (index - length + 1) :(index+1)%agentSetSize);
      }
      protected int findSouth(int index) {
         return ((index >= agentSetSize-length)? index - agentSetSize + length :(index+length)%agentSetSize);
      }
   
      protected int findWest(int index) {
         return ((index%length == 0)? index + length -1 : (index+agentSetSize-1)%agentSetSize);
      }
      protected int findNorth(int index) {
         return ((index+agentSetSize-length)%agentSetSize);
      }
   
   
   }
