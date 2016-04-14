/** class tarifBinome.Agent.java
 * Title:        Moduleco<p>
 * Description:  Je suis un EAgent.
 * Mon monde est un tarifBinome.World.<p>
 * Copyright:    Copyright (c)enst-bretagne
 * @author denis.phan@enst-bretagne.fr, Antoine.Beugnard@enst-bretagne.fr
 * @version 1.2  august,5, 2002
* @version 1.4. february 2004   
 */
   package models.tarifBinome;

   import java.util.Iterator;
   import java.util.ArrayList;

   import modulecoFramework.modeleco.EAgent;
   import modulecoFramework.modeleco.randomeco.CRandomDouble;
// import modulecoGUI.grapheco.descriptor.DataDescriptor;
   import modulecoGUI.grapheco.descriptor.DoubleDataDescriptor;
   import modulecoGUI.grapheco.descriptor.BooleanDataDescriptor;
// import modulecoGUI.grapheco.descriptor.IntegerDataDescriptor;

   import modulecoFramework.medium.NeighbourMedium;


   public class Agent extends EAgent {
   /**
   *Agent variables and idiosyncrasic parameters
   *============================================
   * state is the actual agent' state (true if it is a subscriptor.
   * stateC is the computing state
   */
      protected boolean state, stateC, previouState, refractaire, hasChanged;
   /**
   * idosyncrasics Agent's parameters
   */
      protected double Theta, Epsilon, Alpha ;
   /**
   * optimal comsumption
   */
      protected double q;
   /**
   * minimal threshold for to adopt
   */
      protected double ThetaMin ;
   /**
   * initial adoption probability for the agent
   * conditioned by expectations
   * activate only if  alea = true
   */
      protected double EarlyAdopt ;
      protected boolean alea ;
   /**
   *Environmental variables
   *=======================
   * market values
   *==============
   * subscription and usage price from (the market / supplier)
   */
      protected double A, P ;
   /**
   * social network information
   *===========================
   * proportion of neighbours wich are customers
   */
      protected double N;
   /**
   *=============
   * random value
   */
      protected CRandomDouble random;
   //Part I - Initialisation of the agent =================
   /**
   * COMMENTER LE CONSTRUCTEUR
   */
      public Agent () { // initialisation in init()
         super();
      }
   /**
   * COMMENTER
   */
      public void getInfo() {
         this.EarlyAdopt = ((World) world).EarlyAdopt;
         this.Alpha      = ((World) world).Alpha;
         this.Epsilon    = ((World) world).Epsilon;
         this.alea       = ((World) world).alea ;
         this.random     = ((World) world).random;
      }
   /**
   * COMMENTER
   */           
      public ArrayList getDescriptors()
      {
         descriptors.clear();
         descriptors.add(new BooleanDataDescriptor(this,"Etat","state",state,true));
         descriptors.add(new BooleanDataDescriptor(this,"Has changed","hasChanged",hasChanged,false));
         descriptors.add(new BooleanDataDescriptor(this,"Refractaire","refractaire",refractaire,true));
         descriptors.add(new DoubleDataDescriptor(this,"Theta","Theta",Theta,true,6));
         descriptors.add(new DoubleDataDescriptor(this,"Theta min","ThetaMin",ThetaMin,false,6));
         descriptors.add(new DoubleDataDescriptor(this,"Alpha","Alpha",Alpha,true,6));
         descriptors.add(new DoubleDataDescriptor(this,"Epsilon","Epsilon",Epsilon,true,6));
         descriptors.add(new DoubleDataDescriptor(this,"q","q",q,false,6));
         descriptors.add(new BooleanDataDescriptor(this,"alea","alea",alea,false));
         return descriptors;
      }
   /**
   * 
   */
      public void init()
      {
         neighbours = ((NeighbourMedium) mediums[0]).getNeighbours();
      
         Theta = random.getDouble();
         marketOpen();
      }
   /**
   * 
   */
      public void marketOpen(){
         this.A          = ((World) world).A;
         this.P          = ((World) world).P;
         ThetaMin = threshold(1);
         refractaire = (  Theta < ThetaMin ? true : false);
        //refractaire = (  random.getDouble() <= 0.01 ? true : false);
      
         if (!refractaire)
            if (alea){
               if(random.getDouble() <= EarlyAdopt)
                  state = expectations();}
            else
               state = expectations();
            //state = (random.getDouble() <= EarlyAdopt  ? true : false);
         else
            state = false;
      
         stateC = state;
      }
   // Part II - Agent's computations
   /**
   *
   */
      public double threshold(double N){
         double a = (Epsilon-1)*A/java.lang.Math.pow(P,1-Epsilon)/java.lang.Math.pow(N,Epsilon);
         double b = 1/Alpha/Epsilon;
         double threshold = java.lang.Math.pow(a,b);
      
         return threshold;
      }
   /**
   *
   */
      public boolean expectations(){
         double neighbourTheta ;
         boolean neighbourState = false ;
         for (Iterator i= neighbours.iterator();i.hasNext();) {
            neighbourTheta =((Agent) i.next()).getTheta();
            if (neighbourState == false)
               neighbourState = (  neighbourTheta >= threshold(1/(double)neighbours.size()) ? true : false);
         }
         return neighbourState;
      }
   /**
   *
   */
      public double getNeighboursProportion(){
         int nbAb = 0;
      
         for (Iterator i= neighbours.iterator();i.hasNext();) {
            if (((Agent) i.next()).state) {//previouState
               nbAb++;
            }
         }
         return ((double)nbAb / (double)neighbours.size());
      }
   /**
    * Return the final state
    */
      public Object getState() {
         return new Boolean(stateC); // pour affichage c'est celui là
      }
   /**
   *
   */
      public void compute() { // fait évoluer l'state
         N =getNeighboursProportion();
      
      
         if (!refractaire)
            stateC = (  Theta >= threshold(N) ? true : false);
      
         if(stateC)
            q =  java.lang.Math.pow(( P / N * java.lang.Math.pow(Theta,Alpha) ) , -Epsilon);
         else
            q=0;
      }
   /**
   *
   */
      public void commit() { // valide le changement d'state
         hasChanged = (state!=stateC);
         previouState = state;
         state = stateC;
      
        //if (ae != null) ae.update ();
      }
   // Part III - Data Exchanges =================
   /**
   *
   */
      public void setTheta(double T) {
      
         Theta =T;
      }
      public double getTheta() {
      
         return Theta;
      }
   
      public void setP(double q) {
      
         if (q >=0 && q <= 1) P =q;
      
      }
   
      public void setA(double t) {
      
         if (t >=0 && t <= 1) A =t;
      }
   
      public void setEarlyAdopt(double e) {
      
         if (e >=0 && e <= 1)  {EarlyAdopt =e;
         
         }
      }
   
      public void setAlpha(double q) {
      
         if (q >=0 && q <= 1) Alpha =q;
      }
   
   
      public void setEpsilon(double q) {
      
         Epsilon =q;
      }
   
   
      public void inverseState() {
      
         state = !state; stateC = state;
         //if (ae != null) ae.update();
      }
   
   
      public void inverseRefractaire() {
      
         refractaire = !refractaire;
      
      }
      public void inverseEtat() {
      
         state = !state; stateC = state;
         //if (ae != null) ae.update();
      }
   
   
      public Boolean hasChanged()
      
      {
         boolean temp;
      
         temp = hasChanged;
         hasChanged = false;
         return new Boolean(temp);
      }
   
   
      public String toString() {
      
         return (new Boolean(state)).toString();
      }
   
      public void setRandom(CRandomDouble r){
         random=r;
      }
   }
