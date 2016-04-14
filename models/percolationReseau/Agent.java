/** class percolationReseau.Agent.java
 * Title:        Moduleco<p>
 * Description:  I am an EAgent wich represents a tree, I can bee in 3 different states: treeHere, fireHere
 * and deadTreeHere. I can set my neighbours in fire, but only the neighbours I am linked to, the number of 
 * neighbours depends on proportionOfLinks
 * Copyright:    Copyright (c)enst-bretagne
 * @author Gregory.Gackel@enst-bretagne.fr
* @version 1.2  august,5, 2002
* @version 1.4. february 2004    
 */
   package models.percolationReseau;

   import java.util.Iterator;
   import java.util.ArrayList;

   import modulecoFramework.modeleco.EAgent;
   import modulecoFramework.modeleco.randomeco.CRandomDouble;

   import modulecoGUI.grapheco.descriptor.ChoiceDataDescriptor;
   import modulecoGUI.grapheco.descriptor.IntegerDataDescriptor;

   import modulecoFramework.medium.NeighbourMedium;


   public class Agent extends EAgent {
   
    /**
     *differents states of the tree
     **/
      protected static String treeHere = "treeHere";
      protected static String fireHere = "fireHere";
      protected static String deadTreeHere = "deadTreeHere";
   
    /**
     * proportion of links that will be opened to the neighbours
     */
      protected double proportionOfLinks;
   
    /**
     *number of links opened for each tree
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
   
    /** Creates new Agent */
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
      
         actualState = treeHere;
      
      
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
      
         futureState = actualState;
      
      }
   
      public Boolean isEveryOneDead() // Return false if the tree
      // is dead
      {
         if(actualState.equals(deadTreeHere))
            return new Boolean(true);
         else
            return new Boolean(false);
      }
   
      public Object getState() {
         return new Boolean(actualState.equals(treeHere)); 
      }
   
      public void commit() { // validates changing of state
         actualState = futureState;
      }
   
      public String toString() {
         return ("nothing");
      }
   
      public void inverseState() {
        //     etat = !etat; etatS = etat;
        //     if (ae != null) ae.update ();
      }
   
      public void compute() {
      
        /**
        * while computing, we verify in wich state the agent is in :
        * if he is in fire, his future state will be dead and it contamines his neighbours 
        * with wich it has an opened link
        */      
      
         if(actualState.equals(fireHere) ) {
            futureState = deadTreeHere;
            for (Iterator i = openLink.iterator(); i.hasNext();) {
               agentCourant = ((Agent) i.next());
               if (agentCourant != null) {
                  if (agentCourant.getActualState().equals(treeHere))
                     agentCourant.setFireHere();
               }
            }
         }
      
      }
   
      public void setFireHere() {
         futureState = fireHere;
      }
   
      public void setActualState(String newState) {
         actualState = newState;
      }
   
      public String getActualState() {
         return actualState;
      }
   
      public int getNumberOfLinks() {
         return nbLinks;
      }
   
      public void getInfo() {
         this.proportionOfLinks = ((World) world).proportionOfLinks;
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
   
      public ArrayList getDescriptors() {
         descriptors.clear();
         descriptors.add(new ChoiceDataDescriptor(this,"Actual State","actualState",new String[]{"treeHere", "fireHere", "deadTreeHere"},actualState, true));
         descriptors.add(new IntegerDataDescriptor(this,"Number of Links","nbLinks",nbLinks,true));
         return descriptors;
      }
   }
