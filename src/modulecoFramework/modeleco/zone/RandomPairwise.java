/**
 * Title:       RandomPairwise <p>
 * Description: The world is randomly divised in random pairs (no intersections)
 * @author philippe.legoff@enst-bretagne.fr, optimized by antoine.beugnard@enst-bretagne.fr
 * Created on aout, 23, 2000
 * @version 1.2  august,5, 2002 ;
 */
   package modulecoFramework.modeleco.zone; // Level 4 Class

   import java.util.ArrayList;
// import java.util.Iterator;

   import modulecoFramework.modeleco.EWorld;
   import modulecoFramework.modeleco.NeighbourSelector;
//   import modulecoGUI.grapheco.EWarningDialog;
   import modulecoFramework.modeleco.ENeighbourRandomPairwiseWorld;

   public class RandomPairwise extends NeighbourSelector {
      ArrayList neighbours;
      EWorld ew;
   /**
    * On redéfinit cette méthode de NeighbourSelector parce qu'on veut vérifier que la taille du monde est paire.
    */
   
      public void setWorld(EWorld ew) {
      		/*
      		* method setWorld(EWorld ew) invoked by EWorld Constructor
      		*/
         this.ew = ew; 
      
         //madeNewEworldClone(); // NE PAS METTRE ICI SINON L'OBJET EST CREE A CHAQUE INSTANCE
         super.setWorld(ew);
      }
   
    /**
     * Choose randomly one neighbour (that belongs to any neighborhood) + itself <p>
     */
      public ArrayList compute(int index) {
         neighbours = new ArrayList(1);
         try{
            neighbours=((ENeighbourRandomPairwiseWorld) ew).updateNeighbourhood(neighbours ,index);
         }
         
            catch (java.lang.ClassCastException e){
               System.out.println("Neighbourood : RandomPairwise need a ENeighbourRandomPairwiseWorld type");
            }
         return neighbours;
      }
   
   }
