/*
 * @(#)World.java	1.1 09-Mar-04
 */
   package models.ising2;

//import java.io.BufferedWriter;
//import java.io.FileWriter;
//import models.ising2.Agent;
   import java.lang.reflect.Constructor;
   import java.util.ArrayList;
   import java.util.Iterator;
//import java.awt.Color;

   import modulecoFramework.modeleco.ENeighbourWorld;
   import modulecoFramework.modeleco.randomeco.RandomSD;
   import modulecoGUI.grapheco.descriptor.ChoiceDataDescriptor;
   import modulecoGUI.grapheco.descriptor.DoubleDataDescriptor;
//import modulecoGUI.grapheco.descriptor.InfoDescriptor;
   import modulecoGUI.grapheco.descriptor.LongDataDescriptor;
//import modulecoGUI.grapheco.descriptor.IntegerDataDescriptor;
   import modulecoGUI.grapheco.statManager.Var;

//import modulecoGUI.grapheco.statManager.CalculatedVar;
/**
 * Describe the world, the way agents communicate with each other, etc.
 * 
 * 
 * 
 * 
 * @author Christophe Belin, Viktoriya
 * @version 1.0, 23-Feb-04
 * @version 1.1, 09-Mar-04
 * @see modulecoFramework.modeleco.ENeighbourWorld
 * @see modulecoFramework.modeleco.EWorld
 * @see modulecoFramework.modeleco.CAgent
 */

           
   public class World extends ENeighbourWorld {
           
   
   /**
	* Initial values for the 4 basic parameters of this world <br>
	* parameter 1: world size <br>
	* parameter 2: neighbourhood type <br>
	* parameter 3: active zone type <br>
	* parameter 4: scheduler type <br>
	*/
	  public static String initLength = "10";
   
	  public static String initNeighbour = "World";
   
	  public static String initZone = "World"; //"RandomIndividual";
   
	  public static String initScheduler = "LateCommitScheduler";
   
   /**
	* <tt>price</tt> is the observable variable in this world.
	* <p>
	* It will be displayed on the right panel (<em>Graphique</em>). It
	* represents the aggregated value of all agents.
	*/
	  protected double price;
   
	  protected double j;
   
   /**
	* number of buyers at time t
	*/
	  protected int buyers = 0;
   
   /**
	* % of buyers at time t = buyers/agentSetSize
	* 
	* Warning ! Dont use it to have eta ! this variable is only for the descriptor. Use getEta() !
	*/
	  protected double eta;
   
   /**
	* average idiosyncrasic willingness to pay equivalent to an external
	* magnetic field in MS
	*/
	  protected double h;
   
   /**
	* number of buyers at time t-1
	*/
	  protected int buyersprev = 0; // agentSetSize ; // 0 // nombre d'acheteur au
							  // debut du calcul
   
   /**
	* répartition des agents a l'initialisation (a supprimer)
	*/
	  protected String repart;
   
   /**
	* Random generators random is always uniform
	*/
	  protected long seed;
   
	  protected RandomSD random;
   
   /**
	* Random generators with standard deviation sd2
	*/
	  protected long seed2;
   
	  protected static double sd2 = 1.0;
   
	  protected RandomSD random2;
   
	  private static String randomPath = "modulecoFramework.modeleco.randomeco.";
   
	  protected String random_s2;
   
   /**
	* dynamic state of the system ("stable" or "running")
	*/
	  protected String sysState = "Stable"; // "Running"
   
   /**
	* price incremental step
	*/
	  protected double priceStep;
   
   /**
	* change incremental J step
	*/
	  protected double jStep;
   
   /**
	* learning EWA parameters
	*/
	  protected double mu, kapa, phi, delta;
   
   /**
	* measure the size of avalanche
	*/
	  double ava;
   
   /**
	* Class to be manage avalanche computation
	*/
	  public Avalanche avalanche; //(to remove ?)
   
   /**
	* Les variables de l'algorithme original
	*/
   
   //public static final double BETA = 1;
   /**
	* Viktoriya's values
	*/
	  protected int _stateofJ = 0; // _stateofJ switch define agent's J
   
	  protected int _stateofdistributionh = 0; // _stateofJ switch define agent's
   
   // H (null or random)
   
	  protected int _stateofdistributiontheta = 1; // _stateofJ switch define
   
   // agent's theta (null or
   // logistic)
   
	  protected int _stateofspin = 0; // _stateofJ switch define agent's spin
   
   //double TotEnergy = 0; // pour mémoire
   
   //boolean load = false;
   /**
	* 
	* Variables pour les events dans compute
	*/
   //	private double coupleprec;
   //	private int parenhaut;
   /**
	* the agents' state is symbolised by a color
	*/
   //protected Color colors[];
   /**
	* Constructor
	* 
	* @param taille
	*            size of the world (taille x taille), i.e. squre root of the
	*            number of agents, e.g. 5
	* @param eaClass
	*            e.g. models.emptyModel.Agent
	* @param vsClass
	*            e.g. modulecoFramework.modeleco.zone.NeighbourVonNeuman
	* @param tsClass
	*            time scheduler, e.g.
	*            modulecoFramework.modeleco.scheduler.LateCommitScheduler
	* @param zsClass
	*            type of neighbour, e.g. modulecoFramework.modeleco.zone.World
	* @param ctrl
	*            Central Control, e.g.
	*            modulecoGUI.grapheco.CentralControl[,0,0,0x0,invalid,layout=java.awt.GridBagLayout,alignmentX=null,alignmentY=null,border=,flags=9,maximumSize=,minimumSize=,preferredSize=]
	* @param l
	*            True is this is new world
	*/
   
              
	  public World(int taille) {
              
      
		 super(taille);
	  //System.out.println("[World()] ");
		 sysState = "Running";
	  }
   
   /**
	* Used by <tt>EWorld</tt>, when a new world is created.
	* <p>
	* Mandatory method (inherited from its abstract superclass in
	* <tt>EWorld</tt>).
	* 
	* @see modulecoFramework.modeleco.EWorld
	* @see modulecoFramework.modeleco.CAgent
	*/
   
              
	  public void getInfo() {
              
	  //System.out.println("[World.getInfo()]");
	  }
   
   /**
	* Set the default values when the world is created for the very first time.
	* <p>
	* Initialise HERE (and not in the constructor or init()) the parameters you
	* might want to change through the bottom panel (<em>World Editor</em>).
	* <p>
	* Mandatory method (inherited from its abstract superclass in
	* <tt>EWorld</tt>)
	* 
	* @see modulecoFramework.modeleco.EWorld
	*/
              
	  public void setDefaultValues() {
              
		 System.out.println("[World.setDefaultValues()]");
	  /**
	   * set the default value of the initial agent's choice
	   */
		 this.setRepart("NobodyBuy");
	  /**
	   * set the default value of the seed fotr the standard (ie uniform)
	   * random generator for initialisation of the agents
	   */
		 this.setSeed(0); //System.currentTimeMillis()
	  /**
	   * set the default value for theta random generator
	   */
		 this.random_s2 = "JavaLogistic";
		 this.setSeed2(0);
		 this.setRandom_s2(random_s2);
	  /**
	   * set the default value of the initial price
	   */
		 this.setPrice(0.7);
	  /**
	   * set the default value of the initial agents's J
	   */
		 this.setJ(1);
	  /**
	   * Define the external field from a uniform distribution with specified
	   * interval
	   */
		 if (_stateofdistributionh == 0)
			this.h = 0;
		 else
			this.h = getRandom().getDouble();
	  // dans ce cas le H est idiosyncrasic !!!!!
	  // ATTENTION ! double emploi avec that !!
	  /**
	   * set the default value of the incremental P step
	   */
		 this.priceStep = 0.1;
	  /**
	   * set the default value of the incremental J step
	   */
		 this.jStep = 0.5;
		 random = new RandomSD(seed);
	  /**
	   * learning EWA parameters
	   */
		 this.phi = 1;
		 this.kapa = 1;
		 this.mu = 1;
		 this.delta = 1;
	  }
   
   /**
	*  
	*/
              
	  public void setPreviousValues() {
              
		 super.setPreviousValues();
		 random = new RandomSD(seed);
	  }
   
   //random = new RandomSD(seed);
   /**
	* Initialise the simulation.
	* <p>
	* In particular, this is where you add some variables to the
	* <tt>statManager</tt> to be recorded or displayed in the right panel (
	* <em>Graphique</em>).
	*/
              
	  public void init() {
              
		 System.out.println("[World.init()]");
		 avalanche = new Avalanche(this);
	  /**
	   * Add the variables to observe through the <em>statManager</em>
	   */
		 try {
		 /**
		  * <ul>
		  * <li>"statPrice" is the name used by the statManager
		  * <li>"getPrice" is the exact name of the method that returns the
		  * variable. For the <tt>Trace</tt> and the
		  * <tt>Graphique</t> to work properly, getPrice() MUST RETURN a double (@see
		  * modulecoGUI.grapheco.graphix.Trace)
		  * </ul>
		  */
			statManager.add(new Var("statNormalizedPrice", Class.forName(
													 this.pack() + ".World").getMethod("getNormalizedPrice",
													 null)));
			statManager.add(new Var("statRatio", Class.forName(
													 this.pack() + ".World").getMethod("getRatio", null)));
			statManager.add(new Var("statAvalanche", Class.forName(
													 this.pack() + ".World").getMethod("getAvalanche", null)));
		 }
         
                    
			catch (ClassNotFoundException e) {
                    
			   System.out.println(e.toString());
			} 
                    
			catch (NoSuchMethodException e) {
                    
			   System.out.println(e.toString());
			}
	  }
   
   /**
	* Once the agents have computed and committed, the world computes.
	* <p>
	* Invoked at each time step.
	*/
   
              
	  public void compute() {
              
      
		 System.out.println("[World.compute()] Price : " + this.price + " j : "
						   + this.getJ() + " Ratio : " + this.getRatio() + " buyers : "
						   + buyers);
	  }
   
   /**
	* commit()
	*/
              
	  public void commit() {
              
		 System.out.println("[World.commmit()]");
		 if (avalanche.Est_Stable(buyers - buyersprev, initZone == "World"))
			sysState = "Stable";
		 else
			sysState = "Running";
      
		 ava = this.getAvalanche();
		 this.buyersprev = this.buyers;
		 if (sysState.equals("Stable")) {
			this.j = this.j + this.jStep;
			this.price = this.price + this.priceStep;
		 }
	  //calcul du surplusExpost des agents
		 for (Iterator i = iterator(); i.hasNext();)
			((Agent) i.next()).computeExpostSurplus(price);
	  }
   
   /**
	* Handle the colors of the agents.
	* <p>
	* Necessary only if the default colors are modified by the creation of a
	* canevas.
	* 
	* @return the colors used to represent the states of the agents
	* @see models.emptyModel.Canevas
	*/
   /*
	* public Color[] getColors() {
	* 
	* System.out.println("[World.getColors()]"); return colors; }
	*/
   /**
	* Add to the bottom panel (<em>World Editor</em>) the variables (resp
	* parameters) to observe (resp change) during the simulation.
	* <p>
	* Those variables should have been initialised in setDefaultValues() when
	* the world is created for the very first time.
	* <p>
	* Invoked at each time step.
	*/
   
              
	  public ArrayList getDescriptors() {
              
		 descriptors.clear();
	  /**
	   * <ul>
	   * <li>"Price" is the complete name to display in the bottom panel
	   * (World Editor)
	   * <li>"price" is the variable name. A method "setPrice()" MUST EXIST
	   * <li>price is the variable itself
	   * <li>false means that you cannot change the value of the variable
	   *  
	   */
		 descriptors.add(new ChoiceDataDescriptor(this,
										  "  Initial configuration", "repart", new String[] {
											 "NobodyBuy", "EverybodyBuy", "Random" }, repart, true));
		 descriptors.add(new LongDataDescriptor(this, "Seed Init", "seed", seed,
										  true, 3));
	  //descriptors.add(new InfoDescriptor(""));
	  //descriptors.add(new InfoDescriptor(""));
		 descriptors.add(new LongDataDescriptor(this, "Seed2(Theta)", "seed2",
										  seed2, true, 3));
		 descriptors.add(new ChoiceDataDescriptor(this, "  AgentRandom",
										  "random_s2", new String[] { "Default", "JavaRandom",
											 "JavaGaussian", "JavaLogistic" }, random_s2, true));
	  //descriptors.add(new InfoDescriptor(""));
		 descriptors.add(new DoubleDataDescriptor(this, "  Price", "price",
										  price, true));
		 descriptors.add(new DoubleDataDescriptor(this, "  J", "j", j, true));
		 descriptors.add(new DoubleDataDescriptor(this, "  Buyers", "buyers",
										  buyers, false));
		 descriptors.add(new DoubleDataDescriptor(this, "  % Eta", "eta", eta,
										  false));
		 descriptors.add(new DoubleDataDescriptor(this, "  Price Step",
										  "priceStep", priceStep, true));
		 descriptors.add(new DoubleDataDescriptor(this, "  J Step", "jStep",
										  jStep, true));
		 descriptors.add(new DoubleDataDescriptor(this, "  Avalanche ", "ava",
										  ava, false));
		 descriptors.add(new ChoiceDataDescriptor(this, "  Status", "sysState",
										  new String[] { "Running", "Stable" }, sysState, false));
		 descriptors.add(new DoubleDataDescriptor(this, "EWA phi", "phi", phi,
										  true));
		 descriptors
			.add(new DoubleDataDescriptor(this, "EWA mu", "mu", mu, true));
		 descriptors.add(new DoubleDataDescriptor(this, "EWA kapa", "kapa",
										  kapa, true));
		 descriptors.add(new DoubleDataDescriptor(this, "EWA delta", "delta",
										  delta, true));
		 return descriptors;
	  }
   
              
	  public void setRepart(String r) {
              
		 this.repart = r;
		 if (r.equalsIgnoreCase("NobodyBuy"))
			this._stateofspin = 0;
		 else if (r.equalsIgnoreCase("EverybodyBuy"))
			this._stateofspin = 1;
		 else
			this._stateofspin = 2; //"Random"
	  }
   
              
	  public void setSeed(long d) { //   <- erreur !
              
      
		 this.seed = (long) d;
	  }
   
              
	  public RandomSD getRandom() {
              
      
		 return random;
	  }
   
              
	  public void setRandom_s2(String s) {
              
      
		 random_s2 = s;
		 Object RandomSDConstructorParameters[] = new Object[] { new Long(seed),
			new Double(sd2) };
		 try {
			System.out.println("models.DBBGame.random_s sd = " + sd2
							  + " seed =" + seed);
			Constructor constructor = Class.forName(randomPath + random_s2)
			.getConstructor(new Class[] { long.class, double.class });
			random2 = (RandomSD) constructor
			   .newInstance(RandomSDConstructorParameters);
		 }
		 /*
		 * catch (IllegalAccessException e){ System.out.println(e.toString());}
		 * 
		 * catch (InstantiationException e){ System.out.println(e.toString());}
		 * 
		 * catch (ClassNotFoundException e){ random = null;}
		 */
         
                    
			catch (Exception e) {
                    
            
			   System.out.println(e.toString());
			}
	  }
   
              
	  public void setSeed2(long d) {
              
      
		 this.seed2 = (long) d;
	  }
   
              
	  public RandomSD getRandom2() {
              
      
		 return random2;
	  }
   
              
	  public void setPrice(double price) {
              
      
		 this.price = price;
	  }
   
   /**
	* normalisation des prix pour le graphique : n'est pas utilisé par les
	* agents (revoir !!!)
	* 
	* @return
	*/
              
	  public double getPrice() {
              
		 return price; //10.0 * (price + 5.0);
	  }
   
              
	  public double getNormalizedPrice() {
              
		 return 10.0 * (price + 5.0);
	  }
   
              
	  public void setJ(double j) {
              
      
		 this.j = j;
	  }
   
              
	  public double getJ() {
              
      
		 return j;
	  }
   
              
	  public void setBuyers(double d) {
              
      
		 this.buyers = (int) d;
	  }
   
              
	  public void setAva(double a) {
              
      
		 this.ava = a;
	  }
   
              
	  public double getAvalanche() {
              
	  //System.out.println("[World.GetAvalanche] avalanche = "
	  // +avalanche.Get_avalanche());
		 double d = avalanche.Get_avalanche();
		 return (d > 80 ? 80 : d);
	  }
   
              
	  public void setPriceStep(double d) {
              
      
		 this.priceStep = d;
	  }
   
              
	  public double getPriceStep() {
              
      
		 return this.priceStep;
	  }
   
              
	  public void setJStep(double d) {
              
      
		 this.jStep = d;
	  }
   
              
	  public double getJStep() {
              
      
		 return this.jStep;
	  }
   
              
	  public void setEtat(String s) {
              
      
		 this.sysState = s;
	  }
   
              
	  public String getEtat() {
              
      
		 return this.sysState;
	  }
   
   /**
	* for graph : % buyers
	* 
	* @return
	*/
   
              
	  public double getRatio() {
              
		 return getEta() * 100;
	  }
   
              
	  public double getEta() {
              
      
		 return (double) this.buyers / (double) this.agentSetSize;
	  }
   
   /**
	* Neighbour is the all world without himself
	* 
	* @param s
	* @return
	*/
   
              
	  public double getEta_world_moins1(int s) {
              
      
		 if (s == 1) // himself is a buyer -> -1
			return (this.buyers - 1) / (double) this.agentSetSize;
		 return this.buyers / (double) this.agentSetSize;
	  }
   
   /**
	* the buyers++/buyer-- agent ability ^^
	*  
	*/
   
              
	  public void incBuyers() {
              
      
		 this.buyers++;
	  }
   
              
	  public void decBuyers() {
              
      
		 this.buyers--;
	  }
   
              
	  public void setPopulation(int p) {
              
      
		 this.agentSetSize = p;
	  }
   
   /**
	* A simple way to re-initialise all agents during process Green and blue
	* are relative to the agent's color blue for spin-1, green for spin 1;
	*/
   
              
	  public void setagentsblue() { 
              
	  //System.out.println("setagentsblue");
		 for (Iterator i = iterator(); i.hasNext();)
			((Agent) i.next()).setValue(-1);
		 this.buyers = 0;
		 this.buyersprev = 0;
	  }
   
              
	  public void setagentsgreen() {
              
	  //System.out.println("setagentsgreen");
		 for (Iterator i = iterator(); i.hasNext();)
			((Agent) i.next()).setValue(1);
		 this.buyers = this.agentSetSize;
		 this.buyersprev = this.agentSetSize;
	  }
   
              
	  public void setSysState(String ss) {
              
		 sysState = ss;
	  }
   
   /**
	* learning EWA parameters
	*/
              
	  public void setMu(double mu) {
              
		 this.mu = mu;
	  }
   
              
	  public double getMu(){
              
		 return mu;
	  }
   
              
	  public void setKapa(double kapa) {
              
		 this.kapa = kapa;
	  }
   
              
	  public double getKapa() {
              
		 return kapa;
	  }
   
              
	  public void setPhi(double phi) {
              
		 this.phi = phi;
	  }
   
              
	  public double getPhi() {
              
		 return phi;
	  }
   
              
	  public void setDelta(double delta) {
              
		 this.delta = delta;
	  }
   
              
	  public double getDelta() {
              
		 return delta;
	  }
   
              
	  public double getCumulativeDistribution(double h) {
              
		 int cumul = 0;
		 Agent ag;
		 for (Iterator i = iterator(); i.hasNext();) {
			ag = (Agent) i.next();
			if (ag.theta > h)
			   cumul++;
		 }
		 double frequency = (double) 100 * ((double) cumul) / ((double) size());
	  //System.out.println("frequency theta = "+frequency);
		 return frequency;
	  }
   }

/*
 * //compute the total energy of the system at the beginning for (Iterator i =
 * iterator(); i.hasNext();) ((Agent) i.next()).partial1();
 */