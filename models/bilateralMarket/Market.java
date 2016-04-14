/**
 * Title: bilateralMarket.java
 * Description: medium applied to walrasian economic market
 * @author denis.phan@enst-bretagne.fr, sebastien.chivoret@ensta.org
 * @version 1.4  February, 2004
 */

   package models.bilateralMarket;

// import java.util.ArrayList;
// import java.util.Iterator;

// import modulecoFramework.modeleco.EAgent;
   import modulecoFramework.modeleco.EWorld;
   import modulecoFramework.medium.Medium;
/**
* This class, a medium, is an random-pairwise out of equilibrium pure exchange market
* exchange arises at any point of time, generally outside of walrassian equilibrium.
**/

   public class Market extends Medium {
      protected EWorld eWorld;
      protected double x11,x21,x12,x22,p1,x11_new,x21_new,x12_new,x22_new,alpha,epsilon,size;
      //protected Auctioneer auctioneer;
   
      public Market () {
         super();
         //System.out.println(" MarketConstructor ");
      
      }
   
      public void setEWorld(EWorld e){
         eWorld=e;
         size=eWorld.getAgentSetSize();
         //System.out.println("size : "+size);
      }
   
   
      public void init(){
         alpha=((World) eWorld).getAlpha();
         epsilon=((World) eWorld).getEpsilon();
         //System.out.println("market.init() - getAlpha()"+alpha);
      }
   
   
   
      public void deal(Agent agent1, Agent agent2){
         //if((agent1.getAgentID()==0 || agent1.getAgentID()==2)&&(eWorld.getIter()==0 || eWorld.getIter()==1))
         //System.out.print("deal between "+agent1.getAgentID()+" and "+agent2.getAgentID()+"  t ="+eWorld.getIter());
         x11=agent1.getX1();
         x12=agent1.getX2();
         x21=agent2.getX1();
         x22=agent2.getX2();
         String utility = ((World) eWorld).getUtility() ;
         if(utility.equals("Cobb Douglas")){
            p1=alpha*(x12+x22)/(x11+x21)/(1-alpha);}
         else{p1=java.lang.Math.pow(alpha*(x12+x22)/(x11+x21)/(1-alpha),(1/epsilon));}//REVOIR
      		   //System.out.println(" at price = "+p1);
         agent1.exchange(p1);
         agent2.exchange(p1);
      	//agent1.getTransactionalIncome();
         //agent2.getTransactionalIncome();
      }
   }
