
/**
 * Default.java
 *
 *
 * Created: Thu Aug 24 17:50:48 2000
 *
 * @author frederic.falempin@enst-bretagne.fr
 *@version 1.2  august,5, 2002
 */

   package modulecoFramework.modeleco.randomeco;

   public class Default implements CRandomDouble 
   {    
      public Default() 
      {
         //System.out.println("Je suis Default!");
      }
      public Default(long l) 
      
      {
         //System.out.println("Je suis Default! et mon long est : "+l);
      }
      public double getDouble(){
         return java.lang.Math.random();
      }
   
      public void setSeed(double d){
         System.out.println("Def-setSeed "+d);
      
      }   
   } // Default
