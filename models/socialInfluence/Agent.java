/** class socialInfluence.Agent.java
 * Title:        Moduleco<p>
 * Description:  Je suis un EAgent. A partir d'un etat initial (equiprobable) entre
 * true et false, je calcule mon evolution au travers d'un choix. La règle de choix
 * est la suivante : soit f la proportion de mes voisins à avoir choisi "true"<p>
 * <UL>
 * <LI>si f >= 0.5 et que j'ai moi-même choisi true (1), mon choix est true (1)
 * <LI>si f >= 0.5 et que j'ai moi-même choisi false(0), je change mon choix pour true (1) avec une probabilité mu
 * <LI>si f < 0.5 et que j'ai moi-même choisi false (0), mon choix est false (0)
 * <LI>si f < 0.5 et que j'ai moi-même choisi true (1), je change mon choix pour false avec une probabilité mu
 * </UL>
 * Mon monde est un dp.World.<p>
 * Copyright:    Copyright (c)enst-bretagne
 * @author denis.phan@enst-bretagne.fr, Antoine.Beugnard@enst-bretagne.fr, sebastien.chivoret@ensta.org (16/05/2001)
 * @version 1.4  February, 2004 
 */
   package models.socialInfluence;

   import modulecoFramework.modeleco.EAgent;
   import modulecoFramework.modeleco.randomeco.CRandomDouble;
// import modulecoGUI.grapheco.descriptor.DataDescriptor;
// import modulecoGUI.grapheco.descriptor.DoubleDataDescriptor;
   import modulecoGUI.grapheco.descriptor.BooleanDataDescriptor;
// import modulecoGUI.grapheco.descriptor.IntegerDataDescriptor;
// import modulecoGUI.grapheco.descriptor.ChoiceDataDescriptor;

   import java.util.Iterator;
   import java.util.ArrayList;

   import modulecoFramework.medium.NeighbourMedium;

   public class Agent extends EAgent {
   
    /**
     * etat est l'état stable. etatS celui en cours de calcul...
     */
      protected boolean etat, etatS, hasChanged;
   
    /**
     * Probabilité que je ne change pas d'avis si la majorité me contredis (0.5 par défaut)
     */
      protected double mu ; // defaut, symétrique
   
    /**
     * Proportion de voisin qui ont choisi "Vrai"
     */
      protected double f;
   
    /**
     * Probabilité initiale de penser "Vrai"
     */
   
      protected double theta ;
      protected boolean alea ;
   
    /**
     * random
     */
      protected CRandomDouble random;
      //protected ArrayList descriptors; // dans EAgent
   
   
   
      public Agent () { // initialisation dans init()
         super();
      }
   
    /**
     * Retourne l'état stable
     */
      public Object getState() {
         return new Boolean(etatS); // pour affichage c'est celui là
      }
   
      public void commit() { // valide le changement d'etat
         hasChanged = (etat != etatS);
         etat = etatS;
        //if (ae != null) ae.update ();
      }
   
      public String toString() {
         return (new Boolean(etat)).toString();
      }
   
      public void inverseState() {
         etat = !etat; etatS = etat;
         System.out.println("inverseState()");
      }
   
      public Boolean hasChanged()
      {
         boolean temp = hasChanged;
      
         hasChanged = false;
      
         return new Boolean(temp);
      }
   
      public void voisinage(){
         neighbours = ((NeighbourMedium) mediums[0]).getNeighbours();
      
         if (world.getAgents() == neighbours) {//optimisation ok pour late, mais pas pour early !!!!
            f = ((Double) ((World) world).getState()).doubleValue();
         }
         else
         {//cas général
            int n = 0;
            for (Iterator i= neighbours.iterator();i.hasNext();) {
               if (((Agent) i.next()).etat) {
                  n++;
               }
            }
            f = ((double) n) / ((double) neighbours.size()) ;
         }
      }
   
      public void compute() { // fait évoluer l'etat
      
         getInfo();
         voisinage();
         if (alea) etat = ( (random.getDouble() >= theta) ? true : false); // une nouvelle information à chaque tour
      
         if (etat) { // etat = true
            if (f>=0.5) {
               etatS = true; //choix = etat
            }
            else {// f < 0.5 dissonance cognitive
                // si (random.getDouble() <= mu alors etat sinon !etat
               etatS = (random.getDouble() <= (1-mu) ?  true : false );
            }
         }
         else { // ! etat <=> etat = false
            if (f<=0.5) {
               etatS = false;//choix = etat
            }
            else {// f > 0.5 dissonance cognitive
                // if (random.getDouble() <= (1-mu)
                // then I keep on my private choice (!etat)
                // else I turn to majority's choice (etat)
               etatS = (random.getDouble() <= (1-mu) ? false :  true );
            }
         }
      }
   
      public void getInfo() {// est execute par ENeighborWorld je crois SC
         this.theta  = ((World) world).theta;       //cast on remplace Eworld=world par elem.World
         this.mu     = ((World) world).mu ;
         this.alea   = ((World) world).alea ;
         this.random = ((World) world).random;
      
      	//System.out.println("mon random est ... "+random.getClass().getName());
      
      }
   
   
      public void init() {
      
         etat = ( (random.getDouble() >= 0.5) ? true : false);
         etatS = etat;
      }
   
   
      public ArrayList getDescriptors()
      {
         descriptors.clear();
         descriptors.add(new BooleanDataDescriptor(this,"Etat","etat",etat,true));
         //descriptors.add(new DoubleDataDescriptor(this,"Theta","theta",theta,0,1,true,6));
         //descriptors.add(new DoubleDataDescriptor(this,"Mu","mu",mu,0,1,true,6));
         //descriptors.add(new DoubleDataDescriptor(this,"f","f",f,false));
         return descriptors;
      }
   
      public void inverseEtat() {
      
         etat = !etat;}
   
      public void setMu(double newMu) {
      
         mu = newMu;}
   
      public double getmu() {
      
         return mu;
      }
      public void setTheta(double newTheta) {
         theta = newTheta;;
      }
   
      public double gettheta() {
      
         return theta;
      }
   }
