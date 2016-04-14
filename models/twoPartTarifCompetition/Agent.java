/** class market.Agent.java
 * Title:        Moduleco<p>
 * Description:
 * Copyright:    Copyright (c)enst-bretagne
 * @author denis.Phan@enst-bretagne.fr , Philippe LeGoff
 * @version 1.2  august,5, 2002
* @version 1.4. february 2004    
 */

   package models.twoPartTarifCompetition;

   import java.util.Iterator;
   import java.util.Vector;
   import java.util.ArrayList;

   import modulecoFramework.modeleco.EAgent;
   import modulecoFramework.modeleco.randomeco.CRandomDouble;
// import modulecoGUI.grapheco.descriptor.DataDescriptor;
   import modulecoGUI.grapheco.descriptor.DoubleDataDescriptor;
   import modulecoGUI.grapheco.descriptor.BooleanDataDescriptor;
   import modulecoGUI.grapheco.descriptor.IntegerDataDescriptor;

   import modulecoFramework.medium.NeighbourMedium;
 
   public class Agent extends EAgent {
    /**
     * state is the actual state . stateC is the computing state
     */
      protected int  state, stateC, previouState ;    // values : 0 nothing, 1 first company, 2 second company ....
      /**
   	*
   	*/
      protected boolean refractaire, subscriber ; // commentaires
      protected ArrayList choice ;      // list of chosen competitors
      protected int numCompetitor, nbCompetitors ; // id & number of competitors
     /**
     * subscription and usage price
     */
      protected double[] subscription, price;
   /**
     * Proportion of neighbours
     */
      protected double[] N;
   
      protected double NChoice; // temporary value 
   /**
     * idosyncrasics Agent's parameters
     */
      protected double theta, epsilon, alpha ;
   	/**
   	*
   	*/
      protected double thetaMin; // revoir le statut de cette variable
   /**
     * Initial probability to adopte
     */
      protected Vector earlyAdopters; // revoir le statut de cette variable
   
    /**
     * test proportion of neighbours (true : all competitors , false : one);
     */
      protected boolean proportion; // fonction ??
   
    /**
     * Optimal comsumption
     */
      protected double q;
   
    /**
     * random (PRECISER)
     */
      protected CRandomDouble random;
      /**
   	*
   	*/
      protected Market market;
   //Part I - Initialisation of the agent =================
   /**
   * COMMENTER LE CONSTRUCTEUR
   */
      public Agent() {
         super();
         //if (agentID==0)
            //System.out.println(" agent.constructeur");
      }
   /**
   * 
   */
      public void getInfo() {
      
         //if (agentID==0)
            //System.out.println(" agent.getInfo() ");
         this.earlyAdopters  = ((World) world).earlyAdopters;
         this.alpha       = ((World) world).alpha;
         this.epsilon     = ((World) world).epsilon;
         this.proportion  = ((World) world).proportion;
         this.random      = ((World) world).random;
      }
   	/**
   	*
   	*/
   
   /**
   *
   */
   
      public void init() {
         //if (agentID==0)
            //System.out.println(" agent.init() ");
         // personal information
          // non subscriber
         previouState = 0;
         stateC = 0;
         theta = random.getDouble(); //idiosyncrasic individual coefficient
      // environmental information
         neighbours = ((NeighbourMedium) mediums[0]).getNeighbours();
         market = ((World) world).getMarket();
         nbCompetitors = market.getNbCompetitors();
         subscription = new double[nbCompetitors];
         price = new double[nbCompetitors];
         N = new double[nbCompetitors];
         choice = new ArrayList();
         getInfo();		//SC 28.06.01
      }
   	/**
   	*
   	*/
   
      public void marketOpen(){
         //if (agentID==0)
            //System.out.println(" agent.marketOpen() ");
      
      
         setMenuPrice();// The agent get all menu of prices for the market
         thetaMin=thetaMin(subscription, price);
         refractaire = (theta <= thetaMin ? true : false);
         //refractaire = (  random.getDouble() <= 0.01 ? true : false);
         if (!refractaire) {
            for (int i = 0; i < nbCompetitors; i++) {//tirage des primo-adopteurs
               if (random.getDouble() <= ((Double) earlyAdopters.get(i)).doubleValue() && expectations(i+1))
               {
                  previouState = i+1; stateC = previouState ; state = stateC; 
               }
            }
         }
      	// earlyAdopters commitment
         state = stateC;
      
      // double emploi ?
      /*
         for (int i = 0; i < market.getNbCompetitors(); i++){ 
            N[i] = getNeighboursProportion(i+1); 
         	*/
            //System.out.print(" N["+i+"]="+N[i]);
         //}
      }
   // Part II - Agent's computations
   /**
   *
   */
      public double thetaMin(double [] A, double [] P){
         thetaMin = threshold(1,subscription[0],price[0]);
         if (nbCompetitors > 1){
            for (int i = 1; i < nbCompetitors ; i++){
               double temp = threshold(1,subscription[i],price[i]);
               if (temp<thetaMin)
                  thetaMin=temp ;
            }
         }      
         return thetaMin;
      }
   /**
   *
   */
      public double threshold(double N, double A, double P){
      
      //Calculs OK -> (vérifié pour N=4)
         N=N*(double)neighbours.size()/4;
         double numA = (epsilon-1)*A;
         double divNP = java.lang.Math.pow(N,epsilon)*java.lang.Math.pow(P,1-epsilon);
         double ratio = numA/divNP;
         double exposant = 1/alpha/epsilon;
         double threshold = java.lang.Math.pow(ratio,exposant);
         return threshold;
      }
   /**
   *
   */
      public boolean expectations(int numCompetitor){
         double neighbourTheta ;
         boolean neighbourState = false ;
         for (Iterator i= neighbours.iterator();i.hasNext();) {
            Agent agentemp =((Agent) i.next()); 
            neighbourTheta =(agentemp.getTheta());
            if (neighbourState == false)
               neighbourState = (  neighbourTheta >=
                                threshold(1/(double)neighbours.size(),subscription[numCompetitor-1],price[numCompetitor-1]) ? true : false);
            //System.out.print(" nTheta : "+neighbourTheta);
         }
         return neighbourState;
      }
   /**
   *
   */
      public double getNeighboursProportion(int numCompetitor){
         int nbSubscriber = 0;
      
         for (Iterator i= neighbours.iterator();i.hasNext();) {
            Agent agentemp =((Agent) i.next()); 
         //
            if (proportion) {  // test for all competitors
               if (agentemp.state > 0)//previouState
                  nbSubscriber++;
            }
            else {            // test for only one competitor
            
               if (agentemp.state == numCompetitor){
                  nbSubscriber++;
               }
            }
         }
         return ((double)nbSubscriber / (double)neighbours.size());
      }
   /**
   *
   */
      public void compute() {
         //if (agentID==0)
            //System.out.println(" agent.compute() ");
         choice.clear(); 
         setMenuPrice(); //The agent get all menu of prices for the market
      
         for (int i = 0; i < market.getNbCompetitors(); i++) {   // for all market 's competitors
            N[i] = getNeighboursProportion(i+1);
            // proportion of neighbours who have chosen this competitor
            if (!refractaire && N[i]>0){
               if (theta >= threshold(N[i], subscription[i],price[i])){
                  choice.add(new Integer(i+1));            // store the Identifier of the chosen supplier
               }
            }
         }
      
         if( choice.size() > 0){
            int j = (int) (random.getDouble() * choice.size() );
            { // choose only one competitor
               stateC = ((Integer) choice.get(j)).intValue();
               NChoice = N[stateC-1];
            }
         }
         else 
            stateC = 0;
      }
   	/**
   	*
   	*/
      public void commit() {
         //if (agentID==0)
            //System.out.println(" agent.commit() ");
         previouState = state;
         state = stateC;
         if (state == 0) {
            q = 0 ; //random.getDouble();
         }
         else {
            double priceSupplier = price[state-1];// temporary variables
            //q = random.getDouble();
            q =  java.lang.Math.pow(( priceSupplier / NChoice * java.lang.Math.pow(theta,alpha) ) , -epsilon);   // compute the optimal comsumption
         }
         market.buy(agentID, state, q);
      }
   // Part III - Data Exchanges =================
   /**
   *
   */
      public void setMenuPrice(){
         //if (agentID==0)
            //System.out.println(" agent.setMenuPrice() ");
         subscription = market.getSubscription();
         price = market.getPrice();
      }
   /**
     * Return stable state (Competitor number)
   * methode call from XXX at each Canevas update (including between iterations)
     */
      public Object getState() {
         //System.out.println(" agent.getState() ");
         return new Integer(stateC);
      }
    /**
     * Return boolean state  (buy or no) >> DOUBLE EMPLOI AVEC PRECEDENT ?
     */
      public Object getBooleanState() {
         //System.out.println(" agent.getBooleanState() ");
         return new Boolean(stateC == 0 ? false : true);
      }
   /**
   *
   */
   
      public String toString() {
         //System.out.println(" agent.toString() ");
         return (new Integer(state)).toString();
      }
   /**
   *
   */
      protected void setTheta(double newTheta) {
         //System.out.println(" agent.setTheta() ");
         theta =newTheta;
      }
   /**
   *
   */             
      public double getTheta() {
         //System.out.println(" agent.getTheta() ");
         return theta;
      }
   /**
   *
   */
      public void setRandom(CRandomDouble random) {
         //if (agentID==0)
            //System.out.println("agent.setRandom() ");
         this.random = random;
      }
   /**
   *
   */
      public void setAlpha(double newAlpha) {
         //if (agentID==0)
            //System.out.println(" Agent.setAlpha");
         alpha =newAlpha;
      }
   /**
   *
   */
      public void setEpsilon(double newEpsilon) {
         //if (agentID==0)
            //System.out.println(" Agent.setEpsilon");
         epsilon =newEpsilon;
      }
   
      public void setProportion(boolean b) {
      
         //if (agentID==0)
            //System.out.println(" Agent.setProportion");
         proportion =b;
      }
   /**
   *
   */
      public void inverseState() {           
         state = 0; stateC = state;
        //if (ae != null) ae.update ();
         subscriber = !subscriber;
      }
   /**
   *
   */
      public void inverseRefractaire() {
         double aleatemp = random.getDouble();
         if (aleatemp > thetaMin) theta = aleatemp;
         else  theta = 1 - aleatemp;
         refractaire = !refractaire;
      
      }
      public ArrayList getDescriptors(){
      
         descriptors.clear();
         descriptors.add(new IntegerDataDescriptor(this,"State","state",state,false));
         //descriptors.add(new BooleanDataDescriptor(this,"Has changed","hasChanged",hasChanged,false));
         descriptors.add(new BooleanDataDescriptor(this,"Refractaire","refractaire",refractaire,false));
         descriptors.add(new DoubleDataDescriptor(this,"Theta","theta",theta,false,6));
         descriptors.add(new DoubleDataDescriptor(this,"Theta min","thetaMin",thetaMin,false,6));
         descriptors.add(new DoubleDataDescriptor(this,"Alpha","alpha",alpha,true,6));
         descriptors.add(new DoubleDataDescriptor(this,"Epsilon","epsilon",epsilon,true,6));
         descriptors.add(new DoubleDataDescriptor(this,"q","q",q,false,6));
         //if (agentID==0)
            //System.out.println(" agent.getDescriptors");
         return descriptors;
         //return new ArrayList(); ???
      }
   }