/**
 * Title:        Random<p>
 * Description:  Un nombre aléatoire d'individus est sélectionné... doublons acceptés.<p>
 * @author Antoine.Beugnard@enst-bretagne.fr
 * Created on may, 2000
 * @version 1.1  july,10, 2001
 */
   package modulecoFramework.modeleco.zone;

   import java.util.ArrayList;

   import modulecoFramework.modeleco.ZoneSelector;

   public class Random extends ZoneSelector {
   
   /**
   * Choisit des voisins au hasard (de 1 à taille du monde.) Les occurences multiples sont acceptées.
   */
   
      public ArrayList  compute() {// Calcule une zone
         int taille = (int) (Math.random() * agentSetSize ) + 1;
         ArrayList v = new ArrayList(taille);
      /*   for (int i = 0; i < taille ; i++) {//taille
            v.add(i, world.get((int) (Math.random() * ws)));
         }*/
         return v;
      }
   
   
   /**
   * Choisit des voisins au hasard (de 1 à taille du monde.) Les occurences multiples sont acceptées.
   */
      public ArrayList  compute(int index) {// Calcule un voisinage
      // taille de la zone aleatoire...sauf 0
      // on accepte que le même objet soit sélectionné plusieurs fois
         int bound=2;
         int taille = (int) (Math.random() * bound ); //+ 1; //ws
         System.out.println(taille);
         ArrayList v = new ArrayList(taille);
         for (int i = 0; i < taille ; i++) {
            int temp = (int) (Math.random() * agentSetSize);
            v.add(i, world.get(temp));
            System.out.print(" ; "+temp);
         }
         return v;
      }
   }
