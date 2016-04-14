/** class sugarscape.Agent.java
 * Title:        Moduleco<p>
 * Description:  I am an EAgent. I represent an habitation, and not an habitant. My state depends on the habitant who lives in me. There are three states: a blue habitant, a red habitant, and no habitant.
 * Copyright:    Copyright (c)enst-bretagne
 * @author denis.phan@enst-bretagne.fr, Antoine.Beugnard@enst-bretagne.fr, frederic.falempin@enst-bretagne.fr
 * @version 1.2  august,5, 2002
* @version 1.4. february 2004   
 */
package models.sugarscape;

// import java.util.Iterator;
import java.util.ArrayList;

import modulecoFramework.modeleco.EAgent;
import modulecoFramework.modeleco.randomeco.CRandomDouble;
import modulecoFramework.modeleco.randomeco.Default;

import modulecoFramework.medium.NeighbourMedium;

public class Agent extends EAgent {
	/**
	 * Here are the different states for an agent
	 */

	protected static int nothingHere = 0;
	protected static int sugarHere = 1;
	protected static int antSugarHere = 2;
	protected static int toBeComputed = 3;

	protected static int produceMax = 6;
	protected static int sightMax = 6;

	protected int production;

	/**
	 *
	 * We name an object ant
	 */

	protected Ant ant;
	/**
	 * actualState represents the state of the agent (who lives on this
	 * habitation and if there is sugar ). futureState represents the state that
	 *is computed this turn. preferedState represents the state that can be stable
	 *for the agent
	 */
	protected int actualState, futureState, preferedState;
	/**
	 * The proportion of different (different color) neighbours the habitant
	 * find acceptable to continue to live in a given place
	 */
	protected double acceptation;
	/**
	 * The proportion of precalculated agents against random agents
	 */
	protected double randomize;
	/**
	 * The proportion of free agent (with no habitant) against occupated agent
	 */
	protected double freeAgentsProportion;
	/**
	 * The proportion of Red against Blue occupated agent
	 */
	protected double redBlueProportion;

	protected CRandomDouble random;

	public Agent() {
		super();
	}

	public void init() {

		neighbours = ((NeighbourMedium) mediums[0]).getNeighbours();

		futureState = toBeComputed;

		random = new Default();

		randomize = ((World) world).proportionOfAnts; //randomize

		//        freeAgentsProportion = ((World) world).freeAgentsProportion;
		//        redBlueProportion = ((World) world).redBlueProportion;

		if (10 * random.getDouble() >= 9) {
			production = (int) (produceMax * random.getDouble());
		}

		if (production == 0) {
			actualState = nothingHere;
		} else {
			actualState = sugarHere;
		}

		/*if (random.getDouble() <= randomize) { //isolé pour problèmes de compilation 14/076/2001
		actualState = antSugarHere;
		ant = new Ant(this);
		ant.init();
		}*/
	}

	public int getProduction() {
		return production;
	}

	public Object getState() {
		return new Boolean(actualState == antSugarHere);
	}

	public int getActualState() {
		return actualState;
	}

	public void setActualState(int newState) {

		actualState = newState;
		futureState = toBeComputed;
	}

	public Ant getAnt() {
		return ant;
	}

	public void setAnt(Ant newAnt) {
		ant = newAnt;
		actualState = antSugarHere;
	}

	public int getFutureState() {

		return futureState;
	}

	public void setFutureState(int newState) {

		futureState = newState;
	}

	/*   public int getPreferedState()
	 {
	     return preferedState;
	 }
	 public void setPreferedState(int newState)
	 {
	     preferedState = newState;
	 }
	*/

	public void commit() // Commit the change of the state after the global
	// computation...
	{
		actualState = futureState;
		futureState = toBeComputed;
		//if (ae != null) ae.update();
	}

	public String toString() {

		return (new Integer(actualState)).toString();
	}

	public void inverseState() {

		System.out.println("gant : " + agentID + " inverseState()");
	}

	//   protected boolean canAccept(int wantedState)

	/*    public void computePreferedState()
	 {
	     int numberOfNeighbours = 0;
	     int numberOfBlueNeighbours = 0;
	     int numberOfRedNeighbours = 0;
	     for (Iterator i = neighbours.iterator(); i.hasNext(); )
	     {
	         int stateOfNeighbour = ((Agent) i.next()).getActualState();
	         if (stateOfNeighbour == blueHere)
	         {
	             numberOfNeighbours++;
	             numberOfBlueNeighbours++;
	         }
	         else
	             if (stateOfNeighbour == redHere)
	             {
	                 numberOfNeighbours++;
	                numberOfRedNeighbours++;
	            }
	     }
	    if (numberOfNeighbours == 0) // To avoid dividing by by zero...
	         preferedState = nothingHere;
	     if (((double)numberOfRedNeighbours)/((double)numberOfNeighbours) <= acceptation)
	         preferedState = blueHere;
	     else
	         if (((double)numberOfBlueNeighbours)/((double)numberOfNeighbours) <= acceptation)
	             preferedState = redHere;
	         else
	             preferedState = nothingHere;
	 }
	*/
	public Boolean doILiveInTheGoodPlace() // Return false if the habitant
	// will have to move next turn
	{
		return new Boolean(
			(actualState == antSugarHere)
				&& (production > ant.getConsumption()));
	}
	/*
	 public boolean canAccept(int wantedState)
	 {
	     return (wantedState == preferedState);
	 }
	 */

	public void compute() {
		/*       computePreferedState();
		  if (!(actualState == nothingHere))
		      if (!(doILiveInTheGoodPlace().booleanValue()))
		      {
		          // My habitant have to move.
		          futureState = actualState; // If the move fails, the habitant stays
		          ((World) world).move(this); // Trying to move
		      }
		      else // Habitant live in the good place
		      {
		          // Oh brave new world (i.e. nothing to do, it is a good place for the habitant)
		          futureState = actualState;
		      }
		  else // Nobody here...
		      if (futureState == toBeComputed) //it means no body next turn too.
		      {
		          futureState = actualState;
		      }
		  //else there will be another guy out there.
		*/
		//if (actualState == antSugarHere) // Isolé le 14/07/2001 pour Pblm de compilation
		//((World) world).move(this);
	}

	public void getInfo() {
		//        this.acceptation  = ((World) world).acceptation;
		this.random = ((World) world).random;
	}

	protected void setRandom(CRandomDouble random) {
		this.random = random;
	}

	protected void setAcceptation(double acceptation) {
		if (acceptation >= 0 && acceptation <= 1)
			this.acceptation = acceptation;
	}

	protected void setRandomize(double randomize) {
		if (randomize >= 0 && randomize <= 1)
			this.randomize = randomize;
	}

	public ArrayList getDescriptors() {
		return new ArrayList();
	}

	/*    protected void setFreeAgentsProportion(double freeAgentsProportion)
	 {
	    if (freeAgentsProportion >=0 && freeAgentsProportion <= 1)
	         this.freeAgentsProportion = freeAgentsProportion;
	 }
	 protected void setRedBlueProportion(double redBlueProportion)
	 {
	     if (redBlueProportion >=0 && redBlueProportion <= 1)
	         this.redBlueProportion = redBlueProportion;
	 }
	*/
}
