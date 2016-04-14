/**
 * centerSugarPosition.java
 *
 * Created on 11 mai 2001, 13:36
 *
 *
 * @author  Jérôme Schapman
 * @version 1.2  august,5, 2002  
 */

   package models.sugarscape.sugarPosition;

   import java.util.ArrayList;
   import java.util.Iterator;

   import models.sugarscape.SugarPosition;
   import models.sugarscape.Place;

   import modulecoFramework.modeleco.zone.NeighbourVonNeuman;
   import modulecoFramework.modeleco.mobility.EMobileWorld;


   public class CenterSugarPosition extends SugarPosition {
      protected NeighbourVonNeuman neighbourhood = new NeighbourVonNeuman();
      protected int sight,sugarQuantity;
      protected Place placeCourante;
      protected ArrayList neighbours;
      protected boolean initialized = false;
   
      public CenterSugarPosition() {
         this(null);
      }
   
      public CenterSugarPosition(EMobileWorld world) {
         super(world);
      }
   
      public void setWorld(EMobileWorld world) {
         super.setWorld(world);
      }
   
      public void init(EMobileWorld world) {
      
         setWorld(world);
         sight = Math.round((float) (10*Math.random()));
      //        neighbourhood.setSight(sight);
         neighbours = neighbourhood.compute(world.getAgentSetSize()/2);
      
         sugarQuantity = 10;
         placeCourante.setProduction(sugarQuantity);
         sugarQuantity -= 1;
      
         int t=0;
      
         for (Iterator i = neighbours.iterator(); i.hasNext();) {
            placeCourante = ((Place) i.next());
            if (t<=3)
               t++;
            else {
               sugarQuantity -= 1;
               t=0;
            }
            if (sugarQuantity>0)
               placeCourante.setProduction(sugarQuantity);
            else
               break;
         }
      
        /*
        if (random.getDouble() <= sugarQuantity){
            produceMax = (int) (10 * random.getDouble());
        }
        production = produceMax;
         */
      
      }
   
   }
