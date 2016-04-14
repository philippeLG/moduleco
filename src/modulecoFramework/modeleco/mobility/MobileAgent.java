/**
 *
 * Title:        AgentMobile<p>
  * Description:  Je suis susceptible de calculer mon voisinage à chaque pas...<p>
  * ATTENTION : MES SOUS CLASSES DOIVENT FAIRE super.compute() à la fin de compute() !<p>
  * Par défaut mon voisinage est calculé par @see{modeleco.zone.BoundedRandom}.<p>
  * @author Antoine.Beugnard@enst-bretagne.fr
  * Created may 2000
  * @version 1.1  july,10, 2001
 *
 */

package modulecoFramework.modeleco.mobility;

import modulecoFramework.modeleco.EAgent;
import modulecoFramework.modeleco.mobility.Move;
import modulecoFramework.modeleco.exceptions.AlreadyUsedPlaceException;

public abstract class MobileAgent extends EAgent {
	protected Move move;
	/**
	 * Chaque AgentMobile définit son voisinage.
	 */
	protected EPlace place;

	public MobileAgent() {
	}

	public void go(EPlace p) { // called by receive in EPlace
		try {
			place.leave();
			p.receive(this);

		} catch (AlreadyUsedPlaceException e) {
			e.printStackTrace();
		}
	}

	public void setMove(Move move) {
		this.move = move;
	}

	/**
	 * Search an emply place to go...using the move algorithm.
	 */

	public void move() {
		move.move(this, (EMobileWorld) world);
	}
/**
 * called by receive in EPlace
 * @param p : set a place 
 */
//VERIFIER LA FONCTION DE CETTE METHODE
	public void setPlace(EPlace p) { 
		place = p;
	}
/**
 * 
 * @return the place where is this agent
 */
	public EPlace getPlace() {
		return place;
	}

	public abstract boolean canAccept(EPlace p);
}