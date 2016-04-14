/**
 * RandomSD.java
 *
 *
 * Created: Thu Aug 24 17:40:50 2000
 *
 * @author frederic.falempin@enst-bretagne.fr
 * @version 1.2  august,5, 2002
 */

   package modulecoFramework.modeleco.randomeco;
// import java.util.Random;

   public class RandomSD extends java.util.Random 
   {
   
   /**
   * sd is the standard deviation
   */
      double sd;
   /**
   * Default constructor which set the standard deviation at 1.
   */
   /*
      public RandomSD(){
      
         sd=1;
      }
   /**
   * Constructor which set the standard deviation at s.
   * @param s is the wanted standard deviation.
   */
   
      public RandomSD(long seed){
      
         this(seed, 1); //0.1
      }
   
      public RandomSD(long seed, double s){
         super(seed);
         //System.out.println("RandomSD seed = "+seed+ " - Standard deviation = "+s);
         sd = s;
      }
   
   
      public double getDouble()
      {	
         return nextDouble();//Math.random(); // REVOIR
      }
   
   }

