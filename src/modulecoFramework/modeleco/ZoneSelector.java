/**
 * Title:        ZoneSelector<p>
 * Description:  Je suis une classe abstraite, racine du pattern Strat�gie. Je choisis
 * pour un world la zone qui va �voluer. Le calcul est effectu� par les m�thodes compute.<p>
 * Les m�thodes compute() et compute(int) doivent �tre red�finies.<p>
 * Sous classes concr�tes connues sont dans le package: @see{modeleco.zone}.<p>
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
	* La taille et le cot� du monde suppos� "carr�".<p>
	* Sont stock�e pour ne pas �tre racalcul�es. <p>
	*/
	protected int agentSetSize, length;
	// optimisation...souvent utiles donc stockees !

	/**
	* On d�finit le monde, et on conserve sa taille (nombre de CAgent) et son cot�.
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
	* Calcule une zone du monde ind�pendament d'une position
	*/
	public abstract ArrayList compute();

	/**
	* Calcule une zone du monde en fonction d'une position
	*/
	public abstract ArrayList compute(int index);
}
