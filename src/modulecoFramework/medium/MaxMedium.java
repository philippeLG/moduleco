/**
 * MeanMedium.java
 *@author Antoine.Beugnard@enst-bretagne.fr
 * Created on april 5 2001, 14:15
* @version 1.2  august,5, 2002
 */
   package modulecoFramework.medium;

/**
 * 
 *** send(double) 
 *** double get()
  */
   public class MaxMedium extends Medium {
   
    /** Creates new MeanMedium */
      public MaxMedium() {
      }
   
      public void send(double d) {
         max = Math.max(max, d);
      }
   
      public double get() {
         return max;
      }
   
      protected double max;
   }
