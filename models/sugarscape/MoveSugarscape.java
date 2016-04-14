
/**
 * MoveSugarscape.java
 *
 *
 * Created: Sat Apr 21 17:45:01 2001
 *
 * @author Jerome Schapman
 * @version 1.2  august,5, 2002  
 */

   package models.sugarscape;

   import java.util.Iterator;
   import java.util.ArrayList;

   import modulecoFramework.modeleco.mobility.MobileAgent;
// import modulecoFramework.modeleco.mobility.EPlace;
   import modulecoFramework.modeleco.mobility.EMobileWorld;
   import modulecoFramework.modeleco.mobility.Move;
   import modulecoFramework.modeleco.zone.*;
   import modulecoFramework.modeleco.exceptions.AlreadyUsedPlaceException;

   public class MoveSugarscape extends Move
   {
   
      protected NeighbourVonNeumanSight neighbourhood = new NeighbourVonNeumanSight();
      protected ArrayList neighbours;
      protected Place p, placeCourante, destination;
      protected int quantity=0;
      protected int max;
      protected int random;
      protected boolean antHaveToMove;
      protected boolean initialized=false;
   
   
      public MoveSugarscape()
      {
      }
   
   /*    public MoveSugarscape(EMobileWorld world)
    {
        this();
        neighbourhood.setWorld(world);
    }
   */  
      public void move(MobileAgent mobileagent, EMobileWorld world)
      {
        //		Cette méthode vérifie pour chaque cellule appartenant au voisinage de l'agent courant (dans la ligne de vue de la Ant suivant un modèle de Von Neumann) :
        //		- Occupé ou non
      
      
         if (!initialized)
         {
            neighbourhood.setWorld(world);
            initialized = true;
            //System.out.println("Init ...");
         }
      //        System.out.println("  Dans MoveSugarscape ... ");
         p = (Place) mobileagent.getPlace();
         antHaveToMove=false;
         max=p.getProduction();
      //       System.out.println("***********************place " + p.getAgentID() + "computee*******");
      
         if(p.isThereAnt())
            neighbourhood.setSight(((Ant) p.getAgent()).getSight());
      
         neighbours = neighbourhood.compute(p.getAgentID());
      
         for (Iterator i = neighbours.iterator(); i.hasNext();)
         
         {
            placeCourante = ((Place) i.next());
         
            if (!(placeCourante.isThereAnt()))
            {
            
                //			- Si libre alors, quantité de sucre dans cette cellule
            
               quantity=placeCourante.getProduction();
            
                //			- Si la quantité est supérieure à celle déjà trouvée
                //                      on garde cette nouvelle valeur et l'index.
            
               if  (max < quantity) {
                  max = quantity;
                  destination = placeCourante;
                  antHaveToMove = true;
               } 
               else if  (max == quantity) {
                    //			- Sinon si la quantité trouvée est égale à celle déjà trouvée, on tire au hazard quelle cellule	sera choisie pour le déplacement (ou pour rester sur place)
               /*         if ((max==0)&& !antHaveToMove) {
                        destination = placeCourante;
                        antHaveToMove = true;
                    } else {
                        random = Math.round((float) (10*Math.random()));
                    */    
                  if (random%2 == 1)
                  {
                     destination = placeCourante;
                     antHaveToMove = true;
                  } 
                  else{}
               } 
               else {
                    //				on laisse comme c'est
               }
            
            }
         }
        //	- Pour finir, on affecte la Ant de la cellule courante à la nouvelle cellule et on libère la place.
         if (antHaveToMove) {
            try {
               int tmp = neighbours.indexOf(destination);
               tmp = tmp % 4;
               destination = (Place) neighbours.get(tmp);
            
               destination.receive(mobileagent);
               p.leave();
            //                System.out.println("Agent a bougé ...");
            } 
               catch (AlreadyUsedPlaceException e) {
               //   System.out.println("Place deja occupée...");
               }
         }
      }
   
   
   } // MoveSugarscape
