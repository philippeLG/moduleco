/*
 * (c)enst-bretagne, created May 2000
 */
package modulecoFramework.modeleco.scheduler;
import java.util.ArrayList;
import java.util.Iterator;
import modulecoFramework.modeleco.TimeScheduler;
import modulecoFramework.modeleco.CAgent;
/**
 * One specific Time Scheduler. The agents and extra agents of the world evolve as follows:<br>
 * <ul>
 * <li>1: extraAgent.compute(), extraAgent.commit() one by one
 * <li>2: agents.compute()
 * <li>3: agents.commit() 
 * </ul>
 * 
 * @see{modeleco.scheduler.LateCommitScheduler}
 * @see{modeleco.TimeScheduler}
 * @author Antoine.Beugnard@enst-bretagne.fr, revised by Denis.Phan@enst-bretagne.fr
 * @version 1.0 May 2000
 * @version 1.2 August 5, 2002
 * @version 1.4 June 2004
 *  @version 1.4 June 2004
 */
public class ExtraAgentFirstThenLateCommit extends TimeScheduler {
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
		 * extraAgents.commit() <br>
		 */
		for (int j = 0; j < ea.length; j++) {
			ea[j].compute();
			ea[j].commit();
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
	}
}