/*
 * @(#)Agent.java	1.1 09-Mar-04
 */
   package models.ising2;

//import models.ising2.World;
   import java.util.ArrayList;
   import java.util.Iterator;
   //import modulecoFramework.modeleco.randomeco.*;
   import modulecoFramework.modeleco.EAgent;
   import modulecoGUI.grapheco.descriptor.DoubleDataDescriptor;
   import modulecoGUI.grapheco.descriptor.IntegerDataDescriptor;

/**
 * Describe the behaviour of an agent.
 * <p>
 * Each agent is characterised by a <tt>value</tt> which changes randomly at
 * each time step. His <tt>state</tt> depends on the sign of the
 * <tt>value</tt> (green if positive, red if negative).
 * 
 * @author Gilles Daniel (gilles@cs.man.ac.uk)
 * @version 1.0, 23-Feb-04
 * @version 1.1, 09-Mar-04
 * @see models.emptyModel.World
 */
           
   public class Agent extends EAgent {
           
   /**
	* Ising variables
	*  
	*/
	  protected int oldspin; // useless for now
   
	  protected int spin; // the spin
   
	  protected int futurspin;
   
   /**
	* The state of the agent.
	* <p>
	* <tt>value</tt>= 1 ---> <tt>state == 'green'<br>
	* <tt>value</tt> =-1 ---> <tt>state == 'red'
	*/
	  protected boolean state; // REDONDANT
   
   /**
	* magnitude of social influence (interaction)
	*/
	  protected double J;
   
   /**
	* idiosyncratic preference
	*/
	  protected double theta;
   
   /**
	* % of customers within the neighbourhood
	*/
	  protected double eta;
   
   /**
	* ex post Surplus : Willingness to pay - Price : W = h + theta + J.eta /
	* Suplus = W -P
	*/
	  protected double expostSurplus;
   
	  protected double A0, A1;
   
	  protected double mu, kapa, phi, delta;
   
   //protected double temperature = 0.0; // absolute temperature
   
   /*
	* flag : the state of agent (customer or not) has changed
	*/
	  protected boolean hasChanged;
   
   /**
	* expected fraction of customers within population
	*/
	  protected double expectedEta;
   
   //protected double thetaminprev; // variables de tests
   //protected double etaprev;
   
   /**
	* Create a new <tt>agent</tt>.
	*/
              
	  public Agent() {
              
		 super();
	  //System.out.println("[Agent()]");
	  }
   
   /**
	* Required to be a CAgent Invoked by <tt>ENeighbourWorld</tt>, when a
	* new agent is created.
	* <p>
	* method get info is invoked after the agent constructor but before
	* probe(descriptors) updates and initialisation. Mandatory method
	* (inherited from its abstract superclass in <tt>EAgent</tt>)
	* 
	* @see modulecoFramework.modeleco.ENeighbourWorld
	* @see modulecoFramework.modeleco.EAgent
	* @see modulecoFramework.modeleco.CAgent
	*/
              
	  public void getInfo() {
              
	  }
   
   /**
	* Invoked by SimulationControl.initSimulation() Initialise this EAgent
	* Required to be a CAgent
	* 
	* @see modulecoFramework.modeleco.SimulationControl.initSimulation()
	*/
              
	  public void init() {
              
		 if (agentID == 0)
			System.out.println("[Agent.init()]");
	  /**
	   * Define the preference from a Logistic distribution
	   */
		 if (((World) world)._stateofdistributiontheta == 0)
			this.theta = 0; // each agent gets the same value of theta
		 else
			this.theta = ((World) world).getRandom2().getDouble();
	  //...a random value of theta that with a logistic pdf
	  /**
	   * define the social influence
	   */
      
		 this.J = ((World) world).getJ();
	  /**
	   * Define the spin - Viktoriya's way
	   */
		 if (((World) world).repart.equals("Nobody buy")) {
			spin = -1;
			eta = 0;
		 } 
		 else if (((World) world).repart.equals("Everybody buy")) {
			spin = 1;
			eta = 1;
			((World) world).incBuyers();
		 } 
		 else {
			double r = ((World) world).getRandom().getDouble();
			if (r > 0.5) { //spins are given randomly
			   spin = 1;
			   ((World) world).incBuyers();
			} 
			else
			   this.spin = -1;
		 // dans ce cas il faut proceder autrement en
		 //tirant l'etat des agents et en calculant
		 //eta dans le World getInfo() cad avant l'init des agents
		 }
      
		 this.A1=((World) world).getRandom().getDouble();
      
		 this.mu = ((World) world).getMu(); // ces 4 lignes ne fonctionnnent pas chez moi, j'ai tout essayé, j'ai pas trouvé.
		 this.kapa = ((World) world).getKapa();
		 this.phi = ((World) world).getPhi();
		 this.delta = ((World) world).getDelta();
	  // IL RESTE DES INCOHERENCES !!
		 expostSurplus = ((World)world).h + theta + J * eta - getPrice();
		 if (agentID == 0)
			System.out.println("agent " + agentID + " computeExpostSurplus() :"
							  + expostSurplus);
      
	  /**
	   * Viktoriya adjustment Conversion SPIN <->boolean / Initialise the
	   * state of the agent
	   */
		 updateState();
	  //System.out.println("[models.a.Agent()] agent initiated");
	  }
   
   //	-------------------------------------------------------------------------------------------------------------
   //	
   //	METHODS
   //	
   //	-------------------------------------------------------------------------------------------------------------
   /**
	* this method is used to generate a new random eta estimated
	*/
              
	  public void generatenewetaEst() { //pas envie de creer un troisieme seed
              
	  // ...
		 this.expectedEta = ((World) world).getRandom().getDouble();
	  }
   
   /**
	* Implement HERE the behaviour of the agent.
	* <p>
	* Invoked at each time step.
	*/
              
	  public void compute() {
              
		 if (this.agentID == 1)
			System.out.println("[Agent.compute()] 1");//calcul :
	  // "+((World)world).calcul+"
	  // agents_restants =
	  // "+((World)world).agents_restants);
	  //if (this.agentID == 2)
	  //System.out.println("[Agent.compute()] 2");//calcul :
	  // "+((World)world).calcul+"
	  // agents_restants =
	  // "+((World)world).agents_restants);
	  //if (this.agentID == 3)
	  //System.out.println("[Agent.compute()] 3");//calcul :
	  // "+((World)world).calcul+"
	  // agents_restants =
	  // "+((World)world).agents_restants);
      
	  //		agentChange(Attraction)
		 if (A1>0) { // En fait, l'agent agit ici, puisqu'il ne depend que des elements du pas T-1
			if (spin == 1) {
			   oldspin = 1; //useless for now
			   futurspin = -1;
			   ((World)world).decBuyers(); 
			} 
			else {
			   oldspin = -1; // useless for now
			   futurspin = 1;
			   ((World) world).incBuyers();
			}
		 }
	  }
   
   /**
	* Just after compute, the agent commit.
	* <p>
	* Invoked at each time step
	*/
              
	  public void commit() {
              
		 if (this.agentID == 1)
			System.out.println("[Agent.commit()] 1");
	  //if (this.agentID == 2)
	  //System.out.println("[Agent.commit()] 2");
	  //if (this.agentID == 3)
	  //System.out.println("[Agent.commit()] 3");
      
	  //if(getAttraction())
      
	  //j'avoue n'avoir pas compris vos fonctions
	  A1=phi*A1+ (1-phi)* spin*(((World)world).h+theta+J*((World)world).getEta()-((World)world).getPrice()); 
		 // Et ici, il calcule ce qu'il fera pour le pas T+1
	  // Ceci (un peu long j'avoue) est la simplification de la formule que nous utiliserons.
		 if (futurspin != spin) { 
			if (spin == 1) { 
			   spin = -1; 
			} 
			else {
			   spin = 1; 
			}
		 }
      
		 updateState();
	  }
   
              
	  public double getPrice() {
              
		 return ((World) world).getPrice();
	  }
   
              
	  public boolean getAttraction() {
              
		 boolean tempA1 = true;
		 mu = (1 - kapa) * phi * mu + 1;
		 A0 = 0;
		 A1 = 0;
		 if (A1 > A0)
			tempA1 = true;
		 else
			tempA1 = false;
		 return tempA1;
	  }
   
   /*
	* public double getWillignessToPay() { double tempW = 0; return tempW; }
	*/
              
	  public double getEta() {
              
		 double tempEta = 0;
      
		 if (world.getNeighbourSelected() == "World")
			tempEta = ((World) world).getEta_world_moins1(this.spin);//ok pour
		 // late, et early, puisque les agents modifient eux-meme world.buyers
		 // avec agent.commit
		 else {
			if (connectivity == 0)
			   tempEta = 0; // pas nÈcessaire, car alors J = 0 ??
			else {
			   int buyers = 0;
			   for (Iterator i = neighbours.iterator(); i.hasNext();) {
				  Agent ag = ((Agent) i.next());
				  if (ag.spin > 0) //previouState
					 buyers++;
			   }
			   tempEta = (double) buyers / connectivity; //neighbours.size();
			}
		 }
		 return tempEta;
	  }
   
   /**
	* Compute the ex-post surplus of the agent method invoked by World.commit()
	* 
	* @see models.ising2.World.commit()
	*/
              
	  public void computeExpostSurplus(double price) {
              
		 eta = getEta();
		 expostSurplus = ((World)world).h + theta + J * eta - price;
		 if (agentID == 0)
			System.out.println("agent " + agentID + " computeExpostSurplus() :"
							  + expostSurplus);
	  }
   
   //======================================================================
   
   /**
	*  
	*/
              
	  public Boolean hasChanged() {
              
		 boolean temp;
		 temp = hasChanged;
		 hasChanged = false;
		 return new Boolean(temp);
	  }
   
   /**
	* Get the state of the agent Could be a boolean, an Integer, a Double, etc.
	*/
              
	  public Object getState() {
              
	  //System.out.println("[Agent.getState()]");
		 return new Boolean(state);
	  }
   
   /**
	* Hack used by the <em>Canevas</em>
	* 
	* @return a boolean
	* @see models.emptyModel.Canevas
	*/
              
	  public boolean getBooleanState() {
              
	  //System.out.println("[Agent.getBooleanState()]");
		 return state;
	  }
   
   /**
	* Return some information about the agent Accessible on right-click
	*/
              
	  public ArrayList getDescriptors() {
              
	  //System.out.println("[Agent.getDescriptors()]");
		 descriptors.clear();
	  /**
	   * <ul>
	   * <li>"Value" is the complete name to display when the agent is edited
	   * <li>"value" is the variable name. A method "setValue()" MUST EXIST
	   * <li>value is the variable itself
	   * <li>false means that you cannot edit the variable
	   * </ul>
	   */
		 descriptors.add(new IntegerDataDescriptor(this, "Value", "value", spin,
										  false));
		 descriptors.add(new DoubleDataDescriptor(this, "  % Eta", "eta", eta,
										  false));
		 descriptors.add(new DoubleDataDescriptor(this, "EWAttraction 1", "A1",
										  A1, false));
		 descriptors.add(new DoubleDataDescriptor(this, "EWAttraction 0", "A0",
										  A0, false));
		 descriptors.add(new DoubleDataDescriptor(this, "expost Surplus",
										  "expostSurplus", expostSurplus, false));
		 return descriptors;
	  }
   
   /**
	* Value accessor
	* 
	* @return value
	*/
              
	  public double getValue() {
              
		 return spin;
	  }
   
   /**
	* Set the <tt>value</tt>
	* 
	* @param value
	*/
              
	  public void setValue(int value) {
              
		 this.spin = value;
	  //if(agentID==0)
	  //System.out.println("setvalue "+value);
	  }
   
   //Vicktoria adjustments
   /**
	* Condiftions to change the state of the agent, and its color in the left
	* panel (<em>Canevas</em>)
	*  
	*/
              
	  public void updateState() {
              
		 if (spin == 1)
			state = true;
		 else
			state = false;
	  }
   }
