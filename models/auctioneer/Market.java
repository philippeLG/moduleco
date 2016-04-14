/**
 * Title: Market.java
 * Description: medium applied to walrasian economic market
 * @author Philippe.legoff@enst-bretagne.fr, sebastien.chivoret@ensta.org revised denis.phan@enst-bretagne.fr
 * @version 1.4  February, 2004
 */

   package models.auctioneer;

// import java.util.ArrayList;
   import java.util.Iterator;

// import modulecoFramework.modeleco.EAgent;
   import modulecoFramework.modeleco.EWorld;
   import modulecoFramework.medium.Medium;

// import modulecoFramework.modeleco.SimulationControl;
/**
* This class, a medium, is an auctioneer-driven pure exchange market
* no exchange arises outside equilibrium. So, the only function of this market
* is to aggregates agents' excess demand and to be a medium of communication
* between the agents' and the auctioneer.
**/

   public class Market extends Medium {
      protected EWorld eWorld;
      protected double E1,E2;
      protected double p1, p2; 
      protected Auctioneer auctioneer;
   
      public Market () {
         super();
         //System.out.println(" MarketConstructor ");
      }
   
      public void setEWorld(EWorld e){
         eWorld=e;
      }
      public void setAuctioneer(Auctioneer a){
         auctioneer=a;
      }
   
      public void init(){
         //System.out.println("market.init()-DEBUT");
         marketOpen();
      
         /*for (Iterator i=this.getAgentsForRole("competitor").iterator();i.hasNext();){
            ((Competitor) i.next()).setMenuPrice();
         }*/
         //System.out.println("market.init()-FIN");
      }
   
      public double getE1(){
         E1=0;
         for (Iterator i=eWorld.iterator();i.hasNext();){
            E1+=((Agent) i.next()).getE1();
         }
         return E1;
      }
   
      public double getE2(){
      
         E2=0;
         for (Iterator i=eWorld.iterator();i.hasNext();){
            E2+=((Agent) i.next()).getE2();
         }
         return E2;
      }
      public double getP1(){
      
         return auctioneer.getP1();
      }
   
      public double getP2(){
         return auctioneer.getP2();
      }
      public double getK(){
         return auctioneer.getK();
      }
      public void marketOpen(){
      /*   
      //System.out.println("market.marketOpen()");
         p1= getP1();
         p2=getP2();
         Agent ag;
         double Y = 0;
         for (Iterator i=eWorld.iterator();i.hasNext();){
            ag =((Agent) i.next());
            ag.marketOpen();
            Y+=ag.getInitialIncome();
         }
      
         //((World)eWorld).setAgregateIncome(Y);
      */
      }
      public void marketClose(boolean test){
         //System.out.println("market.marketOpen()");
         double Y = 0;
         for (Iterator i=eWorld.iterator();i.hasNext();)
            Y+=((Agent) i.next()).getTransactionalIncome();
         ((World) eWorld).setAgregateIncome(Y);
         if (test)
            ((World) eWorld).simulationControl.stop();
      }
   }
