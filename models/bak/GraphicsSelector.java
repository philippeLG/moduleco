/*
 * Créé le 22 mai 2004
 *
 */
package models.bak;

import modulecoGUI.grapheco.CentralControl;

/**
 * @author denis.phan@enst-bretagne.fr
 *
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
		this.packageName = packageName;
		displayXGraphics = true;
		//String packageName = ((this.getClass()).getPackage()).getName();
	}

	   public String getPreferredDynamicRepresentation(boolean xGraph){
		  String TempRep;   
		  if (xGraph){
			 TempRep="modulecoGUI.grapheco.XRepresentations";
		  //System.out.println(" world.getPreferredDynamicRepresentation() = XRepresentations");
		  }
		  else {
			 TempRep=packageName+".Graphique1";
		  //System.out.println(" world.getPreferredDynamicRepresentation() = Graphique");
		  }
		  return TempRep ;
	   }
   
	   public String getXRepresentationsComponents(int numComponent){
		  //System.out.println("getXRepresentationsComponents( "+numComponent+" )");
		  String stringTemp="";
		  switch(numComponent){
			 case 0 : stringTemp = packageName+".Graphique1" ;
				break;
			 case 1 : stringTemp = packageName+".Graphique2";
				break;
			 case 2 : stringTemp = packageName+".Graphique3";
				break;
			 case 3 : stringTemp = packageName+".BarChart";//BarChart Graphique3
				break;
			 default :
				break;
		  }	
		  return stringTemp ;
	   }
}
