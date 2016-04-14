/**
 * Title:        Moduleco<p>
 * Description:  Je suis une classe qui représente des donnees de type "integer" <p>
 * Copyright:    Copyright (c)enst-bretagne
 * @author sebastien.chivoret@ensta.org
 * @Created on may 25, 2001 ; Revision 1, july,28
 * @version 1.2  august,5, 2002
 */
package modulecoGUI.grapheco.descriptor;

import java.util.ArrayList; //depuis JDK 1.2

//import java.awt.Container;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Color;
// import java.awt.Font;
// import java.awt.GridLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
// A supprimer par remplacement ModulecoTextField
import java.awt.event.FocusEvent;

import modulecoFramework.modeleco.CAgent;
import java.io.PrintWriter; //CK 04/02

//import java.awt.GridBagLayout; // supprimé dp 29/06/2001
//import java.awt.GridBagConstraints; // supprimé dp 29/06/2001

public class IntegerDataDescriptor extends DataDescriptor {
	protected int value; //the value of the data
	protected int min; //if there is a minimum value acceptable
	protected int max; //if there is a maximum value acceptable

	public IntegerDataDescriptor(
		CAgent ag,
		String cn,
		String n,
		int v,
		boolean e,
		int j) {
		agent = ag;
		completeName = cn;
		name = n;
		value = v;
		this.min = Integer.MIN_VALUE;
		this.max = Integer.MAX_VALUE;
		editable = e;
		labelForegroundColor = color[j];
	}

	public IntegerDataDescriptor(
		CAgent ag,
		String cn,
		String n,
		int v,
		boolean e) {

		this(ag, cn, n, v, e, 0);
	}

	public IntegerDataDescriptor(
		CAgent ag,
		String cn,
		String n,
		int v,
		int min,
		int max,
		boolean e) {
		agent = ag;
		completeName = cn + " [" + min + "," + max + "]";
		name = n;
		value = v;
		this.min = min;
		this.max = max;
		editable = e;

	}

	public int getValue() {
		return value;
	}

	public void setValue(int i) {
		value = i;
	}

	// DP 29/06/2001
	//the container is a label and a TextField
	public void makeContainer(ArrayList containerList) {
		//if the data is editable ; otherwise just 2 labels
		// container build in the superClasse

		super.makeContainer(containerList);
		label.setText(completeName);
		//label.setSize(label.getMinimumSize());
		//if (editable==true){											//the data is editable : creation of the
		final JTextField textField = new JTextField(); //JTextField
		textField.setText("" + value);
		if (value == -10000)
			textField.setText("");
		if (editable == false) {
			textField.setEditable(false);
		} else {
			textField.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent evt) {

					try {
						value = Integer.parseInt(textField.getText());
						//the value of the DataDescriptor is changed
						if ((value >= min) && (value <= max))
							//the value is acceptable : we change it (in the Agent)
							{
							textField.setBackground(Color.white);
							// textField.setSize(textField.getMinimumSize());
							set();
						} else { //the value is not acceptable : nothing is changed
							textField.setBackground(Color.red);
						}
					} catch (NumberFormatException ex) {
						textField.setBackground(Color.red);
					}

					//agent.updateDescriptors();
				}
			});

			textField.addFocusListener(
			//the JTextField becomes green if the focus is on it

			new FocusListener() {

				public void focusGained(FocusEvent e) {
					if (!(textField.getBackground().equals(Color.red)))
						textField.setBackground(Color.green);
				}

				public void focusLost(FocusEvent e) {
					if (!(textField.getBackground().equals(Color.red)))
						textField.setBackground(Color.white);
				}
			});
		}
		container.add(textField);

		/*else {											//the data is not editable (2 labels)
		   Label label2=new Label(""+value);	
		   label2.setAlignment(Label.LEFT);
		   container.add(label2,constraints);
		}*/
	}

	//the update depends if we update a 	
	// label or a JTextField (editable or not)
	public void updateContainer(JPanel container) {
		((JLabel) container.getComponent(0)).setText(completeName);
		((JLabel) container.getComponent(0)).setForeground(
			labelForegroundColor);
		//if (editable==true)
		if (value == -10000)
			 ((JTextField) container.getComponent(1)).setText("");
		else
			 ((JTextField) container.getComponent(1)).setText("" + value);
		//else ((Label)container.getComponent(0)).setText(""+value);

	}
	public void set() {
		//if (editable==true){
		try {
			Class[] intType = { java.lang.Integer.TYPE };
			Object[] intParameter = { new Integer(value)};
			java.lang.reflect.Method method;
			String modifiedName =
				name.substring(0, 1).toUpperCase() + name.substring(1);
			method = agent.getClass().getMethod("set" + modifiedName, intType);
			method.invoke(agent, intParameter); //Agent is changed
		} catch (NoSuchMethodException e) {
			System.out.println("NoSuchMethodException");
			String modifiedName =
				name.substring(0, 1).toUpperCase() + name.substring(1);
			System.out.println(
				"PUBLIC method set"
					+ modifiedName
					+ " Invoked by IntegerDataDescriptor not found in "
					+ agent.getClass().getName());
		} catch (java.lang.IllegalAccessException e) {
			System.out.println("java.lang.IllegalAccessException");
		} catch (java.lang.reflect.InvocationTargetException e) {
			System.out.println(
				(this.getClass()).getName()
					+ " "
					+ e.toString()
					+ " cause :"
					+ e.getCause());
			e.printStackTrace();
		}

	}

	public void printCVS(PrintWriter printWriter) {
		printWriter.print(value);
		printWriter.print(',');

	}
	public void printTxt(PrintWriter printWriter) {
		printWriter.print(value + " ");
	}
	//}
}