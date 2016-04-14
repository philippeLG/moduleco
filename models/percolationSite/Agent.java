/*
 * percolationSite.Agent.java
 *
 * Created on 21 mai 2001, 22:28
 *
 *
 * @author  gregory.gackel@enst-bretagne.fr
 * @version 1.2  august,5, 2002
* @version 1.4. february 2004    
 */

   package models.percolationSite;

   import java.util.Iterator;
   import java.util.ArrayList;

   import modulecoFramework.modeleco.EAgent;
   import modulecoFramework.modeleco.randomeco.CRandomDouble;
   import modulecoGUI.grapheco.descriptor.ChoiceDataDescriptor;

   import modulecoFramework.medium.NeighbourMedium;

   public class Agent extends EAgent {
   
      protected static String nothingHere = "nothingHere";
      protected static String treeHere = "treeHere";
      protected static String fireHere = "fireHere";
      protected static String deadTreeHere = "deadTreeHere";
   
      protected double proportionOfTrees;
   
      protected int count;
   
   
      protected String actualState, futureState, newState;
   
      protected Agent agentCourant;
   
      protected CRandomDouble random;
   
    /** Creates new Agent */
      public Agent() {
         super();
      }
   
      public void init() {
      
         proportionOfTrees = ((World) world).proportionOfTrees;
         random = ((World) world).random;
      
         actualState = nothingHere;
      
         if (random.getDouble() <= proportionOfTrees )
            actualState = treeHere;
      
         futureState = actualState;
      
      }
   
      public Boolean isEveryOneInFire() // Return false if the habitant
      // hasn't got enough sugar
      {
         if(actualState.equals(deadTreeHere))
            return new Boolean(true);
         else
            return new Boolean(false);
      }
   
      public Object getState() {
         return new Boolean(actualState.equals(treeHere)); // pour affichage c'est celui là
      }
   
      public void commit() { // valide le changement d'etat
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
         random = ((World) world).random;
      
         if(actualState.equals(fireHere)) {
            count++;
            neighbours = ((NeighbourMedium )getMediums()[0]).getNeighbours();
            for (Iterator i = neighbours.iterator(); i.hasNext();) {
               agentCourant = ((Agent) i.next());
               if (agentCourant.getActualState().equals(treeHere))
                  agentCourant.setFireHere();
            }
         }
      
         if (count == 2 ) {
            futureState = deadTreeHere;
            count = 0;
         }
      
      
      }
   
      public void setActualState(String newState) {
         actualState = newState;
      }
   
      public void setFireHere() {
         if (actualState.equals(treeHere))
            futureState = fireHere;
      }
   
      public String getActualState() {
         return actualState;
      }
   
      public void getInfo() {
         this.proportionOfTrees = ((World) world).proportionOfTrees;
         this.random = ((World) world).random;
      }
   
      protected void setRandom(CRandomDouble random)
      {
         this.random = random;
      }
   
   
      protected void setproportionOfTrees(double proportionOfTrees)
      {
         if (proportionOfTrees >=0 && proportionOfTrees <= 1)
            this.proportionOfTrees = proportionOfTrees;
      }
   
   
   
      public ArrayList getDescriptors() {
         descriptors.clear();
         descriptors.add(new ChoiceDataDescriptor(this,"Actual State","actualState",new String[]{"nothingHere", "treeHere", "fireHere","deadTreeHere"},actualState, true));
         return descriptors;
      }
   }
