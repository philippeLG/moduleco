/*
 * @(#)Agent.java	1.1 09-Mar-04
 */
package models.discreteChoice2;

import java.util.ArrayList;
import java.util.Iterator;
//import modulecoFramework.modeleco.randomeco.*;
import modulecoFramework.modeleco.EAgent;
import modulecoGUI.grapheco.descriptor.BooleanDataDescriptor;
import modulecoGUI.grapheco.descriptor.DoubleDataDescriptor;

//import modulecoGUI.grapheco.descriptor.InfoDescriptor;
//import modulecoGUI.grapheco.descriptor.IntegerDataDescriptor;

/**
 * Describe the behaviour of an agent.
 * <p>
 * Each agent is characterised by a <tt>value</tt> which may changes at each
 * time step. His <tt>state</tt> depends on the <tt>value</tt> black & wihte
 * (green if positive, red if negative).
 * 
 * @author denis.phan@enst-bretagne.fr
 * @version 1.0, 24-Jul-04
 */

public class Agent extends EAgent {
	/**
	 * State of the agent : boolean : to be or not to be a customer
	 */
	protected boolean oldState, state, nextState; // REDONDANT

	/**
	 * magnitude of social influence (interaction)
	 */
	protected double j;

	/**
	 * idiosyncratic preference
	 */
	protected double theta;

	/**
	 * % of customers within the neighbourhood
	 */
	protected double eta;

	/**
	 * idiosyncraticWP = h + theta
	 */
	protected double idiosyncraticWP;

	/**
	 * ex post Surplus : Willingness to pay - Price Willingness to Pay : W =
	 * idiosyncraticWP + J.eta Suplus = W -P
	 */
	protected double expectedSurplus, expostSurplus;

	protected double ewaA1;

	protected double mu, kapa, phi, delta;

	//protected double temperature = 0.0; // absolute temperature

	/**
	 * flag : the state of agent (customer or not) has changed
	 */
	protected boolean hasChanged;

	/**
	 * expected fraction of customers within population
	 */
	protected double expectedEta;

