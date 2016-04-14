/*
 * ReproductionRule.java
 *
 * Created on 17 mai 2001, 13:28
 * @version 1.2  august,5, 2002
* @version 1.4. february 2004   
 */

   package models.sugarscape.rule;

// import java.util.Iterator;
   import java.util.ArrayList;

   import models.sugarscape.Rule;
   import models.sugarscape.Ant;

   import modulecoFramework.medium.NeighbourMedium;
/**
 * This Class Creates new Reproduction Rules
 */
   public class ReproductionRule extends Rule {
   
    /** Creates new ReproductionRule */
      public ReproductionRule() {
      }
   
      public void rule (Ant agent) {
      
         int random = (Math.round((float) (8*Math.random())))%(8);
         ArrayList v = new ArrayList(8);
         int nbTour = 0;
      
      /*     for (int i=1;i<=8;i++) {
            v.add(null);
        }*/
      
         v =((NeighbourMedium)place.getMediums()[0]).getNeighbours();//complete DP 08/07/2001
         if (place.doILiveInTheGoodPlace()==new Boolean(true)) {
         
         }
      }
   }
