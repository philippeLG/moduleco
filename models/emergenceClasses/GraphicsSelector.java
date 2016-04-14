/*
 * Créé le 22 mai 2004
 *
 */

package models.emergenceClasses;


/**
 * @author denis.phan@enst-bretagne.fr
 */
import modulecoGUI.grapheco.CentralControl;

public class GraphicsSelector extends modulecoGUI.grapheco.GraphicsSelector {

String packageName;
	
	public GraphicsSelector(CentralControl centralControl, String packageName){
	super(centralControl, packageName);
	this.packageName = packageName;
	//packageName = ((this.getClass()).getPackage()).getName();
	}
	
	public String getPreferredDynamicRepresentation() {
		//		instancié depuis Epanel.setCAgent()via centralControl.loadCAgentRepresentationClass - surcharge EWorld
		//System.out.println("models.emergenceClasses.GraphicsSelector.getPreferredDynamicRepresentation");
		return packageName + ".SimplexCanevas";
	}

	public String getPreferredStaticRepresentation() {
		//		instancié depuis Epanel.setCAgent() centralControl.loadCAgentRepresentationClass - surcharge EWorld
		//System.out.println("models.emergenceClasses.World.getPreferredStaticRepresentation");
		return packageName + ".SimplexCanevas";
	}

}