/*
 * Partial is for compute the total energy of the system at the beginning public
 * void partial1() { double partial=-this.h; for (Iterator i = world.iterator();
 * i.hasNext();) partial+=((Agent) i.next()).partial2(this.agentID); ((World)
 * world).TotEnergy += (double)spin * partial; }
 * 
 * public double partial2(int no) { double par=0; if(no!=this.agentID) { par =
 * -(0.5) * this.J * (double)this.spin; } return par; } //Le code original
 * (present dans le world) :
 * 
 * On peut certainement faire mieux pour parcourir au carre la liste des agents.
 * 
 * for (i = 0; i < NumberOfAgents; i++) {
 * 
 * tmp2 = (Agent) (network.get(i)); partial = -tmp2.h; for (k = 0; k <
 * NumberOfAgents; k++) { tmp = (Agent) (network.get(k)); if (k != i) { partial =
 * -(0.5) * tmp.getJ() * tmp.getSpin() + partial; } } TotEnergy = TotEnergy +
 * tmp2.getSpin() * partial; }
 */
/*
 * public boolean agentChange(double thetaMin) { if (temperature <= 0.0) { if
 * (spin * theta < spin * thetaMin) { //System.out.println("theta prev :
 * "+thetaminprev+" theta : // "+thetaMin+ " eta prev : "+etaprev+" eta : //
 * "+((World)world).getEta()); return true; } else return false; } else { if
 * (((World) world).getRandom().getDouble() < Math.exp(spin (thetaMin - theta)) /
 * temperature) { //quatrieme seed ? return true; } else return false; } }
 */
/**
 * this section is devoted to decision whether accept or not the new state
 * Methode non invoquÈe !!
 */
/*
 * public boolean commit_plop(double deltaE) { double temperature = 0; oldspin =
 * spin;
 * 
 * if (deltaE <= 0) { // if energy would decrease... spin = -spin; // flip the
 * spin! return true; } else { // otherwise... if (Math.random() <
 * Math.exp(-deltaE / temperature)) { //Boltzmann // factor // gives flip spin =
 * -spin; return true; } else { return false; } } }
 */