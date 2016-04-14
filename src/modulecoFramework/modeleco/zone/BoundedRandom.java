/**
 * Title:        BoundedRandom<p>
 * Description:  Un nombre aléatoire borné d'individus est sélectionné. (Les occurences multiples sont acceptées.)<p>
 * @author Antoine.Beugnard@enst-bretagne.fr
 * created  mai 2000
* @version 1.1  july,10, 2001
 */
   package modulecoFramework.modeleco.zone;

   import java.util.ArrayList;
   import modulecoFramework.modeleco.ZoneSelector;
   import modulecoGUI.grapheco.EDialog;

   public class BoundedRandom extends ZoneSelector {
   
   /**
   * Le nombre par défaut de voisins maximum prix au hasard.
   */
      protected int bound = 10; // max - 1
   
   /**
   * Demande interactivement à la création la borne !.
   */
      public BoundedRandom() {
         super();
      // ask for the bound
         EDialog ed = new EDialog("Borne ?", "entre 1 et la taille du monde", "10");
         ed.setVisible(true);
         if (ed.getValue() != null) {
            bound = ((Integer) new Integer(ed.getValue())).intValue();
         }
      }
   
   /**
   * Fixe la borne maximale de voisins.
   */
      public void setBound(int b) {
         bound = b;}
   
   /**
   * Choisit bound voisins maximum au hasard. Les occurences multiples sont acceptées.
   */
      public ArrayList  compute() {// Calcule une zone
         int taille = (int) (Math.random() * bound ) + 1;
         ArrayList v = new ArrayList(taille);
         for (int i = 0; i < taille ; i++) {
            v.add(i, world.get((int) (Math.random() * agentSetSize)));
         }
         return v;
      }
   
   /**
   * Choisit bound voisins maximum au hasard. Les occurences multiples sont acceptées.
   */
      public ArrayList  compute(int index) {// Calcule un voisinage
         int taille = (int) (Math.random() * bound ) + 1;
         ArrayList v = new ArrayList(taille);
         for (int i = 0; i < taille ; i++) {
            v.add(i, world.get((int) (Math.random() * agentSetSize)));
         }
         return v;
      }
   }
