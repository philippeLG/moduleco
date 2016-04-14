/**
 * JavaLogistic.java
 *
 *
 * Created: Thu Aug 24 17:54:19 2000
 *
  * @author Xiao.Xu@enst-bretagne.fr
 * @version 1.2  august,5, 2002
 */
   package modulecoFramework.modeleco.randomeco;
// import java.util.Random;
   import java.lang.Math; // REVOIR ?

   public class JavaTriangular extends RandomSD 
   implements modulecoFramework.modeleco.randomeco.CRandomDouble
   {
   /**
   * sd is the standard deviation of the logistic distribution
   */
      double sd;
      java.util.Random random, random2 ;
   /**
   * Default constructor which set the standard deviation at 1.
   */
   
   
      public JavaTriangular(long seed){
      
         this(seed, 1); //0.1
      }
   
   /**
   * Constructor which set the standard deviation at s.
   * @param s is the wanted standard deviation.
   */
   
      public JavaTriangular(long seed, double s){
         super(seed, s);
         //System.out.println("JavaLogistic seed = "+seed+ " - Standard deviation = "+s);
         sd = s;
         //random = new java.util.Random() ;
      }
   	/**
   	* It uses the default random of Java
   	* @return a random double selected with the logistic distribution 
   	*/
      public double getDouble()
      {	
         double u,out;
      
         do{
            u = nextDouble();  //Math.random();
            //System.out.println(u);
         }
         while(u==0 || u==1);
         double b=Math.sqrt(2);
         out=2*b-3*b*Math.sqrt(u);
         return out;
      }
   
   	/**
   	* It uses a random distribution which is an Object implementing Random.
   	* For the moment, it is the random of Java but it can be replaced by any
   	* uniform distribution if this one is defined as a Java Object implementing Random.
   	* Therefore just change the definition of <tt>uniform</tt> at the first line. 
   	* @return a random double selected with the logistic distribution.
   	*/
   
      public double getDouble2() // VERFIFIER
      
      {	
         JavaRandom uniform=new JavaRandom();// replace JavaRandom with the wanted uniform generator.
         double u,out;
      
         do{
            u=uniform.getDouble(); 
         }
         while(u==0 || u==1);
         out=sd*Math.log(u/(1-u));
         return out;
      }
   
   	/**
   	* This function sets the standard deviation at the given value.
   	* @param d is the new standard deviation.
   	*/
      public void setSeed(double d){
         sd = d;
      } 
   }