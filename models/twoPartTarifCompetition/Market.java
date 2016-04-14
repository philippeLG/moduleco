/**
 * Title: medium.Market.java
 * Description: medium concept applied to economic market
 * @author Philippe.legoff@enst-bretagne.fr
 * @version 1.2  august,5, 2002
* @version 1.4. february 2004    
 */

   package models.twoPartTarifCompetition;

   import java.util.ArrayList;
   import java.util.Iterator;

   import modulecoFramework.medium.Medium;

   public class Market extends Medium {
      protected int nbCompetitors ;		// Number of competitors on the market
      public double Q[];              // agregate consumption per competitor
      public int N[];                 // number of subscriptors (customers) per competitor
      public double price[];          // consumption price per competitor
      public double subscription[];   // subscription price per competitor
   
   /**
    * constructor
    */
      public Market () {
         super();
         //System.out.println(" MarketConstructor ");
      }
   
    /*
     * Competitor services
     */
      public double getDemand(int competitorID){
         //System.out.println("market.getDemand() Q["+(competitorID-1)+"] = "+Q[competitorID-1]);
         return Q[competitorID-1];
      }
      public int getCustomers(int competitorID){
         //System.out.println("market.Customers()"+" N("+competitorID+") = "+N[competitorID-1]);
         return N[competitorID-1];
      }
   /**
   * method called by agent.compute()
   * the market provide the subscription vector to each agents
   */
   
      public double[] getSubscription(){
         //System.out.println(" market.getSubscription()");
         return subscription;
      }
   /**
   * method called by agent.compute()
   * the market provide the usage price vector to each agents
   */
   
      public double[] getPrice(){
         //System.out.println(" market.getPrice ");
         return price;
      }
   
      public void givePrice(double p, double s, int competitorID) {
         //System.out.println(" market.givePrice ");
         price[competitorID-1] = p;
         subscription[competitorID-1] = s;
      }
   
    /*
     * Agent services
     * buy() implemented by agent.commit()
     */
      public void buy(int agentID, int newCompetitorID, double q){
         if (newCompetitorID>0) {
            Q[newCompetitorID-1] += q;
            N[newCompetitorID-1]++;
            //if (agentID==0)
               //System.out.println(" market.buy() to agent " + agentID + " buy "+ q + " to " + newCompetitorID + "; total "+Q[newCompetitorID-1]);
         }
      }
   
    /**
     * get number of competitors
     * method called from init(), agent.compute()
     */
   // l'appel depuis agent.compute() a chaque tour ralentit le temps de calcul :
   // discuter pour voir s'il ne vaut mieux pas le faire dans agent.int()ou agent.getInfo() 
      public int getNbCompetitors () {
         int nbCompetitors =((ArrayList) refTable.get("competitor")).size();
         //System.out.println(" market.getNbCompetitors () / return : "+nbCompetitors);
         return nbCompetitors ;
      }
    /*
     * Market initialization
     */
      public void init(){
         //System.out.println("market.init()-DEBUT");
         nbCompetitors = getNbCompetitors ();
         price = new double[nbCompetitors];
         subscription = new double[nbCompetitors];
         Q = new double[nbCompetitors];
         N = new int[nbCompetitors];
      
         marketClear();
         for (Iterator i=this.getAgentsForRole("competitor").iterator();i.hasNext();){
            ((Competitor) i.next()).setMenuPrice();
         }
         for (Iterator i=this.getAgentsForRole("customer").iterator();i.hasNext();)
            ((Agent) i.next()).marketOpen();
         //System.out.println("market.init()-FIN");
      }
   /**
   * Clear All market data : 
   * quantities Qi, Ni,
   * prices pi, Ai 
   * for all competitors
   * implemented from world.commit()
   */
   // il y a 2 market clear a chaque tour voir :
      public void marketClear(){
         for(int i=0 ; i< (nbCompetitors); i++){
            Q[i] = 0;
            N[i] = 0;
            price[i] = 0;
            subscription[i] = 0;
            //System.out.println("marketClear()");
         // marketClear() est implémenté 2 fois : a voir...
         }
      }
      //public void test(double p, double s, int competitorID){
      //}
    /**
     * send informations
     *
     * get informations
     *
     */
   }
