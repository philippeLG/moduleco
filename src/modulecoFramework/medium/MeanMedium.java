/**
 * MeanMedium.java
 *@author Antoine.Beugnard@enst-bretagne.fr
 * Created on april 2001, 14:15
* @version 1.2  august,5, 2002
*/
   package modulecoFramework.medium;

   import java.util.ArrayList;

   import modulecoFramework.modeleco.CAgent;

   public class MeanMedium extends Medium {
   
    /** Creates new MeanMedium */
      public MeanMedium() {
      }
   
      public void send(double d, CAgent a) {
         data.set(((ArrayList) refTable.get("neighbour")).indexOf(a), new Double(d));
      }
   
      public double get() {
         double t = 0.0D;
         int n = data.size();
         for (int i = 0; i < n; i++) {
            t += ((Double) data.get(i)).doubleValue();
         }
         return t/n;
      }
   }
