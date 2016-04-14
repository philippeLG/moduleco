/**
 * EWorld.java
 * Copyright (c)enst-bretagne
 * @author Antoine.Beugnard@enst-bretagne.fr, Denis.Phan@enst-bretagne.fr, philippe.legoff@enst-bretagne.fr
 * @version 1.0  May 2000
 * @version 1.2  August 2002
 * @version 1.4  February 2004
 */
package modulecoFramework.modeleco;
import java.util.ArrayList;
import java.util.Iterator;
import java.io.Serializable;
import modulecoFramework.medium.Medium;
import modulecoGUI.grapheco.statManager.StatManager;
import modulecoGUI.grapheco.descriptor.DataDescriptor;
import modulecoGUI.Traces.Etat;
// MadKit
//import madkit.kernel.*;
// MadKit
/**
 * Je définis un monde (carré) puis le fais évoluer. Un monde est caractérisé
 * par les classes passées en paramètre du constructeur :
 * <p>
 * <ol>
 * <li>capacity : n*n, la taille du monde (le nombre d'agents visibles)
 * <li>eaClass : nom de la classe des agents (unique - donc tous agents
 * identiques))
 * <li>vsClass : nom de la classe décrivant la manière de construire les
 * voisinages)
 * <li>tsClass : nom de la classe décrivant la nature de l'évolution du temps
 * <li>zsClass : nom de la classe décrivant la zone qui évolue à chaque
 * évolution
 * </ol>
 * Je m'initialise en 4 phases :
 * <ol>
 * <li>populateAll(nsClass) pour la création des agents, médiums ...
 * <li>connectAll() pour leur interconnexion
 * <li>initAll() pour leur initialisation
 * <li>compute() pour définir mon état de départ
 * </ol>
 * J'execute chacun de mes pas dans un Thread contrairement aux EAgent...
 * <p>
 * Si l'on souhaite altérer la façon de parcourir mon contenu, il suffit de
 * redefinir iterator().
 * <p>
 * La methode edit() est spécialisée dans les sous-classes.
 */
