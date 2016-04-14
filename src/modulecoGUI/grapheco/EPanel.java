/**
 * Title : EPanel.java
 * Description:  This class is the main panel.
 * It implements CAgentRepresentation. It contains the ControlPanel (with
 * all buttons, allowing the user to control the simulation.
 * Copyright:    Copyright (c)enst-bretagne
 * @author Antoine.Beugnard@enst-bretagne.fr, Denis.Phan@enst-bretagne.fr
 * created on may 2000
 * @version 1.3  august, 2003
 */

package modulecoGUI.grapheco;

// import java.awt.Graphics;
// import java.awt.Dimension;
// import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.Component;

import javax.swing.JComponent;
// import javax.swing.JPanel;

import java.lang.reflect.Constructor;
 import java.lang.reflect.InvocationTargetException;

import java.util.ArrayList;

import modulecoFramework.modeleco.EWorld;
import modulecoFramework.modeleco.CAgent;
import modulecoGUI.grapheco.EAgentEditor;

public class EPanel extends CPanel implements CAgentRepresentation {
	/**
		* The name of this CAgentRepresentationContainer
		*/
	public String name = "EPanel Moduleco 1.5";
	protected EWorld eWorld;

	protected ControlPanel controlPanel;
	protected CentralControl centralControl;
	public CAgentRepresentation leftRepresentation; //protected DP 20/08/2002
	public CAgentRepresentation rightRepresentation; //protected DP 20/08/2002

	protected String model;

	protected ArrayList additionalPanels;
	protected boolean displayTorusWorld,
		displayCircleWorld,
		displayGraphic,
		displayXGraphics;

	protected EAgentEditor ae;

	protected GraphicsSelector graphicsSelector;

	public EPanel(int width, int height, CentralControl centralControl) {
		this.centralControl = centralControl;
		//System.out.println("EPane.constructor-DEBUT");
		additionalPanels = new ArrayList();

		leftRepresentation = (CAgentRepresentation) new IntroCARPanel();
		rightRepresentation =
			(CAgentRepresentation) new IntroCARPanel("No Model Selected");
		/*
		                    catch (ClassNotFoundException e){System.out.println(e.toString());}
		             catch (java.lang.InstantiationException e){System.out.println(e.toString());}
		             catch (java.lang.IllegalAccessException e){System.out.println(e.toString());}
		        */
		initComponents();
		//this.width = width;
		//this.height = height;
		//setSize(width, height);
		//setBackground(Color.lightGray);
		//System.out.println("EPane.constructor-FIN");
	}

	public void setControlPanel(ControlPanel cp) {
		// since version1.3 DP august 2003
		this.controlPanel = cp;
	}

	public ControlPanel getControlPanel() {
		// since version1.3 DP august 2003
		return controlPanel;
	}

	protected void initComponents() {
		constraints.weighty = 1.0;

		constraints.anchor = GridBagConstraints.NORTH;
		this.add((Component) leftRepresentation, 0, 1, 1, 1);

		constraints.anchor = GridBagConstraints.NORTH;
		this.add((Component) rightRepresentation, 1, 1, 1, 1);

		constraints.weighty = 0.0;
		constraints.anchor = GridBagConstraints.SOUTH;

		validate(); //SC 01.06.01
	}
	//============= CAgentRepresentation methods ============
	public void setCentralControl(CentralControl centralControl) {
		// inactif car passé par le constructeur !
	}
	public void resetImage() {
		//REVOIR UTILITE DE CETTE METHODE
		//System.out.println("EPanel.resetImage()");
		//rightRepresentation.resetImage();
		//leftRepresentation.resetImage();
	}

