/* Source File Name:		 PanelSud.java
 * Copyright:    Copyright (c)enst-bretagne
 * @author Denis.Phan@enst-bretagne.fr
 * created 1.0 du 29 mai 2000
*  @version 1.2  august,5, 2002
  */

package modulecoGUI.grapheco;

//import java.awt.Panel;
//import java.awt.Label;
import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.Color;
import modulecoFramework.modeleco.CAgent;
import modulecoGUI.grapheco.ControlPanel;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class PanelSud extends EWorldEditor implements CAgentRepresentation {
	/**
	 * The name of this CAgentRepresentationContainer
	 */
	public String name;
	protected JLabel labelVide, labelTitre, labelAuteurs;
	protected GridLayout layout;

	public PanelSud() {

		//labelVide = new Label();
		//labelVide.setText("  ");
		//labelVide.setAlignment(Label.CENTER);
		labelTitre = new JLabel();
		labelTitre.setText("Moduleco");
		labelTitre.setHorizontalAlignment(JLabel.CENTER);
		labelAuteurs = new JLabel();
		labelAuteurs.setText(
			" Antoine.Beugnard@enst-bretagne.fr, Denis.Phan@enst-bretagne.fr ");
		labelAuteurs.setForeground(Color.darkGray.brighter());
		labelAuteurs.setHorizontalAlignment(JLabel.CENTER);
		layout = new GridLayout(2, 1);
		setLayout(layout);
		//add(labelVide);         
		add(labelTitre);
		add(labelAuteurs);
		//validate();  
	}

	public void setCAgent(CAgent cAgent) {
	}

	public void setCentralControl(CentralControl centralControl) {
	}

	public void updateImage() {
	}

	public void resetImage() {
	}

	public void addMouseMotionListener(MouseMotionListener mouseMotionListener) {
	}

	public void addMouseListener(MouseListener mouseListener) {
	}

	public void setControl(ControlPanel control) {
	}

	public void initComponents() {
	}

	public void update() {
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
