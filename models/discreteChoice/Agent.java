/** class discreteChoice.Agent.java
 * Title:        Moduleco<p>
 * Description:
 * Copyright:    Copyright (c)enst-bretagne
 * @author denis.Phan@enst-bretagne.fr 
 * @version 1.4  February, 2004
 */

package models.discreteChoice;

import java.util.Iterator;
import java.util.ArrayList;

import modulecoFramework.modeleco.EAgentLinks;
//import modulecoFramework.modeleco.randomeco.Random;
// import modulecoGUI.grapheco.descriptor.DataDescriptor;
import modulecoGUI.grapheco.descriptor.DoubleDataDescriptor;
import modulecoGUI.grapheco.descriptor.BooleanDataDescriptor;
import modulecoGUI.grapheco.descriptor.IntegerDataDescriptor;

// import modulecoFramework.medium.NeighbourMedium;
// import modulecoFramework.medium.Medium;

import modulecoFramework.modeleco.randomeco.RandomSD;

public class Agent extends EAgentLinks {
	/**
	 * state is the actual state . stateC is the computing state
	 */
	protected int state, stateC, previouState;
	// values : 0 nothing, 1 first company, 2 second company ....
	/**
		*
		*/
	protected boolean refractory, innovative, adopter, hasChanged;
	// commentaires
	/** price
	  */
	protected double price;
	/**
	* Proportion of neighbours
	*/
	protected double eta;
	/**
	* Willingness to pay common parameters
	*/
	protected static double u1 = 1;
	/**
		* Willingness to pay externality magnitude (common parameter)
		*/
	protected double a;
	/**
	* idosyncrasics Agent's parameters
	*/
	protected double theta, thetaMin;

	/**
	* test proportion of neighbours (true : all competitors , false : one);
	*/
	protected static boolean proportion; // fonction ??

	/**
	 * random (PRECISER)
	 */
	protected RandomSD random2;
	/**
		*
		*/
	protected Market market;

	protected boolean test = true; // DP 20/08/2002
	protected double lastPrice;

	//Part I - Initialisation of the agent =================
	/**
	* getInfo()
	* receieve Info from the Eworld Before connection and initialisation
	* @see modulecoFramework.modeleco.ENeighbourWorld
	*
	*/
	public void getInfo() {
	}
	/**
	* init()
	*/
	public void init() {
		super.init();
		//if (agentID==0)
		//System.out.println(" agent0.init() ");
		previouState = 0;
		stateC = 0;
		a = 0.5;
		this.random2 = ((World) world).getRandom2();
		//idiosyncrasic individual coefficient
		//theta = random2.getDouble();

		if ((((World) world).random_s2).equalsIgnoreCase("JavaLogistic")) {
			theta = random2.getDouble(); //
		} else
			theta = random2.getDouble() - 0.5;
		//if (agentID==0)
		//System.out.println ("Agent.init() : NeighbourGeneration");  
		//if (java.lang.StrictMath.abs(theta)>1)
		//System.out.println ("agent "+agentID+" theta= "+theta);
		// environmental information 
		//neighbours = ((NeighbourMedium) mediums[0]).getNeighbours();
		// pour améliorer la computabilité avec voisinage monde :
		// constituer une liste de voisins spécifique unique pour tous les agents
		connectivity = neighbours.size(); // DP 13/09/2002
		/*
			System.out.print ("agent "+agentID);
		for (Iterator i= neighbours.iterator();i.hasNext();) {
		   Agent ag =((Agent) i.next());
		   System.out.print (" voisin : "+ag.agentID);
		}
		System.out.println();
			*/

		if (agentID == 0)
			 ((World) world).setNeighbourSize(neighbours.size()); //??
		market = ((World) world).getMarket();
	}
	/**
	* marketOpen()
	*/
	public void marketOpen() {

		price = setMenuPrice();
		// The agent get all menu of prices for the market
		innovative = (theta > price - u1 ? true : false);
		refractory = (theta <= price - u1 - a ? true : false);
		//if (refractory)
		//System.out.println(" agent "+ agentID+ "refractory theta = "+ theta+" price-u1-a ="+ (price-u1));
		//if (innovative)
		//System.out.println(" agent "+ agentID+ "innovative theta = "+ theta+" price-u1 ="+ (price-u1));

	}

	// Part II - Agent's computations
	/**
	*
	*/
	public double thetaMin(double p, double eta) {

		double thetaMin = price - (u1 + a * eta);

		return thetaMin;
	}
	/**
	*computeEta()
	*/

