/**
 * Rule.java
 *
 * Created on 11 mai 2001, 11:37
 *
 * @author gregory.gackel@enst-bretagne.fr
 * @version 1.2  august,5, 2002  
 */

   package models.sugarscape;

// import modulecoFramework.modeleco.mobility.MobileAgent;

   public abstract class Rule  
   {    
      protected Place place;
      protected Ant agent;
   
      public abstract void rule(Ant agent);
   } // Rule