	public void updateImage() {
		if (displayTorusWorld) {
			//System.out.println("updateImage step4l : EPanel(leftRepresentation) from CAgentRepresentationContainer");
			leftRepresentation.updateImage();
		}
		//displayXGraphics=controlPanel.checkMenuDisplayXGraphics.getState();
		if (displayXGraphics) {
			//System.out.println("updateImage step4r : EPanel(rightRepresentation)/displayXGraphic from CAgentRepresentationContainer");
			rightRepresentation.updateImage(); // ((XRepresentations)
			//System.out.println(rightRepresentation);
		} else {
			//System.out.println("updateImage step4r : EPanel(rightRepresentation /displayGraphic from CAgentRepresentationContainer");
			rightRepresentation.updateImage();
		}
	}
	/**
	 * 
	 */
	public void setCAgent(CAgent cAgent) {
		//System.out.println("EPanel.setCAgent()");
		eWorld = (EWorld) cAgent;
		buildGraphicSelector();
		// setLeftRepresentation(centralControl.loadCAgentRepresentationClass(eWorld.getPreferredStaticRepresentation()));
		setLeftRepresentation(
			centralControl.loadCAgentRepresentationClass(
				graphicsSelector.getPreferredStaticRepresentation()));
		//RightRepresentation
		displayXGraphics = graphicsSelector.getDisplayXGraphicsEnabled();
		//displayXGraphics=getDisplayXGraphicsEnabled();
		if (displayXGraphics) {
			//System.out.println("EPanel.setCAgent(); displayXGraphics = "+displayXGraphics);
			setRightRepresentation(
				centralControl.loadCAgentRepresentationClass(
					graphicsSelector.getPreferredDynamicRepresentation(
						displayXGraphics)));
		} else
			setRightRepresentation(
				centralControl.loadCAgentRepresentationClass(
					graphicsSelector.getPreferredDynamicRepresentation()));
		//System.out.println("EPanel.setCAgent()");
		leftRepresentation.setCAgent(cAgent);
		rightRepresentation.setCAgent(cAgent);
		constraints.weighty = 0.0;
		//System.out.println("[EPanel.setCAgent()] About to leftRepresentation.updateImage()");
		rightRepresentation.updateImage(); // NullPointerException
		//System.out.println("[EPanel.setCAgent()] About to rightRepresentation.updateImage()");
		leftRepresentation.updateImage();
		validate();
	}

	public void buildGraphicSelector() {
		Constructor GraphicsSelectorConstructor;

		String packageName = eWorld.getPackageName();
		String temp = "";
		// "modulecoGUI.grapheco";//

		try {
			temp = (Class.forName(packageName + ".GraphicsSelector")).getName();
			Class GraphicsSelectorConstructorParameterTypes[] =
				new Class[] {
					Class.forName("modulecoGUI.grapheco.CentralControl"),
					Class.forName("java.lang.String")};
			// Object
			Object GraphicsSelectorConstructorParameters[] =
				new Object[] { centralControl, packageName };
			//System.out.println("[EPanel.buildGraphicSelector()] before GraphicSelectorConstructor : "+"models." + model + ".GraphicsSelector");

			GraphicsSelectorConstructor =
				Class.forName(
					packageName + ".GraphicsSelector").getConstructor(
					GraphicsSelectorConstructorParameterTypes);
			graphicsSelector =
				(GraphicsSelector) GraphicsSelectorConstructor.newInstance(
					GraphicsSelectorConstructorParameters);
			// if class GraphicsSelector do not exist for this package (model),
			// one use the original modulecoGUI.grapheco.GraphicsSelector 

		} catch (ClassNotFoundException e) {
			//System.out.println("ClassNotFoundException");
			graphicsSelector =
				new GraphicsSelector(centralControl, packageName);
		} catch (IllegalAccessException e) {
			System.out.println("GraphicsSelector ; " + e);
		}catch (InstantiationException e) {
			System.out.println("GraphicsSelector ; " + e);
		} 
 catch (InvocationTargetException e) {
			System.out.println("GraphicsSelector ; " + e);
		} 
		catch (NoSuchMethodException e) {
			System.out.println("GraphicsSelector ; " + e);
		}
		/*
		catch (Exception e) {
				//System.out.println("[EPanel.buildGraphicSelector] "+e.toString());
			}
		*/
	}

	//============ END CAgentRepresentation methods ============

	/*   
	   public void paint(Graphics g)
	   {
	      g.fill3DRect(0, 0, this.getSize().width,  this.getSize().height, true);
	   }
	
	*/

	public void setLeftRepresentation(CAgentRepresentation cAgentRepresentation) {
		// instancié par : this.setCAgent
		remove((Component) leftRepresentation);

		leftRepresentation = cAgentRepresentation;
		leftRepresentation.setSize(
			(int) ((double) getSize().width * .5),
			getSize().height - controlPanel.getMinimumSize().height);

		constraints.fill = GridBagConstraints.BOTH;
		constraints.weighty = 1.0;
		this.add((Component) leftRepresentation, 0, 1, 1, 1);
		leftRepresentation.setName("leftRepresentation");
		validate();
		//System.out.println("EPanel.setLeftRepresentation()");
	}

