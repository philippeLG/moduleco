/** class models.epidemia.Agent.java
 * Title:        Moduleco<p>
 * Description:  I am an EAgent that can be contamined and can contamine the neighbours I am linked to,
 * I am sick for timeOfDesease and then am immune to the desease for
 * timeOfImmunity.
 * Copyright:    Copyright (c)enst-bretagne
 * @author Gregory.Gackel@enst-bretagne.fr
 * Created on mai,27, 2001
 * @version 1.4  February, 2004
 */


   package models.epidemia;

   import java.util.Iterator;
   import java.util.ArrayList;

   import modulecoFramework.modeleco.EAgent;
   import modulecoFramework.modeleco.randomeco.CRandomDouble;
// import modulecoGUI.grapheco.descriptor.DataDescriptor;
   import modulecoGUI.grapheco.descriptor.IntegerDataDescriptor;
   import modulecoGUI.grapheco.descriptor.ChoiceDataDescriptor;

   import modulecoFramework.medium.NeighbourMedium;
// import modulecoFramework.medium.Medium;

// import modulecoGUI.grapheco.ControlPanel;


   public class Agent extends EAgent {
   
    /**
     * different states of the agent
     **/
      protected static String humanHere = "humanHere" ;
      protected static String sickHere = "sickHere" ;
      protected static String immuneHere = "immuneHere" ;
   
    /**
     * proportion of opened links to neighbours
     *
     */
      protected double proportionOfLinks;
   
    /**
     * increments the time of desease
     */
      protected int count;
   
    /**
     * increments the time of immunity
     */
      protected int count2;
   
    /**
     * length of the desease and immunity
     */
      protected int timeOfDesease;
      protected int timeOfImmunity;
   
    /**
     *number of opened links for each agent
     */
      protected int nbLinks=0;
   
    /**
     * different states an agent can be in
     */
      protected String actualState, futureState, newState;
   
      protected Agent agentCourant;
   
      protected CRandomDouble random;
   
    /**
     * arraylists of neighbours
     */
      protected ArrayList openLink;
   
   
      public Agent() {
         super();
      }
   
      public void init() {
        /**
         * when initialising the agent we decide randomly how many opened links it will
         * have to his neighbours
         * we place this neighbours in the arraylist openlink
         */
         proportionOfLinks = ((World) world).proportionOfLinks;
         random = ((World) world).random;
      
         actualState = futureState = humanHere;
      
      
         neighbours = ((NeighbourMedium )getMediums()[0]).getNeighbours();
         openLink = new ArrayList(neighbours.size());
      
         for (Iterator i = neighbours.iterator(); i.hasNext();) {
            agentCourant = ((Agent) i.next());
            if (random.getDouble() <= proportionOfLinks ) {
               openLink.add(agentCourant);
               nbLinks++;
            }
            else openLink.add(null);
         }
      
      }
   
      public Boolean isEveryOneSick()
      {
         if(actualState.equals(sickHere))
            return new Boolean(true);
         else
            return new Boolean(false);
      }
   
      public Boolean isEveryOneImmune()
      {
         if(actualState.equals(immuneHere))
            return new Boolean(true);
         else
            return new Boolean(false);
      }
   
      public Object getState() {
         return new Boolean(actualState.equals(humanHere)); 
      }
   
      public void commit() { 
         actualState = futureState;
      }
   
      public String toString() {
         return ("rien");
      }
   
      public void inverseState() {
        //     etat = !etat; etatS = etat;
        //     if (ae != null) ae.update ();
      }
   
      public void compute() {
       /**
        * while computing, we verify in wich state the agent is in :
        * if he is sick, it increments the count and it contamines his neighbours with wich it has an opened link
        * when the time of desease has come to his end the agent decomes immune for a certain time
        * and later it becomes normal, ready to be contamined again
        */
      
         if(actualState.equals(sickHere)) {
            count++;
            for (Iterator i = openLink.iterator(); i.hasNext();) {
               agentCourant = ((Agent) i.next());
               if (agentCourant != null) {
                  if (agentCourant.getActualState().equals(humanHere))
                     agentCourant.setSickHere();
               }
            }
         }
         if (count == timeOfDesease) {
            futureState = immuneHere;
            count=0;
         }
      
      
         if (actualState.equals(immuneHere)) {
            count2++;
            if (count2 == timeOfImmunity) {
               futureState = humanHere;
               count2=0;
            }
         
         }
      
      
      
      }
   
      public void setSickHere() {
         futureState = sickHere;
      }
   
      public void setActualState(String newState) {
         futureState = newState;
      }
   
      public String getActualState() {
         return actualState;
      }
   
      public int getNumberOfLinks() {
         return nbLinks;
      }
   
      public void getInfo() {
         this.proportionOfLinks = ((World) world).proportionOfLinks;
         this.timeOfDesease = ((World) world).timeOfDesease;
         this.timeOfImmunity = ((World) world).timeOfImmunity;
         this.random = ((World) world).random;
      }
   
      protected void setRandom(CRandomDouble random)
      {
         this.random = random;
      }
   
   
      protected void setproportionOfLinks(double proportionOfLinks)
      {
         if (proportionOfLinks >=0 && proportionOfLinks <= 1)
            this.proportionOfLinks = proportionOfLinks;
      }
   
      protected void settimeOfImmunity(int timeOfImmunity)
      {
         this.timeOfImmunity = timeOfImmunity;
      }
   
      protected void settimeOfDesease(int timeOfDesease)
      {
         this.timeOfDesease = timeOfDesease;
      }
   
      public ArrayList getDescriptors() {
         descriptors.clear();
         descriptors.add(new ChoiceDataDescriptor(this,"Actual State","actualState",new String[]{"humanHere","sickHere","immuneHere"},actualState,true));
         descriptors.add(new IntegerDataDescriptor((EAgent) this,"Time of desease","timeOfDesease",timeOfDesease,true));
         descriptors.add(new IntegerDataDescriptor((EAgent) this,"Time of immunity","timeOfImmunity",timeOfImmunity,true));
         return descriptors;
      }
   }
