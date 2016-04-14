/**
 * JavaGaussian.java
 *
 *
 * Created: Thu Aug 24 17:54:19 2000 revised 16/04/2004
 *
  * @author frederic.falempin ; denis.phan@enst-bretagne.fr
 * @version 1.2  august,5, 2002
 */

   package modulecoFramework.modeleco.randomeco;

   public class JavaGaussian extends java.util.Random implements CRandomDouble
   {    
      public JavaGaussian(long seed)
      {
         super(seed);
         //System.out.println("Je suis JavaGaussian! et mon long est : "+seed);
      }
   
      public JavaGaussian()
      {
         super();
         //System.out.println("Je suis JavaGaussian!");
      }
   
   
      public double getDouble()
      {
         return nextGaussian();
      }
   
      public String getGraphicEditor()
      {
         return "modulecoGUI.grapheco.randomeco.JavaGaussian";
      }
   } // JavaGaussian
