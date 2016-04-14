/**
 * Title:        ZoneSelector<p>
 * Description:  Je suis une classe abstraite, racine du pattern Stratégie. Je choisis
 * pour un world la zone qui va évoluer. Le calcul est effectué par les méthodes compute.<p>
 * Les méthodes compute() et compute(int) doivent être redéfinies.<p>
 * Sous classes concrètes connues sont dans le package: @see{modeleco.zone}.<p>
 * @author Antoine.Beugnard@enst-bretagne.fr
 * created may 2000
* @version 1.2  august,5, 2002
 */
package modulecoFramework.modeleco;

import java.util.ArrayList;
import java.io.Serializable;

public abstract class ZoneSelector implements Serializable {

	/**
	* Le monde dans lequel une zone sera choisie.
	*/
	protected EWorld world;

	/**
	* La taille et le coté du monde supposé "carré".<p>
	* Sont stockée pour ne pas être racalculées. <p>
	*/
	protected int agentSetSize, length;
	// optimisation...souvent utiles donc stockees !

	/**
	* On définit le monde, et on conserve sa taille (nombre de CAgent) et son coté.
	*/
	public void setWorld(EWorld ew) {
		world = ew;
		agentSetSize = ew.getAgentSetSize();
		length = ew.getLength();
	}

	public EWorld getWorld() {
		return world;
	}

	/**
	* Calcule une zone du monde indépendament d'une position
	*/
	public abstract ArrayList compute();

	/**
	* Calcule une zone du monde en fonction d'une position
	*/
	public abstract ArrayList compute(int index);
}