	//protected double thetaminprev; // variables de tests
	//protected double etaprev;
	protected boolean refractory, customer;

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
		//if (agentID == 0) System.out.println("[Agent.init()]");
		oldState = false;
		state = false;
		/*
		 * Load the idiosyncratic Willingness to pay (preferences)
		 * from a pdf
		 */
		theta = ((World) world).getRandom2().getDouble();
		idiosyncraticWP = ((World) world).h + theta;
		/*
		 * Load J, the social influence parameter
		 */
		this.j = ((World) world).getJ();
		/*
		 * Load the EWA parmeters : mu and delta
		 */
		mu = ((World) world).getMu0();
		delta = ((World) world).getDelta();
		/*
		 * Compute the initial EWA, given the initialBelief.
		 */
		double initialBelief = ((World) world).getInitialBelief() ;
		ewaA1 = getSurplus(idiosyncraticWP, initialBelief);
		if (ewaA1 > 0)
			nextState = true;
		else
			nextState = false;	
		/* myopicBRSR
		expectedSurplus = getSurplus(idiosyncraticWP, 0);
		if (expectedSurplus > 0)
			expostSurplus = expectedSurplus;
		else
			expostSurplus = 0;
		*/
		commit();
//=======================
		if (agentID == 0)
			System.out.println("agent " + agentID + " idiosyncraticWP = "
					+ idiosyncraticWP + " expostSurplus() :" + expostSurplus);
	}

	/**
	 * Implement HERE the behaviour of the agent.
	 * <p>
	 * Invoked at each time step.
	 */
	public void compute() {
		//if (this.agentID == 1)
		  //System.out.println("[Agent.compute()] 1");//calcul :
		/**
		 * update mu = (1 - kapa) * mu / (phi + mu) + kapa * (1 - phi);
		 * if kapa = 0 => mu = mu / (phi + mu) which converge to (1 - phi)
		 * if kapa >= 0 and phi = 0 mu = 1 for all kapa and all all mu0
		 * if kapa = 1 => mu = (1 - phi) for all mu0
		 *  
		 */
		mu = ((World) world).getMu();
		ewaA1 = computeAttraction(mu);
		refractory = (getSurplus(idiosyncraticWP, 1) < 0); //=
		/* myopicBRSR
	       expectedSurplus = getSurplus(idiosyncraticWP, getEta());
		   if (expectedSurplus > 0)
		*/
		if (ewaA1 > 0)
			nextState = true;
		else
			nextState = false;
	}
	
	public double computeAttraction(double mu) {
		int indicator;
		double updateAttraction, updatedSurplus;
		if (state)
			indicator = 1;
		else
			indicator = 0;
		updatedSurplus = (delta + (1 - delta) * indicator)
				* getSurplus(idiosyncraticWP, getEta());
		/**
		 * autoregressive update of attraction to adopt
		 */
		updateAttraction = (1 - mu) * ewaA1 + mu * updatedSurplus;
		return updateAttraction;

	}

	/**
	 * Just after compute, the agent commit.
	 * <p>
	 * Invoked at each time step
	 */
	public void commit() {
		//if (this.agentID == 0)
		//System.out.println("[Agent.commit()]");
		oldState = state;
		state = nextState;
		hasChanged = !(oldState == nextState); //VERIFIER
		//Viky
		if (state)
			((World) world).incrementBuyers();
	}

	//	-------------------------------------------------------------------------------------------------------------
	//	METHODS
	//	-------------------------------------------------------------------------------------------------------------
	/**
	 * this method is used to generate a new random eta estimated
	 */

	public double getSurplus(double IWP, double n) {
		double surplus = IWP + j * n - getPrice();
		return surplus;
	}

	/*
	 * public void generatenewetaEst() { //pas envie de creer un troisieme seed //
	 * ... this.expectedEta = ((World) world).getRandom().getDouble(); }
	 */

	public double getPrice() {

		return ((World) world).getPrice();
	}

	/*
	 * public double getWillignessToPay() { double tempW = 0; return tempW; }
	 */

	public double getEta() {

		double tempEta = 0;

		if (world.getNeighbourSelected() == "World")
			tempEta = ((World) world).getEta();//ok pour
		// late, et early, puisque les agents modifient eux-meme world.buyers
		// avec agent.commit
		else {
			if (connectivity == 0)
				tempEta = 0; // pas nécessaire, car alors J = 0 ??
			else {
				int buyers = 0;
				for (Iterator i = neighbours.iterator(); i.hasNext();) {
					Agent ag = ((Agent) i.next());
					if (ag.state == true) //previouState
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
	 * @see models.discreteChoice2.World.commit()
	 */
	public void computeExpostSurplus(double price) {
    	eta = getEta();
		expostSurplus = ((World) world).h + theta + j * eta - price;
		//if (agentID == 0)
		//System.out.println("agent " + agentID + " computeExpostSurplus() :"
		//+ expostSurplus);
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
	 * Get the state of the agent used by the <em>Canevas</em>
	 * 
	 * @return a Boolean Object
	 * @see modulecoGUI.grapheco.Canevas
	 */

	public Object getState() {
		//System.out.println("[Agent.getState()]");
		return new Boolean(nextState);
	}

	/**
	 * Return some information about the agent Accessible on right-click
	 */

	public ArrayList getDescriptors() {

		//System.out.println("[Agent.getDescriptors()]");
		descriptors.clear();
		descriptors.add(new DoubleDataDescriptor(this, " idiosyncratic WP",
				"idiosyncraticWP", idiosyncraticWP, false));
		descriptors.add(new DoubleDataDescriptor(this, "  expected Surplus",
				"expectedSurplus", expectedSurplus, false));
		descriptors.add(new DoubleDataDescriptor(this, "  expost Surplus",
				"expostSurplus", expostSurplus, false));
		descriptors.add(new DoubleDataDescriptor(this, "  % Eta", "eta", eta,
				false));
		descriptors.add(new DoubleDataDescriptor(this, "EWAttraction A1", "ewaA1",
				ewaA1, false));
		descriptors.add(new BooleanDataDescriptor(this, "Customer", "state",
				state, false));
		descriptors.add(new BooleanDataDescriptor(this, "Refractory",
				"refractory", refractory, false));
		return descriptors;
	}

	/**
	 * get the idiosyncratic Willingness to Pay
	 * 
	 * @return idiosyncraticWP
	 */
	public double getIdiosyncraticWP() {
		return idiosyncraticWP;
	}

	/**
	 * get the expected Surplus
	 * 
	 * @return expectedSurplus
	 */
	public double getExpectedSurplus() {
		return expectedSurplus;
	}
	/**
	 * get the EWAttraction A1
	 * 
	 * @return ewaA1
	 */
	public double getEwaA1() {
		return ewaA1;
	}
	

	/**
	 * get the effective ex post Surplus
	 * 
	 * @return expostSurplus
	 */
	public double getExpostSurplus() {
		return expostSurplus;
	}

	/**
	 * get if the agent is customer
	 * 
	 * @return state
	 */
	public boolean getCustomer() {
		System.out.println("getCustomer = " + state);
		return state;
	}

	public boolean getRefractory() {
		return refractory;
	}

}