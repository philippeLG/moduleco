/**
 * Title:        NeighbourSelector<p>
 * Description:  Je suis une classe abstraite, racine du pattern Strat�gie. Je choisis
 * pour un world la zone qui va �voluer. Le calcul est effectu� par les m�thodes compute.<p>
 * Les m�thodes compute() et compute(int) doivent �tre red�finies.<p>
 * Sous classes concr�tes connues sont dans le package @see{modeleco.zone}. <p>
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
