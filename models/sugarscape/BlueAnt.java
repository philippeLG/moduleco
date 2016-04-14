/*
 * BlueAnt.java
 *
 * Created on 17 mai 2001, 10:10
* @version 1.1  july,10, 2001
* @version 1.4. february 2004 
 */

   package models.sugarscape;

// import modulecoFramework.modeleco.mobility.MobileAgent;
// import modulecoFramework.modeleco.mobility.EPlace;
/**
 *
 */
   public class BlueAnt extends Ant {
   
    /** Creates new BlueAnt */
      public BlueAnt(Place p) {
         super(p);
      }
   
      public boolean isBlue() {
         return true;
      }
   
      public String toString()
      {
         return "I'm blue !";
      }
   
   }