	public double computeEta() {
		double f;
		double connect;
		double Nc;
		double N;

		if (world.getNeighbourSelected() == "World") {
			//optimisation ok pour late, mais pas pour early !!!!
			/*
				* Nc = Number of customers in the world in t-1 (at le last market clear)
				*/
			Nc = (double) market.getNCustomers();
			N = (double) neighbours.size();
			f = Nc / N;
			//System.out.println("Agent : "+agentID+" Nc = "+Nc+" N = "+N+" f = "+f);
			//f = ((Double) ((World) world).getState()).doubleValue();
		} else {
			connect = (double) connectivity;
			if (connectivity == 0) {
				//if (agentID==0)System.out.println("connectivity = "+connectivity);
				f = 0;
			} else {

				int nbAdopter = 0;

				for (Iterator i = neighbours.iterator(); i.hasNext();) {
					Agent ag = ((Agent) i.next());
					if (ag.state > 0) //previouState
						nbAdopter++;
				}
				f = (double) nbAdopter / connect; //neighbours.size();
			}
			//System.out.println("Agent : "+agentID+" connectivity = "+connect+" f = "+f);
		}
		return f;
	}

	/**
	*compute()
	*/

	public void compute() {
		price = setMenuPrice();
		//The agent get all menu of prices for the market

		//System.out.println(" Compute()");

		eta = computeEta();
		thetaMin = thetaMin(price, eta);
		if (theta > thetaMin) {
			stateC = 1;
			//System.out.println("StateC =1");
		} else
			stateC = 0;
		lastPrice = price;
	}

	/**
	*commit()
	*/
	public void commit() {
		if (state != stateC) {
			hasChanged = true;
			// format BEGIN
			//String priceString = (new Integer((new Double(lastPrice*10000)).intValue())).toString();
			//String priceStringFormated = priceString.substring(0,1)+"."+priceString.substring(1);
			// Format END
			//System.out.println("price = "+priceStringFormated+" ; t = "+((World) world).centralControl.getIter()+" ; agentID : "+agentID+" hasChanged !");	// SP 16/01/2003
			if (stateC == 1)
				market.buy(agentID);
			else
				market.removeCustomer(agentID);
		}
		previouState = state;
		state = stateC;

		//if (agentID==0)
		//System.out.println(" agent.commit() ");

		//
	}
	// Part III - Data Exchanges =================
	/**
	*
	*/
	public double setMenuPrice() {
		//if (agentID==0)
		//System.out.println(" agent0.setMenuPrice() ");
		return price = market.getPrice();
	}
	/**
	  * Return stable state (Competitor number)
	* methode call from XXX at each Canevas update (including between iterations)
	  */
	public Object getState() {
		//System.out.println(" agent.getState() ");
		return new Boolean(stateC == 0 ? false : true);
	}
	/**
	 * Return boolean state  (buy or no) >> DOUBLE EMPLOI AVEC PRECEDENT ?
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
		theta = newTheta;
	}
	/**
	*
	*/
	public double getTheta() {

		return theta;
	}

	public double getAdjustedWP() {
		double wp;
		//wp=java.lang.StrictMath.max(a*eta+theta,0) ;
		wp = a * eta + theta;
		if (wp > 1.8 && test) { // DP 20/08/2002
			//System.out.println("agent : "+agentID+" wp = a*eta+theta = "+wp);
			test = false;
		}
		return wp;
	}
	public double getEta() {

		return eta;
	}

	/**
	*
	*/
	/*
	   public void setProportion(boolean b) {
	   
	      //if (agentID==0)
	         //System.out.println(" Agent.setProportion");
	      proportion =b;
	   }
		*/
	/**
	*
	*/
	public void inverseState() {
		state = 0;
		stateC = state;
		//if (ae != null) ae.update ();
		//adopter = !adopter;
	}

	public boolean getRefractory() {
		return refractory;
	}
	public boolean getInnovative() {

		return innovative;
	}
	/**
	*
	*/

	public Boolean hasChanged() {
		boolean temp;

		temp = hasChanged;
		hasChanged = false;
		return new Boolean(temp);
	}
	public void inverseRefractory() {
		double aleatemp = random2.getDouble();
		if (aleatemp > thetaMin)
			theta = aleatemp;
		else
			theta = 1 - aleatemp;
		refractory = !refractory;

	}

	public ArrayList getDescriptors() {

		descriptors.clear();
		descriptors.add(
			new IntegerDataDescriptor(this, "State", "state", state, false));
		//descriptors.add(new BooleanDataDescriptor(this,"Has changed","hasChanged",hasChanged,false));
		descriptors.add(
			new BooleanDataDescriptor(
				this,
				"Innovative",
				"innovative",
				innovative,
				false));
		descriptors.add(
			new BooleanDataDescriptor(
				this,
				"Refractory",
				"refractory",
				refractory,
				false));
		descriptors.add(
			new DoubleDataDescriptor(this, "Eta", "eta", eta, false, 4));
		descriptors.add(
			new DoubleDataDescriptor(this, "Theta", "theta", theta, false, 6));
		descriptors.add(
			new IntegerDataDescriptor(
				this,
				"Connectivity",
				"connectivity",
				connectivity,
				false));
		// add DP 13/09/2002
		return descriptors;
	}

	public int getConnectivity() {
		connectivity = neighbours.size();
		//if( connectivity == ((World) world).getNeighbourSize())System.out.println("TEST");
		//else System.out.println("agent : "+agentID+" connectivity = "+connectivity);
		return connectivity;

		//for (Iterator i= neighbours.iterator();i.hasNext();) {
		//Agent ag =((Agent) i.next()); 
	}

}