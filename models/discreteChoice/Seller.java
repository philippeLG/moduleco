/** class models.discreteChoice.Seller.java
 * Title:        Moduleco<p>
 * Description:  EAgent who represents one Seller
 * Copyright: Copyright (c)enst-bretagne
 * @author Denis.Phan@enst-bretagne.fr
 * @version 1.4  February, 2004
 */

   package models.discreteChoice;

   import java.util.ArrayList;

   import modulecoFramework.modeleco.EAgent;
   import modulecoFramework.modeleco.randomeco.CRandomDouble;
   import modulecoGUI.grapheco.descriptor.DoubleDataDescriptor;
   import modulecoGUI.grapheco.descriptor.IntegerDataDescriptor;
   import modulecoGUI.grapheco.descriptor.BooleanDataDescriptor;

// import modulecoGUI.grapheco.CentralControl;

   public class Seller extends EAgent {
   
   /**
    * state is the actual state . stateC is the computing state
    */
   /**
    * Competitor's data
   */
      protected double price, maxProfitPrice, profit, maxProfit ;// DP 20/08/2002
      protected double maxPrice = 1.26;//1.6 ;// DP 4/01/2003
      protected double minPrice = 0.9 ;// DP 4/01/2003
      protected int customers, oldCustomers, newCustomers, maxProfitCustomers, directChange, inducedChange ; // DP 20/08/2002
   /**
    * random
    */
      protected CRandomDouble random;
   
      protected Market market;
      protected boolean restartTest, newPriceStep;// DP 20/08/2002
      protected boolean maximize = true ;// DP 20/08/2002
      protected boolean priceDown = true ;// DP 4/01/2003
   /**
   * models.discreteChoice.Seller() is a seller in a monopoly market 
   */
      public Seller() {
      }
   /**
   * getInfo()
   * receieve Info from the Eworld Before connection and initialisation
   * @see modulecoFramework.modeleco.ENeighbourWorld
   *
   */
   
      public void getInfo() {
         //System.out.println("Seller.getInfo()");	
         //price=1.2;
      }
      public void setPreviousValues()//Inactif ?
      {
         //System.out.println("Seller.setPreviousValues()");
      }
   
   /**
   * int()
   * the Seller knows the markey (by the way of the world)
   * And send his price to the market  
   */
   
      public void init() {
         //System.out.println("Seller.init()");
         market = ((World) world).getMarket();
         if(priceDown)// DP 4/01/2003
            price=maxPrice;	// SP 20/01/2003 (old:) price=1.245;
         else
            price=minPrice; // 0.609
         market.takePrice(price);
         customers=0;
         newPriceStep = true ;
         profit=0;
         maxProfit=0;
         restartTest=false;// DP 20/08/2002
      }
       /**
      * compute()
      * send price to the market
     */
      public void compute() {
         market.takePrice(price);
      }
   	/**
      * commit()
      * get agregate demand (activity) from the market
      * @see models.discreteChoice.Market
     */
   
      public void commit() {
         //System.out.println(" Competitor.commit()DEBUT ");   
         market.takePrice(price);
         newCustomers = market.getCurrentCustomers();
         double newProfit = price*((double)newCustomers);
         if(maximize){// DP 20/08/2002
            //if (newCustomers!=oldCustomers){
               //System.out.println("price = ; "+price+" ; customers = ; "+newCustomers);	// SP 16/01/2003
            //}            
            if(newPriceStep)
               if (newCustomers==oldCustomers){ // no changes
                  //System.out.println("Price = "+price+" no changes"); 
                  nextPriceStep();
               }
               else { // first (direct) Change
                  directChange =newCustomers-oldCustomers;
                  newPriceStep=false;
                  System.out.println("Price = ; "+doubleFormated(price, 4)+" ; directChange \t= ; "+directChange+ "; marketSize \t= ; "+newCustomers);
               }
            else{ // behind the first price step : induced changes
               if (newCustomers==oldCustomers) {//the number of customer is stable : end of avalanches
                  if (newProfit>maxProfit){
                     maxProfit = newProfit;
                     maxProfitCustomers = newCustomers ;
                     maxProfitPrice=price;
                     //System.out.println("P = "+maxProfitPrice+" N = "+ maxProfitCustomers +" profit = "+maxProfit);
                  }
                  nextPriceStep();}
               else{ //the number of customer have changes (avalanche)
                  inducedChange = newCustomers-oldCustomers;
                  System.out.println("Price = ; "+doubleFormated(price, 4)+" ; inducedChange \t= ; "+inducedChange+ "; marketSize \t= ; "+newCustomers);
               }
            }
         
         }
         profit = newProfit;
         oldCustomers=newCustomers;
      
         if(priceDown)
            if(price < minPrice) //SP 15_01_2003
               restartTest = true ;
            else
               restartTest = false ;
         else
            if(price > maxPrice)//SP 15_01_2003
               restartTest = true ;
            else
               restartTest = false ;
      
      }
      public void nextPriceStep(){
         if(priceDown)
            price =price  - 0.0001;
         else
            price =price  + 0.0001;
         newPriceStep = true ;
      }
      public String doubleFormated(double x, int format){
         double decim;
         int dotIndex=0;
         String doubleString;
         String doubleStringFormated;
         decim = java.lang.Math.pow(10,format);
         doubleString = (new Integer((new Double(x*decim)).intValue())).toString();
         if (x >=1){
            dotIndex = ((new Double(x)).toString()).indexOf(".");
            doubleStringFormated = doubleString.substring(0,dotIndex)+".";
         }
         else doubleStringFormated = "0.";
         doubleStringFormated += doubleString.substring(dotIndex);
         return doubleStringFormated;
      }
   //==================== Information input output ===================
   /*
   *
   */
      public String toString() {
         return (new Integer(customers)).toString();
      }
      public Object getState() {
         return new Integer(customers);
      }
   
      public void setPrice(double d){
         price=d;
      }
   
   
      public void setProfit(double p){
      
         profit=p;
      }
   
      public double getPrice(){
         return price;
      }
   
      public void setCustomers(int c){
         //oldCustomers=customers;
         customers=c;
         // System.out.println("world.setCustomers()"+customers);
      }
   
   
      public void setMaximize(boolean m){
      
         maximize=m;
      }
   
   
      public void setPriceDown(boolean pd){ //DP 4/01/2003
         //System.out.println("Seller.setPriceDown()");
         priceDown=pd;
      }
      public void setMinPrice(double minP){ //DP 4/01/2003
         minPrice=minP;
      }
      public void setMaxPrice(double maxP){ //DP 4/01/2003
         maxPrice=maxP;
      }
   
      public ArrayList getDescriptors(){
         descriptors.clear();
      
         descriptors.add(new DoubleDataDescriptor(this,"price","price",price,true,4));
         descriptors.add(new IntegerDataDescriptor(this,"Customers","customers",customers,false));
         descriptors.add(new DoubleDataDescriptor(this,"profit","profit",profit,true,2));		// SP 16/01/2003
         descriptors.add(new BooleanDataDescriptor(this,"maximize","maximize",maximize,true));
         descriptors.add(new BooleanDataDescriptor(this,"priceDown","priceDown",priceDown,true));//DP 4/01/2003
         descriptors.add(new DoubleDataDescriptor(this,"minPrice","minPrice",minPrice,true,4));
         descriptors.add(new DoubleDataDescriptor(this,"maxPrice","maxPrice",maxPrice,true,4));
      
         return descriptors;
      }
   
      public void setDefaultValues(){//Inactif ?
         //System.out.println("Seller.setDefaultValues()");
         customers=0;
         profit=0; 
      }
   
      public boolean getRestartTest(){// DP 20/08/2002
         return restartTest;
      }
   
      public int getCustomers(){//DP 16/01/2003
         return customers;
      }
   
   }


