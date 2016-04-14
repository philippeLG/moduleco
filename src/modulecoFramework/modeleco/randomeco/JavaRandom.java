/**
 * JavaRandom.java
 *
 *
 * Created: Thu Aug 24 17:54:19 2000 revised 16/04/2004
 *
  * @author frederic.falempin ; denis.phan@enst-bretagne.fr
 * @version 1.2  august,5, 2002
 */

   package modulecoFramework.modeleco.randomeco;

   public class JavaRandom extends java.util.Random 
   implements CRandomDouble, CRandomInt
   
   {
   
      public JavaRandom(long seed) {
         super(seed);
         //System.out.println("Je suis JavaRandom! et mon long est : "+seed);
      }
      public JavaRandom(){
         super();
         //System.out.println("Je suis JavaRandom!");
      }
      public double getDouble(){
         return nextDouble();
      }
      public int getInt(int upperLimit){
         return nextInt(upperLimit);
      }
      public void setSeed(double d){
         System.out.println("Jav-setSeed "+d);
      } 
   } // JavaRandom
