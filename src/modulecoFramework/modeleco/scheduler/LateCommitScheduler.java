/**
 * Title:        LateCommitScheduler<p>
 * Description:  Pour tous les CAgent, je fais faire un pas (progress), puis, une fois tous
 * avancé, je les valide tous (commit).<p>
 */
package modulecoFramework.modeleco.scheduler;
import java.util.ArrayList;
import java.util.Iterator;
import modulecoFramework.modeleco.TimeScheduler;
import modulecoFramework.modeleco.CAgent;
/**
 * One specific Time Scheduler. The agents and extra agents of the world evolve as follows:<br>
 * <ul>
 * <li>1: extraAgents.compute()
 * <li>2: agents.compute()
 * <li>3: agents.commit() 
 * <li>4: extraAgents.commit() 
 * </ul>
 * 
 * @see{modeleco.scheduler.EarlyCommitScheduler}
 * @see{modeleco.TimeScheduler}
 * @author Antoine.Beugnard@enst-bretagne.fr revised by Denis.Phan@enst-bretagne.fr
 * @version 1.0 May 2000
 * @version 1.2 August 5, 2002
 *  @version 1.4 June 2004
 */
public class LateCommitScheduler extends TimeScheduler {
	/**
	 * Make the agents and extra agents evolve one step.
	 */
	public void step() {
		Iterator i;
		/**
		 * Get the agents in the zone of the world to make evolve.
		 */
		ArrayList agentsList = zs.compute();
		/**
		 * extraAgents.compute() <br>
		 * inversed by DP 20/01/2003
		 */
		for (int j = 0; j < ea.length; j++) {
			ea[j].compute();
		}
		/**
		 * agents.compute()
		 */
		for (i = agentsList.iterator(); i.hasNext();) {
			CAgent a = (CAgent) i.next();
			a.compute();
		}
		/**
		 * agents.commit() 
		 */
		for (i = agentsList.iterator(); i.hasNext();) {
			CAgent a = (CAgent) i.next();
			a.commit();
		}
		/**
		 * extraAgents.commit() 
		 */
		for (int j = 0; j < ea.length; j++) {
			ea[j].commit();
		}
	}
}