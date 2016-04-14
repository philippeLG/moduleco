/**
 * deathRule.java
 *
 * Created on 11 mai 2001, 13:36
 *
 *
 * @author  gregory.gackel@enst-bretagne.fr
 * @version 1.2  august,5, 2002
* @version 1.4. february 2004   
 */

   package models.sugarscape.rule;

   import models.sugarscape.Rule;
   import models.sugarscape.Place;
   import models.sugarscape.Ant;

// import modulecoFramework.modeleco.mobility.MobileAgent;

   public class DeathRule extends Rule {
   
      protected int stock;
    /** Creates new deathRule */
      public DeathRule() {
      }
   
   
      public void rule(Ant agent) {
      
         stock = agent.getStock();
         place = ((Place) agent.getPlace());
      
         if (stock <= 0) {
            place.leave();
         } 
         else {        
            stock+=place.getProduction();
            stock-=agent.getConsumption();
         }
      
         agent.setStock(stock);
      }
   }
