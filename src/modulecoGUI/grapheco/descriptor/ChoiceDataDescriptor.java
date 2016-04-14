/**
 * Title:        ChoiceDataDescriptor<p>
 * Description:  Je suis une classe qui représente des donnees sous forme de menu deroulant <p>
 * Copyright:    Copyright (c)enst-bretagne
 * @author sebastien.chivoret@ensta.org
 * Created on may, 11, 2001; 
 * @version 1.2  august,5, 2002
 */
package modulecoGUI.grapheco.descriptor;
import java.util.ArrayList; //depuis JDK 1.2
import javax.swing.JComboBox;
import javax.swing.JPanel;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import modulecoFramework.modeleco.CAgent;
import java.io.PrintWriter; //CK 04/02
public class ChoiceDataDescriptor extends DataDescriptor {
	protected String[] stringArray; //the possible values of the data
	public String value;
	protected JComboBox choice; //the current value of the data
	public ChoiceDataDescriptor(
		CAgent ag,
		String cn,
		String n,
		String[] sa,
		String defaultValue,
		boolean e) {
		agent = ag;
		completeName = cn;
		name = n;
		stringArray = sa;
		value = defaultValue;
		editable = e;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String v) {
		value = v;
	}
	//the container is a label and a Choice
	//if the data is editable ; otherwise just 2 labels
	// DP 29/06/2001
	public void makeContainer(ArrayList containerList) {
		//if (editable==true) //the data is editable : creation of the Choice
		super.makeContainer(containerList);
		label.setText(completeName);
		choice = new JComboBox();
		for (int i = 0; i < stringArray.length; i++) {
			choice.addItem(stringArray[i]);
		}
		choice.setSelectedItem(value);
		if (editable == false) {
			choice.setEnabled(false);
		} else {
			choice.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent evt) {
					value = (String) evt.getItem();
					//value=choice.getSelectedIndex(); //the value of the
					// DataDescriptor is changed
					set();
					//agent.updateDescriptors();
				}
			});
		}
		container.add(choice);
		//}
		/*
		 * else { //the data is not editable (2 labels) Label label2=new
		 * Label(value); container.add(label,constraints);
		 * container.add(label2);}
		 */
	}
	public void updateContainer(JPanel container) {
		//if (editable==true) {
		 ((JComboBox) container.getComponent(1)).setSelectedItem(value);
		//}
		//else ((Label)container.getComponent(1)).setText(value);
	}
	public void set() {
		//if (editable==true){
		try {
			java.lang.reflect.Method method;
			String modifiedName =
				name.substring(0, 1).toUpperCase() + name.substring(1);
			Class[] stringType = { Class.forName("java.lang.String")};
			Object[] stringParameter = { new String(value)};
			//Class[] intType={ java.lang.Integer.TYPE };
			//Object[] intParameter={new Integer(value)};
			method =
				agent.getClass().getMethod("set" + modifiedName, stringType);
			method.invoke(agent, stringParameter); //Agent is changed
		} catch (NoSuchMethodException e) {
			System.out.println("NoSuchMethodException");
			String modifiedName =
				name.substring(0, 1).toUpperCase() + name.substring(1);
			System.out.println(
				"PUBLIC method set"
					+ modifiedName
					+ " Invoked by ChoiceDataDescriptor not found in "
					+ agent.getClass().getName());
		} catch (java.lang.IllegalAccessException e) {
			System.out.println("java.lang.IllegalAccessException");
		} catch (java.lang.reflect.InvocationTargetException e) {
			System.out.println(
				(this.getClass()).getName() + " " + e.toString());
			//e.printStackTrace();
		} catch (java.lang.ClassNotFoundException e) {
			System.out.println("java.lang.ClassNotFoundException");
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