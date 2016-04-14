/*
 * Cr�� le 22 mai 2004
 *
 * Pour changer le mod�le de ce fichier g�n�r�, allez � :
 * Fen�tre&gt;Pr�f�rences&gt;Java&gt;G�n�ration de code&gt;Code et commentaires
 */
package models.discreteChoice;

import modulecoGUI.grapheco.CentralControl;

/**
 * @author phan1
 *
 * Pour changer le mod�le de ce commentaire de type g�n�r�, allez � :
 * Fen�tre&gt;Pr�f�rences&gt;Java&gt;G�n�ration de code&gt;Code et commentaires
 */
public class GraphicsSelector extends modulecoGUI.grapheco.GraphicsSelector {

	/**
	 * @param centralControl
	 * @param packageName
	 */
	public GraphicsSelector(
		CentralControl centralControl,
		String packageName) {
		super(centralControl, packageName);
		displayXGraphics = true;
	}
	/**
	 * 
	 */
	public String getPreferredDynamicRepresentation(boolean xGraph) {
		String TempRep = "";
		try {
			String packageName = ((this.getClass()).getPackage()).getName();

			if (xGraph) {
				TempRep = "modulecoGUI.grapheco.XRepresentations";
			} else {
				TempRep = (Class.forName(packageName + ".Graphique")).getName();
			}
		} catch (ClassNotFoundException e) {

			e.printStackTrace();

		}
		return TempRep;
	}

	/**
	 * 
	 */
	public String getXRepresentationsComponents(int numComponent) {
		String stringTemp = "";
		try {
			//System.out.println("getXRepresentationsComponents( "+numComponent+" )");

			String packageName = ((this.getClass()).getPackage()).getName();
			switch (numComponent) {
				case 0 :
					stringTemp =
						(Class.forName(packageName + ".Graphique")).getName();
					//stringTemp = "models.explorationExploitation.BarChart0" ;
					break;
				case 1 :
					stringTemp =
						(Class.forName(packageName + ".BarChart1")).getName();
					break;
				case 2 :
					stringTemp =
						(Class.forName(packageName + ".BarChart2")).getName();
					break;
				case 3 :
					stringTemp =
						(Class.forName(packageName + ".BarChart3")).getName();
					//BarChart Graphique3
					break;
				default :
					break;
			}

		} catch (ClassNotFoundException e) {

			e.printStackTrace();

		}
		return stringTemp;
	}
}
