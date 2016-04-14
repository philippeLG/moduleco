/**
 * Title:        TimeScheduler<p>
 * Description:  Implantation du Pattern Stratégie, je propose (pour l'instant)
 * 2 variantes @see{modeleco.EarlyCommitScheduler}, @see{modeleco.LateCommitScheduler}.<p>
 * Mes sous classes concrètes doivent implanter step().<p>
 * Je choisis une zone à faire évoluer en fonction du contenu de selector @see{modeleco.ZoneSelector}.<p>
 */
package modulecoFramework.modeleco;
import java.io.Serializable;
/**
 * Manage the time evolution of the world. <br>
 * 1: get the zone to make evolve via the ZoneSelector <br>
 * 2: make evolve the agents (CAgents) in the zone selected
 * <p>
 * This is an abstract clas. My children have to implement steps(), which
 * defines the way agents evolve.
 * 
 * @author Antoine.Beugnard@enst-bretagne.fr created mai 2000
 * @version 1.1 July 10, 2001
 * @version 1.2 August 5, 2002
 */
public abstract class TimeScheduler implements Serializable {
	/**
	 * The world.
	 */
	protected EWorld world;
	/**
	 * The ZoneSelector zs describes the evolving zone of agents. <br>
	 * e.g. Moore
	 */
	protected ZoneSelector zs;
	/**
	 * ExtraAgents, not included in the ZoneSelector.
	 */
	protected CAgent[] ea;
	/**
	 * Define the zone to make evolve.
	 * 
	 * @param zs, the ZoneSelector
	 */
	public void setZoneSelector(ZoneSelector zs) {
		this.zs = zs;
		world = zs.getWorld();
		ea = world.getExtraAgents();
	}
	/**
	 * Make the agents and extra agents evolve one step.
	 */
	public abstract void step();
}