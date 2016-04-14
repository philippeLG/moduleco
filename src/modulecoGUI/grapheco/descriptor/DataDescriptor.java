/**
 * Title:        Moduleco<p>
 * Description:  Je suis une classe abstraite qui représente un parametre d'un agent. Elles sont instanciees <p>
 * sous plusieurs formes (double,int,boolean). Chacune contient la valeur du parametre, son nom (dans le code <p>
 * Agent.java et le nom qui apparait dans la fenetre d'edition) et un booleen (editable) qui est vrai s'il est<p>
 * modifiable par l'utilisateur. De plus, chacune des instanciations de cette classe abstraite possede une <p>
 * methode qui renvoie un Container (sa representation graphique).
 * Copyright:    Copyright (c)enst-bretagne
 * @author sebastien.chivoret@ensta.org
 * Created on may, 11, 2001
 * @version 1.2  august,5, 2002
 */
package modulecoGUI.grapheco.descriptor;
import java.util.ArrayList; //depuis JDK 1.2
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.Color;
import modulecoFramework.modeleco.CAgent;
import modulecoGUI.grapheco.EAgentEditor;
import java.io.PrintWriter; //CK 04/02
public abstract class DataDescriptor implements Cloneable {
	protected String completeName;
	protected String name;
	protected boolean editable;
	protected EAgentEditor editor;
	protected CAgent agent;
	public JPanel container; //DP 17/10/2003
	protected JLabel label;
	protected Color color[];
	protected Color labelForegroundColor;
	// DP 29/06/2001
	public DataDescriptor() {
		name = "";
		completeName = "";
		editable = false;
		color = new Color[12];
		color[0] = Color.black;
		color[1] = Color.blue;
		color[2] = Color.red;
		color[3] = Color.green.darker();
		color[4] = Color.magenta;
		color[5] = Color.cyan;
		color[6] = Color.darkGray;
		color[7] = Color.yellow;
		color[8] = Color.gray;
		color[9] = Color.orange;
		color[10] = Color.pink;
		color[10] = Color.white;
		labelForegroundColor = color[0]; // A supprimer ultérieurement ?
		container = new JPanel();
	}
	public String getCompleteName() {
		return completeName;
	}
	public String getDataName() {
		return name;
	}
	public boolean getEditable() {
		return editable;
	}
	public JPanel buildContainer(ArrayList containerList) {
		makeContainer(containerList);
		containerList.add(container);
		return container;
	}
	public void makeContainer(ArrayList containerList) {
		container.setLayout(new GridLayout(1, 2));
		label = new JLabel();
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setBackground(Color.gray);
		label.setForeground(labelForegroundColor);
		container.add(label);
		//System.out.println("DataFescritor.makeContainer()");
		label.setVisible(true);
		//label.setSize (60, 40);
		container.validate();
	}
	public abstract void updateContainer(JPanel container);
	public DataDescriptor getClone() {
		try {
			return ((DataDescriptor) this.clone());
		} catch (java.lang.CloneNotSupportedException e) {
			System.out.println("DataDescriptor : " + e.toString());
			return (null);
		}
	}
	public abstract void set();
	public void setAgent(CAgent cAgent) {
		agent = cAgent;
	}
	public abstract void printTxt(PrintWriter printWriter);
	public abstract void printCVS(PrintWriter printWriter);
}