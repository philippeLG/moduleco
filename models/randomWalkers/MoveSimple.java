
/**
 * Class randomWalkers.Movesimple.java
 *
 *
 * Created: Sat Apr 21 17:45:01 2001
 *
 * @author Jerome Schapman
 * @version 1.2  august,5, 2002
* @version 1.4. february 2004   
 */

   package models.randomWalkers;

// import java.util.Iterator;
   import java.util.ArrayList;

   import modulecoFramework.modeleco.mobility.MobileAgent;
// import modulecoFramework.modeleco.mobility.EPlace;
   import modulecoFramework.modeleco.mobility.EMobileWorld;
   import modulecoFramework.modeleco.mobility.Move;
   import modulecoFramework.modeleco.zone.NeighbourVonNeuman;

// import modulecoFramework.modeleco.exceptions.AlreadyUsedPlaceException;

   public class MoveSimple extends Move
   {
   
      protected static NeighbourVonNeuman neighbourhood = new NeighbourVonNeuman();
      protected ArrayList neighbours;
      protected Place p, destination;
      protected boolean initialized=false;
   
      protected ArrayList a = new ArrayList(4);
      protected Place temporaryPlace;
      protected int compteur;
   
   
   
      public MoveSimple()
      {
      }
   
   
      public void move(MobileAgent mobileagent, EMobileWorld world)
      {
         if (!initialized)
         {
            neighbourhood.setWorld(world);
            initialized = true;
         }
      
         p = (Place) mobileagent.getPlace();
        //        System.out.println("traitement de l'Ant sur la case : " + p.getAgentID());
      
         neighbours = neighbourhood.compute(p.getAgentID());
      
         compteur = 0;
         int i=0;
      
         a.clear();
      
         while (i<4) {
            temporaryPlace = ((Place) neighbours.get(i));
            if (!temporaryPlace.isThereFuturAnt()){
               a.add(temporaryPlace);
               compteur++;
            }
            i++;
         }
      
        //        System.out.println("Le compteur est : " + compteur);
      
         int tmp = ((int) (100*java.lang.Math.random())) % compteur;
      
        //        System.out.println("Le tmp est : " + tmp);
      
         destination = (Place) a.get(tmp);
         ((Ant) p.getAgent()).futurGo(destination);
      }
   }



// Movesimple
