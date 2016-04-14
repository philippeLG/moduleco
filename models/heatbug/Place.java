/** class heatbug.Place.java
 * Title:        Moduleco<p>
 *
 * Description:  I am an EPlace. I represent a cell, and not an habitant.
 * My state depends on the habitagent who lives in me. There are four states:
 *  ant, heat , ant and heat, and nothing.
 *
 * Copyright:    Copyright (c)enst-bretagne
 * @author Jerome.Schapman@enst-bretagne.fr, Gregory.Gackel@enst-bretagne.fr, Jerome.Lorenzi@enst-bretagne.fr 
 * @version 1.2  august,5, 2002
 */

   package models.heatbug;

   import java.util.Iterator;
   import java.util.ArrayList;

// import modulecoFramework.modeleco.mobility.MobileAgent;
   import modulecoFramework.modeleco.mobility.EPlace;
   import modulecoFramework.modeleco.randomeco.CRandomDouble;
   import modulecoFramework.medium.NeighbourMedium;

   public  class Place extends EPlace
   {
    /**
     * Here are the different states for an Place
     */
   
      protected  static int nothingHere = 0;
      protected  static int heatHere = 1;
      protected  static int antHere = 2;
      protected  static int antHeatHere = 3;
   
   
    /**
     * The proportion of precalculated Places against random Places
     */
      protected double proportionOfAnts;
    /**
     * The maximum sight of an ant
     */
      protected int sightMax;
   
   /**
     * The maximum temperature an ant is searching
     */
   
      protected int idealTemperatureMax;
   
   
    /**
     * The heat of a place
     */
      protected int heat;
   
      protected CRandomDouble random;
      protected Place placeCourante;
   //protected Place placeCouranteDeux;
   
      protected MoveHeatbug moveheatbug2;
   
    //    protected NeighbourMoore neighbourhood = new NeighbourMoore();
   
    // Conception of alist of the neigbours
      protected ArrayList neighbours;
      protected ArrayList neighbors;
   
   
   
      public void init(){
      
        /**
         * variables taken out of the interface
         */
      
      
         proportionOfAnts = ((World) world).proportionOfAnts;
         sightMax = ((World) world).sightMax;
         idealTemperatureMax = ((World) world).idealTemperatureMax;
      
      
        /**
         * initialization for the heat of a place
       */
      
         heat = 0;
      
         /**
         * Creation of an ant
       */
      
         if (random.getDouble() <= proportionOfAnts ) {
         
            agent = new Ant(this);
         
         }
      
         super.init();
      }
   
   
   
   
      public Object getState() {
         return new Boolean(isThereAnt());
      }
   
    /**
     * this method will throw the state of the place to the canevas
     */
   
      public int getActualState() {
         if (isThereAnt() ) {
            if (isThereHeat())
               return antHeatHere;
            else
               return antHere;
         }
      
         if (isThereHeat())
            return heatHere;
         else
            return nothingHere;
      
      }
   
      public void setActualState(int newState) {
      
      }
   
   
   
      public String toString() {
         return "heatbug.Place."+agentID; 
      }
   
    /**
     * Allows to change the state of the Place 'by the hand' in the Editor
     */
   
      public void inverseState() {
		System.out.println("gant : "+agentID+" inverseState()");
      }
   
   /**
     * this method will throw the state of the ant to the graphique
     */
      public Boolean doILiveInTheGoodPlace() 
      {
         if(isThereAnt())
            return new Boolean(((Ant) agent).getIdealTemperature() <= ((Ant) agent).getCourantTemperature());
         else
            return new Boolean(false);
      
       /*if (isThereAnt())		
      { 
      if  ( moveheatbug2.antHaveToMove = true)
          {
           return new Boolean(true);
          }
      	else
      	{
      	return new Boolean (false);
      	}
               
      }
      else   
      {
           return new Boolean(false);
      }*/
      }
   
   
      public void setHeat(int heat) {
         if (heat>60)
            this.heat = 60;
         /* else if (heat<0)
            this.heat = 0;*/
         else 
            this.heat = heat;	
      }
   
   
      public int getHeat() {
         return heat;
      } 
   
   
      public void compute()
      {
         super.compute();
      
      //Evaporation rules
      
         if (heat>0)
            heat=heat - 10;
         if (heat<0)
            heat=0;	
      
      //Set the heat of a place and of his neighborhood
      
         if (isThereAnt() ) { 
         
         
            int heatAnt = ((Ant) agent).getCourantTemperature();
            int antIdealTemperature = ((Ant) agent).getIdealTemperature();
         
         
            heat += heatAnt;
         
         
            if (heatAnt>=antIdealTemperature-10) {
            
            
                //int a =(int)( 8*random.getDouble());
               neighbours = ((NeighbourMedium )getMediums()[0]).getNeighbours();//neighbourhood.compute(getAgentID());
            
               for (Iterator i = neighbours.iterator(); i.hasNext();) {
               
                  placeCourante = ((Place) i.next());
                  placeCourante.setHeat( placeCourante.getHeat() + heatAnt - 1);
               
               
               /*	neighbors = ((NeighbourMedium )getMediums()[0]).getNeighbours();
               
               for (Iterator j = neighbors.iterator(); j.hasNext();) {
               
               for (int k=0;k<=7;k++)
               {
               	for(int l=0;0<=7;l++)
               	{
               if ( neighbours[l] == neighbors[k] )
               {	
               }
               else
               {
               placeCouranteDeux = ((Place) j.next());
               placeCouranteDeux.setHeat( 30);
               }
               	}
               }
               }*/
               
               
               }
            }
         } 
      // (( Place) placeCourante).setHeat(placeCourante.getHeat());  
      
      }
   
   
      public void getInfo()
      {
         this.random = ((World) world).random;
      
      }
   
      public boolean isThereAnt() {
         return (agent!=null);
      }
   
      public boolean isThereHeat() {
         return (heat !=0);
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
   
   
      protected void setsightMax(int sightMax)
      {
         if (sightMax >=0 && sightMax <= 20)
            this.sightMax = sightMax;
      }
   
      protected void setidealTemperatureMax(int idealTemperatureMax)
      {
         if (idealTemperatureMax >=0 && idealTemperatureMax <= 200)
            this.idealTemperatureMax = idealTemperatureMax;
      }
   
   
   }
