/**
 * Title:   bilateralMarket.Agent
 * * Copyright:    Copyright (c)enst-bretagne
 * @author denis.phan@enst-bretagne.fr
* @version 1.4  February, 2004
*  
*/
   package models.bilateralMarket;

// import java.util.Iterator;
   import java.util.ArrayList;

   import modulecoFramework.modeleco.EAgent;
   import modulecoGUI.grapheco.descriptor.DoubleDataDescriptor;
// import modulecoGUI.grapheco.descriptor.BooleanDataDescriptor;
// import modulecoGUI.grapheco.descriptor.IntegerDataDescriptor;
// import modulecoGUI.grapheco.descriptor.InfoDescriptor;
   import modulecoFramework.modeleco.randomeco.CRandomDouble;
// import modulecoFramework.modeleco.randomeco.Default;
// import modulecoGUI.grapheco.ControlPanel;

// import modulecoFramework.medium.Medium;
   import modulecoFramework.medium.NeighbourMedium;
// import modulecoFramework.modeleco.zone.RandomPairwise ;

// import modulecoFramework.modeleco.randomeco.CRandomDouble;
/**
* This class is an agent in a random pairwise out of equilibrium pure exchange market
*/
   public class Agent extends EAgent
   {
   /**
   *
   */
      protected double alpha, epsilon, a1, a2, R, p1,p2, x1_0, x2_0, x1, x2, x1_new, x2_new ;
      protected double initialUtility, currentUtility, transactionalIncome;
      private String utilityForm;
   
   /**
   *
   */
      protected CRandomDouble randomDot;
   /**
   *
   */
      protected Market market;
   /**
   *
   */
      protected boolean state ;
      public Agent ()
      {
         super();
      }
   /**
   *
   */
      public void init() {
         this.randomDot = ((World) world).randomDot;
         //random=new Default();
         market = ((World) world).getMarket();
         alpha=((World) world).getAlpha();
         p1=1;
         p2=1;
         x1_0=randomDot.getDouble(); //the initial dotation of product 1
         x2_0=randomDot.getDouble(); //the initial dotation of product 2
         x1=x1_0; //current x1;
         x2=x2_0; //current x2;
         state = true;
         a1=((World) world).getAlpha();
         a2=1-a1;
         epsilon=((World) world).getEpsilon();
         utilityForm = ((World)world).getUtility() ;
      
         initialUtility=computeUtility(utilityForm, x1_0,x2_0,a1,a2,epsilon);
      
         currentUtility = initialUtility;
      
      }
      public double computeUtility(String utilityForm, double x1,double x2,double a1,double a2, double epsilon){
      
         double utility;
      
         if(utilityForm.equals("Cobb Douglas")){
            utility = java.lang.Math.pow(x1,a1)*
               java.lang.Math.pow(x2,a2);
         }
         else{
            //epsilon=((World) world).getEpsilon();//the elasticity of the demand
            //a1=1;
            //a2=1;
            utility = java.lang.Math.pow(a1,1/epsilon)*
               java.lang.Math.pow(x1,1-1/epsilon)
               +java.lang.Math.pow(a2,1/epsilon)*
               java.lang.Math.pow(x2,1-1/epsilon);
         }
         return utility ;
      }
   
   /**
   *
   */
      public void compute()
      {
         neighbours = ((NeighbourMedium) mediums[0]).getNeighbours();
      //REVOIR CELA
         //getPrices();
         /*
      	x1_d=alpha*(x1+x2*p2/p1);
         x2_d=(1-alpha)*(x2+x1*p1/p2);
         e1=x1_d-x1;
         e2=x2_d-x2;
      */
         market.deal(this, (Agent)neighbours.get(0)); // modifié DP le 10/07/2002
      
      }
   
      public void exchange(double p1){
         this.p1=p1;
         R=x1*p1+x2*p2;
      
         if(utilityForm.equals("Cobb Douglas")){
            x1_new=a1*(R/p1);
            x2_new=a2*(R/p2);
         }
         else{
            double priceIndex = a1*java.lang.Math.pow(p1,1-epsilon)
            +a2*java.lang.Math.pow(p2,1-epsilon);
            x1_new=a1*R/java.lang.Math.pow(p1,epsilon)/priceIndex ;
            x2_new=a2*R/java.lang.Math.pow(p2,epsilon)/priceIndex ;
         	/*
         	double num = ((java.lang.Math.pow(p1,1-epsilon))+(java.lang.Math.pow(p2,1-epsilon)));
            x1_new=(java.lang.Math.pow(p1,1-epsilon))*(x1+x2*p2/p1)/num ;
            x2_new=(java.lang.Math.pow(p2,1-epsilon))*(x2+x1*p1/p2)/num ;
         	*/
         }
      
      }
   
      public void commit(){
         x1 = x1_new ;
         x2 = x2_new ;
         double average=((World)world).getAveragePrice();
         state = (average>p1);
         currentUtility=computeUtility(utilityForm, x1,x2,a1,a2,epsilon);
         transactionalIncome = getTransactionalIncome();
         //if(agentID ==0 ||agentID ==1)
            //System.out.println("agent : "+agentID+ " transactionalIncome = "+transactionalIncome+" currentUtility = "+currentUtility);
      	/*
         if(utilityForm.equals("Cobb Douglas")){
            currentUtility = java.lang.Math.pow(x1,alpha)*java.lang.Math.pow(x2,1-alpha);
         }
         else{
            currentUtility = (java.lang.Math.pow(x1,1-1/alpha)+java.lang.Math.pow(x2,1-1/alpha));
         }
      	*/
      }
   /**
   *
   */                     
   
      public Object getState()
      {
         return new Boolean(state);
      }
   
      public void inverseState(){
      
      }
      public void getInfo(){
      
      }
      public ArrayList getDescriptors()
      {
         descriptors.clear();
         descriptors.add(new DoubleDataDescriptor(this,"initial dotation X1 : " ,"x1_0" ,x1_0,false ,4 ));
         descriptors.add(new DoubleDataDescriptor(this,"initial dotation X2 : " ,"x2_0" ,x2_0 ,false ,4));
         descriptors.add(new DoubleDataDescriptor(this,"P1 (P2 = 1)" ,"p1" ,p1 ,false ,4));
         //descriptors.add(new DoubleDataDescriptor(this,"Alpha" ,"alpha" ,alpha ,false ,3));
         descriptors.add(new DoubleDataDescriptor(this,"current x1 ","x1",x1,false,4));
         descriptors.add(new DoubleDataDescriptor(this,"current x2 ","x2",x2,false,4));
         descriptors.add(new DoubleDataDescriptor(this,"Initial utility","initialUtility",initialUtility,false,6));
         descriptors.add(new DoubleDataDescriptor(this,"Current utility","currentUtility",currentUtility,false,6));
         descriptors.add(new DoubleDataDescriptor(this,"Transac.Income","transactionalIncome",transactionalIncome,false,6));
         return descriptors;
      }
   
      public double getX1(){
         return x1;
      }
   
      public double getX2(){
         return x2;
      }
   
      public double getP1(){
         return p1;
      }
      public void setX1(double d){
         x1=d;
      }
      public void setX2(double d){
         x2=d;
      }
      public void setP1(double d){
         p1=d;
      }
   
      protected void setRandom(CRandomDouble randomDot) {
         //System.out.println(" agent.setRandom() ");
         this.randomDot = randomDot;
      }
   /*
      public void setAlpha(double d){
         alpha=d;
      }
   
      public double getAlpha(){
         return alpha;
      }
   
      public void setEpsilon(double d){
      
         Epsilon=d;
      }
   
   
      public double getEpsilon(){
      
         return Epsilon;
      }
   	*/
      public double getInitialUtility(){
         return initialUtility;
      }
   
      public double getCurrentUtility(){
         return currentUtility;
      }
   
      public double getTransactionalIncome(){
         double Y = x2*p2+x1*p1;
         return Y ;
      }
   }