	public void setRightRepresentation(CAgentRepresentation cAgentRepresentation) {
		// instancié par : this.setCAgent bak.Graphique et par grapheco.BarChart.
		remove((Component) rightRepresentation);

		rightRepresentation = cAgentRepresentation;
		rightRepresentation.setSize(
			(int) ((double) getSize().width * .5),
			getSize().height - controlPanel.getMinimumSize().height);

		constraints.fill = GridBagConstraints.BOTH;
		constraints.weighty = 1.0;
		this.add((Component) rightRepresentation, 1, 1, 1, 1);
		rightRepresentation.setName("rightRepresentation");
		validate();
		//System.out.println("EPanel.setRightRepresentation()");
	}

	//============== AdditionalPanels ==================

	public void addAdditionalPanel(EAdditionalPanel eAdditionalPanel) {
		additionalPanels.add(eAdditionalPanel);
		constraints.weighty = 0.0;
		constraints.anchor = GridBagConstraints.SOUTH;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		add(eAdditionalPanel, 0, 2 + additionalPanels.size(), 2, 1);
		validate();
	}

	public void addAdditionalPanel(
		EAdditionalPanel eAdditionalPanel,
		EAdditionalPanel parent) {
		eAdditionalPanel.setParentAdditionalPanel(parent);
		additionalPanels.add(eAdditionalPanel);
		constraints.weighty = 0.0;
		constraints.anchor = GridBagConstraints.SOUTH;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		add(eAdditionalPanel, 0, 2 + additionalPanels.size(), 2, 1);
		validate();
	}

	public void removeAdditionalPanel(EAdditionalPanel eAdditionalPanel) {
		ArrayList tempArray = new ArrayList();

		if (eAdditionalPanel != null) {
			super.remove(eAdditionalPanel);
			additionalPanels.remove(eAdditionalPanel);
		}
		for (java.util.Iterator i = additionalPanels.iterator();
			i.hasNext();
			) {
			EAdditionalPanel eap = (EAdditionalPanel) i.next();
			if (eap.getParentAdditionalPanel() == eAdditionalPanel)
				tempArray.add(eap);
		}
		for (java.util.Iterator i = tempArray.iterator(); i.hasNext();) {
			removeAdditionalPanel((EAdditionalPanel) i.next());
		}
		validate();
	}

	public void resetAdditionalPanels() {
		int s = additionalPanels.size();
		for (int i = 0; i < s; i++) {
			removeAdditionalPanel((EAdditionalPanel) additionalPanels.get(0));
		}
		additionalPanels.clear();
	}

	public ArrayList getAdditionalPanels() {
		return additionalPanels;
	}

	//================= END AdditionalPanels ==================

	//================= Display Graphics======================

	protected void setDisplayGraphic() {
		displayGraphic = controlPanel.checkMenuDisplayGraphic.getState();
		//System.out.println("EPanel.setDisplayGraphic() : "+ displayGraphic);
	}

	protected boolean getDisplayXGraphicsEnabled() {
		//System.out.println("EPanel.getDisplayXGraphicsEnabled()");
		displayXGraphics = graphicsSelector.getDisplayXGraphicsEnabled();
		return displayXGraphics;
	}

	//================= END Display Graphics ======================

	public void setMenuBar() {
		(((JComponent) this.getParent()).getRootPane()).setJMenuBar(
			controlPanel.getMenuBar());
	}

	//============================  Display Torus or Circle World ===============

	// EPanel.updateDisplayXGraphics() invoqued by :
	// CentralControl.updateDisplayXGraphics()
	// in case
	// @see modulecoGUI.grapheco.CentralControl.updateDisplayXGraphics()
	//
	protected void setDisplayXGraphics(boolean b) {
		displayXGraphics = b;
		controlPanel.checkMenuDisplayXGraphics.getState();

		//System.out.println("EPanel.setDisplayXGraphics() : "+ displayXGraphics);
	}

	protected void setDisplayTorusWorld() {
		displayTorusWorld = controlPanel.checkMenuDisplayTorusWorld.getState();
		//System.out.println("EPanel.setDisplayTorusWorld() State : "+displayTorusWorld);
	}

	protected void setDisplayCircleWorld() {
		displayCircleWorld =
			controlPanel.checkMenuDisplayCircleWorld.getState();
		//System.out.println("EPanel.setDisplayCircleWorld() State : "+displayCircleWorld);
	}

	//

	// since version1.3 DP august 2003
	public void CheckMenuDisplayWorld() {

		if (controlPanel.checkMenuDisplayTorusWorld.getState())
			setDisplayTorusWorld();
		else
			setDisplayCircleWorld();
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
