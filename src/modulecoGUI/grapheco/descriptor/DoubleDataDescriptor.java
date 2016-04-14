/**
 * Title:        Moduleco<p>
 * Description:  Je suis une classe qui représente des donnees de type "double" <p>
 * Copyright:    Copyright (c)enst-bretagne
 * @author sebastien.chivoret@ensta.org
 * created on may, 11, 2001
 * @version 1.2  august,5, 2002
 */
package modulecoGUI.grapheco.descriptor;

import java.util.ArrayList; //depuis JDK 1.2
import java.math.BigDecimal;

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

public class DoubleDataDescriptor extends DataDescriptor {
	/**
	* the value of the data
	*/
	protected double value;
	/**
		* the minimum acceptable value of the data (if any)
		*/
	protected double min;
	/**
		* the maximum acceptable value of the data (if any)
		*/
	protected double max;
	protected int decimalPlace;
	//DP 29/06/2001
	public DoubleDataDescriptor(
		CAgent ag,
		String cn,
		String n,
		double v,
		boolean e,
		int i,
		int j) {
		agent = ag;
		completeName = cn;
		name = n;
		value = v;
		this.min = Double.NEGATIVE_INFINITY;
		this.max = Double.POSITIVE_INFINITY;
		editable = e;
		decimalPlace = i;
		labelForegroundColor = color[j];
		//System.out.println("my name is : "+name+ " decimalPlace =" +i);
	}
	//DP 29/06/2001
	public DoubleDataDescriptor(
		CAgent ag,
		String cn,
		String n,
		double v,
		boolean e,
		int i) {
		this(ag, cn, n, v, e, i, 0);
		//System.out.println("my name is : "+name);
	}
	//DP 29/06/2001

	public DoubleDataDescriptor(
		CAgent ag,
		String cn,
		String n,
		double v,
		boolean e) {

		this(ag, cn, n, v, e, 0, 0);
		//System.out.println("my name is : "+name);
	}
	public DoubleDataDescriptor(
		CAgent ag,
		String cn,
		String n,
		double v,
		double min,
		double max,
		boolean e,
		int i) {
		agent = ag;
		completeName = cn + " [" + min + "," + max + "]";
		name = n;
		value = v;
		this.min = min;
		this.max = max;
		editable = e;
		decimalPlace = i;

	}
	public DoubleDataDescriptor(
		CAgent ag,
		String cn,
		String n,
		double v,
		double min,
		double max,
		boolean e) {
		agent = ag;
		completeName = cn + " [" + min + "," + max + "]";
		name = n;
		value = v;
		this.min = min;
		this.max = max;
		editable = e;
		decimalPlace = 0;

	}
	public double getValue() {
		return value;
	}

	public void setValue(double d) {
		value = d;
		//System.out.println("setValue() = "+value);
	}

	public double getRoundedValue() {
		if (decimalPlace != 0) {
			BigDecimal bd = new BigDecimal(value);
			bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
			//System.out.println("getRoundedValue() = " +bd.doubleValue()+ " decimalPlace =" +decimalPlace);
			return bd.doubleValue();
		} else {
			return value;
		}

	}
	//the container is a label and a TextField
	// DP 29/06/2001
	public void makeContainer(ArrayList containerList) {
		super.makeContainer(containerList);

		//JLabel label = new JLabel(completeName);	
		//label.setAlignment(JLabel.CENTER);

		//container.setLayout(new GridLayout(1,2));

		//container.add(label); //constraints
		//if (editable==true){	//the data is editable : creation of the TextField

		label.setText(completeName);

		final JTextField textField = new JTextField();
		textField.setText("" + getRoundedValue());

		if (editable == false) {
			textField.setEditable(false);
		} else {
			textField.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent evt) {

					try {
						value = Double.parseDouble(textField.getText());
						//the value of the DataDescriptor is changed
						if ((value >= min)
							&& (value <= max))
							//the value is acceptable : we change it (in the Agent)
							{
							textField.setBackground(Color.white);
							set();

						} else
							textField.setBackground(Color.red);
					} catch (NumberFormatException ex) {
						textField.setBackground(Color.red);
					}

					//System.out.println("on update l'agent.");		
					//agent.updateDescriptors();

				}
			});

			textField.addFocusListener(
			//the TextField becomes green if the focus is on it

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
		container.add(textField); //,constraints

		/*else{															//the data is not editable (2 labels)
		   Label label2=new Label(""+getRoundedValue());
		   label2.setAlignment (Label.LEFT);
		   container.add(label2);
				}*/
		container.validate();
	}

	public void updateContainer(JPanel container) {
		((JLabel) container.getComponent(0)).setText(completeName);
		((JLabel) container.getComponent(0)).setForeground(
			labelForegroundColor);
		//if (editable==true) 
		set();
		((JTextField) container.getComponent(1)).setText(
			"" + getRoundedValue());
		//System.out.println("DoubleDataDescriptor.updateContainer-() (ma valeur : "+getRoundedValue());
		//else {((Label)container.getComponent(0)).setText(""+getRoundedValue());}
	}

	public void set() {
		if (editable == true) {
			try {
				Class[] doubleType = { java.lang.Double.TYPE };
				Object[] doubleParameter = { new Double(value)};
				java.lang.reflect.Method method;
				String modifiedName =
					name.substring(0, 1).toUpperCase() + name.substring(1);
				method =
					agent.getClass().getMethod(
						"set" + modifiedName,
						doubleType);
				//System.out.println("method : "+method);
				method.invoke(agent, doubleParameter); //Agent is changed
			} catch (NoSuchMethodException e) {
				System.out.println(e.toString());
				String modifiedName =
					name.substring(0, 1).toUpperCase() + name.substring(1);
				System.out.println(
					"PUBLIC method set"
						+ modifiedName
						+ "  Invoked by DoubleDataDescriptor not found in "
						+ agent.getClass().getName());
			} catch (java.lang.IllegalAccessException e) {
				System.out.println(e.toString());
			} catch (java.lang.reflect.InvocationTargetException e) {
				System.out.println(
					(this.getClass()).getName() + " " + e.toString());

			}
		}
	}

	public void printCVS(PrintWriter printWriter) {
		printWriter.print(value);
		printWriter.print(',');
	}
	public void printTxt(PrintWriter printWriter) {
		printWriter.print(value + " ");
	}
}