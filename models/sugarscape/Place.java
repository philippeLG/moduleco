/** class sugarscape.Place.java
 * Title:        Moduleco<p>
 * Description:  I am an EPlace. I represent a cell, and not an habitant. My state depends on the habitagent who lives in me. There are four states: a habitagent: agent, a ressource Sugar , agent and Sugar, and nothing.
 * Copyright:    Copyright (c)enst-bretagne
 * @author Jerome.Schapman@enst-bretagne.fr, Gregory.Gackel@enst-bretagne.fr
 * @version 1.2  august,5, 2002
* @version 1.4. february 2004   
 */

   package models.sugarscape;

// import java.util.Iterator;
   import java.util.ArrayList;


// import modulecoFramework.modeleco.mobility.MobileAgent;
   import modulecoFramework.modeleco.mobility.EPlace;
   import modulecoFramework.modeleco.randomeco.CRandomDouble;

// import modulecoFramework.medium.NeighbourMedium;

   public  class Place extends EPlace
   {
    /**
     * Here are the different states for an Place
     */
      protected  static int nothingHere = 0;
      protected  static int sugarHere = 1;
      protected  static int antHere = 2;
      protected  static int antSugarHere = 3;
   
    /**
     * The ratio of sugar places against empty places
     */
      protected double sugarQuantity;
    /**
     * The maximum quantity of sugar that can be produced in a Place
     */
      protected int produceMax = 0;
   
    /**
     * The proportion of precalculated Places against random Places
     */
      protected double proportionOfAnts;
    /**
     * The maximum sight of an ant
     */
      protected int sightMax;
    /**
     * The quantity of sugar needed for an Place
     */
      protected int consumptionMax;
    /**
     * The production of sugar of a place
     */
      protected int production = 0;
    /**
     * compteur pour lengthchangement de saison
     */
      protected int count;
    /**
     * The season we are in
     */
      protected int season;
      protected CRandomDouble random;
      protected ArrayList neighbours;
   
      public void init(){
         proportionOfAnts = ((World) world).proportionOfAnts;
         sugarQuantity = ((World) world).sugarQuantity;
         sightMax = ((World) world).sightMax;
         consumptionMax = ((World) world).consumptionMax;
      /**
      * taking a random number, we choose wich cell can have sugar
      */        
         if (random.getDouble() <= sugarQuantity){
            produceMax = (int) (10 * random.getDouble());
         }
         production = produceMax;
      
         if (random.getDouble() <= proportionOfAnts ) {
            agent = new Ant(this);
         //         agent.setPlace(this);
          //  agent.init();
         }
         super.init();
      }
   
      public int getProduction() {
         return production;
      }
   
      public Object getState() {
         return new Boolean(isThereAnt());
      }
   
      public int getActualState() {
         if (isThereAnt())
            if (isThereSugar())
               return antSugarHere;
            else
               return antHere;
         else
            if (isThereSugar())
               return sugarHere;
            else
               return nothingHere;
      }
   
      public void setActualState(int newState) {
      }
   
      public String toString() {
         return "sugarscape.Place."+agentID; //(new Integer(actualState)).toString();
      }
   
    /**
     * Allows to change the state of the Place 'by the hand' in the Editor
     */
      public void inverseState() {
         //if (ae != null) ae.update ();
      }
   
      public Boolean doILiveInTheGoodPlace() // Return false if the habitant
      // hasn't got enough sugar
      {
         if(isThereAnt())
            return new Boolean(production>((Ant) agent).getConsumption());
         else
            return new Boolean(false);
      }
   
      public void compute(){ 
         super.compute();
      
         if (((World) world).getSeason() % 2 == 0) {
            if (isThereSugar() && production<produceMax-1)
               production = production + 2;
         }
      
         if (isThereAnt() && isThereSugar())
            production-=((Ant) agent).getConsumption();
      
         if (production<0) production = 0;
      }
   
      public void getInfo()
      {
         this.random = ((World) world).random;
      }
   
      public boolean isThereAnt() {
         return (agent!=null);
      }
   
      public boolean isThereSugar() {
         return (produceMax!=0);
      }
   
      protected void setRandom(CRandomDouble random)
      {
         this.random = random;
      }
   
   
      protected void setproportionOfAnts(double proportionOfAnts)
      {
         if (proportionOfAnts >=0 && proportionOfAnts <= 1)
            this.proportionOfAnts = proportionOfAnts;
      }
   
      protected void setsugarQuantity(double sugarQuantity)
      {
         if (sugarQuantity >=0 && sugarQuantity <= 1)
            this.sugarQuantity = sugarQuantity;
      } 
      protected void setsightMax(int sightMax)
      {
         if (sightMax >=0 && sightMax <= 10)
            this.sightMax = sightMax;
      }
   
      protected void setconsumptionMax(int consumptionMax)
      {
         if (consumptionMax >=0 && consumptionMax <= 9)
            this.consumptionMax = consumptionMax;
      }
   
      public void setProduction(int production)
      {
         if (production >=0 && production <= 9)
            this.produceMax = production;
      }
   }
