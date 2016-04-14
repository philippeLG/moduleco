/** class segregation.Agent.java
 * Title:        Moduleco<p>
 * Description:  I am an EAgent. I represent an habitation, and not an habitant. My state depends on the habitant who lives in me. There are three states: a blue habitant, a red habitant, and no habitant.
 * Copyright:    Copyright (c)enst-bretagne
 * @author denis.phan@enst-bretagne.fr, Antoine.Beugnard@enst-bretagne.fr, frederic.falempin@enst-bretagne.fr
 * @version 1.2  august,5, 2002  
 */

   package models.segregation;


   import modulecoFramework.modeleco.mobility.MobileAgent;
   import modulecoFramework.modeleco.mobility.EPlace;
   import modulecoFramework.modeleco.mobility.Move;
   import modulecoFramework.modeleco.mobility.move.RandomMove;

   public abstract class SegregationAgent extends MobileAgent
   {
      public SegregationAgent() {
         move = new RandomMove();
      }
   
      public abstract boolean isBlue() ;
   
    /*public void inverseState()
    {
        if (ae != null)
            ae.update ();
    }*/
   
      public abstract Boolean doILiveInTheGoodPlace() ;
   
      public abstract boolean canAccept(EPlace p) ;
   
      public void setMove(Move m) {
         move = m;
      }
   
      public void init() {
      } // do nothing
   
      public void compute() {
      } // do nothing
   
      public void commit() {
      } // do nothing
   
      public void getInfo() {
      } // do nothing, the Place does
   
      public String toString() {
         return "SegregationAgent !";
      }   
   }
