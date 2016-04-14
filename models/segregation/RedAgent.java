/** class segregation.RedAgent.java
 * Title:        Moduleco<p>
 * Description:  I am an EAgent. I represent an habitation, and not an habitant. My state depends on the habitant who lives in me. There are three states: a blue habitant, a red habitant, and no habitant.
 * Copyright:    Copyright (c)enst-bretagne
 * @author denis.phan@enst-bretagne.fr, Antoine.Beugnard@enst-bretagne.fr, frederic.falempin@enst-bretagne.fr
 * @version 1.2  august,5, 2002  
 */

   package models.segregation;

   import java.util.ArrayList;
   import java.awt.Color; //DP 11/09/2001

// import modulecoFramework.modeleco.mobility.MobileAgent;
   import modulecoFramework.modeleco.mobility.EPlace;
   import modulecoGUI.grapheco.descriptor.InfoDescriptor;//DP 11/09/2001

   public class RedAgent extends SegregationAgent
   {        
      public RedAgent(Place p) {
         super();
         place = p;
         world = p.getWorld();
      }
   
      public boolean isBlue() {
         return false;
      }
   
      public Object getState()
      {
         return new Boolean(false);
      }
   
      public String toString()
      {
         return "I'm red !";
      }
   
   /*
      public void inverseState()
      {
         if (ae != null)
            ae.update ();
      }
   */
   
    //   protected boolean canAccept(int wantedState)
   
   
      public Boolean doILiveInTheGoodPlace() // Return false if the habitant
      // will have to move next turn
      {
         return new Boolean(((Place) place).getPreferedState() == Place.redHere);
      }
   
      public boolean canAccept(EPlace p)
      {
         return (((Place) p).getPreferedState() != Place.blueHere);
      }
   
      public void init() {
      } // do nothing
   
      public void compute() {
      } // do nothing
   
      public void commit() {
      } // do nothing
   
      public void getInfo() {
      } // do nothing, the Place does
   
      public ArrayList getDescriptors(){
         descriptors.clear();
         descriptors.add(new InfoDescriptor("Red Agent ; "," agentID = "+(new Integer (agentID)).toString(), Color.red));//DP 11/09/2001
      	//descriptors.add(new BooleanDataDescriptor(this,"Blue?","",isBlue(),false));
         return descriptors;
      }
   }
