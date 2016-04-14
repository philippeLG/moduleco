/** class segregation.BlueAgent.java
 * Title:        Moduleco<p>
 * Description:  I am an EAgent. I represent an habitation, and not an habitant. My state depends on the habitant who lives in me. There are three states: a blue habitant, a red habitant, and no habitant.
 * Copyright:    Copyright (c)enst-bretagne
 * @author 
 * @version 1.2  august,5, 2002  
 */

   package models.segregation;

   import java.util.ArrayList;
   import java.awt.Color; //DP 11/09/2001

// import modulecoFramework.modeleco.*;
// import modulecoFramework.modeleco.mobility.MobileAgent;
   import modulecoFramework.modeleco.mobility.EPlace;
   import modulecoGUI.grapheco.descriptor.InfoDescriptor;//DP 11/09/2001

   public class BlueAgent extends SegregationAgent
   {
      public BlueAgent(Place p) {
         place = p;
         world = p.getWorld();
      }
   
      public boolean isBlue() {
         return true;
      }
   
      public Object getState()
      {
         return new Boolean(true);
      }
   
      public String toString()
      {
         return "I'm blue !";
      }
   
      public Boolean doILiveInTheGoodPlace() // Return false if the habitant
      // will have to move next turn
      {
         return new Boolean(((Place) place).getPreferedState() == Place.blueHere);
      }
   
      public boolean canAccept(EPlace p)
      {
         return (((Place) p).getPreferedState() != Place.redHere);
      }
      public ArrayList getDescriptors()
      {
         descriptors.clear();
         descriptors.add(new InfoDescriptor("Blue Agent ; "," agentID = "+(new Integer (agentID)).toString(), Color.blue));//DP 11/09/2001
      
      	//descriptors.add(new BooleanDataDescriptor(this,"Blue?","",isBlue(),false)); //supprimé DP 11/09/2001
         return descriptors;
      }
   }
