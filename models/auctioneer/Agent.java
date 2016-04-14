/** class auctioneer.Agent.java
 * Title: Moduleco<p>
 * Description:
 * Copyright:    Copyright (c)enst-bretagne
 * @author denis.Phan@enst-bretagne.fr , Philippe LeGoff, sebastien.chivoret@ensta.org revised denis.phan@enst-bretagne.fr
 * @version 1.4  February, 2004
 */

   package models.auctioneer;

//   import java.util.Iterator;
// import java.util.Vector;
   import java.util.ArrayList;
//  import java.lang.Math;

// import modulecoGUI.grapheco.descriptor.DataDescriptor;
 import modulecoGUI.grapheco.descriptor.DoubleDataDescriptor;
// import modulecoGUI.grapheco.descriptor.BooleanDataDescriptor;
// import modulecoGUI.grapheco.descriptor.IntegerDataDescriptor;
   import modulecoFramework.modeleco.EAgent;
   import modulecoFramework.medium.NeighbourMedium;
// import modulecoFramework.medium.Medium;

   import modulecoFramework.modeleco.randomeco.CRandomDouble;
/**
* This class is an agent in a walrassian pure exchange market with auctioneer
**/

   public class Agent extends EAgent {
   
      protected Market market;
      protected double epsilon, alpha,a1, a2, p1, p2,x1_d,x2_d,x1_0,x2_0,e1,e2;
      protected double initialUtility, currentUtility, transacIncome, test;
      protected CRandomDouble random;
      private String utilityForm;
      public Agent() {
         super();
         //if (agentID==0)
            //System.out.print(" agent.constructeur");
      
      }
   
      public void getInfo() {
      
         //if (agentID==0)
            //System.out.println(" agent.getInfo() ");
      
      }
   
      public void init() {
         //if (agentID==0)
            //System.out.println(" agent.init() ");
      
         this.random = ((World) world).random;
      
         neighbours = ((NeighbourMedium) mediums[0]).getNeighbours();
         market = ((World) world).getMarket();
         x1_0=random.getDouble();			//the initial quantity of product 0
         x2_0=random.getDouble();			//the initial quantity of product 1
         x1_d=0;
         x2_d=0;
         // Excess demand
         e1=0;
         e2=0;
      	// Part of the (real) income in consumption 
         a1=((World) world).getAlpha();
         a2=1-a1;
         epsilon=((World) world).getEpsilon();
         utilityForm = ((World)world).getUtility() ;
      
         initialUtility=computeUtility(utilityForm, x1_0,x2_0,a1,a2,epsilon);
      
         currentUtility = initialUtility;
         //if(agentID==0)System.out.print("U0 = "+currentUtility);
         //if(agentID==1)System.out.println("; U1 = "+currentUtility);      
      
      }
   
      public double computeUtility(String utilityForm, double x1,double x2,double a1,double a2, double epsilon){
         double utility;
      
         if(utilityForm.equals("Cobb Douglas")){
            utility = java.lang.Math.pow(x1,a1)*
               java.lang.Math.pow(x2,a2);
         }
         else{
         
            utility = java.lang.Math.pow(a1,1/epsilon)*
               java.lang.Math.pow(x1,1-1/epsilon)
               +java.lang.Math.pow(a2,1/epsilon)*
               java.lang.Math.pow(x2,1-1/epsilon);
         }
         return utility ;
      }
   
      public void marketOpen(){
         if (agentID==0)
            System.out.println(" agent.marketOpen() ");
         getPrices();
      }
      public void computeDemand(String utilityForm, double R, double p1,double p2, double a1,double a2, double epsilon){
         if(utilityForm.equals("Cobb Douglas")){
            x1_d=a1*R/p1;
            x2_d=a2*R/p2;
         }
         else{
            double priceIndex = a1*java.lang.Math.pow(p1,1-epsilon)
            +a2*java.lang.Math.pow(p2,1-epsilon);
            x1_d=a1*R/java.lang.Math.pow(p1,epsilon)/priceIndex ;
            x2_d=a2*R/java.lang.Math.pow(p2,epsilon)/priceIndex ;
         
           /*
         	double num = ((java.lang.Math.pow(p1,1-epsilon))+(java.lang.Math.pow(p2,1-epsilon)));
            x1_d=R/(java.lang.Math.pow(p1,epsilon))/num ;
            x2_d=R/(java.lang.Math.pow(p2,epsilon))/num ;
         	*/
         
         }
      }
   
      public void compute() {
         //if (agentID==0)
            //System.out.println(" agent.compute() ");
         getPrices();
         double R = getIncome();
         computeDemand(utilityForm, R, p1, p2, a1, a2, epsilon);
         e1=x1_d-x1_0;
         e2=x2_d-x2_0;
         //if(agentID==0)System.out.println("x1_d = "+x1_d+"x2_d = "+x2_d);
      }
   
      public void commit() {
      
         currentUtility=computeUtility(utilityForm, x1_d,x2_d,a1,a2,epsilon);
      
      }
   
   //=========================
   
   
      public Object getState() {
         //System.out.println(" agent.getState() ");
         return new Integer(1);
      }
   
   
      public boolean getBooleanState() {
      
         return true;
      }
      public void inverseState() {           
      
      }
   
   
      public ArrayList getDescriptors(){
      
         descriptors.clear();
         //descriptors.add(new DoubleDataDescriptor(this,"alpha","alpha",alpha,true,3));
         descriptors.add(new DoubleDataDescriptor(this,"x1 initial","x1_0",x1_0,false,4));
         descriptors.add(new DoubleDataDescriptor(this,"x2 initial","x2_0",x2_0,false,4));
         descriptors.add(new DoubleDataDescriptor(this,"x1 demande","x1_d",x1_d,false,4));
         descriptors.add(new DoubleDataDescriptor(this,"x2 demande","x2_d",x2_d,false,4));
         descriptors.add(new DoubleDataDescriptor(this,"e1","e1",e1,false,4));
         descriptors.add(new DoubleDataDescriptor(this,"e2","e2",e2,false,4));
         descriptors.add(new DoubleDataDescriptor(this,"Initial utility","initialUtility",initialUtility,false,6));
         descriptors.add(new DoubleDataDescriptor(this,"Current utility","currentUtility",currentUtility,false,6));
         descriptors.add(new DoubleDataDescriptor(this,"Transac.Income","transacIncome",transacIncome,false,6));
         return descriptors;
      
      }
   
      public void setAlpha(double d){
         alpha=d;
      }
   
   	/*
   	public double getAlpha(){
      
         return alpha;
      }
   */
      public double getX1_d(){
      
         return x1_d;
      }
   
      public double getX2_d(){
      
         return x2_d;
      }
      public double getX1_0(){
         return x1_0;
      }
      public double getX2_0(){
      
         return x2_0;
      }
      public double getE1(){
      
         return e1;
      }
   
      public double getE2(){
      
      
      
         return e2;
      }
   
   
      public void getPrices(){
         p1=((Market)market).getP1();
         p2=((Market)market).getP2();	
      }
   
   
      protected void setRandom(CRandomDouble random) {
      
         //System.out.println(" agent.setRandom() ");
         this.random = random;
      }
      public double getInitialUtility(){
         return initialUtility;
      }
   
   
      public double getCurrentUtility(){
         return currentUtility;
      }
   
      public double getTransactionalIncome(){
      
         double Y = x2_d*p2+x1_d*p1;
         setTransactionalIncome(Y);
         //System.out.println("Agent "+agentID+" Income = "+Y);
         return Y ;
      }
   
      public double getIncome(){
      
         double Y = x2_0*p2+x1_0*p1;
         return Y ;
      }
      public void setTransactionalIncome(double Y){
         transacIncome = Y;
      }
   }