
/**
 * SugarPosition.java
 *
 *
 * Created on 11 mai 2001, 11:37
 *
 * @author Jerome Schapman
 * @version 1.2  august,5, 2002  
 */

   package models.sugarscape;

   import modulecoFramework.modeleco.mobility.EMobileWorld;

   public abstract class SugarPosition
   {
      protected EMobileWorld world;
   
      public SugarPosition(EMobileWorld world) {
         this.world = world;
      }
   
      public void setWorld(EMobileWorld world) {
         this.world = world;
      }
   
      public abstract void init(EMobileWorld world);
   }
