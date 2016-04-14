/**
 * RandomMove.java
 *
 * Created: Thu Sep 14 15:58:01 2000
 * Copyright:    Copyright (c)enst-bretagne
 * @author frederic.falempin@enst-bretagne.fr
 * @version 1.1  july,10, 2001
 */

   package modulecoFramework.modeleco.mobility.move;

   import modulecoFramework.modeleco.mobility.MobileAgent;
   import modulecoFramework.modeleco.mobility.EPlace;
   import modulecoFramework.modeleco.mobility.EMobileWorld;
   import modulecoFramework.modeleco.mobility.Move;

   public class RandomMove extends Move {
      public RandomMove()
      {
      }
   
      public void move(MobileAgent agent, EMobileWorld world)
      {
         int freeAgentIndex;
         EPlace target;
      
      
         for (int iterations=0; iterations < (world.getAgentSetSize() -1); iterations++)
         {
            freeAgentIndex = Math.round((float) (Math.random() * (world.getAgentSetSize()-1)));
         
            target = ((EPlace) world.get(freeAgentIndex));
         
            if (target.getFutureState() == EPlace.nobodyHere)
            {
               if (agent.canAccept(target))
               {
                  agent.go(target);
                  return;
               }
            }
            else if ((target.getActualState() == EPlace.nobodyHere) && target.getFutureState() == EPlace.toBeComputed)
            {
               if (agent.canAccept(target))
               {
                  agent.go(target);
                  return;
               }
            }
         }
      }
   } // RandomMove
