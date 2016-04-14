/* Source File Name:		 PanelSud.java
 * Copyright:    Copyright (c)enst-bretagne
 * @author Denis.Phan@enst-bretagne.fr
 * created may, 29, 2000 - Interface graphique NON compatible jdk 1.1
* @version 1.1  july,10, 2001  
*/

   package modulecoGUI.grapheco;

   import java.awt.Panel;
   import java.awt.GridBagLayout;
   import java.awt.GridBagConstraints;

   import java.awt.event.MouseListener;
   import java.awt.event.MouseMotionListener;

   import javax.swing.ImageIcon;
   import javax.swing.JButton;

   import modulecoFramework.modeleco.CAgent;
   import modulecoGUI.grapheco.ControlPanel;

	//revoir les adresses en dur dans ImageENSTB()

   public class ImageENSTB extends Panel implements CAgentRepresentation
   {
	/**
	 * The name of this CAgentRepresentationContainer
	 */
	public String name;
      protected GridBagConstraints contrainte;
      protected CentralControl centralControl;
	  //	Static Access to static Method
	  protected static String modulecoPath = CentralControl.getModulecoPathRoot();
      
      public ImageENSTB()
      {
         contrainte = new GridBagConstraints();
      
         setLayout(new GridBagLayout());
      
         contrainte.anchor = GridBagConstraints.CENTER;// DP 29/06/2001
      	//DP 28/6/2001  getModulecoPathRoot()
         add(new JButton(new ImageIcon(modulecoPath + "modulecoFramework/grapheco/ImageENSTB.gif")), contrainte);
      }
   
      public void setCAgent(CAgent cAgent)
      {
      }
   
      public void setCentralControl(CentralControl centralControl)
      {
         this.centralControl=centralControl;
      }
   
      public void updateImage()
      {
      }
   
      public void resetImage()
      {
      }
   
      public void addMouseMotionListener(MouseMotionListener mouseMotionListener)
      {
      }
   
      public void addMouseListener(MouseListener mouseListener)
      {
      }
   
      public void setControl(ControlPanel control)
      {
      }
   
      public void initComponents()
      {
      }
   
      public void update()
      {
      }
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
