/*
 * Créé le 22 mai 2004 
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
   package models.discreteChoice2;

   import modulecoGUI.grapheco.CentralControl;

/**
 * @author phan1
 *
 * Pour changer le modèle de ce commentaire de type généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
           
   public class GraphicsSelector extends modulecoGUI.grapheco.GraphicsSelector {
           
   
   /**
	* @param centralControl
	* @param packageName
	*/
              
	  public GraphicsSelector(CentralControl centralControl,
						String packageName) {
              
		 super(centralControl, packageName);
	  //System.out.println("ising2.GraphicsSelector.constrctor / packageName : "+packageName);
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
			} 
			else {
			   TempRep = (Class.forName(packageName + ".Graphique")).getName();
			}
		 } 
                    
			catch (ClassNotFoundException e) {
                    
            
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
		 //System.out.println("GraphicsSelector.getXRepresentationsComponents( "+numComponent+" )");
			String packageName = ((this.getClass()).getPackage()).getName();
			switch (numComponent) {
			   case 0 :
				  stringTemp =
						(Class.forName(packageName + ".Graphique")).getName();
			   //stringTemp = "models.explorationExploitation.BarChart0" ;
				  break;
			   case 1 :
				  stringTemp =
						(Class.forName(packageName + ".Graphique_price")).getName();
				  break;
			   case 2 :
				  stringTemp =
						(Class.forName(packageName + ".Graphique_all3")).getName();
				  break;
			   case 3 :
				  stringTemp =
						(Class.forName(packageName + ".BarChart1")).getName();
			   //BarChart Graphique3
				  break;
			   default :
				  break;
			}
         
		 } 
                    
			catch (ClassNotFoundException e) {
                    
            
			   e.printStackTrace();
            
			}
	  //System.out.println("ising2.GraphicsSelector.getXRepresentationsComponents : return "+stringTemp);
		 return stringTemp;
	  }
   }