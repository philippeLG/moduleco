/**
 * Title:        NeighbourSelector<p>
 * Description:  Je suis une classe abstraite, racine du pattern Stratégie. Je choisis
 * pour un world la zone qui va évoluer. Le calcul est effectué par les méthodes compute.<p>
 * Les méthodes compute() et compute(int) doivent être redéfinies.<p>
 * Sous classes concrètes connues sont dans le package @see{modeleco.zone}. <p>
 * @author Antoine.Beugnard@enst-bretagne.fr
 *@version 1.2  august,5, 2002
 */
   package modulecoFramework.modeleco;// Level 3 Class

   import java.util.ArrayList;
   import java.io.Serializable;

   public abstract class NeighbourSelector extends ZoneSelector implements Serializable {
   
      protected int pos = 0;
   
   // Calcule une zone du monde
      public ArrayList compute() {
         ArrayList v;
         v = compute(pos);
         pos= (pos+1)%agentSetSize; // glissement de la zone
         return v;
      }
   }
