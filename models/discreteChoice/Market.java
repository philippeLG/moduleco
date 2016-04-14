 /* Source File Name:   discreteChoice.Market.java
 * Copyright:    Copyright (c)enst-bretagne
 * @author : Denis.Phan@enst-bretagne.fr, (from Philippe.legoff @version 1.0 du 10 aout 2000)
 * @version 1.4  February, 2004
 */

   package models.discreteChoice;

   import java.util.ArrayList;
   import java.util.Iterator;

// import modulecoFramework.modeleco.EAgent;
   import modulecoFramework.medium.Medium;

   public class Market extends Medium {
      public double price;          // consumption price
      public int nCustomers ;  //numberOfCustomers
      protected Seller seller;
      protected ArrayList customers ;
   /**
    * constructor
    */
      public Market () {
         super();
         //System.out.println(" MarketConstructor ");
      }
   /**
   * method called by agent.compute()
   * the market provide the usage price vector to each agents
   */
   
      public double getPrice(){
         //System.out.println(" market.getPrice ");
         return price;
      }
   	/**
   	* takePrice(double p)
   	*/
      public void takePrice(double p){
         this.price = p ;
      }
   
     /**
     * Agent services
     * buy() implemented by agent.commit()
     * add the agentID of the new buyers in the cursomers'ArrayList
     */
      public void buy(int agentID){
         if(!customers.contains( new Integer (agentID)))
            customers.add(new Integer(agentID)) ;
         seller.setCustomers(customers.size());
         //System.out.println(" market.buy() "+agentID);
      }
   	/*
     * Agent services
     * removeCustomer() implemented by agent.commit()
     * remove the agentID of disadopters in the cursomers'ArrayList
     * rem : not useful in deterministic preferences models
     * useful in stochastic preferences models
     */
      public void removeCustomer(int agentID){
         customers.remove(new Integer(agentID)) ;
         seller.setCustomers(customers.size());
         //System.out.println(" market.remove() "+agentID);
      }
     /**
     * get number of Customers in (t-1)
     * method called from agent.computeEta()
     */
      public int getNCustomers(){ 
         return nCustomers ;
      }
   /**
     * get number of Customers in (t)
     * method called from seller.commit()
     */
      public int getCurrentCustomers(){
         int currentCustomers;
         try{
            currentCustomers = customers.size() ;
         }
            catch (Exception e){
               currentCustomers =0;
               //java.lang.NullPointerException
            }
         return currentCustomers ;
      }
   
    /*
     * Market initialization
     */
      public void init(){
         //System.out.println("market.init()-DEBUT");
         marketClear();
         for (Iterator j=this.getAgentsForRole("Seller").iterator();j.hasNext();)
            seller =(Seller)j.next();
         price = seller.getPrice();
         for (Iterator i=this.getAgentsForRole("customer").iterator();i.hasNext();)
            ((Agent) i.next()).marketOpen();
         customers = new ArrayList();
         //System.out.println("market.init()-FIN");
      }
   /**
   * Clear All market data : 
   * implemented from world.commit()
   */
   // il y a 2 market clear a chaque tour voir  ?
      public void marketClear(){
         {
            //N = 0;
            price = 0;
            try{
               nCustomers = customers.size() ;
            }
            
               catch (Exception e){
                  nCustomers =0;
               //java.lang.NullPointerException
               }
         //System.out.println("marketClear()");
         
         }
      }
   }