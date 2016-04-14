/**
 * Title:       bilateralGame.Agent<p>
 * Description:  Chaque Agent joue avec ses voisins et adopte, pour le tour suivant, la
 * statégie de celui qui a le meilleur gain. Le gain est réinitialisé à chaque tour.
 * La matrice de gain est la même pour tous les Agents ; elle est enregistré par le World.<p>
 * Copyright:    Copyright (c)enst-bretagne
 * @author denis.phan@enst-bretagne.fr
 * @version 1.4  February, 2004
 */
   package models.DBBGame;

   import java.util.Iterator;
   import java.util.ArrayList;

   import modulecoFramework.modeleco.EAgent;
   import modulecoGUI.grapheco.descriptor.IntegerDataDescriptor;
   import modulecoGUI.grapheco.descriptor.DoubleDataDescriptor;
   import modulecoGUI.grapheco.descriptor.BooleanDataDescriptor;
   import modulecoGUI.grapheco.descriptor.InfoDescriptor;

// import modulecoFramework.medium.Medium;
   import modulecoFramework.medium.NeighbourMedium;

// import modulecoGUI.grapheco.ControlPanel;
   import modulecoFramework.modeleco.randomeco.CRandomDouble;
   import modulecoFramework.modeleco.randomeco.RandomSD;

   public class Agent extends EAgent
   {
      protected double gain=0, payoffAlterStrategy=0;
      protected int S1neighbours, S2neighbours;
   
      protected boolean newStrategy, oldStrategy, nextStrategy;
      private boolean hasChanged;
      protected String revisionRuleIndex;
      protected double finalAveragePayoff1 ;
      protected double finalAveragePayoff2 ;
      protected double epsilon;
      protected CRandomDouble random2;
      protected RandomSD random;
      double[] averagePayoff;
      protected double k,h,j,tremble;
      protected double surplusS1, surplusS2;
      protected double cumulativeCPRnumerator, cumulativeCPRdenominator ;
      public Agent ()
      {
         super();
      //if(agentID==0)System.out.println("models.DBBGame.constructor");	
      }
   
   
      public void getInfo(){
         revisionRuleIndex =  ((World) world).getRevisionRuleIndex();
      
//		idiosyncrasic individual coefficient
         this.random = ((World) world).getRandom();
         /* DEBUG
         if(random!=null){
			double testD = random.getDouble();
         }
                  else
		if(agentID==0) System.out.println("models.DBBGame.getInfo() random null !");
      */
      }
   
      public void init() {
         if(agentID==0)System.out.println("models.DBBGame.init()");
      
         j = ((World) world).getJ();
         k = ((World) world).getK();
         h = ((World) world).getH();
         tremble=((World) world).getTremble();
      
      
         neighbours = ((NeighbourMedium) mediums[0]).getNeighbours();
         int neighbourSize =neighbours.size(); 
      
         if((((World) world).random_s).equalsIgnoreCase("JavaLogistic")){
            epsilon = random.getDouble();
            surplusS1 = neighbourSize*(k +h +epsilon);
            surplusS2 = neighbourSize*(k -h -epsilon);
            //if(surplusS2>surplusS1)
               //System.out.println("epsilon = "+epsilon+" SurplusS1 = "+surplusS1+" SurplusS2 = "+surplusS2);
            if(epsilon+h > 0){
               oldStrategy = true;
            	//System.out.println("agent 1 e ="+epsilon);
            }
            else {	
               oldStrategy = false;
            
            }
         
         }
         else {
            if((((World) world).random_s).equalsIgnoreCase("IdenticalAgents")){
               oldStrategy = false ;
               //epsilon = random.getDouble();
               //if(agentID==0)System.out.println("IdenticalAgents");
            }
            else{
               epsilon = random.getDouble();
               oldStrategy  = ( (epsilon >= ((World) world).getRateS2()) ? true : false);
            }
         }
         newStrategy = oldStrategy;
         nextStrategy = newStrategy;
         averagePayoff = new double[2];
         if(revisionRuleIndex.equalsIgnoreCase("BehaviorialCPR")){
            this.random2 = ((World) world).getRandom2();
            //if(agentID==0)System.out.println("models.DBBGame.getInfo(): BehaviorialCPR case");
            if(oldStrategy)
               cumulativeCPRnumerator=surplusS1;
            else
               cumulativeCPRnumerator=surplusS2;
         	 //(k+j)*neighbourSize;
            cumulativeCPRdenominator = surplusS1+surplusS2;//(k+j)*2*neighbourSize;
         }
      }
   
      public void compute(){
         //if(agentID==1)System.out.println("agent 1 Compute");
         tremble=((World) world).getTremble(); // non optimise
         oldStrategy = newStrategy;// on stocke la stratégie qu'on a joué
         //if(revisionRuleIndex.equalsIgnoreCase("myopicMaximization"))
            //myopicMaximization();
         //else
         computePayoff();
      }
   
      public void computePayoff(){
         int dpaStrategy;
         Agent dpa;
         surplusS1=0;
         surplusS2=0;
      
         for (Iterator i= neighbours.iterator();i.hasNext();) {
            dpa = (Agent) i.next();
            dpaStrategy =(dpa.newStrategy?1:-1); // true = 1 false = -1
            surplusS1 += k +h+epsilon +j*dpaStrategy;
            surplusS2 += k -h -epsilon -j*dpaStrategy;
            //if (agentID== 0 || agentID == 168)
               //System.out.println("agent : "+agentID+" dpaStrategy = "+dpaStrategy+" surplusS1 = "+surplusS1+" surplusS2 = "+surplusS2);
         }
         if(newStrategy) {
            gain = surplusS1;
            payoffAlterStrategy=surplusS2;
         }
         else{
            gain = surplusS2;
            payoffAlterStrategy=surplusS1;
         }
      
         //if(agentID==0)System.out.println("models.DBBGame.computePayoff()= "+gain+" Alter Strategy = "+payoffAlterStrategy);
      }
   
   
   
   
      public void commit() {
         averagePayoff = ComputeAveragePayoff();
         finalAveragePayoff1 = averagePayoff[0];
         finalAveragePayoff2 = averagePayoff[1];
      	// decide sa strategie
         revisionRuleIndex =  ((World) world).getRevisionRuleIndex();
         if (revisionRuleIndex.equalsIgnoreCase("LastNeighbour.BestPayoff")){
            newStrategy = ComputeBestPayoff();
            //System.out.print("0");
         }
         if (revisionRuleIndex.equalsIgnoreCase("LN.BestAveragePayoff"))
         {
            newStrategy = ComputeBestAveragePayoff();
            //System.out.print("1");
         }
         if (revisionRuleIndex.equalsIgnoreCase("myopicBestReply")){
            newStrategy = ComputeMyopicBestReply();
         }
      
         if(revisionRuleIndex.equalsIgnoreCase("BehaviorialCPR")){
            newStrategy = computeBehaviorialCPR();
         }
         hasChanged = (newStrategy != oldStrategy);
      
        //if (ae != null) ae.update ();
      }
   
      public double[] ComputeAveragePayoff(){
      
         double[] ap = new double[2];
         Agent dpa;
         S1neighbours =0;
         S2neighbours = 0;
         int payoff1 = 0;
         int payoff2 = 0;
         for (Iterator i= neighbours.iterator();i.hasNext();) {
            dpa = (Agent) i.next();
            if (dpa.oldStrategy)
            {S1neighbours++ ; payoff1 += dpa.gain;
               //if(agentID==1)System.out.println("pay1 dpa.gain = "+dpa.gain);
            }
            else 
            {S2neighbours++  ; payoff2 += dpa.gain;
               //if(agentID==1)System.out.println("pay2 dpa.gain = "+dpa.gain);
            }
         }
         if (S1neighbours==0) ap[0] = 0;
         else ap[0] = payoff1/S1neighbours;
         if (S2neighbours==0) ap[1] = 0;
         else ap[1] = payoff2/S2neighbours;
         return ap;
      } 
   
    //======================= strategy revision rule ===
      public boolean computeBehaviorialCPR(){
      
         if(newStrategy){
            cumulativeCPRnumerator+=gain ;
            cumulativeCPRdenominator +=gain;
         }
         else
            cumulativeCPRdenominator +=gain;
         double probaS1=cumulativeCPRnumerator/cumulativeCPRdenominator;		
         if (payoffAlterStrategy > gain){
            //System.out.println(payoffAlterStrategy+" ; "+gain);
            nextStrategy = random2.getDouble()<probaS1 ;
         }
         else
            //if(probaS1>0.5||probaS1<0.5)//
            if(random2.getDouble()<tremble){
               //System.out.println("inftremble = "+tremble+"");
               nextStrategy=random2.getDouble()<probaS1 ;
            }
            else
               nextStrategy =newStrategy;
         //if(agentID==0 ||agentID==20 )
            //System.out.println(agentID+" ; "+probaS1);
         return nextStrategy;
      } 
      public boolean ComputeMyopicBestReply(){
         if (payoffAlterStrategy > gain) 
            nextStrategy =!newStrategy;
         else
            if(payoffAlterStrategy < gain)
               nextStrategy =newStrategy;
            else
               nextStrategy =newStrategy; // REMPLACER PAR 1/2
         return nextStrategy;
      
      }
   
      public boolean ComputeBestPayoff(){
         double gainMax = gain;
         Agent dpa;
      
         for (Iterator i= neighbours.iterator();i.hasNext();) {
            dpa = (Agent) i.next();
            if (gainMax < dpa.gain) {
               nextStrategy = dpa.oldStrategy; //on copie la strategie du meilleur voisin
               gainMax = dpa.gain;
            }
         }
         return nextStrategy;
      }
   
      public boolean ComputeBestAveragePayoff(){
      
        //System.out.println("ComputeBestAveragePayoff()");
            //on copie la strategie qui apporte le meilleur gain moyen
         if (averagePayoff[0]>averagePayoff[1])
            nextStrategy = true;
         if (averagePayoff[1]>averagePayoff[0])
            nextStrategy = false;
         if (averagePayoff[0]==averagePayoff[1])
            nextStrategy = oldStrategy;
         return nextStrategy;
      }
   	//=====================
      public Boolean hasChanged()
      {
         boolean temp;
      
         temp = hasChanged;
         hasChanged = false;
         return new Boolean(temp);
      }
   
      public Object getState()
      {
         return new Boolean(newStrategy);
      }
   
      public String toString()
      {
         String s;
         s = (new Boolean(newStrategy)).toString() + " g = " + (new Double(gain)).toString();
      
         return s ;
      }
   
      protected String strategy(boolean b){
         String s;
         if (b) s=" S1"; 
         else s=" S2";
         return s;
      }
      protected int strategyColor(boolean b){
         int s;
         if (b) s = 1 ; 
         else s = 2 ;
         return s;
      }
      public ArrayList getDescriptors()
      {
      
         String oldStrategyString = strategy(oldStrategy);
         String newStrategyString = strategy(newStrategy);
         int oldStrategyColor = strategyColor(oldStrategy);
         int newStrategyColor = strategyColor(newStrategy);
         int alterStrategyColor = strategyColor(!oldStrategy);
      
         descriptors.clear();
         descriptors.add(new DoubleDataDescriptor(this,"Total Payoff Agent "+oldStrategyString,"gain",gain,false,oldStrategyColor));
         descriptors.add(new DoubleDataDescriptor(this,"Payoff AlterStrategy	","payoffAlterStrategy",payoffAlterStrategy,false,alterStrategyColor));
         descriptors.add(new DoubleDataDescriptor(this,"Average Payoff S1","finalAveragePayoff1",finalAveragePayoff1,false,1,2));
         descriptors.add(new DoubleDataDescriptor(this,"Average Payoff S2","finalAveragePayoff2",finalAveragePayoff2,false,1,2));
         descriptors.add(new DoubleDataDescriptor(this,"S1 Surplus","surplusS1",surplusS1,false,1,2));
         descriptors.add(new DoubleDataDescriptor(this,"S2 Surplus","SurplusS2",surplusS2,false,1,2));
         descriptors.add(new BooleanDataDescriptor(this,"New Strategy"+newStrategyString,"newStrategy",newStrategy,true,newStrategyColor));
         descriptors.add(new BooleanDataDescriptor(this,"Old Strategy"+oldStrategyString,"oldStrategy",oldStrategy,true,oldStrategyColor));
         descriptors.add(new IntegerDataDescriptor(this,"S1 neighbours","S1neighbours",S1neighbours,false,1));
         descriptors.add(new IntegerDataDescriptor(this,"S2 neighbours","S2neighbours",S2neighbours,false,2));
         descriptors.add(new InfoDescriptor(revisionRuleIndex));
         return descriptors;
      }
   // utile ?
      public void setGain(int g){
      
         gain=g;}
      public void setpayoffAlterStrategy(int p){
      
         payoffAlterStrategy = p;}
      public void setFinalAveragePayoff1(double f){
         finalAveragePayoff1=f;}
      public void setFinalAveragePayoff2(double f){
         finalAveragePayoff2=f;}
      public void setNewStrategy(boolean b){
         newStrategy=b;
		System.out.println("gant : "+agentID+" inverseState()");
      }
      public void inverseState(){
         setNewStrategy(!newStrategy);
      }
      public void setOldStrategy(boolean b){
         oldStrategy=b;}
   }
