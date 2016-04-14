/*
 * Créé le 22 mai 2004
 *
 * @author denis.phan@enst-bretagne.fr
 */
package modulecoGUI.grapheco ;

/**
 * @author phan1
 *
 * Pour changer le modèle de ce commentaire de type généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class GraphicsSelector {

	protected CentralControl centralControl;
	protected String packageName;
	protected boolean displayXGraphics = false;

	public GraphicsSelector(
		CentralControl centralControl,
		String packageName) {
		this.centralControl = centralControl;
		this.packageName = packageName;
		centralControl.setGraphicsSelector(this);
	}
	/**
	 * Return the string representing the class of the prefered static representation of the world
	 * by default : "modulecoGUI.grapheco.Canevas"
	 * Can be overloaded by subclasses.
	 */
	public String getPreferredStaticRepresentation() {
		//System.out.println("GraphicsSelector.getPreferredStatic");
		String temp = "";
		String WorldTypeRepresentation;
		//worldListener.getDisplayWorldType() Top tot ! PBLM LAUCHER
		if (centralControl.getDisplayWorldType()) {
			try {
				temp =
					(Class.forName(packageName + ".canvasSmallWorld"))
						.getName();
			}
			// if class Canevas do not exist for this package (model),
			//one use the original modulecoGUI.grapheco.canvasSmallWorld
			// if class Canevas dont' exist in the package 
			catch (ClassNotFoundException e1) {
				//System.out.println("ClassNotFoundException");
				//e.printStackTrace();
				try {
					temp = (Class.forName(packageName + ".Canevas")).getName();
					centralControl.getControlPanel().setCircleWorldDesabled();
				} catch (ClassNotFoundException e2) {
					temp = "modulecoGUI.grapheco.canvasSmallWorld";
				}
			}
		} else {
			try {

				temp = (Class.forName(packageName + ".Canevas")).getName();
			}
			// if class Canevas do not exist for this package (model),
			//one use the original modulecoGUI.grapheco.Canevas 
			// if class canvasSmallWorld dont' exist in the package 

			catch (ClassNotFoundException e1) {

				//System.out.println("ClassNotFoundException");
				//e.printStackTrace();
				try {
					temp =
						(Class.forName(packageName + ".canvasSmallWorld"))
							.getName();
				} catch (ClassNotFoundException e2) {

					temp = "modulecoGUI.grapheco.Canevas";
				}

			}
		}
		//System.out.println("GraphicsSelector.getPreferredStaticRepresentation() - String packageName = "+((this.getClass()).getPackage()).getName()+" temp = "+temp);

		return temp; // instancié depuis Epanel.setCAgent()
	}
	/*
	* method GraphicsSelector.getDisplayXGraphicsEnabled() 
	* return the boolean state of the default XGraphic option for this world.
	* invoqued by CentralControl.updateMenuDisplayXGraphics()
	* and by
	* @see modulecoGUI.grapheco.CentralControl.updateMenuDisplayXGraphics()
	*/
	public boolean getDisplayXGraphicsEnabled() { // modifié DP 27/07/2002
		//System.out.println("GraphicsSelector.getDisplayXGraphicsEnabled() ="+displayXGraphics);
		boolean tempDisplayXGraphics ;
		if (displayXGraphics == false)
		tempDisplayXGraphics = centralControl
					.getControlPanel()
					.checkMenuDisplayXGraphics
					.getState();
					else
		tempDisplayXGraphics = true ;
		return tempDisplayXGraphics ;
	}
	/**
	 * Return the string representing the class of the prefered dynamic representation of the world
	 * by default : "modulecoGUI.grapheco.graphix.Graphique"
	 * Can be overloaded by subclasses.
	 */
	public String getPreferredDynamicRepresentation() {
		//System.out.println("GraphicsSelector.getPreferredDynamic");
		String temp = "";
		// "modulecoGUI.grapheco";//

		try {
			temp = (Class.forName(packageName + ".Graphique")).getName();
		}
		// if class Canevas do not exist for this package (model),
		//one use the original modulecoGUI.grapheco.Graphique 

		catch (ClassNotFoundException e) {
			//System.out.println("ClassNotFoundException");
			//e.printStackTrace();
			temp = "modulecoGUI.grapheco.graphix.Graphique";
		}
		return temp;
		// instancié depuis Epanel.setCAgent()
	}
	/**
	 * 
	 * @param xGraph
	 * @return
	 */
	public String getPreferredDynamicRepresentation(boolean xGraph) {
		return "modulecoGUI.grapheco.graphix.XRepresentations";
		// instancié depuis Epanel.setCAgent()
	}
	/**
	 * 
	 * @param numComponent
	 * @return
	 */
	public String getXRepresentationsComponents(int numComponent) {

		// Instancié depuis Xreprésentations.setCAgent()
		return "modulecoGUI.grapheco.ImageENSTB"; // added DP 29/10 2001
	}
}
