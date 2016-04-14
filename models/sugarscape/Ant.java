/*
 * Ant.java
 *
 * Created on 25 avril 2001, 16:41
 * @author jerome.schapman@enst-bretagne.fr, gregory.gackel@enst-bretagne.fr
 * @version 1.2  august,5, 2002
* @version 1.4. february 2004   
 */

   package models.sugarscape;

   import models.sugarscape.Rule;
   import modulecoFramework.modeleco.mobility.MobileAgent;
   import modulecoFramework.modeleco.mobility.EPlace;
// import modulecoFramework.modeleco.mobility.Move;
// import modulecoFramework.modeleco.mobility.move.RandomMove;
   import modulecoFramework.modeleco.randomeco.CRandomDouble;

/**
 * Mobile agent in Sugarscape
 */

   public class Ant extends MobileAgent
   {
      protected Rule rule;
   
      protected int consumptionMax;
      protected int sightMax;
      protected int stockMax = 100;
   
      private int sight;
   
      private int consumption;
   
      private int stock;
   
   
      protected CRandomDouble random;
   
    /** Creates new Ant */
      public Ant(Place p) {
      //      move = new MoveSugarscape();
         place = p;
         world = p.getWorld();
      }
   
      public void init() {
         sightMax = ((Place) place).sightMax;
         consumptionMax = ((Place) place).consumptionMax;
         random = ((Place) place).random;
         consumption =  (int) (1 + consumptionMax*random.getDouble());
         sight = (int) (sightMax*random.getDouble());
         stock = (int) (stockMax*random.getDouble());
      }
   
      public void compute() {
      
         applyRule();
      
      /*     if (stock <= 0) {
      
                place.leave();
      
        } else {        
            stock+=((Place) place).getProduction();
            stock-=consumption;
        }*/
      
         super.move();
      }
   
      public void setRule(Rule rule)
      {
         this.rule = rule;
      }
   
      public void applyRule() {
         rule.rule(this);
      
      }
      public  boolean canAccept(EPlace p) {
         return true;
      
      }
   
      public void commit() {
      }
   
      public Object getState() {
         return new Boolean(stock > 0);
      }
   
      public void getInfo() {
         this.sightMax  = 6 ;//sightMax;
         this.consumptionMax = 9; //consumptionMax;
      }
   
      public int getSight() {
         return sight;
      }
   
      public int getStock() {
         return stock;
      }
   
      public void setStock(int stock) {
         this.stock = stock;
      }
   
   
      public int getConsumption() {
         return consumption;
      }
   
   
      protected void setsightMax(int sightMax) {
         if (sightMax >=0 && sightMax <= 10)
            this.sightMax = sightMax;
      }
   
      protected void setconsumptionMax(int consumptionMax) {
         if (consumptionMax >=0 && consumptionMax <= 9)
            this.consumptionMax = consumptionMax;
      }
   }