public abstract class EWorld
		implements
			CAgent,
			Serializable {
	/**
	 * Define for each medium the connection strategy used to attach agents and
	 * mediums. There are as many mediums as connectionsStrategies. <br>
	 * Used only by subclasses.
	 */
	protected ZoneSelector[] connectionsStrategies;
	/**
	 * Used in this.initAll()
	 */
	protected Medium[] mediumsInWorld;
	/**
	 * Extra-Agents are Agents that are not visible.
	 */
	protected CAgent[] extraAgents = new CAgent[0];
	/**
	 * The ID of the world considered as an agent in the world it belongs (in
	 * case of recursive simulation)
	 */
	protected int agentID;
	/**
	 * N, the size of a row of agents. <br>
	 * The World is a N-by-N square of agents.
	 */
	public int length;
	/**
	 * The NxN visible agents
	 */
	public int agentSetSize;
	/**
	 * Descriptors of the World.
	 */
	public ArrayList descriptors;
	/**
	 * Check if this is a new world.
	 */
	protected Boolean newWorld;
	//protected boolean displayXGraphics = false; //dp 26/07/2002
	/**
	 * Manage the simulation.
	 */
	public SimulationControl simulationControl;
	/**
	 * Listen to the World.
	 */
	public WorldListener worldListener;
	/**
	 *  
	 */
	public ArrayList agentSet;
	/**
	 * Monitor the evolution of variables.
	 */
	protected StatManager statManager;
	//	====== RECURSIVITY
	/**
	 * My own Mediums, since I'm potentialy an Agent. used only by
	 * this.getMediums()- (in case of recursion)
	 */
	protected Medium[] mediums;
	/**
	 * The world I'm in (in case of recursion). <br>
	 * For parent word if needed, "this" by default.
	 */
	protected EWorld worldMother;
	//	====== RECURSIVITY
	/**
	 * Constructor of the EWorld.
	 * 
	 * @param length
	 */
	public EWorld(int length) {
		/**
		 * Define the length and size. <br>
		 * size = length * length
		 */
		this.length = length;
		this.agentSetSize = length * length;
		/**
		 * Create the agent set.
		 */
		agentSet = new ArrayList(agentSetSize);
		/**
		 * Initialisation of fields.
		 */
		getInfo();
		/**
		 * Get a reference to the StatManager.
		 */
		this.statManager = new StatManager(this);
		//		====== RECURSIVITY
		/**
		 * For recursivity. <br>
		 * Plus d'interet si pas de recursivite ? voir Jacques
		 */
		this.setWorld(this);
		//		====== RECURSIVITY
	}
	/**
	 * Get information from the editor (during <init>- constructor) <br>
	 * Required to be a CAgent.
	 */
	public abstract void getInfo();
	/**
	 * @param l
	 *            new World or old world (reloaded)
	 */
	public void buildDescriptors(Boolean l) {
		descriptors = new ArrayList();
		this.newWorld = l;
		if (l.booleanValue()) {
			setDefaultValuesAll();
		} else {
			setPreviousValues();
		}
	}
	public void setDefaultValuesAll() {
		//System.out.println("EWorld.DefaultValuesAll()");
		setDefaultValues();
	}
	/**
	 * Set the World DefaultValue setDefaultValues() must be defined by the
	 * subclasses
	 */
	public abstract void setDefaultValues();
	/**
	 * Set by default the World pevious Value in case of world reload
	 *  
	 */
	public void setPreviousValues() {
		//System.out.println("EWorld.setPreviousValues()");
		ArrayList a = new ArrayList();
		a = worldListener.getDescriptors_temp();
		if (a.size() != 0) {
			for (int i = 0; i < a.size(); i++) {
				((DataDescriptor) a.get(i)).setAgent(this);
				((DataDescriptor) a.get(i)).set();
			}
		} else {
			//System.out.println("no previous list");
		}
	}
	// MadKit
	//public void activate() {
	//	createGroup(false, "moduleco", "simulation", null, null);
	//	requestRole("moduleco", "simulation", "world", null);
	//}
	/**
	 * Populate the world with Agents, Mediums, ZoneSelectors method invoked by
	 * ModulecoLauncher.agentLauncher(EWorld ew) Not to redefine
	 * Methods that subclasses CAN redefine
	 */
	public void populateAll(String nsClass) {
		this.populate();
	}
	/**
	 * Populate the world with Agents, Mediums, ZoneSelectors This is a default
	 * implementation to be ascendent compatible with the previous neighbourhood
	 * management Methods that subclasses CAN redefine
	 */
	// Because populate() can be redefine by subclasses
	// please add command line in populateAll but not in populate()
	public void populate() {
		//System.out.println("eWorld.populate()");
	}
	/**
	 * Methods that subclasses CAN redefine
	 */
	public void connectAll() {
		//System.out.println("eWorld.connectAll()");
		this.connect();
	}
	/**
	 * Connect Agents with Mediums... This is a default implementation to be
	 * ascendent compatible with the previous neighbourhood management It
	 * assumes that Agent have already created a NeighbourMedium.
	 * Methods that subclasses CAN redefine 
	 */
	public void connect() {
		// Because connect() can be redefine by subclasses
		// please add command line in connectAll() but not in connect()
		//System.out.println("eWorld.connect()");
	}
	/**
	 * Initialize all states (Mediums and Agents) method invoked by
	 * modulecoFramework.modeleco.SimulationControl
	 * Methods that subclasses CAN redefine
	 */
	public void initAll() {
		//System.out.println("eWorld.InitAll()");
		for (Iterator i = agentSet.iterator(); i.hasNext();) {
			((EAgent) i.next()).init();
		}
		if (extraAgents != null) {
			for (int i = 0; i < extraAgents.length; i++) {
				extraAgents[i].init();
			}
		}
		if (mediumsInWorld != null) {
			for (int i = 0; i < mediumsInWorld.length; i++) {
				mediumsInWorld[i].init();
			}
		}
		this.init();
	}
	/**
	 *  Initialize the world. Do nothing, but can be overloaded in subclasses.
	 *  Required to be a CAgent. Methods that subclasses CAN redefine
	 * @see{ENeighbourWorld, mobility.EMobileWorld}
	 */
	public void init() {
		// Because init() can be redefine by subclasses
		// please add command line in initAll() but not in init()
		//System.out.println("eWorld.init()");
	}
	//	==== step method invoked at the initialization
	/**
	 * Required to be a CAgent. Methods that subclasses MUST redefine MISE AU
	 * POINT DOIT DEVENIR ABSTRAITE Liste des modeles qui n'implementent pas
	 * compute : discrete Choice, Ecxploration Exploitation, KMR Lux segragation
	 * twoPart Tarif Competition
	 */
	public void compute() {
	} //abstract ;
	//	==== step method ====
	/**
	 * At each time step, method inoked by SimulationControl.progress() Commit
	 * the Cagent state change.
     *  Methods that subclasses CAN redefine
	 * @see modulecoFramework.modeleco.SimulationControl.progress()
	 */
	public void commitAll() { //DP 05/08/2002
		//System.out.println("eWorld.commitAll()");
		compute();
		commit();
		if (worldListener.isRecording())
			statManager.step();
	}
	/**
	 * Commit the Cagent state change. First recalculate the world state, then
	 * update the statManager, then the display. Required to be a CAgent.
	 *  Methods that subclasses CAN redefine
	 */
	public void commit() {
		// Because commit() can be redefine by subclasses
		// please add command line in commitAll() but not in commit()
		//System.out.println("eWorld.commit()");

	}
	//============ Terminate the process =====
	/**
	 * To close my editor and those of my content (CAgents). Required to be a
	 * CAgent. invoked by SimulationControl.terminate()
	 * 
	 * @see modulecoFramework.modeleco.EAgent.terminate()
	 * @see modulecoFramework.modeleco.SimulationControl.terminate()
	 */
	public void terminate() {
		//System.out.println("EWorld.terminate()");
		worldListener.terminate();
	}
	/**
	 * MadKit / non active method linked with terminate() ?
	 *  
	 */
	public void cleanup() {
		System.out.println("eWorld.cleanup()");
	}
	// VERIFIER SI METHODES OBSOLETE : pour assurer la compatibilité ascendante
	/**
	 *  
	 */
	public void restart() {
		//System.out.println("eWorld.restart()");
		descriptors = getDescriptors();
		worldListener.restart();
	}
	// invoked by Lux & BilateralMarket
	public int getIter() { // added DP 02/08/2001
		System.out.println("eWorld.getIter()");
		return simulationControl.getIter();
	}
	//==== AgentSet =====================================
	/**
	 * get the ith element of the population encapsulate method get() of the
	 * class ArrayList
	 *  
	 */
	public Object get(int i) {
		return agentSet.get(i);
	}
	/**
	 * return an iterator on the elements of the population encapsulate class
	 * iterator() associated with ArrayList
	 */
	public Iterator iterator() {
		return agentSet.iterator();
	}
	/**
	 * insert a CAgent at the ith position on the elements of the population
	 * encapsulate method add() of the class ArrayList
	 */
	public void add(int i, CAgent a) {
		agentSet.add(i, a);
	}
	/**
	 * encapsulate method indexOf() of the class ArrayList (used in many
	 * subclasses)
	 * 
	 * @param agent
	 * @return the index of this CAgent within the ArrayList
	 */
	public int getIndex(CAgent agent) {
		return agentSet.indexOf(agent);
	}
	/**
	 * return the size of the population
	 *  
	 */
	public int size() {
		return agentSet.size();
	}
	/**
	 * Returns the arraylist of the agentSet
	 *  
	 */
	public ArrayList getAgents() {
		return agentSet;
	}
	//== DESCRIPTORS & EDITORS
	// ==================================================
	/**
	 *  
	 */
	public ArrayList getDescriptors() {
		descriptors.clear();
		//descriptors.add(new InfoDescriptor("No data"));
		return descriptors;
	}
	/**
	 * @param vars
	 * @return
	 */
	public Object getPState(ArrayList vars) {
		ArrayList etats = new ArrayList();
		ArrayList des = new ArrayList();
		for (Iterator i = vars.iterator(); i.hasNext();)
			for (Iterator j = this.getDescriptors().iterator(); j.hasNext();) {
				DataDescriptor d = (DataDescriptor) j.next();
				if (d.getCompleteName().equals((String) i.next()))
					des.add(d);
			}
		if (des.size() > 0) {
			Etat etat = new Etat("World");
			etat.setDescriptors(des);
			etats.add(etat);
		}
		for (Iterator i = agentSet.iterator(); i.hasNext();) {
			if ((Object) ((EAgent) i.next()).getPState(vars) != null)
				etats.add((Etat) ((EAgent) i.next()).getPState(vars));
		}
		CAgent[] extraAgent = this.getExtraAgents();
		if (extraAgent.length > 0) {
			for (int i = 0; i < extraAgent.length; i++)
				if ((Object) ((EAgent) extraAgent[i]).getPState(vars) != null)
					etats.add(((EAgent) extraAgent[i]).getPState(vars));
		}
		return etats;
	}
	/**
	 * @param avars
	 * @param eavars
	 * @param wvars
	 * @return
	 */
	public Object getPState(ArrayList avars, ArrayList eavars, ArrayList wvars) {
		ArrayList etats = new ArrayList();
		ArrayList des = new ArrayList();
		ArrayList desc = this.getDescriptors();
		for (Iterator j = wvars.iterator(); j.hasNext();) {
			String varName = (String) j.next();
			for (Iterator i = desc.iterator(); i.hasNext();) {
				DataDescriptor d = (DataDescriptor) i.next();
				if (d.getDataName().equals(varName))
					des.add(d);
			}
		}
		if (des.size() > 0) {
			Etat etat = new Etat("World");
			etat.setDescriptors(des);
			etats.add(etat);
		}
		for (Iterator i = this.agentSet.iterator(); i.hasNext();) {
			Etat etat = (Etat) ((EAgent) i.next()).getPState(avars);
			if (etat.getDescriptors().size() > 0)
				etats.add(etat);
		}
		CAgent[] extraAgent = this.getExtraAgents();
		if (extraAgent.length > 0) {
			for (int i = 0; i < extraAgent.length; i++) {
				Etat etat = (Etat) ((EAgent) extraAgent[i]).getPState(eavars);
				if (etat.getDescriptors().size() > 0) {
					etat.setAgentType("Extra Agent");
					etats.add(etat);
				}
			}
		}
		return etats;
	}
	/**
	 * @param agentIds
	 * @param avars
	 * @param eavars
	 * @param wvars
	 * @return
	 */
	public Object getPState(ArrayList agentIds, ArrayList avars,
			ArrayList eavars, ArrayList wvars) {
		ArrayList etats = new ArrayList();
		ArrayList des = this.getDescriptors();
		ArrayList des1 = new ArrayList();
		for (Iterator i = wvars.iterator(); i.hasNext();) {
			String varName = (String) i.next();
			for (Iterator j = des.iterator(); j.hasNext();) {
				DataDescriptor d = (DataDescriptor) j.next();
				if (d.getDataName().equals(varName))
					des1.add(d);
			}
		}
		if (des1.size() > 0) {
			Etat etat = new Etat("World", des1);
			etats.add(etat);
		}
		for (Iterator i = agentSet.iterator(); i.hasNext();) {
			EAgent agent = (EAgent) i.next();
			if (agentIds.contains(new Integer(agent.agentID))) {
				Etat etat = (Etat) agent.getPState(avars);
				if (etat.getDescriptors().size() > 0)
					etats.add(etat);
			}
		}
		CAgent[] extraAgent = this.getExtraAgents();
		if (extraAgent.length > 0) {
			for (int i = 0; i < extraAgent.length; i++) {
				Etat etat = (Etat) ((EAgent) extraAgent[i]).getPState(eavars);
				if (etat.getDescriptors().size() > 0) {
					etat.setAgentType("Extra Agent");
					etats.add(etat);
				}
			}
		}
		return etats;
	}
	//CK 23/07/2002
	/**
	 *  
	 */
	public boolean isNewWorld() {
		return this.newWorld.booleanValue();
	}
	//=============================================
	// Communication avec WorldEditor : services accessibles du package
	// uniquement
	//OBSOLETE !!
	/*
	 * public void WorldEdit() { try { EWorldEditor cwe =
	 * centralControl.getEWorldEditor(); cwe.setEWorld(this); } catch (Exception
	 * e) { e.printStackTrace(); } }
	 */
	// ACCESSORS ==========================================
	/**
	 * Inverse world state. Do nothing. Can be overloaded. Required to be a
	 * CAgent.
	 */
	public void inverseState() {
	}
	/**
	 * Create the Autorun of the model.
	 * 
	 * @return the Autorun
	 */
	public CAutorun createAutorun() {
		return null;
	}
	/**
	 * ######### <br>
	 * Accessors <br>
	 * #########
	 */
	/**
	 * Get the old Extra-Agents. <br>
	 * Use worldListener.
	 */
	public CAgent[] returnOldExtraAgents() {
		return worldListener.returnOldExtraAgents();
	}
	/**
	 * Get the neighbourhood selected in the GUI. <br>
	 * Use worldListener
	 */
	public String getNeighbourSelected() {
		return worldListener.getNeighbourhood();
		//return centralControl.controlPanel.getNeighbourSelected();
	}
	/**
	 * Access to the world state. Do nothing. Can be overloaded. Required to be
	 * a CAgent.
	 */
	public Object getState() {
		return null;
	}
	/**
	 * Return the array of non visible CAGents (extraAgents)
	 * @see{models.market.World}
	 */
	public CAgent[] getExtraAgents() {
		return extraAgents;
	}
	/**
	 * Return the array of mediums in this world.
	 * @see{medium.Medium}
	 * Required to be a CAgent.
	 */
	public Medium[] getMediums() {
		return mediums;
	}
	/**
	 * Get the world I'm in (usualy myself) Required to be a CAgent.
	 */
	public EWorld getWorld() {
		return worldMother;
	}
	/**
	 * Get the number of agents in this world Math.square(getLength())
	 */
	public int getAgentSetSize() {
		return agentSetSize;
	}
	/**
	 * Get the length of this EWorld.
	 * 
	 * @return length
	 */
	public int getLength() {
		return length;
	}
	/**
	 * Get the StatManager.
	 * 
	 * @return statManager
	 */
	public StatManager getStatManager() {
		return statManager;
	}
	/**
	 * Get the full path of the implementation of this EWorld.
	 * 
	 * @return name
	 */
	public String pack() {
		String name = this.getClass().getName();
		return name.substring(0, name.lastIndexOf('.'));
	}
	/**
	 * Get the package name of the implementation of this EWorld.
	 */
	public String getPackageName() {
		return ((this.getClass()).getPackage()).getName();
	}
	/**
	 * Get the descriptors of this EWorld.
	 * 
	 * @return descriptors
	 */
	public ArrayList returnDescriptors() {
		return descriptors;
	}
	/**
	 * The agentID of this EWorld is -1.
	 * 
	 * @return -1
	 */
	public int getAgentID() {
		return -1;
	}
	/**
	 * Set the world I'm in (usualy myself) Required to be a CAgent.
	 * 
	 * @param eWorld
	 */
	public void setWorld(EWorld eWorld) {
		worldMother = eWorld;
	}
	/**
	 * Set the agent ID in the world I'm in
	 */
	public void setAgentID(int agentID) {
		this.agentID = agentID;
	}
	/**
	 * Set the SimulationControl. <br>
	 * Used in SimulationCrontol.buildScheduler()
	 * 
	 * @param simulationControl
	 */
	public void setSimulationControl(SimulationControl simulationControl) {
		this.simulationControl = simulationControl;
	}
	/**
	 * Set the WorldListener.
	 * 
	 * @param worldListener
	 */
	public void setWorldListener(WorldListener worldListener) {
		this.worldListener = worldListener;
	}
}