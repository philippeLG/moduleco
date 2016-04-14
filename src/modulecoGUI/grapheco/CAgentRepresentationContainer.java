/**
 * CAgentRepresentationContainer.java
 * Created: Mon Aug 21 11:49:14 2000
 * @author frederic.falempin@enst-bretagne.fr
 * @version 1.3  august, 2003
 */

package modulecoGUI.grapheco;

import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JFileChooser;
import modulecoFramework.modeleco.CAgent;

/**
 * This abstract class is a container of CAgentRepresentation. Each time
 * an operation will be made on the container, the same one will be done
 * on each CAgentRepresentation contained by it.
 */
public abstract class CAgentRepresentationContainer
	extends CPanel
	implements CAgentRepresentation {
	/**
	 * The list of all the CAgentRepresentation contained by the container.
	 */
	protected ArrayList arrayListCAgentRepresentation;
	/**
	 * The name of this CAgentRepresentationContainer
	 */
	public String name;

	/**
	 * Create an empty CAgentRepresentationContainer
	 */
	public CAgentRepresentationContainer() {
		super();
		arrayListCAgentRepresentation = new ArrayList();
	}

	public void updateImage() {
		//System.out.println("updateImage step3 : CAgentRepresentationContainer from CentralControl");

		int j = 1;
		for (Iterator i = arrayListCAgentRepresentation.iterator();
			i.hasNext();
			) {
			((CAgentRepresentation) i.next()).updateImage();
			//le 03.10 2000 Le seul CAgentRepresentation dans l'ArrayList est le EPanel
		}
	}

	public void setCAgent(CAgent eWorld) {
		for (Iterator i = arrayListCAgentRepresentation.iterator();
			i.hasNext();
			) {
			//System.out.println("[CAgentRepresentationContainer.setCAgent()] i is a " + i.getClass().getName());
			CAgentRepresentation tmp = (CAgentRepresentation) i.next();
			//System.out.println("[CAgentRepresentationContainer.setCAgent] i.next().toString " + tmp.toString());
			try {
				//System.out.println("[CAgentRepresentationContainer.setCAgent()] eWorld is a " + eWorld.getClass().getName());
				tmp.setCAgent(eWorld);
				//System.out.println("[CAgentRepresentationContainer.setCAgent()] after");
			} catch (RuntimeException e) {
				// Catch the exception	
				System.out.println(
					"[CAgentRepresentationContainer.setCAgent()] !!!exception!!! "
						+ e.toString());
				// Exit
				//System.exit(1);	
			}
		}
	}

	public void resetImage() {
		for (Iterator i = arrayListCAgentRepresentation.iterator();
			i.hasNext();
			) {
			((CAgentRepresentation) i.next()).resetImage();
		}
	}

	/**
	 * Add a new CAgentRepresentation to the Container.
	 * @param cAgentRepresentation The CAgentRepresentation to be added
	 */

	public void addCAgentRepresentation(CAgentRepresentation cAgentRepresentation) {
		arrayListCAgentRepresentation.add(cAgentRepresentation);
		//System.out.println("(CAgentRepresentationContainerArrayList :"+((java.awt.Component)cAgentRepresentation).getName());
		//le 03.10 2000 Le seul CAgentRepresentation ajouté à l'ArrayList est le EPanel
	}

	public abstract JFileChooser getJFileChooser(); // DP august 2003

	public String getName() {
		if (name != null)
			return name;
		else
			return null;
	}

	public void setName(String s) {
		this.name = s;
	}

}
