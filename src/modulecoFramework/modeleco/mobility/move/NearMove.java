/**
 * NearMove.java
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

   public class NearMove extends Move 
   {
      public NearMove()
      {
      }
   
      public void move(MobileAgent agent, EMobileWorld world)
      {
         int freeAgentIndex = 0;
         int size = (new Double(Math.sqrt(world.getAgentSetSize()))).intValue();
         EPlace target;
      
      // The following code is completly empiric
      // It seems to work, and I assume this
      // You can understand now why I can't explain anything.
      
      // The algorithm is thought to search a good agent for a guy,
      // in the nearest place he can find (around the position of the guy).
      
         for (int widthMove=1; widthMove < size;  widthMove++)
            for (int empiric=0; empiric < 4; empiric++)
               for (int count=0; count < widthMove; count++)
               {
                  if (empiric == 0)
                     freeAgentIndex--;
                  if (empiric == 1)
                     freeAgentIndex = (freeAgentIndex - size)% world.getAgentSetSize();
                  if (empiric == 2)
                     freeAgentIndex = (freeAgentIndex + 1) % world.getAgentSetSize();
                  if (empiric == 3)
                     freeAgentIndex = (freeAgentIndex + size) % world.getAgentSetSize();
               
               
                  if (freeAgentIndex < 0)
                     freeAgentIndex = world.getAgentSetSize() - freeAgentIndex;
                  if (freeAgentIndex > world.getAgentSetSize())
                     freeAgentIndex = freeAgentIndex - world.getAgentSetSize() ;
               
                  target = ((EPlace) world.get(freeAgentIndex));
               
                  if (target.getFutureState() == EPlace.nobodyHere)
                  {
                     if (agent.canAccept(target))
                     {
                        agent.go(target);
                        return;
                     }
                  }
                  else if ((target.getActualState() == EPlace.nobodyHere) && (target.getFutureState() == EPlace.toBeComputed))
                  {
                     if (agent.canAccept(target))
                     {
                        agent.go(target);
                        return;
                     }
                  }
               }
      }
   } // NearMove
