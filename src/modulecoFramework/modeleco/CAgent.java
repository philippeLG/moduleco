/**
 * Title:        CAgent<p>
 * Description:  I represent a Moduleco Agents, that is autonomous active object.
 * This activity is described in methods body of <t>compute()</t>.
 * An activity is validated when method <t>commit()</t> is executed.
 * My main subclasses are:
 * <ul>
 * <li> EAgent: an elementary activity
 * <li> EPlace: a special agent that represents a localization and may contain another CAgent...
 * <li> EWorld: an agent composed of other CAgents by the way of agentSet (Pattern Composite)
 * </ul>
 * Agents interact via EMedium.
 * Known implementors: @see{modeleco.EWorld, modeleco.EAgent, modeleco.mobility.EPlace, modeleco.mobility.EMobileAgent}.<p>
 * Copyright: (c)enst-bretagne
 * @author Antoine.Beugnard@enst-bretagne.fr, Denis.Phan@enst-bretagne.fr ; sebastien.chivoret@ensta.org
 * created may, 2000
 * @version 1.4.2  june, 2004
 */

package modulecoFramework.modeleco;

import java.util.ArrayList;
import java.io.Serializable;
import modulecoFramework.medium.Medium;

public interface CAgent extends Serializable {
	/**
	 * Initialize and compute an initial step with propagation within the sub-world if necessary.
	 */
	public void init();
	/**
	 * Compute a step for this CAgent.
	 */
	public void compute();
	/**
	 * Commit the Cagent state change.
	 */
	public void commit();
	/**
	 * To close my editor and those of my content (CAgents).
	 */
	public void terminate(); // ferme l'éditeur ses sous-editeurs

	// ==========ACCESSORS ===================
	/**
	 * Access to the CAgent state. 
	 */
	public Object getState();
	/**
	 * Return the array of mediums for this CAgent.
	 * @see{medium.Medium}
	 * Used by ENeighbourgWorld & canvasSmallWorld
	 */
	public Medium[] getMediums();
	/**
	 * Get the agent ID in the agentSet
	 */
	public int getAgentID();

	// =========== UTILS ====================
	/**
	 * Return the name of the agent's package.
	 */
	// not used for CAgent, only useful for dynamicLinks
	public String pack();

	// PROBE - DESCRIPTORS
	/**
	 * Get information from the editor (during <init> - constructor)
	 */
	public void getInfo();
	/**
	 * fields descriptors (probe)
	 * build the ArrayList of descriptors
	 */
	public ArrayList getDescriptors();

}