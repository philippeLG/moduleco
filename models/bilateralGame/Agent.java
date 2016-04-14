/**
 * Title:       bilateralGame.Agent<p>
 * Description:  Chaque Agent joue avec ses voisins et adopte, pour le tour suivant, la
 * statégie de celui qui a le meilleur payoff. Le payoff est réinitialisé à chaque tour.
 * La matrice de payoff est la même pour tous les Agents ; elle est enregistré par le World.<p>
 * Copyright:    Copyright (c)enst-bretagne
 * @author Antoine.Beugnard@enst-bretagne.fr, Denis.Phan@enst-bretagne.fr
 * @version 1.4  February, 2004
 */
   package models.bilateralGame;

   import java.util.Iterator;
   import java.util.ArrayList;

   import modulecoFramework.abstractModels.EAgentGame ;

   import modulecoGUI.grapheco.descriptor.IntegerDataDescriptor;
   import modulecoGUI.grapheco.descriptor.DoubleDataDescriptor;
// import modulecoGUI.grapheco.descriptor.BooleanDataDescriptor;
   import modulecoGUI.grapheco.descriptor.InfoDescriptor;


   public class Agent extends EAgentGame
   {
      //protected String revisionRuleIndex;
      protected double finalAveragePayoff1 ;
      protected double finalAveragePayoff2 ;
   
      public Agent (){
         super();
      }
   
      public void init() {
         super.oldStrategy = ((world.getIndex(this) == (world.getAgentSetSize()/2)) ? false : true);
         super.init();
      }
   
      public void compute()
      {
         oldStrategy = newStrategy;// on stocke la stratégie qu'on a joué
         super.payoff = 0; // on reinitialise le payoff !  
      // play
         for (Iterator i= neighbours.iterator();i.hasNext();) {
         
            super.payoff += payoffMatrix [(newStrategy? 0: 1)] [(((Agent) i.next()).newStrategy? 0: 1)];
         }
      }
   
      public void commit()
      {
      	// decide sa strategie
         revisionRuleIndex =  ((World) world).getRevisionRuleIndex();
         if (revisionRuleIndex.equalsIgnoreCase("Last neighbourhood best payoff")){
            ComputeBestPayoff(payoff);
         }
         if (revisionRuleIndex.equalsIgnoreCase("Last neighbourhood best average payoff"))
         {
            ComputeBestAveragePayoff(payoff);
         }
         hasChanged = (newStrategy != oldStrategy);
      }
   
   
   
      public ArrayList getdescriptors()
      {
         descriptors=super.getDescriptors();
         descriptors.add(new IntegerDataDescriptor(this,"Total AgentPayoff","payoff",payoff,0,10,true));
         descriptors.add(new DoubleDataDescriptor(this,"Average Payoff S1","finalAveragePayoff1",finalAveragePayoff1,true,6));
         descriptors.add(new DoubleDataDescriptor(this,"Average Payoff S2","finalAveragePayoff2",finalAveragePayoff2,true,6));
         //descriptors.add(new BooleanDataDescriptor(this,"New Strategy","newStrategy",newStrategy,true));
         //descriptors.add(new BooleanDataDescriptor(this,"Old Strategy","oldStrategy",oldStrategy,false));
         descriptors.add(new InfoDescriptor(revisionRuleIndex));
         return descriptors;
      }
   
      public void setGain(int g){
         payoff=g;}
      public void setFinalAveragePayoff1(double f){
         finalAveragePayoff1=f;}
      public void setFinalAveragePayoff2(double f){
         finalAveragePayoff2=f;}
   
   }
