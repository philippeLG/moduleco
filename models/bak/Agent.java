/** class models.bak.Agent.java
 * Title:        Moduleco<p>
 * Description:  Je suis un EAgent, j'ai comme attribut ma Viabilite (nombre reel aleatoire tire entre 0 et 1).
 * Le monde evolue selon la regle suivante : l'agent qui a la plus faible viabilite, ainsi que ces voisins,
 * sont elimines et remplaces par de nouveaux agents avec une nouvelle viabilite.
 * PARAMETRES RECOMMANDES POUR LA SIMULATION :
 * Neighbour : Von Neumann (4)
 * Zone Active : World
 * Validation : LateCommitScheduler
 * Copyright: Copyright (c)enst-bretagne
 * @author camille.monge@enst-bretagne.fr, denis.phan@@enst-bretagne.fr
 * @version 1.4  February, 2004
 */
   package models.bak;

   import java.util.Iterator;
   import java.util.ArrayList;

   import modulecoFramework.modeleco.EAgent;
   import modulecoGUI.grapheco.descriptor.DoubleDataDescriptor;
// import modulecoFramework.medium.Medium;
   import modulecoFramework.medium.NeighbourMedium;
// import modulecoGUI.grapheco.CentralControl;

   import modulecoFramework.modeleco.randomeco.CRandomDouble;

   public class Agent extends EAgent {
   
   
      protected double Viability; // Agent's Fitness at time t
   
      protected int Life=0; //Number of Times the Agent's fitness has already changed
      protected CRandomDouble random;
      public Agent () { // initialisation dans init()
         super();
      }
   
      public void init() {
         this.random = ((World) world).random;
         Viability = random.getDouble();//Math.random();
        //FirstViability=Viability;
         neighbours = ((NeighbourMedium) mediums[0]).getNeighbours();
      }
   
      public Object getState(){
         return (new Boolean(true));
      }
   
      public double getViability()
      {
         return Viability;
      }
   
      public double getGraphicViability()
      {
         return (Viability * 100);
      }
   
      public void commit() { // valide le changement d'etat
        //if (ae != null) ae.update ();
      }
   
      public String toString() {
         return (new Double(Viability)).toString();
      }
   
      public void voisinage(){//compute on the neighbours
         Iterator i;
         Agent A;
         double tmp;
         for (i= neighbours.iterator();i.hasNext();) {
            A=(Agent)i.next();
            A.Viability=Math.random();
            A.Life++;
         }
      
      }
   
      public void compute() {
        // look at the fewer Fitness and replace the agent who have it and his neighbours.
      
         if (Viability==(((World) world).Min)){
            Viability=Math.random();
            Life++;
            voisinage();
         }
      }
   
   
      public void getInfo() {
      }
   
   
      public ArrayList getDescriptors()
      
      {
         descriptors.clear();
         descriptors.add(new DoubleDataDescriptor(this,"Viability","Viability",Viability,true,3));
         return descriptors;
      }
   
      public void setViability(double V){
         if (V>=0 && V<=1) Viability=V;
      }
   
      protected void setRandom(CRandomDouble random) {
      
         //System.out.println(" agent.setRandom() ");
         this.random = random;
      }
   }
