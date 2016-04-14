/**
 * JavaRandom.java
 *
 *
 * Created: Thu Aug 24 17:54:19 2000 revised 16/04/2004
 *
  * @author denis.phan@enst-bretagne.fr
 * @version 1.2  august,5, 2002
 */

   package modulecoFramework.modeleco.randomeco;

   public class IdenticalAgents implements CRandomDouble
   {    
      public IdenticalAgents() {
      }
      public IdenticalAgents(long l){
      
      }
   
      public IdenticalAgents(long seed, double s){
      
      }
   
      public double getDouble()
      {
         return 0.0; 
      }
   
      public void setSeed(double d){
         System.out.println("Def-setSeed "+d);
      
      }   
   } // Default
