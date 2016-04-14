/**
 * Title:        Modeleco<p>
 * Description:  Je construit pour un world le voisinage de chaque EAgent. Le calcul du
 * voisinage est effectu� par un @see[modeleco.ZoneSelector}.<p>
 * Remarque : Attention � la complexit� spatiale de la description du voisinage. World Zone O(1),
 * Voisinage4 et Voisinage de VonNeuman (4n), RandomIndividual (n), Random O(n2) avec des risques
 * de OutOfMemory Errors...d'o� le BoundedRandom en O(bn).<p>
 * Le voisinage est calcul�e une fois pour toute � la cr�ation du monde...Mais des extensions sont possibles
 * voir par exemple : @see{modeleco.AgentMobile}<p>
 * @author Antoine.Beugnard@enst-bretagne.fr
 * created on may 2000
* @version 1.2  august,5, 2002
 */
   package modulecoFramework.modeleco;

// import java.util.Iterator;
// import modulecoFramework.modeleco.zone.*;
   import java.io.Serializable;

   public class NeighbourBuilder implements Serializable {
   
   /**
   * Le monde dans lequel une zone sera choisie.
   */
      protected EWorld world;
   
   /**
   * Je choisis un voisinage � faire �voluer en fonction du contenu de selector.
   */
      protected ZoneSelector selector;
   
   /**
   * On d�finit le monde.
   */
      public void setWorld(EWorld ew) {
         world = ew;
      }
   
   /**
   * On d�finit la mani�re de calculer le voisinage.
   */
      public void setSelector(ZoneSelector zs) {
         selector = zs;
      }
   
   /**
   * On d�clenche le calcul de tous les voisinages.
   */
      public void initialize() {
         for (int i=0; i< world.size(); i++) {
           // ((CAgent)world.get(i)).setVoisins(selector.compute(i));
         }
         System.out.println("NeighbourBuilder.initialize()");
      }
   }
