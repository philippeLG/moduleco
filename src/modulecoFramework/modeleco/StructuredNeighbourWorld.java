 /**
 * Title:        StructuredNeighbourWorld<p>
 * Description: StructuredNeighbourWorld is a reorganisation (deterministic or at random)
 * of the network structure such as Small World or Random Pairwise
 * Known implementors:
 *  @see{modeleco.ENeighbourSmallWorld}
 * @see{modeleco.ENeighbourRandomPairwiseWorld}
 * @author Denis.Phan@enst-bretagne.fr
 *
 * @version 1.2  august,5, 2002
 */
   package modulecoFramework.modeleco;

   import java.util.ArrayList; 


   public interface StructuredNeighbourWorld
   {
      public void rebuildLinks();
   
      public void buildRandomLinks();
   
      public void setSeed(long d);
      public void setRandom_s(String s);
      public ArrayList getDescriptors();
      public void RandomGeneratorsetDefaultValues();
   }
