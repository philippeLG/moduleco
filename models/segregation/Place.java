/** class segregation.Place.java
 * Title:        Moduleco<p>
 * Description:  I am an EAgent. I represent an habitation, and not an habitant. My state depends on the habitant who lives in me. There are three states: a blue habitant, a red habitant, and no habitant.
 * Copyright:    Copyright (c)enst-bretagne
 * @author 
 * @version 1.2  august,5, 2002  
 */

   package models.segregation;

   import java.awt.Color ; // DP 11/09/2001
// import modulecoFramework.modeleco.mobility.MobileAgent;
   import modulecoFramework.modeleco.mobility.EPlace;
   import modulecoFramework.modeleco.randomeco.CRandomDouble;

// import modulecoFramework.medium.NeighbourMedium;

   import java.util.Iterator;
   import java.util.ArrayList;

   import modulecoGUI.grapheco.descriptor.InfoDescriptor; // DP 11/09/2001

   public class Place extends EPlace {
   
    /**
     * The proportion of different (different color) neighbours the habitant
     * finds acceptable to continue to live in a given place
     */
      protected double acceptation;
   
    /**
     * The proportion of precalculated agents against random agents
     */
      protected double randomize;
   
    /**
     * The proportion of free agent (with no habitant) against occupated agent
     */
      protected double freeAgentsProportion;
   
    /**
     * The proportion of Red against Blue occupated agent
     */
      protected double redBlueProportion;
   
      protected static int blueHere = 3;
      protected static int redHere = 4;
      protected int preferedState;
   
      protected CRandomDouble random;
   
      public void init() {
         super.init(); // state and neighbourhood management
         randomize = ((World) world).randomize;
         freeAgentsProportion = ((World) world).freeAgentsProportion;
         redBlueProportion = ((World) world).redBlueProportion;
      
         this.setActualState(nobodyHere);
         //setOldDistribution(0);
         //System.out.println("segregation.place.init()");
      
      }
   
      public void setOldDistribution(int aID){
         this.setPlaceID(aID);
         if (random.getDouble() <= randomize) {
            this.setActualState(somebodyHere);
            if (this.agentID % 2 == 1) //(((World) world).getIndex(this) % 2 == 1)
            {
               agent = new RedAgent(this);
               this.setActualState(redHere);
            }
            else {
               agent = new BlueAgent(this);
               this.setActualState(blueHere);
            }
         }
         else {
            if (random.getDouble() > freeAgentsProportion) {
               actualState = somebodyHere;
               if (random.getDouble() <= redBlueProportion) {
                  agent = new RedAgent(this);
                  this.setActualState(redHere);
               }
               else {
                  agent = new BlueAgent(this);
                  this.setActualState(blueHere);
               }
            }
         }
         if(agent != null)
            agent.setAgentID(aID);
      }
     // DP 11/09/2001 BEGIN
      public void setIntitialPeopleColor(String peopleColor, int aID){
         this.setPlaceID(aID);
         //System.out.println("segregation.Place.setIntitialPeopleColor = "+aID+" ; placeID = "+placeID);
         if (peopleColor == "void"){
            this.setActualState(nobodyHere);
         }
      
         if (peopleColor == "red"){
            this.setActualState(somebodyHere);
            agent = new RedAgent(this);
            agent.setAgentID(aID);
            this.setActualState(redHere);
         }
         if (peopleColor == "blue"){
            this.setActualState(somebodyHere);
            agent = new BlueAgent(this);
            agent.setAgentID(aID);
            this.setActualState(blueHere);
         }
      }
   // DP 11/09/2001 END
   
      public Object getState() {
         if (agent != null)
            return new Boolean(((SegregationAgent) agent).isBlue());
         return null ;//new Boolean(false);
      }
   
      public int getActualState() {
         if (agent != null) {
            if (((SegregationAgent) agent).isBlue()) {
               return blueHere;
            }
            else {
               return redHere;
            }
         }
         return nobodyHere;
      }
   // DP 11/09/2001 BEGIN
      public String getActualStateStringForm() {
         if (agent != null) {
            if (((SegregationAgent) agent).isBlue()) {
               return "blue agent here";
            }
            else {
               return "red agent here";
            }
         }
         return "nobody here";
      }
   
      public Color getActualColor() {
         if (agent != null) {
            if (((SegregationAgent) agent).isBlue()) {
               return Color.blue;
            }
            else {
               return Color.red;
            }
         }
         return Color.black;
      }
   // DP 11/09/2001 END
      public int getPreferedState() {
         return preferedState;
      }
   
      public void setPreferedState(int newState) {
         preferedState = newState;
      }
   
      public String toString() {
         return "segregation..Place."+agentID; //(new Integer(actualState)).toString();
      }
   
    /*public void inverseState()
    {
        if (ae != null)
            ae.update ();
    }*/
    //   protected boolean canAccept(int wantedState)
   
      public void computePreferedState() {
         int numberOfNeighbours = 0;
         int numberOfBlueNeighbours = 0;
         int numberOfRedNeighbours = 0;
         EPlace neighbour;
         int stateOfNeighbour;
      
         for (Iterator i = neighbours.iterator(); i.hasNext(); ) {
            neighbour = ((EPlace) i.next());
            numberOfNeighbours++;
            stateOfNeighbour= neighbour.getActualState();
         
            if (stateOfNeighbour != EPlace.nobodyHere) {
               if (((SegregationAgent) neighbour.getAgent()).isBlue()) {
                  numberOfBlueNeighbours++;
               }
               else {
                  numberOfRedNeighbours++;
               }
            }
         }
      
         if (numberOfNeighbours == 0) // To avoid dividing by by zero...
            preferedState = nobodyHere;
      
         if (((double)numberOfRedNeighbours)/((double)numberOfNeighbours) <= acceptation)
            preferedState = blueHere;
         else
            if (((double)numberOfBlueNeighbours)/((double)numberOfNeighbours) <= acceptation)
               preferedState = redHere;
            else
               preferedState = nobodyHere;
      }
   
      public Boolean doILiveInTheGoodPlace() // Return false if the habitant
      // will have to move next turn
      {
         if (agent != null) {
            return ((SegregationAgent)agent).doILiveInTheGoodPlace();
         }
         else {
            return new Boolean(false);
         }
      }
   
    /*public boolean canAccept(int wantedState)
    {
        return (wantedState == preferedState);
    }*/
   
   
      public void compute() {
         computePreferedState();
      
         if ((actualState != nobodyHere) && (actualState != toBeComputed))
            if (!(doILiveInTheGoodPlace().booleanValue())) {
                // My habitant have to move.
               futureState = actualState; // If the move fails, the habitant stays
               agent.move(); // Trying to move
            }
            else // Habitant live in the good place
            {
                // Oh brave new world (i.e. nothing to do, it is a good place for the habitant)
               futureState = actualState;
            }
         else {
            if (futureState == toBeComputed) //it means nobody next turn.
            {
               futureState = actualState;
            }
         }
        //else there will be another guy out there.
      }
   
      public void getInfo() {
         this.acceptation  = ((World) world).acceptation;
         this.random = ((World) world).random;
      }
   
      protected void setRandom(CRandomDouble random) {
         this.random = random;
      }
   
      protected void setAcceptation(double acceptation) {
         if (acceptation >=0 && acceptation <= 1)
            this.acceptation = acceptation;
      }
   
      protected void setRandomize(double randomize) {
         if (randomize >=0 && randomize <= 1)
            this.randomize = randomize;
      }
   
      protected void setFreeAgentsProportion(double freeAgentsProportion) {
         if (freeAgentsProportion >=0 && freeAgentsProportion <= 1)
            this.freeAgentsProportion = freeAgentsProportion;
      }
   
      protected void setRedBlueProportion(double redBlueProportion) {
         if (redBlueProportion >=0 && redBlueProportion <= 1)
            this.redBlueProportion = redBlueProportion;
      }
   
      public ArrayList getDescriptors() {
         descriptors.clear();
         descriptors.add(new InfoDescriptor("Place ; "," PlaceID = "+(new Integer (placeID)).toString())); // DP 11/09/2001
         descriptors.add(new InfoDescriptor("Occupant : ", getActualStateStringForm(), getActualColor())); // DP 11/09/2001
         return descriptors;
      }
   }
