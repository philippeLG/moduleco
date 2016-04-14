/** class randomWalkers.Place.java
 * Title:        Moduleco<p>
 * Description:  I am an EPlace. I represent a cell, and not an habitant. My state depends on the habitagent who lives in me. There are four states: a habitagent: agent, a ressource Sugar , agent and Sugar, and nothing.
 * Copyright:    Copyright (c)enst-bretagne
 * @author Jerome.Schapman@enst-bretagne.fr, Gregory.Gackel@enst-bretagne.fr
 * @version 1.2  august,5, 2002
* @version 1.4. february 2004  
*/
   package models.randomWalkers;

// import java.util.Iterator;
   import java.util.ArrayList;

   import modulecoFramework.modeleco.mobility.MobileAgent;
   import modulecoFramework.modeleco.mobility.EPlace;
   import modulecoFramework.modeleco.randomeco.CRandomDouble;

// import modulecoFramework.medium.NeighbourMedium;

   import modulecoFramework.modeleco.exceptions.AlreadyUsedPlaceException;

   public  class Place extends EPlace
   {
    /**
     * Here are the different states for an Place
     */
   
      protected  static int nothingHere = 0;
      protected  static int antHere = 1;
   
   
      protected World world;
   
   
    //    protected NeighbourMoore neighbourhood = new NeighbourMoore();
   
   
      protected CRandomDouble random;
      protected Place placeCourante;
      protected ArrayList neighbours;
   
      protected MobileAgent futurAgent;
      protected boolean antHaveToMove;
      protected boolean toBeComputed;
   
      public void init(){
         super.init();
         futurAgent = null;
         toBeComputed = true;
         antHaveToMove = false;
      }
   
      public Object getState() {
         return new Boolean(isThereAnt());
      }
   
      public int getActualState() {
         if (isThereAnt() ) {
            return antHere;
         }
         else
            return nothingHere;
      
      }
   
      public void setActualState(int newState) {
      
      }
   
      public String toString() {
         return "randomWalkers.Place."+agentID; //(new Integer(actualState)).toString();
      }
   
      public void inverseState() {
      //        if (ae != null)
      //            ae.update ();
      }
   
      public double distanceFromBeginning() 
      {
         if (isThereAnt()) {
         //            System.out.println("distanceFromBeginning");
            return (((Ant)agent).distanceFromBeginning());
         }
         return 0.0;        
      }
   
      public Boolean doILive() 
      {
         return (new Boolean (isThereAnt()));
      }
   
   
   
      public void compute() {
         if (isThereAnt() && toBeComputed)
            agent.compute();
      }
   
      public void getInfo()
      {
      //        this.random = java.lang.Math.random();
      }
   
      public boolean isThereAnt() {
         return (agent!=null);
      }
   
   
      public boolean isThereFuturAnt() {
         return (futurAgent!=null);
      }
   
      protected void setRandom(CRandomDouble random)
      {
         this.random = random;
      }
   
      public void futurReceive (MobileAgent a) throws AlreadyUsedPlaceException {
      //        System.out.println("futurAgent est : " + futurAgent);
         if (futurAgent == null) {
         //            System.out.println("futurAgent est NUL");
            futurAgent = a ;
         }
         else {}//throw new AlreadyUsedPlaceException();
      }
   
      public void futurLeave () {
         futurAgent = null;
      }
   
      public void commit() {
         toBeComputed = true;
         if (antHaveToMove) {
            agent = futurAgent;
            futurAgent = null;
            if (agent != null) {
               agent.setPlace(this);
            }
            antHaveToMove = false;
         }
      }
   
      public MobileAgent getFuturAgent() {
         return futurAgent;
      }
   
      public void antHaveToMove() {
         antHaveToMove = true;
      }
      public void computed() {
         toBeComputed = false;
      }
   
      public void putAnt() {
         if (!isThereAnt()) {
            agent = new Ant(this);
            agent.init();
         }
      }
   }
