/*
 * Class Ant.java
 * I am an ant with caracteristics such as sight, courantTemperature and idealTemperature which is
 * the courantTemperature I try to access.
 * 
 * Created on 25 juin 2001, 16:41
 * @author jerome.schapman@enst-bretagne.fr, gregory.gackel@enst-bretagne.fr, jerome.lorenzi@enst-bretagne.fr
 * @version 1.2  august,5, 2002
 */

   package models.heatbug;

   import  modulecoFramework.modeleco.mobility.MobileAgent;
   import  modulecoFramework.modeleco.mobility.EPlace;
// import  modulecoFramework.modeleco.mobility.Move;
// import  modulecoFramework.modeleco.mobility.move.RandomMove;
   import  modulecoFramework.modeleco.randomeco.CRandomDouble;

   public class Ant extends MobileAgent
   {
   
    // Maxima of the values sight and idealTemperature
      protected int sightMax;
      protected int idealTemperatureMax;
   
     // sight of an ant
      private int sight;
   
    // ideal and courant temperature of an ant
      private int idealTemperature, courantTemperature; 
   
   
      protected CRandomDouble random;
   
   
   
    /** Creates new Ant  */
   
      public Ant(Place p) {
         place = p;
         world = p.getWorld();
      }
   
      public void init() {
      
      //These maxima are taken by the user in the boxes with the same name
         sightMax = ((Place) place).sightMax;
         idealTemperatureMax = ((Place) place).idealTemperatureMax;
      
         random = ((Place) place).random;
      
        /**
         * we take random number to determine the caracteristics of our ants
         * at first
         */
      
         sight = (int) (sightMax*random.getDouble());
         idealTemperature = (int) ( idealTemperatureMax*random.getDouble());
      
      //The first courant temperature of all the ants is fixed
         courantTemperature = 5;
      
      }
   
      public void compute() {
      
       //We take the heat of each place
         int prod = ((Place) place).getHeat();
      
      //The rules to warm or freeze an ant depending on the temperature of the place
      
      
         if (prod >= courantTemperature)
            courantTemperature++;
         else 
            courantTemperature = courantTemperature - 5;
      
       //The condition of moving  
      
         if (idealTemperature  <= courantTemperature ) {
         } 
         else {
            super.move();
         
         }
      
      }
   
   
      public  boolean canAccept(EPlace p) {
         return true;
      
      }
   
   
   
      public void commit() {
      }
   
      public Object getState() {
         return new Boolean(true);
      }
   
   //return the sight and the idealtemperature
   
      public void getInfo() {
         this.sightMax  = 6 ;//sightMax;
         this.idealTemperatureMax = 3; // idealTemperatureMax;
      }
   
   //return the sight
   
      public int getSight() {
         return sight;
      }
   
   //return the ideal temperature which has a minimum of 10
   
      public int getIdealTemperature() {
         if (idealTemperature <=10)
            return 10;
         else
            return idealTemperature;
      }
   
   //return the courant temperature
   
      public int getCourantTemperature() {
      /*	if (courantTemperature > idealTemperature )
      	return 0;
      else*/	
         return courantTemperature;
      }
   
    //To confirm that the ideal temperaure max  is between...
   
      public void setidealTemperatureMax(int idealTemperatureMax) {
         if (idealTemperatureMax >=0 && idealTemperatureMax<=200)
            this.idealTemperatureMax = idealTemperatureMax;
      }
   
   
    //To confirm that the sight max is between...
   
      protected void setsightMax(int sightMax) {
         if (sightMax >=0 && sightMax <= 20)
            this.sightMax = sightMax;
      }
   
   
   }
